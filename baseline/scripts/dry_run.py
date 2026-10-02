"""Run MonoMorph's steps that need no LLM on the prepared inputs: static analysis, decomposition preprocessing and
inter-service dependency detection. Reports, per app, how many classes MonoMorph will place and how many API classes
it will hand to the LLM agents, so input problems show up before any tokens are spent.

Needs JAVA_EXEC_PATH pointing to a JDK 17 java executable.
"""
import argparse
import json
import logging
import sys
import types
from collections import Counter
from pathlib import Path

import prepare_inputs

BASELINE_DIR = Path(__file__).resolve().parent.parent
REPO_DIR = BASELINE_DIR.parent
sys.path.insert(0, str(REPO_DIR))

from monomorph.analysis import LocalAnalysis  # noqa: E402
from monomorph.const import ApproachType, RefactoringMethod  # noqa: E402
from monomorph.helpers import HelperManager  # noqa: E402
from monomorph.models import Decomposition  # noqa: E402
from monomorph.monomorph import MonoMorph  # noqa: E402
from monomorph.planning.dependencies import DependencyDetector  # noqa: E402
from monomorph.planning.preprocessing import DecompositionPreprocessor  # noqa: E402
from monomorph.planning.proxies import ProxyPlanner  # noqa: E402
from monomorph.project import MicroservicesProject  # noqa: E402


def dry_run(app: str, cfg: dict, work_dir: Path) -> dict:
    source = prepare_inputs.prepare(app, cfg, work_dir)
    analysis_path = work_dir / app / "analysis"
    model = LocalAnalysis(app, str(source), str(analysis_path), create_subdirs=True).load()
    data = json.loads((BASELINE_DIR / "inputs" / app / "decomposition.monomorph.json").read_text())
    decomposition = Decomposition(data["name"], app, data["partitions"], data["language"], data["granularity"])
    listed = {c for p in decomposition.partitions for c in p.classes}
    analyzed = set(model.get_class_names())
    updated = DecompositionPreprocessor(decomposition, model, include_tests=False, restrictive_selection=True,
                                        project_root=str(source)).update_decomposition()
    detector = DependencyDetector(updated, model)
    _, method_interactions, other_interactions = detector.find_new_apis_partition()
    api_classes = detector.to_api_classes(method_interactions, other_interactions)
    owners = Counter({ms: len(classes) for ms, classes in api_classes.items()})
    with_methods = sum(1 for classes in api_classes.values() for c in classes if c.methods)
    # Planning and project assembly with every API class set to ID-based (stands in for the LLM decisions)
    helper_manager = HelperManager(cfg["package"])
    flat_api_classes = {c.name: c for classes in api_classes.values() for c in classes}
    decisions = {name: RefactoringMethod(decision=ApproachType.ID_BASED, reasoning="dry run")
                 for name in flat_api_classes}
    planned = ProxyPlanner(model, helper_manager).find_and_name_all_api_classes(decisions, flat_api_classes)
    planning_check = {approach.value: check_sorting(approach, model, cfg, updated, detector, method_interactions,
                                                    other_interactions)
                      for approach in (ApproachType.ID_BASED, ApproachType.DTO_BASED)}
    project = MicroservicesProject(app, cfg["package"], updated, str(source), str(work_dir / app / "dry_run_output"),
                                   helper_manager, build_tool="maven")
    not_copied = sorted(c for ms in project.microservices.values()
                        for c in ms.partition.classes if ms.class_file_map.get(c) is None and "$" not in c)
    return {
        "analyzed_classes": len(analyzed),
        "listed_classes_missing_from_analysis": sorted(listed - analyzed),
        "classes_duplicated_into_every_service": len(updated.partitions[0].duplicated_classes),
        "api_classes": sum(owners.values()),
        "api_classes_needing_llm_decision": with_methods,
        "api_classes_per_owner": dict(owners),
        "planned_api_classes_id_only": len(planned),
        "listed_classes_not_copied": not_copied,
        "planning_check": planning_check,
    }


def check_sorting(approach: ApproachType, model, cfg: dict, updated, detector, method_interactions,
                  other_interactions) -> str:
    """Run MonoMorph's own (unmodified) sort_by_ms_and_approach, the planning step right after the LLM decisions,
    with every API class set to `approach`. Exceptions here abort a real run after the decision tokens are spent."""
    flat = {c.name: c for classes in detector.to_api_classes(method_interactions, other_interactions).values()
            for c in classes}
    decisions = {name: RefactoringMethod(decision=approach, reasoning="dry run") for name in flat}
    planned = ProxyPlanner(model, HelperManager(cfg["package"])).find_and_name_all_api_classes(decisions, flat)
    mono = types.SimpleNamespace(updated_decomposition=updated, logger=logging.getLogger("monomorph"))
    for name in ("sort_by_ms_and_approach", "_assign_microservice", "_assign_client_microservice",
                 "_get_invoking_classes"):
        setattr(mono, name, types.MethodType(getattr(MonoMorph, name), mono))
    try:
        mono.sort_by_ms_and_approach(planned)
    except RecursionError:
        return "RecursionError"
    except Exception as e:
        return f"{type(e).__name__}: {e}"
    # A client microservice of None (an invoking class whose own microservice was not assigned yet, as classes are
    # assigned in iteration order) makes code generation fail later with "Invalid parameters for client prompt"
    none_clients = sorted(c.name for c in planned.values() if c.client_microservices and None in c.client_microservices)
    if none_clients:
        return f"client microservice None for {none_clients}"
    return "ok"


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("apps", nargs="*")
    parser.add_argument("--work-dir", default=str(REPO_DIR / "work"))
    parser.add_argument("--output", default=None, help="write the report as JSON to this file")
    args = parser.parse_args()
    logging.getLogger("monomorph").setLevel(logging.WARNING)
    apps = json.loads((BASELINE_DIR / "apps.json").read_text())
    report = {}
    for app in args.apps or apps:
        report[app] = dry_run(app, apps[app], Path(args.work_dir))
        r = report[app]
        print(f"{app}: {r['analyzed_classes']} classes, {r['api_classes']} API classes "
              f"({r['api_classes_needing_llm_decision']} need an ID/DTO decision), "
              f"{r['classes_duplicated_into_every_service']} duplicated into every service, "
              f"{len(r['listed_classes_missing_from_analysis'])} listed classes missing from analysis, "
              f"{len(r['listed_classes_not_copied'])} listed classes not copied", flush=True)
        for approach, result in r["planning_check"].items():
            if result != "ok":
                print(f"  planning with every API class {approach}: {result}", flush=True)
    if args.output:
        Path(args.output).write_text(json.dumps(report, indent=2))


if __name__ == "__main__":
    main()

"""Build the source trees MonoMorph runs on from the verbatim input-kit copies.

baseline/inputs/<app>/monolith is never modified. For each app this script copies
inputs/<app>/monolith/<source_subdir> to work/<app>/source and applies the app's "prepare" step, if any.

MonoMorph needs a single Maven module (<source>/pom.xml + <source>/src/main/java). Two monoliths are
multi-module and are flattened; the edits below are structural only and do not change behaviour:

- flatten_booking: buildingblocks is merged into booking-app (sources + dependencies), the reactor parent is
  replaced by spring-boot-starter-parent 3.4.1 (the reactor's own parent). One pattern-matching instanceof in
  Mediator.java is rewritten as instanceof + cast, because MonoMorph's Spoon-based analyzer crashes on it.
- flatten_petclinic: the aggregator parent (whose only module is spring-petclinic-server) is replaced by its
  own parent spring-boot-starter-parent 2.1.3.RELEASE.
"""
import argparse
import json
import re
import shutil
from pathlib import Path

BASELINE_DIR = Path(__file__).resolve().parent.parent


def replace_once(text: str, old: str, new: str, what: str) -> str:
    count = text.count(old)
    if count != 1:
        raise RuntimeError(f"{what}: expected exactly one match, found {count}")
    return text.replace(old, new)


def replace_parent(pom: str, artifact_id: str, version: str, what: str) -> str:
    pattern = re.compile(r"<parent>.*?</parent>", re.S)
    if len(pattern.findall(pom)) != 1:
        raise RuntimeError(f"{what}: expected exactly one <parent> block")
    new_parent = ("<parent>\n    <groupId>org.springframework.boot</groupId>\n"
                  f"    <artifactId>{artifact_id}</artifactId>\n    <version>{version}</version>\n"
                  "    <relativePath/>\n  </parent>")
    return pattern.sub(new_parent, pom, count=1)


def flatten_booking(monolith: Path, source: Path):
    buildingblocks = monolith / "buildingblocks"
    # Merge the buildingblocks sources (package root `buildingblocks`, no clash with io.bookingmonolith)
    shutil.copytree(buildingblocks / "src" / "main" / "java", source / "src" / "main" / "java", dirs_exist_ok=True)
    # Merge the poms
    pom = (source / "pom.xml").read_text()
    bb_pom = (buildingblocks / "pom.xml").read_text()
    pom = replace_parent(pom, "spring-boot-starter-parent", "3.4.1", "booking-app pom")
    # lombok.version was defined in the removed reactor parent
    pom = replace_once(pom, "<java.version>17</java.version>",
                       "<java.version>17</java.version>\n    <lombok.version>1.18.36</lombok.version>",
                       "booking-app properties")
    bb_deps = re.search(r"<dependencies>(.*?)</dependencies>", bb_pom, re.S).group(1)
    # lombok is already declared by booking-app
    bb_deps = re.sub(r"\s*<!-- Lombok -->", "", bb_deps)
    bb_deps = re.sub(r"\s*<dependency>\s*<groupId>org\.projectlombok</groupId>.*?</dependency>", "", bb_deps,
                     flags=re.S)
    bb_dependency = re.search(
        r"\s*<dependency>\s*<groupId>io\.buildingblocks</groupId>\s*<artifactId>buildingblocks</artifactId>.*?</dependency>",
        pom, re.S)
    if bb_dependency is None:
        raise RuntimeError("booking-app pom: buildingblocks dependency not found")
    pom = pom.replace(bb_dependency.group(0), "\n    <!-- merged from buildingblocks/pom.xml -->" + bb_deps.rstrip())
    (source / "pom.xml").write_text(pom)
    # Work around the Spoon analyzer crash (semantics-preserving rewrite)
    mediator = source / "src" / "main" / "java" / "buildingblocks" / "mediator" / "Mediator.java"
    mediator.write_text(replace_once(
        mediator.read_text(),
        "if (genericInterface instanceof ParameterizedType paramType) {",
        "if (genericInterface instanceof ParameterizedType) {\n"
        "                ParameterizedType paramType = (ParameterizedType) genericInterface;",
        "Mediator.java"))


def flatten_petclinic(monolith: Path, source: Path):
    pom = (source / "pom.xml").read_text()
    pom = replace_parent(pom, "spring-boot-starter-parent", "2.1.3.RELEASE", "spring-petclinic-server pom")
    # groupId/version were inherited from the removed aggregator parent
    pom = replace_once(pom, "<artifactId>spring-petclinic-server</artifactId>",
                       "<groupId>org.springframework.samples</groupId>\n"
                       "    <artifactId>spring-petclinic-server</artifactId>\n    <version>2.1.3</version>",
                       "spring-petclinic-server artifactId")
    (source / "pom.xml").write_text(pom)


PREPARE_STEPS = {"flatten_booking": flatten_booking, "flatten_petclinic": flatten_petclinic}


def prepare(app: str, cfg: dict, work_dir: Path) -> Path:
    monolith = BASELINE_DIR / "inputs" / app / "monolith"
    source = work_dir / app / "source"
    if source.exists():
        shutil.rmtree(source)
    shutil.copytree(monolith / cfg["source_subdir"], source)
    if cfg["prepare"]:
        PREPARE_STEPS[cfg["prepare"]](monolith, source)
    return source


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("apps", nargs="*", help="apps to prepare (default: all)")
    parser.add_argument("--work-dir", default=str(BASELINE_DIR.parent / "work"))
    args = parser.parse_args()
    apps = json.loads((BASELINE_DIR / "apps.json").read_text())
    for app in args.apps or apps:
        source = prepare(app, apps[app], Path(args.work_dir))
        print(f"{app}: {source}")


if __name__ == "__main__":
    main()

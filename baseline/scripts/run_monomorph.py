"""Run MonoMorph on the input-kit applications to produce the baseline candidate repositories.

For each app: prepare work/<app>/source (see prepare_inputs.py), then run MonoMorph's CLI in its own process with
working directory runs/<tag>/<app>, so its logs, LLM cache and static-analysis data stay per app. Every LLM role
(code generation, ID/DTO decision, response parsing, compilation correction, fallback) uses the same model.

Required environment (a .env file at the repository root is also read):
  DEEPSEEK_API_KEY         DeepSeek API key
  JAVA_EXEC_PATH           java executable of a JDK 17 (MonoMorph's bundled analysis jars do not run on JDK 21)
  CUSTOM_DOCKER_SOCKET     Docker socket, defaults to unix:///var/run/docker.sock
"""
import argparse
import json
import os
import re
import subprocess
import sys
import time
from datetime import datetime
from pathlib import Path

import prepare_inputs

BASELINE_DIR = Path(__file__).resolve().parent.parent
REPO_DIR = BASELINE_DIR.parent
DEFAULT_MODEL = "mm_deepseek/deepseek-v4-pro::high"


def load_dotenv_file():
    env_file = REPO_DIR / ".env"
    if env_file.exists():
        for line in env_file.read_text().splitlines():
            match = re.match(r"\s*([A-Za-z_][A-Za-z0-9_]*)\s*=\s*(.*)\s*$", line)
            if match and match.group(1) not in os.environ:
                os.environ[match.group(1)] = match.group(2).strip().strip("'\"")


def check_environment():
    errors = []
    if not os.getenv("DEEPSEEK_API_KEY"):
        errors.append("DEEPSEEK_API_KEY is not set")
    java = os.getenv("JAVA_EXEC_PATH")
    if not java:
        errors.append("JAVA_EXEC_PATH is not set (it must point to a JDK 17 java executable)")
    else:
        version = subprocess.run([java, "-version"], capture_output=True, text=True).stderr
        if not re.search(r'version "17\.', version):
            errors.append(f"JAVA_EXEC_PATH must be a JDK 17, got: {version.strip().splitlines()[-1]}")
    os.environ.setdefault("CUSTOM_DOCKER_SOCKET", "unix:///var/run/docker.sock")
    if subprocess.run(["docker", "info"], capture_output=True).returncode != 0:
        errors.append("the Docker daemon is not reachable")
    if errors:
        sys.exit("Environment check failed:\n  - " + "\n  - ".join(errors))


def write_run_dockerfile(app: str, run_dir: Path, image_suffix: str) -> Path:
    dockerfile = (BASELINE_DIR / "inputs" / app / "Dockerfile").read_text()
    if image_suffix:
        dockerfile = re.sub(r"^(FROM\s+\S+)", rf"\g<1>{image_suffix}", dockerfile, count=1, flags=re.M)
    path = run_dir / "Dockerfile"
    path.write_text(dockerfile)
    return path


def run_app(app: str, cfg: dict, args, run_root: Path) -> dict:
    run_dir = run_root / app
    run_dir.mkdir(parents=True, exist_ok=True)
    source = prepare_inputs.prepare(app, cfg, REPO_DIR / "work")
    dockerfile = write_run_dockerfile(app, run_dir, args.image_suffix)
    # MonoMorph reads the analysis from <path>/<app>/ only when the app name does not occur anywhere in <path>
    # (monomorph.py: `create_subdirs = app_name not in analysis_path`), but the analysis jar always writes there.
    # So the data goes to runs/<tag>/analysis/<app>/ rather than under run_dir, whose path contains the app name.
    analysis_path = run_root / "analysis"
    if app in str(analysis_path):
        sys.exit(f"The analysis path {analysis_path} must not contain the app name '{app}'; use another --runs-dir/--tag")
    command = [
        sys.executable, str(REPO_DIR / "cli.py"),
        "--app", app,
        "--app-source-code-path", str(source),
        "--decomposition-file", str(BASELINE_DIR / "inputs" / app / "decomposition.monomorph.json"),
        "--package", cfg["package"],
        "--java-version", cfg["java_version"],
        "--build-tool", "maven",
        "--original-dockerfile-path", str(dockerfile),
        "--refact-approach", "Hybrid",
        "--refact-model", args.model,
        "--parser-model", args.model,
        "--decision-model", args.model,
        "--correction-model", args.model,
        "--fallback-model", args.model,
        "--analysis-data-path", str(analysis_path),
        "--out-path", str(run_dir / "output"),
        "--llm-cache-path", str(run_dir / "llm_cache.db"),
        "--run-id", f"{app}-{args.tag}",
    ]
    if args.multithreading:
        command.append("--use-multithreading")
    env = dict(os.environ)
    env.setdefault("MONOMORPH_LLM_INVOKE_TIMEOUT_SECONDS", "900")
    # MonoMorph starts a local Java import-parser server on a fixed port (50051) during project assembly; apps running
    # in parallel would collide there ("Timeout waiting for gRPC server to be healthy"), so each app gets its own port
    env.setdefault("REFACTOR_SERVER_PORT", str(50100 + list(json.loads((BASELINE_DIR / "apps.json").read_text())).index(app)))
    print(f"[{datetime.now():%H:%M:%S}] {app}: starting (logs in {run_dir / 'monomorph.log'})", flush=True)
    start = time.time()
    with open(run_dir / "monomorph.log", "w") as log:
        try:
            returncode = subprocess.run(command, cwd=run_dir, env=env, stdout=log, stderr=subprocess.STDOUT,
                                        timeout=args.timeout_hours * 3600).returncode
        except subprocess.TimeoutExpired:
            returncode = "timeout"
    result = {"app": app, "returncode": returncode, "wall_time_seconds": round(time.time() - start),
              "run_dir": str(run_dir), "command": command}
    (run_dir / "run_result.json").write_text(json.dumps(result, indent=2))
    print(f"[{datetime.now():%H:%M:%S}] {app}: finished with {returncode} in {result['wall_time_seconds']}s",
          flush=True)
    return result


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("apps", nargs="*", help="apps to run (default: all, in apps.json order)")
    parser.add_argument("--model", default=DEFAULT_MODEL)
    parser.add_argument("--tag", default=datetime.now().strftime("%y%m%d%H%M"), help="run tag (default: timestamp)")
    parser.add_argument("--runs-dir", default=str(REPO_DIR / "runs"))
    parser.add_argument("--timeout-hours", type=float, default=6.0, help="per-app wall-clock limit")
    parser.add_argument("--image-suffix", default="",
                        help="suffix appended to the Dockerfile base image, e.g. -sandboxca for the images built by "
                             "build_sandbox_images.sh")
    parser.add_argument("--no-multithreading", dest="multithreading", action="store_false")
    args = parser.parse_args()
    load_dotenv_file()
    check_environment()
    apps = json.loads((BASELINE_DIR / "apps.json").read_text())
    selected = args.apps or list(apps)
    unknown = [a for a in selected if a not in apps]
    if unknown:
        sys.exit(f"Unknown apps: {unknown}")
    run_root = Path(args.runs_dir) / args.tag
    results = [run_app(app, apps[app], args, run_root) for app in selected]
    (run_root / "summary.json").write_text(json.dumps(results, indent=2))


if __name__ == "__main__":
    main()

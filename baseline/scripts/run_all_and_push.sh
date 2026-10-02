#!/usr/bin/env bash
# Run MonoMorph on the apps one at a time (smallest first) and, after each app, copy its result into
# baseline/candidates/<app>/ and commit + push it, so finished apps survive if the session's container goes away.
#
#   baseline/scripts/run_all_and_push.sh <tag> <branch> [extra run_monomorph.py args...]
#
# Per app, baseline/candidates/<app>/ holds:
#   code/             the candidate microservices (output/refactored_code/<app>-<time>-<id>/), without .git and target/
#   monomorph.log     MonoMorph's full log
#   run_result.json   return code and wall time
# Apps that crash or time out get the log and run_result.json only, as their real baseline result.
set -uo pipefail

TAG="$1"; BRANCH="$2"; shift 2
REPO="$(cd "$(dirname "$0")/../.." && pwd)"
# Fewest services first, then fewest lines of Java (see README)
APPS=(booking petclinic lakeside zlt newbee goodskill youlai passjava gulimall ecommerce)

push_with_retry() {
  for delay in 0 2 4 8 16; do
    sleep "$delay"
    git -C "$REPO" push -q origin "$BRANCH" && return 0
  done
  echo "push failed" >&2; return 1
}

for app in "${APPS[@]}"; do
  if [ -f "$REPO/baseline/candidates/$app/run_result.json" ]; then
    echo "[$(date +%H:%M:%S)] $app: already delivered, skipping"; continue
  fi
  docker info > /dev/null 2>&1 || { (nohup dockerd > /tmp/dockerd.log 2>&1 &); sleep 5; }
  (cd "$REPO" && uv run --frozen python baseline/scripts/run_monomorph.py "$app" --tag "$TAG" "$@")

  run_dir="$REPO/runs/$TAG/$app"
  dest="$REPO/baseline/candidates/$app"
  rm -rf "$dest"; mkdir -p "$dest"
  candidate="$(ls -d "$run_dir"/output/refactored_code/*/ 2>/dev/null | sort | tail -1)"
  if [ -n "$candidate" ]; then
    cp -a "$candidate" "$dest/code"
    find "$dest/code" \( -name .git -o -name target \) -prune -exec rm -rf {} +
  fi
  cp "$run_dir/monomorph.log" "$run_dir/run_result.json" "$dest/" 2>/dev/null
  # GitHub rejects files over 100MB; never commit the API key
  big="$(find "$dest" -type f -size +95M)"
  if [ -n "$big" ] || grep -rqF -- "$DEEPSEEK_API_KEY" "$dest"; then
    echo "[$(date +%H:%M:%S)] $app: NOT committed (oversized file or secret found): $big" >&2
    continue
  fi
  returncode="$(python3 -c "import json;print(json.load(open('$dest/run_result.json'))['returncode'])" 2>/dev/null)"
  git -C "$REPO" add -f "baseline/candidates/$app"
  git -C "$REPO" commit -q -m "baseline: MonoMorph candidate for $app (run $TAG, returncode $returncode)

Co-Authored-By: Claude Opus 5.5 <noreply@anthropic.com>
Claude-Session: https://claude.ai/code/session_01PUdj2RiqkKMui89Q6AaD3e" -- "baseline/candidates/$app"
  push_with_retry && echo "[$(date +%H:%M:%S)] $app: delivered (returncode $returncode, code: ${candidate:+yes}${candidate:-no})"
done
echo "[$(date +%H:%M:%S)] all apps done"

#!/usr/bin/env bash
# Parallel version of run_all_and_push.sh: start this script N times (one per worker). Each worker takes the next
# app, smallest first, that is neither delivered (baseline/candidates/<app>/run_result.json) nor claimed by another
# worker (runs/<tag>/locks/<app>, created atomically with mkdir), runs it, then commits and pushes its result.
# Git operations of all workers are serialized with flock.
#
#   baseline/scripts/run_parallel_and_push.sh <tag> <branch> [extra run_monomorph.py args...]
#   baseline/scripts/run_parallel_and_push.sh --deliver <app> <tag> <branch>   # wait for a run started elsewhere,
#                                                                              # deliver it, then work as a worker
#
# What MonoMorph runs per app is isolated: its own work/<app>/, runs/<tag>/<app>/ (logs, LLM cache, checkpoints,
# output), runs/<tag>/analysis/<app>/, and Docker images/containers named <app>-<service>-<uuid>, without volumes or
# published ports, removed after each service's validation.
set -uo pipefail

DELIVER_ONLY=""
if [ "${1:-}" = "--deliver" ]; then DELIVER_ONLY="$2"; shift 2; fi
TAG="$1"; BRANCH="$2"; shift 2
REPO="$(cd "$(dirname "$0")/../.." && pwd)"
# Fewest services first, then fewest lines of Java (see README)
APPS=(booking petclinic lakeside zlt newbee goodskill youlai passjava gulimall ecommerce)
LOCKS="$REPO/runs/$TAG/locks"
mkdir -p "$LOCKS"

log() { echo "[$(date +%H:%M:%S)] $*"; }

deliver() {
  local app="$1" run_dir="$REPO/runs/$TAG/$1" dest="$REPO/baseline/candidates/$1" candidate big returncode
  (
    flock 9
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
      log "$app: NOT committed (oversized file or secret found): $big" >&2; exit 1
    fi
    returncode="$(python3 -c "import json;print(json.load(open('$dest/run_result.json'))['returncode'])" 2>/dev/null)"
    git -C "$REPO" add -f "baseline/candidates/$app"
    git -C "$REPO" commit -q -m "baseline: MonoMorph candidate for $app (run $TAG, returncode $returncode)

Co-Authored-By: Claude Opus 5.5 <noreply@anthropic.com>
Claude-Session: https://claude.ai/code/session_01PUdj2RiqkKMui89Q6AaD3e" -- "baseline/candidates/$app"
    for delay in 0 2 4 8 16; do
      sleep "$delay"
      if git -C "$REPO" push -q origin "$BRANCH"; then
        log "$app: delivered (returncode $returncode, code: ${candidate:+yes}${candidate:-no})"; exit 0
      fi
    done
    log "$app: push failed" >&2
  ) 9> "$REPO/runs/$TAG/git.lock"
}

if [ -n "$DELIVER_ONLY" ]; then
  until [ -f "$REPO/runs/$TAG/$DELIVER_ONLY/run_result.json" ]; do sleep 30; done
  deliver "$DELIVER_ONLY"
fi

for app in "${APPS[@]}"; do
  [ -f "$REPO/baseline/candidates/$app/run_result.json" ] && continue
  mkdir "$LOCKS/$app" 2>/dev/null || continue   # claimed by another worker
  docker info > /dev/null 2>&1 || { (nohup dockerd > /tmp/dockerd.log 2>&1 &); sleep 5; }
  rm -f "$REPO/runs/$TAG/$app/run_result.json"
  (cd "$REPO" && uv run --frozen python baseline/scripts/run_monomorph.py "$app" --tag "$TAG" "$@")
  deliver "$app"
done
log "worker done"

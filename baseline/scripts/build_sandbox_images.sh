#!/usr/bin/env bash
# Only needed in the Claude Code cloud environment, whose egress gateway re-signs outbound TLS: containers there
# must trust the gateway CAs or Maven cannot download anything (PKIX errors). This builds
# maven:3.9-eclipse-temurin-{8,17,21}-sandboxca on top of the official images; use them with
# `run_monomorph.py --image-suffix -sandboxca`. Not needed on a normal machine.
set -euo pipefail

CA_BUNDLE="${CA_BUNDLE:-/root/.ccr/ca-bundle.crt}"
build_dir="$(mktemp -d)"
trap 'rm -rf "$build_dir"' EXIT

# Keep only the Anthropic proxy/gateway CAs from the bundle
awk -v dir="$build_dir" '/BEGIN CERT/{n++; f=sprintf("%s/cert-%03d.pem", dir, n)} {if (f) print > f}' "$CA_BUNDLE"
for cert in "$build_dir"/cert-*.pem; do
  if openssl x509 -in "$cert" -noout -subject 2>/dev/null | grep -q Anthropic; then
    mv "$cert" "$build_dir/sandbox-$(basename "$cert" .pem).crt"
  else
    rm "$cert"
  fi
done

for jdk in 8 17 21; do
  cat > "$build_dir/Dockerfile" <<EOF
FROM maven:3.9-eclipse-temurin-$jdk
COPY sandbox-*.crt /usr/local/share/ca-certificates/
RUN for f in /usr/local/share/ca-certificates/sandbox-*.crt; do \\
      keytool -importcert -noprompt -alias "\$(basename "\$f" .crt)" -file "\$f" \\
        -keystore "\$(find "\$JAVA_HOME" -name cacerts | head -1)" -storepass changeit >/dev/null; \\
    done && update-ca-certificates >/dev/null
EOF
  docker build -q -t "maven:3.9-eclipse-temurin-$jdk-sandboxca" "$build_dir"
done

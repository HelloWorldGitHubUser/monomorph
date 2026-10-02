"""Convert an input-kit decomposition into MonoMorph's decomposition format.

input-kit format: {"<service>": [owned classes..., "", shared classes the service uses...], ..., "shared": [...]}
MonoMorph format: {"name", "language", "granularity", "partitions": [{"name", "classes"}]}

Shared classes are handled the same way as in MonoMorph's own sample decompositions
(examples/spring-petclinic): a shared class is listed in every partition that uses it, and MonoMorph
copies it into each of those services as a local class. The top-level "shared" key is not turned into
a partition. Classes listed in no partition are duplicated into every service by MonoMorph itself.
"""
import argparse
import json
from collections import Counter


def convert(decomposition: dict, name: str) -> tuple[dict, dict]:
    shared = set(decomposition.get("shared", []))
    partitions = []
    for service, entries in decomposition.items():
        if service == "shared":
            continue
        # owned classes come before the "" separator, shared classes the service uses come after it
        classes = list(dict.fromkeys(c for c in entries if c))
        partitions.append({"name": service, "classes": classes})
    counts = Counter(c for p in partitions for c in p["classes"])
    listed = set(counts)
    stats = {
        "partitions": {p["name"]: len(p["classes"]) for p in partitions},
        "classes_in_several_partitions": sorted(c for c, n in counts.items() if n > 1),
        "shared_classes_used_by_no_partition": sorted(shared - listed),
    }
    converted = {"name": name, "language": "java", "granularity": "class", "partitions": partitions}
    return converted, stats


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("input")
    parser.add_argument("output")
    parser.add_argument("--name", default="input-kit")
    args = parser.parse_args()
    with open(args.input) as f:
        decomposition = json.load(f)
    converted, stats = convert(decomposition, args.name)
    with open(args.output, "w") as f:
        json.dump(converted, f, indent=2)
        f.write("\n")
    print(json.dumps(stats, indent=2))


if __name__ == "__main__":
    main()

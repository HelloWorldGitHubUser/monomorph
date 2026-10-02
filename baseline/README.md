# MonoMorph baseline on input-kit

用 MonoMorph 为 input-kit 的 10 个单体应用生成微服务候选仓库，作为单体→微服务转换的 baseline。
只使用 input-kit 提供的输入（单体源码 + decomposition），不使用 MonoMorph 作者发布的任何样例或产物。

## 目录

| 路径 | 内容 |
|---|---|
| `inputs/<app>/monolith/` | input-kit 单体源码，原样复制，不做任何修改（来源见 `inputs/PROVENANCE.md`） |
| `inputs/<app>/decomposition.input-kit.json` | input-kit 原始 decomposition，原样复制 |
| `inputs/<app>/decomposition.monomorph.json` | 转换成 MonoMorph 格式的 decomposition（`scripts/convert_decomposition.py` 生成） |
| `inputs/<app>/Dockerfile` | 只有一行 `FROM`：MonoMorph 编译/纠错容器的基础镜像，JDK 版本与单体自身一致 |
| `apps.json` | 每个应用的源码子目录、包名、Java 版本、预处理步骤 |
| `scripts/prepare_inputs.py` | 把 `inputs/` 复制到 `work/<app>/source` 并做必要的结构调整 |
| `scripts/dry_run.py` | 不调用 LLM，跑静态分析、依赖检测、规划和项目组装，提前发现输入问题 |
| `scripts/run_monomorph.py` | 正式运行 MonoMorph，输出到 `runs/<tag>/<app>/` |
| `scripts/build_sandbox_images.sh` | 仅 Claude Code 云环境需要：构建信任出口代理 CA 的 Maven 镜像 |

`work/` 和 `runs/` 不进 git。

## 输入处理规则

**decomposition 转换。** input-kit 中每个服务的列表是 `[自有类..., "", 该服务用到的共享类...]`，另有顶层 `shared`。
转换时每个服务的 partition = 自有类 + 它用到的共享类，`shared` 不变成 partition。这与 MonoMorph 自带样例
（`examples/spring-petclinic`）的做法一致：共享类列入每个用到它的服务，MonoMorph 把它复制进这些服务并当作本地类；
同一个类列在多个服务里时，第一个列出它的服务是 owner。没有列在任何服务里的类由 MonoMorph 自动复制到所有服务。

**单模块化（只改 `work/` 中的副本）。** MonoMorph 要求 `<source>/pom.xml` 和 `<source>/src/main/java` 存在：
- booking：`buildingblocks` 的源码和依赖并入 `booking-app`，parent 换成 reactor 自己的 parent
  `spring-boot-starter-parent:3.4.1`；`Mediator.java` 中一处 `instanceof ParameterizedType paramType`
  改写成 instanceof + 强制转换（语义不变），因为 MonoMorph 的 Spoon 分析器在这里崩溃。
- petclinic：去掉只有一个模块的聚合 parent，换成它的 parent `spring-boot-starter-parent:2.1.3.RELEASE`。
- passjava：直接用内层的 `passjava-monolith/`。

10 个准备好的单体都已在各自 JDK 镜像中 `mvn compile` 通过。

## 对 MonoMorph 的修改（相对上游 `99ebc29`）

| 文件 | 修改 | 原因 |
|---|---|---|
| `monomorph/llm/custom_chat.py`, `monomorph/llm/factory.py` | 新增 `DeepSeekChat`，模型名 `mm_deepseek/<model>::<effort>` | 接入 DeepSeek；thinking 模式下多轮工具调用必须回传 `reasoning_content`，langchain-openai 0.3.11 不支持；结构化输出改用 function calling（DeepSeek 不支持 `json_schema`）；thinking 模式忽略 temperature |
| `monomorph/llm/factory.py` | fallback 包装的调用超时可由 `MONOMORPH_LLM_INVOKE_TIMEOUT_SECONDS` 配置（默认仍为 60s，运行脚本设为 900s） | thinking=high 的调用常超过 60s，超时会在 fallback 上重跑一遍 |
| `monomorph/planning/inheritance.py`, `monomorph/planning/dependencies.py` | 跳过静态分析中不存在的类 | MonoMorph 的分析器忽略 Java record（booking 45 个、ecommerce 26 个），原代码遇到会 KeyError 崩溃。这些类仍按 decomposition 复制，但 MonoMorph 看不到它们的跨服务使用 |

方法本身的局限（不感知 Spring、gRPC 服务端注册、资源全量复制、不改包名等）一律不修。

## 运行

前置条件：Python 依赖（`uv sync`）、Docker、JDK 17（只用于 MonoMorph 自带的两个分析 jar，JDK 21 下会崩溃）。

在仓库根目录建 `.env`（已在 `.gitignore` 中）：
```
DEEPSEEK_API_KEY=...
JAVA_EXEC_PATH=/path/to/jdk-17/bin/java
CUSTOM_DOCKER_SOCKET=unix:///var/run/docker.sock
```
云环境中这些变量可以直接设成环境变量。

```bash
uv run python baseline/scripts/dry_run.py                      # 不调用 LLM 的检查
uv run python baseline/scripts/run_monomorph.py petclinic      # 先跑一个小应用
uv run python baseline/scripts/run_monomorph.py --tag v1       # 全部 10 个应用
```
在 Claude Code 云环境中先执行 `baseline/scripts/build_sandbox_images.sh`，运行时加 `--image-suffix -sandboxca`。

所有 LLM 角色（代码生成、ID/DTO 决策、解析、编译纠错、fallback）都用 `mm_deepseek/deepseek-v4-pro::high`，
其余参数取 MonoMorph 默认值：Hybrid、restrictive、不含测试、启用编译纠错。每个应用默认 6 小时上限（`--timeout-hours`）。

每个应用的输出在 `runs/<tag>/<app>/`：`output/refactored_code/<app>-<时间>-<id>/<服务>/` 是候选微服务代码，
`monomorph.log` 是完整日志，`run_result.json` 记录返回码和耗时。

## dry run 结果（无 LLM）

| 应用 | 分析到的类 | API 类 | 需 LLM 决策 | 复制到所有服务的类 | 分析器缺失（record） |
|---|---|---|---|---|---|
| booking | 186 | 0 | 0 | 15 | 45 |
| ecommerce | 231 | 22 | 6 | 28 | 26 |
| goodskill | 149 | 8 | 5 | 8 | 0 |
| gulimall | 403 | 36 | 17 | 10 | 0 |
| lakeside | 163 | 16 | 16 | 1 | 0 |
| newbee | 120 | 12 | 6 | 4 | 0 |
| passjava | 81 | 2 | 2 | 7 | 0 |
| petclinic | 37 | 21 | 11 | 6 | 0 |
| youlai | 373 | 12 | 3 | 29 | 0 |
| zlt | 112 | 6 | 4 | 39 | 0 |

booking 检测不到任何跨服务调用：它的服务间交互走 mediator 反射分发和事件，加上 record 不在分析结果里，
MonoMorph 只会按 decomposition 拆分代码并做编译纠错，不会生成 gRPC 通信代码。

# 交接手册：在新会话中继续 MonoMorph baseline

给接手的 Claude 会话（或人）看。先读完本文件，再读 `baseline/README.md`（输入处理规则、对 MonoMorph 的修改、
dry run 结果）。本文件记录目标、用户已做的决定、当前进度、环境搭建步骤和下一步。

## 1. 目标

用户在做一个单体→微服务代码转换 agent，把 MonoMorph（论文 *Beyond Decomposition: A LLM-Powered Automated
Approach to Refactoring Monoliths into Microservices*，QRS 2025）作为 baseline 之一。

**交付物**：MonoMorph 为 input-kit 的 10 个应用各生成一个微服务候选仓库。用户拿这些候选仓库在自己的评测代码上测试。
不复现论文的 RQ，也不需要算论文里的指标。

## 2. 用户已经确定的决定

1. 只用 input-kit（`HelloWorldGitHubUser/input-kit`，commit `b3e80d2`，`agent-input-v8`）提供的输入，
   即使和 MonoMorph 作者的样例应用重合（如 petclinic）也不用作者发布的任何东西。输入已复制进本仓库 `baseline/inputs/`。
2. MonoMorph 只能用单体源码和 decomposition；spec、prepared-component、micro_db、`*-m2m` 不用。
3. 所有调用 LLM 的地方都用 **DeepSeek V4 Pro，thinking=high**：模型名 `mm_deepseek/deepseek-v4-pro::high`，
   refact / parser / decision / correction / fallback 五个角色相同。thinking 模式下 temperature 无效，不发送。
4. 先把 10 个候选生成出来，再说评测。预期很多应用编译或启动不了，这是 baseline 的真实表现，如实交付。
5. 共享类按 MonoMorph 作者样例的做法处理：列入每个用到它的服务（见 README「输入处理规则」）。
6. 编译和纠错用每个应用原本的 Java 版本（8 / 17 / 21）。只有 MonoMorph 自带的两个分析 jar 需要 JDK 17。
7. 只修让工具能跑起来的问题，不修 MonoMorph 方法本身的缺陷（不感知 Spring、gRPC 服务端注册、资源全量复制、
   不改包名、不生成 Dockerfile 和根 pom 等）。每处修改都记在 README 的修改表里。

8. （第二个会话）候选仓库推到本仓库（`HelloWorldGitHubUser/monomorph`）；每个应用跑 1 次；dry run 预测会在规划阶段崩溃的应用
   （petclinic、gulimall、zlt）也照常跑，拿真实结果。规划阶段的崩溃属于方法缺陷，不修

9. （第二个会话）每个应用墙钟上限 3 小时（`--timeout-hours 3`）；按服务数、再按代码行数从小到大运行：
   booking(3) → petclinic(4) → lakeside(4) → zlt(5) → newbee(5) → goodskill(6) → youlai(6) → passjava(7) → gulimall(10) → ecommerce(13)
   候选推到分支 `claude/charming-ritchie-o7xgln` 的 `baseline/candidates/<app>/`

## 3. 当前进度（分支 `claude/charming-ritchie-o7xgln`）

已完成：

- [x] 10 个应用的输入原样复制，git tree id 与 input-kit 一致（`baseline/inputs/PROVENANCE.md`）
- [x] decomposition 转换、每个应用一行的 Dockerfile、`apps.json`
- [x] booking / petclinic 单模块化（`prepare_inputs.py`），10 个单体在各自 JDK 镜像中 `mvn compile` 通过
- [x] DeepSeek 适配（`DeepSeekChat`）及离线单元测试 `tests/test_deepseek_chat.py`（5 个全部通过）
- [x] 修补 MonoMorph 遇到 Java record 时崩溃的问题（分析器漏掉 record）
- [x] `dry_run.py`：10 个应用不调用 LLM 的部分全部跑通
- [x] `run_monomorph.py` 批量运行脚本
- [x] 真实 DeepSeek API 冒烟测试（第二个会话）：普通调用、多轮工具调用回传 `reasoning_content`、历史中空 `reasoning_content` 都被接受；
  thinking 模式拒绝任何强制 `tool_choice`（400），已在 `DeepSeekChat.bind_tools` 降级为 `auto` 并补测试
- [x] 第一次 pilot 发现 `auto` 下模型常把 JSON 写在文本里（13 次 parser 调用中 5 次），导致决策被默认成 ID-Based；
  已在 `DeepSeekChat.with_structured_output` 加文本 JSON 兜底解析并补测试（9 个通过），pilot 已停止并重跑
- [x] dry run 在新环境重跑，10 个应用结果与 README 表一致

**未完成**：

- [x] 运行脚本修正：`--image-suffix=-sandboxca` 必须带等号；分析数据路径不能含应用名（见 README「运行脚本自身的修正」）
- [x] petclinic pilot（`runs/pilot/petclinic/`）：11 个 ID/DTO 决策全部解析成功（修复后），之后在规划阶段 `RecursionError` 崩溃，
  **没有产出候选代码**。用户决定不修（见 README「规划阶段的崩溃」）。旧日志 `monomorph.attempt1.log`（解析问题）、
  `monomorph.attempt2.log`（崩溃）；`monomorph.attempt3-void.log` 是用已撤回的环检测跑的第三次，作废
- [x] `dry_run.py` 新增规划阶段检查：petclinic、gulimall、zlt 必然在规划阶段崩溃，lakeside 必然在之后的代码生成崩溃（客户端服务 `None`），ecommerce 视决策而定
- [ ] 跑出 10 个候选仓库：`run_all_and_push.sh v1 claude/charming-ritchie-o7xgln --timeout-hours 3 --image-suffix=-sandboxca`
  - booking 第一次在 15 秒内因 record 引发的 `KeyError`（`decision/tools.py`）崩溃，已修复；旧结果已撤下，由并行 worker 重跑
  - 06:25 发现上游 `skip_standard_run` 导致所有应用在编译验证写日志时崩溃（`90859bc` 修复，用户确认）；停掉 lakeside/newbee/goodskill，
    booking 在 `runs/dbg` 验证修复有效（flight、passenger 服务编译通过）后，07:1x 改为 **2 个 worker**（编译阶段 4 核 CPU 吃紧，
    3 路只验证过 5 分钟）。被停的应用从各自 `llm_cache.db` 回放已完成的调用；booking 用 `runs/dbg` 的缓存
  - 10:36 容器重启，进程全部终止，已用同样的命令恢复（磁盘上的缓存和日志保留）。恢复后 goodskill、youlai 同时走到导入解析而端口冲突，
    结果作废（已撤下）；run_monomorph.py 改为每应用独立端口后重跑
  - 12:15 容器再次重启（13:09 才发现）。两次重启都发生在我的一个回合结束、且**没有挂着 Monitor** 之后几分钟；挂着 Monitor 时容器一直存活。**原因未证实**，推测是会话空闲时容器被回收。结论：长时间运行期间始终保持一个 Monitor，并且每次回复前先 re-arm
  - 容器重启后第一次启动的应用容易撞上导入解析服务的 15 秒健康检查超时（端口独立也一样），已在 run_monomorph.py 加自动重试
  - 之前：06:18 起曾 3 路并行（用户同意）：瓶颈是 DeepSeek 响应时间，机器空闲（4 核 / 15GB，单个应用约 0.5GB + 一个 Maven 容器）。
    `run_parallel_and_push.sh v1 claude/charming-ritchie-o7xgln --timeout-hours 3 --image-suffix=-sandboxca` 启动 2 个 worker，
    另一个用 `--deliver lakeside` 等串行驱动留下的 lakeside 跑完、交付后再接活。日志 `runs/v1/worker-*.log`。
    中断后：删掉 `runs/v1/locks/` 里没交付的应用的锁，再启动 worker 即可续跑
  在后台运行中（tag `v1`，驱动日志 `runs/v1/driver.log`；用 `setsid nohup` 启动，不受会话后台任务 2 小时限制）。已交付的应用会跳过，中断后重跑同一命令即可续跑
- [ ] 候选仓库交付给用户的方式（见第 6 节）

### v1 结果（每个应用跑完更新）

| 应用 | 返回码 | 耗时 | 候选代码 | 说明 |
|---|---|---|---|---|
| **booking** | **0** | 2759s | **有** | 3 个服务全部编译通过（含测试）：flight 1 轮、passenger 2 轮、booking-service 7 轮纠错（日志每轮都显示 attempt 1/20，即撞步数上限的轮次不计数）。0 个 API 类，没有生成 gRPC 代码，只是拆分 + 修到能编译。第 1、2 次运行作废（record KeyError；gRPC 健康检查偶发超时） |
| petclinic | 1 | 712s | 无 | 11 个决策全部解析成功；规划阶段 `RecursionError`（方法缺陷，按决定不修，与 dry run 预测一致） |
| zlt | 1 | 247s | 无 | 规划阶段 `ValueError: Could not find a matching microservice`（方法缺陷，不修，与 dry run 预测一致） |
| newbee（json_mode 对照，tag `v1-jsonmode`，不替代 v1） | 运行中 | | | 用户要求：只对 newbee 设 `DEEPSEEK_STRUCTURED_OUTPUT_METHOD=json_mode`，复制 v1 的 `llm_cache.db`，代码生成调用回放、只有 parser 换成 JSON 模式。真实 API 上已能解析 v1 失败的那段输出 |
| gulimall | 1 | 946s | 无 | 17 个 ID/DTO 决策全部解析成功；规划阶段 `ValueError: Could not find a matching microservice for class io.gulimall.vo.SocialUser`（`shared` 中的类按规则不属于任何 partition，却成了 API 类；方法缺陷，不修，与 dry run 预测一致） |
| ecommerce | 1 | 298s | 无 | 6 个决策全部解析成功（5 个 DTO-based、1 个 ID-based）；规划阶段 `ValueError: Could not find a matching microservice for class ...ProductDataChangeEvent$Operation`（内部类不在任何 partition 中，partition 只列外部类；dry run 预测「全 DTO-Based 会崩」，LLM 的实际决策触发了它；方法缺陷，不修） |
| **youlai** | **0** | 5428s（含启动失败后的重试） | **有** | 6 个服务全部编译通过（含测试）：mall-oms、mall-pms、mall-sms、mall-ums、youlai-auth、youlai-system，各 1～2 轮纠错，最长一个约 30 分钟。第 1、2 次运行因导入解析服务启动超时作废 |
| lakeside | 1 | 约 36min + 582s | 部分 | 决策和 ID 类、大部分 DTO 类代码生成成功；DTO 客户端生成时 `client_ms=None` 崩溃（规划阶段顺序缺陷，方法缺陷不修，dry run 已能预测）。第一段因修 `skip_standard_run` 被停，第二段从缓存回放后继续 |
| newbee | 1 | 1114s | 部分 | ID 类 `NewBeeMallGoodsMapper` 的服务端代码生成后，parser 解析 3 次失败 → `Server file generation failed`。原因：DeepSeek 把思考内容混进工具参数，JSON 不合法（全部 537 次工具调用中仅此 1 次）；第 3 次重试与第 2 次 prompt 相同，命中缓存。偶发模型错误 + MonoMorph 重试设计，按实记录 |
| booking（v1 第 2 次，作废） | 1 | 28s | 部分 | 本地导入解析服务 15s 健康检查超时（3 路并行刚启动、机器忙时偶发；单独重跑正常），重跑 |

## 4. 新会话的环境搭建（Claude Code 云环境）

云环境容器每次都是新的，上一个会话装的东西不会保留。依次执行：

```bash
cd /home/user/monomorph
git fetch origin claude/charming-ritchie-o7xgln && git checkout claude/charming-ritchie-o7xgln && git pull

# 1. 检查 key（只看是否存在，不要打印内容）
[ -n "$DEEPSEEK_API_KEY" ] && echo "key set" || echo "key MISSING"

# 2. JDK 17：只给 MonoMorph 自带的两个分析 jar 用。JDK 21 下会抛 ClassCastException
apt-get install -y -q openjdk-17-jdk-headless
export JAVA_EXEC_PATH=/usr/lib/jvm/java-17-openjdk-amd64/bin/java

# 3. Python 依赖（项目要求 Python >= 3.12；uv sync 可能改动 uv.lock，不要提交这个改动：git checkout uv.lock）
uv sync && git checkout uv.lock

# 4. Docker 守护进程默认没有运行；会话空闲后可能自己停掉，停了就重新启动
(nohup dockerd > /tmp/dockerd.log 2>&1 &) ; sleep 5 ; docker info > /dev/null && echo "docker ok"
export CUSTOM_DOCKER_SOCKET=unix:///var/run/docker.sock

# 5. 云环境的出口网关会对 TLS 重新签名，容器里的 Maven 会报 PKIX 错误。
#    构建信任网关 CA 的镜像，运行时加 --image-suffix=-sandboxca（必须带等号，否则 argparse 把 -sandboxca 当成选项）
#    Docker Hub 可能对匿名拉取返回 429 Too Many Requests；这时先从 Google 的 Docker Hub 镜像拉基础镜像并打本地 tag：
#    for jdk in 8 17 21; do docker pull mirror.gcr.io/library/maven:3.9-eclipse-temurin-$jdk && \
#      docker tag mirror.gcr.io/library/maven:3.9-eclipse-temurin-$jdk maven:3.9-eclipse-temurin-$jdk; done
baseline/scripts/build_sandbox_images.sh

# 6. 验证 DeepSeek 可达（返回 200 说明 key 有效；401 是 key 问题；403 是网络策略拦截）
curl -sS -o /dev/null -w "%{http_code}\n" https://api.deepseek.com/models -H "Authorization: Bearer $DEEPSEEK_API_KEY"
```

环境变量也可以写进仓库根目录的 `.env`（已在 `.gitignore` 中，**不要提交**）。不要让用户把 key 贴进聊天。

在用户自己的 Mac 上运行时：不需要第 5 步，也不加 `--image-suffix`；用 Docker Desktop；JDK 17 自行安装后设置 `JAVA_EXEC_PATH`。

## 5. 运行步骤

```bash
# 不调用 LLM 的检查（约 1 分钟），确认环境和输入无误
uv run python baseline/scripts/dry_run.py

# 先跑一个小应用，验证 DeepSeek 在真实 API 上能用
uv run python baseline/scripts/run_monomorph.py petclinic --tag pilot --image-suffix=-sandboxca
```

petclinic 跑完后先检查 `runs/pilot/petclinic/monomorph.log`：

- 有没有 HTTP 400：thinking 模式的 `reasoning_content` 回传或参数问题
- 结构化解析是否成功：日志里决策和代码生成的解析结果，而不是解析失败后的默认值。
  决策失败时 MonoMorph 会打出 `Decision workflow returned an invalid decision ... Defaulting to ID-Based`
- 有没有大量 `Model invocation timed out`：说明 `MONOMORPH_LLM_INVOKE_TIMEOUT_SECONDS`（脚本默认 900）还不够

**已在真实 API 上验证（第二个会话）：**

- thinking 模式**不接受**强制 `tool_choice`（`required` 或指定工具名都返回 400），只接受 `auto`/不传。
  `DeepSeekChat.bind_tools` 已把强制选择降级为 `auto`；结构化输出因此依赖模型自己调用 schema 工具，
  模型有时不调用工具而把 JSON 写在文本里，`with_structured_output` 会从文本兜底解析。
  如果日志里仍有 `Parsing failed`，可设 `DEEPSEEK_STRUCTURED_OUTPUT_METHOD=json_mode` 对比
- 每次 parser 调用都会出现 `Cannot save checkpoint - no checkpoint_id generated` 警告：parser 响应不写 checkpoint，
  但仍会写进 `llm_cache.db`，不影响结果。MonoMorph 的解析重试第 2、3 次 prompt 相同，第 3 次会直接命中缓存
- 历史里没有 reasoning 的 assistant 消息补空字符串 `reasoning_content: ""`：API 接受

适配代码在 `monomorph/llm/custom_chat.py` 的 `DeepSeekChat`。

pilot 没问题后跑全部：

```bash
uv run python baseline/scripts/run_monomorph.py --tag v1 --image-suffix=-sandboxca
```

- 应用串行运行，每个默认 6 小时上限（`--timeout-hours`），超时记为失败，**不要中途手工修代码**
- 应该用 `run_in_background` 在后台运行，不要阻塞会话
- 每个应用的结果在 `runs/<tag>/<app>/`：
  - `output/refactored_code/<app>-<时间>-<id>/<服务>/`：候选代码
  - `monomorph.log`：完整日志
  - 静态分析数据在 `runs/<tag>/analysis/<app>/`（不在应用目录下，原因见 README）
  - `run_result.json`：返回码和耗时
- 全部完成后有 `runs/<tag>/summary.json`

## 6. 需要向用户确认的事

1. 每个应用的时间和费用上限（目前默认 6 小时，没有费用上限）。MonoMorph 没有全局的 LLM 调用次数或费用上限：
   决策和代码生成的 LangGraph 图用默认的 25 步上限；编译纠错每个服务最多 20 轮、每轮 100 步，
   但撞到 100 步上限的那一轮不计数（`attempts -= 1`），理论上可以一直循环，只有我们的墙钟超时能截断
2. ~~候选仓库怎么交付~~：已定，推到本仓库（具体分支/目录待确认；gulimall 每个服务约 38MB × 10）
3. ~~每个应用跑几次~~：已定，1 次

## 7. 已知情况（避免重复排查）

- **Spoon 分析器**：
  - 必须用 JDK 17
  - 会忽略 Java record，影响 booking 和 ecommerce，已修补成跳过
  - 在 booking 的 `Mediator.java` 一处 pattern-matching instanceof 上崩溃，已在 `prepare_inputs.py` 里改写
- **booking**：dry run 检测到 0 个跨服务 API 类（交互走 mediator 反射和事件），MonoMorph 只会拆分代码和纠错
- **`uv.lock`**：`uv run` 会改写它，不要提交。用 `uv run --frozen ...` 可避免改写，或事后 `git checkout uv.lock`
- **MonoMorph 本身不做的事**：
  - 不生成 Dockerfile 和 docker-compose（`build_config_files` / `build_docker_files` 是空实现）
  - 编译纠错只跑 `mvn compile` / `test-compile`，不启动应用
  - 纠错 agent 不能修改原有的 Java 文件
- **作者发布的 petclinic 产物**（`HelloWorldGitHubUser/MonoMorph-QRS25-AP`）能编译但起不来：
  - 生成的 `*ServerGRPC` 没注册业务服务
  - 客户端代理不是 Spring Bean
  - 预期我们的候选也有同类问题，这是 baseline 的局限，不要修
- **仓库自带测试**：`tests/test_dependency_detector.py` 和 `tests/test_inheritance_handler.py` 在上游就报错（用了 `Partition`
  而不是 `UpdatedPartition`），与我们的修改无关
- **input-kit 的应用特征**（协议要求、框架、Java 版本）见 `input-kit/INPUT-REVIEW-FINDINGS.md` 和各应用的
  `architecture-spec-en.md`。10 个里只有 booking 的 spec 要求 gRPC，其余要求 OpenFeign/HTTP（goodskill 另有 Dubbo）。
  MonoMorph 一律生成 gRPC

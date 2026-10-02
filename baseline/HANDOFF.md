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

## 3. 当前进度（分支 `claude/charming-ritchie-o7xgln`）

已完成：

- [x] 10 个应用的输入原样复制，git tree id 与 input-kit 一致（`baseline/inputs/PROVENANCE.md`）
- [x] decomposition 转换、每个应用一行的 Dockerfile、`apps.json`
- [x] booking / petclinic 单模块化（`prepare_inputs.py`），10 个单体在各自 JDK 镜像中 `mvn compile` 通过
- [x] DeepSeek 适配（`DeepSeekChat`）及离线单元测试 `tests/test_deepseek_chat.py`（5 个全部通过）
- [x] 修补 MonoMorph 遇到 Java record 时崩溃的问题（分析器漏掉 record）
- [x] `dry_run.py`：10 个应用不调用 LLM 的部分全部跑通
- [x] `run_monomorph.py` 批量运行脚本

**未完成**：

- [ ] 用真实 key 调用 DeepSeek。上一个会话启动时 `DEEPSEEK_API_KEY` 还没加进云环境，所以从没真正调用过 API
- [ ] 跑出 10 个候选仓库
- [ ] 候选仓库交付给用户的方式（见第 6 节）

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
#    构建信任网关 CA 的镜像，运行时加 --image-suffix -sandboxca
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
uv run python baseline/scripts/run_monomorph.py petclinic --tag pilot --image-suffix -sandboxca
```

petclinic 跑完后先检查 `runs/pilot/petclinic/monomorph.log`：

- 有没有 HTTP 400：thinking 模式的 `reasoning_content` 回传或参数问题
- 结构化解析是否成功：日志里决策和代码生成的解析结果，而不是解析失败后的默认值。
  决策失败时 MonoMorph 会打出 `Decision workflow returned an invalid decision ... Defaulting to ID-Based`
- 有没有大量 `Model invocation timed out`：说明 `MONOMORPH_LLM_INVOKE_TIMEOUT_SECONDS`（脚本默认 900）还不够

**尚未在真实 API 上验证的点：**

- DeepSeek thinking 模式是否接受 function calling 强制指定工具（`tool_choice`）。如果解析器报错，
  设 `DEEPSEEK_STRUCTURED_OUTPUT_METHOD=json_mode` 再试
- 历史里没有 reasoning 的 assistant 消息补的是空字符串 `reasoning_content: ""`，需确认 API 接受

适配代码在 `monomorph/llm/custom_chat.py` 的 `DeepSeekChat`。

pilot 没问题后跑全部：

```bash
uv run python baseline/scripts/run_monomorph.py --tag v1 --image-suffix -sandboxca
```

- 应用串行运行，每个默认 6 小时上限（`--timeout-hours`），超时记为失败，**不要中途手工修代码**
- 应该用 `run_in_background` 在后台运行，不要阻塞会话
- 每个应用的结果在 `runs/<tag>/<app>/`：
  - `output/refactored_code/<app>-<时间>-<id>/<服务>/`：候选代码
  - `monomorph.log`：完整日志
  - `run_result.json`：返回码和耗时
- 全部完成后有 `runs/<tag>/summary.json`

## 6. 需要向用户确认的事

1. 每个应用的时间和费用上限（目前默认 6 小时，没有费用上限）
2. 候选仓库怎么交付：`runs/` 不进 git。候选包含整份复制的资源文件，gulimall 每个服务约 38MB × 10。
   可选做法：推到单独的分支 / 单独的仓库 / 打包成压缩文件
3. 每个应用跑几次（目前计划每个 1 次）

## 7. 已知情况（避免重复排查）

- **Spoon 分析器**：
  - 必须用 JDK 17
  - 会忽略 Java record，影响 booking 和 ecommerce，已修补成跳过
  - 在 booking 的 `Mediator.java` 一处 pattern-matching instanceof 上崩溃，已在 `prepare_inputs.py` 里改写
- **booking**：dry run 检测到 0 个跨服务 API 类（交互走 mediator 反射和事件），MonoMorph 只会拆分代码和纠错
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

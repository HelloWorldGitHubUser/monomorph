# Refactoring Report for application "youlai"
 This report contains some of the details of the refactoring process applied to the application. For more details on the refactoring changes, please refer to the reports `<ms_name>/REFACTORING_REPORT.md` for each microservice.
## Project Details
- **Application Name**: youlai
- **Package Name**: com.youlai.mall
- **Decomposition**: input-kit-youlai
- **Programming Language**: java

- **Java Version**: 11
- **Build Tool**: Maven
- **Number of unique classes**: 321
- **Number of microservices**: 6
## Refactoring Details
- **Number of new API classes**: 12
- **Number of DTO-based API classes**: 9
- **Number of ID-based API classes**: 3

---

## Microservices
- **Microservice "mall-oms"**:
  - Code name: `mall_oms`
  - Path: "[mall_oms](mall_oms)"
  - Report: "[mall_oms/REFACTORING_REPORT.md](mall_oms/REFACTORING_REPORT.md)"
- **Microservice "mall-pms"**:
  - Code name: `mall_pms`
  - Path: "[mall_pms](mall_pms)"
  - Report: "[mall_pms/REFACTORING_REPORT.md](mall_pms/REFACTORING_REPORT.md)"
  - Listen Port: 50052
- **Microservice "mall-sms"**:
  - Code name: `mall_sms`
  - Path: "[mall_sms](mall_sms)"
  - Report: "[mall_sms/REFACTORING_REPORT.md](mall_sms/REFACTORING_REPORT.md)"
- **Microservice "mall-ums"**:
  - Code name: `mall_ums`
  - Path: "[mall_ums](mall_ums)"
  - Report: "[mall_ums/REFACTORING_REPORT.md](mall_ums/REFACTORING_REPORT.md)"
  - Listen Port: 50054
- **Microservice "youlai-auth"**:
  - Code name: `youlai_auth`
  - Path: "[youlai_auth](youlai_auth)"
  - Report: "[youlai_auth/REFACTORING_REPORT.md](youlai_auth/REFACTORING_REPORT.md)"
- **Microservice "youlai-system"**:
  - Code name: `youlai_system`
  - Path: "[youlai_system](youlai_system)"
  - Report: "[youlai_system/REFACTORING_REPORT.md](youlai_system/REFACTORING_REPORT.md)"
  - Listen Port: 50056

---

## Approach Description
The "MonoMorph" refactoring process generates a ID and DTO based microservices architecture using an agentic approach combining LLMs and Modeling to transform a monolithic application into a microservices architecture. In the ID based design, each microservice is responsible for the lifecycle of the class it owns. The rest consume it through its unique ID and calls to the corresponding API. In this implementation, the interaction between microservices is done through gRPC. The lifecycle of the classes is managed by a leasing/TTL (time-to-live) system. The DTO based design is a more traditional approach where each microservice exposes its own API and  the classes are transferred through the network in each interaction. To combine the simplicity of the DTO approach when possible and the ID based approach when needed, the MonoMorph process generates a hybrid architecture where the selection of the refactoring approach for each candidate API class is done by the LLM.

The process is divided into the following steps:
1. **Dependency Analysis**: The process starts by analyzing the dependencies between the classes in the monolithic application.
2. **Detecting new APIs**: The process detects the new APIs that need to be created for each microservice.
3. **Approach Selection**: The process selects the approach for each API class using the agentic LLM.
4. **Post-decision**: The process analyzes the inter-service interactions, taking into account the selected approach to find any potential new API classes (which will be asigned to the DTO method).
5. **Refactoring**: For each new API class, the process uses a LLM to generate a protocol buffer definition and its corresponding gRPC server and client implementations. The implementation and design differ based on the chose approach.
6. **Configuration Generation**: The process generates and adds the helper classes to the microservices. It updates as well the dependencies of the build tool.
7. **Entrypoint Generation**: The process generates the entry point for each microservice ensuring that each gRPC server is configured and integrates the process into the original main if needed.
8. **Report Generation**: The current report is generated for the project and each microservice ensuring tracing of the transformations and their explanations.

---


---

## Decisions and Explanations
 The following section provides the reasoning behind the decisions made for the refactoring approach:
 - Class `com.youlai.mall.service.pms.SkuService`:
   - Decision: `ID-Based`
   - Reasoning: 1. `find_class_usages(SkuService)` identified `mall-oms` as the sole cross-microservice consumer, using both reads (`getSkuInfo`, `getSkuInfoList`) and state-mutating commands (`lockStock`, `unlockStock`, `deductStock`). 2. `get_source_code(SkuServiceImpl)` showed these write methods rely on atomic SQL conditional updates (`stock - locked_stock >= quantity`) and Redis state, so they must execute inside the inventory owner against the latest state. 3. `get_source_code(PmsSku)` showed moderate data complexity (9 fields), but the service encapsulates lifecycle behavior, not just data transfer. 4. Mutability is extremely high; `stock` and `lockedStock` change on every order submit/pay/cancel, making stale snapshots unacceptable. 5. The already-existing `SkuInfoDTO` can remain as the response payload for ID-based read endpoints, but it cannot satisfy the command behavior. Therefore, `SkuService` functionality should remain owned by `mall-pms` and be exposed to `mall-oms` via ID-Based REST APIs rather than shared DTO snapshots.

---

 - Class `com.youlai.mall.model.pms.dto.SkuInfoDTO`:
   - Decision: `DTO-Only`
   - Reasoning: Class com.youlai.mall.model.pms.dto.SkuInfoDTO was used within the fields/inputs/outputs of class com.youlai.mall.service.oms.app.impl.CartServiceImpl from microservice mall-oms.
Class com.youlai.mall.model.pms.dto.SkuInfoDTO was used within the fields/inputs/outputs of class com.youlai.mall.converter.CartConverter from microservice mall-oms.
Class com.youlai.mall.model.pms.dto.SkuInfoDTO was used within the fields/inputs/outputs of class com.youlai.mall.service.oms.app.impl.OrderServiceImpl from microservice mall-oms.

---

 - Class `com.youlai.mall.model.pms.dto.LockSkuDTO`:
   - Decision: `DTO-Only`
   - Reasoning: Class com.youlai.mall.model.pms.dto.LockSkuDTO was used within the fields/inputs/outputs of class com.youlai.mall.service.oms.app.impl.OrderServiceImpl from microservice mall-oms.

---

 - Class `com.youlai.mall.model.pms.vo.ProductHistoryVO`:
   - Decision: `DTO-Only`
   - Reasoning: Class com.youlai.mall.model.pms.vo.ProductHistoryVO was used within the fields/inputs/outputs of class com.youlai.mall.service.ums.UmsMemberService from microservice mall-ums.
Class com.youlai.mall.model.pms.vo.ProductHistoryVO was used within the fields/inputs/outputs of class com.youlai.mall.service.ums.impl.UmsMemberServiceImpl from microservice mall-ums.
Class com.youlai.mall.model.pms.vo.ProductHistoryVO was used within the fields/inputs/outputs of class com.youlai.mall.controller.MemberController from microservice mall-ums.

---

 - Class `com.youlai.mall.service.ums.UmsMemberService`:
   - Decision: `ID-Based`
   - Reasoning: Step-by-step: (1) Entity complexity: UmsMember has ~16 fields plus BaseEntity and is highly mutable (balance, point, status, openid, sessionKey, addressList), but the service already projects small purpose-specific DTOs (MemberAuthDTO, MemberVO, MemberAddressDTO), so size alone is not decisive. (2) Consumer behavior: find_class_usages shows 4 microservices; mall-oms performs a financial write (deductBalance), needs current openid for WeChat Pay (getMemberOpenId), and reads addresses for order confirmation; mall-pms writes product view history; youlai-auth must load current status for authentication and register members. (3) These are command/decision operations requiring authoritative current state, not read-only reporting. (4) Mutability makes stale DTO snapshots unsafe for security (disabled member) and finance (balance deduction). (5) Therefore the correct tradeoff is runtime coupling through synchronous ID-based API calls to mall-ums; existing DTOs remain on-demand API response/request contracts, not event-shared snapshots.

---

 - Class `com.youlai.mall.model.ums.dto.MemberAddressDTO`:
   - Decision: `DTO-Only`
   - Reasoning: Class com.youlai.mall.model.ums.dto.MemberAddressDTO was used within the fields/inputs/outputs of class com.youlai.mall.service.oms.app.impl.OrderServiceImpl from microservice mall-oms.
Class com.youlai.mall.model.ums.dto.MemberAddressDTO was used within the fields/inputs/outputs of class com.youlai.mall.model.oms.vo.OrderConfirmVO from microservice mall-oms.

---

 - Class `com.youlai.mall.model.ums.dto.MemberAuthDTO`:
   - Decision: `DTO-Only`
   - Reasoning: Class com.youlai.mall.model.ums.dto.MemberAuthDTO was used within the fields/inputs/outputs of class com.youlai.mall.model.auth.MemberDetails from microservice youlai-auth.
Class com.youlai.mall.model.ums.dto.MemberAuthDTO was used within the fields/inputs/outputs of class com.youlai.mall.service.auth.MemberDetailsService from microservice youlai-auth.

---

 - Class `com.youlai.mall.model.ums.dto.MemberRegisterDto`:
   - Decision: `DTO-Only`
   - Reasoning: Class com.youlai.mall.model.ums.dto.MemberRegisterDto was used within the fields/inputs/outputs of class com.youlai.mall.service.auth.MemberDetailsService from microservice youlai-auth.

---

 - Class `com.youlai.mall.service.system.SysUserService`:
   - Decision: `ID-Based`
   - Reasoning: Analysis of usages showed SysUserService is primarily used inside youlai-system, while the only cross-service consumers (SysUserDetailsService and CustomOidcUserInfoService in youlai-auth) need just getUserAuthInfo(username). DTO-Based sharing was rejected because the returned UserAuthInfo is security-critical and highly mutable: it feeds Spring Security authentication (password, status, roles, perms), so an eventually consistent snapshot could allow disabled accounts or stale passwords to authenticate. The owning service already exposes GET /api/v1/users/{username}/authInfo, making a synchronous ID/username-based API call the natural fit. Therefore, SysUserService should remain internal to youlai-system, and youlai-auth should call a narrow read-only authentication endpoint instead of sharing DTOs or the service directly.

---

 - Class `com.youlai.mall.model.system.dto.UserAuthInfo`:
   - Decision: `DTO-Only`
   - Reasoning: Class com.youlai.mall.model.system.dto.UserAuthInfo was used within the fields/inputs/outputs of class com.youlai.mall.service.auth.SysUserDetailsService from microservice youlai-auth.
Class com.youlai.mall.model.system.dto.UserAuthInfo was used within the fields/inputs/outputs of class com.youlai.mall.config.auth.oauth2.oidc.CustomOidcUserInfoService from microservice youlai-auth.
Class com.youlai.mall.model.system.dto.UserAuthInfo was used within the fields/inputs/outputs of class com.youlai.mall.model.auth.SysUserDetails from microservice youlai-auth.

---

 - Class `com.youlai.mall.constant.RedisConstants`:
   - Decision: `DTO-Only`
   - Reasoning: Class com.youlai.mall.constant.RedisConstants was used within the fields/inputs/outputs of class com.youlai.mall.security.service.PermissionService from microservice mall-ums.
Class com.youlai.mall.constant.RedisConstants was used within the fields/inputs/outputs of class com.youlai.mall.security.service.PermissionService from microservice mall-sms.
Class com.youlai.mall.constant.RedisConstants was used within the fields/inputs/outputs of class com.youlai.mall.security.service.PermissionService from microservice mall-oms.
Class com.youlai.mall.constant.RedisConstants was used within the fields/inputs/outputs of class com.youlai.mall.security.service.PermissionService from microservice mall-pms.

---

 - Class `com.youlai.mall.constant.SystemConstants`:
   - Decision: `DTO-Only`
   - Reasoning: Class com.youlai.mall.constant.SystemConstants was used within the fields/inputs/outputs of class com.youlai.mall.security.util.SecurityUtils from microservice mall-sms.
Class com.youlai.mall.constant.SystemConstants was used within the fields/inputs/outputs of class com.youlai.mall.security.util.SecurityUtils from microservice youlai-auth.

---


---

## Refactoring Metadata
- **run_id**: youlai-v1
- **include_tests**: All test classes that were not already in the decomposition were excluded from the refactoring process
- **restrictive_mode**: Restrictive mode was enabled: All Java classes that were not already in the decomposition, were detected by the analysis tool and were not in the source package src/main/java were excluded from the execution
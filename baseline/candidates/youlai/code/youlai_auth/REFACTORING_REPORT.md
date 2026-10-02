# **Microservice "youlai-auth" ("youlai_auth") Report**
## Microservice Summary
 The microservice "youlai-auth" (renamed "youlai_auth" in pathing and identification) contains a total of **111** classes and files:
  - **55** classes were selected from the decomposition file
  - **29** classes were added as duplicate
  - **16** new classes were added or generated
  - **11** new proto files were added or generated

 The microservice has inherited the old main class "[MonolithApplication](src/main/java/com/youlai/mall/MonolithApplication.java)" of the monolith.

---

---

## Changes
 The following changes were made in order to create the microservice:
### Dependencies
 The dependencies of the microservice have been updated. The following packages were added to the file [pom.xml](pom.xml):
 - `com.github.ben-manes.caffeine:caffeine:2.8.0`
 - `com.google.protobuf:protobuf-java:3.25.5`
 - `io.grpc:grpc-netty-shaded:1.71.0`
 - `io.grpc:grpc-protobuf:1.71.0`
 - `io.grpc:grpc-stub:1.71.0`
 - `javax.annotation:javax.annotation-api:1.3.2`
 - `org.mapstruct:mapstruct:1.6.3`

### Copied Classes
 The following classes were copied from the original microservice based on the decomposition:
 - `com.youlai.mall.config.auth.AuthorizationServerConfig` was copied to [src/main/java/com/youlai/mall/config/auth/AuthorizationServerConfig.java](src/main/java/com/youlai/mall/config/auth/AuthorizationServerConfig.java)
 - `com.youlai.mall.config.auth.CaptchaConfig` was copied to [src/main/java/com/youlai/mall/config/auth/CaptchaConfig.java](src/main/java/com/youlai/mall/config/auth/CaptchaConfig.java)
 - `com.youlai.mall.config.auth.CaptchaProperties` was copied to [src/main/java/com/youlai/mall/config/auth/CaptchaProperties.java](src/main/java/com/youlai/mall/config/auth/CaptchaProperties.java)
 - `com.youlai.mall.config.auth.JwtTokenCustomizerConfig` was copied to [src/main/java/com/youlai/mall/config/auth/JwtTokenCustomizerConfig.java](src/main/java/com/youlai/mall/config/auth/JwtTokenCustomizerConfig.java)
 - `com.youlai.mall.config.auth.SecurityConfig` was copied to [src/main/java/com/youlai/mall/config/auth/SecurityConfig.java](src/main/java/com/youlai/mall/config/auth/SecurityConfig.java)
 - `com.youlai.mall.config.auth.WxMiniAppConfig` was copied to [src/main/java/com/youlai/mall/config/auth/WxMiniAppConfig.java](src/main/java/com/youlai/mall/config/auth/WxMiniAppConfig.java)
 - `com.youlai.mall.config.auth.oauth2.extension.captcha.CaptchaAuthenticationConverter` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/captcha/CaptchaAuthenticationConverter.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/captcha/CaptchaAuthenticationConverter.java)
 - `com.youlai.mall.config.auth.oauth2.extension.captcha.CaptchaAuthenticationProvider` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/captcha/CaptchaAuthenticationProvider.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/captcha/CaptchaAuthenticationProvider.java)
 - `com.youlai.mall.config.auth.oauth2.extension.captcha.CaptchaAuthenticationToken` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/captcha/CaptchaAuthenticationToken.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/captcha/CaptchaAuthenticationToken.java)
 - `com.youlai.mall.config.auth.oauth2.extension.captcha.CaptchaParameterNames` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/captcha/CaptchaParameterNames.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/captcha/CaptchaParameterNames.java)
 - `com.youlai.mall.config.auth.oauth2.extension.password.PasswordAuthenticationConverter` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/password/PasswordAuthenticationConverter.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/password/PasswordAuthenticationConverter.java)
 - `com.youlai.mall.config.auth.oauth2.extension.password.PasswordAuthenticationProvider` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/password/PasswordAuthenticationProvider.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/password/PasswordAuthenticationProvider.java)
 - `com.youlai.mall.config.auth.oauth2.extension.password.PasswordAuthenticationToken` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/password/PasswordAuthenticationToken.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/password/PasswordAuthenticationToken.java)
 - `com.youlai.mall.config.auth.oauth2.extension.smscode.SmsCodeAuthenticationConverter` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/smscode/SmsCodeAuthenticationConverter.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/smscode/SmsCodeAuthenticationConverter.java)
 - `com.youlai.mall.config.auth.oauth2.extension.smscode.SmsCodeAuthenticationProvider` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/smscode/SmsCodeAuthenticationProvider.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/smscode/SmsCodeAuthenticationProvider.java)
 - `com.youlai.mall.config.auth.oauth2.extension.smscode.SmsCodeAuthenticationToken` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/smscode/SmsCodeAuthenticationToken.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/smscode/SmsCodeAuthenticationToken.java)
 - `com.youlai.mall.config.auth.oauth2.extension.smscode.SmsCodeParameterNames` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/smscode/SmsCodeParameterNames.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/smscode/SmsCodeParameterNames.java)
 - `com.youlai.mall.config.auth.oauth2.extension.wechat.WechatAuthenticationConverter` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/wechat/WechatAuthenticationConverter.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/wechat/WechatAuthenticationConverter.java)
 - `com.youlai.mall.config.auth.oauth2.extension.wechat.WechatAuthenticationProvider` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/wechat/WechatAuthenticationProvider.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/wechat/WechatAuthenticationProvider.java)
 - `com.youlai.mall.config.auth.oauth2.extension.wechat.WechatAuthenticationToken` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/extension/wechat/WechatAuthenticationToken.java](src/main/java/com/youlai/mall/config/auth/oauth2/extension/wechat/WechatAuthenticationToken.java)
 - `com.youlai.mall.config.auth.oauth2.handler.MyAuthenticationFailureHandler` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/handler/MyAuthenticationFailureHandler.java](src/main/java/com/youlai/mall/config/auth/oauth2/handler/MyAuthenticationFailureHandler.java)
 - `com.youlai.mall.config.auth.oauth2.handler.MyAuthenticationSuccessHandler` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/handler/MyAuthenticationSuccessHandler.java](src/main/java/com/youlai/mall/config/auth/oauth2/handler/MyAuthenticationSuccessHandler.java)
 - `com.youlai.mall.config.auth.oauth2.jackson.MemberMixin` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/jackson/MemberMixin.java](src/main/java/com/youlai/mall/config/auth/oauth2/jackson/MemberMixin.java)
 - `com.youlai.mall.config.auth.oauth2.jackson.SysUserDeserializer` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/jackson/SysUserDeserializer.java](src/main/java/com/youlai/mall/config/auth/oauth2/jackson/SysUserDeserializer.java)
 - `com.youlai.mall.config.auth.oauth2.jackson.SysUserMixin` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/jackson/SysUserMixin.java](src/main/java/com/youlai/mall/config/auth/oauth2/jackson/SysUserMixin.java)
 - `com.youlai.mall.config.auth.oauth2.oidc.CustomOidcAuthenticationConverter` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/oidc/CustomOidcAuthenticationConverter.java](src/main/java/com/youlai/mall/config/auth/oauth2/oidc/CustomOidcAuthenticationConverter.java)
 - `com.youlai.mall.config.auth.oauth2.oidc.CustomOidcAuthenticationProvider` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/oidc/CustomOidcAuthenticationProvider.java](src/main/java/com/youlai/mall/config/auth/oauth2/oidc/CustomOidcAuthenticationProvider.java)
 - `com.youlai.mall.config.auth.oauth2.oidc.CustomOidcToken` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/oidc/CustomOidcToken.java](src/main/java/com/youlai/mall/config/auth/oauth2/oidc/CustomOidcToken.java)
 - `com.youlai.mall.config.auth.oauth2.oidc.CustomOidcUserInfo` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/oidc/CustomOidcUserInfo.java](src/main/java/com/youlai/mall/config/auth/oauth2/oidc/CustomOidcUserInfo.java)
 - `com.youlai.mall.config.auth.oauth2.oidc.CustomOidcUserInfoService` was copied to [src/main/java/com/youlai/mall/config/auth/oauth2/oidc/CustomOidcUserInfoService.java](src/main/java/com/youlai/mall/config/auth/oauth2/oidc/CustomOidcUserInfoService.java)
 - `com.youlai.mall.controller.AuthController` was copied to [src/main/java/com/youlai/mall/controller/AuthController.java](src/main/java/com/youlai/mall/controller/AuthController.java)
 - `com.youlai.mall.enums.CaptchaCodeTypeEnum` was copied to [src/main/java/com/youlai/mall/enums/CaptchaCodeTypeEnum.java](src/main/java/com/youlai/mall/enums/CaptchaCodeTypeEnum.java)
 - `com.youlai.mall.enums.CaptchaTypeEnum` was copied to [src/main/java/com/youlai/mall/enums/CaptchaTypeEnum.java](src/main/java/com/youlai/mall/enums/CaptchaTypeEnum.java)
 - `com.youlai.mall.filter.TokenBlacklistFilter` was copied to [src/main/java/com/youlai/mall/filter/TokenBlacklistFilter.java](src/main/java/com/youlai/mall/filter/TokenBlacklistFilter.java)
 - `com.youlai.mall.model.auth.CaptchaResult` was copied to [src/main/java/com/youlai/mall/model/auth/CaptchaResult.java](src/main/java/com/youlai/mall/model/auth/CaptchaResult.java)
 - `com.youlai.mall.model.auth.LoginUserInfo` was copied to [src/main/java/com/youlai/mall/model/auth/LoginUserInfo.java](src/main/java/com/youlai/mall/model/auth/LoginUserInfo.java)
 - `com.youlai.mall.model.auth.MemberDetails` was copied to [src/main/java/com/youlai/mall/model/auth/MemberDetails.java](src/main/java/com/youlai/mall/model/auth/MemberDetails.java)
 - `com.youlai.mall.model.auth.SysUserDetails` was copied to [src/main/java/com/youlai/mall/model/auth/SysUserDetails.java](src/main/java/com/youlai/mall/model/auth/SysUserDetails.java)
 - `com.youlai.mall.service.auth.AuthService` was copied to [src/main/java/com/youlai/mall/service/auth/AuthService.java](src/main/java/com/youlai/mall/service/auth/AuthService.java)
 - `com.youlai.mall.service.auth.CaptchaService` was copied to [src/main/java/com/youlai/mall/service/auth/CaptchaService.java](src/main/java/com/youlai/mall/service/auth/CaptchaService.java)
 - `com.youlai.mall.service.auth.MemberDetailsService` was copied to [src/main/java/com/youlai/mall/service/auth/MemberDetailsService.java](src/main/java/com/youlai/mall/service/auth/MemberDetailsService.java)
 - `com.youlai.mall.service.auth.SysUserDetailsService` was copied to [src/main/java/com/youlai/mall/service/auth/SysUserDetailsService.java](src/main/java/com/youlai/mall/service/auth/SysUserDetailsService.java)
 - `com.youlai.mall.util.OAuth2AuthenticationProviderUtils` was copied to [src/main/java/com/youlai/mall/util/OAuth2AuthenticationProviderUtils.java](src/main/java/com/youlai/mall/util/OAuth2AuthenticationProviderUtils.java)
 - `com.youlai.mall.util.OAuth2EndpointUtils` was copied to [src/main/java/com/youlai/mall/util/OAuth2EndpointUtils.java](src/main/java/com/youlai/mall/util/OAuth2EndpointUtils.java)
 - `com.youlai.mall.base.IBaseEnum` was copied to [src/main/java/com/youlai/mall/base/IBaseEnum.java](src/main/java/com/youlai/mall/base/IBaseEnum.java)
 - `com.youlai.mall.constant.GlobalConstants` was copied to [src/main/java/com/youlai/mall/constant/GlobalConstants.java](src/main/java/com/youlai/mall/constant/GlobalConstants.java)
 - `com.youlai.mall.constant.JwtClaimConstants` was copied to [src/main/java/com/youlai/mall/constant/JwtClaimConstants.java](src/main/java/com/youlai/mall/constant/JwtClaimConstants.java)
 - `com.youlai.mall.constant.RedisConstants` was copied to [src/main/java/com/youlai/mall/constant/RedisConstants.java](src/main/java/com/youlai/mall/constant/RedisConstants.java)
 - `com.youlai.mall.enums.StatusEnum` was copied to [src/main/java/com/youlai/mall/enums/StatusEnum.java](src/main/java/com/youlai/mall/enums/StatusEnum.java)
 - `com.youlai.mall.messaging.property.AliyunSmsProperties` was copied to [src/main/java/com/youlai/mall/messaging/property/AliyunSmsProperties.java](src/main/java/com/youlai/mall/messaging/property/AliyunSmsProperties.java)
 - `com.youlai.mall.messaging.service.SmsService` was copied to [src/main/java/com/youlai/mall/messaging/service/SmsService.java](src/main/java/com/youlai/mall/messaging/service/SmsService.java)
 - `com.youlai.mall.messaging.service.impl.AliyunSmsService` was copied to [src/main/java/com/youlai/mall/messaging/service/impl/AliyunSmsService.java](src/main/java/com/youlai/mall/messaging/service/impl/AliyunSmsService.java)
 - `com.youlai.mall.result.IResultCode` was copied to [src/main/java/com/youlai/mall/result/IResultCode.java](src/main/java/com/youlai/mall/result/IResultCode.java)
 - `com.youlai.mall.result.Result` was copied to [src/main/java/com/youlai/mall/result/Result.java](src/main/java/com/youlai/mall/result/Result.java)
 - `com.youlai.mall.result.ResultCode` was copied to [src/main/java/com/youlai/mall/result/ResultCode.java](src/main/java/com/youlai/mall/result/ResultCode.java)

### Duplicated Classes
 The following classes were added as duplicates during the preprocessing step of the approach due to reasons such as (parent classes, missing from decomposition):
 - `com.youlai.mall.apidoc.ApiDocInfoProperties` was duplicated  to [src/main/java/com/youlai/mall/apidoc/ApiDocInfoProperties.java](src/main/java/com/youlai/mall/apidoc/ApiDocInfoProperties.java)
 - `com.youlai.mall.apidoc.OpenApiConfig` was duplicated  to [src/main/java/com/youlai/mall/apidoc/OpenApiConfig.java](src/main/java/com/youlai/mall/apidoc/OpenApiConfig.java)
 - `com.youlai.mall.config.CorsConfig` was duplicated  to [src/main/java/com/youlai/mall/config/CorsConfig.java](src/main/java/com/youlai/mall/config/CorsConfig.java)
 - `com.youlai.mall.mybatis.annotation.DataPermission` was duplicated  to [src/main/java/com/youlai/mall/mybatis/annotation/DataPermission.java](src/main/java/com/youlai/mall/mybatis/annotation/DataPermission.java)
 - `com.youlai.mall.mybatis.config.MybatisPlusConfig` was duplicated  to [src/main/java/com/youlai/mall/mybatis/config/MybatisPlusConfig.java](src/main/java/com/youlai/mall/mybatis/config/MybatisPlusConfig.java)
 - `com.youlai.mall.mybatis.enums.DataScopeEnum` was duplicated  to [src/main/java/com/youlai/mall/mybatis/enums/DataScopeEnum.java](src/main/java/com/youlai/mall/mybatis/enums/DataScopeEnum.java)
 - `com.youlai.mall.mybatis.handler.ArrayObjectJsonTypeHandler` was duplicated  to [src/main/java/com/youlai/mall/mybatis/handler/ArrayObjectJsonTypeHandler.java](src/main/java/com/youlai/mall/mybatis/handler/ArrayObjectJsonTypeHandler.java)
 - `com.youlai.mall.mybatis.handler.IntegerArrayJsonTypeHandler` was duplicated  to [src/main/java/com/youlai/mall/mybatis/handler/IntegerArrayJsonTypeHandler.java](src/main/java/com/youlai/mall/mybatis/handler/IntegerArrayJsonTypeHandler.java)
 - `com.youlai.mall.mybatis.handler.LongArrayJsonTypeHandler` was duplicated  to [src/main/java/com/youlai/mall/mybatis/handler/LongArrayJsonTypeHandler.java](src/main/java/com/youlai/mall/mybatis/handler/LongArrayJsonTypeHandler.java)
 - `com.youlai.mall.mybatis.handler.MyDataPermissionHandler` was duplicated  to [src/main/java/com/youlai/mall/mybatis/handler/MyDataPermissionHandler.java](src/main/java/com/youlai/mall/mybatis/handler/MyDataPermissionHandler.java)
 - `com.youlai.mall.mybatis.handler.MyMetaObjectHandler` was duplicated  to [src/main/java/com/youlai/mall/mybatis/handler/MyMetaObjectHandler.java](src/main/java/com/youlai/mall/mybatis/handler/MyMetaObjectHandler.java)
 - `com.youlai.mall.mybatis.handler.StringArrayJsonTypeHandler` was duplicated  to [src/main/java/com/youlai/mall/mybatis/handler/StringArrayJsonTypeHandler.java](src/main/java/com/youlai/mall/mybatis/handler/StringArrayJsonTypeHandler.java)
 - `com.youlai.mall.rabbitmq.config.RabbitConfig` was duplicated  to [src/main/java/com/youlai/mall/rabbitmq/config/RabbitConfig.java](src/main/java/com/youlai/mall/rabbitmq/config/RabbitConfig.java)
 - `com.youlai.mall.redis.BusinessSnGenerator` was duplicated  to [src/main/java/com/youlai/mall/redis/BusinessSnGenerator.java](src/main/java/com/youlai/mall/redis/BusinessSnGenerator.java)
 - `com.youlai.mall.redis.RedisCacheConfig` was duplicated  to [src/main/java/com/youlai/mall/redis/RedisCacheConfig.java](src/main/java/com/youlai/mall/redis/RedisCacheConfig.java)
 - `com.youlai.mall.redis.RedisConfig` was duplicated  to [src/main/java/com/youlai/mall/redis/RedisConfig.java](src/main/java/com/youlai/mall/redis/RedisConfig.java)
 - `com.youlai.mall.security.exception.MyAccessDeniedHandler` was duplicated  to [src/main/java/com/youlai/mall/security/exception/MyAccessDeniedHandler.java](src/main/java/com/youlai/mall/security/exception/MyAccessDeniedHandler.java)
 - `com.youlai.mall.security.exception.MyAuthenticationEntryPoint` was duplicated  to [src/main/java/com/youlai/mall/security/exception/MyAuthenticationEntryPoint.java](src/main/java/com/youlai/mall/security/exception/MyAuthenticationEntryPoint.java)
 - `com.youlai.mall.security.service.PermissionService` was duplicated  to [src/main/java/com/youlai/mall/security/service/PermissionService.java](src/main/java/com/youlai/mall/security/service/PermissionService.java)
 - `com.youlai.mall.security.util.SecurityUtils` was duplicated  to [src/main/java/com/youlai/mall/security/util/SecurityUtils.java](src/main/java/com/youlai/mall/security/util/SecurityUtils.java)
 - `com.youlai.mall.util.BloomFilterUtils` was duplicated  to [src/main/java/com/youlai/mall/util/BloomFilterUtils.java](src/main/java/com/youlai/mall/util/BloomFilterUtils.java)
 - `com.youlai.mall.web.annotation.PreventDuplicateResubmit` was duplicated  to [src/main/java/com/youlai/mall/web/annotation/PreventDuplicateResubmit.java](src/main/java/com/youlai/mall/web/annotation/PreventDuplicateResubmit.java)
 - `com.youlai.mall.web.aspect.DuplicateSubmitAspect` was duplicated  to [src/main/java/com/youlai/mall/web/aspect/DuplicateSubmitAspect.java](src/main/java/com/youlai/mall/web/aspect/DuplicateSubmitAspect.java)
 - `com.youlai.mall.web.config.RestTemplateConfig` was duplicated  to [src/main/java/com/youlai/mall/web/config/RestTemplateConfig.java](src/main/java/com/youlai/mall/web/config/RestTemplateConfig.java)
 - `com.youlai.mall.web.config.ValidationConfig` was duplicated  to [src/main/java/com/youlai/mall/web/config/ValidationConfig.java](src/main/java/com/youlai/mall/web/config/ValidationConfig.java)
 - `com.youlai.mall.web.config.WebMvcConfig` was duplicated  to [src/main/java/com/youlai/mall/web/config/WebMvcConfig.java](src/main/java/com/youlai/mall/web/config/WebMvcConfig.java)
 - `com.youlai.mall.web.exception.BizException` was duplicated  to [src/main/java/com/youlai/mall/web/exception/BizException.java](src/main/java/com/youlai/mall/web/exception/BizException.java)
 - `com.youlai.mall.web.exception.GlobalExceptionHandler` was duplicated  to [src/main/java/com/youlai/mall/web/exception/GlobalExceptionHandler.java](src/main/java/com/youlai/mall/web/exception/GlobalExceptionHandler.java)
 - `com.youlai.mall.MonolithApplication` was duplicated  to [src/main/java/com/youlai/mall/MonolithApplication.java](src/main/java/com/youlai/mall/MonolithApplication.java)

### New Clients
 The following gRPC clients were generated and added to invoke their corresponding servers through RPCs. They serve as proxies to their corresponding original classes:
 - Class `com.youlai.mall.monomorph.id.generated.client.UmsMemberService`:
   - A proxy for `com.youlai.mall.service.ums.UmsMemberService`
   - Location: [src/main/java/com/youlai/mall/monomorph/id/generated/client/UmsMemberService.java](src/main/java/com/youlai/mall/monomorph/id/generated/client/UmsMemberService.java)
   - Corresponding Proto service `UmsMemberServiceService` in file [ums_member_service.proto](src/main/proto/ums_member_service.proto)
 - Class `com.youlai.mall.monomorph.id.generated.client.SysUserService`:
   - A proxy for `com.youlai.mall.service.system.SysUserService`
   - Location: [src/main/java/com/youlai/mall/monomorph/id/generated/client/SysUserService.java](src/main/java/com/youlai/mall/monomorph/id/generated/client/SysUserService.java)
   - Corresponding Proto service `SysUserServiceService` in file [sys_user_service.proto](src/main/proto/sys_user_service.proto)
 - Class `com.youlai.mall.monomorph.dto.generated.client.ProductHistoryVO`:
   - A proxy for `com.youlai.mall.model.pms.vo.ProductHistoryVO`
   - Location: [src/main/java/com/youlai/mall/monomorph/dto/generated/client/ProductHistoryVO.java](src/main/java/com/youlai/mall/monomorph/dto/generated/client/ProductHistoryVO.java)
   - Corresponding Proto service `ProductHistoryService` in file [product_history_vo.proto](src/main/proto/product_history_vo.proto)
   - DTO Message: ProductHistoryVODTO in [product_history_vo.proto](src/main/proto/product_history_vo.proto)
 - Class `com.youlai.mall.monomorph.dto.generated.client.MemberAddressDTO`:
   - A proxy for `com.youlai.mall.model.ums.dto.MemberAddressDTO`
   - Location: [src/main/java/com/youlai/mall/monomorph/dto/generated/client/MemberAddressDTO.java](src/main/java/com/youlai/mall/monomorph/dto/generated/client/MemberAddressDTO.java)
   - Corresponding Proto service `MemberAddressDTO` in file [member_address_dto.proto](src/main/proto/member_address_dto.proto)
   - DTO Message: MemberAddressDTODTO in [member_address_dto.proto](src/main/proto/member_address_dto.proto)
 - Class `com.youlai.mall.monomorph.dto.generated.client.MemberAuthDTO`:
   - A proxy for `com.youlai.mall.model.ums.dto.MemberAuthDTO`
   - Location: [src/main/java/com/youlai/mall/monomorph/dto/generated/client/MemberAuthDTO.java](src/main/java/com/youlai/mall/monomorph/dto/generated/client/MemberAuthDTO.java)
   - Corresponding Proto service `MemberAuthDTO` in file [member_auth_dto.proto](src/main/proto/member_auth_dto.proto)
   - DTO Message: MemberAuthDTODTO in [member_auth_dto.proto](src/main/proto/member_auth_dto.proto)
 - Class `com.youlai.mall.monomorph.dto.generated.client.MemberRegisterDto`:
   - A proxy for `com.youlai.mall.model.ums.dto.MemberRegisterDto`
   - Location: [src/main/java/com/youlai/mall/monomorph/dto/generated/client/MemberRegisterDto.java](src/main/java/com/youlai/mall/monomorph/dto/generated/client/MemberRegisterDto.java)
   - Corresponding Proto service `` in file [member_register_dto.proto](src/main/proto/member_register_dto.proto)
   - DTO Message: MemberRegisterDtoDTO in [member_register_dto.proto](src/main/proto/member_register_dto.proto)
 - Class `com.youlai.mall.monomorph.dto.generated.client.UserAuthInfo`:
   - A proxy for `com.youlai.mall.model.system.dto.UserAuthInfo`
   - Location: [src/main/java/com/youlai/mall/monomorph/dto/generated/client/UserAuthInfo.java](src/main/java/com/youlai/mall/monomorph/dto/generated/client/UserAuthInfo.java)
   - Corresponding Proto service `UserAuthInfo` in file [user_auth_info.proto](src/main/proto/user_auth_info.proto)
   - DTO Message: UserAuthInfoDTO in [user_auth_info.proto](src/main/proto/user_auth_info.proto)
 - Class `com.youlai.mall.monomorph.dto.generated.client.SystemConstants`:
   - A proxy for `com.youlai.mall.constant.SystemConstants`
   - Location: [src/main/java/com/youlai/mall/monomorph/dto/generated/client/SystemConstants.java](src/main/java/com/youlai/mall/monomorph/dto/generated/client/SystemConstants.java)
   - Corresponding Proto service `SystemConstants` in file [system_constants.proto](src/main/proto/system_constants.proto)
   - DTO Message: SystemConstantsDTO in [system_constants.proto](src/main/proto/system_constants.proto)

### Shared Utilities
 The following helper classes were added to the microservice in order to implement the pattern and shared logic defined in the approach (leasing, service discovery, ID mapping, etc):
 - Helper Proto file `leasing.proto`:
   - Location: [src/main/proto/leasing.proto](src/main/proto/leasing.proto)
   - Description: The proto file describing the leasing service api and messages. Required for the leasing/TTL logic. It is shared by all server microservices.
 - Helper Class `ServerObjectManager.java`:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/shared/server/ServerObjectManager.java](src/main/java/com/youlai/mall/monomorph/id/shared/server/ServerObjectManager.java)
   - Description: The ServerObjectManager interface defines the methods for managing the objects to IDs and vice versa. It is shared by all microservices.
 - Helper Proto file `shared.proto`:
   - Location: [src/main/proto/shared.proto](src/main/proto/shared.proto)
   - Description: The proto file that describes the RefactoredObjectID messages required for exchanging instance IDs across microservices. It is shared by all server microservices.
 - Helper Class `GrpcLeaseRpcClient.java`:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/shared/client/GrpcLeaseRpcClient.java](src/main/java/com/youlai/mall/monomorph/id/shared/client/GrpcLeaseRpcClient.java)
   - Description: The GrpcLeaseRpcClient class is a gRPC client that implements LeaseRpcClient and that interacts with the LeasingServiceImpl class. It is shared by all microservices.
 - Helper Class `LeaseRpcClient.java`:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/shared/client/LeaseRpcClient.java](src/main/java/com/youlai/mall/monomorph/id/shared/client/LeaseRpcClient.java)
   - Description: The LeaseRpcClient interface defines the methods that the client microservices can use to interact with the LeaseManager. It is shared by all microservices.
 - Helper Class `AbstractRefactoredClient.java`:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/shared/client/AbstractRefactoredClient.java](src/main/java/com/youlai/mall/monomorph/id/shared/client/AbstractRefactoredClient.java)
   - Description: The AbstractRefactoredClient class is a base class for all client classes that use the leasing API. It provides the common methods for the generated client classes and incorporates the leasing logic. It is shared by all microservices.
### Generated Utilities
 The following helper classes were generated and customized for the microservice "youlai-auth":
 - Class `ServiceRegistry` was generated for the microservice:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/generated/helpers/ServiceRegistry.java](src/main/java/com/youlai/mall/monomorph/id/generated/helpers/ServiceRegistry.java)
   - Description: A utility class that serves as a placeholder for service discovery. It is customized for each microservice.
 - Class `ClassIdRegistry` was generated for the microservice:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/generated/helpers/ClassIdRegistry.java](src/main/java/com/youlai/mall/monomorph/id/generated/helpers/ClassIdRegistry.java)
   - Description: A utility class that defines the CLASSIDs for the classes that are exchanged between microservices. It is customized for each microservice.
 - Class `IDMapper` was generated for the microservice:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/generated/helpers/IDMapper.java](src/main/java/com/youlai/mall/monomorph/id/generated/helpers/IDMapper.java)
   - Description: A utility class for mapping a RefactoredObjectID into actual type instances (if the type is in the microservice) or client instances and vice versa. It is customized for each microservice.

### Updated Imports
 The following imports were updated in the microservice:
 - In class [com.youlai.mall.service.auth.MemberDetailsService](src/main/java/com/youlai/mall/service/auth/MemberDetailsService.java):
   - `com.youlai.mall.service.ums.UmsMemberService` was replaced with `com.youlai.mall.monomorph.id.generated.client.UmsMemberService`
   - `com.youlai.mall.model.ums.dto.MemberAuthDTO` was replaced with `com.youlai.mall.monomorph.dto.generated.client.MemberAuthDTO`
   - `com.youlai.mall.model.ums.dto.MemberRegisterDto` was replaced with `com.youlai.mall.monomorph.dto.generated.client.MemberRegisterDto`
 - In class [com.youlai.mall.config.auth.oauth2.oidc.CustomOidcUserInfoService](src/main/java/com/youlai/mall/config/auth/oauth2/oidc/CustomOidcUserInfoService.java):
   - `com.youlai.mall.service.system.SysUserService` was replaced with `com.youlai.mall.monomorph.id.generated.client.SysUserService`
   - `com.youlai.mall.model.system.dto.UserAuthInfo` was replaced with `com.youlai.mall.monomorph.dto.generated.client.UserAuthInfo`
 - In class [com.youlai.mall.service.auth.SysUserDetailsService](src/main/java/com/youlai/mall/service/auth/SysUserDetailsService.java):
   - `com.youlai.mall.service.system.SysUserService` was replaced with `com.youlai.mall.monomorph.id.generated.client.SysUserService`
   - `com.youlai.mall.model.system.dto.UserAuthInfo` was replaced with `com.youlai.mall.monomorph.dto.generated.client.UserAuthInfo`
 - In class [com.youlai.mall.model.auth.MemberDetails](src/main/java/com/youlai/mall/model/auth/MemberDetails.java):
   - `com.youlai.mall.model.ums.dto.MemberAuthDTO` was replaced with `com.youlai.mall.monomorph.dto.generated.client.MemberAuthDTO`
 - In class [com.youlai.mall.model.auth.SysUserDetails](src/main/java/com/youlai/mall/model/auth/SysUserDetails.java):
   - `com.youlai.mall.model.system.dto.UserAuthInfo` was replaced with `com.youlai.mall.monomorph.dto.generated.client.UserAuthInfo`
 - In class [com.youlai.mall.security.util.SecurityUtils](src/main/java/com/youlai/mall/security/util/SecurityUtils.java):
   - `com.youlai.mall.constant.SystemConstants` was replaced with `com.youlai.mall.monomorph.dto.generated.client.SystemConstants`

---

---

## Detailed Refactoring Comments for Consumed Services
### Original Class `com.youlai.mall.service.ums.UmsMemberService`
#### Client Class `com.youlai.mall.monomorph.id.generated.client.UmsMemberService` (Service `UmsMemberServiceService`)
##### Explanation
 Validated the generated client against the exposed proto service methods. Only the declared methods are implemented publicly; createObject remains internal to client initialization. The class provides a public no-arg constructor because the original interface has no constructor parameters, and retains a private RefactoredObjectID constructor plus fromID factory. Proxy DTOs are converted with toDTO()/fromDTO() to avoid importing original model classes. Each service method lazily initializes the gRPC stub and wraps checked exceptions in RuntimeException to preserve signatures.
##### Comments
 The client class retains the template package and class name exactly as required. The public no-arg constructor calls initialize() without arguments. The private constructor and static fromID factory are retained for reattaching to an existing remote object. gRPC cleanup is handled in performSubclassRpcCleanup. No original model classes are imported.

---

### Original Class `com.youlai.mall.service.system.SysUserService`
#### Client Class `com.youlai.mall.monomorph.id.generated.client.SysUserService` (Service `SysUserServiceService`)
##### Explanation
 Generated gRPC client proxy for the original SysUserService interface. Only the business method getUserAuthInfo is exposed publicly; createObject is handled internally through performRemoteCreateAndGetId. The original interface had no concrete constructor, so the client uses a public no-arg constructor plus the required private RefactoredObjectID constructor and fromID factory. The return type is mapped to the generated proxy UserAuthInfo and converted from the protobuf UserAuthInfoDTO via UserAuthInfo.fromDTO.
##### Comments
 The gRPC stub is lazily initialized and reused across calls. performSubclassRpcCleanup shuts down the channel gracefully and restores the interrupt flag if awaitTermination is interrupted. The client never references the original com.youlai.mall.model.system.dto.UserAuthInfo class.

---

### Original Class `com.youlai.mall.model.pms.vo.ProductHistoryVO`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.ProductHistoryVO` (Service `ProductHistoryService`)
##### Explanation
 1. **Class Analysis**: `ProductHistoryVO` is a simple value object with three instance fields: `id` (`Long`), `name` (`String`), and `picUrl` (`String`). The original Lombok `@Data` class generated getters, setters, and a no-argument constructor.

2. **DTO Design**: The generated `ProductHistoryVODTO` protobuf message mirrors these fields. The client uses composition: it stores a `ProductHistoryVODTO` instance internally and delegates all getter/setter calls to it. Because protobuf messages are immutable, setters rebuild the internal DTO using its `toBuilder()` method.

3. **Type Mapping**:
   - `java.lang.Long` → `int64` (protobuf) → `long` (Java primitive)
   - `java.lang.String` → `string` (protobuf) → `String` (Java)
   Getter return types use wrapper `Long` to match the original API; setters accept wrapper `Long` and auto-unbox for the builder.

4. **Import Strategy**: Only the generated proto package is imported (`com.youlai.mall.monomorph.dto.generated.proto.producthistoryvo.*`). No additional imports are required.

No gRPC service methods are defined for `ProductHistoryVODTO` (the proto only contains a message), so no stub implementation is needed.
##### Comments
 - The DTO constructor is left **public** as provided in the template. If strict encapsulation is required, it can be made `private` because `fromDTO` is a static method inside the same class.
- **Null Handling**: Proto3 scalar fields do not preserve `null`. `Long` fields will return `0` when unset, and `String` fields will return the empty string. This matches the default behavior of the generated DTO and is a known limitation of proto3 scalars.
- The `serialVersionUID` field from the DTO is not exposed through getters/setters because the original Java class had it as a `static final` field, which does not generate instance accessors.

---

### Original Class `com.youlai.mall.model.ums.dto.MemberAddressDTO`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.MemberAddressDTO` (Service `MemberAddressDTO`)
##### Explanation
 The original MemberAddressDTO is a Lombok @Data DTO. The generated client uses composition rather than inheritance: all data is stored in a private MemberAddressDTODTO field, initialized to the default proto instance. A private constructor accepting MemberAddressDTODTO supports fromDTO()/toDTO() conversion. Getters and setters delegate to the internal proto DTO and rebuild the message on mutation because proto messages are immutable. Type mappings are Long to int64, Integer to int32, and String to string; null setter values clear the corresponding proto field.
##### Comments
 The client preserves the original getter/setter signatures with boxed types (Long, Integer) for API compatibility. Proto3 scalar fields do not support null directly, so null is approximated by clearing the field. No gRPC stub or ServiceRegistry integration is generated because the proto definition only contains a DTO message and no service. The constructor accepting MemberAddressDTODTO is private so external code uses the no-args constructor or fromDTO() rather than proto internals. Existing code must import this class from com.youlai.mall.monomorph.dto.generated.client rather than the original package.

---

### Original Class `com.youlai.mall.model.ums.dto.MemberAuthDTO`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.MemberAuthDTO` (Service `MemberAuthDTO`)
##### Explanation
 The original MemberAuthDTO is a simple Lombok data class with three fields: Long id, String username, and Integer status. The generated protobuf message MemberAuthDTODTO already represents the same three fields. The client class wraps a private MemberAuthDTODTO instance and delegates all getters/setters to it. This composition approach keeps the original API while replacing the original class's runtime storage with the generated DTO. The no-args constructor creates a protobuf default instance, and the all-args constructor builds a MemberAuthDTODTO from the provided values. The DTO-accepting constructor is kept for fromDTO/toDTO conversion. The proto definition defines only a DTO message, not a gRPC service, so no service stub methods or ServiceRegistry lookup are required.
##### Comments
 The client preserves the original class's constructor signatures ((), (Long, String, Integer)) so existing callers require no changes. The internal MemberAuthDTODTO is immutable, so setters rebuild the DTO using toBuilder(). Scalar protobuf fields cannot distinguish between unset and default zero/empty values. If null semantics are critical, the protobuf schema should be updated to use optional or wrapper types. This implementation treats null by clearing the field, which results in getters returning 0/"" rather than null. No gRPC service methods are implemented because the generated proto contained only a DTO message, not a service definition.

---

### Original Class `com.youlai.mall.model.ums.dto.MemberRegisterDto`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.MemberRegisterDto` (Service ``)
##### Explanation
 Fixed the generated wrapper so it compiles against standard protobuf-generated messages. Protobuf message objects are immutable, so setters must be applied through toBuilder(). The wrapper now keeps a mutable reference to the current DTO and reassigns it with an updated immutable snapshot on every setter call. LocalDate is still mapped to the protobuf string field using ISO-8601.
##### Comments
 Only the DTO wrapper is shown here; no service stub was generated because no gRPC service definition was in scope. The toDTO()/fromDTO() methods can be used directly for request/response marshalling when a service is available.

---

### Original Class `com.youlai.mall.model.system.dto.UserAuthInfo`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.UserAuthInfo` (Service `UserAuthInfo`)
##### Explanation
 Refactored UserAuthInfo into a gRPC DTO client by composing a single generated UserAuthInfoDTO instance instead of storing each field independently. The public no-argument constructor builds an empty DTO, while the private DTO constructor supports fromDTO. All original getters and setters are retained and delegate to the DTO. Set<String> fields (roles and perms) are converted to and from the DTO's repeated string lists using LinkedHashSet copies. No gRPC service methods were added because the original class had no business methods and the proto definition contains only the DTO message.
##### Comments
 Null handling: protobuf scalar fields do not support null; passing null to setters may throw NullPointerException, and unset fields return protobuf default values (0 or empty string) instead of null. If null semantics are required, use protobuf wrapper types such as google.protobuf.Int64Value/StringValue. Set mutability: getRoles() and getPerms() return defensive LinkedHashSet copies, so mutating the returned set does not modify the internal DTO.

---

### Original Class `com.youlai.mall.constant.SystemConstants`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.SystemConstants` (Service `SystemConstants`)
##### Explanation
 The original SystemConstants interface contains only public static final constants and exposes no instance methods, so no gRPC service methods or stub setup are generated. The client wraps a generated SystemConstantsDTO protobuf message and delegates field access to it. Since protobuf messages are immutable, setters rebuild the DTO via its builder while preserving all other field values.
##### Comments
 The generated protobuf field names are retained in uppercase as originally defined, so the generated Java DTO methods use concatenated uppercase names such as getROOTNODEID(). Because SystemConstantsDTO is immutable, setters on the client create a new DTO instance via the protobuf builder pattern, preserving any other field values. The original interface had no methods, so no gRPC stub setup or ServiceRegistry integration is required for this client. If null-safety is desired, the constructor could initialize dtoInstance with SystemConstantsDTO.getDefaultInstance() when a null value is provided, but this was not required.

---

---

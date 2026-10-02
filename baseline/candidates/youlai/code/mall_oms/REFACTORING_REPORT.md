# **Microservice "mall-oms" ("mall_oms") Report**
## Microservice Summary
 The microservice "mall-oms" (renamed "mall_oms" in pathing and identification) contains a total of **121** classes and files:
  - **63** classes were selected from the decomposition file
  - **29** classes were added as duplicate
  - **17** new classes were added or generated
  - **12** new proto files were added or generated

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
 - `com.youlai.mall.config.oms.OrderCloseRabbitConfig` was copied to [src/main/java/com/youlai/mall/config/oms/OrderCloseRabbitConfig.java](src/main/java/com/youlai/mall/config/oms/OrderCloseRabbitConfig.java)
 - `com.youlai.mall.config.oms.ThreadPoolConfig` was copied to [src/main/java/com/youlai/mall/config/oms/ThreadPoolConfig.java](src/main/java/com/youlai/mall/config/oms/ThreadPoolConfig.java)
 - `com.youlai.mall.config.oms.WxPayConfiguration` was copied to [src/main/java/com/youlai/mall/config/oms/WxPayConfiguration.java](src/main/java/com/youlai/mall/config/oms/WxPayConfiguration.java)
 - `com.youlai.mall.config.oms.WxPayProperties` was copied to [src/main/java/com/youlai/mall/config/oms/WxPayProperties.java](src/main/java/com/youlai/mall/config/oms/WxPayProperties.java)
 - `com.youlai.mall.constant.OrderConstants` was copied to [src/main/java/com/youlai/mall/constant/OrderConstants.java](src/main/java/com/youlai/mall/constant/OrderConstants.java)
 - `com.youlai.mall.controller.CartController` was copied to [src/main/java/com/youlai/mall/controller/CartController.java](src/main/java/com/youlai/mall/controller/CartController.java)
 - `com.youlai.mall.controller.OmsOrderController` was copied to [src/main/java/com/youlai/mall/controller/OmsOrderController.java](src/main/java/com/youlai/mall/controller/OmsOrderController.java)
 - `com.youlai.mall.controller.OrderController` was copied to [src/main/java/com/youlai/mall/controller/OrderController.java](src/main/java/com/youlai/mall/controller/OrderController.java)
 - `com.youlai.mall.controller.WxPayCallbackController` was copied to [src/main/java/com/youlai/mall/controller/WxPayCallbackController.java](src/main/java/com/youlai/mall/controller/WxPayCallbackController.java)
 - `com.youlai.mall.converter.CartConverter` was copied to [src/main/java/com/youlai/mall/converter/CartConverter.java](src/main/java/com/youlai/mall/converter/CartConverter.java)
 - `com.youlai.mall.converter.OrderConverter` was copied to [src/main/java/com/youlai/mall/converter/OrderConverter.java](src/main/java/com/youlai/mall/converter/OrderConverter.java)
 - `com.youlai.mall.converter.OrderItemConverter` was copied to [src/main/java/com/youlai/mall/converter/OrderItemConverter.java](src/main/java/com/youlai/mall/converter/OrderItemConverter.java)
 - `com.youlai.mall.enums.OrderSourceEnum` was copied to [src/main/java/com/youlai/mall/enums/OrderSourceEnum.java](src/main/java/com/youlai/mall/enums/OrderSourceEnum.java)
 - `com.youlai.mall.enums.OrderStatusEnum` was copied to [src/main/java/com/youlai/mall/enums/OrderStatusEnum.java](src/main/java/com/youlai/mall/enums/OrderStatusEnum.java)
 - `com.youlai.mall.enums.PaymentMethodEnum` was copied to [src/main/java/com/youlai/mall/enums/PaymentMethodEnum.java](src/main/java/com/youlai/mall/enums/PaymentMethodEnum.java)
 - `com.youlai.mall.factory.NamedThreadFactory` was copied to [src/main/java/com/youlai/mall/factory/NamedThreadFactory.java](src/main/java/com/youlai/mall/factory/NamedThreadFactory.java)
 - `com.youlai.mall.listener.OrderCloseListener` was copied to [src/main/java/com/youlai/mall/listener/OrderCloseListener.java](src/main/java/com/youlai/mall/listener/OrderCloseListener.java)
 - `com.youlai.mall.mapper.OrderDeliveryMapper` was copied to [src/main/java/com/youlai/mall/mapper/OrderDeliveryMapper.java](src/main/java/com/youlai/mall/mapper/OrderDeliveryMapper.java)
 - `com.youlai.mall.mapper.OrderItemMapper` was copied to [src/main/java/com/youlai/mall/mapper/OrderItemMapper.java](src/main/java/com/youlai/mall/mapper/OrderItemMapper.java)
 - `com.youlai.mall.mapper.OrderLogMapper` was copied to [src/main/java/com/youlai/mall/mapper/OrderLogMapper.java](src/main/java/com/youlai/mall/mapper/OrderLogMapper.java)
 - `com.youlai.mall.mapper.OrderMapper` was copied to [src/main/java/com/youlai/mall/mapper/OrderMapper.java](src/main/java/com/youlai/mall/mapper/OrderMapper.java)
 - `com.youlai.mall.mapper.OrderPayMapper` was copied to [src/main/java/com/youlai/mall/mapper/OrderPayMapper.java](src/main/java/com/youlai/mall/mapper/OrderPayMapper.java)
 - `com.youlai.mall.mapper.OrderSettingMapper` was copied to [src/main/java/com/youlai/mall/mapper/OrderSettingMapper.java](src/main/java/com/youlai/mall/mapper/OrderSettingMapper.java)
 - `com.youlai.mall.model.oms.bo.OrderBO` was copied to [src/main/java/com/youlai/mall/model/oms/bo/OrderBO.java](src/main/java/com/youlai/mall/model/oms/bo/OrderBO.java)
 - `com.youlai.mall.model.oms.dto.CartItemDto` was copied to [src/main/java/com/youlai/mall/model/oms/dto/CartItemDto.java](src/main/java/com/youlai/mall/model/oms/dto/CartItemDto.java)
 - `com.youlai.mall.model.oms.dto.CartItemVo` was copied to [src/main/java/com/youlai/mall/model/oms/dto/CartItemVo.java](src/main/java/com/youlai/mall/model/oms/dto/CartItemVo.java)
 - `com.youlai.mall.model.oms.dto.OrderDTO` was copied to [src/main/java/com/youlai/mall/model/oms/dto/OrderDTO.java](src/main/java/com/youlai/mall/model/oms/dto/OrderDTO.java)
 - `com.youlai.mall.model.oms.dto.OrderItemDTO` was copied to [src/main/java/com/youlai/mall/model/oms/dto/OrderItemDTO.java](src/main/java/com/youlai/mall/model/oms/dto/OrderItemDTO.java)
 - `com.youlai.mall.model.oms.entity.OmsOrder` was copied to [src/main/java/com/youlai/mall/model/oms/entity/OmsOrder.java](src/main/java/com/youlai/mall/model/oms/entity/OmsOrder.java)
 - `com.youlai.mall.model.oms.entity.OmsOrderDelivery` was copied to [src/main/java/com/youlai/mall/model/oms/entity/OmsOrderDelivery.java](src/main/java/com/youlai/mall/model/oms/entity/OmsOrderDelivery.java)
 - `com.youlai.mall.model.oms.entity.OmsOrderItem` was copied to [src/main/java/com/youlai/mall/model/oms/entity/OmsOrderItem.java](src/main/java/com/youlai/mall/model/oms/entity/OmsOrderItem.java)
 - `com.youlai.mall.model.oms.entity.OmsOrderLog` was copied to [src/main/java/com/youlai/mall/model/oms/entity/OmsOrderLog.java](src/main/java/com/youlai/mall/model/oms/entity/OmsOrderLog.java)
 - `com.youlai.mall.model.oms.entity.OmsOrderPay` was copied to [src/main/java/com/youlai/mall/model/oms/entity/OmsOrderPay.java](src/main/java/com/youlai/mall/model/oms/entity/OmsOrderPay.java)
 - `com.youlai.mall.model.oms.entity.OmsOrderSetting` was copied to [src/main/java/com/youlai/mall/model/oms/entity/OmsOrderSetting.java](src/main/java/com/youlai/mall/model/oms/entity/OmsOrderSetting.java)
 - `com.youlai.mall.model.oms.form.OrderPaymentForm` was copied to [src/main/java/com/youlai/mall/model/oms/form/OrderPaymentForm.java](src/main/java/com/youlai/mall/model/oms/form/OrderPaymentForm.java)
 - `com.youlai.mall.model.oms.form.OrderSubmitForm` was copied to [src/main/java/com/youlai/mall/model/oms/form/OrderSubmitForm.java](src/main/java/com/youlai/mall/model/oms/form/OrderSubmitForm.java)
 - `com.youlai.mall.model.oms.query.OrderPageQuery` was copied to [src/main/java/com/youlai/mall/model/oms/query/OrderPageQuery.java](src/main/java/com/youlai/mall/model/oms/query/OrderPageQuery.java)
 - `com.youlai.mall.model.oms.vo.OmsOrderPageVO` was copied to [src/main/java/com/youlai/mall/model/oms/vo/OmsOrderPageVO.java](src/main/java/com/youlai/mall/model/oms/vo/OmsOrderPageVO.java)
 - `com.youlai.mall.model.oms.vo.OrderConfirmVO` was copied to [src/main/java/com/youlai/mall/model/oms/vo/OrderConfirmVO.java](src/main/java/com/youlai/mall/model/oms/vo/OrderConfirmVO.java)
 - `com.youlai.mall.model.oms.vo.OrderPageVO` was copied to [src/main/java/com/youlai/mall/model/oms/vo/OrderPageVO.java](src/main/java/com/youlai/mall/model/oms/vo/OrderPageVO.java)
 - `com.youlai.mall.model.oms.vo.WxPayResponseVO` was copied to [src/main/java/com/youlai/mall/model/oms/vo/WxPayResponseVO.java](src/main/java/com/youlai/mall/model/oms/vo/WxPayResponseVO.java)
 - `com.youlai.mall.service.oms.admin.OmsOrderService` was copied to [src/main/java/com/youlai/mall/service/oms/admin/OmsOrderService.java](src/main/java/com/youlai/mall/service/oms/admin/OmsOrderService.java)
 - `com.youlai.mall.service.oms.admin.impl.OmsOrderServiceImpl` was copied to [src/main/java/com/youlai/mall/service/oms/admin/impl/OmsOrderServiceImpl.java](src/main/java/com/youlai/mall/service/oms/admin/impl/OmsOrderServiceImpl.java)
 - `com.youlai.mall.service.oms.app.CartService` was copied to [src/main/java/com/youlai/mall/service/oms/app/CartService.java](src/main/java/com/youlai/mall/service/oms/app/CartService.java)
 - `com.youlai.mall.service.oms.app.OrderDeliveryService` was copied to [src/main/java/com/youlai/mall/service/oms/app/OrderDeliveryService.java](src/main/java/com/youlai/mall/service/oms/app/OrderDeliveryService.java)
 - `com.youlai.mall.service.oms.app.OrderItemService` was copied to [src/main/java/com/youlai/mall/service/oms/app/OrderItemService.java](src/main/java/com/youlai/mall/service/oms/app/OrderItemService.java)
 - `com.youlai.mall.service.oms.app.OrderLogService` was copied to [src/main/java/com/youlai/mall/service/oms/app/OrderLogService.java](src/main/java/com/youlai/mall/service/oms/app/OrderLogService.java)
 - `com.youlai.mall.service.oms.app.OrderService` was copied to [src/main/java/com/youlai/mall/service/oms/app/OrderService.java](src/main/java/com/youlai/mall/service/oms/app/OrderService.java)
 - `com.youlai.mall.service.oms.app.OrderSettingService` was copied to [src/main/java/com/youlai/mall/service/oms/app/OrderSettingService.java](src/main/java/com/youlai/mall/service/oms/app/OrderSettingService.java)
 - `com.youlai.mall.service.oms.app.impl.CartServiceImpl` was copied to [src/main/java/com/youlai/mall/service/oms/app/impl/CartServiceImpl.java](src/main/java/com/youlai/mall/service/oms/app/impl/CartServiceImpl.java)
 - `com.youlai.mall.service.oms.app.impl.OrderDeliveryServiceImpl` was copied to [src/main/java/com/youlai/mall/service/oms/app/impl/OrderDeliveryServiceImpl.java](src/main/java/com/youlai/mall/service/oms/app/impl/OrderDeliveryServiceImpl.java)
 - `com.youlai.mall.service.oms.app.impl.OrderItemServiceImpl` was copied to [src/main/java/com/youlai/mall/service/oms/app/impl/OrderItemServiceImpl.java](src/main/java/com/youlai/mall/service/oms/app/impl/OrderItemServiceImpl.java)
 - `com.youlai.mall.service.oms.app.impl.OrderLogServiceImpl` was copied to [src/main/java/com/youlai/mall/service/oms/app/impl/OrderLogServiceImpl.java](src/main/java/com/youlai/mall/service/oms/app/impl/OrderLogServiceImpl.java)
 - `com.youlai.mall.service.oms.app.impl.OrderServiceImpl` was copied to [src/main/java/com/youlai/mall/service/oms/app/impl/OrderServiceImpl.java](src/main/java/com/youlai/mall/service/oms/app/impl/OrderServiceImpl.java)
 - `com.youlai.mall.service.oms.app.impl.OrderSettingServiceImpl` was copied to [src/main/java/com/youlai/mall/service/oms/app/impl/OrderSettingServiceImpl.java](src/main/java/com/youlai/mall/service/oms/app/impl/OrderSettingServiceImpl.java)
 - `com.youlai.mall.base.BaseEntity` was copied to [src/main/java/com/youlai/mall/base/BaseEntity.java](src/main/java/com/youlai/mall/base/BaseEntity.java)
 - `com.youlai.mall.base.BasePageQuery` was copied to [src/main/java/com/youlai/mall/base/BasePageQuery.java](src/main/java/com/youlai/mall/base/BasePageQuery.java)
 - `com.youlai.mall.base.IBaseEnum` was copied to [src/main/java/com/youlai/mall/base/IBaseEnum.java](src/main/java/com/youlai/mall/base/IBaseEnum.java)
 - `com.youlai.mall.constant.SystemConstants` was copied to [src/main/java/com/youlai/mall/constant/SystemConstants.java](src/main/java/com/youlai/mall/constant/SystemConstants.java)
 - `com.youlai.mall.result.IResultCode` was copied to [src/main/java/com/youlai/mall/result/IResultCode.java](src/main/java/com/youlai/mall/result/IResultCode.java)
 - `com.youlai.mall.result.PageResult` was copied to [src/main/java/com/youlai/mall/result/PageResult.java](src/main/java/com/youlai/mall/result/PageResult.java)
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
 - Class `com.youlai.mall.monomorph.id.generated.client.SkuService`:
   - A proxy for `com.youlai.mall.service.pms.SkuService`
   - Location: [src/main/java/com/youlai/mall/monomorph/id/generated/client/SkuService.java](src/main/java/com/youlai/mall/monomorph/id/generated/client/SkuService.java)
   - Corresponding Proto service `SkuServiceService` in file [sku_service.proto](src/main/proto/sku_service.proto)
 - Class `com.youlai.mall.monomorph.id.generated.client.UmsMemberService`:
   - A proxy for `com.youlai.mall.service.ums.UmsMemberService`
   - Location: [src/main/java/com/youlai/mall/monomorph/id/generated/client/UmsMemberService.java](src/main/java/com/youlai/mall/monomorph/id/generated/client/UmsMemberService.java)
   - Corresponding Proto service `UmsMemberServiceService` in file [ums_member_service.proto](src/main/proto/ums_member_service.proto)
 - Class `com.youlai.mall.monomorph.dto.generated.client.SkuInfoDTO`:
   - A proxy for `com.youlai.mall.model.pms.dto.SkuInfoDTO`
   - Location: [src/main/java/com/youlai/mall/monomorph/dto/generated/client/SkuInfoDTO.java](src/main/java/com/youlai/mall/monomorph/dto/generated/client/SkuInfoDTO.java)
   - Corresponding Proto service `SkuInfoDTODTO` in file [sku_info_dto.proto](src/main/proto/sku_info_dto.proto)
   - DTO Message: SkuInfoDTODTO in [sku_info_dto.proto](src/main/proto/sku_info_dto.proto)
 - Class `com.youlai.mall.monomorph.dto.generated.client.LockSkuDTO`:
   - A proxy for `com.youlai.mall.model.pms.dto.LockSkuDTO`
   - Location: [src/main/java/com/youlai/mall/monomorph/dto/generated/client/LockSkuDTO.java](src/main/java/com/youlai/mall/monomorph/dto/generated/client/LockSkuDTO.java)
   - Corresponding Proto service `LockSkuDTO` in file [lock_sku_dto.proto](src/main/proto/lock_sku_dto.proto)
   - DTO Message: LockSkuDTODTO in [lock_sku_dto.proto](src/main/proto/lock_sku_dto.proto)
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
 - Class `com.youlai.mall.monomorph.dto.generated.client.RedisConstants`:
   - A proxy for `com.youlai.mall.constant.RedisConstants`
   - Location: [src/main/java/com/youlai/mall/monomorph/dto/generated/client/RedisConstants.java](src/main/java/com/youlai/mall/monomorph/dto/generated/client/RedisConstants.java)
   - Corresponding Proto service `None` in file [redis_constants.proto](src/main/proto/redis_constants.proto)
   - DTO Message: RedisConstantsDTO in [redis_constants.proto](src/main/proto/redis_constants.proto)

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
 The following helper classes were generated and customized for the microservice "mall-oms":
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
 - In class [com.youlai.mall.listener.OrderCloseListener](src/main/java/com/youlai/mall/listener/OrderCloseListener.java):
   - `com.youlai.mall.service.pms.SkuService` was replaced with `com.youlai.mall.monomorph.id.generated.client.SkuService`
 - In class [com.youlai.mall.service.oms.app.impl.CartServiceImpl](src/main/java/com/youlai/mall/service/oms/app/impl/CartServiceImpl.java):
   - `com.youlai.mall.service.pms.SkuService` was replaced with `com.youlai.mall.monomorph.id.generated.client.SkuService`
   - `com.youlai.mall.model.pms.dto.SkuInfoDTO` was replaced with `com.youlai.mall.monomorph.dto.generated.client.SkuInfoDTO`
 - In class [com.youlai.mall.service.oms.app.impl.OrderServiceImpl](src/main/java/com/youlai/mall/service/oms/app/impl/OrderServiceImpl.java):
   - `com.youlai.mall.service.pms.SkuService` was replaced with `com.youlai.mall.monomorph.id.generated.client.SkuService`
   - `com.youlai.mall.service.ums.UmsMemberService` was replaced with `com.youlai.mall.monomorph.id.generated.client.UmsMemberService`
   - `com.youlai.mall.model.pms.dto.SkuInfoDTO` was replaced with `com.youlai.mall.monomorph.dto.generated.client.SkuInfoDTO`
   - `com.youlai.mall.model.pms.dto.LockSkuDTO` was replaced with `com.youlai.mall.monomorph.dto.generated.client.LockSkuDTO`
   - `com.youlai.mall.model.ums.dto.MemberAddressDTO` was replaced with `com.youlai.mall.monomorph.dto.generated.client.MemberAddressDTO`
 - In class [com.youlai.mall.converter.CartConverter](src/main/java/com/youlai/mall/converter/CartConverter.java):
   - `com.youlai.mall.model.pms.dto.SkuInfoDTO` was replaced with `com.youlai.mall.monomorph.dto.generated.client.SkuInfoDTO`
 - In class [com.youlai.mall.model.oms.vo.OrderConfirmVO](src/main/java/com/youlai/mall/model/oms/vo/OrderConfirmVO.java):
   - `com.youlai.mall.model.ums.dto.MemberAddressDTO` was replaced with `com.youlai.mall.monomorph.dto.generated.client.MemberAddressDTO`
 - In class [com.youlai.mall.security.service.PermissionService](src/main/java/com/youlai/mall/security/service/PermissionService.java):
   - `com.youlai.mall.constant.RedisConstants` was replaced with `com.youlai.mall.monomorph.dto.generated.client.RedisConstants`

---

---

## Detailed Refactoring Comments for Consumed Services
### Original Class `com.youlai.mall.service.pms.SkuService`
#### Client Class `com.youlai.mall.monomorph.id.generated.client.SkuService` (Service `SkuServiceService`)
##### Explanation
 Refactored SkuService to make gRPC setup idempotent and thread-safe. Channel and stub are now volatile and guarded by a static lock, preventing duplicate channel creation and races during lazy initialization. performRemoteCreateAndGetId now uses ensureRpcSetup instead of unconditionally calling performRpcSetup, which previously leaked channels on every remote-create call. Cleanup now atomically clears the channel/stub references and always calls shutdownNow if the graceful shutdown is interrupted. Removed the invalid @Override annotation from the static fromID factory method.
##### Comments
 The generated proto and DTO APIs were kept unchanged. If ConstructorArgs must be populated from the Object... args parameter, add the corresponding proto-field mapping in performRemoteCreateAndGetId based on the actual ConstructorArgs definition.

---

### Original Class `com.youlai.mall.service.ums.UmsMemberService`
#### Client Class `com.youlai.mall.monomorph.id.generated.client.UmsMemberService` (Service `UmsMemberServiceService`)
##### Explanation
 Implemented the generated gRPC client for UmsMemberService by extending AbstractRefactoredClient and wiring the blocking stub to the mall_ums service through ServiceRegistry. The original interface had no constructors, so the client exposes a public no-argument constructor and preserves a private constructor for the fromID factory. All exposed RPCs (addProductViewHistory, deductBalance, getMemberOpenId, listMemberAddresses, loadUserByMobile, loadUserByOpenId, registerMember) are implemented; createObject is not exposed directly and is instead used through performRemoteCreateAndGetId.
##### Comments
 Type mappings use client proxy toDTO()/fromDTO() methods for ProductHistoryVO, MemberRegisterDto, MemberAuthDTO, and MemberAddressDTO. Each request includes RefactoredObjectID as the first field. ConstructorArgs is explicitly empty because the original type is an interface. performRpcSetup is invoked during initialization by the parent, and performSubclassRpcCleanup shuts down the managed channel.

---

### Original Class `com.youlai.mall.model.pms.dto.SkuInfoDTO`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.SkuInfoDTO` (Service `SkuInfoDTODTO`)
##### Explanation
 1. **Class Analysis**: The original `SkuInfoDTO` is a Lombok `@Data` class with seven fields: `id`, `skuSn`, `skuName`, `picUrl`, `price`, `stock`, `spuName`. It has no business methods, only generated getters, setters, and a no-arg constructor. No RPC methods are defined.

2. **DTO Client Design**: The client uses composition to store all data in a private `SkuInfoDTODTO` field (`dtoInstance`). Getters and setters delegate to this proto-generated message. A public no-arg constructor creates a default `SkuInfoDTODTO` instance. The DTO-accepting constructor is kept private to encapsulate the internal DTO representation, while `fromDTO()` and `toDTO()` provide conversion.

3. **Type Mapping**: The generated proto message already uses the correct scalar types: `java.lang.Long id` ↔ `int64`, `java.lang.String` ↔ `string`, `java.lang.Long price` ↔ `int64`, `java.lang.Integer stock` ↔ `int32`. The client exposes the original wrapper types (`Long`, `Integer`) to maintain source compatibility.

4. **Import Strategy**: The client imports `com.youlai.mall.monomorph.dto.generated.proto.skuinfodto.*` to access the generated `SkuInfoDTODTO` class.

5. **gRPC Integration**: The generated proto definition contains only a `SkuInfoDTODTO` message and no service. Therefore, no gRPC stub methods or `ServiceRegistry` calls are implemented. The template's `performRpcSetup` and `ServiceRegistry` comments are placeholders for future RPC definitions and are not applicable to a pure DTO client.
##### Comments
 - **Null Handling**: Proto3 scalar fields cannot represent `null`. Setters that receive `null` clear the corresponding field, causing getters to return the proto default (`0` or `""`). This is an inherent limitation of the DTO pattern with proto3.
- **Constructor Visibility**: The DTO-accepting constructor is made private per the requirements, even though the original template showed `public`. This keeps the internal composition detail hidden while still enabling `fromDTO()` and the no-arg constructor.
- **No gRPC Service Methods**: The generated proto contained only a message definition and no service. Therefore, no RPC stubs or `ServiceRegistry` logic is included. If a service is later added, the required `performRpcSetup` and stub methods would be placed in this class.
- **API Compatibility**: All getters and setters retain the original Java wrapper types (`Long`, `Integer`, `String`) to maintain source-level compatibility with existing code.
- **Immutability**: Each setter rebuilds the internal `SkuInfoDTODTO` using its builder, preserving immutability of the proto message while allowing safe updates.

---

### Original Class `com.youlai.mall.model.pms.dto.LockSkuDTO`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.LockSkuDTO` (Service `LockSkuDTO`)
##### Explanation
 The original com.youlai.mall.model.pms.dto.LockSkuDTO is a Lombok-annotated DTO with Long skuId and Integer quantity. The generated client uses composition with the protobuf message LockSkuDTODTO, preserving the original no-arg and all-arg constructor API. Long maps to protobuf int64 and Integer maps to int32; proto3 scalar defaults mean null is coerced to 0 on setters. Getters/setters delegate to the immutable protobuf message using toBuilder(), and fromDTO/toDTO provide conversion points.
##### Comments
 The client retains the original simple name LockSkuDTO and the expected no-arg/all-arg constructors. Since LockSkuDTODTO fields are proto3 scalar types without optional, null cannot be represented directly; setters map null to 0/0L, and getters always return a non-null boxed value. No gRPC service methods were generated, so no additional stubs or RPC calls are implemented. fromDTO and toDTO do not copy the underlying protobuf message, which is immutable, so conversion is efficient and safe.

---

### Original Class `com.youlai.mall.model.pms.vo.ProductHistoryVO`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.ProductHistoryVO` (Service `ProductHistoryService`)
##### Explanation
 ProductHistoryVO is refactored to a standalone gRPC DTO client. Lombok @Data behavior is replaced with explicit constructors, getters, and setters. The class stores an internal ProductHistoryVODTO protobuf message and delegates all data access to it. The no-arg constructor creates an empty DTO, and a constructor plus fromDTO/toDTO methods support protobuf conversion.
##### Comments
 The no-arg constructor matches the original Lombok @Data generated constructor. serialVersionUID is not exposed via getters/setters because it is a static final field on the original class and therefore not part of the instance API. Protobuf scalar fields cannot represent null; the client maps null to protobuf defaults (0 for numbers, empty string for strings) to preserve predictable behavior. The client avoids any reference to the original ProductHistoryVO class and relies solely on the generated ProductHistoryVODTO, making it suitable as a standalone microservice client.

---

### Original Class `com.youlai.mall.model.ums.dto.MemberAddressDTO`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.MemberAddressDTO` (Service `MemberAddressDTO`)
##### Explanation
 The original Lombok @Data DTO is transformed into a plain Java wrapper class that composes the generated protobuf DTO MemberAddressDTODTO. A no-argument constructor is provided to match the original API and initializes an empty DTO using the proto builder. All getters and setters delegate to the internal DTO, preserving field names and Java types. Proto3 scalar fields cannot be null, so setters map null to the corresponding proto default (0 for Long/Integer, empty string for String).
##### Comments
 This is a pure DTO wrapper, so no gRPC stub or ServiceRegistry integration is required. The public constructor accepting MemberAddressDTODTO and the static fromDTO() method support conversion between the wrapper and the generated proto DTO. The import is explicit rather than wildcard to avoid name clashes.

---

### Original Class `com.youlai.mall.model.ums.dto.MemberAuthDTO`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.MemberAuthDTO` (Service `MemberAuthDTO`)
##### Explanation
 The original MemberAuthDTO is a Lombok data class with Long id, String username, and Integer status, exposing no-args/all-args constructors and getters/setters through @Data, @NoArgsConstructor, and @AllArgsConstructor. The generated proto contains only a MemberAuthDTODTO message and no service definition, so the client uses composition instead of a gRPC stub: it wraps a private MemberAuthDTODTO instance and delegates getter/setter calls to it. Type mapping is Long to int64, String to string, and Integer to int32. Null inputs are coerced to proto scalar defaults (0L, "", 0) because the scalar fields are not marked optional, so getters never return null.
##### Comments
 The generated proto service definition contains no service, only a MemberAuthDTODTO message, so no ServiceRegistry, TARGET_SERVICE_ID, or gRPC stub integration is required. The private MemberAuthDTO(MemberAuthDTODTO) constructor supports fromDTO/toDTO while preventing external code from depending on the internal DTO type. The original class in com.youlai.mall.model.ums.dto is not referenced. Null preservation would require optional or wrapper fields in the proto definition.

---

### Original Class `com.youlai.mall.model.ums.dto.MemberRegisterDto`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.MemberRegisterDto` (Service ``)
##### Explanation
 The original MemberRegisterDto is a Lombok @Data POJO with 11 private fields and no explicit constructors or business methods. The refactored client uses composition to wrap a MemberRegisterDtoDTO proto message. Getters and setters delegate to this DTO, adapting Integer to int32 and LocalDate to ISO-8601 string. The public no-arg constructor preserves the original API, and no gRPC service methods are implemented because the generated proto definition contains only a message.
##### Comments
 The client implements the same getters/setters as the original MemberRegisterDto, but type adaptation is required for gender (Integer vs int32) and birthday (LocalDate vs string). Because proto3 scalar fields are not nullable, null values are converted to default values (0 for gender, empty string for strings). This may cause a slight semantic difference when round-tripping null values. The DTO message is immutable; setters use toBuilder() and build() to create a new instance, reassigning the internal field. No gRPC service methods are implemented because the generated proto definition contains only a message, no service. Therefore no stub or ServiceRegistry integration is required. The private constructor accepting MemberRegisterDtoDTO is used by fromDTO(); the public no-arg constructor preserves the original API.

---

### Original Class `com.youlai.mall.constant.RedisConstants`
#### Client Class `com.youlai.mall.monomorph.dto.generated.client.RedisConstants` (Service `None`)
##### Explanation
 This client implements the DTO composition pattern for RedisConstants. The original class was a Java interface containing only public static final String constants. Those constants are represented in the generated protobuf message RedisConstantsDTO as string fields. Because the original class had no methods, the generated proto also has no service definition. Therefore, the client only needs to expose getters and setters that delegate to the internal RedisConstantsDTO instance. Data is held in the private dtoInstance field. fromDTO and toDTO are provided by the template and remain unchanged. Getter methods return values from the internal DTO, and setter methods rebuild the DTO via its builder because protobuf generated messages are immutable. Getters and setters follow standard Protobuf Java naming conventions (snake_case proto field to lowerCamelCase Java accessor).
##### Comments
 The original class was an interface with only constants. The DTO-based client exposes instance getters/setters rather than static fields, as required by the DTO composition pattern. Protobuf generated messages are immutable, so setters replace the internal dtoInstance with a new builder result. Method names follow the standard Java accessor convention for Protobuf fields. If the generated DTO accessors differ because of the non-standard uppercase proto field names, adjust the getter/setter method names accordingly to match the generated DTO. No gRPC service stubs are needed because the proto definition contains only RedisConstantsDTO.

---

---

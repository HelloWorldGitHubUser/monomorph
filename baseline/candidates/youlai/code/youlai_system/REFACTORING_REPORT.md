# **Microservice "youlai-system" ("youlai_system") Report**
## Microservice Summary
 The microservice "youlai-system" (renamed "youlai_system" in pathing and identification) contains a total of **148** classes and files:
  - **100** classes were selected from the decomposition file
  - **29** classes were added as duplicate
  - **15** new classes were added or generated
  - **4** new proto files were added or generated

 The microservice has a new main class "[MonoMorphYoulai_systemMain](src/main/java/com/youlai/mall/monomorph/MonoMorphYoulai_systemMain.java)" that combines the old main of the monolith "[MonolithApplication](src/main/java/com/youlai/mall/MonolithApplication.java)" and the new gRPC main class "[MonoMorphYoulai_systemServerGRPC](src/main/java/com/youlai/mall/monomorph/id/MonoMorphYoulai_systemServerGRPC.java)".

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
 - `com.youlai.mall.config.system.PasswordEncoderConfig` was copied to [src/main/java/com/youlai/mall/config/system/PasswordEncoderConfig.java](src/main/java/com/youlai/mall/config/system/PasswordEncoderConfig.java)
 - `com.youlai.mall.controller.FileController` was copied to [src/main/java/com/youlai/mall/controller/FileController.java](src/main/java/com/youlai/mall/controller/FileController.java)
 - `com.youlai.mall.controller.SysDeptController` was copied to [src/main/java/com/youlai/mall/controller/SysDeptController.java](src/main/java/com/youlai/mall/controller/SysDeptController.java)
 - `com.youlai.mall.controller.SysDictController` was copied to [src/main/java/com/youlai/mall/controller/SysDictController.java](src/main/java/com/youlai/mall/controller/SysDictController.java)
 - `com.youlai.mall.controller.SysMenuController` was copied to [src/main/java/com/youlai/mall/controller/SysMenuController.java](src/main/java/com/youlai/mall/controller/SysMenuController.java)
 - `com.youlai.mall.controller.SysRoleController` was copied to [src/main/java/com/youlai/mall/controller/SysRoleController.java](src/main/java/com/youlai/mall/controller/SysRoleController.java)
 - `com.youlai.mall.controller.SysUserController` was copied to [src/main/java/com/youlai/mall/controller/SysUserController.java](src/main/java/com/youlai/mall/controller/SysUserController.java)
 - `com.youlai.mall.converter.DeptConverter` was copied to [src/main/java/com/youlai/mall/converter/DeptConverter.java](src/main/java/com/youlai/mall/converter/DeptConverter.java)
 - `com.youlai.mall.converter.DictConverter` was copied to [src/main/java/com/youlai/mall/converter/DictConverter.java](src/main/java/com/youlai/mall/converter/DictConverter.java)
 - `com.youlai.mall.converter.DictTypeConverter` was copied to [src/main/java/com/youlai/mall/converter/DictTypeConverter.java](src/main/java/com/youlai/mall/converter/DictTypeConverter.java)
 - `com.youlai.mall.converter.MenuConverter` was copied to [src/main/java/com/youlai/mall/converter/MenuConverter.java](src/main/java/com/youlai/mall/converter/MenuConverter.java)
 - `com.youlai.mall.converter.RoleConverter` was copied to [src/main/java/com/youlai/mall/converter/RoleConverter.java](src/main/java/com/youlai/mall/converter/RoleConverter.java)
 - `com.youlai.mall.converter.UserConverter` was copied to [src/main/java/com/youlai/mall/converter/UserConverter.java](src/main/java/com/youlai/mall/converter/UserConverter.java)
 - `com.youlai.mall.enums.GenderEnum` was copied to [src/main/java/com/youlai/mall/enums/GenderEnum.java](src/main/java/com/youlai/mall/enums/GenderEnum.java)
 - `com.youlai.mall.enums.MenuTypeEnum` was copied to [src/main/java/com/youlai/mall/enums/MenuTypeEnum.java](src/main/java/com/youlai/mall/enums/MenuTypeEnum.java)
 - `com.youlai.mall.listener.CanalListener` was copied to [src/main/java/com/youlai/mall/listener/CanalListener.java](src/main/java/com/youlai/mall/listener/CanalListener.java)
 - `com.youlai.mall.listener.MyAnalysisEventListener` was copied to [src/main/java/com/youlai/mall/listener/MyAnalysisEventListener.java](src/main/java/com/youlai/mall/listener/MyAnalysisEventListener.java)
 - `com.youlai.mall.listener.UserImportListener` was copied to [src/main/java/com/youlai/mall/listener/UserImportListener.java](src/main/java/com/youlai/mall/listener/UserImportListener.java)
 - `com.youlai.mall.mapper.SysDeptMapper` was copied to [src/main/java/com/youlai/mall/mapper/SysDeptMapper.java](src/main/java/com/youlai/mall/mapper/SysDeptMapper.java)
 - `com.youlai.mall.mapper.SysDictMapper` was copied to [src/main/java/com/youlai/mall/mapper/SysDictMapper.java](src/main/java/com/youlai/mall/mapper/SysDictMapper.java)
 - `com.youlai.mall.mapper.SysDictTypeMapper` was copied to [src/main/java/com/youlai/mall/mapper/SysDictTypeMapper.java](src/main/java/com/youlai/mall/mapper/SysDictTypeMapper.java)
 - `com.youlai.mall.mapper.SysMenuMapper` was copied to [src/main/java/com/youlai/mall/mapper/SysMenuMapper.java](src/main/java/com/youlai/mall/mapper/SysMenuMapper.java)
 - `com.youlai.mall.mapper.SysRoleMapper` was copied to [src/main/java/com/youlai/mall/mapper/SysRoleMapper.java](src/main/java/com/youlai/mall/mapper/SysRoleMapper.java)
 - `com.youlai.mall.mapper.SysRoleMenuMapper` was copied to [src/main/java/com/youlai/mall/mapper/SysRoleMenuMapper.java](src/main/java/com/youlai/mall/mapper/SysRoleMenuMapper.java)
 - `com.youlai.mall.mapper.SysUserMapper` was copied to [src/main/java/com/youlai/mall/mapper/SysUserMapper.java](src/main/java/com/youlai/mall/mapper/SysUserMapper.java)
 - `com.youlai.mall.mapper.SysUserRoleMapper` was copied to [src/main/java/com/youlai/mall/mapper/SysUserRoleMapper.java](src/main/java/com/youlai/mall/mapper/SysUserRoleMapper.java)
 - `com.youlai.mall.model.system.bo.RolePermsBO` was copied to [src/main/java/com/youlai/mall/model/system/bo/RolePermsBO.java](src/main/java/com/youlai/mall/model/system/bo/RolePermsBO.java)
 - `com.youlai.mall.model.system.bo.RouteBO` was copied to [src/main/java/com/youlai/mall/model/system/bo/RouteBO.java](src/main/java/com/youlai/mall/model/system/bo/RouteBO.java)
 - `com.youlai.mall.model.system.bo.UserBO` was copied to [src/main/java/com/youlai/mall/model/system/bo/UserBO.java](src/main/java/com/youlai/mall/model/system/bo/UserBO.java)
 - `com.youlai.mall.model.system.bo.UserFormBO` was copied to [src/main/java/com/youlai/mall/model/system/bo/UserFormBO.java](src/main/java/com/youlai/mall/model/system/bo/UserFormBO.java)
 - `com.youlai.mall.model.system.bo.UserProfileBO` was copied to [src/main/java/com/youlai/mall/model/system/bo/UserProfileBO.java](src/main/java/com/youlai/mall/model/system/bo/UserProfileBO.java)
 - `com.youlai.mall.model.system.dto.UserAuthInfo` was copied to [src/main/java/com/youlai/mall/model/system/dto/UserAuthInfo.java](src/main/java/com/youlai/mall/model/system/dto/UserAuthInfo.java)
 - `com.youlai.mall.model.system.entity.SysDept` was copied to [src/main/java/com/youlai/mall/model/system/entity/SysDept.java](src/main/java/com/youlai/mall/model/system/entity/SysDept.java)
 - `com.youlai.mall.model.system.entity.SysDict` was copied to [src/main/java/com/youlai/mall/model/system/entity/SysDict.java](src/main/java/com/youlai/mall/model/system/entity/SysDict.java)
 - `com.youlai.mall.model.system.entity.SysDictType` was copied to [src/main/java/com/youlai/mall/model/system/entity/SysDictType.java](src/main/java/com/youlai/mall/model/system/entity/SysDictType.java)
 - `com.youlai.mall.model.system.entity.SysMenu` was copied to [src/main/java/com/youlai/mall/model/system/entity/SysMenu.java](src/main/java/com/youlai/mall/model/system/entity/SysMenu.java)
 - `com.youlai.mall.model.system.entity.SysRole` was copied to [src/main/java/com/youlai/mall/model/system/entity/SysRole.java](src/main/java/com/youlai/mall/model/system/entity/SysRole.java)
 - `com.youlai.mall.model.system.entity.SysRoleMenu` was copied to [src/main/java/com/youlai/mall/model/system/entity/SysRoleMenu.java](src/main/java/com/youlai/mall/model/system/entity/SysRoleMenu.java)
 - `com.youlai.mall.model.system.entity.SysUser` was copied to [src/main/java/com/youlai/mall/model/system/entity/SysUser.java](src/main/java/com/youlai/mall/model/system/entity/SysUser.java)
 - `com.youlai.mall.model.system.entity.SysUserRole` was copied to [src/main/java/com/youlai/mall/model/system/entity/SysUserRole.java](src/main/java/com/youlai/mall/model/system/entity/SysUserRole.java)
 - `com.youlai.mall.model.system.form.DeptForm` was copied to [src/main/java/com/youlai/mall/model/system/form/DeptForm.java](src/main/java/com/youlai/mall/model/system/form/DeptForm.java)
 - `com.youlai.mall.model.system.form.DictForm` was copied to [src/main/java/com/youlai/mall/model/system/form/DictForm.java](src/main/java/com/youlai/mall/model/system/form/DictForm.java)
 - `com.youlai.mall.model.system.form.DictTypeForm` was copied to [src/main/java/com/youlai/mall/model/system/form/DictTypeForm.java](src/main/java/com/youlai/mall/model/system/form/DictTypeForm.java)
 - `com.youlai.mall.model.system.form.MenuForm` was copied to [src/main/java/com/youlai/mall/model/system/form/MenuForm.java](src/main/java/com/youlai/mall/model/system/form/MenuForm.java)
 - `com.youlai.mall.model.system.form.RoleForm` was copied to [src/main/java/com/youlai/mall/model/system/form/RoleForm.java](src/main/java/com/youlai/mall/model/system/form/RoleForm.java)
 - `com.youlai.mall.model.system.form.UserForm` was copied to [src/main/java/com/youlai/mall/model/system/form/UserForm.java](src/main/java/com/youlai/mall/model/system/form/UserForm.java)
 - `com.youlai.mall.model.system.form.UserRegisterForm` was copied to [src/main/java/com/youlai/mall/model/system/form/UserRegisterForm.java](src/main/java/com/youlai/mall/model/system/form/UserRegisterForm.java)
 - `com.youlai.mall.model.system.query.DeptQuery` was copied to [src/main/java/com/youlai/mall/model/system/query/DeptQuery.java](src/main/java/com/youlai/mall/model/system/query/DeptQuery.java)
 - `com.youlai.mall.model.system.query.DictPageQuery` was copied to [src/main/java/com/youlai/mall/model/system/query/DictPageQuery.java](src/main/java/com/youlai/mall/model/system/query/DictPageQuery.java)
 - `com.youlai.mall.model.system.query.DictTypePageQuery` was copied to [src/main/java/com/youlai/mall/model/system/query/DictTypePageQuery.java](src/main/java/com/youlai/mall/model/system/query/DictTypePageQuery.java)
 - `com.youlai.mall.model.system.query.MenuQuery` was copied to [src/main/java/com/youlai/mall/model/system/query/MenuQuery.java](src/main/java/com/youlai/mall/model/system/query/MenuQuery.java)
 - `com.youlai.mall.model.system.query.PermPageQuery` was copied to [src/main/java/com/youlai/mall/model/system/query/PermPageQuery.java](src/main/java/com/youlai/mall/model/system/query/PermPageQuery.java)
 - `com.youlai.mall.model.system.query.RolePageQuery` was copied to [src/main/java/com/youlai/mall/model/system/query/RolePageQuery.java](src/main/java/com/youlai/mall/model/system/query/RolePageQuery.java)
 - `com.youlai.mall.model.system.query.UserPageQuery` was copied to [src/main/java/com/youlai/mall/model/system/query/UserPageQuery.java](src/main/java/com/youlai/mall/model/system/query/UserPageQuery.java)
 - `com.youlai.mall.model.system.vo.DeptVO` was copied to [src/main/java/com/youlai/mall/model/system/vo/DeptVO.java](src/main/java/com/youlai/mall/model/system/vo/DeptVO.java)
 - `com.youlai.mall.model.system.vo.DictPageVO` was copied to [src/main/java/com/youlai/mall/model/system/vo/DictPageVO.java](src/main/java/com/youlai/mall/model/system/vo/DictPageVO.java)
 - `com.youlai.mall.model.system.vo.DictTypePageVO` was copied to [src/main/java/com/youlai/mall/model/system/vo/DictTypePageVO.java](src/main/java/com/youlai/mall/model/system/vo/DictTypePageVO.java)
 - `com.youlai.mall.model.system.vo.FileInfoVO` was copied to [src/main/java/com/youlai/mall/model/system/vo/FileInfoVO.java](src/main/java/com/youlai/mall/model/system/vo/FileInfoVO.java)
 - `com.youlai.mall.model.system.vo.MenuVO` was copied to [src/main/java/com/youlai/mall/model/system/vo/MenuVO.java](src/main/java/com/youlai/mall/model/system/vo/MenuVO.java)
 - `com.youlai.mall.model.system.vo.RolePageVO` was copied to [src/main/java/com/youlai/mall/model/system/vo/RolePageVO.java](src/main/java/com/youlai/mall/model/system/vo/RolePageVO.java)
 - `com.youlai.mall.model.system.vo.RouteVO` was copied to [src/main/java/com/youlai/mall/model/system/vo/RouteVO.java](src/main/java/com/youlai/mall/model/system/vo/RouteVO.java)
 - `com.youlai.mall.model.system.vo.UserExportVO` was copied to [src/main/java/com/youlai/mall/model/system/vo/UserExportVO.java](src/main/java/com/youlai/mall/model/system/vo/UserExportVO.java)
 - `com.youlai.mall.model.system.vo.UserImportVO` was copied to [src/main/java/com/youlai/mall/model/system/vo/UserImportVO.java](src/main/java/com/youlai/mall/model/system/vo/UserImportVO.java)
 - `com.youlai.mall.model.system.vo.UserInfoVO` was copied to [src/main/java/com/youlai/mall/model/system/vo/UserInfoVO.java](src/main/java/com/youlai/mall/model/system/vo/UserInfoVO.java)
 - `com.youlai.mall.model.system.vo.UserPageVO` was copied to [src/main/java/com/youlai/mall/model/system/vo/UserPageVO.java](src/main/java/com/youlai/mall/model/system/vo/UserPageVO.java)
 - `com.youlai.mall.model.system.vo.UserProfileVO` was copied to [src/main/java/com/youlai/mall/model/system/vo/UserProfileVO.java](src/main/java/com/youlai/mall/model/system/vo/UserProfileVO.java)
 - `com.youlai.mall.service.system.OssService` was copied to [src/main/java/com/youlai/mall/service/system/OssService.java](src/main/java/com/youlai/mall/service/system/OssService.java)
 - `com.youlai.mall.service.system.SysDeptService` was copied to [src/main/java/com/youlai/mall/service/system/SysDeptService.java](src/main/java/com/youlai/mall/service/system/SysDeptService.java)
 - `com.youlai.mall.service.system.SysDictService` was copied to [src/main/java/com/youlai/mall/service/system/SysDictService.java](src/main/java/com/youlai/mall/service/system/SysDictService.java)
 - `com.youlai.mall.service.system.SysDictTypeService` was copied to [src/main/java/com/youlai/mall/service/system/SysDictTypeService.java](src/main/java/com/youlai/mall/service/system/SysDictTypeService.java)
 - `com.youlai.mall.service.system.SysMenuService` was copied to [src/main/java/com/youlai/mall/service/system/SysMenuService.java](src/main/java/com/youlai/mall/service/system/SysMenuService.java)
 - `com.youlai.mall.service.system.SysRoleMenuService` was copied to [src/main/java/com/youlai/mall/service/system/SysRoleMenuService.java](src/main/java/com/youlai/mall/service/system/SysRoleMenuService.java)
 - `com.youlai.mall.service.system.SysRoleService` was copied to [src/main/java/com/youlai/mall/service/system/SysRoleService.java](src/main/java/com/youlai/mall/service/system/SysRoleService.java)
 - `com.youlai.mall.service.system.SysUserRoleService` was copied to [src/main/java/com/youlai/mall/service/system/SysUserRoleService.java](src/main/java/com/youlai/mall/service/system/SysUserRoleService.java)
 - `com.youlai.mall.service.system.SysUserService` was copied to [src/main/java/com/youlai/mall/service/system/SysUserService.java](src/main/java/com/youlai/mall/service/system/SysUserService.java)
 - `com.youlai.mall.service.system.impl.SysDeptServiceImpl` was copied to [src/main/java/com/youlai/mall/service/system/impl/SysDeptServiceImpl.java](src/main/java/com/youlai/mall/service/system/impl/SysDeptServiceImpl.java)
 - `com.youlai.mall.service.system.impl.SysDictServiceImpl` was copied to [src/main/java/com/youlai/mall/service/system/impl/SysDictServiceImpl.java](src/main/java/com/youlai/mall/service/system/impl/SysDictServiceImpl.java)
 - `com.youlai.mall.service.system.impl.SysDictTypeServiceImpl` was copied to [src/main/java/com/youlai/mall/service/system/impl/SysDictTypeServiceImpl.java](src/main/java/com/youlai/mall/service/system/impl/SysDictTypeServiceImpl.java)
 - `com.youlai.mall.service.system.impl.SysMenuServiceImpl` was copied to [src/main/java/com/youlai/mall/service/system/impl/SysMenuServiceImpl.java](src/main/java/com/youlai/mall/service/system/impl/SysMenuServiceImpl.java)
 - `com.youlai.mall.service.system.impl.SysRoleMenuServiceImpl` was copied to [src/main/java/com/youlai/mall/service/system/impl/SysRoleMenuServiceImpl.java](src/main/java/com/youlai/mall/service/system/impl/SysRoleMenuServiceImpl.java)
 - `com.youlai.mall.service.system.impl.SysRoleServiceImpl` was copied to [src/main/java/com/youlai/mall/service/system/impl/SysRoleServiceImpl.java](src/main/java/com/youlai/mall/service/system/impl/SysRoleServiceImpl.java)
 - `com.youlai.mall.service.system.impl.SysUserRoleServiceImpl` was copied to [src/main/java/com/youlai/mall/service/system/impl/SysUserRoleServiceImpl.java](src/main/java/com/youlai/mall/service/system/impl/SysUserRoleServiceImpl.java)
 - `com.youlai.mall.service.system.impl.SysUserServiceImpl` was copied to [src/main/java/com/youlai/mall/service/system/impl/SysUserServiceImpl.java](src/main/java/com/youlai/mall/service/system/impl/SysUserServiceImpl.java)
 - `com.youlai.mall.service.system.impl.oss.AliyunOssService` was copied to [src/main/java/com/youlai/mall/service/system/impl/oss/AliyunOssService.java](src/main/java/com/youlai/mall/service/system/impl/oss/AliyunOssService.java)
 - `com.youlai.mall.service.system.impl.oss.MinioOssService` was copied to [src/main/java/com/youlai/mall/service/system/impl/oss/MinioOssService.java](src/main/java/com/youlai/mall/service/system/impl/oss/MinioOssService.java)
 - `com.youlai.mall.base.BaseEntity` was copied to [src/main/java/com/youlai/mall/base/BaseEntity.java](src/main/java/com/youlai/mall/base/BaseEntity.java)
 - `com.youlai.mall.base.BasePageQuery` was copied to [src/main/java/com/youlai/mall/base/BasePageQuery.java](src/main/java/com/youlai/mall/base/BasePageQuery.java)
 - `com.youlai.mall.base.IBaseEnum` was copied to [src/main/java/com/youlai/mall/base/IBaseEnum.java](src/main/java/com/youlai/mall/base/IBaseEnum.java)
 - `com.youlai.mall.constant.GlobalConstants` was copied to [src/main/java/com/youlai/mall/constant/GlobalConstants.java](src/main/java/com/youlai/mall/constant/GlobalConstants.java)
 - `com.youlai.mall.constant.RedisConstants` was copied to [src/main/java/com/youlai/mall/constant/RedisConstants.java](src/main/java/com/youlai/mall/constant/RedisConstants.java)
 - `com.youlai.mall.constant.SystemConstants` was copied to [src/main/java/com/youlai/mall/constant/SystemConstants.java](src/main/java/com/youlai/mall/constant/SystemConstants.java)
 - `com.youlai.mall.enums.StatusEnum` was copied to [src/main/java/com/youlai/mall/enums/StatusEnum.java](src/main/java/com/youlai/mall/enums/StatusEnum.java)
 - `com.youlai.mall.messaging.property.AliyunSmsProperties` was copied to [src/main/java/com/youlai/mall/messaging/property/AliyunSmsProperties.java](src/main/java/com/youlai/mall/messaging/property/AliyunSmsProperties.java)
 - `com.youlai.mall.messaging.service.SmsService` was copied to [src/main/java/com/youlai/mall/messaging/service/SmsService.java](src/main/java/com/youlai/mall/messaging/service/SmsService.java)
 - `com.youlai.mall.messaging.service.impl.AliyunSmsService` was copied to [src/main/java/com/youlai/mall/messaging/service/impl/AliyunSmsService.java](src/main/java/com/youlai/mall/messaging/service/impl/AliyunSmsService.java)
 - `com.youlai.mall.result.IResultCode` was copied to [src/main/java/com/youlai/mall/result/IResultCode.java](src/main/java/com/youlai/mall/result/IResultCode.java)
 - `com.youlai.mall.result.PageResult` was copied to [src/main/java/com/youlai/mall/result/PageResult.java](src/main/java/com/youlai/mall/result/PageResult.java)
 - `com.youlai.mall.result.Result` was copied to [src/main/java/com/youlai/mall/result/Result.java](src/main/java/com/youlai/mall/result/Result.java)
 - `com.youlai.mall.result.ResultCode` was copied to [src/main/java/com/youlai/mall/result/ResultCode.java](src/main/java/com/youlai/mall/result/ResultCode.java)
 - `com.youlai.mall.web.model.Option` was copied to [src/main/java/com/youlai/mall/web/model/Option.java](src/main/java/com/youlai/mall/web/model/Option.java)

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

### New Services
 The following gRPC services were generated and added to expose their corresponding classes to other microservices:
 - Class `com.youlai.mall.monomorph.id.generated.proto.sysuserservice.SysUserServiceService`:
   - Exposes the API of [com.youlai.mall.service.system.SysUserService](src/main/java/com/youlai/mall/service/system/SysUserService.java)
   - Location: [src/main/java/com/youlai/mall/monomorph/id/generated/server/SysUserServiceImpl.java](src/main/java/com/youlai/mall/monomorph/id/generated/server/SysUserServiceImpl.java)
   - Corresponding Proto service `SysUserServiceService` in file [sys_user_service.proto](src/main/proto/sys_user_service.proto)

### Shared Utilities
 The following helper classes were added to the microservice in order to implement the pattern and shared logic defined in the approach (leasing, service discovery, ID mapping, etc):
 - Helper Class `CaffeineLeaseManager.java`:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/shared/server/CaffeineLeaseManager.java](src/main/java/com/youlai/mall/monomorph/id/shared/server/CaffeineLeaseManager.java)
   - Description: The CaffeineLeaseManager class is an implementation of the LeaseManager interface that uses Caffeine for caching leases in the runtime memory. It implements the leasing logic required for managing the lifecycle of classes across different microservices. It is shared by all server microservices.
 - Helper Class `LeaseKey.java`:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/shared/server/LeaseKey.java](src/main/java/com/youlai/mall/monomorph/id/shared/server/LeaseKey.java)
   - Description: The LeaseKey class is used to identify the lease of an object in the leasing system. It is defined by a RefactoredObjectID instance and a clientID (the microservice that is using the instance). It is shared by all server microservices.
 - Helper Class `ServerObjectManager.java`:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/shared/server/ServerObjectManager.java](src/main/java/com/youlai/mall/monomorph/id/shared/server/ServerObjectManager.java)
   - Description: The ServerObjectManager interface defines the methods for managing the objects to IDs and vice versa. It is shared by all microservices.
 - Helper Class `GrpcLeaseRpcClient.java`:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/shared/client/GrpcLeaseRpcClient.java](src/main/java/com/youlai/mall/monomorph/id/shared/client/GrpcLeaseRpcClient.java)
   - Description: The GrpcLeaseRpcClient class is a gRPC client that implements LeaseRpcClient and that interacts with the LeasingServiceImpl class. It is shared by all microservices.
 - Helper Class `LeaseRpcClient.java`:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/shared/client/LeaseRpcClient.java](src/main/java/com/youlai/mall/monomorph/id/shared/client/LeaseRpcClient.java)
   - Description: The LeaseRpcClient interface defines the methods that the client microservices can use to interact with the LeaseManager. It is shared by all microservices.
 - Helper Proto file `shared.proto`:
   - Location: [src/main/proto/shared.proto](src/main/proto/shared.proto)
   - Description: The proto file that describes the RefactoredObjectID messages required for exchanging instance IDs across microservices. It is shared by all server microservices.
 - Helper Class `AbstractRefactoredClient.java`:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/shared/client/AbstractRefactoredClient.java](src/main/java/com/youlai/mall/monomorph/id/shared/client/AbstractRefactoredClient.java)
   - Description: The AbstractRefactoredClient class is a base class for all client classes that use the leasing API. It provides the common methods for the generated client classes and incorporates the leasing logic. It is shared by all microservices.
 - Helper Proto file `leasing.proto`:
   - Location: [src/main/proto/leasing.proto](src/main/proto/leasing.proto)
   - Description: The proto file describing the leasing service api and messages. Required for the leasing/TTL logic. It is shared by all server microservices.
 - Helper Class `LeaseManager.java`:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/shared/server/LeaseManager.java](src/main/java/com/youlai/mall/monomorph/id/shared/server/LeaseManager.java)
   - Description: The LeaseManager interface defines the methods for managing leases. It is shared by all server microservices.
 - Helper Class `LeasingServiceImpl.java`:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/shared/server/LeasingServiceImpl.java](src/main/java/com/youlai/mall/monomorph/id/shared/server/LeasingServiceImpl.java)
   - Description: The LeasingServiceImpl class is a gRPC service implementation that exposes the leasing API so that client microservices can interact with server microservices and handle the leasing interactions. It is shared by all server microservices.
### Generated Utilities
 The following helper classes were generated and customized for the microservice "youlai-system":
 - Class `ServiceRegistry` was generated for the microservice:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/generated/helpers/ServiceRegistry.java](src/main/java/com/youlai/mall/monomorph/id/generated/helpers/ServiceRegistry.java)
   - Description: A utility class that serves as a placeholder for service discovery. It is customized for each microservice.
 - Class `ClassIdRegistry` was generated for the microservice:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/generated/helpers/ClassIdRegistry.java](src/main/java/com/youlai/mall/monomorph/id/generated/helpers/ClassIdRegistry.java)
   - Description: A utility class that defines the CLASSIDs for the classes that are exchanged between microservices. It is customized for each microservice.
 - Class `IDMapper` was generated for the microservice:
   - Location: [src/main/java/com/youlai/mall/monomorph/id/generated/helpers/IDMapper.java](src/main/java/com/youlai/mall/monomorph/id/generated/helpers/IDMapper.java)
   - Description: A utility class for mapping a RefactoredObjectID into actual type instances (if the type is in the microservice) or client instances and vice versa. It is customized for each microservice.
 -  Class `MonoMorphYoulai_systemServerGRPC`:
     - Location: [src/main/java/com/youlai/mall/monomorph/id/MonoMorphYoulai_systemServerGRPC.java](src/main/java/com/youlai/mall/monomorph/id/MonoMorphYoulai_systemServerGRPC.java)
     - Description: A Server class with a main method that initializes and exposes the new gRPC services
 -  Class `MonoMorphYoulai_systemMain`:
     - Location: [src/main/java/com/youlai/mall/monomorph/MonoMorphYoulai_systemMain.java](src/main/java/com/youlai/mall/monomorph/MonoMorphYoulai_systemMain.java)
     - Description: A new entrypoint class that combines the old entrypoint of the monolith and the new gRPC server class
     - **This is the main entrypoint of the microservice**

---

---

## Detailed Refactoring Comments for Exposed Services
### Original Class `com.youlai.mall.service.system.SysUserService`
#### Service `com.youlai.mall.monomorph.id.generated.proto.sysuserservice.SysUserServiceService`
##### Explanation
 From the requested methods, only getUserAuthInfo(java.lang.String) exists in SysUserService. SysUserService is a Java interface and does not declare a concrete constructor, so ConstructorArgs remains empty. The username parameter maps to protobuf string, and the return type maps to UserAuthInfoDTO from user_auth_info.proto. CreateObjectRequest and ConstructorArgs follow the provided template. GetUserAuthInfoRequest includes the required RefactoredObjectID field plus the original username input. GetUserAuthInfoResponse wraps the mapped UserAuthInfoDTO output. Imports are limited to shared.proto for RefactoredObjectID and user_auth_info.proto for UserAuthInfoDTO.
##### Comments
 Only getUserAuthInfo is exposed from the requested method list. createObject is retained as the constructor wrapper from the template, with empty ConstructorArgs because the target class is an interface. The RefactoredObjectID field is included in GetUserAuthInfoRequest, as required for all request messages except CreateObjectRequest. Fully qualified proto type names are used with a leading dot to avoid package resolution ambiguity.
#### Server Class `com.youlai.mall.monomorph.id.generated.server.SysUserServiceImpl`
##### Explanation
 Refactored SysUserServiceImpl for safer gRPC handling while preserving the existing API. Added null/empty validation for requests and object IDs, type-safe instance checks before casting, a fallback to the service ID when createObject receives a blank client ID, and clearer error messages for lease lookups. The gRPC service contract and method signatures remain unchanged.
##### Comments
 The SysUserService singleton is still obtained from the constructor rather than reconstructed from CreateObjectRequest. LeaseManager remains the source of truth for instance IDs, and createObject reuses an existing lease ID when the singleton has already been registered.

---


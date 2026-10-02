package com.youlai.mall.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import com.youlai.mall.monomorph.id.generated.proto.sysuserservice.*;
import com.youlai.mall.monomorph.id.shared.server.LeaseManager;
import com.youlai.mall.monomorph.id.shared.server.ServerObjectManager;
import com.youlai.mall.monomorph.id.shared.RefactoredObjectID;
import com.youlai.mall.monomorph.id.generated.helpers.ServiceRegistry;
import com.youlai.mall.monomorph.id.generated.helpers.ClassIdRegistry;
import com.youlai.mall.service.system.SysUserService;
import com.youlai.mall.model.system.dto.UserAuthInfo;
import com.youlai.mall.monomorph.dto.generated.proto.userauthinfo.UserAuthInfoDTO;
import com.youlai.mall.monomorph.dto.generated.proto.userauthinfo.UserAuthInfoMapper;

import java.util.Objects;
import java.util.UUID;

/**
 * gRPC Service implementation for SysUserService.
 * - Handles gRPC requests for SysUserService API.
 * - Manages singleton SysUserService instance (Spring-managed).
 * - Interacts with LeaseManager for instance registration/retrieval.
 * - Calls business methods on retrieved SysUserService instances.
 */
public class SysUserServiceImpl extends SysUserServiceServiceGrpc.SysUserServiceServiceImplBase implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final String serviceId;
    private final SysUserService sysUserService;

    public static final String CLASS_ID = ClassIdRegistry.getClassId("SysUserService");

    public SysUserServiceImpl(LeaseManager leaseManager, SysUserService sysUserService) {
        this.leaseManager = Objects.requireNonNull(leaseManager, "leaseManager");
        this.sysUserService = Objects.requireNonNull(sysUserService, "sysUserService");
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId(), "serviceId");
    }

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            if (request == null) {
                throw new IllegalArgumentException("CreateObjectRequest must not be null");
            }
            String clientId = request.getClientID();
            SysUserService instance = this.sysUserService;
            RefactoredObjectID responseProto = toID(instance, clientId);
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getUserAuthInfo(GetUserAuthInfoRequest request, StreamObserver<GetUserAuthInfoResponse> responseObserver) {
        try {
            if (request == null) {
                throw new IllegalArgumentException("GetUserAuthInfoRequest must not be null");
            }
            if (request.getObjectId() == null) {
                throw new IllegalArgumentException("ObjectId must not be null");
            }
            SysUserService instance = fromID(request.getObjectId());
            UserAuthInfo userAuthInfo = instance.getUserAuthInfo(request.getUsername());
            if (userAuthInfo == null) {
                throw new IllegalStateException("SysUserService.getUserAuthInfo returned null for username: " + request.getUsername());
            }
            UserAuthInfoDTO dto = UserAuthInfoMapper.INSTANCE.toDTO(userAuthInfo);
            GetUserAuthInfoResponse response = GetUserAuthInfoResponse.newBuilder()
                    .setUserAuthInfo(dto)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public RefactoredObjectID toID(Object instance, String clientId) throws Exception {
        if (!(instance instanceof SysUserService)) {
            throw new IllegalArgumentException(
                    "Expected instance of SysUserService, got: " + (instance == null ? "null" : instance.getClass().getName()));
        }
        String effectiveClientId = (clientId == null || clientId.trim().isEmpty()) ? serviceId : clientId;
        String instanceId = leaseManager.findInstanceIdForInstance(instance);
        if (instanceId == null || instanceId.trim().isEmpty()) {
            instanceId = UUID.randomUUID().toString();
        }
        boolean registered = leaseManager.registerInstanceAndGrantLease(instanceId, CLASS_ID, instance, effectiveClientId);
        if (!registered) {
            throw new IllegalStateException("Failed to register new instance ID: " + instanceId);
        }
        return RefactoredObjectID.newBuilder()
                .setInstanceID(instanceId)
                .setClassID(CLASS_ID)
                .setServiceID(this.serviceId)
                .build();
    }

    @Override
    public RefactoredObjectID toID(Object instance) throws Exception {
        return toID(instance, serviceId);
    }

    @Override
    public SysUserService fromID(RefactoredObjectID id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("ObjectID must not be null");
        }
        if (id.getInstanceID() == null || id.getInstanceID().trim().isEmpty()) {
            throw new IllegalArgumentException("InstanceID must not be null or empty");
        }
        if (!CLASS_ID.equals(id.getClassID())) {
            throw new IllegalArgumentException("class ID mismatch: expected " + CLASS_ID + ", got " + id.getClassID());
        }
        if (id.getServiceID() != null && !id.getServiceID().trim().isEmpty() && !this.serviceId.equals(id.getServiceID())) {
            throw new IllegalArgumentException("service ID mismatch: expected " + this.serviceId + ", got " + id.getServiceID());
        }
        Object instance = leaseManager.getInstance(id.getInstanceID());
        if (!(instance instanceof SysUserService)) {
            throw new IllegalArgumentException("No SysUserService instance found for ID: " + id.getInstanceID());
        }
        return (SysUserService) instance;
    }

    @Override
    public String getManagedClassId() {
        return CLASS_ID;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }
}

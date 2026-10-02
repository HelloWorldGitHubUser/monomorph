package com.goodskill.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import com.goodskill.monomorph.id.generated.proto.seckillservice.*;
import com.goodskill.monomorph.id.shared.server.LeaseManager;
import com.goodskill.monomorph.id.shared.server.ServerObjectManager;
import com.goodskill.monomorph.id.shared.RefactoredObjectID;
import com.goodskill.monomorph.id.generated.helpers.ServiceRegistry;
import com.goodskill.monomorph.id.generated.helpers.ClassIdRegistry;
import com.goodskill.service.SeckillService;
import com.goodskill.dto.SeckillMockRequestDTO;
import com.goodskill.monomorph.dto.generated.helpers.SeckillMockRequestDTOMapper;
import java.util.Objects;
import java.util.UUID;

/**
 * gRPC Service implementation for SeckillService.
 * Handles object instance management and delegates business calls.
 */
public class SeckillServiceImpl extends SeckillServiceServiceGrpc.SeckillServiceServiceImplBase
        implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final String serviceId;

    public static final String CLASS_ID = ClassIdRegistry.getClassId("SeckillService");

    public SeckillServiceImpl(LeaseManager leaseManager) {
        this.leaseManager = Objects.requireNonNull(leaseManager, "leaseManager");
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId(), "serviceId");
    }

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            String clientId = request.getClientID();

            // Use the fully qualified name of the business implementation to avoid a
            // simple-name clash with this generated gRPC service class.
            SeckillService newInstance = new com.goodskill.service.impl.SeckillServiceImpl();

            RefactoredObjectID responseProto = toID(newInstance, clientId);
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public RefactoredObjectID toID(Object instance, String clientId) throws Exception {
        String instanceId = leaseManager.findInstanceIdForInstance(instance);
        if (instanceId == null) {
            instanceId = UUID.randomUUID().toString();
        }
        boolean registered = leaseManager.registerInstanceAndGrantLease(instanceId, CLASS_ID, instance, clientId);
        if (!registered) {
            throw new RuntimeException("Failed to register new instance ID: " + instanceId);
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
    public SeckillService fromID(RefactoredObjectID id) throws Exception {
        if (id == null || id.getClassID() == null || !id.getClassID().equals(CLASS_ID)) {
            throw new IllegalArgumentException(
                    "class ID mismatch: expected " + CLASS_ID + ", got " + (id == null ? "null" : id.getClassID()));
        }
        Object instance = leaseManager.getInstance(id.getInstanceID());
        if (instance == null) {
            throw new IllegalArgumentException("No instance found for ID: " + id.getInstanceID());
        }
        if (!(instance instanceof SeckillService)) {
            throw new IllegalArgumentException(
                    "Instance is not of type SeckillService: " + instance.getClass().getName());
        }
        return (SeckillService) instance;
    }

    @Override
    public String getManagedClassId() {
        return CLASS_ID;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public void endSeckill(EndSeckillRequest request, StreamObserver<EndSeckillResponse> responseObserver) {
        try {
            SeckillService instance = fromID(request.getObjectId());
            boolean success = instance.endSeckill(request.getSeckillId());
            EndSeckillResponse response = EndSeckillResponse.newBuilder()
                    .setSuccess(success)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void execute(ExecuteRequest request, StreamObserver<ExecuteResponse> responseObserver) {
        try {
            SeckillService instance = fromID(request.getObjectId());
            SeckillMockRequestDTO actualDto = SeckillMockRequestDTOMapper.INSTANCE.fromDTO(request.getRequestDto());
            instance.execute(actualDto, request.getStrategyNumber());
            responseObserver.onNext(ExecuteResponse.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getSuccessKillCount(GetSuccessKillCountRequest request,
                                    StreamObserver<GetSuccessKillCountResponse> responseObserver) {
        try {
            SeckillService instance = fromID(request.getObjectId());
            long count = instance.getSuccessKillCount(request.getSeckillId());
            GetSuccessKillCountResponse response = GetSuccessKillCountResponse.newBuilder()
                    .setCount(count)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void prepareSeckill(PrepareSeckillRequest request,
                               StreamObserver<PrepareSeckillResponse> responseObserver) {
        try {
            SeckillService instance = fromID(request.getObjectId());
            instance.prepareSeckill(request.getSeckillId(), request.getSeckillCount(), request.getTaskId());
            responseObserver.onNext(PrepareSeckillResponse.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}

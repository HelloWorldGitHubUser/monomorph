package com.goodskill.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import com.goodskill.monomorph.id.generated.proto.seckillmockcontroller.*;
import com.goodskill.monomorph.id.shared.server.LeaseManager;
import com.goodskill.monomorph.id.shared.server.ServerObjectManager;
import com.goodskill.monomorph.id.shared.RefactoredObjectID;
import com.goodskill.monomorph.id.generated.helpers.ServiceRegistry;
import com.goodskill.monomorph.id.generated.helpers.ClassIdRegistry;
import com.goodskill.controller.SeckillMockController;

import com.goodskill.monomorph.dto.generated.client.Result;
import com.goodskill.monomorph.dto.generated.client.SeckillWebMockRequestDTO;

import java.util.Objects;
import java.util.UUID;

/**
 * gRPC Service implementation for SeckillMockController.
 * - Handles gRPC requests for SeckillMockController API.
 * - Creates transient SeckillMockController instances.
 * - Interacts with LeaseManager for instance registration/retrieval.
 * - Calls business methods on retrieved SeckillMockController instances.
 */
public class SeckillMockControllerImpl extends SeckillMockControllerServiceGrpc.SeckillMockControllerServiceImplBase
        implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final String serviceId;

    // Static identifier for the class type managed by this service
    public static final String CLASS_ID = ClassIdRegistry.getClassId("SeckillMockController");

    public SeckillMockControllerImpl(LeaseManager leaseManager) {
        this.leaseManager = Objects.requireNonNull(leaseManager);
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId());
    }

    // --- createObject gRPC Method Implementation ---

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            String clientId = request.getClientID();
            // ConstructorArgs is intentionally empty; SeckillMockController has a default no-arg constructor.
            // Create a new transient instance.
            SeckillMockController newInstance = new SeckillMockController();

            // Register and obtain a RefactoredObjectID
            RefactoredObjectID responseProto = toID(newInstance, clientId);

            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // --- ServerObjectManager method implementations ---

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
    public SeckillMockController fromID(RefactoredObjectID id) throws Exception {
        if (id.getClassID() == null || !id.getClassID().equals(CLASS_ID)) {
            throw new IllegalArgumentException("class ID mismatch: expected " + CLASS_ID + ", got " + id.getClassID());
        }
        SeckillMockController instance = (SeckillMockController) leaseManager.getInstance(id.getInstanceID());
        if (instance == null) {
            throw new IllegalArgumentException("No instance found for ID: " + id);
        }
        return instance;
    }

    @Override
    public String getManagedClassId() {
        return CLASS_ID;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    // --- Other gRPC methods defined in the proto file ---

    @Override
    public void doWithSychronized(DoWithSychronizedRequest request,
                                  StreamObserver<DoWithSychronizedResponse> responseObserver) {
        try {
            SeckillMockController instance = fromID(request.getObjectId());
            SeckillWebMockRequestDTO clientDto = SeckillWebMockRequestDTO.fromDTO(request.getDto());
            Result<Long> result = instance.doWithSychronized(clientDto);

            DoWithSychronizedResponse response = DoWithSychronizedResponse.newBuilder()
                    .setResult(result.toDTO())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void getTaskTimeInfo(GetTaskTimeInfoRequest request,
                                StreamObserver<GetTaskTimeInfoResponse> responseObserver) {
        try {
            SeckillMockController instance = fromID(request.getObjectId());
            long seckillId = request.getSeckillId();
            Result<String> result = instance.getTaskTimeInfo(seckillId);

            GetTaskTimeInfoResponse response = GetTaskTimeInfoResponse.newBuilder()
                    .setResult(result.toDTO())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
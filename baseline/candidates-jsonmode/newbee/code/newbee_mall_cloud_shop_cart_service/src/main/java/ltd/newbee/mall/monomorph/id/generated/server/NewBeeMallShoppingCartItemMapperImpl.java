package ltd.newbee.mall.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallshoppingcartitemmapper.*;
import ltd.newbee.mall.monomorph.id.shared.server.LeaseManager;
import ltd.newbee.mall.monomorph.id.shared.server.ServerObjectManager;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.generated.helpers.ClassIdRegistry;
import ltd.newbee.mall.dao.NewBeeMallShoppingCartItemMapper;

import java.util.Objects;
import java.util.UUID;

/**
 * gRPC Service implementation for NewBeeMallShoppingCartItemMapper.
 * - Handles gRPC requests for the deleteBatch operation.
 * - Cannot create instances because the target class is an interface.
 * - Retrieves existing instances via LeaseManager and calls business methods.
 */
public class NewBeeMallShoppingCartItemMapperImpl
        extends NewBeeMallShoppingCartItemMapperServiceGrpc.NewBeeMallShoppingCartItemMapperServiceImplBase
        implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final String serviceId;

    public static final String CLASS_ID = ClassIdRegistry.getClassId("NewBeeMallShoppingCartItemMapper");

    public NewBeeMallShoppingCartItemMapperImpl(LeaseManager leaseManager) {
        this.leaseManager = Objects.requireNonNull(leaseManager);
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId());
    }

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        // The original class NewBeeMallShoppingCartItemMapper is an interface.
        // It has no constructor and cannot be instantiated directly.
        // Therefore object creation is not supported for this service.
        responseObserver.onError(new UnsupportedOperationException(
                "Cannot create instance of NewBeeMallShoppingCartItemMapper: it is an interface"));
    }

    @Override
    public RefactoredObjectID toID(Object instance, String clientId) throws Exception {
        if (instance == null) {
            throw new IllegalArgumentException("Cannot register null instance");
        }
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
    public NewBeeMallShoppingCartItemMapper fromID(RefactoredObjectID id) throws Exception {
        if (id == null || id.getClassID() == null || !id.getClassID().equals(CLASS_ID)) {
            throw new IllegalArgumentException(
                    "class ID mismatch: expected " + CLASS_ID + ", got " + (id == null ? "null" : id.getClassID()));
        }
        Object instance = leaseManager.getInstance(id.getInstanceID());
        if (instance == null) {
            throw new IllegalArgumentException("No instance found for ID: " + id.getInstanceID());
        }
        if (!(instance instanceof NewBeeMallShoppingCartItemMapper)) {
            throw new IllegalArgumentException("Instance is not of type NewBeeMallShoppingCartItemMapper");
        }
        return (NewBeeMallShoppingCartItemMapper) instance;
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
    public void deleteBatch(DeleteBatchRequest request, StreamObserver<DeleteBatchResponse> responseObserver) {
        try {
            RefactoredObjectID objectId = request.getObjectId();
            NewBeeMallShoppingCartItemMapper instance = fromID(objectId);
            int deletedCount = instance.deleteBatch(request.getIdsList());
            DeleteBatchResponse response = DeleteBatchResponse.newBuilder()
                    .setDeletedCount(deletedCount)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
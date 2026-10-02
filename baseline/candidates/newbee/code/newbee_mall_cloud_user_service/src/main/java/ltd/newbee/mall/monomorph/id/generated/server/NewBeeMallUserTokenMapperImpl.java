package ltd.newbee.mall.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallusertokenmapper.*;
import ltd.newbee.mall.monomorph.id.shared.server.LeaseManager;
import ltd.newbee.mall.monomorph.id.shared.server.ServerObjectManager;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.generated.helpers.ClassIdRegistry;
import ltd.newbee.mall.dao.NewBeeMallUserTokenMapper;
import ltd.newbee.mall.entity.MallUserToken;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusertoken.MallUserTokenDTO;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusertoken.MallUserTokenMapper;

import java.util.Objects;
import java.util.UUID;

/**
 * gRPC Service implementation for NewBeeMallUserTokenMapper.
 * - Handles gRPC requests for NewBeeMallUserTokenMapper API.
 * - Instance creation is not supported because the target type is an interface.
 * - Business methods retrieve registered instances and delegate calls.
 */
public class NewBeeMallUserTokenMapperImpl extends NewBeeMallUserTokenMapperServiceGrpc.NewBeeMallUserTokenMapperServiceImplBase implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final String serviceId;

    public static final String CLASS_ID = ClassIdRegistry.getClassId("NewBeeMallUserTokenMapper");

    public NewBeeMallUserTokenMapperImpl(LeaseManager leaseManager) {
        this.leaseManager = Objects.requireNonNull(leaseManager);
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId());
    }

    // --- createObject gRPC Method Implementation ---

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            // NewBeeMallUserTokenMapper is an interface (MyBatis mapper) and cannot be
            // instantiated directly. No concrete implementation is available in this
            // service. Therefore object creation is not supported here.
            throw new UnsupportedOperationException(
                "Cannot create instance of NewBeeMallUserTokenMapper: interface and no concrete implementation provided."
            );
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
    public NewBeeMallUserTokenMapper fromID(RefactoredObjectID id) throws Exception {
        if (id.getClassID() == null || !id.getClassID().equals(CLASS_ID)) {
            throw new IllegalArgumentException("class ID mismatch: expected " + CLASS_ID + ", got " + id.getClassID());
        }
        NewBeeMallUserTokenMapper instance = (NewBeeMallUserTokenMapper) leaseManager.getInstance(id.getInstanceID());
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
    public void selectByToken(SelectByTokenRequest request, StreamObserver<SelectByTokenResponse> responseObserver) {
        try {
            // Retrieve the instance using the provided RefactoredObjectID
            NewBeeMallUserTokenMapper instance = fromID(request.getRefactoredObjectID());

            // Call the actual business method
            MallUserToken result = instance.selectByToken(request.getToken());

            // Map entity to DTO
            MallUserTokenDTO dto = MallUserTokenMapper.INSTANCE.toDTO(result);

            // Build and send response
            SelectByTokenResponse response = SelectByTokenResponse.newBuilder()
                    .setResult(dto)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
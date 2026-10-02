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
import ltd.newbee.mall.monomorph.dto.generated.mapper.MallUserTokenMapper;

import java.util.Objects;
import java.util.UUID;

/**
 * gRPC Service implementation for NewBeeMallUserTokenMapper.
 * - Handles gRPC requests for NewBeeMallUserTokenMapper API.
 * - Uses an injected singleton NewBeeMallUserTokenMapper instance.
 * - Interacts with LeaseManager for instance registration/retrieval.
 * - Calls business methods on the injected mapper instance.
 */
public class NewBeeMallUserTokenMapperImpl
        extends NewBeeMallUserTokenMapperServiceGrpc.NewBeeMallUserTokenMapperServiceImplBase
        implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final NewBeeMallUserTokenMapper mapper;
    private final String serviceId;

    // Static identifier for the class type managed by this service
    public static final String CLASS_ID = ClassIdRegistry.getClassId("NewBeeMallUserTokenMapper");

    public NewBeeMallUserTokenMapperImpl(LeaseManager leaseManager, NewBeeMallUserTokenMapper mapper) {
        this.leaseManager = Objects.requireNonNull(leaseManager);
        this.mapper = Objects.requireNonNull(mapper);
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId());
    }

    // --- createObject gRPC Method Implementation ---

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            String clientId = request.getClientID();
            RefactoredObjectID responseProto = toID(mapper, clientId);
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // --- Business method implementations ---

    @Override
    public void selectByToken(SelectByTokenRequest request, StreamObserver<SelectByTokenResponse> responseObserver) {
        try {
            RefactoredObjectID id = request.getRefactoredObjectId();
            NewBeeMallUserTokenMapper instance = fromID(id);
            MallUserToken result = instance.selectByToken(request.getToken());
            MallUserTokenDTO dto = MallUserTokenMapper.INSTANCE.toDTO(result);
            SelectByTokenResponse response = SelectByTokenResponse.newBuilder()
                    .setMallUserToken(dto)
                    .build();
            responseObserver.onNext(response);
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
    public NewBeeMallUserTokenMapper fromID(RefactoredObjectID id) throws Exception {
        if (id == null || id.getClassID() == null || !id.getClassID().equals(CLASS_ID)) {
            throw new IllegalArgumentException("class ID mismatch: expected " + CLASS_ID
                    + ", got " + (id != null ? id.getClassID() : null));
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
}

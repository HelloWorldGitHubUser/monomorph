package ltd.newbee.mall.monomorph.id.generated.server;

import io.grpc.stub.StreamObserver;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.*;
import ltd.newbee.mall.monomorph.id.shared.server.LeaseManager;
import ltd.newbee.mall.monomorph.id.shared.server.ServerObjectManager;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.generated.helpers.ClassIdRegistry;
import ltd.newbee.mall.dao.NewBeeAdminUserTokenMapper;
import ltd.newbee.mall.entity.AdminUserToken;
import ltd.newbee.mall.monomorph.dto.generated.proto.adminusertoken.AdminUserTokenDTO;
import ltd.newbee.mall.monomorph.dto.mapper.AdminUserTokenMapper;

import java.util.Objects;
import java.util.UUID;

/**
 * gRPC Service implementation for NewBeeAdminUserTokenMapper.
 * - Handles gRPC requests for NewBeeAdminUserTokenMapper API.
 * - Registers a singleton mapper instance with the LeaseManager.
 * - Calls business methods on retrieved NewBeeAdminUserTokenMapper instances.
 */
public class NewBeeAdminUserTokenMapperImpl
        extends NewBeeAdminUserTokenMapperServiceGrpc.NewBeeAdminUserTokenMapperServiceImplBase
        implements ServerObjectManager {

    private final LeaseManager leaseManager;
    private final String serviceId;

    // Static identifier for the class type managed by this service
    public static final String CLASS_ID = ClassIdRegistry.getClassId("NewBeeAdminUserTokenMapper");

    // Singleton mapper instance; must be injected before first use.
    private static volatile NewBeeAdminUserTokenMapper mapperInstance;

    public NewBeeAdminUserTokenMapperImpl(LeaseManager leaseManager) {
        this.leaseManager = Objects.requireNonNull(leaseManager, "leaseManager");
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId(), "serviceId");
    }

    /**
     * Injects the actual singleton mapper instance. This instance will be
     * registered with the LeaseManager by {@link #createObject}.
     */
    public static void setMapperInstance(NewBeeAdminUserTokenMapper mapper) {
        mapperInstance = mapper;
    }

    private static NewBeeAdminUserTokenMapper getMapperInstance() {
        return mapperInstance;
    }

    // --- createObject gRPC Method Implementation ---

    @Override
    public void createObject(CreateObjectRequest request,
                             StreamObserver<RefactoredObjectID> responseObserver) {
        try {
            String clientId = request.getClientID();

            // ConstructorArgs is intentionally empty: the mapper is an interface with no constructor.
            NewBeeAdminUserTokenMapper mapper = getMapperInstance();
            if (mapper == null) {
                throw new IllegalStateException(
                        "No NewBeeAdminUserTokenMapper instance has been injected. " +
                        "Call NewBeeAdminUserTokenMapperImpl.setMapperInstance(...) before createObject.");
            }

            RefactoredObjectID responseProto = toID(mapper, clientId);
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    // --- selectByToken gRPC Method Implementation ---

    @Override
    public void selectByToken(SelectByTokenRequest request,
                              StreamObserver<SelectByTokenResponse> responseObserver) {
        try {
            NewBeeAdminUserTokenMapper mapper = fromID(request.getRefId());
            String token = request.getToken();

            AdminUserToken entity = mapper.selectByToken(token);
            AdminUserTokenDTO dto = (entity == null)
                    ? null
                    : AdminUserTokenMapper.INSTANCE.toDTO(entity);

            SelectByTokenResponse.Builder responseBuilder = SelectByTokenResponse.newBuilder();
            if (dto != null) {
                responseBuilder.setAdminUserToken(dto);
            }

            responseObserver.onNext(responseBuilder.build());
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

        boolean registered = leaseManager.registerInstanceAndGrantLease(
                instanceId, CLASS_ID, instance, clientId);
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
        return toID(instance, this.serviceId);
    }

    @Override
    public NewBeeAdminUserTokenMapper fromID(RefactoredObjectID id) throws Exception {
        if (id.getClassID() == null || !id.getClassID().equals(CLASS_ID)) {
            throw new IllegalArgumentException(
                    "class ID mismatch: expected " + CLASS_ID + ", got " + id.getClassID());
        }

        NewBeeAdminUserTokenMapper instance = (NewBeeAdminUserTokenMapper) leaseManager.getInstance(
                id.getInstanceID());
        if (instance == null) {
            throw new IllegalArgumentException("No instance found for ID: " + id.getInstanceID());
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
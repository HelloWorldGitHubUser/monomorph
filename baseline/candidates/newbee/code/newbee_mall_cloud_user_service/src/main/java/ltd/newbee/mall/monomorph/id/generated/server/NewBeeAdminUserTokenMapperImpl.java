package ltd.newbee.mall.monomorph.id.generated.server;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import ltd.newbee.mall.dao.NewBeeAdminUserTokenMapper;
import ltd.newbee.mall.entity.AdminUserToken;
import ltd.newbee.mall.monomorph.dto.generated.proto.adminusertoken.AdminUserTokenDTO;
import ltd.newbee.mall.monomorph.dto.generated.proto.adminusertoken.AdminUserTokenMapper;
import ltd.newbee.mall.monomorph.id.generated.helpers.ClassIdRegistry;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.*;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.shared.server.LeaseManager;
import ltd.newbee.mall.monomorph.id.shared.server.ServerObjectManager;

import java.util.Objects;
import java.util.UUID;

/**
 * gRPC Service implementation for NewBeeAdminUserTokenMapper.
 *
 * <p>This implementation manages externally created MyBatis mapper instances because
 * {@code NewBeeAdminUserTokenMapper} is an interface and cannot be instantiated directly.
 * Mapper instances are stored and retrieved through {@link LeaseManager}.</p>
 */
public class NewBeeAdminUserTokenMapperImpl
        extends NewBeeAdminUserTokenMapperServiceGrpc.NewBeeAdminUserTokenMapperServiceImplBase
        implements ServerObjectManager {

    private static final String UNSUPPORTED_CREATE_MESSAGE =
            "Cannot instantiate interface ltd.newbee.mall.dao.NewBeeAdminUserTokenMapper; "
                    + "register an externally-created MyBatis mapper instance via LeaseManager";

    private final LeaseManager leaseManager;
    private final String serviceId;

    public static final String CLASS_ID = ClassIdRegistry.getClassId("NewBeeAdminUserTokenMapper");

    public NewBeeAdminUserTokenMapperImpl(LeaseManager leaseManager) {
        this.leaseManager = Objects.requireNonNull(leaseManager, "leaseManager");
        this.serviceId = Objects.requireNonNull(ServiceRegistry.getServiceId(), "serviceId");
    }

    @Override
    public void createObject(CreateObjectRequest request, StreamObserver<RefactoredObjectID> responseObserver) {
        responseObserver.onError(Status.UNIMPLEMENTED
                .withDescription(UNSUPPORTED_CREATE_MESSAGE)
                .asRuntimeException());
    }

    @Override
    public RefactoredObjectID toID(Object instance, String clientId) throws Exception {
        if (instance == null) {
            throw new IllegalArgumentException("instance must not be null");
        }

        if (!(instance instanceof NewBeeAdminUserTokenMapper)) {
            throw new IllegalArgumentException(
                    "Unsupported instance type for class ID " + CLASS_ID + ": "
                            + instance.getClass().getName());
        }

        String effectiveClientId = (clientId == null || clientId.trim().isEmpty())
                ? serviceId
                : clientId;

        String instanceId = leaseManager.findInstanceIdForInstance(instance);
        if (instanceId == null) {
            instanceId = UUID.randomUUID().toString();
        }

        boolean registered = leaseManager.registerInstanceAndGrantLease(
                instanceId, CLASS_ID, instance, effectiveClientId);
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
    public NewBeeAdminUserTokenMapper fromID(RefactoredObjectID id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("RefactoredObjectID must not be null");
        }

        if (!CLASS_ID.equals(id.getClassID())) {
            throw new IllegalArgumentException(
                    "class ID mismatch: expected " + CLASS_ID + ", got " + id.getClassID());
        }

        if (id.getInstanceID() == null || id.getInstanceID().trim().isEmpty()) {
            throw new IllegalArgumentException("instance ID must not be null or empty");
        }

        Object instance = leaseManager.getInstance(id.getInstanceID());
        if (!(instance instanceof NewBeeAdminUserTokenMapper)) {
            throw new IllegalArgumentException(
                    "No NewBeeAdminUserTokenMapper instance found for ID: " + id.getInstanceID());
        }

        return (NewBeeAdminUserTokenMapper) instance;
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
    public void selectByToken(SelectByTokenRequest request,
                              StreamObserver<SelectByTokenResponse> responseObserver) {
        try {
            if (request == null) {
                throw new IllegalArgumentException("SelectByTokenRequest must not be null");
            }

            NewBeeAdminUserTokenMapper instance = fromID(request.getObjectId());
            String token = request.getToken();

            AdminUserToken entity = instance.selectByToken(token);
            AdminUserTokenDTO dto = null;
            if (entity != null) {
                dto = AdminUserTokenMapper.INSTANCE.toDTO(entity);
            }

            SelectByTokenResponse response;
            if (dto != null) {
                response = SelectByTokenResponse.newBuilder().setResult(dto).build();
            } else {
                response = SelectByTokenResponse.getDefaultInstance();
            }

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            sendError(responseObserver, e);
        }
    }

    private <T> void sendError(StreamObserver<T> responseObserver, Throwable t) {
        responseObserver.onError(toStatus(t).asRuntimeException());
    }

    private Status toStatus(Throwable t) {
        if (t instanceof IllegalArgumentException) {
            return Status.INVALID_ARGUMENT.withDescription(messageOf(t));
        }
        if (t instanceof IllegalStateException) {
            return Status.FAILED_PRECONDITION.withDescription(messageOf(t));
        }
        return Status.INTERNAL
                .withDescription(messageOf(t))
                .withCause(t);
    }

    private String messageOf(Throwable t) {
        return t.getMessage() == null ? t.getClass().getSimpleName() : t.getMessage();
    }
}

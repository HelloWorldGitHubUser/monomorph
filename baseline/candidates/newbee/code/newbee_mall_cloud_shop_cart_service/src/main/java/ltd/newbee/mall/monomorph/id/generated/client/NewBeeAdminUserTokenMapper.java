package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ltd.newbee.mall.monomorph.dto.generated.client.AdminUserToken;

import java.util.concurrent.TimeUnit;

/**
 * gRPC client for the NewBeeAdminUserTokenMapper service.
 *
 * This class maintains the original service method API while delegating
 * calls to a remote microservice. Only the method exposed by the
 * generated proto service is implemented.
 */
public class NewBeeAdminUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    private ManagedChannel businessChannel;
    private NewBeeAdminUserTokenMapperServiceGrpc.NewBeeAdminUserTokenMapperServiceBlockingStub businessStub;

    /**
     * Public no-arg constructor. The original class was an interface
     * with no constructor parameters, so no arguments are required.
     */
    public NewBeeAdminUserTokenMapper() {
        initialize();
    }

    /**
     * Private constructor used by the fromID factory method.
     */
    private NewBeeAdminUserTokenMapper(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        // Lazy initialization to avoid recreating the channel on every call.
        if (businessChannel != null && !businessChannel.isShutdown()) {
            return;
        }
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        businessStub = NewBeeAdminUserTokenMapperServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        performRpcSetup();

        // The target class has no constructor arguments, so ConstructorArgs is empty.
        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(ConstructorArgs.newBuilder().build())
                .build();

        return businessStub.createObject(createRequest);
    }

    @Override
    protected void performSubclassRpcCleanup() {
        if (businessChannel != null && !businessChannel.isShutdown()) {
            try {
                businessChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                if (!businessChannel.isTerminated()) {
                    businessChannel.shutdownNow();
                }
            } catch (InterruptedException e) {
                // Preserve interrupt status
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Factory method for creating a client proxy from an existing RefactoredObjectID.
     */
    public static NewBeeAdminUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeAdminUserTokenMapper(existingId);
    }

    /**
     * Exposed service method: selectByToken.
     *
     * @param token the token to search for
     * @return the corresponding AdminUserToken proxy object, or null if not found
     */
    public AdminUserToken selectByToken(String token) {
        try {
            performRpcSetup();

            SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                    .setRefId(objectId)
                    .setToken(token)
                    .build();

            SelectByTokenResponse response = businessStub.selectByToken(request);
            return AdminUserToken.fromDTO(response.getAdminUserToken());
        } catch (Exception e) {
            throw new RuntimeException("selectByToken RPC failed", e);
        }
    }
}
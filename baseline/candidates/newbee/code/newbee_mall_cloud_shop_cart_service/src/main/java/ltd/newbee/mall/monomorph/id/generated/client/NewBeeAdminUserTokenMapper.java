package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.*;
import ltd.newbee.mall.monomorph.dto.generated.client.AdminUserToken;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class NewBeeAdminUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    private ManagedChannel businessChannel;
    private NewBeeAdminUserTokenMapperServiceGrpc.NewBeeAdminUserTokenMapperServiceBlockingStub businessStub;

    /**
     * Public no-arg constructor because the original interface has no constructor arguments.
     */
    public NewBeeAdminUserTokenMapper() {
        initialize();
    }

    /**
     * Private constructor used by the fromID factory.
     */
    private NewBeeAdminUserTokenMapper(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = NewBeeAdminUserTokenMapperServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        ensureRpcSetup();

        ConstructorArgs constructorArgs = ConstructorArgs.newBuilder().build();
        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientId(clientId)
                .setConstructorArgs(constructorArgs)
                .build();

        return this.businessStub.createObject(createRequest);
    }

    @Override
    protected void performSubclassRpcCleanup() {
        if (this.businessChannel != null && !this.businessChannel.isShutdown()) {
            try {
                this.businessChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                if (!this.businessChannel.isTerminated()) {
                    this.businessChannel.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                this.businessChannel.shutdownNow();
            }
        }
    }

    /**
     * Factory method for creating a proxy from an EXISTING ID.
     */
    public static NewBeeAdminUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeAdminUserTokenMapper(existingId);
    }

    /**
     * gRPC implementation of selectByToken from the generated proto service.
     * Maps AdminUserTokenDTO to the generated client proxy.
     */
    public AdminUserToken selectByToken(String token) {
        ensureRpcSetup();

        SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                .setObjectId(this.objectId)
                .setToken(token)
                .build();

        SelectByTokenResponse response = this.businessStub.selectByToken(request);

        if (!response.hasResult()) {
            return null;
        }

        return AdminUserToken.fromDTO(response.getResult());
    }

    /**
     * Lazy initialization of the gRPC channel and stub if they are not already available.
     */
    private void ensureRpcSetup() {
        if (businessStub == null || businessChannel == null || businessChannel.isShutdown()) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize gRPC channel for NewBeeAdminUserTokenMapper", e);
            }
        }
    }
}
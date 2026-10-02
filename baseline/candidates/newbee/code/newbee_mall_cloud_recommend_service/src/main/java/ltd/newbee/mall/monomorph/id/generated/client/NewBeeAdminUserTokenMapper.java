package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.*;
import ltd.newbee.mall.monomorph.dto.generated.client.AdminUserToken;
import ltd.newbee.mall.monomorph.dto.generated.proto.adminusertoken.AdminUserTokenDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Generated gRPC client for NewBeeAdminUserTokenMapper service.
 * Implements only the methods exposed by the protobuf service definition.
 */
public class NewBeeAdminUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    private ManagedChannel businessChannel;
    private NewBeeAdminUserTokenMapperServiceGrpc.NewBeeAdminUserTokenMapperServiceBlockingStub businessStub;

    /** Public no-arg constructor. Original interface had no constructor parameters. */
    public NewBeeAdminUserTokenMapper() {
        initialize();
    }

    /** Private constructor for wrapping an existing remote object ID. */
    private NewBeeAdminUserTokenMapper(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        if (this.businessStub != null) {
            return;
        }
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder
                .forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = NewBeeAdminUserTokenMapperServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        performRpcSetup();

        ConstructorArgs constructorArgs = ConstructorArgs.newBuilder().build();
        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
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
            }
        }
        this.businessStub = null;
    }

    /** Factory method for creating a proxy from an EXISTING remote object ID. */
    public static NewBeeAdminUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeAdminUserTokenMapper(existingId);
    }

    /**
     * Implements the selectByToken RPC.
     * Maps the protobuf DTO response back to the client proxy type.
     */
    public AdminUserToken selectByToken(String token) {
        try {
            ensureRpcSetup();
            SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                    .setRefId(objectId)
                    .setToken(token)
                    .build();
            SelectByTokenResponse response = businessStub.selectByToken(request);
            AdminUserTokenDTO dto = response.getAdminUserToken();
            return AdminUserToken.fromDTO(dto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute selectByToken RPC", e);
        }
    }

    private void ensureRpcSetup() throws Exception {
        if (businessStub == null) {
            performRpcSetup();
        }
    }
}
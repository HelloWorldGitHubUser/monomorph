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

public class NewBeeAdminUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    private ManagedChannel businessChannel;
    private NewBeeAdminUserTokenMapperServiceGrpc.NewBeeAdminUserTokenMapperServiceBlockingStub businessStub;

    /** Public no-argument constructor (original interface has no constructor args). */
    public NewBeeAdminUserTokenMapper() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
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
        performRpcSetup();

        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(ConstructorArgs.newBuilder().build())
                .build();

        RefactoredObjectID createResponseProto = this.businessStub.createObject(createRequest);
        return createResponseProto;
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
    }

    /** Factory method for creating proxy from an EXISTING ID. */
    public static NewBeeAdminUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeAdminUserTokenMapper(existingId);
    }

    /**
     * Implements the {@code selectByToken} RPC defined in the proto service.
     * Converts the returned {@link AdminUserTokenDTO} to the client-side proxy
     * {@link AdminUserToken}.
     */
    public AdminUserToken selectByToken(String token) {
        try {
            performRpcSetup();
            SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                    .setObjectId(this.objectId)
                    .setToken(token)
                    .build();
            SelectByTokenResponse response = this.businessStub.selectByToken(request);
            AdminUserTokenDTO dto = response.getResult();
            return AdminUserToken.fromDTO(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error calling selectByToken", e);
        }
    }
}
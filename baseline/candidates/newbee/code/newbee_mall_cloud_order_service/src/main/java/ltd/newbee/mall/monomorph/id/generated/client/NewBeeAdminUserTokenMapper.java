package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.NewBeeAdminUserTokenMapperServiceGrpc;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.CreateObjectRequest;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.ConstructorArgs;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.SelectByTokenRequest;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.SelectByTokenResponse;
import ltd.newbee.mall.monomorph.dto.generated.client.AdminUserToken;
import ltd.newbee.mall.monomorph.dto.generated.proto.adminusertoken.AdminUserTokenDTO;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class NewBeeAdminUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    private ManagedChannel businessChannel;
    private NewBeeAdminUserTokenMapperServiceGrpc.NewBeeAdminUserTokenMapperServiceBlockingStub businessStub;

    /**
     * Public constructor for a new object. Calls the parent initialization,
     * which performs remote creation and obtains the object ID.
     */
    public NewBeeAdminUserTokenMapper() {
        try {
            initialize();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize NewBeeAdminUserTokenMapper client", e);
        }
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
                this.businessChannel.shutdownNow();
            }
        }
    }

    /**
     * Factory method for creating a proxy from an existing ID.
     */
    public static NewBeeAdminUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeAdminUserTokenMapper(existingId);
    }

    /**
     * Implements the selectByToken RPC method from the generated proto service.
     *
     * @param token the token string to search for
     * @return the corresponding AdminUserToken proxy object, or {@code null} if not found
     */
    public AdminUserToken selectByToken(String token) {
        try {
            if (businessStub == null) {
                performRpcSetup();
            }

            SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                    .setRefId(this.objectId)   // objectId is inherited from AbstractRefactoredClient
                    .setToken(token)
                    .build();

            SelectByTokenResponse response = businessStub.selectByToken(request);
            AdminUserTokenDTO dto = response.getAdminUserToken();
            if (dto == null) {
                return null;
            }
            return AdminUserToken.fromDTO(dto);
        } catch (Exception e) {
            throw new RuntimeException("Error in selectByToken", e);
        }
    }
}
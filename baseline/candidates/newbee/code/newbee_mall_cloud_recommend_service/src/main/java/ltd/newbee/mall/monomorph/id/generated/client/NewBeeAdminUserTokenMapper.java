package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;

// gRPC imports
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

// DTO client proxy import
import ltd.newbee.mall.monomorph.dto.generated.client.AdminUserToken;

import java.util.concurrent.TimeUnit;

public class NewBeeAdminUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    // --- gRPC Specific Fields ---
    private ManagedChannel businessChannel;
    private NewBeeAdminUserTokenMapperServiceGrpc.NewBeeAdminUserTokenMapperServiceBlockingStub businessStub;

    /** Public no-arg constructor (original interface had no explicit constructor). */
    public NewBeeAdminUserTokenMapper() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
    private NewBeeAdminUserTokenMapper(RefactoredObjectID existingId) {
        super(existingId);
    }

    // --- Implementation of Abstract Methods ---

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
                .setConstructorArgs(ConstructorArgs.getDefaultInstance())
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
                // Preserve interrupt status
                Thread.currentThread().interrupt();
            }
        }
    }

    /** Factory method for creating proxy from an EXISTING ID. */
    public static NewBeeAdminUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeAdminUserTokenMapper(existingId);
    }

    // --- Service Methods ---

    public AdminUserToken selectByToken(String token) {
        performRpcSetup();

        SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                .setObjectId(this.objectId)
                .setToken(token)
                .build();

        SelectByTokenResponse response;
        try {
            response = this.businessStub.selectByToken(request);
        } catch (Exception e) {
            throw new RuntimeException("Error calling selectByToken", e);
        }

        return AdminUserToken.fromDTO(response.getResult());
    }
}
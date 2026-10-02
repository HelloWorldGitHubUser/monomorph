package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallusertokenmapper.*;
import ltd.newbee.mall.monomorph.dto.generated.client.MallUserToken;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class NewBeeMallUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    private ManagedChannel businessChannel;
    private NewBeeMallUserTokenMapperServiceGrpc.NewBeeMallUserTokenMapperServiceBlockingStub businessStub;

    /** Public no-arg constructor for normal client creation. */
    public NewBeeMallUserTokenMapper() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
    private NewBeeMallUserTokenMapper(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder
                .forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = NewBeeMallUserTokenMapperServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        ensureBusinessStub();

        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(ConstructorArgs.newBuilder().build())
                .build();

        return this.businessStub.createObject(createRequest);
    }

    @Override
    protected void performSubclassRpcCleanup() {
        if (this.businessChannel != null) {
            if (!this.businessChannel.isShutdown()) {
                try {
                    this.businessChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                    if (!this.businessChannel.isTerminated()) {
                        this.businessChannel.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            this.businessChannel = null;
            this.businessStub = null;
        }
    }

    public static NewBeeMallUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeMallUserTokenMapper(existingId);
    }

    /**
     * Exposed service method matching the original selectByToken signature,
     * converted to use the available proxy DTO.
     */
    public MallUserToken selectByToken(String token) {
        ensureBusinessStub();

        SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                .setRefactoredObjectId(this.objectId)
                .setToken(token)
                .build();

        SelectByTokenResponse response = this.businessStub.selectByToken(request);

        if (response == null || !response.hasMallUserToken()) {
            return null;
        }

        return MallUserToken.fromDTO(response.getMallUserToken());
    }

    private void ensureBusinessStub() {
        if (this.businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize gRPC stub for " + TARGET_SERVICE_ID, e);
            }
        }
    }
}

package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.dto.generated.client.MallUserToken;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallusertokenmapper.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class NewBeeMallUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    private ManagedChannel businessChannel;
    private NewBeeMallUserTokenMapperServiceGrpc.NewBeeMallUserTokenMapperServiceBlockingStub businessStub;

    /** Public no-arg constructor: original mapper interface had no constructor arguments. */
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
        performRpcSetup();

        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(ConstructorArgs.newBuilder().build())
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
    }

    public static NewBeeMallUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeMallUserTokenMapper(existingId);
    }

    /** Implements the exposed RPC method selectByToken. */
    public MallUserToken selectByToken(String token) {
        try {
            if (this.businessStub == null) {
                performRpcSetup();
            }

            SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                    .setRefactoredObjectID(this.objectId)
                    .setToken(token)
                    .build();

            SelectByTokenResponse response = this.businessStub.selectByToken(request);

            if (response.hasResult()) {
                return MallUserToken.fromDTO(response.getResult());
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke selectByToken", e);
        }
    }
}
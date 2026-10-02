package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;

import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallusertokenmapper.*;
import ltd.newbee.mall.monomorph.dto.generated.client.MallUserToken;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusertoken.MallUserTokenDTO;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public class NewBeeMallUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    private ManagedChannel businessChannel;
    private NewBeeMallUserTokenMapperServiceGrpc.NewBeeMallUserTokenMapperServiceBlockingStub businessStub;

    /** Constructor for new instances. */
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
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort()).usePlaintext().build();
        this.businessStub = NewBeeMallUserTokenMapperServiceGrpc.newBlockingStub(businessChannel);
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        ensureRpcSetup();

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
            } catch (InterruptedException e) {
                this.businessChannel.shutdownNow();
                Thread.currentThread().interrupt();
                return;
            }
            if (!this.businessChannel.isTerminated()) {
                this.businessChannel.shutdownNow();
            }
        }
    }

    /** Factory method for creating a proxy from an existing ID. */
    public static NewBeeMallUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeMallUserTokenMapper(existingId);
    }

    public MallUserToken selectByToken(String token) {
        try {
            ensureRpcSetup();

            SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                    .setRefactoredObjectID(this.objectId)
                    .setToken(token == null ? "" : token)
                    .build();

            SelectByTokenResponse response = this.businessStub.selectByToken(request);
            MallUserTokenDTO resultDTO = response.getResult();
            return MallUserToken.fromDTO(resultDTO);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke selectByToken over gRPC", e);
        }
    }

    private void ensureRpcSetup() throws Exception {
        if (this.businessStub == null) {
            performRpcSetup();
        }
    }
}

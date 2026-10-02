package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;

import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallusertokenmapper.CreateObjectRequest;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallusertokenmapper.ConstructorArgs;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallusertokenmapper.NewBeeMallUserTokenMapperServiceGrpc;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallusertokenmapper.SelectByTokenRequest;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeemallusertokenmapper.SelectByTokenResponse;

import ltd.newbee.mall.monomorph.dto.generated.proto.mallusertoken.MallUserTokenDTO;
import ltd.newbee.mall.monomorph.dto.generated.client.MallUserToken;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * gRPC client for NewBeeMallUserTokenMapper.
 * Implements only the RPC methods defined in the generated proto service.
 */
public class NewBeeMallUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    private ManagedChannel businessChannel;
    private NewBeeMallUserTokenMapperServiceGrpc.NewBeeMallUserTokenMapperServiceBlockingStub businessStub;

    /** No-arg constructor for creating a new object. */
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

    /** Factory method for creating a proxy from an existing RefactoredObjectID. */
    public static NewBeeMallUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeMallUserTokenMapper(existingId);
    }

    /**
     * Implements the RPC method selectByToken from the generated proto service.
     * Original return type ltd.newbee.mall.entity.MallUserToken is replaced by the
     * DTO client proxy ltd.newbee.mall.monomorph.dto.generated.client.MallUserToken.
     */
    public MallUserToken selectByToken(String token) {
        try {
            performRpcSetup();
            SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId)
                    .setToken(token)
                    .build();
            SelectByTokenResponse response = this.businessStub.selectByToken(request);
            if (response.hasMallUserToken()) {
                return MallUserToken.fromDTO(response.getMallUserToken());
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("selectByToken RPC failed", e);
        } finally {
            performSubclassRpcCleanup();
        }
    }
}

package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;

// gRPC imports
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

public class NewBeeMallUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    private ManagedChannel businessChannel;
    private NewBeeMallUserTokenMapperServiceGrpc.NewBeeMallUserTokenMapperServiceBlockingStub businessStub;

    /** Constructor. */
    public NewBeeMallUserTokenMapper() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
    private NewBeeMallUserTokenMapper(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        if (businessStub != null) {
            return; // already set up
        }
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort()).usePlaintext().build();
        this.businessStub = NewBeeMallUserTokenMapperServiceGrpc.newBlockingStub(businessChannel);
    }

    private void ensureRpcSetup() throws Exception {
        if (businessStub == null) {
            performRpcSetup();
        }
    }

    @Override
    protected RefactoredObjectID performRemoteCreateAndGetId(String clientId, Object... args) throws Exception {
        ensureRpcSetup();

        // Build constructor args from the provided arguments if necessary.
        // For this mapper, the default constructor has no arguments, so we pass an empty ConstructorArgs.
        ConstructorArgs constructorArgs = ConstructorArgs.newBuilder().build();

        CreateObjectRequest createRequest = CreateObjectRequest.newBuilder()
                .setClientID(clientId)
                .setConstructorArgs(constructorArgs)
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
                // ignore
            } finally {
                this.businessChannel = null;
                this.businessStub = null;
            }
        }
    }

    /** Factory method for creating proxy from an EXISTING ID. */
    public static NewBeeMallUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeMallUserTokenMapper(existingId);
    }

    // --- Start of the implementation of the rest of the Service Methods ---

    public MallUserToken selectByToken(String token) {
        try {
            ensureRpcSetup();
            SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                    .setRefactoredObjectId(this.objectId)
                    .setToken(token)
                    .build();
            SelectByTokenResponse response = businessStub.selectByToken(request);
            MallUserTokenDTO dto = response.getMallUserToken();
            return MallUserToken.fromDTO(dto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to call selectByToken", e);
        }
    }

    // --- End of the implementation of the rest of the Service Methods ---
}
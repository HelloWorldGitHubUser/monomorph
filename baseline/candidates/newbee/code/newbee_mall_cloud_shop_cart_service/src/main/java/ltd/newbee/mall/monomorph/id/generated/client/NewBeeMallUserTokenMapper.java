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

    private final Object rpcSetupLock = new Object();

    private volatile ManagedChannel businessChannel;
    private volatile NewBeeMallUserTokenMapperServiceGrpc.NewBeeMallUserTokenMapperServiceBlockingStub businessStub;

    public NewBeeMallUserTokenMapper() {
        initialize();
    }

    private NewBeeMallUserTokenMapper(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        if (isRpcReady()) {
            return;
        }
        synchronized (rpcSetupLock) {
            if (isRpcReady()) {
                return;
            }
            ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
            ManagedChannel channel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                    .usePlaintext()
                    .build();
            this.businessChannel = channel;
            this.businessStub = NewBeeMallUserTokenMapperServiceGrpc.newBlockingStub(channel);
        }
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
        ManagedChannel channelToClose;
        synchronized (rpcSetupLock) {
            channelToClose = this.businessChannel;
            this.businessChannel = null;
            this.businessStub = null;
        }
        if (channelToClose == null) {
            return;
        }
        channelToClose.shutdown();
        try {
            if (!channelToClose.awaitTermination(5, TimeUnit.SECONDS)) {
                channelToClose.shutdownNow();
            }
        } catch (InterruptedException e) {
            channelToClose.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static NewBeeMallUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeMallUserTokenMapper(existingId);
    }

    public MallUserToken selectByToken(String token) {
        ensureRpcSetup();
        SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                .setRefactoredObjectID(this.objectId)
                .setToken(token)
                .build();
        SelectByTokenResponse response = this.businessStub.selectByToken(request);
        return MallUserToken.fromDTO(response.getResult());
    }

    private boolean isRpcReady() {
        return this.businessStub != null
                && this.businessChannel != null
                && !this.businessChannel.isShutdown();
    }

    private void ensureRpcSetup() {
        if (!isRpcReady()) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to set up gRPC channel", e);
            }
        }
    }
}

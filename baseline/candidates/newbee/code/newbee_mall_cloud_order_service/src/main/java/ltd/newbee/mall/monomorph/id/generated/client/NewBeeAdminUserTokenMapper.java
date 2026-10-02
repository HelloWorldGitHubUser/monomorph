package ltd.newbee.mall.monomorph.id.generated.client;

import ltd.newbee.mall.monomorph.id.shared.client.AbstractRefactoredClient;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;
import ltd.newbee.mall.monomorph.id.shared.RefactoredObjectID;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.CreateObjectRequest;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.ConstructorArgs;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.SelectByTokenRequest;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.SelectByTokenResponse;
import ltd.newbee.mall.monomorph.id.generated.proto.newbeeadminusertokenmapper.NewBeeAdminUserTokenMapperServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ltd.newbee.mall.monomorph.dto.generated.client.AdminUserToken;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class NewBeeAdminUserTokenMapper extends AbstractRefactoredClient {

    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    private final Object channelLock = new Object();
    private ManagedChannel businessChannel;
    private NewBeeAdminUserTokenMapperServiceGrpc.NewBeeAdminUserTokenMapperServiceBlockingStub businessStub;

    /** Constructor matching original no-arg constructor. */
    public NewBeeAdminUserTokenMapper() {
        initialize();
    }

    /** Private constructor used by the fromID factory. */
    private NewBeeAdminUserTokenMapper(RefactoredObjectID existingId) {
        super(existingId);
    }

    @Override
    protected void performRpcSetup() throws Exception {
        synchronized (channelLock) {
            if (this.businessChannel != null) {
                if (!this.businessChannel.isShutdown()) {
                    return;
                }
            }
            ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
            this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                    .usePlaintext()
                    .build();
            this.businessStub = NewBeeAdminUserTokenMapperServiceGrpc.newBlockingStub(businessChannel);
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
        synchronized (channelLock) {
            if (this.businessChannel == null) {
                return;
            }
            if (this.businessChannel.isShutdown()) {
                return;
            }
            try {
                this.businessChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                if (!this.businessChannel.isTerminated()) {
                    this.businessChannel.shutdownNow();
                }
            } catch (InterruptedException e) {
                this.businessChannel.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    public static NewBeeAdminUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeAdminUserTokenMapper(existingId);
    }

    public AdminUserToken selectByToken(String token) {
        Objects.requireNonNull(this.objectId, "objectId is not initialized; use the no-arg constructor or fromID");
        try {
            performRpcSetup();
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up gRPC channel", e);
        }

        SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                .setObjectId(this.objectId)
                .setToken(token)
                .build();

        SelectByTokenResponse response = this.businessStub.selectByToken(request);

        return AdminUserToken.fromDTO(response.getResult());
    }
}

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

    /**
     * Public no-arg constructor.
     * The original NewBeeMallUserTokenMapper is an interface and has no constructor arguments,
     * so the proxy class exposes a parameterless constructor.
     */
    public NewBeeMallUserTokenMapper() {
        initialize();
    }

    /**
     * Private constructor used by the {@link #fromID(RefactoredObjectID)} factory.
     */
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

    /**
     * Factory method for creating a proxy from an EXISTING ID.
     */
    public static NewBeeMallUserTokenMapper fromID(RefactoredObjectID existingId) {
        return new NewBeeMallUserTokenMapper(existingId);
    }

    // --- Start of the implementation of the rest of the Service Methods ---

    /**
     * Implements the generated proto RPC {@code selectByToken}.
     *
     * @param token the token string
     * @return the corresponding {@link MallUserToken} client proxy, or {@code null} if the DTO is absent
     */
    public MallUserToken selectByToken(String token) {
        if (this.businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Unable to set up gRPC channel for service " + TARGET_SERVICE_ID, e);
            }
        }

        SelectByTokenRequest request = SelectByTokenRequest.newBuilder()
                .setRefactoredObjectID(this.objectId)
                .setToken(token)
                .build();

        SelectByTokenResponse response = this.businessStub.selectByToken(request);
        MallUserTokenDTO dto = response.getResult();
        if (dto == null) {
            return null;
        }
        return MallUserToken.fromDTO(dto);
    }

    // --- End of the implementation of the rest of the Service Methods ---
}
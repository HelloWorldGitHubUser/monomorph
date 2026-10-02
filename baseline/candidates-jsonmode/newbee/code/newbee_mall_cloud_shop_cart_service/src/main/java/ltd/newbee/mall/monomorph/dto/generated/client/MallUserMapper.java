package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.mallusermapper.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;

import java.util.concurrent.TimeUnit;

/**
 * Auto-generated DTO gRPC client for {@link MallUserMapperDTO} and related protobuf types.
 */
public class MallUserMapperClient {
    private MallUserMapperDTO dtoInstance;

    public MallUserMapperClient(MallUserMapperDTO dtoInstance) {
        this.dtoInstance = dtoInstance == null
                ? MallUserMapperDTO.newBuilder().build()
                : dtoInstance;
    }

    // Mapping methods
    public MallUserMapperDTO toDTO() {
        return this.dtoInstance;
    }

    public static MallUserMapperClient fromDTO(MallUserMapperDTO dtoInstance) {
        return new MallUserMapperClient(dtoInstance);
    }

    // TARGET_SERVICE_ID is the unique ID for the user service, provided by the tool
    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    // --- gRPC Specific Fields ---
    private ManagedChannel businessChannel;
    private MallUserMapperServiceGrpc.MallUserMapperServiceBlockingStub businessStub;

    // Helper methods for gRPC
    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort())
                .usePlaintext()
                .build();
        this.businessStub = MallUserMapperServiceGrpc.newBlockingStub(businessChannel);
    }

    protected void performRpcCleanup() {
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
        this.businessChannel = null;
        this.businessStub = null;
    }

    /**
     * Closes the underlying gRPC channel and releases resources.
     * After calling this method, the client can be reused; the channel will be re-established on the next RPC.
     */
    public void close() {
        performRpcCleanup();
    }

    // --- START OF gRPC METHOD IMPLEMENTATIONS ---

    public MallUser selectByPrimaryKey(Long userId) {
        // Re-initialize if stub is null or the channel has been shut down externally
        if (businessStub == null || (businessChannel != null && businessChannel.isShutdown())) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize gRPC stub", e);
            }
        }

        SelectByPrimaryKeyRequest request = SelectByPrimaryKeyRequest.newBuilder()
                .setDto(this.dtoInstance)
                .setUserId(userId)
                .build();

        SelectByPrimaryKeyResponse response = businessStub.selectByPrimaryKey(request);

        if (response.hasMallUser()) {
            return MallUser.fromDTO(response.getMallUser());
        }
        return null;
    }

    // --- END OF gRPC METHOD IMPLEMENTATIONS ---

    // --- START OF DTO GETTERS AND SETTERS ---
    public long getUserId() {
        return dtoInstance.getUserId();
    }

    public void setUserId(long userId) {
        dtoInstance = dtoInstance.toBuilder().setUserId(userId).build();
    }

    public String getLoginName() {
        return dtoInstance.getLoginName();
    }

    public void setLoginName(String loginName) {
        dtoInstance = dtoInstance.toBuilder().setLoginName(loginName).build();
    }

    public String getNickName() {
        return dtoInstance.getNickName();
    }

    public void setNickName(String nickName) {
        dtoInstance = dtoInstance.toBuilder().setNickName(nickName).build();
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}
package ltd.newbee.mall.monomorph.dto.generated.client;

// gRPC imports
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusermapper.*;
import ltd.newbee.mall.monomorph.dto.generated.proto.malluser.MallUserDTO;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ltd.newbee.mall.monomorph.id.generated.helpers.ServiceRegistry;

import java.util.concurrent.TimeUnit;

/**
 * Auto-generated DTO gRPC client
 * {@link MallUserMapper} and {@link MallUserMapperDTO}.
 */
public class MallUserMapper {
    private MallUserMapperDTO dtoInstance;

    // Private constructor used by fromDTO and toDTO
    private MallUserMapper(MallUserMapperDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    // Public no-arg constructor for convenient instantiation
    public MallUserMapper() {
        this.dtoInstance = MallUserMapperDTO.newBuilder().build();
    }

    // mapping methods
    public MallUserMapperDTO toDTO() {
        return this.dtoInstance;
    }

    public static MallUserMapper fromDTO(MallUserMapperDTO dtoInstance) {
        return new MallUserMapper(dtoInstance);
    }

    // TARGET_SERVICE_ID is the unique ID for the MallUserMapper service
    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    // --- gRPC Specific Fields ---
    private ManagedChannel businessChannel;
    private MallUserMapperServiceGrpc.MallUserMapperServiceBlockingStub businessStub;

    protected void performRpcSetup() throws Exception {
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort()).usePlaintext().build();
        this.businessStub = MallUserMapperServiceGrpc.newBlockingStub(businessChannel);
    }

    protected void performSubclassRpcCleanup() {
        if (this.businessChannel != null && !this.businessChannel.isShutdown()) {
            try {
                this.businessChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                if (!this.businessChannel.isTerminated()) {
                    this.businessChannel.shutdownNow();
                }
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    // --- gRPC METHOD IMPLEMENTATIONS ---

    public MallUser selectByPrimaryKey(Long userId) {
        try {
            if (businessStub == null) {
                performRpcSetup();
            }
            SelectByPrimaryKeyRequest request = SelectByPrimaryKeyRequest.newBuilder()
                    .setDto(this.dtoInstance)
                    .setUserId(userId)
                    .build();
            SelectByPrimaryKeyResponse response = businessStub.selectByPrimaryKey(request);
            MallUserDTO mallUserDTO = response.getMallUser();
            return MallUser.fromDTO(mallUserDTO);
        } catch (Exception e) {
            throw new RuntimeException("selectByPrimaryKey RPC failed", e);
        }
    }

    // --- END OF gRPC METHOD IMPLEMENTATIONS ---

    // --- DTO GETTERS AND SETTERS ---

    public Long getUserId() {
        return dtoInstance.getUserId();
    }

    public void setUserId(Long userId) {
        dtoInstance = dtoInstance.toBuilder()
                .setUserId(userId == null ? 0L : userId)
                .build();
    }

    public String getLoginName() {
        return dtoInstance.getLoginName();
    }

    public void setLoginName(String loginName) {
        dtoInstance = dtoInstance.toBuilder()
                .setLoginName(loginName == null ? "" : loginName)
                .build();
    }

    public String getNickName() {
        return dtoInstance.getNickName();
    }

    public void setNickName(String nickName) {
        dtoInstance = dtoInstance.toBuilder()
                .setNickName(nickName == null ? "" : nickName)
                .build();
    }

    // --- END OF DTO GETTERS AND SETTERS ---
}
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

    public MallUserMapper(MallUserMapperDTO dtoInstance) {
        // dtoConstructor to initialize from a DTO instance
        this.dtoInstance = dtoInstance;
    }

    // add any additional constructors if needed
    public MallUserMapper() {
        this(MallUserMapperDTO.newBuilder().build());
    }

    // mapping methods
    public MallUserMapperDTO toDTO() {
        return this.dtoInstance;
    }

    public static MallUserMapper fromDTO(MallUserMapperDTO dtoInstance) {
        MallUserMapper instance = new MallUserMapper(dtoInstance);
        return instance;
    }

    // implementation of the gRPC exposed methods
    
    // TARGET_SERVICE_ID is the unique ID for the ClassA service, provided by the tool
    private static final String TARGET_SERVICE_ID = "newbee_mall_cloud_user_service";

    // --- gRPC Specific Fields ---
    private ManagedChannel businessChannel; // Channel for RPC calls
    private MallUserMapperServiceGrpc.MallUserMapperServiceBlockingStub businessStub; // Use this stub for RPC calls
    
    // Helper methods for gRPC
    protected void performRpcSetup() throws Exception {
        // Use the static TARGET_SERVICE_ID to find the endpoint for business logic calls
        ServiceRegistry.ServiceEndpoint endpoint = ServiceRegistry.getEndpoint(TARGET_SERVICE_ID);
        this.businessChannel = ManagedChannelBuilder.forAddress(endpoint.getHost(), endpoint.getPort()).usePlaintext().build();
        this.businessStub = MallUserMapperService.newBlockingStub(businessChannel);
    }
    
    protected void performSubclassRpcCleanup() {
        // ... shutdown logic for businessChannel ...
         if (this.businessChannel != null && !this.businessChannel.isShutdown()) {
             try {
                 this.businessChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
                  if (!this.businessChannel.isTerminated()) { this.businessChannel.shutdownNow(); }
             } catch (InterruptedException e) {  }
         }
    }
    
    // Implement required methods for gRPC calls here
    // --- START OF gRPC METHOD IMPLEMENTATIONS ---

    public MallUser selectByPrimaryKey(Long userId) {
        if (businessStub == null) {
            try {
                performRpcSetup();
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize gRPC stub", e);
            }
        }
        SelectByPrimaryKeyRequest request = SelectByPrimaryKeyRequest.newBuilder()
                .setDto(dtoInstance)
                .setUserId(userId)
                .build();
        SelectByPrimaryKeyResponse response = businessStub.selectByPrimaryKey(request);
        return MallUser.fromDTO(response.getMallUser());
    }

    // --- END OF gRPC METHOD IMPLEMENTATIONS ---

    

    // Implement all other getters and setters corresponding to the DTO fields
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

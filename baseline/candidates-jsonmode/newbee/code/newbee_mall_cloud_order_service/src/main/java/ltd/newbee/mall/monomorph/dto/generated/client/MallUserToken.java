package ltd.newbee.mall.monomorph.dto.generated.client;

// gRPC imports
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusertoken.*;

import com.google.protobuf.util.Timestamps;

import java.util.Date;

/**
 * Auto-generated DTO gRPC client
 * {@link MallUserToken} and {@link MallUserTokenDTO}.
 */
public class MallUserToken {
    private MallUserTokenDTO dtoInstance;

    public MallUserToken() {
        this.dtoInstance = MallUserTokenDTO.getDefaultInstance();
    }

    public MallUserToken(MallUserTokenDTO dtoInstance) {
        this.dtoInstance = (dtoInstance != null) ? dtoInstance : MallUserTokenDTO.getDefaultInstance();
    }

    // mapping methods
    public MallUserTokenDTO toDTO() {
        return this.dtoInstance;
    }

    public static MallUserToken fromDTO(MallUserTokenDTO dtoInstance) {
        return new MallUserToken(dtoInstance);
    }

    // implementation of the gRPC exposed methods
    // No RPC methods are defined for MallUserTokenDTO, so no stub is required.

    // --- START OF DTO GETTERS AND SETTERS ---
    public Long getUserId() {
        return dtoInstance.getUserId();
    }

    public void setUserId(Long userId) {
        long value = (userId != null) ? userId : 0L;
        dtoInstance = dtoInstance.toBuilder().setUserId(value).build();
    }

    public String getToken() {
        return dtoInstance.getToken();
    }

    public void setToken(String token) {
        String value = (token != null) ? token : "";
        dtoInstance = dtoInstance.toBuilder().setToken(value).build();
    }

    public Date getUpdateTime() {
        return dtoInstance.hasUpdateTime() ? Timestamps.toDate(dtoInstance.getUpdateTime()) : null;
    }

    public void setUpdateTime(Date updateTime) {
        if (updateTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearUpdateTime().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setUpdateTime(Timestamps.fromDate(updateTime)).build();
        }
    }

    public Date getExpireTime() {
        return dtoInstance.hasExpireTime() ? Timestamps.toDate(dtoInstance.getExpireTime()) : null;
    }

    public void setExpireTime(Date expireTime) {
        if (expireTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearExpireTime().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setExpireTime(Timestamps.fromDate(expireTime)).build();
        }
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}
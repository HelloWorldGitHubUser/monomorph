package ltd.newbee.mall.monomorph.dto.generated.client;

// gRPC imports
import ltd.newbee.mall.monomorph.dto.generated.proto.adminusertoken.AdminUserTokenDTO;

import com.google.protobuf.Timestamp;

import java.util.Date;

/**
 * Auto-generated DTO gRPC client
 * {@link AdminUserToken} and {@link AdminUserTokenDTO}.
 */
public class AdminUserToken {
    private AdminUserTokenDTO dtoInstance;

    public AdminUserToken(AdminUserTokenDTO dtoInstance) {
        // dtoConstructor to initialize from a DTO instance
        this.dtoInstance = dtoInstance;
    }

    public AdminUserToken() {
        this(AdminUserTokenDTO.newBuilder().build());
    }

    // mapping methods
    public AdminUserTokenDTO toDTO() {
        return this.dtoInstance;
    }

    public static AdminUserToken fromDTO(AdminUserTokenDTO dtoInstance) {
        AdminUserToken instance = new AdminUserToken(dtoInstance);
        return instance;
    }

    // --- START OF DTO GETTERS AND SETTERS ---
    public Long getAdminUserId() {
        return dtoInstance.getAdminUserId();
    }

    public void setAdminUserId(Long adminUserId) {
        if (adminUserId == null) {
            dtoInstance = dtoInstance.toBuilder().clearAdminUserId().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setAdminUserId(adminUserId).build();
        }
    }

    public String getToken() {
        return dtoInstance.getToken();
    }

    public void setToken(String token) {
        if (token == null) {
            dtoInstance = dtoInstance.toBuilder().clearToken().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setToken(token).build();
        }
    }

    public Date getUpdateTime() {
        if (!dtoInstance.hasUpdateTime()) {
            return null;
        }
        Timestamp ts = dtoInstance.getUpdateTime();
        return new Date(ts.getSeconds() * 1000 + ts.getNanos() / 1_000_000);
    }

    public void setUpdateTime(Date updateTime) {
        if (updateTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearUpdateTime().build();
        } else {
            long millis = updateTime.getTime();
            Timestamp ts = Timestamp.newBuilder()
                    .setSeconds(millis / 1000)
                    .setNanos((int) ((millis % 1000) * 1_000_000))
                    .build();
            dtoInstance = dtoInstance.toBuilder().setUpdateTime(ts).build();
        }
    }

    public Date getExpireTime() {
        if (!dtoInstance.hasExpireTime()) {
            return null;
        }
        Timestamp ts = dtoInstance.getExpireTime();
        return new Date(ts.getSeconds() * 1000 + ts.getNanos() / 1_000_000);
    }

    public void setExpireTime(Date expireTime) {
        if (expireTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearExpireTime().build();
        } else {
            long millis = expireTime.getTime();
            Timestamp ts = Timestamp.newBuilder()
                    .setSeconds(millis / 1000)
                    .setNanos((int) ((millis % 1000) * 1_000_000))
                    .build();
            dtoInstance = dtoInstance.toBuilder().setExpireTime(ts).build();
        }
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}

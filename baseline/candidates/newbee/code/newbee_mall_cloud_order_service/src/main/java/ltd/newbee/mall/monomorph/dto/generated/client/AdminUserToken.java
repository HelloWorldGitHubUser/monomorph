package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.adminusertoken.*;
import com.google.protobuf.Timestamp;

import java.util.Date;

/**
 * Auto-generated DTO gRPC client
 * {@link AdminUserToken} and {@link AdminUserTokenDTO}.
 */
public class AdminUserToken {
    private AdminUserTokenDTO dtoInstance;

    public AdminUserToken() {
        this.dtoInstance = AdminUserTokenDTO.newBuilder().build();
    }

    private AdminUserToken(AdminUserTokenDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public AdminUserTokenDTO toDTO() {
        return this.dtoInstance;
    }

    public static AdminUserToken fromDTO(AdminUserTokenDTO dtoInstance) {
        return new AdminUserToken(dtoInstance);
    }

    // --- START OF DTO GETTERS AND SETTERS ---
    public Long getAdminUserId() {
        return dtoInstance.getAdminUserId();
    }

    public void setAdminUserId(Long adminUserId) {
        long value = adminUserId == null ? 0L : adminUserId;
        dtoInstance = dtoInstance.toBuilder().setAdminUserId(value).build();
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
        return fromTimestamp(dtoInstance.getUpdateTime());
    }

    public void setUpdateTime(Date updateTime) {
        if (updateTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearUpdateTime().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setUpdateTime(toTimestamp(updateTime)).build();
        }
    }

    public Date getExpireTime() {
        if (!dtoInstance.hasExpireTime()) {
            return null;
        }
        return fromTimestamp(dtoInstance.getExpireTime());
    }

    public void setExpireTime(Date expireTime) {
        if (expireTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearExpireTime().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setExpireTime(toTimestamp(expireTime)).build();
        }
    }
    // --- END OF DTO GETTERS AND SETTERS ---

    private static Timestamp toTimestamp(Date date) {
        long millis = date.getTime();
        long seconds = millis / 1000;
        int nanos = (int) ((millis % 1000) * 1_000_000);
        if (nanos < 0) {
            nanos += 1_000_000_000;
            seconds--;
        }
        return Timestamp.newBuilder()
                .setSeconds(seconds)
                .setNanos(nanos)
                .build();
    }

    private static Date fromTimestamp(Timestamp timestamp) {
        long millis = timestamp.getSeconds() * 1000 + timestamp.getNanos() / 1_000_000;
        return new Date(millis);
    }
}

package ltd.newbee.mall.monomorph.dto.generated.client;

// gRPC imports
import ltd.newbee.mall.monomorph.dto.generated.proto.adminusertoken.*;

import com.google.protobuf.Timestamp;
import java.util.Date;

/**
 * Auto-generated DTO gRPC client
 * {@link AdminUserToken} and {@link AdminUserTokenDTO}.
 */
public class AdminUserToken {
    private AdminUserTokenDTO dtoInstance;

    /**
     * No-argument constructor matching the original class.
     * Initializes an empty DTO.
     */
    public AdminUserToken() {
        this.dtoInstance = AdminUserTokenDTO.newBuilder().build();
    }

    /**
     * Constructor from a DTO instance.
     */
    public AdminUserToken(AdminUserTokenDTO dtoInstance) {
        this.dtoInstance = dtoInstance != null ? dtoInstance : AdminUserTokenDTO.getDefaultInstance();
    }

    // mapping methods
    public AdminUserTokenDTO toDTO() {
        return this.dtoInstance;
    }

    public static AdminUserToken fromDTO(AdminUserTokenDTO dtoInstance) {
        return new AdminUserToken(dtoInstance);
    }

    // implementation of the gRPC exposed methods
    // (none, original class has no methods)

    // --- START OF DTO GETTERS AND SETTERS ---
    public Long getAdminUserId() {
        return Long.valueOf(dtoInstance.getAdminUserId());
    }

    public void setAdminUserId(Long adminUserId) {
        long value = adminUserId != null ? adminUserId.longValue() : 0L;
        dtoInstance = dtoInstance.toBuilder().setAdminUserId(value).build();
    }

    public String getToken() {
        return dtoInstance.getToken();
    }

    public void setToken(String token) {
        String value = token != null ? token : "";
        dtoInstance = dtoInstance.toBuilder().setToken(value).build();
    }

    public Date getUpdateTime() {
        if (dtoInstance.hasUpdateTime()) {
            return toDate(dtoInstance.getUpdateTime());
        }
        return null;
    }

    public void setUpdateTime(Date updateTime) {
        Timestamp timestamp = toTimestamp(updateTime);
        if (timestamp != null) {
            dtoInstance = dtoInstance.toBuilder().setUpdateTime(timestamp).build();
        } else {
            dtoInstance = dtoInstance.toBuilder().clearUpdateTime().build();
        }
    }

    public Date getExpireTime() {
        if (dtoInstance.hasExpireTime()) {
            return toDate(dtoInstance.getExpireTime());
        }
        return null;
    }

    public void setExpireTime(Date expireTime) {
        Timestamp timestamp = toTimestamp(expireTime);
        if (timestamp != null) {
            dtoInstance = dtoInstance.toBuilder().setExpireTime(timestamp).build();
        } else {
            dtoInstance = dtoInstance.toBuilder().clearExpireTime().build();
        }
    }
    // --- END OF DTO GETTERS AND SETTERS ---

    private static Timestamp toTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        long millis = date.getTime();
        long seconds = millis / 1000;
        int nanos = (int) (millis % 1000 * 1_000_000);
        if (nanos < 0) {
            nanos += 1_000_000_000;
            seconds--;
        }
        return Timestamp.newBuilder()
                .setSeconds(seconds)
                .setNanos(nanos)
                .build();
    }

    private static Date toDate(Timestamp timestamp) {
        long millis = timestamp.getSeconds() * 1000 + timestamp.getNanos() / 1_000_000;
        return new Date(millis);
    }
}

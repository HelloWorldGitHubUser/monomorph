package ltd.newbee.mall.monomorph.dto.generated.client;

// gRPC imports
import ltd.newbee.mall.monomorph.dto.generated.proto.adminusertoken.AdminUserTokenDTO;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import java.util.Date;

/**
 * Auto-generated DTO gRPC client
 * {@link AdminUserToken} and {@link AdminUserTokenDTO}.
 */
public class AdminUserToken {

    private AdminUserTokenDTO dtoInstance;

    /**
     * No-args constructor matching the original class (Lombok @Data provides an implicit no-args constructor).
     * Initializes the DTO with default values.
     */
    public AdminUserToken() {
        this.dtoInstance = AdminUserTokenDTO.getDefaultInstance();
    }

    /**
     * Constructor that initializes from a DTO instance.
     * Used by {@link #fromDTO(AdminUserTokenDTO)}.
     */
    public AdminUserToken(AdminUserTokenDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    // mapping methods
    public AdminUserTokenDTO toDTO() {
        return this.dtoInstance;
    }

    public static AdminUserToken fromDTO(AdminUserTokenDTO dtoInstance) {
        return new AdminUserToken(dtoInstance);
    }

    // implementation of the gRPC exposed methods
    // No service methods are defined in the proto definition; only the DTO message is provided.

    // --- START OF DTO GETTERS AND SETTERS ---

    public Long getAdminUserId() {
        return dtoInstance.getAdminUserId();
    }

    public void setAdminUserId(Long adminUserId) {
        long value = (adminUserId == null) ? 0L : adminUserId.longValue();
        dtoInstance = dtoInstance.toBuilder().setAdminUserId(value).build();
    }

    public String getToken() {
        return dtoInstance.getToken();
    }

    public void setToken(String token) {
        String value = (token == null) ? "" : token;
        dtoInstance = dtoInstance.toBuilder().setToken(value).build();
    }

    public Date getUpdateTime() {
        Timestamp ts = dtoInstance.getUpdateTime();
        return new Date(Timestamps.toMillis(ts));
    }

    public void setUpdateTime(Date updateTime) {
        if (updateTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearUpdateTime().build();
        } else {
            Timestamp ts = Timestamps.fromMillis(updateTime.getTime());
            dtoInstance = dtoInstance.toBuilder().setUpdateTime(ts).build();
        }
    }

    public Date getExpireTime() {
        Timestamp ts = dtoInstance.getExpireTime();
        return new Date(Timestamps.toMillis(ts));
    }

    public void setExpireTime(Date expireTime) {
        if (expireTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearExpireTime().build();
        } else {
            Timestamp ts = Timestamps.fromMillis(expireTime.getTime());
            dtoInstance = dtoInstance.toBuilder().setExpireTime(ts).build();
        }
    }

    // --- END OF DTO GETTERS AND SETTERS ---

    // Override equals, hashCode, and toString to match the original Lombok-generated behavior.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AdminUserToken other = (AdminUserToken) obj;
        return this.dtoInstance.equals(other.dtoInstance);
    }

    @Override
    public int hashCode() {
        return dtoInstance.hashCode();
    }

    @Override
    public String toString() {
        return "AdminUserToken(" +
                "adminUserId=" + getAdminUserId() +
                ", token=" + getToken() +
                ", updateTime=" + getUpdateTime() +
                ", expireTime=" + getExpireTime() +
                ")";
    }
}
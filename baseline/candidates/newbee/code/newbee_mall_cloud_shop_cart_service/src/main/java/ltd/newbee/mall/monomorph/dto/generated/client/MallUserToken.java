package ltd.newbee.mall.monomorph.dto.generated.client;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.Date;
import ltd.newbee.mall.monomorph.dto.generated.proto.mallusertoken.MallUserTokenDTO;

/**
 * Auto-generated DTO gRPC client
 * {@link MallUserToken} and {@link MallUserTokenDTO}.
 */
public class MallUserToken {

    private MallUserTokenDTO dtoInstance;

    /**
     * No-argument constructor matching the original class API.
     * Initialises the internal DTO to the default instance.
     */
    public MallUserToken() {
        this.dtoInstance = MallUserTokenDTO.getDefaultInstance();
    }

    /**
     * Private constructor used by {@link #fromDTO(MallUserTokenDTO)}.
     */
    private MallUserToken(MallUserTokenDTO dtoInstance) {
        if (dtoInstance == null) {
            dtoInstance = MallUserTokenDTO.getDefaultInstance();
        }
        this.dtoInstance = dtoInstance;
    }

    /**
     * Converts this client instance to its underlying DTO.
     */
    public MallUserTokenDTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a client instance from a DTO.
     */
    public static MallUserToken fromDTO(MallUserTokenDTO dtoInstance) {
        return new MallUserToken(dtoInstance);
    }

    // ------------------------------------------------------------------
    // Getters and setters matching the original Lombok-generated API
    // ------------------------------------------------------------------

    public Long getUserId() {
        return dtoInstance.getUserId();
    }

    public void setUserId(Long userId) {
        long value = userId == null ? 0L : userId;
        dtoInstance = dtoInstance.toBuilder().setUserId(value).build();
    }

    public String getToken() {
        return dtoInstance.getToken();
    }

    public void setToken(String token) {
        String value = token == null ? "" : token;
        dtoInstance = dtoInstance.toBuilder().setToken(value).build();
    }

    public Date getUpdateTime() {
        if (!dtoInstance.hasUpdateTime()) {
            return null;
        }
        Timestamp timestamp = dtoInstance.getUpdateTime();
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return Date.from(instant);
    }

    public void setUpdateTime(Date updateTime) {
        if (updateTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearUpdateTime().build();
        } else {
            Instant instant = updateTime.toInstant();
            Timestamp timestamp = Timestamp.newBuilder()
                    .setSeconds(instant.getEpochSecond())
                    .setNanos(instant.getNano())
                    .build();
            dtoInstance = dtoInstance.toBuilder().setUpdateTime(timestamp).build();
        }
    }

    public Date getExpireTime() {
        if (!dtoInstance.hasExpireTime()) {
            return null;
        }
        Timestamp timestamp = dtoInstance.getExpireTime();
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return Date.from(instant);
    }

    public void setExpireTime(Date expireTime) {
        if (expireTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearExpireTime().build();
        } else {
            Instant instant = expireTime.toInstant();
            Timestamp timestamp = Timestamp.newBuilder()
                    .setSeconds(instant.getEpochSecond())
                    .setNanos(instant.getNano())
                    .build();
            dtoInstance = dtoInstance.toBuilder().setExpireTime(timestamp).build();
        }
    }
}

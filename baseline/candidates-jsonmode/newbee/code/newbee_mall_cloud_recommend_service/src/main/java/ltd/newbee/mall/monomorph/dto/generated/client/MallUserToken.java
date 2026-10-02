package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.mallusertoken.MallUserTokenDTO;
import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.util.Date;

/**
 * Auto-generated DTO gRPC client for MallUserToken.
 * Uses composition to expose the same API as the original entity class.
 */
public class MallUserToken {

    private MallUserTokenDTO dtoInstance;

    /**
     * No-arg constructor, matching the original Lombok @Data generated constructor.
     */
    public MallUserToken() {
        this.dtoInstance = MallUserTokenDTO.getDefaultInstance();
    }

    /**
     * DTO-based constructor, used by fromDTO().
     */
    public MallUserToken(MallUserTokenDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Converts this client object to its DTO representation.
     */
    public MallUserTokenDTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a client instance from a DTO object.
     */
    public static MallUserToken fromDTO(MallUserTokenDTO dtoInstance) {
        return new MallUserToken(dtoInstance);
    }

    // --- DTO GETTERS AND SETTERS ---

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
        Timestamp ts = dtoInstance.getUpdateTime();
        return Date.from(Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos()));
    }

    public void setUpdateTime(Date updateTime) {
        MallUserTokenDTO.Builder builder = dtoInstance.toBuilder();
        if (updateTime == null) {
            builder.clearUpdateTime();
        } else {
            Instant instant = updateTime.toInstant();
            builder.setUpdateTime(Timestamp.newBuilder()
                    .setSeconds(instant.getEpochSecond())
                    .setNanos(instant.getNano())
                    .build());
        }
        dtoInstance = builder.build();
    }

    public Date getExpireTime() {
        if (!dtoInstance.hasExpireTime()) {
            return null;
        }
        Timestamp ts = dtoInstance.getExpireTime();
        return Date.from(Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos()));
    }

    public void setExpireTime(Date expireTime) {
        MallUserTokenDTO.Builder builder = dtoInstance.toBuilder();
        if (expireTime == null) {
            builder.clearExpireTime();
        } else {
            Instant instant = expireTime.toInstant();
            builder.setExpireTime(Timestamp.newBuilder()
                    .setSeconds(instant.getEpochSecond())
                    .setNanos(instant.getNano())
                    .build());
        }
        dtoInstance = builder.build();
    }
}
package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.mallusertoken.*;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.Date;

/**
 * Auto-generated DTO gRPC client for {@link MallUserToken} and {@link MallUserTokenDTO}.
 */
public class MallUserToken {

    private MallUserTokenDTO dtoInstance;

    public MallUserToken() {
        this(MallUserTokenDTO.newBuilder().build());
    }

    public MallUserToken(MallUserTokenDTO dtoInstance) {
        // dtoConstructor to initialize from a DTO instance
        this.dtoInstance = dtoInstance == null
                ? MallUserTokenDTO.newBuilder().build()
                : dtoInstance;
    }

    // mapping methods
    public MallUserTokenDTO toDTO() {
        return this.dtoInstance;
    }

    public static MallUserToken fromDTO(MallUserTokenDTO dtoInstance) {
        return new MallUserToken(dtoInstance);
    }

    // No gRPC service methods are defined in the provided proto service.
    // Only DTO accessors are required for this client.

    // --- START OF DTO GETTERS AND SETTERS ---

    public Long getUserId() {
        return dtoInstance.getUserId();
    }

    public void setUserId(Long userId) {
        MallUserTokenDTO.Builder builder = dtoInstance.toBuilder();
        builder.setUserId(userId == null ? 0L : userId.longValue());
        this.dtoInstance = builder.build();
    }

    public String getToken() {
        return dtoInstance.getToken();
    }

    public void setToken(String token) {
        MallUserTokenDTO.Builder builder = dtoInstance.toBuilder();
        builder.setToken(token == null ? "" : token);
        this.dtoInstance = builder.build();
    }

    public Date getUpdateTime() {
        return toDate(dtoInstance.getUpdateTime());
    }

    public void setUpdateTime(Date updateTime) {
        MallUserTokenDTO.Builder builder = dtoInstance.toBuilder();
        if (updateTime == null) {
            builder.clearUpdateTime();
        } else {
            builder.setUpdateTime(toTimestamp(updateTime));
        }
        this.dtoInstance = builder.build();
    }

    public Date getExpireTime() {
        return toDate(dtoInstance.getExpireTime());
    }

    public void setExpireTime(Date expireTime) {
        MallUserTokenDTO.Builder builder = dtoInstance.toBuilder();
        if (expireTime == null) {
            builder.clearExpireTime();
        } else {
            builder.setExpireTime(toTimestamp(expireTime));
        }
        this.dtoInstance = builder.build();
    }

    // --- END OF DTO GETTERS AND SETTERS ---

    private static Date toDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return Date.from(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()));
    }

    private static Timestamp toTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}

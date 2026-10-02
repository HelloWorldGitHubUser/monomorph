package ltd.newbee.mall.monomorph.dto.generated.client;

// gRPC imports
import ltd.newbee.mall.monomorph.dto.generated.proto.malluser.*;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.Date;

/**
 * Auto-generated DTO gRPC client.
 * Composition-based implementation that wraps a {@link MallUserDTO}
 * and exposes the same JavaBean API as the original MallUser class.
 */
public class MallUser {

    private MallUserDTO dtoInstance;

    /**
     * Default constructor compatible with the original Lombok {@code @Data} class.
     */
    public MallUser() {
        this.dtoInstance = MallUserDTO.getDefaultInstance();
    }

    /**
     * Constructor to initialize from a DTO instance.
     */
    public MallUser(MallUserDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Converts this client to its underlying DTO.
     */
    public MallUserDTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a client instance from the given DTO.
     */
    public static MallUser fromDTO(MallUserDTO dtoInstance) {
        return new MallUser(dtoInstance);
    }

    // ---- DTO GETTERS AND SETTERS ----

    public Long getUserId() {
        return dtoInstance.getUserId();
    }

    public void setUserId(Long userId) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        if (userId == null) {
            builder.clearUserId();
        } else {
            builder.setUserId(userId);
        }
        this.dtoInstance = builder.build();
    }

    public String getNickName() {
        return dtoInstance.getNickName();
    }

    public void setNickName(String nickName) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        if (nickName == null) {
            builder.clearNickName();
        } else {
            builder.setNickName(nickName);
        }
        this.dtoInstance = builder.build();
    }

    public String getLoginName() {
        return dtoInstance.getLoginName();
    }

    public void setLoginName(String loginName) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        if (loginName == null) {
            builder.clearLoginName();
        } else {
            builder.setLoginName(loginName);
        }
        this.dtoInstance = builder.build();
    }

    public String getPasswordMd5() {
        return dtoInstance.getPasswordMd5();
    }

    public void setPasswordMd5(String passwordMd5) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        if (passwordMd5 == null) {
            builder.clearPasswordMd5();
        } else {
            builder.setPasswordMd5(passwordMd5);
        }
        this.dtoInstance = builder.build();
    }

    public String getIntroduceSign() {
        return dtoInstance.getIntroduceSign();
    }

    public void setIntroduceSign(String introduceSign) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        if (introduceSign == null) {
            builder.clearIntroduceSign();
        } else {
            builder.setIntroduceSign(introduceSign);
        }
        this.dtoInstance = builder.build();
    }

    public Byte getIsDeleted() {
        return (byte) dtoInstance.getIsDeleted();
    }

    public void setIsDeleted(Byte isDeleted) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        if (isDeleted == null) {
            builder.clearIsDeleted();
        } else {
            builder.setIsDeleted(isDeleted.byteValue());
        }
        this.dtoInstance = builder.build();
    }

    public Byte getLockedFlag() {
        return (byte) dtoInstance.getLockedFlag();
    }

    public void setLockedFlag(Byte lockedFlag) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        if (lockedFlag == null) {
            builder.clearLockedFlag();
        } else {
            builder.setLockedFlag(lockedFlag.byteValue());
        }
        this.dtoInstance = builder.build();
    }

    public Date getCreateTime() {
        if (dtoInstance.hasCreateTime()) {
            Timestamp timestamp = dtoInstance.getCreateTime();
            return Date.from(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()));
        }
        return null;
    }

    public void setCreateTime(Date createTime) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        if (createTime == null) {
            builder.clearCreateTime();
        } else {
            Instant instant = createTime.toInstant();
            Timestamp timestamp = Timestamp.newBuilder()
                    .setSeconds(instant.getEpochSecond())
                    .setNanos(instant.getNano())
                    .build();
            builder.setCreateTime(timestamp);
        }
        this.dtoInstance = builder.build();
    }
}
package ltd.newbee.mall.monomorph.dto.generated.client;

import com.google.protobuf.Timestamp;
import ltd.newbee.mall.monomorph.dto.generated.proto.malluser.MallUserDTO;

import java.time.Instant;
import java.util.Date;

/**
 * Auto-generated DTO gRPC client.
 * Uses composition to expose the same API as the original entity while
 * storing all data in an underlying {@link MallUserDTO}.
 */
public class MallUser {

    private MallUserDTO dtoInstance;

    /**
     * No-args constructor, matching the original Lombok-generated constructor.
     */
    public MallUser() {
        this.dtoInstance = MallUserDTO.getDefaultInstance();
    }

    /**
     * Private constructor for DTO-based creation. Used by {@link #fromDTO}.
     */
    private MallUser(MallUserDTO dtoInstance) {
        this.dtoInstance = dtoInstance == null ? MallUserDTO.getDefaultInstance() : dtoInstance;
    }

    public MallUserDTO toDTO() {
        return this.dtoInstance;
    }

    public static MallUser fromDTO(MallUserDTO dtoInstance) {
        return new MallUser(dtoInstance);
    }

    // --- START OF DTO GETTERS AND SETTERS ---

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
        builder.setNickName(nickName == null ? "" : nickName);
        this.dtoInstance = builder.build();
    }

    public String getLoginName() {
        return dtoInstance.getLoginName();
    }

    public void setLoginName(String loginName) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        builder.setLoginName(loginName == null ? "" : loginName);
        this.dtoInstance = builder.build();
    }

    public String getPasswordMd5() {
        return dtoInstance.getPasswordMd5();
    }

    public void setPasswordMd5(String passwordMd5) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        builder.setPasswordMd5(passwordMd5 == null ? "" : passwordMd5);
        this.dtoInstance = builder.build();
    }

    public String getIntroduceSign() {
        return dtoInstance.getIntroduceSign();
    }

    public void setIntroduceSign(String introduceSign) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        builder.setIntroduceSign(introduceSign == null ? "" : introduceSign);
        this.dtoInstance = builder.build();
    }

    public Byte getIsDeleted() {
        return (byte) dtoInstance.getIsDeleted();
    }

    public void setIsDeleted(Byte isDeleted) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        builder.setIsDeleted(isDeleted == null ? 0 : isDeleted.intValue());
        this.dtoInstance = builder.build();
    }

    public Byte getLockedFlag() {
        return (byte) dtoInstance.getLockedFlag();
    }

    public void setLockedFlag(Byte lockedFlag) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        builder.setLockedFlag(lockedFlag == null ? 0 : lockedFlag.intValue());
        this.dtoInstance = builder.build();
    }

    public Date getCreateTime() {
        Timestamp timestamp = dtoInstance.getCreateTime();
        if (timestamp.equals(Timestamp.getDefaultInstance())) {
            return null;
        }
        return Date.from(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()));
    }

    public void setCreateTime(Date createTime) {
        MallUserDTO.Builder builder = dtoInstance.toBuilder();
        if (createTime == null) {
            builder.clearCreateTime();
        } else {
            Instant instant = Instant.ofEpochMilli(createTime.getTime());
            Timestamp timestamp = Timestamp.newBuilder()
                    .setSeconds(instant.getEpochSecond())
                    .setNanos(instant.getNano())
                    .build();
            builder.setCreateTime(timestamp);
        }
        this.dtoInstance = builder.build();
    }

    // --- END OF DTO GETTERS AND SETTERS ---
}
package ltd.newbee.mall.monomorph.dto.generated.client;

import com.google.protobuf.Timestamp;
import ltd.newbee.mall.monomorph.dto.generated.proto.malluser.MallUserDTO;

import java.util.Date;

/**
 * Auto-generated DTO gRPC client
 * {@link MallUser} and {@link MallUserDTO}.
 */
public class MallUser {
    private MallUserDTO dtoInstance;

    /**
     * Public no-args constructor (mirrors original Lombok @Data).
     */
    public MallUser() {
        this.dtoInstance = MallUserDTO.newBuilder().build();
    }

    /**
     * Constructor from a DTO instance (enables fromDTO/toDTO).
     */
    public MallUser(MallUserDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Returns the internal DTO.
     */
    public MallUserDTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a MallUser client from a DTO.
     */
    public static MallUser fromDTO(MallUserDTO dtoInstance) {
        return new MallUser(dtoInstance);
    }

    // --- GETTERS AND SETTERS (delegating to DTO) ---

    public Long getUserId() {
        return dtoInstance.getUserId();
    }

    public void setUserId(Long userId) {
        dtoInstance = dtoInstance.toBuilder()
                .setUserId(userId == null ? 0L : userId)
                .build();
    }

    public String getNickName() {
        return dtoInstance.getNickName();
    }

    public void setNickName(String nickName) {
        dtoInstance = dtoInstance.toBuilder()
                .setNickName(nickName == null ? "" : nickName)
                .build();
    }

    public String getLoginName() {
        return dtoInstance.getLoginName();
    }

    public void setLoginName(String loginName) {
        dtoInstance = dtoInstance.toBuilder()
                .setLoginName(loginName == null ? "" : loginName)
                .build();
    }

    public String getPasswordMd5() {
        return dtoInstance.getPasswordMd5();
    }

    public void setPasswordMd5(String passwordMd5) {
        dtoInstance = dtoInstance.toBuilder()
                .setPasswordMd5(passwordMd5 == null ? "" : passwordMd5)
                .build();
    }

    public String getIntroduceSign() {
        return dtoInstance.getIntroduceSign();
    }

    public void setIntroduceSign(String introduceSign) {
        dtoInstance = dtoInstance.toBuilder()
                .setIntroduceSign(introduceSign == null ? "" : introduceSign)
                .build();
    }

    public Byte getIsDeleted() {
        return (byte) dtoInstance.getIsDeleted();
    }

    public void setIsDeleted(Byte isDeleted) {
        dtoInstance = dtoInstance.toBuilder()
                .setIsDeleted(isDeleted == null ? 0 : isDeleted)
                .build();
    }

    public Byte getLockedFlag() {
        return (byte) dtoInstance.getLockedFlag();
    }

    public void setLockedFlag(Byte lockedFlag) {
        dtoInstance = dtoInstance.toBuilder()
                .setLockedFlag(lockedFlag == null ? 0 : lockedFlag)
                .build();
    }

    public Date getCreateTime() {
        if (!dtoInstance.hasCreateTime()) {
            return null;
        }
        Timestamp ts = dtoInstance.getCreateTime();
        return new Date(ts.getSeconds() * 1000L + ts.getNanos() / 1_000_000L);
    }

    public void setCreateTime(Date createTime) {
        if (createTime == null) {
            dtoInstance = dtoInstance.toBuilder()
                    .clearCreateTime()
                    .build();
        } else {
            long millis = createTime.getTime();
            Timestamp ts = Timestamp.newBuilder()
                    .setSeconds(millis / 1000)
                    .setNanos((int) ((millis % 1000) * 1_000_000))
                    .build();
            dtoInstance = dtoInstance.toBuilder()
                    .setCreateTime(ts)
                    .build();
        }
    }
}
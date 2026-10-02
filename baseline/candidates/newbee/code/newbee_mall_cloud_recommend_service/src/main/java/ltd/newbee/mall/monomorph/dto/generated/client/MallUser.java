package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.malluser.*;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import java.util.Date;

public class MallUser {
    private MallUserDTO dtoInstance;

    private MallUser(MallUserDTO dtoInstance) {
        this.dtoInstance = dtoInstance == null ? MallUserDTO.getDefaultInstance() : dtoInstance;
    }

    public MallUser() {
        this(MallUserDTO.getDefaultInstance());
    }

    public MallUserDTO toDTO() {
        return this.dtoInstance;
    }

    public static MallUser fromDTO(MallUserDTO dtoInstance) {
        return new MallUser(dtoInstance);
    }

    public Long getUserId() {
        return dtoInstance.getUserId();
    }

    public void setUserId(Long userId) {
        dtoInstance = dtoInstance.toBuilder().setUserId(userId).build();
    }

    public String getNickName() {
        return dtoInstance.getNickName();
    }

    public void setNickName(String nickName) {
        dtoInstance = dtoInstance.toBuilder().setNickName(nickName).build();
    }

    public String getLoginName() {
        return dtoInstance.getLoginName();
    }

    public void setLoginName(String loginName) {
        dtoInstance = dtoInstance.toBuilder().setLoginName(loginName).build();
    }

    public String getPasswordMd5() {
        return dtoInstance.getPasswordMd5();
    }

    public void setPasswordMd5(String passwordMd5) {
        dtoInstance = dtoInstance.toBuilder().setPasswordMd5(passwordMd5).build();
    }

    public String getIntroduceSign() {
        return dtoInstance.getIntroduceSign();
    }

    public void setIntroduceSign(String introduceSign) {
        dtoInstance = dtoInstance.toBuilder().setIntroduceSign(introduceSign).build();
    }

    public Byte getIsDeleted() {
        return (byte) dtoInstance.getIsDeleted();
    }

    public void setIsDeleted(Byte isDeleted) {
        dtoInstance = dtoInstance.toBuilder().setIsDeleted(isDeleted).build();
    }

    public Byte getLockedFlag() {
        return (byte) dtoInstance.getLockedFlag();
    }

    public void setLockedFlag(Byte lockedFlag) {
        dtoInstance = dtoInstance.toBuilder().setLockedFlag(lockedFlag).build();
    }

    public Date getCreateTime() {
        if (!dtoInstance.hasCreateTime()) {
            return null;
        }
        return Timestamps.toDate(dtoInstance.getCreateTime());
    }

    public void setCreateTime(Date createTime) {
        if (createTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearCreateTime().build();
        } else {
            Timestamp timestamp = Timestamps.fromDate(createTime);
            dtoInstance = dtoInstance.toBuilder().setCreateTime(timestamp).build();
        }
    }
}
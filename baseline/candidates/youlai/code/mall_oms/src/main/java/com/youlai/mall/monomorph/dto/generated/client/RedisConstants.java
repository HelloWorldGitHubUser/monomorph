package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.redisconstants.RedisConstantsDTO;

public class RedisConstants {

    public static final String ROLE_PERMS_PREFIX = "role_perms:";

    private RedisConstantsDTO dtoInstance;

    public RedisConstants(RedisConstantsDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public RedisConstantsDTO toDTO() {
        return dtoInstance;
    }

    public static RedisConstants fromDTO(RedisConstantsDTO dtoInstance) {
        return new RedisConstants(dtoInstance);
    }

    public String getTokenBlacklistPrefix() {
        return dtoInstance.getTOKENBLACKLISTPREFIX();
    }

    public void setTokenBlacklistPrefix(String value) {
        dtoInstance = dtoInstance.toBuilder().setTOKENBLACKLISTPREFIX(value).build();
    }

    public String getCaptchaCodePrefix() {
        return dtoInstance.getCAPTCHACODEPREFIX();
    }

    public void setCaptchaCodePrefix(String value) {
        dtoInstance = dtoInstance.toBuilder().setCAPTCHACODEPREFIX(value).build();
    }

    public String getLoginSmsCodePrefix() {
        return dtoInstance.getLOGINSMSCODEPREFIX();
    }

    public void setLoginSmsCodePrefix(String value) {
        dtoInstance = dtoInstance.toBuilder().setLOGINSMSCODEPREFIX(value).build();
    }

    public String getRegisterSmsCodePrefix() {
        return dtoInstance.getREGISTERSMSCODEPREFIX();
    }

    public void setRegisterSmsCodePrefix(String value) {
        dtoInstance = dtoInstance.toBuilder().setREGISTERSMSCODEPREFIX(value).build();
    }

    public String getRolePermsPrefix() {
        return dtoInstance.getROLEPERMSPREFIX();
    }

    public void setRolePermsPrefix(String value) {
        dtoInstance = dtoInstance.toBuilder().setROLEPERMSPREFIX(value).build();
    }

    public String getJwkSetKey() {
        return dtoInstance.getJWKSETKEY();
    }

    public void setJwkSetKey(String value) {
        dtoInstance = dtoInstance.toBuilder().setJWKSETKEY(value).build();
    }
}


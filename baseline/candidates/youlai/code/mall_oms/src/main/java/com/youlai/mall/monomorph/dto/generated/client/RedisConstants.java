package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.redisconstants.RedisConstantsDTO;

public class RedisConstants {
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
        return dtoInstance.getTokenBlacklistPrefix();
    }

    public void setTokenBlacklistPrefix(String value) {
        dtoInstance = dtoInstance.toBuilder().setTokenBlacklistPrefix(value).build();
    }

    public String getCaptchaCodePrefix() {
        return dtoInstance.getCaptchaCodePrefix();
    }

    public void setCaptchaCodePrefix(String value) {
        dtoInstance = dtoInstance.toBuilder().setCaptchaCodePrefix(value).build();
    }

    public String getLoginSmsCodePrefix() {
        return dtoInstance.getLoginSmsCodePrefix();
    }

    public void setLoginSmsCodePrefix(String value) {
        dtoInstance = dtoInstance.toBuilder().setLoginSmsCodePrefix(value).build();
    }

    public String getRegisterSmsCodePrefix() {
        return dtoInstance.getRegisterSmsCodePrefix();
    }

    public void setRegisterSmsCodePrefix(String value) {
        dtoInstance = dtoInstance.toBuilder().setRegisterSmsCodePrefix(value).build();
    }

    public String getRolePermsPrefix() {
        return dtoInstance.getRolePermsPrefix();
    }

    public void setRolePermsPrefix(String value) {
        dtoInstance = dtoInstance.toBuilder().setRolePermsPrefix(value).build();
    }

    public String getJwkSetKey() {
        return dtoInstance.getJwkSetKey();
    }

    public void setJwkSetKey(String value) {
        dtoInstance = dtoInstance.toBuilder().setJwkSetKey(value).build();
    }
}
package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.redisconstants.RedisConstantsDTO;

/**
 * Auto-generated DTO gRPC client for {@link RedisConstantsDTO}.
 * Implements composition-based DTO mapping for RedisConstants.
 */
public class RedisConstants {
    private RedisConstantsDTO dtoInstance;

    /**
     * Constructs a client backed by the given DTO.
     *
     * @param dtoInstance the DTO carrying RedisConstants payload
     */
    public RedisConstants(RedisConstantsDTO dtoInstance) {
        this.dtoInstance = dtoInstance == null
                ? RedisConstantsDTO.getDefaultInstance()
                : dtoInstance;
    }

    /**
     * Returns the underlying DTO.
     */
    public RedisConstantsDTO toDTO() {
        return dtoInstance;
    }

    /**
     * Creates a client instance from the given DTO.
     */
    public static RedisConstants fromDTO(RedisConstantsDTO dtoInstance) {
        return new RedisConstants(dtoInstance);
    }

    // --- DTO getters/setters ---

    public String getTokenBlacklistPrefix() {
        return dtoInstance.getTokenBlacklistPrefix();
    }

    public void setTokenBlacklistPrefix(String tokenBlacklistPrefix) {
        dtoInstance = dtoInstance.toBuilder()
                .setTokenBlacklistPrefix(tokenBlacklistPrefix)
                .build();
    }

    public String getCaptchaCodePrefix() {
        return dtoInstance.getCaptchaCodePrefix();
    }

    public void setCaptchaCodePrefix(String captchaCodePrefix) {
        dtoInstance = dtoInstance.toBuilder()
                .setCaptchaCodePrefix(captchaCodePrefix)
                .build();
    }

    public String getLoginSmsCodePrefix() {
        return dtoInstance.getLoginSmsCodePrefix();
    }

    public void setLoginSmsCodePrefix(String loginSmsCodePrefix) {
        dtoInstance = dtoInstance.toBuilder()
                .setLoginSmsCodePrefix(loginSmsCodePrefix)
                .build();
    }

    public String getRegisterSmsCodePrefix() {
        return dtoInstance.getRegisterSmsCodePrefix();
    }

    public void setRegisterSmsCodePrefix(String registerSmsCodePrefix) {
        dtoInstance = dtoInstance.toBuilder()
                .setRegisterSmsCodePrefix(registerSmsCodePrefix)
                .build();
    }

    public String getRolePermsPrefix() {
        return dtoInstance.getRolePermsPrefix();
    }

    public void setRolePermsPrefix(String rolePermsPrefix) {
        dtoInstance = dtoInstance.toBuilder()
                .setRolePermsPrefix(rolePermsPrefix)
                .build();
    }

    public String getJwkSetKey() {
        return dtoInstance.getJwkSetKey();
    }

    public void setJwkSetKey(String jwkSetKey) {
        dtoInstance = dtoInstance.toBuilder()
                .setJwkSetKey(jwkSetKey)
                .build();
    }
}
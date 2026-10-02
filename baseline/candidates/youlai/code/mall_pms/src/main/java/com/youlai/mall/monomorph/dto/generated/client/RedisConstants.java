package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.redisconstants.RedisConstantsDTO;

/**
 * Auto-generated DTO gRPC client for {@link RedisConstantsDTO}.
 * Implements composition-based DTO mapping for RedisConstants.
 */
public class RedisConstants {

    // --- Static constants (values match original RedisConstants) ---
    public static final String TOKEN_BLACKLIST_PREFIX = "token:blacklist:";
    public static final String CAPTCHA_CODE_PREFIX = "captcha_code:";
    public static final String LOGIN_SMS_CODE_PREFIX = "sms_code:login";
    public static final String REGISTER_SMS_CODE_PREFIX = "sms_code:register";
    public static final String ROLE_PERMS_PREFIX = "role_perms:";
    public static final String JWK_SET_KEY = "jwk_set";

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

    // --- DTO getters/setters (aligned with generated method names) ---

    public String getTokenBlacklistPrefix() {
        return dtoInstance.getTOKENBLACKLISTPREFIX();
    }

    public void setTokenBlacklistPrefix(String tokenBlacklistPrefix) {
        dtoInstance = dtoInstance.toBuilder()
                .setTOKENBLACKLISTPREFIX(tokenBlacklistPrefix)
                .build();
    }

    public String getCaptchaCodePrefix() {
        return dtoInstance.getCAPTCHACODEPREFIX();
    }

    public void setCaptchaCodePrefix(String captchaCodePrefix) {
        dtoInstance = dtoInstance.toBuilder()
                .setCAPTCHACODEPREFIX(captchaCodePrefix)
                .build();
    }

    public String getLoginSmsCodePrefix() {
        return dtoInstance.getLOGINSMSCODEPREFIX();
    }

    public void setLoginSmsCodePrefix(String loginSmsCodePrefix) {
        dtoInstance = dtoInstance.toBuilder()
                .setLOGINSMSCODEPREFIX(loginSmsCodePrefix)
                .build();
    }

    public String getRegisterSmsCodePrefix() {
        return dtoInstance.getREGISTERSMSCODEPREFIX();
    }

    public void setRegisterSmsCodePrefix(String registerSmsCodePrefix) {
        dtoInstance = dtoInstance.toBuilder()
                .setREGISTERSMSCODEPREFIX(registerSmsCodePrefix)
                .build();
    }

    public String getRolePermsPrefix() {
        return dtoInstance.getROLEPERMSPREFIX();
    }

    public void setRolePermsPrefix(String rolePermsPrefix) {
        dtoInstance = dtoInstance.toBuilder()
                .setROLEPERMSPREFIX(rolePermsPrefix)
                .build();
    }

    public String getJwkSetKey() {
        return dtoInstance.getJWKSETKEY();
    }

    public void setJwkSetKey(String jwkSetKey) {
        dtoInstance = dtoInstance.toBuilder()
                .setJWKSETKEY(jwkSetKey)
                .build();
    }
}


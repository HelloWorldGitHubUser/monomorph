package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.redisconstants.RedisConstantsDTO;

/**
 * Auto-generated DTO gRPC client for {@code RedisConstants}.
 *
 * Uses composition over a {@link RedisConstantsDTO} to expose the same API
 * as the original constants class while providing DTO conversion and
 * getter/setter access to the underlying DTO fields.
 */
public class RedisConstants {

    // Original public constants preserved for source compatibility
    public static final String TOKEN_BLACKLIST_PREFIX = "token:blacklist:";
    public static final String CAPTCHA_CODE_PREFIX = "captcha_code:";
    public static final String LOGIN_SMS_CODE_PREFIX = "sms_code:login";
    public static final String REGISTER_SMS_CODE_PREFIX = "sms_code:register";
    public static final String ROLE_PERMS_PREFIX = "role_perms:";
    public static final String JWK_SET_KEY = "jwk_set";

    private RedisConstantsDTO dtoInstance;

    /**
     * Default constructor initializes the DTO with the original constant values.
     */
    public RedisConstants() {
        this(RedisConstantsDTO.newBuilder()
                .setTOKENBLACKLISTPREFIX(TOKEN_BLACKLIST_PREFIX)
                .setCAPTCHACODEPREFIX(CAPTCHA_CODE_PREFIX)
                .setLOGINSMSCODEPREFIX(LOGIN_SMS_CODE_PREFIX)
                .setREGISTERSMSCODEPREFIX(REGISTER_SMS_CODE_PREFIX)
                .setROLEPERMSPREFIX(ROLE_PERMS_PREFIX)
                .setJWKSETKEY(JWK_SET_KEY)
                .build());
    }

    /**
     * Constructs a client from an existing DTO instance.
     */
    public RedisConstants(RedisConstantsDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Returns the underlying DTO instance.
     */
    public RedisConstantsDTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a client instance from the supplied DTO.
     */
    public static RedisConstants fromDTO(RedisConstantsDTO dtoInstance) {
        return new RedisConstants(dtoInstance);
    }

    // --- START OF DTO GETTERS AND SETTERS ---

    public String getTOKENBLACKLISTPREFIX() {
        return dtoInstance.getTOKENBLACKLISTPREFIX();
    }

    public void setTOKENBLACKLISTPREFIX(String value) {
        dtoInstance = dtoInstance.toBuilder()
                .setTOKENBLACKLISTPREFIX(value)
                .build();
    }

    public String getCAPTCHACODEPREFIX() {
        return dtoInstance.getCAPTCHACODEPREFIX();
    }

    public void setCAPTCHACODEPREFIX(String value) {
        dtoInstance = dtoInstance.toBuilder()
                .setCAPTCHACODEPREFIX(value)
                .build();
    }

    public String getLOGINSMSCODEPREFIX() {
        return dtoInstance.getLOGINSMSCODEPREFIX();
    }

    public void setLOGINSMSCODEPREFIX(String value) {
        dtoInstance = dtoInstance.toBuilder()
                .setLOGINSMSCODEPREFIX(value)
                .build();
    }

    public String getREGISTERSMSCODEPREFIX() {
        return dtoInstance.getREGISTERSMSCODEPREFIX();
    }

    public void setREGISTERSMSCODEPREFIX(String value) {
        dtoInstance = dtoInstance.toBuilder()
                .setREGISTERSMSCODEPREFIX(value)
                .build();
    }

    public String getROLEPERMSPREFIX() {
        return dtoInstance.getROLEPERMSPREFIX();
    }

    public void setROLEPERMSPREFIX(String value) {
        dtoInstance = dtoInstance.toBuilder()
                .setROLEPERMSPREFIX(value)
                .build();
    }

    public String getJWKSETKEY() {
        return dtoInstance.getJWKSETKEY();
    }

    public void setJWKSETKEY(String value) {
        dtoInstance = dtoInstance.toBuilder()
                .setJWKSETKEY(value)
                .build();
    }

    // --- END OF DTO GETTERS AND SETTERS ---
}


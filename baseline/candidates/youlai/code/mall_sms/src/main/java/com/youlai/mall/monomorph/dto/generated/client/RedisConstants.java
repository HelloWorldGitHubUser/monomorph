package com.youlai.mall.monomorph.dto.generated.client;

// gRPC imports
import com.youlai.mall.monomorph.dto.generated.proto.redisconstants.*;

/**
 * Auto-generated DTO gRPC client
 * {@link RedisConstants} and {@link RedisConstantsDTO}.
 */
public class RedisConstants {

    /**
     * 黑名单TOKEN Key前缀
     */
    public static final String TOKEN_BLACKLIST_PREFIX = "token:blacklist:";

    /**
     * 图形验证码key前缀
     */
    public static final String CAPTCHA_CODE_PREFIX = "captcha_code:";

    /**
     * 登录短信验证码key前缀
     */
    public static final String LOGIN_SMS_CODE_PREFIX = "sms_code:login";

    /**
     * 注册短信验证码key前缀
     */
    public static final String REGISTER_SMS_CODE_PREFIX = "sms_code:register";

    /**
     * 角色和权限缓存前缀
     */
    public static final String ROLE_PERMS_PREFIX = "role_perms:";

    /**
     * JWT 密钥对(包含公钥和私钥)
     */
    public static final String JWK_SET_KEY = "jwk_set";

    private RedisConstantsDTO dtoInstance;

    public RedisConstants(RedisConstantsDTO dtoInstance) {
        this.dtoInstance = dtoInstance == null
            ? RedisConstantsDTO.getDefaultInstance()
            : dtoInstance;
    }

    public RedisConstants() {
        this.dtoInstance = RedisConstantsDTO.getDefaultInstance();
    }

    // mapping methods
    public RedisConstantsDTO toDTO() {
        return this.dtoInstance;
    }

    public static RedisConstants fromDTO(RedisConstantsDTO dtoInstance) {
        return new RedisConstants(dtoInstance);
    }

    // --- START OF DTO GETTERS AND SETTERS ---

    public String getTOKENBLACKLISTPREFIX() {
        return this.dtoInstance.getTOKENBLACKLISTPREFIX();
    }

    public void setTOKENBLACKLISTPREFIX(String value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
            .setTOKENBLACKLISTPREFIX(value)
            .build();
    }

    public String getCAPTCHACODEPREFIX() {
        return this.dtoInstance.getCAPTCHACODEPREFIX();
    }

    public void setCAPTCHACODEPREFIX(String value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
            .setCAPTCHACODEPREFIX(value)
            .build();
    }

    public String getLOGINSMSCODEPREFIX() {
        return this.dtoInstance.getLOGINSMSCODEPREFIX();
    }

    public void setLOGINSMSCODEPREFIX(String value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
            .setLOGINSMSCODEPREFIX(value)
            .build();
    }

    public String getREGISTERSMSCODEPREFIX() {
        return this.dtoInstance.getREGISTERSMSCODEPREFIX();
    }

    public void setREGISTERSMSCODEPREFIX(String value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
            .setREGISTERSMSCODEPREFIX(value)
            .build();
    }

    public String getROLEPERMSPREFIX() {
        return this.dtoInstance.getROLEPERMSPREFIX();
    }

    public void setROLEPERMSPREFIX(String value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
            .setROLEPERMSPREFIX(value)
            .build();
    }

    public String getJWKSETKEY() {
        return this.dtoInstance.getJWKSETKEY();
    }

    public void setJWKSETKEY(String value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
            .setJWKSETKEY(value)
            .build();
    }

    // --- END OF DTO GETTERS AND SETTERS ---
}
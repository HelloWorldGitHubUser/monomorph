package com.youlai.mall.monomorph.dto.generated.client;

// gRPC imports
import com.youlai.mall.monomorph.dto.generated.proto.redisconstants.*;

/**
 * Auto-generated DTO gRPC client
 * {@link RedisConstants} and {@link RedisConstantsDTO}.
 */
public class RedisConstants {

    private RedisConstantsDTO dtoInstance;

    public RedisConstants(RedisConstantsDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    // mapping methods
    public RedisConstantsDTO toDTO() {
        return this.dtoInstance;
    }

    public static RedisConstants fromDTO(RedisConstantsDTO dtoInstance) {
        return new RedisConstants(dtoInstance);
    }

    // implementation of the gRPC exposed methods
    // No RPC methods are exposed by the original RedisConstants interface.

    // Implement all other getters and setters corresponding to the DTO fields
    // --- START OF DTO GETTERS AND SETTERS ---
    public String getTOKENBLACKLISTPREFIX() {
        return dtoInstance.getTOKENBLACKLISTPREFIX();
    }

    public void setTOKENBLACKLISTPREFIX(String value) {
        dtoInstance = dtoInstance.toBuilder().setTOKENBLACKLISTPREFIX(value).build();
    }

    public String getCAPTCHACODEPREFIX() {
        return dtoInstance.getCAPTCHACODEPREFIX();
    }

    public void setCAPTCHACODEPREFIX(String value) {
        dtoInstance = dtoInstance.toBuilder().setCAPTCHACODEPREFIX(value).build();
    }

    public String getLOGINSMSCODEPREFIX() {
        return dtoInstance.getLOGINSMSCODEPREFIX();
    }

    public void setLOGINSMSCODEPREFIX(String value) {
        dtoInstance = dtoInstance.toBuilder().setLOGINSMSCODEPREFIX(value).build();
    }

    public String getREGISTERSMSCODEPREFIX() {
        return dtoInstance.getREGISTERSMSCODEPREFIX();
    }

    public void setREGISTERSMSCODEPREFIX(String value) {
        dtoInstance = dtoInstance.toBuilder().setREGISTERSMSCODEPREFIX(value).build();
    }

    public String getROLEPERMSPREFIX() {
        return dtoInstance.getROLEPERMSPREFIX();
    }

    public void setROLEPERMSPREFIX(String value) {
        dtoInstance = dtoInstance.toBuilder().setROLEPERMSPREFIX(value).build();
    }

    public String getJWKSETKEY() {
        return dtoInstance.getJWKSETKEY();
    }

    public void setJWKSETKEY(String value) {
        dtoInstance = dtoInstance.toBuilder().setJWKSETKEY(value).build();
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}


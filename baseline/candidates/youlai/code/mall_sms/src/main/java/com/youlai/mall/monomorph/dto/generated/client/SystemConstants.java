package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.systemconstants.SystemConstantsDTO;

/**
 * Auto-generated DTO gRPC client wrapping {@link SystemConstantsDTO}.
 */
public class SystemConstants {

    // Backward-compatible constants from the original interface.
    public static final Long ROOT_NODE_ID = 0L;
    public static final String DEFAULT_PASSWORD = "123456";
    public static final String ROOT_ROLE_CODE = "ROOT";

    private SystemConstantsDTO dtoInstance;

    /**
     * Creates a default wrapper whose DTO fields mirror the original constants.
     */
    public SystemConstants() {
        this.dtoInstance = SystemConstantsDTO.newBuilder()
                .setROOTNODEID(ROOT_NODE_ID)
                .setDEFAULTPASSWORD(DEFAULT_PASSWORD)
                .setROOTROLECODE(ROOT_ROLE_CODE)
                .build();
    }

    /**
     * Creates a wrapper backed by the supplied DTO instance.
     */
    public SystemConstants(SystemConstantsDTO dtoInstance) {
        if (dtoInstance == null) {
            throw new IllegalArgumentException("dtoInstance must not be null");
        }
        this.dtoInstance = dtoInstance;
    }

    public SystemConstantsDTO toDTO() {
        return this.dtoInstance;
    }

    public static SystemConstants fromDTO(SystemConstantsDTO dtoInstance) {
        return new SystemConstants(dtoInstance);
    }

    public long getRootNodeId() {
        return dtoInstance.getROOTNODEID();
    }

    public void setRootNodeId(long value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setROOTNODEID(value)
                .build();
    }

    public String getDefaultPassword() {
        return dtoInstance.getDEFAULTPASSWORD();
    }

    public void setDefaultPassword(String value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setDEFAULTPASSWORD(value)
                .build();
    }

    public String getRootRoleCode() {
        return dtoInstance.getROOTROLECODE();
    }

    public void setRootRoleCode(String value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setROOTROLECODE(value)
                .build();
    }
}


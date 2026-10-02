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
                .setRootNodeId(ROOT_NODE_ID)
                .setDefaultPassword(DEFAULT_PASSWORD)
                .setRootRoleCode(ROOT_ROLE_CODE)
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
        return dtoInstance.getRootNodeId();
    }

    public void setRootNodeId(long value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setRootNodeId(value)
                .build();
    }

    public String getDefaultPassword() {
        return dtoInstance.getDefaultPassword();
    }

    public void setDefaultPassword(String value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setDefaultPassword(value)
                .build();
    }

    public String getRootRoleCode() {
        return dtoInstance.getRootRoleCode();
    }

    public void setRootRoleCode(String value) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setRootRoleCode(value)
                .build();
    }
}

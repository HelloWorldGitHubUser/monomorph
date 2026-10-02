package com.youlai.mall.monomorph.dto.generated.client;

// gRPC imports
import com.youlai.mall.monomorph.dto.generated.proto.systemconstants.*;

/**
 * Auto-generated DTO gRPC client
 * {@link SystemConstants} and {@link SystemConstantsDTO}.
 */
public class SystemConstants {

    /**
     * Code of the root/super-admin role.
     *
     * <p>The original SystemConstants interface only exposed constant values and
     * did not define any RPC methods. These constants are used by handwritten
     * application code (e.g. {@code SecurityUtils.isRoot()}).
     */
    public static final String ROOT_ROLE_CODE = "ROOT";

    private SystemConstantsDTO dtoInstance;

    public SystemConstants(SystemConstantsDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    // mapping methods
    public SystemConstantsDTO toDTO() {
        return this.dtoInstance;
    }

    public static SystemConstants fromDTO(SystemConstantsDTO dtoInstance) {
        return new SystemConstants(dtoInstance);
    }

    // implementation of the gRPC exposed methods
    // No RPC methods are exposed by the original SystemConstants interface.

    // Implement all other getters and setters corresponding to the DTO fields
    // --- START OF DTO GETTERS AND SETTERS ---
    public long getROOTNODEID() {
        return dtoInstance.getROOTNODEID();
    }

    public void setROOTNODEID(long value) {
        dtoInstance = dtoInstance.toBuilder().setROOTNODEID(value).build();
    }

    public String getDEFAULTPASSWORD() {
        return dtoInstance.getDEFAULTPASSWORD();
    }

    public void setDEFAULTPASSWORD(String value) {
        dtoInstance = dtoInstance.toBuilder().setDEFAULTPASSWORD(value).build();
    }

    public String getROOTROLECODE() {
        return dtoInstance.getROOTROLECODE();
    }

    public void setROOTROLECODE(String value) {
        dtoInstance = dtoInstance.toBuilder().setROOTROLECODE(value).build();
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}


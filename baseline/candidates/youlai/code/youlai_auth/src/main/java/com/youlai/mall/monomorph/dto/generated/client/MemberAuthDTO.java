package com.youlai.mall.monomorph.dto.generated.client;

// gRPC imports
import com.youlai.mall.monomorph.dto.generated.proto.memberauthdto.*;

/**
 * Auto-generated DTO gRPC client.
 * Provides the same API as the original MemberAuthDTO through DTO composition.
 */
public class MemberAuthDTO {
    private MemberAuthDTODTO dtoInstance;

    public MemberAuthDTO(MemberAuthDTODTO dtoInstance) {
        // dtoConstructor to initialize from a DTO instance
        this.dtoInstance = dtoInstance;
    }

    /**
     * No-args constructor matching the original @NoArgsConstructor API.
     */
    public MemberAuthDTO() {
        this(MemberAuthDTODTO.getDefaultInstance());
    }

    /**
     * All-args constructor matching the original @AllArgsConstructor API.
     */
    public MemberAuthDTO(Long id, String username, Integer status) {
        this();
        if (id != null) {
            this.dtoInstance = this.dtoInstance.toBuilder().setId(id).build();
        }
        if (username != null) {
            this.dtoInstance = this.dtoInstance.toBuilder().setUsername(username).build();
        }
        if (status != null) {
            this.dtoInstance = this.dtoInstance.toBuilder().setStatus(status).build();
        }
    }

    // mapping methods
    public MemberAuthDTODTO toDTO() {
        return this.dtoInstance;
    }

    public static MemberAuthDTO fromDTO(MemberAuthDTODTO dtoInstance) {
        return new MemberAuthDTO(dtoInstance);
    }

    // --- START OF DTO GETTERS AND SETTERS ---
    public Long getId() {
        return this.dtoInstance.getId();
    }

    public void setId(Long id) {
        if (id != null) {
            this.dtoInstance = this.dtoInstance.toBuilder().setId(id).build();
        } else {
            this.dtoInstance = this.dtoInstance.toBuilder().clearId().build();
        }
    }

    public String getUsername() {
        return this.dtoInstance.getUsername();
    }

    public void setUsername(String username) {
        if (username != null) {
            this.dtoInstance = this.dtoInstance.toBuilder().setUsername(username).build();
        } else {
            this.dtoInstance = this.dtoInstance.toBuilder().clearUsername().build();
        }
    }

    public Integer getStatus() {
        return this.dtoInstance.getStatus();
    }

    public void setStatus(Integer status) {
        if (status != null) {
            this.dtoInstance = this.dtoInstance.toBuilder().setStatus(status).build();
        } else {
            this.dtoInstance = this.dtoInstance.toBuilder().clearStatus().build();
        }
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}
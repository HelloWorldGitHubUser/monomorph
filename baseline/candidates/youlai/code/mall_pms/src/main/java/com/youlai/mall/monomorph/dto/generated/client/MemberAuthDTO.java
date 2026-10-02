package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.memberauthdto.MemberAuthDTODTO;

/**
 * Auto-generated DTO gRPC client.
 * Composition wrapper for {@link MemberAuthDTODTO} that mirrors the API of the original
 * {@code com.youlai.mall.model.ums.dto.MemberAuthDTO} class.
 */
public class MemberAuthDTO {
    private MemberAuthDTODTO dtoInstance;

    /**
     * No-argument constructor matching the original {@code @NoArgsConstructor}.
     */
    public MemberAuthDTO() {
        this.dtoInstance = MemberAuthDTODTO.getDefaultInstance();
    }

    /**
     * All-arguments constructor matching the original {@code @AllArgsConstructor}.
     */
    public MemberAuthDTO(Long id, String username, Integer status) {
        MemberAuthDTODTO.Builder builder = MemberAuthDTODTO.newBuilder();
        if (id != null) {
            builder.setId(id);
        }
        if (username != null) {
            builder.setUsername(username);
        }
        if (status != null) {
            builder.setStatus(status);
        }
        this.dtoInstance = builder.build();
    }

    /**
     * Private constructor used by {@code fromDTO}.
     */
    private MemberAuthDTO(MemberAuthDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Converts this wrapper to its underlying DTO.
     */
    public MemberAuthDTODTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a wrapper from a DTO instance.
     */
    public static MemberAuthDTO fromDTO(MemberAuthDTODTO dtoInstance) {
        return new MemberAuthDTO(dtoInstance);
    }

    // --- Getters and setters mirroring the original API ---

    public Long getId() {
        return this.dtoInstance.getId();
    }

    public void setId(Long id) {
        MemberAuthDTODTO.Builder builder = this.dtoInstance.toBuilder();
        if (id == null) {
            builder.clearId();
        } else {
            builder.setId(id);
        }
        this.dtoInstance = builder.build();
    }

    public String getUsername() {
        return this.dtoInstance.getUsername();
    }

    public void setUsername(String username) {
        MemberAuthDTODTO.Builder builder = this.dtoInstance.toBuilder();
        if (username == null) {
            builder.clearUsername();
        } else {
            builder.setUsername(username);
        }
        this.dtoInstance = builder.build();
    }

    public Integer getStatus() {
        return this.dtoInstance.getStatus();
    }

    public void setStatus(Integer status) {
        MemberAuthDTODTO.Builder builder = this.dtoInstance.toBuilder();
        if (status == null) {
            builder.clearStatus();
        } else {
            builder.setStatus(status);
        }
        this.dtoInstance = builder.build();
    }
}

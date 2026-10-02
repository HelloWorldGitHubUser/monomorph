package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.memberauthdto.MemberAuthDTODTO;

/**
 * Auto-generated DTO gRPC client for {@link MemberAuthDTODTO}.
 */
public class MemberAuthDTO {

    private MemberAuthDTODTO dtoInstance;

    // No-args constructor matching original @NoArgsConstructor
    public MemberAuthDTO() {
        this.dtoInstance = MemberAuthDTODTO.newBuilder().build();
    }

    // All-args constructor matching original @AllArgsConstructor
    public MemberAuthDTO(Long id, String username, Integer status) {
        this.dtoInstance = MemberAuthDTODTO.newBuilder()
                .setId(id == null ? 0L : id)
                .setUsername(username == null ? "" : username)
                .setStatus(status == null ? 0 : status)
                .build();
    }

    // Private DTO constructor for fromDTO/toDTO
    private MemberAuthDTO(MemberAuthDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public MemberAuthDTODTO toDTO() {
        return this.dtoInstance;
    }

    public static MemberAuthDTO fromDTO(MemberAuthDTODTO dtoInstance) {
        return new MemberAuthDTO(dtoInstance);
    }

    // --- START OF DTO GETTERS AND SETTERS ---

    public Long getId() {
        return dtoInstance.getId();
    }

    public void setId(Long id) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setId(id == null ? 0L : id)
                .build();
    }

    public String getUsername() {
        return dtoInstance.getUsername();
    }

    public void setUsername(String username) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setUsername(username == null ? "" : username)
                .build();
    }

    public Integer getStatus() {
        return dtoInstance.getStatus();
    }

    public void setStatus(Integer status) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setStatus(status == null ? 0 : status)
                .build();
    }

    // --- END OF DTO GETTERS AND SETTERS ---
}

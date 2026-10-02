package com.youlai.mall.monomorph.dto.generated.client;

// gRPC imports
import com.youlai.mall.monomorph.dto.generated.proto.userauthinfo.UserAuthInfoDTO;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Auto-generated DTO gRPC client for UserAuthInfo.
 * Uses composition to expose the same API as the original class
 * while storing all data in a {@link UserAuthInfoDTO} instance.
 */
public class UserAuthInfo {

    private UserAuthInfoDTO dtoInstance;

    /**
     * Default constructor matching the original implicit no-args constructor.
     * Initializes an empty DTO instance.
     */
    public UserAuthInfo() {
        this.dtoInstance = UserAuthInfoDTO.newBuilder().build();
    }

    /**
     * Private constructor that accepts a {@link UserAuthInfoDTO}.
     * Used internally and by {@link #fromDTO(UserAuthInfoDTO)}.
     */
    private UserAuthInfo(UserAuthInfoDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    // Mapping methods

    public UserAuthInfoDTO toDTO() {
        return this.dtoInstance;
    }

    public static UserAuthInfo fromDTO(UserAuthInfoDTO dtoInstance) {
        return new UserAuthInfo(dtoInstance);
    }

    // Getter and Setter methods corresponding to the DTO fields

    public Long getUserId() {
        return dtoInstance.getUserId();
    }

    public void setUserId(Long userId) {
        dtoInstance = dtoInstance.toBuilder().setUserId(userId).build();
    }

    public String getUsername() {
        return dtoInstance.getUsername();
    }

    public void setUsername(String username) {
        dtoInstance = dtoInstance.toBuilder().setUsername(username).build();
    }

    public String getPassword() {
        return dtoInstance.getPassword();
    }

    public void setPassword(String password) {
        dtoInstance = dtoInstance.toBuilder().setPassword(password).build();
    }

    public Integer getStatus() {
        return dtoInstance.getStatus();
    }

    public void setStatus(Integer status) {
        dtoInstance = dtoInstance.toBuilder().setStatus(status).build();
    }

    public Set<String> getRoles() {
        return new LinkedHashSet<>(dtoInstance.getRolesList());
    }

    public void setRoles(Set<String> roles) {
        dtoInstance = dtoInstance.toBuilder()
                .clearRoles()
                .addAllRoles(roles)
                .build();
    }

    public Set<String> getPerms() {
        return new LinkedHashSet<>(dtoInstance.getPermsList());
    }

    public void setPerms(Set<String> perms) {
        dtoInstance = dtoInstance.toBuilder()
                .clearPerms()
                .addAllPerms(perms)
                .build();
    }

    public Long getDeptId() {
        return dtoInstance.getDeptId();
    }

    public void setDeptId(Long deptId) {
        dtoInstance = dtoInstance.toBuilder().setDeptId(deptId).build();
    }

    public Integer getDataScope() {
        return dtoInstance.getDataScope();
    }

    public void setDataScope(Integer dataScope) {
        dtoInstance = dtoInstance.toBuilder().setDataScope(dataScope).build();
    }

    public String getNickname() {
        return dtoInstance.getNickname();
    }

    public void setNickname(String nickname) {
        dtoInstance = dtoInstance.toBuilder().setNickname(nickname).build();
    }

    public String getMobile() {
        return dtoInstance.getMobile();
    }

    public void setMobile(String mobile) {
        dtoInstance = dtoInstance.toBuilder().setMobile(mobile).build();
    }

    public String getEmail() {
        return dtoInstance.getEmail();
    }

    public void setEmail(String email) {
        dtoInstance = dtoInstance.toBuilder().setEmail(email).build();
    }

    public String getAvatar() {
        return dtoInstance.getAvatar();
    }

    public void setAvatar(String avatar) {
        dtoInstance = dtoInstance.toBuilder().setAvatar(avatar).build();
    }
}
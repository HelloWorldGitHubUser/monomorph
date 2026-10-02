package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.memberregisterdto.MemberRegisterDtoDTO;
import java.time.LocalDate;

/**
 * Auto-generated DTO gRPC client
 * Composes {@link MemberRegisterDtoDTO} to expose the same API as the original DTO.
 */
public class MemberRegisterDto {
    private MemberRegisterDtoDTO dtoInstance;

    // Public no-arg constructor for original API compatibility
    public MemberRegisterDto() {
        this.dtoInstance = MemberRegisterDtoDTO.getDefaultInstance();
    }

    // Private DTO constructor used by fromDTO/toDTO
    private MemberRegisterDto(MemberRegisterDtoDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    // Mapping methods
    public MemberRegisterDtoDTO toDTO() {
        return this.dtoInstance;
    }

    public static MemberRegisterDto fromDTO(MemberRegisterDtoDTO dtoInstance) {
        return new MemberRegisterDto(dtoInstance);
    }

    // DTO getters and setters
    public Integer getGender() {
        return dtoInstance.getGender();
    }

    public void setGender(Integer gender) {
        dtoInstance = dtoInstance.toBuilder().setGender(gender == null ? 0 : gender).build();
    }

    public String getNickName() {
        return dtoInstance.getNickName();
    }

    public void setNickName(String nickName) {
        dtoInstance = dtoInstance.toBuilder().setNickName(nickName == null ? "" : nickName).build();
    }

    public String getMobile() {
        return dtoInstance.getMobile();
    }

    public void setMobile(String mobile) {
        dtoInstance = dtoInstance.toBuilder().setMobile(mobile == null ? "" : mobile).build();
    }

    public LocalDate getBirthday() {
        String value = dtoInstance.getBirthday();
        return (value == null || value.isEmpty()) ? null : LocalDate.parse(value);
    }

    public void setBirthday(LocalDate birthday) {
        dtoInstance = dtoInstance.toBuilder().setBirthday(birthday == null ? "" : birthday.toString()).build();
    }

    public String getAvatarUrl() {
        return dtoInstance.getAvatarUrl();
    }

    public void setAvatarUrl(String avatarUrl) {
        dtoInstance = dtoInstance.toBuilder().setAvatarUrl(avatarUrl == null ? "" : avatarUrl).build();
    }

    public String getOpenid() {
        return dtoInstance.getOpenid();
    }

    public void setOpenid(String openid) {
        dtoInstance = dtoInstance.toBuilder().setOpenid(openid == null ? "" : openid).build();
    }

    public String getSessionKey() {
        return dtoInstance.getSessionKey();
    }

    public void setSessionKey(String sessionKey) {
        dtoInstance = dtoInstance.toBuilder().setSessionKey(sessionKey == null ? "" : sessionKey).build();
    }

    public String getCity() {
        return dtoInstance.getCity();
    }

    public void setCity(String city) {
        dtoInstance = dtoInstance.toBuilder().setCity(city == null ? "" : city).build();
    }

    public String getCountry() {
        return dtoInstance.getCountry();
    }

    public void setCountry(String country) {
        dtoInstance = dtoInstance.toBuilder().setCountry(country == null ? "" : country).build();
    }

    public String getLanguage() {
        return dtoInstance.getLanguage();
    }

    public void setLanguage(String language) {
        dtoInstance = dtoInstance.toBuilder().setLanguage(language == null ? "" : language).build();
    }

    public String getProvince() {
        return dtoInstance.getProvince();
    }

    public void setProvince(String province) {
        dtoInstance = dtoInstance.toBuilder().setProvince(province == null ? "" : province).build();
    }
}
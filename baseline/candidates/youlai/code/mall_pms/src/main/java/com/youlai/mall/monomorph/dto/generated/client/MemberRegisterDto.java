package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.memberregisterdto.MemberRegisterDtoDTO;
import java.time.LocalDate;

/**
 * Auto-generated DTO gRPC client for MemberRegisterDto.
 * Uses composition with {@link MemberRegisterDtoDTO} to maintain the original API.
 */
public class MemberRegisterDto {

    private MemberRegisterDtoDTO dtoInstance;

    public MemberRegisterDto() {
        this.dtoInstance = MemberRegisterDtoDTO.newBuilder().build();
    }

    private MemberRegisterDto(MemberRegisterDtoDTO dtoInstance) {
        this.dtoInstance = dtoInstance == null
                ? MemberRegisterDtoDTO.newBuilder().build()
                : dtoInstance;
    }

    public MemberRegisterDtoDTO toDTO() {
        return dtoInstance;
    }

    public static MemberRegisterDto fromDTO(MemberRegisterDtoDTO dtoInstance) {
        return new MemberRegisterDto(dtoInstance);
    }

    public Integer getGender() {
        return dtoInstance.getGender();
    }

    public void setGender(Integer gender) {
        dtoInstance = dtoInstance.toBuilder()
                .setGender(gender == null ? 0 : gender)
                .build();
    }

    public String getNickName() {
        return dtoInstance.getNickName();
    }

    public void setNickName(String nickName) {
        dtoInstance = dtoInstance.toBuilder()
                .setNickName(nickName == null ? "" : nickName)
                .build();
    }

    public String getMobile() {
        return dtoInstance.getMobile();
    }

    public void setMobile(String mobile) {
        dtoInstance = dtoInstance.toBuilder()
                .setMobile(mobile == null ? "" : mobile)
                .build();
    }

    public LocalDate getBirthday() {
        String birthday = dtoInstance.getBirthday();
        if (birthday == null || birthday.isEmpty()) {
            return null;
        }
        return LocalDate.parse(birthday);
    }

    public void setBirthday(LocalDate birthday) {
        dtoInstance = dtoInstance.toBuilder()
                .setBirthday(birthday == null ? "" : birthday.toString())
                .build();
    }

    public String getAvatarUrl() {
        return dtoInstance.getAvatarUrl();
    }

    public void setAvatarUrl(String avatarUrl) {
        dtoInstance = dtoInstance.toBuilder()
                .setAvatarUrl(avatarUrl == null ? "" : avatarUrl)
                .build();
    }

    public String getOpenid() {
        return dtoInstance.getOpenid();
    }

    public void setOpenid(String openid) {
        dtoInstance = dtoInstance.toBuilder()
                .setOpenid(openid == null ? "" : openid)
                .build();
    }

    public String getSessionKey() {
        return dtoInstance.getSessionKey();
    }

    public void setSessionKey(String sessionKey) {
        dtoInstance = dtoInstance.toBuilder()
                .setSessionKey(sessionKey == null ? "" : sessionKey)
                .build();
    }

    public String getCity() {
        return dtoInstance.getCity();
    }

    public void setCity(String city) {
        dtoInstance = dtoInstance.toBuilder()
                .setCity(city == null ? "" : city)
                .build();
    }

    public String getCountry() {
        return dtoInstance.getCountry();
    }

    public void setCountry(String country) {
        dtoInstance = dtoInstance.toBuilder()
                .setCountry(country == null ? "" : country)
                .build();
    }

    public String getLanguage() {
        return dtoInstance.getLanguage();
    }

    public void setLanguage(String language) {
        dtoInstance = dtoInstance.toBuilder()
                .setLanguage(language == null ? "" : language)
                .build();
    }

    public String getProvince() {
        return dtoInstance.getProvince();
    }

    public void setProvince(String province) {
        dtoInstance = dtoInstance.toBuilder()
                .setProvince(province == null ? "" : province)
                .build();
    }
}
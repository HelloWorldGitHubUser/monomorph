package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.memberregisterdto.MemberRegisterDtoDTO;
import java.time.LocalDate;

/**
 * Auto-generated DTO gRPC client for {@code MemberRegisterDto}.
 * Uses composition to expose the same getter/setter API as the original class,
 * while storing all data in a protobuf {@link MemberRegisterDtoDTO}.
 */
public class MemberRegisterDto {

    private MemberRegisterDtoDTO dtoInstance;

    /**
     * Default constructor, creates an empty DTO instance.
     * Maintains compatibility with the original no-argument constructor.
     */
    public MemberRegisterDto() {
        this.dtoInstance = MemberRegisterDtoDTO.newBuilder().build();
    }

    /**
     * Constructor used by {@link #fromDTO(MemberRegisterDtoDTO)} and {@link #toDTO()}.
     */
    private MemberRegisterDto(MemberRegisterDtoDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Returns the underlying protobuf DTO.
     */
    public MemberRegisterDtoDTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Builds a {@code MemberRegisterDto} from a protobuf DTO.
     */
    public static MemberRegisterDto fromDTO(MemberRegisterDtoDTO dtoInstance) {
        return new MemberRegisterDto(dtoInstance);
    }

    // --- Getters and Setters ---

    public Integer getGender() {
        return dtoInstance.getGender();
    }

    public void setGender(Integer gender) {
        this.dtoInstance = this.dtoInstance.toBuilder().setGender(gender).build();
    }

    public String getNickName() {
        return dtoInstance.getNickName();
    }

    public void setNickName(String nickName) {
        this.dtoInstance = this.dtoInstance.toBuilder().setNickName(nickName).build();
    }

    public String getMobile() {
        return dtoInstance.getMobile();
    }

    public void setMobile(String mobile) {
        this.dtoInstance = this.dtoInstance.toBuilder().setMobile(mobile).build();
    }

    public LocalDate getBirthday() {
        String birthdayStr = dtoInstance.getBirthday();
        return birthdayStr == null || birthdayStr.isEmpty() ? null : LocalDate.parse(birthdayStr);
    }

    public void setBirthday(LocalDate birthday) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setBirthday(birthday == null ? "" : birthday.toString())
                .build();
    }

    public String getAvatarUrl() {
        return dtoInstance.getAvatarUrl();
    }

    public void setAvatarUrl(String avatarUrl) {
        this.dtoInstance = this.dtoInstance.toBuilder().setAvatarUrl(avatarUrl).build();
    }

    public String getOpenid() {
        return dtoInstance.getOpenid();
    }

    public void setOpenid(String openid) {
        this.dtoInstance = this.dtoInstance.toBuilder().setOpenid(openid).build();
    }

    public String getSessionKey() {
        return dtoInstance.getSessionKey();
    }

    public void setSessionKey(String sessionKey) {
        this.dtoInstance = this.dtoInstance.toBuilder().setSessionKey(sessionKey).build();
    }

    public String getCity() {
        return dtoInstance.getCity();
    }

    public void setCity(String city) {
        this.dtoInstance = this.dtoInstance.toBuilder().setCity(city).build();
    }

    public String getCountry() {
        return dtoInstance.getCountry();
    }

    public void setCountry(String country) {
        this.dtoInstance = this.dtoInstance.toBuilder().setCountry(country).build();
    }

    public String getLanguage() {
        return dtoInstance.getLanguage();
    }

    public void setLanguage(String language) {
        this.dtoInstance = this.dtoInstance.toBuilder().setLanguage(language).build();
    }

    public String getProvince() {
        return dtoInstance.getProvince();
    }

    public void setProvince(String province) {
        this.dtoInstance = this.dtoInstance.toBuilder().setProvince(province).build();
    }
}

package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.memberaddressdto.MemberAddressDTODTO;

/**
 * Auto-generated DTO client for MemberAddressDTO.
 */
public class MemberAddressDTO {

    private MemberAddressDTODTO dtoInstance;

    public MemberAddressDTO() {
        this.dtoInstance = MemberAddressDTODTO.getDefaultInstance();
    }

    private MemberAddressDTO(MemberAddressDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public MemberAddressDTODTO toDTO() {
        return this.dtoInstance;
    }

    public static MemberAddressDTO fromDTO(MemberAddressDTODTO dtoInstance) {
        return new MemberAddressDTO(dtoInstance);
    }

    public Long getId() {
        return this.dtoInstance.getId();
    }

    public void setId(Long id) {
        MemberAddressDTODTO.Builder builder = this.dtoInstance.toBuilder();
        if (id == null) {
            builder.clearId();
        } else {
            builder.setId(id);
        }
        this.dtoInstance = builder.build();
    }

    public Long getMemberId() {
        return this.dtoInstance.getMemberId();
    }

    public void setMemberId(Long memberId) {
        MemberAddressDTODTO.Builder builder = this.dtoInstance.toBuilder();
        if (memberId == null) {
            builder.clearMemberId();
        } else {
            builder.setMemberId(memberId);
        }
        this.dtoInstance = builder.build();
    }

    public String getConsigneeName() {
        return this.dtoInstance.getConsigneeName();
    }

    public void setConsigneeName(String consigneeName) {
        MemberAddressDTODTO.Builder builder = this.dtoInstance.toBuilder();
        if (consigneeName == null) {
            builder.clearConsigneeName();
        } else {
            builder.setConsigneeName(consigneeName);
        }
        this.dtoInstance = builder.build();
    }

    public String getConsigneeMobile() {
        return this.dtoInstance.getConsigneeMobile();
    }

    public void setConsigneeMobile(String consigneeMobile) {
        MemberAddressDTODTO.Builder builder = this.dtoInstance.toBuilder();
        if (consigneeMobile == null) {
            builder.clearConsigneeMobile();
        } else {
            builder.setConsigneeMobile(consigneeMobile);
        }
        this.dtoInstance = builder.build();
    }

    public String getProvince() {
        return this.dtoInstance.getProvince();
    }

    public void setProvince(String province) {
        MemberAddressDTODTO.Builder builder = this.dtoInstance.toBuilder();
        if (province == null) {
            builder.clearProvince();
        } else {
            builder.setProvince(province);
        }
        this.dtoInstance = builder.build();
    }

    public String getCity() {
        return this.dtoInstance.getCity();
    }

    public void setCity(String city) {
        MemberAddressDTODTO.Builder builder = this.dtoInstance.toBuilder();
        if (city == null) {
            builder.clearCity();
        } else {
            builder.setCity(city);
        }
        this.dtoInstance = builder.build();
    }

    public String getArea() {
        return this.dtoInstance.getArea();
    }

    public void setArea(String area) {
        MemberAddressDTODTO.Builder builder = this.dtoInstance.toBuilder();
        if (area == null) {
            builder.clearArea();
        } else {
            builder.setArea(area);
        }
        this.dtoInstance = builder.build();
    }

    public String getDetailAddress() {
        return this.dtoInstance.getDetailAddress();
    }

    public void setDetailAddress(String detailAddress) {
        MemberAddressDTODTO.Builder builder = this.dtoInstance.toBuilder();
        if (detailAddress == null) {
            builder.clearDetailAddress();
        } else {
            builder.setDetailAddress(detailAddress);
        }
        this.dtoInstance = builder.build();
    }

    public Integer getDefaulted() {
        return this.dtoInstance.getDefaulted();
    }

    public void setDefaulted(Integer defaulted) {
        MemberAddressDTODTO.Builder builder = this.dtoInstance.toBuilder();
        if (defaulted == null) {
            builder.clearDefaulted();
        } else {
            builder.setDefaulted(defaulted);
        }
        this.dtoInstance = builder.build();
    }
}

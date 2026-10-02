package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.memberaddressdto.MemberAddressDTODTO;

/**
 * Auto-generated DTO gRPC client
 * Wraps {@link MemberAddressDTODTO} and exposes the same API as the original
 * {@code MemberAddressDTO} class using composition.
 */
public class MemberAddressDTO {

    private MemberAddressDTODTO dtoInstance;

    /**
     * No-argument constructor matching the original class API.
     * Initializes an empty {@link MemberAddressDTODTO}.
     */
    public MemberAddressDTO() {
        this.dtoInstance = MemberAddressDTODTO.newBuilder().build();
    }

    /**
     * Constructor to initialize from a DTO instance.
     * Enables {@link #fromDTO(MemberAddressDTODTO)} and {@link #toDTO()}.
     */
    public MemberAddressDTO(MemberAddressDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Converts this client wrapper to the underlying proto DTO.
     * @return the internal {@link MemberAddressDTODTO}
     */
    public MemberAddressDTODTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a client wrapper from a proto DTO instance.
     * @param dtoInstance the proto DTO
     * @return a new {@link MemberAddressDTO} wrapping the given DTO
     */
    public static MemberAddressDTO fromDTO(MemberAddressDTODTO dtoInstance) {
        return new MemberAddressDTO(dtoInstance);
    }

    // --- Getters and Setters ---

    public Long getId() {
        return dtoInstance.getId();
    }

    public void setId(Long id) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setId(id == null ? 0L : id)
                .build();
    }

    public Long getMemberId() {
        return dtoInstance.getMemberId();
    }

    public void setMemberId(Long memberId) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setMemberId(memberId == null ? 0L : memberId)
                .build();
    }

    public String getConsigneeName() {
        return dtoInstance.getConsigneeName();
    }

    public void setConsigneeName(String consigneeName) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setConsigneeName(consigneeName == null ? "" : consigneeName)
                .build();
    }

    public String getConsigneeMobile() {
        return dtoInstance.getConsigneeMobile();
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setConsigneeMobile(consigneeMobile == null ? "" : consigneeMobile)
                .build();
    }

    public String getProvince() {
        return dtoInstance.getProvince();
    }

    public void setProvince(String province) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setProvince(province == null ? "" : province)
                .build();
    }

    public String getCity() {
        return dtoInstance.getCity();
    }

    public void setCity(String city) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setCity(city == null ? "" : city)
                .build();
    }

    public String getArea() {
        return dtoInstance.getArea();
    }

    public void setArea(String area) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setArea(area == null ? "" : area)
                .build();
    }

    public String getDetailAddress() {
        return dtoInstance.getDetailAddress();
    }

    public void setDetailAddress(String detailAddress) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setDetailAddress(detailAddress == null ? "" : detailAddress)
                .build();
    }

    public Integer getDefaulted() {
        return dtoInstance.getDefaulted();
    }

    public void setDefaulted(Integer defaulted) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setDefaulted(defaulted == null ? 0 : defaulted)
                .build();
    }
}


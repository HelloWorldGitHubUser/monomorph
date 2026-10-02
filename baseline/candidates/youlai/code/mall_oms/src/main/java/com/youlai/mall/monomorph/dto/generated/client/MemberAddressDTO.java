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
        dtoInstance.setId(id == null ? 0L : id);
    }

    public Long getMemberId() {
        return dtoInstance.getMemberId();
    }

    public void setMemberId(Long memberId) {
        dtoInstance.setMemberId(memberId == null ? 0L : memberId);
    }

    public String getConsigneeName() {
        return dtoInstance.getConsigneeName();
    }

    public void setConsigneeName(String consigneeName) {
        dtoInstance.setConsigneeName(consigneeName == null ? "" : consigneeName);
    }

    public String getConsigneeMobile() {
        return dtoInstance.getConsigneeMobile();
    }

    public void setConsigneeMobile(String consigneeMobile) {
        dtoInstance.setConsigneeMobile(consigneeMobile == null ? "" : consigneeMobile);
    }

    public String getProvince() {
        return dtoInstance.getProvince();
    }

    public void setProvince(String province) {
        dtoInstance.setProvince(province == null ? "" : province);
    }

    public String getCity() {
        return dtoInstance.getCity();
    }

    public void setCity(String city) {
        dtoInstance.setCity(city == null ? "" : city);
    }

    public String getArea() {
        return dtoInstance.getArea();
    }

    public void setArea(String area) {
        dtoInstance.setArea(area == null ? "" : area);
    }

    public String getDetailAddress() {
        return dtoInstance.getDetailAddress();
    }

    public void setDetailAddress(String detailAddress) {
        dtoInstance.setDetailAddress(detailAddress == null ? "" : detailAddress);
    }

    public Integer getDefaulted() {
        return dtoInstance.getDefaulted();
    }

    public void setDefaulted(Integer defaulted) {
        dtoInstance.setDefaulted(defaulted == null ? 0 : defaulted);
    }
}
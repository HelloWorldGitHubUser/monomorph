package com.youlai.mall.monomorph.dto.generated.client;

// gRPC imports
import com.youlai.mall.monomorph.dto.generated.proto.memberaddressdto.MemberAddressDTODTO;

/**
 * Auto-generated DTO gRPC client for {@link MemberAddressDTODTO}.
 * Provides API-compatible access to member address data using composition.
 */
public class MemberAddressDTO {
    private MemberAddressDTODTO dtoInstance;

    /**
     * Default constructor for API compatibility with the original class.
     */
    public MemberAddressDTO() {
        this.dtoInstance = MemberAddressDTODTO.getDefaultInstance();
    }

    /**
     * Constructs a client from an existing DTO instance.
     *
     * @param dtoInstance the protobuf DTO instance
     */
    public MemberAddressDTO(MemberAddressDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance != null ? dtoInstance : MemberAddressDTODTO.getDefaultInstance();
    }

    // mapping methods
    public MemberAddressDTODTO toDTO() {
        return this.dtoInstance;
    }

    public static MemberAddressDTO fromDTO(MemberAddressDTODTO dtoInstance) {
        return new MemberAddressDTO(dtoInstance);
    }

    // implementation of the gRPC exposed methods
    // No service methods are defined for this DTO-only proto.

    // --- START OF DTO GETTERS AND SETTERS ---

    public Long getId() {
        return dtoInstance.getId();
    }

    public void setId(Long id) {
        dtoInstance = dtoInstance.toBuilder().setId(id == null ? 0L : id).build();
    }

    public Long getMemberId() {
        return dtoInstance.getMemberId();
    }

    public void setMemberId(Long memberId) {
        dtoInstance = dtoInstance.toBuilder().setMemberId(memberId == null ? 0L : memberId).build();
    }

    public String getConsigneeName() {
        return dtoInstance.getConsigneeName();
    }

    public void setConsigneeName(String consigneeName) {
        dtoInstance = dtoInstance.toBuilder().setConsigneeName(consigneeName == null ? "" : consigneeName).build();
    }

    public String getConsigneeMobile() {
        return dtoInstance.getConsigneeMobile();
    }

    public void setConsigneeMobile(String consigneeMobile) {
        dtoInstance = dtoInstance.toBuilder().setConsigneeMobile(consigneeMobile == null ? "" : consigneeMobile).build();
    }

    public String getProvince() {
        return dtoInstance.getProvince();
    }

    public void setProvince(String province) {
        dtoInstance = dtoInstance.toBuilder().setProvince(province == null ? "" : province).build();
    }

    public String getCity() {
        return dtoInstance.getCity();
    }

    public void setCity(String city) {
        dtoInstance = dtoInstance.toBuilder().setCity(city == null ? "" : city).build();
    }

    public String getArea() {
        return dtoInstance.getArea();
    }

    public void setArea(String area) {
        dtoInstance = dtoInstance.toBuilder().setArea(area == null ? "" : area).build();
    }

    public String getDetailAddress() {
        return dtoInstance.getDetailAddress();
    }

    public void setDetailAddress(String detailAddress) {
        dtoInstance = dtoInstance.toBuilder().setDetailAddress(detailAddress == null ? "" : detailAddress).build();
    }

    public Integer getDefaulted() {
        return dtoInstance.getDefaulted();
    }

    public void setDefaulted(Integer defaulted) {
        dtoInstance = dtoInstance.toBuilder().setDefaulted(defaulted == null ? 0 : defaulted).build();
    }

    // --- END OF DTO GETTERS AND SETTERS ---
}
package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.producthistoryvo.*;

/**
 * Auto-generated DTO gRPC client
 * {@link ProductHistoryVO} and {@link ProductHistoryVODTO}.
 */
public class ProductHistoryVO {
    private ProductHistoryVODTO dtoInstance;

    public ProductHistoryVO() {
        this.dtoInstance = ProductHistoryVODTO.getDefaultInstance();
    }

    public ProductHistoryVO(ProductHistoryVODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public ProductHistoryVODTO toDTO() {
        return this.dtoInstance;
    }

    public static ProductHistoryVO fromDTO(ProductHistoryVODTO dtoInstance) {
        return new ProductHistoryVO(dtoInstance);
    }

    // No gRPC service methods are defined for ProductHistoryVODTO.

    // --- START OF DTO GETTERS AND SETTERS ---
    public Long getId() {
        return dtoInstance.getId();
    }

    public void setId(Long id) {
        dtoInstance = dtoInstance.toBuilder().setId(id).build();
    }

    public String getName() {
        return dtoInstance.getName();
    }

    public void setName(String name) {
        dtoInstance = dtoInstance.toBuilder().setName(name).build();
    }

    public String getPicUrl() {
        return dtoInstance.getPicUrl();
    }

    public void setPicUrl(String picUrl) {
        dtoInstance = dtoInstance.toBuilder().setPicUrl(picUrl).build();
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}
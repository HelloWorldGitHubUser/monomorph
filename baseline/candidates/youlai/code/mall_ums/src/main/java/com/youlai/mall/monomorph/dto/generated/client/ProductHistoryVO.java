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
        this.dtoInstance = dtoInstance == null
                ? ProductHistoryVODTO.getDefaultInstance()
                : dtoInstance;
    }

    // Mapping methods
    public ProductHistoryVODTO toDTO() {
        return this.dtoInstance;
    }

    public static ProductHistoryVO fromDTO(ProductHistoryVODTO dtoInstance) {
        return new ProductHistoryVO(dtoInstance);
    }

    // Implementation of the gRPC exposed methods
    // None: this DTO is not associated with a gRPC service definition.

    // --- START OF DTO GETTERS AND SETTERS ---
    public long getId() {
        return dtoInstance.getId();
    }

    public void setId(long id) {
        this.dtoInstance = this.dtoInstance.toBuilder().setId(id).build();
    }

    public String getName() {
        return dtoInstance.getName();
    }

    public void setName(String name) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setName(name == null ? "" : name)
                .build();
    }

    public String getPicUrl() {
        return dtoInstance.getPicUrl();
    }

    public void setPicUrl(String picUrl) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setPicUrl(picUrl == null ? "" : picUrl)
                .build();
    }

    public long getSerialVersionUID() {
        return dtoInstance.getSerialVersionUID();
    }

    public void setSerialVersionUID(long serialVersionUID) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setSerialVersionUID(serialVersionUID)
                .build();
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}
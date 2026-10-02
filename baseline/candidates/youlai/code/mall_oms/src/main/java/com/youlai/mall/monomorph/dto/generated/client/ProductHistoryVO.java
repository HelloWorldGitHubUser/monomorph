package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.producthistoryvo.ProductHistoryVODTO;

/**
 * Auto-generated DTO gRPC client for ProductHistoryVO.
 */
public class ProductHistoryVO {

    private ProductHistoryVODTO dtoInstance;

    public ProductHistoryVO() {
        this.dtoInstance = ProductHistoryVODTO.newBuilder().build();
    }

    public ProductHistoryVO(ProductHistoryVODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public ProductHistoryVODTO toDTO() {
        return dtoInstance;
    }

    public static ProductHistoryVO fromDTO(ProductHistoryVODTO dtoInstance) {
        return new ProductHistoryVO(dtoInstance);
    }

    public Long getId() {
        return dtoInstance.getId();
    }

    public void setId(Long id) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setId(id == null ? 0L : id)
                .build();
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
}

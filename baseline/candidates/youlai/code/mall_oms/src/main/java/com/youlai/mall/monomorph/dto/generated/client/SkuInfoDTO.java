package com.youlai.mall.monomorph.dto.generated.client;

import com.youlai.mall.monomorph.dto.generated.proto.skuinfodto.*;

/**
 * Auto-generated DTO gRPC client for {@code SkuInfoDTO}.
 * Uses composition to store data in a {@link SkuInfoDTODTO} instance.
 */
public class SkuInfoDTO {

    private SkuInfoDTODTO dtoInstance;

    /**
     * Public no-arg constructor matching the original class API.
     * Initializes an empty default DTO.
     */
    public SkuInfoDTO() {
        this(SkuInfoDTODTO.newBuilder().build());
    }

    /**
     * Private constructor for internal DTO composition.
     * Used by {@link #fromDTO(SkuInfoDTODTO)} and the no-arg constructor.
     */
    private SkuInfoDTO(SkuInfoDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Converts this client object to its DTO representation.
     */
    public SkuInfoDTODTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a client instance from a DTO.
     */
    public static SkuInfoDTO fromDTO(SkuInfoDTODTO dtoInstance) {
        return new SkuInfoDTO(dtoInstance);
    }

    // --- Getter and Setter implementations delegating to the DTO ---

    public Long getId() {
        return dtoInstance.getId();
    }

    public void setId(Long id) {
        if (id == null) {
            dtoInstance = dtoInstance.toBuilder().clearId().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setId(id).build();
        }
    }

    public String getSkuSn() {
        return dtoInstance.getSkuSn();
    }

    public void setSkuSn(String skuSn) {
        if (skuSn == null) {
            dtoInstance = dtoInstance.toBuilder().clearSkuSn().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setSkuSn(skuSn).build();
        }
    }

    public String getSkuName() {
        return dtoInstance.getSkuName();
    }

    public void setSkuName(String skuName) {
        if (skuName == null) {
            dtoInstance = dtoInstance.toBuilder().clearSkuName().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setSkuName(skuName).build();
        }
    }

    public String getPicUrl() {
        return dtoInstance.getPicUrl();
    }

    public void setPicUrl(String picUrl) {
        if (picUrl == null) {
            dtoInstance = dtoInstance.toBuilder().clearPicUrl().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setPicUrl(picUrl).build();
        }
    }

    public Long getPrice() {
        return dtoInstance.getPrice();
    }

    public void setPrice(Long price) {
        if (price == null) {
            dtoInstance = dtoInstance.toBuilder().clearPrice().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setPrice(price).build();
        }
    }

    public Integer getStock() {
        return dtoInstance.getStock();
    }

    public void setStock(Integer stock) {
        if (stock == null) {
            dtoInstance = dtoInstance.toBuilder().clearStock().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setStock(stock).build();
        }
    }

    public String getSpuName() {
        return dtoInstance.getSpuName();
    }

    public void setSpuName(String spuName) {
        if (spuName == null) {
            dtoInstance = dtoInstance.toBuilder().clearSpuName().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setSpuName(spuName).build();
        }
    }
}
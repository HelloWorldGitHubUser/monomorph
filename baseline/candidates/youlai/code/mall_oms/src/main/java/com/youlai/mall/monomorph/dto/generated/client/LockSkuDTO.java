package com.youlai.mall.monomorph.dto.generated.client;

// gRPC imports
import com.youlai.mall.monomorph.dto.generated.proto.lockskudto.*;

/**
 * Auto-generated DTO gRPC client
 * {@link LockSkuDTO} and {@link LockSkuDTODTO}.
 */
public class LockSkuDTO {

    private LockSkuDTODTO dtoInstance;

    /**
     * No-argument constructor matching the original API.
     * Creates an empty underlying protobuf DTO.
     */
    public LockSkuDTO() {
        this.dtoInstance = LockSkuDTODTO.newBuilder().build();
    }

    /**
     * All-arguments constructor matching the original API.
     * Null values are coerced to protobuf default (0) for scalar fields.
     */
    public LockSkuDTO(Long skuId, Integer quantity) {
        LockSkuDTODTO.Builder builder = LockSkuDTODTO.newBuilder();
        if (skuId != null) {
            builder.setSkuId(skuId);
        }
        if (quantity != null) {
            builder.setQuantity(quantity);
        }
        this.dtoInstance = builder.build();
    }

    /**
     * Constructor accepting an existing protobuf DTO instance.
     * Used by {@link #fromDTO(LockSkuDTODTO)}.
     */
    public LockSkuDTO(LockSkuDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Converts this client wrapper back to its protobuf DTO representation.
     */
    public LockSkuDTODTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a client wrapper from a protobuf DTO.
     */
    public static LockSkuDTO fromDTO(LockSkuDTODTO dtoInstance) {
        return new LockSkuDTO(dtoInstance);
    }

    // --- START OF DTO GETTERS AND SETTERS ---

    public Long getSkuId() {
        return dtoInstance.getSkuId();
    }

    public void setSkuId(Long skuId) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setSkuId(skuId == null ? 0L : skuId)
                .build();
    }

    public Integer getQuantity() {
        return dtoInstance.getQuantity();
    }

    public void setQuantity(Integer quantity) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setQuantity(quantity == null ? 0 : quantity)
                .build();
    }

    // --- END OF DTO GETTERS AND SETTERS ---
}

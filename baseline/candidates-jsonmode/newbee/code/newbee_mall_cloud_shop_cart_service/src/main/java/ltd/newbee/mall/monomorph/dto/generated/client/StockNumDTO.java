package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.stocknumdto.StockNumDTODTO;

/**
 * Auto-generated DTO gRPC client
 * {@link StockNumDTO} and {@link StockNumDTODTO}.
 */
public class StockNumDTO {
    private StockNumDTODTO dtoInstance;

    /**
     * No-args constructor matching the original class API.
     * Creates an empty DTO instance.
     */
    public StockNumDTO() {
        this.dtoInstance = StockNumDTODTO.getDefaultInstance();
    }

    /**
     * Private DTO constructor used by fromDTO/toDTO.
     */
    private StockNumDTO(StockNumDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance == null ? StockNumDTODTO.getDefaultInstance() : dtoInstance;
    }

    /**
     * Converts this client wrapper to the underlying DTO.
     */
    public StockNumDTODTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a client wrapper from a DTO instance.
     */
    public static StockNumDTO fromDTO(StockNumDTODTO dtoInstance) {
        return new StockNumDTO(dtoInstance);
    }

    /**
     * Returns the goodsId, matching the original Long return type.
     */
    public Long getGoodsId() {
        return dtoInstance.getGoodsId();
    }

    /**
     * Sets the goodsId. A null input is stored as the protobuf default value (0).
     */
    public void setGoodsId(Long goodsId) {
        long value = (goodsId != null) ? goodsId : 0L;
        this.dtoInstance = this.dtoInstance.toBuilder().setGoodsId(value).build();
    }

    /**
     * Returns the goodsCount, matching the original Integer return type.
     */
    public Integer getGoodsCount() {
        return dtoInstance.getGoodsCount();
    }

    /**
     * Sets the goodsCount. A null input is stored as the protobuf default value (0).
     */
    public void setGoodsCount(Integer goodsCount) {
        int value = (goodsCount != null) ? goodsCount : 0;
        this.dtoInstance = this.dtoInstance.toBuilder().setGoodsCount(value).build();
    }
}
package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.stocknumdto.*;

public class StockNumDTO {
    private StockNumDTODTO dtoInstance;

    // No-arg constructor matching the original class API
    public StockNumDTO() {
        this.dtoInstance = StockNumDTODTO.getDefaultInstance();
    }

    // Constructor from DTO for mapping support
    public StockNumDTO(StockNumDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public StockNumDTODTO toDTO() {
        return this.dtoInstance;
    }

    public static StockNumDTO fromDTO(StockNumDTODTO dtoInstance) {
        return new StockNumDTO(dtoInstance);
    }

    // --- START OF DTO GETTERS AND SETTERS ---
    public Long getGoodsId() {
        return dtoInstance.getGoodsId();
    }

    public void setGoodsId(Long goodsId) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setGoodsId(goodsId == null ? 0L : goodsId)
                .build();
    }

    public Integer getGoodsCount() {
        return dtoInstance.getGoodsCount();
    }

    public void setGoodsCount(Integer goodsCount) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setGoodsCount(goodsCount == null ? 0 : goodsCount)
                .build();
    }
    // --- END OF DTO GETTERS AND SETTERS ---
}
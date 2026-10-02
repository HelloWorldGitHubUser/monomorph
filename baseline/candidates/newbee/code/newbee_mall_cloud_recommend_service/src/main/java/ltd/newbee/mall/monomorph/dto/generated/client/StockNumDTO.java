package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.stocknumdto.*;

public class StockNumDTO {
    private StockNumDTODTO dtoInstance;

    public StockNumDTO() {
        this.dtoInstance = StockNumDTODTO.newBuilder().build();
    }

    public StockNumDTO(StockNumDTODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public StockNumDTODTO toDTO() {
        return this.dtoInstance;
    }

    public static StockNumDTO fromDTO(StockNumDTODTO dtoInstance) {
        return new StockNumDTO(dtoInstance);
    }

    // Getters and setters delegating to the internal DTO
    public Long getGoodsId() {
        return dtoInstance.getGoodsId();
    }

    public void setGoodsId(Long goodsId) {
        dtoInstance = dtoInstance.toBuilder()
                .setGoodsId(goodsId == null ? 0L : goodsId)
                .build();
    }

    public Integer getGoodsCount() {
        return dtoInstance.getGoodsCount();
    }

    public void setGoodsCount(Integer goodsCount) {
        dtoInstance = dtoInstance.toBuilder()
                .setGoodsCount(goodsCount == null ? 0 : goodsCount)
                .build();
    }
}
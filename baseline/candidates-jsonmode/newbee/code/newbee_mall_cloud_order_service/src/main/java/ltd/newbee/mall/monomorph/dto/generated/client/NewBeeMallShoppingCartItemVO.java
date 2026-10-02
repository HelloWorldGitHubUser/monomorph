package ltd.newbee.mall.monomorph.dto.generated.client;

// gRPC imports
import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallshoppingcartitemvo.*;

/**
 * Auto-generated DTO gRPC client
 * {@link NewBeeMallShoppingCartItemVO} and {@link NewBeeMallShoppingCartItemVODTO}.
 */
public class NewBeeMallShoppingCartItemVO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private NewBeeMallShoppingCartItemVODTO dtoInstance;

    /**
     * No-argument constructor matching the original Lombok-generated constructor.
     */
    public NewBeeMallShoppingCartItemVO() {
        this.dtoInstance = NewBeeMallShoppingCartItemVODTO.getDefaultInstance();
    }

    /**
     * Private constructor accepting the internal DTO instance.
     * Used by {@link #fromDTO(NewBeeMallShoppingCartItemVODTO)}.
     */
    private NewBeeMallShoppingCartItemVO(NewBeeMallShoppingCartItemVODTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    // Mapping methods
    public NewBeeMallShoppingCartItemVODTO toDTO() {
        return this.dtoInstance;
    }

    public static NewBeeMallShoppingCartItemVO fromDTO(NewBeeMallShoppingCartItemVODTO dtoInstance) {
        return new NewBeeMallShoppingCartItemVO(dtoInstance);
    }

    // Getters and setters corresponding to the DTO fields

    public Long getCartItemId() {
        return dtoInstance.getCartItemId();
    }

    public void setCartItemId(Long cartItemId) {
        dtoInstance = dtoInstance.toBuilder()
                .setCartItemId(cartItemId == null ? 0L : cartItemId)
                .build();
    }

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

    public String getGoodsName() {
        return dtoInstance.getGoodsName();
    }

    public void setGoodsName(String goodsName) {
        dtoInstance = dtoInstance.toBuilder()
                .setGoodsName(goodsName == null ? "" : goodsName)
                .build();
    }

    public String getGoodsCoverImg() {
        return dtoInstance.getGoodsCoverImg();
    }

    public void setGoodsCoverImg(String goodsCoverImg) {
        dtoInstance = dtoInstance.toBuilder()
                .setGoodsCoverImg(goodsCoverImg == null ? "" : goodsCoverImg)
                .build();
    }

    public Integer getSellingPrice() {
        return dtoInstance.getSellingPrice();
    }

    public void setSellingPrice(Integer sellingPrice) {
        dtoInstance = dtoInstance.toBuilder()
                .setSellingPrice(sellingPrice == null ? 0 : sellingPrice)
                .build();
    }
}
package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallgoods.NewBeeMallGoodsDTO;
import com.google.protobuf.util.Timestamps;
import java.util.Date;

/**
 * Auto-generated DTO gRPC client representing NewBeeMallGoods.
 * Uses composition with NewBeeMallGoodsDTO for data storage and mapping.
 */
public class NewBeeMallGoods {
    private NewBeeMallGoodsDTO dtoInstance;

    /**
     * Private constructor accepting a DTO instance. Used by {@link #fromDTO(NewBeeMallGoodsDTO)}.
     */
    private NewBeeMallGoods(NewBeeMallGoodsDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    /**
     * Public no-args constructor compatible with the original Lombok @Data class.
     */
    public NewBeeMallGoods() {
        this.dtoInstance = NewBeeMallGoodsDTO.newBuilder().build();
    }

    /**
     * Converts this client to its underlying DTO representation.
     */
    public NewBeeMallGoodsDTO toDTO() {
        return this.dtoInstance;
    }

    /**
     * Creates a client instance from a DTO.
     */
    public static NewBeeMallGoods fromDTO(NewBeeMallGoodsDTO dtoInstance) {
        return new NewBeeMallGoods(dtoInstance);
    }

    // Getters and setters matching the original class API, delegating to the DTO.

    public Long getGoodsId() {
        return dtoInstance.getGoodsId();
    }

    public void setGoodsId(Long goodsId) {
        dtoInstance = dtoInstance.toBuilder().setGoodsId(goodsId == null ? 0L : goodsId).build();
    }

    public String getGoodsName() {
        return dtoInstance.getGoodsName();
    }

    public void setGoodsName(String goodsName) {
        dtoInstance = dtoInstance.toBuilder().setGoodsName(goodsName == null ? "" : goodsName).build();
    }

    public String getGoodsIntro() {
        return dtoInstance.getGoodsIntro();
    }

    public void setGoodsIntro(String goodsIntro) {
        dtoInstance = dtoInstance.toBuilder().setGoodsIntro(goodsIntro == null ? "" : goodsIntro).build();
    }

    public Long getGoodsCategoryId() {
        return dtoInstance.getGoodsCategoryId();
    }

    public void setGoodsCategoryId(Long goodsCategoryId) {
        dtoInstance = dtoInstance.toBuilder().setGoodsCategoryId(goodsCategoryId == null ? 0L : goodsCategoryId).build();
    }

    public String getGoodsCoverImg() {
        return dtoInstance.getGoodsCoverImg();
    }

    public void setGoodsCoverImg(String goodsCoverImg) {
        dtoInstance = dtoInstance.toBuilder().setGoodsCoverImg(goodsCoverImg == null ? "" : goodsCoverImg).build();
    }

    public String getGoodsCarousel() {
        return dtoInstance.getGoodsCarousel();
    }

    public void setGoodsCarousel(String goodsCarousel) {
        dtoInstance = dtoInstance.toBuilder().setGoodsCarousel(goodsCarousel == null ? "" : goodsCarousel).build();
    }

    public Integer getOriginalPrice() {
        return dtoInstance.getOriginalPrice();
    }

    public void setOriginalPrice(Integer originalPrice) {
        dtoInstance = dtoInstance.toBuilder().setOriginalPrice(originalPrice == null ? 0 : originalPrice).build();
    }

    public Integer getSellingPrice() {
        return dtoInstance.getSellingPrice();
    }

    public void setSellingPrice(Integer sellingPrice) {
        dtoInstance = dtoInstance.toBuilder().setSellingPrice(sellingPrice == null ? 0 : sellingPrice).build();
    }

    public Integer getStockNum() {
        return dtoInstance.getStockNum();
    }

    public void setStockNum(Integer stockNum) {
        dtoInstance = dtoInstance.toBuilder().setStockNum(stockNum == null ? 0 : stockNum).build();
    }

    public String getTag() {
        return dtoInstance.getTag();
    }

    public void setTag(String tag) {
        dtoInstance = dtoInstance.toBuilder().setTag(tag == null ? "" : tag).build();
    }

    public Byte getGoodsSellStatus() {
        return (byte) dtoInstance.getGoodsSellStatus();
    }

    public void setGoodsSellStatus(Byte goodsSellStatus) {
        dtoInstance = dtoInstance.toBuilder().setGoodsSellStatus(goodsSellStatus == null ? 0 : goodsSellStatus.intValue()).build();
    }

    public Integer getCreateUser() {
        return dtoInstance.getCreateUser();
    }

    public void setCreateUser(Integer createUser) {
        dtoInstance = dtoInstance.toBuilder().setCreateUser(createUser == null ? 0 : createUser).build();
    }

    public Date getCreateTime() {
        return dtoInstance.hasCreateTime() ? new Date(Timestamps.toMillis(dtoInstance.getCreateTime())) : null;
    }

    public void setCreateTime(Date createTime) {
        if (createTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearCreateTime().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setCreateTime(Timestamps.fromMillis(createTime.getTime())).build();
        }
    }

    public Integer getUpdateUser() {
        return dtoInstance.getUpdateUser();
    }

    public void setUpdateUser(Integer updateUser) {
        dtoInstance = dtoInstance.toBuilder().setUpdateUser(updateUser == null ? 0 : updateUser).build();
    }

    public Date getUpdateTime() {
        return dtoInstance.hasUpdateTime() ? new Date(Timestamps.toMillis(dtoInstance.getUpdateTime())) : null;
    }

    public void setUpdateTime(Date updateTime) {
        if (updateTime == null) {
            dtoInstance = dtoInstance.toBuilder().clearUpdateTime().build();
        } else {
            dtoInstance = dtoInstance.toBuilder().setUpdateTime(Timestamps.fromMillis(updateTime.getTime())).build();
        }
    }

    public String getGoodsDetailContent() {
        return dtoInstance.getGoodsDetailContent();
    }

    public void setGoodsDetailContent(String goodsDetailContent) {
        dtoInstance = dtoInstance.toBuilder().setGoodsDetailContent(goodsDetailContent == null ? "" : goodsDetailContent).build();
    }
}
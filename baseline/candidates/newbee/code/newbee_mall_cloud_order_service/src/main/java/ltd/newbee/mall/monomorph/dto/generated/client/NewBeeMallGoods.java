package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallgoods.*;
import com.google.protobuf.Timestamp;
import java.util.Date;

/**
 * Auto-generated DTO gRPC client using composition over {@link NewBeeMallGoodsDTO}.
 */
public class NewBeeMallGoods {
    private NewBeeMallGoodsDTO dtoInstance;

    public NewBeeMallGoods() {
        this.dtoInstance = NewBeeMallGoodsDTO.newBuilder().build();
    }

    public NewBeeMallGoods(NewBeeMallGoodsDTO dtoInstance) {
        this.dtoInstance = dtoInstance == null ? NewBeeMallGoodsDTO.getDefaultInstance() : dtoInstance;
    }

    // Mapping methods
    public NewBeeMallGoodsDTO toDTO() {
        return this.dtoInstance;
    }

    public static NewBeeMallGoods fromDTO(NewBeeMallGoodsDTO dtoInstance) {
        return new NewBeeMallGoods(dtoInstance);
    }

    // Getters and setters corresponding to DTO fields

    public Long getGoodsId() {
        return dtoInstance.getGoodsId();
    }

    public void setGoodsId(Long goodsId) {
        dtoInstance = dtoInstance.toBuilder()
                .setGoodsId(goodsId == null ? 0L : goodsId)
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

    public String getGoodsIntro() {
        return dtoInstance.getGoodsIntro();
    }

    public void setGoodsIntro(String goodsIntro) {
        dtoInstance = dtoInstance.toBuilder()
                .setGoodsIntro(goodsIntro == null ? "" : goodsIntro)
                .build();
    }

    public Long getGoodsCategoryId() {
        return dtoInstance.getGoodsCategoryId();
    }

    public void setGoodsCategoryId(Long goodsCategoryId) {
        dtoInstance = dtoInstance.toBuilder()
                .setGoodsCategoryId(goodsCategoryId == null ? 0L : goodsCategoryId)
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

    public String getGoodsCarousel() {
        return dtoInstance.getGoodsCarousel();
    }

    public void setGoodsCarousel(String goodsCarousel) {
        dtoInstance = dtoInstance.toBuilder()
                .setGoodsCarousel(goodsCarousel == null ? "" : goodsCarousel)
                .build();
    }

    public Integer getOriginalPrice() {
        return dtoInstance.getOriginalPrice();
    }

    public void setOriginalPrice(Integer originalPrice) {
        dtoInstance = dtoInstance.toBuilder()
                .setOriginalPrice(originalPrice == null ? 0 : originalPrice)
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

    public Integer getStockNum() {
        return dtoInstance.getStockNum();
    }

    public void setStockNum(Integer stockNum) {
        dtoInstance = dtoInstance.toBuilder()
                .setStockNum(stockNum == null ? 0 : stockNum)
                .build();
    }

    public String getTag() {
        return dtoInstance.getTag();
    }

    public void setTag(String tag) {
        dtoInstance = dtoInstance.toBuilder()
                .setTag(tag == null ? "" : tag)
                .build();
    }

    public Byte getGoodsSellStatus() {
        return (byte) dtoInstance.getGoodsSellStatus();
    }

    public void setGoodsSellStatus(Byte goodsSellStatus) {
        dtoInstance = dtoInstance.toBuilder()
                .setGoodsSellStatus(goodsSellStatus == null ? 0 : goodsSellStatus.intValue())
                .build();
    }

    public Integer getCreateUser() {
        return dtoInstance.getCreateUser();
    }

    public void setCreateUser(Integer createUser) {
        dtoInstance = dtoInstance.toBuilder()
                .setCreateUser(createUser == null ? 0 : createUser)
                .build();
    }

    public Date getCreateTime() {
        return toDate(dtoInstance.getCreateTime());
    }

    public void setCreateTime(Date createTime) {
        dtoInstance = dtoInstance.toBuilder()
                .setCreateTime(toTimestamp(createTime))
                .build();
    }

    public Integer getUpdateUser() {
        return dtoInstance.getUpdateUser();
    }

    public void setUpdateUser(Integer updateUser) {
        dtoInstance = dtoInstance.toBuilder()
                .setUpdateUser(updateUser == null ? 0 : updateUser)
                .build();
    }

    public Date getUpdateTime() {
        return toDate(dtoInstance.getUpdateTime());
    }

    public void setUpdateTime(Date updateTime) {
        dtoInstance = dtoInstance.toBuilder()
                .setUpdateTime(toTimestamp(updateTime))
                .build();
    }

    public String getGoodsDetailContent() {
        return dtoInstance.getGoodsDetailContent();
    }

    public void setGoodsDetailContent(String goodsDetailContent) {
        dtoInstance = dtoInstance.toBuilder()
                .setGoodsDetailContent(goodsDetailContent == null ? "" : goodsDetailContent)
                .build();
    }

    // Helper methods for Date <-> Timestamp conversion

    private static Timestamp toTimestamp(Date date) {
        if (date == null) {
            return Timestamp.getDefaultInstance();
        }
        long ms = date.getTime();
        return Timestamp.newBuilder()
                .setSeconds(ms / 1000)
                .setNanos((int) ((ms % 1000) * 1_000_000))
                .build();
    }

    private static Date toDate(Timestamp timestamp) {
        if (timestamp == null) {
            return new Date(0);
        }
        return new Date(timestamp.getSeconds() * 1000 + timestamp.getNanos() / 1_000_000);
    }
}

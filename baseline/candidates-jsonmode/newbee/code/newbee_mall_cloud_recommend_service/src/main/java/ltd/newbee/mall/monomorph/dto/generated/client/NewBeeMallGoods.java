package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.newbeemallgoods.NewBeeMallGoodsDTO;
import com.google.protobuf.Timestamp;

import java.util.Date;

/**
 * Auto-generated DTO gRPC client for {@link NewBeeMallGoodsDTO}.
 * This client exposes the same API as the original NewBeeMallGoods class
 * using composition instead of inheritance.
 */
public class NewBeeMallGoods {

    private NewBeeMallGoodsDTO dtoInstance;

    /**
     * No-arg constructor matching the original class API.
     */
    public NewBeeMallGoods() {
        this.dtoInstance = NewBeeMallGoodsDTO.newBuilder().build();
    }

    /**
     * Private constructor used by {@link #fromDTO(NewBeeMallGoodsDTO)}.
     */
    private NewBeeMallGoods(NewBeeMallGoodsDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public NewBeeMallGoodsDTO toDTO() {
        return this.dtoInstance;
    }

    public static NewBeeMallGoods fromDTO(NewBeeMallGoodsDTO dtoInstance) {
        return new NewBeeMallGoods(dtoInstance);
    }

    // --- Getter and Setter delegation ---

    public Long getGoodsId() {
        return dtoInstance.getGoodsId();
    }

    public void setGoodsId(Long goodsId) {
        dtoInstance.setGoodsId(goodsId == null ? 0L : goodsId.longValue());
    }

    public String getGoodsName() {
        return dtoInstance.getGoodsName();
    }

    public void setGoodsName(String goodsName) {
        dtoInstance.setGoodsName(goodsName == null ? "" : goodsName);
    }

    public String getGoodsIntro() {
        return dtoInstance.getGoodsIntro();
    }

    public void setGoodsIntro(String goodsIntro) {
        dtoInstance.setGoodsIntro(goodsIntro == null ? "" : goodsIntro);
    }

    public Long getGoodsCategoryId() {
        return dtoInstance.getGoodsCategoryId();
    }

    public void setGoodsCategoryId(Long goodsCategoryId) {
        dtoInstance.setGoodsCategoryId(goodsCategoryId == null ? 0L : goodsCategoryId.longValue());
    }

    public String getGoodsCoverImg() {
        return dtoInstance.getGoodsCoverImg();
    }

    public void setGoodsCoverImg(String goodsCoverImg) {
        dtoInstance.setGoodsCoverImg(goodsCoverImg == null ? "" : goodsCoverImg);
    }

    public String getGoodsCarousel() {
        return dtoInstance.getGoodsCarousel();
    }

    public void setGoodsCarousel(String goodsCarousel) {
        dtoInstance.setGoodsCarousel(goodsCarousel == null ? "" : goodsCarousel);
    }

    public Integer getOriginalPrice() {
        return dtoInstance.getOriginalPrice();
    }

    public void setOriginalPrice(Integer originalPrice) {
        dtoInstance.setOriginalPrice(originalPrice == null ? 0 : originalPrice.intValue());
    }

    public Integer getSellingPrice() {
        return dtoInstance.getSellingPrice();
    }

    public void setSellingPrice(Integer sellingPrice) {
        dtoInstance.setSellingPrice(sellingPrice == null ? 0 : sellingPrice.intValue());
    }

    public Integer getStockNum() {
        return dtoInstance.getStockNum();
    }

    public void setStockNum(Integer stockNum) {
        dtoInstance.setStockNum(stockNum == null ? 0 : stockNum.intValue());
    }

    public String getTag() {
        return dtoInstance.getTag();
    }

    public void setTag(String tag) {
        dtoInstance.setTag(tag == null ? "" : tag);
    }

    public Byte getGoodsSellStatus() {
        return Byte.valueOf((byte) dtoInstance.getGoodsSellStatus());
    }

    public void setGoodsSellStatus(Byte goodsSellStatus) {
        dtoInstance.setGoodsSellStatus(goodsSellStatus == null ? 0 : goodsSellStatus.byteValue());
    }

    public Integer getCreateUser() {
        return dtoInstance.getCreateUser();
    }

    public void setCreateUser(Integer createUser) {
        dtoInstance.setCreateUser(createUser == null ? 0 : createUser.intValue());
    }

    public Date getCreateTime() {
        Timestamp ts = dtoInstance.getCreateTime();
        if (ts == null) {
            return null;
        }
        return new Date(ts.getSeconds() * 1000L + ts.getNanos() / 1_000_000L);
    }

    public void setCreateTime(Date createTime) {
        if (createTime == null) {
            dtoInstance.setCreateTime(Timestamp.getDefaultInstance());
        } else {
            dtoInstance.setCreateTime(toTimestamp(createTime));
        }
    }

    public Integer getUpdateUser() {
        return dtoInstance.getUpdateUser();
    }

    public void setUpdateUser(Integer updateUser) {
        dtoInstance.setUpdateUser(updateUser == null ? 0 : updateUser.intValue());
    }

    public Date getUpdateTime() {
        Timestamp ts = dtoInstance.getUpdateTime();
        if (ts == null) {
            return null;
        }
        return new Date(ts.getSeconds() * 1000L + ts.getNanos() / 1_000_000L);
    }

    public void setUpdateTime(Date updateTime) {
        if (updateTime == null) {
            dtoInstance.setUpdateTime(Timestamp.getDefaultInstance());
        } else {
            dtoInstance.setUpdateTime(toTimestamp(updateTime));
        }
    }

    public String getGoodsDetailContent() {
        return dtoInstance.getGoodsDetailContent();
    }

    public void setGoodsDetailContent(String goodsDetailContent) {
        dtoInstance.setGoodsDetailContent(goodsDetailContent == null ? "" : goodsDetailContent);
    }

    /**
     * Converts a {@link Date} to a protobuf {@link Timestamp}.
     */
    private static Timestamp toTimestamp(Date date) {
        long millis = date.getTime();
        long seconds = millis / 1000L;
        int nanos = (int) ((millis % 1000L) * 1_000_000L);
        if (nanos < 0) {
            seconds -= 1;
            nanos += 1_000_000_000;
        }
        return Timestamp.newBuilder()
                .setSeconds(seconds)
                .setNanos(nanos)
                .build();
    }
}

package io.gulimall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单项 VO - 统一版本（合并自 order.vo 和 ware.vo）
 * 用于订单确认、库存锁定等场景
 */
@Data
public class OrderItemVo {
    private Long skuId;

    private Boolean check = true;

    private String title;

    private String image;

    /**
     * 商品套餐属性
     */
    private List<String> skuAttrValues;

    private BigDecimal price;

    private Integer count;

    private BigDecimal totalPrice;

    /** 商品重量 **/
    private BigDecimal weight = new BigDecimal("0.085");
}









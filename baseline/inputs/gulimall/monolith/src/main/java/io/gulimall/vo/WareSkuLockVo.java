package io.gulimall.vo;

import lombok.Data;

import java.util.List;

/**
 * 库存锁定请求 VO - 统一版本（合并自 order.vo 和 ware.vo）
 */
@Data
public class WareSkuLockVo {
    private String OrderSn;

    private List<OrderItemVo> locks;
}









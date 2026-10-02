package io.gulimall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 运费信息 VO - 统一版本（合并自 order.vo 和 ware.vo）
 */
@Data
public class FareVo {
    private MemberAddressVo address;

    private BigDecimal fare;
}









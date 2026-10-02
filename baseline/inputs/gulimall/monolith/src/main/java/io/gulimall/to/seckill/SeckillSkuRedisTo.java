package io.gulimall.to.seckill;

import io.gulimall.vo.seckill.SkuInfoVo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeckillSkuRedisTo {
    private Long id;
    private Long promotionId;
    private Long promotionSessionId;
    private Long skuId;
    private BigDecimal seckillPrice;
    private Integer seckillCount;
    private Integer seckillLimit;
    private Integer seckillSort;
    private SkuInfoVo skuInfoVo;
    private Long startTime;
    private Long endTime;
    private String randomCode;
}

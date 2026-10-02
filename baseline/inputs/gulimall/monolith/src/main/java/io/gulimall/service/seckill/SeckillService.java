package io.gulimall.service.seckill;

import io.gulimall.to.seckill.SeckillSkuRedisTo;

import java.util.List;

public interface SeckillService {
    void uploadSeckillSkuLatest3Days();

    List<SeckillSkuRedisTo> getCurrentSeckillSkus();

    SeckillSkuRedisTo getSeckillSkuInfo(Long skuId);

    String kill(String killId, String key, Integer num) throws InterruptedException;
}

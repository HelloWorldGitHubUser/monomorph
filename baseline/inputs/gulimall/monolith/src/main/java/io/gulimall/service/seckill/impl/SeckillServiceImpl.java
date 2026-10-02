package io.gulimall.service.seckill.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.gulimall.entity.coupon.SeckillSessionEntity;
import io.gulimall.entity.coupon.SeckillSkuRelationEntity;
import io.gulimall.entity.product.SkuInfoEntity;
import io.gulimall.interceptor.LoginInterceptor;
import io.gulimall.service.coupon.SeckillSessionService;
import io.gulimall.service.order.OrderService;
import io.gulimall.service.product.SkuInfoService;
import io.gulimall.service.seckill.SeckillService;
import io.gulimall.to.mq.SeckillOrderTo;
import io.gulimall.to.seckill.SeckillSkuRedisTo;
import io.gulimall.vo.MemberResponseVo;
import io.gulimall.vo.seckill.SkuInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service("seckillService")
public class SeckillServiceImpl implements SeckillService {

    private static final String SESSION_CACHE_PREFIX = "seckill:sessions:";
    private static final String SECKILL_CACHE_PREFIX = "seckill:skus";
    private static final String SKU_STOCK_PREFIX = "seckill:stock:";
    private static final String PURCHASE_PREFIX = "seckill:purchase:";

    private static final DefaultRedisScript<Long> ACQUIRE_STOCK_SCRIPT =
            new DefaultRedisScript<>(
                    "if redis.call('exists', KEYS[2]) == 1 then return -1 end "
                            + "local stock = tonumber(redis.call('get', KEYS[1])) "
                            + "local quantity = tonumber(ARGV[1]) "
                            + "if not stock or stock < quantity then return 0 end "
                            + "redis.call('decrby', KEYS[1], quantity) "
                            + "redis.call('psetex', KEYS[2], ARGV[2], ARGV[1]) "
                            + "return 1",
                    Long.class);

    private static final DefaultRedisScript<Long> ROLLBACK_STOCK_SCRIPT =
            new DefaultRedisScript<>(
                    "local quantity = redis.call('get', KEYS[2]) "
                            + "if not quantity then return 0 end "
                            + "redis.call('del', KEYS[2]) "
                            + "if redis.call('exists', KEYS[1]) == 1 then "
                            + "redis.call('incrby', KEYS[1], quantity) "
                            + "end "
                            + "return 1",
                    Long.class);

    @Autowired
    private SeckillSessionService seckillSessionService;

    @Autowired
    @Lazy
    private SkuInfoService skuInfoService;

    @Autowired
    @Lazy
    private OrderService orderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void uploadSeckillSkuLatest3Days() {
        List<SeckillSessionEntity> sessions =
                seckillSessionService.getSeckillSessionsIn3Days();
        if (sessions == null || sessions.isEmpty()) {
            return;
        }
        saveSeckillSession(sessions);
        saveSeckillSku(sessions);
    }

    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
        Set<String> keys = redisTemplate.keys(SESSION_CACHE_PREFIX + "*");
        if (keys == null || keys.isEmpty()) {
            return null;
        }

        long currentTime = System.currentTimeMillis();
        for (String cacheKey : keys) {
            String window = cacheKey.replace(SESSION_CACHE_PREFIX, "");
            String[] bounds = window.split("_");
            if (bounds.length != 2) {
                continue;
            }
            long startTime = Long.parseLong(bounds[0]);
            long endTime = Long.parseLong(bounds[1]);
            if (currentTime > startTime && currentTime < endTime) {
                List<String> skuKeys = redisTemplate.opsForList().range(cacheKey, -100, 100);
                if (skuKeys == null || skuKeys.isEmpty()) {
                    return null;
                }
                BoundHashOperations<String, Object, Object> skuCache =
                        redisTemplate.boundHashOps(SECKILL_CACHE_PREFIX);
                return skuKeys.stream()
                        .map(skuCache::get)
                        .filter(value -> value != null)
                        .map(value -> JSON.parseObject((String) value, SeckillSkuRedisTo.class))
                        .collect(Collectors.toList());
            }
        }
        return null;
    }

    @Override
    public SeckillSkuRedisTo getSeckillSkuInfo(Long skuId) {
        if (skuId == null) {
            return null;
        }
        BoundHashOperations<String, String, String> skuCache =
                redisTemplate.boundHashOps(SECKILL_CACHE_PREFIX);
        Set<String> keys = skuCache.keys();
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        for (String skuKey : keys) {
            if (!skuKey.endsWith("-" + skuId)) {
                continue;
            }
            SeckillSkuRedisTo redisTo =
                    JSON.parseObject(skuCache.get(skuKey), SeckillSkuRedisTo.class);
            if (redisTo == null) {
                continue;
            }
            long currentTime = System.currentTimeMillis();
            if (redisTo.getStartTime() < currentTime && redisTo.getEndTime() > currentTime) {
                return redisTo;
            }
            redisTo.setRandomCode(null);
            return redisTo;
        }
        return null;
    }

    @Override
    public String kill(String killId, String key, Integer num) throws InterruptedException {
        if (StringUtils.isEmpty(killId) || StringUtils.isEmpty(key) || num == null || num <= 0) {
            return null;
        }

        BoundHashOperations<String, String, String> skuCache =
                redisTemplate.boundHashOps(SECKILL_CACHE_PREFIX);
        String json = skuCache.get(killId);
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        SeckillSkuRedisTo redisTo = JSON.parseObject(json, SeckillSkuRedisTo.class);
        long currentTime = System.currentTimeMillis();
        if (currentTime < redisTo.getStartTime() || currentTime >= redisTo.getEndTime()) {
            return null;
        }
        String expectedKillId = redisTo.getPromotionSessionId() + "-" + redisTo.getSkuId();
        if (!expectedKillId.equals(killId) || !key.equals(redisTo.getRandomCode())) {
            return null;
        }
        if (redisTo.getSeckillLimit() == null || num > redisTo.getSeckillLimit()) {
            return null;
        }

        MemberResponseVo member = LoginInterceptor.loginUser.get();
        if (member == null || member.getId() == null) {
            return null;
        }

        long ttl = redisTo.getEndTime() - currentTime;
        String stockKey = stockKey(redisTo.getRandomCode());
        String purchaseKey = purchaseKey(
                redisTo.getRandomCode(), member.getId(), redisTo.getSkuId());
        Long acquired = redisTemplate.execute(
                ACQUIRE_STOCK_SCRIPT,
                Arrays.asList(stockKey, purchaseKey),
                num.toString(),
                Long.toString(ttl));
        if (!Long.valueOf(1L).equals(acquired)) {
            return null;
        }

        String orderSn = IdWorker.getTimeId();
        SeckillOrderTo orderTo = new SeckillOrderTo();
        orderTo.setMemberId(member.getId());
        orderTo.setNum(num);
        orderTo.setOrderSn(orderSn);
        orderTo.setPromotionSessionId(redisTo.getPromotionSessionId());
        orderTo.setSeckillPrice(redisTo.getSeckillPrice());
        orderTo.setSkuId(redisTo.getSkuId());
        try {
            orderService.createSeckillOrder(orderTo);
            return orderSn;
        } catch (RuntimeException ex) {
            try {
                redisTemplate.execute(
                        ROLLBACK_STOCK_SCRIPT,
                        Arrays.asList(stockKey, purchaseKey));
            } catch (RuntimeException rollbackEx) {
                ex.addSuppressed(rollbackEx);
            }
            throw ex;
        }
    }

    private void saveSeckillSession(List<SeckillSessionEntity> sessions) {
        sessions.forEach(session -> {
            if (session.getStartTime() == null || session.getEndTime() == null
                    || session.getRelations() == null || session.getRelations().isEmpty()) {
                return;
            }
            String key = SESSION_CACHE_PREFIX + session.getStartTime().getTime()
                    + "_" + session.getEndTime().getTime();
            if (!redisTemplate.hasKey(key)) {
                List<String> values = session.getRelations().stream()
                        .map(sku -> sku.getPromotionSessionId() + "-" + sku.getSkuId())
                        .collect(Collectors.toList());
                redisTemplate.opsForList().leftPushAll(key, values);
            }
        });
    }

    private void saveSeckillSku(List<SeckillSessionEntity> sessions) {
        BoundHashOperations<String, Object, Object> skuCache =
                redisTemplate.boundHashOps(SECKILL_CACHE_PREFIX);
        sessions.forEach(session -> {
            if (session.getStartTime() == null || session.getEndTime() == null
                    || session.getRelations() == null || session.getRelations().isEmpty()) {
                return;
            }
            session.getRelations().forEach(relation -> {
                String skuKey =
                        relation.getPromotionSessionId() + "-" + relation.getSkuId();
                if (skuCache.hasKey(skuKey)) {
                    return;
                }
                SeckillSkuRedisTo redisTo = toRedisTo(session, relation);
                if (redisTo == null || redisTo.getSeckillCount() == null
                        || redisTo.getSeckillCount() <= 0
                        || redisTo.getSeckillLimit() == null
                        || redisTo.getSeckillLimit() <= 0) {
                    return;
                }
                String token = UUID.randomUUID().toString().replace("-", "");
                redisTo.setRandomCode(token);
                long ttl = session.getEndTime().getTime() - System.currentTimeMillis();
                if (ttl <= 0) {
                    return;
                }
                redisTemplate.opsForValue().setIfAbsent(
                        stockKey(token),
                        redisTo.getSeckillCount().toString(),
                        ttl,
                        TimeUnit.MILLISECONDS);
                skuCache.put(skuKey, JSON.toJSONString(redisTo));
            });
        });
    }

    private String stockKey(String token) {
        return SKU_STOCK_PREFIX + "{" + token + "}";
    }

    private String purchaseKey(String token, Long memberId, Long skuId) {
        return PURCHASE_PREFIX + "{" + token + "}:" + memberId + "-" + skuId;
    }

    private SeckillSkuRedisTo toRedisTo(SeckillSessionEntity session,
                                        SeckillSkuRelationEntity relation) {
        SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
        redisTo.setId(relation.getId());
        redisTo.setPromotionId(relation.getPromotionId());
        redisTo.setPromotionSessionId(relation.getPromotionSessionId());
        redisTo.setSkuId(relation.getSkuId());
        redisTo.setSeckillPrice(relation.getSeckillPrice());
        try {
            redisTo.setSeckillCount(toInteger(relation.getSeckillCount()));
            redisTo.setSeckillLimit(toInteger(relation.getSeckillLimit()));
        } catch (ArithmeticException ex) {
            return null;
        }
        redisTo.setSeckillSort(relation.getSeckillSort());
        redisTo.setStartTime(session.getStartTime().getTime());
        redisTo.setEndTime(session.getEndTime().getTime());

        SkuInfoEntity skuInfo = skuInfoService.getById(relation.getSkuId());
        if (skuInfo != null) {
            SkuInfoVo skuInfoVo = new SkuInfoVo();
            BeanUtils.copyProperties(skuInfo, skuInfoVo);
            redisTo.setSkuInfoVo(skuInfoVo);
        }
        return redisTo;
    }

    private Integer toInteger(BigDecimal value) {
        return value == null ? null : value.intValueExact();
    }
}

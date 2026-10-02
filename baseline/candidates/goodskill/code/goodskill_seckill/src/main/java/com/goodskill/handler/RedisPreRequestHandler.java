package com.goodskill.handler;

import com.goodskill.constant.SeckillStatusConstant;
import com.goodskill.dto.SeckillWebMockRequestDTO;
import com.goodskill.entity.mysql.Seckill;
import com.goodskill.service.RedisService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class RedisPreRequestHandler extends AbstractPreRequestHandler {
    @Resource
    private RedisService redisService;

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public void handle(SeckillWebMockRequestDTO request) {
        long seckillId = request.getSeckillId();
        redisService.removeSeckill(seckillId);
        Seckill seckill = redisService.getSeckill(seckillId);
        seckill.setStatus(SeckillStatusConstant.IN_PROGRESS);
        redisService.putSeckill(seckill);
        redisService.clearSeckillEndFlag(seckillId, request.getTaskId());
    }
}



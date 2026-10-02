package com.goodskill.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.goodskill.dto.SeckillWebMockRequestDTO;
import com.goodskill.entity.mysql.Seckill;
import com.goodskill.entity.mysql.SuccessKilled;
import com.goodskill.mapper.SeckillMapper;
import com.goodskill.mapper.SuccessKilledMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
@Slf4j
public class DatabasePreRequestHandler extends AbstractPreRequestHandler {
    @Resource
    private SeckillMapper seckillMapper;
    @Resource
    private SuccessKilledMapper successKilledMapper;
    @Override
    public void handle(SeckillWebMockRequestDTO request) {
        Seckill entity = new Seckill();
        entity.setSeckillId(request.getSeckillId());
        entity.setNumber(request.getSeckillCount());
        int rows = seckillMapper.updateById(entity);
        log.info("【库存重置】seckillId={}, 重置库存为={}, 更新行数={}", request.getSeckillId(), request.getSeckillCount(), rows);

        // 清理已成功秒杀记录
        SuccessKilled example = new SuccessKilled();
        example.setSeckillId(request.getSeckillId());
        successKilledMapper.delete(new QueryWrapper<>(example));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}


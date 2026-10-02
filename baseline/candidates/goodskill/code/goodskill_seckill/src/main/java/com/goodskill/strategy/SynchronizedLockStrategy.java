package com.goodskill.strategy;

import com.goodskill.dto.SeckillMockRequestDTO;
import com.goodskill.entity.mysql.Seckill;
import com.goodskill.mapper.SeckillMapper;
import com.goodskill.executor.SeckillExecutor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import static com.goodskill.enums.SeckillSolutionEnum.SYCHRONIZED;

/**
 * @author techa03
 * @date 2019/7/27
 */
@Component
@Slf4j
public class SynchronizedLockStrategy implements GoodsKillStrategy {
    @Autowired
    private SeckillMapper seckillMapper;

    @Resource
    private SeckillExecutor seckillExecutor;

    private final ConcurrentHashMap<Long, Object> seckillIdList = new ConcurrentHashMap<>();

    @Override
    public void execute(SeckillMockRequestDTO requestDto) {
        Long seckillId = requestDto.getSeckillId();
        String userId = requestDto.getPhoneNumber();
        Object seckillMonitor = seckillIdList.computeIfAbsent(seckillId, k -> new Object());

        // This lock only protects a single JVM. Multi-instance deployments need a distributed stock guard.
        synchronized (seckillMonitor) {
            Seckill seckill = seckillMapper.selectById(seckillId);
            if (seckill.getNumber() > 0) {
                seckillExecutor.dealSeckillWithPreCheck(seckillId, userId, SYCHRONIZED.getName(),
                    requestDto.getTaskId(), true);
            } else {
                seckillExecutor.dealSeckillWithPreCheck(seckillId, userId, SYCHRONIZED.getName(),
                    requestDto.getTaskId(), false);
            }
        }
    }
}

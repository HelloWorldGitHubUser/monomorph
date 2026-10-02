package com.goodskill.listener;

import com.goodskill.dto.SeckillMockResponseDTO;
import com.goodskill.service.SeckillService;
import com.goodskill.util.TaskTimeCaculateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 秒杀结果处理
 */
@Slf4j
@Service
public class SeckillMockResponseListener {
    @Autowired
    private SeckillService seckillService;

    public void handleSeckillResult(SeckillMockResponseDTO responseDto) {
        long seckillId = responseDto.getSeckillId();
        String note = responseDto.getNote();

        if (Boolean.TRUE.equals(responseDto.getStatus())) {
            log.info("秒杀活动结束，{}时间：{},秒杀id：{}", note, new Date(), seckillId);
            long successKillCount = seckillService.getSuccessKillCount(seckillId);
            long temp = 0;
            while (successKillCount != temp) {
                log.info("最终成功交易笔数统计中。。。");
                successKillCount = temp;
                try {
                    Thread.sleep(1500L);
                } catch (InterruptedException e) {
                    log.warn(e.getMessage(), e);
                }
                temp = seckillService.getSuccessKillCount(seckillId);
            }
            TaskTimeCaculateUtil.stop(responseDto.getTaskId());
            log.info("最终成功交易笔数：{}", successKillCount);
            log.info("历史任务耗时统计：{}", TaskTimeCaculateUtil.prettyPrint(responseDto.getTaskId()));
            seckillService.endSeckill(seckillId);
        }
    }
}

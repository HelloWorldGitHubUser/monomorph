package io.gulimall.schedule;

import io.gulimall.service.seckill.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SeckillSchedule {

    @Autowired
    private SeckillService seckillService;

    @Scheduled(cron = "${seckill.upload.cron:0 0 3 * * ?}")
    public void uploadSeckillSkuLatest3Days() {
        seckillService.uploadSeckillSkuLatest3Days();
    }
}

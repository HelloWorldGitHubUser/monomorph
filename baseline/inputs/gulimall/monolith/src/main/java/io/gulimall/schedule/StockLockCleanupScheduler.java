package io.gulimall.schedule;

import io.gulimall.service.ware.WareSkuService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StockLockCleanupScheduler {

    private final WareSkuService wareSkuService;
    private final long maxLockMinutes;

    public StockLockCleanupScheduler(WareSkuService wareSkuService,
                                     @Value("${stock.lock.max-minutes:30}") long maxLockMinutes) {
        this.wareSkuService = wareSkuService;
        this.maxLockMinutes = maxLockMinutes;
    }

    @Scheduled(fixedDelayString = "${stock.lock.cleanup-interval-ms:60000}")
    public void cleanupExpiredLocks() {
        wareSkuService.releaseExpiredLocks(maxLockMinutes);
    }
}


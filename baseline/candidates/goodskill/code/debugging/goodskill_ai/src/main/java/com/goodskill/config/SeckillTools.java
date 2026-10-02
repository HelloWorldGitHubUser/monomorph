package com.goodskill.config;
import com.goodskill.dto.Result;
import com.goodskill.dto.SeckillWebMockRequestDTO;
import com.goodskill.monomorph.id.generated.client.SeckillMockController;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
/**
 *
 * @author techa03
 */
@Configuration
public class SeckillTools {
    private static final Logger logger = LoggerFactory.getLogger(SeckillTools.class);

    @Autowired
    private SeckillMockController seckillMockController;

    public record StartSeckillRequest(Long seckillId, Integer seckillCount, Integer requestCount) {
    }

    @Bean
    @Description("开启秒杀活动")
    public Function<StartSeckillRequest, Long> startSeckill() {
        return request -> {
            SeckillWebMockRequestDTO dto = new SeckillWebMockRequestDTO();
            dto.setSeckillId(request.seckillId);
            dto.setSeckillCount(request.seckillCount);
            dto.setRequestCount(request.requestCount);
            // 改用场景一 doWithSychronized
            CompletableFuture<Result<Long>> resultCompletableFuture = CompletableFuture.supplyAsync(() -> seckillMockController.doWithSychronized(dto));
            if (resultCompletableFuture.isCompletedExceptionally()) {
                return -1L;
            } else {
                try {
                    return resultCompletableFuture.get().getData();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Bean
    @Description("获取任务耗时统计信息")
    public Function<StartSeckillRequest, String> getTaskTimeInfo() {
        return request -> {
            CompletableFuture<Result<String>> resultCompletableFuture = CompletableFuture.supplyAsync(() -> seckillMockController.getTaskTimeInfo(request.seckillId));
            if (resultCompletableFuture.isCompletedExceptionally()) {
                return "";
            } else {
                try {
                    return resultCompletableFuture.get().getData();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
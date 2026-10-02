package com.goodskill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 * 提供两种线程池以满足不同模块的需求
 * @author techa03
 * @date 2020/12/26
 */
@Configuration
public class ThreadPoolConfig {
    
    /**
     * JDK ThreadPoolExecutor
     * bean名称为 taskExecutor，保持与微服务版本一致
     */
    @Bean("taskExecutor")
    public ThreadPoolExecutor taskExecutor() {
        return new ThreadPoolExecutor(2, 10, 1, TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
    }
    
    /**
     * Spring ThreadPoolTaskExecutor
     * 使用不同的 bean 名称避免冲突
     */
    @Bean("webTaskExecutor")
    public ThreadPoolTaskExecutor webTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("web-task-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}

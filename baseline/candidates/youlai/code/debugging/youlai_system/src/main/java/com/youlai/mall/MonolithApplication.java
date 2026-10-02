package com.youlai.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 有来商城单体应用启动类
 *
 * @author haoxr
 * @since 2024-12-01
 */
@SpringBootApplication(scanBasePackages = {"com.youlai.mall"})
@EnableScheduling
@MapperScan({"com.youlai.mall.mapper", "com.youlai.mall.mybatis"})
public class MonolithApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonolithApplication.class, args);
    }

}



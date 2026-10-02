package io.gulimall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"io.gulimall"})
@MapperScan(basePackages = {"io.gulimall.dao"})
public class GulimallApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimallApplication.class, args);
    }
}

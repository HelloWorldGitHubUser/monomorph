package com.passjava;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PassJava 单体应用启动类
 */
@SpringBootApplication
@MapperScan("com.passjava.dao")
public class PassJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassJavaApplication.class, args);
    }
}






package io.gulimall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 配置 - 禁用默认安全配置
 * 项目使用自定义 LoginInterceptor 处理登录，不需要 Spring Security 的默认登录页
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().permitAll()  // 允许所有请求通过
            .and()
            .csrf().disable();  // 禁用 CSRF（因为使用 Session 认证）
    }
}


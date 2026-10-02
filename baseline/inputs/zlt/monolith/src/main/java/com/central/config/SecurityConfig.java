package com.central.config;

import com.central.security.TokenAuthenticationFilter;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * Spring Security 配置
 *
 * @author zlt
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private CorsConfigurationSource corsConfigurationSource;

    @Resource
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 获取白名单URL
        String[] ignoreUrls = securityProperties.getIgnore().getUrls().toArray(new String[0]);
        
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource))  // 启用 CORS
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> {
                // 白名单URL放行
                if (ignoreUrls.length > 0) {
                    auth.requestMatchers(ignoreUrls).permitAll();
                }
                // 静态资源放行
                auth.requestMatchers("/static/**", "/assets/**", "/pages/**", "/module/**", "/*.html", "/*.js", "/*.css").permitAll();
                // 其他请求需要认证
                auth.anyRequest().authenticated();
            })
            // 添加 Token 认证过滤器
            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index.html")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login.html")
                .permitAll()
            );
        
        return http.build();
    }
}


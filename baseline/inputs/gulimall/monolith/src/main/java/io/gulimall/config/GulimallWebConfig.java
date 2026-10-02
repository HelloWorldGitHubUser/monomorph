package io.gulimall.config;

import io.gulimall.interceptor.CartInterceptor;
import io.gulimall.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GulimallWebConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册购物车拦截器（排除静态资源）
        registry.addInterceptor(new CartInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/error");
        // 注册登录拦截器（排除静态资源）
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**", "/error");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // product 模块静态资源
        registry.addResourceHandler("/static/index/**")
                .addResourceLocations("classpath:/product/static/index/");
        registry.addResourceHandler("/static/item/**")
                .addResourceLocations("classpath:/product/static/item/");
        
        // search 模块静态资源（特殊：请求 /static/search/** 映射到 search/static/）
        registry.addResourceHandler("/static/search/**")
                .addResourceLocations("classpath:/search/static/");
        
        // cart 模块静态资源
        registry.addResourceHandler("/static/cart/**")
                .addResourceLocations("classpath:/cart/static/cart/");
        
        // order 模块静态资源
        registry.addResourceHandler("/static/order/**")
                .addResourceLocations("classpath:/order/static/order/");
        
        // auth-server 模块静态资源
        registry.addResourceHandler("/static/login/**")
                .addResourceLocations("classpath:/auth-server/static/login/");
        registry.addResourceHandler("/static/register/**")
                .addResourceLocations("classpath:/auth-server/static/register/");
    }
}


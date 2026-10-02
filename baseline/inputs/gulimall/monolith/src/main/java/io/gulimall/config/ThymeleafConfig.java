package io.gulimall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Thymeleaf 配置 - 支持多个模板目录
 * 因为模板文件分散在 product/templates、auth-server/templates 等子目录下
 */
@Configuration
public class ThymeleafConfig {

    @Bean
    @Primary
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        // 设置基础路径为 classpath 根目录
        resolver.setPrefix("classpath:/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false); // 开发环境关闭缓存
        resolver.setOrder(1);
        return resolver;
    }
}


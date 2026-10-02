package com.hoangtien2k3.ecommerce.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hoangtien2k3")
@Getter
@Setter
public class YasConfig {
    private String publicUrl;
}

package com.central.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全配置属性
 *
 * @author zlt
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "zlt.security")
public class SecurityProperties {
    
    private AuthProperties auth = new AuthProperties();
    private IgnoreProperties ignore = new IgnoreProperties();

    @Getter
    @Setter
    public static class AuthProperties {
        private UrlPermissionProperties urlPermission = new UrlPermissionProperties();
    }

    @Getter
    @Setter
    public static class UrlPermissionProperties {
        /**
         * 是否开启URL权限控制
         */
        private Boolean enable = false;
    }

    @Getter
    @Setter
    public static class IgnoreProperties {
        /**
         * 白名单URL列表
         */
        private List<String> urls = new ArrayList<>();
    }
}






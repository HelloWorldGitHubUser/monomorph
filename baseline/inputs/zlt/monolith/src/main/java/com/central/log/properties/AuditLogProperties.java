package com.central.log.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 审计日志配置
 *
 * @author zlt
 * @date 2020/2/3
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "zlt.audit-log")
public class AuditLogProperties {
    /**
     * 是否开启审计日志
     */
    private Boolean enabled = false;
}

package com.central.trace;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 日志链路追踪配置
 *
 * @author zlt
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "zlt.trace")
public class TraceProperties {
    /**
     * 是否开启日志链路追踪
     */
    private Boolean enable = true;
}






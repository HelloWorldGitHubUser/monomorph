package com.central.log.annotation;

import java.lang.annotation.*;

/**
 * 审计日志注解
 * 用于标记需要记录审计日志的方法
 *
 * @author zlt
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {
    /**
     * 操作信息
     * 支持 SpEL 表达式，例如: "'新增用户:' + #sysUser.username"
     */
    String operation();
}





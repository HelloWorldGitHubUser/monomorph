package com.central.datascope;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据权限配置属性
 *
 * @author jarvis create by 2023/1/8
 */
@Data
@Component
@ConfigurationProperties(prefix = "zlt.datascope")
public class DataScopeProperties {
    /**
     * 默认忽略的SQL ID
     */
    private static final Set<String> DEFAULT_IGNORE_SQL_ID = Set.of(
            "com.central.mapper.SysUserMapper.selectList",
            "com.central.mapper.SysUserRoleMapper.findRolesByUserId",
            "com.central.mapper.SysRoleMenuMapper.findMenusByRoleIds"
    );

    /**
     * 是否开启权限控制
     */
    private Boolean enabled = Boolean.FALSE;

    /**
     * 是否开启打印sql的修改情况
     */
    private Boolean enabledSqlDebug = Boolean.FALSE;

    /**
     * 配置哪些表不执行权限控制
     */
    private Set<String> ignoreTables = Collections.emptySet();

    /**
     * 指定哪些sql不执行权限控制
     */
    private Set<String> ignoreSqls = DEFAULT_IGNORE_SQL_ID;

    /**
     * 配置哪些表执行数据权限控制，默认是*则表示全部
     */
    private Set<String> includeTables = Collections.singleton("*");

    /**
     * 指定哪些sql执行数据权限控制
     * 1. 为空时：所有sql都添加权限控制
     * 2. 有值时：只有配置的sql添加权限控制
     */
    private Set<String> includeSqls = Collections.emptySet();

    /**
     * 指定创建人id的字段名
     */
    private String creatorIdColumnName = "creator_id";

    public void setIgnoreSqls(Set<String> ignoreSqls) {
        Set<String> ignoreSet = new HashSet<>(DEFAULT_IGNORE_SQL_ID);
        if (CollUtil.isNotEmpty(ignoreSqls)) {
            ignoreSet.addAll(ignoreSqls);
        }
        this.ignoreSqls = ignoreSet;
    }
}





package com.central.datascope;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.StringReader;
import java.util.*;

import static com.central.datascope.SqlHandler.ALIAS_SYMBOL;

/**
 * 数据权限拦截器
 * 自动为配置的表添加数据权限 WHERE 条件
 *
 * @author jarvis create by 2023/1/7
 */
@Slf4j
@Data
@NoArgsConstructor
public class DataScopeInnerInterceptor implements InnerInterceptor {

    private DataScopeProperties dataScopeProperties;

    /**
     * 权限的where条件处理器
     */
    private SqlHandler sqlHandler;

    /**
     * 对表配置进行缓存，优先读取缓存，再进行匹配
     */
    private Map<String, TableConfig> tableInfoMap = new HashMap<>();

    /**
     * 通配符匹配器
     */
    private PathPatternParser pathPatternParser = new PathPatternParser();

    public DataScopeInnerInterceptor(DataScopeProperties dataScopeProperties, SqlHandler sqlHandler) {
        this.dataScopeProperties = dataScopeProperties;
        this.sqlHandler = sqlHandler;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, 
                           RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        // 检查是否启用数据权限
        if (!dataScopeProperties.getEnabled()) {
            return;
        }
        
        // 为空时：所有sql都添加权限控制
        // 有值时：只有配置的sql添加权限控制
        if (CollUtil.isEmpty(dataScopeProperties.getIncludeSqls())
                || dataScopeProperties.getIncludeSqls().contains(ms.getId())) {
            // 判断排除的sql
            if (CollUtil.isEmpty(dataScopeProperties.getIgnoreSqls())
                    || !dataScopeProperties.getIgnoreSqls().contains(ms.getId())) {
                PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
                String sql = boundSql.getSql();
                try {
                    Select select = explainQuerySql(sql);
                    reform(select.getSelectBody());
                    String newSql = select.toString();
                    if (dataScopeProperties.getEnabledSqlDebug()) {
                        log.debug("数据权限SQL改造: {} -> {}", sql, newSql);
                    }
                    mpBs.sql(newSql);
                } catch (JSQLParserException e) {
                    log.warn("SQL解析失败，跳过数据权限处理: {}", e.getMessage());
                }
            }
        }
    }

    public Select explainQuerySql(String sql) throws JSQLParserException {
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        return (Select) parserManager.parse(new StringReader(sql));
    }

    /**
     * 递归对查询和解析后的子查询进行改造
     */
    public <T extends SelectBody> void reform(SelectBody selectBody) throws JSQLParserException {
        if (selectBody instanceof PlainSelect && ObjectUtil.isNotNull(sqlHandler)) {
            PlainSelect select = (PlainSelect) selectBody;
            // 获取权限的where条件
            String scopeWhereSql = sqlHandler.handleScopeSql();
            // 如果条件不是空的话才对select进行改造
            if (StrUtil.isNotBlank(scopeWhereSql)) {
                // 需要改造的别名列表，自动增加到where条件中
                List<String> tableAliasList = new ArrayList<>();
                FromItem fromItem = select.getFromItem();
                String tableAlias = explainFromItem(fromItem);
                
                // 获取from的表字段，如果from是子查询则进行递归
                if (fromItem instanceof Table) {
                    String upperTableName = ((Table) fromItem).getName().toUpperCase();
                    if (tableInfoMap.containsKey(upperTableName)) {
                        if (!tableInfoMap.get(upperTableName).getIgnore()) {
                            tableAliasList.add(StrUtil.isNotBlank(tableAlias) ? tableAlias : "");
                        }
                    } else {
                        boolean ignore = true;
                        if (isReformTable(upperTableName)) {
                            tableAliasList.add(StrUtil.isNotBlank(tableAlias) ? tableAlias : "");
                            ignore = false;
                        }
                        // 写入缓存
                        tableInfoMap.put(upperTableName, new TableConfig(upperTableName, ignore));
                    }
                } else if (fromItem instanceof SubSelect) {
                    reform(((SubSelect) fromItem).getSelectBody());
                }
                
                // 获取join列表，然后获取对应的表或者递归子查询
                List<Join> joinList = select.getJoins();
                if (CollUtil.isNotEmpty(joinList)) {
                    for (Join join : joinList) {
                        if (join.getRightItem() instanceof Table) {
                            String joinTable = ((Table) join.getRightItem()).getName().toUpperCase();
                            Alias joinAliasObj = ((Table) join.getRightItem()).getAlias();
                            String joinAlias = joinAliasObj != null ? joinAliasObj.getName() : "";
                            
                            if (tableInfoMap.containsKey(joinTable)) {
                                if (!tableInfoMap.get(joinTable).getIgnore()) {
                                    tableAliasList.add(StrUtil.isNotBlank(joinAlias) ? joinAlias : "");
                                }
                            } else {
                                boolean ignore = true;
                                if (isReformTable(joinTable)) {
                                    tableAliasList.add(StrUtil.isNotBlank(joinAlias) ? joinAlias : "");
                                    ignore = false;
                                }
                                // 写入缓存
                                tableInfoMap.put(joinTable, new TableConfig(joinTable, ignore));
                            }
                        }
                        if (join.getRightItem() instanceof SubSelect) {
                            reform(((SubSelect) join.getRightItem()).getSelectBody());
                        }
                    }
                }
                
                // 如果改造的表是空的话则不改造对应的select
                if (CollUtil.isNotEmpty(tableAliasList)) {
                    reformWhere(select, scopeWhereSql, tableAliasList);
                }
            }
        } else if (selectBody instanceof WithItem && Objects.nonNull(((WithItem) selectBody).getSubSelect())) {
            reform(((WithItem) selectBody).getSubSelect().getSelectBody());
        }
    }

    /**
     * 判断表是否需要改造
     */
    private boolean isReformTable(String table) {
        return (dataScopeProperties.getIncludeTables().contains(table)
                || dataScopeProperties.getIncludeTables().contains("*")
                || dataScopeProperties.getIncludeTables().stream().anyMatch(item ->
                        pathPatternParser.parse(item.toUpperCase()).matches(PathContainer.parsePath(table))))
                && (CollUtil.isEmpty(dataScopeProperties.getIgnoreTables())
                        || !(dataScopeProperties.getIgnoreTables().contains(table)
                                || dataScopeProperties.getIgnoreTables().stream().anyMatch(item ->
                                        pathPatternParser.parse(item.toUpperCase()).matches(PathContainer.parsePath(table)))));
    }

    /**
     * 解析from中的内容
     */
    private String explainFromItem(FromItem fromItem) throws JSQLParserException {
        String alias = "";
        if (Objects.nonNull(fromItem)) {
            if (fromItem instanceof Table) {
                Alias tableAlias = ((Table) fromItem).getAlias();
                if (Objects.nonNull(tableAlias) && StrUtil.isNotBlank(tableAlias.getName())) {
                    alias = tableAlias.getName();
                } else {
                    alias = ((Table) fromItem).getName();
                }
            }
            if (fromItem instanceof SubSelect) {
                SelectBody subSelectBody = ((SubSelect) fromItem).getSelectBody();
                reform(subSelectBody);
            }
        }
        return alias;
    }

    /**
     * 改造where条件
     */
    private SelectBody reformWhere(PlainSelect select, String whereSql, List<String> aliasName) throws JSQLParserException {
        if (StrUtil.isNotBlank(whereSql) && CollUtil.isNotEmpty(aliasName)) {
            for (String alias : aliasName) {
                Expression expression = CCJSqlParserUtil.parseCondExpression(whereSql);
                expression.accept(new ExpressionVisitorAdapter() {
                    @Override
                    public void visit(Column column) {
                        if (Objects.isNull(column.getTable()) || ALIAS_SYMBOL.equals(column.getTable().toString())) {
                            Table table = new Table();
                            table.setAlias(new Alias(alias));
                            column.setTable(table);
                        }
                    }
                });
                if (ObjectUtil.isNull(select.getWhere())) {
                    select.setWhere(expression);
                } else {
                    AndExpression andExpression = new AndExpression(select.getWhere(), expression);
                    select.setWhere(andExpression);
                }
            }
        }
        return select;
    }

    /**
     * 表配置信息
     */
    public static class TableConfig {
        private final String tableName;
        private final Boolean isIgnore;

        public TableConfig(String tableName, Boolean isIgnore) {
            this.tableName = tableName;
            this.isIgnore = isIgnore;
        }

        public String getTableName() {
            return tableName;
        }

        public Boolean getIgnore() {
            return isIgnore;
        }
    }
}





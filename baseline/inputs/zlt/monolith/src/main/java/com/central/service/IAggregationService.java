package com.central.service;

import java.io.IOException;
import java.util.Map;

/**
 * 聚合统计服务接口
 * @author zlt
 */
public interface IAggregationService {
    /**
     * 访问统计聚合查询
     * @param indexName 索引名
     * @param routing es的路由
     * @return 统计数据
     */
    Map<String, Object> requestStatAgg(String indexName, String routing) throws IOException;

    /**
     * 获取默认的统计数据（当ES不可用时）
     * @return 默认统计数据
     */
    Map<String, Object> getDefaultStatData();
}






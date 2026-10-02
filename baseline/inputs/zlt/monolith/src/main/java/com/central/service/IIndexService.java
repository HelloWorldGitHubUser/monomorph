package com.central.service;

import com.central.model.dto.IndexDto;
import com.central.model.PageResult;

import java.io.IOException;
import java.util.Map;

/**
 * ES索引管理服务接口
 * 完全参考微服务版本
 *
 * @author zlt
 * @date 2019/4/23
 */
public interface IIndexService {
    /**
     * 创建索引
     * @return 是否创建成功
     */
    boolean create(IndexDto indexDto) throws IOException;

    /**
     * 删除索引
     * @return 是否删除成功
     */
    boolean delete(String indexName) throws IOException;

    /**
     * 索引列表
     * @param queryStr 查询条件
     * @param indices 要显示的索引模式
     */
    PageResult<Map<String, String>> list(String queryStr, String indices) throws IOException;

    /**
     * 索引详情
     */
    Map<String, Object> show(String indexName) throws IOException;
}


package com.central.service;

import com.central.model.dto.LogicDelDto;
import com.central.model.PageResult;
import com.central.model.dto.SearchDto;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * ES搜索服务接口
 * @author zlt
 */
public interface ISearchService {
    /**
     * 字符串查询
     * @param indexName 索引名
     * @param searchDto 搜索参数
     * @return 分页结果
     */
    PageResult<JsonNode> strQuery(String indexName, SearchDto searchDto) throws IOException;

    /**
     * 字符串查询（带逻辑删除过滤）
     * @param indexName 索引名
     * @param searchDto 搜索参数
     * @param logicDelDto 逻辑删除条件
     * @return 分页结果
     */
    PageResult<JsonNode> strQuery(String indexName, SearchDto searchDto, LogicDelDto logicDelDto) throws IOException;
}


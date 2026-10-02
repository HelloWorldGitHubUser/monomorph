package com.central.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.central.model.dto.LogicDelDto;
import com.central.model.PageResult;
import com.central.model.dto.SearchDto;
import com.central.service.ISearchService;
import com.central.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * ES搜索服务实现
 * @author zlt
 */
@Slf4j
@Service
public class SearchServiceImpl implements ISearchService {
    
    private static final String HIGHLIGHTER_PRE_TAGS = "<mark>";
    private static final String HIGHLIGHTER_POST_TAGS = "</mark>";
    private static final String SORT_ORDER_ASC = "ASC";

    @Autowired(required = false)
    private RestHighLevelClient client;

    @Override
    public PageResult<JsonNode> strQuery(String indexName, SearchDto searchDto) throws IOException {
        return strQuery(indexName, searchDto, null);
    }

    @Override
    public PageResult<JsonNode> strQuery(String indexName, SearchDto searchDto, LogicDelDto logicDelDto) throws IOException {
        if (client == null) {
            log.warn("Elasticsearch 客户端未配置，返回空数据");
            return PageResult.<JsonNode>builder().data(Collections.emptyList()).code(0).count(0L).build();
        }

        // 拼装逻辑删除条件
        setLogicDelQueryStr(searchDto, logicDelDto);

        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchBuilder);

        // 设置查询条件
        QueryBuilder queryBuilder;
        if (StrUtil.isNotEmpty(searchDto.getQueryStr())) {
            queryBuilder = QueryBuilders.queryStringQuery(searchDto.getQueryStr());
        } else {
            queryBuilder = QueryBuilders.matchAllQuery();
        }
        searchBuilder.query(queryBuilder);

        // 设置分页
        Integer page = searchDto.getPage();
        Integer limit = searchDto.getLimit();
        if (page != null && limit != null) {
            searchBuilder.from((page - 1) * limit).size(limit);
        }

        // 设置排序
        if (StrUtil.isNotEmpty(searchDto.getSortCol())) {
            SortOrder so = SORT_ORDER_ASC.equalsIgnoreCase(searchDto.getSortOrder()) 
                ? SortOrder.ASC : SortOrder.DESC;
            searchBuilder.sort(searchDto.getSortCol(), so);
        }

        // 设置高亮
        if (BooleanUtil.isTrue(searchDto.getIsHighlighter())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("*")
                .preTags(HIGHLIGHTER_PRE_TAGS)
                .postTags(HIGHLIGHTER_POST_TAGS);
            searchBuilder.highlighter(highlightBuilder);
        }

        // 设置路由
        if (StrUtil.isNotEmpty(searchDto.getRouting())) {
            searchRequest.routing(searchDto.getRouting());
        }

        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = response.getHits();
            long totalCnt = searchHits.getTotalHits().value;
            List<JsonNode> list = getList(searchHits);
            return PageResult.<JsonNode>builder().data(list).code(0).count(totalCnt).build();
        } catch (Exception e) {
            log.error("ES搜索失败: {}", e.getMessage());
            return PageResult.<JsonNode>builder().data(Collections.emptyList()).code(0).count(0L).build();
        }
    }

    private List<JsonNode> getList(SearchHits searchHits) {
        List<JsonNode> list = new ArrayList<>();
        if (searchHits != null) {
            searchHits.forEach(item -> {
                JsonNode jsonNode = JsonUtil.parse(item.getSourceAsString());
                ObjectNode objectNode = (ObjectNode) jsonNode;
                objectNode.put("id", item.getId());

                Map<String, HighlightField> highlightFields = item.getHighlightFields();
                if (highlightFields != null) {
                    populateHighLightedFields(objectNode, highlightFields);
                }
                list.add(objectNode);
            });
        }
        return list;
    }

    private void populateHighLightedFields(ObjectNode result, Map<String, HighlightField> highlightFields) {
        for (HighlightField field : highlightFields.values()) {
            String name = field.getName();
            if (!name.endsWith(".keyword")) {
                result.put(name, concat(field.getFragments()));
            }
        }
    }

    private String concat(Text[] texts) {
        StringBuilder sb = new StringBuilder();
        for (Text text : texts) {
            sb.append(text.toString());
        }
        return sb.toString();
    }

    /**
     * 拼装逻辑删除的条件
     * @param searchDto 搜索dto
     * @param logicDelDto 逻辑删除dto
     */
    private void setLogicDelQueryStr(SearchDto searchDto, LogicDelDto logicDelDto) {
        if (logicDelDto != null
                && StrUtil.isNotEmpty(logicDelDto.getLogicDelField())
                && StrUtil.isNotEmpty(logicDelDto.getLogicNotDelValue())) {
            String result;
            // 搜索条件
            String queryStr = searchDto.getQueryStr();
            // 拼凑逻辑删除的条件
            String logicStr = logicDelDto.getLogicDelField() + ":" + logicDelDto.getLogicNotDelValue();
            if (StrUtil.isNotEmpty(queryStr)) {
                result = "(" + queryStr + ") AND " + logicStr;
            } else {
                result = logicStr;
            }
            searchDto.setQueryStr(result);
        }
    }
}


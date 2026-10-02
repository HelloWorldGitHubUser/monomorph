package com.central.service.impl;

import cn.hutool.core.util.StrUtil;
import com.central.model.dto.IndexDto;
import com.central.model.PageResult;
import com.central.service.IIndexService;
import com.central.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 索引管理服务实现
 * 完全参考微服务版本实现
 *
 * @author zlt
 * @date 2019/4/23
 */
@Slf4j
@Service
public class IndexServiceImpl implements IIndexService {
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired(required = false)
    private RestHighLevelClient client;

    @Override
    public boolean create(IndexDto indexDto) throws IOException {
        if (client == null) {
            log.warn("Elasticsearch 客户端未配置");
            return false;
        }
        CreateIndexRequest request = new CreateIndexRequest(indexDto.getIndexName());
        request.settings(Settings.builder()
                .put("index.number_of_shards", indexDto.getNumberOfShards())
                .put("index.number_of_replicas", indexDto.getNumberOfReplicas())
        );
        if (StrUtil.isNotEmpty(indexDto.getMappingsSource())) {
            // mappings
            request.mapping(indexDto.getMappingsSource(), XContentType.JSON);
        }
        CreateIndexResponse response = client
                .indices()
                .create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    @Override
    public boolean delete(String indexName) throws IOException {
        if (client == null) {
            log.warn("Elasticsearch 客户端未配置");
            return false;
        }
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    @Override
    public PageResult<Map<String, String>> list(String queryStr, String indices) throws IOException {
        if (client == null) {
            log.warn("Elasticsearch 客户端未配置，返回空数据");
            return PageResult.<Map<String, String>>builder().data(Collections.emptyList()).code(0).count(0L).build();
        }

        try {
            if (StrUtil.isNotEmpty(queryStr)) {
                indices = queryStr;
            }
            // 使用与微服务版本完全一致的 _cat/indices REST API
            Response response = client.getLowLevelClient()
                    .performRequest(new Request(
                            "GET",
                            "/_cat/indices?h=health,status,index,docsCount,docsDeleted,storeSize&s=cds:desc&format=json&index=" + StrUtil.nullToEmpty(indices)
                    ));

            List<Map<String, String>> listOfIndicesFromEs = null;
            if (response != null) {
                String rawBody = EntityUtils.toString(response.getEntity());
                TypeReference<List<Map<String, String>>> typeRef = new TypeReference<List<Map<String, String>>>() {};
                listOfIndicesFromEs = mapper.readValue(rawBody, typeRef);
            }
            return PageResult.<Map<String, String>>builder().data(listOfIndicesFromEs).code(0).count(listOfIndicesFromEs != null ? (long) listOfIndicesFromEs.size() : 0L).build();
        } catch (Exception e) {
            log.error("获取索引列表失败: {}", e.getMessage());
            return PageResult.<Map<String, String>>builder().data(Collections.emptyList()).code(0).count(0L).build();
        }
    }

    @Override
    public Map<String, Object> show(String indexName) throws IOException {
        if (client == null) {
            log.warn("Elasticsearch 客户端未配置");
            return new HashMap<>();
        }

        try {
            GetIndexRequest request = new GetIndexRequest(indexName);
            GetIndexResponse getIndexResponse = client
                    .indices().get(request, RequestOptions.DEFAULT);
            MappingMetadata mappingMetadata = getIndexResponse.getMappings().get(indexName);
            Map<String, Object> mappOpenMap = mappingMetadata.getSourceAsMap();
            List<AliasMetadata> indexAliases = getIndexResponse.getAliases().get(indexName);

            String settingsStr = getIndexResponse.getSettings().get(indexName).toString();
            Object settingsObj = null;
            if (StrUtil.isNotEmpty(settingsStr)) {
                settingsObj = JsonUtil.parse(settingsStr);
            }
            Map<String, Object> result = new HashMap<>(1);
            Map<String, Object> indexMap = new HashMap<>(3);
            List<String> aliasesList = new ArrayList<>(indexAliases != null ? indexAliases.size() : 0);
            indexMap.put("aliases", aliasesList);
            indexMap.put("settings", settingsObj);
            indexMap.put("mappings", mappOpenMap);
            result.put(indexName, indexMap);
            // 获取aliases数据
            if (indexAliases != null) {
                for (AliasMetadata aliases : indexAliases) {
                    aliasesList.add(aliases.getAlias());
                }
            }
            return result;
        } catch (Exception e) {
            log.error("获取索引详情失败: {}", e.getMessage());
            return new HashMap<>();
        }
    }
}


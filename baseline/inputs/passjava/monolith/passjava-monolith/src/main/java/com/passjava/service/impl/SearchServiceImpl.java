package com.passjava.service.impl;

import com.alibaba.fastjson.JSON;
import com.passjava.exception.BizCodeEnum;
import com.passjava.utils.R;
import com.passjava.dto.QuestionEsModel;
import com.passjava.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ES 搜索服务实现
 */
@Slf4j
@Service("searchService")
public class SearchServiceImpl implements SearchService {

    private static final String QUESTION_INDEX = "question";
    private static final int PAGE_SIZE = 10;

    @Autowired(required = false)
    private RestHighLevelClient client;

    @Override
    public R saveQuestion(QuestionEsModel questionEsModel) {
        if (client == null) {
            log.warn("Elasticsearch client not configured, skipping save");
            return R.ok();
        }
        
        try {
            IndexRequest indexRequest = new IndexRequest(QUESTION_INDEX);
            indexRequest.id(questionEsModel.getId().toString());
            String jsonString = JSON.toJSONString(questionEsModel);
            indexRequest.source(jsonString, XContentType.JSON);
            
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            log.info("Save question to ES: {}", indexResponse.getId());
            return R.ok();
        } catch (IOException e) {
            log.error("Failed to save question to ES", e);
            return R.error(BizCodeEnum.QUESTION_SAVE_EXCEPTION.getCode(), BizCodeEnum.QUESTION_SAVE_EXCEPTION.getMsg());
        }
    }

    @Override
    public Object search(String keyword, Long id, Integer pageNum) {
        if (client == null) {
            log.warn("Elasticsearch client not configured");
            return R.error("搜索服务不可用");
        }

        Map<String, Object> result = new HashMap<>();
        
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            
            if (!StringUtils.isEmpty(keyword)) {
                boolQuery.must(QueryBuilders.multiMatchQuery(keyword, "title", "answer", "typeName"));
            }
            if (id != null) {
                boolQuery.filter(QueryBuilders.termQuery("id", id));
            }
            sourceBuilder.query(boolQuery);

            int page = pageNum != null ? pageNum : 1;
            sourceBuilder.from((page - 1) * PAGE_SIZE);
            sourceBuilder.size(PAGE_SIZE);

            SearchRequest request = new SearchRequest(new String[]{QUESTION_INDEX}, sourceBuilder);
            SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);

            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            
            List<QuestionEsModel> questionList = new ArrayList<>();
            if (searchHits != null && searchHits.length > 0) {
                for (SearchHit hit : searchHits) {
                    String hitStr = hit.getSourceAsString();
                    QuestionEsModel questionEsModel = JSON.parseObject(hitStr, QuestionEsModel.class);
                    questionList.add(questionEsModel);
                }
            }

            long total = hits.getTotalHits().value;
            int totalPages = (int) (total % PAGE_SIZE == 0 ? total / PAGE_SIZE : total / PAGE_SIZE + 1);

            result.put("questionList", questionList);
            result.put("total", total);
            result.put("pageNum", page);
            result.put("totalPages", totalPages);
            
            return R.ok().put("data", result);
        } catch (IOException e) {
            log.error("Search failed", e);
            return R.error("搜索失败");
        }
    }
}






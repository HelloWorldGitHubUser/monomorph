package com.central.service.impl;

import cn.hutool.core.util.StrUtil;
import com.central.constant.CommonConstant;
import com.central.model.vo.AggItemVo;
import com.central.service.IAggregationService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.*;
import org.elasticsearch.search.aggregations.bucket.range.ParsedDateRange;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedCardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 聚合统计服务实现
 * @author zlt
 */
@Slf4j
@Service
public class AggregationServiceImpl implements IAggregationService {

    @Autowired(required = false)
    private RestHighLevelClient client;

    @Override
    public Map<String, Object> requestStatAgg(String indexName, String routing) throws IOException {
        if (client == null) {
            log.warn("Elasticsearch 客户端未配置，返回默认数据");
            return getDefaultStatData();
        }

        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.now();
            LocalDate localDate = LocalDate.now();
            LocalDateTime curDateTime = LocalDateTime.now();

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            SearchRequest searchRequest = new SearchRequest(indexName);
            searchRequest.source(searchSourceBuilder).routing(routing);
            searchSourceBuilder.aggregation(
                // 聚合查询当天的数据
                AggregationBuilders
                    .dateRange("currDate")
                    .field("timestamp")
                    .addRange(
                        zonedDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0), 
                        zonedDateTime.plusDays(1)
                    )
                    .subAggregation(
                        AggregationBuilders.cardinality("uv").field("ip")
                    )
            ).aggregation(
                // 聚合查询24小时内的数据
                AggregationBuilders
                    .dateRange("curr24Hour")
                    .field("timestamp")
                    .addRange(zonedDateTime.minusDays(1), zonedDateTime)
                    .subAggregation(
                        AggregationBuilders
                            .dateHistogram("statDate")
                            .field("timestamp")
                            .fixedInterval(new DateHistogramInterval("90m"))
                            .format(CommonConstant.DATETIME_FORMAT)
                            .timeZone(ZoneId.of(CommonConstant.TIME_ZONE_GMT8))
                            .minDocCount(0L)
                            .extendedBounds(new LongBounds(
                                curDateTime.minusDays(1).format(DateTimeFormatter.ofPattern(CommonConstant.DATETIME_FORMAT)),
                                curDateTime.format(DateTimeFormatter.ofPattern(CommonConstant.DATETIME_FORMAT))
                            ))
                            .subAggregation(
                                AggregationBuilders.cardinality("uv").field("ip")
                            )
                    )
            ).aggregation(
                // 聚合查询7天内的数据
                AggregationBuilders
                    .dateRange("currWeek")
                    .field("timestamp")
                    .addRange(zonedDateTime.minusDays(7), zonedDateTime)
                    .subAggregation(
                        AggregationBuilders
                            .dateHistogram("statWeek")
                            .field("timestamp")
                            .calendarInterval(DateHistogramInterval.DAY)
                            .format(CommonConstant.DATE_FORMAT)
                            .timeZone(ZoneId.of(CommonConstant.TIME_ZONE_GMT8))
                            .minDocCount(0L)
                            .extendedBounds(new LongBounds(
                                localDate.minusDays(6).format(DateTimeFormatter.ofPattern(CommonConstant.DATE_FORMAT)),
                                localDate.format(DateTimeFormatter.ofPattern(CommonConstant.DATE_FORMAT))
                            ))
                            .subAggregation(
                                AggregationBuilders.cardinality("uv").field("ip")
                            )
                    )
            ).aggregation(
                // 聚合查询30天内的数据
                AggregationBuilders
                    .dateRange("currMonth")
                    .field("timestamp")
                    .addRange(zonedDateTime.minusDays(30), zonedDateTime)
            ).aggregation(
                // 聚合查询浏览器的数据
                AggregationBuilders.terms("browser").field("browser")
            ).aggregation(
                // 聚合查询操作系统的数据
                AggregationBuilders.terms("operatingSystem").field("operatingSystem")
            ).aggregation(
                // 聚合查询1小时内的数据
                AggregationBuilders
                    .dateRange("currHour")
                    .field("timestamp")
                    .addRange(zonedDateTime.minusHours(1), zonedDateTime)
                    .subAggregation(
                        AggregationBuilders.cardinality("uv").field("ip")
                    )
            ).size(0);

            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = response.getAggregations();
            Map<String, Object> result = new HashMap<>(15);
            if (aggregations != null) {
                setCurrDate(result, aggregations);
                setCurr24Hour(result, aggregations);
                setCurrWeek(result, aggregations);
                setCurrMonth(result, aggregations);
                setTermsData(result, aggregations, "browser");
                setTermsData(result, aggregations, "operatingSystem");
                setCurrHour(result, aggregations);
            }
            return result;
        } catch (Exception e) {
            log.error("ES聚合查询失败", e);
            return getDefaultStatData();
        }
    }

    @Override
    public Map<String, Object> getDefaultStatData() {
        Map<String, Object> result = new HashMap<>(15);
        // 当天统计
        result.put("currDate_pv", 0);
        result.put("currDate_uv", 0);
        // 周统计
        result.put("currWeek_pv", 0);
        // 月统计
        result.put("currMonth_pv", 0);
        // 在线人数
        result.put("currHour_uv", 0);
        
        // 浏览器数据
        result.put("browser_legendData", Collections.emptyList());
        result.put("browser_datas", Collections.emptyList());
        
        // 操作系统数据
        result.put("operatingSystem_legendData", Collections.emptyList());
        result.put("operatingSystem_datas", Collections.emptyList());
        
        // 周趋势
        List<String> weekItems = new ArrayList<>();
        List<Long> weekPv = new ArrayList<>();
        List<Long> weekUv = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            weekItems.add(now.minusDays(i).format(DateTimeFormatter.ofPattern(CommonConstant.DATE_FORMAT)));
            weekPv.add(0L);
            weekUv.add(0L);
        }
        result.put("statWeek_items", weekItems);
        result.put("statWeek_pv", weekPv);
        result.put("statWeek_uv", weekUv);
        
        // 天趋势
        result.put("statDate_items", Collections.emptyList());
        result.put("statDate_pv", Collections.emptyList());
        result.put("statDate_uv", Collections.emptyList());
        
        return result;
    }

    private void setCurrDate(Map<String, Object> result, Aggregations aggregations) {
        ParsedDateRange currDate = aggregations.get("currDate");
        if (currDate != null && !currDate.getBuckets().isEmpty()) {
            Range.Bucket bucket = currDate.getBuckets().get(0);
            ParsedCardinality cardinality = bucket.getAggregations().get("uv");
            result.put("currDate_pv", bucket.getDocCount());
            result.put("currDate_uv", cardinality.getValue());
        } else {
            result.put("currDate_pv", 0);
            result.put("currDate_uv", 0);
        }
    }

    private void setCurr24Hour(Map<String, Object> result, Aggregations aggregations) {
        ParsedDateRange curr24Hour = aggregations.get("curr24Hour");
        if (curr24Hour != null && !curr24Hour.getBuckets().isEmpty()) {
            Range.Bucket bucket = curr24Hour.getBuckets().get(0);
            setStatDate(result, bucket.getAggregations());
        } else {
            result.put("statDate_items", Collections.emptyList());
            result.put("statDate_pv", Collections.emptyList());
            result.put("statDate_uv", Collections.emptyList());
        }
    }

    private void setCurrWeek(Map<String, Object> result, Aggregations aggregations) {
        ParsedDateRange currWeek = aggregations.get("currWeek");
        if (currWeek != null && !currWeek.getBuckets().isEmpty()) {
            Range.Bucket bucket = currWeek.getBuckets().get(0);
            result.put("currWeek_pv", bucket.getDocCount());
            setStatWeek(result, bucket.getAggregations());
        } else {
            result.put("currWeek_pv", 0);
            result.put("statWeek_items", Collections.emptyList());
            result.put("statWeek_pv", Collections.emptyList());
            result.put("statWeek_uv", Collections.emptyList());
        }
    }

    private void setCurrMonth(Map<String, Object> result, Aggregations aggregations) {
        ParsedDateRange currMonth = aggregations.get("currMonth");
        if (currMonth != null && !currMonth.getBuckets().isEmpty()) {
            Range.Bucket bucket = currMonth.getBuckets().get(0);
            result.put("currMonth_pv", bucket.getDocCount());
        } else {
            result.put("currMonth_pv", 0);
        }
    }

    private void setTermsData(Map<String, Object> result, Aggregations aggregations, String key) {
        Terms terms = aggregations.get(key);
        List<String> legendData = new ArrayList<>();
        List<AggItemVo> datas = new ArrayList<>();
        if (terms != null) {
            for (Terms.Bucket bucket : terms.getBuckets()) {
                legendData.add((String) bucket.getKey());
                AggItemVo item = new AggItemVo();
                item.setName((String) bucket.getKey());
                item.setValue(bucket.getDocCount());
                datas.add(item);
            }
        }
        result.put(key + "_legendData", legendData);
        result.put(key + "_datas", datas);
    }

    private void setStatWeek(Map<String, Object> result, Aggregations aggregations) {
        ParsedDateHistogram agg = aggregations.get("statWeek");
        List<String> items = new ArrayList<>();
        List<Long> uv = new ArrayList<>();
        List<Long> pv = new ArrayList<>();
        if (agg != null) {
            for (Histogram.Bucket bucket : agg.getBuckets()) {
                items.add(bucket.getKeyAsString());
                pv.add(bucket.getDocCount());
                ParsedCardinality cardinality = bucket.getAggregations().get("uv");
                uv.add(cardinality.getValue());
            }
        }
        result.put("statWeek_items", items);
        result.put("statWeek_uv", uv);
        result.put("statWeek_pv", pv);
    }

    private void setCurrHour(Map<String, Object> result, Aggregations aggregations) {
        ParsedDateRange currDate = aggregations.get("currHour");
        if (currDate != null && !currDate.getBuckets().isEmpty()) {
            Range.Bucket bucket = currDate.getBuckets().get(0);
            ParsedCardinality cardinality = bucket.getAggregations().get("uv");
            result.put("currHour_uv", cardinality.getValue());
        } else {
            result.put("currHour_uv", 0);
        }
    }

    private void setStatDate(Map<String, Object> result, Aggregations aggregations) {
        ParsedDateHistogram agg = aggregations.get("statDate");
        List<String> items = new ArrayList<>();
        List<Long> uv = new ArrayList<>();
        List<Long> pv = new ArrayList<>();
        if (agg != null) {
            for (Histogram.Bucket bucket : agg.getBuckets()) {
                items.add(getTimeByDatetimeStr(bucket.getKeyAsString()));
                pv.add(bucket.getDocCount());
                ParsedCardinality cardinality = bucket.getAggregations().get("uv");
                uv.add(cardinality.getValue());
            }
        }
        result.put("statDate_items", items);
        result.put("statDate_uv", uv);
        result.put("statDate_pv", pv);
    }

    private String getTimeByDatetimeStr(String datetimeStr) {
        if (StrUtil.isNotEmpty(datetimeStr) && datetimeStr.length() > 16) {
            return datetimeStr.substring(5, 16);
        }
        return datetimeStr != null ? datetimeStr : "";
    }
}


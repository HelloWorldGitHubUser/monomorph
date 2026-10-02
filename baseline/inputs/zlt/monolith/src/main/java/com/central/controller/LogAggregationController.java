package com.central.controller;

import com.central.service.IAggregationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 日志访问统计控制器
 * 模拟网关路由前缀 /api-log
 * @author zlt
 */
@Tag(name = "访问统计")
@Slf4j
@RestController
@RequestMapping("/api-log")
public class LogAggregationController {

    @Autowired
    private IAggregationService aggregationService;

    /**
     * 访问统计 - 首页调用
     * 固定参数查询，返回完整的统计数据格式
     */
    @Operation(summary = "访问统计")
    @GetMapping(value = "/requestStat")
    public Map<String, Object> requestStatAgg() {
        log.info("查询访问统计");
        try {
            return aggregationService.requestStatAgg("point-log-*", "request-statistics");
        } catch (Exception e) {
            log.warn("ES查询失败，返回默认数据: {}", e.getMessage());
            return aggregationService.getDefaultStatData();
        }
    }
}



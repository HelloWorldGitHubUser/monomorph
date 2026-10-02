package com.central.controller;

import cn.hutool.core.util.StrUtil;
import com.central.model.PageResult;
import com.central.model.dto.SearchDto;
import com.central.service.ISearchService;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;

/**
 * 系统日志控制器
 * @author zlt
 */
@Tag(name = "系统日志")
@Slf4j
@RestController
@RequestMapping("/api-log")
public class SysLogController {

    private static final String SYS_LOG_INDEX = "sys-log-*";

    @Autowired
    private ISearchService searchService;

    @Operation(summary = "系统日志全文搜索列表")
    @GetMapping(value = "/sysLog")
    public PageResult<JsonNode> sysLog(SearchDto searchDto) {
        try {
            return searchService.strQuery(SYS_LOG_INDEX, searchDto);
        } catch (IOException e) {
            log.error("查询系统日志失败", e);
            return PageResult.<JsonNode>builder()
                    .data(Collections.emptyList())
                    .code(0)
                    .count(0L)
                    .build();
        }
    }

    /**
     * 系统日志链路列表 (按 traceId 查询)
     */
    @Operation(summary = "系统日志链路列表")
    @GetMapping(value = "/traceLog")
    public PageResult<JsonNode> traceLog(
            @RequestParam(required = false) String queryStr,
            @RequestParam(required = false) String traceId,
            @RequestParam(required = false, defaultValue = "100") Integer limit) {

        String searchTraceId = traceId;
        if (StrUtil.isEmpty(searchTraceId) && StrUtil.isNotEmpty(queryStr)) {
            if (queryStr.startsWith("traceId:")) {
                searchTraceId = queryStr.substring(8);
            } else {
                searchTraceId = queryStr;
            }
        }

        if (StrUtil.isEmpty(searchTraceId)) {
            return PageResult.<JsonNode>builder()
                    .data(Collections.emptyList())
                    .code(0)
                    .count(0L)
                    .build();
        }

        SearchDto searchDto = new SearchDto();
        searchDto.setQueryStr("traceId:\"" + searchTraceId + "\"");
        searchDto.setSortCol("timestamp");
        searchDto.setSortOrder("ASC");
        searchDto.setPage(1);
        searchDto.setLimit(limit);

        try {
            return searchService.strQuery(SYS_LOG_INDEX, searchDto);
        } catch (IOException e) {
            log.error("查询链路日志失败", e);
            return PageResult.<JsonNode>builder()
                    .data(Collections.emptyList())
                    .code(0)
                    .count(0L)
                    .build();
        }
    }
}

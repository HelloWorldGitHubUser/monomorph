package com.central.controller;

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
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;

/**
 * 审计日志控制器
 * @author zlt
 */
@Tag(name = "审计日志")
@Slf4j
@RestController
@RequestMapping("/api-log")
public class AuditLogController {

    private static final String AUDIT_LOG_INDEX = "audit-log-*";

    @Autowired
    private ISearchService searchService;

    @Operation(summary = "审计日志全文搜索列表")
    @GetMapping(value = "/auditLog")
    public PageResult<JsonNode> getPage(SearchDto searchDto) {
        searchDto.setIsHighlighter(true);
        searchDto.setSortCol("timestamp");
        try {
            return searchService.strQuery(AUDIT_LOG_INDEX, searchDto);
        } catch (IOException e) {
            log.error("查询审计日志失败", e);
            return PageResult.<JsonNode>builder()
                    .data(Collections.emptyList())
                    .code(0)
                    .count(0L)
                    .build();
        }
    }
}

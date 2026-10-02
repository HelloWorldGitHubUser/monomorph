package com.central.controller;

import com.central.model.PageResult;
import com.central.model.vo.TokenVo;
import com.central.service.ITokensService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.Map;

/**
 * Token管理控制器
 * 模拟网关路由前缀 /api-uaa
 * @author zlt
 */
@Tag(name = "Token管理")
@Slf4j
@RestController
@RequestMapping("/api-uaa/tokens")
public class TokensController {
    @Resource
    private ITokensService tokensService;

    @GetMapping("")
    @Operation(summary = "token列表")
    public PageResult<TokenVo> list(@RequestParam Map<String, Object> params, 
                                     @RequestParam(required = false) String tenantId) {
        return tokensService.listTokens(params, tenantId);
    }
}

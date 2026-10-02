package com.central.service.impl;

import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import com.central.model.PageResult;
import com.central.model.vo.TokenVo;
import com.central.service.ITokensService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Token 管理服务实现
 * 单体版简化实现 - 使用 StringRedisTemplate
 * @author zlt
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokensServiceImpl implements ITokensService {
    
    private static final String TOKEN_PREFIX = "token:";
    
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public PageResult<TokenVo> listTokens(Map<String, Object> params, String clientId) {
        Integer page = MapUtils.getInteger(params, "page", 1);
        Integer limit = MapUtils.getInteger(params, "limit", 10);
        String username = MapUtils.getString(params, "username");
        
        // 从 Redis 获取所有 token
        Set<String> keys = stringRedisTemplate.keys(TOKEN_PREFIX + "*");
        
        List<TokenVo> result = new ArrayList<>();
        if (keys != null) {
            for (String key : keys) {
                String userId = stringRedisTemplate.opsForValue().get(key);
                if (StrUtil.isNotBlank(userId)) {
                    TokenVo tokenVo = new TokenVo();
                    String tokenValue = key.replace(TOKEN_PREFIX, "");
                    tokenVo.setTokenValue(tokenValue);
                    tokenVo.setClientId(clientId);
                    tokenVo.setGrantType("password");
                    
                    // 获取过期时间
                    Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
                    if (expire != null && expire > 0) {
                        tokenVo.setExpiration(new Date(System.currentTimeMillis() + expire * 1000));
                    }
                    
                    result.add(tokenVo);
                }
            }
        }
        
        // 简单分页
        int[] startEnds = PageUtil.transToStartEnd(page - 1, limit);
        int start = Math.min(startEnds[0], result.size());
        int end = Math.min(startEnds[1], result.size());
        List<TokenVo> pageResult = result.subList(start, end);
        
        return PageResult.<TokenVo>builder()
                .data(pageResult)
                .code(0)
                .count((long) result.size())
                .build();
    }
}






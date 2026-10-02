package com.central.service;

import com.central.model.PageResult;
import com.central.model.vo.TokenVo;

import java.util.Map;

/**
 * Token 管理服务接口
 * @author zlt
 */
public interface ITokensService {
    /**
     * 查询token列表
     * @param params 请求参数
     * @param clientId 应用id
     */
    PageResult<TokenVo> listTokens(Map<String, Object> params, String clientId);
}

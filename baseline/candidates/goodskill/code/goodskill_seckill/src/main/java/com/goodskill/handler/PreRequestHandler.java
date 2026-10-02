package com.goodskill.handler;

import com.goodskill.dto.SeckillWebMockRequestDTO;

/**
 * 请求处理类
 */
public interface PreRequestHandler {
    /**
     * @param request
     */
    void handle(SeckillWebMockRequestDTO request);
}



package com.goodskill.handler;


import com.goodskill.dto.SeckillWebMockRequestDTO;
import org.springframework.core.Ordered;

public abstract class AbstractPreRequestHandler implements PreRequestHandler, Ordered {

    /**
     * @param request
     */
    @Override
    public abstract void handle(SeckillWebMockRequestDTO request);

}



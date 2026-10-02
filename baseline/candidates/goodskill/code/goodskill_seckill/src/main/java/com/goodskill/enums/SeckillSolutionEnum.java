package com.goodskill.enums;

import lombok.Getter;

/**
 * 秒杀场景枚举
 *
 * @author techa03
 * @date 2019/4/4
 */
@Getter
public enum SeckillSolutionEnum {
    /**
     * 场景一：synchronized同步锁实现
     */
    SYCHRONIZED(1,"秒杀场景一(sychronized同步锁实现)");

    private final int code;
    private final String name;

    SeckillSolutionEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

}

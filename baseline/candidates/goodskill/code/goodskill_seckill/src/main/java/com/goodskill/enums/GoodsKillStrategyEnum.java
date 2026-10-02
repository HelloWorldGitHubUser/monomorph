package com.goodskill.enums;

import com.goodskill.enums.SeckillSolutionEnum;
import com.goodskill.strategy.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 秒杀策略枚举，通过枚举避免暴露具体方法
 *
 * @author heng
 */
@Getter
@AllArgsConstructor
public enum GoodsKillStrategyEnum {

    /**
     * 场景一：Synchronized同步锁
     */
    SYNCHRONIZED(SeckillSolutionEnum.SYCHRONIZED, "Synchronized同步锁", SynchronizedLockStrategy.class.getName());


    private final SeckillSolutionEnum seckillSolutionEnum;

    private final String strategyName;

    private final String className;

    public static GoodsKillStrategyEnum stateOf(int code) {
        for (GoodsKillStrategyEnum state : values()) {
            if (state.seckillSolutionEnum.getCode() == code) {
                return state;
            }
        }
        return null;
    }
}

package com.central.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 逻辑删除条件对象
 * 用于 ES 搜索时过滤已逻辑删除的记录
 *
 * @author zlt
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogicDelDto {
    /**
     * 逻辑删除字段名
     */
    private String logicDelField;
    /**
     * 逻辑删除字段未删除的值
     */
    private String logicNotDelValue;
}






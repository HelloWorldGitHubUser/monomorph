package com.central.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 聚合统计项
 * @author zlt
 */
@Data
public class AggItemVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private Long value;
}






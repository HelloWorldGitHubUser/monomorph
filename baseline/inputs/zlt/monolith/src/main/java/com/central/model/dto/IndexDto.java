package com.central.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ES 索引DTO
 * @author zlt
 */
@Data
public class IndexDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 索引名
     */
    private String indexName;
    /**
     * 分片数
     */
    private Integer numberOfShards;
    /**
     * 副本数
     */
    private Integer numberOfReplicas;
    /**
     * 索引类型
     */
    private String type;
    /**
     * mapping内容
     */
    private String mappingsSource;
}






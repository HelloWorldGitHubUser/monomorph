package com.youlai.mall.model.pms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.mall.base.BaseEntity;
import lombok.Data;

/**
 * 分类品牌
 *
 * @author haoxr
 * @since 2022/7/2
 */
@Data
public class PmsCategoryBrand extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 品牌ID
     */
    private Long brandId;

}

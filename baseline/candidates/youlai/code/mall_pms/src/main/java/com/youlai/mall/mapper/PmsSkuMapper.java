package com.youlai.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.model.pms.dto.SkuInfoDTO;
import com.youlai.mall.model.pms.entity.PmsSku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PmsSkuMapper extends BaseMapper<PmsSku> {

    /**
     * 获取商品库存单元信息
     *
     * @param skuId
     * @return
     */
    SkuInfoDTO getSkuInfo(Long skuId);
}

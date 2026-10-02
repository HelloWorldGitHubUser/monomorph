package com.youlai.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.model.pms.entity.PmsSpu;
import com.youlai.mall.model.pms.query.SpuPageQuery;
import com.youlai.mall.model.pms.vo.PmsSpuPageVO;
import com.youlai.mall.model.pms.vo.SpuPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PmsSpuMapper extends BaseMapper<PmsSpu> {

    /**
     * Admin-商品分页列表
     *
     * @param page        分页参数
     * @param queryParams 查询参数
     * @return 商品分页列表
     */
    List<PmsSpuPageVO> listPagedSpu(Page<PmsSpuPageVO> page, SpuPageQuery queryParams);

    /**
     * APP-商品分页列表
     *
     * @param page        分页参数
     * @param queryParams 查询参数
     * @return 商品分页列表
     */
    List<SpuPageVO> listPagedSpuForApp(Page<SpuPageVO> page, SpuPageQuery queryParams);


}

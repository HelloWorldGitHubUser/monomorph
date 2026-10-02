package com.youlai.mall.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.model.sms.entity.SmsCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.model.sms.query.CouponPageQuery;
import com.youlai.mall.model.sms.vo.CouponPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SmsCouponMapper extends BaseMapper<SmsCoupon> {

    List<SmsCoupon> getCouponPage(Page<CouponPageVO> page, CouponPageQuery queryParams);
}





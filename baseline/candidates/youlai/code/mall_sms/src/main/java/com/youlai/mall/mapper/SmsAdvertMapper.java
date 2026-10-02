package com.youlai.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.model.sms.entity.SmsAdvert;
import com.youlai.mall.model.sms.query.AdvertPageQuery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SmsAdvertMapper extends BaseMapper<SmsAdvert> {

    /**
     * 广告分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    Page<SmsAdvert> getAdvertPage(Page<SmsAdvert> page, AdvertPageQuery queryParams);
}

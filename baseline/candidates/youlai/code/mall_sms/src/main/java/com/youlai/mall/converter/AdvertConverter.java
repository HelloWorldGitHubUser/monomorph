package com.youlai.mall.converter;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.model.sms.entity.SmsAdvert;
import com.youlai.mall.model.sms.vo.BannerVO;
import com.youlai.mall.model.sms.vo.AdvertPageVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * advert实体转换器
 *
 * @author haoxr
 * @since 2022/5/29
 */
@Mapper(componentModel = "spring")
public interface AdvertConverter {

    AdvertPageVO entity2PageVo(SmsAdvert entity);

    Page<AdvertPageVO> entity2PageVo(Page<SmsAdvert> po);

    BannerVO entity2BannerVo(SmsAdvert entity);
    
    List<BannerVO> entity2BannerVo(List<SmsAdvert> entities);
}
package com.youlai.mall.service.sms.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.enums.StatusEnum;
import com.youlai.mall.converter.AdvertConverter;
import com.youlai.mall.model.sms.entity.SmsAdvert;
import com.youlai.mall.mapper.SmsAdvertMapper;
import com.youlai.mall.model.sms.query.AdvertPageQuery;
import com.youlai.mall.model.sms.vo.BannerVO;
import com.youlai.mall.model.sms.vo.AdvertPageVO;
import com.youlai.mall.service.sms.SmsAdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 广告业务实现类
 *
 * @author Ray Hao
 * @since 2022/5/28
 */
@Service
@RequiredArgsConstructor
public class SmsAdvertServiceImpl extends ServiceImpl<SmsAdvertMapper, SmsAdvert> implements SmsAdvertService {

    private final AdvertConverter advertConverter;

    /**
     * 广告分页列表
     *
     * @param queryParams 查询参数
     * @return 广告分页列表
     */
    @Override
    public Page<AdvertPageVO> getAdvertPage(AdvertPageQuery queryParams) {
        Page<SmsAdvert> page = this.baseMapper.getAdvertPage(new Page<>(queryParams.getPageNum(),
                        queryParams.getPageSize()),
                queryParams);
        return advertConverter.entity2PageVo(page);
    }

    /**
     * 获取广告横幅列表
     */
    @Override
    public List<BannerVO> getBannerList() {

        List<SmsAdvert> entities = this.list(new LambdaQueryWrapper<SmsAdvert>().
                eq(SmsAdvert::getStatus, StatusEnum.ENABLE.getValue())
                .select(SmsAdvert::getTitle, SmsAdvert::getImageUrl, SmsAdvert::getRedirectUrl)
        );
        return advertConverter.entity2BannerVo(entities);
    }
}

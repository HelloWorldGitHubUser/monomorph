package com.passjava.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.passjava.utils.PageUtils;
import com.passjava.entity.BannerEntity;

import java.util.Map;

/**
 * 横幅广告服务
 */
public interface BannerService extends IService<BannerEntity> {
    PageUtils queryPage(Map<String, Object> params);
}






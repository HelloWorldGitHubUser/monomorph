package com.passjava.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.passjava.utils.PageUtils;
import com.passjava.entity.ChannelEntity;

import java.util.Map;

/**
 * 渠道服务
 */
public interface ChannelService extends IService<ChannelEntity> {
    PageUtils queryPage(Map<String, Object> params);
}






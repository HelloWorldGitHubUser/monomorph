package com.passjava.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.passjava.utils.PageUtils;
import com.passjava.entity.GrowthChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变化历史服务
 */
public interface GrowthChangeHistoryService extends IService<GrowthChangeHistoryEntity> {
    PageUtils queryPage(Map<String, Object> params);
}






package com.passjava.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.passjava.utils.PageUtils;
import com.passjava.entity.ViewLogEntity;

import java.util.Map;

/**
 * 浏览记录服务
 */
public interface ViewLogService extends IService<ViewLogEntity> {
    PageUtils queryPage(Map<String, Object> params);
}






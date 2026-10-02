package com.passjava.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.passjava.utils.PageUtils;
import com.passjava.entity.TypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 题目类型服务
 */
public interface ITypeService extends IService<TypeEntity> {
    PageUtils queryPage(Map<String, Object> params);
    
    /**
     * 获取类型列表（带缓存）
     */
    List<TypeEntity> getTypeEntityList();
    
    /**
     * 获取类型列表（带本地锁保护缓存）
     * 单体应用使用本地锁替代分布式锁
     */
    List<TypeEntity> getTypeEntityListWithLock();
}





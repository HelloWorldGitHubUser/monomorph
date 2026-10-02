package com.passjava.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.passjava.utils.PageUtils;
import com.passjava.entity.NewsEntity;

import java.util.Map;

/**
 * 资讯服务
 */
public interface NewsService extends IService<NewsEntity> {
    PageUtils queryPage(Map<String, Object> params);
}






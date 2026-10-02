package com.passjava.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.passjava.utils.PageUtils;
import com.passjava.entity.AccessTokenEntity;

import java.util.Map;

/**
 * 访问令牌服务
 */
public interface AccessTokenService extends IService<AccessTokenEntity> {
    PageUtils queryPage(Map<String, Object> params);
}






package com.passjava.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.passjava.utils.PageUtils;
import com.passjava.utils.Query;
import com.passjava.dao.TypeDao;
import com.passjava.entity.TypeEntity;
import com.passjava.service.ITypeService;

/**
 * 题目类型服务实现
 * 
 * 单体应用中已转换为 synchronized 本地锁，适用于单实例部署场景
 */
@Service("typeService")
public class TypeServiceImpl extends ServiceImpl<TypeDao, TypeEntity> implements ITypeService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TypeEntity> page = this.page(
                new Query<TypeEntity>().getPage(params),
                new QueryWrapper<TypeEntity>()
        );
        return new PageUtils(page);
    }

    /**
     * 获取类型列表（带缓存，无锁保护）
     */
    @Override
    public List<TypeEntity> getTypeEntityList() {
        // 1.从缓存中查询数据
        String typeEntityListCache = stringRedisTemplate.opsForValue().get("typeEntityList");
        // 2.如果缓存中有数据
        if (!StringUtils.isEmpty(typeEntityListCache)) {
            // 反序列化为实例对象
            return JSON.parseObject(typeEntityListCache, new TypeReference<List<TypeEntity>>(){});
        }
        // 3.如果缓存中没有数据，从数据库中查询
        System.out.println("缓存为空，从数据库查询");
        List<TypeEntity> typeEntityListFromDb = this.list();
        // 4.将从数据库中查询出的数据序列化 JSON 字符串
        String jsonStr = JSON.toJSONString(typeEntityListFromDb);
        // 5.将序列化后的数据存入缓存中
        stringRedisTemplate.opsForValue().set("typeEntityList", jsonStr, 1, TimeUnit.DAYS);
        return typeEntityListFromDb;
    }

    /**
     * 获取类型列表（带本地锁保护缓存）
     * 
     * 
     * 单体应用实现：
     * 使用 synchronized 本地锁，保证单实例内的线程安全
     * 适用于单体应用的单实例部署场景
     */
    @Override
    public List<TypeEntity> getTypeEntityListWithLock() {
        // 1.先尝试从缓存获取（无需加锁）
        String typeEntityListCache = stringRedisTemplate.opsForValue().get("typeEntityList");
        if (!StringUtils.isEmpty(typeEntityListCache)) {
            return JSON.parseObject(typeEntityListCache, new TypeReference<List<TypeEntity>>(){});
        }
        
        // 2.缓存未命中，加锁后再次检查并更新缓存（双重检查锁定）
        synchronized (this) {
            System.out.println("获取 synchronized 锁成功，线程 ID：" + Thread.currentThread().getId());
            return getDataFromDbWithCache();
        }
    }

    /**
     * 从数据库获取数据并更新缓存（需在锁保护下调用）
     */
    private List<TypeEntity> getDataFromDbWithCache() {
        // 双重检查：再次从缓存中查询数据
        String typeEntityListCache = stringRedisTemplate.opsForValue().get("typeEntityList");
        if (!StringUtils.isEmpty(typeEntityListCache)) {
            return JSON.parseObject(typeEntityListCache, new TypeReference<List<TypeEntity>>(){});
        }
        
        // 缓存中没有数据，从数据库中查询
        System.out.println("缓存为空，从数据库查询");
        List<TypeEntity> typeEntityListFromDb = this.list();
        
        // 将数据序列化并存入缓存
        String jsonStr = JSON.toJSONString(typeEntityListFromDb);
        stringRedisTemplate.opsForValue().set("typeEntityList", jsonStr, 1, TimeUnit.DAYS);
        
        return typeEntityListFromDb;
    }
}


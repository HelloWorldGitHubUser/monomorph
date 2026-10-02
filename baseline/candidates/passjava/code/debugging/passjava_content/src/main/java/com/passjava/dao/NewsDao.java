package com.passjava.dao;

import com.passjava.entity.NewsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 内容-资讯表
 */
@Mapper
public interface NewsDao extends BaseMapper<NewsEntity> {
}






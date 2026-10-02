package com.passjava.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.passjava.utils.PageUtils;
import com.passjava.utils.Query;
import com.passjava.dao.BannerDao;
import com.passjava.entity.BannerEntity;
import com.passjava.service.BannerService;

@Service("bannerService")
public class BannerServiceImpl extends ServiceImpl<BannerDao, BannerEntity> implements BannerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BannerEntity> page = this.page(
                new Query<BannerEntity>().getPage(params),
                new QueryWrapper<BannerEntity>()
        );
        return new PageUtils(page);
    }
}






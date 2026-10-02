package com.youlai.mall.service.pms.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.mapper.PmsBrandMapper;
import com.youlai.mall.model.pms.entity.PmsBrand;
import com.youlai.mall.service.pms.BrandService;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements BrandService {
}

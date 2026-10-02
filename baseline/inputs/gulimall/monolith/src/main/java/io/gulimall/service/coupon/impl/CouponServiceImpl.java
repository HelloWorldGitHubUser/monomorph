package io.gulimall.service.coupon.impl;

import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gulimall.utils.PageUtils;
import io.gulimall.utils.Query;

import io.gulimall.dao.coupon.CouponDao;
import io.gulimall.entity.coupon.CouponEntity;
import io.gulimall.service.coupon.CouponService;


@Service("couponService")
public class CouponServiceImpl extends ServiceImpl<CouponDao, CouponEntity> implements CouponService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CouponEntity> page = this.page(
                new Query<CouponEntity>().getPage(params),
                new QueryWrapper<CouponEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CouponEntity> listMemberCoupons() {
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponName("discount 20%");
        return Collections.singletonList(couponEntity);
    }

}

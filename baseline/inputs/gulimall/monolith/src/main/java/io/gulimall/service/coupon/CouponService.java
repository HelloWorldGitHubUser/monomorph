package io.gulimall.service.coupon;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gulimall.utils.PageUtils;
import io.gulimall.entity.coupon.CouponEntity;

import java.util.List;
import java.util.Map;

/**
 * 优惠券信息
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 20:03:33
 */
public interface CouponService extends IService<CouponEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CouponEntity> listMemberCoupons();
}

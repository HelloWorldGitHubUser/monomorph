package io.gulimall.service.coupon;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gulimall.to.SkuReductionTo;
import io.gulimall.utils.PageUtils;
import io.gulimall.entity.coupon.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 20:03:33
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReductionTo(SkuReductionTo skuReductionTo);
}


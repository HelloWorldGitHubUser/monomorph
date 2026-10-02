package io.gulimall.service.ware;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gulimall.vo.SkuHasStockVo;
import io.gulimall.to.mq.OrderTo;
import io.gulimall.utils.PageUtils;
import io.gulimall.entity.ware.WareSkuEntity;
import io.gulimall.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:15:25
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkuHasStocks(List<Long> ids);

    Boolean orderLockStock(WareSkuLockVo lockVo);

    void unlock(OrderTo orderTo);

    void releaseExpiredLocks(long maxLockMinutes);
}


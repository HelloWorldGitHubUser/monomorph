package io.gulimall.service.ware;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gulimall.utils.PageUtils;
import io.gulimall.entity.ware.PurchaseEntity;
import io.gulimall.vo.ware.MergeVo;
import io.gulimall.vo.ware.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:15:25
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils listUnreceive(Map<String, Object> params);

    void mergePurchaseDetail(MergeVo mergeVo);

    void ReceivedPurchase(List<Long> ids);

    void finishPurchase(PurchaseDoneVo purchaseDoneVo);
}


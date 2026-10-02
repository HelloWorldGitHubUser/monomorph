package io.gulimall.service.order;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gulimall.to.mq.SeckillOrderTo;
import io.gulimall.utils.PageUtils;
import io.gulimall.entity.order.OrderEntity;
import io.gulimall.vo.order.*;

import java.util.Map;

/**
 * 订单
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:07:28
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo confirmOrder();

    SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo);

    OrderEntity getOrderByOrderSn(String orderSn);

    void closeOrder(OrderEntity orderEntity);

    PageUtils getMemberOrderPage(Map<String, Object> params);

    PayVo getOrderPay(String orderSn);

    void handlerPayResult(PayAsyncVo payAsyncVo);

    void createSeckillOrder(SeckillOrderTo orderTo);

    /**
     * 模拟支付 - 直接将订单状态改为已支付（用于测试）
     * @param orderSn 订单号
     * @return 是否成功
     */
    boolean mockPay(String orderSn);
}


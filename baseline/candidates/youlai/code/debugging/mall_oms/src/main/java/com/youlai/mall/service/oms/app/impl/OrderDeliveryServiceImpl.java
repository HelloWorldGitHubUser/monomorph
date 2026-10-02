package com.youlai.mall.service.oms.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.mapper.OrderDeliveryMapper;
import com.youlai.mall.model.oms.entity.OmsOrderDelivery;
import com.youlai.mall.service.oms.app.OrderDeliveryService;
import org.springframework.stereotype.Service;

@Service("orderDeliveryService")
public class OrderDeliveryServiceImpl extends ServiceImpl<OrderDeliveryMapper, OmsOrderDelivery> implements OrderDeliveryService {

}

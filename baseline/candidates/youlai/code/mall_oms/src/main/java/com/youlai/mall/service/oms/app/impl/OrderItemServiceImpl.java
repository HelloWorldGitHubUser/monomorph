package com.youlai.mall.service.oms.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.mapper.OrderItemMapper;
import com.youlai.mall.model.oms.entity.OmsOrderItem;
import com.youlai.mall.service.oms.app.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OmsOrderItem> implements OrderItemService {


}

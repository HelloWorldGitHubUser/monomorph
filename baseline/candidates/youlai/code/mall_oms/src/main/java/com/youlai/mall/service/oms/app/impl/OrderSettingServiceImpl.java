package com.youlai.mall.service.oms.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.mapper.OrderSettingMapper;
import com.youlai.mall.model.oms.entity.OmsOrderSetting;
import com.youlai.mall.service.oms.app.OrderSettingService;
import org.springframework.stereotype.Service;


@Service
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OmsOrderSetting> implements OrderSettingService {

}

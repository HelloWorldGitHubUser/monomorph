package com.youlai.mall.service.oms.app.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.security.util.SecurityUtils;
import com.youlai.mall.mapper.OrderLogMapper;
import com.youlai.mall.model.oms.entity.OmsOrderLog;
import com.youlai.mall.service.oms.app.OrderLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderLogServiceImpl extends ServiceImpl<OrderLogMapper, OmsOrderLog> implements OrderLogService {
    @Override
    public void addOrderLogs(Long orderId, Integer orderStatus, String user, String detail) {
        log.info("添加订单操作日志，orderId={}，detail={}", orderId, detail);
        OmsOrderLog orderLog = new OmsOrderLog();
        orderLog.setDetail(detail);
        orderLog.setOrderId(orderId);
        orderLog.setOrderStatus(orderStatus);
        orderLog.setUser(user);
        this.save(orderLog);
    }

    @Override
    public void addOrderLogs(Long orderId, Integer orderStatus, String detail) {
        Long memberId = SecurityUtils.getMemberId();
        addOrderLogs(orderId, orderStatus, memberId.toString(), detail);
    }

}

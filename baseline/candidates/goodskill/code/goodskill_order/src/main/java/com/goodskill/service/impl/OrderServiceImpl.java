package com.goodskill.service.impl;


import com.goodskill.entity.mongo.Order;
import com.goodskill.dto.OrderDTO;
import com.goodskill.enums.OrderStatusEnum;
import com.goodskill.repository.mongo.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author heng
 */
@Slf4j
@Service
public class OrderServiceImpl {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Boolean deleteRecord(long seckillId) {
        orderRepository.deleteBySeckillId(seckillId);
        return true;
    }

    public String saveRecord(OrderDTO orderDTO) {
        OrderStatusEnum status = OrderStatusEnum.getByCode(orderDTO.getStatus());
        if (status == null) {
            status = OrderStatusEnum.PENDING_PAYMENT;
        }
        LocalDateTime createTime = orderDTO.getCreateTime() == null ? LocalDateTime.now() : orderDTO.getCreateTime();
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .seckillId(orderDTO.getSeckillId())
                .userPhone(orderDTO.getUserPhone())
                .status(status.getCode())
                .createTime(createTime)
                .serverIp(orderDTO.getServerIp())
                .userIp(orderDTO.getUserIp())
                .userId(orderDTO.getUserId())
                .goodsName(orderDTO.getGoodsName())
                .goodsTitle(orderDTO.getGoodsTitle())
                .goodsImg(orderDTO.getGoodsImg())
                .seckillPrice(orderDTO.getSeckillPrice())
                .stateDesc(orderDTO.getStateDesc() == null ? status.getDesc() : orderDTO.getStateDesc())
                .build();
        orderRepository.insert(order);
        log.info("保存成功,{}", order);
        return order.getId();
    }

    public Long count(long seckillId) {
        return orderRepository.countBySeckillId(seckillId);
    }

    public Page<Order> list(String userId, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        if (userId == null || userId.isBlank()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        return orderRepository.findByUserIdOrderByCreateTimeDesc(userId, pageable);
    }

    public Order findById(String orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.orElse(null);
    }

    public boolean updateOrderStatus(String orderId, Byte status, String stateDesc, String alipayTradeNo, String timestamp) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            log.warn("更新订单状态失败: 订单不存在, orderId={}", orderId);
            return false;
        }
        Order order = optionalOrder.get();
        order.setStatus(status);
        order.setStateDesc(stateDesc);
        if (alipayTradeNo != null) {
            order.setAlipayTradeNo(alipayTradeNo);
        }
        if (timestamp != null && !timestamp.isBlank()) {
            order.setPayCompleteTime(parseAlipayTimestamp(timestamp));
        } else if (OrderStatusEnum.PAID.getCode().equals(status)) {
            order.setPayCompleteTime(LocalDateTime.now());
        }
        orderRepository.save(order);
        log.info("更新订单状态成功: orderId={}, status={}, stateDesc={}, alipayTradeNo={}", orderId, status, stateDesc, alipayTradeNo);
        return true;
    }

    public Page<Order> adminList(int page, int size, String orderId, Long seckillId, String userPhone, Long userId,
                                 Integer status, String startTime, String endTime) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createTime"));
        Query query = new Query();
        if (orderId != null && !orderId.isBlank()) {
            query.addCriteria(Criteria.where("id").is(orderId));
        }
        if (seckillId != null) {
            query.addCriteria(Criteria.where("seckillId").is(seckillId));
        }
        if (userPhone != null && !userPhone.isBlank()) {
            query.addCriteria(Criteria.where("userPhone").is(userPhone));
        }
        if (userId != null) {
            query.addCriteria(Criteria.where("userId").is(userId.toString()));
        }
        if (status != null) {
            query.addCriteria(Criteria.where("status").is(status));
        }
        LocalDateTime parsedStart = parseDateTime(startTime);
        LocalDateTime parsedEnd = parseDateTime(endTime);
        if (parsedStart != null || parsedEnd != null) {
            Criteria criteria = Criteria.where("createTime");
            if (parsedStart != null) {
                criteria.gte(parsedStart);
            }
            if (parsedEnd != null) {
                criteria.lte(parsedEnd);
            }
            query.addCriteria(criteria);
        }
        long total = mongoTemplate.count(query, Order.class);
        query.with(pageable);
        List<Order> orders = mongoTemplate.find(query, Order.class);
        return new PageImpl<>(orders, pageable, total);
    }

    public Boolean deleteById(String id) {
        orderRepository.deleteById(id);
        return true;
    }

    public Boolean batchDelete(List<String> ids) {
        orderRepository.deleteAllById(ids);
        return true;
    }

    public Boolean cancelOrder(String orderId, String userId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return false;
        }
        Order order = optionalOrder.get();
        if (order.getUserId() == null || !order.getUserId().equals(userId)) {
            return false;
        }
        if (!OrderStatusEnum.PENDING_PAYMENT.getCode().equals(order.getStatus())) {
            return false;
        }
        order.setStatus(OrderStatusEnum.CANCELLED.getCode());
        order.setStateDesc(OrderStatusEnum.CANCELLED.getDesc());
        orderRepository.save(order);
        return true;
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isBlank()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            log.warn("时间格式解析失败: dateTimeStr={}", dateTimeStr);
            return null;
        }
    }

    private LocalDateTime parseAlipayTimestamp(String timestamp) {
        try {
            return LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            log.warn("支付宝时间格式解析失败: timestamp={}", timestamp);
            return LocalDateTime.now();
        }
    }

}

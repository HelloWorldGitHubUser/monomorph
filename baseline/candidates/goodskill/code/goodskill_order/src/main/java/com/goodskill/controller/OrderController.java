package com.goodskill.controller;

import com.goodskill.dto.OrderDTO;
import com.goodskill.dto.Result;
import com.goodskill.entity.mongo.Order;
import com.goodskill.service.impl.OrderServiceImpl;
import com.goodskill.util.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author heng
 */
@Slf4j
@RestController
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    @DeleteMapping("/deleteRecord")
    public Boolean deleteRecord(long seckillId) {
        return orderService.deleteRecord(seckillId);
    }

    @PostMapping("/saveRecord")
    public String saveRecord(@RequestBody OrderDTO orderDTO) {
        return orderService.saveRecord(orderDTO);
    }

    @GetMapping("/count")
    public Long count(long seckillId) {
        return orderService.count(seckillId);
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> list(@RequestParam(defaultValue = "1") int pageNum,
                                            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Order> orderPage = orderService.list(UserInfoUtil.getUserId(), pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("records", orderPage.getContent());
        result.put("total", orderPage.getTotalElements());
        result.put("size", orderPage.getSize());
        result.put("current", orderPage.getNumber() + 1);
        result.put("pages", orderPage.getTotalPages());
        return Result.ok(result);
    }

    @GetMapping("/detail/{orderId}")
    public Order detail(@PathVariable String orderId) {
        return orderService.findById(orderId);
    }

    @PostMapping("/cancel/{orderId}")
    public Result<Boolean> cancelOrder(@PathVariable String orderId) {
        boolean result = orderService.cancelOrder(orderId, UserInfoUtil.getUserId());
        return result ? Result.ok(true) : Result.fail("取消订单失败");
    }

}

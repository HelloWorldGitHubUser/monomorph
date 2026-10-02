package com.goodskill.controller;
import com.goodskill.dto.Result;
import com.goodskill.dto.SuccessKilledDTO;
import com.goodskill.exception.SeckillException;
import com.goodskill.monomorph.dto.generated.client.OrderDTO;
import com.goodskill.monomorph.dto.generated.client.OrderStatusEnum;
import com.goodskill.monomorph.id.generated.client.OrderServiceImpl;
import com.goodskill.service.SeckillService;
import com.goodskill.util.MD5Util;
import com.goodskill.util.UserInfoUtil;
import com.goodskill.vo.SeckillVO;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/customer")
@Slf4j
public class SeckillCustomerController {
    @Resource
    private SeckillService seckillService;

    @Resource
    private OrderServiceImpl orderService;

    @PostMapping("/execute")
    public Result<Map<String, Object>> executeSeckill(@RequestParam("seckillId")
    long seckillId, @RequestParam("userPhone")
    String userPhone, @RequestParam("md5")
    String md5) {
        if (!MD5Util.getMD5(seckillId).equals(md5)) {
            return Result.fail("秒杀地址已失效");
        }
        SuccessKilledDTO successKilledDTO = new SuccessKilledDTO();
        successKilledDTO.setSeckillId(seckillId);
        successKilledDTO.setUserPhone(userPhone);
        successKilledDTO.setCreateTime(new Date());
        successKilledDTO.setUserId(UserInfoUtil.getUserId());
        int result = seckillService.reduceNumber(successKilledDTO);
        if (result <= 0) {
            return Result.fail("秒杀失败，库存不足");
        }
        SeckillVO seckillVO = seckillService.findById(seckillId);
        if (seckillVO == null) {
            throw new SeckillException("商品信息不存在");
        }
        OrderStatusEnum pendingStatus = OrderStatusEnum.PENDING_PAYMENT;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setSeckillId(seckillId);
        orderDTO.setUserPhone(userPhone);
        orderDTO.setUserId(UserInfoUtil.getUserId());
        orderDTO.setStatus(pendingStatus.getCode());
        orderDTO.setCreateTime(LocalDateTime.now());
        orderDTO.setGoodsName(seckillVO.getName());
        orderDTO.setGoodsTitle(seckillVO.getName());
        orderDTO.setGoodsImg(seckillVO.getPhotoUrl());
        if (seckillVO.getPrice() != null) {
            orderDTO.setSeckillPrice(seckillVO.getPrice().doubleValue());
        }
        orderDTO.setStateDesc(pendingStatus.getDesc());
        String orderId;
        try {
            orderId = orderService.saveRecord(orderDTO);
        } catch (Exception e) {
            log.error("创建订单失败: seckillId={}, userPhone={}", seckillId, userPhone, e);
            throw new SeckillException("创建订单失败");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("orderId", orderId);
        data.put("seckillId", seckillId);
        data.put("userPhone", userPhone);
        data.put("status", pendingStatus.getCode());
        data.put("createTime", new Date());
        return Result.ok(data);
    }
}
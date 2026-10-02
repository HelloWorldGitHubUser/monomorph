package com.youlai.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.mall.result.PageResult;
import com.youlai.mall.result.Result;
import com.youlai.mall.model.oms.dto.OrderDTO;
import com.youlai.mall.model.oms.entity.OmsOrder;
import com.youlai.mall.model.oms.entity.OmsOrderItem;
import com.youlai.mall.model.oms.query.OrderPageQuery;
import com.youlai.mall.model.oms.vo.OmsOrderPageVO;
import com.youlai.mall.service.oms.admin.OmsOrderService;
import com.youlai.mall.service.oms.app.OrderItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Admin-订单控制层
 *
 * @author huawei
 * @since 2.3.0
 */
@Tag(name  = "Admin-订单管理")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OmsOrderController {

    private final OmsOrderService orderService;

    private final OrderItemService orderItemService;

    @Operation(summary ="订单分页列表")
    @GetMapping
    public PageResult<OmsOrderPageVO> getOrderPage(OrderPageQuery queryParams) {
        IPage<OmsOrderPageVO> page = orderService.getOrderPage(queryParams);
        return PageResult.success(page);
    }

    @Operation(summary = "订单详情")
    @GetMapping("/{orderId}")
    public Result getOrderDetail(
            @Parameter(description ="订单ID") @PathVariable Long orderId
    ) {
        OrderDTO orderDTO = new OrderDTO();
        // 订单
        OmsOrder order = orderService.getById(orderId);

        // 订单明细
        List<OmsOrderItem> orderItems = orderItemService.list(new LambdaQueryWrapper<OmsOrderItem>()
                .eq(OmsOrderItem::getOrderId, orderId)
        );
        orderItems = Optional.ofNullable(orderItems).orElse(Collections.EMPTY_LIST);

        orderDTO.setOrder(order).setOrderItems(orderItems);
        return Result.success(orderDTO);
    }
}





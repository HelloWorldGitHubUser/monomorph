package com.youlai.mall.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.model.oms.bo.OrderBO;
import com.youlai.mall.model.oms.entity.OmsOrder;
import com.youlai.mall.model.oms.form.OrderSubmitForm;
import com.youlai.mall.model.oms.vo.OmsOrderPageVO;
import com.youlai.mall.model.oms.vo.OrderPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


/**
 * 订单对象转化器
 *
 * @author haoxr
 * @since 2.0.0
 */
@Mapper(componentModel = "spring")
public interface OrderConverter {

    @Mappings({
            @Mapping(target = "orderSn", source = "orderToken"),
            @Mapping(target = "totalQuantity",
                    expression = "java(orderSubmitForm.getOrderItems().stream().map(OrderSubmitForm.OrderItem::getQuantity).reduce(0, Integer::sum))"),
            @Mapping(target = "totalAmount",
                    expression = "java(orderSubmitForm.getOrderItems().stream().map(item -> item.getPrice() * item.getQuantity()).reduce(0L, Long::sum))"),
            @Mapping(target = "source", expression = "java(orderSubmitForm.getOrderSource().getValue())"),
    })
    OmsOrder form2Entity(OrderSubmitForm orderSubmitForm);

    @Mappings({
            @Mapping(
                    target = "paymentMethodLabel",
                    expression = "java(com.youlai.mall.base.IBaseEnum.getLabelByValue(bo.getPaymentMethod(), com.youlai.mall.enums.PaymentMethodEnum.class))"
            ),
            @Mapping(
                    target = "sourceLabel",
                    expression = "java(com.youlai.mall.base.IBaseEnum.getLabelByValue(bo.getSource(), com.youlai.mall.enums.OrderSourceEnum.class))"
            ),
            @Mapping(
                    target = "statusLabel",
                    expression = "java(com.youlai.mall.base.IBaseEnum.getLabelByValue(bo.getStatus(), com.youlai.mall.enums.OrderStatusEnum.class))"
            ),
            @Mapping(
                    target = "orderItems",
                    source = "orderItems"
            )
    })
    OmsOrderPageVO toVoPage(OrderBO bo);

    Page<OmsOrderPageVO> toVoPage(Page<OrderBO> boPage);

    OmsOrderPageVO.OrderItem toVoPageOrderItem(OrderBO.OrderItem orderItem);


    @Mappings({
            @Mapping(
                    target = "paymentMethodLabel",
                    expression = "java(com.youlai.mall.base.IBaseEnum.getLabelByValue(bo.getPaymentMethod(), com.youlai.mall.enums.PaymentMethodEnum.class))"
            ),
            @Mapping(
                    target = "sourceLabel",
                    expression = "java(com.youlai.mall.base.IBaseEnum.getLabelByValue(bo.getSource(), com.youlai.mall.enums.OrderSourceEnum.class))"
            ),
            @Mapping(
                    target = "statusLabel",
                    expression = "java(com.youlai.mall.base.IBaseEnum.getLabelByValue(bo.getStatus(), com.youlai.mall.enums.OrderStatusEnum.class))"
            ),
            @Mapping(
                    target = "orderItems",
                    source = "orderItems"
            )
    })
    OrderPageVO toVoPageForApp(OrderBO bo);

    Page<OrderPageVO> toVoPageForApp(Page<OrderBO> boPage);

    OrderPageVO.OrderItem toVoPageOrderItemForApp(OrderBO.OrderItem orderItem);
}
package com.youlai.mall.model.oms.dto;

import com.youlai.mall.model.oms.entity.OmsOrder;
import com.youlai.mall.model.oms.entity.OmsOrderItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderDTO {

    private OmsOrder order;

    private List<OmsOrderItem> orderItems;

}

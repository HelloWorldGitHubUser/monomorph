package com.hoangtien2k3.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hoangtien2k3.ecommerce.dto.response.OrderResponse;
import com.hoangtien2k3.ecommerce.dto.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderItemDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer productId;
    private Integer orderId;
    private Integer orderedQuantity;

    @JsonProperty("product")
    @JsonInclude(Include.NON_NULL)
    private ProductResponse productDto;

    @JsonProperty("order")
    @JsonInclude(Include.NON_NULL)
    private OrderResponse orderDto;

}

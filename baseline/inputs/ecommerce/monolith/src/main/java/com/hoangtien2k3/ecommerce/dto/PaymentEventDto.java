package com.hoangtien2k3.ecommerce.dto;

import com.hoangtien2k3.ecommerce.model.payment.PaymentStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Builder
public class PaymentEventDto {
    private Integer paymentId;
    private Boolean isPayed;
    private PaymentStatus paymentStatus;
    private Integer orderId;
    private Long userId;
}

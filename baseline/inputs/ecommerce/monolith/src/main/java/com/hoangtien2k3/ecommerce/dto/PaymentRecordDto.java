package com.hoangtien2k3.ecommerce.dto;

import com.hoangtien2k3.ecommerce.model.notification.PaymentRecordStatus;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Builder
public class PaymentRecordDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer paymentId;
    private Boolean isPayed;
    private PaymentRecordStatus paymentStatus;

    private Integer orderId;
    private Long userId;

}

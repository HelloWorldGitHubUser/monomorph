package com.hoangtien2k3.ecommerce.model.notification;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "payments")
public class PaymentRecord {

    @Id
    private String id;

    private Integer paymentId;
    private Boolean isPayed;
    private PaymentRecordStatus paymentStatus;

    private Integer orderId;
    private Long userId;

}

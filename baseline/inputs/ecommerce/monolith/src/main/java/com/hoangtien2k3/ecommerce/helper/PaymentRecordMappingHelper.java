package com.hoangtien2k3.ecommerce.helper;

import com.hoangtien2k3.ecommerce.dto.PaymentRecordDto;
import com.hoangtien2k3.ecommerce.model.notification.PaymentRecord;

public interface PaymentRecordMappingHelper {

    static PaymentRecordDto map(PaymentRecord paymentRecord) {
        if (paymentRecord == null) return null;
        return PaymentRecordDto.builder()
                .paymentId(paymentRecord.getPaymentId())
                .isPayed(paymentRecord.getIsPayed())
                .paymentStatus(paymentRecord.getPaymentStatus())
                .orderId(paymentRecord.getOrderId())
                .userId(paymentRecord.getUserId())
                .build();
    }

    static PaymentRecord map(PaymentRecordDto paymentRecordDto) {
        if (paymentRecordDto == null) return null;
        return PaymentRecord.builder()
                .paymentId(paymentRecordDto.getPaymentId())
                .isPayed(paymentRecordDto.getIsPayed())
                .paymentStatus(paymentRecordDto.getPaymentStatus())
                .orderId(paymentRecordDto.getOrderId())
                .userId(paymentRecordDto.getUserId())
                .build();
    }

}

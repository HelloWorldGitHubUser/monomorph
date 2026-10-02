package com.hoangtien2k3.ecommerce.service;

import com.hoangtien2k3.ecommerce.dto.PaymentRecordDto;
import com.hoangtien2k3.ecommerce.model.notification.PaymentRecord;

import java.util.List;

public interface PaymentRecordService {
    PaymentRecord savePayment(PaymentRecordDto paymentRecordDto);
    PaymentRecord getPayment(Integer paymentId);
    List<PaymentRecord> getAllPayments();
    void deletePayment(Integer paymentId);
}

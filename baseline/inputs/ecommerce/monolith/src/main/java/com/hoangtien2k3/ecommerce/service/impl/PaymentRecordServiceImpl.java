package com.hoangtien2k3.ecommerce.service.impl;

import com.hoangtien2k3.ecommerce.dto.PaymentRecordDto;
import com.hoangtien2k3.ecommerce.helper.PaymentRecordMappingHelper;
import com.hoangtien2k3.ecommerce.model.notification.PaymentRecord;
import com.hoangtien2k3.ecommerce.repository.notification.PaymentRecordRepository;
import com.hoangtien2k3.ecommerce.service.PaymentRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PaymentRecordServiceImpl implements PaymentRecordService {

    private final PaymentRecordRepository paymentRecordRepository;

    @Autowired
    public PaymentRecordServiceImpl(PaymentRecordRepository paymentRecordRepository) {
        this.paymentRecordRepository = paymentRecordRepository;
    }

    @Override
    public PaymentRecord savePayment(PaymentRecordDto paymentRecordDto) {
        return paymentRecordRepository.save(PaymentRecordMappingHelper.map(paymentRecordDto));
    }

    @Override
    public PaymentRecord getPayment(Integer paymentId) {
        return paymentRecordRepository.findById(paymentId).orElse(null);
    }

    @Override
    public List<PaymentRecord> getAllPayments() {
        return paymentRecordRepository.findAll();
    }

    @Override
    public void deletePayment(Integer paymentId) {
        log.info("Void, service; delete payment by id");
        paymentRecordRepository.deleteById(paymentId);
    }
}

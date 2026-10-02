package com.hoangtien2k3.ecommerce.controller;

import com.hoangtien2k3.ecommerce.dto.PaymentRecordDto;
import com.hoangtien2k3.ecommerce.model.notification.PaymentRecord;
import com.hoangtien2k3.ecommerce.service.PaymentRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment-records")
public class PaymentRecordController {

    private final PaymentRecordService paymentRecordService;

    @PostMapping
    public ResponseEntity<PaymentRecord> savePayment(@RequestBody PaymentRecordDto paymentRecordDto) {
        return ResponseEntity.ok(paymentRecordService.savePayment(paymentRecordDto));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentRecord> getPayment(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentRecordService.getPayment(paymentId));
    }

    @GetMapping
    public ResponseEntity<List<PaymentRecord>> getAllPayments() {
        return ResponseEntity.ok(paymentRecordService.getAllPayments());
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer paymentId) {
        paymentRecordService.deletePayment(paymentId);
        return ResponseEntity.ok().build();
    }

}

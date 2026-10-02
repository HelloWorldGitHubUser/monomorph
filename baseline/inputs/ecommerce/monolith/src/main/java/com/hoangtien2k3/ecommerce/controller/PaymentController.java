package com.hoangtien2k3.ecommerce.controller;

import com.hoangtien2k3.ecommerce.dto.PaymentDto;
import com.hoangtien2k3.ecommerce.dto.response.OrderResponse;
import com.hoangtien2k3.ecommerce.service.PaymentService;
import com.hoangtien2k3.ecommerce.service.impl.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentServiceImpl paymentServiceImpl;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<PaymentDto>> findAll() {
        log.info("*** PaymentDto List, controller; fetch all payments *");
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<PaymentDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "paymentId") String sortBy,
                                                     @RequestParam(defaultValue = "asc") String sortOrder) {
        return ResponseEntity.ok(paymentService.findAll(page, size, sortBy, sortOrder));
    }

    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<PaymentDto> findById(@PathVariable("paymentId") final String paymentId) {
        log.info("*** PaymentDto, resource; fetch payment by id *");
        return ResponseEntity.ok(paymentService.findById(Integer.parseInt(paymentId)));
    }

    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDto(@PathVariable("orderId") final Integer orderId) {
        return ResponseEntity.ok(paymentServiceImpl.getOrderDto(orderId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<PaymentDto> save(@RequestBody final PaymentDto paymentDto) {
        log.info("*** PaymentDto, resource; save payment *");
        return ResponseEntity.ok(paymentService.save(paymentDto));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PaymentDto> update(@RequestBody final PaymentDto paymentDto) {
        log.info("*** PaymentDto, resource; update payment *");
        return ResponseEntity.ok(paymentService.update(paymentDto));
    }

    @PutMapping("/{paymentId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<PaymentDto> update(@PathVariable("paymentId") final Integer paymentId,
                                             @RequestBody
                                             final PaymentDto paymentDto) {
        log.info("*** PaymentDto, resource; update payment with paymentId *");
        return ResponseEntity.ok(paymentService.update(paymentId, paymentDto));
    }

    @DeleteMapping("/{paymentId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Boolean> deleteById(@PathVariable("paymentId") final Integer paymentId) {
        log.info("*** Boolean, resource; delete payment by id *");
        paymentService.deleteById(paymentId);
        return ResponseEntity.ok(true);
    }
}

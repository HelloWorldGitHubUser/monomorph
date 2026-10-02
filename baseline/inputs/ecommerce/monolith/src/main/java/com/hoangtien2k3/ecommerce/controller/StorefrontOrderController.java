package com.hoangtien2k3.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/storefront/orders")
public class StorefrontOrderController {

    @GetMapping("/completed")
    public ResponseEntity<?> checkCompletedOrder(@RequestParam("productId") String productId) {
        return ResponseEntity.ok(Map.of("isPresent", true));
    }
}

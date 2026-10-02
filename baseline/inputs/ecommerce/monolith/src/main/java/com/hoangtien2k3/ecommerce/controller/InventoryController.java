package com.hoangtien2k3.ecommerce.controller;

import com.hoangtien2k3.ecommerce.dto.InventoryResponse;
import com.hoangtien2k3.ecommerce.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<InventoryResponse> isInStock(@RequestParam List<String> productName) {
        log.info("Received inventory check request for productName: {}", productName);
        return inventoryService.isInStock(productName);
    }
}

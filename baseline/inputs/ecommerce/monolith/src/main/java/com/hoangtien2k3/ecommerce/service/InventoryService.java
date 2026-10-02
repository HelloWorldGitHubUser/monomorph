package com.hoangtien2k3.ecommerce.service;

import com.hoangtien2k3.ecommerce.dto.InventoryResponse;
import com.hoangtien2k3.ecommerce.repository.inventory.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> productName) {
        log.info("Checking Inventory");
        return inventoryRepository.findByProductNameIn(productName).stream()
                .map(inventory -> InventoryResponse.builder()
                        .productName(inventory.getProductName())
                        .isInStock(inventory.getQuantity() > 0)
                        .build())
                .toList();
    }
}

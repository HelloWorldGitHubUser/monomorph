package com.hoangtien2k3.ecommerce.listener;

import com.hoangtien2k3.ecommerce.event.ProductDataChangeEvent;
import com.hoangtien2k3.ecommerce.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductSyncEventListener {

    private final ProductSearchService productSearchService;

    @Async
    @EventListener
    public void onProductChange(ProductDataChangeEvent event) {
        log.info("Product data change event: id={}, op={}", event.getProductId(), event.getOperation());
        switch (event.getOperation()) {
            case DELETE -> productSearchService.deleteProduct(event.getProductId());
            case CREATE, UPDATE -> productSearchService.syncProduct(event.getProductId());
        }
    }
}

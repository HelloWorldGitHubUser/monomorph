package com.hoangtien2k3.ecommerce.dto;

public record ProductVm(Integer productId,
                        String productTitle,
                        String imageUrl,
                        String sku,
                        Double priceUnit,
                        Integer quantity) {
}

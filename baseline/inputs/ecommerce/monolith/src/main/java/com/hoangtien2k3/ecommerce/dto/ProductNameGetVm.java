package com.hoangtien2k3.ecommerce.dto;

import com.hoangtien2k3.ecommerce.model.search.ProductDocument;

public record ProductNameGetVm(String name) {
    public static ProductNameGetVm fromModel(ProductDocument product) {
        return new ProductNameGetVm(
                product.getName()
        );
    }
}

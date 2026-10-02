package com.hoangtien2k3.ecommerce.dto;

import com.hoangtien2k3.ecommerce.model.tax.TaxClass;

public record TaxClassVm(Long id, String name) {

    public static TaxClassVm fromModel(TaxClass taxClass) {
        return new TaxClassVm(taxClass.getId(), taxClass.getName());
    }
}

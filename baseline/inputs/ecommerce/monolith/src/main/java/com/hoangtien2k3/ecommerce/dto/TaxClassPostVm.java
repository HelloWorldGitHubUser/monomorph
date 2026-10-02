package com.hoangtien2k3.ecommerce.dto;

import com.hoangtien2k3.ecommerce.model.tax.TaxClass;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaxClassPostVm(@NotBlank String id, @Size(min = 1, max = 450) String name) {

    public TaxClass toModel() {
        TaxClass taxClass = new TaxClass();
        taxClass.setName(name);
        return taxClass;
    }

}

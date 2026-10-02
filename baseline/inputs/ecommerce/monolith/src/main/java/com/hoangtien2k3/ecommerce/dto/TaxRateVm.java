package com.hoangtien2k3.ecommerce.dto;

import com.hoangtien2k3.ecommerce.model.tax.TaxRate;

public record TaxRateVm(Long id, Double rate, String zipCode, Long taxClassId, Long stateOrProvinceId, Long countryId) {

    public static TaxRateVm fromModel(TaxRate taxRate) {
        return new TaxRateVm(taxRate.getId(), taxRate.getRate(), taxRate.getZipCode(),
            taxRate.getTaxClass().getId(), taxRate.getStateOrProvinceId(), taxRate.getCountryId());
    }
}

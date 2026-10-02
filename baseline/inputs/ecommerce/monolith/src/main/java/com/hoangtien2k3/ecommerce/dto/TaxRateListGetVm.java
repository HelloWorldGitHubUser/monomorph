package com.hoangtien2k3.ecommerce.dto;

import java.util.List;

public record TaxRateListGetVm(
    List<TaxRateGetDetailVm> taxRateGetDetailContent,
    int pageNo,
    int pageSize,
    int totalElements,
    int totalPages,
    boolean isLast
) {

}

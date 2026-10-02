package com.hoangtien2k3.ecommerce.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record PromotionListVm(
        List<PromotionDetailVm> promotionDetailVmList,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages
) {
}

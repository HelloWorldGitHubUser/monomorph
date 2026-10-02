package com.hoangtien2k3.ecommerce.repository.promotion;

import com.hoangtien2k3.ecommerce.model.promotion.PromotionUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionUsageRepository extends JpaRepository<PromotionUsage, Long> {
    boolean existsByPromotionId(Long promotionId);
}

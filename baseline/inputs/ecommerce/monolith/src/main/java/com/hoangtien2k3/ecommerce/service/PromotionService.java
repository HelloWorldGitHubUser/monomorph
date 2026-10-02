package com.hoangtien2k3.ecommerce.service;

import com.hoangtien2k3.ecommerce.constants.MessageCode;
import com.hoangtien2k3.ecommerce.dto.BrandVm;
import com.hoangtien2k3.ecommerce.dto.CategoryDto;
import com.hoangtien2k3.ecommerce.dto.CategoryGetVm;
import com.hoangtien2k3.ecommerce.dto.ProductDto;
import com.hoangtien2k3.ecommerce.dto.ProductVm;
import com.hoangtien2k3.ecommerce.dto.PromotionDetailVm;
import com.hoangtien2k3.ecommerce.dto.PromotionListVm;
import com.hoangtien2k3.ecommerce.dto.PromotionPostVm;
import com.hoangtien2k3.ecommerce.dto.PromotionPutVm;
import com.hoangtien2k3.ecommerce.exception.BadRequestException;
import com.hoangtien2k3.ecommerce.exception.DuplicatedException;
import com.hoangtien2k3.ecommerce.exception.NotFoundException;
import com.hoangtien2k3.ecommerce.model.promotion.Promotion;
import com.hoangtien2k3.ecommerce.model.promotion.PromotionApply;
import com.hoangtien2k3.ecommerce.repository.promotion.PromotionRepository;
import com.hoangtien2k3.ecommerce.repository.promotion.PromotionUsageRepository;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "postgresTransactionManager")
@Slf4j
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionUsageRepository promotionUsageRepository;
    private final ProductService productService;
    private final CategoryService categoryService;

    public PromotionDetailVm createPromotion(PromotionPostVm promotionPostVm) {
        validateIfPromotionExistedSlug(promotionPostVm.getSlug());
        validateIfPromotionEndDateIsBeforeStartDate(
            promotionPostVm.getStartDate().toInstant(),
            promotionPostVm.getEndDate().toInstant());

        Promotion promotion = Promotion.builder()
                .name(promotionPostVm.getName())
                .slug(promotionPostVm.getSlug())
                .description(promotionPostVm.getDescription())
                .couponCode(promotionPostVm.getCouponCode())
                .applyTo(promotionPostVm.getApplyTo())
                .usageType(promotionPostVm.getUsageType())
                .usageLimit(promotionPostVm.getUsageLimit())
                .discountType(promotionPostVm.getDiscountType())
                .discountPercentage(promotionPostVm.getDiscountPercentage())
                .discountAmount(promotionPostVm.getDiscountAmount())
                .isActive(promotionPostVm.isActive())
                .startDate(promotionPostVm.getStartDate().toInstant())
                .endDate(promotionPostVm.getEndDate().toInstant())
                .minimumOrderPurchaseAmount(promotionPostVm.getMinimumOrderPurchaseAmount())
                .build();

        List<PromotionApply> promotionApplies =
                PromotionPostVm.createPromotionApplies(promotionPostVm, promotion);
        promotion.setPromotionApplies(promotionApplies);

        return PromotionDetailVm.fromModel(promotionRepository.save(promotion));
    }

    public PromotionDetailVm updatePromotion(PromotionPutVm promotionPutVm) {
        Optional<Promotion> promotionOp = promotionRepository.findById(promotionPutVm.getId());

        if (promotionOp.isEmpty()) {
            throw new NotFoundException(MessageCode.PROMOTION_NOT_FOUND_ERROR_MESSAGE, promotionPutVm.getId());
        }

        Promotion promotion = promotionOp.get();

        promotion.setApplyTo(promotionPutVm.getApplyTo());
        promotion.setName(promotionPutVm.getName());
        promotion.setDescription(promotionPutVm.getDescription());
        promotion.setCouponCode(promotionPutVm.getCouponCode());
        promotion.setUsageType(promotionPutVm.getUsageType());
        promotion.setUsageLimit(promotionPutVm.getUsageLimit());
        promotion.setSlug(promotionPutVm.getSlug());
        promotion.setDiscountType(promotionPutVm.getDiscountType());
        promotion.setDiscountPercentage(promotionPutVm.getDiscountPercentage());
        promotion.setDiscountAmount(promotionPutVm.getDiscountAmount());
        promotion.setIsActive(promotionPutVm.isActive());
        promotion.setStartDate(promotionPutVm.getStartDate().toInstant());
        promotion.setEndDate(promotionPutVm.getEndDate().toInstant());
        promotion.setMinimumOrderPurchaseAmount(promotionPutVm.getMinimumOrderPurchaseAmount());

        promotion.setPromotionApplies(PromotionPutVm.createPromotionApplies(promotionPutVm, promotion));

        promotion = promotionRepository.save(promotion);
        return PromotionDetailVm.fromModel(promotion);
    }

    @Transactional(transactionManager = "postgresTransactionManager", readOnly = true,
            propagation = Propagation.NOT_SUPPORTED)
    public PromotionListVm getPromotions(
        int pageNo,
        int pageSize,
        String promotionName,
        String couponCode,
        Instant startDate,
        Instant endDate
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Promotion> promotionPage = promotionRepository.findPromotions(
            promotionName.trim(),
            couponCode.trim(),
            startDate,
            endDate,
            pageable
        );

        List<PromotionDetailVm> promotionDetailVmList = promotionPage
                .getContent()
                .stream()
                .map(this::toPromotionDetail)
                .toList();

        return PromotionListVm.builder()
                .promotionDetailVmList(promotionDetailVmList)
                .pageNo(promotionPage.getNumber())
                .pageSize(promotionPage.getSize())
                .totalElements(promotionPage.getTotalElements())
                .totalPages(promotionPage.getTotalPages())
                .build();
    }

    private PromotionDetailVm toPromotionDetail(Promotion promotion) {
        List<BrandVm> brandVms = null;
        List<CategoryGetVm> categoryGetVms = null;
        List<ProductVm> productVms = null;
        List<PromotionApply> promotionApplies = promotion.getPromotionApplies();
        try {
            switch (promotion.getApplyTo()) {
                case CATEGORY ->
                    categoryGetVms = promotionApplies.stream()
                        .map(PromotionApply::getCategoryId)
                        .map(id -> {
                            CategoryDto dto = categoryService.findById(id.intValue());
                            return new CategoryGetVm(dto.getCategoryId(), dto.getCategoryTitle(), dto.getImageUrl());
                        })
                        .toList();
                case BRAND ->
                    brandVms = Collections.emptyList();
                case PRODUCT ->
                    productVms = promotionApplies.stream()
                        .map(PromotionApply::getProductId)
                        .map(id -> {
                            ProductDto dto = productService.findById(id.intValue());
                            return new ProductVm(dto.getProductId(), dto.getProductTitle(),
                                    dto.getImageUrl(), dto.getSku(), dto.getPriceUnit(), dto.getQuantity());
                        })
                        .toList();
                default -> {
                    break;
                }
            }
        } catch (Exception ex) {
            log.warn("Failed to enrich promotion {} with product data: {}", promotion.getId(), ex.getMessage());
        }
        return PromotionDetailVm.fromModel(promotion, brandVms, categoryGetVms, productVms);
    }

    private void validateIfPromotionExistedSlug(String slug) {
        if (promotionRepository.findBySlugAndIsActiveTrue(slug).isPresent()) {
            throw new DuplicatedException(String.format(MessageCode.SLUG_ALREADY_EXITED, slug));
        }
    }

    private void validateIfPromotionEndDateIsBeforeStartDate(Instant startDate, Instant endDate) {
        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new BadRequestException(String.format(MessageCode.DATE_RANGE_INVALID));
        }
    }

    public void deletePromotion(Long id) {
        if (promotionUsageRepository.existsByPromotionId(id)) {
            throw new BadRequestException(MessageCode.PROMOTION_IN_USE, id);
        }
        promotionRepository.deleteById(id);
    }

    @Transactional(transactionManager = "postgresTransactionManager", readOnly = true,
            propagation = Propagation.NOT_SUPPORTED)
    public PromotionDetailVm getPromotion(Long promotionId) {
        Optional<Promotion> promotionOp = promotionRepository.findById(promotionId);
        if (promotionOp.isEmpty()) {
            throw new NotFoundException(MessageCode.PROMOTION_NOT_FOUND_ERROR_MESSAGE, promotionId);
        }
        return toPromotionDetail(promotionOp.get());
    }
}

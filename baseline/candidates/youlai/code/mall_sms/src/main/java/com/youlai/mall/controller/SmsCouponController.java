package com.youlai.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.result.PageResult;
import com.youlai.mall.result.Result;
import com.youlai.mall.model.sms.form.CouponForm;
import com.youlai.mall.model.sms.query.CouponPageQuery;
import com.youlai.mall.model.sms.vo.CouponPageVO;
import com.youlai.mall.service.sms.SmsCouponService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "Admin-优惠券管理")
@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class SmsCouponController {

    private final SmsCouponService couponService;

    @Operation(summary= "优惠券分页列表")
    @GetMapping("/page")
    public PageResult getCouponPage(CouponPageQuery queryParams) {
        Page<CouponPageVO> result = couponService.getCouponPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary= "优惠券表单数据")
    @GetMapping("/{couponId}/form_data")
    public Result<CouponForm> getCouponFormData(@Parameter(description = "优惠券ID") @PathVariable Long couponId) {
        CouponForm couponForm = couponService.getCouponFormData(couponId);
        return Result.success(couponForm);
    }

    @Operation(summary ="新增优惠券")
    @PostMapping
    public Result saveCoupon(@RequestBody @Valid CouponForm couponForm) {
        boolean result = couponService.saveCoupon(couponForm);
        return Result.judge(result);
    }

    @Operation(summary ="修改优惠券")
    @PutMapping("/{couponId}")
    public Result updateCoupon(
            @PathVariable Long couponId,
            @RequestBody @Valid CouponForm couponForm
    ) {
        boolean result = couponService.updateCoupon(couponId,couponForm);
        return Result.judge(result);
    }

    @Operation(summary= "删除优惠券")
    @DeleteMapping("/{ids}")
    public Result deleteCoupons(@Parameter(description = "优惠券ID，多个以英文逗号(,)分割") @PathVariable String ids) {
        boolean result = couponService.deleteCoupons(ids);
        return Result.judge(result);
    }
}





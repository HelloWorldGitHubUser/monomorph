package com.youlai.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.mall.result.Result;
import com.youlai.mall.model.pms.form.PmsCategoryAttributeForm;
import com.youlai.mall.model.pms.entity.PmsCategoryAttribute;
import com.youlai.mall.service.pms.AttributeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;  
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Admin-商品属性控制器
 *
 * @author haoxr
 * @since 2022/1/1
 */
@Tag(name = "Admin-商品属性")
@RestController
@RequestMapping("/api/v1/attributes")
@Slf4j
@AllArgsConstructor
public class PmsAttributeController {

    private AttributeService attributeService;

    @Operation(summary= "属性列表")
    @GetMapping
    public Result getAttributeList(
            @Parameter(description = "商品分类ID") Long categoryId,
            @Parameter(description = "类型（1：规格；2：属性）") Integer type
    ) {
        List<PmsCategoryAttribute> list = attributeService.list(new LambdaQueryWrapper<PmsCategoryAttribute>()
                .eq(categoryId != null, PmsCategoryAttribute::getCategoryId, categoryId)
                .eq(type != null, PmsCategoryAttribute::getType, type)
        );
        return Result.success(list);
    }

    @Operation(summary= "批量新增/修改")
    @PostMapping("/batch")
    public Result saveBatch(@RequestBody PmsCategoryAttributeForm pmsCategoryAttributeForm) {
        boolean result = attributeService.saveBatch(pmsCategoryAttributeForm);
        return Result.judge(result);
    }
}





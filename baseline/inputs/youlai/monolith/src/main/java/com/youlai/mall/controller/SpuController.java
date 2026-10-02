package com.youlai.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.mall.result.PageResult;
import com.youlai.mall.result.Result;
import com.youlai.mall.model.pms.query.SpuPageQuery;
import com.youlai.mall.model.pms.vo.SeckillingSpuVO;
import com.youlai.mall.model.pms.vo.SpuDetailVO;
import com.youlai.mall.model.pms.vo.SpuPageVO;
import com.youlai.mall.service.pms.SpuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name  = "App-商品接口")
@RestController
@RequestMapping("/app-api/v1/spu")
@RequiredArgsConstructor
public class SpuController {

    private final SpuService spuService;

    @Operation(summary = "商品分页列表")
    @GetMapping("/pages")
    public PageResult<SpuPageVO> listPagedSpuForApp(SpuPageQuery queryParams) {
        IPage<SpuPageVO> result = spuService.listPagedSpuForApp(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "获取商品详情")
    @GetMapping("/{spuId}")
    public Result<SpuDetailVO> getSpuDetail(
            @Parameter(description ="商品ID") @PathVariable Long spuId
    ) {
        SpuDetailVO spuDetailVO = spuService.getSpuDetailForApp(spuId);
        return Result.success(spuDetailVO);
    }

    @Operation(summary = "获取秒杀商品列表")
    @GetMapping("/seckilling")
    public Result<List<SeckillingSpuVO>> listSeckillingSpu() {
        List<SeckillingSpuVO> list = spuService.listSeckillingSpu();
        return Result.success(list);
    }

}





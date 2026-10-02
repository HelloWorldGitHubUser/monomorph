package com.youlai.mall.controller;

import com.youlai.mall.result.Result;
import com.youlai.mall.model.sms.vo.BannerVO;
import com.youlai.mall.service.sms.SmsAdvertService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "App-营销广告")
@RestController
@RequestMapping("/app-api/v1/adverts")
@Slf4j
@AllArgsConstructor
public class AdvertController {

    private SmsAdvertService smsAdvertService;
    @Operation(summary= "APP首页广告横幅列表")
    @GetMapping("/banners")
    public Result<List<BannerVO>> getBannerList() {
        List<BannerVO> list = smsAdvertService.getBannerList();
        return Result.success(list);
    }
}





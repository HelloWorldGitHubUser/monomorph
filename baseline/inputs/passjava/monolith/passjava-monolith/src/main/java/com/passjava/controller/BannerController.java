package com.passjava.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.passjava.entity.BannerEntity;
import com.passjava.service.BannerService;
import com.passjava.utils.PageUtils;
import com.passjava.utils.R;

/**
 * 横幅广告控制器
 */
@RestController
@RequestMapping("content/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = bannerService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        BannerEntity banner = bannerService.getById(id);
        return R.ok().put("banner", banner);
    }

    @RequestMapping("/save")
    public R save(@RequestBody BannerEntity banner) {
        bannerService.save(banner);
        return R.ok();
    }

    @RequestMapping("/update")
    public R update(@RequestBody BannerEntity banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        bannerService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}






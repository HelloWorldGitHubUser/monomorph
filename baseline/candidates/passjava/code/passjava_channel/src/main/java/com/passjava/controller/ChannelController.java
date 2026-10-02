package com.passjava.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.passjava.entity.ChannelEntity;
import com.passjava.service.ChannelService;
import com.passjava.utils.PageUtils;
import com.passjava.utils.R;

/**
 * 渠道控制器
 */
@RestController
@RequestMapping("channel/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = channelService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        ChannelEntity channel = channelService.getById(id);
        return R.ok().put("channel", channel);
    }

    @RequestMapping("/save")
    public R save(@RequestBody ChannelEntity channel) {
        channelService.save(channel);
        return R.ok();
    }

    @RequestMapping("/update")
    public R update(@RequestBody ChannelEntity channel) {
        channelService.updateById(channel);
        return R.ok();
    }

    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        channelService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}






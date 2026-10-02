package com.passjava.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.passjava.utils.PageUtils;
import com.passjava.utils.Query;
import com.passjava.dao.ChannelDao;
import com.passjava.entity.ChannelEntity;
import com.passjava.service.ChannelService;

@Service("channelService")
public class ChannelServiceImpl extends ServiceImpl<ChannelDao, ChannelEntity> implements ChannelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ChannelEntity> page = this.page(
                new Query<ChannelEntity>().getPage(params),
                new QueryWrapper<ChannelEntity>()
        );
        return new PageUtils(page);
    }
}






package com.passjava.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.passjava.utils.PageUtils;
import com.passjava.utils.Query;
import com.passjava.dao.MemberDao;
import com.passjava.entity.MemberEntity;
import com.passjava.service.MemberService;

@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public String sendCoupon(int num) throws Exception {
        if (num <= 0) {
            throw new Exception("发放的优惠券数量必须大于 0");
        }
        return "success";
    }

    @Override
    public MemberEntity getMemberByUserId(String userId) {
        return baseMapper.getMemberByUserId(userId);
    }
}






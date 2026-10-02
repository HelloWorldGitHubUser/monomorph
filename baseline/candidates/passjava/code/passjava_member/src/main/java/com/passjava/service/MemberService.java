package com.passjava.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.passjava.utils.PageUtils;
import com.passjava.entity.MemberEntity;

import java.util.Map;

/**
 * 会员-会员表
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    String sendCoupon(int num) throws Exception;

    MemberEntity getMemberByUserId(String userId);
}






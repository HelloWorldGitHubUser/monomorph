package com.passjava.dao;

import com.passjava.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员-会员表
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
    MemberEntity getMemberByUserId(String userId);
}






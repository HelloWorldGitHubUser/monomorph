package com.youlai.mall.service.auth;

import cn.hutool.core.lang.Assert;
import com.youlai.mall.model.auth.MemberDetails;
import com.youlai.mall.enums.StatusEnum;
import com.youlai.mall.result.ResultCode;
import com.youlai.mall.service.ums.UmsMemberService;
import com.youlai.mall.model.ums.dto.MemberAuthDTO;
import com.youlai.mall.model.ums.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 商城会员用户认证服务
 *
 * @author haoxr
 * @since 3.0.0
 */
@Service
@RequiredArgsConstructor
public class MemberDetailsService {

    private final UmsMemberService memberService;


    /**
     * 手机号码认证方式
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    public UserDetails loadUserByMobile(String mobile) {
        MemberAuthDTO memberAuthInfo = memberService.loadUserByMobile(mobile);

        if (memberAuthInfo == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        }
        MemberDetails userDetails = new MemberDetails(memberAuthInfo);
        if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param openid  微信公众平台唯一身份标识
     * @return {@link MemberDetails}
     */
    public UserDetails loadUserByOpenid(String openid) {
        // 根据 openid 获取微信用户认证信息
        MemberAuthDTO memberAuthInfo = memberService.loadUserByOpenId(openid);

        // 会员不存在，注册成为新会员
        if (memberAuthInfo == null) {
            MemberRegisterDto memberRegisterInfo = new MemberRegisterDto();
            memberRegisterInfo.setOpenid(openid);
            memberRegisterInfo.setNickName("微信用户");
            // 注册会员
            Long memberId = memberService.registerMember(memberRegisterInfo);
            // 注册成功将会员信息赋值给会员认证信息
            if (memberId != null) {
                memberAuthInfo = new MemberAuthDTO(memberId, openid, StatusEnum.ENABLE.getValue());
            }
        }

        // 用户不存在
        if (memberAuthInfo == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        }

        UserDetails userDetails = new MemberDetails(memberAuthInfo);
        if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }

}

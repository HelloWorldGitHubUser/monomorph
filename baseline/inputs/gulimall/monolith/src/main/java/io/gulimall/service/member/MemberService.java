package io.gulimall.service.member;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gulimall.utils.PageUtils;
import io.gulimall.entity.member.MemberEntity;
import io.gulimall.vo.member.MemberLoginVo;
import io.gulimall.vo.member.MemberRegisterVo;
import io.gulimall.vo.SocialUser;

import java.util.Map;

/**
 * 会员
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:01:00
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberRegisterVo registerVo);

    MemberEntity login(MemberLoginVo loginVo);

    MemberEntity login(SocialUser socialUser);
}


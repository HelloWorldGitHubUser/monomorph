package io.gulimall.controller.auth;

import io.gulimall.vo.auth.UserLoginVo;
import io.gulimall.constant.AuthServerConstant;
import io.gulimall.exception.BizCodeEnum;
import io.gulimall.utils.R;
import io.gulimall.vo.MemberResponseVo;
import io.gulimall.entity.member.MemberEntity;
import io.gulimall.service.member.MemberService;
import io.gulimall.vo.member.MemberLoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 认证 REST API 接口 - 用于 Postman 等工具测试
 */
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 登录接口（REST API 版本）
     * POST /api/auth/login
     * Body: {"loginacct": "用户名/手机号/邮箱", "password": "密码"}
     */
    @PostMapping("/login")
    public R login(@RequestBody UserLoginVo vo, HttpSession session) {
        MemberLoginVo memberLoginVo = new MemberLoginVo();
        memberLoginVo.setLoginAccount(vo.getLoginacct());
        memberLoginVo.setPassword(vo.getPassword());
        MemberEntity entity = memberService.login(memberLoginVo);
        if (entity != null) {
            MemberResponseVo memberResponseVo = new MemberResponseVo();
            BeanUtils.copyProperties(entity, memberResponseVo);
            session.setAttribute(AuthServerConstant.LOGIN_USER, memberResponseVo);
            String sessionId = session.getId();
            return R.ok()
                    .put("memberEntity", memberResponseVo)
                    .put("sessionId", sessionId)
                    .put("cookieName", "JSESSIONID")
                    .put("cookieValue", sessionId)
                    .put("cookieHeader", "Cookie: JSESSIONID=" + sessionId)
                    .put("debug", "请在后续请求的 Headers 中添加: Cookie: JSESSIONID=" + sessionId);
        } else {
            return R.error(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getCode(), BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMsg());
        }
    }

    /**
     * 获取当前登录用户信息
     * GET /api/auth/user
     * 方式1：使用 Cookie（推荐）：自动携带 JSESSIONID Cookie
     * 方式2：使用 Header：在 Headers 中添加 X-Session-Id: sessionId
     */
    @GetMapping("/user")
    public R getCurrentUser(HttpSession session, @RequestHeader(value = "X-Session-Id", required = false) String headerSessionId) {
        // 如果通过 Header 传递了 sessionId，尝试从 Redis 中获取 Session
        HttpSession targetSession = session;
        if (headerSessionId != null && !headerSessionId.equals(session.getId())) {
            // 注意：这里只是示例，实际 Spring Session 会自动处理
            // 如果 Header 中的 sessionId 与当前 session 不同，说明 Cookie 没有正确传递
        }
        
        // 调试信息
        String sessionId = session.getId();
        Object loginUser = session.getAttribute(AuthServerConstant.LOGIN_USER);
        
        MemberResponseVo memberResponseVo = (MemberResponseVo) loginUser;
        if (memberResponseVo != null) {
            return R.ok()
                    .put("memberEntity", memberResponseVo)
                    .put("sessionId", sessionId)
                    .put("debug", "Session中存在用户信息");
        } else {
            // 返回调试信息
            return R.error(401, "未登录")
                    .put("sessionId", sessionId)
                    .put("sessionIsNew", session.isNew())
                    .put("headerSessionId", headerSessionId)
                    .put("debug", "Session中不存在用户信息。请确认：1) Cookie是否正确发送 2) 或使用 Header: X-Session-Id");
        }
    }

    /**
     * 登出接口
     * POST /api/auth/logout
     */
    @PostMapping("/logout")
    public R logout(HttpSession session) {
        session.invalidate();
        return R.ok().put("msg", "登出成功");
    }

    /**
     * 生成测试验证码（仅用于测试，实际应该通过短信发送）
     * GET /api/auth/test/code?phone=手机号
     * 返回的验证码会存储在 Redis 中，key 为 sms:code:手机号
     */
    @GetMapping("/test/code")
    public R generateTestCode(@RequestParam String phone) {
        String code = String.valueOf((int)((Math.random() * 9 + 1) * 100000)); // 6位随机数
        String redisKey = AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone;
        redisTemplate.opsForValue().set(redisKey, code + "_" + System.currentTimeMillis(), 5, java.util.concurrent.TimeUnit.MINUTES);
        return R.ok().put("code", code).put("phone", phone).put("msg", "测试验证码（仅用于开发测试）");
    }
}


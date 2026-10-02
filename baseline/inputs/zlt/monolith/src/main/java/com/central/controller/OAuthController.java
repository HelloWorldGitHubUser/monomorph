package com.central.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.central.entity.SysUser;
import com.central.model.Result;
import com.central.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * OAuth 认证控制器
 * 对应微服务 zlt-uaa 的 OAuth2 端点（简化实现）
 * 模拟网关路由前缀 /api-uaa
 * @author zlt
 */
@Slf4j
@RestController
@Tag(name = "OAuth认证")
@RequestMapping("/api-uaa")
public class OAuthController {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 验证码缓存前缀
    private static final String CODE_PREFIX = "captcha:";
    // Token 缓存前缀
    public static final String TOKEN_PREFIX = "token:";

    /**
     * 登录获取 Token
     */
    @PostMapping("/oauth/token")
    @Operation(summary = "登录获取Token")
    public Map<String, Object> login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) String validCode,
            @RequestParam(required = false) String deviceId,
            @RequestParam(required = false, defaultValue = "password") String grant_type,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 验证码校验 (如果启用)
            if (StrUtil.isNotBlank(deviceId) && StrUtil.isNotBlank(validCode)) {
                String cacheCode = stringRedisTemplate.opsForValue().get(CODE_PREFIX + deviceId);
                if (cacheCode == null || !cacheCode.equalsIgnoreCase(validCode)) {
                    result.put("resp_code", 1);
                    result.put("resp_msg", "验证码错误或已过期");
                    return result;
                }
                // 验证成功后删除验证码
                stringRedisTemplate.delete(CODE_PREFIX + deviceId);
            }

            // 查询用户
            SysUser user = sysUserService.findByUsername(username);
            if (user == null) {
                result.put("resp_code", 1);
                result.put("resp_msg", "用户不存在");
                return result;
            }

            // 验证密码
            if (!passwordEncoder.matches(password, user.getPassword())) {
                result.put("resp_code", 1);
                result.put("resp_msg", "密码错误");
                return result;
            }

            // 检查用户状态
            if (user.getEnabled() != null && !user.getEnabled()) {
                result.put("resp_code", 1);
                result.put("resp_msg", "用户已禁用");
                return result;
            }

            // 生成 Token
            String accessToken = IdUtil.fastSimpleUUID();
            String refreshToken = IdUtil.fastSimpleUUID();

            // Token 数据
            Map<String, Object> tokenData = new HashMap<>();
            tokenData.put("access_token", accessToken);
            tokenData.put("refresh_token", refreshToken);
            tokenData.put("token_type", "Bearer");
            tokenData.put("expires_in", 7200);
            tokenData.put("user_id", user.getId());
            tokenData.put("username", user.getUsername());

            // 缓存 Token (2小时过期)
            stringRedisTemplate.opsForValue().set(
                    TOKEN_PREFIX + accessToken,
                    user.getId().toString(),
                    2, TimeUnit.HOURS
            );

            result.put("resp_code", 0);
            result.put("resp_msg", "登录成功");
            result.put("datas", tokenData);

            log.info("用户登录成功: {}", username);

        } catch (Exception e) {
            log.error("登录异常", e);
            result.put("resp_code", 1);
            result.put("resp_msg", "登录异常: " + e.getMessage());
        }

        return result;
    }

    /**
     * 登出
     */
    @PostMapping("/oauth/remove/token")
    @Operation(summary = "登出")
    public Result<String> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        if (StrUtil.isNotBlank(authorization)) {
            String token = authorization.replace("Bearer ", "").replace("bearer ", "");
            stringRedisTemplate.delete(TOKEN_PREFIX + token);
        }
        return Result.succeed("登出成功");
    }
}



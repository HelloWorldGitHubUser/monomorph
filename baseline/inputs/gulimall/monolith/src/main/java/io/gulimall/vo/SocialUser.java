package io.gulimall.vo;

import lombok.Data;

/**
 * 社交登录用户信息 VO - 统一版本（合并自 auth.vo 和 member.vo）
 * 用以封装社交登录认证后换回的令牌等信息
 */
@Data
public class SocialUser {
    /**
     * 令牌
     */
    private String access_token;

    /**
     * 提醒时间（使用 String 保持兼容性）
     */
    private String remind_in;

    /**
     * 令牌过期时间
     */
    private Long expires_in;

    /**
     * 该社交用户的唯一标识
     */
    private String uid;

    private String isRealName;
}









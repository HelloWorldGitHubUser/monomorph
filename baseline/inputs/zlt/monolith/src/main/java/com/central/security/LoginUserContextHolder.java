package com.central.security;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 登录用户holder
 *
 * @author zlt
 * @date 2022/6/26
 */
public class LoginUserContextHolder {
    private static final ThreadLocal<LoginAppUser> CONTEXT = new TransmittableThreadLocal<>();

    public static void setUser(LoginAppUser user) {
        CONTEXT.set(user);
    }

    public static LoginAppUser getUser() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}






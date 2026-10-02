package com.central.security;

import cn.hutool.core.util.StrUtil;
import com.central.entity.SysUser;
import com.central.service.ISysUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Token 认证过滤器
 * 从请求头提取 Token，验证后设置 LoginUserContextHolder 和 SecurityContext
 *
 * @author zlt
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "token:";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final StringRedisTemplate stringRedisTemplate;
    private final ISysUserService sysUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 提取 Token
            String token = extractToken(request);
            
            if (StrUtil.isNotBlank(token)) {
                // 从 Redis 获取用户 ID
                String userId = stringRedisTemplate.opsForValue().get(TOKEN_PREFIX + token);
                
                if (StrUtil.isNotBlank(userId)) {
                    // 查询用户信息（含权限）
                    SysUser sysUser = sysUserService.getById(Long.valueOf(userId));
                    
                    if (sysUser != null) {
                        // 设置用户权限
                        sysUserService.setUserPermission(sysUser);
                        
                        // 转换为 LoginAppUser
                        LoginAppUser loginAppUser = convertToLoginAppUser(sysUser);
                        
                        // 设置到 LoginUserContextHolder
                        LoginUserContextHolder.setUser(loginAppUser);
                        
                        // 设置到 Spring Security Context
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(loginAppUser, null, loginAppUser.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        
                        log.debug("Token 认证成功, 用户: {}", sysUser.getUsername());
                    }
                }
            }
            
            filterChain.doFilter(request, response);
        } finally {
            // 清理 ThreadLocal，防止内存泄漏
            LoginUserContextHolder.clear();
        }
    }

    /**
     * 从请求头提取 Token
     */
    private String extractToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        
        if (StrUtil.isNotBlank(authorization) && authorization.startsWith(BEARER_PREFIX)) {
            return authorization.substring(BEARER_PREFIX.length());
        }
        
        // 也支持从请求参数获取 (兼容某些场景)
        String tokenParam = request.getParameter("access_token");
        if (StrUtil.isNotBlank(tokenParam)) {
            return tokenParam;
        }
        
        return null;
    }

    /**
     * 将 SysUser 转换为 LoginAppUser
     */
    private LoginAppUser convertToLoginAppUser(SysUser sysUser) {
        // 构建权限列表
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        
        // 添加角色权限
        if (sysUser.getRoles() != null) {
            sysUser.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
            });
        }
        
        // 获取权限集合
        Set<String> permissions = sysUser.getPermissions() != null 
                ? sysUser.getPermissions() 
                : new HashSet<>();
        
        return new LoginAppUser(
                sysUser.getId(),
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.getMobile(),
                permissions,
                sysUser.getEnabled() != null ? sysUser.getEnabled() : true,
                true,
                true,
                true,
                authorities
        );
    }
}






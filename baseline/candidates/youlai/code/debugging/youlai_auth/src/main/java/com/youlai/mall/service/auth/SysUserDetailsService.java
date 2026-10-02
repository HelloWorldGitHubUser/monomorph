package com.youlai.mall.service.auth;
import com.youlai.mall.enums.StatusEnum;
import com.youlai.mall.model.auth.LoginUserInfo;
import com.youlai.mall.model.auth.SysUserDetails;
import com.youlai.mall.monomorph.dto.generated.client.UserAuthInfo;
import com.youlai.mall.monomorph.id.generated.client.SysUserService;
import cn.hutool.core.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
/**
 * 系统用户信息加载实现类
 *
 * @author haoxr
 * @since 3.0.0
 */
@Service("sysUserDetailsService")
@RequiredArgsConstructor
@Slf4j
public class SysUserDetailsService implements UserDetailsService {
    private final SysUserService sysUserService;

    /**
     * 根据用户名获取用户信息(用户名、密码和角色权限)
     * <p>
     * 用户名、密码用于后续认证，认证成功之后将权限授予用户
     *
     * @param username
     * 		用户名
     * @return {@link SysUserDetails}
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("=== 开始加载用户信息: {} ===", username);
        UserAuthInfo userAuthInfo = sysUserService.getUserAuthInfo(username);
        log.info("从数据库查询到的用户信息: {}", userAuthInfo);
        Assert.isTrue(userAuthInfo != null, "用户不存在");
        if (userAuthInfo.getPassword() != null) {
            log.info("数据库中的密码哈希: {}", userAuthInfo.getPassword());
        } else {
            log.error("警告: 数据库中密码为空!");
        }
        if (!StatusEnum.ENABLE.getValue().equals(userAuthInfo.getStatus())) {
            throw new DisabledException("该账户已被禁用!");
        }
        log.info("创建 SysUserDetails 对象");
        return new SysUserDetails(userAuthInfo);
    }

    public LoginUserInfo getLoginUserInfo() {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setId(123L);
        return loginUserInfo;
    }
}
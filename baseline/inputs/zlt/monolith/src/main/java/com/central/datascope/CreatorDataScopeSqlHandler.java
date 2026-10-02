package com.central.datascope;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.central.entity.SysRole;
import com.central.enums.DataScope;
import com.central.security.LoginAppUser;
import com.central.service.ISysUserService;
import com.central.security.LoginUserContextHolder;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 个人权限的处理器
 * 根据用户角色的数据权限设置，自动添加 WHERE 条件
 *
 * @author jarvis create by 2023/1/10
 */
@Component
public class CreatorDataScopeSqlHandler implements SqlHandler {

    @Lazy
    @Resource
    private ISysUserService sysUserService;

    @Resource
    private DataScopeProperties dataScopeProperties;

    /**
     * 返回需要增加的where条件，返回空字符的话则代表不需要权限控制
     *
     * @return where条件
     * 如果角色是全部权限的话则不进行控制，如果是个人权限的话则自动加入 creator_id = user_id
     */
    @Override
    public String handleScopeSql() {
        LoginAppUser user = LoginUserContextHolder.getUser();
        if (user == null) {
            // 未登录用户不进行权限控制
            return DO_NOTHING;
        }
        
        List<SysRole> roleList = sysUserService.findRolesByUserId(user.getId());
        
        // 判断是否需要权限控制
        // 1. 创建人ID字段名为空 - 不控制
        // 2. 角色列表为空 - 不控制（默认可以看全部）
        // 3. 任意角色有 ALL 权限或未设置数据权限 - 不控制
        if (StrUtil.isBlank(dataScopeProperties.getCreatorIdColumnName())
                || CollUtil.isEmpty(roleList)
                || roleList.stream().anyMatch(item -> 
                        Objects.isNull(item.getDataScope()) 
                        || DataScope.ALL.equals(item.getDataScope()))) {
            return DO_NOTHING;
        }
        
        // 所有角色都是 CREATOR 权限，添加 creator_id = user_id 条件
        return String.format("%s = '%s'", 
                dataScopeProperties.getCreatorIdColumnName(), 
                user.getId());
    }
}


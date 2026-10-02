package com.central.service;

import com.central.entity.SysMenu;
import com.central.entity.SysRoleMenu;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单关联服务接口
 * @author zlt
 */
public interface ISysRoleMenuService extends ISuperService<SysRoleMenu> {
    int save(Long roleId, Long menuId);

    int delete(Long roleId, Long menuId);

    List<SysMenu> findMenusByRoleIds(Set<Long> roleIds, Integer type);

    List<SysMenu> findMenusByRoleCodes(Set<String> roleCodes, Integer type);
}






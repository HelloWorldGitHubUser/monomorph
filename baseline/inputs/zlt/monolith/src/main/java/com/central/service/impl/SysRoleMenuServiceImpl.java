package com.central.service.impl;

import com.central.entity.SysMenu;
import com.central.entity.SysRoleMenu;
import com.central.mapper.SysRoleMenuMapper;
import com.central.service.ISysRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 角色菜单关联服务实现
 * @author zlt
 */
@Slf4j
@Service
public class SysRoleMenuServiceImpl extends SuperServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int save(Long roleId, Long menuId) {
        return sysRoleMenuMapper.save(roleId, menuId);
    }

    @Override
    public int delete(Long roleId, Long menuId) {
        return sysRoleMenuMapper.delete(roleId, menuId);
    }

    @Override
    public List<SysMenu> findMenusByRoleIds(Set<Long> roleIds, Integer type) {
        return sysRoleMenuMapper.findMenusByRoleIds(roleIds, type);
    }

    @Override
    public List<SysMenu> findMenusByRoleCodes(Set<String> roleCodes, Integer type) {
        return sysRoleMenuMapper.findMenusByRoleCodes(roleCodes, type);
    }
}






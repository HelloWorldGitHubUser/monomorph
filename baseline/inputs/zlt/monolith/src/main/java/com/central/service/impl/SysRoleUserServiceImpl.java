package com.central.service.impl;

import com.central.entity.SysRole;
import com.central.mapper.SysUserRoleMapper;
import com.central.entity.SysRoleUser;
import com.central.service.ISysRoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 角色用户关联服务实现
 * @author zlt
 */
@Slf4j
@Service
public class SysRoleUserServiceImpl extends SuperServiceImpl<SysUserRoleMapper, SysRoleUser> implements ISysRoleUserService {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int deleteUserRole(Long userId, Long roleId) {
        return sysUserRoleMapper.deleteUserRole(userId, roleId);
    }

    @Override
    public int saveUserRoles(Long userId, Long roleId) {
        return sysUserRoleMapper.saveUserRoles(userId, roleId);
    }

    @Override
    public List<SysRole> findRolesByUserId(Long userId) {
        return sysUserRoleMapper.findRolesByUserId(userId);
    }

    @Override
    public List<SysRole> findRolesByUserIds(List<Long> userIds) {
        return sysUserRoleMapper.findRolesByUserIds(userIds);
    }
}






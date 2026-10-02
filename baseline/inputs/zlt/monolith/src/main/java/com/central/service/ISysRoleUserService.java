package com.central.service;

import com.central.entity.SysRole;
import com.central.entity.SysRoleUser;

import java.util.List;

/**
 * 角色用户关联服务接口
 * @author zlt
 */
public interface ISysRoleUserService extends ISuperService<SysRoleUser> {
    int deleteUserRole(Long userId, Long roleId);

    int saveUserRoles(Long userId, Long roleId);

    /**
     * 根据用户id获取角色
     *
     * @param userId
     * @return
     */
    List<SysRole> findRolesByUserId(Long userId);

    /**
     * 根据用户ids 获取
     *
     * @param userIds
     * @return
     */
    List<SysRole> findRolesByUserIds(List<Long> userIds);
}






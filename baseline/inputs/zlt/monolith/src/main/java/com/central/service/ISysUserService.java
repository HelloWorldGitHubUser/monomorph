package com.central.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.central.model.SysUserExcel;
import com.central.model.PageResult;
import com.central.model.Result;
import com.central.entity.SysRole;
import com.central.entity.SysUser;

/**
 * 用户服务接口
 * @author zlt
 */
public interface ISysUserService extends ISuperService<SysUser> {
    /**
     * 根据用户名查询用户（含权限信息）
     * @param username
     * @return SysUser
     */
    SysUser findByUsername(String username);

    /**
     * 根据OpenId查询用户（含权限信息）
     * @param openId
     * @return SysUser
     */
    SysUser findByOpenId(String openId);

    /**
     * 根据手机号查询用户（含权限信息）
     * @param mobile
     * @return SysUser
     */
    SysUser findByMobile(String mobile);

    /**
     * 通过SysUser 把roles和permissions也查询出来
     * @param sysUser
     * @return
     */
    void setUserPermission(SysUser sysUser);

    /**
     * 根据用户名查询用户（内部使用，不含权限）
     * @param username
     * @return
     */
    SysUser selectByUsername(String username);

    /**
     * 根据手机号查询用户（内部使用）
     * @param mobile
     * @return
     */
    SysUser selectByMobile(String mobile);

    /**
     * 根据openId查询用户（内部使用）
     * @param openId
     * @return
     */
    SysUser selectByOpenId(String openId);

    /**
     * 用户分配角色
     * @param id
     * @param roleIds
     */
    void setRoleToUser(Long id, Set<Long> roleIds);

    /**
     * 更新密码
     * @param id
     * @param oldPassword
     * @param newPassword
     * @return
     */
    Result updatePassword(Long id, String oldPassword, String newPassword);

    /**
     * 用户列表
     * @param params
     * @return
     */
    PageResult<SysUser> findUsers(Map<String, Object> params);

    /**
     * 用户角色列表
     * @param userId
     * @return
     */
    List<SysRole> findRolesByUserId(Long userId);

    /**
     * 状态变更
     * @param params
     * @return
     */
    Result updateEnabled(Map<String, Object> params);

    /**
     * 查询全部用户
     * @param params
     * @return
     */
    List<SysUserExcel> findAllUsers(Map<String, Object> params);

    Result saveOrUpdateUser(SysUser sysUser) throws Exception;

    /**
     * 删除用户
     */
    boolean delUser(Long id);
}






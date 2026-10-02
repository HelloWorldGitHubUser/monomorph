package com.central.service;

import java.util.List;
import java.util.Map;

import com.central.model.PageResult;
import com.central.model.Result;
import com.central.entity.SysRole;

/**
 * 角色服务接口
 * @author zlt
 */
public interface ISysRoleService extends ISuperService<SysRole> {
    void saveRole(SysRole sysRole) throws Exception;

    void deleteRole(Long id);

    /**
     * 角色列表
     * @param params
     * @return
     */
    PageResult<SysRole> findRoles(Map<String, Object> params);

    /**
     * 新增或更新角色
     * @param sysRole
     * @return Result
     */
    Result saveOrUpdateRole(SysRole sysRole) throws Exception;

    /**
     * 查询所有角色
     * @return
     */
    List<SysRole> findAll();
}






package com.central.mapper;

import java.util.List;
import java.util.Set;

import com.central.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.central.entity.SysMenu;

/**
 * 角色菜单 Mapper
 * @author zlt
 */
@Mapper
public interface SysRoleMenuMapper extends SuperMapper<SysRoleMenu> {
    @Insert("insert into sys_role_menu(role_id, menu_id) values(#{roleId}, #{menuId})")
    int save(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    int delete(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    List<SysMenu> findMenusByRoleIds(@Param("roleIds") Set<Long> roleIds, @Param("type") Integer type);

    List<SysMenu> findMenusByRoleCodes(@Param("roleCodes") Set<String> roleCodes, @Param("type") Integer type);
}






package com.central.mapper;

import com.central.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单 Mapper
 *
 * @author zlt
 */
@Mapper
public interface SysMenuMapper extends SuperMapper<SysMenu> {
    List<SysMenu> findByUserId(@Param("userId") Long userId, @Param("type") Integer type);
}






package com.youlai.mall.converter;

import com.youlai.mall.model.system.entity.SysMenu;
import com.youlai.mall.model.system.form.MenuForm;
import com.youlai.mall.model.system.vo.MenuVO;
import org.mapstruct.Mapper;

/**
 * 菜单对象转换器
 *
 * @author haoxr
 * @since 2022/7/29
 */
@Mapper(componentModel = "spring")
public interface MenuConverter {

    MenuVO entity2Vo(SysMenu entity);


    MenuForm entity2Form(SysMenu entity);

    SysMenu form2Entity(MenuForm menuForm);

}
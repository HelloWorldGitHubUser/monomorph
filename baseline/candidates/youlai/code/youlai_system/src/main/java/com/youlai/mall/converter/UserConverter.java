package com.youlai.mall.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.model.system.bo.UserBO;
import com.youlai.mall.model.system.bo.UserFormBO;
import com.youlai.mall.model.system.bo.UserProfileBO;
import com.youlai.mall.model.system.entity.SysUser;
import com.youlai.mall.model.system.form.UserForm;
import com.youlai.mall.model.system.vo.UserImportVO;
import com.youlai.mall.model.system.vo.UserInfoVO;
import com.youlai.mall.model.system.vo.UserPageVO;
import com.youlai.mall.model.system.vo.UserProfileVO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 用户对象转换器
 *
 * @author haoxr
 * @since 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    @Mappings({
            @Mapping(target = "genderLabel", expression = "java(com.youlai.mall.base.IBaseEnum.getLabelByValue(bo.getGender(), com.youlai.mall.enums.GenderEnum.class))")
    })
    UserPageVO bo2Vo(UserBO bo);

    Page<UserPageVO> bo2Vo(Page<UserBO> bo);

    UserForm bo2Form(UserFormBO bo);

    UserForm entity2Form(SysUser entity);

    @InheritInverseConfiguration(name = "entity2Form")
    SysUser form2Entity(UserForm entity);

    @Mappings({
            @Mapping(target = "userId", source = "id")
    })
    UserInfoVO entity2UserInfoVo(SysUser entity);

    SysUser importVo2Entity(UserImportVO vo);

    @Mappings({
            @Mapping(target = "genderLabel", expression = "java(com.youlai.mall.base.IBaseEnum.getLabelByValue(bo.getGender(), com.youlai.mall.enums.GenderEnum.class))")
    })
    UserProfileVO userProfileBo2Vo(UserProfileBO bo);
}

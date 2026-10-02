package com.youlai.mall.converter;

import com.youlai.mall.model.system.entity.SysDept;
import com.youlai.mall.model.system.form.DeptForm;
import com.youlai.mall.model.system.vo.DeptVO;
import org.mapstruct.Mapper;

/**
 * 部门对象转换器
 *
 * @author haoxr
 * @since 2022/7/29
 */
@Mapper(componentModel = "spring")
public interface DeptConverter {

    DeptForm entity2Form(SysDept entity);
    DeptVO entity2Vo(SysDept entity);

    SysDept form2Entity(DeptForm deptForm);

}
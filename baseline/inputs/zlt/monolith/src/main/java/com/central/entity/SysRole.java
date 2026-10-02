package com.central.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.central.enums.DataScope;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色实体
 * @author zlt
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRole extends SuperEntity<SysRole> {
    private static final long serialVersionUID = 4497149010220586111L;
    private String code;
    private String name;
    @TableField(exist = false)
    private Long userId;
    /**
     * 数据权限字段
     */
    private DataScope dataScope;
    private Long creatorId;
}






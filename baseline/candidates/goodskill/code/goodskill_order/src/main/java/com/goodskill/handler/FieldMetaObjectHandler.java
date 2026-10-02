package com.goodskill.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.goodskill.util.UserInfoUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * MyBatis-Plus 数据填充处理器
 * 自动填充 createTime、updateTime、createUser、updateUser 字段
 *
 * @author techa03
 */
@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 填充 createTime - 支持 LocalDateTime 和 Date 两种类型
        if (metaObject.hasSetter("createTime")) {
            Class<?> fieldType = metaObject.getSetterType("createTime");
            if (LocalDateTime.class.equals(fieldType)) {
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
            } else if (Date.class.equals(fieldType)) {
                this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
            }
        }
        
        // 填充 createUser
        this.strictInsertFill(metaObject, "createUser", UserInfoUtil::getUserId, String.class);
        
        // 填充 updateTime - 支持 LocalDateTime 和 Date 两种类型
        if (metaObject.hasSetter("updateTime")) {
            Class<?> fieldType = metaObject.getSetterType("updateTime");
            if (LocalDateTime.class.equals(fieldType)) {
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
            } else if (Date.class.equals(fieldType)) {
                this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
            }
        }
        
        // 填充 updateUser
        this.strictInsertFill(metaObject, "updateUser", UserInfoUtil::getUserId, String.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 填充 updateTime - 支持 LocalDateTime 和 Date 两种类型
        if (metaObject.hasSetter("updateTime")) {
            Class<?> fieldType = metaObject.getSetterType("updateTime");
            if (LocalDateTime.class.equals(fieldType)) {
                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
            } else if (Date.class.equals(fieldType)) {
                this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
            }
        }
        
        // 填充 updateUser
        this.strictUpdateFill(metaObject, "updateUser", UserInfoUtil::getUserId, String.class);
    }
}



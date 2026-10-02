package com.goodskill.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodskill.entity.mysql.Permission;
import com.goodskill.mapper.PermissionMapper;
import com.goodskill.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heng
 * @since 2019-09-07
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}


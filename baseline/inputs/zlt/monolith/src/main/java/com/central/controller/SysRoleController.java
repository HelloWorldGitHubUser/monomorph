package com.central.controller;

import java.util.List;
import java.util.Map;

import com.central.entity.SysRole;
import com.central.log.annotation.AuditLog;
import com.central.model.PageResult;
import com.central.model.Result;
import com.central.service.ISysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色管理控制器
 * 模拟网关路由前缀 /api-user
 * @author zlt
 */
@Slf4j
@RestController
@Tag(name = "角色模块api")
@RequestMapping("/api-user")
public class SysRoleController {
    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 后台管理查询角色
     */
    @Operation(summary = "后台管理查询角色")
    @GetMapping("/roles")
    public PageResult<SysRole> findRoles(@RequestParam Map<String, Object> params) {
        return sysRoleService.findRoles(params);
    }

    /**
     * 用户管理查询所有角色
     */
    @Operation(summary = "查询所有角色")
    @GetMapping("/allRoles")
    public Result<List<SysRole>> findAll() {
        List<SysRole> result = sysRoleService.findAll();
        return Result.succeed(result);
    }

    /**
     * 角色新增或者更新
     */
    @AuditLog(operation = "'新增或更新角色:' + #sysRole.name")
    @PostMapping("/roles/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody SysRole sysRole) throws Exception {
        return sysRoleService.saveOrUpdateRole(sysRole);
    }

    /**
     * 后台管理删除角色
     */
    @AuditLog(operation = "'删除角色:' + #id")
    @Operation(summary = "后台管理删除角色")
    @DeleteMapping("/roles/{id}")
    public Result deleteRole(@PathVariable Long id) {
        try {
            if (id == 1L) {
                return Result.failed("管理员不可以删除");
            }
            sysRoleService.deleteRole(id);
            return Result.succeed("操作成功");
        } catch (Exception e) {
            log.error("role-deleteRole-error", e);
            return Result.failed("操作失败");
        }
    }
}



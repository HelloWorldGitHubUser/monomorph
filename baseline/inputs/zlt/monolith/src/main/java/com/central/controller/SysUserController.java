package com.central.controller;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.central.constant.CommonConstant;
import com.central.model.*;
import com.central.model.dto.LogicDelDto;
import com.central.model.dto.SearchDto;
import com.central.enums.UserType;
import com.central.entity.SysRole;
import com.central.entity.SysUser;
import com.central.service.ISearchService;
import com.central.service.ISysUserService;
import com.central.util.ExcelUtil;
import com.central.log.annotation.AuditLog;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户管理控制器
 * 模拟网关路由前缀 /api-user
 * @author zlt
 */
@Slf4j
@RestController
@Tag(name = "用户模块api")
@RequestMapping("/api-user")
public class SysUserController {
    private static final String ADMIN_CHANGE_MSG = "超级管理员不给予修改";
    private static final String SYS_USER_INDEX = "sys_user";
    private static final String TOKEN_PREFIX = "token:";
    /**
     * 用户搜索逻辑删除条件：过滤 isDel=false 的记录
     */
    private static final LogicDelDto SEARCH_LOGIC_DEL_DTO = new LogicDelDto("isDel", "false");

    @Autowired
    private ISysUserService appUserService;

    @Autowired
    private ISearchService searchService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据用户名查询用户
     */
    @GetMapping(value = "/users/name/{username}")
    @Operation(summary = "根据用户名查询用户实体")
    public SysUser selectByUsername(@PathVariable String username) {
        return appUserService.selectByUsername(username);
    }

    /**
     * 根据用户名查询用户（登录使用）
     */
    @GetMapping(value = "/users-anon/login", params = "username")
    @Operation(summary = "根据用户名查询用户")
    public SysUser findByUsername(String username) {
        return appUserService.findByUsername(username);
    }

    /**
     * 通过手机号查询用户
     */
    @GetMapping(value = "/users-anon/mobile", params = "mobile")
    @Operation(summary = "根据手机号查询用户")
    public SysUser findByMobile(String mobile) {
        return appUserService.findByMobile(mobile);
    }

    /**
     * 根据OpenId查询用户
     */
    @GetMapping(value = "/users-anon/openId", params = "openId")
    @Operation(summary = "根据OpenId查询用户")
    public SysUser findByOpenId(String openId) {
        return appUserService.findByOpenId(openId);
    }

    @GetMapping("/users/{id}")
    public SysUser findUserById(@PathVariable Long id) {
        return appUserService.getById(id);
    }

    /**
     * 修改用户
     */
    @PutMapping("/users")
    public void updateSysUser(@RequestBody SysUser sysUser) {
        appUserService.updateById(sysUser);
    }

    /**
     * 给用户分配角色
     */
    @PostMapping("/users/{id}/roles")
    public void setRoleToUser(@PathVariable Long id, @RequestBody Set<Long> roleIds) {
        appUserService.setRoleToUser(id, roleIds);
    }

    /**
     * 获取用户的角色
     */
    @GetMapping("/users/{id}/roles")
    public List<SysRole> findRolesByUserId(@PathVariable Long id) {
        return appUserService.findRolesByUserId(id);
    }

    /**
     * 用户查询
     */
    @Operation(summary = "用户查询列表")
    @Parameters({
            @Parameter(name = "page", description = "分页起始位置", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "limit", description = "分页结束位置", required = true, in = ParameterIn.QUERY)
    })
    @GetMapping("/users")
    public PageResult<SysUser> findUsers(@RequestParam Map<String, Object> params) {
        return appUserService.findUsers(params);
    }

    /**
     * 修改用户状态
     */
    @AuditLog(operation = "'修改用户状态'")
    @Operation(summary = "修改用户状态")
    @GetMapping("/users/updateEnabled")
    @Parameters({
            @Parameter(name = "id", description = "用户id", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "enabled", description = "是否启用", required = true, in = ParameterIn.QUERY)
    })
    public Result updateEnabled(@RequestParam Map<String, Object> params) {
        Long id = MapUtils.getLong(params, "id");
        if (checkAdmin(id)) {
            return Result.failed(ADMIN_CHANGE_MSG);
        }
        return appUserService.updateEnabled(params);
    }

    /**
     * 重置密码
     */
    @PutMapping(value = "/users/{id}/password")
    public Result resetPassword(@PathVariable Long id) {
        if (checkAdmin(id)) {
            return Result.failed(ADMIN_CHANGE_MSG);
        }
        appUserService.updatePassword(id, null, null);
        return Result.succeed("重置成功");
    }

    /**
     * 用户自己修改密码
     */
    @PutMapping(value = "/users/password")
    public Result resetPassword(@RequestBody SysUser sysUser) {
        if (checkAdmin(sysUser.getId())) {
            return Result.failed(ADMIN_CHANGE_MSG);
        }
        appUserService.updatePassword(sysUser.getId(), sysUser.getOldPassword(), sysUser.getNewPassword());
        return Result.succeed("重置成功");
    }

    /**
     * 删除用户
     */
    @AuditLog(operation = "'删除用户:' + #id")
    @DeleteMapping(value = "/users/{id}")
    public Result delete(@PathVariable Long id) {
        if (checkAdmin(id)) {
            return Result.failed(ADMIN_CHANGE_MSG);
        }
        appUserService.delUser(id);
        return Result.succeed("删除成功");
    }

    /**
     * 新增或更新用户
     */
    @AuditLog(operation = "'新增或更新用户:' + #sysUser.username")
    @PostMapping("/users/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody SysUser sysUser) throws Exception {
        return appUserService.saveOrUpdateUser(sysUser);
    }

    /**
     * 用户全文搜索
     * 使用ES进行全文搜索
     */
    @Operation(summary = "用户全文搜索列表")
    @Parameters({
            @Parameter(name = "page", description = "分页起始位置", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "limit", description = "分页结束位置", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "queryStr", description = "搜索关键字", in = ParameterIn.QUERY)
    })
    @GetMapping("/users/search")
    public PageResult<JsonNode> search(SearchDto searchDto) {
        try {
            searchDto.setIsHighlighter(true);
            searchDto.setSortCol("createTime");
            // 使用逻辑删除条件，过滤已删除的用户
            return searchService.strQuery(SYS_USER_INDEX, searchDto, SEARCH_LOGIC_DEL_DTO);
        } catch (Exception e) {
            log.error("用户搜索失败", e);
            return PageResult.<JsonNode>builder().data(java.util.Collections.emptyList()).code(0).count(0L).build();
        }
    }

    /**
     * 导出用户Excel
     */
    @PostMapping("/users/export")
    @Operation(summary = "导出用户Excel")
    public void exportUser(@RequestParam Map<String, Object> params, HttpServletResponse response) throws IOException {
        List<SysUserExcel> result = appUserService.findAllUsers(params);
        // 导出操作
        ExcelUtil.exportExcel(result, null, "用户", SysUserExcel.class, "user.xlsx", response);
    }

    /**
     * 导入用户Excel
     */
    @PostMapping(value = "/users/import")
    @Operation(summary = "导入用户Excel")
    public Result<?> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        int rowNum = 0;
        if (!file.isEmpty()) {
            List<SysUserExcel> list = ExcelUtil.importExcel(file, 0, 1, SysUserExcel.class);
            rowNum = list.size();
            if (rowNum > 0) {
                List<SysUser> users = new ArrayList<>(rowNum);
                list.forEach(u -> {
                    SysUser user = new SysUser();
                    BeanUtil.copyProperties(u, user);
                    user.setPassword(CommonConstant.DEF_USER_PASSWORD);
                    user.setType(UserType.BACKEND.name());
                    users.add(user);
                });
                appUserService.saveBatch(users);
            }
        }
        return Result.succeed("导入数据成功，一共【" + rowNum + "】行");
    }

    /**
     * 是否超级管理员
     */
    private boolean checkAdmin(long id) {
        return id == 1L;
    }

    // ==================== 当前用户相关接口（对应微服务 user-center 的功能） ====================

    /**
     * 获取当前用户信息 (前端 index.js 调用)
     */
    @GetMapping("/users/current")
    @Operation(summary = "获取当前登录用户信息")
    public Result<?> currentUser(@RequestHeader(value = "Authorization", required = false) String authorization) {
        log.info("=== 获取用户请求 === Authorization: {}", authorization);
        SysUser user = getUserFromToken(authorization);
        if (user == null) {
            log.warn("Token 无效或未提供");
            return Result.failed("未登录或Token已过期");
        }
        log.info("获取用户成功: {}", user.getUsername());

        // 获取用户权限
        appUserService.setUserPermission(user);

        // 构建返回数据
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("mobile", user.getMobile());
        // 头像处理：如果为空则使用默认头像
        String headImgUrl = user.getHeadImgUrl();
        if (StrUtil.isBlank(headImgUrl)) {
            headImgUrl = "assets/images/head.png";
        }
        userInfo.put("headImgUrl", headImgUrl);
        userInfo.put("enabled", user.getEnabled());
        userInfo.put("type", user.getType());

        // 权限列表
        if (user.getPermissions() != null) {
            userInfo.put("permissions", user.getPermissions());
        } else {
            userInfo.put("permissions", new HashSet<>());
        }

        // 角色列表
        if (user.getRoles() != null) {
            List<String> roleCodes = user.getRoles().stream()
                    .map(SysRole::getCode)
                    .collect(Collectors.toList());
            userInfo.put("roles", roleCodes);
        } else {
            userInfo.put("roles", new ArrayList<>());
        }

        return Result.succeed(userInfo);
    }

    /**
     * 从 Token 获取用户
     */
    private SysUser getUserFromToken(String authorization) {
        if (StrUtil.isBlank(authorization)) {
            return null;
        }
        String token = authorization.replace("Bearer ", "").replace("bearer ", "");
        String userId = stringRedisTemplate.opsForValue().get(TOKEN_PREFIX + token);
        if (StrUtil.isBlank(userId)) {
            return null;
        }
        return appUserService.getById(Long.parseLong(userId));
    }
}

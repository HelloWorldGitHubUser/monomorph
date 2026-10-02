/*
 * 有来商城单体应用数据库
 * MySQL 8.x版本
 */

-- ----------------------------
-- 创建单体数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS youlai_mall DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

USE youlai_mall;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ================================
-- 系统管理模块表 (youlai_system)
-- 表前缀: sys_*
-- ================================

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父节点id',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '父节点id路径',
  `sort` int NULL DEFAULT 0 COMMENT '显示顺序',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态(1:正常;0:禁用)',
  `deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除标识(1:已删除;0:未删除)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

INSERT INTO `sys_dept` VALUES (1, '有来技术', 0, '0', 1, 1, 0, NULL, NULL);
INSERT INTO `sys_dept` VALUES (2, '研发部门', 1, '0,1', 1, 1, 0, NULL, '2022-04-19 12:46:37');
INSERT INTO `sys_dept` VALUES (3, '测试部门', 1, '0,1', 1, 1, 0, NULL, '2022-04-19 12:46:37');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典类型编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典项名称',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典项值',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态(1:正常;0:禁用)',
  `defaulted` tinyint NULL DEFAULT 0 COMMENT '是否默认(1:是;0:否)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'gender', '男', '1', 1, 1, 0, NULL, '2019-05-05 13:07:52', '2022-06-12 23:20:39');
INSERT INTO `sys_dict` VALUES (2, 'gender', '女', '2', 2, 1, 0, NULL, '2019-04-19 11:33:00', '2019-07-02 14:23:05');
INSERT INTO `sys_dict` VALUES (3, 'gender', '未知', '0', 1, 1, 0, NULL, '2020-10-17 08:09:31', '2020-10-17 08:09:31');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类型名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '类型编码',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态(0:正常;1:禁用)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '性别', 'gender', 1, NULL, '2019-12-06 19:03:32', '2022-06-12 16:21:28');
INSERT INTO `sys_dict_type` VALUES (2, '授权方式', 'grant_type', 1, NULL, '2020-10-17 08:09:50', '2021-01-31 09:48:24');
INSERT INTO `sys_dict_type` VALUES (3, '微服务列表', 'micro_service', 1, NULL, '2021-06-17 00:13:43', '2021-06-17 00:17:22');
INSERT INTO `sys_dict_type` VALUES (4, '请求方式', 'request_method', 1, NULL, '2021-06-17 00:18:07', '2021-06-17 00:18:07');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID',
  `type` tinyint NULL DEFAULT NULL COMMENT '菜单类型(1：菜单；2：目录；3：外链；4：按钮)',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单名称',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由路径(浏览器地址栏路径)',
  `component` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径(vue页面完整路径，省略.vue后缀)',
  `perm` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '按钮权限标识',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单图标',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '状态(0:禁用;1:开启)',
  `redirect` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '跳转路径',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `always_show` tinyint NULL DEFAULT NULL COMMENT '【目录】只有一个子路由是否始终显示(1:是 0:否)',
  `keep_alive` tinyint NULL DEFAULT NULL COMMENT '【菜单】是否开启页面缓存(1:是 0:否)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, 2, '系统管理', '/system', 'Layout', NULL, 'system', 1, 1, '/system/user', NULL, NULL, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (2, 1, 1, '用户管理', 'user', 'system/user/index', NULL, 'user', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (3, 1, 1, '角色管理', 'role', 'system/role/index', NULL, 'role', 2, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (4, 1, 1, '菜单管理', 'cmenu', 'system/menu/index', NULL, 'menu', 3, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (5, 1, 1, '部门管理', 'dept', 'system/dept/index', NULL, 'tree', 4, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (6, 1, 1, '字典管理', 'dict', 'system/dict/index', NULL, 'dict', 5, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (9, 0, 2, '营销管理', '/sms', 'Layout', NULL, 'number', 5, 1, NULL, NULL, NULL, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (10, 9, 1, '广告列表', 'advert', 'sms/advert/index', NULL, 'advert', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (11, 0, 1, '商品管理', '/pms', 'Layout', NULL, 'el-icon-Goods', 2, 1, '/pms/goods', '0', NULL, 1, '2021-08-28 09:12:21', '2024-03-03 23:54:35');
INSERT INTO `sys_menu` VALUES (12, 11, 1, '商品列表', 'goods', 'pms/goods/index', NULL, 'goods-list', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (13, 0, 2, '订单管理', '/oms', 'Layout', NULL, 'el-icon-ShoppingCart', 3, 1, '/oms/order', '0', 1, 1, '2021-08-28 09:12:21', '2024-03-03 23:52:32');
INSERT INTO `sys_menu` VALUES (14, 13, 1, '订单列表', 'order', 'oms/order/index', NULL, 'el-icon-Document', 1, 1, NULL, 'null,13', NULL, 1, '2021-08-28 09:12:21', '2024-03-03 23:45:09');
INSERT INTO `sys_menu` VALUES (15, 0, 1, '会员管理', '/ums', 'Layout', NULL, 'user', 4, 1, '/ums/member', NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (16, 15, 1, '会员列表', 'member', 'ums/member/index', NULL, 'peoples', 1, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (17, 11, 1, '品牌管理', 'brand', 'pms/brand/index', NULL, 'brand', 5, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (18, 11, 1, '商品分类', 'category', 'pms/category/index', NULL, 'menu', 3, 1, NULL, NULL, NULL, 1, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (19, 11, 1, '商品上架', 'goods-detail', 'pms/goods/detail', NULL, 'publish', 2, 1, NULL, NULL, NULL, NULL, '2021-08-28 09:12:21', '2021-08-28 09:12:21');
INSERT INTO `sys_menu` VALUES (20, 0, 2, '多级菜单', '/multi-level', 'Layout', NULL, 'nested', 7, 1, '/nested/level1/level2', NULL, NULL, NULL, '2022-02-16 23:11:00', '2022-02-16 23:11:00');
INSERT INTO `sys_menu` VALUES (21, 20, 1, '菜单一级', 'level1', 'demo/multi-level/level1', NULL, '', 1, 1, '/nested/level1/level2', NULL, NULL, NULL, '2022-02-16 23:13:38', '2022-02-16 23:13:38');
INSERT INTO `sys_menu` VALUES (22, 21, 1, '菜单二级', 'level2', 'demo/multi-level/children/level2', NULL, '', 1, 1, '/nested/level1/level2/level3', NULL, NULL, NULL, '2022-02-16 23:14:23', '2022-02-16 23:14:23');
INSERT INTO `sys_menu` VALUES (23, 22, 1, '菜单三级-1', 'level3-1', 'demo/multi-level/children/children/level3-1', NULL, '', 1, 1, '', NULL, NULL, NULL, '2022-02-16 23:14:51', '2022-02-16 23:14:51');
INSERT INTO `sys_menu` VALUES (24, 22, 1, '菜单三级-2', 'level3-2', 'demo/multi-level/children/children/level3-2', NULL, '', 2, 1, '', NULL, NULL, NULL, '2022-02-16 23:15:08', '2022-02-16 23:15:08');
INSERT INTO `sys_menu` VALUES (26, 0, 1, '外部链接', '/external-link', 'Layout', NULL, 'link', 9, 1, 'noredirect', NULL, NULL, NULL, '2022-02-17 22:51:20', '2022-02-17 22:51:20');
INSERT INTO `sys_menu` VALUES (30, 26, 3, '项目文档', 'https://juejin.cn/post/7228990409909108793', '', NULL, 'link', 1, 1, '', NULL, NULL, NULL, '2022-02-18 00:01:40', '2022-02-18 00:01:40');
INSERT INTO `sys_menu` VALUES (37, 9, 1, '优惠券列表', 'coupon', 'sms/coupon/index', NULL, 'menu', 2, 1, '', NULL, NULL, NULL, '2022-05-29 00:24:07', '2022-05-29 00:24:07');
INSERT INTO `sys_menu` VALUES (40, 2, 4, '新增用户', '', NULL, 'sys:user:add', '', 1, 1, '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (41, 2, 4, '修改用户', '', NULL, 'sys:user:edit', '', 2, 1, '', NULL, NULL, NULL, '2022-11-05 01:26:44', '2022-11-05 01:26:44');
INSERT INTO `sys_menu` VALUES (42, 2, 4, '删除用户', '', NULL, 'sys:user:del', '', 3, 1, '', NULL, NULL, NULL, '2022-11-05 01:27:13', '2022-11-05 01:27:13');
INSERT INTO `sys_menu` VALUES (70, 3, 4, '角色新增', '', NULL, 'sys:role:add', '', 1, 1, NULL, '0,1,3', NULL, NULL, '2023-05-20 23:39:09', '2023-05-20 23:39:09');
INSERT INTO `sys_menu` VALUES (71, 3, 4, '角色编辑', '', NULL, 'sys:role:edit', '', 2, 1, NULL, '0,1,3', NULL, NULL, '2023-05-20 23:40:31', '2023-05-20 23:40:31');
INSERT INTO `sys_menu` VALUES (72, 3, 4, '角色删除', '', NULL, 'sys:role:delete', '', 3, 1, NULL, '0,1,3', NULL, NULL, '2023-05-20 23:41:08', '2023-05-20 23:41:08');
INSERT INTO `sys_menu` VALUES (73, 4, 4, '菜单新增', '', NULL, 'sys:menu:add', '', 1, 1, NULL, '0,1,4', NULL, NULL, '2023-05-20 23:41:35', '2023-05-20 23:41:35');
INSERT INTO `sys_menu` VALUES (74, 4, 4, '菜单编辑', '', NULL, 'sys:menu:edit', '', 3, 1, NULL, '0,1,4', NULL, NULL, '2023-05-20 23:41:58', '2023-05-20 23:41:58');
INSERT INTO `sys_menu` VALUES (75, 4, 4, '菜单删除', '', NULL, 'sys:menu:delete', '', 3, 1, NULL, '0,1,4', NULL, NULL, '2023-05-20 23:44:18', '2023-05-20 23:44:18');
INSERT INTO `sys_menu` VALUES (76, 5, 4, '部门新增', '', NULL, 'sys:dept:add', '', 1, 1, NULL, '0,1,5', NULL, NULL, '2023-05-20 23:45:00', '2023-05-20 23:45:00');
INSERT INTO `sys_menu` VALUES (77, 5, 4, '部门编辑', '', NULL, 'sys:dept:edit', '', 2, 1, NULL, '0,1,5', NULL, NULL, '2023-05-20 23:46:16', '2023-05-20 23:46:16');
INSERT INTO `sys_menu` VALUES (78, 5, 4, '部门删除', '', NULL, 'sys:dept:delete', '', 3, 1, NULL, '0,1,5', NULL, NULL, '2023-05-20 23:46:36', '2023-05-20 23:46:36');
INSERT INTO `sys_menu` VALUES (79, 6, 4, '字典类型新增', '', NULL, 'sys:dict_type:add', '', 1, 1, NULL, '0,1,6', NULL, NULL, '2023-05-21 00:16:06', '2023-05-21 00:16:06');
INSERT INTO `sys_menu` VALUES (81, 6, 4, '字典类型编辑', '', NULL, 'sys:dict_type:edit', '', 2, 1, NULL, '0,1,6', NULL, NULL, '2023-05-21 00:27:37', '2023-05-21 00:27:37');
INSERT INTO `sys_menu` VALUES (84, 6, 4, '字典类型删除', '', NULL, 'sys:dict_type:delete', '', 3, 1, NULL, '0,1,6', NULL, NULL, '2023-05-21 00:29:39', '2023-05-21 00:29:39');
INSERT INTO `sys_menu` VALUES (85, 6, 4, '字典数据新增', '', NULL, 'sys:dict:add', '', 4, 1, NULL, '0,1,6', NULL, NULL, '2023-05-21 00:46:56', '2023-05-21 00:47:06');
INSERT INTO `sys_menu` VALUES (86, 6, 4, '字典数据编辑', '', NULL, 'sys:dict:edit', '', 5, 1, NULL, '0,1,6', NULL, NULL, '2023-05-21 00:47:36', '2023-05-21 00:47:36');
INSERT INTO `sys_menu` VALUES (87, 6, 4, '字典数据删除', '', NULL, 'sys:dict:delete', '', 6, 1, NULL, '0,1,6', NULL, NULL, '2023-05-21 00:48:10', '2023-05-21 00:48:20');
INSERT INTO `sys_menu` VALUES (88, 2, 4, '重置密码', '', NULL, 'sys:user:reset_pwd', '', 4, 1, NULL, '0,1,2', NULL, NULL, '2023-05-21 00:49:18', '2023-05-21 00:49:18');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `sort` int NULL DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint NULL DEFAULT 1 COMMENT '角色状态(1-正常；0-停用)',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除；1-已删除)',
  `data_scope` tinyint NULL DEFAULT NULL COMMENT '数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'ROOT', 1, 1, 0, 0, '2021-05-21 14:56:51', '2018-12-23 16:00:00');
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'ADMIN', 1, 1, 0, 0, '2021-03-25 12:39:54', '2022-11-05 00:22:02');
INSERT INTO `sys_role` VALUES (3, '访问游客', 'GUEST', 3, 1, 0, 30, '2021-05-26 15:49:05', '2019-05-05 16:00:00');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 40);
INSERT INTO `sys_role_menu` VALUES (2, 41);
INSERT INTO `sys_role_menu` VALUES (2, 42);
INSERT INTO `sys_role_menu` VALUES (2, 88);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 70);
INSERT INTO `sys_role_menu` VALUES (2, 71);
INSERT INTO `sys_role_menu` VALUES (2, 72);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 73);
INSERT INTO `sys_role_menu` VALUES (2, 74);
INSERT INTO `sys_role_menu` VALUES (2, 75);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 76);
INSERT INTO `sys_role_menu` VALUES (2, 77);
INSERT INTO `sys_role_menu` VALUES (2, 78);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 79);
INSERT INTO `sys_role_menu` VALUES (2, 81);
INSERT INTO `sys_role_menu` VALUES (2, 84);
INSERT INTO `sys_role_menu` VALUES (2, 85);
INSERT INTO `sys_role_menu` VALUES (2, 86);
INSERT INTO `sys_role_menu` VALUES (2, 87);
INSERT INTO `sys_role_menu` VALUES (2, 11);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (2, 19);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (2, 17);
INSERT INTO `sys_role_menu` VALUES (2, 13);
INSERT INTO `sys_role_menu` VALUES (2, 14);
INSERT INTO `sys_role_menu` VALUES (2, 15);
INSERT INTO `sys_role_menu` VALUES (2, 16);
INSERT INTO `sys_role_menu` VALUES (2, 9);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 37);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 24);
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 30);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) NULL DEFAULT 1 COMMENT '性别((1:男;2:女))',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `dept_id` int NULL DEFAULT NULL COMMENT '部门ID',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户头像',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '用户状态((1:正常;0:禁用))',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(0:未删除;1:已删除)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', '有来技术', 0, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', NULL, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210123', 1, 'youlaitech@163.com', 0, '2023-04-12 01:31:29', '2023-04-12 01:31:29', 1, 1);
INSERT INTO `sys_user` VALUES (2, 'admin', '系统管理员', 1, '$2a$10$8/8PxGHA.30EeWg8x4/4BuWuCUJubFbGJXyUYRs7RaJEdVvEMRbWe', 2, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210123', 1, 'youlaitech@163.com', 0, '2023-04-12 01:31:29', '2023-04-12 01:31:29', 1, 1);
INSERT INTO `sys_user` VALUES (3, 'test', '测试小用户', 1, '$2a$10$MPJkNw.hKT/fZOgwYP8q9eu/rFJJDsNov697AmdkHNJkpjIpVSw2q', 3, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210123', 1, 'youlaitech@163.com', 0, '2023-04-12 01:31:29', '2023-04-12 01:31:29', 1, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` int NOT NULL COMMENT '用户ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (3, 3);

SET FOREIGN_KEY_CHECKS = 1;
-- ================================
-- OAuth2认证模块表 (oauth2_server)
-- 表前缀: oauth2_*
-- ================================

-- ----------------------------
-- Table structure for oauth2_authorization
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_authorization`;
CREATE TABLE `oauth2_authorization` (
    `id` varchar(100) NOT NULL,
    `registered_client_id` varchar(100) NOT NULL,
    `principal_name` varchar(200) NOT NULL,
    `authorization_grant_type` varchar(100) NOT NULL,
    `authorized_scopes` varchar(1000) DEFAULT NULL,
    `attributes` blob DEFAULT NULL,
    `state` varchar(500) DEFAULT NULL,
    `authorization_code_value` blob DEFAULT NULL,
    `authorization_code_issued_at` timestamp DEFAULT NULL,
    `authorization_code_expires_at` timestamp DEFAULT NULL,
    `authorization_code_metadata` blob DEFAULT NULL,
    `access_token_value` blob DEFAULT NULL,
    `access_token_issued_at` timestamp DEFAULT NULL,
    `access_token_expires_at` timestamp DEFAULT NULL,
    `access_token_metadata` blob DEFAULT NULL,
    `access_token_type` varchar(100) DEFAULT NULL,
    `access_token_scopes` varchar(1000) DEFAULT NULL,
    `oidc_id_token_value` blob DEFAULT NULL,
    `oidc_id_token_issued_at` timestamp DEFAULT NULL,
    `oidc_id_token_expires_at` timestamp DEFAULT NULL,
    `oidc_id_token_metadata` blob DEFAULT NULL,
    `refresh_token_value` blob DEFAULT NULL,
    `refresh_token_issued_at` timestamp DEFAULT NULL,
    `refresh_token_expires_at` timestamp DEFAULT NULL,
    `refresh_token_metadata` blob DEFAULT NULL,
    `user_code_value` blob DEFAULT NULL,
    `user_code_issued_at` timestamp DEFAULT NULL,
    `user_code_expires_at` timestamp DEFAULT NULL,
    `user_code_metadata` blob DEFAULT NULL,
    `device_code_value` blob DEFAULT NULL,
    `device_code_issued_at` timestamp DEFAULT NULL,
    `device_code_expires_at` timestamp DEFAULT NULL,
    `device_code_metadata` blob DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'OAuth2授权记录表';

-- ----------------------------
-- 2 oauth2_authorization_consent 授权记录表
-- ----------------------------
CREATE TABLE oauth2_authorization_consent (
    registered_client_id varchar(100) NOT NULL,
    principal_name varchar(200) NOT NULL,
    authorities varchar(1000) NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);

-- ----------------------------
-- 3 oauth2-registered-client OAuth2 客户端信息表
-- ----------------------------
CREATE TABLE oauth2_registered_client (
    id varchar(100) NOT NULL,
    client_id varchar(100) NOT NULL,
    client_id_issued_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret varchar(200) DEFAULT NULL,
    client_secret_expires_at timestamp DEFAULT NULL,
    client_name varchar(200) NOT NULL,
    client_authentication_methods varchar(1000) NOT NULL,
    authorization_grant_types varchar(1000) NOT NULL,
    redirect_uris varchar(1000) DEFAULT NULL,
    post_logout_redirect_uris varchar(1000) DEFAULT NULL,
    scopes varchar(1000) NOT NULL,
    client_settings varchar(2000) NOT NULL,
    token_settings varchar(2000) NOT NULL,
    PRIMARY KEY (id)
);


-- ----------------------------
-- Records of oauth2_registered_client
-- ----------------------------
INSERT INTO `oauth2_registered_client` VALUES ('0d2eb3c4-bab5-4973-a64e-2c5c3898e249', 'mall-app',now(), '{bcrypt}$2a$10$OqbaoJTZyCnnBLhtaAKumONuMuwfigqRzjicx4B2Lqy3YsgTBF9Hq', NULL, '商城APP客户端', 'client_secret_basic', 'refresh_token,client_credentials,authorization_code,sms_code,wechat', 'http://127.0.0.1:8080/authorized', 'http://127.0.0.1:8080/logged-out', 'openid,profile', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",86400.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000],\"settings.token.authorization-code-time-to-live\":[\"java.time.Duration\",300.000000000],\"settings.token.device-code-time-to-live\":[\"java.time.Duration\",300.000000000]}');
INSERT INTO `oauth2_registered_client` VALUES ('94aa75c2-1c00-44d5-a1ff-edb18f8ad9c4', 'mall-admin', now(), '{bcrypt}$2a$10$9klpwOFEl1zeWlATOYNFYeHoWuDsb160od86/cJrJG8Ik.7fQNOaC', NULL, '商城管理客户端', 'client_secret_basic', 'refresh_token,client_credentials,password,captcha,authorization_code', 'http://127.0.0.1:8080/authorized', 'http://127.0.0.1:8080/logged-out', 'openid,profile', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",86400.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000],\"settings.token.authorization-code-time-to-live\":[\"java.time.Duration\",300.000000000],\"settings.token.device-code-time-to-live\":[\"java.time.Duration\",300.000000000]}');
INSERT INTO `oauth2_registered_client` VALUES ('94aa75c2-1c00-44d5-a1ff-edb18f8ad9c5', 'client', now(), '{bcrypt}$2a$10$T2Z9XX60zjBS0pwXZmqe1.uEf6OJiPEUhGKZg5b0SnZPwtvlxESJ2', NULL, '商城管理客户端', 'client_secret_basic', 'refresh_token,client_credentials,password,captcha,authorization_code', 'http://127.0.0.1:8080/authorized', 'http://127.0.0.1:8080/logged-out', 'openid,profile', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",86400.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",3600.000000000],\"settings.token.authorization-code-time-to-live\":[\"java.time.Duration\",300.000000000],\"settings.token.device-code-time-to-live\":[\"java.time.Duration\",300.000000000]}');

-- ================================
-- 会员模块表 (mall_ums)
-- 表前缀: ums_*
-- ================================

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `gender` tinyint(1) NULL DEFAULT NULL,
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `openid` char(28) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `session_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT 1,
  `point` int NULL DEFAULT 0 COMMENT '会员积分',
  `deleted` tinyint(1) NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `balance` bigint NULL DEFAULT 1000000000,
  `city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `country` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `language` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_member
-- ----------------------------
INSERT INTO `ums_member` VALUES (1, 1, '郝先瑞', '18866668888', NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/J31cY2qVWviaOqhjPlr18VY5ic1SUvDESG1mQkicQfFugWibYe7VJIhYJBZYDBib0T4TJVhUOtiaW1TGkJRqIWd3K0dQ/132', 'oUBUG5hAB_8EMrSaqd2HjJQBFg74', NULL, 1, 0, 0, '2022-02-26 20:59:20', '2022-02-26 20:59:20', 812659400, '', '', 'zh_CN', '');
INSERT INTO `ums_member` VALUES (2, 1, 'Flamesky', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eorwiaJcRPxKMNHgov0HGBRA8JODQrhw67x61FGEFwic2E2UlhXSKmQ455jqT5RIPsZjmpkdia0pyZdA/132', 'oEMah4qx8utBwve1_5U2bq4z9Ucw', NULL, 1, 0, 0, '2021-01-12 17:52:03', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (3, 1, '非洲小白脸', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIIs1glKcYOadLFibr2et98eXTADdicLUGrQqF8EtvicIu5e5TwOkuBAzIf8zEl0aYPJaDkfIHTOEWuQ/132', 'oH-MK0V-N4Lotq-kXIMAMjLdXdtY', NULL, 1, 0, 0, '2021-01-12 17:52:06', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (4, 1, '花花的世界', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLxWhtkFhVKpfXib0BibMaIzeOAVCGVScnR5ibsibdENiaibjvnfy7AxeSSCTbn9IBvqMe1iaJ6BWTxIjZtg/132', 'oUBUG5lBhnnn-HBCF9mYMZbQv7A8', NULL, 1, 0, 0, '2021-01-12 17:52:09', '2021-02-24 22:43:41', 999680200, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (5, 1, '微尘', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/vNZqQTZRAia4sz17MJeeXeqhzaBbIEzEXGvgwG4l1KQg2mQAb3eB1q9HLnVJUo4u8OSNSv1seuqHxNPKyYicb4Dw/132', 'oZ75o5Kk-opj_ioVOj6vTO7-K0HM', NULL, 1, 0, 0, '2021-01-12 17:52:12', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (6, 1, '大田', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIPhsrhOFictxk44ialrd4gZP6SyFmz4v2rHBZ3C72O0KsKQDTHlVBqtoSJ5uiaPAvD0t9F5VBjbruQw/132', 'oUBUG5m_9Gfa3bZmzP_tRNnX2vsg', NULL, 1, 0, 0, '2021-01-12 17:52:16', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (7, 1, '看好路，向前走！', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/lZibiaShtph66QznR6yiarR7VsBkkjqGqPCwqDGD8WlaxnllcjG7SRiaX0DOujFXX8epAbyvFpHv03uI83xXFhdwZA/132', 'oUBUG5tj5LF8IjJDU2s1cqSdthdo', NULL, 1, 0, 0, NULL, NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (8, 1, 'CIAO！', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/0d5kVzsH20SUXzPjbgamFn7DraWURYE7GJX15rMSVVDCeHN3kKW3ZozlUichS7Ch5jXADocWYW3jzBTj24oZVKw/132', 'oEeWo5IXHzfBqOQRI5mzSAfzoN2A', NULL, 1, 0, 0, NULL, NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (10, 1, '时光会咬人', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJyb4yl7JJCDKNX3yRwGjZ8fdXBTSVaW9cQIErvibmDR08m0vsrqWonxvRibrFxric0wqAKgVFa1IBlg/132', 'oUBUG5mQmw7d5XMwci78J6nDApA0', NULL, 1, 0, 0, '2021-01-30 17:22:18', NULL, 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (14, 1, '77777777', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83ercp0SnvuleWloRkX8y5pibLHtg2OoKGECJH7udBoAoicsO87ibjmsUMiaDgJAJ8ibaiavGv1aEQicle8lMA/132', 'oUBUG5mOf9q54E6ob0kDC_cymoiw', NULL, 1, 0, 0, '2021-02-02 19:49:50', '2021-02-02 19:49:50', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (15, 1, 'Max_Qiu', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIspnkuj3p0Ly4v6dIz5nClVLnNvIE5BVyd6ORaz6kLrwsxbicfqnG7ic4JpqWedpqk1lgx71QlHauQ/132', 'owHt46JBw46D1cCP8kyLefAoB8Ss', NULL, 1, 0, 0, '2021-02-04 12:08:16', '2021-02-04 12:08:16', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (16, 0, '神经蛙', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM4Z6nX9KwiaJy2momCR0BLGnXF7ibI9WCJNTpqiaWDYS80624vRibsWr1muV2N8qM5wia0n5lSxOvttjNA/132', 'oUBUG5i0B3nUFejoqSlNwFlcJ_oc', NULL, 1, 0, 0, '2021-02-16 15:48:07', '2021-02-16 15:48:07', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (17, 1, '自渡.', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/jAiavgRK2sHGs29TfZOlfGibBEpkq5btJQcVib2OoOibDTrsbC3d1R2LEtyEN48Cx8pQBZE174k13ribJamUkrD1ctg/132', 'oUBUG5l9OT1HaIZmOKJciVVaiZaA', NULL, 1, 0, 0, '2021-02-17 13:11:54', '2021-02-17 13:11:54', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (18, 1, 'lxm', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKCMRO8bKSzryP9QD8DqOHyaVP8ibK41qpviaqNpTN3IDibiapjqLibibIZS7LrQTfiaNV6YNhYn8vzqcviaw/132', 'oEJcR0faCvzl8xF5fMKPoPBZQwB8', NULL, 1, 0, 0, '2021-02-20 16:47:34', '2021-02-20 16:47:34', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (19, 1, '林育挺', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/pHghIy3YR0f1pyWuENRiaqic03azQnbW6YtjyWrfl0bXZjF4J9UA5QPG9jXUe8BymtngqJ0zPwnS0VSPLIBBJEiaw/132', 'oUBUG5oCupLtC1SaawIk9uotF66U', NULL, 1, 0, 0, '2021-02-20 22:12:13', '2021-02-20 22:12:13', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (20, 1, '香蕉皮i', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Z1BicLpfe2ygKc91pm1LhKdLKUtFPdyn4lSkVkA5Pn5iaI5lT3h4M4dFAanxGKEMfPIgOCZjxjiaIHLuqq9Fn5E0Q/132', 'oUBUG5soM0AUAgP8PM8e0M2X5qxs', NULL, 1, 0, 0, '2021-02-24 12:09:26', '2021-02-24 12:09:26', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (23, 1, '蓝动', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/9cfWYQjJMKsplTQQLJqR3A75j9Hib44jHF0vIEJqfHC2ttfg0GCiaSzQbSQVVxrgicAJallo3eB2qsGyE1Z4RNYCQ/132', 'oUBUG5nOlD91HYvXRhCqKrMSWzUs', NULL, 1, 0, 0, '2021-03-23 17:35:03', '2021-03-23 17:35:03', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (24, 1, '路亚小生', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/tnib4ZCXWGOznmtyoHBL5BFicYZWICNyic0EyPWk70kr9IWzHSCVdIqFKN2o7BxyuYaDib0ogmfpuMTBgo3pOibPt9A/132', 'oUBUG5l8zS6fstLbQh4GNf81w438', NULL, 1, 0, 0, '2021-03-24 12:06:23', '2021-03-24 12:06:23', 1000000000, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (25, 1, 'Alan', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/QohQ9hnZnxF2mJOM1RywBPqToNVicDpeF8KdXrwmtYnRyoWaBHk0R25T1wxzleCJV3Un8iappa70yn8fJmgGAZnQ/132', 'oUBUG5kQ2YvWcX7OIYpw8owuWGqE', NULL, 1, 0, 0, '2021-03-29 15:57:24', '2021-03-29 15:57:24', 999380300, NULL, NULL, NULL, NULL);
INSERT INTO `ums_member` VALUES (40, 1, '秋城', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/ajNVdqHZLLDfyM5iaYFwhzQ1Xv9zyA3bXDV42niazQlibiajdXba0YK4yAFFWIMY7vwfI1ny8Ej8pm0pmp7OkC2afg/132', 'oUBUG5oPQnarJi7g2mXE_svcHVeU', NULL, 1, 0, 0, '2021-06-19 16:57:51', '2021-06-19 16:57:51', 1000000000, '张家口', '中国', 'zh_CN', '河北');
INSERT INTO `ums_member` VALUES (72, 0, 'Benji', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/WjvlGaEv9ajwHfpEGWd4KwjloXjCgvibMibPQiaL7I4ausEzp0rH0AHqKpJeERS2UHiaLSlftOYCj77nLEVNtXZqcQ/132', 'oBdKY4iltAU9HBHUBC-S8p3H1vv4', NULL, 1, 0, 0, '2022-03-13 04:23:33', '2022-03-13 04:23:33', 1000000000, '', '', 'zh_CN', '');
INSERT INTO `ums_member` VALUES (73, 0, '_六月流星', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/gZlE19RpbIuqmicAdL8wC7u26gx6LhRwsicc3icFGYA8TNvy6RJnyGUMTbWkyhg1lJ4yolnOVqCs6gI7Oiaby8lqSQ/132', 'oUBUG5rsrAjW173G25vgL2hq6AZk', NULL, 1, 0, 0, '2022-03-14 16:17:33', '2022-03-14 16:17:33', 1000000000, '', '', 'zh_CN', '');
INSERT INTO `ums_member` VALUES (74, 0, 'zhang', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIOca9icPByKxn3Z6AZAL3l0xfmOOqbRQVn1f2qoqtOYw3bJliawrTvu4F9Tg2aAHicASicXrW74zVUYA/132', 'oYCwr5IaYCS7web81wifWivPEMGw', NULL, 1, 0, 0, '2022-03-16 11:18:36', '2022-03-16 11:18:36', 1000000000, '', '', 'zh_CN', '');
INSERT INTO `ums_member` VALUES (75, 0, '德才-Edward', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/4DgXefgtM24MXSMPomsvuwCiav9v77bA5Ou8S74qrlAALfNPMEqD938jrNVybP5sJzfaKyqHrYcicbIwU8Xcc1YA/132', 'oRJIA43TMiAw76HRFEMli4BSvX6c', NULL, 1, 0, 0, '2022-04-06 18:14:07', '2022-04-06 18:14:07', 1000000000, '', '', 'zh_CN', '');
INSERT INTO `ums_member` VALUES (76, 0, '小乐有点笨', NULL, NULL, 'https://thirdwx.qlogo.cn/mmopen/vi_32/gTdG3Zs0OMqruJpGAKu9mZxo4K9WjEzmG759HiaBS5I71LVxktv2zyqNcKiaxeNF3JTSn1epfU3gUz8hZWdqhfPg/132', 'oTAHp5OexvW68nRkn5ISV0zKvEGE', NULL, 1, 0, 0, '2022-04-22 18:55:39', '2022-04-22 18:55:39', 1000000000, '', '', 'zh_CN', '');
-- ----------------------------
-- Table structure for ums_address
-- ----------------------------
DROP TABLE IF EXISTS `ums_address`;
CREATE TABLE `ums_address`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NULL DEFAULT NULL COMMENT '会员ID',
  `consignee_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `consignee_mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人联系方式',
  `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市',
  `area` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区',
  `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `zip_code` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮编',
  `defaulted` tinyint NULL DEFAULT NULL COMMENT '是否默认地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

INSERT INTO `ums_address` VALUES (1, 1, '郝先瑞', '18866668888', '上海', '上海市', '浦东新区', '111111', NULL, NULL, '2021-03-22 21:56:58', '2021-03-22 21:56:58');
-- 从 docs/sql/mysql8/mall_ums.sql 导入其他ums_*表...

-- ================================
-- 商品模块表 (mall_pms)
-- 表前缀: pms_*
-- ================================

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '品牌名称',
  `logo_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'LOGO图片',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品品牌表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_brand
-- ----------------------------
INSERT INTO `pms_brand` VALUES (1, '有来', 'http://a.youlai.tech:9000/default/5409e3deb5a14b8fa8cb4275dee0e25d.png', 1, '2021-07-11 19:56:58', '2021-07-11 20:02:54');
INSERT INTO `pms_brand` VALUES (10, '小米', 'http://a.youlai.tech:9000/default/6a5a606fc60742919149a7861bf26cd5.jpg', 2, '2022-03-05 16:12:16', '2022-03-05 16:12:16');
INSERT INTO `pms_brand` VALUES (11, '华硕', 'http://a.youlai.tech:9000/default/f18083f95e104a0bae3c587dee3bb2ed.png', 3, '2022-03-05 16:12:16', '2022-03-05 16:12:16');
INSERT INTO `pms_brand` VALUES (20, '华为', 'https://oss.youlai.tech/default/ff61bd639b23491d8f2aa85d09fcf788.jpg', 1, '2022-05-06 23:08:33', '2022-05-06 23:08:33');
INSERT INTO `pms_brand` VALUES (33, '惠普', 'https://oss.youlai.tech/default/4cf579add9544c6eaafb41ce1131559e.gif', 1, '2022-07-07 00:12:16', '2022-07-07 00:12:16');

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品分类名称',
  `parent_id` bigint NOT NULL COMMENT '父级ID',
  `level` int NULL DEFAULT NULL COMMENT '层级',
  `icon_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图标地址',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '显示状态:( 0:隐藏 1:显示)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category` VALUES (3, '手机配件', 0, 1, NULL, 2, 1, NULL, '2022-07-07 22:56:53');
INSERT INTO `pms_category` VALUES (4, '智能手机', 3, 2, NULL, 1, 1, NULL, NULL);
INSERT INTO `pms_category` VALUES (5, '5g手机', 4, 3, 'https://oss.youlai.tech/default/6ffb37110ac2434a9882b9e8968b2887.jpg', 1, 1, NULL, '2022-07-08 00:28:38');
INSERT INTO `pms_category` VALUES (6, '电脑办公', 0, 1, 'https://www.youlai.tech/files/default/776c21c1a71848069093033f461c5f4a.jpg', 1, 1, '2022-02-25 11:22:44', '2022-07-07 22:56:38');
INSERT INTO `pms_category` VALUES (97, '笔记本电脑', 6, 2, NULL, 100, 1, '2022-07-08 00:10:27', '2022-07-08 00:10:27');
INSERT INTO `pms_category` VALUES (99, '三星轻薄本', 97, 3, 'https://oss.youlai.tech/default/2f849b96ebb54ab3a94b1b90137f1b4d.png', 100, 1, '2022-07-08 00:14:03', '2022-07-08 00:26:52');
INSERT INTO `pms_category` VALUES (100, '全能本', 97, 3, 'https://oss.youlai.tech/default/37cc080ec61b4ce7b0583b002568ebaa.png', 100, 1, '2022-07-08 00:14:10', '2022-07-08 00:27:01');
INSERT INTO `pms_category` VALUES (101, '游戏本', 97, 3, 'https://oss.youlai.tech/default/5c1a2d5427534b48bc382caa55197f11.png', 100, 1, '2022-07-08 00:14:18', '2022-07-08 00:27:11');

-- ----------------------------
-- Table structure for pms_category_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_attribute`;
CREATE TABLE `pms_category_attribute`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性名称',
  `type` tinyint NOT NULL COMMENT '类型(1:规格;2:属性;)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_attr_pms_category`(`category_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 183 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品属性表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category_attribute
-- ----------------------------
INSERT INTO `pms_category_attribute` VALUES (34, 5, '颜色', 1, '2021-07-11 17:57:06', '2022-07-01 00:08:19');
INSERT INTO `pms_category_attribute` VALUES (35, 5, '规格', 1, '2021-07-11 18:00:06', '2022-07-01 00:08:19');
INSERT INTO `pms_category_attribute` VALUES (36, 5, '上市时间', 2, '2021-07-11 18:00:08', '2022-06-01 17:41:05');

-- ----------------------------
-- Table structure for pms_category_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_brand`;
CREATE TABLE `pms_category_brand`  (
  `category_id` bigint NOT NULL,
  `brand_id` bigint NOT NULL,
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category_brand
-- ----------------------------

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sku_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品编码',
  `spu_id` bigint NOT NULL COMMENT 'SPU ID',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `spec_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品规格值，以英文逗号(,)分割',
  `price` bigint NULL DEFAULT NULL COMMENT '商品价格(单位：分)',
  `stock` int UNSIGNED NULL DEFAULT NULL COMMENT '库存数量',
  `locked_stock` int NULL DEFAULT NULL COMMENT '库存锁定数量',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_sku_pms_spu`(`spu_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 755 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品库存表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_sku
-- ----------------------------
INSERT INTO `pms_sku` VALUES (1, 'sn001', 1, '黑 6+128g', '1_3', 399900, 990, 150, 'https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png', '2021-08-08 00:43:26', '2022-07-03 14:16:16');
INSERT INTO `pms_sku` VALUES (2, 'sn002', 1, '黑 8+256g', '1_4', 499900, 999, 0, 'https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png', '2021-08-08 00:43:26', '2022-07-03 14:16:16');
INSERT INTO `pms_sku` VALUES (3, 'sn003', 1, '蓝 6+128g', '216_3', 399900, 999, 0, 'https://www.youlai.tech/files/default/835d73a337964b9b97e5c7c90acc8cb2.png', '2022-03-05 09:25:53', '2022-07-03 14:16:16');
INSERT INTO `pms_sku` VALUES (4, 'sn004', 1, '蓝 8+256g', '216_4', 499900, 999, 0, 'https://www.youlai.tech/files/default/835d73a337964b9b97e5c7c90acc8cb2.png', '2022-03-05 09:25:53', '2022-07-03 14:16:16');
INSERT INTO `pms_sku` VALUES (5, '10000001', 2, '魔幻青 RTX3060/i7-12700H/165Hz 2.5K屏', '256_258', 1025000, 998, 0, 'http://a.youlai.tech:9000/default/8815c9a46fcc4b1ea952623406750da5.jpg', '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_sku` VALUES (6, '10000002', 2, '魔幻青 RTX3050tTi/12代i5/144Hz高色域屏', '256_259', 925000, 999, 0, 'http://a.youlai.tech:9000/default/8815c9a46fcc4b1ea952623406750da5.jpg', '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_sku` VALUES (7, '10000003', 2, '日蚀灰 RTX3060/i7-12700H/165Hz 2.5K屏', '257_258', 1025000, 999, 0, 'http://a.youlai.tech:9000/default/3210cd1ffb6c4346b743a10855d3cb37.jpg', '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_sku` VALUES (8, '10000004', 2, '日蚀灰 RTX3050tTi/12代i5/144Hz高色域屏', '257_259', 925000, 999, 0, 'http://a.youlai.tech:9000/default/3210cd1ffb6c4346b743a10855d3cb37.jpg', '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_sku` VALUES (9, '111', 3, '16g 512g 【2022款】锐龙六核R5-6600U/核芯显卡/100%sRGB高色域', '841_843_845', 589900, 992, 1, 'https://oss.youlai.tech/youlai-boot/2023/06/08/78b8efecb753426f81e5dcfcd175495f.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_sku` VALUES (10, '112', 3, '16g 512g 【2022款】锐龙八核R7-6800U/核芯显卡/100%sRGB高色域', '841_843_846', 629900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/93cbc9dc6fe144f5a59793a6248479a0.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_sku` VALUES (11, '113', 3, '16g 1t 【2022款】锐龙六核R5-6600U/核芯显卡/100%sRGB高色域', '841_844_845', 639900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/78b8efecb753426f81e5dcfcd175495f.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_sku` VALUES (12, '114', 3, '16g 1t 【2022款】锐龙八核R7-6800U/核芯显卡/100%sRGB高色域', '841_844_846', 639900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/93cbc9dc6fe144f5a59793a6248479a0.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_sku` VALUES (13, '115', 3, '32g 512g 【2022款】锐龙六核R5-6600U/核芯显卡/100%sRGB高色域', '842_843_845', 589900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/78b8efecb753426f81e5dcfcd175495f.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_sku` VALUES (14, '116', 3, '32g 512g 【2022款】锐龙八核R7-6800U/核芯显卡/100%sRGB高色域', '842_843_846', 629900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/93cbc9dc6fe144f5a59793a6248479a0.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_sku` VALUES (15, '117', 3, '32g 1t 【2022款】锐龙六核R5-6600U/核芯显卡/100%sRGB高色域', '842_844_845', 639900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/78b8efecb753426f81e5dcfcd175495f.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_sku` VALUES (16, '118', 3, '32g 1t 【2022款】锐龙八核R7-6800U/核芯显卡/100%sRGB高色域', '842_844_846', 639900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/93cbc9dc6fe144f5a59793a6248479a0.jpg', '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_sku` VALUES (17, 'sn001', 4, '黑 6+128g', '1_3', 399900, 999, 0, 'https://oss.youlai.tech/youlai-boot/2023/06/08/6b83dd33eaa248ed8e11cff0003287ee.jpg', '2021-08-08 00:43:26', '2022-07-03 14:16:16');

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `category_id` bigint NOT NULL COMMENT '商品类型ID',
  `brand_id` bigint NULL DEFAULT NULL COMMENT '商品品牌ID',
  `origin_price` bigint NOT NULL COMMENT '原价【起】',
  `price` bigint NOT NULL COMMENT '现价【起】',
  `sales` int NULL DEFAULT 0 COMMENT '销量',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品主图',
  `album` json NULL COMMENT '商品图册',
  `unit` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '单位',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品简介',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品详情',
  `status` tinyint NULL DEFAULT 1 COMMENT '商品状态(0:下架 1:上架)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_spu_pms_brand`(`brand_id` ASC) USING BTREE,
  INDEX `fk_pms_spu_pms_category`(`category_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 288 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu
-- ----------------------------
INSERT INTO `pms_spu` VALUES (1, 'Galaxy Z Fold5', 5, 10, 599900, 599900, 1, 'https://shop-image.samsung.com.cn/productv5/img/2023/07/26/64c0e30fe4b08db29258c50c.png', '[\"https://shop-image.samsung.com.cn/productv5/img/2023/07/26/64c0e30fe4b08db29258c50c.png\"]', '台', '好快,好稳,\n好一次强上加强。\n高通全新一代芯片赋能，速度大幅提升。\n三大专业主摄影像加持，能力全面进化。\n大师级设计理念新诠释，质感简而不凡。\n斩获十五项纪录旗舰屏，感官万般出众。', '<p><img src=\"https://shop-image.samsung.com.cn/productv5/img/2023/11/03/65449d3ee4b08db20823bcbe.jpg\" alt=\"\" data-href=\"\" style=\"width: 449.00px;height: 449.00px;\"/></p>', 1, NULL, '2022-07-03 14:16:16');
INSERT INTO `pms_spu` VALUES (2, '华硕天选3', 101, 11, 1145000, 929900, 0, 'https://www.youlai.tech/files/default/d97457b3fd7d4aef8846da96fe032bf8.jpg', '[\"https://www.youlai.tech/files/default/3edd01c723ff456384cea9bd3c9b19e7.jpg\", \"https://www.youlai.tech/files/default/a6681c18fc294ee49efb8e121b8e943f.jpg\", \"https://www.youlai.tech/files/default/97458ae9ea734bc498724660abb1c6cd.jpg\", \"https://www.youlai.tech/files/default/501b0e6dcb3f4d69b7e40e90b3d3ac32.jpg\"]', NULL, '中国台湾华硕电脑股份有限公司 [1]  是当前全球第一大主板生产商、全球第三大显卡生产商，同时也是全球领先的3C解决方案提供商之一，致力于为个人和企业用户提供最具创新价值的产品及应用方案。华硕的产品线完整覆盖至笔记本电脑、主板、显卡、服务器、光存储、有线/无线网络通讯产品、LCD、掌上电脑、智能手机等全线3C产品。其中显卡和主板以及笔记本电脑三大产品已经成为华硕的主要竞争实力。', '<p><img src=\"http://a.youlai.tech:9000/default/5e4fb81b04244a74aacaabb4685101e2.png\" alt=\"\" data-href=\"\" style=\"\"/><img src=\"http://a.youlai.tech:9000/default/0744c5b6d77b47b294eb111ee992c62b.png\" alt=\"\" data-href=\"\" style=\"\"/></p>', 1, '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_spu` VALUES (3, '惠普战X ', 99, 33, 639900, 629900, 0, 'https://oss.youlai.tech/default/e59859e0effb4b66a0f7380ff5369d66.jpg', '[\"https://oss.youlai.tech/default/de9c5625e35b4c0aa9888c48d4def446.jpg\"]', NULL, '【2022新款】HP/惠普战X 16英寸锐龙新款6000系列R5六核/R7八核高性能学生家用轻薄办公商用笔记本电脑\n六核/八核处理器，高性能集成显卡', '<p><img src=\"https://oss.youlai.tech/default/d645a6f642794e2183cc44d340613b9d.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>', 1, '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_spu` VALUES (4, '小米13', 5, 10, 599900, 599900, 1, 'https://oss.youlai.tech/youlai-boot/2023/06/08/6b83dd33eaa248ed8e11cff0003287ee.jpg', '[\"https://oss.youlai.tech/youlai-boot/2023/06/08/6b83dd33eaa248ed8e11cff0003287ee.jpg\"]', '台', '好快,好稳,\n好一次强上加强。\n高通全新一代芯片赋能，速度大幅提升。\n三大专业主摄影像加持，能力全面进化。\n大师级设计理念新诠释，质感简而不凡。\n斩获十五项纪录旗舰屏，感官万般出众。', '<p><img src=\"http://a.youlai.tech:9000/default/1a69357664c24962ac23953905c3c38f.png\" alt=\"\" data-href=\"\" style=\"width: 449.00px;height: 449.00px;\"/></p>', 1, NULL, '2022-07-03 14:16:16');

-- ----------------------------
-- Table structure for pms_spu_attribute
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_attribute`;
CREATE TABLE `pms_spu_attribute`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `spu_id` bigint NOT NULL COMMENT '产品ID',
  `attribute_id` bigint NULL DEFAULT NULL COMMENT '属性ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性名称',
  `value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性值',
  `type` tinyint NOT NULL COMMENT '类型(1:规格;2:属性;)',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '规格图片',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_pms_spu_attribute_pms_attr`(`name` ASC) USING BTREE,
  INDEX `fk_pms_spu_attribute_pms_spu`(`spu_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 847 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品属性/规格表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_attribute
-- ----------------------------
INSERT INTO `pms_spu_attribute` VALUES (1, 1, 34, '颜色', '黑', 1, 'https://www.youlai.tech/files/default/c25b39470474494485633c49101a0f5d.png', NULL, '2022-07-03 14:16:16');
INSERT INTO `pms_spu_attribute` VALUES (3, 1, 35, '规格', '6+128g', 1, NULL, NULL, '2022-07-03 14:16:16');
INSERT INTO `pms_spu_attribute` VALUES (4, 1, 35, '规格', '8+256g', 1, NULL, NULL, '2022-07-03 14:16:16');
INSERT INTO `pms_spu_attribute` VALUES (5, 1, 36, '上市时间', '2021-07-17', 2, NULL, NULL, '2022-07-03 14:16:16');
INSERT INTO `pms_spu_attribute` VALUES (216, 1, NULL, '颜色', '蓝', 1, 'https://www.youlai.tech/files/default/835d73a337964b9b97e5c7c90acc8cb2.png', '2022-03-05 09:25:53', '2022-07-03 14:16:16');
INSERT INTO `pms_spu_attribute` VALUES (251, 2, NULL, '上市时间', '2022/3/11', 2, NULL, '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_spu_attribute` VALUES (252, 2, NULL, '商品名称', '华硕天选3', 2, NULL, '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_spu_attribute` VALUES (253, 2, NULL, '商品编号', '100032610338', 2, NULL, '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_spu_attribute` VALUES (254, 2, NULL, '商品毛重', '4.05kg', 2, NULL, '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_spu_attribute` VALUES (255, 2, NULL, '系统', 'windows11', 2, NULL, '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_spu_attribute` VALUES (256, 2, NULL, '颜色', '魔幻青', 1, 'http://a.youlai.tech:9000/default/8815c9a46fcc4b1ea952623406750da5.jpg', '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_spu_attribute` VALUES (257, 2, NULL, '颜色', '日蚀灰', 1, 'http://a.youlai.tech:9000/default/3210cd1ffb6c4346b743a10855d3cb37.jpg', '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_spu_attribute` VALUES (258, 2, NULL, '规格', 'RTX3060/i7-12700H/165Hz 2.5K屏', 1, NULL, '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_spu_attribute` VALUES (259, 2, NULL, '规格', 'RTX3050tTi/12代i5/144Hz高色域屏', 1, NULL, '2022-03-11 14:39:21', '2022-07-08 00:29:56');
INSERT INTO `pms_spu_attribute` VALUES (838, 3, NULL, '内存', '16g 32g', 2, NULL, '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_spu_attribute` VALUES (839, 3, NULL, '重量', '1.5kg(含)-2kg(不含)', 2, NULL, '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_spu_attribute` VALUES (840, 3, NULL, '显卡类型', '核芯显卡', 2, NULL, '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_spu_attribute` VALUES (841, 3, NULL, '内存容量', '16g', 1, NULL, '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_spu_attribute` VALUES (842, 3, NULL, '内存容量', '32g', 1, NULL, '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_spu_attribute` VALUES (843, 3, NULL, '硬盘容量', '512g', 1, NULL, '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_spu_attribute` VALUES (844, 3, NULL, '硬盘容量', '1t', 1, NULL, '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_spu_attribute` VALUES (845, 3, NULL, '套餐类型', '【2022款】锐龙六核R5-6600U/核芯显卡/100%sRGB高色域', 1, NULL, '2022-07-07 00:22:13', '2022-07-08 00:29:41');
INSERT INTO `pms_spu_attribute` VALUES (846, 3, NULL, '套餐类型', '【2022款】锐龙八核R7-6800U/核芯显卡/100%sRGB高色域', 1, NULL, '2022-07-07 00:22:13', '2022-07-08 00:29:41');
-- ================================
-- 订单模块表 (mall_oms)
-- 表前缀: oms_*
-- ================================

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `order_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '订单号',
                              `total_amount` bigint NOT NULL DEFAULT 0 COMMENT '订单总额（分）',
                              `total_quantity` int NOT NULL DEFAULT 0 COMMENT '商品总数',
                              `source` tinyint NULL DEFAULT NULL COMMENT '订单来源(1:APP；2:网页)',
                              `status` int NOT NULL DEFAULT 101 COMMENT '订单状态：\r\n101->待付款；\r\n102->用户取消；\r\n103->系统取消；\r\n201->已付款；\r\n202->申请退款；\r\n203->已退款；\r\n301->待发货；\r\n401->已发货；\r\n501->用户收货；\r\n502->系统收货；\r\n901->已完成；',
                              `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '订单备注',
                              `member_id` bigint NOT NULL DEFAULT 0 COMMENT '会员id',
                              `coupon_id` bigint NOT NULL DEFAULT 0 COMMENT '使用的优惠券',
                              `coupon_amount` bigint NOT NULL DEFAULT 0 COMMENT '优惠券抵扣金额（分）',
                              `freight_amount` bigint NOT NULL DEFAULT 0 COMMENT '运费金额（分）',
                              `payment_amount` bigint NOT NULL DEFAULT 0 COMMENT '应付总额（分）',
                              `payment_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
                              `payment_method` tinyint NULL DEFAULT NULL COMMENT '支付方式(1：微信JSAPI；2：支付宝；3：余额；4：微信APP)',
                              `out_trade_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信支付等第三方支付平台的商户订单号',
                              `transaction_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信支付订单号',
                              `out_refund_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商户退款单号',
                              `refund_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信退款单号',
                              `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
                              `receive_time` datetime NULL DEFAULT NULL COMMENT '确认收货时间',
                              `comment_time` datetime NULL DEFAULT NULL COMMENT '评价时间',
                              `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
                              `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                              `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `index_order_sn`(`order_sn` ASC) USING BTREE COMMENT '订单号唯一索引',
                              UNIQUE INDEX `index_otn`(`out_trade_no` ASC) USING BTREE COMMENT '商户订单号唯一索引',
                              UNIQUE INDEX `index_ti`(`transaction_id` ASC) USING BTREE COMMENT '商户支付单号唯一索引',
                              UNIQUE INDEX `index_orn`(`out_refund_no` ASC) USING BTREE COMMENT '商户退款单号唯一索引',
                              UNIQUE INDEX `index_ri`(`refund_id` ASC) USING BTREE COMMENT '退款单号唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1351548262424822283 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情表' ROW_FORMAT = DYNAMIC;


DROP TABLE IF EXISTS `oms_order_delivery`;
CREATE TABLE `oms_order_delivery`  (
                                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                       `order_id` bigint NOT NULL COMMENT '订单id',
                                       `delivery_company` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '物流公司(配送方式)',
                                       `delivery_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '物流单号',
                                       `receiver_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '收货人姓名',
                                       `receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '收货人电话',
                                       `receiver_post_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '收货人邮编',
                                       `receiver_province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '省份/直辖市',
                                       `receiver_city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '城市',
                                       `receiver_region` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '区',
                                       `receiver_detail_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '详细地址',
                                       `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
                                       `delivery_status` tinyint NULL DEFAULT 0 COMMENT '物流状态【0->运输中；1->已收货】',
                                       `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
                                       `receive_time` datetime NULL DEFAULT NULL COMMENT '确认收货时间',
                                       `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
                                       `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                       `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单物流记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                   `order_id` bigint NOT NULL COMMENT '订单ID',
                                   `spu_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
                                   `sku_id` bigint NOT NULL DEFAULT 0 COMMENT '商品ID',
                                   `sku_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品编码',
                                   `sku_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '规格名称',
                                   `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '商品图片',
                                   `price` bigint NOT NULL DEFAULT 0 COMMENT '商品单价(单位：分)',
                                   `quantity` int NULL DEFAULT NULL COMMENT '商品数量',
                                   `total_amount` bigint NOT NULL DEFAULT 0 COMMENT '商品总价(单位：分)',
                                   `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(1:已删除；0:正常)',
                                   `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                   `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   INDEX `index_order_id`(`order_id` ASC) USING BTREE COMMENT '订单id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 551 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单商品信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_log
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_log`;
CREATE TABLE `oms_order_log`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `order_id` bigint NOT NULL COMMENT '订单id',
                                  `user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
                                  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '操作详情',
                                  `order_status` int NULL DEFAULT NULL COMMENT '操作时订单状态',
                                  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
                                  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
                                  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单操作历史记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_pay
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_pay`;
CREATE TABLE `oms_order_pay`  (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `order_id` bigint NOT NULL COMMENT '订单id',
                                  `pay_sn` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '支付流水号',
                                  `pay_amount` bigint NOT NULL DEFAULT 0 COMMENT '应付总额(分)',
                                  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
                                  `pay_type` tinyint NULL DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
                                  `pay_status` tinyint NULL DEFAULT NULL COMMENT '支付状态',
                                  `confirm_time` datetime NULL DEFAULT NULL COMMENT '确认时间',
                                  `callback_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '回调内容',
                                  `callback_time` datetime NULL DEFAULT NULL COMMENT '回调时间',
                                  `pay_subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '交易内容',
                                  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
                                  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '支付信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
                                      `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                      `flash_order_overtime` int NULL DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
                                      `normal_order_overtime` int NULL DEFAULT NULL COMMENT '正常订单超时时间(分)',
                                      `confirm_overtime` int NULL DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
                                      `finish_overtime` int NULL DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
                                      `comment_overtime` int NULL DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
                                      `member_level` tinyint NULL DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
                                      `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除【0->正常；1->已删除】',
                                      `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                      `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单配置信息' ROW_FORMAT = DYNAMIC;
-- ================================
-- 营销模块表 (mall_sms)
-- 表前缀: sms_*
-- ================================

-- ----------------------------
-- Table structure for sms_advert
-- ----------------------------
DROP TABLE IF EXISTS `sms_advert`;
CREATE TABLE `sms_advert`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '广告标题',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `status` tinyint(1) NOT NULL COMMENT '状态(1:开启；0:关闭)',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `redirect_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '跳转链接',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间(新增有值)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '广告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_advert
-- ----------------------------
INSERT INTO `sms_advert` VALUES (35, 'banner3', 'https://oss.youlai.tech/default/14d9b8fb471a43429334080124a9c65d.png', '2022-07-04 00:00:00', '2022-10-07 00:00:00', 1, 3, NULL, NULL, '2022-07-04 00:08:10', '2022-07-04 00:08:10');
INSERT INTO `sms_advert` VALUES (36, 'banner2', 'https://oss.youlai.tech/default/2022/07/17/34f2b48e346a4726bbe5352a18d493fc.jpg', '2022-07-04 00:00:00', '2022-10-07 00:00:00', 1, 2, NULL, NULL, '2022-07-04 00:08:10', '2022-07-04 00:08:10');
INSERT INTO `sms_advert` VALUES (47, 'banner1', 'https://oss.youlai.tech/default/2022/07/17/91765310d3d845999a8a55600ad9ccb3.png', '2022-07-04 00:00:00', '2022-10-07 00:00:00', 1, 1, NULL, NULL, '2022-07-04 00:08:10', '2022-07-04 00:08:10');

-- ----------------------------
-- Table structure for sms_coupon
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon`;
CREATE TABLE `sms_coupon`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优惠券名称',
  `type` tinyint(0) NOT NULL DEFAULT 1 COMMENT '优惠券类型(1-满减券;2-直减券;3-折扣券)',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '优惠券码',
  `platform` int(0) NULL DEFAULT NULL COMMENT '使用平台(0-全平台;1-APP;2-PC)',
  `face_value_type` tinyint(0) NULL DEFAULT NULL COMMENT '优惠券面值类型',
  `face_value` bigint(0) NULL DEFAULT NULL COMMENT '优惠券面值(分)',
  `discount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣',
  `min_point` bigint(0) NULL DEFAULT NULL COMMENT '使用门槛(0:无门槛)',
  `per_limit` int(0) NULL DEFAULT 1 COMMENT '每人限领张数(-1-无限制)',
  `validity_period_type` tinyint(0) NULL DEFAULT NULL COMMENT '有效期类型(1:自领取时起有效天数;2:有效起止时间)',
  `validity_days` int(0) NULL DEFAULT 1 COMMENT '有效期天数',
  `validity_begin_time` datetime(0) NULL DEFAULT NULL COMMENT '有效期起始时间',
  `validity_end_time` datetime(0) NULL DEFAULT NULL COMMENT '有效期截止时间',
  `application_scope` tinyint(0) NULL DEFAULT NULL COMMENT '应用范围(0-全场通用;1-指定商品分类;2-指定商品)',
  `circulation` int(0) NULL DEFAULT 1 COMMENT '发行量(-1-无限制)',
  `received_count` int(0) NULL DEFAULT 0 COMMENT '已领取的优惠券数量(统计)',
  `used_count` int(0) NULL DEFAULT 0 COMMENT '已使用的优惠券数量(统计)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(0) NULL DEFAULT 1 COMMENT '逻辑删除标识(0-正常;1-删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '优惠券表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon
-- ----------------------------
INSERT INTO `sms_coupon` VALUES (1, '新人无门槛优惠券,立减20元', 1, '123456', 1, 2, 100000000000000, 0.80, 10000000000000, 1, 1, 7, '2021-03-17 21:10:35', '2025-03-17 21:10:38', 2, 1, 0, 0, NULL, '2022-11-08 16:29:26', '2021-03-17 21:10:56', 0);
INSERT INTO `sms_coupon` VALUES (2, '满减优惠券', 1, '50', 2, NULL, 12, 0.00, 10000000, 1, 2, 7, NULL, NULL, 2, 100, 0, 0, NULL, '2022-07-16 01:28:42', '2021-03-17 21:19:46', 0);

-- ----------------------------
-- Table structure for sms_coupon_history
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_history`;
CREATE TABLE `sms_coupon_history`  (
  `id` bigint(0) NOT NULL,
  `coupon_id` bigint(0) NULL DEFAULT NULL COMMENT '优惠券ID',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '会员ID',
  `member_nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员昵称',
  `coupon_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优惠券码',
  `get_type` tinyint(0) NULL DEFAULT NULL COMMENT '获取类型(1：后台增删；2：主动领取)',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态(0：未使用；1：已使用；2：已过期)',
  `use_time` datetime(0) NULL DEFAULT NULL COMMENT '使用时间',
  `order_id` bigint(0) NULL DEFAULT NULL COMMENT '订单ID',
  `order_sn` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon_history
-- ----------------------------

-- ----------------------------
-- Table structure for sms_coupon_spu
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_spu`;
CREATE TABLE `sms_coupon_spu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `coupon_id` bigint(0) NOT NULL COMMENT '优惠券ID',
  `spu_id` bigint(0) NOT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1548148841429663756 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_coupon_spu
-- ----------------------------
INSERT INTO `sms_coupon_spu` VALUES (1548148841429663747, 8, 289);
INSERT INTO `sms_coupon_spu` VALUES (1548148841429663748, 8, 2);
INSERT INTO `sms_coupon_spu` VALUES (1548148841429663749, 10, 2);
INSERT INTO `sms_coupon_spu` VALUES (1548148841429663750, 12, 289);
INSERT INTO `sms_coupon_spu` VALUES (1548148841429663751, 12, 2);
INSERT INTO `sms_coupon_spu` VALUES (1548148841429663752, 12, 287);
INSERT INTO `sms_coupon_spu` VALUES (1548148841429663753, 15, 289);
INSERT INTO `sms_coupon_spu` VALUES (1548148841429663754, 15, 2);
INSERT INTO `sms_coupon_spu` VALUES (1548148841429663755, 15, 287);

-- ----------------------------
-- Table structure for sms_coupon_spu_category
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_spu_category`;
CREATE TABLE `sms_coupon_spu_category`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `coupon_id` bigint(0) NOT NULL COMMENT '优惠券ID',
  `category_id` bigint(0) NOT NULL COMMENT '商品分类ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;



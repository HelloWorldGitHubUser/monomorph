-- =========================================================================
-- ZLT 微服务平台 - 单体版数据库初始化脚本
-- 合并自: user-center, oauth-center, file-center, logger-center
-- =========================================================================

CREATE DATABASE IF NOT EXISTS `zlt_monolith` DEFAULT CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;
USE `zlt_monolith`;

-- =========================================================================
-- 用户中心相关表 (原 user-center)
-- =========================================================================

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `head_img_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` tinyint(1) NULL DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT 1,
  `type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `open_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `creator_id` int(11) COMMENT '创建人id',
  PRIMARY KEY (`id`),
  KEY `idx_username` (`username`),
  KEY `idx_mobile` (`mobile`),
  KEY `idx_open_id` (`open_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user (密码: 123456)
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$TJkwVdlpbHKnV45.nBxbgeFHmQRmyWlshg94lFu2rKxVtT2OMniDO', '管理员', 'http://pkqtmn0p1.bkt.clouddn.com/头像.png', '18888888888', 0, 1, 'BACKEND', '2017-11-17 16:56:59', '2019-01-08 17:05:47', 'ENGJ', '123', 0, 1);
INSERT INTO `sys_user` VALUES (2, 'user', '$2a$10$OhfZv4VQJiqMEukpf1qXA.V7UMiHjr86g6lJqPvKUoHwrPk35steG', '体验用户', NULL, '18888888887', 1, 1, 'BACKEND', '2017-11-17 16:56:59', NULL, 'ENGJ', NULL, 0, 1);
INSERT INTO `sys_user` VALUES (3, 'test', '$2a$10$RD18sHNphJMmcuLuUX/Np.IV/7Ngbjd3Jtj3maFLpwaA6KaHVqPtq', '测试账户', NULL, '13851539156', 0, 1, 'BACKEND', '2017-11-17 16:56:59', '2018-09-07 03:27:40', 'ENGJ', NULL, 0, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色code',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `data_scope` varchar(32) DEFAULT 'ALL' COMMENT '数据权限范围配置：ALL/全部权限，CREATOR/创建者权限',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `tenant_id` varchar(32) DEFAULT '' COMMENT '租户字段',
  `creator_id` int(11) COMMENT '创建人id',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ADMIN', '管理员', 'ALL', '2017-11-17 16:56:59', '2018-09-19 09:39:10', 'webApp', 1);
INSERT INTO `sys_role` VALUES (2, 'USER', '普通用户', 'CREATOR', '2018-09-17 10:15:51', '2018-11-15 01:49:14', 'webApp', 1);
INSERT INTO `sys_role` VALUES (3, 'TEST', '测试角色', 'ALL', '2018-11-15 01:49:19', '2018-11-15 01:49:19', 'webApp', 1);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, 1);
INSERT INTO `sys_role_user` VALUES (2, 2);
INSERT INTO `sys_role_user` VALUES (3, 3);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `css` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `create_time` datetime(0) NULL,
  `update_time` datetime(0) NULL,
  `type` tinyint(1) NOT NULL COMMENT '1-菜单 2-权限',
  `hidden` tinyint(1) NOT NULL DEFAULT 0,
  `tenant_id` varchar(32) DEFAULT '' COMMENT '租户字段',
  `creator_id` int(11) COMMENT '创建人id',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu (简化版菜单)
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, -1, '系统管理', 'javascript:;', '', NULL, 'layui-icon-set', 1, '2017-11-17 16:56:59', '2018-12-13 15:02:49', 1, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', '#!user', 'system/user.html', NULL, 'layui-icon-friends', 1, '2017-11-17 16:56:59', '2018-09-19 11:26:14', 1, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', '#!role', 'system/role.html', NULL, 'layui-icon-user', 2, '2017-11-17 16:56:59', '2019-01-14 15:34:40', 1, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', '#!menus', 'system/menus.html', NULL, 'layui-icon-menu-fill', 3, '2017-11-17 16:56:59', '2018-09-03 02:23:47', 1, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (5, 1, '应用管理', '#!app', 'attestation/app.html', NULL, 'layui-icon-link', 4, '2017-11-17 16:56:59', '2019-01-14 15:35:15', 1, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (6, 1, 'Token管理', '#!tokens', 'system/tokens.html', NULL, 'layui-icon-unlink', 5, '2019-07-11 16:56:59', '2019-07-11 16:56:59', 1, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (7, 1, '我的信息', '#!myInfo', 'system/myInfo.html', NULL, 'layui-icon-login-qq', 10, '2017-11-17 16:56:59', '2018-09-02 06:12:24', 1, 0, 'webApp', 1);

INSERT INTO `sys_menu` VALUES (10, -1, '文件管理', 'javascript:;', '', NULL, 'layui-icon-file', 2, '2018-08-25 10:41:58', '2019-01-23 14:01:58', 1, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (11, 10, '文件中心', '#!files', 'files/files.html', NULL, 'layui-icon-file', 1, '2017-11-17 16:56:59', '2019-01-17 20:18:44', 1, 0, 'webApp', 1);

INSERT INTO `sys_menu` VALUES (20, -1, '系统监控', 'javascript:;', '', NULL, 'layui-icon-chart-screen', 3, '2019-01-10 18:35:05', '2019-01-10 18:35:05', 1, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (21, 20, '系统日志', '#!sysLog', 'log/sysLog.html', NULL, 'layui-icon-file-b', 1, '2019-01-10 18:35:55', '2019-01-12 00:27:20', 1, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (22, 20, '审计日志', '#!auditLog', 'log/auditLog.html', NULL, 'layui-icon-file-b', 2, '2020-02-04 12:00:27', '2020-02-04 15:32:31', 1, 0, 'webApp', 1);

-- 权限菜单
INSERT INTO `sys_menu` VALUES (50, 2, '用户列表', '/users', 'user-list', 'GET', null, 1, '2019-07-29 16:56:59', '2019-07-29 16:56:59', 2, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (51, 2, '用户添加', '/users/saveOrUpdate', 'user-btn-add', 'POST', null, 2, '2019-07-29 16:56:59', '2019-07-29 16:56:59', 2, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (52, 2, '用户删除', '/users/*', 'user-btn-delete', 'DELETE', null, 3, '2019-07-29 16:56:59', '2019-07-29 16:56:59', 2, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (53, 3, '角色列表', '/roles', 'role-list', 'GET', null, 1, '2019-07-29 16:56:59', '2019-07-29 16:56:59', 2, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (54, 3, '角色添加', '/roles', 'role-btn-add', 'POST', null, 2, '2019-07-29 16:56:59', '2019-07-29 16:56:59', 2, 0, 'webApp', 1);
INSERT INTO `sys_menu` VALUES (55, 4, '菜单列表', '/menus', 'menu-list', 'GET', null, 1, '2019-07-29 16:56:59', '2019-07-29 16:56:59', 2, 0, 'webApp', 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu (管理员拥有所有权限)
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 20);
INSERT INTO `sys_role_menu` VALUES (1, 21);
INSERT INTO `sys_role_menu` VALUES (1, 22);
INSERT INTO `sys_role_menu` VALUES (1, 50);
INSERT INTO `sys_role_menu` VALUES (1, 51);
INSERT INTO `sys_role_menu` VALUES (1, 52);
INSERT INTO `sys_role_menu` VALUES (1, 53);
INSERT INTO `sys_role_menu` VALUES (1, 54);
INSERT INTO `sys_role_menu` VALUES (1, 55);
-- 普通用户权限
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 11);

-- =========================================================================
-- OAuth2 认证相关表 (原 oauth-center)
-- =========================================================================

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用标识',
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源限定串(逗号分割)',
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用密钥(bcrypt加密)',
  `client_secret_str` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用密钥(明文)',
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '范围',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'OAuth授权方式',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回调地址',
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限',
  `access_token_validity` int(11) NULL DEFAULT NULL COMMENT 'access_token有效期(秒)',
  `refresh_token_validity` int(11) NULL DEFAULT NULL COMMENT 'refresh_token有效期(秒)',
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '{}' COMMENT '附加信息',
  `autoapprove` char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'true' COMMENT '是否自动授权',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `client_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '应用名称',
  `token_format` varchar(20) NOT NULL DEFAULT 'reference' COMMENT 'token格式: reference/self-contained',
  `creator_id` int(11) COMMENT '创建人id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_client_id` (`client_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details (密钥: webApp/app)
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES (1, 'webApp', NULL, '$2a$10$06msMGYRH8nrm4iVnKFNKOoddB8wOwymVhbUzw/d3ZixD7Nq8ot72', 'webApp', 'app,openid,profile', 'authorization_code,password,refresh_token,client_credentials', 'http://127.0.0.1:8080/callback.html', NULL, 3600, 28800, '{}', 'true', NOW(), NOW(), 'Web端应用', 'reference', 1);
INSERT INTO `oauth_client_details` VALUES (2, 'app', NULL, '$2a$10$i3F515wEDiB4Gvj9ym9Prui0dasRttEUQ9ink4Wpgb4zEDCAlV8zO', 'app', 'app', 'authorization_code,password,refresh_token', 'http://127.0.0.1:8081/callback.html', NULL, 3600, 28800, '{}', 'true', NOW(), NOW(), '移动端应用', 'reference', 1);

-- =========================================================================
-- 文件中心相关表 (原 file-center)
-- =========================================================================

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件md5',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
  `is_img` tinyint(1) NOT NULL COMMENT '是否图片',
  `content_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容类型',
  `size` int(11) NOT NULL COMMENT '文件大小(字节)',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物理路径',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '访问URL',
  `source` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '存储来源(LOCAL/S3/OSS等)',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `tenant_id` varchar(32) DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- =========================================================================
-- 日志中心相关表 (原 logger-center)
-- =========================================================================

-- ----------------------------
-- Table structure for sys_logger
-- ----------------------------
DROP TABLE IF EXISTS `sys_logger`;
CREATE TABLE `sys_logger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `application_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '应用名',
  `class_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类名',
  `method_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '方法名',
  `user_id` int(11) NULL COMMENT '用户id',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户名',
  `client_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '客户端id',
  `operation` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作信息',
  `timestamp` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_timestamp` (`timestamp`),
  KEY `idx_user_id` (`user_id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- =========================================================================
-- 搜索管理菜单 (原 search-center)
-- =========================================================================

-- 搜索管理父菜单
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `path`, `path_method`, `css`, `sort`, 
`create_time`, `update_time`, `type`, `hidden`, `tenant_id`, `creator_id`)
VALUES (71, -1, '搜索管理', 'javascript:;', '', NULL, 'layui-icon-search', 3, NOW(), NOW(), 1, 0, 'webApp', 1)
ON DUPLICATE KEY UPDATE `name`=VALUES(`name`);

-- 索引管理子菜单
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `path`, `path_method`, `css`, `sort`, 
`create_time`, `update_time`, `type`, `hidden`, `tenant_id`, `creator_id`)
VALUES (72, 71, '索引管理', '#!index', 'search/index_manager.html', NULL, 'layui-icon-template', 1, NOW(), NOW(), 1, 0, 'webApp', 1)
ON DUPLICATE KEY UPDATE `name`=VALUES(`name`);

-- 用户搜索子菜单
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `path`, `path_method`, `css`, `sort`, 
`create_time`, `update_time`, `type`, `hidden`, `tenant_id`, `creator_id`)
VALUES (73, 71, '用户搜索', '#!userSearch', 'search/user_search.html', NULL, 'layui-icon-user', 2, NOW(), NOW(), 1, 0, 'webApp', 1)
ON DUPLICATE KEY UPDATE `name`=VALUES(`name`);

-- 为admin角色分配搜索菜单权限
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 71) ON DUPLICATE KEY UPDATE `role_id`=VALUES(`role_id`);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 72) ON DUPLICATE KEY UPDATE `role_id`=VALUES(`role_id`);
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES (1, 73) ON DUPLICATE KEY UPDATE `role_id`=VALUES(`role_id`);

-- =========================================================================
-- 完成提示
-- =========================================================================
-- 数据库初始化完成！
-- 
-- 默认账号:
--   用户名: admin  密码: 123456 (管理员)
--   用户名: user   密码: 123456 (普通用户)
--   用户名: test   密码: 123456 (测试用户)
-- 
-- OAuth2 客户端:
--   client_id: webApp  client_secret: webApp
--   client_id: app     client_secret: app
-- =========================================================================


-- =====================================================
-- PassJava 单体应用业务数据初始化脚本
-- =====================================================

SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

USE `passjava_all`;

-- =====================================================
-- 业务菜单配置
-- =====================================================

INSERT IGNORE INTO `sys_menu`(`menu_id`,`parent_id`,`name`,`url`,`perms`,`type`,`icon`,`order_num`) 
VALUES 
(31, 0, '题目中心', '', '', 0, 'editor', 0),
(32, 31, '题目配置', 'question/question', '', 1, 'config', 0),
(33, 31, '类型配置', 'question/type', '', 1, 'config', 1),

(34, 0, '内容中心', '', '', 0, 'documentation', 1),
(35, 34, '横幅配置', 'content/banner', '', 1, 'config', 0),
(36, 34, '资讯配置', 'content/news', '', 1, 'config', 1),

(37, 0, '会员中心', '', '', 0, 'peoples', 2),
(38, 37, '会员管理', 'member/member', '', 1, 'user', 0),

(39, 0, '学习中心', '', '', 0, 'education', 3),
(40, 39, '学习时长', 'study/studytime', '', 1, 'time', 0),
(41, 39, '浏览记录', 'study/viewlog', '', 1, 'log', 1),

(42, 0, '渠道中心', '', '', 0, 'component', 4),
(43, 42, '渠道管理', 'channel/channel', '', 1, 'config', 0);

-- =====================================================
-- 题目管理表 (qms_question)
-- =====================================================

CREATE TABLE IF NOT EXISTS `qms_question`
(
   `id`                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   `title`                VARCHAR(500) COMMENT '题目标题',
   `answer`               VARCHAR(15000) COMMENT '题目解答',
   `level`                TINYINT COMMENT '题目难度等级',
   `display_order`        INT COMMENT '排序',
   `sub_title`            VARCHAR(500) COMMENT '副标题',
   `type`                 BIGINT COMMENT '题目类型',
   `enable`               TINYINT COMMENT '是否显示',
   `del_flag`             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   `create_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目-题目表';

-- =====================================================
-- 题目类型表 (qms_type)
-- =====================================================

CREATE TABLE IF NOT EXISTS `qms_type`
(
   `id`                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   `type`                 CHAR(64) COMMENT '类型名称',
   `comments`             CHAR(64) COMMENT '备注',
   `logo_url`             VARCHAR(500) COMMENT '类型logo路径',
   `del_flag`             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   `create_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目-类型表';

-- =====================================================
-- 会员表 (ums_member)
-- =====================================================

CREATE TABLE IF NOT EXISTS `ums_member`
(
   `id`                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   `mini_openid`          INT COMMENT '小程序openid',
   `mp_openid`            VARCHAR(64) COMMENT '服务号openid',
   `unionid`              VARCHAR(64) COMMENT '微信unionid',
   `level_id`             BIGINT COMMENT '会员等级id',
   `user_name`            CHAR(64) COMMENT '用户名',
   `password`             VARCHAR(64) COMMENT '密码',
   `nickname`             VARCHAR(64) COMMENT '昵称',
   `phone`                VARCHAR(20) COMMENT '手机号码',
   `email`                VARCHAR(64) COMMENT '邮箱',
   `avatar`               VARCHAR(500) COMMENT '头像',
   `gender`               TINYINT COMMENT '性别',
   `birth`                DATE COMMENT '生日',
   `city`                 VARCHAR(500) COMMENT '所在城市',
   `source_type`          TINYINT COMMENT '用户来源',
   `integration`          INT COMMENT '积分',
   `register_time`        DATETIME COMMENT '注册时间',
   `del_flag`             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   `create_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   `user_id`              VARCHAR(64) COMMENT '用户账户名',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员-会员表';

-- =====================================================
-- 会员积分变化历史表 (ums_growth_change_history)
-- =====================================================

CREATE TABLE IF NOT EXISTS `ums_growth_change_history`
(
   `id`                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   `member_id`            BIGINT COMMENT '会员id',
   `change_count`         INT COMMENT '改变的值（正负计数）',
   `note`                 VARCHAR(500) COMMENT '备注',
   `source_type`          TINYINT COMMENT '0->扫码；1->搜索;2->分享',
   `del_flag`             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   `create_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员-积分变化历史表';

-- =====================================================
-- 横幅广告表 (cms_banner)
-- =====================================================

CREATE TABLE IF NOT EXISTS `cms_banner`
(
   `id`                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   `img_url`              VARCHAR(500) COMMENT '图片路径',
   `title`                VARCHAR(500) COMMENT '标题',
   `display_order`        INT COMMENT '排序',
   `enable`               TINYINT COMMENT '是否显示',
   `render_type`          TINYINT COMMENT '跳转类型',
   `render_url`           VARCHAR(500) COMMENT '跳转路径',
   `del_flag`             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   `create_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内容-横幅广告表';

-- =====================================================
-- 资讯表 (cms_news)
-- =====================================================

CREATE TABLE IF NOT EXISTS `cms_news`
(
   `id`                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   `image_url`            VARCHAR(500) COMMENT '图片路径',
   `title`                VARCHAR(500) COMMENT '标题',
   `display_order`        INT COMMENT '排序',
   `render_url`           VARCHAR(500) COMMENT '跳转路径',
   `enable`               TINYINT COMMENT '是否显示',
   `del_flag`             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   `create_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='内容-资讯表';

-- =====================================================
-- 学习时长表 (sms_study_time)
-- =====================================================

CREATE TABLE IF NOT EXISTS `sms_study_time`
(
   `id`                   BIGINT NOT NULL AUTO_INCREMENT,
   `ques_type`            BIGINT COMMENT '题目类型id',
   `member_id`            BIGINT COMMENT '用户id',
   `total_time`           INT COMMENT '学习时长（分）',
   `del_flag`             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   `create_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学习-用户学习时长表';

-- =====================================================
-- 浏览记录表 (sms_view_log)
-- =====================================================

CREATE TABLE IF NOT EXISTS `sms_view_log`
(
   `id`                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   `ques_id`              BIGINT COMMENT '题目id',
   `ques_type`            BIGINT COMMENT '题目类型id',
   `member_id`            BIGINT COMMENT '用户id',
   `del_flag`             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   `create_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学习-用户浏览记录表';

-- =====================================================
-- 渠道表 (chms_channel)
-- =====================================================

CREATE TABLE IF NOT EXISTS `chms_channel`
(
   `id`                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   `name`                 VARCHAR(100) COMMENT '渠道名称',
   `appid`                VARCHAR(100) COMMENT '渠道appid',
   `appsecret`            VARCHAR(500) COMMENT '渠道appsecret',
   `del_flag`             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   `create_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='渠道-渠道表';

-- =====================================================
-- 渠道认证表 (chms_access_token)
-- =====================================================

CREATE TABLE IF NOT EXISTS `chms_access_token`
(
   `id`                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   `access_token`         VARCHAR(500) COMMENT 'access_token',
   `expire_time`          DATETIME COMMENT '到期时间',
   `channel_id`           BIGINT COMMENT '渠道id',
   `del_flag`             TINYINT(1) DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
   `create_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time`          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='渠道-认证表';

-- =====================================================
-- 初始化示例数据
-- =====================================================

-- 题目类型示例数据
INSERT IGNORE INTO `qms_type` (`id`, `type`, `comments`, `logo_url`, `del_flag`, `create_time`, `update_time`) VALUES 
(1, 'javaBasic', 'Java基础', 'https://via.placeholder.com/100', 0, NOW(), NOW()),
(2, 'jvm', 'Java虚拟机', 'https://via.placeholder.com/100', 0, NOW(), NOW()),
(3, 'spring', 'Spring核心原理', 'https://via.placeholder.com/100', 0, NOW(), NOW()),
(4, 'bigData', '大数据', 'https://via.placeholder.com/100', 0, NOW(), NOW()),
(5, 'thread', '多线程', 'https://via.placeholder.com/100', 0, NOW(), NOW());

-- 题目示例数据
INSERT IGNORE INTO `qms_question` (`id`, `title`, `answer`, `level`, `display_order`, `sub_title`, `type`, `enable`, `del_flag`, `create_time`, `update_time`) VALUES 
(1, 'JVM垃圾回收机制', '垃圾自动回收机制是JVM的核心功能之一...', 1, 1, 'GC', 2, 1, 0, NOW(), NOW()),
(2, 'Java基本数据类型有哪些？', 'Java有8种基本数据类型：byte, short, int, long, float, double, char, boolean', 1, 2, '基础', 1, 1, 0, NOW(), NOW()),
(3, 'Spring IOC原理', 'IOC即控制反转，是Spring框架的核心...', 2, 3, '原理', 3, 1, 0, NOW(), NOW());

-- 会员示例数据
INSERT IGNORE INTO `ums_member` (`id`, `user_name`, `nickname`, `phone`, `del_flag`, `create_time`, `update_time`, `user_id`) VALUES 
(1, '悟空', '悟空聊架构', '13800138000', 0, NOW(), NOW(), 'wukong');

-- 横幅示例数据
INSERT IGNORE INTO `cms_banner` (`id`, `img_url`, `title`, `display_order`, `enable`, `del_flag`, `create_time`, `update_time`) VALUES 
(1, 'https://via.placeholder.com/800x300', 'PassJava面试学习平台', 1, 1, 0, NOW(), NOW());

-- 资讯示例数据
INSERT IGNORE INTO `cms_news` (`id`, `image_url`, `title`, `display_order`, `enable`, `del_flag`, `create_time`, `update_time`) VALUES 
(1, 'https://via.placeholder.com/200x150', 'Java面试必备知识点', 1, 1, 0, NOW(), NOW());

-- 渠道示例数据
INSERT IGNORE INTO `chms_channel` (`id`, `name`, `appid`, `del_flag`, `create_time`, `update_time`) VALUES 
(1, '微信小程序', 'wx_demo_appid', 0, NOW(), NOW());






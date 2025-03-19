/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80100 (8.1.0)
 Source Host           : 127.0.0.1:3306
 Source Schema         : migoo

 Target Server Type    : MySQL
 Target Server Version : 80100 (8.1.0)
 File Encoding         : 65001

 Date: 07/12/2023 17:49:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`
(
    `id`               varchar(32)  NOT NULL,
    `workspace_id`     varchar(32)  NULL     DEFAULT NULL COMMENT '工作空间',
    `status`           tinyint(1)   NOT NULL DEFAULT 1 COMMENT '状态 ',
    `name`             varchar(64)  NOT NULL COMMENT '项目名称',
    `product_managers` varchar(255) NULL     DEFAULT NULL COMMENT '产品经理',
    `developers`       varchar(255) NULL     DEFAULT NULL COMMENT '开发人员',
    `testers`          varchar(255) NULL     DEFAULT NULL COMMENT '测试人员',
    `links`            longtext     NULL COMMENT '文档链接',
    `versions`         longtext     NULL COMMENT '版本号',
    `memo`             varchar(255) NULL     DEFAULT NULL,
    `deleted`          bit(1)       NOT NULL DEFAULT b'0',
    `creator`          varchar(64)  NOT NULL,
    `create_time`      datetime     NOT NULL,
    `updater`          varchar(64)  NOT NULL,
    `update_time`      datetime     NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '项目信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_archive
-- ----------------------------
DROP TABLE IF EXISTS `project_archive`;
CREATE TABLE `project_archive`
(
    `id`          varchar(32) NOT NULL,
    `project_id`  varchar(32) NOT NULL COMMENT '归属项目',
    `name`        varchar(64) NOT NULL COMMENT '归档名称',
    `deleted`     tinyint     NULL DEFAULT NULL,
    `creator`     varchar(64) NULL DEFAULT NULL,
    `create_time` datetime    NULL DEFAULT NULL,
    `updater`     varchar(64) NULL DEFAULT NULL,
    `update_time` datetime    NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '项目归档表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_archive_node
-- ----------------------------
DROP TABLE IF EXISTS `project_archive_node`;
CREATE TABLE `project_archive_node`
(
    `id`          varchar(32)  NOT NULL,
    `project_id`  varchar(32)  NOT NULL COMMENT '归属项目',
    `archive_id`  varchar(32)  NOT NULL COMMENT '归档id',
    `original_id` varchar(32)  NOT NULL COMMENT '模块原始id',
    `parent_id`   varchar(32)  NOT NULL COMMENT '上级id',
    `name`        varchar(32)  NOT NULL COMMENT '模块名称',
    `path`        varchar(512) NOT NULL COMMENT '模块路径',
    `sort`        int          NOT NULL COMMENT '排序',
    `deleted`     tinyint      NULL DEFAULT NULL,
    `creator`     varchar(64)  NULL DEFAULT NULL,
    `create_time` datetime     NULL DEFAULT NULL,
    `updater`     varchar(64)  NULL DEFAULT NULL,
    `update_time` datetime     NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '归档模块表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for project_archive_testcase
-- ----------------------------
DROP TABLE IF EXISTS `project_archive_testcase`;
CREATE TABLE `project_archive_testcase`
(
    `id`           varchar(32)  NOT NULL,
    `archive_id`   varchar(32)  NOT NULL COMMENT '归档id',
    `original_id`  varchar(32)  NOT NULL COMMENT '测试用例原始id',
    `project_id`   varchar(32)  NOT NULL,
    `node_id`      varchar(32)  NOT NULL,
    `name`         varchar(64)  NOT NULL,
    `tags`         varchar(255) NULL DEFAULT NULL,
    `level`        varchar(2)   NOT NULL,
    `prerequisite` longtext     NULL,
    `steps`        longtext     NOT NULL,
    `maintainer`   varchar(64)  NOT NULL,
    `reviewed`     varchar(32)  NULL DEFAULT NULL,
    `deleted`      tinyint      NULL DEFAULT NULL,
    `creator`      varchar(64)  NULL DEFAULT NULL,
    `create_time`  datetime     NULL DEFAULT NULL,
    `updater`      varchar(64)  NULL DEFAULT NULL,
    `update_time`  datetime     NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '归档用例表'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for tracked_plan
-- ----------------------------
DROP TABLE IF EXISTS `tracked_plan`;
CREATE TABLE `tracked_plan`
(
    `id`                  varchar(32)  NOT NULL,
    `project_id`          varchar(32)  NOT NULL COMMENT '关联项目id',
    `name`                varchar(255) NOT NULL COMMENT '计划名称',
    `stage`               varchar(64)  NOT NULL COMMENT '测试阶段',
    `executor`            varchar(64)  NOT NULL COMMENT '执行人',
    `expected_start_time` datetime     NOT NULL COMMENT '预期开始时间',
    `expected_end_time`   datetime     NOT NULL COMMENT '预期结束时间',
    `actual_start_time`   datetime     NULL     DEFAULT NULL COMMENT '实际开始时间',
    `actual_end_time`     datetime     NULL     DEFAULT NULL COMMENT '实际结束时间',
    `status`              varchar(20)  NOT NULL DEFAULT 'NOTSTART',
    `memo`                varchar(255) NULL     DEFAULT NULL,
    `deleted`             bit(1)       NOT NULL DEFAULT b'0',
    `creator`             varchar(64)  NOT NULL,
    `create_time`         datetime     NOT NULL,
    `updater`             varchar(64)  NOT NULL,
    `update_time`         datetime     NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '测试计划表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tracked_plan_case
-- ----------------------------
DROP TABLE IF EXISTS `tracked_plan_case`;
CREATE TABLE `tracked_plan_case`
(
    `id`              varchar(32)  NOT NULL,
    `plan_id`         varchar(32)  NOT NULL COMMENT '关联计划id',
    `node_id`         varchar(32)  NOT NULL COMMENT '关联模块id',
    `case_id`         varchar(32)  NOT NULL COMMENT '测试用例id',
    `name`            varchar(64)  NOT NULL COMMENT '测试用例名称',
    `level`           varchar(255) NULL     DEFAULT NULL COMMENT '测试用例等级',
    `tags`            varchar(255) NULL     DEFAULT NULL COMMENT '标签',
    `prerequisite`    longtext     NULL COMMENT '前置条件',
    `steps`           longtext     NULL COMMENT '执行步骤',
    `maintainer`      varchar(64)  NULL     DEFAULT NULL COMMENT '负责人',
    `execute_time`    datetime     NULL     DEFAULT NULL COMMENT '执行时间',
    `executor`        varchar(64)  NULL     DEFAULT NULL COMMENT '执行人',
    `execute_result`  varchar(32)  NULL     DEFAULT 'Prepare' COMMENT '测试结果',
    `execute_comment` longtext     NULL COMMENT '测试评论',
    `deleted`         bit(1)       NOT NULL DEFAULT b'0',
    `creator`         varchar(64)  NOT NULL,
    `create_time`     datetime     NOT NULL,
    `updater`         varchar(64)  NOT NULL,
    `update_time`     datetime     NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '计划用例表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tracked_review
-- ----------------------------
DROP TABLE IF EXISTS `tracked_review`;
CREATE TABLE `tracked_review`
(
    `id`                  varchar(32)  NOT NULL,
    `project_id`          varchar(32)  NOT NULL COMMENT '关联项目id',
    `name`                varchar(255) NOT NULL COMMENT '评审名称',
    `speaker`             varchar(64)  NOT NULL COMMENT '主讲人',
    `reviewers`           varchar(255) NOT NULL COMMENT '评审成员',
    `status`              varchar(20)  NOT NULL DEFAULT 'NOTSTART',
    `expected_start_time` datetime     NOT NULL COMMENT '预期开始时间',
    `expected_end_time`   datetime     NOT NULL COMMENT '预期结束时间',
    `actual_start_time`   datetime     NULL     DEFAULT NULL COMMENT '实际开始时间',
    `actual_end_time`     datetime     NULL     DEFAULT NULL COMMENT '实际结束时间',
    `memo`                varchar(255) NULL     DEFAULT NULL COMMENT '备注',
    `deleted`             bit(1)       NOT NULL DEFAULT b'0',
    `creator`             varchar(64)  NOT NULL,
    `create_time`         datetime     NOT NULL,
    `updater`             varchar(64)  NOT NULL,
    `update_time`         datetime     NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '测试评审表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tracked_review_case
-- ----------------------------
DROP TABLE IF EXISTS `tracked_review_case`;
CREATE TABLE `tracked_review_case`
(
    `id`             varchar(32)  NOT NULL,
    `review_id`      varchar(32)  NOT NULL COMMENT '关联评审id',
    `node_id`        varchar(32)  NOT NULL COMMENT '关联模块id',
    `case_id`        varchar(32)  NOT NULL COMMENT '测试用例id',
    `name`           varchar(64)  NOT NULL COMMENT '测试用例名称',
    `level`          varchar(255) NOT NULL COMMENT '测试用例等级',
    `tags`           varchar(255) NULL     DEFAULT NULL,
    `prerequisite`   longtext     NULL COMMENT '前置条件',
    `steps`          longtext     NOT NULL COMMENT '测试步骤',
    `maintainer`     varchar(64)  NULL     DEFAULT NULL,
    `review_time`    datetime     NULL     DEFAULT NULL COMMENT '评审时间',
    `reviewer`       varchar(64)  NULL     DEFAULT NULL COMMENT '评审人',
    `review_result`  varchar(32)  NULL     DEFAULT 'Prepare' COMMENT '评审结果',
    `review_comment` longtext     NULL,
    `deleted`        bit(1)       NOT NULL DEFAULT b'0',
    `creator`        varchar(64)  NOT NULL,
    `create_time`    datetime     NOT NULL,
    `updater`        varchar(64)  NOT NULL,
    `update_time`    datetime     NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '评审用例表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tracked_testcase
-- ----------------------------
DROP TABLE IF EXISTS `tracked_testcase`;
CREATE TABLE `tracked_testcase`
(
    `id`           varchar(32)  NOT NULL,
    `project_id`   varchar(32)  NOT NULL COMMENT '关联项目id',
    `node_id`      varchar(32)  NOT NULL DEFAULT '-1' COMMENT '关联模块id',
    `name`         varchar(64)  NOT NULL COMMENT '用例名称',
    `maintainer`   varchar(64)  NULL     DEFAULT NULL COMMENT '负责人',
    `tags`         varchar(255) NULL     DEFAULT '[]' COMMENT '标签',
    `level`        varchar(2)   NOT NULL COMMENT '用例等级',
    `prerequisite` longtext     NULL COMMENT '前置条件',
    `steps`        longtext     NULL COMMENT '步骤',
    `reviewed`     varchar(32)  NULL     DEFAULT 'Prepare' COMMENT '评审状态',
    `deleted`      bit(1)       NOT NULL DEFAULT b'0',
    `creator`      varchar(64)  NOT NULL,
    `create_time`  datetime     NOT NULL,
    `updater`      varchar(64)  NOT NULL,
    `update_time`  datetime     NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '测试用例表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tracked_testcase_node
-- ----------------------------
DROP TABLE IF EXISTS `tracked_testcase_node`;
CREATE TABLE `tracked_testcase_node`
(
    `id`          varchar(32)  NOT NULL,
    `project_id`  varchar(32)  NOT NULL COMMENT '归属项目',
    `parent_id`   varchar(32)  NOT NULL COMMENT '父节点id',
    `name`        varchar(64)  NOT NULL COMMENT '节点名称',
    `path`        varchar(255) NULL     DEFAULT NULL COMMENT '路径',
    `sort`        int          NULL     DEFAULT 0 COMMENT '排序',
    `deleted`     bit(1)       NOT NULL DEFAULT b'0',
    `creator`     varchar(64)  NOT NULL,
    `create_time` datetime     NOT NULL,
    `updater`     varchar(64)  NOT NULL,
    `update_time` datetime     NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '用例模块表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tracked_testcase_recycle
-- ----------------------------
DROP TABLE IF EXISTS `tracked_testcase_recycle`;
CREATE TABLE `tracked_testcase_recycle`
(
    `id`           varchar(32)  NOT NULL,
    `project_id`   varchar(32)  NULL     DEFAULT NULL,
    `node_id`      varchar(32)  NULL     DEFAULT NULL,
    `case_id`      varchar(32)  NOT NULL,
    `name`         longtext     NOT NULL COMMENT '用例名称',
    `level`        longtext     NOT NULL COMMENT '用例等级',
    `tags`         varchar(255) NULL     DEFAULT NULL COMMENT '标签',
    `prerequisite` longtext     NULL COMMENT '前置条件',
    `steps`        longtext     NOT NULL COMMENT '执行步骤',
    `maintainer`   varchar(64)  NOT NULL COMMENT '负责人',
    `deleted`      bit(1)       NOT NULL DEFAULT b'0',
    `creator`      varchar(64)  NOT NULL,
    `create_time`  datetime     NOT NULL,
    `updater`      varchar(64)  NOT NULL,
    `update_time`  datetime     NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '用例回收站'
  ROW_FORMAT = Dynamic;



-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4110, '项目管理', '', 1, 99, 0, '/project', 'ep:promotion', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-08 16:59:07', '超级管理员', '2023-12-07 14:54:53');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4111, '项目列表', 'system:project:query', 2, 5, 2, 'project', 'fa:table', 'ProjectList', 'Project/table/index.vue', 1, 1, 1, 1, 0, '超级管理员', '2023-11-08 17:03:17', '超级管理员', '2023-12-07 15:14:25');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4112, '新增', 'system:project:add', 3, 0, 4111, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-08 17:03:45', '超级管理员', '2023-11-08 17:03:45');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4113, '修改', 'system:project:update', 3, 1, 4111, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-08 17:04:03', '超级管理员', '2023-11-08 17:04:03');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4114, '删除', 'system:project:remove', 3, 2, 4111, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-08 17:04:20', '超级管理员', '2023-11-08 17:04:20');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4115, '项目设置', 'project:info:query', 2, 0, 4110, 'profile', 'ep:promotion', 'ProjectInfo', 'Project/info/index.vue', 1, 1, 1, 1, 0, '超级管理员', '2023-11-10 14:54:29', '超级管理员', '2023-12-07 15:15:41');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4118, '测试跟踪', '', 1, 1, 0, '/track', 'ep:coffee', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-13 16:04:04', '超级管理员', '2023-11-29 17:51:17');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4119, '测试用例', '', 2, 0, 4118, 'case', 'ep:burger', 'CaseTracked', 'Tracked/case/index.vue', 1, 1, 1, 1, 0, '超级管理员', '2023-11-13 16:05:53', '超级管理员', '2023-11-30 10:17:47');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4120, '测试评审', '', 2, 1, 4118, 'review', 'ep:ice-cream', 'ReviewTracked', 'Tracked/review/index.vue', 1, 1, 1, 1, 0, '超级管理员', '2023-11-13 16:08:46', '超级管理员', '2023-11-30 10:18:33');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4121, '测试计划', '', 2, 2, 4118, 'plan', 'ep:hot-water', 'PlanTracked', 'Tracked/plan/index.vue', 1, 1, 1, 1, 0, '超级管理员', '2023-11-13 16:09:35', '超级管理员', '2023-11-30 10:19:10');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4122, '新增用例', '', 2, 99, 4118, 'case/add', 'ep:document-add', 'CaseAdd', 'Tracked/case/CaseEditor.vue', 1, 0, 0, 1, 0, '超级管理员', '2023-11-14 17:04:14', '超级管理员', '2023-12-05 15:51:21');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4123, '编辑用例', '', 2, 99, 4118, 'case/edit/:id', 'ep:edit', 'CaseEdit', 'Tracked/case/CaseEditor.vue', 1, 0, 0, 1, 0, '超级管理员', '2023-11-14 17:31:39', '超级管理员', '2023-12-05 15:51:28');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4124, '规划&评审', '', 2, 99, 4118, 'review/:reviewId/associated-use-cases', 'ep:link', 'ReviewAssociatedCase', 'Tracked/review/associated.vue', 1, 0, 0, 0, 0, '超级管理员', '2023-11-15 15:30:08', '超级管理员', '2023-11-29 17:52:22');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4125, '规划&执行', '', 2, 99, 4118, 'plan/:planId/associated-use-cases', 'fa-solid:link', 'PlanAssociatedCase', 'Tracked/plan/associated.vue', 1, 0, 0, 0, 0, '超级管理员', '2023-11-16 11:13:23', '超级管理员', '2023-11-29 17:52:27');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4127, '关联用例', 'review:case:add', 3, 0, 4124, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:23:55', '超级管理员', '2023-11-29 17:23:55');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4128, '解除关联', 'review:case:remove', 3, 1, 4124, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:24:39', '超级管理员', '2023-11-29 17:24:39');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4129, '评审用例', 'review:case:execute', 3, 2, 4124, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:26:03', '超级管理员', '2023-11-29 17:26:03');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4130, '关联用例', 'plan:case:add', 3, 0, 4125, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:26:32', '超级管理员', '2023-11-29 17:26:32');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4131, '解除关联', 'plan:case:remove', 3, 1, 4125, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:26:48', '超级管理员', '2023-11-29 17:26:48');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4132, '执行测试', 'plan:case:execute', 3, 2, 4125, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:27:07', '超级管理员', '2023-11-29 17:27:07');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4133, '新增', 'tracked:plan:add', 3, 0, 4121, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:32:05', '超级管理员', '2023-11-29 17:34:20');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4134, '修改', 'tracked:plan:update', 3, 1, 4121, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:32:25', '超级管理员', '2023-11-29 17:34:26');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4135, '删除', 'tracked:plan:remove', 3, 2, 4121, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:32:45', '超级管理员', '2023-11-29 17:34:32');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4136, '规划&执行', 'tracked:plan:execute', 3, 3, 4121, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:33:57', '超级管理员', '2023-11-29 17:34:39');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4137, '新增', 'tracked:review:add', 3, 0, 4120, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:35:02', '超级管理员', '2023-11-29 17:35:02');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4138, '修改', 'tracked:review:update', 3, 1, 4120, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:35:13', '超级管理员', '2023-11-29 17:35:13');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4139, '删除', 'tracked:review:remove', 3, 2, 4120, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:35:28', '超级管理员', '2023-11-29 17:35:28');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4140, '规划&执行', 'tracked:review:execute', 3, 3, 4120, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:35:50', '超级管理员', '2023-11-29 17:35:50');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4141, '归档管理', '', 2, 5, 4110, 'archives', 'fa:file-archive-o', 'ProjectArchive', 'Project/archive/index.vue', 1, 1, 1, 1, 1, '超级管理员', '2023-12-04 11:52:07', '超级管理员', '2023-12-07 14:16:34');
INSERT INTO `sys_menu` (`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component_name`, `component`, `status`, `visible`, `always_show`, `keep_alive`, `deleted`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4142, '归档详情', '', 2, 99, 4110, 'archive/:id', 'fa:file-archive-o', 'ArchiveDetail', 'Project/archive/detail.vue', 1, 0, 0, 0, 0, '超级管理员', '2023-12-05 16:32:09', '超级管理员', '2023-12-07 14:53:02');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

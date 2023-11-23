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

 Date: 17/11/2023 17:36:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`             bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `name`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '菜单名称',
    `permission`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '权限标识',
    `menu_type`      tinyint                                                       NOT NULL COMMENT '菜单类型',
    `sort`           int                                                           NOT NULL DEFAULT 0 COMMENT '显示顺序',
    `parent_id`      bigint                                                        NOT NULL DEFAULT 0 COMMENT '父菜单ID',
    `path`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT '' COMMENT '路由地址',
    `icon`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT '#' COMMENT '菜单图标',
    `component_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT NULL COMMENT '组件名称',
    `component`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT NULL COMMENT '组件路径',
    `status`         tinyint                                                       NOT NULL DEFAULT 1 COMMENT '菜单状态',
    `page_type`      tinyint                                                       NOT NULL DEFAULT 2 COMMENT '页面类型，1: 会员页面；2:管理员页面',
    `visible`        tinyint                                                       NOT NULL DEFAULT 1 COMMENT '是否可见',
    `always_show`    tinyint                                                       NOT NULL DEFAULT 0 COMMENT '总是显示：1:是，0:否',
    `keep_alive`     tinyint                                                       NOT NULL DEFAULT 1 COMMENT '是否缓存',
    `deleted`        tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否删除',
    `creator`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT '' COMMENT '创建者',
    `create_time`    datetime                                                      NULL     DEFAULT NULL COMMENT '创建时间',
    `updater`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL     DEFAULT '' COMMENT '更新者',
    `update_time`    datetime                                                      NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4127
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu`
VALUES (1, '工作台', 'dashboard', 2, 0, 0, '/index', 'ep:alarm-clock', 'Dashboard', 'dashboard/index', 1, 2, 1, 1, 1, 0,
        '', NULL, '超级管理员', '2023-10-26 17:20:34');
INSERT INTO `sys_menu`
VALUES (2, '系统管理', 'system:manager', 1, 9998, 0, '/system', 'ep:setting', NULL, '', 1, 2, 1, 0, 1, 0, '',
        '2022-04-29 18:34:52', '超级管理员', '2023-09-27 22:48:18');
INSERT INTO `sys_menu`
VALUES (100, '主题测试', 'layout:setting', 3, 0, 1, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2023-07-23 18:56:46', '超级管理员', '2023-07-23 18:56:46');
INSERT INTO `sys_menu`
VALUES (200, '用户管理', 'system:user:query', 2, 0, 2, 'user', 'ep:user', 'SystemUser', 'system/user/index', 1, 2, 1, 1,
        1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-10-26 17:20:41');
INSERT INTO `sys_menu`
VALUES (201, '角色管理', 'system:role:query', 2, 1, 2, 'role', 'fa-solid:user-plus', 'UserRole', 'system/role/index', 1,
        2, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:49:31');
INSERT INTO `sys_menu`
VALUES (202, '部门管理', 'system:dept:query', 2, 2, 2, 'dept', 'ep:basketball', 'SystemDept', 'system/dept/index', 1, 2,
        1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:50:05');
INSERT INTO `sys_menu`
VALUES (203, '岗位管理', 'system:post:query', 2, 3, 2, 'post', 'ep:postcard', 'SystemPost', 'system/post/index', 1, 2,
        1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:50:32');
INSERT INTO `sys_menu`
VALUES (204, '菜单管理', 'system:menu:query', 2, 4, 2, 'menu', 'ep:menu', 'SystemMenu', 'system/menu/index', 1, 2, 1, 1,
        1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:50:53');
INSERT INTO `sys_menu`
VALUES (2000, '新增', 'system:user:add', 3, 0, 200, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 13:55:34', '超级管理员', '2022-05-01 16:27:05');
INSERT INTO `sys_menu`
VALUES (2001, '修改', 'system:user:update', 3, 1, 200, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 13:55:59', '超级管理员', '2023-09-30 15:51:13');
INSERT INTO `sys_menu`
VALUES (2002, '删除', 'system:user:remove', 3, 3, 200, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 13:56:20', '超级管理员', '2022-05-01 16:27:12');
INSERT INTO `sys_menu`
VALUES (2003, '重置密码', 'system:user:reset-password', 3, 4, 200, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 14:00:56', '超级管理员', '2022-05-01 16:27:16');
INSERT INTO `sys_menu`
VALUES (2004, '分配角色', 'system:permission:assign-user-role', 3, 4, 200, '', '', NULL, '', 1, 2, 1, 0, 1, 0,
        '超级管理员', '2022-05-01 16:05:28', '超级管理员', '2022-05-01 16:27:23');
INSERT INTO `sys_menu`
VALUES (2010, '新增', 'system:role:add', 3, 0, 201, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:00:30', '超级管理员', '2022-05-01 16:27:37');
INSERT INTO `sys_menu`
VALUES (2011, '修改', 'system:role:update', 3, 1, 201, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:01:01', '超级管理员', '2023-10-01 10:18:19');
INSERT INTO `sys_menu`
VALUES (2012, '删除', 'system:role:remove', 3, 2, 201, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:01:24', '超级管理员', '2022-05-01 16:27:53');
INSERT INTO `sys_menu`
VALUES (2013, '分配菜单', 'system:permission:assign-role-menu', 3, 3, 201, '', '', NULL, '', 1, 2, 1, 0, 1, 0,
        '超级管理员', '2022-05-01 16:06:41', '超级管理员', '2022-05-01 16:27:57');
INSERT INTO `sys_menu`
VALUES (2020, '新增', 'system:dept:add', 3, 0, 202, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:07:56', '超级管理员', '2022-05-01 16:28:02');
INSERT INTO `sys_menu`
VALUES (2021, '修改', 'system:dept:update', 3, 1, 202, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:08:12', '超级管理员', '2023-10-01 10:18:13');
INSERT INTO `sys_menu`
VALUES (2022, '删除', 'system:dept:remove', 3, 3, 202, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:08:24', '超级管理员', '2022-05-01 16:28:10');
INSERT INTO `sys_menu`
VALUES (2030, '新增', 'system:post:add', 3, 0, 203, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:15:22', '超级管理员', '2022-05-01 16:28:21');
INSERT INTO `sys_menu`
VALUES (2031, '修改', 'system:post:update', 3, 1, 203, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:18:11', '超级管理员', '2023-10-01 10:17:56');
INSERT INTO `sys_menu`
VALUES (2032, '删除', 'system:post:remove', 3, 2, 203, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:18:28', '超级管理员', '2022-05-01 16:28:42');
INSERT INTO `sys_menu`
VALUES (2040, '新增', 'system:menu:add', 3, 0, 204, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:19:11', '超级管理员', '2022-05-01 16:28:48');
INSERT INTO `sys_menu`
VALUES (2041, '修改', 'system:menu:update', 3, 1, 204, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:19:42', '超级管理员', '2023-10-01 10:18:06');
INSERT INTO `sys_menu`
VALUES (2042, '删除', 'system:menu:remove', 3, 2, 204, '', '', NULL, '', 1, 2, 1, 0, 1, 0, '超级管理员',
        '2022-05-01 16:19:56', '超级管理员', '2022-05-01 16:28:57');
INSERT INTO `sys_menu`
VALUES (4110, '项目管理', '', 1, 99, 0, '/project', 'fa:product-hunt', '', '', 1, 2, 1, 1, 1, 0, '超级管理员',
        '2023-11-08 16:59:07', '超级管理员', '2023-11-08 16:59:07');
INSERT INTO `sys_menu`
VALUES (4111, '项目列表', 'system:project:query', 2, 0, 4110, 'table', 'fa-solid:table', 'ProjectList',
        'Project/list/index.vue', 1, 2, 1, 1, 1, 0, '超级管理员', '2023-11-08 17:03:17', '超级管理员',
        '2023-11-10 14:50:11');
INSERT INTO `sys_menu`
VALUES (4112, '新增', 'system:project:add', 3, 0, 4111, '', '', '', '', 1, 2, 1, 1, 1, 0, '超级管理员',
        '2023-11-08 17:03:45', '超级管理员', '2023-11-08 17:03:45');
INSERT INTO `sys_menu`
VALUES (4113, '修改', 'system:project:update', 3, 1, 4111, '', '', '', '', 1, 2, 1, 1, 1, 0, '超级管理员',
        '2023-11-08 17:04:03', '超级管理员', '2023-11-08 17:04:03');
INSERT INTO `sys_menu`
VALUES (4114, '删除', 'system:project:remove', 3, 2, 4111, '', '', '', '', 1, 2, 1, 1, 1, 0, '超级管理员',
        '2023-11-08 17:04:20', '超级管理员', '2023-11-08 17:04:20');
INSERT INTO `sys_menu`
VALUES (4115, '项目信息', 'project:info:query', 2, 1, 4110, 'info', 'fa:info', 'ProjectInfo', 'Project/index.vue', 1, 2,
        1, 1, 1, 0, '超级管理员', '2023-11-10 14:54:29', '超级管理员', '2023-11-10 14:54:45');
INSERT INTO `sys_menu`
VALUES (4116, '项目成员', '', 2, 2, 4110, 'member', 'fa:group', 'ProjectMember', 'Project/member/index.vue', 1, 2, 1, 1,
        1, 0, '超级管理员', '2023-11-13 14:30:29', '超级管理员', '2023-11-13 14:30:29');
INSERT INTO `sys_menu`
VALUES (4117, '模块管理', '', 2, 3, 4110, 'module', 'fa-solid:align-justify', 'ProjectModule',
        'Project/module/index.vue', 1, 2, 1, 1, 1, 0, '超级管理员', '2023-11-13 14:31:44', '超级管理员',
        '2023-11-13 14:31:44');
INSERT INTO `sys_menu`
VALUES (4118, '测试跟踪', '', 1, 1, 0, '/st', 'ep:coffee', '', '', 1, 2, 1, 1, 1, 0, '超级管理员',
        '2023-11-13 16:04:04', '超级管理员', '2023-11-16 11:25:59');
INSERT INTO `sys_menu`
VALUES (4119, '测试用例', '', 2, 0, 4118, 'case', 'fa-solid:suitcase', 'STCase', 'ST/case/index.vue', 1, 2, 1, 1, 1, 0,
        '超级管理员', '2023-11-13 16:05:53', '超级管理员', '2023-11-13 16:05:53');
INSERT INTO `sys_menu`
VALUES (4120, '测试评审', '', 2, 1, 4118, 'review', 'fa:envira', 'STReview', 'ST/review/index.vue', 1, 2, 1, 1, 1, 0,
        '超级管理员', '2023-11-13 16:08:46', '超级管理员', '2023-11-13 16:09:41');
INSERT INTO `sys_menu`
VALUES (4121, '测试计划', '', 2, 2, 4118, 'plan', 'fa-solid:paper-plane', 'STPlan', 'ST/plan/index.vue', 1, 2, 1, 1, 1,
        0, '超级管理员', '2023-11-13 16:09:35', '超级管理员', '2023-11-13 16:09:35');
INSERT INTO `sys_menu`
VALUES (4122, '新增用例', '', 2, 99, 4118, 'case/add', 'ep:document-add', 'CaseAdd', 'ST/case/add.vue', 1, 2, 0, 0, 0,
        0, '超级管理员', '2023-11-14 17:04:14', '超级管理员', '2023-11-14 17:30:35');
INSERT INTO `sys_menu`
VALUES (4123, '编辑用例', '', 2, 99, 4118, 'case/edit/:id', 'ep:edit', 'CaseEdit', 'ST/case/edit.vue', 1, 2, 0, 0, 0, 0,
        '超级管理员', '2023-11-14 17:31:39', '超级管理员', '2023-11-14 17:31:39');
INSERT INTO `sys_menu`
VALUES (4124, '规划用例', '', 2, 99, 4118, 'review/:reviewId/associated-use-cases', 'ep:link', 'ReviewAssociatedCase',
        'ST/review/associated.vue', 1, 2, 0, 0, 0, 0, '超级管理员', '2023-11-15 15:30:08', '超级管理员',
        '2023-11-16 11:13:56');
INSERT INTO `sys_menu`
VALUES (4125, '规划用例', '', 2, 99, 4118, 'plan/:planId/associated-use-cases', 'fa-solid:link', 'PlanAssociatedCase',
        'ST/plan/associated.vue', 1, 2, 0, 0, 0, 0, '超级管理员', '2023-11-16 11:13:23', '超级管理员',
        '2023-11-16 11:30:57');
INSERT INTO `sys_menu`
VALUES (4126, '标签管理', '', 2, 4, 4110, 'tag', 'ep:collection-tag', 'ProjectTag', 'Project/tag/index.vue', 1, 2, 1, 1,
        1, 0, '超级管理员', '2023-11-16 15:39:02', '超级管理员', '2023-11-16 15:39:15');

SET FOREIGN_KEY_CHECKS = 1;

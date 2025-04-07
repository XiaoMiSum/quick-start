/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 90100 (9.1.0)
 Source Host           : localhost:3306
 Source Schema         : quick-click

 Target Server Type    : MySQL
 Target Server Version : 90100 (9.1.0)
 File Encoding         : 65001

 Date: 07/04/2025 17:20:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `menu_type` tinyint NOT NULL COMMENT '菜单类型',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父菜单ID',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '路由地址',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `component_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件名称',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '菜单状态',
  `visible` tinyint NOT NULL DEFAULT 1 COMMENT '是否可见',
  `always_show` tinyint NOT NULL DEFAULT 0 COMMENT '总是显示：1:是，0:否',
  `keep_alive` tinyint NOT NULL DEFAULT 1 COMMENT '是否缓存',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4171 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', '', 1, 9998, 0, '/management/system', 'ep:setting', NULL, '', 1, 1, 0, 1, 0, '', '2022-04-29 18:34:52', '超级管理员', '2025-03-20 09:36:02');
INSERT INTO `sys_menu` VALUES (2, '研发工具', 'developer', 1, 9999, 0, '/management/developer', 'fa:connectdevelop', NULL, NULL, 1, 1, 0, 1, 0, '', NULL, '超级管理员', '2025-03-20 08:49:00');
INSERT INTO `sys_menu` VALUES (3, '工作台', 'dashboard', 2, 0, 0, '', 'ep:alarm-clock', 'Dashboard', 'dashboard/index', 1, 1, 1, 1, 0, '', NULL, '超级管理员', '2023-09-27 22:37:53');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 'system:user:query', 2, 0, 1, 'user', 'ep:user', 'SystemUser', 'system/user/index', 1, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:48:39');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 'system:role:query', 2, 1, 1, 'role', 'fa-solid:user-plus', 'UserRole', 'system/role/index', 1, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:49:31');
INSERT INTO `sys_menu` VALUES (102, '部门管理', 'system:dept:query', 2, 2, 1, 'dept', 'ep:basketball', 'SystemDept', 'system/dept/index', 1, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:50:05');
INSERT INTO `sys_menu` VALUES (103, '岗位管理', 'system:post:query', 2, 3, 1, 'post', 'ep:postcard', 'SystemPost', 'system/post/index', 1, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:50:32');
INSERT INTO `sys_menu` VALUES (104, '菜单管理', 'system:menu:query', 2, 4, 1, 'menu', 'ep:menu', 'SystemMenu', 'system/menu/index', 1, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:50:53');
INSERT INTO `sys_menu` VALUES (105, '配置管理', 'system:configurer:query', 2, 99999, 1, 'configurer', 'ep:guide', 'SystemConfig', 'system/configurer/index', 1, 1, 1, 1, 0, '超级管理员', '2023-05-18 14:45:15', '超级管理员', '2024-04-14 12:58:30');
INSERT INTO `sys_menu` VALUES (106, '主题设置', 'layout:setting', 3, 0, 3, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-07-23 18:56:46', '超级管理员', '2024-04-14 12:58:34');
INSERT INTO `sys_menu` VALUES (107, '任务管理', '', 1, 0, 2, 'job', 'fa-solid:tasks', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-10-01 14:50:25', '超级管理员', '2024-04-14 12:58:36');
INSERT INTO `sys_menu` VALUES (108, '异常日志', 'developer:error-log:query', 2, 2, 2, 'error-log', 'fa-solid:bug', 'Bug', 'Management/developer/errorlog/index', 1, 1, 1, 1, 0, '超级管理员', '2022-07-10 13:54:45', '超级管理员', '2025-03-20 01:31:15');
INSERT INTO `sys_menu` VALUES (110, '短信管理', '', 1, 5, 2, 'message', 'ep:message', NULL, NULL, 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:17:16', '超级管理员', '2024-04-14 12:57:34');
INSERT INTO `sys_menu` VALUES (111, '新增', 'system:user:add', 3, 0, 100, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 13:55:34', '奥丁1', '2024-04-14 12:57:29');
INSERT INTO `sys_menu` VALUES (112, '修改', 'system:user:update', 3, 1, 100, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 13:55:59', '超级管理员', '2024-04-14 12:57:28');
INSERT INTO `sys_menu` VALUES (113, '删除', 'system:user:remove', 3, 3, 100, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 13:56:20', '奥丁1', '2024-04-14 12:57:27');
INSERT INTO `sys_menu` VALUES (114, '重置密码', 'system:user:reset-password', 3, 4, 100, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 14:00:56', '奥丁1', '2024-04-14 12:57:26');
INSERT INTO `sys_menu` VALUES (115, '分配角色', 'system:permission:assign-user-role', 3, 4, 100, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:05:28', '奥丁1', '2024-04-14 12:57:24');
INSERT INTO `sys_menu` VALUES (116, '新增', 'system:role:add', 3, 0, 101, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:00:30', '奥丁1', '2024-04-14 12:57:23');
INSERT INTO `sys_menu` VALUES (117, '修改', 'system:role:update', 3, 1, 101, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:01:01', '超级管理员', '2024-04-14 12:57:22');
INSERT INTO `sys_menu` VALUES (118, '删除', 'system:role:remove', 3, 2, 101, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:01:24', '奥丁1', '2024-04-14 12:57:21');
INSERT INTO `sys_menu` VALUES (119, '分配菜单', 'system:permission:assign-role-menu', 3, 3, 101, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:06:41', '奥丁1', '2024-04-14 12:57:20');
INSERT INTO `sys_menu` VALUES (120, '新增', 'system:dept:add', 3, 0, 102, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:07:56', '奥丁1', '2024-04-14 12:57:18');
INSERT INTO `sys_menu` VALUES (121, '修改', 'system:dept:update', 3, 1, 102, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:08:12', '超级管理员', '2024-04-14 12:57:17');
INSERT INTO `sys_menu` VALUES (122, '删除', 'system:dept:remove', 3, 3, 102, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:08:24', '奥丁1', '2024-04-14 12:57:16');
INSERT INTO `sys_menu` VALUES (123, '新增', 'system:post:add', 3, 0, 103, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:15:22', '奥丁1', '2024-04-14 12:57:15');
INSERT INTO `sys_menu` VALUES (124, '修改', 'system:post:update', 3, 1, 103, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:18:11', '超级管理员', '2024-04-14 12:57:14');
INSERT INTO `sys_menu` VALUES (125, '删除', 'system:post:remove', 3, 2, 103, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:18:28', '奥丁1', '2024-04-14 12:57:01');
INSERT INTO `sys_menu` VALUES (126, '新增', 'system:menu:add', 3, 0, 104, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:19:11', '奥丁1', '2024-04-14 12:56:59');
INSERT INTO `sys_menu` VALUES (127, '修改', 'system:menu:update', 3, 1, 104, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:19:42', '超级管理员', '2024-04-14 12:56:57');
INSERT INTO `sys_menu` VALUES (128, '删除', 'system:menu:remove', 3, 2, 104, '', '', NULL, '', 1, 1, 0, 1, 0, '奥丁1', '2022-05-01 16:19:56', '奥丁1', '2024-04-14 12:56:56');
INSERT INTO `sys_menu` VALUES (129, '修改', 'system:configurer:update', 3, 0, 105, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-05-18 14:45:35', '超级管理员', '2024-04-14 13:00:42');
INSERT INTO `sys_menu` VALUES (130, '定时任务', 'developer:job:query', 2, 0, 107, 'list', 'fa-solid:tasks', 'JobIndex', 'Management/developer/job/index', 1, 1, 1, 1, 0, '超级管理员', '2023-03-17 17:10:47', '超级管理员', '2025-03-20 00:55:26');
INSERT INTO `sys_menu` VALUES (131, '调度日志', '', 2, 1, 107, 'log', 'ep:document', 'JobLog', 'Management/developer/job/logger/index', 1, 1, 1, 1, 0, '超级管理员', '2023-03-18 13:50:00', '超级管理员', '2025-03-20 00:55:33');
INSERT INTO `sys_menu` VALUES (132, '新增', 'developer:job:add', 3, 0, 130, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-03-17 17:12:23', '超级管理员', '2024-04-14 13:01:05');
INSERT INTO `sys_menu` VALUES (133, '修改', 'developer:job:update', 3, 1, 130, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-03-17 17:12:39', '超级管理员', '2024-04-14 13:01:07');
INSERT INTO `sys_menu` VALUES (134, '删除', 'developer:job:remove', 3, 2, 130, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-03-17 17:12:58', '超级管理员', '2024-04-14 13:01:11');
INSERT INTO `sys_menu` VALUES (135, '执行', 'developer:job:trigger', 3, 3, 130, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-03-17 17:13:23', '超级管理员', '2024-04-14 13:01:15');
INSERT INTO `sys_menu` VALUES (136, '修改', 'developer:error-log:update', 3, 0, 108, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2022-07-10 13:55:37', '超级管理员', '2024-04-14 13:00:07');
INSERT INTO `sys_menu` VALUES (137, '删除', 'developer:error-log:remove', 3, 1, 108, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2022-07-10 13:55:57', '超级管理员', '2024-04-14 13:00:05');
INSERT INTO `sys_menu` VALUES (145, '短信渠道', 'developer:sms:channel:query', 2, 0, 110, 'channel', 'fa:at', 'SmsChannel', 'Management/developer/sms/channel/index', 1, 1, 1, 1, 0, '超级管理员', '2023-06-06 17:21:14', '超级管理员', '2025-03-20 00:55:37');
INSERT INTO `sys_menu` VALUES (146, '短信模板', 'developer:sms:template:query', 2, 1, 110, 'template', 'fa-solid:align-justify', 'SmsTemplate', 'Management/developer/sms/template/index', 1, 1, 1, 1, 0, '超级管理员', '2023-06-06 17:30:09', '超级管理员', '2025-03-20 00:55:43');
INSERT INTO `sys_menu` VALUES (147, '短信日志', 'developer:sms:log:query', 2, 2, 110, 'logger', 'ep:document', 'SmsLog', 'Management/developer/sms/log/index', 1, 1, 1, 1, 0, '超级管理员', '2023-06-09 16:39:31', '超级管理员', '2025-03-20 00:55:47');
INSERT INTO `sys_menu` VALUES (148, '新增', 'developer:sms:channel:add', 3, 0, 145, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:21:43', '超级管理员', '2024-04-14 13:04:38');
INSERT INTO `sys_menu` VALUES (149, '修改', 'developer:sms:channel:update', 3, 1, 145, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:22:44', '超级管理员', '2024-04-14 13:04:45');
INSERT INTO `sys_menu` VALUES (150, '删除', 'developer:sms:channel:remove', 3, 2, 145, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:23:02', '超级管理员', '2024-04-14 13:04:50');
INSERT INTO `sys_menu` VALUES (151, '新增', 'developer:sms:template:add', 3, 0, 146, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:30:23', '超级管理员', '2024-04-14 13:04:53');
INSERT INTO `sys_menu` VALUES (152, '修改', 'developer:sms:template:update', 3, 1, 146, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:30:40', '超级管理员', '2024-04-14 13:04:55');
INSERT INTO `sys_menu` VALUES (153, '删除', 'developer:sms:template:remove', 3, 2, 146, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:30:57', '超级管理员', '2024-04-14 13:04:58');
INSERT INTO `sys_menu` VALUES (154, '测试', 'developer:sms:template:send:sms', 3, 3, 146, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:49:03', '超级管理员', '2024-04-14 13:05:01');
INSERT INTO `sys_menu` VALUES (155, '文件管理', '', 1, 1, 2, 'file', 'ep:folder-opened', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:33:21', '超级管理员', '2024-04-22 14:45:31');
INSERT INTO `sys_menu` VALUES (156, '文件列表', 'developer:file:query', 2, 0, 155, 'list', 'ep:files', 'FileList', 'Management/developer/file/index', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:41:51', '超级管理员', '2025-03-20 00:55:51');
INSERT INTO `sys_menu` VALUES (157, 'OSS配置', 'developer:file:config:query', 2, 1, 155, 'config', 'fa:crosshairs', 'FileConfig', 'Management/developer/file/config/index', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:43:40', '超级管理员', '2025-03-20 00:55:56');
INSERT INTO `sys_menu` VALUES (158, '删除', 'developer:file:remove', 3, 0, 156, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:01', '超级管理员', '2024-04-22 14:46:12');
INSERT INTO `sys_menu` VALUES (159, '新增', 'developer:file:config:add', 3, 0, 157, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:21', '超级管理员', '2024-04-22 14:46:15');
INSERT INTO `sys_menu` VALUES (160, '修改', 'developer:file:config:update', 3, 1, 157, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:32', '超级管理员', '2024-04-22 14:46:18');
INSERT INTO `sys_menu` VALUES (161, '删除', 'developer:file:config:remove', 3, 2, 157, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:45', '超级管理员', '2024-04-22 14:46:19');
INSERT INTO `sys_menu` VALUES (162, '字典管理', '', 1, 6, 2, 'dictionary', 'ep:folder-opened', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:33:21', '超级管理员', '2024-05-23 10:03:04');
INSERT INTO `sys_menu` VALUES (163, '字典列表', 'developer:dictionary:query', 2, 0, 162, 'list', 'ep:files', 'DictionaryList', 'Management/developer/dictionary/index', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:41:51', '超级管理员', '2025-03-20 00:56:01');
INSERT INTO `sys_menu` VALUES (164, '字典数据', 'developer:dictionary:value:query', 2, 1, 162, 'values', 'fa:crosshairs', 'DictionaryValue', 'Management/developer/dictionary/value/index', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:43:40', '超级管理员', '2025-03-20 00:56:09');
INSERT INTO `sys_menu` VALUES (165, '新增', 'developer:dictionary:add', 3, 0, 163, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:01', '超级管理员', '2024-04-22 14:46:12');
INSERT INTO `sys_menu` VALUES (166, '修改', 'developer:dictionary:update', 3, 0, 163, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:21', '超级管理员', '2024-04-22 14:46:15');
INSERT INTO `sys_menu` VALUES (167, '删除', 'developer:dictionary:remove', 3, 1, 163, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:32', '超级管理员', '2024-04-22 14:46:18');
INSERT INTO `sys_menu` VALUES (168, '新增', 'developer:dictionary:value:add', 3, 0, 164, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:01', '超级管理员', '2024-04-22 14:46:12');
INSERT INTO `sys_menu` VALUES (169, '修改', 'developer:dictionary:value:update', 3, 0, 164, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:21', '超级管理员', '2024-04-22 14:46:15');
INSERT INTO `sys_menu` VALUES (170, '删除', 'developer:dictionary:value:remove', 3, 1, 164, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:32', '超级管理员', '2024-04-22 14:46:18');
INSERT INTO `sys_menu` VALUES (4110, '项目管理', '', 1, 99, 0, '/project', 'ep:box', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-08 16:59:07', '超级管理员', '2025-03-20 17:09:33');
INSERT INTO `sys_menu` VALUES (4111, '项目列表', 'project:list:query', 2, 50, 4110, 'list', 'fa:table', 'ProjectList', 'Project/table/index', 1, 1, 1, 1, 0, '超级管理员', '2023-11-08 17:03:17', '超级管理员', '2025-04-07 11:40:42');
INSERT INTO `sys_menu` VALUES (4112, '新增', 'project:list:add', 3, 0, 4111, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-08 17:03:45', '超级管理员', '2025-03-20 01:07:37');
INSERT INTO `sys_menu` VALUES (4113, '修改', 'project:list:update', 3, 1, 4111, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-08 17:04:03', '超级管理员', '2025-03-20 01:07:47');
INSERT INTO `sys_menu` VALUES (4114, '删除', 'project:list:remove', 3, 2, 4111, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-08 17:04:20', '超级管理员', '2025-03-20 01:07:52');
INSERT INTO `sys_menu` VALUES (4115, '项目设置', 'project:info:query', 2, 0, 4110, 'profile', 'ep:promotion', 'ProjectInfo', 'Project/info/index', 1, 1, 1, 1, 0, '超级管理员', '2023-11-10 14:54:29', '超级管理员', '2025-03-20 00:57:03');
INSERT INTO `sys_menu` VALUES (4118, '质量中心', '', 1, 1, 0, '/quality', 'ep:coffee', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-13 16:04:04', '超级管理员', '2025-03-20 01:08:07');
INSERT INTO `sys_menu` VALUES (4119, '测试用例', 'quality:case:query', 2, 0, 4166, 'list', 'ep:burger', 'CaseList', 'Quality/case/index', 1, 1, 1, 0, 0, '超级管理员', '2023-11-13 16:05:53', '超级管理员', '2025-04-07 14:09:29');
INSERT INTO `sys_menu` VALUES (4120, '测试评审', 'quality:review:query', 2, 0, 4167, 'list', 'ep:ice-cream', 'ReviewList', 'Quality/review/index', 1, 1, 1, 0, 0, '超级管理员', '2023-11-13 16:08:46', '超级管理员', '2025-04-07 14:09:40');
INSERT INTO `sys_menu` VALUES (4121, '测试计划', 'quality:plan:query', 2, 2, 4168, 'list', 'ep:hot-water', 'PlanList', 'Quality/plan/index', 1, 1, 1, 0, 0, '超级管理员', '2023-11-13 16:09:35', '超级管理员', '2025-04-07 14:09:48');
INSERT INTO `sys_menu` VALUES (4122, '新增用例', '', 2, 99, 4166, 'add', 'ep:document-add', 'CaseAdd', 'Quality/case/CaseEditor', 1, 0, 0, 0, 0, '超级管理员', '2023-11-14 17:04:14', '超级管理员', '2025-04-07 11:28:56');
INSERT INTO `sys_menu` VALUES (4123, '编辑用例', '', 2, 99, 4166, 'edit/:id', 'ep:edit', 'CaseEdit', 'Quality/case/CaseEditor', 1, 0, 0, 0, 0, '超级管理员', '2023-11-14 17:31:39', '超级管理员', '2025-04-07 11:29:03');
INSERT INTO `sys_menu` VALUES (4124, '用例评审', '', 2, 99, 4167, ':reviewId/associated-use-cases', 'ep:link', 'ReviewAssociatedCase', 'Quality/review/associated', 1, 0, 0, 0, 0, '超级管理员', '2023-11-15 15:30:08', '超级管理员', '2025-04-07 11:40:19');
INSERT INTO `sys_menu` VALUES (4125, '测试执行', '', 2, 99, 4168, ':planId/associated-use-cases', 'fa-solid:link', 'PlanAssociatedCase', 'Quality/plan/associated', 1, 0, 0, 0, 0, '超级管理员', '2023-11-16 11:13:23', '超级管理员', '2025-04-07 11:37:00');
INSERT INTO `sys_menu` VALUES (4127, '关联用例', 'review:case:add', 3, 0, 4124, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:23:55', '超级管理员', '2023-11-29 17:23:55');
INSERT INTO `sys_menu` VALUES (4128, '解除关联', 'review:case:remove', 3, 1, 4124, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:24:39', '超级管理员', '2023-11-29 17:24:39');
INSERT INTO `sys_menu` VALUES (4129, '评审用例', 'review:case:execute', 3, 2, 4124, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:26:03', '超级管理员', '2023-11-29 17:26:03');
INSERT INTO `sys_menu` VALUES (4130, '关联用例', 'plan:case:add', 3, 0, 4125, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:26:32', '超级管理员', '2023-11-29 17:26:32');
INSERT INTO `sys_menu` VALUES (4131, '解除关联', 'plan:case:remove', 3, 1, 4125, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:26:48', '超级管理员', '2023-11-29 17:26:48');
INSERT INTO `sys_menu` VALUES (4132, '执行测试', 'plan:case:execute', 3, 2, 4125, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:27:07', '超级管理员', '2023-11-29 17:27:07');
INSERT INTO `sys_menu` VALUES (4133, '新增', 'quality:plan:add', 3, 0, 4121, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:32:05', '超级管理员', '2025-03-20 01:06:39');
INSERT INTO `sys_menu` VALUES (4134, '修改', 'quality:plan:update', 3, 1, 4121, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:32:25', '超级管理员', '2025-03-20 01:06:42');
INSERT INTO `sys_menu` VALUES (4135, '删除', 'quality:plan:remove', 3, 2, 4121, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:32:45', '超级管理员', '2025-03-20 01:06:45');
INSERT INTO `sys_menu` VALUES (4136, '规划&执行', 'quality:plan:execute', 3, 3, 4121, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:33:57', '超级管理员', '2025-03-20 01:06:48');
INSERT INTO `sys_menu` VALUES (4137, '新增', 'quality:review:add', 3, 0, 4120, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:35:02', '超级管理员', '2025-03-20 01:06:51');
INSERT INTO `sys_menu` VALUES (4138, '修改', 'quality:review:update', 3, 1, 4120, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:35:13', '超级管理员', '2025-03-20 01:06:56');
INSERT INTO `sys_menu` VALUES (4139, '删除', 'quality:review:remove', 3, 2, 4120, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:35:28', '超级管理员', '2025-03-20 01:07:01');
INSERT INTO `sys_menu` VALUES (4140, '规划&执行', 'quality:review:execute', 3, 3, 4120, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-11-29 17:35:50', '超级管理员', '2025-03-20 01:07:05');
INSERT INTO `sys_menu` VALUES (4141, '归档详情', '', 2, 99, 4110, 'archive/:id', 'fa:file-archive-o', 'ArchiveDetail', 'Project/archive/detail', 1, 0, 0, 0, 0, '超级管理员', '2023-12-05 16:32:09', '超级管理员', '2025-03-20 00:58:44');
INSERT INTO `sys_menu` VALUES (4144, '成员管理', 'project:member:query', 3, 10, 4115, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-20 15:38:55', '超级管理员', '2025-03-20 15:38:55');
INSERT INTO `sys_menu` VALUES (4145, '新增成员', 'project:member:add', 3, 11, 4115, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-20 15:39:51', '超级管理员', '2025-03-20 15:39:51');
INSERT INTO `sys_menu` VALUES (4146, '修改成员', 'project:member:update', 3, 12, 4115, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-20 15:40:21', '超级管理员', '2025-03-20 15:40:21');
INSERT INTO `sys_menu` VALUES (4147, '删除成员', 'project:member:remove', 3, 13, 4115, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-20 15:40:45', '超级管理员', '2025-03-20 15:40:45');
INSERT INTO `sys_menu` VALUES (4148, '模块管理', 'project:node:query', 3, 20, 4115, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-21 08:42:49', '超级管理员', '2025-03-21 08:42:49');
INSERT INTO `sys_menu` VALUES (4149, '新增模块', 'project:node:add', 3, 21, 4115, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-21 08:43:30', '超级管理员', '2025-03-21 08:43:30');
INSERT INTO `sys_menu` VALUES (4150, '修改模块', 'project:node:update', 3, 22, 4115, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-21 08:43:56', '超级管理员', '2025-03-21 08:43:56');
INSERT INTO `sys_menu` VALUES (4151, '删除模块', 'project:node:remove', 3, 23, 4115, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-21 08:44:20', '超级管理员', '2025-03-21 08:44:20');
INSERT INTO `sys_menu` VALUES (4152, '缺陷跟踪', 'quality:bug:query', 2, 0, 4169, 'list', 'fa:bug', 'BugManagement', 'Quality/bug/index', 1, 1, 1, 0, 0, '超级管理员', '2025-03-26 09:30:35', '超级管理员', '2025-04-07 14:07:48');
INSERT INTO `sys_menu` VALUES (4153, '新增', 'quality:bug:add', 3, 0, 4152, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-26 15:51:50', '超级管理员', '2025-03-26 15:51:50');
INSERT INTO `sys_menu` VALUES (4154, '修改', 'quality:bug:update', 3, 1, 4152, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-26 15:56:50', '超级管理员', '2025-03-26 15:56:50');
INSERT INTO `sys_menu` VALUES (4155, '删除', 'quality:bug:remove', 3, 2, 4152, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-26 15:57:06', '超级管理员', '2025-03-26 15:57:06');
INSERT INTO `sys_menu` VALUES (4156, '确认', 'quality:bug:confirm', 3, 3, 4152, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-26 15:57:24', '超级管理员', '2025-03-26 15:57:24');
INSERT INTO `sys_menu` VALUES (4157, '修复', 'quality:bug:fix', 3, 4, 4152, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-26 15:57:50', '超级管理员', '2025-03-26 15:57:50');
INSERT INTO `sys_menu` VALUES (4158, '激活', 'quality:bug:reopen', 3, 5, 4152, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-26 15:58:07', '超级管理员', '2025-03-26 15:58:07');
INSERT INTO `sys_menu` VALUES (4159, '关闭', 'quality:bug:close', 3, 6, 4152, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-03-26 15:58:30', '超级管理员', '2025-03-26 15:58:30');
INSERT INTO `sys_menu` VALUES (4160, '新增缺陷', '', 2, 99, 4169, 'add', 'fa:bug', 'BugAdd', 'Quality/bug/BugEditor', 1, 0, 0, 0, 0, '超级管理员', '2025-03-26 16:02:48', '超级管理员', '2025-04-07 11:39:15');
INSERT INTO `sys_menu` VALUES (4161, '修改缺陷', '', 2, 99, 4169, 'edit/:id', 'fa:bug', 'BugEdit', 'Quality/bug/BugEditor', 1, 0, 0, 0, 0, '超级管理员', '2025-03-26 16:04:31', '超级管理员', '2025-04-07 11:39:12');
INSERT INTO `sys_menu` VALUES (4162, '缺陷明细', '', 2, 99, 4169, 'view/:id', 'fa:bug', 'BugViewer', 'Quality/bug/BugViewer', 1, 0, 1, 1, 0, '超级管理员', '2025-03-31 15:59:41', '超级管理员', '2025-04-07 11:39:06');
INSERT INTO `sys_menu` VALUES (4163, '新增', 'quality:case:add', 3, 0, 4119, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-04-01 09:44:41', '超级管理员', '2025-04-01 09:44:41');
INSERT INTO `sys_menu` VALUES (4164, '修改', 'quality:case:update', 3, 1, 4119, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-04-01 09:44:54', '超级管理员', '2025-04-01 09:44:54');
INSERT INTO `sys_menu` VALUES (4165, '删除', 'quality:case:remove', 3, 2, 4119, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-04-01 09:45:08', '超级管理员', '2025-04-01 09:45:08');
INSERT INTO `sys_menu` VALUES (4166, '测试用例 ', '', 1, 0, 4118, 'test-case', 'ep:burger', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-04-07 11:19:24', '超级管理员', '2025-04-07 11:34:58');
INSERT INTO `sys_menu` VALUES (4167, '测试评审 ', '', 1, 10, 4118, 'test-review', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-04-07 11:32:33', '超级管理员', '2025-04-07 11:34:25');
INSERT INTO `sys_menu` VALUES (4168, '测试计划  ', '', 1, 20, 4118, 'test-plan', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-04-07 11:35:38', '超级管理员', '2025-04-07 11:35:38');
INSERT INTO `sys_menu` VALUES (4169, '缺陷跟踪 ', '', 1, 30, 4118, 'bug', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-04-07 11:37:39', '超级管理员', '2025-04-07 11:37:39');
INSERT INTO `sys_menu` VALUES (4170, '拒绝', 'quality:bug:reject', 3, 7, 4152, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2025-04-07 11:49:03', '超级管理员', '2025-04-07 11:49:03');

SET FOREIGN_KEY_CHECKS = 1;

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
-- Records of sys_menu
-- ----------------------------
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

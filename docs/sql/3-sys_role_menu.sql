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

 Date: 30/11/2023 09:13:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint NOT NULL COMMENT '角色id',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 3, 1, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (2, 3, 4110, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (3, 3, 4111, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (4, 3, 4112, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (5, 3, 4113, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (6, 3, 4114, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (7, 3, 4115, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (8, 3, 4116, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (9, 3, 4117, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (10, 3, 4118, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (11, 3, 4119, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (12, 3, 4120, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (13, 3, 4121, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (14, 3, 4122, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (15, 3, 4123, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (16, 3, 4124, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (17, 3, 4125, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (18, 3, 4126, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (19, 3, 4127, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (20, 3, 4128, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (21, 3, 4129, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (22, 3, 4130, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (23, 3, 4131, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (24, 3, 100, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (25, 3, 4132, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (26, 3, 4133, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (27, 3, 4134, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (28, 3, 4135, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (29, 3, 4136, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (30, 3, 4137, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (31, 3, 4138, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (32, 3, 4139, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (33, 3, 4140, 0, '超级管理员', '2023-11-30 09:11:49', '超级管理员', '2023-11-30 09:11:49');
INSERT INTO `sys_role_menu` VALUES (34, 4, 1, 0, '超级管理员', '2023-11-30 09:12:14', '超级管理员', '2023-11-30 09:12:14');
INSERT INTO `sys_role_menu` VALUES (35, 4, 4129, 0, '超级管理员', '2023-11-30 09:12:14', '超级管理员', '2023-11-30 09:12:14');
INSERT INTO `sys_role_menu` VALUES (36, 4, 100, 0, '超级管理员', '2023-11-30 09:12:14', '超级管理员', '2023-11-30 09:12:14');
INSERT INTO `sys_role_menu` VALUES (37, 4, 4132, 0, '超级管理员', '2023-11-30 09:12:14', '超级管理员', '2023-11-30 09:12:14');
INSERT INTO `sys_role_menu` VALUES (38, 4, 4118, 0, '超级管理员', '2023-11-30 09:12:14', '超级管理员', '2023-11-30 09:12:14');
INSERT INTO `sys_role_menu` VALUES (39, 4, 4136, 0, '超级管理员', '2023-11-30 09:12:14', '超级管理员', '2023-11-30 09:12:14');
INSERT INTO `sys_role_menu` VALUES (40, 4, 4120, 0, '超级管理员', '2023-11-30 09:12:14', '超级管理员', '2023-11-30 09:12:14');
INSERT INTO `sys_role_menu` VALUES (41, 4, 4121, 0, '超级管理员', '2023-11-30 09:12:14', '超级管理员', '2023-11-30 09:12:14');
INSERT INTO `sys_role_menu` VALUES (42, 4, 4140, 0, '超级管理员', '2023-11-30 09:12:14', '超级管理员', '2023-11-30 09:12:14');
INSERT INTO `sys_role_menu` VALUES (43, 4, 4124, 0, '超级管理员', '2023-11-30 09:12:14', '超级管理员', '2023-11-30 09:12:14');
INSERT INTO `sys_role_menu` VALUES (44, 4, 4125, 0, '超级管理员', '2023-11-30 09:12:14', '超级管理员', '2023-11-30 09:12:14');

SET FOREIGN_KEY_CHECKS = 1;

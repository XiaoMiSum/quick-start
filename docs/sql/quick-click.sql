/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80405 (8.4.5)
 Source Host           : localhost:3306
 Source Schema         : quick-click

 Target Server Type    : MySQL
 Target Server Version : 80405 (8.4.5)
 File Encoding         : 65001

 Date: 27/06/2025 10:18:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for infra_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `infra_dictionary`;
CREATE TABLE `infra_dictionary`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典键值',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典名称',
  `source` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '来源系统',
  `status` tinyint NULL DEFAULT 1,
  `deleted` bit(1) NULL DEFAULT b'0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`code` ASC, `name` ASC, `source` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_dictionary
-- ----------------------------
INSERT INTO `infra_dictionary` VALUES (1, 'common_status', '状态', 'ones', 1, b'0', '超级管理员', '2024-05-23 11:04:26', '超级管理员', '2024-05-23 14:47:43');
INSERT INTO `infra_dictionary` VALUES (2, 'system_role_type', '角色类型', 'ones', 1, b'0', '超级管理员', '2024-05-23 14:55:32', '超级管理员', '2024-05-23 14:55:32');
INSERT INTO `infra_dictionary` VALUES (3, 'system_user_gender', '性别', 'ones', 1, b'0', '超级管理员', '2024-05-23 15:29:04', '超级管理员', '2024-05-23 15:29:04');
INSERT INTO `infra_dictionary` VALUES (4, 'system_menu_type', '菜单类型', 'ones', 1, b'0', '超级管理员', '2024-05-23 15:37:52', '超级管理员', '2024-05-23 15:37:52');
INSERT INTO `infra_dictionary` VALUES (5, 'infra_job_status', '定时任务状态', 'ones', 1, b'0', '超级管理员', '2024-05-23 15:45:29', '超级管理员', '2024-05-23 15:45:29');
INSERT INTO `infra_dictionary` VALUES (6, 'infra_job_log_status', '定时任务调度状态', 'ones', 1, b'0', '超级管理员', '2024-05-23 15:45:59', '超级管理员', '2024-05-23 15:49:54');
INSERT INTO `infra_dictionary` VALUES (7, 'infra_api_error_log_process_status', '异常日志处理状态', 'ones', 1, b'0', '超级管理员', '2024-05-23 15:46:22', '超级管理员', '2024-05-23 15:46:22');
INSERT INTO `infra_dictionary` VALUES (8, 'infra_file_storage', '文件存储类型', 'ones', 1, b'0', '超级管理员', '2024-05-23 15:50:50', '超级管理员', '2024-05-23 15:50:50');
INSERT INTO `infra_dictionary` VALUES (9, 'infra_boolean_string', '布尔类型字符', 'ones', 1, b'0', '超级管理员', '2024-05-23 15:51:11', '超级管理员', '2024-05-23 15:51:11');
INSERT INTO `infra_dictionary` VALUES (10, 'infra_sms_send_status', '短信发送状态', 'ones', 1, b'0', '超级管理员', '2024-05-23 16:21:22', '超级管理员', '2024-05-23 16:21:22');
INSERT INTO `infra_dictionary` VALUES (11, 'infra_sms_receive_status', '短信接收状态', 'ones', 1, b'0', '超级管理员', '2024-05-23 16:21:46', '超级管理员', '2024-05-23 16:21:46');

-- ----------------------------
-- Table structure for infra_dictionary_value
-- ----------------------------
DROP TABLE IF EXISTS `infra_dictionary_value`;
CREATE TABLE `infra_dictionary_value`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `dict_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属字典',
  `label` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '页面展示名称',
  `value` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典数据值',
  `status` tinyint NULL DEFAULT 1,
  `color_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sort` int NULL DEFAULT 0,
  `deleted` bit(1) NULL DEFAULT b'0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx_dict_key`(`dict_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_dictionary_value
-- ----------------------------
INSERT INTO `infra_dictionary_value` VALUES (1, 'common_status', '启用', '1', 1, 'success', 0, b'0', '超级管理员', '2024-05-23 11:30:28', '超级管理员', '2024-05-23 14:47:51');
INSERT INTO `infra_dictionary_value` VALUES (2, 'common_status', '禁用', '0', 1, 'danger', 1, b'0', '超级管理员', '2024-05-23 11:38:25', '超级管理员', '2024-05-23 11:38:25');
INSERT INTO `infra_dictionary_value` VALUES (3, 'system_role_type', '内置角色', '1', 1, 'danger', 0, b'0', '超级管理员', '2024-05-23 14:56:15', '超级管理员', '2024-05-23 14:56:15');
INSERT INTO `infra_dictionary_value` VALUES (4, 'system_role_type', '自定义角色', '2', 1, 'primary', 1, b'0', '超级管理员', '2024-05-23 14:56:37', '超级管理员', '2024-05-23 14:56:44');
INSERT INTO `infra_dictionary_value` VALUES (5, 'system_user_gender', '男', '1', 1, 'default', 0, b'0', '超级管理员', '2024-05-23 15:29:43', '超级管理员', '2024-05-23 15:29:43');
INSERT INTO `infra_dictionary_value` VALUES (6, 'system_user_gender', '女', '0', 1, 'default', 1, b'0', '超级管理员', '2024-05-23 15:30:00', '超级管理员', '2024-05-23 15:30:00');
INSERT INTO `infra_dictionary_value` VALUES (7, 'system_menu_type', '目录', '1', 1, 'danger', 0, b'0', '超级管理员', '2024-05-23 15:39:51', '超级管理员', '2024-05-23 15:40:28');
INSERT INTO `infra_dictionary_value` VALUES (8, 'system_menu_type', '菜单', '2', 1, 'success', 1, b'0', '超级管理员', '2024-05-23 15:40:02', '超级管理员', '2024-05-23 15:40:25');
INSERT INTO `infra_dictionary_value` VALUES (9, 'system_menu_type', '按钮', '3', 1, 'info', 2, b'0', '超级管理员', '2024-05-23 15:40:19', '超级管理员', '2024-05-23 15:40:19');
INSERT INTO `infra_dictionary_value` VALUES (10, 'infra_job_status', '初始化', '0', 1, 'info', 0, b'0', '超级管理员', '2024-05-23 15:47:31', '超级管理员', '2024-05-23 15:47:31');
INSERT INTO `infra_dictionary_value` VALUES (11, 'infra_job_status', '运行中', '1', 1, 'success', 1, b'0', '超级管理员', '2024-05-23 15:47:45', '超级管理员', '2024-05-23 15:47:45');
INSERT INTO `infra_dictionary_value` VALUES (12, 'infra_job_status', '已暂停', '2', 1, 'danger', 2, b'0', '超级管理员', '2024-05-23 15:47:59', '超级管理员', '2024-05-23 15:47:59');
INSERT INTO `infra_dictionary_value` VALUES (13, 'infra_job_log_status', '调度中', '0', 1, 'info', 0, b'0', '超级管理员', '2024-05-23 15:48:33', '超级管理员', '2024-05-23 15:48:33');
INSERT INTO `infra_dictionary_value` VALUES (14, 'infra_job_log_status', '调度成功', '1', 1, 'success', 1, b'0', '超级管理员', '2024-05-23 15:48:41', '超级管理员', '2024-05-23 15:48:41');
INSERT INTO `infra_dictionary_value` VALUES (15, 'infra_job_log_status', '调度失败', '2', 1, 'danger', 2, b'0', '超级管理员', '2024-05-23 15:48:51', '超级管理员', '2024-05-23 15:48:51');
INSERT INTO `infra_dictionary_value` VALUES (16, 'infra_api_error_log_process_status', '未处理', '0', 1, 'danger', 0, b'0', '超级管理员', '2024-05-23 15:49:18', '超级管理员', '2024-05-23 15:49:18');
INSERT INTO `infra_dictionary_value` VALUES (17, 'infra_api_error_log_process_status', '已处理', '1', 1, 'success', 1, b'0', '超级管理员', '2024-05-23 15:49:30', '超级管理员', '2024-05-23 15:49:30');
INSERT INTO `infra_dictionary_value` VALUES (18, 'infra_api_error_log_process_status', '已忽略', '2', 1, 'info', 2, b'0', '超级管理员', '2024-05-23 15:49:43', '超级管理员', '2024-05-23 15:49:43');
INSERT INTO `infra_dictionary_value` VALUES (19, 'infra_file_storage', '数据库', '1', 1, 'default', 0, b'0', '超级管理员', '2024-05-23 15:52:23', '超级管理员', '2024-05-23 15:52:23');
INSERT INTO `infra_dictionary_value` VALUES (20, 'infra_file_storage', '本地存储', '10', 1, 'warning', 10, b'0', '超级管理员', '2024-05-23 15:52:39', '超级管理员', '2024-05-23 15:52:39');
INSERT INTO `infra_dictionary_value` VALUES (21, 'infra_file_storage', 'S3协议', '20', 1, 'danger', 20, b'0', '超级管理员', '2024-05-23 15:52:56', '超级管理员', '2024-05-23 15:52:56');
INSERT INTO `infra_dictionary_value` VALUES (22, 'infra_boolean_string', '是', 'true', 1, 'success', 0, b'0', '超级管理员', '2024-05-23 15:53:39', '超级管理员', '2024-05-23 15:53:39');
INSERT INTO `infra_dictionary_value` VALUES (23, 'infra_boolean_string', '否', 'false', 1, 'info', 1, b'0', '超级管理员', '2024-05-23 15:53:54', '超级管理员', '2024-05-23 15:53:54');
INSERT INTO `infra_dictionary_value` VALUES (24, 'infra_sms_send_status', '初始化', '0', 1, 'info', 0, b'0', '超级管理员', '2024-05-23 16:22:19', '超级管理员', '2024-05-23 16:22:19');
INSERT INTO `infra_dictionary_value` VALUES (25, 'infra_sms_send_status', '发送成功', '10', 1, 'success', 10, b'0', '超级管理员', '2024-05-23 16:22:40', '超级管理员', '2024-05-23 16:22:40');
INSERT INTO `infra_dictionary_value` VALUES (26, 'infra_sms_send_status', '发送失败', '20', 1, 'danger', 20, b'0', '超级管理员', '2024-05-23 16:22:52', '超级管理员', '2024-05-23 16:22:52');
INSERT INTO `infra_dictionary_value` VALUES (27, 'infra_sms_send_status', '仅日志', '30', 1, 'warning', 30, b'0', '超级管理员', '2024-05-23 16:23:35', '超级管理员', '2024-05-23 16:23:35');
INSERT INTO `infra_dictionary_value` VALUES (28, 'infra_sms_receive_status', '初始化', '0', 1, 'info', 0, b'0', '超级管理员', '2024-05-23 16:24:21', '超级管理员', '2024-05-23 16:24:21');
INSERT INTO `infra_dictionary_value` VALUES (29, 'infra_sms_receive_status', '接收成功', '10', 1, 'success', 10, b'0', '超级管理员', '2024-05-23 16:24:36', '超级管理员', '2024-05-23 16:24:36');
INSERT INTO `infra_dictionary_value` VALUES (30, 'infra_sms_receive_status', '接收失败', '20', 1, 'danger', 20, b'0', '超级管理员', '2024-05-23 16:24:46', '超级管理员', '2024-05-23 16:24:46');

-- ----------------------------
-- Table structure for infra_error_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_error_log`;
CREATE TABLE `infra_error_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `application_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '应用名称',
  `request_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方法',
  `request_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求地址',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数',
  `user_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '来源ip',
  `exception_time` datetime NULL DEFAULT NULL COMMENT '异常时间',
  `exception_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '异常名称',
  `exception_class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '异常类',
  `exception_file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '异常文件',
  `exception_method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '异常方法',
  `exception_line_number` int NULL DEFAULT NULL COMMENT '异常行',
  `exception_stack_trace` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '堆栈信息',
  `exception_root_cause_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `exception_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `status` tinyint NULL DEFAULT 0,
  `deleted` tinyint NULL DEFAULT 0,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '接口异常表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_error_log
-- ----------------------------
INSERT INTO `infra_error_log` VALUES (1, 'quick-click-server', 'GET', '/quick-click-server/self/profile', '{\"query\":{},\"body\":\"\"}', '0:0:0:0:0:0:0:1', '2025-06-27 10:06:26', 'org.springframework.jdbc.BadSqlGrammarException', 'org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator', 'SQLErrorCodeSQLExceptionTranslator.java', 'doTranslate', 246, 'org.springframework.jdbc.BadSqlGrammarException: \r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'quick-click.sys_user_profile\' doesn\'t exist\r\n### The error may exist in io/github/xiaomisum/quickclick/dal/mapper/profile/ProfileMapper.java (best guess)\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT    id,user_id,project_id,create_time,update_time,creator,updater,deleted    FROM  sys_user_profile     WHERE deleted=0     AND (user_id = ?)\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'quick-click.sys_user_profile\' doesn\'t exist\n; bad SQL grammar []\r\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:246)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:107)\r\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:92)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:439)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy112.selectList(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate.selectList(SqlSessionTemplate.java:224)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperMethod.executeForMany(MybatisMapperMethod.java:164)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperMethod.execute(MybatisMapperMethod.java:77)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy$PlainMethodInvoker.invoke(MybatisMapperProxy.java:152)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy.invoke(MybatisMapperProxy.java:89)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy179.selectList(Unknown Source)\r\n	at com.baomidou.mybatisplus.core.mapper.BaseMapper.selectOne(BaseMapper.java:306)\r\n	at java.base/java.lang.invoke.MethodHandle.invokeWithArguments(MethodHandle.java:733)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy$DefaultMethodInvoker.invoke(MybatisMapperProxy.java:166)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy.invoke(MybatisMapperProxy.java:89)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy179.selectOne(Unknown Source)\r\n	at com.baomidou.mybatisplus.core.mapper.BaseMapper.selectOne(BaseMapper.java:295)\r\n	at java.base/java.lang.invoke.MethodHandle.invokeWithArguments(MethodHandle.java:733)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy$DefaultMethodInvoker.invoke(MybatisMapperProxy.java:166)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy.invoke(MybatisMapperProxy.java:89)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy179.selectOne(Unknown Source)\r\n	at xyz.migoo.framework.mybatis.core.BaseMapperX.selectOne(BaseMapperX.java:48)\r\n	at java.base/java.lang.invoke.MethodHandle.invokeWithArguments(MethodHandle.java:733)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy$DefaultMethodInvoker.invoke(MybatisMapperProxy.java:166)\r\n	at com.baomidou.mybatisplus.', 'SQLSyntaxErrorException: Table \'quick-click.sys_user_profile\' doesn\'t exist', 'BadSqlGrammarException: \r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'quick-click.sys_user_profile\' doesn\'t exist\r\n### The error may exist in io/github/xiaomisum/quickclick/dal/mapper/profile/ProfileMapper.java (best guess)\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT    id,user_id,project_id,create_time,update_time,creator,updater,deleted    FROM  sys_user_profile     WHERE deleted=0     AND (user_id = ?)\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'quick-click.sys_user_profile\' doesn\'t exist\n; bad SQL grammar []', 0, 0, '超级管理员', '2025-06-27 10:06:26', '超级管理员', '2025-06-27 10:06:26');
INSERT INTO `infra_error_log` VALUES (2, 'quick-click-server', 'GET', '/quick-click-server/self/todo', '{\"query\":{},\"body\":\"\"}', '0:0:0:0:0:0:0:1', '2025-06-27 10:10:18', 'org.springframework.jdbc.BadSqlGrammarException', 'org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator', 'SQLErrorCodeSQLExceptionTranslator.java', 'doTranslate', 246, 'org.springframework.jdbc.BadSqlGrammarException: \r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'quick-click.qc_quality_test_plan\' doesn\'t exist\r\n### The error may exist in io/github/xiaomisum/quickclick/dal/mapper/qualitycenter/PlanMapper.java (best guess)\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT COUNT( * ) AS total FROM  qc_quality_test_plan     WHERE deleted=0     AND (executor = ? AND status IN (?,?))\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'quick-click.qc_quality_test_plan\' doesn\'t exist\n; bad SQL grammar []\r\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:246)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:107)\r\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:92)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:439)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy112.selectOne(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate.selectOne(SqlSessionTemplate.java:160)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperMethod.execute(MybatisMapperMethod.java:87)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy$PlainMethodInvoker.invoke(MybatisMapperProxy.java:152)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy.invoke(MybatisMapperProxy.java:89)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy171.selectCount(Unknown Source)\r\n	at io.github.xiaomisum.quickclick.dal.mapper.qualitycenter.PlanMapper.selectCount(PlanMapper.java:82)\r\n	at java.base/java.lang.invoke.MethodHandle.invokeWithArguments(MethodHandle.java:733)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy$DefaultMethodInvoker.invoke(MybatisMapperProxy.java:166)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy.invoke(MybatisMapperProxy.java:89)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy171.selectCount(Unknown Source)\r\n	at io.github.xiaomisum.quickclick.service.qualitycenter.plan.PlanServiceImpl.count(PlanServiceImpl.java:90)\r\n	at io.github.xiaomisum.quickclick.controller.self.SelfController.getTodo(SelfController.java:125)\r\n	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)\r\n	at java.base/java.lang.reflect.Method.invoke(Method.java:580)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:258)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:191)\r\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandle', 'SQLSyntaxErrorException: Table \'quick-click.qc_quality_test_plan\' doesn\'t exist', 'BadSqlGrammarException: \r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Table \'quick-click.qc_quality_test_plan\' doesn\'t exist\r\n### The error may exist in io/github/xiaomisum/quickclick/dal/mapper/qualitycenter/PlanMapper.java (best guess)\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: SELECT COUNT( * ) AS total FROM  qc_quality_test_plan     WHERE deleted=0     AND (executor = ? AND status IN (?,?))\r\n### Cause: java.sql.SQLSyntaxErrorException: Table \'quick-click.qc_quality_test_plan\' doesn\'t exist\n; bad SQL grammar []', 0, 0, '超级管理员', '2025-06-27 10:10:18', '超级管理员', '2025-06-27 10:10:18');

-- ----------------------------
-- Table structure for infra_file
-- ----------------------------
DROP TABLE IF EXISTS `infra_file`;
CREATE TABLE `infra_file`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文件编号',
  `config_id` bigint NULL DEFAULT NULL COMMENT '配置编号',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件路径',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件 URL',
  `type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件类型',
  `source` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件来源',
  `size` int NOT NULL COMMENT '文件大小',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_file
-- ----------------------------

-- ----------------------------
-- Table structure for infra_file_config
-- ----------------------------
DROP TABLE IF EXISTS `infra_file_config`;
CREATE TABLE `infra_file_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置名',
  `storage` tinyint NOT NULL COMMENT '存储器',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `master` bit(1) NOT NULL COMMENT '是否为主配置',
  `config` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '存储配置',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_file_config
-- ----------------------------

-- ----------------------------
-- Table structure for infra_file_content
-- ----------------------------
DROP TABLE IF EXISTS `infra_file_content`;
CREATE TABLE `infra_file_content`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `config_id` bigint NOT NULL COMMENT '配置编号',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件路径',
  `content` mediumblob NOT NULL COMMENT '文件内容',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of infra_file_content
-- ----------------------------

-- ----------------------------
-- Table structure for infra_job
-- ----------------------------
DROP TABLE IF EXISTS `infra_job`;
CREATE TABLE `infra_job`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称',
  `status` tinyint NOT NULL COMMENT '任务状态',
  `handler_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '处理器的名字',
  `handler_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '处理器的参数',
  `cron_expression` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'CRON 表达式',
  `retry_count` int NOT NULL DEFAULT 0 COMMENT '重试次数',
  `retry_interval` int NOT NULL DEFAULT 0 COMMENT '重试间隔',
  `monitor_timeout` int NOT NULL DEFAULT 0 COMMENT '监控超时时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_job
-- ----------------------------

-- ----------------------------
-- Table structure for infra_job_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_job_log`;
CREATE TABLE `infra_job_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `job_id` bigint NOT NULL COMMENT '任务编号',
  `handler_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '处理器的名字',
  `handler_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '处理器的参数',
  `execute_index` tinyint NOT NULL DEFAULT 1 COMMENT '第几次执行',
  `begin_time` datetime NOT NULL COMMENT '开始执行时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束执行时间',
  `duration` int NULL DEFAULT NULL COMMENT '执行时长',
  `status` tinyint NOT NULL COMMENT '任务状态',
  `result` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '结果数据',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for infra_sms_channel
-- ----------------------------
DROP TABLE IF EXISTS `infra_sms_channel`;
CREATE TABLE `infra_sms_channel`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `signature` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '短信签名',
  `code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '三方编码',
  `status` tinyint NULL DEFAULT 1 COMMENT '开启状态',
  `api_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '短信 API 的账号',
  `api_secret` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '短信 API 的秘钥',
  `callback_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '短信发送回调 URL',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '短信三方' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_sms_channel
-- ----------------------------
INSERT INTO `infra_sms_channel` VALUES (1, 'BARK', 'BARK', 1, 'BARK', 'BARK', 'http://BARK.top', NULL, '超级管理员', '2023-09-23 15:28:36', '超级管理员', '2023-09-23 15:30:34', b'0');

-- ----------------------------
-- Table structure for infra_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `infra_sms_log`;
CREATE TABLE `infra_sms_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `channel_id` bigint NOT NULL COMMENT '短信三方编号',
  `channel_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '短信三方编码',
  `template_id` bigint NOT NULL COMMENT '模板编号',
  `template_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板编码',
  `template_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '短信内容',
  `template_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '短信参数',
  `api_template_id` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '短信 API 的模板编号',
  `mobile` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户编号',
  `user_type` tinyint NULL DEFAULT NULL COMMENT '用户类型',
  `send_status` tinyint NOT NULL DEFAULT 0 COMMENT '发送状态',
  `send_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `send_code` int NULL DEFAULT NULL COMMENT '发送结果的编码',
  `send_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发送结果的提示',
  `api_send_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '短信 API 发送结果的编码',
  `api_send_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '短信 API 发送失败的提示',
  `api_request_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '短信 API 发送返回的唯一请求 ID',
  `api_serial_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '短信 API 发送返回的序号',
  `receive_status` tinyint NOT NULL DEFAULT 0 COMMENT '接收状态',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '接收时间',
  `api_receive_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'API 接收结果的编码',
  `api_receive_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'API 接收结果的说明',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '短信日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_sms_log
-- ----------------------------

-- ----------------------------
-- Table structure for infra_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `infra_sms_template`;
CREATE TABLE `infra_sms_template`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `status` tinyint NULL DEFAULT 1 COMMENT '开启状态',
  `code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板编码',
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板名称',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板内容',
  `params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参数数组',
  `api_template_id` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '短信 API 的模板编号',
  `channel_id` bigint NOT NULL COMMENT '短信三方编号',
  `channel_code` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '短信三方编码',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '短信模板' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of infra_sms_template
-- ----------------------------

-- ----------------------------
-- Table structure for qc_production_line
-- ----------------------------
DROP TABLE IF EXISTS `qc_production_line`;
CREATE TABLE `qc_production_line`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `manager` bigint NOT NULL COMMENT '总监',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint(1) NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品线信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qc_production_line
-- ----------------------------

-- ----------------------------
-- Table structure for qc_project
-- ----------------------------
DROP TABLE IF EXISTS `qc_project`;
CREATE TABLE `qc_project`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目名称',
  `production_line_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产线编号',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_title`(`title` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '项目信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_project
-- ----------------------------

-- ----------------------------
-- Table structure for qc_project_archive
-- ----------------------------
DROP TABLE IF EXISTS `qc_project_archive`;
CREATE TABLE `qc_project_archive`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '归属项目',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '归档名称',
  `deleted` tinyint NOT NULL DEFAULT 0,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx_title_project`(`project_id` ASC, `title` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '项目归档表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_project_archive
-- ----------------------------

-- ----------------------------
-- Table structure for qc_project_archive_node
-- ----------------------------
DROP TABLE IF EXISTS `qc_project_archive_node`;
CREATE TABLE `qc_project_archive_node`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `archive_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '归档id',
  `original_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块原始id',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '上级id',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块名称',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块路径',
  `sort` int NOT NULL COMMENT '排序',
  `deleted` tinyint NOT NULL DEFAULT 0,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx_archive`(`archive_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '归档模块表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_project_archive_node
-- ----------------------------

-- ----------------------------
-- Table structure for qc_project_archive_testcase
-- ----------------------------
DROP TABLE IF EXISTS `qc_project_archive_testcase`;
CREATE TABLE `qc_project_archive_testcase`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `archive_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '归档id',
  `original_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '测试用例原始id',
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `node_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模块路径',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
  `priority` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优先级',
  `prerequisite` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '前置条件',
  `steps` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行步骤',
  `supervisor` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '负责人',
  `last_review_result` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后一次评审结果',
  `last_review_time` datetime NULL DEFAULT NULL COMMENT '最后一次评审时间',
  `deleted` tinyint NOT NULL DEFAULT 0,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`original_id` ASC, `project_id` ASC, `node_id` ASC, `title` ASC, `priority` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '归档用例表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_project_archive_testcase
-- ----------------------------

-- ----------------------------
-- Table structure for qc_project_member
-- ----------------------------
DROP TABLE IF EXISTS `qc_project_member`;
CREATE TABLE `qc_project_member`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `post_id` bigint NULL DEFAULT NULL COMMENT '岗位id',
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目id',
  `deleted` tinyint NOT NULL DEFAULT 0,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`project_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '项目成员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_project_member
-- ----------------------------

-- ----------------------------
-- Table structure for qc_project_node
-- ----------------------------
DROP TABLE IF EXISTS `qc_project_node`;
CREATE TABLE `qc_project_node`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '归属项目',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '父节点id',
  `title` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '节点名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路径',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`project_id` ASC, `title` ASC, `sort` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '项目模块表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_project_node
-- ----------------------------

-- ----------------------------
-- Table structure for qc_quality_bug
-- ----------------------------
DROP TABLE IF EXISTS `qc_quality_bug`;
CREATE TABLE `qc_quality_bug`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `plan_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `testcase_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `node_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `severity` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '严重程度',
  `priority` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优先级',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `supervisor` bigint NOT NULL COMMENT '责任人',
  `handler` bigint NULL DEFAULT NULL COMMENT '处理人',
  `assigned_time` datetime NOT NULL COMMENT '指派时间',
  `confirmed_time` datetime NULL DEFAULT NULL COMMENT '确认时间',
  `fixer` bigint NULL DEFAULT NULL COMMENT '修复人',
  `fixed_time` datetime NULL DEFAULT NULL COMMENT '修复时间',
  `fix_duration` int NULL DEFAULT NULL COMMENT '修复时长',
  `closer` bigint NULL DEFAULT NULL COMMENT '关闭人',
  `closed_time` datetime NULL DEFAULT NULL COMMENT '关闭时间',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'New' COMMENT '状态',
  `cause` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '产生原因',
  `root_cause` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '产生原因详细描述',
  `solution` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '解决方案',
  `reopened_times` int NULL DEFAULT 0 COMMENT '激活次数',
  `source` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '来源',
  `deleted` tinyint NULL DEFAULT 0,
  `creator_id` bigint NOT NULL COMMENT '创建人User_Id',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`project_id` ASC, `plan_id` ASC, `testcase_id` ASC, `node_id` ASC, `title` ASC, `severity` ASC, `priority` ASC, `supervisor` ASC, `handler` ASC, `assigned_time` ASC, `fixer` ASC, `closer` ASC, `status` ASC, `source` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '质量缺陷表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_quality_bug
-- ----------------------------

-- ----------------------------
-- Table structure for qc_quality_bug_exec_record
-- ----------------------------
DROP TABLE IF EXISTS `qc_quality_bug_exec_record`;
CREATE TABLE `qc_quality_bug_exec_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bug_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '缺陷编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `operation` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作类型',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `deleted` tinyint NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`bug_id` ASC, `user_id` ASC, `operation` ASC, `create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '缺陷评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_quality_bug_exec_record
-- ----------------------------

-- ----------------------------
-- Table structure for qc_quality_bug_unclosed_record
-- ----------------------------
DROP TABLE IF EXISTS `qc_quality_bug_unclosed_record`;
CREATE TABLE `qc_quality_bug_unclosed_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'New' COMMENT '状态',
  `bug_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_date` date NOT NULL COMMENT '缺陷创建日期',
  `supervisor` bigint NOT NULL COMMENT '责任人',
  `fixer` bigint NULL DEFAULT NULL COMMENT '修复人',
  `deleted` tinyint NULL DEFAULT 0,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`project_id` ASC, `status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '未关闭缺陷表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_quality_bug_unclosed_record
-- ----------------------------

-- ----------------------------
-- Table structure for qc_quality_test_plan
-- ----------------------------
DROP TABLE IF EXISTS `qc_quality_test_plan`;
CREATE TABLE `qc_quality_test_plan`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联项目id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划名称',
  `stage` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '测试阶段',
  `executor` bigint NOT NULL COMMENT '执行人',
  `expected_start_time` datetime NOT NULL COMMENT '预期开始时间',
  `expected_end_time` datetime NOT NULL COMMENT '预期结束时间',
  `actual_start_time` datetime NULL DEFAULT NULL COMMENT '实际开始时间',
  `actual_end_time` datetime NULL DEFAULT NULL COMMENT '实际结束时间',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Preparing',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`project_id` ASC, `title` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '测试计划表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_quality_test_plan
-- ----------------------------

-- ----------------------------
-- Table structure for qc_quality_test_plan_case
-- ----------------------------
DROP TABLE IF EXISTS `qc_quality_test_plan_case`;
CREATE TABLE `qc_quality_test_plan_case`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联计划id',
  `plan_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联计划id',
  `node_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联模块id',
  `original_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '测试用例id',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模块路径',
  `priority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '优先级',
  `frontend_developer` bigint NULL DEFAULT NULL COMMENT '前端开发者',
  `backend_developer` bigint NULL DEFAULT NULL COMMENT '后端开发者',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
  `prerequisite` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '前置条件',
  `steps` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '执行步骤',
  `execute_time` datetime NULL DEFAULT NULL COMMENT '执行时间',
  `executor` bigint NULL DEFAULT NULL COMMENT '执行人',
  `result` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Preparing' COMMENT '测试结果',
  `comment` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '测试评论',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`plan_id` ASC, `node_id` ASC, `title` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '计划用例表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_quality_test_plan_case
-- ----------------------------

-- ----------------------------
-- Table structure for qc_quality_test_plan_exec_record
-- ----------------------------
DROP TABLE IF EXISTS `qc_quality_test_plan_exec_record`;
CREATE TABLE `qc_quality_test_plan_exec_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目编号',
  `plan_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划编号',
  `data_id` bigint NOT NULL COMMENT '关联编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `operation` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作类型',
  `duration` int NOT NULL DEFAULT 0 COMMENT '操作耗时，修复、激活、关闭缺陷时记录消耗时间',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `deleted` tinyint NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx1`(`project_id` ASC, `user_id` ASC, `operation` ASC, `create_time` ASC) USING BTREE,
  INDEX `inx2`(`plan_id` ASC, `data_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '执行记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_quality_test_plan_exec_record
-- ----------------------------

-- ----------------------------
-- Table structure for qc_quality_test_review
-- ----------------------------
DROP TABLE IF EXISTS `qc_quality_test_review`;
CREATE TABLE `qc_quality_test_review`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联项目id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评审名称',
  `speaker` bigint NOT NULL COMMENT '主讲人',
  `reviewer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评审成员',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Preparing',
  `expected_start_time` datetime NOT NULL COMMENT '预期开始时间',
  `expected_end_time` datetime NOT NULL COMMENT '预期结束时间',
  `actual_start_time` datetime NULL DEFAULT NULL COMMENT '实际开始时间',
  `actual_end_time` datetime NULL DEFAULT NULL COMMENT '实际结束时间',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`project_id` ASC, `title` ASC, `speaker` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '测试评审表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_quality_test_review
-- ----------------------------

-- ----------------------------
-- Table structure for qc_quality_test_review_case
-- ----------------------------
DROP TABLE IF EXISTS `qc_quality_test_review_case`;
CREATE TABLE `qc_quality_test_review_case`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联计划id',
  `review_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联评审id',
  `node_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联模块id',
  `original_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '测试用例id',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '测试用例名称',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模块路径',
  `priority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '测试用例等级',
  `frontend_developer` bigint NULL DEFAULT NULL COMMENT '前端开发者',
  `backend_developer` bigint NULL DEFAULT NULL COMMENT '后端开发者',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `prerequisite` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '前置条件',
  `steps` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '测试步骤',
  `review_time` datetime NULL DEFAULT NULL COMMENT '评审时间',
  `reviewer` bigint NULL DEFAULT NULL COMMENT '评审人',
  `result` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Preparing' COMMENT '评审结果',
  `comment` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`review_id` ASC, `node_id` ASC, `title` ASC, `priority` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评审用例表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_quality_test_review_case
-- ----------------------------

-- ----------------------------
-- Table structure for qc_quality_test_review_exec_record
-- ----------------------------
DROP TABLE IF EXISTS `qc_quality_test_review_exec_record`;
CREATE TABLE `qc_quality_test_review_exec_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目编号',
  `review_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划编号',
  `data_id` bigint NOT NULL COMMENT '关联编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `operation` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作类型',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `deleted` tinyint NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`project_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评审记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_quality_test_review_exec_record
-- ----------------------------

-- ----------------------------
-- Table structure for qc_quality_testcase
-- ----------------------------
DROP TABLE IF EXISTS `qc_quality_testcase`;
CREATE TABLE `qc_quality_testcase`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联项目id',
  `node_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '-1' COMMENT '关联模块id',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用例名称',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模块路径',
  `supervisor` bigint NULL DEFAULT NULL COMMENT '负责人',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '[]' COMMENT '标签',
  `priority` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '优先级',
  `frontend_developer` bigint NULL DEFAULT NULL COMMENT '前端开发者',
  `backend_developer` bigint NULL DEFAULT NULL COMMENT '后端开发者',
  `prerequisite` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '前置条件',
  `steps` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '步骤',
  `last_review_result` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'Preparing' COMMENT '最后一次评审结果',
  `last_review_time` datetime NULL DEFAULT NULL COMMENT '最后一次评审时间',
  `trash` tinyint NOT NULL DEFAULT 0 COMMENT '回收站',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`project_id` ASC, `node_id` ASC, `title` ASC, `supervisor` ASC, `tags` ASC, `priority` ASC, `trash` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '测试用例表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_quality_testcase
-- ----------------------------

-- ----------------------------
-- Table structure for qc_report_quality_developer_day
-- ----------------------------
DROP TABLE IF EXISTS `qc_report_quality_developer_day`;
CREATE TABLE `qc_report_quality_developer_day`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日期：yyyy-mm-dd',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目编号',
  `testcase_total` int NOT NULL DEFAULT 0 COMMENT '归属用例数',
  `new_bug_total` int NOT NULL DEFAULT 0 COMMENT '归属Bug数',
  `closed_bug_total` int NOT NULL DEFAULT 0 COMMENT '关闭Bug数',
  `fixed_bug_duration` int NULL DEFAULT 0 COMMENT '修复Bug耗时',
  `reopened_bug_total` int NULL DEFAULT 0 COMMENT '归属Bug激活数',
  `deleted` bit(1) NULL DEFAULT b'0',
  `create_time` datetime NULL DEFAULT NULL,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`user_id` ASC, `project_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '开发基础数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_report_quality_developer_day
-- ----------------------------

-- ----------------------------
-- Table structure for qc_report_quality_tester_day
-- ----------------------------
DROP TABLE IF EXISTS `qc_report_quality_tester_day`;
CREATE TABLE `qc_report_quality_tester_day`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '日期：yyyy-mm-dd',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目编号',
  `testcase_total` int NOT NULL DEFAULT 0 COMMENT '新增用例数',
  `execute_testcase_total` int NOT NULL DEFAULT 0 COMMENT '执行用例数',
  `new_bug_total` int NOT NULL DEFAULT 0 COMMENT '归属Bug数',
  `validated_bug_total` int NOT NULL DEFAULT 0 COMMENT '验证Bug数',
  `validated_bug_duration` int NOT NULL DEFAULT 0 COMMENT '验证Bug耗时',
  `closed_bug_total` int NOT NULL DEFAULT 0 COMMENT '关闭Bug数',
  `reopened_bug_total` int NOT NULL DEFAULT 0 COMMENT '激活Bug数',
  `deleted` bit(1) NULL DEFAULT b'0',
  `create_time` datetime NULL DEFAULT NULL,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx`(`user_id` ASC, `project_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '报表基础数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qc_report_quality_tester_day
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父部门id',
  `sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `leader_user_id` bigint NULL DEFAULT NULL COMMENT '负责人',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '部门状态（1正常 0停用）',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 'migoo.club', 0, 0, 1, 'migoo@migoo.xyz', 1, 0, '系统', '2022-04-29 18:04:35', '系统', '2022-04-29 18:04:35');
INSERT INTO `sys_dept` VALUES (2, '董事会', 1, 0, 1, NULL, 1, 0, '系统', '2022-05-01 16:56:08', '系统', '2022-05-01 16:56:43');
INSERT INTO `sys_dept` VALUES (3, '总经办', 1, 10, 1, NULL, 1, 0, '系统', '2022-05-01 16:56:35', '系统', '2022-05-01 16:56:44');

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
INSERT INTO `sys_menu` VALUES (1, '系统管理', 'system:manager', 1, 9998, 0, '/system', 'ep:setting', NULL, '', 1, 1, 0, 1, 0, '', '2022-04-29 18:34:52', '超级管理员', '2023-09-27 22:48:18');
INSERT INTO `sys_menu` VALUES (2, '研发工具', 'developer', 1, 9999, 0, '/developer', 'fa:connectdevelop', NULL, NULL, 1, 1, 0, 1, 0, '', NULL, '超级管理员', '2023-09-27 22:51:30');
INSERT INTO `sys_menu` VALUES (3, '工作台', 'dashboard', 2, 0, 0, '', 'ep:alarm-clock', 'Dashboard', 'dashboard/index', 1, 1, 1, 1, 0, '', NULL, '超级管理员', '2023-09-27 22:37:53');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 'system:user:query', 2, 0, 1, 'user', 'ep:user', 'SystemUser', 'system/user/index', 1, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:48:39');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 'system:role:query', 2, 1, 1, 'role', 'fa-solid:user-plus', 'UserRole', 'system/role/index', 1, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:49:31');
INSERT INTO `sys_menu` VALUES (102, '部门管理', 'system:dept:query', 2, 2, 1, 'dept', 'ep:basketball', 'SystemDept', 'system/dept/index', 1, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:50:05');
INSERT INTO `sys_menu` VALUES (103, '岗位管理', 'system:post:query', 2, 3, 1, 'post', 'ep:postcard', 'SystemPost', 'system/post/index', 1, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:50:32');
INSERT INTO `sys_menu` VALUES (104, '菜单管理', 'system:menu:query', 2, 4, 1, 'menu', 'ep:menu', 'SystemMenu', 'system/menu/index', 1, 1, 1, 1, 0, '', '2022-04-29 18:45:02', '超级管理员', '2023-09-27 22:50:53');
INSERT INTO `sys_menu` VALUES (105, '配置管理', 'system:configurer:query', 2, 99999, 1, 'configurer', 'ep:guide', 'SystemConfig', 'system/configurer/index', 1, 1, 1, 1, 0, '超级管理员', '2023-05-18 14:45:15', '超级管理员', '2024-04-14 12:58:30');
INSERT INTO `sys_menu` VALUES (106, '主题设置', 'layout:setting', 3, 0, 3, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-07-23 18:56:46', '超级管理员', '2024-04-14 12:58:34');
INSERT INTO `sys_menu` VALUES (107, '任务管理', '', 1, 0, 2, 'job', 'fa-solid:tasks', '', '', 1, 1, 1, 1, 0, '超级管理员', '2023-10-01 14:50:25', '超级管理员', '2024-04-14 12:58:36');
INSERT INTO `sys_menu` VALUES (108, '异常日志', 'developer:error-log:query', 2, 2, 2, 'error-log', 'fa-solid:bug', 'Bug', 'developer/errorlog/index', 1, 1, 1, 1, 0, '超级管理员', '2022-07-10 13:54:45', '超级管理员', '2024-04-14 12:57:39');
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
INSERT INTO `sys_menu` VALUES (130, '定时任务', 'developer:job:query', 2, 0, 107, 'list', 'fa-solid:tasks', 'JobIndex', 'developer/job/index', 1, 1, 1, 1, 0, '超级管理员', '2023-03-17 17:10:47', '超级管理员', '2024-04-14 13:00:20');
INSERT INTO `sys_menu` VALUES (131, '调度日志', '', 2, 1, 107, 'log', 'ep:document', 'JobLog', 'developer/job/logger/index', 1, 1, 1, 1, 0, '超级管理员', '2023-03-18 13:50:00', '超级管理员', '2024-04-14 13:00:17');
INSERT INTO `sys_menu` VALUES (132, '新增', 'developer:job:add', 3, 0, 130, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-03-17 17:12:23', '超级管理员', '2024-04-14 13:01:05');
INSERT INTO `sys_menu` VALUES (133, '修改', 'developer:job:update', 3, 1, 130, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-03-17 17:12:39', '超级管理员', '2024-04-14 13:01:07');
INSERT INTO `sys_menu` VALUES (134, '删除', 'developer:job:remove', 3, 2, 130, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-03-17 17:12:58', '超级管理员', '2024-04-14 13:01:11');
INSERT INTO `sys_menu` VALUES (135, '执行', 'developer:job:trigger', 3, 3, 130, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-03-17 17:13:23', '超级管理员', '2024-04-14 13:01:15');
INSERT INTO `sys_menu` VALUES (136, '修改', 'developer:error-log:update', 3, 0, 108, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2022-07-10 13:55:37', '超级管理员', '2024-04-14 13:00:07');
INSERT INTO `sys_menu` VALUES (137, '删除', 'developer:error-log:remove', 3, 1, 108, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2022-07-10 13:55:57', '超级管理员', '2024-04-14 13:00:05');
INSERT INTO `sys_menu` VALUES (145, '短信渠道', 'developer:sms:channel:query', 2, 0, 110, 'channel', 'fa:at', 'SmsChannel', 'developer/sms/channel/index', 1, 1, 1, 1, 0, '超级管理员', '2023-06-06 17:21:14', '超级管理员', '2024-04-14 13:02:42');
INSERT INTO `sys_menu` VALUES (146, '短信模板', 'developer:sms:template:query', 2, 1, 110, 'template', 'fa-solid:align-justify', 'SmsTemplate', 'developer/sms/template/index', 1, 1, 1, 1, 0, '超级管理员', '2023-06-06 17:30:09', '超级管理员', '2024-04-14 13:02:57');
INSERT INTO `sys_menu` VALUES (147, '短信日志', 'developer:sms:log:query', 2, 2, 110, 'logger', 'ep:document', 'SmsLog', 'developer/sms/log/index', 1, 1, 1, 1, 0, '超级管理员', '2023-06-09 16:39:31', '超级管理员', '2024-04-14 13:03:06');
INSERT INTO `sys_menu` VALUES (148, '新增', 'developer:sms:channel:add', 3, 0, 145, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:21:43', '超级管理员', '2024-04-14 13:04:38');
INSERT INTO `sys_menu` VALUES (149, '修改', 'developer:sms:channel:update', 3, 1, 145, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:22:44', '超级管理员', '2024-04-14 13:04:45');
INSERT INTO `sys_menu` VALUES (150, '删除', 'developer:sms:channel:remove', 3, 2, 145, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:23:02', '超级管理员', '2024-04-14 13:04:50');
INSERT INTO `sys_menu` VALUES (151, '新增', 'developer:sms:template:add', 3, 0, 146, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:30:23', '超级管理员', '2024-04-14 13:04:53');
INSERT INTO `sys_menu` VALUES (152, '修改', 'developer:sms:template:update', 3, 1, 146, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:30:40', '超级管理员', '2024-04-14 13:04:55');
INSERT INTO `sys_menu` VALUES (153, '删除', 'developer:sms:template:remove', 3, 2, 146, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:30:57', '超级管理员', '2024-04-14 13:04:58');
INSERT INTO `sys_menu` VALUES (154, '测试', 'developer:sms:template:send:sms', 3, 3, 146, '', '', NULL, '', 1, 1, 0, 1, 0, '超级管理员', '2023-06-06 17:49:03', '超级管理员', '2024-04-14 13:05:01');
INSERT INTO `sys_menu` VALUES (155, '文件管理', '', 1, 1, 2, 'file', 'ep:folder-opened', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:33:21', '超级管理员', '2024-04-22 14:45:31');
INSERT INTO `sys_menu` VALUES (156, '文件列表', 'developer:file:query', 2, 0, 155, 'f', 'ep:files', 'FileList', 'developer/file/index', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:41:51', '超级管理员', '2024-04-22 14:45:42');
INSERT INTO `sys_menu` VALUES (157, 'OSS配置', 'developer:file:config:query', 2, 1, 155, 'config', 'fa:crosshairs', 'FileConfig', 'developer/file/config/index', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:43:40', '超级管理员', '2024-04-22 14:45:48');
INSERT INTO `sys_menu` VALUES (158, '删除', 'developer:file:remove', 3, 0, 156, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:01', '超级管理员', '2024-04-22 14:46:12');
INSERT INTO `sys_menu` VALUES (159, '新增', 'developer:file:config:add', 3, 0, 157, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:21', '超级管理员', '2024-04-22 14:46:15');
INSERT INTO `sys_menu` VALUES (160, '修改', 'developer:file:config:update', 3, 1, 157, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:32', '超级管理员', '2024-04-22 14:46:18');
INSERT INTO `sys_menu` VALUES (161, '删除', 'developer:file:config:remove', 3, 2, 157, '', '', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:44:45', '超级管理员', '2024-04-22 14:46:19');
INSERT INTO `sys_menu` VALUES (162, '字典管理', '', 1, 6, 2, 'dictionary', 'ep:folder-opened', '', '', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:33:21', '超级管理员', '2024-05-23 10:03:04');
INSERT INTO `sys_menu` VALUES (163, '字典列表', 'developer:dictionary:query', 2, 0, 162, 's', 'ep:files', 'DictionaryList', 'developer/dictionary/index', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:41:51', '超级管理员', '2024-05-23 10:29:46');
INSERT INTO `sys_menu` VALUES (164, '字典数据', 'developer:dictionary:value:query', 2, 1, 162, 'values', 'fa:crosshairs', 'DictionaryValue', 'developer/dictionary/value/index', 1, 1, 1, 1, 0, '超级管理员', '2024-04-22 14:43:40', '超级管理员', '2024-05-23 11:22:38');
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

-- ----------------------------
-- Table structure for sys_page_configurer
-- ----------------------------
DROP TABLE IF EXISTS `sys_page_configurer`;
CREATE TABLE `sys_page_configurer`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性名称',
  `label` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '页面显示名称',
  `value` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性值',
  `options` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '操作选项',
  `deleted` bit(1) NULL DEFAULT b'0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_page_configurer
-- ----------------------------
INSERT INTO `sys_page_configurer` VALUES (1, 'title', '系统名称', 'Quick-Click', NULL, b'0', '系统', '2023-05-18 14:26:29', '超级管理员', '2024-05-22 10:57:58');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位编码',
  `sort` int NOT NULL COMMENT '排序',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：1正常；0停用',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, '董事长', 'chairman', 0, 1, '董事长', 0, '系统', '2022-04-29 20:43:45', '系统', '2022-04-29 20:44:34');
INSERT INTO `sys_post` VALUES (2, '研发工程师', 'developer', 1, 1, '研发工程师', 0, '系统', '2022-04-29 20:43:45', '系统', '2022-04-29 20:44:34');
INSERT INTO `sys_post` VALUES (3, '运营', 'operations', 2, 1, '运营', 0, '系统', '2022-04-29 20:43:45', '系统', '2022-04-29 20:44:34');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码',
  `sort` int NOT NULL COMMENT '排序',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：1正常；0停用',
  `type` int NOT NULL COMMENT '角色类型',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'super_admin', 0, 1, 1, '超级管理员', 0, '系统', '2022-04-29 20:43:45', '系统', '2022-04-29 20:44:34');
INSERT INTO `sys_role` VALUES (2, '开发者', 'developer', 1, 1, 2, '开发者', 0, '奥丁1', '2022-05-14 13:14:56', '奥丁1', '2022-05-14 13:14:56');
INSERT INTO `sys_role` VALUES (3, '管理员', 'admin', 2, 1, 2, '普通管理员', 0, '超级管理员', '2022-07-16 21:54:07', '超级管理员', '2022-07-16 21:54:07');

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `phone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录名',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '真实姓名',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态0禁用、1启用',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别 1男 2女',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门编号',
  `post_id` bigint NULL DEFAULT NULL COMMENT '岗位编号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '验证器安全码',
  `bind_authenticator` tinyint NOT NULL DEFAULT 0 COMMENT '身份验证器绑定状态：0 -未绑定 1- 已绑定',
  `required_verify_authenticator` tinyint NOT NULL DEFAULT 1 COMMENT '是否验证身份验证器',
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'superadmin', '$2a$10$zG2yO39Gqv4f80BBS.YVj.rfk6m4jeudn1p.rHI6UbpaLalhLDDNW', NULL, '超级管理员', '', 1, 1, 1, NULL, NULL, '', 1, 0, 0, '1', '2022-04-29 16:06:56', '超级管理员', '2025-06-27 02:18:34');
INSERT INTO `sys_user` VALUES (2, 'developer', '$2a$10$zG2yO39Gqv4f80BBS.YVj.rfk6m4jeudn1p.rHI6UbpaLalhLDDNW', NULL, '开发者', NULL, 1, 0, 2, 1, 'mail@cc.com', '', 0, 1, 0, '奥丁1', '2022-05-01 17:53:00', '奥丁1', '2025-06-27 02:18:31');
INSERT INTO `sys_user` VALUES (3, 'admin', '$2a$10$zG2yO39Gqv4f80BBS.YVj.rfk6m4jeudn1p.rHI6UbpaLalhLDDNW', NULL, '管理员', NULL, 1, NULL, 1, 1, NULL, '', 0, 1, 0, '超级管理员', '2022-07-16 21:52:15', '管理员', '2025-06-27 02:18:33');

-- ----------------------------
-- Table structure for sys_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_profile`;
CREATE TABLE `sys_user_profile`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目编号',
  `deleted` tinyint(1) NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `inx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_profile
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, 0, '系统', '2022-04-29 20:54:56', '系统', '2022-04-29 20:55:01');
INSERT INTO `sys_user_role` VALUES (2, 2, 2, 0, '系统', '2022-05-14 13:21:55', '系统', '2022-05-14 13:22:01');
INSERT INTO `sys_user_role` VALUES (3, 3, 3, 0, '超级管理员', '2022-07-16 22:32:22', '超级管理员', '2022-07-16 22:32:22');

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `email`, `status`, `deleted`, `creator`,
                        `create_time`, `updater`, `update_time`)
VALUES (1, 'migoo.club', 0, 0, 1, 'migoo@migoo.xyz', 1, 0, '系统', '2022-04-29 18:04:35', '系统',
        '2022-04-29 18:04:35');
INSERT INTO `sys_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `email`, `status`, `deleted`, `creator`,
                        `create_time`, `updater`, `update_time`)
VALUES (2, '董事会', 1, 0, 1, NULL, 1, 0, '系统', '2022-05-01 16:56:08', '系统', '2022-05-01 16:56:43');
INSERT INTO `sys_dept` (`id`, `name`, `parent_id`, `sort`, `leader_user_id`, `email`, `status`, `deleted`, `creator`,
                        `create_time`, `updater`, `update_time`)
VALUES (3, '总经办', 1, 10, 1, NULL, 1, 0, '系统', '2022-05-01 16:56:35', '系统', '2022-05-01 16:56:44');
COMMIT;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
INSERT INTO `sys_post` (`id`, `name`, `code`, `sort`, `status`, `remark`, `deleted`, `creator`, `create_time`,
                        `updater`, `update_time`)
VALUES (1, '董事长', 'chairman', 0, 1, '董事长', 0, '系统', '2022-04-29 20:43:45', '系统', '2022-04-29 20:44:34');
INSERT INTO `sys_post` (`id`, `name`, `code`, `sort`, `status`, `remark`, `deleted`, `creator`, `create_time`,
                        `updater`, `update_time`)
VALUES (2, '研发工程师', 'developer', 1, 1, '研发工程师', 0, '系统', '2022-04-29 20:43:45', '系统',
        '2022-04-29 20:44:34');
INSERT INTO `sys_post` (`id`, `name`, `code`, `sort`, `status`, `remark`, `deleted`, `creator`, `create_time`,
                        `updater`, `update_time`)
VALUES (3, '运营', 'operations', 2, 1, '运营', 0, '系统', '2022-04-29 20:43:45', '系统', '2022-04-29 20:44:34');
COMMIT;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `name`, `code`, `sort`, `status`, `type`, `remark`, `deleted`, `creator`, `create_time`,
                        `updater`, `update_time`)
VALUES (1, '超级管理员', 'super_admin', 0, 1, 1, '超级管理员', 0, '系统', '2022-04-29 20:43:45', '系统',
        '2022-04-29 20:44:34');
INSERT INTO `sys_role` (`id`, `name`, `code`, `sort`, `status`, `type`, `remark`, `deleted`, `creator`, `create_time`,
                        `updater`, `update_time`)
VALUES (2, '开发者', 'developer', 1, 1, 2, '开发者', 0, '超级管理员', '2022-05-14 13:14:56', '超级管理员',
        '2022-05-14 13:14:56');
INSERT INTO `sys_role` (`id`, `name`, `code`, `sort`, `status`, `type`, `remark`, `deleted`, `creator`, `create_time`,
                        `updater`, `update_time`)
VALUES (3, '管理员', 'admin', 2, 1, 2, '普通管理员', 0, '超级管理员', '2022-07-16 21:54:07', '超级管理员',
        '2022-07-16 21:54:07');
COMMIT;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `phone`, `password`, `name`, `avatar`, `status`, `gender`, `dept_id`, `post_ids`, `email`,
                        `deleted`, `creator`, `create_time`, `updater`, `update_time`)
VALUES (1, 'superadmin', '$2a$10$OMZslBjgQK/LJogOedenfOq65uywRcAUKnJgkVE9dTBElcp0OpT7W', '超级管理员', '', 1, 1, 1,
        NULL, NULL, 0, '1', '2022-04-29 16:06:56', '超级管理员', '2022-11-23 12:29:42');
INSERT INTO `sys_user` (`id`, `phone`, `password`, `name`, `avatar`, `status`, `gender`, `dept_id`, `post_ids`, `email`,
                        `deleted`, `creator`, `create_time`, `updater`, `update_time`)
VALUES (2, 'developer', '$2a$10$DmzOXDEemQOXtQjEHDnMqOcMLi0wjuYluTDSAEvA3VpYOAn9Zh3KC', '开发者', NULL, 1, 0, 2, '[]',
        'mail@cc.com', 0, '超级管理员', '2022-05-01 17:53:00', '超级管理员', '2022-05-31 20:14:42');
COMMIT;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`)
VALUES (1, 1, 1, 0, '系统', '2022-04-29 20:54:56', '系统', '2022-04-29 20:55:01');
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`)
VALUES (2, 2, 2, 0, '系统', '2022-05-14 13:21:55', '系统', '2022-05-14 13:22:01');
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`, `deleted`, `creator`, `create_time`, `updater`, `update_time`)
VALUES (3, 3, 3, 0, '超级管理员', '2022-07-16 22:32:22', '超级管理员', '2022-07-16 22:32:22');
COMMIT;

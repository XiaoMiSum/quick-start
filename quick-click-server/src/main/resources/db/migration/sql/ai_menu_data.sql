/*
 * AI模块菜单配置
 */

-- 插入AI模块一级菜单
INSERT INTO sys_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, creator, create_time, updater, update_time, deleted) 
VALUES (10000, 'AI智能体', '', 1, 10, 0, '/ai', 'ep:cpu', '', 0, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 插入AI智能体管理二级菜单
INSERT INTO sys_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, creator, create_time, updater, update_time, deleted) 
VALUES (10001, '智能体管理', '', 2, 1, 10000, 'agent', 'ep:management', 'AI/Agent/index', 0, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 插入AI智能体管理按钮权限
INSERT INTO sys_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, creator, create_time, updater, update_time, deleted) 
VALUES (10002, '新增', 'ai:agent:add', 3, 1, 10001, '', '', '', 0, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO sys_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, creator, create_time, updater, update_time, deleted) 
VALUES (10003, '修改', 'ai:agent:update', 3, 2, 10001, '', '', '', 0, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO sys_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, creator, create_time, updater, update_time, deleted) 
VALUES (10004, '删除', 'ai:agent:remove', 3, 3, 10001, '', '', '', 0, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 插入AI生成测试用例二级菜单
INSERT INTO sys_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, creator, create_time, updater, update_time, deleted) 
VALUES (10005, '测试用例', '', 2, 2, 10000, 'testcase', 'ep:document', 'AI/Testcase/index', 0, 1, 1, '1', NOW(), '1', NOW(), 0);

-- 插入AI生成测试用例按钮权限
INSERT INTO sys_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, creator, create_time, updater, update_time, deleted) 
VALUES (10006, '确认', 'ai:testcase:confirm', 3, 1, 10005, '', '', '', 0, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO sys_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, creator, create_time, updater, update_time, deleted) 
VALUES (10007, '拒绝', 'ai:testcase:reject', 3, 2, 10005, '', '', '', 0, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO sys_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, creator, create_time, updater, update_time, deleted) 
VALUES (10008, '编辑', 'ai:testcase:update', 3, 3, 10005, '', '', '', 0, 1, 1, '1', NOW(), '1', NOW(), 0);
INSERT INTO sys_menu (id, name, permission, type, sort, parent_id, path, icon, component, status, visible, keep_alive, creator, create_time, updater, update_time, deleted) 
VALUES (10009, '删除', 'ai:testcase:remove', 3, 4, 10005, '', '', '', 0, 1, 1, '1', NOW(), '1', NOW(), 0);
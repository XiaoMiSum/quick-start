/*
 * AI模块字典配置
 */

-- 插入AI智能体状态字典类型
INSERT INTO infra_dictionary (id, name, type, status, remark, creator, create_time, updater, update_time, deleted) 
VALUES (10000, 'AI智能体状态', 'ai_agent_status', 0, 'AI智能体状态', '1', NOW(), '1', NOW(), 0);

-- 插入AI智能体状态字典数据
INSERT INTO infra_dictionary_data (id, dict_id, name, value, sort, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) 
VALUES (10001, 10000, '启用', '1', 1, 'success', '', '启用', '1', NOW(), '1', NOW(), 0);
INSERT INTO infra_dictionary_data (id, dict_id, name, value, sort, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) 
VALUES (10002, 10000, '禁用', '0', 2, 'danger', '', '禁用', '1', NOW(), '1', NOW(), 0);

-- 插入AI生成测试用例状态字典类型
INSERT INTO infra_dictionary (id, name, type, status, remark, creator, create_time, updater, update_time, deleted) 
VALUES (10001, 'AI生成测试用例状态', 'ai_generated_testcase_status', 0, 'AI生成测试用例状态', '1', NOW(), '1', NOW(), 0);

-- 插入AI生成测试用例状态字典数据
INSERT INTO infra_dictionary_data (id, dict_id, name, value, sort, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) 
VALUES (10003, 10001, '待确认', '0', 1, 'info', '', '待确认', '1', NOW(), '1', NOW(), 0);
INSERT INTO infra_dictionary_data (id, dict_id, name, value, sort, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) 
VALUES (10004, 10001, '已确认', '1', 2, 'success', '', '已确认', '1', NOW(), '1', NOW(), 0);
INSERT INTO infra_dictionary_data (id, dict_id, name, value, sort, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) 
VALUES (10005, 10001, '已拒绝', '2', 3, 'danger', '', '已拒绝', '1', NOW(), '1', NOW(), 0);
INSERT INTO infra_dictionary_data (id, dict_id, name, value, sort, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) 
VALUES (10006, 10001, '已导入', '3', 4, 'warning', '', '已导入', '1', NOW(), '1', NOW(), 0);
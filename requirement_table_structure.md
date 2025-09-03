# 需求管理表结构说明

## 表名
`qc_project_requirement`

## 表结构

```sql
DROP TABLE IF EXISTS `qc_project_requirement`;
CREATE TABLE `qc_project_requirement`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '需求标题',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '详细描述',
  `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属项目',
  `module_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属模块',
  `prototype_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原型地址',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `inx_project_id`(`project_id` ASC) USING BTREE,
  INDEX `inx_module_id`(`module_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '项目需求表' ROW_FORMAT = DYNAMIC;
```

## 字段说明

| 字段名 | 类型 | 允许为空 | 默认值 | 说明 |
|--------|------|----------|--------|------|
| id | varchar(32) | 否 | - | 需求唯一标识 |
| title | varchar(64) | 否 | - | 需求标题 |
| description | text | 是 | NULL | 详细描述 |
| project_id | varchar(32) | 否 | - | 所属项目 |
| module_id | varchar(32) | 是 | NULL | 所属模块 |
| prototype_url | varchar(255) | 是 | NULL | 原型地址 |
| deleted | bit(1) | 否 | b'0' | 删除标记 |
| creator | varchar(64) | 是 | NULL | 创建者 |
| create_time | datetime | 是 | NULL | 创建时间 |
| updater | varchar(64) | 是 | NULL | 更新者 |
| update_time | datetime | 是 | NULL | 更新时间 |

## 索引说明

1. 主键索引：`id`
2. 普通索引：`inx_project_id` (project_id)
3. 普通索引：`inx_module_id` (module_id)

## 设计原则

1. 遵循项目现有的表命名规范：以`qc_`开头，表示quick-click系统
2. 遵循项目现有的字段命名规范：使用下划线分隔的小写字母
3. 遵循项目现有的时间戳字段规范：包含create_time和update_time
4. 遵循项目现有的软删除规范：使用deleted字段
5. 遵循项目现有的创建者/更新者规范：包含creator和updater字段
6. 字符集统一使用utf8mb4，排序规则使用utf8mb4_0900_ai_ci
7. 存储引擎使用InnoDB，行格式使用DYNAMIC

## 与其他表的关系

1. `project_id` 字段关联 `qc_project` 表的 `id` 字段
2. `module_id` 字段关联 `qc_project_node` 表的 `id` 字段（可选）

## 备注

该表结构与项目中其他表保持一致的设计风格和规范，确保了系统的一致性和可维护性。
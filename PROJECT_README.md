# Quick-Click 项目说明文档

## 项目概述

Quick-Click 是一个基于前后端分离架构的快速启动项目，旨在为开发人员提供一个开箱即用的基础框架，便于快速搭建业务系统。项目采用现代化的技术栈，提供了统一的开发规范、基础组件封装、权限控制、国际化支持等功能，能够显著减少重复开发工作。

## 技术架构

### 后端技术栈
- **核心框架**: Spring Boot 2.x
- **编程语言**: Java 21
- **数据库**: MySQL 8.x
- **持久层框架**: MyBatis Plus
- **安全框架**: Spring Security + JWT
- **对象映射**: MapStruct
- **代码生成**: Lombok
- **日志管理**: Logback
- **构建工具**: Maven 3.8+

### 前端技术栈
- **核心框架**: Vue 3 + TypeScript
- **构建工具**: Vite 4
- **UI框架**: Element Plus
- **状态管理**: Pinia
- **路由管理**: Vue Router
- **HTTP客户端**: Axios
- **CSS框架**: UnoCSS
- **国际化**: Vue I18n
- **代码规范**: ESLint + Prettier
- **包管理**: pnpm
- **图表库**: ECharts

## 项目结构

```
quick-click/
├── docs/                    # 文档目录
│   └── sql/                # 数据库脚本
├── quick-click-server/     # 后端服务
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── io/github/xiaomisum/quickclick/
│   │   │   │       ├── BootStart.java          # 启动类
│   │   │   │       ├── controller/             # 控制器层
│   │   │   │       ├── service/                # 服务层
│   │   │   │       ├── dal/                    # 数据访问层
│   │   │   │       ├── convert/                # 对象转换层
│   │   │   │       ├── model/                  # 数据传输对象
│   │   │   │       ├── enums/                  # 枚举类
│   │   │   │       └── job/                    # 定时任务
│   │   │   └── resources/
│   │   │       ├── application.yaml            # 配置文件
│   │   │       └── logback-1.xml              # 日志配置
│   │   └── test/                               # 测试目录
│   └── pom.xml                                # Maven配置
└── quick-click-www/                           # 前端项目
    ├── src/
    │   ├── api/                               # API接口封装
    │   ├── components/                        # 公共组件
    │   ├── config/                            # 配置文件
    │   ├── directives/                        # 自定义指令
    │   ├── hooks/                             # Vue自定义Hook
    │   ├── layout/                            # 页面布局
    │   ├── locales/                           # 国际化资源
    │   ├── plugins/                           # 插件封装
    │   ├── router/                            # 路由配置
    │   ├── store/                             # 状态管理
    │   ├── styles/                            # 样式文件
    │   ├── types/                             # TypeScript类型定义
    │   ├── utils/                             # 工具函数
    │   ├── views/                             # 页面视图
    │   ├── App.vue                            # 根组件
    │   ├── main.ts                            # 入口文件
    │   ├── permission.ts                      # 权限控制
    │   └── vite.config.ts                     # Vite配置
    ├── package.json                           # 依赖配置
    └── tsconfig.json                          # TypeScript配置
```

## 核心功能模块

### 1. 生产线管理 (Production Line)
- 产品线信息维护
- 产品线负责人管理

### 2. 项目管理 (Project)
- 项目信息维护
- 项目成员管理
- 项目模块管理

### 3. 需求管理 (Requirement)
- 需求信息维护
- 需求与项目关联
- 需求原型链接管理

### 4. 质量管理 (Quality)
- 缺陷管理
- 测试计划管理
- 测试用例管理

### 5. 系统管理 (System)
- 用户管理
- 角色权限管理
- 菜单管理
- 字典管理
- 文件管理
- 定时任务管理

## 开发环境搭建

### 后端环境要求
1. **JDK**: Java 21
2. **数据库**: MySQL 8.x
3. **构建工具**: Maven 3.8+
4. **IDE**: IntelliJ IDEA 或 Eclipse

### 前端环境要求
1. **Node.js**: 18+
2. **包管理器**: pnpm
3. **IDE**: VSCode 或 WebStorm

### 环境配置步骤

#### 1. 数据库配置
```sql
-- 创建数据库
CREATE DATABASE `quick-click` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
```

#### 2. 导入初始数据
```bash
# 执行SQL脚本
mysql -u root -p quick-click < docs/sql/quick-click.sql
```

#### 3. 后端启动
```bash
# 进入后端目录
cd quick-click-server

# 编译项目
mvn clean install

# 启动服务
mvn spring-boot:run
```

#### 4. 前端启动
```bash
# 进入前端目录
cd quick-click-www

# 安装依赖
pnpm install

# 开发模式启动
pnpm run dev
```

## 项目配置说明

### 后端配置 (application.yaml)
```yaml
server:
  port: 48080  # 服务端口

spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/quick-click  # 数据库连接
    username: root  # 数据库用户名
    password: 123456qq!  # 数据库密码
  data:
    redis:
      host: localhost  # Redis地址
      port: 6379       # Redis端口

migoo:
  security:
    token:
      secret: 75cc36a82345f49075293bcd8d3e547a  # JWT密钥
```

### 前端配置
```bash
# 环境变量配置
.env.dev     # 开发环境
.env.pro     # 生产环境
```

## 代码规范

### 后端规范
1. **分层架构**: Controller-Service-Mapper 三层架构
2. **数据传输**: 使用VO、DTO、DO进行数据传输和转换
3. **对象转换**: 使用MapStruct进行对象转换
4. **API设计**: 遵循RESTful API设计规范，使用Result包装返回结果
5. **异常处理**: 统一异常处理机制

### 前端规范
1. **组件规范**: 使用Vue和TypeScript，集成useI18n和useMessage
2. **代码风格**: 使用ESLint + Prettier统一代码风格
3. **组件命名**: 使用PascalCase命名规范
4. **目录结构**: 按功能模块划分目录结构

## 数据库设计

### 核心表结构
1. **用户相关表**
   - sys_user: 用户信息表
   - sys_role: 角色信息表
   - sys_menu: 菜单信息表
   - sys_user_role: 用户角色关联表

2. **业务相关表**
   - qc_production_line: 产品线信息表
   - qc_project: 项目信息表
   - qc_project_node: 项目模块表
   - qc_project_requirement: 项目需求表
   - qc_quality_bug: 质量缺陷表

3. **系统配置表**
   - infra_dictionary: 字典信息表
   - infra_dictionary_value: 字典数据表
   - infra_file: 文件表

## 部署说明

### 后端部署
```bash
# 打包
mvn clean package

# 运行
java -jar quick-click-server.jar
```

### 前端部署
```bash
# 构建生产版本
pnpm run build

# 部署到Web服务器
# 将dist目录下的文件部署到Nginx或Apache等Web服务器
```

## 项目特色

1. **模块化设计**: 前后端均采用模块化组织代码，便于扩展和维护
2. **权限控制**: 基于JWT的用户认证和RBAC权限控制
3. **国际化支持**: 支持中英文切换的多语言功能
4. **组件封装**: 封装了常用的UI组件，如表格、表单、弹窗等
5. **代码生成**: 使用Lombok和MapStruct减少样板代码
6. **日志管理**: 完善的日志记录和异常处理机制
7. **安全防护**: 内置XSS防护和SQL注入防护机制

## 常见问题

1. **启动失败**: 检查数据库连接配置是否正确
2. **权限问题**: 确认JWT密钥配置是否正确
3. **跨域问题**: 检查前端代理配置
4. **依赖问题**: 确保Maven和pnpm依赖安装完整

## 贡献指南

1. Fork项目到个人仓库
2. 创建功能分支
3. 提交代码变更
4. 发起Pull Request
5. 等待代码审查和合并

## 许可证

本项目采用MIT许可证，详情请查看LICENSE文件。
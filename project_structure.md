# 项目结构说明

## 目录结构

```
quick-start/
├── quick-click-server/              # 后端服务模块
│   ├── src/main/
│   │   ├── java/io/github/xiaomisum/quickclick/
│   │   │   ├── controller/          # 接口控制器
│   │   │   │   ├── ai/              # AI模块控制器
│   │   │   │   ├── productionline/  # 产线模块控制器
│   │   │   │   ├── project/         # 项目模块控制器
│   │   │   │   ├── quality/         # 质量模块控制器
│   │   │   │   └── self/            # 个人中心控制器
│   │   │   ├── convert/             # 对象转换器
│   │   │   ├── dal/                 # 数据访问层
│   │   │   │   ├── dataobject/      # 数据对象
│   │   │   │   │   ├── ai/          # AI模块数据对象
│   │   │   │   │   ├── productionline/ # 产线模块数据对象
│   │   │   │   │   ├── profile/     # 个人资料数据对象
│   │   │   │   │   ├── project/     # 项目模块数据对象
│   │   │   │   │   ├── quality/     # 质量模块数据对象
│   │   │   │   │   └── report/      # 报表模块数据对象
│   │   │   │   └── mapper/          # 数据访问接口
│   │   │   │       ├── ai/          # AI模块数据访问接口
│   │   │   │       ├── productionline/ # 产线模块数据访问接口
│   │   │   │       ├── profile/     # 个人资料数据访问接口
│   │   │   │       ├── project/     # 项目模块数据访问接口
│   │   │   │       ├── qualitycenter/ # 质量模块数据访问接口
│   │   │   │       └── report/      # 报表模块数据访问接口
│   │   │   ├── enums/               # 枚举类
│   │   │   ├── job/                 # 定时任务
│   │   │   ├── model/dto/           # 数据传输对象
│   │   │   ├── service/             # 业务逻辑层
│   │   │   │   ├── ai/              # AI模块服务
│   │   │   │   ├── productionline/  # 产线模块服务
│   │   │   │   ├── profile/         # 个人资料服务
│   │   │   │   ├── project/         # 项目模块服务
│   │   │   │   ├── qualitycenter/   # 质量模块服务
│   │   │   │   └── report/          # 报表模块服务
│   │   │   └── BootStart.java       # 启动类
│   │   └── resources/               # 资源文件
│   │       ├── application.yaml     # 应用配置文件
│   │       └── logback-1.xml        # 日志配置文件
│   └── pom.xml                      # Maven配置文件
├── quick-click-www/                 # 前端模块
│   ├── build/vite/                  # Vite构建配置
│   ├── src/
│   │   ├── api/                     # 接口封装
│   │   │   ├── ai/                  # AI模块接口
│   │   │   ├── login/               # 登录接口
│   │   │   ├── management/          # 管理接口
│   │   │   ├── production-line/     # 产线接口
│   │   │   ├── project/             # 项目接口
│   │   │   └── quality/             # 质量接口
│   │   ├── components/              # 公共组件
│   │   ├── config/axios/            # Axios配置
│   │   ├── directives/              # 自定义指令
│   │   ├── hooks/                   # Vue自定义Hook
│   │   ├── layout/                  # 页面布局
│   │   ├── locales/                 # 国际化配置
│   │   ├── plugins/                 # 插件封装
│   │   ├── router/                  # 路由配置
│   │   │   ├── modules/             # 路由模块
│   │   │   └── index.ts             # 路由入口
│   │   ├── store/                   # Vuex状态管理
│   │   ├── styles/                  # 样式文件
│   │   ├── types/                   # TypeScript类型定义
│   │   ├── utils/                   # 工具函数
│   │   ├── views/                   # 页面视图
│   │   │   ├── AI/                  # AI模块页面
│   │   │   │   ├── Agent/           # AI智能体管理页面
│   │   │   │   ├── Testcase/        # AI生成测试用例页面
│   │   │   │   │   └── Draft/       # 草稿箱页面
│   │   │   │   └── index.vue        # AI模块概览页面
│   │   │   ├── Login/               # 登录页面
│   │   │   ├── Management/          # 管理页面
│   │   │   ├── ProductionLine/      # 产线页面
│   │   │   ├── Project/             # 项目页面
│   │   │   ├── Quality/             # 质量页面
│   │   │   ├── components/          # 组件页面
│   │   │   ├── dashboard/           # 仪表板页面
│   │   │   └── 404.vue              # 404页面
│   │   ├── App.vue                  # 根组件
│   │   ├── main.ts                  # 入口文件
│   │   └── permission.ts            # 权限配置
│   ├── types/                       # 类型定义
│   ├── index.html                   # HTML模板
│   ├── package.json                 # npm配置文件
│   ├── pnpm-lock.yaml               # 依赖锁定文件
│   ├── postcss.config.js            # PostCSS配置
│   ├── prettier.config.js           # Prettier配置
│   ├── stylelint.config.js          # Stylelint配置
│   ├── tsconfig.json                # TypeScript配置
│   ├── uno.config.ts                # UnoCSS配置
│   └── vite.config.ts               # Vite配置
├── PROJECT_README.md                # 项目详细说明文档
├── README.md                        # 项目简介文档
├── pom.xml                          # 根Maven配置文件
├── project_structure.md             # 项目结构说明文档
├── requirement_feature_summary.md   # 需求功能摘要文档
└── requirement_table_structure.md   # 需求表结构文档
```

## 核心模块介绍

### AI模块 (ai)
AI模块是本项目的核心功能模块之一，提供基于人工智能的测试用例自动生成能力。

#### 主要功能
1. **AI智能体管理**：创建和管理不同类型的AI智能体，用于生成测试用例
2. **AI生成测试用例**：基于需求文档自动生成测试用例，支持确认和拒绝操作
3. **草稿箱**：集中管理所有待确认的AI生成测试用例
4. **统计概览**：提供AI模块使用情况的统计信息

#### 技术特点
- 基于Spring Boot和Vue 3的前后端分离架构
- 使用RESTful API进行前后端通信
- 采用JWT进行权限认证
- 遵循项目统一的代码规范和设计模式

#### 数据库表
- `qc_ai_agent`：AI智能体表
- `qc_ai_generated_testcase`：AI生成测试用例表

#### 权限控制
- `ai:agent:add`：新增AI智能体
- `ai:agent:update`：修改AI智能体
- `ai:agent:remove`：删除AI智能体
- `ai:testcase:confirm`：确认AI生成的测试用例
- `ai:testcase:reject`：拒绝AI生成的测试用例

### 产线模块 (productionline)
产线模块用于管理产品的生产线信息。

### 项目模块 (project)
项目模块用于管理项目信息，包括项目基本信息、项目成员、项目模块等。

### 质量模块 (quality)
质量模块用于管理产品质量相关信息，包括缺陷管理、测试用例管理、测试计划管理等。

### 管理模块 (management)
管理模块用于系统管理功能，包括用户管理、角色管理、菜单管理、部门管理、岗位管理、系统配置管理等。

### 个人中心模块 (self)
个人中心模块用于管理个人资料和系统设置。

## 技术架构

### 后端技术栈
- Spring Boot 2.x
- MyBatis Plus
- MySQL
- Redis
- JWT
- Maven

### 前端技术栈
- Vue 3
- TypeScript
- Vite
- Element Plus
- Pinia
- Vue Router
- Axios
- UnoCSS

## 开发规范

### 后端开发规范
- 采用Controller-Service-Mapper三层架构
- 使用VO、DTO、DO进行数据传输和转换
- 遵循RESTful API设计规范
- 使用统一的异常处理机制
- 使用JWT进行权限认证

### 前端开发规范
- 采用Vue 3 Composition API
- 使用TypeScript进行类型检查
- 遵循Element Plus组件库的UI风格
- 使用Pinia进行状态管理
- 使用Vue Router进行路由管理

## 部署说明

### 后端部署
1. 确保已安装JDK 17
2. 使用Maven构建项目：`mvn clean install`
3. 运行项目：`java -jar quick-click-server.jar`

### 前端部署
1. 确保已安装Node.js 18+
2. 安装依赖：`pnpm install`
3. 构建项目：`pnpm run build`
4. 部署构建产物到Web服务器
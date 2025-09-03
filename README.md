# Quick-Click

Quick-Click 是一个基于前后端分离架构的快速启动项目，旨在为开发人员提供一个开箱即用的基础框架，便于快速搭建业务系统。

## 技术栈

### 后端
- Spring Boot 2.x
- Java 21
- MyBatis Plus
- MySQL 8.x
- Maven

### 前端
- Vue 3 + TypeScript
- Vite 4
- Element Plus
- pnpm

## 快速开始

### 环境要求
- JDK 21
- Node.js 18+
- MySQL 8.x
- Maven 3.8+
- pnpm

### 启动步骤

1. **创建数据库**
   ```sql
   CREATE DATABASE `quick-click` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
   ```

2. **导入初始数据**
   ```bash
   mysql -u root -p quick-click < docs/sql/quick-click.sql
   ```

3. **启动后端服务**
   ```bash
   cd quick-click-server
   mvn clean install
   mvn spring-boot:run
   ```

4. **启动前端服务**
   ```bash
   cd quick-click-www
   pnpm install
   pnpm run dev
   ```

## 项目文档

详细文档请查看 [PROJECT_README.md](PROJECT_README.md)

## 许可证

MIT
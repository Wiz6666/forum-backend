# 论坛系统项目说明

## 一、项目简介

本项目为基于 Spring Boot 的简易论坛后端，实现用户注册、发帖、回帖、板块管理、站内消息等功能，适合学习、简历展示和二次开发。

---

## 二、技术选型

- **后端框架**：Spring Boot 3.2.5
- **JDK 版本**：JDK 17（目标语言级别为 17）
- **构建工具**：Maven（已从 Gradle 迁移到 Maven；项目根含 `pom.xml`）
    - 建议使用 Maven Wrapper（`./mvnw`）以保证团队中一致的 Maven 版本。
- **数据库**：MySQL 9.0（本地部署，字符集 utf8mb4）
- **数据库管理工具**：Sequel Ace
- **主要依赖**：
    - Lombok
    - Spring Web
    - Spring Boot DevTools
- **开发工具**：IntelliJ IDEA
- **版本控制**：Git（推送到 GitHub 以便展示和发布）

---

## 三、数据库表结构

### t_user（用户表）
- id：主键，自增
- username：用户名，唯一
- password：加密密码
- nickname：昵称
- phone：手机号
- email：邮箱
- gender：性别（0女 1男 2保密）
- salt：密码盐
- avatarUrl：头像
- articleCount：发帖数
- isAdmin：是否管理员
- remark：自我介绍
- state：状态（0正常，-1禁用）
- deleteState：删除状态（0未删，1已删）
- createTime/updateTime：创建/修改时间

### t_board（板块表）
- id：主键，自增
- name：板块名
- articleCount：帖子数
- sort：排序
- state：状态
- deleteState：删除状态
- createTime/updateTime：创建/修改时间

### t_article（帖子表）
- id：主键，自增
- boardID：所属板块
- userId：发帖人
- title/content：标题/内容
- vistCount/replyCount/likeCount：浏览/回复/点赞数
- state/deleteState/createTime/updateTime

### t_article_reply（回复表）
- id：主键，自增
- articleID：所属帖子
- postUserID：楼主
- replyId/replyUserId：楼中楼支持
- content：回复内容
- likeCount/state/deleteState/createTime/updateTime

### t_message（站内消息表）
- id：主键，自增
- postUserId/receiveUserId：发/收消息人
- content：消息内容
- state：已读/未读
- createTime/updateTime

---

## 四、开发规范与协作建议

- 统一使用 YAML 配置（`application.yml`），如需切换为 Properties 可随时调整。
- 代码风格遵循阿里巴巴 Java 开发手册，实体类使用 Lombok 注解。
- 所有业务开发遵循三层架构（Controller-Service-Mapper/Repository）。
- 版本控制建议每开发一个功能分支，开发完成后合并到 `main`（或 `master`）分支。
- 项目根目录维护 `TODO.md`，记录开发进度与任务分配。

---

## 五、项目进度

### 2026-04-10
- **依赖管理**: 确认了 Maven 依赖无误并完成 debug。
- **环境降级**: 回滚 Spring Boot 版本至 3.x (3.2.5)，回滚 JDK 版本从 21 降至 17。
- **数据库设计**: 完成了数据库 ORM 映射（修复 MyBatis Mapper XML 及实体类生成）。
- **未来计划**: 编写状态码代码（尚未开始）。

---

## 六、当前进度
- [x] 数据库表结构设计与初始化
- [ ] 用户注册/登录模块
- [ ] 发帖/回帖功能
- [ ] 板块管理
- [ ] 站内消息
- [ ] API 文档与测试


# AGENTS.md

本文件为 AI 代理在处理本项目代码时提供指导。

## 构建与运行命令

- **构建**: `mvn clean package -DskipTests` (在项目根目录)
- **运行**: `java -jar ruoyi-admin/target/fac.war` 或使用 Maven 插件：`mvn spring-boot:run -pl ruoyi-admin`
- **Linux 启动脚本**: `./ry.sh start|stop|restart|status`

## 项目结构

- `ruoyi-admin`: 主启动入口 + Web 控制器 (打包为 fac.war)
- `ruoyi-fac`: 商城业务模块 (FAC)
- `ruoyi-mry`: 美业管理业务模块 (MRY)
- `ruoyi-framework`: 框架核心配置 (Shiro、MyBatis 等)
- `ruoyi-system`: 系统基础服务 (用户、角色、菜单、字典)
- `ruoyi-quartz`: 定时任务
- `ruoyi-generator`: 代码生成器
- `ruoyi-common`: 通用工具类和公共类

## 配置要点

- 默认存储路径：`ruoyi.profile = D:/profile/` (Windows 路径 - Linux/Mac 需调整)
- 文件上传存储：`/opt/fac/picture/` (Linux) 或 `D:/data/facPic/` (Windows) - 通过 `file.imagesUploadPath` 设置
- Redis 配置硬编码在 `application.yml` 的 `spring.redis` 下 (host: 127.0.0.1, port: 6379, db: 0)
- 激活配置文件：`druid` (数据库连接池配置在 `application-druid.yml` 中)
- Knife4j API 文档地址：`http://localhost:8080/doc.html?plus=1`

## 数据库初始化

SQL 脚本执行顺序：
1. `sql/ry.sql` - RuoYi 基础表结构 (用户、角色、菜单)
2. `sql/fac.sql` - 商城相关表
3. `sql/mry.sql` - 美业管理相关表
4. `sql/quartz.sql` - Quartz 调度表

## 注意事项

- 需要 Java 8 (`pom.xml` 中设置了 `<java.version>1.8</java.version>`)
- Spring Boot 2.1.1.RELEASE 专用版本 - 不兼容新版本
- Shiro session 超时默认 30 分钟 (`shiro.session.expireTime`)
- 默认使用 War 打包方式 (`ruoyi-admin/pom.xml` 中有 `<packaging>war</packaging>`)
- 文件上传限制为 30MB/个 (`spring.servlet.multipart.max-file-size`, `max-request-size`)

## 代码规范

- 包结构遵循：`com.ruoyi.<module>.<layer>.<entity>`
- Mapper XML 位置：`classpath*:mapper/**/*Mapper.xml`
- 代码生成器默认配置：`packageName: com.ruoyi.fac`, `tablePrefix: sys_`

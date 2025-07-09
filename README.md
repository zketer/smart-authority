

# SmartAuthority

SmartAuthority 是一个基于 Spring Boot 的权限管理系统，提供用户认证、权限控制及基础统计信息查询等功能，适用于多租户场景下的权限与角色管理。

## 功能特性

- 用户管理：创建、查询、更新、删除用户
- 角色管理：创建、查询、更新、删除角色，并支持为角色分配权限
- 权限管理：创建、查询、更新、删除权限，支持权限树结构
- 租户管理：创建、查询、更新、删除租户
- 统计接口：提供用户数、角色数、权限数、租户数的获取
- JWT 认证：支持基于 JWT 的安全认证机制
- 国际化支持：支持多语言配置

## 模块结构

- **Controller**：处理 HTTP 请求，提供 RESTful API
- **Service**：业务逻辑实现，包含用户、角色、权限、租户等服务
- **Mapper**：数据库操作接口，基于 MyBatis-Plus
- **Model**：数据模型，包含实体类、请求类、响应类
- **Config**：系统配置，如 MyBatis、Security、OpenAPI、国际化等
- **Exception**：统一异常处理机制
- **Security**：安全控制模块，包含 JWT 处理、用户认证等

## 快速开始

确保你已安装 Java 17+、Maven 3.6+、以及支持的数据库（如 MySQL）。

### 启动项目

```bash
mvn spring-boot:run
```

或使用 IDE 运行 `SmartAuthorityApplication.java`。

### 接口文档

访问 `/swagger-ui.html` 查看 API 文档。

## API 示例

- 获取用户总数：`GET /dashboard/user-count`
- 获取角色总数：`GET /dashboard/role-count`
- 用户登录：`POST /api/auth/login`
- 创建权限：`POST /permissions`
- 分配权限给角色：`POST /roles/{roleId}/permissions`

## 安全配置

项目使用 Spring Security + JWT 实现认证与授权机制。相关配置在 `SecurityConfig.java`，JWT 处理逻辑在 `JwtTokenProvider.java`。

## 异常处理

统一使用 `ApiResponse` 返回结果，异常信息封装在 `ErrorResponse` 中。支持自定义错误码和本地化错误信息。

## 国际化支持

通过配置 `messages_en.properties` 和 `messages_zh_CN.properties` 实现多语言支持。

## 数据库迁移

支持 Flyway 数据库迁移，相关脚本位于 `src/main/resources/db/migration`。

## 单元测试

使用 `SmartAuthorityApplicationTests.java` 进行基础上下文加载测试。

## 许可证

本项目使用 [MIT License](LICENSE)。
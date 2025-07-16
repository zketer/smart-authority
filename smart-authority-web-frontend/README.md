

# SmartAuthority 管理系统

这是一个基于 Ant Design Pro 的管理后台系统，提供多租户支持、权限管理、角色管理和部门管理等功能。

## 功能特点
- 多租户架构支持
- 完善的权限控制系统
- 角色与权限的关联管理
- 部门组织结构管理
- 用户个人资料管理
- 支持国际化多语言

## 技术栈
- React
- Ant Design Pro
- UmiJS
- TypeScript
- Redux
- nginx (Docker部署)

## 主要模块
- 用户登录与权限验证
- 系统仪表盘
- 租户管理
- 部门管理
- 角色管理
- 权限管理
- 个人设置与安全配置

## 安装部署
请参考项目中的 Dockerfile 配置进行部署:
```dockerfile
FROM node:16-alpine as build
COPY package.json yarn.lock ./
COPY . .
FROM nginx:alpine
COPY --from=build /app/SmartAuthority /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

## 使用说明
1. 登录系统
2. 通过侧边栏导航访问不同模块
3. 使用顶部菜单进行用户设置和权限管理
4. �3管理员可进行租户、部门、角色和权限的全面管理

## 开发贡献
- 请参考 config 目录下的配置文件进行环境设置
- 项目使用 TypeScript，遵循 Ant Design Pro 的开发规范
- mock 目录包含模拟数据接口

## 许可证
本项目采用 Apache 许可证 2.0 版本

## 注意
- 部分敏感操作(如删除)需要权限验证
- 表格组件使用 ProTable 实现
- 表单组件使用 ProForm 实现
- 权限相关的字段如 description 在多处使用以提供详细的说明信息
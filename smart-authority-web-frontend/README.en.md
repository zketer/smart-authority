# SmartAuthority Management System

This is a management backend system based on Ant Design Pro, providing features such as multi-tenant support, permission management, role management, and department management.

## Key Features
- Support for multi-tenant architecture
- Comprehensive permission control system
- Role and permission association management
- Department organizational structure management
- User profile management
- Internationalization and multi-language support

## Technology Stack
- React
- Ant Design Pro
- UmiJS
- TypeScript
- Redux
- nginx (Docker deployment)

## Main Modules
- User login and permission verification
- System dashboard
- Tenant management
- Department management
- Role management
- Permission management
- Personal settings and security configurations

## Installation and Deployment
Please refer to the Dockerfile configuration in the project for deployment:
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

## Usage Instructions
1. Log in to the system
2. Access different modules via the sidebar navigation
3. Use the top menu for user settings and permission management
4. Administrators can perform comprehensive management of tenants, departments, roles, and permissions

## Development and Contribution
- Please refer to the configuration files under the config directory for environment setup
- The project uses TypeScript and follows Ant Design Pro's development guidelines
- The mock directory contains simulated data interfaces

## License
This project uses the Apache License Version 2.0

## Notes
- Some sensitive operations (such as deletion) require permission verification
- The table component is implemented using ProTable
- The form component is implemented using ProForm
- Permission-related fields such as description are used in multiple places to provide detailed explanations
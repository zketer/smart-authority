

# SmartAuthority

SmartAuthority is a permission management system based on Spring Boot, providing functions such as user authentication, permission control, and basic statistical information query, suitable for permission and role management in multi-tenant scenarios.

## Features

- **User Management**: Create, query, update, and delete users
- **Role Management**: Create, query, update, and delete roles, with support for assigning permissions to roles
- **Permission Management**: Create, query, update, and delete permissions, supporting a hierarchical permission structure
- **Tenant Management**: Create, query, update, and delete tenants
- **Statistics Interface**: Provides retrieval of user count, role count, permission count, and tenant count
- **JWT Authentication**: Supports secure authentication based on JWT
- **Internationalization Support**: Supports multi-language configuration

## Module Structure

- **Controller**: Handles HTTP requests and provides RESTful APIs
- **Service**: Implements business logic, including services for users, roles, permissions, and tenants
- **Mapper**: Database operation interfaces based on MyBatis-Plus
- **Model**: Data models, including entity classes, request classes, and response classes
- **Config**: System configurations such as MyBatis, Security, OpenAPI, and internationalization
- **Exception**: Unified exception handling mechanism
- **Security**: Security control module, including JWT handling and user authentication

## Quick Start

Ensure you have installed Java 17+, Maven 3.6+, and a supported database (e.g., MySQL).

### Run the Project

```bash
mvn spring-boot:run
```

Or run `SmartAuthorityApplication.java` using your IDE.

### API Documentation

Visit `/swagger-ui.html` to view the API documentation.

## API Examples

- Get total number of users: `GET /dashboard/user-count`
- Get total number of roles: `GET /dashboard/role-count`
- User login: `POST /api/auth/login`
- Create a permission: `POST /permissions`
- Assign a permission to a role: `POST /roles/{roleId}/permissions`

## Security Configuration

The project uses Spring Security + JWT for authentication and authorization. Related configurations are in `SecurityConfig.java`, and JWT handling logic is in `JwtTokenProvider.java`.

## Exception Handling

All responses use the `ApiResponse` class, with exception details encapsulated in `ErrorResponse`. Supports custom error codes and localized error messages.

## Internationalization Support

Multi-language support is implemented through configuration files `messages_en.properties` and `messages_zh_CN.properties`.

## Database Migration

Supports Flyway database migration. Related scripts are located in `src/main/resources/db/migration`.

## Unit Testing

Basic context loading tests are conducted using `SmartAuthorityApplicationTests.java`.

## License

This project uses the [MIT License](LICENSE).
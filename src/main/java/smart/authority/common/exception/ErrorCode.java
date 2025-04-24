package smart.authority.common.exception;

public enum ErrorCode {
    // 通用错误码
    SUCCESS(200, "操作成功", "Operation successful"),
    BAD_REQUEST(400, "请求参数错误", "Bad request"),
    UNAUTHORIZED(401, "未授权", "Unauthorized"),
    FORBIDDEN(403, "禁止访问", "Forbidden"),
    NOT_FOUND(404, "资源不存在", "Resource not found"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误", "Internal server error"),

    // 用户相关错误码 (1000-1099)
    USER_NOT_FOUND(1000, "用户不存在", "User not found"),
    USER_ALREADY_EXISTS(1001, "用户已存在", "User already exists"),
    USERNAME_PASSWORD_ERROR(1002, "用户名或密码错误", "Invalid username or password"),
    USER_ACCOUNT_LOCKED(1003, "账号已被锁定", "Account is locked"),
    USER_ACCOUNT_DISABLED(1004, "账号已被禁用", "Account is disabled"),
    USER_NAME_EXISTS(1104, "用户名已存在", "User name already exists" ),
    TOKEN_INVALID(1005, "Token无效或已过期", "Token is invalid or expired"),

    // 角色相关错误码 (1100-1199)
    ROLE_NOT_FOUND(1100, "角色不存在", "Role not found"),
    ROLE_NAME_EXISTS(1101, "角色名已存在", "Role name already exists"),
    ROLE_ALREADY_ASSIGNED(1102, "角色已分配给用户", "Role is already assigned to users"),
    ROLE_PERMISSION_DENIED(1103, "角色权限不足", "Insufficient role permissions"),
    ROLE_HAS_USERS(1104, "角色已分配给用户", "Role is already assigned to users"),


    // 权限相关错误码 (1200-1299)
    PERMISSION_NOT_FOUND(1200, "权限不存在", "Permission not found"),
    PERMISSION_DENIED(1201, "没有操作权限", "Permission denied"),
    PERMISSION_ALREADY_EXISTS(1202, "权限已存在", "Permission already exists"),
    PERMISSION_CODE_EXISTS(1203, "权限编码已存在", "Permission code already exists"),
    PERMISSION_PARENT_NOT_FOUND(1204, "父权限不存在", "Parent permission not found"),
    PERMISSION_HAS_CHILDREN(1205, "该权限下存在子权限，无法删除", "Permission has children, cannot be deleted"),
    PERMISSION_CIRCULAR_REFERENCE(1206, "不能设置本身为父权限", "Cannot set itself as parent"),

    // 租户相关错误码 (1300-1399)
    TENANT_NOT_FOUND(1300, "租户不存在", "Tenant not found"),
    TENANT_ALREADY_EXISTS(1301, "租户已存在", "Tenant already exists"),
    TENANT_DISABLED(1302, "租户已禁用", "Tenant is disabled"),

    // 部门相关错误码 (1400-1499)
    DEPARTMENT_NOT_FOUND(1400, "部门不存在", "Department not found"),
    DEPARTMENT_ALREADY_EXISTS(1401, "部门名称已存在", "Department name already exists"),
    DEPARTMENT_HAS_CHILDREN(1402, "部门下存在子部门", "Department has children"),
    DEPARTMENT_HAS_USERS(1403, "部门下存在用户", "Department has users"),
    DEPARTMENT_PARENT_NOT_FOUND(1404, "父部门不存在", "Parent department not found"),
    DEPARTMENT_CIRCULAR_REFERENCE(1405, "不能设置本身为父部门", "Cannot set itself as parent"),

    // 系统错误码 (2000-2099)
    SYSTEM_ERROR(2000, "系统错误", "System error"),
    DATABASE_ERROR(2001, "数据库错误", "Database error"),
    CACHE_ERROR(2002, "缓存错误", "Cache error"),
    FILE_UPLOAD_ERROR(2003, "文件上传失败", "File upload failed"),
    NETWORK_ERROR(2004, "网络错误", "Network error"),
    ;

    private final int code;
    private final String chMessage;
    private final String enMessage;

    ErrorCode(int code, String chMessage, String enMessage) {
        this.code = code;
        this.chMessage = chMessage;
        this.enMessage = enMessage;
    }

    public int getCode() {
        return code;
    }

    public String getChMessage() {
        return chMessage;
    }

    public String getEnMessage() {
        return enMessage;
    }
}
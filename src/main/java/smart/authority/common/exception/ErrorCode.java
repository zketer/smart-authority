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

    // 角色相关错误码 (1100-1199)
    ROLE_NOT_FOUND(1100, "角色不存在", "Role not found"),
    ROLE_NAME_EXISTS(1101, "角色名已存在", "Role name already exists"),
    ROLE_ALREADY_ASSIGNED(1102, "角色已分配给用户", "Role is already assigned to users"),
    ROLE_PERMISSION_DENIED(1103, "角色权限不足", "Insufficient role permissions"),

    // 权限相关错误码 (1200-1299)
    PERMISSION_NOT_FOUND(1200, "权限不存在", "Permission not found"),
    PERMISSION_DENIED(1201, "没有操作权限", "Permission denied"),
    PERMISSION_ALREADY_EXISTS(1202, "权限已存在", "Permission already exists"),

    // 租户相关错误码 (1300-1399)
    TENANT_NOT_FOUND(1300, "租户不存在", "Tenant not found"),
    TENANT_ALREADY_EXISTS(1301, "租户已存在", "Tenant already exists"),
    TENANT_DISABLED(1302, "租户已禁用", "Tenant is disabled"),

    // 部门相关错误码 (1400-1499)
    DEPARTMENT_NOT_FOUND(1400, "部门不存在", "Department not found"),
    DEPARTMENT_ALREADY_EXISTS(1401, "部门已存在", "Department already exists"),
    DEPARTMENT_HAS_USERS(1402, "部门下存在用户", "Department has users"),

    // 系统错误码 (2000-2099)
    SYSTEM_ERROR(2000, "系统错误", "System error"),
    DATABASE_ERROR(2001, "数据库错误", "Database error"),
    CACHE_ERROR(2002, "缓存错误", "Cache error"),
    FILE_UPLOAD_ERROR(2003, "文件上传失败", "File upload failed"),
    NETWORK_ERROR(2004, "网络错误", "Network error");

    private final int code;
    private final String message;
    private final String englishMessage;

    ErrorCode(int code, String message, String englishMessage) {
        this.code = code;
        this.message = message;
        this.englishMessage = englishMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getEnglishMessage() {
        return englishMessage;
    }
}
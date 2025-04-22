package smart.authority.web.service;

public interface DashboardService {
    /**
     * 获取用户总数
     *
     * @return 用户总数
     */
    Long getUserCount();

    /**
     * 获取角色总数
     *
     * @return 角色总数
     */
    Long getRoleCount();

    /**
     * 获取权限总数
     *
     * @return 权限总数
     */
    Long getPermissionCount();

    /**
     * 获取租户总数
     *
     * @return 租户总数
     */
    Long getTenantCount();
} 
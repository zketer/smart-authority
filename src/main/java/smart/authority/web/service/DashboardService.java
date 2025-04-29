package smart.authority.web.service;

import smart.authority.web.model.resp.*;

/**
 * @author lynn
 */
public interface DashboardService {

    /**
     * 获取用户统计
     */
    StatsResp getUserStats(Integer tenantId);

    /**
     * 获取部门统计
     */
    StatsResp getDepartmentStats(Integer tenantId);

    /**
     * 获取角色统计
     */
    StatsResp getRoleStats(Integer tenantId);

    /**
     * 获取权限统计
     */
    StatsResp getPermissionStats(Integer tenantId);

    /**
     * 获取租户统计
     */
    StatsResp getTenantStats(Integer tenantId);

    /**
     * 获取用户增长数据
     */
    UserGrowthResp getUserGrowth(Integer tenantId);

    /**
     * 获取用户活跃度数据
     */
    UserActivityResp getUserActivity(Integer tenantId);

    /**
     * 获取用户行为数据
     */
    UserBehaviorResp getUserBehavior(Integer tenantId);
} 
package smart.authority.web.service;

import smart.authority.web.model.resp.*;

/**
 * @author lynn
 */
public interface DashboardService {

    /**
     * 获取用户统计
     */
    StatsResp getUserStats();

    /**
     * 获取部门统计
     */
    StatsResp getDepartmentStats();

    /**
     * 获取角色统计
     */
    StatsResp getRoleStats();

    /**
     * 获取权限统计
     */
    StatsResp getPermissionStats();

    /**
     * 获取用户增长数据
     */
    UserGrowthResp getUserGrowth();

    /**
     * 获取部门分布数据
     */
    StatsResp getDepartmentDistribution();

    /**
     * 获取用户活跃度数据
     */
    UserActivityResp getUserActivity();

    /**
     * 获取用户行为数据
     */
    UserBehaviorResp getUserBehavior();
} 
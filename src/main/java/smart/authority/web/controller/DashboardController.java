package smart.authority.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smart.authority.common.model.ApiResponse;
import smart.authority.web.model.resp.*;
import smart.authority.web.service.DashboardService;

/**
 * @author lynn
 */
@Tag(name = "DashboardController")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "获取用户统计")
    @GetMapping("/user/stats")
    public ApiResponse<StatsResp> getUserStats(@RequestParam(defaultValue = "", required = false) Integer tenantId) {
        return ApiResponse.success(dashboardService.getUserStats(tenantId));
    }

    @Operation(summary = "获取部门统计")
    @GetMapping("/department/stats")
    public ApiResponse<StatsResp> getDepartmentStats(@RequestParam(defaultValue = "", required = false) Integer tenantId) {
        return ApiResponse.success(dashboardService.getDepartmentStats(tenantId));
    }

    @Operation(summary = "获取角色统计")
    @GetMapping("/role/stats")
    public ApiResponse<StatsResp> getRoleStats(@RequestParam(defaultValue = "", required = false) Integer tenantId) {
        return ApiResponse.success(dashboardService.getRoleStats(tenantId));
    }

    @Operation(summary = "获取权限统计")
    @GetMapping("/permission/stats")
    public ApiResponse<StatsResp> getPermissionStats(@RequestParam(defaultValue = "", required = false) Integer tenantId) {
        return ApiResponse.success(dashboardService.getPermissionStats(tenantId));
    }

    @Operation(summary = "获取租户统计")
    @GetMapping("/tenant/stats")
    public ApiResponse<StatsResp> getTenantStats(@RequestParam(defaultValue = "", required = false) Integer tenantId) {
        return ApiResponse.success(dashboardService.getTenantStats(tenantId));
    }

    @Operation(summary = "获取用户增长数据")
    @GetMapping("/user/growth")
    public ApiResponse<UserGrowthResp> getUserGrowth(@RequestParam(defaultValue = "", required = false) Integer tenantId) {
        return ApiResponse.success(dashboardService.getUserGrowth(tenantId));
    }

    @Operation(summary = "获取用户活跃度数据")
    @GetMapping("/user/activity")
    public ApiResponse<UserActivityResp> getUserActivity(@RequestParam(defaultValue = "", required = false) Integer tenantId) {
        return ApiResponse.success(dashboardService.getUserActivity(tenantId));
    }

    @Operation(summary = "获取用户行为数据")
    @GetMapping("/user/behavior")
    public ApiResponse<UserBehaviorResp> getUserBehavior(@RequestParam(defaultValue = "", required = false) Integer tenantId) {
        return ApiResponse.success(dashboardService.getUserBehavior(tenantId));
    }
} 
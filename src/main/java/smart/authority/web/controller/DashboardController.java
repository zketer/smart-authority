package smart.authority.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ApiResponse<StatsResp> getUserStats() {
        return ApiResponse.success(dashboardService.getUserStats());
    }

    @Operation(summary = "获取部门统计")
    @GetMapping("/department/stats")
    public ApiResponse<StatsResp> getDepartmentStats() {
        return ApiResponse.success(dashboardService.getDepartmentStats());
    }

    @Operation(summary = "获取角色统计")
    @GetMapping("/role/stats")
    public ApiResponse<StatsResp> getRoleStats() {
        return ApiResponse.success(dashboardService.getRoleStats());
    }

    @Operation(summary = "获取权限统计")
    @GetMapping("/permission/stats")
    public ApiResponse<StatsResp> getPermissionStats() {
        return ApiResponse.success(dashboardService.getPermissionStats());
    }

    @Operation(summary = "获取租户统计")
    @GetMapping("/tenant/stats")
    public ApiResponse<StatsResp> getTenantStats() {
        return ApiResponse.success(dashboardService.getTenantStats());
    }

    @Operation(summary = "获取用户增长数据")
    @GetMapping("/user/growth")
    public ApiResponse<UserGrowthResp> getUserGrowth() {
        return ApiResponse.success(dashboardService.getUserGrowth());
    }

    @Operation(summary = "获取用户活跃度数据")
    @GetMapping("/user/activity")
    public ApiResponse<UserActivityResp> getUserActivity() {
        return ApiResponse.success(dashboardService.getUserActivity());
    }

    @Operation(summary = "获取用户行为数据")
    @GetMapping("/user/behavior")
    public ApiResponse<UserBehaviorResp> getUserBehavior() {
        return ApiResponse.success(dashboardService.getUserBehavior());
    }
} 
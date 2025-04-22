package com.smartauthority.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smart.authority.common.model.ApiResponse;
import smart.authority.web.service.DashboardService;

/**
 * @author lynn
 */
@Tag(name = "统计信息接口")
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    @Operation(summary = "获取用户总数")
    @GetMapping("/user-count")
    public ApiResponse<Long> getUserCount() {
        return ApiResponse.success(dashboardService.getUserCount());
    }

    @Operation(summary = "获取角色总数")
    @GetMapping("/role-count")
    public ApiResponse<Long> getRoleCount() {
        return ApiResponse.success(dashboardService.getRoleCount());
    }

    @Operation(summary = "获取权限总数")
    @GetMapping("/permission-count")
    public ApiResponse<Long> getPermissionCount() {
        return ApiResponse.success(dashboardService.getPermissionCount());
    }

    @Operation(summary = "获取租户总数")
    @GetMapping("/tenant-count")
    public ApiResponse<Long> getTenantCount() {
        return ApiResponse.success(dashboardService.getTenantCount());
    }
} 
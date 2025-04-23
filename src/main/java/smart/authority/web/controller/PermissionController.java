package smart.authority.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import smart.authority.common.model.ApiResponse;
import smart.authority.web.model.req.permission.PermissionCreateReq;
import smart.authority.web.model.req.permission.PermissionQueryReq;
import smart.authority.web.model.req.permission.PermissionUpdateReq;
import smart.authority.web.model.resp.PermissionResp;
import smart.authority.web.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lynn
 */
@RestController
@RequestMapping("/api/permissions")
@Tag(name = "PermissionController", description = "权限相关接口")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @PostMapping
    @Operation(summary = "创建权限")
    public ApiResponse<Void> createPermission(@Validated @RequestBody PermissionCreateReq req) {
        permissionService.createPermission(req);
        return ApiResponse.success(null);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取权限详情")
    public ApiResponse<PermissionResp> getPermission(@PathVariable Integer id) {
        return ApiResponse.success(permissionService.getPermissionById(id));
    }

    @GetMapping
    @Operation(summary = "分页查询权限")
    public ApiResponse<IPage<PermissionResp>> pagePermissions(@Valid PermissionQueryReq req) {
        return ApiResponse.success(permissionService.pagePermissions(req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除权限")
    public ApiResponse<Void> deletePermission(@PathVariable Integer id) {
        permissionService.deletePermission(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/role/{roleId}")
    @Operation(summary = "获取角色的所有权限")
    public ApiResponse<List<PermissionResp>> getRolePermissions(@PathVariable Integer roleId) {
        return ApiResponse.success(permissionService.getPermissionsByRoleId(roleId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户的所有权限")
    public ApiResponse<List<PermissionResp>> getUserPermissions(@PathVariable Integer userId) {
        return ApiResponse.success(permissionService.getPermissionsByUserId(userId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新权限")
    public ApiResponse<PermissionResp> updatePermission(@PathVariable Integer id, @Validated @RequestBody PermissionUpdateReq req) {
        req.setId(id);
        return ApiResponse.success(permissionService.updatePermission(req));
    }

    @GetMapping("/tree")
    @Operation(summary = "获取权限树")
    public ApiResponse<List<PermissionResp>> getPermissionTree() {
        return ApiResponse.success(permissionService.getPermissionTree());
    }
}
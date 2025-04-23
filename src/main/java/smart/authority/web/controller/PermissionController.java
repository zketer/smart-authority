package smart.authority.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import smart.authority.common.model.ApiResponse;
import smart.authority.web.model.entity.Permission;
import smart.authority.web.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lynn
 */
@RestController
@RequestMapping("/permissions")
@Tag(name = "PermissionController", description = "权限相关接口")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @PostMapping
    @Operation(summary = "创建权限")
    public ApiResponse<Permission> createPermission(@RequestBody Permission permission) {
        return ApiResponse.success(permissionService.createPermission(permission));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取权限详情")
    public ApiResponse<Permission> getPermission(@PathVariable Integer id) {
        return ApiResponse.success(permissionService.getPermissionById(id));
    }

    @GetMapping
    @Operation(summary = "分页查询权限")
    public ApiResponse<Page<Permission>> pagePermissions(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String name) {
        return ApiResponse.success(permissionService.pagePermissions(new Page<>(current, size), name));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除权限")
    public ApiResponse<Void> deletePermission(@PathVariable Integer id) {
        permissionService.deletePermission(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/role/{roleId}")
    @Operation(summary = "获取角色的所有权限")
    public ApiResponse<List<Permission>> getRolePermissions(@PathVariable Integer roleId) {
        return ApiResponse.success(permissionService.getPermissionsByRoleId(roleId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户的所有权限")
    public ApiResponse<List<Permission>> getUserPermissions(@PathVariable Integer userId) {
        return ApiResponse.success(permissionService.getPermissionsByUserId(userId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新权限")
    public ApiResponse<Permission> updatePermission(@PathVariable Integer id, @RequestBody Permission permission) {
        permission.setId(id);
        return ApiResponse.success(permissionService.updatePermission(permission));
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有权限")
    public ApiResponse<List<Permission>> getAllPermissions() {
        return ApiResponse.success(permissionService.getAllPermissions());
    }
}
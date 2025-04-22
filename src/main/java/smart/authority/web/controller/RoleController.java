package smart.authority.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import smart.authority.common.model.ApiResponse;
import smart.authority.web.model.entity.Role;
import smart.authority.web.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author lynn
 */
@RestController
@RequestMapping("/roles")
@Tag(name = "RoleController", description = "角色相关接口")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping
    @Operation(summary = "创建角色")
    public ApiResponse<Role> createRole(@RequestBody Role role) {
        return ApiResponse.success(roleService.createRole(role));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取角色详情")
    public ApiResponse<Role> getRole(@PathVariable Integer id) {
        return ApiResponse.success(roleService.getRole(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新角色")
    public ApiResponse<Role> updateRole(@PathVariable Integer id, @RequestBody Role role) {
        role.setId(id);
        return ApiResponse.success(roleService.updateRole(role));
    }

    @GetMapping
    @Operation(summary = "查询角色（支持分页）")
    public ApiResponse<?> getRoles(
            @RequestParam(required = false, defaultValue = "1") Integer current,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String name) {
        if (size > 0) {
            Page<Role> page = roleService.pageRoles(new Page<>(current, size), name);
            return ApiResponse.success(page);
        } else {
            List<Role> roles = roleService.listRoles(name);
            return ApiResponse.success(roles);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色")
    public ApiResponse<Void> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ApiResponse.success(null);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户的所有角色")
    public ApiResponse<List<Role>> getUserRoles(@PathVariable Integer userId) {
        return ApiResponse.success(roleService.getRolesByUserId(userId));
    }

    @PostMapping("/{roleId}/permissions")
    @Operation(summary = "为角色分配权限")
    public ApiResponse<Void> assignPermissions(
            @PathVariable Integer roleId,
            @RequestBody List<Integer> permissionIds) {
        roleService.assignPermissions(roleId, permissionIds);
        return ApiResponse.success(null);
    }
}
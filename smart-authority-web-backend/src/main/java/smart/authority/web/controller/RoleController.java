package smart.authority.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import smart.authority.common.model.ApiResponse;
import smart.authority.web.model.entity.Role;
import smart.authority.web.model.req.role.RoleCreateReq;
import smart.authority.web.model.req.role.RoleQueryReq;
import smart.authority.web.model.req.role.RoleUpdateReq;
import smart.authority.web.model.resp.RoleResp;
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
    public ApiResponse<Void> createRole(@RequestBody RoleCreateReq req) {
        roleService.createRole(req);
        return ApiResponse.success(null);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取角色详情")
    public ApiResponse<RoleResp> getRole(@PathVariable Integer id) {
        return ApiResponse.success(roleService.getRoleById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新角色")
    public ApiResponse<Void> updateRole(@PathVariable Integer id, @RequestBody RoleUpdateReq req) {
        req.setId(id);
        roleService.updateRole(req);
        return ApiResponse.success(null);
    }

    @GetMapping
    @Operation(summary = "查询角色（支持分页）")
    public ApiResponse<?> getRoles(RoleQueryReq req) {
        if (req.getSize() > 0) {
            Page<RoleResp> page = roleService.pageRoles(req);
            return ApiResponse.success(page);
        } else {
            List<RoleResp> roles = roleService.listRoles(req);
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
    public ApiResponse<List<RoleResp>> getUserRoles(@PathVariable Integer userId) {
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
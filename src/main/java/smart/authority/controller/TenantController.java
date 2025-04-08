package smart.authority.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import smart.authority.common.model.ApiResponse;
import smart.authority.entity.Tenant;
import smart.authority.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author lynn
 */
@RestController
@RequestMapping("/tenants")
@Tag(name = "TenantController", description = "租户相关接口")
public class TenantController {

    @Resource
    private TenantService tenantService;

    @PostMapping
    @Operation(summary = "创建租户")
    public ApiResponse<Tenant> createTenant(@RequestBody Tenant tenant) {

        return ApiResponse.success(tenantService.createTenant(tenant));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取租户详情")
    public ApiResponse<Tenant> getTenant(@PathVariable Long id) {

        return ApiResponse.success(tenantService.getTenantById(id));
    }

    @GetMapping
    @Operation(summary = "分页查询租户")
    public ApiResponse<Page<Tenant>> pageTenants(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String name) {
        return ApiResponse.success(tenantService.pageTenants(new Page<>(current, size), name));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除租户")
    public ApiResponse deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return ApiResponse.success(null);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新租户")
    public ApiResponse<Tenant> updateTenant(@PathVariable Long id, @RequestBody Tenant tenant) {
        tenant.setId(id);
        return ApiResponse.success(tenantService.updateTenant(tenant));
    }
}
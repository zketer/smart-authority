package smart.authority.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import smart.authority.common.ApiResponse;
import smart.authority.web.model.req.tenant.TenantCreateReq;
import smart.authority.web.model.req.tenant.TenantQueryReq;
import smart.authority.web.model.req.tenant.TenantUpdateReq;
import smart.authority.web.model.resp.tenant.TenantResp;
import smart.authority.web.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author lynn
 */
@RestController
@RequestMapping("/smart-authority/v1.0/tenants")
@RequiredArgsConstructor
@Tag(name = "TenantController", description = "租户相关接口")
public class TenantController {

    private final TenantService tenantService;

    /**
     * 分页查询租户
     */
    @GetMapping
    @Operation(summary = "分页查询租户")
    public ApiResponse<Page<TenantResp>> pageTenants(@Valid TenantQueryReq req) {
        return ApiResponse.ok(tenantService.pageTenants(req));
    }

    /**
     * 创建租户
     */
    @PostMapping
    @Operation(summary = "创建租户")
    public ApiResponse<TenantResp> createTenant(@Valid @RequestBody TenantCreateReq req) {
        return ApiResponse.ok(tenantService.createTenant(req));
    }

    /**
     * 更新租户
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新租户")
    public ApiResponse<TenantResp> updateTenant(@PathVariable Integer id, @Valid @RequestBody TenantUpdateReq req) {
        req.setId(id);
        return ApiResponse.ok(tenantService.updateTenant(req));
    }

    /**
     * 获取租户详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取租户详情")
    public ApiResponse<TenantResp> getTenant(@PathVariable Integer id) {
        return ApiResponse.ok(tenantService.getTenant(id));
    }

    /**
     * 删除租户
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除租户")
    public ApiResponse<Void> deleteTenant(@PathVariable Integer id) {
        tenantService.deleteTenant(id);
        return ApiResponse.ok();
    }
}
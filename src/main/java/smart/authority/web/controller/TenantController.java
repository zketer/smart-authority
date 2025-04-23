package smart.authority.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import smart.authority.common.model.ApiResponse;
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
@RequestMapping("/tenants")
@Tag(name = "TenantController", description = "租户相关接口")
public class TenantController {

    @Resource
    private TenantService tenantService;

    /**
     * 分页查询租户
     */
    @GetMapping
    @Operation(summary = "分页查询租户")
    public ApiResponse<IPage<TenantResp>> pageTenants(@Valid TenantQueryReq req) {
        return ApiResponse.success(tenantService.pageTenants(req));
    }

    /**
     * 创建租户
     */
    @PostMapping
    @Operation(summary = "创建租户")
    public ApiResponse<Void> createTenant(@Valid @RequestBody TenantCreateReq req) {
        tenantService.createTenant(req);
        return ApiResponse.success(null);
    }

    /**
     * 更新租户
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新租户")
    public ApiResponse<Void> updateTenant(@PathVariable Integer id, @Valid @RequestBody TenantUpdateReq req) {
        req.setId(id);
        tenantService.updateTenant(req);
        return ApiResponse.success(null);
    }

    /**
     * 获取租户详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取租户详情")
    public ApiResponse<TenantResp> getTenant(@PathVariable Integer id) {
        return ApiResponse.success(tenantService.getTenant(id));
    }

    /**
     * 删除租户
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除租户")
    public ApiResponse<Void> deleteTenant(@PathVariable Integer id) {
        tenantService.deleteTenant(id);
        return ApiResponse.success(null);
    }
}
package smart.authority.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import smart.authority.web.model.entity.Tenant;
import smart.authority.web.model.req.tenant.TenantCreateReq;
import smart.authority.web.model.req.tenant.TenantQueryReq;
import smart.authority.web.model.req.tenant.TenantUpdateReq;
import smart.authority.web.model.resp.tenant.TenantResp;

/**
 * 租户服务接口
 */
public interface TenantService extends IService<Tenant> {
    
    /**
     * 分页查询租户
     */
    Page<TenantResp> pageTenants(TenantQueryReq req);
    
    /**
     * 创建租户
     */
    TenantResp createTenant(TenantCreateReq req);
    
    /**
     * 更新租户
     */
    TenantResp updateTenant(TenantUpdateReq req);
    
    /**
     * 获取租户详情
     */
    TenantResp getTenant(Integer id);
    
    /**
     * 删除租户
     */
    void deleteTenant(Integer id);
}
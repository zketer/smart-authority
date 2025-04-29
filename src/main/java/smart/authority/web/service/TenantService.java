package smart.authority.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import smart.authority.web.model.entity.Tenant;
import smart.authority.web.model.req.tenant.TenantCreateReq;
import smart.authority.web.model.req.tenant.TenantQueryReq;
import smart.authority.web.model.req.tenant.TenantUpdateReq;
import smart.authority.web.model.resp.tenant.TenantResp;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 租户服务接口
 */
public interface TenantService extends IService<Tenant> {

    /**
     * 创建租户
     */
    void createTenant(TenantCreateReq req);
    
    /**
     * 更新租户
     */
    void updateTenant(TenantUpdateReq req);
    
    /**
     * 获取租户详情
     */
    TenantResp getTenant(Integer id);
    
    /**
     * 删除租户
     */
    void deleteTenant(Integer id);

    /**
     * 分页查询租户
     */
    IPage<TenantResp> pageTenants(TenantQueryReq req);

    /**
     * 查询租户
     */
    Map<Integer, String> getTenantByIds(List<Integer> ids);
}
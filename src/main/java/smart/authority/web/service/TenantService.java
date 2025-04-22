package smart.authority.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import smart.authority.web.model.entity.Tenant;

public interface TenantService extends IService<Tenant> {
    /**
     * 创建租户
     *
     * @param tenant 租户信息
     * @return 租户信息
     */
    Tenant createTenant(Tenant tenant);

    /**
     * 获取租户详情
     *
     * @param id 租户ID
     * @return 租户信息
     */
    Tenant getTenantById(Integer id);

    /**
     * 分页查询租户
     *
     * @param page 分页参数
     * @param name 租户名称
     * @return 分页租户信息
     */
    Page<Tenant> pageTenants(Page<Tenant> page, String name);

    /**
     * 删除租户
     *
     * @param id 租户ID
     */
    void deleteTenant(Integer id);

    /**
     * 更新租户信息
     *
     * @param tenant 租户信息
     * @return 更新后的租户信息
     */
    Tenant updateTenant(Tenant tenant);
}
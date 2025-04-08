package smart.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import smart.authority.entity.Tenant;
import smart.authority.mapper.TenantMapper;
import smart.authority.service.TenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Override
    @Transactional
    public Tenant createTenant(Tenant tenant) {
        save(tenant);
        return tenant;
    }

    @Override
    public Tenant getTenantById(Long id) {
        return getById(id);
    }

    @Override
    public Page<Tenant> pageTenants(Page<Tenant> page, String name) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like(Tenant::getName, name);
        }
        return page(page, wrapper);
    }

    @Override
    @Transactional
    public void deleteTenant(Long id) {
        removeById(id);
    }

    @Override
    @Transactional
    public Tenant updateTenant(Tenant tenant) {
        if (tenant.getId() == null) {
            throw new IllegalArgumentException("Tenant id cannot be null");
        }
        updateById(tenant);
        return getById(tenant.getId());
    }
}
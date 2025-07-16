package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smart.authority.common.BeanCopyUtils;
import smart.authority.web.model.entity.Tenant;
import smart.authority.web.mapper.TenantMapper;
import smart.authority.web.model.req.tenant.TenantCreateReq;
import smart.authority.web.model.req.tenant.TenantQueryReq;
import smart.authority.web.model.req.tenant.TenantUpdateReq;
import smart.authority.web.model.resp.tenant.TenantResp;
import smart.authority.web.service.TenantService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 租户服务实现类
 * @author lynn
 */
@Service
@RequiredArgsConstructor
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Override
    public Page<TenantResp> pageTenants(TenantQueryReq req) {
        // 构建查询条件
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<Tenant>()
                .like(StringUtils.isNotBlank(req.getName()), Tenant::getName, req.getName())
                .eq(Objects.nonNull(req.getStatus()), Tenant::getStatus, req.getStatus());

        // 执行分页查询
        Page<Tenant> page = new Page<>(req.getCurrent(), req.getSize());
        page = this.page(page, wrapper);

        // 转换为响应对象
        Page<TenantResp> respPage = new Page<>();
        BeanUtils.copyProperties(page, respPage, "records");
        List<TenantResp> records = page.getRecords().stream()
                .map(tenant -> {
                    TenantResp resp = new TenantResp();
                    BeanUtils.copyProperties(tenant, resp);
                    return resp;
                })
                .collect(Collectors.toList());
        respPage.setRecords(records);

        return respPage;
    }

    @Override
    public Map<Integer, String> getTenantByIds(List<Integer> ids) {
        List<Tenant> tenants = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ids)) {
            tenants = baseMapper.selectBatchIds(ids);
        }
        return tenants.stream().collect(Collectors.toMap(Tenant::getId, Tenant::getName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createTenant(TenantCreateReq req) {

        // 设置默认值
        if (StringUtils.isBlank(req.getStatus())) {
            req.setStatus("open");
        }
        if (StringUtils.isBlank(req.getIsDefault())) {
            req.setIsDefault("not default");
        }

        // 转换为实体对象
        Tenant tenant = BeanCopyUtils.copyBean(req, Tenant.class);
        
        // 保存租户
        this.save(tenant);

        // 转换为响应对象
        BeanCopyUtils.copyBean(tenant, TenantResp.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenant(TenantUpdateReq req) {
        // 检查租户是否存在
        Tenant existingTenant = this.getById(req.getId());
        if (existingTenant == null) {
            throw new RuntimeException("租户不存在");
        }

        // 如果是默认租户，不允许修改状态和默认标识
        if (Objects.equals(existingTenant.getIsDefault(), "default")) {
            if (!Objects.equals(req.getStatus(), existingTenant.getStatus()) ||
                !Objects.equals(req.getIsDefault(), existingTenant.getIsDefault())) {
                throw new RuntimeException("默认租户不允许修改状态和默认标识");
            }
        }

        // 设置默认值
        if (StringUtils.isBlank(req.getStatus())) {
            req.setStatus(existingTenant.getStatus());
        }
        if (StringUtils.isBlank(req.getIsDefault())) {
            req.setIsDefault(existingTenant.getIsDefault());
        }

        // 转换为实体对象并更新
        Tenant tenant = BeanCopyUtils.copyBean(req, Tenant.class);
        this.updateById(tenant);

        // 转换为响应对象
        BeanCopyUtils.copyBean(tenant, TenantResp.class);
    }

    @Override
    public TenantResp getTenant(Integer id) {
        // 获取租户
        Tenant tenant = this.getById(id);
        if (tenant == null) {
            throw new RuntimeException("租户不存在");
        }

        // 转换为响应对象
        return BeanCopyUtils.copyBean(tenant, TenantResp.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTenant(Integer id) {
        // 检查租户是否存在
        Tenant tenant = this.getById(id);
        if (tenant == null) {
            throw new RuntimeException("租户不存在");
        }

        // 默认租户不允许删除
        if (Objects.equals(tenant.getIsDefault(), "default")) {
            throw new RuntimeException("默认租户不允许删除");
        }

        // 删除租户
        this.removeById(id);
    }
}
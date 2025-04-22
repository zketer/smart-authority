package smart.authority.web.service.impl;

import jakarta.annotation.Resource;
import smart.authority.web.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author lynn
 */
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private TenantService tenantService;
    @Override
    public Long getUserCount() {
        return userService.count();
    }

    @Override
    public Long getRoleCount() {
        return roleService.count();
    }

    @Override
    public Long getPermissionCount() {
        return permissionService.count();
    }

    @Override
    public Long getTenantCount() {
        return tenantService.count();
    }
} 
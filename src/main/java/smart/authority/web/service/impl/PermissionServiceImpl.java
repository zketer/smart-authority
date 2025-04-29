package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import smart.authority.common.exception.BusinessException;
import smart.authority.common.exception.ErrorCode;
import smart.authority.web.model.entity.Permission;
import smart.authority.web.model.entity.Role;
import smart.authority.web.model.entity.RolePermission;
import smart.authority.web.model.entity.Tenant;
import smart.authority.web.mapper.PermissionMapper;
import smart.authority.web.mapper.RoleMapper;
import smart.authority.web.mapper.RolePermissionMapper;
import smart.authority.web.mapper.TenantMapper;
import smart.authority.web.model.req.permission.PermissionCreateReq;
import smart.authority.web.model.req.permission.PermissionQueryReq;
import smart.authority.web.model.req.permission.PermissionUpdateReq;
import smart.authority.web.model.resp.PermissionResp;
import smart.authority.web.model.resp.RoleResp;
import smart.authority.web.model.resp.tenant.TenantResp;
import smart.authority.web.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private TenantMapper tenantMapper;

    @Override
    @Transactional
    public void createPermission(PermissionCreateReq req) {
        // 1. 检查权限编码是否重复
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getCode, req.getCode());
        if (count(wrapper) > 0) {
            throw new BusinessException(ErrorCode.PERMISSION_CODE_EXISTS);
        }

        // 2. 如果有父权限，检查父权限是否存在
        if (req.getParentId() != null && req.getParentId() > 0) {
            Permission parent = getById(req.getParentId());
            if (parent == null) {
                throw new BusinessException(ErrorCode.PERMISSION_PARENT_NOT_FOUND);
            }
        }

        // 3. 创建权限
        Permission permission = new Permission();
        BeanUtils.copyProperties(req, permission);
        save(permission);
    }

    @Override
    public PermissionResp getPermissionById(Integer id) {
        Permission permission = getById(id);
        if (permission == null) {
            return null;
        }

        PermissionResp resp = new PermissionResp();
        BeanUtils.copyProperties(permission, resp);

        // 获取父权限名称
        if (permission.getParentId() != null && permission.getParentId() > 0) {
            Permission parent = getById(permission.getParentId());
            if (parent != null) {
                resp.setParentName(parent.getName());
            }
        }

        // 获取角色信息
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(
            new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getPermissionId, id)
        );
        
        if (!rolePermissions.isEmpty()) {
            Set<RoleResp> roles = rolePermissions.stream()
                .map(rp -> {
                    Role role = roleMapper.selectById(rp.getRoleId());
                    if (role != null) {
                        RoleResp roleResp = new RoleResp();
                        BeanUtils.copyProperties(role, roleResp);
                        return roleResp;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
            resp.setRoles(roles);
        }

        return resp;
    }

    @Override
    public IPage<PermissionResp> pagePermissions(PermissionQueryReq req) {
        // 1. 构建查询条件
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(req.getName()), Permission::getName, req.getName())
                .like(StringUtils.hasText(req.getCode()), Permission::getCode, req.getCode())
                .eq(req.getType() != null, Permission::getType, req.getType())
                .eq(req.getParentId() != null, Permission::getParentId, req.getParentId());
        if (req.getTenantId() != null) {
            wrapper.eq(Permission::getTenantId, req.getTenantId());
        }

        // 2. 执行分页查询
        Page<Permission> page = new Page<>(req.getCurrent(), req.getSize());
        Page<Permission> permissionPage = page(page, wrapper);

        // 3. 转换结果
        Page<PermissionResp> respPage = new Page<>(permissionPage.getCurrent(), permissionPage.getSize(), permissionPage.getTotal());
        respPage.setRecords(permissionPage.getRecords().stream().map(permission -> {
            PermissionResp resp = new PermissionResp();
            BeanUtils.copyProperties(permission, resp);

            // 获取父权限名称
            if (permission.getParentId() != null && permission.getParentId() > 0) {
                Permission parent = getById(permission.getParentId());
                if (parent != null) {
                    resp.setParentName(parent.getName());
                }
            }

            // 获取角色信息
            List<RolePermission> rolePermissions = rolePermissionMapper.selectList(
                new LambdaQueryWrapper<RolePermission>()
                    .eq(RolePermission::getPermissionId, permission.getId())
            );
            
            if (!rolePermissions.isEmpty()) {
                Set<RoleResp> roles = rolePermissions.stream()
                    .map(rp -> {
                        Role role = roleMapper.selectById(rp.getRoleId());
                        if (role != null) {
                            RoleResp roleResp = new RoleResp();
                            BeanUtils.copyProperties(role, roleResp);
                            return roleResp;
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
                resp.setRoles(roles);
            }

            // 获取租户信息
            if (permission.getTenantId() != null) {
                Tenant tenant = tenantMapper.selectById(permission.getTenantId());
                if (tenant != null) {
                    TenantResp tenantResp = new TenantResp();
                    BeanUtils.copyProperties(tenant, tenantResp);
                    resp.setTenantResp(tenantResp);
                }
            }

            return resp;
        }).collect(Collectors.toList()));

        return respPage;
    }

    @Override
    @Transactional
    public void deletePermission(Integer id) {
        // 1. 检查权限是否存在
        Permission permission = getById(id);
        if (permission == null) {
            throw new BusinessException(ErrorCode.PERMISSION_NOT_FOUND);
        }

        // 2. 检查是否有子权限
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getParentId, id);
        if (count(wrapper) > 0) {
            throw new BusinessException(ErrorCode.PERMISSION_HAS_CHILDREN);
        }

        // 3. 删除角色权限关联
        LambdaQueryWrapper<RolePermission> rpWrapper = new LambdaQueryWrapper<>();
        rpWrapper.eq(RolePermission::getPermissionId, id);
        rolePermissionMapper.delete(rpWrapper);

        // 4. 删除权限
        removeById(id);
    }

    @Override
    public List<PermissionResp> getPermissionsByRoleId(Integer roleId) {
        List<Permission> permissions = baseMapper.selectPermissionsByRoleId(roleId);
        return permissions.stream()
                .map(permission -> {
                    PermissionResp resp = new PermissionResp();
                    BeanUtils.copyProperties(permission, resp);
                    // 获取父权限名称
                    if (permission.getParentId() != null && permission.getParentId() > 0) {
                        Permission parent = getById(permission.getParentId());
                        if (parent != null) {
                            resp.setParentName(parent.getName());
                        }
                    }
                    return resp;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionResp> getPermissionsByUserId(Integer userId) {
        List<Permission> permissions = baseMapper.selectPermissionsByUserId(userId);
        return permissions.stream()
                .map(permission -> {
                    PermissionResp resp = new PermissionResp();
                    BeanUtils.copyProperties(permission, resp);
                    // 获取父权限名称
                    if (permission.getParentId() != null && permission.getParentId() > 0) {
                        Permission parent = getById(permission.getParentId());
                        if (parent != null) {
                            resp.setParentName(parent.getName());
                        }
                    }
                    return resp;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PermissionResp updatePermission(PermissionUpdateReq req) {
        // 1. 检查权限是否存在
        Permission existingPermission = getById(req.getId());
        if (existingPermission == null) {
            throw new BusinessException(ErrorCode.PERMISSION_NOT_FOUND);
        }

        // 2. 检查权限编码是否重复
        if (!existingPermission.getCode().equals(req.getCode())) {
            LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Permission::getCode, req.getCode())
                    .ne(Permission::getId, req.getId());
            if (count(wrapper) > 0) {
                throw new BusinessException(ErrorCode.PERMISSION_CODE_EXISTS);
            }
        }

        // 3. 如果有父权限，检查父权限是否存在
        if (req.getParentId() != null && req.getParentId() > 0) {
            Permission parent = getById(req.getParentId());
            if (parent == null) {
                throw new BusinessException(ErrorCode.PERMISSION_PARENT_NOT_FOUND);
            }
            // 检查是否形成循环引用
            if (req.getParentId().equals(req.getId())) {
                throw new BusinessException(ErrorCode.PERMISSION_CIRCULAR_REFERENCE);
            }
        }

        // 4. 更新权限信息
        Permission permission = new Permission();
        BeanUtils.copyProperties(req, permission);
        updateById(permission);

        return getPermissionById(permission.getId());
    }

    @Override
    public List<PermissionResp> getPermissionTree() {
        // 1. 获取所有权限
        List<Permission> allPermissions = list();

        // 2. 按照 parentId 分组
        Map<Integer, List<Permission>> permissionMap = allPermissions.stream()
                .collect(Collectors.groupingBy(p -> p.getParentId() == null ? 0 : p.getParentId()));

        // 3. 构建树形结构
        return buildPermissionTree(0, permissionMap);
    }

    private List<PermissionResp> buildPermissionTree(Integer parentId, Map<Integer, List<Permission>> permissionMap) {
        List<Permission> children = permissionMap.get(parentId);
        if (children == null) {
            return new ArrayList<>();
        }

        return children.stream().map(permission -> {
            PermissionResp resp = new PermissionResp();
            BeanUtils.copyProperties(permission, resp);

            // 递归构建子树
            List<PermissionResp> childrenResp = buildPermissionTree(permission.getId(), permissionMap);
            if (!childrenResp.isEmpty()) {
                resp.setChildren(childrenResp);
            }

            return resp;
        }).collect(Collectors.toList());
    }
}
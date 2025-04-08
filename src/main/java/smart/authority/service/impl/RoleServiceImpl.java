package smart.authority.service.impl;

import smart.authority.common.exception.BusinessException;
import smart.authority.common.exception.ErrorCode;
import smart.authority.entity.Role;
import smart.authority.entity.RolePermission;
import smart.authority.entity.UserRole;
import smart.authority.mapper.RoleMapper;
import smart.authority.mapper.RolePermissionMapper;
import smart.authority.mapper.UserRoleMapper;
import smart.authority.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public Role createRole(Role role) {
        if (isRoleNameExists(role.getName(), null)) {
            throw new BusinessException(ErrorCode.ROLE_NAME_EXISTS);
        }
        save(role);
        return role;
    }

    @Override
    @Transactional
    public Role updateRole(Role role) {
        if (isRoleNameExists(role.getName(), role.getId())) {
            throw new BusinessException(ErrorCode.ROLE_NAME_EXISTS);
        }
        updateById(role);
        return role;
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId) {
        removeById(roleId);
        rolePermissionMapper.deleteByRoleId(roleId);
    }

    @Override
    public Role getRole(Long roleId) {
        return getById(roleId);
    }

    @Override
    public Page<Role> pageRoles(Page<Role> page, String name) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            queryWrapper.like(Role::getName, name);
        }
        return page(page, queryWrapper);
    }

    @Override
    public List<Role> listRoles(String name) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            queryWrapper.like(Role::getName, name);
        }
        return list(queryWrapper);
    }

    @Override
    @Transactional
    public void assignPermissions(Long roleId, List<Long> permissionIds) {
        rolePermissionMapper.deleteByRoleId(roleId);
        List<RolePermission> rolePermissions = permissionIds.stream()
                .map(permissionId -> {
                    RolePermission rp = new RolePermission();
                    rp.setRoleId(roleId);
                    rp.setPermissionId(permissionId);
                    return rp;
                })
                .collect(Collectors.toList());
        rolePermissionMapper.batchInsert(rolePermissions);
    }

    @Override
    public Set<Long> getRolePermissions(Long roleId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleId, roleId);
        return rolePermissionMapper.selectList(queryWrapper).stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isRoleNameExists(String name, Long roleId) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getName, name);
        if (roleId != null) {
            queryWrapper.ne(Role::getId, roleId);
        }
        return count(queryWrapper) > 0;
    }

    @Override
    public List<Role> getRolesByUserId(Long userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        List<Long> roleIds = userRoleMapper.selectList(queryWrapper)
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        if (roleIds.isEmpty()) {
            return List.of();
        }

        return listByIds(roleIds);
    }
}
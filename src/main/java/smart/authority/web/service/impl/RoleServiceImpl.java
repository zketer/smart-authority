package smart.authority.web.service.impl;

import smart.authority.common.exception.BusinessException;
import smart.authority.common.exception.ErrorCode;
import smart.authority.web.model.entity.Role;
import smart.authority.web.model.entity.RolePermission;
import smart.authority.web.model.entity.UserRole;
import smart.authority.web.mapper.RoleMapper;
import smart.authority.web.mapper.RolePermissionMapper;
import smart.authority.web.mapper.UserRoleMapper;
import smart.authority.web.service.RoleService;
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
    public void deleteRole(Integer roleId) {
        removeById(roleId);
        rolePermissionMapper.deleteByRoleId(roleId);
    }

    @Override
    public Role getRole(Integer roleId) {
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
    public void assignPermissions(Integer roleId, List<Integer> permissionIds) {
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
    public Set<Integer> getRolePermissions(Integer roleId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleId, roleId);
        return rolePermissionMapper.selectList(queryWrapper).stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isRoleNameExists(String name, Integer roleId) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getName, name);
        if (roleId != null) {
            queryWrapper.ne(Role::getId, roleId);
        }
        return count(queryWrapper) > 0;
    }

    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        List<Integer> roleIds = userRoleMapper.selectList(queryWrapper)
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        if (roleIds.isEmpty()) {
            return List.of();
        }

        return listByIds(roleIds);
    }
}
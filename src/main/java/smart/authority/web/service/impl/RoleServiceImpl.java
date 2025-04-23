package smart.authority.web.service.impl;

import smart.authority.common.exception.BusinessException;
import smart.authority.common.exception.ErrorCode;
import smart.authority.web.model.entity.Role;
import smart.authority.web.model.entity.RolePermission;
import smart.authority.web.model.entity.UserRole;
import smart.authority.web.mapper.RoleMapper;
import smart.authority.web.mapper.RolePermissionMapper;
import smart.authority.web.mapper.UserRoleMapper;
import smart.authority.web.model.req.role.RoleCreateReq;
import smart.authority.web.model.req.role.RoleQueryReq;
import smart.authority.web.model.req.role.RoleUpdateReq;
import smart.authority.web.model.resp.RoleResp;
import smart.authority.web.service.PermissionService;
import smart.authority.web.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RolePermissionMapper rolePermissionMapper;
    private final UserRoleMapper userRoleMapper;
    private final PermissionService permissionService;

    public RoleServiceImpl(RolePermissionMapper rolePermissionMapper,
                          UserRoleMapper userRoleMapper,
                          PermissionService permissionService) {
        this.rolePermissionMapper = rolePermissionMapper;
        this.userRoleMapper = userRoleMapper;
        this.permissionService = permissionService;
    }

    @Override
    @Transactional
    public void createRole(RoleCreateReq req) {
        if (isRoleNameExists(req.getName(), null)) {
            throw new BusinessException(ErrorCode.ROLE_NAME_EXISTS);
        }

        Role role = new Role();
        BeanUtils.copyProperties(req, role);
        save(role);

        // 分配权限
        if (req.getPermissionIds() != null && !req.getPermissionIds().isEmpty()) {
            assignPermissions(role.getId(), req.getPermissionIds());
        }
    }

    @Override
    @Transactional
    public void updateRole(RoleUpdateReq req) {
        if (isRoleNameExists(req.getName(), req.getId())) {
            throw new BusinessException(ErrorCode.ROLE_NAME_EXISTS);
        }

        Role role = new Role();
        BeanUtils.copyProperties(req, role);
        updateById(role);

        // 更新权限
        if (req.getPermissionIds() != null) {
            assignPermissions(role.getId(), req.getPermissionIds());
        }

        getRoleById(role.getId());
    }

    @Override
    @Transactional
    public void deleteRole(Integer roleId) {
        // 检查是否有用户关联
        LambdaQueryWrapper<UserRole> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.eq(UserRole::getRoleId, roleId);
        if (userRoleMapper.selectCount(userRoleWrapper) > 0) {
            throw new BusinessException(ErrorCode.ROLE_HAS_USERS);
        }

        removeById(roleId);
        rolePermissionMapper.deleteByRoleId(roleId);
    }

    @Override
    public RoleResp getRoleById(Integer roleId) {
        Role role = getById(roleId);
        if (role == null) {
            return null;
        }

        RoleResp resp = new RoleResp();
        BeanUtils.copyProperties(role, resp);

        // 获取角色的权限
        List<Integer> permissionIds = getRolePermissions(roleId);
        resp.setPermissionIds(permissionIds);

        return resp;
    }

    @Override
    public Page<RoleResp> pageRoles(RoleQueryReq req) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(req.getName()), Role::getName, req.getName())
                   .like(StringUtils.hasText(req.getCode()), Role::getCode, req.getCode())
                   .eq(req.getStatus() != null, Role::getStatus, req.getStatus());

        Page<Role> page = new Page<>(req.getCurrent(), req.getSize());
        Page<Role> rolePage = page(page, queryWrapper);

        Page<RoleResp> respPage = new Page<>(rolePage.getCurrent(), rolePage.getSize(), rolePage.getTotal());
        List<RoleResp> records = rolePage.getRecords().stream()
                .map(role -> {
                    RoleResp resp = new RoleResp();
                    BeanUtils.copyProperties(role, resp);
                    resp.setPermissionIds(getRolePermissions(role.getId()));
                    return resp;
                })
                .collect(Collectors.toList());
        respPage.setRecords(records);

        return respPage;
    }

    @Override
    public List<RoleResp> listRoles(RoleQueryReq req) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(req.getName()), Role::getName, req.getName())
                   .like(StringUtils.hasText(req.getCode()), Role::getCode, req.getCode())
                   .eq(req.getStatus() != null, Role::getStatus, req.getStatus());

        return list(queryWrapper).stream()
                .map(role -> {
                    RoleResp resp = new RoleResp();
                    BeanUtils.copyProperties(role, resp);
                    resp.setPermissionIds(getRolePermissions(role.getId()));
                    return resp;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void assignPermissions(Integer roleId, List<Integer> permissionIds) {
        rolePermissionMapper.deleteByRoleId(roleId);
        if (permissionIds != null && !permissionIds.isEmpty()) {
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
    }

    @Override
    public List<Integer> getRolePermissions(Integer roleId) {
        return rolePermissionMapper.selectList(
                new LambdaQueryWrapper<RolePermission>()
                        .eq(RolePermission::getRoleId, roleId)
        ).stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
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
    public List<RoleResp> getRolesByUserId(Integer userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId, userId);
        List<Integer> roleIds = userRoleMapper.selectList(queryWrapper)
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());

        if (roleIds.isEmpty()) {
            return new ArrayList<>();
        }

        return listByIds(roleIds).stream()
                .map(role -> {
                    RoleResp resp = new RoleResp();
                    BeanUtils.copyProperties(role, resp);
                    resp.setPermissionIds(getRolePermissions(role.getId()));
                    return resp;
                })
                .collect(Collectors.toList());
    }
}
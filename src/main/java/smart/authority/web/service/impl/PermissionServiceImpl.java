package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import smart.authority.web.model.entity.Permission;
import smart.authority.web.model.entity.RolePermission;
import smart.authority.web.mapper.PermissionMapper;
import smart.authority.web.mapper.RolePermissionMapper;
import smart.authority.web.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private final RolePermissionMapper rolePermissionMapper;

    public PermissionServiceImpl(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    @Transactional
    public Permission createPermission(Permission permission) {
        save(permission);
        return permission;
    }

    @Override
    public Permission getPermissionById(Integer id) {
        return getById(id);
    }

    @Override
    public Page<Permission> pagePermissions(Page<Permission> page, String name) {
        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like(Permission::getName, name);
        }
        return page(page, wrapper);
    }

    @Override
    @Transactional
    public void deletePermission(Integer id) {
        // 删除角色权限关联
        LambdaQueryWrapper<RolePermission> rpWrapper = new LambdaQueryWrapper<>();
        rpWrapper.eq(RolePermission::getPermissionId, id);
        rolePermissionMapper.delete(rpWrapper);

        // 删除权限
        removeById(id);
    }

    @Override
    public List<Permission> getPermissionsByRoleId(Integer roleId) {
        return baseMapper.selectPermissionsByRoleId(roleId);
    }

    @Override
    public List<Permission> getPermissionsByUserId(Integer userId) {
        return baseMapper.selectPermissionsByUserId(userId);
    }

    @Override
    @Transactional
    public Permission updatePermission(Permission permission) {
        updateById(permission);
        return permission;
    }

    @Override
    public List<Permission> getAllPermissions() {
        // 获取所有权限
        List<Permission> allPermissions = list();
        
        // 按照 parentId 分组
        Map<Integer, List<Permission>> permissionMap = allPermissions.stream()
                .collect(Collectors.groupingBy(p -> p.getParentId() == null ? 0 : p.getParentId()));
        
        // 构建树形结构
        return buildPermissionTree(0, permissionMap);
    }

    private List<Permission> buildPermissionTree(Integer parentId, Map<Integer, List<Permission>> permissionMap) {
        List<Permission> children = permissionMap.get(parentId);
        if (children == null) {
            return new ArrayList<>();
        }

        // 按照 sort 字段排序
        children.sort(Comparator.comparing(p -> p.getSort() == null ? 0 : p.getSort()));

        // 递归构建子树
        for (Permission child : children) {
            List<Permission> subTree = buildPermissionTree(child.getId(), permissionMap);
            // 设置子权限
            if (!subTree.isEmpty()) {
                child.setChildren(subTree);
            }
        }

        return children;
    }
}
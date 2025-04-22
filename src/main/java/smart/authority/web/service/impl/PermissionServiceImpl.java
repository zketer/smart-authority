package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import smart.authority.web.model.entity.Permission;
import smart.authority.web.model.entity.RolePermission;
import smart.authority.web.mapper.PermissionMapper;
import smart.authority.web.mapper.RolePermissionMapper;
import smart.authority.web.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    @Transactional(rollbackFor = Exception.class)
    public void deletePermission(Integer id) {
        // 1. 查找所有子权限
        List<Permission> children = baseMapper.selectList(
            new LambdaQueryWrapper<Permission>()
                .eq(Permission::getParentId, id)
        );

        // 2. 递归删除子权限
        for (Permission child : children) {
            deletePermission(child.getId());
        }

        // 3. 删除角色权限关联
        rolePermissionMapper.delete(
            new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getPermissionId, id)
        );

        // 4. 物理删除权限本身
        baseMapper.deleteById(id);
    }

    @Override
    public List<Permission> getPermissionsByRoleId(Long roleId) {
        return baseMapper.selectPermissionsByRoleId(roleId);
    }

    @Override
    public List<Permission> getPermissionsByUserId(Long userId) {
        return baseMapper.selectPermissionsByUserId(userId);
    }

    @Override
    @Transactional
    public Permission updatePermission(Permission permission) {
        if (permission.getId() == null) {
            throw new IllegalArgumentException("Permission id cannot be null");
        }
        // 检查权限是否存在
        Permission existingPermission = getById(permission.getId());
        if (existingPermission == null) {
            throw new IllegalArgumentException("Permission not found");
        }
        // 更新权限信息
        updateById(permission);
        return getById(permission.getId());
    }

    @Override
    public List<Permission> getPermissionTree() {
        // 获取所有权限
        List<Permission> allPermissions = list(new LambdaQueryWrapper<Permission>()
            .orderByAsc(Permission::getId)
        );
        
        // 构建树形结构
        List<Permission> tree = buildTree(allPermissions, null);
        log.info("权限树数据: " + tree);
        return tree;
    }

    /**
     * 构建权限树
     *
     * @param permissions 权限列表
     * @param parentId   父级ID
     * @return 权限树
     */
    private List<Permission> buildTree(List<Permission> permissions, Integer parentId) {
        return permissions.stream()
                .filter(permission -> (parentId == null && (permission.getParentId() == null || permission.getParentId() == 0)) || 
                        (parentId != null && parentId.equals(permission.getParentId())))
                .peek(permission -> {
                    List<Permission> children = buildTree(permissions, permission.getId());
                    if (!children.isEmpty()) {
                        permission.setChildren(children);
                    }
                })
                .collect(Collectors.toList());
    }
}
package smart.authority.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import smart.authority.entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    /**
     * 创建权限
     *
     * @param permission 权限信息
     * @return 权限信息
     */
    Permission createPermission(Permission permission);

    /**
     * 获取权限详情
     *
     * @param id 权限ID
     * @return 权限信息
     */
    Permission getPermissionById(Long id);

    /**
     * 分页查询权限
     *
     * @param page 分页参数
     * @param name 权限名称
     * @return 分页权限信息
     */
    Page<Permission> pagePermissions(Page<Permission> page, String name);

    /**
     * 删除权限
     *
     * @param id 权限ID
     */
    void deletePermission(Long id);

    /**
     * 获取角色的所有权限
     *
     * @param roleId 角色ID
     * @return 权限列表
     */
    List<Permission> getPermissionsByRoleId(Long roleId);

    /**
     * 获取用户的所有权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<Permission> getPermissionsByUserId(Long userId);

    /**
     * 更新权限信息
     *
     * @param permission 权限信息
     * @return 更新后的权限信息
     */
    Permission updatePermission(Permission permission);
}
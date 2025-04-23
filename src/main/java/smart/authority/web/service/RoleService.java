package smart.authority.web.service;

import smart.authority.web.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Set;

public interface RoleService extends IService<Role> {
    /**
     * 创建角色
     *
     * @param role 角色信息
     * @return 创建的角色
     */
    Role createRole(Role role);

    /**
     * 更新角色信息
     *
     * @param role 角色信息
     * @return 更新后的角色
     */
    Role updateRole(Role role);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     */
    void deleteRole(Integer roleId);

    /**
     * 获取角色信息
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    Role getRole(Integer roleId);

    /**
     * 分页查询角色列表
     *
     * @param page 分页参数
     * @param name 角色名称（可选）
     * @return 分页后的角色列表
     */
    Page<Role> pageRoles(Page<Role> page, String name);

    /**
     * 查询所有角色列表
     *
     * @param name 角色名称（可选）
     * @return 角色列表
     */
    List<Role> listRoles(String name);

    /**
     * 为角色分配权限
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表
     */
    void assignPermissions(Integer roleId, List<Integer> permissionIds);

    /**
     * 获取角色的权限ID列表
     *
     * @param roleId 角色ID
     * @return 权限ID列表
     */
    List<Integer> getRolePermissions(Integer roleId);

    /**
     * 检查角色名是否已存在
     *
     * @param name   角色名
     * @param roleId 当前角色ID（可选，用于更新时排除自身）
     * @return 是否存在
     */
    boolean isRoleNameExists(String name, Integer roleId);

    /**
     * 获取用户的所有角色
     *
     * @param userId 用户ID
     * @return 用户的角色列表
     */
    List<Role> getRolesByUserId(Integer userId);
}
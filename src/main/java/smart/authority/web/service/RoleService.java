package smart.authority.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import smart.authority.web.model.entity.Role;
import smart.authority.web.model.req.role.RoleCreateReq;
import smart.authority.web.model.req.role.RoleQueryReq;
import smart.authority.web.model.req.role.RoleUpdateReq;
import smart.authority.web.model.resp.RoleResp;

import java.util.List;

/**
 * @author lynn
 */
public interface RoleService extends IService<Role> {

    /**
     * 创建角色
     *
     * @param req 创建角色请求
     * @return 角色响应
     */
    void createRole(RoleCreateReq req);

    /**
     * 更新角色
     *
     * @param req 更新角色请求
     */
    void updateRole(RoleUpdateReq req);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     */
    void deleteRole(Integer roleId);

    /**
     * 获取角色详情
     *
     * @param roleId 角色ID
     * @return 角色响应
     */
    RoleResp getRoleById(Integer roleId);

    /**
     * 分页查询角色
     *
     * @param req 查询请求
     * @return 分页角色响应
     */
    Page<RoleResp> pageRoles(RoleQueryReq req);

    /**
     * 查询角色列表
     *
     * @param req 查询请求
     * @return 角色响应列表
     */
    List<RoleResp> listRoles(RoleQueryReq req);

    /**
     * 分配权限
     *
     * @param roleId 角色ID
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
     * 检查角色名称是否存在
     *
     * @param name 角色名称
     * @param roleId 角色ID（更新时使用）
     * @return 是否存在
     */
    boolean isRoleNameExists(String name, Integer roleId);

    /**
     * 获取用户的角色列表
     *
     * @param userId 用户ID
     * @return 角色响应列表
     */
    List<RoleResp> getRolesByUserId(Integer userId);
}
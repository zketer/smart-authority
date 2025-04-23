package smart.authority.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import smart.authority.web.model.entity.Permission;
import smart.authority.web.model.req.permission.PermissionCreateReq;
import smart.authority.web.model.req.permission.PermissionQueryReq;
import smart.authority.web.model.req.permission.PermissionUpdateReq;
import smart.authority.web.model.resp.PermissionResp;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    /**
     * 创建权限
     *
     * @param req 创建权限请求
     */
    void createPermission(PermissionCreateReq req);

    /**
     * 获取权限详情
     *
     * @param id 权限ID
     * @return 权限响应
     */
    PermissionResp getPermissionById(Integer id);

    /**
     * 分页查询权限
     *
     * @param req 查询请求
     * @return 分页权限响应
     */
    IPage<PermissionResp> pagePermissions(PermissionQueryReq req);

    /**
     * 删除权限
     *
     * @param id 权限ID
     */
    void deletePermission(Integer id);

    /**
     * 获取角色的所有权限
     *
     * @param roleId 角色ID
     * @return 权限响应列表
     */
    List<PermissionResp> getPermissionsByRoleId(Integer roleId);

    /**
     * 获取用户的所有权限
     *
     * @param userId 用户ID
     * @return 权限响应列表
     */
    List<PermissionResp> getPermissionsByUserId(Integer userId);

    /**
     * 更新权限信息
     *
     * @param req 更新权限请求
     * @return 更新后的权限响应
     */
    PermissionResp updatePermission(PermissionUpdateReq req);

    /**
     * 获取权限树
     *
     * @return 权限树
     */
    List<PermissionResp> getPermissionTree();
}
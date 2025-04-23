package smart.authority.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import smart.authority.web.model.entity.User;
import smart.authority.web.model.req.user.UserCreateReq;
import smart.authority.web.model.req.user.UserQueryReq;
import smart.authority.web.model.req.user.UserUpdateReq;
import smart.authority.web.model.resp.UserResp;

import java.util.List;

/**
 * @author lynn
 */
public interface UserService extends IService<User> {
    /**
     * 创建用户
     *
     * @param req 创建用户请求
     * @return 用户响应
     */
    UserResp createUser(UserCreateReq req);

    /**
     * 获取用户详情
     *
     * @param id 用户ID
     * @return 用户响应
     */
    UserResp getUserById(Integer id);

    /**
     * 分页查询用户
     *
     * @param req 查询请求
     * @return 分页用户响应
     */
    Page<UserResp> pageUsers(UserQueryReq req);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Integer id);

    /**
     * 更新用户
     *
     * @param req 更新用户请求
     */
    void updateUser(UserUpdateReq req);

    /**
     * 分配角色
     *
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     */
    void assignRoles(Integer userId, List<Integer> roleIds);
}
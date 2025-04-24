package smart.authority.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import smart.authority.web.model.entity.User;
import smart.authority.web.model.req.user.LoginReq;
import smart.authority.web.model.req.user.UserCreateReq;
import smart.authority.web.model.req.user.UserQueryReq;
import smart.authority.web.model.req.user.UserUpdateReq;
import smart.authority.web.model.resp.UserResp;
import smart.authority.web.model.resp.user.LoginResp;

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

    /**
     * 用户登录
     *
     * @param req 登录请求
     * @return 登录响应
     */
    LoginResp login(LoginReq req);

    /**
     * 用户登出
     *
     * @param token 访问令牌
     */
    void logout(String token);

    /**
     * 验证token有效性
     *
     * @param token 访问令牌
     */
    void verifyToken(String token);
}
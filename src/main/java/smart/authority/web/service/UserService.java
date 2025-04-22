package smart.authority.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import smart.authority.web.model.entity.User;
import smart.authority.web.model.req.UserCreateReq;
import smart.authority.web.model.req.UserUpdateReq;
import smart.authority.web.model.resp.UserResp;

/**
 * @author lynn
 */
public interface UserService extends IService<User> {
    /**
     * 创建用户
     *
     * @param req 用户创建请求
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
     * @param current
     * @param size
     * @param username 用户名
     * @return 分页用户响应
     */
    IPage<UserResp> pageUsers(Integer current, Integer size, String username);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Integer id);

    /**
     * 更新用户
     *
     * @param user 用户信息
     */
    void updateUser(User user);

    /**
     * 更新用户
     *
     * @param id 用户ID
     * @param req 用户更新请求
     */
    void updateUser(Integer id, UserUpdateReq req);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);

    User getUserByUsername(String username);

    /**
     * 检查用户是否有权限访问指定路径
     *
     * @param userId 用户ID
     * @param requestPath 请求路径
     * @return 是否有权限
     */
    boolean checkUserPermission(Integer userId, String requestPath);
}
package smart.authority.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import smart.authority.entity.User;
import smart.authority.model.req.UserCreateReq;
import smart.authority.model.resp.UserResp;

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
    UserResp getUserById(Long id);

    /**
     * 分页查询用户
     *
     * @param page     分页参数
     * @param username 用户名
     * @return 分页用户响应
     */
    Page<UserResp> pageUsers(Page<User> page, String username);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);
}
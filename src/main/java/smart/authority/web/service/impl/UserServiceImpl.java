package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import smart.authority.web.model.entity.User;
import smart.authority.web.mapper.UserMapper;
import smart.authority.web.model.req.user.UserCreateReq;
import smart.authority.web.model.req.user.UserQueryReq;
import smart.authority.web.model.req.user.UserUpdateReq;
import smart.authority.web.model.resp.UserResp;
import smart.authority.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lynn
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    @Transactional
    public UserResp createUser(UserCreateReq req) {
        // 1. 检查用户名是否重复
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, req.getUsername());
        if (count(wrapper) > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // 2. 创建用户
        User user = new User();
        BeanUtils.copyProperties(req, user);
        save(user);
        return getUserById(user.getId());
    }

    @Override
    public UserResp getUserById(Integer id) {
        User user = getById(id);
        if (user == null) {
            return null;
        }
        UserResp resp = new UserResp();
        BeanUtils.copyProperties(user, resp);
        // TODO: 获取部门名称和租户名称
        return resp;
    }

    @Override
    public Page<UserResp> pageUsers(UserQueryReq req) {
        // 1. 构建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(req.getName()), User::getUsername, req.getName())
                .like(StringUtils.isNotBlank(req.getEmail()), User::getEmail, req.getEmail())
                .like(StringUtils.isNotBlank(req.getPhone()), User::getPhone, req.getPhone())
                .eq(StringUtils.isNotBlank(req.getStatus()), User::getStatus, req.getStatus());

        // 2. 执行分页查询
        Page<User> page = new Page<>(req.getCurrent(), req.getSize());
        Page<User> userPage = page(page, wrapper);

        // 3. 转换结果
        Page<UserResp> respPage = new Page<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        respPage.setRecords(userPage.getRecords().stream().map(user -> {
            UserResp resp = new UserResp();
            BeanUtils.copyProperties(user, resp);
            // TODO: 获取部门名称和租户名称
            return resp;
        }).collect(java.util.stream.Collectors.toList()));

        return respPage;
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        // 1. 检查用户是否存在
        User user = getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 删除用户
        removeById(id);
    }

    @Override
    @Transactional
    public void updateUser(UserUpdateReq req) {
        // 1. 检查用户是否存在
        User existingUser = getById(req.getId());
        if (existingUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 检查用户名是否重复
        if (!existingUser.getUsername().equals(req.getUsername())) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername, req.getUsername());
            if (count(wrapper) > 0) {
                throw new RuntimeException("用户名已存在");
            }
        }

        // 3. 更新用户信息
        User user = new User();
        BeanUtils.copyProperties(req, user);
        
        // 4. 如果密码为空，保持原密码不变
        if (StringUtils.isBlank(req.getPassword())) {
            user.setPassword(existingUser.getPassword());
        }

        // 5. 保存更新
        updateById(user);
    }
}
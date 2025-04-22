package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import smart.authority.web.model.entity.User;
import smart.authority.web.mapper.UserMapper;
import smart.authority.web.model.req.UserCreateReq;
import smart.authority.web.model.resp.UserResp;
import smart.authority.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    @Transactional
    public UserResp createUser(UserCreateReq req) {
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
    public Page<UserResp> pageUsers(Page<User> page, String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(username), User::getUsername, username);
        Page<User> userPage = page(page, wrapper);
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
        removeById(id);
    }
}
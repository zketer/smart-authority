package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import smart.authority.web.model.entity.User;
import smart.authority.web.mapper.UserMapper;
import smart.authority.web.model.req.UserCreateReq;
import smart.authority.web.model.req.UserUpdateReq;
import smart.authority.web.model.resp.UserResp;
import smart.authority.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

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
        
        // 获取用户权限
        List<String> permissions = userMapper.getUserPermissions(id);
        resp.setPermissions(permissions);
        
        return resp;
    }

    @Override
    public IPage<UserResp> pageUsers(Integer current, Integer pageSize, String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Objects.nonNull(username), User::getUsername, username);
        List<User> list = list(wrapper);
        IPage<User> userPage = new Page<>(current, pageSize);
        IPage<UserResp> respPage = new Page<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        respPage.setRecords(list.stream().map(user -> {
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

    @Override
    @Transactional
    public void updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        
        // 检查用户名是否已存在
        User existingUser = userMapper.selectByUsername(user.getUsername());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            throw new IllegalArgumentException("用户名已存在");
        }

        // 如果密码不为空，则加密密码
        if (StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        updateById(user);
    }

    @Override
    @Transactional
    public void updateUser(Integer id, UserUpdateReq req) {
        User user = baseMapper.selectById(id);
        user.setUsername(req.getUsername());
        if (req.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setDepartmentId(req.getDepartmentId());
        user.setTenantId(req.getTenantId());
        
        // 检查用户名是否已存在
        User existingUser = userMapper.selectByUsername(user.getUsername());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            throw new IllegalArgumentException("用户名已存在");
        }
        
        updateById(user);
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return getOne(wrapper);
    }

    @Override
    public User getUserByUsername(String username) {
        return getByUsername(username);
    }

    @Override
    public boolean checkUserPermission(Integer userId, String requestPath) {
        // 获取用户的所有权限
        List<String> permissions = baseMapper.getUserPermissions(userId);
        
        // TODO: 这里需要根据实际业务逻辑来实现权限检查
        // 示例实现：检查用户是否有对应路径的权限
        // 可以通过配置或数据库存储路径与权限的映射关系
        return permissions.stream()
                .anyMatch(permission -> matchPermissionPath(permission, requestPath));
    }

    /**
     * 匹配权限和请求路径
     * 
     * @param permission 权限标识
     * @param requestPath 请求路径
     * @return 是否匹配
     */
    private boolean matchPermissionPath(String permission, String requestPath) {
        // TODO: 实现具体的匹配逻辑
        // 示例：可以通过配置文件或数据库存储权限和路径的映射关系
        // 这里简单返回true，需要根据实际业务逻辑实现
        return true;
    }
}
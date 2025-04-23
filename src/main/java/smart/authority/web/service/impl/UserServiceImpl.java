package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smart.authority.common.exception.BusinessException;
import smart.authority.common.exception.ErrorCode;
import smart.authority.web.config.JwtConfig;
import smart.authority.web.mapper.UserMapper;
import smart.authority.web.mapper.UserRoleMapper;
import smart.authority.web.model.entity.User;
import smart.authority.web.model.entity.UserRole;
import smart.authority.web.model.req.user.LoginReq;
import smart.authority.web.model.req.user.UserCreateReq;
import smart.authority.web.model.req.user.UserQueryReq;
import smart.authority.web.model.req.user.UserUpdateReq;
import smart.authority.web.model.resp.DepartmentResp;
import smart.authority.web.model.resp.UserResp;
import smart.authority.web.model.resp.tenant.TenantResp;
import smart.authority.web.model.resp.user.LoginResp;
import smart.authority.web.service.DepartmentService;
import smart.authority.web.service.RoleService;
import smart.authority.web.service.TenantService;
import smart.authority.web.service.UserService;
import smart.authority.web.util.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lynn
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserRoleMapper userRoleMapper;
    private final RoleService roleService;
    private final DepartmentService departmentService;
    private final TenantService tenantService;

    @Resource
    private JwtConfig jwtConfig;

    @Resource
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRoleMapper userRoleMapper,
                         RoleService roleService,
                         DepartmentService departmentService,
                         TenantService tenantService) {
        this.userRoleMapper = userRoleMapper;
        this.roleService = roleService;
        this.departmentService = departmentService;
        this.tenantService = tenantService;
    }

    @Override
    @Transactional
    public UserResp createUser(UserCreateReq req) {
        // 1. 检查用户名是否重复（在同一租户下）
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, req.getUsername())
               .eq(User::getTenantId, req.getTenantId());
        if (count(wrapper) > 0) {
            throw new BusinessException(ErrorCode.USER_NAME_EXISTS);
        }

        // 2. 创建用户
        User user = new User();
        BeanUtils.copyProperties(req, user);
        // 设置默认状态
        if (StringUtils.isBlank(user.getStatus())) {
            user.setStatus("open");
        }
        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);

        // 3. 分配角色
        if (req.getRoleIds() != null && !req.getRoleIds().isEmpty()) {
            assignRoles(user.getId(), req.getRoleIds());
        }

        return getUserById(user.getId());
    }

    @Override
    public UserResp getUserById(Integer id) {
        User user = getById(id);
        if (user == null) {
            return null;
        }
        return convertToUserResp(user);
    }

    @Override
    public Page<UserResp> pageUsers(UserQueryReq req) {
        req.setTenantId(1);
        // 1. 构建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getTenantId, req.getTenantId());
        wrapper.like(StringUtils.isNotBlank(req.getName()), User::getName, req.getName());
        wrapper.like(StringUtils.isNotBlank(req.getUsername()), User::getUsername, req.getUsername());
        wrapper.like(StringUtils.isNotBlank(req.getEmail()), User::getEmail, req.getEmail());
        wrapper.like(StringUtils.isNotBlank(req.getPhone()), User::getPhone, req.getPhone());
        wrapper.eq(StringUtils.isNotBlank(req.getStatus()), User::getStatus, req.getStatus());

        // 2. 执行分页查询
        Page<User> page = new Page<>(req.getCurrent(), req.getSize());
        Page<User> userPage = page(page, wrapper);

        // 3. 转换结果
        Page<UserResp> respPage = new Page<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        respPage.setRecords(userPage.getRecords().stream()
                .map(this::convertToUserResp)
                .collect(Collectors.toList()));

        return respPage;
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        // 1. 检查用户是否存在
        User user = getById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        // 3. 删除用户角色关联
        LambdaQueryWrapper<UserRole> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.eq(UserRole::getUserId, id);
        userRoleMapper.delete(userRoleWrapper);

        // 4. 删除用户
        removeById(id);
    }

    @Override
    @Transactional
    public void updateUser(UserUpdateReq req) {
        // 1. 检查用户是否存在
        User existingUser = getById(req.getId());
        if (existingUser == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        // 2. 检查用户名是否重复（在同一租户下）
        if (!existingUser.getUsername().equals(req.getUsername())) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername, req.getUsername())
                   .eq(User::getTenantId, existingUser.getTenantId())
                   .ne(User::getId, req.getId());
            if (count(wrapper) > 0) {
                throw new BusinessException(ErrorCode.USER_NAME_EXISTS);
            }
        }

        // 3. 更新用户信息
        User user = new User();
        BeanUtils.copyProperties(req, user);
        // 保持租户ID不变
        user.setTenantId(existingUser.getTenantId());
        // 如果密码不为空，则加密密码
        if (StringUtils.isNotBlank(req.getPassword())) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        } else {
            user.setPassword(existingUser.getPassword());
        }

        // 4. 更新用户角色
        if (req.getRoleIds() != null) {
            assignRoles(user.getId(), req.getRoleIds());
        }

        // 5. 保存更新
        updateById(user);
    }

    @Override
    @Transactional
    public void assignRoles(Integer userId, List<Integer> roleIds) {
        // 1. 删除现有角色
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        userRoleMapper.delete(wrapper);

        // 2. 分配新角色
        if (roleIds != null && !roleIds.isEmpty()) {
            List<UserRole> userRoles = roleIds.stream()
                    .map(roleId -> {
                        UserRole userRole = new UserRole();
                        userRole.setUserId(userId);
                        userRole.setRoleId(roleId);
                        return userRole;
                    })
                    .toList();
            userRoles.forEach(userRoleMapper::insert);
        }
    }

    @Override
    public LoginResp login(LoginReq req) {
        // 1. 查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, req.getUsername());
        User user = getOne(wrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.USERNAME_PASSWORD_ERROR);
        }

        // 2. 验证密码
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.USERNAME_PASSWORD_ERROR);
        }

        // 3. 检查用户状态
        if ("close".equals(user.getStatus())) {
            throw new BusinessException(ErrorCode.USER_ACCOUNT_DISABLED);
        }

        // 4. 生成 token
        String accessToken = jwtConfig.generateToken(user.getUsername());
        String refreshToken = jwtConfig.generateRefreshToken(user.getUsername());

        // 5. 更新最后登录时间
        user.setLastLoginTime(LocalDateTime.now());
        updateById(user);

        // 6. 返回登录信息
        LoginResp resp = new LoginResp();
        BeanUtils.copyProperties(user, resp);
        resp.setAccessToken(accessToken);
        resp.setRefreshToken(refreshToken);
        return resp;
    }

    @Override
    public void logout(String token) {
        // 这里可以添加 token 黑名单等逻辑
    }

    private UserResp convertToUserResp(User user) {
        UserResp resp = new UserResp();
        BeanUtils.copyProperties(user, resp);

        // 设置部门名称
        if (user.getDepartmentId() != null) {
            DepartmentResp departmentResp = departmentService.getDepartmentById(user.getDepartmentId());
            resp.setDepartmentName(departmentResp.getName());
        }

        // 设置租户名称
        if (user.getTenantId() != null) {
            TenantResp tenantResp = tenantService.getTenant(user.getTenantId());
            resp.setTenantName(tenantResp.getName());
        }

        // 设置角色信息
        resp.setRoles(roleService.getRolesByUserId(user.getId()));

        return resp;
    }
}
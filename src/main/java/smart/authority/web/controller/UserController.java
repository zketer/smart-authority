package smart.authority.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import smart.authority.common.model.ApiResponse;
import smart.authority.web.model.req.UserCreateReq;
import smart.authority.web.model.req.UserUpdateReq;
import smart.authority.web.model.resp.UserResp;
import smart.authority.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import smart.authority.web.model.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author lynn
 */
@Tag(name = "UserController", description = "用户相关接口")
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "创建用户")
    @PostMapping
    public ApiResponse<UserResp> createUser(@Validated @RequestBody UserCreateReq req) {
        return ApiResponse.success(userService.createUser(req));
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    public ApiResponse<UserResp> getUserById(@PathVariable Integer id) {
        return ApiResponse.success(userService.getUserById(id));
    }

    @Operation(summary = "分页查询用户")
    @GetMapping
    public ApiResponse<IPage<UserResp>> pageUsers(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username) {
        return ApiResponse.success(userService.pageUsers(current, pageSize, username));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ApiResponse.success(null);
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    public ApiResponse<Void> updateUser(@PathVariable Integer id, @RequestBody @Valid UserUpdateReq req) {
        userService.updateUser(id, req);
        return ApiResponse.success(null);
    }
}
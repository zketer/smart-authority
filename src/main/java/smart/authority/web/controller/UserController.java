package smart.authority.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import smart.authority.common.model.ApiResponse;
import smart.authority.web.model.req.tenant.TenantQueryReq;
import smart.authority.web.model.req.user.UserCreateReq;
import smart.authority.web.model.req.user.UserQueryReq;
import smart.authority.web.model.req.user.UserUpdateReq;
import smart.authority.web.model.resp.UserResp;
import smart.authority.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author lynn
 */
@Tag(name = "UserController", description = "用户相关接口")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Resource
    private UserService userService;

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
    public ApiResponse<Page<UserResp>> pageUsers(@Valid UserQueryReq req) {
        return ApiResponse.success(userService.pageUsers(req));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ApiResponse.success(null);
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    public ApiResponse<Void> updateUser(@PathVariable Integer id, @Validated @RequestBody UserUpdateReq req) {
        req.setId(id);
        userService.updateUser(req);
        return ApiResponse.success(null);
    }
}
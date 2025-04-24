package smart.authority.web.controller;

import smart.authority.common.model.ApiResponse;
import smart.authority.web.model.req.user.LoginReq;
import smart.authority.web.model.resp.user.LoginResp;
import smart.authority.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "LoginController", description = "登录相关接口")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
    @Resource
    private UserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ApiResponse<LoginResp> login(@Validated @RequestBody LoginReq req) {
        return ApiResponse.success(userService.login(req));
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestHeader("Authorization") String token) {
        userService.logout(token);
        return ApiResponse.success(null);
    }

    @Operation(summary = "验证token有效性")
    @GetMapping("/verify")
    public ApiResponse<Void> verifyToken(@RequestHeader("Authorization") String token) {
        userService.verifyToken(token);
        return ApiResponse.success(null);
    }
} 
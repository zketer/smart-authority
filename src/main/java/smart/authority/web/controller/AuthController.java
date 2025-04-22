package smart.authority.web.controller;

import smart.authority.common.model.ApiResponse;
import smart.authority.web.model.req.LoginRequest;
import smart.authority.web.model.resp.JwtAuthResponse;
import smart.authority.web.security.JwtTokenProvider;
import smart.authority.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "AuthController", description = "认证相关接口")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ApiResponse<JwtAuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login request received for user: {}", loginRequest.getUsername());
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        // 获取用户信息
        var user = userService.getByUsername(loginRequest.getUsername());
        log.info("User found: {}", user);
        
        var userResp = userService.getUserById(user.getId());
        log.info("User permissions: {}", userResp.getPermissions());
        
        JwtAuthResponse response = new JwtAuthResponse(jwt, user.getIsAdmin(), userResp.getPermissions());
        log.info("Login response: {}", response);
        
        return ApiResponse.success(response);
    }
} 
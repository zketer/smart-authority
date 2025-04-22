package smart.authority.web.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import smart.authority.web.model.entity.User;
import smart.authority.web.service.UserService;
import smart.authority.web.utils.JwtTokenUtil;

@Component
@RequiredArgsConstructor
public class SecurityInterceptor implements HandlerInterceptor {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头中获取token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            
            // 获取用户信息
            User user = userService.getUserByUsername(username);
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            
            // 如果是管理员，直接放行
            if (user.getIsAdmin() != null && user.getIsAdmin() == 1) {
                return true;
            }
            
            // 获取请求路径
            String requestPath = request.getRequestURI();
            
            // 检查用户是否有权限访问该路径
            boolean hasPermission = userService.checkUserPermission(user.getId(), requestPath);
            if (!hasPermission) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }
        
        return true;
    }
} 
package smart.authority.web.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import smart.authority.web.config.JwtConfig;
import smart.authority.common.exception.BusinessException;
import smart.authority.common.exception.ErrorCode;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求头中的 token
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        // 验证 token
        if (!jwtConfig.validateToken(token)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED);
        }

        return true;
    }
} 
package smart.authority.web.interceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import smart.authority.web.service.UserBehaviorLogService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lynn
 */
@Component
public class UserBehaviorLogInterceptor implements HandlerInterceptor {

    @Resource
    private UserBehaviorLogService userBehaviorLogService;

    private final Map<String, LocalDateTime> pageStartTimeMap = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader("X-User-Id");
        String tenantId = request.getHeader("X-Tenant-Id");
        String page = request.getRequestURI();
        
        if (userId != null && tenantId != null) {
            // 记录页面访问开始时间
            pageStartTimeMap.put(userId + ":" + page, LocalDateTime.now());
        }
        
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String userId = request.getHeader("X-User-Id");
        String tenantId = request.getHeader("X-Tenant-Id");
        String page = request.getRequestURI();
        String feature = request.getHeader("X-Feature");
        String action = request.getMethod();
        
        if (userId != null && tenantId != null) {
            // 计算页面停留时长
            String key = userId + ":" + page;
            LocalDateTime startTime = pageStartTimeMap.remove(key);
            if (startTime != null) {
                long duration = Duration.between(startTime, LocalDateTime.now()).getSeconds();
                
                // 记录用户行为日志
                userBehaviorLogService.logUserBehavior(
                        Integer.valueOf(userId),
                        Integer.valueOf(tenantId),
                        feature,
                        page,
                        action,
                        (int) duration
                );
            }
        }
    }
} 
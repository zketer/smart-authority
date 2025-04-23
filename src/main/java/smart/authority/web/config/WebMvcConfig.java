package smart.authority.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import smart.authority.web.interceptor.UserBehaviorLogInterceptor;

/**
 * @author lynn
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserBehaviorLogInterceptor userBehaviorLogInterceptor;

    public WebMvcConfig(UserBehaviorLogInterceptor userBehaviorLogInterceptor) {
        this.userBehaviorLogInterceptor = userBehaviorLogInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userBehaviorLogInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/**");
    }
} 
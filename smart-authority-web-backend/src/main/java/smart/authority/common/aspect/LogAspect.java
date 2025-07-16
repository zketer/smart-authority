package smart.authority.common.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

/**
 * @author lynn
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* free.tools.mypermission.controller..*.*(..))")
    public void controllerPoint() {
    }

    @Around("controllerPoint()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        // 记录请求信息
        if (request != null) {
            log.info("Request URL: {} {}", request.getMethod(), request.getRequestURL().toString());
            log.info("Remote IP: {}", request.getRemoteAddr());
        }
        log.info("Class Method: {}.{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName());
        log.info("Request Args: {}", Arrays.toString(point.getArgs()));

        try {
            Object result = point.proceed();
            long costTime = System.currentTimeMillis() - beginTime;
            log.info("Response: {} ({}ms)", result, costTime);
            return result;
        } catch (Exception e) {
            log.error("Request failed", e);
            throw e;
        }
    }
}
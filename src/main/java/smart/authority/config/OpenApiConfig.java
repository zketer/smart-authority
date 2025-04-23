package smart.authority.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

/**
 * @author lynn
 */
@Configuration
@Slf4j
public class OpenApiConfig {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Bean
    public OpenApiCustomizer customizeOperationId() {
        log.info("contextPath: {}", contextPath);
        return openApi -> {
            Paths paths = openApi.getPaths();
            Paths updatedPaths = new Paths();

            paths.forEach((path, pathItem) -> {
                // 检查路径是否已经包含 contextPath
                String updatedPath = path;
                if (!path.startsWith(contextPath)) {
                    updatedPath = contextPath + path;
                }
                log.info("Updated Path: {}", updatedPath);
                updatedPaths.addPathItem(updatedPath, pathItem);
            });

            openApi.setPaths(updatedPaths);
        };
    }
}


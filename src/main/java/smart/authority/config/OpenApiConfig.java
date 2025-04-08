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
            Paths updatedPaths = new Paths(); // 用于存储更新后的路径

            paths.forEach((path, pathItem) -> {
//                pathItem.readOperationsMap().forEach((httpMethod, operation) -> {
//                    String originalOperationId = operation.getOperationId();
//                    if (originalOperationId != null) {
//                        // 更新 operationId，附加 HTTP 方法名称
//                        operation.setOperationId(originalOperationId + "Using" + httpMethod.name());
//                    }
//                    if (operation.getRequestBody() != null && operation.getRequestBody().getContent() != null) {
//                        if (!operation.getRequestBody().getContent().containsKey(MediaType.MULTIPART_FORM_DATA_VALUE)) {
//                            // 如果没有 multipart/form-data，则添加
//                            operation.getRequestBody().getContent().put(
//                                    MediaType.MULTIPART_FORM_DATA_VALUE,
//                                    operation.getRequestBody().getContent().values().iterator().next()
//                            );
//                        }
//                    }
//                });

                // 为路径添加 context-path 前缀
                String updatedPath = contextPath + path;
                log.info("Updated Path: {}", updatedPath);
                updatedPaths.addPathItem(updatedPath, pathItem); // 添加到更新后的 Paths 对象
            });

            openApi.setPaths(updatedPaths); // 最后替换原有的 Paths
        };
    }


}


package smart.authority.web.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "JWT认证响应")
public class JwtAuthResponse {
    
    @Schema(description = "访问令牌")
    private String token;

    @Schema(description = "是否为管理员 0否1是")
    private Integer isAdmin;

    @Schema(description = "用户权限列表")
    private List<String> permissions;
} 
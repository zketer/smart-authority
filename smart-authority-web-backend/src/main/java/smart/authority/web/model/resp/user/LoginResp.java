package smart.authority.web.model.resp.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录响应")
public class LoginResp {
    @Schema(description = "用户ID")
    private Integer id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "是否为管理员")
    private String isAdmin;

    @Schema(description = "访问令牌")
    private String accessToken;

    @Schema(description = "刷新令牌")
    private String refreshToken;

    @Schema(description = "租户ID")
    private Integer tenantId;
} 
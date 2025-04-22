package smart.authority.web.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * @author lynn
 */
@Data
@Schema(description = "更新用户请求")
public class UserUpdateReq {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20之间")
    @Schema(description = "用户名")
    private String username;

    @Size(min = 6, max = 20, message = "密码长度必须在6-20之间")
    @Schema(description = "密码")
    private String password;

    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "部门ID")
    private Integer departmentId;

    @Schema(description = "租户ID")
    private Integer tenantId;

    @Schema(description = "权限列表")
    private List<Integer> permissionIds;
} 
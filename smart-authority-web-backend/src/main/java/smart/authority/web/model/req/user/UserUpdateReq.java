package smart.authority.web.model.req.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseReq;

import java.util.List;

/**
 * @author Lynn.z
 * @since 2025/4/23 10:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "更新用户请求")
public class UserUpdateReq extends BaseReq {

//    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20之间")
    @Schema(description = "用户名")
    private String username;

//    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20之间")
    @Schema(description = "密码")
    private String password;

    @Schema(description = "真实姓名")
    private String name;

    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "部门ID")
    private Integer departmentId;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "用户状态：open-启用，close-禁用")
    private String status;

    @Schema(description = "是否为管理员：admin-是，not admin-否")
    private String isAdmin;

    @Schema(description = "角色ID列表")
    private List<Integer> roleIds;

}

package smart.authority.web.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lynn
 */
@Data
@Schema(description = "用户响应")
public class UserResp {
    @Schema(description = "用户ID")
    private Integer id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "部门ID")
    private Integer departmentId;

    @Schema(description = "部门名称")
    private String departmentName;

    @Schema(description = "租户ID")
    private Integer tenantId;

    @Schema(description = "租户名称")
    private String tenantName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "是否为管理员 0否1是")
    private Integer isAdmin;

    @Schema(description = "用户权限列表")
    private List<String> permissions;
}
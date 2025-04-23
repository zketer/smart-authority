package smart.authority.web.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseResp;

import java.time.LocalDateTime;

/**
 * @author lynn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户响应")
public class UserResp extends BaseResp {

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

    @Schema(description = "租户名称")
    private String tenantName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
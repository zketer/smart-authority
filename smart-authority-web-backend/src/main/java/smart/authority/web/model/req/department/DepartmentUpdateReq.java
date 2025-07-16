package smart.authority.web.model.req.department;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseReq;

/**
 * @author lynn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "更新部门请求")
public class DepartmentUpdateReq extends BaseReq {

    @Schema(description = "部门ID")
    private Integer id;

    @NotBlank(message = "部门名称不能为空")
    @Size(max = 100, message = "部门名称不能超过100个字符")
    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "父部门ID")
    private Integer parentId;

    @Size(max = 255, message = "描述不能超过255个字符")
    @Schema(description = "描述")
    private String description;

    @Schema(description = "租户ID")
    private Integer tenantId;
} 
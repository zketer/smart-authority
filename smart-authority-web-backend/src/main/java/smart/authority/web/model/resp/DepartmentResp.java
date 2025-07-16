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
@Schema(description = "部门响应")
public class DepartmentResp extends BaseResp {

    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "父部门ID")
    private Integer parentId;

    @Schema(description = "父部门名称")
    private String parentName;

    @Schema(description = "租户")
    private String tenantName;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 
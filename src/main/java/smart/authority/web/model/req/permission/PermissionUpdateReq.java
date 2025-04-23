package smart.authority.web.model.req.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseReq;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "更新权限请求")
public class PermissionUpdateReq extends BaseReq {

    @Schema(description = "权限ID")
    private Integer id;

    @Schema(description = "父权限ID")
    private Integer parentId;

    @NotBlank(message = "权限名称不能为空")
    @Schema(description = "权限名称")
    private String name;

    @NotBlank(message = "权限编码不能为空")
    @Schema(description = "权限编码")
    private String code;

    @NotNull(message = "权限类型不能为空")
    @Schema(description = "权限类型：1-菜单，2-按钮，3-API")
    private Integer type;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "描述")
    private String description;
} 
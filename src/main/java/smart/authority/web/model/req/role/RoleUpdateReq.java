package smart.authority.web.model.req.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseReq;

import java.util.List;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "更新角色请求")
public class RoleUpdateReq extends BaseReq {

    @NotNull(message = "角色ID不能为空")
    @Schema(description = "角色ID")
    private Integer id;

    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "角色状态：0-禁用，1-启用")
    private Integer status;

    @Schema(description = "权限ID列表")
    private List<Integer> permissionIds;
} 
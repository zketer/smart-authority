package smart.authority.web.model.req.role;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseReq;

import java.util.List;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "创建角色请求")
public class RoleCreateReq extends BaseReq {

    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "角色状态：close-禁用 open-启用")
    private String status;

    @Schema(description = "权限ID列表")
    private List<Integer> permissionIds;
} 
package smart.authority.web.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseResp;

import java.util.List;
import java.util.Set;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色响应")
public class RoleResp extends BaseResp {

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "角色状态：0-禁用，1-启用")
    private Integer status;

    @Schema(description = "角色拥有的权限ID列表")
    private List<Integer> permissionIds;
} 
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

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "是否为管理员")
    private String isAdmin;

    @Schema(description = "角色拥有的权限ID列表")
    private List<Integer> permissionIds;

    @Schema(description = "角色拥有的权限")
    private Set<PermissionResp> permissions;
} 
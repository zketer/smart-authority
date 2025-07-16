package smart.authority.web.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseResp;
import smart.authority.web.model.entity.Permission;
import smart.authority.web.model.entity.Role;
import smart.authority.web.model.resp.tenant.TenantResp;

import java.util.List;
import java.util.Set;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "权限响应")
public class PermissionResp extends BaseResp {

    @Schema(description = "父权限ID")
    private Integer parentId;

    @Schema(description = "父权限名称")
    private String parentName;

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限编码")
    private String code;

    @Schema(description = "权限类型：1-菜单，2-按钮，3-API")
    private Integer type;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "拥有该权限的角色")
    private Set<RoleResp> roles;

    @Schema(description = "子权限列表")
    private List<PermissionResp> children;

    @Schema(description = "租户信息")
    private TenantResp tenantResp;
} 
package smart.authority.web.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseEntity;

/**
 * @author lynn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role_permission")
@Schema(description = "角色权限关联实体")
public class RolePermission extends BaseEntity {

    @Schema(description = "角色ID")
    private Integer roleId;

    @Schema(description = "权限ID")
    private Integer permissionId;

    @Schema(description = "租户ID")
    private Integer tenantId;
}
package smart.authority.web.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseEntity;

import java.util.Set;

/**
 * @author lynn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role")
@Schema(description = "角色实体")
public class Role extends BaseEntity {

    @Schema(description = "租户ID")
    private Integer tenantId;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "状态：close-禁用，open-启用")
    private String status;

    @Schema(description = "是否为管理员：admin-是，not admin-否")
    private String isAdmin;

    @Schema(description = "描述")
    private String description;

    @TableField(exist = false)
    @Schema(description = "角色拥有的权限")
    private Set<Permission> permissions;
}
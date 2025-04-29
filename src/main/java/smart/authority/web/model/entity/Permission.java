package smart.authority.web.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseEntity;

import java.util.List;
import java.util.Set;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("permission")
@Schema(description = "权限实体")
public class Permission extends BaseEntity {

    @Schema(description = "父权限ID")
    private Integer parentId;

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

    @TableField(exist = false)
    @Schema(description = "拥有该权限的角色")
    private Set<Role> roles;

    @TableField(exist = false)
    @Schema(description = "子权限列表")
    private List<Permission> children;
}
package smart.authority.web.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.common.model.BaseEntity;

import java.util.Set;

/**
 * @author lynn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role")
@Schema(description = "角色实体")
public class Role extends BaseEntity {

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色描述")
    private String description;

    @TableField(exist = false)
    @Schema(description = "角色拥有的权限")
    private Set<Permission> permissions;
}
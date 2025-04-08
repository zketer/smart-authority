package smart.authority.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@TableName("role")
@Schema(description = "角色实体")
public class Role {
    @TableId(type = IdType.AUTO)
    @Schema(description = "角色ID")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色描述")
    private String description;

    @TableLogic
    @Schema(description = "是否删除")
    private Integer deleted;

    @TableField(exist = false)
    @Schema(description = "角色拥有的权限")
    private Set<Permission> permissions;
}
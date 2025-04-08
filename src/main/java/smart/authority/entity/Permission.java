package smart.authority.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@TableName("permission")
@Schema(description = "权限实体")
public class Permission {
    @TableId(type = IdType.AUTO)
    @Schema(description = "权限ID")
    private Long id;

    @Schema(description = "父权限ID")
    private Long parentId;

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限编码")
    private String code;

    @Schema(description = "权限类型")
    private Integer type;

    @Schema(description = "权限路径")
    private String path;

    @Schema(description = "权限图标")
    private String icon;

    @Schema(description = "权限组件")
    private String component;

    @Schema(description = "权限排序")
    private Integer sort;

    @Schema(description = "权限状态")
    private Integer status;

    @Schema(description = "权限描述")
    private String description;

    @TableLogic
    @Schema(description = "是否删除")
    private Integer deleted;

    @TableField(exist = false)
    @Schema(description = "拥有该权限的角色")
    private Set<Role> roles;
}
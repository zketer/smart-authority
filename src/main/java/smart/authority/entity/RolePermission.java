package smart.authority.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("role_permission")
@Schema(description = "角色权限关联实体")
public class RolePermission {
    @TableId(type = IdType.AUTO)
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "权限ID")
    private Long permissionId;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private java.time.LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private java.time.LocalDateTime updateTime;
}
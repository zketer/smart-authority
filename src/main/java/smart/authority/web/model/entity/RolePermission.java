package smart.authority.web.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import smart.authority.common.model.BaseEntity;

/**
 * @author lynn
 */
@Data
@TableName("role_permission")
@Schema(description = "角色权限关联实体")
public class RolePermission extends BaseEntity {
    @TableId(type = IdType.AUTO)
    @Schema(description = "ID")
    private Integer id;

    @Schema(description = "角色ID")
    private Integer roleId;

    @Schema(description = "权限ID")
    private Integer permissionId;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private java.time.LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private java.time.LocalDateTime updateTime;
}
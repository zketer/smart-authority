package smart.authority.web.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.common.model.BaseEntity;
import java.util.List;

/**
 * @author lynn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("permission")
@Schema(description = "权限实体")
public class Permission extends BaseEntity {
    @TableId(type = IdType.AUTO)
    @Schema(description = "权限ID")
    private Integer id;

    @Schema(description = "父权限ID")
    private Integer parentId;

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限编码")
    private String code;

    @Schema(description = "权限类型")
    private Integer type;

    @Schema(description = "权限路径")
    private String path;

    @Schema(description = "权限描述")
    private String description;

    @TableField(exist = false)
    @Schema(description = "子权限列表")
    private List<Permission> children;
}
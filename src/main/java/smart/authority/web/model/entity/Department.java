package smart.authority.web.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import smart.authority.common.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("department")
@Schema(description = "部门实体")
public class Department extends BaseEntity {
    @TableId(type = IdType.AUTO)
    @Schema(description = "部门ID")
    private Integer id;

    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "部门描述")
    private String description;

    @Schema(description = "父部门ID")
    private Integer parentId;
}
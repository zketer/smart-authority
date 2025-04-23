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

    @Schema(description = "租户ID")
    private Integer tenantId;

    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "父部门ID")
    private Integer parentId;

    @Schema(description = "描述")
    private String description;
}
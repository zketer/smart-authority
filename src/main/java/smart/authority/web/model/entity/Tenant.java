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
@TableName("tenant")
@Schema(description = "租户实体")
public class Tenant extends BaseEntity {
    @TableId(type = IdType.AUTO)
    @Schema(description = "租户ID")
    private Integer id;

    @Schema(description = "租户名称")
    private String name;

    @Schema(description = "租户描述")
    private String description;

    @Schema(description = "是否为默认, 0是, 1不是")
    private Integer isDefault;
}
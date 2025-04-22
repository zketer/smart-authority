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

    @Schema(description = "租户名称")
    private String name;

    @Schema(description = "租户编码")
    private String code;

    @Schema(description = "租户描述")
    private String description;

    @Schema(description = "状态：close禁用，open启用")
    private String status;

    @Schema(description = "是否默认租户：default默认，not default不是默认")
    private String isDefault;
}
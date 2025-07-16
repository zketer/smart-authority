package smart.authority.web.model.common;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lynn
 */
@Data
public class BaseEntity {

    @TableId(type = IdType.AUTO)
    @Schema(description = "唯一ID")
    private Integer id;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "创建人")
    private Integer createBy;

    @Schema(description = "更新人")
    private Integer updateBy;

    @Schema(description = "租户ID")
    private Integer tenantId;
}
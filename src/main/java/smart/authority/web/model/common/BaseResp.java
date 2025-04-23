package smart.authority.web.model.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础响应类
 * @author lynn
 */
@Data
public class BaseResp {
    @Schema(description = "唯一ID")
    private Integer id;

    @Schema(description = "租户ID")
    private Integer tenantId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "创建人")
    private Integer createBy;

    @Schema(description = "更新人")
    private Integer updateBy;
} 
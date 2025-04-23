package smart.authority.web.model.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 基础请求类
 * @author lynn
 */
@Data
public class BaseReq {
    @Schema(description = "租户ID")
    private Integer tenantId;

    @Schema(description = "创建人")
    private Integer createBy;

    @Schema(description = "更新人")
    private Integer updateBy;
} 
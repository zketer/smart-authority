package smart.authority.web.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseResp;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "JVM内存响应")
public class JvmMemoryResp extends BaseResp {

    @Schema(description = "初始内存大小(MB)")
    private Long init;

    @Schema(description = "已使用内存大小(MB)")
    private Long used;

    @Schema(description = "最大内存大小(MB)")
    private Long max;

    @Schema(description = "已提交内存大小(MB)")
    private Long committed;

    @Schema(description = "内存使用率")
    private Double usage;
} 
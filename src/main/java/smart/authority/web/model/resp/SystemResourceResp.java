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
@Schema(description = "系统资源响应")
public class SystemResourceResp extends BaseResp {

    @Schema(description = "CPU使用率")
    private Double cpuUsage;

    @Schema(description = "内存使用率")
    private Double memoryUsage;

    @Schema(description = "磁盘使用率")
    private Double diskUsage;

    @Schema(description = "JVM内存使用情况")
    private JvmMemoryResp jvmMemory;
} 
package smart.authority.web.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

/**
 * @author lynn
 */
@Data
@Schema(description = "统计数据响应")
public class StatsResp {
    @Schema(description = "统计数据")
    private Map<String, Long> data;
} 
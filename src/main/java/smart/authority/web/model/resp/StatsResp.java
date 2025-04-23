package smart.authority.web.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseResp;

import java.util.List;
import java.util.Map;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "统计数据响应")
public class StatsResp extends BaseResp {

    @Schema(description = "统计数据")
    private Map<String, Long> data;
} 
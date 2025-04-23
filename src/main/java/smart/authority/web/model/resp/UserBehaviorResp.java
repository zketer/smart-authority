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
@Schema(description = "用户行为统计响应")
public class UserBehaviorResp extends BaseResp {

    @Schema(description = "日期列表")
    private List<String> dates;

    @Schema(description = "功能使用次数统计")
    private Map<String, List<Long>> featureUsage;

    @Schema(description = "页面访问次数统计")
    private Map<String, List<Long>> pageViews;

    @Schema(description = "平均停留时长(分钟)统计")
    private Map<String, List<Double>> avgStayTime;
} 
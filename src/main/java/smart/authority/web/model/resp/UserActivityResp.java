package smart.authority.web.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseResp;

import java.util.List;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户活跃度统计响应")
public class UserActivityResp extends BaseResp {

    @Schema(description = "日期列表")
    private List<String> dates;

    @Schema(description = "活跃用户数列表")
    private List<Long> activeUsers;

    @Schema(description = "平均在线时长(分钟)列表")
    private List<Double> avgOnlineTime;
} 
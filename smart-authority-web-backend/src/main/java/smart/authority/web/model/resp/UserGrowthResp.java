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
@Schema(description = "用户增长统计响应")
public class UserGrowthResp extends BaseResp {

    @Schema(description = "日期列表")
    private List<String> dates;

    @Schema(description = "新增用户数列表")
    private List<Long> newUsers;

    @Schema(description = "总用户数列表")
    private List<Long> totalUsers;
} 
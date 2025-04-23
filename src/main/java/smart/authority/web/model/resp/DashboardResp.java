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
@Schema(description = "仪表盘响应")
public class DashboardResp extends BaseResp {

    @Schema(description = "用户总数")
    private Long userCount;

    @Schema(description = "角色总数")
    private Long roleCount;

    @Schema(description = "权限总数")
    private Long permissionCount;

    @Schema(description = "部门总数")
    private Long departmentCount;

    @Schema(description = "租户总数")
    private Long tenantCount;

    @Schema(description = "最近登录用户列表")
    private List<UserResp> recentLoginUsers;

    @Schema(description = "系统资源使用情况")
    private SystemResourceResp systemResource;
} 
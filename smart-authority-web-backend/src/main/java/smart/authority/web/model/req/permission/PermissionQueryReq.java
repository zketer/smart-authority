package smart.authority.web.model.req.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseReq;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "权限查询请求")
public class PermissionQueryReq extends BaseReq {

    @Schema(description = "当前页")
    private Integer current = 1;

    @Schema(description = "每页大小")
    private Integer size = 10;

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限编码")
    private String code;

    @Schema(description = "权限类型：1-菜单，2-按钮，3-API")
    private Integer type;

    @Schema(description = "父权限ID")
    private Integer parentId;
} 
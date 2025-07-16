package smart.authority.web.model.req.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseReq;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色查询请求")
public class RoleQueryReq extends BaseReq {

    @Schema(description = "当前页")
    private Integer current;

    @Schema(description = "每页大小")
    private Integer size;

    @Schema(description = "角色名称")
    private String name;
} 
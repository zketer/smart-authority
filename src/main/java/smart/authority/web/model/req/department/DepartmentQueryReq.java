package smart.authority.web.model.req.department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lynn
 */
@Data
@Schema(description = "部门查询请求")
public class DepartmentQueryReq {

    @Schema(description = "当前页")
    private Integer current = 1;

    @Schema(description = "每页大小")
    private Integer size = 10;

    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "父部门ID")
    private Integer parentId;
} 
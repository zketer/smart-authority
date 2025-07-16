package smart.authority.web.model.resp.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "角色响应")
public class RoleResp {
    @Schema(description = "角色ID")
    private Integer id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色描述")
    private String description;
} 
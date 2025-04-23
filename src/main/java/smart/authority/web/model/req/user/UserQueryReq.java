package smart.authority.web.model.req.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseReq;

/**
 * @author Lynn.z
 * @since 2025/4/23 10:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户查询请求")
public class UserQueryReq extends BaseReq {

    @Schema(description = "当前页")
    private Integer current = 1;

    @Schema(description = "每页大小")
    private Integer size = 10;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "状态：open-启用，close-禁用")
    private String status;
}

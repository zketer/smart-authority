package smart.authority.web.model.req.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseReq;

/**
 * 用户查询请求
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryReq extends BaseReq {

    /**
     * 当前页
     */
    private Integer current = 1;

    /**
     * 每页大小
     */
    private Integer size = 10;

    @Schema(description = "部门ID")
    private Integer departmentId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "状态 open启用 close禁用")
    private String status;
}

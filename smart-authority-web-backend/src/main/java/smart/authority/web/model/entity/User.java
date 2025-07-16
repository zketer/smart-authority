package smart.authority.web.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import smart.authority.web.model.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user")
@Schema(description = "用户实体")
public class User extends BaseEntity {

    @Schema(description = "租户ID")
    private Integer tenantId;

    @Schema(description = "部门ID")
    private Integer departmentId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "状态：close-禁用，open-启用")
    private String status;

    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;
}
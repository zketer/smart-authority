package smart.authority.web.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import smart.authority.web.model.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_role")
@Schema(description = "用户角色关联")
public class UserRole extends BaseEntity {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "角色ID")
    private Integer roleId;

    @Schema(description = "租户ID")
    private Integer tenantId;
}
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
@TableName("user_behavior_log")
@Schema(description = "用户行为日志实体")
public class UserBehaviorLog extends BaseEntity {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "功能名称")
    private String feature;

    @Schema(description = "页面名称")
    private String page;

    @Schema(description = "操作类型")
    private String action;

    @Schema(description = "停留时长(秒)")
    private Integer duration;
} 
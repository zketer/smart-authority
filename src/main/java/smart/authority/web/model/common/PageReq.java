package smart.authority.web.model.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页请求基类
 * @author lynn
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageReq extends BaseReq {

    @Schema(description = "当前页", defaultValue = "1")
    private Integer current = 1;

    @Schema(description = "每页大小", defaultValue = "10")
    private Integer size = 10;
} 
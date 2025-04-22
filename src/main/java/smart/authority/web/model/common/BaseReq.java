package smart.authority.web.model.common;

import lombok.Data;

/**
 * 基础请求类
 * @author lynn
 */
@Data
public class BaseReq {
    private Integer tenantId;
    private Integer createBy;
    private Integer updateBy;
} 
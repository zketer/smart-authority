package smart.authority.web.model.req.tenant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseReq;

/**
 * 查询租户请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantQueryReq extends BaseReq {
    
    /**
     * 当前页
     */
    private Integer current = 1;
    
    /**
     * 每页大小
     */
    private Integer size = 10;
    
    /**
     * 租户名称
     */
    private String name;
    
    /**
     * 租户编码
     */
    private String code;
    
    /**
     * 状态：0-禁用，1-启用
     */
    private String status;
} 
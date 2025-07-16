package smart.authority.web.model.resp.tenant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseResp;

/**
 * 租户响应
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantResp extends BaseResp {
    
    /**
     * 租户名称
     */
    private String name;
    
    /**
     * 租户编码
     */
    private String code;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 状态：close-禁用，open-启用
     */
    private String status;
    
    /**
     * 是否默认租户：default-默认，not default-不是默认
     */
    private String isDefault;
} 
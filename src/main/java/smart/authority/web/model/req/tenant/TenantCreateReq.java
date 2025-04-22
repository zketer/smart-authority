package smart.authority.web.model.req.tenant;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import smart.authority.web.model.common.BaseReq;

/**
 * 创建租户请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantCreateReq extends BaseReq {
    
    /**
     * 租户名称
     */
    @NotBlank(message = "租户名称不能为空")
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
package smart.authority.web.model.common;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础响应类
 * @author lynn
 */
@Data
public class BaseResp {
    private Integer id;
    private Integer tenantId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer createBy;
    private Integer updateBy;
} 
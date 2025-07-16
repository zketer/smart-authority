package smart.authority.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import smart.authority.web.model.entity.UserBehaviorLog;

/**
 * @author lynn
 */
public interface UserBehaviorLogService extends IService<UserBehaviorLog> {

    /**
     * 记录用户行为日志
     *
     * @param userId 用户ID
     * @param tenantId 租户ID
     * @param feature 功能名称
     * @param page 页面名称
     * @param action 操作类型
     * @param duration 停留时长(秒)
     */
    void logUserBehavior(Integer userId, Integer tenantId, String feature, String page, String action, Integer duration);
} 
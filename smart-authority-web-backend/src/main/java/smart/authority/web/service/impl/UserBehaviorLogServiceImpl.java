package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import smart.authority.web.mapper.UserBehaviorLogMapper;
import smart.authority.web.model.entity.UserBehaviorLog;
import smart.authority.web.service.UserBehaviorLogService;

/**
 * @author lynn
 */
@Service
public class UserBehaviorLogServiceImpl extends ServiceImpl<UserBehaviorLogMapper, UserBehaviorLog> implements UserBehaviorLogService {

    @Override
    public void logUserBehavior(Integer userId, Integer tenantId, String feature, String page, String action, Integer duration) {
        UserBehaviorLog log = new UserBehaviorLog();
        log.setUserId(userId);
        log.setTenantId(tenantId);
        log.setFeature(feature);
        log.setPage(page);
        log.setAction(action);
        log.setDuration(duration);
        save(log);
    }
} 
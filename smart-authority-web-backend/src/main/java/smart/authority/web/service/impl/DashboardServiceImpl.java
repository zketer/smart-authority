package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smart.authority.web.mapper.DepartmentMapper;
import smart.authority.web.mapper.PermissionMapper;
import smart.authority.web.mapper.RoleMapper;
import smart.authority.web.mapper.TenantMapper;
import smart.authority.web.mapper.UserMapper;
import smart.authority.web.mapper.UserBehaviorLogMapper;
import smart.authority.web.model.entity.*;
import smart.authority.web.model.resp.*;
import smart.authority.web.service.DashboardService;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;

/**
 * @author lynn
 */
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private UserBehaviorLogMapper userBehaviorLogMapper;

    @Override
    public StatsResp getUserStats(Integer tenantId) {
        StatsResp resp = new StatsResp();
        Map<String, Long> data = new HashMap<>();
        
        // 获取总用户数
        data.put("total", userMapper.selectCount(null));
        
        // 获取今日新增用户数
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = todayStart.plusDays(1);
        LambdaQueryWrapper<User> todayWrapper = new LambdaQueryWrapper<>();
        todayWrapper.between(User::getCreateTime, todayStart, todayEnd);
        todayWrapper.eq(Objects.nonNull(tenantId), User::getTenantId, tenantId);
        data.put("todayNew", userMapper.selectCount(todayWrapper));
        
        // 获取本周新增用户数
        LocalDateTime weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime weekEnd = weekStart.plusWeeks(1);
        LambdaQueryWrapper<User> weekWrapper = new LambdaQueryWrapper<>();
        weekWrapper.between(User::getCreateTime, weekStart, weekEnd);
        weekWrapper.eq(Objects.nonNull(tenantId), User::getTenantId, tenantId);

        data.put("weekNew", userMapper.selectCount(weekWrapper));
        
        // 获取本月新增用户数
        LocalDateTime monthStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime monthEnd = monthStart.plusMonths(1);
        LambdaQueryWrapper<User> monthWrapper = new LambdaQueryWrapper<>();
        monthWrapper.between(User::getCreateTime, monthStart, monthEnd);
        monthWrapper.eq(Objects.nonNull(tenantId), User::getTenantId, tenantId);

        data.put("monthNew", userMapper.selectCount(monthWrapper));
        
        resp.setData(data);
        return resp;
    }

    @Override
    public StatsResp getDepartmentStats(Integer tenantId) {
        StatsResp resp = new StatsResp();
        Map<String, Long> data = new HashMap<>();
        LambdaQueryWrapper<Department> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(tenantId), Department::getTenantId, tenantId);
        data.put("total", departmentMapper.selectCount(queryWrapper));
        // TODO: 实现部门统计的其他逻辑
        resp.setData(data);
        return resp;
    }

    @Override
    public StatsResp getRoleStats(Integer tenantId) {
        StatsResp resp = new StatsResp();
        Map<String, Long> data = new HashMap<>();
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(tenantId), Role::getTenantId, tenantId);
        data.put("total", roleMapper.selectCount(queryWrapper));
        // TODO: 实现角色统计的其他逻辑
        resp.setData(data);
        return resp;
    }

    @Override
    public StatsResp getPermissionStats(Integer tenantId) {
        StatsResp resp = new StatsResp();
        Map<String, Long> data = new HashMap<>();
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(tenantId), Permission::getTenantId, tenantId);
        data.put("total", permissionMapper.selectCount(queryWrapper));
        // TODO: 实现权限统计的其他逻辑
        resp.setData(data);
        return resp;
    }

    @Override
    public StatsResp getTenantStats(Integer tenantId) {
        StatsResp resp = new StatsResp();
        Map<String, Long> data = new HashMap<>();
        LambdaQueryWrapper<Tenant> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(tenantId), Tenant::getId, tenantId);
        data.put("total", tenantMapper.selectCount(queryWrapper));
        // TODO: 实现租户统计的其他逻辑
        resp.setData(data);
        return resp;
    }

    @Override
    public UserGrowthResp getUserGrowth(Integer tenantId) {
        UserGrowthResp resp = new UserGrowthResp();
        
        // 获取最近30天的日期列表
        List<String> dates = new ArrayList<>();
        List<Long> newUsers = new ArrayList<>();
        List<Long> totalUsers = new ArrayList<>();
        
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(29);
        
        // 初始化日期列表和计数器
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate.toString());
            newUsers.add(0L);
            totalUsers.add(0L);
            currentDate = currentDate.plusDays(1);
        }
        
        // 查询每日新增用户数
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(User::getCreateTime, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        wrapper.eq(Objects.nonNull(tenantId), User::getTenantId, tenantId);
        List<User> users = userMapper.selectList(wrapper);
        
        // 统计每日新增用户数
        for (User user : users) {
            LocalDate createDate = user.getCreateTime().toLocalDate();
            int index = (int) ChronoUnit.DAYS.between(startDate, createDate);
            if (index >= 0 && index < dates.size()) {
                newUsers.set(index, newUsers.get(index) + 1);
            }
        }
        
        // 计算每日总用户数
        long total = 0;
        for (int i = 0; i < dates.size(); i++) {
            total += newUsers.get(i);
            totalUsers.set(i, total);
        }
        
        resp.setDates(dates);
        resp.setNewUsers(newUsers);
        resp.setTotalUsers(totalUsers);
        
        return resp;
    }

    @Override
    public UserActivityResp getUserActivity(Integer tenantId) {
        UserActivityResp resp = new UserActivityResp();
        
        // 获取最近7天的日期列表
        List<String> dates = new ArrayList<>();
        List<Long> activeUsers = new ArrayList<>();
        
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);
        
        // 初始化日期列表和计数器
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate.toString());
            activeUsers.add(0L);
            currentDate = currentDate.plusDays(1);
        }
        
        // 查询每日活跃用户数
        LambdaQueryWrapper<UserBehaviorLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(UserBehaviorLog::getCreateTime, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        wrapper.eq(Objects.nonNull(tenantId), UserBehaviorLog::getTenantId, tenantId);
        List<UserBehaviorLog> logs = userBehaviorLogMapper.selectList(wrapper);
        
        // 统计每日活跃用户数
        for (UserBehaviorLog log : logs) {
            LocalDate logDate = log.getCreateTime().toLocalDate();
            int index = (int) ChronoUnit.DAYS.between(startDate, logDate);
            if (index >= 0 && index < dates.size()) {
                activeUsers.set(index, activeUsers.get(index) + 1);
            }
        }
        
        resp.setDates(dates);
        resp.setActiveUsers(activeUsers);
        
        return resp;
    }

    @Override
    public UserBehaviorResp getUserBehavior(Integer tenantId) {
        UserBehaviorResp resp = new UserBehaviorResp();
        
        // 获取最近30天的日期列表
        List<String> dates = new ArrayList<>();
        Map<String, List<Long>> featureUsage = new HashMap<>();
        Map<String, List<Long>> pageViews = new HashMap<>();
        Map<String, List<Double>> avgStayTime = new HashMap<>();
        
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(29);
        
        // 初始化日期列表和计数器
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate.toString());
            currentDate = currentDate.plusDays(1);
        }
        
        // 初始化功能使用次数统计
        List<String> features = Arrays.asList("user_management", "role_management", "permission_management", "department_management");
        for (String feature : features) {
            List<Long> counts = new ArrayList<>(Collections.nCopies(dates.size(), 0L));
            featureUsage.put(feature, counts);
        }
        
        // 初始化页面访问次数统计
        List<String> pages = Arrays.asList("dashboard", "user_list", "role_list", "permission_list", "department_list");
        for (String page : pages) {
            List<Long> counts = new ArrayList<>(Collections.nCopies(dates.size(), 0L));
            pageViews.put(page, counts);
            List<Double> times = new ArrayList<>(Collections.nCopies(dates.size(), 0.0));
            avgStayTime.put(page, times);
        }
        
        // 查询用户行为日志
        LambdaQueryWrapper<UserBehaviorLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(UserBehaviorLog::getCreateTime, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        wrapper.eq(Objects.nonNull(tenantId), UserBehaviorLog::getTenantId, tenantId);
        List<UserBehaviorLog> logs = userBehaviorLogMapper.selectList(wrapper);
        
        // 统计每日数据
        for (UserBehaviorLog log : logs) {
            LocalDate logDate = log.getCreateTime().toLocalDate();
            int index = (int) ChronoUnit.DAYS.between(startDate, logDate);
            if (index >= 0 && index < dates.size()) {
                // 统计功能使用次数
                if (log.getFeature() != null && features.contains(log.getFeature())) {
                    List<Long> counts = featureUsage.get(log.getFeature());
                    counts.set(index, counts.get(index) + 1);
                }
                
                // 统计页面访问次数和停留时长
                if (log.getPage() != null && pages.contains(log.getPage())) {
                    List<Long> views = pageViews.get(log.getPage());
                    views.set(index, views.get(index) + 1);
                    
                    List<Double> times = avgStayTime.get(log.getPage());
                    if (log.getDuration() != null) {
                        times.set(index, times.get(index) + log.getDuration());
                    }
                }
            }
        }
        
        // 计算平均停留时长
        for (String page : pages) {
            List<Long> views = pageViews.get(page);
            List<Double> times = avgStayTime.get(page);
            for (int i = 0; i < dates.size(); i++) {
                if (views.get(i) > 0) {
                    times.set(i, times.get(i) / views.get(i) / 60.0); // 转换为分钟
                }
            }
        }
        
        resp.setDates(dates);
        resp.setFeatureUsage(featureUsage);
        resp.setPageViews(pageViews);
        resp.setAvgStayTime(avgStayTime);
        
        return resp;
    }
} 
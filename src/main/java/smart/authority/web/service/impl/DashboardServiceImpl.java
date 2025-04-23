package smart.authority.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smart.authority.web.mapper.DepartmentMapper;
import smart.authority.web.mapper.PermissionMapper;
import smart.authority.web.mapper.RoleMapper;
import smart.authority.web.mapper.TenantMapper;
import smart.authority.web.mapper.UserMapper;
import smart.authority.web.mapper.UserBehaviorLogMapper;
import smart.authority.web.model.entity.User;
import smart.authority.web.model.entity.UserBehaviorLog;
import smart.authority.web.model.entity.Department;
import smart.authority.web.model.resp.*;
import smart.authority.web.service.DashboardService;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    public DashboardResp getDashboardData() {
        DashboardResp resp = new DashboardResp();

        // 获取各实体数量
        resp.setUserCount(userMapper.selectCount(null));
        resp.setRoleCount(roleMapper.selectCount(null));
        resp.setPermissionCount(permissionMapper.selectCount(null));
        resp.setDepartmentCount(departmentMapper.selectCount(null));
        resp.setTenantCount(tenantMapper.selectCount(null));

        // 获取最近登录的用户
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        List<User> recentUsers = userMapper.selectList(userWrapper);
        resp.setRecentLoginUsers(recentUsers.stream()
                .map(user -> {
                    UserResp userResp = new UserResp();
                    userResp.setId(user.getId());
                    userResp.setUsername(user.getUsername());
                    userResp.setName(user.getName());
                    return userResp;
                })
                .collect(Collectors.toList()));

        // 获取系统资源使用情况
        SystemResourceResp systemResource = new SystemResourceResp();
        
        // 获取JVM内存使用情况
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        JvmMemoryResp heapMemory = new JvmMemoryResp();
        heapMemory.setInit(heapMemoryUsage.getInit() / 1024 / 1024);
        heapMemory.setUsed(heapMemoryUsage.getUsed() / 1024 / 1024);
        heapMemory.setMax(heapMemoryUsage.getMax() / 1024 / 1024);
        heapMemory.setCommitted(heapMemoryUsage.getCommitted() / 1024 / 1024);
        heapMemory.setUsage((double) heapMemoryUsage.getUsed() / heapMemoryUsage.getCommitted() * 100);

        JvmMemoryResp nonHeapMemory = new JvmMemoryResp();
        nonHeapMemory.setInit(nonHeapMemoryUsage.getInit() / 1024 / 1024);
        nonHeapMemory.setUsed(nonHeapMemoryUsage.getUsed() / 1024 / 1024);
        nonHeapMemory.setMax(nonHeapMemoryUsage.getMax() / 1024 / 1024);
        nonHeapMemory.setCommitted(nonHeapMemoryUsage.getCommitted() / 1024 / 1024);
        nonHeapMemory.setUsage((double) nonHeapMemoryUsage.getUsed() / nonHeapMemoryUsage.getCommitted() * 100);

        systemResource.setJvmMemory(heapMemory);
        resp.setSystemResource(systemResource);

        return resp;
    }

    @Override
    public StatsResp getUserStats() {
        StatsResp resp = new StatsResp();
        Map<String, Long> data = new HashMap<>();
        
        // 获取总用户数
        data.put("total", userMapper.selectCount(null));
        
        // 获取今日新增用户数
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        LocalDateTime todayEnd = todayStart.plusDays(1);
        LambdaQueryWrapper<User> todayWrapper = new LambdaQueryWrapper<>();
        todayWrapper.between(User::getCreateTime, todayStart, todayEnd);
        data.put("todayNew", userMapper.selectCount(todayWrapper));
        
        // 获取本周新增用户数
        LocalDateTime weekStart = LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime weekEnd = weekStart.plusWeeks(1);
        LambdaQueryWrapper<User> weekWrapper = new LambdaQueryWrapper<>();
        weekWrapper.between(User::getCreateTime, weekStart, weekEnd);
        data.put("weekNew", userMapper.selectCount(weekWrapper));
        
        // 获取本月新增用户数
        LocalDateTime monthStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime monthEnd = monthStart.plusMonths(1);
        LambdaQueryWrapper<User> monthWrapper = new LambdaQueryWrapper<>();
        monthWrapper.between(User::getCreateTime, monthStart, monthEnd);
        data.put("monthNew", userMapper.selectCount(monthWrapper));
        
        resp.setData(data);
        return resp;
    }

    @Override
    public StatsResp getDepartmentStats() {
        StatsResp resp = new StatsResp();
        Map<String, Long> data = new HashMap<>();
        data.put("total", departmentMapper.selectCount(null));
        // TODO: 实现部门统计的其他逻辑
        resp.setData(data);
        return resp;
    }

    @Override
    public StatsResp getRoleStats() {
        StatsResp resp = new StatsResp();
        Map<String, Long> data = new HashMap<>();
        data.put("total", roleMapper.selectCount(null));
        // TODO: 实现角色统计的其他逻辑
        resp.setData(data);
        return resp;
    }

    @Override
    public StatsResp getPermissionStats() {
        StatsResp resp = new StatsResp();
        Map<String, Long> data = new HashMap<>();
        data.put("total", permissionMapper.selectCount(null));
        // TODO: 实现权限统计的其他逻辑
        resp.setData(data);
        return resp;
    }

    @Override
    public StatsResp getTenantStats() {
        StatsResp resp = new StatsResp();
        Map<String, Long> data = new HashMap<>();
        data.put("total", tenantMapper.selectCount(null));
        // TODO: 实现租户统计的其他逻辑
        resp.setData(data);
        return resp;
    }

    @Override
    public UserGrowthResp getUserGrowth() {
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
    public UserActivityResp getUserActivity() {
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
    public UserBehaviorResp getUserBehavior() {
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
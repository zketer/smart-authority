import React, { useEffect, useState } from 'react';
import { Card, Statistic, Spin } from 'antd';
import {
  UserOutlined,
  ApartmentOutlined,
  SafetyOutlined,
  TeamOutlined,
  LockOutlined
} from '@ant-design/icons';
import {
  getUserStats,
  getDepartmentStats,
  getRoleStats,
  getPermissionStats,
  getTenantStats
} from '@/services/SmartAuthority/DashboardController';
import type { API } from '@/services/SmartAuthority/typings';
import { useModel } from '@umijs/max';

const Dashboard: React.FC = () => {
  const [loading, setLoading] = useState(true);
  const [userStats, setUserStats] = useState<API.ApiResponseStatsResp>();
  const [roleStats, setRoleStats] = useState<API.ApiResponseStatsResp>();
  const [permissionStats, setPermissionStats] = useState<API.ApiResponseStatsResp>();
  const [departmentStats, setDepartmentStats] = useState<API.ApiResponseStatsResp>();
  const [tenantStats, setTenantStats] = useState<API.ApiResponseStatsResp>();
  const { initialState } = useModel('@@initialState');
  const [isAdmin, setIsAdmin] = useState(false);
  const [userTenantId, setUserTenantId] = useState<number | undefined>(undefined);

  useEffect(() => {
    if (initialState?.currentUser) {
      const isAdminUser = initialState.currentUser.isAdmin === 'admin';
      const tenantId = initialState.currentUser.tenantId;
      
      setIsAdmin(isAdminUser);
      setUserTenantId(tenantId ? Number(tenantId) : undefined);
    }
  }, [initialState?.currentUser]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);

        // 准备请求参数，根据用户角色决定是否传递tenantId
        const params = !isAdmin && userTenantId ? { tenantId: userTenantId } : {};

        // 获取统计数据
        const [userStatsResp, deptStatsResp, roleStatsResp, permissionStatsResp, tenantStatsResp] = await Promise.all([
          getUserStats(params),
          getDepartmentStats(params),
          getRoleStats(params),
          getPermissionStats(params),
          getTenantStats(params)
        ]);

        if (userStatsResp.success) {
          console.log('User Stats:', userStatsResp);
          setUserStats(userStatsResp);
        }

        if (deptStatsResp.success) {
          console.log('Department Stats:', deptStatsResp);
          setDepartmentStats(deptStatsResp);
        }

        if (roleStatsResp.success) {
          console.log('Role Stats:', roleStatsResp);
          setRoleStats(roleStatsResp);
        }

        if (permissionStatsResp.success) {
          console.log('Permission Stats:', permissionStatsResp);
          setPermissionStats(permissionStatsResp);
        }

        if (tenantStatsResp.success) {
          console.log('Tenant Stats:', tenantStatsResp);
          setTenantStats(tenantStatsResp);
        }
      } catch (error) {
        console.error('Failed to fetch dashboard data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [isAdmin, userTenantId]);

  return (
      <Spin spinning={loading}>
        <div style={{ padding: '24px' }}>
          {/* 统计卡片 */}
          <div style={{ display: 'flex', gap: '16px', marginBottom: '16px' }}>
            <Card style={{ flex: 1 }}>
              <Statistic
                  title="总用户数"
                  value={userStats?.data?.data?.total || 0}
                  prefix={<UserOutlined style={{ color: '#1890ff' }} />}
              />
            </Card>
            <Card style={{ flex: 1 }}>
              <Statistic
                  title="部门总数"
                  value={departmentStats?.data?.data?.total || 0}
                  prefix={<ApartmentOutlined style={{ color: '#faad14' }} />}
              />
            </Card>
            <Card style={{ flex: 1 }}>
              <Statistic
                  title="角色总数"
                  value={roleStats?.data?.data?.total || 0}
                  prefix={<SafetyOutlined style={{ color: '#722ed1' }} />}
              />
            </Card>
            <Card style={{ flex: 1 }}>
              <Statistic
                  title="权限总数"
                  value={permissionStats?.data?.data?.total || 0}
                  prefix={<LockOutlined style={{ color: '#52c41a' }} />}
              />
            </Card>
            <Card style={{ flex: 1 }}>
              <Statistic
                  title="租户总数"
                  value={tenantStats?.data?.data?.total || 0}
                  prefix={<TeamOutlined style={{ color: '#eb2f96' }} />}
              />
            </Card>
          </div>

          {/* 系统概述 */}
          <Card title="系统概述">
            <p>Smart Authority 是一个现代化的权限管理系统，提供用户、角色、权限和部门的管理功能。</p>
            <p>系统采用前后端分离架构，前端使用 React + Ant Design Pro 开发，后端使用 Spring Boot 构建。</p>
            <p style={{ marginBottom: 0 }}>最后更新时间：{new Date().toLocaleDateString()}</p>
          </Card>
        </div>
      </Spin>
  );
};

export default Dashboard;

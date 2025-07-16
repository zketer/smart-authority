// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 获取部门统计 GET /smart-authority/v1.0/api/dashboard/department/stats */
export async function getDepartmentStats(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getDepartmentStatsParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseStatsResp>('/smart-authority/v1.0/api/dashboard/department/stats', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 获取权限统计 GET /smart-authority/v1.0/api/dashboard/permission/stats */
export async function getPermissionStats(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getPermissionStatsParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseStatsResp>('/smart-authority/v1.0/api/dashboard/permission/stats', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 获取角色统计 GET /smart-authority/v1.0/api/dashboard/role/stats */
export async function getRoleStats(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getRoleStatsParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseStatsResp>('/smart-authority/v1.0/api/dashboard/role/stats', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 获取租户统计 GET /smart-authority/v1.0/api/dashboard/tenant/stats */
export async function getTenantStats(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getTenantStatsParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseStatsResp>('/smart-authority/v1.0/api/dashboard/tenant/stats', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 获取用户活跃度数据 GET /smart-authority/v1.0/api/dashboard/user/activity */
export async function getUserActivity(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getUserActivityParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseUserActivityResp>(
    '/smart-authority/v1.0/api/dashboard/user/activity',
    {
      method: 'GET',
      params: {
        ...params,
      },
      ...(options || {}),
    },
  );
}

/** 获取用户行为数据 GET /smart-authority/v1.0/api/dashboard/user/behavior */
export async function getUserBehavior(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getUserBehaviorParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseUserBehaviorResp>(
    '/smart-authority/v1.0/api/dashboard/user/behavior',
    {
      method: 'GET',
      params: {
        ...params,
      },
      ...(options || {}),
    },
  );
}

/** 获取用户增长数据 GET /smart-authority/v1.0/api/dashboard/user/growth */
export async function getUserGrowth(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getUserGrowthParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseUserGrowthResp>('/smart-authority/v1.0/api/dashboard/user/growth', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 获取用户统计 GET /smart-authority/v1.0/api/dashboard/user/stats */
export async function getUserStats(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getUserStatsParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseStatsResp>('/smart-authority/v1.0/api/dashboard/user/stats', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

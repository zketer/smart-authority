// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 分页查询租户 GET /smart-authority/v1.0/tenants */
export async function pageTenants(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.pageTenantsParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseIPageTenantResp>('/smart-authority/v1.0/tenants', {
    method: 'GET',
    params: {
      ...params,
      req: undefined,
      ...params['req'],
    },
    ...(options || {}),
  });
}

/** 创建租户 POST /smart-authority/v1.0/tenants */
export async function createTenant(body: API.TenantCreateReq, options?: { [key: string]: any }) {
  return request<API.ApiResponseVoid>('/smart-authority/v1.0/tenants', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取租户详情 GET /smart-authority/v1.0/tenants/${param0} */
export async function getTenant(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getTenantParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseTenantResp>(`/smart-authority/v1.0/tenants/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新租户 PUT /smart-authority/v1.0/tenants/${param0} */
export async function updateTenant(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateTenantParams,
  body: API.TenantUpdateReq,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseVoid>(`/smart-authority/v1.0/tenants/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除租户 DELETE /smart-authority/v1.0/tenants/${param0} */
export async function deleteTenant(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteTenantParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseVoid>(`/smart-authority/v1.0/tenants/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

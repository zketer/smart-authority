// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 分页查询权限 GET /smart-authority/v1.0/api/permissions */
export async function pagePermissions(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.pagePermissionsParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseIPagePermissionResp>('/smart-authority/v1.0/api/permissions', {
    method: 'GET',
    params: {
      ...params,
      req: undefined,
      ...params['req'],
    },
    ...(options || {}),
  });
}

/** 创建权限 POST /smart-authority/v1.0/api/permissions */
export async function createPermission(
  body: API.PermissionCreateReq,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseVoid>('/smart-authority/v1.0/api/permissions', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取权限详情 GET /smart-authority/v1.0/api/permissions/${param0} */
export async function getPermission(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getPermissionParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponsePermissionResp>(`/smart-authority/v1.0/api/permissions/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新权限 PUT /smart-authority/v1.0/api/permissions/${param0} */
export async function updatePermission(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updatePermissionParams,
  body: API.PermissionUpdateReq,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponsePermissionResp>(`/smart-authority/v1.0/api/permissions/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除权限 DELETE /smart-authority/v1.0/api/permissions/${param0} */
export async function deletePermission(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deletePermissionParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseVoid>(`/smart-authority/v1.0/api/permissions/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 获取角色的所有权限 GET /smart-authority/v1.0/api/permissions/role/${param0} */
export async function getRolePermissions(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getRolePermissionsParams,
  options?: { [key: string]: any },
) {
  const { roleId: param0, ...queryParams } = params;
  return request<API.ApiResponseListPermissionResp>(
    `/smart-authority/v1.0/api/permissions/role/${param0}`,
    {
      method: 'GET',
      params: { ...queryParams },
      ...(options || {}),
    },
  );
}

/** 获取权限树 GET /smart-authority/v1.0/api/permissions/tree */
export async function getPermissionTree(options?: { [key: string]: any }) {
  return request<API.ApiResponseListPermissionResp>('/smart-authority/v1.0/api/permissions/tree', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取用户的所有权限 GET /smart-authority/v1.0/api/permissions/user/${param0} */
export async function getUserPermissions(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getUserPermissionsParams,
  options?: { [key: string]: any },
) {
  const { userId: param0, ...queryParams } = params;
  return request<API.ApiResponseListPermissionResp>(
    `/smart-authority/v1.0/api/permissions/user/${param0}`,
    {
      method: 'GET',
      params: { ...queryParams },
      ...(options || {}),
    },
  );
}

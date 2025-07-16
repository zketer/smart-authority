// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 查询角色（支持分页） GET /smart-authority/v1.0/roles */
export async function getRoles(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getRolesParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseObject>('/smart-authority/v1.0/roles', {
    method: 'GET',
    params: {
      ...params,
      req: undefined,
      ...params['req'],
    },
    ...(options || {}),
  });
}

/** 创建角色 POST /smart-authority/v1.0/roles */
export async function createRole(body: API.RoleCreateReq, options?: { [key: string]: any }) {
  return request<API.ApiResponseVoid>('/smart-authority/v1.0/roles', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取角色详情 GET /smart-authority/v1.0/roles/${param0} */
export async function getRole(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getRoleParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseRoleResp>(`/smart-authority/v1.0/roles/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新角色 PUT /smart-authority/v1.0/roles/${param0} */
export async function updateRole(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateRoleParams,
  body: API.RoleUpdateReq,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseVoid>(`/smart-authority/v1.0/roles/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除角色 DELETE /smart-authority/v1.0/roles/${param0} */
export async function deleteRole(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteRoleParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseVoid>(`/smart-authority/v1.0/roles/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 为角色分配权限 POST /smart-authority/v1.0/roles/${param0}/permissions */
export async function assignPermissions(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.assignPermissionsParams,
  body: number[],
  options?: { [key: string]: any },
) {
  const { roleId: param0, ...queryParams } = params;
  return request<API.ApiResponseVoid>(`/smart-authority/v1.0/roles/${param0}/permissions`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 获取用户的所有角色 GET /smart-authority/v1.0/roles/user/${param0} */
export async function getUserRoles(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getUserRolesParams,
  options?: { [key: string]: any },
) {
  const { userId: param0, ...queryParams } = params;
  return request<API.ApiResponseListRoleResp>(`/smart-authority/v1.0/roles/user/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

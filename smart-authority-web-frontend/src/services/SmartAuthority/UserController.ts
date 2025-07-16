// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 分页查询用户 GET /smart-authority/v1.0/api/users */
export async function pageUsers(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.pageUsersParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponsePageUserResp>('/smart-authority/v1.0/api/users', {
    method: 'GET',
    params: {
      ...params,
      req: undefined,
      ...params['req'],
    },
    ...(options || {}),
  });
}

/** 创建用户 POST /smart-authority/v1.0/api/users */
export async function createUser(body: API.UserCreateReq, options?: { [key: string]: any }) {
  return request<API.ApiResponseUserResp>('/smart-authority/v1.0/api/users', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取用户详情 GET /smart-authority/v1.0/api/users/${param0} */
export async function getUserById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getUserByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseUserResp>(`/smart-authority/v1.0/api/users/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新用户 PUT /smart-authority/v1.0/api/users/${param0} */
export async function updateUser(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateUserParams,
  body: API.UserUpdateReq,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseVoid>(`/smart-authority/v1.0/api/users/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除用户 DELETE /smart-authority/v1.0/api/users/${param0} */
export async function deleteUser(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteUserParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseVoid>(`/smart-authority/v1.0/api/users/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

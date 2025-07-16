// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 分页查询部门 GET /smart-authority/v1.0/api/departments */
export async function pageDepartments(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.pageDepartmentsParams,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseIPageDepartmentResp>('/smart-authority/v1.0/api/departments', {
    method: 'GET',
    params: {
      ...params,
      req: undefined,
      ...params['req'],
    },
    ...(options || {}),
  });
}

/** 创建部门 POST /smart-authority/v1.0/api/departments */
export async function createDepartment(
  body: API.DepartmentCreateReq,
  options?: { [key: string]: any },
) {
  return request<API.ApiResponseVoid>('/smart-authority/v1.0/api/departments', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取部门详情 GET /smart-authority/v1.0/api/departments/${param0} */
export async function getDepartmentById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getDepartmentByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseDepartmentResp>(`/smart-authority/v1.0/api/departments/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新部门 PUT /smart-authority/v1.0/api/departments/${param0} */
export async function updateDepartment(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.updateDepartmentParams,
  body: API.DepartmentUpdateReq,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseVoid>(`/smart-authority/v1.0/api/departments/${param0}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: { ...queryParams },
    data: body,
    ...(options || {}),
  });
}

/** 删除部门 DELETE /smart-authority/v1.0/api/departments/${param0} */
export async function deleteDepartment(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteDepartmentParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.ApiResponseVoid>(`/smart-authority/v1.0/api/departments/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

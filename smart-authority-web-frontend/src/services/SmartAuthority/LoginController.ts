// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 用户登录 POST /smart-authority/v1.0/api/auth/login */
export async function login(body: API.LoginReq, options?: { [key: string]: any }) {
  return request<API.ApiResponseLoginResp>('/smart-authority/v1.0/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 用户登出 POST /smart-authority/v1.0/api/auth/logout */
export async function logout(options?: { [key: string]: any }) {
  return request<API.ApiResponseVoid>('/smart-authority/v1.0/api/auth/logout', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 验证token有效性 GET /smart-authority/v1.0/api/auth/verify */
export async function verifyToken(options?: { [key: string]: any }) {
  return request<API.ApiResponseVoid>('/smart-authority/v1.0/api/auth/verify', {
    method: 'GET',
    ...(options || {}),
  });
}

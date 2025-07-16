import { request } from '@/utils/request';

export interface LoginParams {
  username: string;
  password: string;
}

export interface LoginResult {
  id: number;
  username: string;
  name: string;
  email: string;
  phone: string;
  avatar: string | null;
  status: string;
  isAdmin: boolean | null;
  accessToken: string;
  refreshToken: string;
}

export async function login(params: LoginParams) {
  return request<LoginResult>('/api/auth/login', {
    method: 'POST',
    data: params,
  });
}

export async function logout() {
  return request('/api/auth/logout', {
    method: 'POST',
  });
} 
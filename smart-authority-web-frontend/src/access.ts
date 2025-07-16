import type { API } from '@/services/SmartAuthority/typings';

/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 * */
export default function access(initialState: { currentUser?: API.UserResp } | undefined) {
  const { currentUser } = initialState ?? {};
  return {
    canAdmin: currentUser && currentUser.isAdmin === 'admin',
    canAccessTenant: currentUser && currentUser.isAdmin === 'admin',
    canAccessOwnTenant: currentUser && currentUser.tenantId !== undefined,
  };
}

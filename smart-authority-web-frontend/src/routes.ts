import { IRoute } from '@umijs/max';

const routes: IRoute[] = [
  {
    path: '/',
    redirect: '/login',
    absPath: '/',
    id: 'root',
  },
  {
    path: '/login',
    component: '@/pages/Login',
    layout: false,
    absPath: '/login',
    id: 'login',
  },
  {
    path: '/dashboard',
    component: '@/pages/Dashboard',
    absPath: '/dashboard',
    id: 'dashboard',
  },
  {
    path: '/tenant',
    component: '@/pages/Tenant/List',
    absPath: '/tenant',
    id: 'tenant',
    access: 'canAccessTenant',
  },
  {
    path: '*',
    component: '@/pages/404',
    absPath: '*',
    id: '404',
  },
];

export default routes; 
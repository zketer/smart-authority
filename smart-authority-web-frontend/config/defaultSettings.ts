import { ProLayoutProps } from '@ant-design/pro-components';

const { NODE_ENV } = process.env;

/**
 * @name Jayden
 */
const Settings: ProLayoutProps & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'light',
  // 拂晓蓝
  colorPrimary: '#4096ff',
  layout: 'mix',
  // splitMenus: true  contentWidth: 'Fluid',
  // fixedHeader: false,  colorWeak: false,
  title: 'Smart Authority',
  pwa: true,
  logo: NODE_ENV === 'development' ? '/logo.svg' : '/SmartAuthority/logo.svg',
  iconfontUrl: '',
  token: {
    // 参见ts声明，demo 见文档，通过token 修改样式
    // https://procomponents.ant.design/components/layout#%E9%80%9A%E8%BF%87-token-%E4%BF%AE%E6%94%B9%E6%A0%B7%E5%BC%8F
    bgLayout: '#ffffff',
    sider: {
      colorMenuBackground: '#fff',
      colorMenuItemDivider: '#dfdfdf',
      colorTextMenu: '#595959',
      colorBgMenuItemSelected: '#4096ff',
      colorTextMenuSelected: '#ffffff',
    }
  },
};

export default Settings;

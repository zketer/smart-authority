import {LayoutSetting, SelectLang, AvatarDropdown} from '@/components';
import {PageLoading, SettingDrawer, Settings as LayoutSettings} from '@ant-design/pro-components';
import type {RunTimeLayoutConfig} from '@umijs/max';
import {history} from '@umijs/max';
import {ConfigProvider, Spin} from 'antd';
import defaultSettings from '../config/defaultSettings';
import '../public/iconfont/iconfont.css';
import './index.less';
import {errorConfig} from './requestErrorConfig';

const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/login';

interface CurrentUser {
	name?: string;
	avatar?: string;
	userid?: string;
	email?: string;
	username?: string;
	[key: string]: any;
}

// 路由守卫
export function onRouteChange({ location, routes, action }: any) {
	const token = localStorage.getItem('accessToken');
	const isLoginPage = location.pathname === loginPath;
	const isDashboardPage = location.pathname === '/dashboard';
	
	// 未登录且不在登录页，重定向到登录页
	if (!token && !isLoginPage) {
		history.push(loginPath);
		return;
	}
	
	// 已登录且在登录页，重定向到dashboard
	if (token && isLoginPage) {
		history.push('/dashboard');
		return;
	}
}

/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */
export async function getInitialState(): Promise<{
	settings?: Partial<LayoutSettings>;
	loading?: boolean;
	currentUser?: CurrentUser;
}> {
	const fetchUserInfo = async () => {
		try {
	const token = localStorage.getItem('accessToken');
			const userInfoStr = localStorage.getItem('userInfo');
	
			if (token && userInfoStr) {
				const userInfo = JSON.parse(userInfoStr);
				return {
					name: userInfo.name || userInfo.username,
					avatar: userInfo.avatar || 'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png',
					userid: userInfo.id,
					email: userInfo.email,
					...userInfo,
				};
			}
			return undefined;
		} catch (error) {
			console.error('获取用户信息失败:', error);
			return undefined;
		}
	};

	// 如果不是登录页面，执行
	const { location } = history;
	if (location.pathname !== loginPath) {
		const currentUser = await fetchUserInfo();
		return {
			currentUser,
			settings: defaultSettings as Partial<LayoutSettings>,
		};
	}
	return {
		settings: defaultSettings as Partial<LayoutSettings>,
	};
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({initialState, setInitialState}) => {
	return {
		siderWidth: 200,
		avatarProps: {
			src: initialState?.currentUser?.avatar || 'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png',
			title: <span>{initialState?.currentUser?.name || '未登录'}</span>,
			render: (props, dom) => {
				if (initialState?.loading) {
					return (
						<Spin size="small">
							{dom}
						</Spin>
					);
				}
				return initialState?.currentUser ? (
					<AvatarDropdown>{dom}</AvatarDropdown>
				) : (
					<span style={{ cursor: 'pointer' }}>{dom}</span>
				);
			},
		},
		actionsRender: () => [
			<SelectLang key="SelectLang"/>,
			<LayoutSetting key={'layout'} />
		],
		// footerRender: () => <Footer />,
		onPageChange: () => {
			// Remove duplicate route change logic since it's handled by onRouteChange
		},
		appList: [],
		layoutBgImgList: [],
		links: isDev
			? [
				// <Link key="openapi" to="/umi/plugin/openapi" target="_blank">
				//   <LinkOutlined />
				//   <span>OpenAPI 文档</span>
				// </Link>,
			]
			: [],
		menuHeaderRender: undefined,
		menuRender: (props, defaultDom) =>
			(
				<div className={'abcd'}>
					{defaultDom}
				</div>
			),

		// 自定义 403 页面
		// unAccessible: <div>unAccessible</div>,
		// 增加一个 loading 的状态
		// @ts-ignore
		childrenRender: (children) => {
			if (initialState?.loading) return <PageLoading/>;
			return (
				<>
					<ConfigProvider
						theme={{
							// 1. 单独使用暗色算法
							// algorithm: theme.compactAlgorithm,

							// 2. 组合使用暗色算法与紧凑算法
							// algorithm: [theme.darkAlgorithm, theme.compactAlgorithm],
							token: {
								borderRadius: 4,
								fontSize: 12,
								wireframe: false,
							},
							components: {
								Button: {
									lineHeight: 1,
								},
								// Menu: {
								//   fontSize: 12,
								// },
								Dropdown: {
									fontSize: 12,
								},
							},
						}}
					>
						{children}
					</ConfigProvider>

					{isDev && (
						<SettingDrawer
							disableUrlParams
							enableDarkTheme
							settings={initialState?.settings}
							onSettingChange={(settings) => {
								setInitialState((preInitialState) => ({
									...preInitialState,
									settings,
								}));
							}}
						/>
					)}
				</>
			);
		},
		...initialState?.settings,
	};
};

/**
 * @name request 配置，可以配置错误处理
 * 它基于 axios 和 ahooks 的 useRequest 提供了一套统一的网络请求和错误处理方案。
 * @doc https://umijs.org/docs/max/request#配置
 */
export const request = {
	...errorConfig,
};

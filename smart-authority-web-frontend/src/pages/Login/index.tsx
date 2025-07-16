import React, { useState, useEffect } from 'react';
import { Form, Input, Button, message } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';
import { history, useModel } from '@umijs/max';
import { login } from '@/services/SmartAuthority/LoginController';
import { getUserById } from '@/services/SmartAuthority/UserController';
import styles from './index.less';

interface LoginParams {
  username: string;
  password: string;
}

interface LoginResponse {
  success: boolean;
  code: number;
  chMessage: string;
  enMessage: string;
  data?: {
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
  };
}

const Login: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const { initialState, setInitialState } = useModel('@@initialState');

  useEffect(() => {
    // 检查是否已登录
    const token = localStorage.getItem('accessToken');
    if (token) {
      history.push('/dashboard');
    }
  }, []);

  const onFinish = async (values: LoginParams) => {
    try {
      setLoading(true);
      const response = await login(values) as LoginResponse;
      
      if (response.success && response.data?.accessToken) {
        const { data } = response;
        // 存储 tokens
        localStorage.setItem('accessToken', data.accessToken);
        localStorage.setItem('refreshToken', data.refreshToken);
        
        // 获取用户详情
        try {
          const userDetailResponse = await getUserById({ id: data.id });
          if (userDetailResponse.data) {
            const userDetail = userDetailResponse.data;
            // 存储完整的用户信息
            localStorage.setItem('userInfo', JSON.stringify(userDetail));
            
            // 更新全局状态
            await setInitialState((s) => ({
              ...s,
              currentUser: {
                name: userDetail.name || userDetail.username,
                avatar: userDetail.avatar || 'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png',
                userid: String(userDetail.id),
                email: userDetail.email,
                username: userDetail.username,
                phone: userDetail.phone,
                status: userDetail.status,
                isAdmin: userDetail.isAdmin,
                roles: userDetail.roles,
                departmentName: userDetail.departmentName,
                tenantName: userDetail.tenantName,
                tenantId: userDetail.tenantId,
              },
              isInitialized: true,
            }));
            
            message.success(response.chMessage);
            // 使用 replace 而不是 push，避免用户点击浏览器后退按钮回到登录页
            history.replace('/dashboard');
          } else {
            message.error('获取用户详情失败');
          }
        } catch (error) {
          console.error('获取用户详情失败:', error);
          message.error('获取用户详情失败');
        }
      } else {
        message.error(response.chMessage || '登录失败');
      }
    } catch (error) {
      message.error('登录失败，请重试');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <div className={styles.title}>
          <h1>Smart Authority</h1>
          <p>智能权限管理系统</p>
        </div>
        <Form
          name="login"
          initialValues={{ remember: true }}
          onFinish={onFinish}
          className={styles.form}
        >
          <Form.Item
            name="username"
            rules={[{ required: true, message: '请输入用户名!' }]}
          >
            <Input
              prefix={<UserOutlined />}
              placeholder="用户名"
              size="large"
            />
          </Form.Item>
          <Form.Item
            name="password"
            rules={[{ required: true, message: '请输入密码!' }]}
          >
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="密码"
              size="large"
            />
          </Form.Item>
          <Form.Item>
            <Button
              type="primary"
              htmlType="submit"
              loading={loading}
              size="large"
              block
            >
              登录
            </Button>
          </Form.Item>
        </Form>
      </div>
    </div>
  );
};

export default Login; 
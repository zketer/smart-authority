import React, { useState } from 'react';
import { LogoutOutlined, UserOutlined } from '@ant-design/icons';
import { Dropdown } from 'antd';
import { history, useModel } from '@umijs/max';
import { logout } from '@/services/SmartAuthority/LoginController';
import HeaderDropdown from '../HeaderDropdown';
import ProfileModal from '../ProfileModal';

interface AvatarDropdownProps {
  children?: React.ReactNode;
}

const AvatarDropdown: React.FC<AvatarDropdownProps> = ({ children }) => {
  const { initialState, setInitialState } = useModel('@@initialState');
  const [isProfileVisible, setIsProfileVisible] = useState(false);

  const onMenuClick = async (event: { key: string }) => {
    const { key } = event;
    if (key === 'logout') {
      try {
        await logout();
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('userInfo');
        await setInitialState((s) => ({ ...s, currentUser: undefined }));
        history.replace('/login');
      } catch (error) {
        console.error('退出登录失败:', error);
      }
      return;
    }

    if (key === 'profile') {
      setIsProfileVisible(true);
    }
  };

  const menuItems = [
    {
      key: 'profile',
      icon: <UserOutlined />,
      label: '个人信息',
    },
    {
      type: 'divider' as const,
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
    },
  ];

  return (
    <>
      <HeaderDropdown
        menu={{
          items: menuItems,
          onClick: onMenuClick,
        }}
      >
        {children}
      </HeaderDropdown>
      <ProfileModal
        open={isProfileVisible}
        onCancel={() => setIsProfileVisible(false)}
      />
    </>
  );
};

export default AvatarDropdown;

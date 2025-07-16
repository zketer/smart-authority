import { SettingOutlined } from '@ant-design/icons';
import { Button } from 'antd';
import React from 'react';

interface IProps {}

export const LayoutSetting: React.FC<IProps> = () => {
  return (
    <Button
      style={{ fontSize: 12 }}
      type={'text'}
      icon={<SettingOutlined style={{ color: 'rgba(0, 0, 0, 0.45)' }} />}
      onClick={() => {
        const handleDom: HTMLDivElement | null = document.querySelector(
          '.ant-pro-setting-drawer-handle',
        );
        if (handleDom) {
          handleDom.click();
        }
      }}
    />
  );
};

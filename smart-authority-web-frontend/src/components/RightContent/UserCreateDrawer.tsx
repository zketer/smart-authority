import { PlusOutlined } from '@ant-design/icons';
import { Button, Drawer } from 'antd';
import React, { useState } from 'react';

interface IProps {}

export const UserCreateDrawer: React.FC<IProps> = () => {
  const [open, setOpen] = useState(false);

  const showDrawer = () => {
    setOpen(true);
  };

  const onClose = () => {
    setOpen(false);
  };

  return (
    <>
      <Button
        type="primary"
        icon={<PlusOutlined />}
        onClick={showDrawer}
        style={{ marginRight: 8 }}
      >
        创建用户
      </Button>
      <Drawer
        title="创建用户"
        placement="right"
        onClose={onClose}
        open={open}
        width={400}
      >
        {/* 这里将添加用户创建表单 */}
        <p>用户创建表单将在这里实现</p>
      </Drawer>
    </>
  );
};
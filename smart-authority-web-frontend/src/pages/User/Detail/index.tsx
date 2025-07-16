import { PageContainer } from '@ant-design/pro-components';
import { ProForm, ProFormText, ProFormSelect } from '@ant-design/pro-components';
import { Card, message } from 'antd';
import { useParams } from 'react-router';
import { useEffect, useState } from 'react';
import { history } from 'umi';
import { getUserById, createUser } from '@/services/SmartAuthority/UserController';
import type { API } from '@/services/SmartAuthority/typings';

const STATUS = {
  OPEN: 'open',
  CLOSE: 'close'
} as const;

const UserDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [user, setUser] = useState<API.UserResp>();
  const isCreate = id === 'create';

  useEffect(() => {
    if (id && !isCreate) {
      getUserById({ id }).then((res) => {
        if (res.data) {
          setUser(res.data);
        }
      });
    }
  }, [id]);

  const handleSubmit = async (values: API.UserCreateReq) => {
    try {
      await createUser(values);
      message.success('操作成功');
      history.push('/user/list');
    } catch (error) {
      message.error('操作失败');
    }
  };

  return (
    <PageContainer>
      <Card>
        <ProForm
          initialValues={user}
          onFinish={handleSubmit}
        >
          <ProFormText
            name="username"
            label="用户名"
            rules={[{ required: true, message: '请输入用户名' }]}
          />
          <ProFormText
            name="nickname"
            label="昵称"
            rules={[{ required: true, message: '请输入昵称' }]}
          />
          <ProFormText
            name="email"
            label="邮箱"
            rules={[{ required: true, message: '请输入邮箱' }]}
          />
          <ProFormText
            name="phone"
            label="手机号"
            rules={[{ required: true, message: '请输入手机号' }]}
          />
          {isCreate && (
            <ProFormText.Password
              name="password"
              label="密码"
              rules={[{ required: true, message: '请输入密码' }]}
            />
          )}
          <ProFormSelect
            name="status"
            label="状态"
            options={[
              { label: '启用', value: STATUS.OPEN },
              { label: '禁用', value: STATUS.CLOSE },
            ]}
            rules={[{ required: true, message: '请选择状态' }]}
          />
        </ProForm>
      </Card>
    </PageContainer>
  );
};

export default UserDetail;
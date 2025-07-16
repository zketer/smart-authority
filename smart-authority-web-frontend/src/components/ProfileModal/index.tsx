import React, { useState, useEffect } from 'react';
import { Modal, Avatar, Form, Input, Button, message, Upload } from 'antd';
import { useModel } from '@umijs/max';
import { UserOutlined, CameraOutlined } from '@ant-design/icons';
import { updateUser, getUserById } from '@/services/SmartAuthority/UserController';
import type { RcFile } from 'antd/es/upload/interface';
import styles from './index.less';

// 默认头像 URL
const DEFAULT_AVATAR = 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png';

interface ProfileModalProps {
  open: boolean;
  onCancel: () => void;
}

const ProfileModal: React.FC<ProfileModalProps> = ({ open, onCancel }) => {
  const { initialState, setInitialState } = useModel('@@initialState');
  const currentUser = initialState?.currentUser;
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [tempAvatar, setTempAvatar] = useState<string>(DEFAULT_AVATAR);
  const [userData, setUserData] = useState<API.UserResp>();

  useEffect(() => {
    if (open && currentUser?.id) {
      loadUserData();
    }
  }, [open, currentUser?.id]);

  const loadUserData = async () => {
    if (!currentUser?.id) return;
    
    try {
      setLoading(true);
      const result = await getUserById({ id: currentUser.id });
      if (result.code === 200 && result.data) {
        const userData = result.data;
        setUserData(userData);
        // 更新表单数据
        form.setFieldsValue({
          username: userData.username,
          name: userData.name || '',
          email: userData.email || '',
          phone: userData.phone || '',
        });
        // 更新头像，使用默认头像作为后备
        setTempAvatar(userData.avatar || DEFAULT_AVATAR);
      }
    } catch (error) {
      message.error('获取用户信息失败');
    } finally {
      setLoading(false);
    }
  };

  if (!currentUser) {
    return null;
  }

  const getBase64 = (file: RcFile): Promise<string> =>
    new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = (error) => reject(error);
    });

  const beforeUpload = (file: RcFile) => {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
    if (!isJpgOrPng) {
      message.error('只能上传 JPG/PNG 格式的图片!');
      return false;
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      message.error('图片大小不能超过 2MB!');
      return false;
    }
    return true;
  };

  const handleChange = async (info: any) => {
    const file = info.file.originFileObj;
    if (!file) return;
    
    try {
      const base64 = await getBase64(file);
      setTempAvatar(base64);
      message.success('头像已选择，点击更新按钮保存修改');
    } catch (error) {
      message.error('头像处理失败');
    }
  };

  const handleSubmit = async (values: any) => {
    try {
      setLoading(true);
      const updateData: API.UserUpdateReq = {
        id: currentUser.id,
        username: values.username,
        name: values.name,
        email: values.email || '',
        phone: values.phone || '',
        avatar: tempAvatar || userData?.avatar || '',
        ...(values.password ? { password: values.password } : {}),
      };

      await updateUser({ id: currentUser.id }, updateData);
      
      // 更新成功后，重新获取用户信息
      const result = await getUserById({ id: currentUser.id });
      if (result.code === 200 && result.data) {
        const newUserInfo = result.data;
        localStorage.setItem('userInfo', JSON.stringify(newUserInfo));
        await setInitialState((s) => ({
          ...s,
          currentUser: {
            name: newUserInfo.name || newUserInfo.username,
            avatar: newUserInfo.avatar || DEFAULT_AVATAR,
            userid: String(newUserInfo.id),
            email: newUserInfo.email,
            username: newUserInfo.username,
            phone: newUserInfo.phone,
            status: newUserInfo.status,
            isAdmin: newUserInfo.isAdmin,
            roles: newUserInfo.roles,
            departmentName: newUserInfo.departmentName,
            tenantName: newUserInfo.tenantName,
          },
        }));
      }

      message.success('更新成功');
      onCancel();
    } catch (error) {
      message.error('更新失败');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Modal
      title="个人信息"
      open={open}
      onCancel={onCancel}
      footer={null}
      width={500}
    >
      <div className={styles.modalContent}>
        <div className={styles.scrollContent}>
          <div className={styles.userHeader}>
            <div className={styles.avatarContainer}>
              <Avatar 
                size={80} 
                src={tempAvatar} 
                icon={!tempAvatar && <UserOutlined />}
              />
              <Upload
                name="avatar"
                showUploadList={false}
                beforeUpload={beforeUpload}
                onChange={handleChange}
                customRequest={({ file, onSuccess }) => {
                  setTimeout(() => {
                    onSuccess?.("ok");
                  }, 0);
                }}
                className={styles.avatarUpload}
              >
                <div className={styles.uploadButton}>
                  <CameraOutlined />
                </div>
              </Upload>
            </div>
            <h3>{userData?.name || userData?.username}</h3>
          </div>
          <Form
            form={form}
            layout="vertical"
            initialValues={{
              username: userData?.username,
              name: userData?.name || '',
              email: userData?.email || '',
              phone: userData?.phone || '',
            }}
            onFinish={handleSubmit}
          >
            <Form.Item
              label="用户名"
              name="username"
            >
              <Input disabled />
            </Form.Item>
            <Form.Item
              label="昵称"
              name="name"
              rules={[
                { max: 50, message: '昵称不能超过50个字符' }
              ]}
            >
              <Input placeholder="请输入昵称" />
            </Form.Item>
            <Form.Item
              label="邮箱"
              name="email"
              rules={[
                { type: 'email', message: '请输入有效的邮箱地址' }
              ]}
            >
              <Input placeholder="请输入邮箱" />
            </Form.Item>
            <Form.Item
              label="手机号"
              name="phone"
              rules={[
                { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号码' }
              ]}
            >
              <Input placeholder="请输入手机号" />
            </Form.Item>
            <Form.Item
              label="新密码"
              name="password"
              rules={[
                { min: 6, message: '密码长度不能小于6位' }
              ]}
            >
              <Input.Password placeholder="不修改请留空" />
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit" loading={loading} block>
                更新
              </Button>
            </Form.Item>
          </Form>
        </div>
      </div>
    </Modal>
  );
};

export default ProfileModal; 
import { Form, Input, Select, Button, message } from 'antd';
import { useEffect } from 'react';
import type { API } from '@/services/SmartAuthority/typings';
import { createPermission, updatePermission } from '@/services/SmartAuthority/PermissionController';

interface PermissionFormProps {
  permission?: API.PermissionResp;
  onSuccess: () => void;
  onCancel: () => void;
}

const PermissionForm: React.FC<PermissionFormProps> = ({ permission, onSuccess, onCancel }) => {
  const [form] = Form.useForm();

  useEffect(() => {
    if (permission) {
      form.setFieldsValue(permission);
    } else {
      form.resetFields();
    }
  }, [permission, form]);

  const handleSubmit = async (values: any) => {
    try {
      if (permission?.id) {
        const result = await updatePermission(
          { id: permission.id },
          values
        );
        if (!result.success) {
          message.error(result.chMessage || '更新失败');
          return;
        }
        message.success('更新成功');
      } else {
        const result = await createPermission(values);
        if (!result.success) {
          message.error(result.chMessage || '创建失败');
          return;
        }
        message.success('创建成功');
      }
      onSuccess();
    } catch (error) {
      console.error('提交失败:', error);
      message.error('提交失败');
    }
  };

  return (
    <Form
      form={form}
      layout="vertical"
      onFinish={handleSubmit}
      initialValues={{
        type: 1,
      }}
    >
      <Form.Item
        name="name"
        label="权限名称"
        rules={[{ required: true, message: '请输入权限名称' }]}
      >
        <Input placeholder="请输入权限名称" />
      </Form.Item>
      <Form.Item
        name="code"
        label="权限编码"
        rules={[{ required: true, message: '请输入权限编码' }]}
      >
        <Input placeholder="请输入权限编码" />
      </Form.Item>
      <Form.Item
        name="type"
        label="权限类型"
        rules={[{ required: true, message: '请选择权限类型' }]}
      >
        <Select>
          <Select.Option value={1}>菜单</Select.Option>
          <Select.Option value={2}>按钮</Select.Option>
          <Select.Option value={3}>API</Select.Option>
        </Select>
      </Form.Item>
      <Form.Item
        name="path"
        label="路径"
        rules={[{ required: true, message: '请输入路径' }]}
      >
        <Input placeholder="请输入路径" />
      </Form.Item>
      <Form.Item
        name="description"
        label="描述"
      >
        <Input.TextArea placeholder="请输入描述" />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit">
          提交
        </Button>
        <Button style={{ marginLeft: 8 }} onClick={onCancel}>
          取消
        </Button>
      </Form.Item>
    </Form>
  );
};

export default PermissionForm; 
import { Form, Input, Switch, Spin, Button, Space, Collapse } from 'antd';
import { useState } from 'react';
import { createTenant, updateTenant } from '@/services/SmartAuthority/TenantController';
import type { API } from '@/services/SmartAuthority/typings';
import { message } from 'antd';

const { Panel } = Collapse;

// 默认租户常量
const IS_DEFAULT = {
  DEFAULT: 'default',
  NOT_DEFAULT: 'not default'
} as const;

interface TenantFormProps {
  tenant?: API.TenantResp;
  onSuccess?: () => void;
  onCancel?: () => void;
}

const TenantForm: React.FC<TenantFormProps> = ({ tenant, onSuccess, onCancel }) => {
  const [loading, setLoading] = useState(false);
  const [form] = Form.useForm();
  const isCreate = !tenant;

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields();
      setLoading(true);
      
      // Handle status field
      const submitValues = {
        ...values,
        status: values.status ? 'open' : 'close',
        isDefault: IS_DEFAULT.NOT_DEFAULT
      };
      
      if (isCreate) {
        const result = await createTenant(submitValues);
        if (result.code === 200) {
          onSuccess?.();
          message.success('创建成功');
        } else {
          message.error(result.message || '创建失败');
        }
      } else {
        const result = await updateTenant({ id: tenant.id! }, submitValues);
        if (result.code === 200) {
          onSuccess?.();
          message.success('更新成功');
        } else {
          message.error(result.message || '更新失败');
        }
      }
    } catch (error) {
      console.error(isCreate ? '创建失败:' : '更新失败:', error);
      message.error(isCreate ? '创建失败' : '更新失败');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Spin spinning={loading}>
      <div style={{ 
        height: 'calc(100vh - 130px)',
        overflow: 'auto',
        padding: '8px 8px 48px',
        backgroundColor: '#fff'
      }}>
        <Form
          form={form}
          layout="vertical"
          initialValues={{
            ...tenant,
            status: tenant?.status === 'open',
          }}
          onFinish={handleSubmit}
          style={{
            maxWidth: '100%',
          }}
        >
          <Collapse
            defaultActiveKey={['tenantInfo']}
            ghost
            style={{
              background: 'transparent',
              border: '1px solid #f0f0f0',
              borderRadius: '2px'
            }}
          >
            <Panel 
              header="租户信息" 
              key="tenantInfo"
              style={{ borderBottom: '1px solid #f0f0f0' }}
            >
              <div style={{ padding: '8px 16px' }}>
                <Form.Item
                  name="name"
                  label="租户名称"
                  rules={[
                    { required: true, message: '请输入租户名称' },
                    { max: 100, message: '租户名称不能超过100个字符' },
                  ]}
                >
                  <Input 
                    placeholder="请输入租户名称" 
                    disabled={tenant?.isDefault === IS_DEFAULT.DEFAULT}
                  />
                </Form.Item>
                <Form.Item
                  name="status"
                  label="状态"
                  valuePropName="checked"
                  getValueFromEvent={(checked: boolean) => checked}
                >
                  <Switch
                    checkedChildren="启用"
                    unCheckedChildren="禁用"
                    disabled={tenant?.isDefault === IS_DEFAULT.DEFAULT}
                  />
                </Form.Item>
                <Form.Item
                  name="description"
                  label="租户描述"
                  rules={[
                    { max: 255, message: '租户描述不能超过255个字符' },
                  ]}
                >
                  <Input.TextArea
                    rows={4}
                    placeholder="请输入租户描述"
                    disabled={tenant?.isDefault === IS_DEFAULT.DEFAULT}
                  />
                </Form.Item>
              </div>
            </Panel>
          </Collapse>
        </Form>
        <div style={{ 
          position: 'fixed',
          bottom: 0,
          left: 'auto',
          right: 0,
          width: '600px',
          padding: '12px 24px',
          textAlign: 'right',
          background: '#fff',
          borderTop: '1px solid #f0f0f0',
          zIndex: 1
        }}>
          <Space>
            <Button onClick={onCancel}>取消</Button>
            <Button type="primary" onClick={handleSubmit}>
              {isCreate ? '确定' : '更新'}
            </Button>
          </Space>
        </div>
      </div>
    </Spin>
  );
};

export default TenantForm; 
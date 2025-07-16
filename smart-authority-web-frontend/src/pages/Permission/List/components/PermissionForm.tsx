import { Form, Input, Spin, Button, Space, TreeSelect, Select, Collapse } from 'antd';
import { useState, useEffect } from 'react';
import { createPermission, updatePermission, pagePermissions } from '@/services/SmartAuthority/PermissionController';
import { pageTenants } from '@/services/SmartAuthority/TenantController';
import type { API } from '@/services/SmartAuthority/typings';
import { message } from 'antd';

const { Panel } = Collapse;

interface PermissionFormProps {
  permission?: API.Permission;
  onSuccess?: () => void;
  onCancel?: () => void;
  isAdmin?: boolean;
  userTenantId?: number;
}

const PermissionForm: React.FC<PermissionFormProps> = ({
  permission,
  onSuccess,
  onCancel,
  isAdmin,
  userTenantId,
}) => {
  const [loading, setLoading] = useState(false);
  const [form] = Form.useForm();
  const [permissions, setPermissions] = useState<API.Permission[]>([]);
  const [tenants, setTenants] = useState<API.Tenant[]>([]);
  const [selectedTenant, setSelectedTenant] = useState<string | undefined>(permission?.tenantResp?.id?.toString());
  const isCreate = !permission?.id;

  useEffect(() => {
    fetchPermissions();
    if (isAdmin) {
      fetchTenants();
    }
  }, [isAdmin]);

  const fetchPermissions = async () => {
    try {
      const result = await pagePermissions({
        req: {
          current: 1,
          size: -1,
          ...(isAdmin ? {} : { tenantId: userTenantId }),
        },
      });
      if (result.code === 200 && result.data?.records) {
        setPermissions(result.data.records);
      }
    } catch (error) {
      console.error('获取权限列表失败:', error);
    }
  };

  const fetchTenants = async () => {
    try {
      const result = await pageTenants({ req: { current: 1, size: -1 } });
      if (result.code === 200 && result.data?.records) {
        setTenants(result.data.records);
        if (permission?.tenantResp?.id) {
          const tenant = result.data.records.find(t => t.id?.toString() === permission.tenantResp.id?.toString());
          if (tenant) {
            setSelectedTenant(tenant.id?.toString());
            form.setFieldsValue({ tenantId: tenant.id?.toString() });
          }
        }
      }
    } catch (error) {
      console.error('获取租户列表失败:', error);
    }
  };

  useEffect(() => {
    if (permission) {
      if (permission.parentId && !permission.id) {
        // 新建子权限时，只清空名称字段，其他字段继承父权限的值
        form.setFieldsValue({
          ...permission,
          name: '',
          tenantId: permission.tenantResp?.id,
        });
      } else {
        // 编辑权限时，设置所有字段的值
        form.setFieldsValue({
          ...permission,
          tenantId: permission.tenantResp?.id,
        });
      }
    }
  }, [permission, form]);

  const handleSubmit = async (values: any) => {
    try {
      setLoading(true);
      const submitData = {
        ...values,
        ...(permission?.id ? { id: permission.id } : {}),
        ...(permission?.parentId ? { parentId: permission.parentId } : {}),
        ...(isAdmin ? { tenantId: values.tenantId } : { tenantId: userTenantId }),
      };

      const result = permission?.id
        ? await updatePermission({ id: permission.id }, submitData)
        : await createPermission(submitData);

      if (result.code !== 200) {
        message.error(result.chMessage || '操作失败');
        return;
      }

      message.success('操作成功');
      onSuccess?.();
    } catch (error) {
      console.error('提交失败:', error);
      message.error('操作失败');
    } finally {
      setLoading(false);
    }
  };

  const handleTenantChange = (value: string) => {
    setSelectedTenant(value);
  };

  const buildTreeData = (permissions: API.Permission[], parentId: number = 0): API.Permission[] => {
    return permissions
      .filter(item => item.parentId === parentId)
      .map(item => ({
        ...item,
        title: item.name,
        value: item.id,
        children: buildTreeData(permissions, item.id),
      }));
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
          onFinish={handleSubmit}
          style={{
            maxWidth: '100%',
          }}
        >
          <Collapse
            defaultActiveKey={['basicInfo', 'advancedInfo']}
            ghost
            style={{
              background: 'transparent',
              border: '1px solid #f0f0f0',
              borderRadius: '2px'
            }}
          >
            <Panel 
              header="基本信息" 
              key="basicInfo"
              style={{ borderBottom: '1px solid #f0f0f0' }}
            >
              <div style={{ padding: '8px 16px' }}>
                <Form.Item
                  name="parentId"
                  label="父级权限"
                  rules={[{ required: true, message: '请选择父级权限' }]}
                >
                  <TreeSelect
                    treeData={[
                      {
                        title: '无',
                        value: 0,
                        children: buildTreeData(permissions),
                      },
                    ]}
                    placeholder="请选择父级权限"
                    treeDefaultExpandAll
                    disabled={!!permission?.id}
                    showSearch
                    allowClear
                    treeNodeFilterProp="title"
                    style={{ width: '100%' }}
                    dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                    treeDefaultExpandedKeys={[0]}
                  />
                </Form.Item>

                <Form.Item
                  name="type"
                  label="权限类型"
                  rules={[{ required: true, message: '请选择权限类型' }]}
                >
                  <Select
                    placeholder="请选择权限类型"
                    options={[
                      { label: '菜单', value: 1 },
                      { label: '按钮', value: 2 },
                      { label: 'API', value: 3 },
                    ]}
                  />
                </Form.Item>

                <Form.Item
                  name="name"
                  label="权限名称"
                  rules={[
                    { required: true, message: '请输入权限名称' },
                    { max: 50, message: '权限名称不能超过50个字符' },
                  ]}
                >
                  <Input placeholder="请输入权限名称" />
                </Form.Item>

                <Form.Item
                  name="description"
                  label="权限描述"
                  rules={[
                    { required: true, message: '请输入权限描述' },
                    { max: 255, message: '权限描述不能超过255个字符' },
                  ]}
                >
                  <Input.TextArea
                    rows={4}
                    placeholder="请输入权限描述"
                  />
                </Form.Item>
              </div>
            </Panel>

            <Panel 
              header="高级配置" 
              key="advancedInfo"
            >
              <div style={{ padding: '8px 16px' }}>
                <Form.Item
                  name="code"
                  label="权限编码"
                  rules={[
                    { required: true, message: '请输入权限编码' },
                    { max: 50, message: '权限编码不能超过50个字符' },
                    { pattern: /^[a-zA-Z0-9_:-]+$/, message: '权限编码只能包含字母、数字、下划线、连字符和冒号' },
                  ]}
                >
                  <Input placeholder="请输入权限编码" />
                </Form.Item>

                <Form.Item
                  name="path"
                  label="权限路径"
                  rules={[
                    { required: true, message: '请输入权限路径' },
                    { max: 255, message: '权限路径不能超过255个字符' },
                  ]}
                >
                  <Input placeholder="请输入权限路径" />
                </Form.Item>

                {isAdmin && (
                  <Form.Item
                    name="tenantId"
                    label="租户"
                    rules={[{ required: true, message: '请选择租户' }]}
                  >
                    <Select
                      placeholder="请选择租户"
                      onChange={handleTenantChange}
                      value={selectedTenant}
                      loading={loading}
                      optionFilterProp="label"
                      showSearch
                      style={{ width: '100%' }}
                      disabled={!isCreate}
                    >
                      {tenants.map((tenant) => (
                        <Select.Option 
                          key={tenant.id} 
                          value={tenant.id?.toString()}
                          label={tenant.name}
                        >
                          {tenant.name}
                        </Select.Option>
                      ))}
                    </Select>
                  </Form.Item>
                )}
              </div>
            </Panel>
          </Collapse>
        </Form>
      </div>
    </Spin>
  );
};

export default PermissionForm; 
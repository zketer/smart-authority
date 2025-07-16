import { Form, Input, Spin, Button, Space, Collapse, Tree, Select } from 'antd';
import { useState, useEffect } from 'react';
import { createRole, updateRole } from '@/services/SmartAuthority/RoleController';
import { pagePermissions } from '@/services/SmartAuthority/PermissionController';
import { pageTenants } from '@/services/SmartAuthority/TenantController';
import type { API } from '@/services/SmartAuthority/typings';
import { message } from 'antd';
import { useModel } from '@umijs/max';

const { Panel } = Collapse;

interface RoleFormProps {
  role?: API.RoleResp;
  onSuccess?: () => void;
  onCancel?: () => void;
}

const RoleForm: React.FC<RoleFormProps> = ({ role, onSuccess, onCancel }) => {
  const [loading, setLoading] = useState(false);
  const [form] = Form.useForm();
  const [permissionTree, setPermissionTree] = useState<any[]>([]);
  const [selectedPermissionIds, setSelectedPermissionIds] = useState<number[]>([]);
  const [tenants, setTenants] = useState<API.TenantResp[]>([]);
  const [selectedTenant, setSelectedTenant] = useState<string | undefined>(role?.tenantId?.toString());
  const isCreate = !role?.id;
  const { initialState } = useModel('@@initialState');
  const [isAdmin, setIsAdmin] = useState(false);
  const [userTenantId, setUserTenantId] = useState<number | undefined>(undefined);

  useEffect(() => {
    if (initialState?.currentUser) {
      const isAdminUser = initialState.currentUser.isAdmin === 'admin';
      const tenantId = initialState.currentUser.tenantId;
      
      setIsAdmin(isAdminUser);
      setUserTenantId(tenantId ? Number(tenantId) : undefined);
    }
  }, [initialState?.currentUser]);

  useEffect(() => {
    if (role) {
      form.setFieldsValue({
        ...role,
        permissionIds: role.permissionIds || [],
        tenantId: role.tenantId?.toString()
      });
      setSelectedPermissionIds(role.permissionIds || []);
      setSelectedTenant(role.tenantId?.toString());
    }
  }, [role, form]);

  useEffect(() => {
    const fetchPermissions = async () => {
      try {
        const result = await pagePermissions({
          current: 1,
          size: 1000,
        });
        if (result.code === 200 && result.data?.records) {
          const treeData = buildPermissionTree(result.data.records);
          setPermissionTree(treeData);
        }
      } catch (error) {
        console.error('获取权限列表失败:', error);
        message.error('获取权限列表失败');
      }
    };

    const fetchTenants = async () => {
      try {
        const result = await pageTenants({ req: { current: 1, size: 100 } });
        if (result.code === 200 && result.data) {
          const tenantList = result.data.records || [];
          setTenants(tenantList as API.TenantResp[]);
          
          if (role?.id && role?.tenantId) {
            const tenant = tenantList.find((t: API.TenantResp) => t.id?.toString() === role.tenantId?.toString());
            if (tenant) {
              setSelectedTenant(tenant.id?.toString());
              form.setFieldsValue({ tenantId: tenant.id?.toString() });
            }
          }
        } else {
          message.error(result.chMessage || '获取租户列表失败');
        }
      } catch (error) {
        console.error('获取租户列表失败:', error);
        message.error('获取租户列表失败');
      }
    };

    fetchPermissions();
    fetchTenants();
  }, [role]);

  const buildPermissionTree = (permissions: API.PermissionResp[]) => {
    const buildTree = (parentId: number = 0): any[] => {
      return permissions
        .filter(item => item.parentId === parentId)
        .map(item => ({
          title: item.name,
          key: item.id,
          children: buildTree(item.id),
        }));
    };
    return buildTree();
  };

  const handleSubmit = async () => {
    try {
      const values = await form.validateFields();
      setLoading(true);
      
      // 使用当前用户的租户ID或选择的租户ID
      const submitValues = {
        ...values,
        tenantId: !isAdmin && userTenantId ? userTenantId : selectedTenant ? Number(selectedTenant) : role?.tenantId,
        permissionIds: selectedPermissionIds
      };
      
      console.log('提交的数据:', submitValues);
      
      if (isCreate) {
        const result = await createRole(submitValues);
        if (result.code === 200) {
          onSuccess?.();
          message.success('创建成功');
        } else {
          message.error(result.message || '创建失败');
        }
      } else {
        const result = await updateRole({ id: role.id! }, submitValues);
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

  const handleTenantChange = (value: string) => {
    setSelectedTenant(value);
  };

  return (
    <Spin spinning={loading}>
      <div style={{ 
        height: 'calc(100vh - 180px)',
        overflow: 'auto',
        padding: '24px',
        backgroundColor: '#fff'
      }}>
        <Form
          form={form}
          layout="vertical"
          onFinish={handleSubmit}
        >
          <Collapse
            defaultActiveKey={['basicInfo', 'permissionInfo']}
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
                  name="name"
                  label="角色名称"
                  rules={[
                    { required: true, message: '请输入角色名称' },
                    { max: 50, message: '角色名称不能超过50个字符' },
                  ]}
                >
                  <Input placeholder="请输入角色名称" />
                </Form.Item>

                <Form.Item
                  name="description"
                  label="角色描述"
                  rules={[
                    { max: 255, message: '角色描述不能超过255个字符' },
                  ]}
                >
                  <Input.TextArea
                    rows={4}
                    placeholder="请输入角色描述"
                  />
                </Form.Item>

                {isAdmin && (
                  <Form.Item
                    name="tenantId"
                    label="租户"
                    rules={[
                      { required: true, message: '请选择租户' },
                    ]}
                  >
                    <Select
                      placeholder="请选择租户"
                      onChange={handleTenantChange}
                      value={selectedTenant}
                      optionFilterProp="label"
                      showSearch
                      style={{ width: '100%' }}
                      disabled={!!role?.id}
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

            <Panel
              header="权限配置"
              key="permissionInfo"
            >
              <div>
                <Form.Item
                  name="permissionIds"
                  style={{ marginBottom: 0 }}
                >
                  <Tree
                    checkable
                    treeData={permissionTree}
                    defaultExpandAll
                    height={400}
                    checkedKeys={selectedPermissionIds}
                    onCheck={(checkedKeys, info) => {
                      console.log('选中的权限:', checkedKeys, info);
                      const checkedIds = checkedKeys as number[];
                      setSelectedPermissionIds(checkedIds);
                      form.setFieldsValue({ permissionIds: checkedIds });
                    }}
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
            <Button type="primary" onClick={() => form.submit()}>
              {isCreate ? '确定' : '更新'}
            </Button>
          </Space>
        </div>
      </div>
    </Spin>
  );
};

export default RoleForm; 
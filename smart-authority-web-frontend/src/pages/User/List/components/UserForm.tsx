import { Form, Input, Select, Button, message, Upload, Collapse } from 'antd';
import { useState, useEffect } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import { createUser, updateUser } from '@/services/SmartAuthority/UserController';
import { getRoles, getUserRoles } from '@/services/SmartAuthority/RoleController';
import { pageDepartments } from '@/services/SmartAuthority/DepartmentController';
import type { API } from '@/services/SmartAuthority/typings';
import type { UploadFile, RcFile } from 'antd/es/upload/interface';
import { pageTenants } from '@/services/SmartAuthority/TenantController';
import { useModel } from '@umijs/max';

const { Panel } = Collapse;

interface UserFormProps {
  values?: API.UserResp;
  onSubmit?: () => void;
  onCancel?: () => void;
}

const STATUS = {
  OPEN: 'open',
  CLOSE: 'close'
} as const;

const UserForm: React.FC<UserFormProps> = ({ values, onSubmit, onCancel }) => {
  const [form] = Form.useForm();
  const [roles, setRoles] = useState<API.RoleResp[]>([]);
  const [selectedRoles, setSelectedRoles] = useState<string[]>([]);
  const [selectedTenant, setSelectedTenant] = useState<string | undefined>(values?.tenantId?.toString());
  const [tempAvatar, setTempAvatar] = useState<string | undefined>(values?.avatar);
  const [showPasswordField, setShowPasswordField] = useState(false);
  const [loading, setLoading] = useState(false);
  const [tenants, setTenants] = useState<API.TenantResp[]>([]);
  const [departments, setDepartments] = useState<API.DepartmentResp[]>([]);
  const [selectedDepartment, setSelectedDepartment] = useState<string | undefined>(values?.departmentId?.toString());
  const { initialState } = useModel('@@initialState');
  const isAdmin = initialState?.currentUser?.isAdmin === 'admin';
  const userTenantId = initialState?.currentUser?.tenantId;

  useEffect(() => {
    const fetchRoles = async () => {
      setLoading(true);
      try {
        const result = await getRoles({ req: { current: 1, size: 100 } });
        if (result.code === 200 && result.data) {
          const rolesData = Array.isArray(result.data) ? result.data : result.data.records || [];
          setRoles(rolesData as API.RoleResp[]);
        }
      } catch (error) {
        console.error('获取角色列表失败:', error);
        message.error('获取角色列表失败');
      } finally {
        setLoading(false);
      }
    };

    const fetchTenants = async () => {
      if (!isAdmin) return;
      setLoading(true);
      try {
        const result = await pageTenants({ req: { current: 1, size: 100 } });
        if (result.code === 200 && result.data) {
          const tenantList = result.data.records || [];
          setTenants(tenantList as API.TenantResp[]);
          if (values?.id && values?.tenantId) {
            const tenant = tenantList.find((t: API.TenantResp) => t.id?.toString() === values.tenantId?.toString());
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
      } finally {
        setLoading(false);
      }
    };

    const fetchUserRoles = async () => {
      if (!values?.id) return;
      try {
        const result = await getUserRoles({ userId: values.id });
        if (result.code === 200 && result.data) {
          const roleIds = result.data.map((role: API.RoleResp) => role.id?.toString() || '');
          setSelectedRoles(roleIds);
          form.setFieldsValue({ roles: roleIds });
        }
      } catch (error) {
        console.error('获取用户角色失败:', error);
        message.error('获取用户角色失败');
      }
    };

    fetchRoles();
    fetchTenants();
    if (values?.id) {
      fetchUserRoles();
    }
  }, [values, form, isAdmin]);

  // 部门加载逻辑
  useEffect(() => {
    const fetchDepartments = async () => {
      setLoading(true);
      try {
        let tenantId;
        if (isAdmin) {
          tenantId = selectedTenant || values?.tenantId;
        } else {
          tenantId = userTenantId;
        }
        if (tenantId) {
          const result = await pageDepartments({
            req: {
              current: 1,
              size: 1000,
              tenantId: Number(tenantId),
            },
          });
          if (result.code === 200 && result.data?.records) {
            let deptList = result.data.records;
            // 如果是编辑，且当前部门不在列表里，则追加
            if (
              values?.id &&
              values?.departmentId &&
              !deptList.some(dep => String(dep.id) === String(values.departmentId)) &&
              // 只有当前租户和用户原租户一致时才兜底
              ((isAdmin && (selectedTenant === String(values.tenantId))) || (!isAdmin && userTenantId === values.tenantId))
            ) {
              deptList = [
                ...deptList,
                {
                  id: values.departmentId,
                  name: values.departmentName || String(values.departmentId),
                },
              ];
            }
            setDepartments(deptList);
            
            // 如果是编辑模式且没有切换租户，设置部门值
            if (values?.id && values?.departmentId && !selectedTenant) {
              const deptIdStr = String(values.departmentId);
              const found = deptList.some(dep => String(dep.id) === deptIdStr);
              if (found) {
                form.setFieldsValue({ departmentId: deptIdStr });
                setSelectedDepartment(deptIdStr);
              }
            }
          }
        } else {
          setDepartments([]);
        }
      } catch (error) {
        message.error('获取部门列表失败');
      } finally {
        setLoading(false);
      }
    };
    fetchDepartments();
  }, [isAdmin, selectedTenant, userTenantId, values, form]);

  const beforeUpload = (file: RcFile) => {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
    if (!isJpgOrPng) {
      message.error('只能上传 JPG/PNG 文件!');
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      message.error('图片大小不能超过 2MB!');
    }
    return isJpgOrPng && isLt2M;
  };

  const handleChange = (info: any) => {
    if (info.file.status === 'done') {
      const reader = new FileReader();
      reader.onload = (e) => {
        setTempAvatar(e.target?.result as string);
      };
      reader.readAsDataURL(info.file.originFileObj);
    }
  };

  const handleSubmit = async (formValues: any) => {
    // 转换 departmentId，确保为数字
    const submitData = {
      ...formValues,
      roleIds: selectedRoles.map(id => Number(id)),
      avatar: tempAvatar,
      tenantId: isAdmin ? (selectedTenant ? Number(selectedTenant) : values?.tenantId) : userTenantId,
      departmentId: formValues.departmentId?.value ? Number(formValues.departmentId.value) : undefined,
    };
    console.log('submitData:', submitData); // departmentId 应该是数字

    if (values?.id && !showPasswordField) {
      delete submitData.password;
    }

    if (values?.id) {
      const result = await updateUser({ id: values.id }, submitData, {});
      if (result.code !== 200) {
        message.error(result.chMessage || '更新失败');
        return;
      }
      message.success('更新成功');
    } else {
      const result = await createUser(submitData);
      if (result.code !== 200) {
        message.error(result.chMessage || '创建失败');
        return;
      }
      message.success('创建成功');
    }

    onSubmit?.();
  };

  const handleRoleChange = (value: string[]) => {
    setSelectedRoles(value);
  };

  const handleTenantChange = (value: string) => {
    setSelectedTenant(value);
    setSelectedDepartment(undefined);
    form.setFieldsValue({ departmentId: undefined });
    // Clear departments when switching tenants
    setDepartments([]);
  };

  const handleDepartmentChange = (value: string) => {
    form.setFieldsValue({ departmentId: value });
  };

  // 构造部门下拉 options
  const departmentOptions = departments.map(dep => ({
    label: dep.name || String(dep.id),
    value: String(dep.id),
  }));

  // 受控 value
  const departmentValue = departmentOptions.find(opt => opt.value === String(form.getFieldValue('departmentId')))
    ? {
        value: String(form.getFieldValue('departmentId')),
        label: departmentOptions.find(opt => opt.value === String(form.getFieldValue('departmentId')))?.label,
      }
    : undefined;

  return (
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
        initialValues={{
          status: STATUS.OPEN,
          ...values,
          departmentId: values?.departmentId && values?.departmentName
            ? { value: String(values.departmentId), label: values.departmentName }
            : undefined,
        }}
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
              <div style={{ display: 'flex', gap: '24px' }}>
                <div style={{ flex: 1 }}>
                  <Form.Item
                    label="用户名"
                    name="username"
                    rules={[
                      { required: true, message: '请输入用户名' },
                      { max: 50, message: '用户名不能超过50个字符' }
                    ]}
                  >
                    <Input 
                      placeholder="请输入用户名" 
                      disabled={!!values?.id}
                    />
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
                      { type: 'email', message: '请输入有效的邮箱地址' },
                      { max: 100, message: '邮箱不能超过100个字符' }
                    ]}
                  >
                    <Input placeholder="请输入邮箱" />
                  </Form.Item>

                  <Form.Item
                    label="手机号"
                    name="phone"
                    rules={[
                      { pattern: /^1[3-9]\d{9}$/, message: '请输入有效的手机号' }
                    ]}
                  >
                    <Input placeholder="请输入手机号" />
                  </Form.Item>

                  {(!values || showPasswordField) && (
                    <Form.Item
                      label="密码"
                      name="password"
                      rules={[
                        { required: !values, message: '请输入密码' },
                        { min: 6, message: '密码长度不能小于6个字符' },
                        { max: 20, message: '密码长度不能超过20个字符' }
                      ]}
                    >
                      <Input.Password placeholder="请输入密码" />
                    </Form.Item>
                  )}

                  {values && !showPasswordField && (
                    <Button type="link" onClick={() => setShowPasswordField(true)}>
                      修改密码
                    </Button>
                  )}
                </div>

                <div style={{ 
                  width: '200px',
                  display: 'flex',
                  flexDirection: 'column',
                  alignItems: 'center'
                }}>
                  <Form.Item
                    label="头像"
                    name="avatar"
                  >
                    <Upload
                      name="avatar"
                      listType="picture-card"
                      showUploadList={false}
                      beforeUpload={beforeUpload}
                      onChange={handleChange}
                      customRequest={({ file, onSuccess }) => {
                        setTimeout(() => {
                          onSuccess?.("ok");
                        }, 0);
                      }}
                    >
                      {tempAvatar ? (
                        <img 
                          src={tempAvatar} 
                          alt="avatar" 
                          style={{ width: '100%', height: '100%', objectFit: 'cover' }} 
                        />
                      ) : (
                        <div>
                          <PlusOutlined />
                          <div style={{ marginTop: 8 }}>上传</div>
                        </div>
                      )}
                    </Upload>
                  </Form.Item>
                </div>
              </div>
            </div>
          </Panel>

          <Panel 
            header="高级属性" 
            key="permissionInfo"
          >
            <div style={{ padding: '8px 16px' }}>
              <Form.Item
                label="状态"
                name="status"
                initialValue={STATUS.OPEN}
              >
                <Select>
                  <Select.Option value={STATUS.OPEN}>启用</Select.Option>
                  <Select.Option value={STATUS.CLOSE}>禁用</Select.Option>
                </Select>
              </Form.Item>

              <Form.Item
                label="角色"
                name="roles"
              >
                <Select
                  mode="multiple"
                  placeholder="请选择角色"
                  onChange={handleRoleChange}
                  value={selectedRoles}
                  loading={loading}
                  optionFilterProp="label"
                  showSearch
                  style={{ width: '100%' }}
                >
                  {roles.map((role) => (
                    <Select.Option 
                      key={role.id} 
                      value={role.id?.toString()}
                      label={role.name}
                    >
                      {role.name}
                    </Select.Option>
                  ))}
                </Select>
              </Form.Item>

              {isAdmin && (
                <Form.Item
                  label="租户"
                  name="tenantId"
                >
                  <Select
                    placeholder="请选择租户"
                    onChange={handleTenantChange}
                    value={selectedTenant}
                    loading={loading}
                    optionFilterProp="label"
                    showSearch
                    style={{ width: '100%' }}
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

              <Form.Item
                label="部门"
                name="departmentId"
                rules={[
                  { required: true, message: '请选择部门' }
                ]}
              >
                <Select
                  labelInValue
                  options={departmentOptions}
                  value={departmentValue}
                  onChange={val => {
                    form.setFieldsValue({ departmentId: val });
                    setSelectedDepartment(val.value);
                  }}
                  loading={loading}
                  showSearch
                  style={{ width: '100%' }}
                  disabled={departments.length === 0}
                  placeholder="请选择部门"
                />
              </Form.Item>
            </div>
          </Panel>
        </Collapse>
      </Form>
      <div style={{ 
        position: 'absolute',
        bottom: 0,
        width: '100%',
        borderTop: '1px solid #f0f0f0',
        padding: '10px 16px',
        textAlign: 'right',
        left: 0,
        background: '#fff',
        borderRadius: '0 0 2px 2px',
      }}>
        <Button onClick={onCancel}>取消</Button>
        <Button
          type="primary"
          onClick={() => form.submit()}
          style={{ marginLeft: 8 }}
        >
          {values?.id ? '更新' : '确定'}
        </Button>
      </div>
    </div>
  );
};

export default UserForm; 
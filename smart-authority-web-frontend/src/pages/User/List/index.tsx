import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import { Button, message, Popconfirm, Drawer, Tooltip, Switch, Tag, Modal, Space } from 'antd';
import { useRef, useState, useEffect } from 'react';
import { useModel } from '@umijs/max';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { pageUsers, deleteUser, updateUser, getUserById } from '@/services/SmartAuthority/UserController';
import { getUserRoles } from '@/services/SmartAuthority/RoleController';
import type { API } from '@/services/SmartAuthority/typings';
import UserForm from './components/UserForm';
import './index.less';

const STATUS = {
  OPEN: 'open',
  CLOSE: 'close'
} as const;

const UserList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [drawerVisible, setDrawerVisible] = useState(false);
  const [roleModalVisible, setRoleModalVisible] = useState(false);
  const [currentUser, setCurrentUser] = useState<API.UserResp>();
  const [currentRoles, setCurrentRoles] = useState<API.RoleResp[]>([]);
  const [drawerTitle, setDrawerTitle] = useState('');
  const [isAdmin, setIsAdmin] = useState(false);
  const [userTenantId, setUserTenantId] = useState<number | undefined>(undefined);
  const { initialState } = useModel('@@initialState');
  const [tableLoading, setTableLoading] = useState(true);
  const [isInitialized, setIsInitialized] = useState(false);

  useEffect(() => {
    if (initialState?.currentUser) {
      const isAdminUser = initialState.currentUser.isAdmin === 'admin';
      const tenantId = initialState.currentUser.tenantId;
      
      setIsAdmin(isAdminUser);
      setUserTenantId(tenantId ? Number(tenantId) : undefined);
      setIsInitialized(true);
      setTableLoading(false);
    }
  }, [initialState?.currentUser]);

  const handleAdd = () => {
    setCurrentUser(undefined);
    setDrawerTitle('新建用户');
    setDrawerVisible(true);
  };

  const handleEdit = async (record: API.UserResp) => {
    if (record.isAdmin === 'admin') {
      message.warning('超级管理员用户不能被修改');
      return;
    }
    try {
      const res = await getUserById({ id: record.id! });
      if (res.code === 200 && res.data) {
        setCurrentUser(res.data);
        setDrawerTitle('编辑用户');
        setDrawerVisible(true);
      } else {
        message.error('获取用户详情失败');
      }
    } catch (error) {
      message.error('获取用户详情失败');
    }
  };

  const handleDelete = async (id: number, isAdmin?: string) => {
    try {
      if (isAdmin === 'admin') {
        message.warning('超级管理员用户不能被删除');
        return;
      }
      const result = await deleteUser({ id: id }, {});
      if (result.code !== 200) {
        message.error(result.chMessage || '删除失败');
        return;
      }
      message.success('删除成功');
      if (actionRef.current) {
        await actionRef.current.reload();
      }
    } catch (error) {
      console.error('删除用户失败:', error);
      message.error('删除失败');
    }
  };

  const handleStatusChange = async (record: API.UserResp, checked: boolean) => {
    try {
      if (record.isAdmin === 'admin') {
        message.warning('超级管理员用户状态不能被修改');
        return;
      }
      const updateData: API.UserUpdateReq = {
        id: record.id,
        username: record.username || '',
        status: checked ? STATUS.OPEN : STATUS.CLOSE,
      };

      const result = await updateUser(
        { id: record.id! },
        updateData,
        {}
      );
      if (result.code !== 200) {
        message.error(result.chMessage || '状态更新失败');
        return;
      }
      message.success('状态更新成功');
      if (actionRef.current) {
        await actionRef.current.reload();
      }
    } catch (error) {
      console.error('状态更新失败:', error);
      message.error('状态更新失败');
    }
  };

  const handleDrawerClose = () => {
    setDrawerVisible(false);
    setCurrentUser(undefined);
  };

  const handleFormSubmit = () => {
    setDrawerVisible(false);
    actionRef.current?.reload();
  };

  const handleRoleClick = async (record: API.UserResp) => {
    try {
      const result = await getUserRoles({ userId: record.id! });
      if (result.code === 200 && result.data) {
        setCurrentRoles(result.data);
        setRoleModalVisible(true);
      } else {
        message.error('获取用户角色失败');
      }
    } catch (error) {
      console.error('获取用户角色失败:', error);
      message.error('获取用户角色失败');
    }
  };

  const columns: ProColumns<API.UserResp>[] = [
    {
      title: '用户名',
      dataIndex: 'username',
      ellipsis: true,
    },
    {
      title: '昵称',
      dataIndex: 'name',
      ellipsis: true,
      search: {
        transform: (value) => ({ name: value }),
      },
      fieldProps: {
        placeholder: '请输入昵称',
      },
    },
    {
      title: '邮箱',
      dataIndex: 'email',
      ellipsis: true,
    },
    {
      title: '手机号',
      dataIndex: 'phone',
      ellipsis: true,
    },
    {
      title: '部门',
      dataIndex: 'departmentName',
      ellipsis: true,
      search: {
        transform: (value) => ({ departmentName: value }),
      },
      fieldProps: {
        placeholder: '请输入部门名称',
      },
    },
    ...(isAdmin ? [{
      title: '租户',
      dataIndex: 'tenantName',
      ellipsis: true,
    }] : []),
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'select',
      valueEnum: {
        [STATUS.CLOSE]: { text: '禁用', status: 'Error' },
        [STATUS.OPEN]: { text: '启用', status: 'Success' },
      },
      render: (_, record) => (
        <Switch
          checked={record.status === STATUS.OPEN}
          checkedChildren="启用"
          unCheckedChildren="禁用"
          onChange={(checked) => handleStatusChange(record, checked)}
          disabled={record.isAdmin === 'admin'}
          style={{
            backgroundColor: record.status === STATUS.OPEN ? '#1677ff' : '#ff4d4f',
          }}
        />
      ),
    },
    {
      title: '角色数量',
      dataIndex: 'roles',
      ellipsis: true,
      search: false,
      render: (_, record) => (
        <Button
          type="link"
          onClick={() => handleRoleClick(record)}
        >
          <Tag color="blue">{record.roles?.length || 0}</Tag>
        </Button>
      ),
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
      search: false,
    },
    {
      title: '操作',
      valueType: 'option',
      render: (_, record) => (
        <Space size={8}>
          <Tooltip title={record.isAdmin === 'admin' ? '超级管理员不可编辑' : '编辑'}>
            <Button
              type="link"
              size="small"
              icon={<EditOutlined style={{ fontSize: '14px' }} />}
              onClick={() => handleEdit(record)}
              disabled={record.isAdmin === 'admin'}
            />
          </Tooltip>
          <Tooltip title={record.isAdmin === 'admin' ? '超级管理员不可删除' : '删除'}>
            <Popconfirm
              title="确定删除该用户吗？"
              onConfirm={() => handleDelete(record.id!, record.isAdmin)}
              disabled={record.isAdmin === 'admin'}
            >
              <Button
                type="link"
                danger
                size="small"
                icon={<DeleteOutlined style={{ fontSize: '14px' }} />}
                disabled={record.isAdmin === 'admin'}
              />
            </Popconfirm>
          </Tooltip>
        </Space>
      ),
    },
  ];

  return (
    <PageContainer className="userList">
      <ProTable<API.UserResp>
        headerTitle="用户列表"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={handleAdd}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request={async (params) => {
          const { current, pageSize, ...restParams } = params;
          try {
            // 如果还没有初始化完成，返回空数据
            if (!isInitialized) {
              return {
                data: [],
                success: true,
                total: 0,
              };
            }

            // 如果非管理员但没有租户ID，则返回空数据
            if (!isAdmin && !userTenantId) {
              return {
                data: [],
                success: true,
                total: 0,
              };
            }

            // 如果不是管理员，添加租户ID筛选
            const requestParams = {
              ...restParams,
              current: current,
              size: pageSize,
              tenantId: !isAdmin ? userTenantId : undefined,
            };
            
            const result = await pageUsers({ req: requestParams });
            if (result.code === 200 && result.data) {
              return {
                data: result.data.records || [],
                success: true,
                total: result.data.total || 0,
              };
            }
            return {
              data: [],
              success: false,
              total: 0,
            };
          } catch (error) {
            console.error('获取用户列表失败:', error);
            message.error('获取用户列表失败');
            return {
              data: [],
              success: false,
              total: 0,
            };
          }
        }}
        columns={columns}
        loading={tableLoading}
      />

      <Drawer
        title={drawerTitle}
        width={600}
        onClose={handleDrawerClose}
        open={drawerVisible}
        destroyOnClose
      >
        <UserForm
          values={currentUser}
          onSubmit={handleFormSubmit}
          onCancel={handleDrawerClose}
        />
      </Drawer>

      <Modal
        title="用户角色"
        open={roleModalVisible}
        onCancel={() => setRoleModalVisible(false)}
        footer={null}
      >
        {currentRoles.map((role) => (
          <Tag key={role.id} color="blue">
            {role.name}
          </Tag>
        ))}
      </Modal>
    </PageContainer>
  );
};

export default UserList;
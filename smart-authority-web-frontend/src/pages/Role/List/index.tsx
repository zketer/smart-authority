import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import { Button, message, Popconfirm, Drawer, Tooltip, Space, Popover, Tag } from 'antd';
import { useRef, useState, useEffect } from 'react';
import { useModel } from '@umijs/max';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { getRoles, deleteRole } from '@/services/SmartAuthority/RoleController';
import type { API } from '@/services/SmartAuthority/typings';
import RoleForm from './components/RoleForm';
import './index.less';

const RoleList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [drawerVisible, setDrawerVisible] = useState(false);
  const [currentRole, setCurrentRole] = useState<API.RoleResp>();
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
    setCurrentRole(undefined);
    setDrawerTitle('新建角色');
    setDrawerVisible(true);
  };

  const handleEdit = (record: API.RoleResp) => {
    setCurrentRole(record);
    setDrawerTitle('编辑角色');
    setDrawerVisible(true);
  };

  const handleDelete = async (id: number) => {
    try {
      const result = await deleteRole({ id });
      if (result.code === 200) {
        message.success('删除成功');
        actionRef.current?.reload();
      } else {
        message.error(result.message || '删除失败');
      }
    } catch (error) {
      console.error('删除角色失败:', error);
      message.error('删除失败');
    }
  };

  const handleDrawerClose = () => {
    setDrawerVisible(false);
    setCurrentRole(undefined);
  };

  const handleFormSubmit = () => {
    setDrawerVisible(false);
    actionRef.current?.reload();
  };

  const columns: ProColumns<API.RoleResp>[] = [
    {
      title: '角色名称',
      dataIndex: 'name',
      ellipsis: true,
    },
    {
      title: '角色描述',
      dataIndex: 'description',
      ellipsis: true,
    },
    ...(isAdmin ? [{
      title: '租户',
      dataIndex: 'tenantName',
      ellipsis: true,
    }] : []),
    {
      title: '权限数量',
      dataIndex: 'permissions',
      render: (_, record) => {
        const permissionCount =  <Tag color="blue">{record.permissions?.length || 0}</Tag>;
        const permissionList = record.permissions?.map((p: API.PermissionResp) => (
          <Tag key={p.id} color="blue">{p.name}</Tag>
        )) || [];

        return (
          <Popover
            title="权限列表"
            content={
              <div style={{ maxWidth: 300, maxHeight: 400, overflow: 'auto' }}>
                {permissionList.length > 0 ? permissionList : '暂无权限'}
              </div>
            }
            trigger="hover"
          >
            <span style={{ cursor: 'pointer', color: '#1890ff' }}>
              {permissionCount}
            </span>
          </Popover>
        );
      },
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
      sorter: true,
      hideInSearch: true,
    },
    {
      title: '操作',
      valueType: 'option',
      render: (_, record) => [
        <Tooltip key="edit" title="编辑">
          <Button
            type="link"
            icon={<EditOutlined />}
            onClick={() => handleEdit(record)}
          />
        </Tooltip>,
        <Tooltip key="delete" title="删除">
          <Popconfirm
            title="确定删除该角色吗？"
            onConfirm={() => handleDelete(record.id!)}
          >
            <Button
              type="link"
              danger
              icon={<DeleteOutlined />}
            />
          </Popconfirm>
        </Tooltip>,
      ],
    },
  ];

  return (
    <PageContainer className="roleList">
      <ProTable<API.RoleResp>
        headerTitle="角色列表"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            key="add"
            type="primary"
            onClick={handleAdd}
            icon={<PlusOutlined />}
          >
            新建
          </Button>,
        ]}
        request={async (params) => {
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

            const { current, pageSize, ...rest } = params;
            // 如果不是管理员，添加租户ID筛选
            const requestParams = {
              current,
              size: pageSize,
              ...rest,
              tenantId: !isAdmin ? userTenantId : undefined,
            };
            
            const result = await getRoles({
              req: requestParams,
            });

            if (result.code !== 200) {
              message.error(result.message || '获取角色列表失败');
              return {
                data: [],
                success: false,
                total: 0,
              };
            }

            const pageData = result.data;
            return {
              data: pageData?.records || [],
              success: true,
              total: pageData?.total || 0,
            };
          } catch (error) {
            console.error('获取角色列表失败:', error);
            message.error('获取角色列表失败');
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
        <RoleForm
          role={currentRole}
          onSuccess={handleFormSubmit}
          onCancel={handleDrawerClose}
        />
      </Drawer>
    </PageContainer>
  );
};

export default RoleList;
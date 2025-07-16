import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import { Button, message, Popconfirm, Drawer, Tooltip, Space, Tag, Tree } from 'antd';
import { useRef, useState, useEffect } from 'react';
import { useModel } from '@umijs/max';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { deletePermission, pagePermissions } from '@/services/SmartAuthority/PermissionController';
import type { API } from '@/services/SmartAuthority/typings';
import PermissionForm from './components/PermissionForm';
import './index.less';

const PermissionList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [drawerVisible, setDrawerVisible] = useState(false);
  const [currentPermission, setCurrentPermission] = useState<API.Permission>();
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
    setCurrentPermission(undefined);
    setDrawerTitle('新建权限');
    setDrawerVisible(true);
  };

  const handleAddChild = (record: API.Permission) => {
    setCurrentPermission({
      ...record,
      id: undefined,
      parentId: record.id,
    });
    setDrawerTitle(`新建${record.name}的子权限`);
    setDrawerVisible(true);
  };

  const handleEdit = (record: API.Permission) => {
    setCurrentPermission(record);
    setDrawerTitle('编辑权限');
    setDrawerVisible(true);
  };

  const handleDelete = async (id: number) => {
    try {
      const result = await deletePermission({ id });
      if (result.code !== 200) {
        message.error(result.chMessage || '删除失败');
        return;
      }
      message.success('删除成功');
      if (actionRef.current) {
        await actionRef.current.reload();
      }
    } catch (error) {
      console.error('删除权限失败:', error);
      message.error('删除失败');
    }
  };

  const handleDrawerClose = () => {
    setDrawerVisible(false);
    setCurrentPermission(undefined);
  };

  const handleFormSubmit = () => {
    setDrawerVisible(false);
    actionRef.current?.reload();
  };

  const buildTreeData = (permissions: API.Permission[], parentId: number = 0): API.Permission[] => {
    return permissions
      .filter(item => item.parentId === parentId)
      .map(item => ({
        ...item,
        children: buildTreeData(permissions, item.id),
      }));
  };

  const columns: ProColumns<API.Permission>[] = [
    {
      title: '权限名称',
      dataIndex: 'name',
      ellipsis: true,
      render: (_, record) => {
        const typeMap = {
          1: { color: 'success', text: '菜单' },
          2: { color: 'processing', text: '按钮' },
          3: { color: 'warning', text: 'API' },
        };
        const { color, text } = typeMap[record.type as keyof typeof typeMap] || { color: 'default', text: '未知' };
        return (
          <Space>
            <span>{record.name}</span>
            <Tag color={color}>{text}</Tag>
          </Space>
        );
      },
    },
    {
      title: '权限编码',
      dataIndex: 'code',
      ellipsis: true,
    },
    {
      title: '权限路径',
      dataIndex: 'path',
      ellipsis: true,
    },
    {
      title: '描述',
      dataIndex: 'description',
      ellipsis: true,
      search: false,
    },
    ...(isAdmin ? [{
      title: '租户',
      dataIndex: ['tenantResp', 'name'],
      ellipsis: true,
      search: false,
    }] : []),
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
          <Tooltip title="新建子权限">
            <Button
              type="link"
              size="small"
              icon={<PlusOutlined style={{ fontSize: '14px' }} />}
              onClick={() => handleAddChild(record)}
            />
          </Tooltip>
          <Tooltip title="编辑">
            <Button
              type="link"
              size="small"
              icon={<EditOutlined style={{ fontSize: '14px' }} />}
              onClick={() => handleEdit(record)}
            />
          </Tooltip>
          <Tooltip title="删除">
            <Popconfirm
              title="确定删除该权限吗？"
              onConfirm={() => handleDelete(record.id!)}
            >
              <Button
                type="link"
                danger
                size="small"
                icon={<DeleteOutlined style={{ fontSize: '14px' }} />}
              />
            </Popconfirm>
          </Tooltip>
        </Space>
      ),
    },
  ];

  if (!isInitialized) {
    return null;
  }

  return (
    <PageContainer className="permissionList">
      <ProTable<API.Permission>
        headerTitle="权限列表"
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
            const { current, pageSize, ...rest } = params;
            // 准备请求参数，根据用户角色决定是否传递tenantId
            const requestParams: API.pagePermissionsParams = {
              req: {
                current,
                size: -1, // 设置为-1以获取所有权限
                ...rest,
                ...(isAdmin ? {} : { tenantId: userTenantId }),
              },
            };
            
            const result = await pagePermissions(requestParams);

            if (result.code !== 200) {
              message.error(result.chMessage || '获取权限列表失败');
              return {
                data: [],
                success: false,
                total: 0,
              };
            }

            const pageData = result.data;
            const records = pageData?.records || [];
            const treeData = buildTreeData(records);
            
            return {
              data: treeData,
              success: true,
              total: pageData?.total || 0,
            };
          } catch (error) {
            console.error('获取权限列表失败:', error);
            message.error('获取权限列表失败');
            return {
              data: [],
              success: false,
              total: 0,
            };
          }
        }}
        columns={columns}
        pagination={false}
        search={false}
        options={false}
        defaultExpandAllRows={true}
      />

      <Drawer
        title={drawerTitle}
        placement="right"
        width={600}
        onClose={handleDrawerClose}
        open={drawerVisible}
        destroyOnClose
        footer={
          <div style={{ textAlign: 'right' }}>
            <Space>
              <Button onClick={handleDrawerClose}>取消</Button>
              <Button type="primary" onClick={() => {
                const formRef = document.querySelector('form');
                if (formRef) {
                  formRef.dispatchEvent(new Event('submit', { bubbles: true }));
                }
              }}>
                {currentPermission?.id ? '更新' : '确定'}
              </Button>
            </Space>
          </div>
        }
      >
        <PermissionForm
          permission={currentPermission}
          onSuccess={handleFormSubmit}
          onCancel={handleDrawerClose}
          isAdmin={isAdmin}
          userTenantId={userTenantId}
        />
      </Drawer>
    </PageContainer>
  );
};

export default PermissionList;
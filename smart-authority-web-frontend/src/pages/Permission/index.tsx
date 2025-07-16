import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import { Button, message, Popconfirm, Drawer, Tooltip, Space, Tag } from 'antd';
import { useRef, useState } from 'react';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { deletePermission, pagePermissions } from '@/services/SmartAuthority/PermissionController';
import type { API } from '@/services/SmartAuthority/typings';
import PermissionForm from './components/PermissionForm';

const PermissionList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [drawerVisible, setDrawerVisible] = useState(false);
  const [currentPermission, setCurrentPermission] = useState<API.PermissionResp>();
  const [drawerTitle, setDrawerTitle] = useState('');

  const handleAdd = () => {
    setCurrentPermission(undefined);
    setDrawerTitle('新建权限');
    setDrawerVisible(true);
  };

  const handleAddChild = (record: API.PermissionResp) => {
    setCurrentPermission({
      ...record,
      id: undefined,
      parentId: record.id,
    });
    setDrawerTitle(`新建${record.name}的子权限`);
    setDrawerVisible(true);
  };

  const handleEdit = (record: API.PermissionResp) => {
    setCurrentPermission(record);
    setDrawerTitle('编辑权限');
    setDrawerVisible(true);
  };

  const handleDelete = async (id: number) => {
    try {
      const result = await deletePermission({ id });
      if (result.code === 1205) {
        message.error('该权限存在子权限，无法删除');
        return;
      }
      if (result.code !== 200) {
        message.error(result.message || '删除失败');
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
    setCurrentPermission(undefined);
    if (actionRef.current) {
      actionRef.current.reload();
    }
  };

  const columns: ProColumns<API.PermissionResp>[] = [
    {
      title: '权限名称',
      dataIndex: 'name',
      width: 200,
    },
    {
      title: '权限编码',
      dataIndex: 'code',
      width: 200,
    },
    {
      title: '权限类型',
      dataIndex: 'type',
      width: 100,
      valueEnum: {
        1: { text: '菜单' },
        2: { text: '按钮' },
        3: { text: 'API' },
      },
    },
    {
      title: '路径',
      dataIndex: 'path',
      width: 200,
    },
    {
      title: '描述',
      dataIndex: 'description',
      width: 200,
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

  return (
    <PageContainer>
      <ProTable<API.PermissionResp>
        headerTitle="权限列表"
        actionRef={actionRef}
        rowKey="id"
        search={false}
        toolBarRender={() => [
          <Button key="add" type="primary" onClick={handleAdd}>
            新建权限
          </Button>,
        ]}
        request={async (params) => {
          const { current, pageSize, ...rest } = params;
          const result = await pagePermissions({
            req: {
              current,
              size: pageSize,
              ...rest,
            },
          });
          return {
            data: result.data?.records || [],
            success: result.code === 200,
            total: result.data?.total || 0,
          };
        }}
        columns={columns}
      />
      <Drawer
        title={drawerTitle}
        width={600}
        open={drawerVisible}
        onClose={handleDrawerClose}
        destroyOnClose
      >
        <PermissionForm
          permission={currentPermission}
          onSuccess={handleFormSubmit}
          onCancel={handleDrawerClose}
        />
      </Drawer>
    </PageContainer>
  );
};

export default PermissionList; 
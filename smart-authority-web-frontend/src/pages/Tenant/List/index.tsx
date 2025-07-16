import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import { Button, message, Popconfirm, Drawer, Switch, Tag, Tooltip } from 'antd';
import { useRef, useState, useEffect } from 'react';
import { useModel, useAccess } from '@umijs/max';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { deleteTenant, pageTenants, updateTenant } from '@/services/SmartAuthority/TenantController';
import type { API } from '@/services/SmartAuthority/typings';
import TenantForm from './components/TenantForm';
import { history } from '@umijs/max';

// 状态常量
const STATUS = {
  OPEN: 'open',
  CLOSE: 'close'
} as const;

// 默认租户常量
const IS_DEFAULT = {
  DEFAULT: 'default',
  NOT_DEFAULT: 'not default'
} as const;

const TenantList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [drawerVisible, setDrawerVisible] = useState(false);
  const [currentTenant, setCurrentTenant] = useState<API.TenantResp>();
  const [drawerTitle, setDrawerTitle] = useState('');
  const { initialState } = useModel('@@initialState');
  const access = useAccess();

  // Check access control
  useEffect(() => {
    if (!access.canAccessTenant) {
      message.error('您没有权限访问此页面');
      history.push('/dashboard');
    }
  }, [access]);

  const handleAdd = () => {
    setCurrentTenant(undefined);
    setDrawerTitle('新建租户');
    setDrawerVisible(true);
  };

  const handleEdit = (record: API.TenantResp) => {
    setCurrentTenant(record);
    setDrawerTitle('编辑租户');
    setDrawerVisible(true);
  };

  const handleDelete = async (id: number) => {
    try {
      const result = await deleteTenant({ id });
      if (result.code !== 200) {
        message.error(result.message || '删除失败');
        return;
      }
      message.success('删除成功');
      if (actionRef.current) {
        await actionRef.current.reload();
      }
    } catch (error) {
      console.error('删除租户失败:', error);
      message.error('删除失败');
    }
  };

  const handleStatusChange = async (record: API.TenantResp, checked: boolean) => {
    if (!record.id) {
      message.error('租户ID不存在');
      return;
    }

    try {
      const result = await updateTenant(
        { id: record.id },
        {
          ...record,
          status: checked ? STATUS.OPEN : STATUS.CLOSE,
        }
      );

      if (result.code !== 200) {
        message.error(result.message || '更新状态失败');
        return;
      }

      message.success('更新状态成功');
      if (actionRef.current) {
        await actionRef.current.reload();
      }
    } catch (error) {
      console.error('更新租户状态失败:', error);
      message.error('更新状态失败');
    }
  };

  const handleDrawerClose = () => {
    setDrawerVisible(false);
    setCurrentTenant(undefined);
  };

  const handleFormSubmit = async () => {
    handleDrawerClose();
    if (actionRef.current) {
      await actionRef.current.reload();
    }
  };

  const columns: ProColumns<API.TenantResp>[] = [
    {
      title: '租户名称',
      dataIndex: 'name',
      ellipsis: true,
    },
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
          onChange={(checked) => {
            if (record && record.id) {
              handleStatusChange(record, checked);
            } else {
              message.error('租户数据不完整');
            }
          }}
          disabled={record.isDefault === IS_DEFAULT.DEFAULT}
          style={{
            backgroundColor: record.status === STATUS.OPEN ? '#1677ff' : '#ff4d4f',
          }}
        />
      ),
    },
    {
      title: '默认租户',
      dataIndex: 'isDefault',
      valueType: 'select',
      valueEnum: {
        [IS_DEFAULT.DEFAULT]: { text: '默认租户', status: 'Success' },
        [IS_DEFAULT.NOT_DEFAULT]: { text: '普通租户', status: 'Default' },
      },
      render: (_, record) => (
        <Tag color={record.isDefault === IS_DEFAULT.DEFAULT ? 'success' : 'default'}>
          {record.isDefault === IS_DEFAULT.DEFAULT ? '默认租户' : '普通租户'}
        </Tag>
      ),
    },
    {
      title: '描述',
      dataIndex: 'description',
      ellipsis: true,
      search: false,
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
      render: (_, record) => [
        <Tooltip key="edit" title="编辑">
          <Button
            type="link"
            icon={<EditOutlined />}
            onClick={() => handleEdit(record)}
            disabled={record.isDefault === IS_DEFAULT.DEFAULT}
          />
        </Tooltip>,
        <Tooltip key="delete" title="删除">
          <Popconfirm
            title="确定删除该租户吗？"
            onConfirm={() => handleDelete(record.id!)}
            disabled={record.isDefault === IS_DEFAULT.DEFAULT}
          >
            <Button
              type="link"
              danger
              icon={<DeleteOutlined />}
              disabled={record.isDefault === IS_DEFAULT.DEFAULT}
            />
          </Popconfirm>
        </Tooltip>,
      ],
    },
  ];

  if (!access.canAccessTenant) {
    return null;
  }

  return (
    <PageContainer>
      <ProTable<API.TenantResp>
        headerTitle="租户列表"
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
            const result = await pageTenants({
              req: {
                current,
                size: pageSize,
                ...rest,
              }
            });

            if (result.code !== 200) {
              message.error(result.message || '获取租户列表失败');
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
            console.error('获取租户列表失败:', error);
            message.error('获取租户列表失败');
            return {
              data: [],
              success: false,
              total: 0,
            };
          }
        }}
        columns={columns}
      />

      <Drawer
        title={drawerTitle}
        placement="right"
        width={600}
        onClose={handleDrawerClose}
        open={drawerVisible}
        destroyOnClose
      >
        <TenantForm
          tenant={currentTenant}
          onSuccess={handleFormSubmit}
          onCancel={handleDrawerClose}
        />
      </Drawer>
    </PageContainer>
  );
};

export default TenantList;

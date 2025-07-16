import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { PageContainer, ProTable } from '@ant-design/pro-components';
import { Button, message, Popconfirm, Drawer, Tooltip, Space, Tag } from 'antd';
import { useRef, useState, useEffect } from 'react';
import { useModel } from '@umijs/max';
import type { ActionType, ProColumns } from '@ant-design/pro-components';
import { pageDepartments, deleteDepartment } from '@/services/SmartAuthority/DepartmentController';
import type { API } from '@/services/SmartAuthority/typings';
import DepartmentForm from './components/DepartmentForm';
import { Form } from 'antd';

const DepartmentList: React.FC = () => {
  const actionRef = useRef<ActionType>();
  const [drawerVisible, setDrawerVisible] = useState(false);
  const [currentDepartment, setCurrentDepartment] = useState<API.DepartmentResp>();
  const [drawerTitle, setDrawerTitle] = useState('');
  const [form] = Form.useForm();
  const [expandedRowKeys, setExpandedRowKeys] = useState<React.Key[]>([]);
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
    setCurrentDepartment(undefined);
    setDrawerTitle('新建部门');
    setDrawerVisible(true);
  };

  const handleAddChild = (record: API.DepartmentResp) => {
    setCurrentDepartment({
      ...record,
      id: undefined,
      parentId: record.id,
    });
    setDrawerTitle(`新建${record.name}的子部门`);
    setDrawerVisible(true);
    // 展开父部门
    setExpandedRowKeys(prev => [...prev, record.id!]);
  };

  const handleEdit = (record: API.DepartmentResp) => {
    setCurrentDepartment(record);
    setDrawerTitle('编辑部门');
    setDrawerVisible(true);
  };

  const handleDelete = async (id: number) => {
    try {
      const result = await deleteDepartment({ id });
      if (result.code !== 200) {
        message.error(result.message || '删除失败');
        return;
      }
      message.success('删除成功');
      if (actionRef.current) {
        await actionRef.current.reload();
      }
    } catch (error) {
      console.error('删除部门失败:', error);
      message.error('删除失败');
    }
  };

  const handleDrawerClose = () => {
    setDrawerVisible(false);
    setCurrentDepartment(undefined);
  };

  const handleFormSubmit = () => {
    setDrawerVisible(false);
    actionRef.current?.reload();
  };

  const buildTreeData = (departments: API.DepartmentResp[], parentId: number = 0): API.DepartmentResp[] => {
    return departments
      .filter(item => item.parentId === parentId)
      .map(item => ({
        ...item,
        children: buildTreeData(departments, item.id),
      }));
  };

  const columns: ProColumns<API.DepartmentResp>[] = [
    {
      title: '部门名称',
      dataIndex: 'name',
      ellipsis: true,
      render: (_, record) => (
        <Space>
          <span>{record.name}</span>
          {!record.parentId && <Tag color="success">顶级部门</Tag>}
        </Space>
      ),
    },
    {
      title: '上级部门',
      dataIndex: 'parentName',
      ellipsis: true,
    },
    {
      title: '描述',
      dataIndex: 'description',
      ellipsis: true,
      search: false as const,
    },
    ...(isAdmin ? [{
      title: '租户',
      dataIndex: 'tenantName',
      ellipsis: true,
      search: false as const,
    }] : []),
    {
      title: '创建时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
      search: false as const,
    },
    {
      title: '操作',
      valueType: 'option',
      render: (_, record) => (
        <Space size={8}>
          <Tooltip title="新建子部门">
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
              title="确定删除该部门吗？"
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
      <ProTable<API.DepartmentResp>
        headerTitle="部门列表"
        actionRef={actionRef}
        rowKey="id"
        search={false}
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

            const result = await pageDepartments(requestParams);

            if (result.code !== 200) {
              message.error(result.message || '获取部门列表失败');
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
            console.error('获取部门列表失败:', error);
            message.error('获取部门列表失败');
            return {
              data: [],
              success: false,
              total: 0,
            };
          }
        }}
        columns={columns}
        pagination={false}
        options={false}
        loading={tableLoading}
        expandable={{
          expandedRowKeys,
          onExpandedRowsChange: (expandedRows) => {
            setExpandedRowKeys(expandedRows as React.Key[]);
          },
        }}
      />

      <Drawer
        title={drawerTitle}
        placement="right"
        width={600}
        onClose={handleDrawerClose}
        open={drawerVisible}
        destroyOnClose
      >
        <DepartmentForm
          department={currentDepartment}
          onSuccess={handleFormSubmit}
          onCancel={handleDrawerClose}
        />
      </Drawer>
    </PageContainer>
  );
};

export default DepartmentList; 
import { PageContainer } from '@ant-design/pro-layout';
import { Card, Spin, message, Tag } from 'antd';
import { useParams, history } from 'umi';
import { useEffect, useState } from 'react';
import ProForm, {
  ProFormText,
  ProFormTextArea,
  ProFormTreeSelect,
  ProFormSelect,
} from '@ant-design/pro-form';
import { getPermission, updatePermission, pagePermissions } from '@/services/SmartAuthority/PermissionController';
import type { API } from '@/services/SmartAuthority/typings';

const PermissionDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [loading, setLoading] = useState(false);
  const [permission, setPermission] = useState<API.Permission>();
  const [permissionTree, setPermissionTree] = useState<API.Permission[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const [permissionResult, treeResult] = await Promise.all([
          getPermission({ id: Number(id) }),
          pagePermissions({ current: 1, size: 1000 }),
        ]);

        if (permissionResult.code === 200) {
          setPermission(permissionResult.data);
        } else {
          message.error(permissionResult.message || '获取权限详情失败');
        }

        if (treeResult.code === 200 && treeResult.data?.records) {
          setPermissionTree(treeResult.data.records);
        }
      } catch (error) {
        console.error('获取数据失败:', error);
        message.error('获取数据失败');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  const buildTreeData = (permissions: API.Permission[], parentId: number = 0): API.Permission[] => {
    return permissions
      .filter(item => item.parentId === parentId && item.id !== permission?.id)
      .map(item => ({
        ...item,
        title: item.name,
        value: item.id,
        children: buildTreeData(permissions, item.id),
      }));
  };

  const handleSubmit = async (values: any) => {
    try {
      setLoading(true);
      const result = await updatePermission({ id: Number(id) }, values);
      if (result.code === 200) {
        message.success('更新成功');
        history.push('/permission/list');
      } else {
        message.error(result.message || '更新失败');
      }
    } catch (error) {
      console.error('更新失败:', error);
      message.error('更新失败');
    } finally {
      setLoading(false);
    }
  };

  if (!permission) {
    return null;
  }

  return (
    <PageContainer>
      <Card>
        <Spin spinning={loading}>
          <ProForm
            initialValues={permission}
            onFinish={handleSubmit}
            submitter={{
              searchConfig: {
                submitText: '更新',
              },
              resetButtonProps: {
                style: {
                  display: 'none',
                },
              },
            }}
          >
            <ProFormTreeSelect
              name="parentId"
              label="父级权限"
              placeholder="请选择父级权限"
              rules={[{ required: true, message: '请选择父级权限' }]}
              fieldProps={{
                treeData: [
                  {
                    title: '无',
                    value: 0,
                    children: buildTreeData(permissionTree),
                  },
                ],
                treeDefaultExpandAll: true,
              }}
            />

            <ProFormSelect
              name="type"
              label="权限类型"
              placeholder="请选择权限类型"
              rules={[{ required: true, message: '请选择权限类型' }]}
              options={[
                { label: '菜单', value: 1 },
                { label: '按钮', value: 2 },
                { label: 'API', value: 3 },
              ]}
            />

            <ProFormText
              name="name"
              label="权限名称"
              placeholder="请输入权限名称"
              rules={[
                { required: true, message: '请输入权限名称' },
                { max: 50, message: '权限名称不能超过50个字符' },
              ]}
            />

            <ProFormText
              name="code"
              label="权限编码"
              placeholder="请输入权限编码"
              disabled
              rules={[
                { required: true, message: '请输入权限编码' },
                { max: 50, message: '权限编码不能超过50个字符' },
                { pattern: /^[a-zA-Z0-9_-]+$/, message: '权限编码只能包含字母、数字、下划线和连字符' },
              ]}
            />

            <ProFormText
              name="path"
              label="权限路径"
              placeholder="请输入权限路径"
              rules={[
                { required: true, message: '请输入权限路径' },
                { max: 255, message: '权限路径不能超过255个字符' },
              ]}
            />

            <ProFormTextArea
              name="description"
              label="权限描述"
              placeholder="请输入权限描述"
              rules={[
                { required: true, message: '请输入权限描述' },
                { max: 255, message: '权限描述不能超过255个字符' },
              ]}
            />
          </ProForm>
        </Spin>
      </Card>
    </PageContainer>
  );
};

export default PermissionDetail;
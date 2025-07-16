import React, { useEffect, useState } from 'react';
import { Select, Spin, message } from 'antd';
import { pageTenants } from '@/services/SmartAuthority/TenantController';
import type { API } from '@/services/SmartAuthority/typings';

interface TenantSelectorProps {
  value?: number;
  onChange?: (value: number) => void;
  placeholder?: string;
  style?: React.CSSProperties;
  allowClear?: boolean;
  disabled?: boolean;
}

const TenantSelector: React.FC<TenantSelectorProps> = ({
  value,
  onChange,
  placeholder = '请选择租户',
  style,
  allowClear = true,
  disabled = false,
}) => {
  const [tenants, setTenants] = useState<API.TenantResp[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchTenants = async () => {
      try {
        setLoading(true);
        const result = await pageTenants({ req: { current: 1, size: 100 } });
        if (result.code === 200 && result.data) {
          setTenants(result.data.records || []);
        } else {
          message.error('获取租户列表失败');
        }
      } catch (error) {
        console.error('获取租户列表失败:', error);
        message.error('获取租户列表失败');
      } finally {
        setLoading(false);
      }
    };

    fetchTenants();
  }, []);

  return (
    <Select
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      style={style}
      allowClear={allowClear}
      disabled={disabled}
      loading={loading}
      showSearch
      optionFilterProp="label"
    >
      {tenants.map((tenant) => (
        <Select.Option 
          key={tenant.id} 
          value={tenant.id}
          label={tenant.name}
        >
          {tenant.name}
        </Select.Option>
      ))}
    </Select>
  );
};

export default TenantSelector; 
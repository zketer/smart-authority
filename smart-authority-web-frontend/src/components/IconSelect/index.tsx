import { Select } from 'antd';
import { useEffect, useState } from 'react';
import { getIconList } from '@/services/SmartAuthority/IconController';

interface IconSelectProps {
  value?: string;
  onChange?: (value: string) => void;
}

const IconSelect: React.FC<IconSelectProps> = ({ value, onChange }) => {
  const [icons, setIcons] = useState<{ label: string; value: string }[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchIcons = async () => {
      try {
        setLoading(true);
        const result = await getIconList();
        if (result.code === 200 && result.data) {
          setIcons(result.data.map(icon => ({
            label: icon,
            value: icon,
          })));
        }
      } catch (error) {
        console.error('获取图标列表失败:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchIcons();
  }, []);

  return (
    <Select
      value={value}
      onChange={onChange}
      placeholder="请选择图标"
      loading={loading}
      options={icons}
      showSearch
      allowClear
    />
  );
};

export default IconSelect; 
import React, { useEffect, useState } from 'react';
import { Card, Table } from 'antd';
import { getDepartmentDistribution } from '@/services/SmartAuthority/DashboardController';
import type { API } from '@/services/SmartAuthority/typings';

const DepartmentDistribution: React.FC = () => {
  const [data, setData] = useState<API.ApiResponseStatsResp>();

  useEffect(() => {
    getDepartmentDistribution().then((resp) => {
      if (resp.success) {
        setData(resp);
      }
    });
  }, []);

  const columns = [
    {
      title: '部门',
      dataIndex: 'department',
      key: 'department',
    },
    {
      title: '人数',
      dataIndex: 'count',
      key: 'count',
    },
  ];

  const tableData = data?.data?.data
    ? Object.entries(data.data.data).map(([department, count]) => ({
        key: department,
        department,
        count,
      }))
    : [];

  return (
    <Card title="部门人数分布" style={{ height: '100%' }}>
      <Table
        columns={columns}
        dataSource={tableData}
        pagination={false}
        size="small"
      />
    </Card>
  );
};

export default DepartmentDistribution; 
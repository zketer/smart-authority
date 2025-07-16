import React from 'react';
import { Avatar, Card, Descriptions } from 'antd';
import { useModel } from '@umijs/max';
import styles from './Profile.less';

const Profile: React.FC = () => {
  const { initialState } = useModel('@@initialState');
  const currentUser = initialState?.currentUser;

  if (!currentUser) {
    return null;
  }

  return (
    <div className={styles.profileContainer}>
      <Card>
        <div className={styles.userHeader}>
          <Avatar size={64} src={currentUser.avatar} />
          <h4>{currentUser.name}</h4>
        </div>
        <Descriptions column={1}>
          <Descriptions.Item label="Username">{currentUser.username}</Descriptions.Item>
          <Descriptions.Item label="Email">{currentUser.email}</Descriptions.Item>
          <Descriptions.Item label="Role">{currentUser.role}</Descriptions.Item>
          <Descriptions.Item label="Department">{currentUser.department}</Descriptions.Item>
        </Descriptions>
      </Card>
    </div>
  );
};

export default Profile; 
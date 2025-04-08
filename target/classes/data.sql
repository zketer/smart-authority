-- 使用数据库
USE mypermission;

-- 插入初始租户
INSERT INTO tenant (id, name, code, status, description) VALUES
(1, '系统管理员', 'admin', 1, '系统管理员租户'),
(2, '测试租户', 'test', 1, '测试租户');

-- 插入初始部门
INSERT INTO department (id, tenant_id, name, parent_id, level, sort, status, description) VALUES
(1, 1, '总部', 0, 1, 1, 1, '总部'),
(2, 1, '技术部', 1, 2, 1, 1, '技术部'),
(3, 1, '市场部', 1, 2, 2, 1, '市场部'),
(4, 2, '测试部门', 0, 1, 1, 1, '测试部门');

-- 插入初始用户 (密码都是 123456 的 BCrypt 加密)
INSERT INTO user (id, tenant_id, department_id, username, password, name, email, phone, status) VALUES
(1, 1, 1, 'admin', '$2a$10$IhNoDpkdJ5nryRgP2nD6B.Wl0I8XSRPXxSJ5VpSA/TPQMGFeIwXdO', '系统管理员', 'admin@example.com', '13800000000', 1),
(2, 1, 2, 'tech1', '$2a$10$IhNoDpkdJ5nryRgP2nD6B.Wl0I8XSRPXxSJ5VpSA/TPQMGFeIwXdO', '技术人员1', 'tech1@example.com', '13800000001', 1),
(3, 1, 3, 'market1', '$2a$10$IhNoDpkdJ5nryRgP2nD6B.Wl0I8XSRPXxSJ5VpSA/TPQMGFeIwXdO', '市场人员1', 'market1@example.com', '13800000002', 1),
(4, 2, 4, 'test', '$2a$10$IhNoDpkdJ5nryRgP2nD6B.Wl0I8XSRPXxSJ5VpSA/TPQMGFeIwXdO', '测试用户', 'test@example.com', '13800000003', 1);

-- 插入初始角色
INSERT INTO role (id, tenant_id, name, code, status, description) VALUES
(1, 1, '超级管理员', 'SUPER_ADMIN', 1, '超级管理员角色'),
(2, 1, '普通管理员', 'ADMIN', 1, '普通管理员角色'),
(3, 1, '普通用户', 'USER', 1, '普通用户角色'),
(4, 2, '测试角色', 'TEST_ROLE', 1, '测试角色');

-- 插入初始权限
INSERT INTO permission (id, parent_id, name, code, type, path, icon, component, sort, status) VALUES
-- 菜单
(1, 0, '系统管理', 'system', 1, '/system', 'setting', 'Layout', 1, 1),
(2, 1, '用户管理', 'system:user', 1, '/system/user', 'user', 'system/user/index', 1, 1),
(3, 1, '角色管理', 'system:role', 1, '/system/role', 'team', 'system/role/index', 2, 1),
(4, 1, '权限管理', 'system:permission', 1, '/system/permission', 'key', 'system/permission/index', 3, 1),
(5, 1, '租户管理', 'system:tenant', 1, '/system/tenant', 'apartment', 'system/tenant/index', 4, 1),
(6, 1, '部门管理', 'system:department', 1, '/system/department', 'cluster', 'system/department/index', 5, 1),

-- 用户管理按钮
(11, 2, '用户查询', 'system:user:list', 2, '', '', '', 1, 1),
(12, 2, '用户创建', 'system:user:create', 2, '', '', '', 2, 1),
(13, 2, '用户编辑', 'system:user:update', 2, '', '', '', 3, 1),
(14, 2, '用户删除', 'system:user:delete', 2, '', '', '', 4, 1),

-- 角色管理按钮
(21, 3, '角色查询', 'system:role:list', 2, '', '', '', 1, 1),
(22, 3, '角色创建', 'system:role:create', 2, '', '', '', 2, 1),
(23, 3, '角色编辑', 'system:role:update', 2, '', '', '', 3, 1),
(24, 3, '角色删除', 'system:role:delete', 2, '', '', '', 4, 1),
(25, 3, '角色权限设置', 'system:role:permission', 2, '', '', '', 5, 1),

-- 权限管理按钮
(31, 4, '权限查询', 'system:permission:list', 2, '', '', '', 1, 1),
(32, 4, '权限创建', 'system:permission:create', 2, '', '', '', 2, 1),
(33, 4, '权限编辑', 'system:permission:update', 2, '', '', '', 3, 1),
(34, 4, '权限删除', 'system:permission:delete', 2, '', '', '', 4, 1),

-- 租户管理按钮
(41, 5, '租户查询', 'system:tenant:list', 2, '', '', '', 1, 1),
(42, 5, '租户创建', 'system:tenant:create', 2, '', '', '', 2, 1),
(43, 5, '租户编辑', 'system:tenant:update', 2, '', '', '', 3, 1),
(44, 5, '租户删除', 'system:tenant:delete', 2, '', '', '', 4, 1),

-- 部门管理按钮
(51, 6, '部门查询', 'system:department:list', 2, '', '', '', 1, 1),
(52, 6, '部门创建', 'system:department:create', 2, '', '', '', 2, 1),
(53, 6, '部门编辑', 'system:department:update', 2, '', '', '', 3, 1),
(54, 6, '部门删除', 'system:department:delete', 2, '', '', '', 4, 1),

-- API权限
(101, 0, '用户API', 'api:user', 3, '/api/users/**', '', '', 1, 1),
(102, 0, '角色API', 'api:role', 3, '/api/roles/**', '', '', 2, 1),
(103, 0, '权限API', 'api:permission', 3, '/api/permissions/**', '', '', 3, 1),
(104, 0, '租户API', 'api:tenant', 3, '/api/tenants/**', '', '', 4, 1),
(105, 0, '部门API', 'api:department', 3, '/api/departments/**', '', '', 5, 1);

-- 用户角色关联
INSERT INTO user_role (user_id, role_id, tenant_id) VALUES
(1, 1, 1),  -- admin用户分配超级管理员角色
(2, 3, 1),  -- tech1用户分配普通用户角色
(3, 3, 1),  -- market1用户分配普通用户角色
(4, 4, 2);  -- test用户分配测试角色

-- 角色权限关联 (超级管理员拥有所有权限)
-- 为超级管理员分配所有权限
INSERT INTO role_permission (role_id, permission_id, tenant_id)
SELECT 1, id, 1 FROM permission;

-- 为普通管理员分配部分权限
INSERT INTO role_permission (role_id, permission_id, tenant_id) VALUES
(2, 1, 1), (2, 2, 1), (2, 3, 1), (2, 11, 1), (2, 12, 1), (2, 13, 1), (2, 21, 1), (2, 22, 1), (2, 23, 1),
(2, 101, 1), (2, 102, 1);

-- 为普通用户分配基本权限
INSERT INTO role_permission (role_id, permission_id, tenant_id) VALUES
(3, 1, 1), (3, 2, 1), (3, 11, 1), (3, 101, 1);

-- 为测试角色分配权限
INSERT INTO role_permission (role_id, permission_id, tenant_id) VALUES
(4, 1, 2), (4, 2, 2), (4, 3, 2), (4, 11, 2), (4, 21, 2), (4, 101, 2), (4, 102, 2);
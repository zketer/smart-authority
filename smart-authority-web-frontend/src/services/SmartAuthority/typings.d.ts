declare namespace API {
  type ApiResponseDepartmentResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: DepartmentResp;
    success?: boolean;
  };

  type ApiResponseIPageDepartmentResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: IPageDepartmentResp;
    success?: boolean;
  };

  type ApiResponseIPagePermissionResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: IPagePermissionResp;
    success?: boolean;
  };

  type ApiResponseIPageTenantResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: IPageTenantResp;
    success?: boolean;
  };

  type ApiResponseListPermissionResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: PermissionResp[];
    success?: boolean;
  };

  type ApiResponseListRoleResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: RoleResp[];
    success?: boolean;
  };

  type ApiResponseLoginResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: LoginResp;
    success?: boolean;
  };

  type ApiResponseObject = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: Record<string, any>;
    success?: boolean;
  };

  type ApiResponsePageUserResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: PageUserResp;
    success?: boolean;
  };

  type ApiResponsePermissionResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: PermissionResp;
    success?: boolean;
  };

  type ApiResponseRoleResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: RoleResp;
    success?: boolean;
  };

  type ApiResponseStatsResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: StatsResp;
    success?: boolean;
  };

  type ApiResponseTenantResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: TenantResp;
    success?: boolean;
  };

  type ApiResponseUserActivityResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: UserActivityResp;
    success?: boolean;
  };

  type ApiResponseUserBehaviorResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: UserBehaviorResp;
    success?: boolean;
  };

  type ApiResponseUserGrowthResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: UserGrowthResp;
    success?: boolean;
  };

  type ApiResponseUserResp = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: UserResp;
    success?: boolean;
  };

  type ApiResponseVoid = {
    code?: number;
    chMessage?: string;
    enMessage?: string;
    data?: Record<string, any>;
    success?: boolean;
  };

  type assignPermissionsParams = {
    roleId: number;
  };

  type deleteDepartmentParams = {
    id: number;
  };

  type deletePermissionParams = {
    id: number;
  };

  type deleteRoleParams = {
    id: number;
  };

  type deleteTenantParams = {
    id: number;
  };

  type deleteUserParams = {
    id: number;
  };

  type DepartmentCreateReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 部门名称 */
    name: string;
    /** 父部门ID */
    parentId?: number;
    /** 描述 */
    description?: string;
  };

  type DepartmentQueryReq = {
    /** 当前页 */
    current?: number;
    /** 每页大小 */
    size?: number;
    /** 部门名称 */
    name?: string;
    /** 父部门ID */
    parentId?: number;
    /** 租户ID */
    tenantId?: number;
    /** 租户名称 */
    tenantName?: string;
  };

  type DepartmentResp = {
    /** 唯一ID */
    id?: number;
    /** 租户ID */
    tenantId?: number;
    /** 创建时间 */
    createTime?: string;
    /** 更新时间 */
    updateTime?: string;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 部门名称 */
    name?: string;
    /** 父部门ID */
    parentId?: number;
    /** 父部门名称 */
    parentName?: string;
    /** 租户 */
    tenantName?: string;
    /** 描述 */
    description?: string;
  };

  type DepartmentUpdateReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 部门ID */
    id?: number;
    /** 部门名称 */
    name: string;
    /** 父部门ID */
    parentId?: number;
    /** 描述 */
    description?: string;
  };

  type getDepartmentByIdParams = {
    id: number;
  };

  type getDepartmentStatsParams = {
    tenantId?: number;
  };

  type getPermissionParams = {
    id: number;
  };

  type getPermissionStatsParams = {
    tenantId?: number;
  };

  type getRoleParams = {
    id: number;
  };

  type getRolePermissionsParams = {
    roleId: number;
  };

  type getRolesParams = {
    req: RoleQueryReq;
  };

  type getRoleStatsParams = {
    tenantId?: number;
  };

  type getTenantParams = {
    id: number;
  };

  type getTenantStatsParams = {
    tenantId?: number;
  };

  type getUserActivityParams = {
    tenantId?: number;
  };

  type getUserBehaviorParams = {
    tenantId?: number;
  };

  type getUserByIdParams = {
    id: number;
  };

  type getUserGrowthParams = {
    tenantId?: number;
  };

  type getUserPermissionsParams = {
    userId: number;
  };

  type getUserRolesParams = {
    userId: number;
  };

  type getUserStatsParams = {
    tenantId?: number;
  };

  type IPageDepartmentResp = {
    size?: number;
    total?: number;
    records?: DepartmentResp[];
    current?: number;
    pages?: number;
  };

  type IPagePermissionResp = {
    size?: number;
    total?: number;
    records?: PermissionResp[];
    current?: number;
    pages?: number;
  };

  type IPageTenantResp = {
    size?: number;
    total?: number;
    records?: TenantResp[];
    current?: number;
    pages?: number;
  };

  type LoginReq = {
    /** 用户名 */
    username: string;
    /** 密码 */
    password: string;
  };

  type LoginResp = {
    /** 用户ID */
    id?: number;
    /** 用户名 */
    username?: string;
    /** 姓名 */
    name?: string;
    /** 邮箱 */
    email?: string;
    /** 手机号 */
    phone?: string;
    /** 头像 */
    avatar?: string;
    /** 状态 */
    status?: string;
    /** 是否为管理员 */
    isAdmin?: string;
    /** 访问令牌 */
    accessToken?: string;
    /** 刷新令牌 */
    refreshToken?: string;
    /** 租户ID */
    tenantId?: number;
  };

  type logoutParams = {
    Authorization: string;
  };

  type OrderItem = {
    column?: string;
    asc?: boolean;
  };

  type pageDepartmentsParams = {
    req: DepartmentQueryReq;
  };

  type pagePermissionsParams = {
    req: PermissionQueryReq;
  };

  type pageTenantsParams = {
    req: TenantQueryReq;
  };

  type PageUserResp = {
    records?: UserResp[];
    total?: number;
    size?: number;
    current?: number;
    orders?: OrderItem[];
    optimizeCountSql?: boolean;
    searchCount?: boolean;
    optimizeJoinOfCountSql?: boolean;
    maxLimit?: number;
    countId?: string;
    pages?: number;
  };

  type pageUsersParams = {
    req: UserQueryReq;
  };

  type PermissionCreateReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 父权限ID */
    parentId?: number;
    /** 权限名称 */
    name: string;
    /** 权限编码 */
    code: string;
    /** 权限类型：1-菜单，2-按钮，3-API */
    type: number;
    /** 路径 */
    path?: string;
    /** 描述 */
    description?: string;
  };

  type PermissionQueryReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 当前页 */
    current?: number;
    /** 每页大小 */
    size?: number;
    /** 权限名称 */
    name?: string;
    /** 权限编码 */
    code?: string;
    /** 权限类型：1-菜单，2-按钮，3-API */
    type?: number;
    /** 父权限ID */
    parentId?: number;
  };

  type PermissionResp = {
    /** 唯一ID */
    id?: number;
    /** 租户ID */
    tenantId?: number;
    /** 创建时间 */
    createTime?: string;
    /** 更新时间 */
    updateTime?: string;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 父权限ID */
    parentId?: number;
    /** 父权限名称 */
    parentName?: string;
    /** 权限名称 */
    name?: string;
    /** 权限编码 */
    code?: string;
    /** 权限类型：1-菜单，2-按钮，3-API */
    type?: number;
    /** 路径 */
    path?: string;
    /** 描述 */
    description?: string;
    /** 拥有该权限的角色 */
    roles?: RoleResp[];
    /** 子权限列表 */
    children?: PermissionResp[];
    tenantResp?: TenantResp;
  };

  type PermissionUpdateReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 权限ID */
    id?: number;
    /** 父权限ID */
    parentId?: number;
    /** 权限名称 */
    name: string;
    /** 权限编码 */
    code: string;
    /** 权限类型：1-菜单，2-按钮，3-API */
    type: number;
    /** 路径 */
    path?: string;
    /** 描述 */
    description?: string;
  };

  type RoleCreateReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 角色名称 */
    name: string;
    /** 角色描述 */
    description?: string;
    /** 权限ID列表 */
    permissionIds?: number[];
  };

  type RoleQueryReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 当前页 */
    current?: number;
    /** 每页大小 */
    size?: number;
    /** 角色名称 */
    name?: string;
  };

  type RoleResp = {
    /** 唯一ID */
    id?: number;
    /** 租户ID */
    tenantId?: number;
    /** 创建时间 */
    createTime?: string;
    /** 更新时间 */
    updateTime?: string;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 角色名称 */
    name?: string;
    /** 角色描述 */
    description?: string;
    /** 是否为管理员 */
    isAdmin?: string;
    /** 租户名称 */
    tenantName?: string;
    /** 角色拥有的权限ID列表 */
    permissionIds?: number[];
    /** 角色拥有的权限 */
    permissions?: PermissionResp[];
  };

  type RoleUpdateReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 角色ID */
    id: number;
    /** 角色名称 */
    name: string;
    /** 角色描述 */
    description?: string;
    /** 权限ID列表 */
    permissionIds?: number[];
  };

  type StatsResp = {
    /** 统计数据 */
    data?: Record<string, any>;
  };

  type TenantCreateReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    name: string;
    description?: string;
    status?: string;
    isDefault?: string;
  };

  type TenantQueryReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    current?: number;
    size?: number;
    name?: string;
    code?: string;
    status?: string;
  };

  type TenantResp = {
    /** 唯一ID */
    id?: number;
    /** 租户ID */
    tenantId?: number;
    /** 创建时间 */
    createTime?: string;
    /** 更新时间 */
    updateTime?: string;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    name?: string;
    code?: string;
    description?: string;
    status?: string;
    isDefault?: string;
  };

  type TenantUpdateReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    id?: number;
    name: string;
    code?: string;
    description?: string;
    status: string;
    isDefault?: string;
  };

  type updateDepartmentParams = {
    id: number;
  };

  type updatePermissionParams = {
    id: number;
  };

  type updateRoleParams = {
    id: number;
  };

  type updateTenantParams = {
    id: number;
  };

  type updateUserParams = {
    id: number;
  };

  type UserActivityResp = {
    /** 唯一ID */
    id?: number;
    /** 租户ID */
    tenantId?: number;
    /** 创建时间 */
    createTime?: string;
    /** 更新时间 */
    updateTime?: string;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 日期列表 */
    dates?: string[];
    /** 活跃用户数列表 */
    activeUsers?: number[];
    /** 平均在线时长(分钟)列表 */
    avgOnlineTime?: number[];
  };

  type UserBehaviorResp = {
    /** 唯一ID */
    id?: number;
    /** 租户ID */
    tenantId?: number;
    /** 创建时间 */
    createTime?: string;
    /** 更新时间 */
    updateTime?: string;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 日期列表 */
    dates?: string[];
    /** 功能使用次数统计 */
    featureUsage?: Record<string, any>;
    /** 页面访问次数统计 */
    pageViews?: Record<string, any>;
    /** 平均停留时长(分钟)统计 */
    avgStayTime?: Record<string, any>;
  };

  type UserCreateReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 用户名 */
    username: string;
    /** 密码 */
    password: string;
    /** 真实姓名 */
    name?: string;
    /** 邮箱 */
    email?: string;
    /** 电话 */
    phone?: string;
    /** 部门ID */
    departmentId?: number;
    /** 头像 */
    avatar?: string;
    /** 用户状态：open-启用，close-禁用 */
    status?: string;
    /** 是否为管理员：admin-是，not admin-否 */
    isAdmin?: string;
    /** 角色ID列表 */
    roleIds?: number[];
  };

  type UserGrowthResp = {
    /** 唯一ID */
    id?: number;
    /** 租户ID */
    tenantId?: number;
    /** 创建时间 */
    createTime?: string;
    /** 更新时间 */
    updateTime?: string;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 日期列表 */
    dates?: string[];
    /** 新增用户数列表 */
    newUsers?: number[];
    /** 总用户数列表 */
    totalUsers?: number[];
  };

  type UserQueryReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    current?: number;
    size?: number;
    /** 部门ID */
    departmentId?: number;
    /** 用户名 */
    username?: string;
    /** 姓名 */
    name?: string;
    /** 邮箱 */
    email?: string;
    /** 手机号 */
    phone?: string;
    /** 状态 open启用 close禁用 */
    status?: string;
  };

  type UserResp = {
    /** 唯一ID */
    id?: number;
    /** 租户ID */
    tenantId?: number;
    /** 创建时间 */
    createTime?: string;
    /** 更新时间 */
    updateTime?: string;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 用户名 */
    username?: string;
    /** 真实姓名 */
    name?: string;
    /** 邮箱 */
    email?: string;
    /** 电话 */
    phone?: string;
    /** 头像 */
    avatar?: string;
    /** 部门ID */
    departmentId?: number;
    /** 部门名称 */
    departmentName?: string;
    /** 租户名称 */
    tenantName?: string;
    /** 用户状态：open-启用，close-禁用 */
    status?: string;
    /** 是否为管理员：admin-是，not admin-否 */
    isAdmin?: string;
    /** 角色列表 */
    roles?: RoleResp[];
  };

  type UserUpdateReq = {
    /** 租户ID */
    tenantId?: number;
    /** 创建人 */
    createBy?: number;
    /** 更新人 */
    updateBy?: number;
    /** 用户ID */
    id?: number;
    /** 用户名 */
    username: string;
    /** 密码 */
    password?: string;
    /** 真实姓名 */
    name?: string;
    /** 邮箱 */
    email?: string;
    /** 电话 */
    phone?: string;
    /** 部门ID */
    departmentId?: number;
    /** 头像 */
    avatar?: string;
    /** 用户状态：open-启用，close-禁用 */
    status?: string;
    /** 是否为管理员：admin-是，not admin-否 */
    isAdmin?: string;
    /** 角色ID列表 */
    roleIds?: number[];
  };

  type verifyTokenParams = {
    Authorization: string;
  };
}

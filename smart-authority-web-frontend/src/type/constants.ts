import { TablePaginationConfig } from 'antd';

export const TableDefaultConfig: { pagination: TablePaginationConfig } = {
  pagination: {
    size: 'small',
    showQuickJumper: false,
    showSizeChanger: true,
    defaultPageSize: 10,
    pageSizeOptions: [10, 20, 50, 100],
  },
};

// Code 前缀
export enum CodePrefix {
  'ImpactCodePrefix' = 'DS',
  'ThreatCodePrefix' = 'TS',
  'RiskCodePrefix' = 'R',
  'AssumptionCodePrefix' = 'CSA',
  'ClaimCodePrefix' = 'CSC',
  'GoalCodePrefix' = 'CSG',
  'RequirementCodePrefix' = 'CSR',
  'AttackStepPrefix' = 'AS',
  'AttackPathPrefix' = 'AP',
}

/** 评论source类型 */
export enum CommentSourceTypeEnum {
  // 子项目TARA分析评论
  'CSMS' = 'CSMS',
  'TARA' = 'TARA',
}

/** 评论sourceInfo类型 */
export enum CommentTypeEnum {
  // 子项目TARA分析评论
  'CSMS_SUBPROJECT_TARA' = 'CSMS_SUBPROJECT_TARA',
  // 子项目安全计划评论
  'CSMS_SP_SECURITYPLAN' = 'CSMS_SP_SECURITYPLAN',
  // 子项目安全规范评论
  'CSMS_SP_SECURITYSPEC' = 'CSMS_SP_SECURITYSPEC',
  // 体系管理文档评论
  'CSMS_FM_DOC' = 'CSMS_FM_DOC',
}

export enum DocLibTypeEnum {
  Personal = 'Personal',
  Company = 'Company',
  Department = 'Department',
  Project = 'Project',
  Solution = 'Solution',
  WIKI = 'WIKI',
}

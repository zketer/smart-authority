/**
 * 这个文件作为组件的目录
 * 目的是统一管理对外输出的组件，方便分类
 */
/**
 * 布局组件
 */

import Footer from './Footer';
import { SelectLang } from './RightContent';
import AvatarDropdown from './RightContent/AvatarDropdown';
import { LayoutSetting } from './RightContent/LayoutSetting';


export {
  SelectLang,
  LayoutSetting,
};

/**
 * 组件管理文件
 * 用于统一导出所有组件，方便在其他地方引用
 */

// 导出 Footer 组件
export { default as Footer } from './Footer';

// 导出 AvatarDropdown 组件
export { default as AvatarDropdown } from './RightContent/AvatarDropdown';

// 导出其他组件...
// export { default as SelectLang } from './SelectLang';
// export { default as LayoutSetting } from './LayoutSetting';

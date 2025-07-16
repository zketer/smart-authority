import type { RequestOptions } from '@@/plugin-request/request';
import type { RequestConfig } from '@umijs/max';
import { history } from '@umijs/max';
import { message, notification } from 'antd';

// 错误处理方案： 错误类型
enum ErrorShowType {
  SILENT = 0,
  WARN_MESSAGE = 1,
  ERROR_MESSAGE = 2,
  NOTIFICATION = 3,
  REDIRECT = 9,
}

// 与后端约定的响应数据格式
interface ResponseStructure {
  success: boolean;
  data: any;
  code?: number;
  chMessage?: string;
  enMessage?: string;
  showType?: ErrorShowType;
}

/**
 * @name 错误处理
 * pro 自带的错误处理， 可以在这里做自己的改动
 * @doc https://umijs.org/docs/max/request#配置
 */
export const errorConfig: RequestConfig = {
  // 错误处理： umi@3 的错误处理方案。
  errorConfig: {
    // 错误抛出
    errorThrower: (res) => {
      const { success, data, code, chMessage, enMessage } = res as unknown as ResponseStructure;
      if (!success) {
        const error: any = new Error(chMessage || enMessage);
        error.name = 'BizError';
        error.info = { ...res };
        throw error;
      }
    },
    // 错误接收及处理
    errorHandler: (error: any, opts: any) => {
      if (opts?.skipErrorHandler) throw error;

      // 我们的 errorThrower 抛出的错误
      if (error.name === 'BizError') {
        const errorInfo: ResponseStructure | undefined = error.info;
        if (errorInfo) {
          const { chMessage, enMessage } = errorInfo;
          // 默认显示中文错误信息，如果没有则显示英文错误信息
          message.error(chMessage || enMessage);
        }
      } else if (error.response) {
        // Axios 的错误
        // 请求成功发出且服务器也响应了状态码，但状态代码超出了 2xx 的范围
        if (error.response.status === 401) {
          // token 过期或未登录
          localStorage.removeItem('accessToken');
          localStorage.removeItem('refreshToken');
          localStorage.removeItem('userInfo');
          message.error('登录已过期，请重新登录');
          history.replace('/login');
          return;
        }
        
        // 处理后端返回的错误信息
        const response = error.response.data;
        if (response && (response.chMessage || response.enMessage)) {
          message.error(response.chMessage || response.enMessage);
        } else {
          message.error(`请求失败: ${error.response.status}`);
        }
      } else if (error.request) {
        // 请求已经成功发起，但没有收到响应
        message.error('服务器无响应，请重试');
      } else {
        // 发送请求时出了点问题
        message.error('请求错误，请重试');
      }
    },
  },

  // 请求拦截器
  requestInterceptors: [
    (config: RequestOptions) => {
      // 拦截请求配置，进行个性化处理。
      const token = localStorage.getItem('accessToken');
      const authHeader = { Authorization: token ? `Bearer ${token}` : '' };

      return {
        ...config,
        headers: {
          ...config.headers,
          ...authHeader,
        }
      };
    }
  ],

  // 响应拦截器
  responseInterceptors: [
    (response) => {
      // 拦截响应数据，进行个性化处理
      const { data } = response as unknown as ResponseStructure;

      if (data?.code && data?.code !== 200) {
        const error: any = new Error(data.chMessage || data.enMessage);
        error.name = 'BizError';
        error.info = data;
        throw error;
      }
      
      return response;
    },
  ],
};

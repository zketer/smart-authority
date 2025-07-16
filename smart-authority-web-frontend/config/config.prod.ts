// https://umijs.org/config/
import { defineConfig } from '@umijs/max';
const SYSTEM_NAME = 'SmartAuthority';

export default defineConfig({
  base: `/${SYSTEM_NAME}/`,
  publicPath: `/${SYSTEM_NAME}/`,
  history: {
    type: 'browser',
  },
  // plugins: [
  //   // https://github.com/zthxxx/react-dev-inspector
  //   'react-dev-inspector/plugins/umi/react-inspector',
  // ],
  // https://github.com/zthxxx/react-dev-inspector#inspector-loader-props
  // inspectorConfig: {
  //   exclude: [],
  // babelPlugins: [],
  // babelOptions: {},
  // },

  headScripts: [
    // 解决首次加载时白屏的问题
    {
      src: `/${SYSTEM_NAME}/scripts/loading.js`,
      async: true,
    },
    {
      src: `/${SYSTEM_NAME}/iconfont/iconfont.js`,
      async: true,
    }
  ],
  define: {
    ENV: 'prod',
    PREFIX: `/${SYSTEM_NAME}`, // 项目前缀, 需要和后端nginx配置相同
    AUTH_URL: '/inauth',
  },
});

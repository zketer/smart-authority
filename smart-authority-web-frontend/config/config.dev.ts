// https://umijs.org/config/
import { defineConfig } from '@umijs/max';

export default defineConfig({
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
      src: '/scripts/loading.js',
      async: true,
    },
    {
      src: "/iconfont/iconfont.js",
      async: true,
    }
  ],
  define: {
    ENV: 'dev',
    PREFIX: '',
    AUTH_URL: '/inauth',
  },
});

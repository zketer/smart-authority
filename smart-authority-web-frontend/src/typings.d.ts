declare module 'slash2';
declare module '*.css';
declare module '*.less';
declare module '*.scss';
declare module '*.sass';
declare module '*.svg';
declare module '*.png';
declare module '*.jpg';
declare module '*.jpeg';
declare module '*.gif';
declare module '*.bmp';
declare module '*.tiff';
declare module 'omit.js';
declare module 'numeral';
declare module '@antv/data-set';
declare module 'mockjs';
declare module 'react-fittext';
declare module 'bizcharts-plugin-slider';
declare module 'bpmn-js-properties-panel';
declare module 'camunda-bpmn-moddle/lib';
declare module 'base-64';

// declare const REACT_APP_ENV: 'test' | 'dev' | 'pre' | false;

declare const REACT_APP_ENV: 'test' | 'dev' | 'pre' | 'prod' | false;
declare const AUTH_URL: string;
declare const PREFIX: string;
declare const REDIRECT_URL: string;
declare const ENV: string;

interface Window {
  App: any;
}

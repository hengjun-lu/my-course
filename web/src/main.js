import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from "axios";
import filter from './filter/filter'

Vue.config.productionTip = false
Vue.prototype.$ajax = axios;

// 全局过滤器
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key])
});

// 解决每次ajax请求，对应的sessionId不一致的问题(前后端分类的问题)
axios.defaults.withCredentials = true;

/**
 * axios拦截器
 */
axios.interceptors.request.use(function (config) {
  console.log("请求：", config);
  // //获取当前登录用户的token
  // let token = Tool.getLoginUser().token;
  // //如果不为空
  // if (Tool.isNotEmpty(token)) {
  //   //就给请求参数的headers增加token参数
  //   config.headers.token = token;
  //   console.log("请求headers增加token:", token);
  // }
  return config;
}, error => {});
axios.interceptors.response.use(function (response) {
  console.log("返回结果：", response);
  return response;
}, error => {});

new Vue({
  router,//配置路由
  render: h => h(App),
}).$mount('#app')

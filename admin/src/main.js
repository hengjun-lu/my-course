import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from "axios";
import filter from './filter/filter'

Vue.config.productionTip = false
Vue.prototype.$ajax = axios;
//Vue.prototype.xxx,可以理解为Vue组件的全局变量。可以在任意Vue组件中，使用
//this.xxx来获取这个值。$代表全局属性的一个约定

// 解决每次ajax请求，对应的sessionId不一致的问题(前后端分类的问题)
axios.defaults.withCredentials = true;

/**
 * axios拦截器
 */
axios.interceptors.request.use(function (config) {
  console.log("请求：", config);
  //首先获取当前登录用户的token
  let token = Tool.getLoginUser().token;
  //如果不为空
  if (Tool.isNotEmpty(token)) {
    //就给请求参数的headers增加token参数
     config.headers.token = token;
    console.log("请求headers增加token:", token);
  }
  return config;
}, error => {});
axios.interceptors.response.use(function (response) {
  console.log("返回结果：", response);
  return response;
}, error => {});

// 全局过滤器
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key])
});

//登录路由拦截（守卫函数）
//to：即将要进入的目标路由对象(跳转后)
//from：当前导航正要离开的路由(跳转前)
//next:执行效果依赖 next 方法的调用参数。
router.beforeEach((to, from, next)=>{
  //是否对meta.loginRequire属性做监控拦截
  if(to.matched.some(function(item){
    return item.meta.loginRequire;
  })){
    //获取存放在缓存的登录信息
    let loginUser = Tool.getLoginUser();
    //判断信息是否为空
    if(Tool.isEmpty(loginUser)){
      next("/login")
    }else{
      next();
    }
  }else{
    next();
  }
});
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

console.log("环境:",process.env.NODE_ENV);

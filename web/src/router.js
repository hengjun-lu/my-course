import Vue from 'vue'
import Router from 'vue-router'
import Index   from "./views/index.vue";
import List from "./views/list.vue"
import Detail from "./views/detail.vue"





Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,//内置变量
    routes: [{
        path: "*",//在地址栏中随便输入什么
        redirect: "/index",//都会跳转到/Index
    },{
        path: "/index",//当地址栏端口号什么也没有的时候，Index
        component: Index //Index
    },{
        path: "/list",//当地址栏端口号什么也没有的时候，List
        component: List //List
    },{
        path: "/detail",//当地址栏端口号什么也没有的时候，Detail
        component: Detail //Detail
    }]
})
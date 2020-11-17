import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'
import Admin from './views/admin.vue'
import Welcome from './views/admin/welcome.vue'
import Category from './views/admin/category.vue'
import Course from './views/admin/course.vue'
import Chapter from './views/admin/chapter.vue'
import Section from './views/admin/section.vue'
import Content from './views/admin/content.vue'
import Teacher from './views/admin/teacher.vue'
import File from './views/admin/file.vue'
import User from './views/admin/user.vue'
import Resource from './views/admin/resource.vue'
import Role from './views/admin/role.vue'
import Member from './views/admin/member.vue'
import Sms from './views/admin/sms.vue'





Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,//内置变量
    routes: [{
        path: "*",//在地址栏中随便输入什么
        redirect: "/login",//都会跳转到/login
    },{
        path: "",//当地址栏端口号什么也没有的时候，重定向到login
        component: Login //Login
    },{
        path: "/login",//login地址对应这主键
        component: Login //Login
    },{
        path: "/",
        name: "admin",//为每一个路由加上name属性
        component: Admin, //Admin
        //父路由，用于登录拦截
        meta:{        //加一个自定义obj
            loginRequire:true   //这个参数 true 代表需要登录才能进入
        },
        children:[{
            path: "welcome",//admin地址对应这主键(子路由不加“/”)
            name: "welcome",//为每一个路由加上name属性
            component: Welcome, //Welcome
        },{
            path: "business/category",//course地址对应这主键(子路由不加“/”)
            name: "business/category",//为每一个路由加上name属性
            component: Category, //Course
        },{
            path: "business/course",//course地址对应这主键(子路由不加“/”)
            name: "business/course",//为每一个路由加上name属性
            component: Course, //Course
        },{
            path: "business/chapter",//chapter地址对应这主键(子路由不加“/”)
            name: "business/chapter",//为每一个路由加上name属性
            component: Chapter, //Chapter
        },{
            path: "business/section",//section地址对应这主键(子路由不加“/”)
            name: "business/section",//为每一个路由加上name属性
            component: Section, //Section
        },{
            path: "business/content",//content地址对应这主键(子路由不加“/”)
            name: "business/content",//为每一个路由加上name属性
            component: Content, //Content
        },{
            path: "business/teacher",//teacher地址对应这主键(子路由不加“/”)
            name: "business/teacher",//为每一个路由加上name属性
            component: Teacher, //Teacher
        },{
            path: "business/member",//member(子路由不加“/”)
            name: "business/member",//为每一个路由加上name属性
            component: Member, //Member
        },{
            path: "business/sms",//Sms(子路由不加“/”)
            name: "business/sms",//为每一个路由加上name属性
            component: Sms, //Sms
        },{
            path: "file/file",//file地址对应这主键(子路由不加“/”)
            name: "file/file",//为每一个路由加上name属性
            component: File, //File
        },{
            path: "system/user",//user地址对应这主键(子路由不加“/”)
            name: "system/user",//为每一个路由加上name属性
            component: User, //File
        },{
            path: "system/resource",//resource地址对应这主键(子路由不加“/”)
            name: "system/resource",//为每一个路由加上name属性
            component: Resource, //Resource
        },{
            path: "system/role",//resource地址对应这主键(子路由不加“/”)
            name: "system/role",//为每一个路由加上name属性
            component: Role, //Role
        }]
    }]
})
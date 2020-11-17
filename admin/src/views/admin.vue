<template>
    <div>
        <div id="navbar" class="navbar navbar-default          ace-save-state">
            <div class="navbar-container ace-save-state" id="navbar-container">
                <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
                    <span class="sr-only">Toggle sidebar</span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>

                    <span class="icon-bar"></span>
                </button>

                <div class="navbar-header pull-left">
                    <router-link to="/welcome" class="navbar-brand">
                        <small>
                            <i class="fa fa-leaf"></i>
                            Ace Admin
                        </small>
                    </router-link>
                </div>

                <div class="navbar-buttons navbar-header pull-right" role="navigation">
                    <ul class="nav ace-nav">
                        <li class="grey dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-tasks"></i>
                                <span class="badge badge-grey">4</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-check"></i>
                                    4 Tasks to complete
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar">
                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Software Update</span>
                                                    <span class="pull-right">65%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:65%" class="progress-bar"></div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Hardware Upgrade</span>
                                                    <span class="pull-right">35%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:35%" class="progress-bar progress-bar-danger"></div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Unit Testing</span>
                                                    <span class="pull-right">15%</span>
                                                </div>

                                                <div class="progress progress-mini">
                                                    <div style="width:15%" class="progress-bar progress-bar-warning"></div>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
                                                    <span class="pull-left">Bug Fixes</span>
                                                    <span class="pull-right">90%</span>
                                                </div>

                                                <div class="progress progress-mini progress-striped active">
                                                    <div style="width:90%" class="progress-bar progress-bar-success"></div>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="#">
                                        See tasks with details
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="purple dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-bell icon-animated-bell"></i>
                                <span class="badge badge-important">8</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-exclamation-triangle"></i>
                                    8 Notifications
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar navbar-pink">
                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
													<span class="pull-left">
														<i class="btn btn-xs no-hover btn-pink fa fa-comment"></i>
														New Comments
													</span>
                                                    <span class="pull-right badge badge-info">+12</span>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <i class="btn btn-xs btn-primary fa fa-user"></i>
                                                Bob just signed up as an editor ...
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
													<span class="pull-left">
														<i class="btn btn-xs no-hover btn-success fa fa-shopping-cart"></i>
														New Orders
													</span>
                                                    <span class="pull-right badge badge-success">+8</span>
                                                </div>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#">
                                                <div class="clearfix">
													<span class="pull-left">
														<i class="btn btn-xs no-hover btn-info fa fa-twitter"></i>
														Followers
													</span>
                                                    <span class="pull-right badge badge-info">+11</span>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="#">
                                        See all notifications
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="green dropdown-modal">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
                                <span class="badge badge-success">5</span>
                            </a>

                            <ul class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
                                <li class="dropdown-header">
                                    <i class="ace-icon fa fa-envelope-o"></i>
                                    5 Messages
                                </li>

                                <li class="dropdown-content">
                                    <ul class="dropdown-menu dropdown-navbar">
                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar.png" class="msg-photo" alt="Alex's Avatar" />
                                                <span class="msg-body">
													<span class="msg-title">
														<span class="blue">Alex:</span>
														Ciao sociis natoque penatibus et auctor ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>a moment ago</span>
													</span>
												</span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar3.png" class="msg-photo" alt="Susan's Avatar" />
                                                <span class="msg-body">
													<span class="msg-title">
														<span class="blue">Susan:</span>
														Vestibulum id ligula porta felis euismod ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>20 minutes ago</span>
													</span>
												</span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar4.png" class="msg-photo" alt="Bob's Avatar" />
                                                <span class="msg-body">
													<span class="msg-title">
														<span class="blue">Bob:</span>
														Nullam quis risus eget urna mollis ornare ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>3:15 pm</span>
													</span>
												</span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar2.png" class="msg-photo" alt="Kate's Avatar" />
                                                <span class="msg-body">
													<span class="msg-title">
														<span class="blue">Kate:</span>
														Ciao sociis natoque eget urna mollis ornare ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>1:33 pm</span>
													</span>
												</span>
                                            </a>
                                        </li>

                                        <li>
                                            <a href="#" class="clearfix">
                                                <img src="../../public/ace/assets/images/avatars/avatar5.png" class="msg-photo" alt="Fred's Avatar" />
                                                <span class="msg-body">
													<span class="msg-title">
														<span class="blue">Fred:</span>
														Vestibulum id penatibus et auctor  ...
													</span>

													<span class="msg-time">
														<i class="ace-icon fa fa-clock-o"></i>
														<span>10:09 am</span>
													</span>
												</span>
                                            </a>
                                        </li>
                                    </ul>
                                </li>

                                <li class="dropdown-footer">
                                    <a href="inbox.html">
                                        See all messages
                                        <i class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </li>

                        <li class="light-blue dropdown-modal">
                            <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                <img class="nav-user-photo" src="../../public/ace/assets/images/avatars/user.jpg" alt="Jason's Photo" />
                                <span class="user-info">
									<small>Welcome,</small>
									{{loginUser.name}}
								</span>

                                <i class="ace-icon fa fa-caret-down"></i>
                            </a>

                            <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">

                                <li>
                                    <a href="#">
                                        <i class="ace-icon fa fa-cog"></i>
                                        系统设置
                                    </a>
                                </li>

                                <li>
                                    <a href="profile.html">
                                        <i class="ace-icon fa fa-user"></i>
                                        个人信息
                                    </a>
                                </li>

                                <li class="divider"></li>

                                <li>
                                    <a v-on:click="logout()" href="#">
                                        <i class="ace-icon fa fa-power-off"></i>
                                        退出登录
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div><!-- /.navbar-container -->
        </div>

        <div class="main-container ace-save-state" id="main-container">

            <div id="sidebar" class="sidebar                  responsive                    ace-save-state">

                <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                        <button class="btn btn-success">
                            <i class="ace-icon fa fa-signal"></i>
                        </button>

                        <button class="btn btn-info">
                            <i class="ace-icon fa fa-pencil"></i>
                        </button>

                        <button class="btn btn-warning">
                            <i class="ace-icon fa fa-users"></i>
                        </button>

                        <button class="btn btn-danger">
                            <i class="ace-icon fa fa-cogs"></i>
                        </button>
                    </div>

                    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                        <span class="btn btn-success"></span>

                        <span class="btn btn-info"></span>

                        <span class="btn btn-warning"></span>

                        <span class="btn btn-danger"></span>
                    </div>
                </div><!-- /.sidebar-shortcuts -->

                <ul class="nav nav-list">
                    <li class="" id="welcome-sidebar">
                        <router-link to="/welcome">
                            <i class="menu-icon fa fa-tachometer"></i>
                            <span class="menu-text"> 欢迎:{{loginUser.name}} </span>
                        </router-link>
                        <b class="arrow"></b>
                    </li>
                    <!-- 系统管理 -->
                    <li v-show="hasResource('01')" class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text">
								系统管理
							</span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul v-show="hasResource('0101')" class="submenu">
                            <li class="" id="system-user-sidebar">
                                <router-link to="/system/user" >
                                    <i class="menu-icon fa fa-caret-right"></i>
                                用户管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>

                            <li v-show="hasResource('0102')" class="">
                                <router-link to="/system/resource" id="system-resource-sidebar">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                资源管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>

                            <li v-show="hasResource('0103')" class="" id="system-role-sidebar">
                                <router-link to="/system/role">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    角色管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>
                    <!-- 业务管理 -->
                    <li v-show="hasResource('02')" class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text">
                                业务管理
                            </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>

                        <ul class="submenu">
                            <li v-show="hasResource('0201')" class="" id="business-category-sidebar">
                                <router-link to="/business/category" class="dropdown-toggle">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    分类管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>
                            <li v-show="hasResource('0202')" class="" id="business-course-sidebar">
                                <router-link to="/business/course" class="dropdown-toggle">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    课程管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>
                            <li v-show="hasResource('0203')" class="" id="business-teacher-sidebar">
                                <router-link to="/business/teacher" class="dropdown-toggle">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    讲师管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>
                            <li v-show="hasResource('0204')" class="" id="business-member-sidebar">
                                <router-link to="/business/member" class="dropdown-toggle">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    会员管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>
                            <li v-show="hasResource('0205')" class="" id="business-sms-sidebar">
                                <router-link to="/business/sms" class="dropdown-toggle">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    短信管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>
<!--                            <li class="active" id="business-chapter-sidebar">-->
<!--                                <router-link to="/business/chapter" class="dropdown-toggle">-->
<!--                                    <i class="menu-icon fa fa-caret-right"></i>-->
<!--                                    大章管理-->
<!--                                </router-link>-->

<!--                                <b class="arrow"></b>-->
<!--                            </li>-->
<!--                            <li class="active" id="business-section-sidebar">-->
<!--                                <router-link to="/business/section" class="dropdown-toggle">-->
<!--                                    <i class="menu-icon fa fa-caret-right"></i>-->
<!--                                    小节管理-->
<!--                                </router-link>-->

<!--                                <b class="arrow"></b>-->
<!--                            </li>-->
                        </ul>
                    </li>
<!--              文件管理      -->
                    <li v-show="hasResource('03')" class="">
                        <a href="#" class="dropdown-toggle">
                            <i class="menu-icon fa fa-list"></i>
                            <span class="menu-text">
                                文件管理
                              </span>

                            <b class="arrow fa fa-angle-down"></b>
                        </a>

                        <b class="arrow"></b>
                        <ul class="submenu">
                            <li v-show="hasResource('0301')" class="" id="file-file-sidebar">
                                <router-link to="/file/file" class="dropdown-toggle">
                                    <i class="menu-icon fa fa-caret-right"></i>
                                    文件管理
                                </router-link>

                                <b class="arrow"></b>
                            </li>
                        </ul>
                    </li>

                </ul><!-- /.nav-list -->

                <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                    <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
                </div>
            </div>

            <div class="main-content">
                <div class="main-content-inner">
<!--                    <div class="breadcrumbs ace-save-state" id="breadcrumbs">-->
<!--                        <ul class="breadcrumb">-->
<!--                            <li>-->
<!--                                <i class="ace-icon fa fa-home home-icon"></i>-->
<!--                                <a href="#">Home</a>-->
<!--                            </li>-->

<!--                            <li>-->
<!--                                <a href="#">Other Pages</a>-->
<!--                            </li>-->
<!--                            <li class="active">Blank Page</li>-->
<!--                        </ul>&lt;!&ndash; /.breadcrumb &ndash;&gt;-->

<!--                        <div class="nav-search" id="nav-search">-->
<!--                            <form class="form-search">-->
<!--								<span class="input-icon">-->
<!--									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />-->
<!--									<i class="ace-icon fa fa-search nav-search-icon"></i>-->
<!--								</span>-->
<!--                            </form>-->
<!--                        </div>&lt;!&ndash; /.nav-search &ndash;&gt;-->
<!--                    </div>-->

                    <div class="page-content">
<!--                        <div class="ace-settings-container" id="ace-settings-container">-->
<!--                            <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">-->
<!--                                <i class="ace-icon fa fa-cog bigger-130"></i>-->
<!--                            </div>-->

<!--                            <div class="ace-settings-box clearfix" id="ace-settings-box">-->
<!--                                <div class="pull-left width-50">-->
<!--                                    <div class="ace-settings-item">-->
<!--                                        <div class="pull-left">-->
<!--                                            <select id="skin-colorpicker" class="hide">-->
<!--                                                <option data-skin="no-skin" value="#438EB9">#438EB9</option>-->
<!--                                                <option data-skin="skin-1" value="#222A2D">#222A2D</option>-->
<!--                                                <option data-skin="skin-2" value="#C6487E">#C6487E</option>-->
<!--                                                <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>-->
<!--                                            </select>-->
<!--                                        </div>-->
<!--                                        <span>&nbsp; Choose Skin</span>-->
<!--                                    </div>-->

<!--                                    <div class="ace-settings-item">-->
<!--                                        <input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-navbar" autocomplete="off" />-->
<!--                                        <label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>-->
<!--                                    </div>-->

<!--                                    <div class="ace-settings-item">-->
<!--                                        <input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-sidebar" autocomplete="off" />-->
<!--                                        <label class="lbl" for="ace-settings-sidebar"> Fixed Sidebar</label>-->
<!--                                    </div>-->

<!--                                    <div class="ace-settings-item">-->
<!--                                        <input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-breadcrumbs" autocomplete="off" />-->
<!--                                        <label class="lbl" for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>-->
<!--                                    </div>-->

<!--                                    <div class="ace-settings-item">-->
<!--                                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" autocomplete="off" />-->
<!--                                        <label class="lbl" for="ace-settings-rtl"> Right To Left (rtl)</label>-->
<!--                                    </div>-->

<!--                                    <div class="ace-settings-item">-->
<!--                                        <input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-add-container" autocomplete="off" />-->
<!--                                        <label class="lbl" for="ace-settings-add-container">-->
<!--                                            Inside-->
<!--                                            <b>.container</b>-->
<!--                                        </label>-->
<!--                                    </div>-->
<!--                                </div>&lt;!&ndash; /.pull-left &ndash;&gt;-->

<!--                                <div class="pull-left width-50">-->
<!--                                    <div class="ace-settings-item">-->
<!--                                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" autocomplete="off" />-->
<!--                                        <label class="lbl" for="ace-settings-hover"> Submenu on Hover</label>-->
<!--                                    </div>-->

<!--                                    <div class="ace-settings-item">-->
<!--                                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" autocomplete="off" />-->
<!--                                        <label class="lbl" for="ace-settings-compact"> Compact Sidebar</label>-->
<!--                                    </div>-->

<!--                                    <div class="ace-settings-item">-->
<!--                                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight" autocomplete="off" />-->
<!--                                        <label class="lbl" for="ace-settings-highlight"> Alt. Active Item</label>-->
<!--                                    </div>-->
<!--                                </div>&lt;!&ndash; /.pull-left &ndash;&gt;-->
<!--                            </div>&lt;!&ndash; /.ace-settings-box &ndash;&gt;-->
<!--                        </div>&lt;!&ndash; /.ace-settings-container &ndash;&gt;-->

                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                <router-view></router-view>
                                <!-- PAGE CONTENT ENDS -->
                            </div><!-- /.col -->
                        </div><!-- /.row -->
                    </div><!-- /.page-content -->
                </div>
            </div><!-- /.main-content -->

            <div class="footer">
                <div class="footer-inner">
                    <div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">Ace</span>
							在线视频课程 &copy; 2013-2014
						</span>

                        &nbsp; &nbsp;
                        <span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>
                    </div>
                </div>
            </div>

            <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
                <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
            </a>
        </div><!-- /.main-container -->
    </div>
</template>

<script>
    export default {
        name: "admin",
        data:function(){
            return{
                //用户登录信息
                loginUser:{},

            }
        },
        //页面初始化
        mounted() {
            let  _this =this;//这里声明一个变量_this指向父函数的this
            $("body").removeClass("login-layout light-login");//删除class属性
            $("body").attr("class", "no-skin");//添加class属性
            //console.log("admin");
            //sidebar 激活样式方法二
            _this.activeSidebar(_this.$route.name.replace("/", "-")+ "-sidebar");

            //由于第一次没有加载侧边栏的初始化监听，所以从登录页进入控台主页后，侧边栏不能使用
            //需要在跳转到主页的时候重新加载js，初始化事件就有用了
            $.getScript('/ace/assets/js/ace.min.js');//重新加载js
            //取出login页面设置的user缓存
            _this.loginUser = Tool.getLoginUser();

            //第一次加载需要根据路由判断权限
            if(!_this.hasResourceRouter(_this.$route.name)){
                _this.$router.push("/login")
            }
        },
        //vue 内置watch，用来监听Vue实列上d的数据变化，$route：当前路由信息对象
        watch:{
            $route:{
                handler:function (val,oldVal) {//val:新路由，oldVal:老路由
                    console.log("----->页面跳转：",val,oldVal);
                    //_this只是一个变量名，this代表父函数，如果在子函数还用this，
                    // this的指向就变成子函数了，_this就是用来存储指向的。
                    let  _this =this;//这里声明一个变量_this指向父函数的this

                    /**
                     * 跳转新路由判断一下是否有权限（新路由.name）
                     */
                    if(!_this.hasResourceRouter(val.name)){
                        _this.$router.push("/login")
                        return;
                    }


                    _this.$nextTick(function () {//页面加载后完成执行//
                    //sidebar 激活样式方法二
                    _this.activeSidebar(_this.$route.name.replace("/", "-")+ "-sidebar");
                    })
                }
            }
        },
        methods:{
            /**
             * 根据路由查找权限
             */
            hasResourceRouter(router){
              let _this = this;
              let resources = Tool.getLoginUser().resource;
              if(Tool.isEmpty(resources)){
                return false;
              }
                for (let i = 0; i <resources.length ; i++) {
                    if(router === resources[i].page){
                        return true;
                    }
                }
                return false;
            },

            /**
             * 查找是否有权限
             */
            hasResource(id){
                return Tool.hasResource(id);
            },

            login(){
                this.router.push("/admin")
            },
            /**
             *激活菜单样式，id是当前菜单的id
             */
            activeSidebar:function (id) {
                //siblings 获取所有的兄弟节点
                //兄弟菜单去掉active样式，自身增加active样式
                $("#" + id).siblings().removeClass("active");
                $("#" + id).siblings().find("li").removeClass("active");
                $("#" + id).addClass("active");

                //如果有父级菜单，父菜单的兄弟菜单去掉open active，父级本身菜单增加open active
                let parentLi = $("#" +id).parents("li");
                if(parentLi){
                    //去掉同级菜单的样式
                    parentLi.siblings().removeClass("open active");
                    //去掉同级菜单下子菜单的样式
                    parentLi.siblings().find("li").removeClass("active");
                    parentLi.addClass("open active");
                }
            },

            /**
             * 退出登录
             */
            logout(){
                let _this = this;
                Loading.show();
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/user/logout/' + _this.loginUser.token).then((response)=>{
                    Loading.hide();
                    let resp = response.data;
                    if(resp.success){
                        console.log("退出登录成功：", resp.content);
                        //清空前端的会话缓存
                        Tool.setLoginUser(null);
                        _this.$router.push("/login");
                    }else{
                        console.log("退出登录失败:",resp.content);
                        Toast.warning(resp.message);
                    }
                });
            },
        },
    }
</script>

<style scoped>

</style>
<template>
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
          <div class="container">
            <a class="navbar-brand" href="#">
                <i class="ace-icon fa fa-video-camera">&nbsp;</i>
                    <router-link to="/index">在线视频</router-link>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <router-link to="" class="nav-link" >主页 <span class="sr-only">(current)</span></router-link>
                    </li>
                    <li class="nav-item active">
                        <router-link to="/list" class="nav-link" >全部视频</router-link>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            更多
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="#">关于我们</a>
                            <a class="dropdown-item" href="#">渠道合作</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#">更多信息</a>
                        </div>
                    </li>
                </ul>
                <span v-show="loginMember.id" class="text-white">欢迎：{{loginMember.name}}</span>
                <button v-show="loginMember.id" v-on:click="logout()" class="btn btn-outline-light my2 my-sm-0" >退出登录</button>
                <button v-show="!loginMember.id" v-on:click="openLoginModal()" class="btn btn-outline-light my2 my-sm-0" type="submit">登录/注册</button>
            </div>
          </div>
        </nav>

      <the-login ref="loginComponent"></the-login>
    </header>
</template>

<script>
    import TheLogin from "./login"
    export default {
        name: "theHeader",
        components :{TheLogin},
        data:function(){
          return{
            loginMember:{}
          }
        },
        mounted() {
          let _this = this;
          _this.loginMember = Tool.getLoginMember();//取出来页面的缓存信息
        },
        methods:{
          /**
           * 打开登录注册窗口
           */
          openLoginModal() {
            let _this = this;
            _this.$refs.loginComponent.openLoginModal();
          },

          /**
           * 设置前端缓存登录信息的方法
           */
          setLoginMember(loginMember){
            let _this = this;
            _this.loginMember =loginMember;
          },

          /**
           *
           */
          logout(){
            let _this = this;
            _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/member/logout/' + _this.loginMember.token).then(function (response) {
              let resp = response.data;
              if(resp.success){
                Tool.setLoginMember(null);//前端缓存登录信息清空
                _this.loginMember = {};//当前主键变量也清空
                Toast.success("退出登录成功");
                _this.$router.push("/");
              }else {
                Toast.warning("退出登录失败",resp.message);
              }

            })
          }


        }
    }
</script>

<style scoped>

</style>
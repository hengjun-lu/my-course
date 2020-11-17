<!-- template页面内容 -->
<template>
    <div class="main-container">
        <div class="main-content">
            <div class="row">
                <div class="col-sm-10 col-sm-offset-1">
                    <div class="login-container">
                        <div class="center">
                            <h1>
                                <i class="ace-icon fa fa-leaf green"></i>
                                <span class="red">控制台登录</span>
                                <!--                <span class="white" id="id-text2">Application</span>-->
                            </h1>
                            <h4 class="blue" id="id-company-text">&copy; Company Name</h4>
                        </div>

                        <div class="space-6"></div>

                        <div class="position-relative">
                            <div id="login-box" class="login-box visible widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header blue lighter bigger">
                                            <i class="ace-icon fa fa-coffee green"></i>
                                            请输入账号和密码
                                        </h4>

                                        <div class="space-6"></div>

                                        <form>
                                            <fieldset>
                                                <label class="block clearfix">
                                                    <span class="block input-icon input-icon-right">
                                                        <input v-model="user.loginName" type="text" class="form-control" placeholder="登陆名" />
                                                        <i class="ace-icon fa fa-user"></i>
                                                    </span>
                                                </label>

                                                <label class="block clearfix">
                                                    <span class="block input-icon input-icon-right">
                                                        <input v-model="user.password" type="password" class="form-control" placeholder="请输入密码" />
                                                        <i class="ace-icon fa fa-lock"></i>
                                                    </span>
                                                </label>

                                                <label class="block clearfix">
                                                  <span class="block input-icon input-icon-right">
                                                    <div class="input-group">
                                                      <input v-model="user.imageCode" type="text" class="form-control" placeholder="验证码">
                                                      <span class="input-group-addon" id="basic-addon2">
                                                        <img v-on:click="loadImageCode()" id="image-code" alt="验证码"/>
                                                      </span>
                                                    </div>
                                                  </span>
                                                </label>

                                                <div class="space"></div>

                                                <div class="clearfix">
                                                    <label class="inline">
                                                        <input type="checkbox" v-model="remember" class="ace" />
                                                        <span class="lbl">记住我 </span>
                                                    </label>

                                                    <button type="button"
                                                            class="width-35 pull-right btn btn-sm btn-primary"
                                                            v-on:click="login()">
                                                        <i class="ace-icon fa fa-key"></i>
                                                        <span class="bigger-110">登录</span>
                                                    </button>
                                                </div>

                                                <div class="space-4"></div>
                                            </fieldset>
                                        </form>

                                        <!--                    <div class="social-or-login center">-->
                                        <!--                      <span class="bigger-110">Or Login Using</span>-->
                                        <!--                    </div>-->

                                        <!--                    <div class="space-6"></div>-->

                                        <!--                    <div class="social-login center">-->
                                        <!--                      <a class="btn btn-primary">-->
                                        <!--                        <i class="ace-icon fa fa-facebook"></i>-->
                                        <!--                      </a>-->

                                        <!--                      <a class="btn btn-info">-->
                                        <!--                        <i class="ace-icon fa fa-twitter"></i>-->
                                        <!--                      </a>-->

                                        <!--                      <a class="btn btn-danger">-->
                                        <!--                        <i class="ace-icon fa fa-google-plus"></i>-->
                                        <!--                      </a>-->
                                        <!--                    </div>-->
                                    </div><!-- /.widget-main -->

                                    <!--                  <div class="toolbar clearfix">-->
                                    <!--                    <div>-->
                                    <!--                      <a href="#" data-target="#forgot-box" class="forgot-password-link">-->
                                    <!--                        <i class="ace-icon fa fa-arrow-left"></i>-->
                                    <!--                        I forgot my password-->
                                    <!--                      </a>-->
                                    <!--                    </div>-->

                                    <!--                    <div>-->
                                    <!--                      <a href="#" data-target="#signup-box" class="user-signup-link">-->
                                    <!--                        I want to register-->
                                    <!--                        <i class="ace-icon fa fa-arrow-right"></i>-->
                                    <!--                      </a>-->
                                    <!--                    </div>-->
                                    <!--                  </div>-->
                                </div><!-- /.widget-body -->
                            </div><!-- /.login-box -->

                            <div id="forgot-box" class="forgot-box widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header red lighter bigger">
                                            <i class="ace-icon fa fa-key"></i>
                                            Retrieve Password
                                        </h4>

                                        <div class="space-6"></div>
                                        <p>
                                            Enter your email and to receive instructions
                                        </p>

                                        <form>
                                            <fieldset>
                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
                                                </label>

                                                <div class="clearfix">
                                                    <button type="button" class="width-35 pull-right btn btn-sm btn-danger">
                                                        <i class="ace-icon fa fa-lightbulb-o"></i>
                                                        <span class="bigger-110">Send Me!</span>
                                                    </button>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div><!-- /.widget-main -->

                                    <div class="toolbar center">
                                        <a href="#" data-target="#login-box" class="back-to-login-link">
                                            Back to login
                                            <i class="ace-icon fa fa-arrow-right"></i>
                                        </a>
                                    </div>
                                </div><!-- /.widget-body -->
                            </div><!-- /.forgot-box -->

                            <div id="signup-box" class="signup-box widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header green lighter bigger">
                                            <i class="ace-icon fa fa-users blue"></i>
                                            New User Registration
                                        </h4>

                                        <div class="space-6"></div>
                                        <p> Enter your details to begin: </p>

                                        <form>
                                            <fieldset>
                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
                                                </label>

                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Username" />
															<i class="ace-icon fa fa-user"></i>
														</span>
                                                </label>

                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Password" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
                                                </label>

                                                <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Repeat password" />
															<i class="ace-icon fa fa-retweet"></i>
														</span>
                                                </label>

                                                <label class="block">
                                                    <input type="checkbox" class="ace" />
                                                    <span class="lbl">
															I accept the
															<a href="#">User Agreement</a>
														</span>
                                                </label>

                                                <div class="space-24"></div>

                                                <div class="clearfix">
                                                    <button type="reset" class="width-30 pull-left btn btn-sm">
                                                        <i class="ace-icon fa fa-refresh"></i>
                                                        <span class="bigger-110">Reset</span>
                                                    </button>

                                                    <button type="button" class="width-65 pull-right btn btn-sm btn-success">
                                                        <span class="bigger-110">Register</span>

                                                        <i class="ace-icon fa fa-arrow-right icon-on-right"></i>
                                                    </button>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div>

                                    <div class="toolbar center">
                                        <a href="#" data-target="#login-box" class="back-to-login-link">
                                            <i class="ace-icon fa fa-arrow-left"></i>
                                            Back to login
                                        </a>
                                    </div>
                                </div><!-- /.widget-body -->
                            </div><!-- /.signup-box -->
                        </div><!-- /.position-relative -->

                        <!--            <div class="navbar-fixed-top align-right">-->
                        <!--              <br />-->
                        <!--              &nbsp;-->
                        <!--              <a id="btn-login-dark" href="#">Dark</a>-->
                        <!--              &nbsp;-->
                        <!--              <span class="blue">/</span>-->
                        <!--              &nbsp;-->
                        <!--              <a id="btn-login-blur" href="#">Blur</a>-->
                        <!--              &nbsp;-->
                        <!--              <span class="blue">/</span>-->
                        <!--              &nbsp;-->
                        <!--              <a id="btn-login-light" href="#">Light</a>-->
                        <!--              &nbsp; &nbsp; &nbsp;-->
                        <!--            </div>-->
                    </div>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container -->
</template>
<!-- 数据绑定区域 -->
<script>
    export default {
        name: "login",
        data:function(){
          return{
              //初始化user
              user:{},
              // 默认勾选记住我
              remember:true,
          }
        },
        mounted() {
            let _this = this;
            $("body").removeClass("no-skin");//删除class属性
            $("body").attr("class", "login-layout light-login");
            //console.log("login");
            //从本地取出来用户缓存消息（账户和密码）
            let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER);
            if(rememberUser){
                _this.user = rememberUser;
            }

            //初始化的时候加载一次验证码图片
            _this.loadImageCode();
        },
        methods:{
        login () {
            let _this = this;

            if (1 != 1
                || !Validator.require(_this.user.loginName, "登录名")
                || !Validator.require(_this.user.password, "密码")
            ) {
                return;
            }

            //对登录中的文本框的密码做一个MD5的算法
            let md5 = hex_md5(_this.user.password);//对密文密码做加密
            let rememberUser = LocalStorage.get(LOCAL_KEY_REMEMBER_USER) || {};
            //从本地缓存取出来的密码与文本框的密码比较，如果相等就不在重新加密
            if(md5 !==rememberUser.md5){
                //加密密码+盐值
                _this.user.password = hex_md5(_this.user.password + KEY);
            }

            //页面刷新生成验证码token，将生成后的验证码token仿作参数传递给后台接口，后台接口根据token判断验证码是否正确
            _this.user.imageCodeToken = _this.imageCodeToken;

            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/login',_this.user).then((response)=>{
                Loading.hide();
                let resp = response.data;
                if(resp.success){
                    console.log("登录成功：", resp.content);
                    Tool.setLoginUser(resp.content);
                    let loginUser = resp.content;
                    //如果点击了记住我，就己住账户密码到本地缓存
                    if(_this.remember){
                        //vuex存储在内存，localstorage（本地存储）则以文件的方式存储在本地,永久保存,关闭后缓存不会删除
                        let md5 = hex_md5(_this.user.password);//对输入的文本框密码进行二次加密
                        //将用户信息，输入的加密密码，二次加密的密码存放到本地缓存，用于检测密码是否重新输入过
                        LocalStorage.set(LOCAL_KEY_REMEMBER_USER, {
                            loginName: loginUser.loginName,
                            password: _this.user.password,
                            md5: md5,
                        });
                    }else{
                        //如果没有点击记住我，就把登录的账户信息清空，否则按照初始化mounted的逻辑，下次会默认勾选“记住我”
                        LocalStorage.set(LOCAL_KEY_REMEMBER_USER,null)
                   }
                    _this.$router.push("/welcome");
                }else{
                    //如果登录不成功就显示错误的消息,清空密码，重新刷新验证码
                    console.log("登录失败:",resp.content);
                    Toast.warning(resp.message);
                    _this.user.password="";
                    _this.user.imageCode="";
                    _this.loadImageCode();
                }
            });
        },

            /**
             * 生成验证码
             */
            loadImageCode:function(){
              let _this = this;
              _this.imageCodeToken = Tool.uuid(8);
              //给image-code 设定一个属性src,和请求地址
              $("#image-code").attr('src',process.env.VUE_APP_SERVER + '/system/admin/kaptcha/image-code/'+ _this.imageCodeToken);
                _this.user.imageCode="";
              },
        }
    }
</script>
<!-- css脚本 -->
<style>
    .input-group-addon {
        padding: 0;
    }
</style>
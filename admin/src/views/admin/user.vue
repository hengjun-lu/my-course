<template>
  <div>
    <P>
        <button v-show="hasResource('010101')" v-on:click="addUser()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增
        </button>
        &nbsp;
        <button v-on:click="listUser()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
        </button>
    </P>
<!--  v-bind:list="list"   前面的list是分页组件暴露出来的list方法，后面的listUser是user组件的list方法    -->
    <pagination ref="pagination" v-bind:list="listUser" v-bind:itemCount="8"></pagination>
    <table id="simple-table" class="table  table-bordered table-hover">
         <thead>
            <tr>
                <!-- 多选框 -->
<!--                        <th class="center">-->
<!--                            <label class="pos-rel">-->
<!--                                <input type="checkbox" class="ace" />-->
<!--                                <span class="lbl"></span>-->
<!--                            </label>-->
<!--                        </th>-->
                    <th class="center">id</th>
                    <th class="center">登陆名</th>
                    <th class="center">昵称</th>
                    <th class="center">密码</th>
                    <th class="center">操作</th>
            </tr>
        </thead>

        <tbody>
            <!-- 数据绑定 -->
            <tr v-for="user in users">
                    <td>{{user.id}}</td>
                    <td>{{user.loginName}}</td>
                    <td>{{user.name}}</td>
                    <td>{{user.password}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-show="hasResource('010103')" v-on:click="editPassword(user)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-key bigger-120"></i>
                        </button>

                        <button v-show="hasResource('010101')" v-on:click="editUser(user)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>

                        <button v-show="hasResource('010102')" v-on:click="del(user.id)" class="btn btn-xs btn-danger">
                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </button>

                    </div>
<!--       小屏幕隐藏按钮             -->
                    <div class="hidden-md hidden-lg">
                        <div class="inline pos-rel">
                            <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                <li>
                                    <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
                                        <span class="blue">
                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                        </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
                                        <span class="green">
                                            <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                        </span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
                                        <span class="red">
                                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                        </span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </td>
            </tr>
         </tbody>
     </table>
<!-- 模态框 -->
      <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
          <div class="modal-dialog" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">表单</h4>
                  </div>
                  <div class="modal-body">
                      <form class="form-horizontal">
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">登陆名</label>
                              <div class="col-sm-10">
                                  <input v-model="userData.loginName" v-bind:disabled="userData.id" type="text" class="form-control" id="loginName" placeholder="登陆名">
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">昵称</label>
                              <div class="col-sm-10">
                                  <input v-model="userData.name" type="text" class="form-control" id="name" placeholder="昵称">
                              </div>
                          </div>
                          <div v-show="!userData.id" class="form-group">
                              <label for="name" class="col-sm-2 control-label">密码</label>
                              <div class="col-sm-10">
                                  <input v-model="userData.password"  type="password" class="form-control" id="password" placeholder="密码">
                              </div>
                          </div>
                      </form>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                      <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
                  </div>
              </div><!-- /.modal-content -->
          </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
<!-- 修改密码模态框     -->
      <div id="edit-password-modal" class="modal fade" tabindex="-1" role="dialog">
          <div class="modal-dialog" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">修改密码</h4>
                  </div>
                  <div class="modal-body">
                      <form class="form-horizontal">
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">登陆名</label>
                              <div class="col-sm-10">
                                  <input v-model="userData.loginName" v-bind:disabled="userData.id" type="text" class="form-control"  >
                              </div>
                          </div>
                          <div  class="form-group">
                              <label for="name" class="col-sm-2 control-label">密码</label>
                              <div class="col-sm-10">
                                  <input v-model="userData.password"  type="password" class="form-control"  placeholder="密码">
                              </div>
                          </div>
                      </form>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                      <button v-on:click="savePassword()" type="button" class="btn btn-primary">保存</button>
                  </div>
              </div><!-- /.modal-content -->
          </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->

  </div>
</template>

<script>
    import Pagination from "../../components/pagination";//导入组件
    export default {
        components: {Pagination},//注册组件
        name: "system-user",
        data: function(){
          return{
              //用于save的 数据绑定
              userData:{},
              //用于list的 数据绑定
              users:[],
          }
        },
        //页面加载时触发的函数(页面初始化)
        mounted:function () {
            let _this=this;
           // _this.$refs.pagination.size="5"; //自定义设定每页查询的条数
            _this.listUser(1);//调用list()方法，初始化list为第一页
            //调用父组件的方法$parent
            //sidebar 激活样式方法一
            //this.$parent.activeSidebar("system-user-sidebar");
        },
        methods:{
            /**
             *查找是否有权限
             */
            hasResource(id){
                return Tool.hasResource(id);
            },
            /**
             * 新增按钮
             */
            addUser(){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                _this.userData={};//每次清空新增框里的内容
                //养成习惯，每个都写
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide

            },

            /**
             * 修改按钮
             */
            editUser(user){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                //养成习惯，每个都写
                //前面的userData是上面data下的属性，后面的user是页面列表循环的数据
                _this.userData =$.extend({},user);//jquery复制方法{目标}，数据源
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide
            },

            /**
             * 查询方法，加载页面数据list，增加参数page查询第几页
             * @param page
             */
            listUser(page){
                let _this=this;
                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                _this.$ajax.post(process.env.VUE_APP_SERVER +"/system/admin/user/list",
                    {
                     page:page,
                     size:_this.$refs.pagination.size,//在分页组件里面设置每页查询的条数
                    }).then((response)=>{
                    Loading.hide();
                    //console.log("查询用户列表结果：",response);
                    let  resp = response.data;//使用axios返回的response中data有多个数据，相当于后端的responseDto
                    //将response的结果给users:users表示后台的数据
                    _this.users=resp.content.list;//this.xxx访问组件内的变量
                    _this.$refs.pagination.render(page,resp.content.total);//获取分页页码重新渲染
                });
            },
            /**
             * 保存方法
             * @param pagesave
             */
            save(){
                let _this=this;
                // 保存校验
                if (1 != 1
                  || !Validator.require(_this.userData.loginName, "登陆名")
                  || !Validator.length(_this.userData.loginName, "登陆名", 1, 50)
                  || !Validator.length(_this.userData.name, "昵称", 1, 50)
                  || !Validator.require(_this.userData.password, "密码")
                ) {
                    return;
                }
                //对密码进行MD5+盐值的加密
                _this.userData.password = hex_md5(_this.userData.password + KEY);
                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/user/save",
                    _this.userData).then((response)=>{
                    Loading.hide();
                    console.log("保存用户列表结果：",response);
                    let  resp = response.data;
                    if(resp.success){//如果保存成功就关闭modal,并且重新查询数据list为第一页
                        $("#form-modal").modal("hide");
                        _this.listUser(1);
                        Toast.success("保存成功");
                    }else{
                        Toast.warning(resp.message);
                    }
                });
            },
            /**
             * 删除方法
             * @param id数据的主键
             */
            del(id){
                let _this=this;

                Confirm.show("删除用户后不可恢复，确认删除?",function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + "/system/admin/user/delete/"+id).then((response)=>{
                        Loading.hide();
                        console.log("删除用户列表结果：",response);
                        let  resp = response.data;
                        if(resp.success){//如果判断成功重新查询数据list为第一页
                            Toast.success("删除成功");
                            _this.listUser(1);
                        }
                    });
                })
            },

            /**
             * 修改密码
             */
            editPassword(user){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                //养成习惯，每个都写
                //前面的userData是上面data下的属性，后面的user是页面列表循环的数据
                _this.userData =$.extend({},user);//jquery复制方法{目标}，数据源
                _this.userData.password=null;
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#edit-password-modal").modal("show");//打开show，关闭hide
            },

            /**
             * 保存方法
             * @param pagesave
             */
            savePassword(){
                let _this=this;
                //对密码进行MD5+盐值的加密
                _this.userData.password = hex_md5(_this.userData.password + KEY);
                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/user/save-password",
                    _this.userData).then((response)=>{
                    Loading.hide();
                    console.log("保存用户列表结果：",response);
                    let  resp = response.data;
                    if(resp.success){//如果保存成功就关闭modal,并且重新查询数据list为第一页
                        $("#edit-password-modal").modal("hide");
                        _this.listUser(1);
                        Toast.success("保存成功");
                    }else{
                        Toast.warning(resp.message);
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>
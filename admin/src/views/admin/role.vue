<template>
  <div>
    <P>
        <button v-on:click="addRole()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增
        </button>
        &nbsp;
        <button v-on:click="listRole()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
        </button>
    </P>
<!--  v-bind:list="list"   前面的list是分页组件暴露出来的list方法，后面的listRole是role组件的list方法    -->
    <pagination ref="pagination" v-bind:list="listRole" v-bind:itemCount="8"></pagination>
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
                    <th class="center">角色</th>
                    <th class="center">描述</th>
                <th class="center">操作</th>
            </tr>
        </thead>

        <tbody>
            <!-- 数据绑定 -->
            <tr v-for="role in roles">
                    <td>{{role.id}}</td>
                    <td>{{role.name}}</td>
                    <td>{{role.desc}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-on:click="editUser(role)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-user bigger-120"></i>
                        </button>

                        <button v-on:click="editResource(role)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-list bigger-120"></i>
                        </button>

                        <button v-on:click="editRole(role)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>

                        <button v-on:click="del(role.id)" class="btn btn-xs btn-danger">
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
                                  <label for="name" class="col-sm-2 control-label">角色</label>
                                  <div class="col-sm-10">
                                      <input v-model="roleData.name" type="text" class="form-control" id="name" placeholder="角色">
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label for="name" class="col-sm-2 control-label">描述</label>
                                  <div class="col-sm-10">
                                      <input v-model="roleData.desc" type="text" class="form-control" id="desc" placeholder="描述">
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

      <!-- 资源配置模态框 -->
      <div id="resource-modal" class="modal fade" tabindex="-1" role="dialog">
          <div class="modal-dialog" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">角色资源关联配置</h4>
                  </div>
<!--              资源树  -->
                  <div class="modal-body">
                      <ul id="tree" class="ztree"></ul>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
                          <i class="ace-icon fa fa-times"></i>
                          关闭
                      </button>
                      <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveResource()">
                          <i class="ace-icon fa fa-plus blue"></i>
                          保存
                      </button>
                  </div>
              </div><!-- /.modal-content -->
          </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->

      <!-- 角色用户关联配置 -->
      <div id="user-modal" class="modal fade" tabindex="-1" role="dialog">
          <div class="modal-dialog" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">角色用户关联配置</h4>
                  </div>
                  <div class="modal-body">
                      <div class="row">
                          <div class="col-md-6">
                              <table id="user-table" class="table table-hover">
                                  <tbody>
                                  <tr v-for="user in users">
                                      <td>{{user.loginName}}</td>
                                      <td class="text-right">
                                          <a v-on:click="addUser(user)" href="javascript:;" class="">
                                              <i class="ace-icon fa fa-arrow-circle-right blue"></i>
                                          </a>
                                      </td>
                                  </tr>
                                  </tbody>
                              </table>
                          </div>
                          <div class="col-md-6">
                              <table id="role-user-table" class="table table-hover">
                                  <tbody>
                                  <tr v-for="user in roleUsers">
                                      <td>{{user.loginName}}</td>
                                      <td class="text-right">
                                          <a v-on:click="deleteUser(user)" href="javascript:;" class="">
                                              <i class="ace-icon fa fa-trash blue"></i>
                                          </a>
                                      </td>
                                  </tr>
                                  </tbody>
                              </table>
                          </div>
                      </div>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
                          <i class="ace-icon fa fa-times"></i>
                          关闭
                      </button>
                      <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveUser()">
                          <i class="ace-icon fa fa-plus blue"></i>
                          保存
                      </button>
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
        name: "system-role",
        data: function(){
          return{
              //用于save的 数据绑定resources
              roleData:{},
              //用于list的 数据绑定
              roles:[],
              resources: [],
              zTree: {},
              users: [],
              roleUsers: []
          }
        },
        //页面加载时触发的函数(页面初始化)
        mounted:function () {
            let _this=this;
           // _this.$refs.pagination.size="5"; //自定义设定每页查询的条数
            _this.listRole(1);//调用list()方法，初始化list为第一页
            //调用父组件的方法$parent
            //sidebar 激活样式方法一
            //this.$parent.activeSidebar("system-role-sidebar");
        },
        methods:{
            /**
             * 新增按钮
             */
            addRole(){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                _this.roleData={};//每次清空新增框里的内容
                //养成习惯，每个都写
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide

            },

            /**
             * 修改按钮
             */
            editRole(role){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                //养成习惯，每个都写
                //前面的roleData是上面data下的属性，后面的role是页面列表循环的数据
                _this.roleData =$.extend({},role);//jquery复制方法{目标}，数据源
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide

            },

            /**
             * 查询方法，加载页面数据list，增加参数page查询第几页
             * @param page
             */
            listRole(page){
                let _this=this;
                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                _this.$ajax.post(process.env.VUE_APP_SERVER +"/system/admin/role/list",
                    {
                     page:page,
                     size:_this.$refs.pagination.size,//在分页组件里面设置每页查询的条数
                    }).then((response)=>{
                    Loading.hide();
                    //console.log("查询角色列表结果：",response);
                    let  resp = response.data;//使用axios返回的response中data有多个数据，相当于后端的responseDto
                    //将response的结果给roles:roles表示后台的数据
                    _this.roles=resp.content.list;//this.xxx访问组件内的变量
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
                  || !Validator.require(_this.roleData.name, "角色")
                  || !Validator.length(_this.roleData.name, "角色", 1, 50)
                  || !Validator.require(_this.roleData.desc, "描述")
                  || !Validator.length(_this.roleData.desc, "描述", 1, 100)
                ) {
                    return;
                }

                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/role/save",
                    _this.roleData).then((response)=>{
                    Loading.hide();
                    console.log("保存角色列表结果：",response);
                    let  resp = response.data;
                    if(resp.success){//如果保存成功就关闭modal,并且重新查询数据list为第一页
                        $("#form-modal").modal("hide");
                        _this.listRole(1);
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

                Confirm.show("删除角色后不可恢复，确认删除?",function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + "/system/admin/role/delete/"+id).then((response)=>{
                        Loading.hide();
                        console.log("删除角色列表结果：",response);
                        let  resp = response.data;
                        if(resp.success){//如果判断成功重新查询数据list为第一页
                            Toast.success("删除成功");
                            _this.listRole(1);
                        }
                    });
                })
            },

            /**
             * 点击【编辑】
             */
            editResource(role) {
                let _this = this;
                _this.role = $.extend({}, role);
                _this.loadResource();
                $("#resource-modal").modal("show");
            },

            /**
             * 加载资源树
             */
            loadResource(){
                let _this = this;
                Loading.show();
                _this.$ajax.get(process.env.VUE_APP_SERVER + "/system/admin/resource/load-tree").then(function (res) {
                    Loading.hide();
                    let response = res.data;
                    _this.resources = response.content;
                    // 初始化树
                    _this.initTree();
                    //根据角色显示选中的资源
                    _this.listRoleResource();
                })
            },

            /**
             * 初始资源树
             */
            initTree() {
                let _this = this;
                let setting = {
                    check: {
                        enable: true //树形勾选
                    },
                    data: {
                        simpleData: {
                            idKey: "id",
                            pIdKey: "parent",
                            rootPId: "",
                            enable: true
                        }
                    }
                };

                _this.zTree = $.fn.zTree.init($("#tree"), setting, _this.resources);
                _this.zTree.expandAll(true);
            },

            /**
             * 保存资源树和角色关系
             */
            saveResource(){
                let _this = this;
                //获取到选中的节点 （父节点和子节点）
                let resources = _this.zTree.getCheckedNodes();
                console.log("勾选的资源：", resources);
                // 保存时，只需要保存资源id，所以使用id数组进行参数传递
                let resourceIds = [];
                for (let i = 0; i <resources.length ; i++) {
                    resourceIds.push(resources[i].id)
                }
                //id:roleId,resourceIds:资源数组
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/save-resource',
                    {id:_this.role.id,resourceIds:resourceIds}).then(function(response){
                    let resp = response.data;
                    if(resp.success){
                        Toast.success("保存成功")
                    }else{
                        Toast.warning(resp.message);
                    }
                });
            },

            /**
             * 根据角色加载资源树
             */
            listRoleResource(){
                let _this = this;
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/role/list-resource/'+_this.role.id)
                  .then(function(response){
                    let resp =  sresponse.data;
                    let resources = resp.content;
                    // 勾选查询到的资源：先把树的所有节点清空勾选，再勾选查询到的节点
                    _this.zTree.checkAllNodes(false);
                    for (let i = 0; i < resources.length; i++) {
                        let node = _this.zTree.getNodeByParam("id", resources[i]);
                        _this.zTree.checkNode(node, true);
                    }
                })
            },

            /**
             * 点击【用户】
             */
            editUser(role) {
                let _this = this;
                _this.role = $.extend({}, role);
                _this.listUser();
                $("#user-modal").modal("show");
            },

            /**
             * 查询所有用户
             */
            listUser() {
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/user/list', {
                    page: 1,
                    size: 9999
                }).then((response)=>{
                    let resp = response.data;
                    if (resp.success) {
                        _this.users = resp.content.list;
                        _this.listRoleUser();
                    } else {
                        Toast.warning(resp.message);
                    }
                })
            },

            /**
             * 角色中增加用户
             */
            addUser(user) {
                let _this = this;

                // 如果当前要添加的用户在右边列表中已经有了，则不用再添加
                let users = _this.roleUsers;
                for (let i = 0; i < users.length; i++) {
                    if (user === users[i]) {
                        return;
                    }
                }

                _this.roleUsers.push(user);
            },

            /**
             * 角色中删除用户
             */
            deleteUser(user) {
                let _this = this;
                Tool.removeObj(_this.roleUsers, user);
            },

            /**
             * 角色用户模态框点击【保存】
             */
            saveUser(){
                let _this = this;
                let users = _this.roleUsers;

                // 保存时，只需要保存用户id，所以使用id数组进行参数传递
                let userIds = [];
                for (let i = 0; i <users.length ; i++) {
                    userIds.push(users[i].id)
                }
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/system/admin/role/save-user',
                    {id: _this.role.id,userIds: userIds}).then(function (response) {
                    console.log("保存角色用户结果：", response);
                    let resp = response.data;
                    if (resp.success) {
                        Toast.success("保存成功!");
                    } else {
                        Toast.warning(resp.message);
                    }
                })
            },

            /**
             * 加载角色用户
             */
            listRoleUser(){
                let _this = this;
                _this.roleUsers = [];
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/system/admin/role/list-user/'+ _this.role.id)
                  .then(function (res) {
                    let response = res.data;
                    let userIds = response.content;
                    // 根据加载到用户ID，到【所有用户数组：users】中查找用户对象，用于列表显示
                    for (let i = 0; i < userIds.length; i++) {
                        for (let j = 0; j < _this.users.length; j++) {
                            if (userIds[i] === _this.users[j].id) {
                                _this.roleUsers.push(_this.users[j]);
                            }
                        }
                    }
                })
            },

        }
    }
</script>

<style scoped>

</style>
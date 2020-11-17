<template>
  <div>
    <P>
<!--        <button v-on:click="addFile()" class="btn btn-white btn-default btn-round">-->
<!--            <i class="ace-icon fa fa-edit"></i>-->
<!--            新增-->
<!--        </button>-->
        &nbsp;
        <button v-on:click="listFile()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
        </button>
    </P>
<!--  v-bind:list="list"   前面的list是分页组件暴露出来的list方法，后面的listFile是file组件的list方法    -->
    <pagination ref="pagination" v-bind:list="listFile" v-bind:itemCount="8"></pagination>
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
                    <th class="center">相对路径</th>
                    <th class="center">文件名</th>
                    <th class="center">后缀</th>
                    <th class="center">大小</th>
                    <th class="center">用途</th>
<!--                    <th class="center">操作</th>-->
            </tr>
        </thead>

        <tbody>
            <!-- 数据绑定 -->
            <tr v-for="file in files">
                    <td>{{file.id}}</td>
                    <td>{{file.path}}</td>
                    <td>{{file.name}}</td>
                    <td>{{file.suffix}}</td>
                    <td>{{file.size | formatFileSize}}</td>
                    <td>{{FILE_USE | optionKV(file.use)}}</td>
<!--                <td>-->
<!--                    <div class="hidden-sm hidden-xs btn-group">-->

<!--                        <button v-on:click="editFile(file)" class="btn btn-xs btn-info">-->
<!--                            <i class="ace-icon fa fa-pencil bigger-120"></i>-->
<!--                        </button>-->

<!--                        <button v-on:click="del(file.id)" class="btn btn-xs btn-danger">-->
<!--                            <i class="ace-icon fa fa-trash-o bigger-120"></i>-->
<!--                        </button>-->

<!--                    </div>-->
<!--&lt;!&ndash;       小屏幕隐藏按钮             &ndash;&gt;-->
<!--                    <div class="hidden-md hidden-lg">-->
<!--                        <div class="inline pos-rel">-->
<!--                            <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">-->
<!--                                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>-->
<!--                            </button>-->

<!--                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">-->
<!--                                <li>-->
<!--                                    <a href="#" class="tooltip-info" data-rel="tooltip" title="View">-->
<!--                                                                    <span class="blue">-->
<!--                                                                        <i class="ace-icon fa fa-search-plus bigger-120"></i>-->
<!--                                                                    </span>-->
<!--                                    </a>-->
<!--                                </li>-->

<!--                                <li>-->
<!--                                    <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">-->
<!--                                                                    <span class="green">-->
<!--                                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>-->
<!--                                                                    </span>-->
<!--                                    </a>-->
<!--                                </li>-->

<!--                                <li>-->
<!--                                    <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">-->
<!--                                                                    <span class="red">-->
<!--                                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>-->
<!--                                                                    </span>-->
<!--                                    </a>-->
<!--                                </li>-->
<!--                            </ul>-->
<!--                        </div>-->
<!--                    </div>-->
<!--                </td>-->
            </tr>
         </tbody>
     </table>
<!-- 模态框 -->
<!--      <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">-->
<!--          <div class="modal-dialog" role="document">-->
<!--              <div class="modal-content">-->
<!--                  <div class="modal-header">-->
<!--                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
<!--                      <h4 class="modal-title">表单</h4>-->
<!--                  </div>-->
<!--                  <div class="modal-body">-->
<!--                      <form class="form-horizontal">-->
<!--                              <div class="form-group">-->
<!--                                  <label for="name" class="col-sm-2 control-label">相对路径</label>-->
<!--                                  <div class="col-sm-10">-->
<!--                                      <input v-model="fileData.path" type="text" class="form-control" id="path" placeholder="相对路径">-->
<!--                                  </div>-->
<!--                              </div>-->
<!--                              <div class="form-group">-->
<!--                                  <label for="name" class="col-sm-2 control-label">文件名</label>-->
<!--                                  <div class="col-sm-10">-->
<!--                                      <input v-model="fileData.name" type="text" class="form-control" id="name" placeholder="文件名">-->
<!--                                  </div>-->
<!--                              </div>-->
<!--                              <div class="form-group">-->
<!--                                  <label for="name" class="col-sm-2 control-label">后缀</label>-->
<!--                                  <div class="col-sm-10">-->
<!--                                      <input v-model="fileData.suffix" type="text" class="form-control" id="suffix" placeholder="后缀">-->
<!--                                  </div>-->
<!--                              </div>-->
<!--                              <div class="form-group">-->
<!--                                  <label for="name" class="col-sm-2 control-label">大小</label>-->
<!--                                  <div class="col-sm-10">-->
<!--                                      <input v-model="fileData.size" type="text" class="form-control" id="size" placeholder="大小">-->
<!--                                  </div>-->
<!--                              </div>-->
<!--                                <div class="form-group">-->
<!--                                    <label for="name" class="col-sm-2 control-label">用途</label>-->
<!--                                    <div class="col-sm-10">-->
<!--                                        <select v-model="fileData.use" class="form-control">-->
<!--                                            <option v-for="o in FILE_USE" v-bind:value="o.key">{{o.value}}</option>-->
<!--                                        </select>-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                      </form>-->
<!--                  </div>-->
<!--                  <div class="modal-footer">-->
<!--                      <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>-->
<!--                      <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>-->
<!--                  </div>-->
<!--              </div>&lt;!&ndash; /.modal-content &ndash;&gt;-->
<!--          </div>&lt;!&ndash; /.modal-dialog &ndash;&gt;-->
<!--      </div>&lt;!&ndash; /.modal &ndash;&gt;-->
  </div>
</template>

<script>
    import Pagination from "../../components/pagination";//导入组件
    export default {
        components: {Pagination},//注册组件
        name: "file-file",
        data: function(){
          return{
              //用于save的 数据绑定
              fileData:{},
              //用于list的 数据绑定
              files:[],
              FILE_USE: FILE_USE,
          }
        },
        //页面加载时触发的函数(页面初始化)
        mounted:function () {
            let _this=this;
           // _this.$refs.pagination.size="5"; //自定义设定每页查询的条数
            _this.listFile(1);//调用list()方法，初始化list为第一页
            //调用父组件的方法$parent
            //sidebar 激活样式方法一
            //this.$parent.activeSidebar("file-file-sidebar");
        },
        methods:{
            /**
             * 新增按钮
             */
            // addFile(){
            //     //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
            //     let _this=this;
            //     _this.fileData={};//每次清空新增框里的内容
            //     //养成习惯，每个都写
            //     //打开模态框
            //     //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
            //     // .modal()是modal内置方法，用于弹出或者关闭模态框
            //     $("#form-modal").modal("show");//打开show，关闭hide
            //
            // },

            /**
             * 修改按钮
             */
            // editFile(file){
            //     //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
            //     let _this=this;
            //     //养成习惯，每个都写
            //     //前面的fileData是上面data下的属性，后面的file是页面列表循环的数据
            //     _this.fileData =$.extend({},file);//jquery复制方法{目标}，数据源
            //     //打开模态框
            //     //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
            //     // .modal()是modal内置方法，用于弹出或者关闭模态框
            //     $("#form-modal").modal("show");//打开show，关闭hide
            //
            // },

            /**
             * 查询方法，加载页面数据list，增加参数page查询第几页
             * @param page
             */
            listFile(page){
                let _this=this;
                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                _this.$ajax.post(process.env.VUE_APP_SERVER +"/file/admin/file/list",
                    {
                     page:page,
                     size:_this.$refs.pagination.size,//在分页组件里面设置每页查询的条数
                    }).then((response)=>{
                    Loading.hide();
                    //console.log("查询文件列表结果：",response);
                    let  resp = response.data;//使用axios返回的response中data有多个数据，相当于后端的responseDto
                    //将response的结果给files:files表示后台的数据
                    _this.files=resp.content.list;//this.xxx访问组件内的变量
                    _this.$refs.pagination.render(page,resp.content.total);//获取分页页码重新渲染
                });
            },
            /**
             * 保存方法
             * @param pagesave
             */
            // save(){
            //     let _this=this;
            //     //汽泡校验法，根据ID取值判断
            //     // if($("#name").val()==""){
            //     //     $("#name").tips({
            //     //         side:3,
            //     //         msg:'名字不能为空',
            //     //         bg:'#AE81FF',
            //     //         time:3
            //     //     });
            //     //     $("#name").focus();
            //     //     return false;
            //     // }
            //     // 保存校验
            //     if (1 != 1
            //       || !Validator.require(_this.fileData.path, "相对路径")
            //       || !Validator.length(_this.fileData.path, "相对路径", 1, 100)
            //       || !Validator.length(_this.fileData.name, "文件名", 1, 100)
            //       || !Validator.length(_this.fileData.suffix, "后缀", 1, 10)
            //     ) {
            //         return;
            //     }
            //
            //     Loading.show();
            //     //调用ajax.获取get请求路径。得到response变量。输出response变量
            //     //调用ajax.获取post请求路径。得到response变量。输出response变量
            //     _this.$ajax.post(process.env.VUE_APP_SERVER + "/file/admin/file/save",
            //         _this.fileData).then((response)=>{
            //         Loading.hide();
            //         console.log("保存文件列表结果：",response);
            //         let  resp = response.data;
            //         if(resp.success){//如果保存成功就关闭modal,并且重新查询数据list为第一页
            //             $("#form-modal").modal("hide");
            //             _this.listFile(1);
            //             Toast.success("保存成功");
            //         }else{
            //             Toast.warning(resp.message);
            //         }
            //     });
            // },
            /**
             * 删除方法
             * @param id数据的主键
             */
            // del(id){
            //     let _this=this;
            //
            //     Confirm.show("删除文件后不可恢复，确认删除?",function () {
            //         Loading.show();
            //         _this.$ajax.delete(process.env.VUE_APP_SERVER + "/file/admin/file/delete/"+id).then((response)=>{
            //             Loading.hide();
            //             console.log("删除文件列表结果：",response);
            //             let  resp = response.data;
            //             if(resp.success){//如果判断成功重新查询数据list为第一页
            //                 Toast.success("删除成功");
            //                 _this.listFile(1);
            //             }
            //         });
            //     })
            // }
        }
    }
</script>

<style scoped>

</style>
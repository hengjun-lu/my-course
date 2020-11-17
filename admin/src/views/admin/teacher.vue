<template>
  <div>
    <P>
        <button v-on:click="addTeacher()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增
        </button>
        &nbsp;
        <button v-on:click="listTeacher()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
        </button>
    </P>
<!--  v-bind:list="list"   前面的list是分页组件暴露出来的list方法，后面的listTeacher是teacher组件的list方法    -->
    <pagination ref="pagination" v-bind:list="listTeacher" v-bind:itemCount="8"></pagination>
      <div class="row">
         <div v-for="teacher in teachers" class="col-md-3">
             <div>
                 <span class="profile-picture">
                    <img v-show="!teacher.image" class="editable img-responsive editable-click editable-empty" src="/static/image/讲师头像/头像1.jpg" v-bind:title="teacher.intro"/>
                    <img v-show="teacher.image"  class="editable img-responsive editable-click editable-empty" v-bind:src="teacher.image" v-bind:title="teacher.intro"/>
                 </span>

                 <div class="space-4"></div>

                 <div class="width-85 label label-info label-xlg arrowed-in arrowed-in-right">
                     <div class="inline position-relative">
                         <a href="javascript:;" class="user-title-label dropdown-toggle" data-toggle="dropdown">
                             <i class="ace-icon fa fa-circle light-green"></i>
                             &nbsp;
                             <span class="white">{{teacher.position}}</span>
                         </a>
                     </div>
                 </div>
             </div>

             <div class="space-6"></div>

             <div class="text-center">
                 <a href="javascript:;" class="text-info bigger-110" v-bind:title="teacher.motto">
                     <i class="ace-icon fa fa-user"></i>
                     {{teacher.name}}【{{teacher.nickname}}】
                 </a>
             </div>

             <div class="space-6"></div>

             <div class="profile-social-links align-center">
                 <button v-on:click="editTeacher(teacher)" class="btn btn-xs btn-info">
                     <i class="ace-icon fa fa-pencil bigger-120"></i>
                 </button>
                 &nbsp;
                 <button v-on:click="del(teacher.id)" class="btn btn-xs btn-danger">
                     <i class="ace-icon fa fa-trash-o bigger-120"></i>
                 </button>
             </div>

             <div class="hr hr16 dotted"></div>

         </div>
      </div>

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
                              <label for="name" class="col-sm-2 control-label">姓名</label>
                              <div class="col-sm-10">
                                  <input v-model="teacherData.name" type="text" class="form-control" id="name" placeholder="姓名">
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">昵称</label>
                              <div class="col-sm-10">
                                  <input v-model="teacherData.nickname" type="text" class="form-control" id="nickname" placeholder="昵称">
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">头像</label>
                              <div class="col-sm-10">
                                  <big-file
                                        v-bind:input-id="'image-upload'"
                                        v-bind:text="'上传头像'"
                                        v-bind:suffixs="[ 'jpg' , 'jpeg' , 'png' ]"
                                        v-bind:use="FILE_USE.TEACHER.key"
                                        v-bind:after-upload="afterUpload">
                                  </big-file>
                                  <div v-show="teacherData.image" class="row">
                                      <div class="col-md-4">
                                          <!-- img-responsive 图片变成响应式自适应-->
                                          <img v-bind:src="teacherData.image" class="img-responsive">
                                      </div>
                                  </div>
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">职位</label>
                              <div class="col-sm-10">
                                  <input v-model="teacherData.position" type="text" class="form-control" id="position" placeholder="职位">
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">座右铭</label>
                              <div class="col-sm-10">
                                  <input v-model="teacherData.motto" type="text" class="form-control" id="motto" placeholder="座右铭">
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">简介</label>
                              <div class="col-sm-10">
                                  <textarea v-model="teacherData.intro" class="form-control" rows="5"></textarea>
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
  </div>
</template>

<script>
    import Pagination from "../../components/pagination";//导入分页组件
    import File from "../../components/file";//导入文件上传组件
    import BigFile from "../../components/big-file";//导入大文件上传组件
    export default {
        //注册分页组件,注册文件上传组件
        components: {Pagination,BigFile,File},
        name: "business-teacher",
        data: function(){
          return{
              //用于save的 数据绑定
              teacherData:{},
              //用于list的 数据绑定
              teachers:[],
              //USE用途的枚举,将全局变量放入到组件内的变量
              FILE_USE: FILE_USE,
          }
        },
        //页面加载时触发的函数(页面初始化)
        mounted:function () {
            let _this=this;
           // _this.$refs.pagination.size="5"; //自定义设定每页查询的条数
            _this.listTeacher(1);//调用list()方法，初始化list为第一页
            //调用父组件的方法$parent
            //sidebar 激活样式方法一
            //this.$parent.activeSidebar("business-teacher-sidebar");
        },
        methods:{
            /**
             * 新增按钮
             */
            addTeacher(){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                _this.teacherData={};//每次清空新增框里的内容
                //养成习惯，每个都写
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide

            },

            /**
             * 修改按钮
             */
            editTeacher(teacher){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                //养成习惯，每个都写
                //前面的teacherData是上面data下的属性，后面的teacher是页面列表循环的数据
                _this.teacherData =$.extend({},teacher);//jquery复制方法{目标}，数据源
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide

            },

            /**
             * 查询方法，加载页面数据list，增加参数page查询第几页
             * @param page
             */
            listTeacher(page){
                let _this=this;
                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                _this.$ajax.post(process.env.VUE_APP_SERVER +"/business/admin/teacher/list",
                    {
                     page:page,
                     size:_this.$refs.pagination.size,//在分页组件里面设置每页查询的条数
                    }).then((response)=>{
                    Loading.hide();
                    //console.log("查询讲师列表结果：",response);
                    let  resp = response.data;//使用axios返回的response中data有多个数据，相当于后端的responseDto
                    //将response的结果给teachers:teachers表示后台的数据
                    _this.teachers=resp.content.list;//this.xxx访问组件内的变量
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
                  || !Validator.require(_this.teacherData.name, "姓名")
                  || !Validator.length(_this.teacherData.name, "姓名", 1, 50)
                  || !Validator.length(_this.teacherData.nickname, "昵称", 1, 50)
                  // || !Validator.length(_this.teacherData.image, "头像", 1, 50)
                  || !Validator.length(_this.teacherData.position, "职位", 1, 50)
                  || !Validator.length(_this.teacherData.motto, "座右铭", 1, 50)
                  || !Validator.length(_this.teacherData.intro, "简介", 1, 50)
                ) {
                    return;
                }

                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/teacher/save",
                    _this.teacherData).then((response)=>{
                    Loading.hide();
                    console.log("保存讲师列表结果：",response);
                    let  resp = response.data;
                    if(resp.success){//如果保存成功就关闭modal,并且重新查询数据list为第一页
                        $("#form-modal").modal("hide");
                        _this.listTeacher(1);
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

                Confirm.show("删除讲师后不可恢复，确认删除?",function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + "/business/admin/teacher/delete/"+id).then((response)=>{
                        Loading.hide();
                        console.log("删除讲师列表结果：",response);
                        let  resp = response.data;
                        if(resp.success){//如果判断成功重新查询数据list为第一页
                            Toast.success("删除成功");
                            _this.listTeacher(1);
                        }
                    });
                })
            },

            /**
             * 给file.vue中的afterUpload函数使用
             * 与组件不相关的业务代码应该由外部通过回调函数传进去
             * @param resp
             */
            afterUpload(resp){
                let _this=this
                let  image  =resp.content.path;//取出来图片路径
                _this.teacherData.image = image;
            }
        }
    }
</script>

<style scoped>

</style>
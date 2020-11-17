<template>
  <div>
    <P>
        <button v-on:click="addCourse()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增
        </button>
        &nbsp;
        <button v-on:click="listCourse()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
        </button>
    </P>
<!--  v-bind:list="list"   前面的list是分页组件暴露出来的list方法，后面的listCourse是course组件的list方法    -->
    <pagination ref="pagination" v-bind:list="listCourse"></pagination>
    <div class="row">
      <div v-for="course in courses" class="col-md-4">
          <div class="thumbnail search-thumbnail" >
              <img style="height: 188px" v-show="!course.image" class="media-object" src="/static/image/demo-timg.jpg" />
              <img style="height: 188px" v-show="course.image" class="media-object" v-bind:src="course.image" />
              <div class="caption">
                  <div class="clearfix">
                      <span class="pull-right label label-primary info-label">
                         {{COURSE_LEVEL | optionKV(course.level)}}
                      </span>
                      <span class="pull-right label label-primary info-label">
                         {{COURSE_CHARGE | optionKV(course.charge)}}
                      </span>
                      <span class="pull-right label label-primary info-label">
                         {{COURSE_STATUS | optionKV(course.status)}}
                      </span>
                  </div>

                  <h3 class="search-title">
                      <a href="#" class="blue">{{course.name}}</a>
                  </h3>

                  <div v-for="teacher in teachers.filter(t=>{return t.id===course.teacherId})" class="profile-activity clearfix">
                      <div>
                          <img v-show="!teacher.image" class="pull-left" src="/ace/assets/images/avatars/avatar5.png">
                          <img v-show="teacher.image" class="pull-left" v-bind:src="teacher.image">
                          <a class="user" href="#"> {{teacher.name}} </a>
                          <br>
                          {{teacher.position}}
                      </div>
                  </div>

                  <p>
                      <span class="blue bolder bigger-150">{{course.price}}&nbsp;<i class="fa fa-rmb"></i></span>
                  </p>
                  <p>{{course.summary}}</p>
                  <p>
                      <span class="badge badge-info">{{course.id}}</span>
                      <span class="badge badge-info">排序:{{course.sort}}</span>
                      <span class="badge badge-info">时长:{{course.time | formatSecond}}</span>
                  </p>
                  <p>
                      <button v-on:click="toChapter(course)" class="btn btn-white btn-xs btn-info btn-round">
                          大章
                      </button>&nbsp;
                      <button v-on:click="toContent(course)" class="btn btn-white btn-xs btn-info btn-round">
                          内容
                      </button>&nbsp;
                      <button v-on:click="openSortModal(course)" class="btn btn-white btn-xs btn-info btn-round">
                          排序
                      </button>&nbsp;
                      <button v-on:click="editCourse(course)" class="btn btn-white btn-xs btn-info btn-round">
                          编辑
                      </button>&nbsp;
                      <button v-on:click="del(course.id)" class="btn btn-white btn-xs btn-warning btn-round">
                          删除
                      </button>
                  </p>
              </div>
          </div>
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
                              <label for="name" class="col-sm-2 control-label">
                                  分类
                              </label>
                              <div class="col-sm-10">
                                  <ul id="tree" class="ztree"></ul>
                              </div>
                          </div>

                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">封面</label>
                              <div class="col-sm-10">
                                  <big-file
                                        v-bind:input-id="'image-upload'"
                                        v-bind:text="'上传封面'"
                                        v-bind:suffixs="[ 'jpg' , 'jpeg' , 'png' ]"
                                        v-bind:use="FILE_USE.COURSE.key"
                                        v-bind:after-upload="afterUpload">
                                  </big-file>
                                  <div v-show="courseData.image" class="row">
                                      <div class="col-md-6">
                                          <!-- img-responsive 图片变成响应式自适应-->
                                          <img v-bind:src="courseData.image" class="img-responsive">
                                      </div>
                                  </div>
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">名称</label>
                              <div class="col-sm-10">
                                  <input v-model="courseData.name" type="text" class="form-control" id="name" placeholder="名称">
                              </div>
                          </div>

                          <div class="form-group">
                              <label  class="col-sm-2 control-label">讲师</label>
                              <div class="col-sm-10">
                                  <select v-model="courseData.teacherId" class="form-control">
                                      <option v-for="o in teachers" v-bind:value="o.id">{{o.name}}</option>
                                  </select>
                              </div>
                          </div>

                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">概述</label>
                              <div class="col-sm-10">
                                  <input v-model="courseData.summary" type="text" class="form-control" id="summary" placeholder="概述">
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">时间</label>
                              <div class="col-sm-10">
                                  <input v-model="courseData.time" type="text" class="form-control" id="time" placeholder="时间">
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">价格(元)</label>
                              <div class="col-sm-10">
                                  <input v-model="courseData.price" type="text" class="form-control" id="price" placeholder="价格(元)">
                              </div>
                          </div>

                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">级别</label>
                              <div class="col-sm-10">
                                  <select v-model="courseData.level" class="form-control">
                                      <option v-for="o in COURSE_LEVEL" v-bind:value="o.key">{{o.value}}</option>
                                  </select>
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">收费</label>
                              <div class="col-sm-10">
                                  <select v-model="courseData.charge" class="form-control">
                                      <option v-for="o in COURSE_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                                  </select>
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">状态</label>
                              <div class="col-sm-10">
                                  <select v-model="courseData.status" class="form-control">
                                      <option v-for="o in COURSE_STATUS" v-bind:value="o.key">{{o.value}}</option>
                                  </select>
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">报名数</label>
                              <div class="col-sm-10">
                                  <input v-model="courseData.enroll" type="text" class="form-control" id="enroll" placeholder="报名数">
                              </div>
                          </div>
                          <div class="form-group">
                              <label for="name" class="col-sm-2 control-label">顺序</label>
                              <div class="col-sm-10">
                                  <input v-model="courseData.sort" type="text" class="form-control" id="sort" disabled >
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

      <div id="course-sort-modal" class="modal fade" tabindex="-1" role="dialog">
          <div class="modal-dialog" role="document">
              <div class="modal-content">
                  <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">排序</h4>
                  </div>
                  <div class="modal-body">
                      <form class="form-horizontal">
                          <div class="form-group">
                              <label class="control-label col-lg-13">
                                  当前排序
                              </label>
                              <div class="col-lg-9">
                                <input class="form-control" v-model="sort.oldSort" name="oldSort" disabled>
                              </div>
                          </div>
                          <div class="form-group">
                              <label class="control-label col-lg-13">
                                  更新排序
                              </label>
                              <div class="col-lg-9">
                                  <input class="form-control" v-model="sort.newSort" name="newSort" >
                              </div>
                          </div>
                      </form>
                  </div>
                  <div class="modal-footer">
                      <button type="button" class="btn btn-white btn-default btn-round" data-dismiss="modal">
                          <i class="ace-icon fa fa-times"></i>
                          取消
                      </button>
                      <button type="button" class="btn btn-white btn-info btn-round" v-on:click="updateSort()">
                          <i class="ace-icon fa fa-plus blue"></i>
                          更新排序
                      </button>
                  </div>
              </div><!-- /.modal-content -->
          </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->



  </div>
</template>

<script>
import Pagination from "../../components/pagination";//导入分页组件
import BigFile from "../../components/big-file";//导入文件上传组件
export default {
    components: {Pagination,BigFile},//注册组件
    name: "business-course",
    data: function(){
      return{
          //用于save的 数据绑定
          courseData:{},
          //用于list的 数据绑定
          courses:[],
          COURSE_LEVEL:COURSE_LEVEL,
          COURSE_CHARGE:COURSE_CHARGE,
          COURSE_STATUS:COURSE_STATUS,
          FILE_USE: FILE_USE,
          categorys:[],
          tree:{},
          saveContentLabel:"",
          //排序
          sort:{
              id:"",//想要变更的课程的ID
              oldSort:0,
              newSort:0,
          },
          //页面一开始加载出来所有讲师的数组
          teachers:[],
          //定义一个文件数组
          files:[],
      }

    },
    //页面加载时触发的函数(页面初始化)
    mounted:function () {
        let _this=this;
        //_this.$refs.pagination.size="5"; //自定义设定每页查询的条数
        _this.allCategory();//所有分类
        _this.allTeacher();//所有讲师
        _this.listCourse(1);//调用list()方法，初始化list为第一页
        //调用父组件的方法$parent
        //sidebar 激活样式方法一
        //this.$parent.activeSidebar("business-course-sidebar");
    },
    methods:{
        /**
         * 新增按钮
         */
        addCourse(){
            //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
            let _this=this;
            //每次清空新增框里的内容
            _this.courseData={
                //新增条数的排序是当前分页的总条数+1
                sort:_this.$refs.pagination.total + 1
            };
            _this.tree.checkAllNodes(false);//点击新增时候清空下拉树的选项
            //养成习惯，每个都写
            //打开模态框
            //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
            // .modal()是modal内置方法，用于弹出或者关闭模态框
            $("#form-modal").modal("show");//打开show，关闭hide

        },

        /**
         * 修改按钮
         */
        editCourse(course){
            //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
            let _this=this;
            //养成习惯，每个都写
            //前面的courseData是上面data下的属性，后面的course是页面列表循环的数据
            _this.courseData =$.extend({},course);//jquery复制方法{目标}，数据源
            _this.listCatagory(course.id);//根据课程ID查找相关分类
            //打开模态框
            //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
            // .modal()是modal内置方法，用于弹出或者关闭模态框
            $("#form-modal").modal("show");//打开show，关闭hide

        },

        /**
         * 查询方法，加载页面数据list，增加参数page查询第几页
         * @param page
         */
        listCourse(page){
            let _this=this;
            Loading.show();
            //调用ajax.获取get请求路径。得到response变量。输出response变量
            //调用ajax.获取post请求路径。得到response变量。输出response变量
            _this.$ajax.post(process.env.VUE_APP_SERVER +"/business/admin/course/list",
                {
                 page:page,
                 size:_this.$refs.pagination.size,//在分页组件里面设置每页查询的条数
                }).then((response)=>{
                Loading.hide();
                //alert(response.data.content.list.1.createdAt);
                console.log("查询课程列表结果：",response);
                let  resp = response.data;//使用axios返回的response中data有多个数据，相当于后端的responseDto
                //将response的结果给courses:courses表示后台的数据
                _this.courses=resp.content.list;//this.xxx访问组件内的变量
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
              || !Validator.require(_this.courseData.name, "名称")
              || !Validator.length(_this.courseData.name, "名称", 1, 50)
              || !Validator.length(_this.courseData.summary, "概述", 1, 2000)
              || !Validator.length(_this.courseData.image, "封面", 1, 250)
            ) {
                return;
            }

            Loading.show();
            //获取输入框被勾选 或 未勾选的节点集合
            let categorys = _this.tree.getCheckedNodes();
            if(Tool.isEmpty(categorys)){
                Toast.warning("请选择分类");
                return;
            }
            console.log(categorys);
            _this.courseData.categorys = categorys;
            //调用ajax.获取get请求路径。得到response变量。输出response变量
            //调用ajax.获取post请求路径。得到response变量。输出response变量
            _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/save",_this.courseData).then((response)=>{
                Loading.hide();
                console.log("保存结果：",response);
                let  resp = response.data;
                if(resp.success){//如果保存成功就关闭modal,并且重新查询数据list为第一页
                    $("#form-modal").modal("hide");
                    _this.listCourse(1);
                    Toast.success("保存成功");
                }else{
                    Toast.warning(resp.message);
                }
            })

        },
        /**
         * 删除方法
         * @param id数据的主键
         */
        del(id){
            let _this=this;

            Confirm.show("删除课程后不可恢复，确认删除?",function () {
                Loading.show();
                _this.$ajax.delete(process.env.VUE_APP_SERVER + "/business/admin/course/delete/"+id).then((response)=>{
                    Loading.hide();
                    console.log("删除课程列表结果：",response);
                    let  resp = response.data;
                    if(resp.success){//如果判断成功重新查询数据list为第一页
                        Toast.success("删除成功");
                        _this.listCourse(1);
                    }
                });
            })
        },
        /**
         * 点击【大章】
         */
        toChapter(course){
            //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
            let _this=this;
            //SessionStorage.set(course,course);不知道为什么没有存进去,所以下面用了原生的H5缓存
            sessionStorage.setItem(SESSION_KEY_COURSE,JSON.stringify(course)) // 转为string存储
            //let courseObj = JSON.parse(sessionStorage.getItem('course'))  //sring转为对象
            //alert(courseObj.name);
            _this.$router.push("/business/chapter");

        },

        /**
         * 点击进去内容页面
         */
        toContent(course){
            debugger
            let _this = this;
            sessionStorage.setItem(SESSION_KEY_COURSE,JSON.stringify(course))
            _this.$router.push("/business/content");
        },

        /**
         * 查询所有分类的方法
         */
        allCategory(){
            let _this=this;
            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER +"/business/admin/category/all",).then((response)=>{
                Loading.hide();
                //console.log("查询分类列表结果：",response);
                let  resp = response.data;//使用axios返回的response中data有多个数据，相当于后端的responseDto
                //将response的结果给categorys:categorys表示后台的数据
                _this.categorys=resp.content;//this.xxx访问组件内的变量
                _this.initTree();
            })
        },

         initTree(){
            let _this = this;
            let setting={
                check: {
                    enable: true
                },
                data: {
                    simpleData: {
                        idKey:"id",//对应查询的字段id
                        pIdKey:"parent",//对应查询的字段parent
                        rootPId:"00000000",
                        enable: true
                    }
                }
            };

             // var zNodes =[
             //     { id:1, pId:0, name:"随意勾选 1", open:true},
             //     { id:11, pId:1, name:"随意勾选 1-1", open:true}];
             let zNodes =_this.categorys;

             _this.tree = $.fn.zTree.init($("#tree"), setting, zNodes);
             // 展开所有的节点
             //_this.tree.expandAll(true);

        },

        /**
         *查询分类
         * @param course
         */
        listCatagory(courseId){
            let _this=this;
            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list-category/' +courseId).then((res)=>{
                Loading.hide();
                console.log("查找分类下所有课程:", res);
                let response = res.data;
                let categorys = response.content;
                //勾选查询的分类
                _this.tree.checkAllNodes(false);
                    for (let i = 0; i <categorys.length ; i++) {
                        //getNodeByParam:根据节点数据的属性搜索，获取条件完全匹配的节点数据 JSON 对象
                        let node = _this.tree.getNodeByParam("id", categorys[i].categoryId);
                        _this.tree.checkNode(node,true);
                    }

            })
        },

        /**
         * 打开排序的模态框
         * @param course
         */
        openSortModal(course){
            let _this = this;
            _this.sort = {
                id : course.id,
                oldSort:course.sort,
                newSort:course.sort,
            };
           $("#course-sort-modal").modal("show");
        },

        /**
         * 修改排序
         * @param
         */
        updateSort(){
            let _this = this;
            if(_this.sort.newSort == _this.sort.oldSort){
                Toast.warning("排序未进行变化");
                return
            }
            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER +"/business/admin/course/sort",_this.sort).then((res) => {
                let response = res.data;
                if (response.success) {
                    Toast.success("更新排序成功");
                    $("#course-sort-modal").modal("hide");
                    _this.listCourse(1);
                } else {
                    Toast.error("更新排序失败");
                }
            });
        },

        /**
         * 查询所有分类的方法
         */
        allTeacher(){
            let _this=this;
            Loading.show();
            _this.$ajax.post(process.env.VUE_APP_SERVER +"/business/admin/teacher/all",).then((response)=>{
                Loading.hide();
                //console.log("查询分类列表结果：",response);
                let  resp = response.data;//使用axios返回的response中data有多个数据，相当于后端的responseDto
                //将response的结果给categorys:categorys表示后台的数据
                _this.teachers=resp.content;//this.xxx访问组件内的变量
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
            _this.courseData.image = image;
        },



    }
}
</script>

<style scoped>
    .caption h3{
        font-size: 20px;
    }

    @media (max-width: 1199px) {
        .caption h3 {
            font-size: 16px;
        }
    }
</style>
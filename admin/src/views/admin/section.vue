<template>
  <div>
    <h4>
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink">{{course.name}}</router-link>
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/chapter" class="pink">{{chapter.name}}</router-link>
    </h4>
    <P>
        <button v-on:click="addSection()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-edit"></i>
            新增
        </button>
        &nbsp;
        <button v-on:click="listSection()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
        </button>
    </P>
<!--  v-bind:list="list"   前面的list是分页组件暴露出来的list方法，后面的listSection是section组件的list方法    -->
    <pagination ref="pagination" v-bind:list="listSection"></pagination>
    <table id="simple-table" class="table  table-bordered table-hover">
         <thead>
            <tr>
                <th class="center">ID</th>
                <th class="center">标题</th>
                <th class="center">VOD</th>
                <th class="center">时长</th>
                <th class="center">收费</th>
                <th class="center">顺序</th>
                <th class="center">操作</th>
            </tr>
        </thead>

        <tbody>
            <!-- 数据绑定 -->
            <tr v-for="section in sections">
                <td>{{section.id}}</td>
                <td>{{section.title}}</td>
                <td>{{section.vod}}</td>
                <td>{{section.time | formatSecond}}</td>
                <td>{{SECTION_CHARGE | optionKV(section.charge)}}</td>
<!--                <td>{{CHARGE | optionKV(section.charge)}}</td>-->
                <td>{{section.sort}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-on:click="play(section)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-video-camera bigger-120"></i>
                        </button>

                        <button v-on:click="editSection(section)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>

                        <button v-on:click="del(section.id)" class="btn btn-xs btn-danger">
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
                                  <label for="title" class="col-sm-2 control-label">标题</label>
                                  <div class="col-sm-10">
                                      <input v-model="sectionData.title" type="text" class="form-control" id="title" placeholder="标题">
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label  class="col-sm-2 control-label">课程</label>
                                  <div class="col-sm-10">
                                      <p class="form-control-static">{{course.name}}</p>
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label  class="col-sm-2 control-label">大章</label>
                                  <div class="col-sm-10">
                                      <p class="form-control-static">{{chapter.name}}</p>
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label  class="col-sm-2 control-label">视频</label>
                                  <div class="col-sm-10">
                                      <vod
                                            v-bind:input-id="'video-upload'"
                                            v-bind:text="'上传大视频'"
                                            v-bind:suffixs="['mp4']"
                                            v-bind:use="FILE_USE.COURSE.key"
                                            v-bind:after-upload="afterUpload">
                                      </vod>
                                      <div v-show="sectionData.video" class="row">
                                          <div class="col-md-9">
                                              <player v-bind:player-id="'form-player-div'" ref="player"></player>
                                              <!-- controls 属性规定浏览器应该为视频提供播放控件。-->
                                              <video v-bind:src="sectionData.video" id="video" controls="controls" class="hidden"></video>
                                          </div>
                                      </div>
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label for="time" class="col-sm-2 control-label">时长</label>
                                  <div class="col-sm-10">
                                      <input v-model="sectionData.time" type="text" class="form-control" id="time" placeholder="时长">
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label for="time" class="col-sm-2 control-label">视频地址</label>
                                  <div class="col-sm-10">
                                      <input v-model="sectionData.video" type="text" class="form-control" id="videov" disabled >
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label for="time" class="col-sm-2 control-label">VOD</label>
                                  <div class="col-sm-10">
                                      <input v-model="sectionData.vod" type="text" class="form-control" id="vod" disabled >
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label class="col-sm-2 control-label">收费</label>
                                  <div class="col-sm-10">
                                      <select v-model="sectionData.charge" class="form-control">
                                          <option v-for="o in SECTION_CHARGE" v-bind:value="o.key">{{o.value}}</option>
                                      </select>
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label for="sort" class="col-sm-2 control-label">顺序</label>
                                  <div class="col-sm-10">
                                      <input v-model="sectionData.sort" type="text" class="form-control" id="sort" placeholder="顺序">
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
        <!-- 带有弹出框的播放器     -->
      <modal-player ref="modalPlayer"></modal-player>
  </div>
</template>

<script>
    import Pagination from "../../components/pagination";//导入组件
    import BigFile from "../../components/big-file";
    import Vod from "../../components/vod";
    import Player from "../../components/player";
    import ModalPlayer from "../../components/modal-player";
    //导入大文件上传组件
    export default {
        components: {ModalPlayer, Player, Vod, Pagination, BigFile},//注册组件
        name: "business-section",
        data: function(){
          return{
              //用于save的 数据绑定
              sectionData:{},
              //用于list的 数据绑定
              sections:[],
              SECTION_CHARGE:SECTION_CHARGE,
              course:{},
              chapter:{},
              FILE_USE: FILE_USE,
              // CHARGE:CHARGE,
          }
        },
        //页面加载时触发的函数(页面初始化)
        mounted:function () {
            let _this=this;
           // _this.$refs.pagination.size="5"; //自定义设定每页查询的条数
            let  course = SessionStorage.get(SESSION_KEY_COURSE) || {};
            let  chapter = SessionStorage.get(SESSION_KEY_CHAPTER) || {};
            if(Tool.isEmpty(course) || Tool.isEmpty(chapter)){
                _this.$router.push("/welcome");
            }
            _this.course = course;
            _this.chapter = chapter;
            _this.chapter = chapter;
            _this.listSection(1);//调用list()方法，初始化list为第一页
            //调用父组件的方法$parent
            //sidebar 激活样式方法一
            this.$parent.activeSidebar("business-course-sidebar");
        },
        methods:{
            /**
             * 新增按钮
             */
            addSection(){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                _this.sectionData={};//每次清空新增框里的内容
                //养成习惯，每个都写
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide

            },

            /**
             * 修改按钮
             */
            editSection(section){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                //养成习惯，每个都写
                //前面的sectionData是上面data下的属性，后面的section是页面列表循环的数据
                _this.sectionData =$.extend({},section);//jquery复制方法{目标}，数据源
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide

            },

            /**
             * 查询方法，加载页面数据list，增加参数page查询第几页
             * @param page
             */
            listSection(page){
                let _this=this;
                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                //size:在分页组件里面设置每页查询的条数
                _this.$ajax.post(process.env.VUE_APP_SERVER +"/business/admin/section/list",
                    {
                     page:page,
                     size:_this.$refs.pagination.size,
                     courseId : _this.course.id,
                     chapterId : _this.chapter.id,
                    }).then((response)=>{
                    Loading.hide();
                    //console.log("查询小节列表结果：",response);
                    let  resp = response.data;//使用axios返回的response中data有多个数据，相当于后端的responseDto
                    //将response的结果给sections:sections表示后台的数据
                    _this.sections=resp.content.list;//this.xxx访问组件内的变量
                    _this.$refs.pagination.render(page,resp.content.total);//获取分页页码重新渲染
                });
            },
            /**
             * 保存方法
             * @param pagesave
             */
            save(){
              debugger;
                let _this=this;
                //_this.sectionData.video = "";
                // 保存校验
                if (1 != 1
                  || !Validator.require(_this.sectionData.title, "标题")
                  || !Validator.length(_this.sectionData.title, "标题", 1, 50)
                  || !Validator.length(_this.sectionData.video, "视频", 1, 600)
                ) {
                    return;
                }
                _this.sectionData.courseId = _this.course.id;
                _this.sectionData.chapterId = _this.chapter.id;


                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/section/save",
                    _this.sectionData).then((response)=>{
                    Loading.hide();
                    console.log("保存小节列表结果：",response);
                    let  resp = response.data;
                    if(resp.success){//如果保存成功就关闭modal,并且重新查询数据list为第一页
                        $("#form-modal").modal("hide");
                        _this.listSection(1);
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

                Confirm.show("删除小节后不可恢复，确认删除?",function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + "/business/admin/section/delete/"+id).then((response)=>{
                        Loading.hide();
                        console.log("删除小节列表结果：",response);
                        let  resp = response.data;
                        if(resp.success){//如果判断成功重新查询数据list为第一页
                            Toast.success("删除成功");
                            _this.listSection(1);
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
                let  video  =resp.content.path;//从后台取出来视频路径
                let  vod    =resp.content.vod;//从后台取出vod视频ID
                _this.sectionData.video = video;//返回给前台
                _this.sectionData.vod = vod;//返回给前台
                _this.getTime();
                _this.$refs.player.playUrl(video);
            },
            /**
             * 获取时长
             */
            getTime(){
                let _this = this;
                setTimeout(function(){
                    //getElementById() 方法可返回对拥有指定 ID 的第一个对象的引用。
                    let ele = document.getElementById("video");
                    //duration 属性返回当前音频/视频的长度，以秒计。10进制的数
                    _this.sectionData.time=parseInt(ele.duration,10)
                },1000);

            },
            /**
             * 播放视频
             * @param section
             */
            play(section) {
                let _this = this;
                _this.$refs.modalPlayer.playVod(section.vod);
            }
        }
    }
</script>

<style scoped>
    video{
        width: 100%;
        height: auto;
        margin-top: 10px;
    }
</style>
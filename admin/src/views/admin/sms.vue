<template>
  <div>
    <P>
<!--        <button v-on:click="addSms()" class="btn btn-white btn-default btn-round">-->
<!--            <i class="ace-icon fa fa-edit"></i>-->
<!--            新增-->
<!--        </button>-->
        &nbsp;
        <button v-on:click="listSms()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
        </button>
    </P>
<!--  v-bind:list="list"   前面的list是分页组件暴露出来的list方法，后面的listSms是sms组件的list方法    -->
    <pagination ref="pagination" v-bind:list="listSms" v-bind:itemCount="8"></pagination>
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
                    <th class="center">手机号</th>
                    <th class="center">验证码</th>
                    <th class="center">用途</th>
                    <th class="center">生成时间</th>
                    <th class="center">状态</th>
<!--                <th class="center">操作</th>-->
            </tr>
        </thead>

        <tbody>
            <!-- 数据绑定 -->
            <tr v-for="sms in smss">
                    <td>{{sms.id}}</td>
                    <td>{{sms.mobile}}</td>
                    <td>{{sms.code}}</td>
                    <td>{{SMS_USE | optionKV(sms.use)}}</td>
                    <td>{{sms.at}}</td>
                    <td>{{SMS_STATUS | optionKV(sms.status)}}</td>
<!--                <td>-->
<!--                    <div class="hidden-sm hidden-xs btn-group">-->

<!--                        <button v-on:click="editSms(sms)" class="btn btn-xs btn-info">-->
<!--                            <i class="ace-icon fa fa-pencil bigger-120"></i>-->
<!--                        </button>-->

<!--                        <button v-on:click="del(sms.id)" class="btn btn-xs btn-danger">-->
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
<!--                                  <label class="col-sm-2 control-label">手机号</label>-->
<!--                                  <div class="col-sm-10">-->
<!--                                      <input v-model="smsData.mobile" type="text" class="form-control" id="mobile" placeholder="手机号">-->
<!--                                  </div>-->
<!--                              </div>-->
<!--                              <div class="form-group">-->
<!--                                  <label for="name" class="col-sm-2 control-label">验证码</label>-->
<!--                                  <div class="col-sm-10">-->
<!--                                      <input v-model="smsData.code" type="text" class="form-control" id="code" placeholder="验证码">-->
<!--                                  </div>-->
<!--                              </div>-->
<!--                              <div class="form-group">-->
<!--                                  <label for="name" class="col-sm-2 control-label">用途</label>-->
<!--                                  <div class="col-sm-10">-->
<!--                                      <select v-model="smsData.use" class="form-control">-->
<!--                                          <option v-for="o in SMS_USE" v-bind:value="o.key">{{o.value}}</option>-->
<!--                                      </select>-->
<!--                                  </div>-->
<!--                              </div>-->
<!--                              <div class="form-group">-->
<!--                                  <label for="name" class="col-sm-2 control-label">生成时间</label>-->
<!--                                  <div class="col-sm-10">-->
<!--                                      <input v-model="smsData.at" type="text" class="form-control" id="at" placeholder="生成时间">-->
<!--                                  </div>-->
<!--                              </div>-->
<!--                                <div class="form-group">-->
<!--                                    <label for="name" class="col-sm-2 control-label">状态</label>-->
<!--                                    <div class="col-sm-10">-->
<!--                                        <select v-model="smsData.status" class="form-control">-->
<!--                                            <option v-for="o in SMS_STATUS" v-bind:value="o.key">{{o.value}}</option>-->
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
        name: "business-sms",
        data: function(){
          return{
              //用于save的 数据绑定
              smsData:{},
              //用于list的 数据绑定
              smss:[],
              SMS_USE: SMS_USE,
              SMS_STATUS: SMS_STATUS,
          }
        },
        //页面加载时触发的函数(页面初始化)
        mounted:function () {
            let _this=this;
           // _this.$refs.pagination.size="5"; //自定义设定每页查询的条数
            _this.listSms(1);//调用list()方法，初始化list为第一页
            //调用父组件的方法$parent
            //sidebar 激活样式方法一
            //this.$parent.activeSidebar("business-sms-sidebar");
        },
        methods:{
            /**
             * 新增按钮
             */
            addSms(){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                _this.smsData={};//每次清空新增框里的内容
                //养成习惯，每个都写
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide

            },

            /**
             * 修改按钮
             */
            editSms(sms){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                //养成习惯，每个都写
                //前面的smsData是上面data下的属性，后面的sms是页面列表循环的数据
                _this.smsData =$.extend({},sms);//jquery复制方法{目标}，数据源
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide

            },

            /**
             * 查询方法，加载页面数据list，增加参数page查询第几页
             * @param page
             */
            listSms(page){
                let _this=this;
                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                _this.$ajax.post(process.env.VUE_APP_SERVER +"/business/admin/sms/list",
                    {
                     page:page,
                     size:_this.$refs.pagination.size,//在分页组件里面设置每页查询的条数
                    }).then((response)=>{
                    Loading.hide();
                    //console.log("查询验证码列表结果：",response);
                    let  resp = response.data;//使用axios返回的response中data有多个数据，相当于后端的responseDto
                    //将response的结果给smss:smss表示后台的数据
                    _this.smss=resp.content.list;//this.xxx访问组件内的变量
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
                  || !Validator.require(_this.smsData.mobile, "手机号")
                  || !Validator.length(_this.smsData.mobile, "手机号", 1, 50)
                  || !Validator.require(_this.smsData.code, "验证码")
                  || !Validator.require(_this.smsData.use, "用途")
                  || !Validator.require(_this.smsData.at, "生成时间")
                  || !Validator.require(_this.smsData.status, "状态")
                ) {
                    return;
                }

                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/sms/save",
                    _this.smsData).then((response)=>{
                    Loading.hide();
                    console.log("保存验证码列表结果：",response);
                    let  resp = response.data;
                    if(resp.success){//如果保存成功就关闭modal,并且重新查询数据list为第一页
                        $("#form-modal").modal("hide");
                        _this.listSms(1);
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

                Confirm.show("删除验证码后不可恢复，确认删除?",function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + "/business/admin/sms/delete/"+id).then((response)=>{
                        Loading.hide();
                        console.log("删除验证码列表结果：",response);
                        let  resp = response.data;
                        if(resp.success){//如果判断成功重新查询数据list为第一页
                            Toast.success("删除成功");
                            _this.listSms(1);
                        }
                    });
                })
            }
        }
    }
</script>

<style scoped>

</style>
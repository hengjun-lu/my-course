<template>
  <div>
    <div class="row">
      <div class="col-md-6">
          <P>
              <button v-on:click="addCategory1()" class="btn btn-white btn-default btn-round">
                  <i class="ace-icon fa fa-edit"></i>
                  新增一级分类
              </button>
              &nbsp;
              <button v-on:click="allCategory()" class="btn btn-white btn-default btn-round">
                  <i class="ace-icon fa fa-refresh"></i>
                  刷新
              </button>
          </P>
          <!--  v-bind:list="list"   前面的list是分页组件暴露出来的list方法，后面的listCategory是category组件的list方法    -->
          <table id="level1-table" class="table  table-bordered table-hover">
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
                  <th class="center">名称</th>
                  <th class="center">排序</th>
                  <th class="center">操作</th>
              </tr>
              </thead>

              <tbody>
              <!-- 数据绑定 -->
              <!-- 动态class：使用v-bind:class=json表达式,key就是样式，value是boolean，为ture时，表示key的样式生效。可以和原生的class并存-->
              <tr v-for="category in level1" v-on:click="onClickLevel1(category)" v-bind:class="{'active':category.id === active.id}">
                  <td>{{category.id}}</td>
                  <td>{{category.name}}</td>
                  <td>{{category.sort}}</td>
                  <td>
                      <div class="hidden-sm hidden-xs btn-group">

                          <button v-on:click="editCategory(category)" class="btn btn-xs btn-info">
                              <i class="ace-icon fa fa-pencil bigger-120"></i>
                          </button>

                          <button v-on:click="del(category.id)" class="btn btn-xs btn-danger">
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
      </div>
      <div class="col-md-6">
            <P>
                <button v-on:click="addCategory2()" class="btn btn-white btn-default btn-round">
                    <i class="ace-icon fa fa-edit"></i>
                    新增二级分类
                </button>
            </P>
            <!--  v-bind:list="list"   前面的list是分页组件暴露出来的list方法，后面的listCategory是category组件的list方法    -->
            <table id="level2-table" class="table  table-bordered table-hover">
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
                    <th class="center">名称</th>
                    <th class="center">排序</th>
                    <th class="center">操作</th>
                </tr>
                </thead>

                <tbody>
                <!-- 数据绑定 -->
                <tr v-for="category in level2" >
                    <td>{{category.id}}</td>
                    <td>{{category.name}}</td>
                    <td>{{category.sort}}</td>
                    <td>
                        <div class="hidden-sm hidden-xs btn-group">

                            <button v-on:click="editCategory(category)" class="btn btn-xs btn-info">
                                <i class="ace-icon fa fa-pencil bigger-120"></i>
                            </button>

                            <button v-on:click="del(category.id)" class="btn btn-xs btn-danger">
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
                                  <label for="name" class="col-sm-2 control-label">父级分类</label>
                                  <div class="col-sm-10">
                                      <p class="form-control-static">{{active.name || '无'}}</p>
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label for="name" class="col-sm-2 control-label">名称</label>
                                  <div class="col-sm-10">
                                      <input v-model="categoryData.name" type="text" class="form-control" id="name" placeholder="名称">
                                  </div>
                              </div>
                              <div class="form-group">
                                  <label for="name" class="col-sm-2 control-label">排序</label>
                                  <div class="col-sm-10">
                                      <input v-model="categoryData.sort" type="text" class="form-control" id="sort" placeholder="排序">
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
    import Pagination from "../../components/pagination";//导入组件
    export default {
        name: "business-category",
        data: function(){
          return{
              //用于save的 数据绑定
              categoryData:{},
              //用于list的 数据绑定
              categorys:[],
              level1:[],//层级分类1级
              level2:[],//层级分类2级
              active:{},
          }
        },
        //页面加载时触发的函数(页面初始化)
        mounted:function () {
            let _this=this;
            _this.allCategory();//调用list()方法，初始化list为第一页
        },
        methods:{
            /**
             * 新增一级分类
             */
            addCategory1(){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                _this.active={};
                _this.level2=[];
                _this.categoryData={
                    parent:"00000000"
                };//每次清空新增框里的内容
                $("#form-modal").modal("show");//打开show，关闭hide
            },
            /**
             * 新增二级分类
             */
            addCategory2(){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                if(Tool.isEmpty(_this.active)){
                    Toast.warning("请先选择一级分类")
                    return;
                }
                _this.categoryData = {
                    parent:_this.active.id,

                }
                $("#form-modal").modal("show");//打开show，关闭hide
            },


            /**
             * 修改按钮
             */
            editCategory(category){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                //养成习惯，每个都写
                //前面的categoryData是上面data下的属性，后面的category是页面列表循环的数据
                _this.categoryData =$.extend({},category);//jquery复制方法{目标}，数据源
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide

            },

            /**
             * 查询方法，加载页面数据list，增加参数page查询第几页
             * @param page
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
                    //将所有记录格式化为树形结构
                    _this.level1=[];
                    for (let i = 0; i <_this.categorys.length; i++) {
                        let  c= _this.categorys[i]
                        if(c.parent === '00000000'){
                            _this.level1.push(c)
                            for (let j = 0; j < _this.categorys.length; j++) {
                                let child = _this.categorys[j]
                                //二级分类的父ID等于一级分类的ID
                                if(child.parent === c.id ){
                                    if(Tool.isEmpty(c.children)){
                                        c.children = [];
                                    }
                                    c.children.push(child)
                                }
                            }
                        }
                    }
                    _this.level2 =[];
                    //对当前一级分类中选中的表格触发一次点击事件，用以刷新耳机列表
                    //注意：界面渲染需要等到vue中绑定好变量才做，所以加上演示100ms
                    setTimeout(function(){
                        $("tr.active").trigger("click");
                    },100);

                });
            },
            /**
             * 保存方法
             * @param pagesave
             */
            save(){
                let _this=this;
                console.log(_this.categoryData.parent);
                // 保存校验
                if (1 != 1
                  || !Validator.require(_this.categoryData.parent, "父id")
                  || !Validator.require(_this.categoryData.name, "名称")
                  || !Validator.length(_this.categoryData.name, "名称", 1, 50)
                ) {
                    return;
                }

                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/category/save",
                    _this.categoryData).then((response)=>{
                    Loading.hide();
                    console.log("保存分类列表结果：",response);
                    let  resp = response.data;
                    if(resp.success){//如果保存成功就关闭modal,并且重新查询数据list为第一页
                        $("#form-modal").modal("hide");
                        _this.allCategory();
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

                Confirm.show("删除分类后不可恢复，确认删除?",function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + "/business/admin/category/delete/"+id).then((response)=>{
                        Loading.hide();
                        console.log("删除分类列表结果：",response);
                        let  resp = response.data;
                        if(resp.success){//如果判断成功重新查询数据list为第一页
                            Toast.success("删除成功");
                            _this.allCategory();
                        }
                    });
                })
            },

            //分类二级列表
            onClickLevel1(category){
                let _this = this;
                _this.active = category;
                _this.level2 = category.children;
            }
        }
    }
</script>

<style scoped>
    .active td{
        background-color: #d6e9c6 !important;;
    }
</style>
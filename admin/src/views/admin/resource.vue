<template>
  <div>
    <P>
        <button v-on:click="listResource()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-refresh"></i>
            刷新
        </button>
    </P>
      <div class="row">
          <div class="col-md-6">
              <textarea id="resource-textarea" class="form-control" v-model="resourcesStr" name="resource" rows="10"></textarea>

              <br>
              <button id="save-btn" type="button" class="btn btn-primary" v-on:click="save()">
                  保存
              </button>
          </div>
          <div class="col-md-6">
              <ul id="tree" class="ztree"></ul>
          </div>
      </div>
  </div>
</template>

<script>
    import Pagination from "../../components/pagination";//导入组件
    export default {
        components: {Pagination},//注册组件
        name: "system-resource",
        data: function(){
          return{
              //用于save的 数据绑定
              resourceData:{},
              //用于list的 数据绑定
              resources:[],
              //绑定多行文本框的内容
              resourcesStr:"",
              tree:{},
          }
        },
        //页面加载时触发的函数(页面初始化)
        mounted:function () {
            let _this=this;
           // _this.$refs.pagination.size="5"; //自定义设定每页查询的条数
            _this.listResource();//调用list()方法，初始化list为第一页
            //调用父组件的方法$parent
            //sidebar 激活样式方法一
            //this.$parent.activeSidebar("system-resource-sidebar");
        },
        methods:{
            /**
             * 新增按钮
             */
            addResource(){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                _this.resourceData={};//每次清空新增框里的内容
                //养成习惯，每个都写
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide

            },

            /**
             * 修改按钮
             */
            editResource(resource){
                //_this只是一个变量名，this代表父函数，如果在子函数还用this，this的指向就变成子函数了，_this就是用来存储指向的。
                let _this=this;
                //养成习惯，每个都写
                //前面的resourceData是上面data下的属性，后面的resource是页面列表循环的数据
                _this.resourceData =$.extend({},resource);//jquery复制方法{目标}，数据源
                //打开模态框
                //$(".modal")里的model是css的选择器，模态框代码里有class=”modal“样式;
                // .modal()是modal内置方法，用于弹出或者关闭模态框
                $("#form-modal").modal("show");//打开show，关闭hide

            },

            /**
             * 查询方法，加载页面数据list，增加参数page查询第几页
             * @param page
             */
            listResource(page){
                let _this=this;
                Loading.show();
                _this.$ajax.get(process.env.VUE_APP_SERVER +"/system/admin/resource/load-tree").then((res)=>{
                    Loading.hide();
                    //console.log("查询资源列表结果：",response);
                    let response = res.data;
                    _this.resources = response.content;
                    //得到数据后初始化树形
                    _this.initTree();
                });
            },
            /**
             * 保存方法
             * @param pagesave
             */
            save(){
                let _this=this;

                if (Tool.isEmpty(_this.resourcesStr)) {
                    Toast.warning("资源不能为空");
                    return;
                }
                //转成json
                let json = JSON.parse(_this.resourcesStr);
                Loading.show();
                //调用ajax.获取get请求路径。得到response变量。输出response变量
                //调用ajax.获取post请求路径。得到response变量。输出response变量
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/system/admin/resource/save",
                    json).then((response)=>{
                    Loading.hide();
                    console.log("保存资源列表结果：",response);
                    let  resp = response.data;
                    if(resp.success){//如果保存成功就关闭modal,并且重新查询数据list为第一页
                        $("#form-modal").modal("hide");
                        _this.listResource(1);
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

                Confirm.show("删除资源后不可恢复，确认删除?",function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + "/system/admin/resource/delete/"+id).then((response)=>{
                        Loading.hide();
                        console.log("删除资源列表结果：",response);
                        let  resp = response.data;
                        if(resp.success){//如果判断成功重新查询数据list为第一页
                            Toast.success("删除成功");
                            _this.listResource(1);
                        }
                    });
                })
            },


            initTree(){
                let _this = this;
                let setting={
                    data: {
                        simpleData: {
                            idKey:"id",//对应查询的字段id
                            pIdKey:"parent",//对应查询的字段parent
                            rootPId:"",
                            enable: true
                        }
                    }
                };
                _this.zTree = $.fn.zTree.init($("#tree"), setting, _this.resources);
                // 展开所有的节点
                _this.zTree.expandAll(true);

            },
        }
    }
</script>

<style scoped>

</style>
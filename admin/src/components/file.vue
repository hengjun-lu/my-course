<template>
    <div>
        <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-upload"></i>
            {{text}}
        </button>
        <input  class="hidden" type="file" ref="file"  v-bind:id="inputId+'-input'" v-on:change="uploadFile()">
    </div>
</template>

<script>
    export default {
        name: 'file',
        //props:用于父子组件传递参数，可以理解为组件中暴露出去的可以配置的属性
        props: {
            text:{
                default:"上传文件"
            },
            inputId:{
                default:"file-upload"
            },
            suffixs:{
                default:[]
            },
            afterUpload: {
                type: Function,
                default: null
            },
            use:{
                default:""
            },
            itemCount: Number // 显示的页码数，比如总共有100页，只显示10页，其它用省略号表示
        },
        data: function () {
            return {
            }
        },
        methods: {

            /**
             * 文件上传功能
             */
            uploadFile(){
                let _this = this;
                //1.将form表单元素的name与value进行组合，实现表单数据的序列化，从而减少表单元素的拼接，提高工作效率。
                //2.异步上传文件
                //通过FormData构造函数创建一个空对象,模拟表单
                let formData = new window.FormData;
                //document.querySelector("#deom")获取文档中 id="demo" 的元素：
                // formData.append('file',document.querySelector("#file-upload-input").files[0]);
                //拿到$refs这个变量可以点出来所有的别名
                let file = _this.$refs.file.files[0];

                //判断文件的格式
                let suffixs=_this.suffixs;
                let fileName = file.name;
                let suffix = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length).toLowerCase();
                let validateSuffix = false;
                for (let i = 0; i <suffixs.length ; i++) {
                    if(suffixs[i].toLowerCase() === suffix ){
                        validateSuffix=true;
                        break
                    }
                }
                if(!validateSuffix){
                    Toast.warning("文件格式不正确，只支持上传" + suffixs.join(","));
                    // $("#" + _this.inputId + "-input").trigger("click");
                    $("#" + _this.inputId + "-input").val("");
                    return;
                }

                formData.append('file',file);
                formData.append('use',_this.use);
                Loading.show();
                // key:"file"必须和后端controller参数名一样
                // 单文件本地上传接口：process.env.VUE_APP_SERVER + '/file/admin/upload'
                // 单文件阿里云OSS上传接口:process.env.VUE_APP_SERVER + '/file/admin/upload'
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload',formData).then((response)=>{
                    Loading.hide();
                    let  resp = response.data;
                    console.log("文件上传成功地址:",resp);
                    //为组件增加上传成功后的回调函数
                    //与组件不相关的业务代码应该由外部通过回调函数传进来
                    _this.afterUpload(resp);
                    //上传完成后清空
                    $("#" + _this.inputId + "-input").val("");

                })
            },

            /**
             * 上传文件的按钮
             */
            selectFile(){
                let _this = this;
                //trigger() 方法触发被选元素上指定的事件以及事件的默认行为（比如表单提交）。
                $("#" + _this.inputId + "-input").trigger("click");
            }

        }
    }
</script>

<style scoped>
    .pagination {
        vertical-align: middle !important;
        font-size: 16px;
        margin-top: 0;
        margin-bottom: 10px;
    }

    .pagination button {
        margin-right: 5px;
    }

    .btn-primary.active {
        background-color: #2f7bba !important;
        border-color: #27689d !important;
        color: white !important;
        font-weight: 600;
    }

    /*.pagination select {*/
    /*vertical-align: middle !important;*/
    /*font-size: 16px;*/
    /*margin-top: 0;*/
    /*}*/
</style>
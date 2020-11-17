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
        name: 'big-file',
        //props:用于父子组件传递参数，可以理解为组件中暴露出去的可以配置的属性
        props: {
            text:{
                default:"上传大文件"
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
            shardSize:{
                default:50 * 1024
            },
            url:{
                default:"oss-append"
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
            console.log(JSON.stringify(file));
            //生成文件标识，用于标识多次上传的文件是否为同一个文件
            //利用md5工具类中的hex_md5方法对文件进行加密
            // let key = hex_md5(file);
            let key = hex_md5(file.name + file.size + file.type);
            let key10 = parseInt(key,16);
            let key62 = Tool._10to62(key10);
            console.log(key,key10,key62);

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

            //文件分片
            //let shardSize= 30 * 1024 * 1024;//定义一个分片的大小是50M
            let shardSize = _this.shardSize;
            let shardIndex = 1;//分片索引 1表示第一个分片
            //获取文件的大小
            let size = file.size;
            //计算文件总片数
            let shardTotal = Math.ceil(size / shardSize);
            //formData.append(name, value);name:数据对应的表单名称;value:表单的值
            // formData.append('shard',fileShard);
            // formData.append('shardIndex',shardIndex);
            // formData.append('shardSize',shardSize);
            // formData.append('shardTotal',shardTotal);
            // formData.append('use',_this.use);
            // formData.append('name',name);
            // formData.append('suffix',suffix);
            // formData.append('size',size);
            // formData.append('key',key62);
            // Loading.show();
            // // key:"file"必须和后端controller参数名一样
            // _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload',formData).then((response)=>{
            //     Loading.hide();
            //     let  resp = response.data;
            //     console.log("文件上传成功地址:",resp);
            //     //为组件增加上传成功后的回调函数
            //     //与组件不相关的业务代码应该由外部通过回调函数传进来
            //     _this.afterUpload(resp);
            //     //上传完成后清空
            //     $("#" + _this.inputId + "-input").val("");
            //
            // });

            let param = {
                'shardIndex': shardIndex,
                'shardSize': shardSize,
                'shardTotal': shardTotal,
                'use': _this.use,
                'name': file.name,
                'suffix': suffix,
                'size': file.size,
                'key': key62
            };
            //_this.upload(param, shardIndex, shardTotal, fileShard);
                _this.check(param);
        },

    /**
     * 检查文件状态，是否传过，传到第几个分片
     */
    check(param){
        let _this = this;
        // 本地快速秒传检查上传分片接口:process.env.VUE_APP_SERVER + '/file/admin/check/'
        // 阿里云oss快速秒传检查分片接口:process.env.VUE_APP_SERVER + '/file/admin/check-oss/'
        _this.$ajax.get(process.env.VUE_APP_SERVER + '/file/admin/check/' + param.key).then((response)=>{
                let resp = response.data;
                if(resp.success){
                    let obj = resp.content;
                    if(!obj){
                        param.shardIndex = 1;
                        console.log("没有找到文件记录，从分片1开始上传");
                        _this.upload(param);
                    }else if(obj.shardIndex === obj.shardTotal){
                        Toast.success("文件急速秒传成功");
                        _this.afterUpload(resp);
                        $("#" + _this.inputId + "-input").val("");
                    }else{
                        param.shardIndex =obj.shardIndex + 1;
                        console.log("找到文件记录，从分片" + param.shardIndex + "开始上传");
                        _this.upload(param);
                    }
                }else{
                   Toast.warning("文件上传失败");
                    $("#" + _this.inputId + "-input").val("");
                }

        })
    },


        /**
         * 文件上传代码
         */
        upload: function (param) {
            let _this = this;
            let shardIndex = param.shardIndex;
            let shardTotal = param.shardTotal;
            let shardSize  = param.shardSize;
            let fileShard = _this.getFileShard(shardIndex, shardSize);
            //将图片转发为base64进行传输
            let fileReader = new FileReader();
            //开始上传显示进度条
            Progress.show(parseInt((shardIndex-1) * 100 / shardTotal));
            fileReader.onload = function (e) {
                let base64 = e.target.result;
                //console.log("base64",base64);
                param.shard = base64;
                // key:"file"必须和后端controller参数名一样
                // 本地上传接口:process.env.VUE_APP_SERVER + '/file/admin/upload'
                // 阿里云oss接口:process.env.VUE_APP_SERVER + '/file/admin/' + _this.url  （_this.url：oss-append）
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/' + _this.url  , param).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    console.log("文件上传成功地址:", resp);
                    //分片传输的过程修改进度条
                    Progress.show(parseInt(shardIndex * 100 / shardTotal));
                    if (shardIndex < shardTotal) {
                        //上传下一个分片
                        param.shardIndex = param.shardIndex+1;
                        _this.upload(param);
                    } else {
                        //上传完毕后关闭进度条
                        Progress.hide();
                        //为组件增加上传成功后的回调函数
                        //与组件不相关的业务代码应该由外部通过回调函数传进来
                        _this.afterUpload(resp);
                        //上传完成后清空
                        $("#" + _this.inputId + "-input").val("");
                    }
                });
            };
            fileReader.readAsDataURL(fileShard);
        },

        /**
         * 计算文件的分片数
         */
        getFileShard: function (shardIndex, shardSize) {
            let _this = this;
            //拿到$refs这个变量可以点出来所有的别名
            let file = _this.$refs.file.files[0];

            let start = (shardIndex - 1) * shardSize;//当前分片的其实位置
            //Math.min(x,y)  取最小值
            let end = Math.min(file.size, start + shardSize);//当前分片的结束位置
            //file.slice： 返回一个开始和结束的数组
            let fileShard = file.slice(start, end)//从文件中截取当前的分片数据
            return fileShard;
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
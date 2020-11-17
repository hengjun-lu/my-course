<template>
    <main role="main">

        <section class="jumbotron text-center">
            <div class="container">
                <h1>在线视频网站</h1>
                <p class="lead text-muted m-3" >
                    视频网站是指在完善的技术平台支持下，让互联网用户在线流畅发布、浏览和分享视频作品的网络媒体。除了传统的对视频网站的理解外，近年来，无论是P2P直播网站，BT下载站，还是本地视频播放软件，还将向影视点播扩展作为自己的一块战略要地。影视点播已经成为各类网络视频运营商的兵家必争之地。
                </p>
                <p>
                    <router-link to="/list" class="btn btn-primary my-2 p-3 font-weight-bold">点击进入所有视频</router-link>
                </p>
            </div>
        </section>

        <div class="album py-5 bg-light">
            <div class="container">
                <div class="title1">最新上线</div>
                <div class="row">
                    <div v-for="o in news" class="col-md-4">
                        <the-course v-bind:course="o"></the-course>
                    </div>
                </div>
            </div>

            <hr>

            <div class="container">
                <div class="title2">视频推荐</div>
                <div class="row">
                    <div v-for="o in news" class="col-md-4">
                        <the-course v-bind:course="o"></the-course>
                    </div>
                </div>
            </div>


        </div>

    </main>
</template>

<script>
    import TheCourse from "../components/the-course";
    export default {
        name: "index",
        components: {TheCourse},
        //data 全局变量
        data:function () {
           return{
               news: [],  //新视频
           }
        },
        //mounted：通常是初始化页面完成后，再对html的dom节点进行一些需要的操作。
        mounted() {
            let _this = this;

            _this.listNew();
        },
        //methods:使用methods属性给vue定义方法/页面渲染后使用
        methods:{
            /**
             * 查询新视频
             */
            listNew(){
              let _this = this;
              //判断是否有缓存
              let news = SessionStorage.get("news");
              if(!Tool.isEmpty(news)){
                  _this.news = news;
                  return
              }
              _this.$ajax.get(process.env.VUE_APP_SERVER+'/business/web/course/list-new/').then(function (response) {
                console.log("查询新视频结果:",response);
                let resp = response.data;
                if(resp.success){
                    _this.news = resp.content;
                    //查询量高，但是数据不易发生改变的，很适合使用缓存a
                    SessionStorage.set("news",_this.news);//存储到缓存中
                }
              }).catch(function (response) {
                console.log("error",response);
              })
            },

        }
    }

</script>

<style>
    .title1{
        margin-bottom: 2rem;
        color: #fafafa;
        letter-spacing: 0;
        text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666, 0px 5px 0px #555, 0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135;
        font-size: 2rem;
    }
    .title2{
        margin-bottom: 2rem;
        color: transparent;
        -webkit-text-stroke: 1px black;
        letter-spacing: 0.04em;
        font-size: 2rem;
    }

</style>
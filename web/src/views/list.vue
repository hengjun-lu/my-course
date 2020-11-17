<template>
  <main role="main">
    <div class="header-nav">
      <div class="clearfix">
        <div class="container">
          <div class="row">
            <div class="col-12">
              <a v-on:click="onClickLevel1('00000000')" id="category-00000000" href="javascript:;" class="cur">全部</a>
              <a v-for="o in level1" v-on:click="onClickLevel1(o.id)" v-bind:id="'category-' + o.id" href="javascript:;">{{o.name}}</a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="skill clearfix">
      <div class="container">
        <div class="row">
          <div class="col-12">
            <a v-on:click="onClickLevel2('11111111')" id="category-11111111" href="javascript:;" class="on">不限</a>
            <a v-for="o in level2" v-on:click="onClickLevel2(o.id)" v-bind:id="'category-' + o.id" href="javascript:;">{{o.name}}</a>

            <div style="clear:both"></div>
          </div>
        </div>
      </div>
    </div>

    <div class="album py-5 bg-light">
      <div class="container">
        <div class="title1">最新上线</div>
        <pagination ref="pagination" v-bind:list="listCourse"></pagination>
        <div class="row">
          <div v-for="o in courses" class="col-md-4">
            <the-course v-bind:course="o"></the-course>
          </div>
          <h3 v-show="courses.length === 0">视频还未上架</h3>
        </div>
      </div>
    </div>
  </main>
</template>

<script>

  import TheCourse from "../components/the-course";
  import Pagination from "../components/pagination";

  export default {
    name: "list",
    components: {Pagination, TheCourse},
    //全局变量
    data: function () {
      return {
        courses: [],    //视频列表
        level1:[],      //层级1
        level2:[],      //层级2
        categorys: [],  //分类
        level1Id:"",    //一级分类ID
        level2Id:"",    //二级分类ID
      }
    },
    //mounted：通常是初始化页面完成后，再对html的dom节点进行一些需要的操作。
    mounted() {
      let _this = this;
      _this.$refs.pagination.size = 1;
      _this.listCourse(1);//查询视频列表
      _this.allCategoryWeb();//查询所有分类
    },
    //methods:使用methods属性给vue定义方法/页面渲染后使用
    //用来定义函数的
    methods: {
      /**
       * 查询视频列表
       */
      listCourse(page) {
        let _this = this;
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/course/list',{
          page: page,
          size: _this.$refs.pagination.size,
          categoryId: _this.level2Id || _this.level1Id || "", //给后台传值用，优先取值level2Id
          }).then(function (response) {
            let resp = response.data;
            if(resp.success){
                _this.courses = resp.content.list;
                //$refs 是一个对象，持有已注册过 ref 的所有的子组件
                _this.$refs.pagination.render(page,resp.content.total);
            }
        }).catch(function (response) {
            console.log("error:",response);
        })
      },

      /**
       * 查询方法，加载页面数据list，增加参数page查询第几页
       * @param page
       */
      allCategoryWeb(){
        let _this=this;
       // Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER +"/business/web/category/all").then((response)=>{
         // Loading.hide();
          //console.log("查询分类列表结果：",response);
          let  resp = response.data;//使用axios返回的response中data有多个数据，相当于后端的responseDto
          //将response的结果给categorys:categorys表示后台的数据
          let categorys = resp.content;//所有分类
          _this.categorys=categorys;//this.xxx访问组件内的变量
          //将所有记录格式化为树形结构
          _this.level1=[];
          for (let i = 0; i <_this.categorys.length; i++) {
            let  c= _this.categorys[i]
            //如果是00000000 就属于一级分类，否则就是二级分类
            if(c.parent === '00000000'){
              _this.level1.push(c);
            }else{
              _this.level2.push(c);
            }
          }
        });
      },

      /**
       * 点击一级分类
       * @param level1Id
       */
      onClickLevel1(level1Id){
        let _this =this;

        //点击一级分类时，设置变量，用于视频筛选
        //点击二级分类时候显示不限，所以二级分类的id为空
        //如果点击全部则一级分类id为空
        _this.level2Id = null;
        //将点击按钮传参的一级分类ID赋值给全局变量的一级分类ID
        _this.level1Id = level1Id;
        if(level1Id === "00000000"){
          _this.level1Id = null;
        }

        //页面展示start
        //点击一级分类的时候，显示激活状态（给当前这个按钮加属性样式cur，他的兄弟节点去掉这个样式）
        //siblings:jQuery 遍历 ,获得匹配集合中每个元素的同胞
        $("#category-" + level1Id).siblings("a").removeClass("cur");
        $("#category-" + level1Id).addClass("cur");
        //点击一级分类时候，二级分类【不限】按钮，设置成激活状态(他的兄弟节点去掉这个样式)
        $("#category-11111111").siblings("a").removeClass("on");
        $("#category-11111111").addClass("on");
        //页面展示end

        //数据存放
        //先清空二级分类，再往里面放值
        _this.level2 = [];
        //取出全部分类放入categorys变量
        let categorys = _this.categorys;

        //如果是点击【全部】按钮，就显示二级分类
        if(level1Id === '00000000'){
          for (let i = 0; i <categorys.length ; i++) {
            //循环取出数据
            let c = categorys[i];
            //如果分类的父ID不是'00000000'，就说明这条数据属于二级分类
            if(c.parent !== '00000000'){
              //放入二级分类
              _this.level2.push(c);
            }
          }
        }

        //如果点击的是某一个一级分类，就显示这个分类下的二级分类
        if(level1Id !== '00000000'){
          for (let i = 0; i <categorys.length ; i++) {
            //循环取出数据
            let c = categorys[i];
            //如果分类的父ID 等于分类一级ID，说明条数据是一级分类
            if(c.parent === level1Id ){
              //放入一级分类
              _this.level2.push(c);
            }
          }
        }

        //点击完毕某一个分类的时候，需要重新课程
        _this.listCourse(1);
      },

      /**
       * 点击二级分类
       * @param level2Id
       */
      onClickLevel2(level2Id){
        let _this =this;
        //点击一级分类的时候，显示激活状态（给当前这个按钮加属性样式on，他的兄弟节点去掉这个样式）
        //siblings:jQuery 遍历 ,获得匹配集合中每个元素的同胞
        $("#category-" + level2Id).siblings("a").removeClass("on");
        $("#category-" + level2Id).addClass("on");

        //点击二级分类，设置变量，用于课程筛选
        //如果点击的二级分类是【不限】,则二级分类ID为空
        if(_this.level2Id === "11111111"){
          _this.level2Id = null;
        }else{
          _this.level2Id = level2Id;
        }

        //点击完毕某一个分类的时候，需要重新课程
        _this.listCourse(1);
      },

    }
  }
</script>

<style scoped>
  /* 头部 一级分类 */
  .header-nav {
    height: auto;
    background: #fff;
    box-shadow: 0 8px 16px 0 rgba(28,31,33,.1);
    padding: 16px 0;
    box-sizing: border-box;
    position: relative;
    z-index: 1;
    /*background-color: #d6e9c6;*/
  }
  .header-nav>div {
    width: 100%;
    padding-left: 12px;
    box-sizing: border-box;
    margin-left: auto;
    margin-right: auto;
    /*background-color: #B4D5AC;*/
  }
  .header-nav a {
    float: left;
    font-size: 16px;
    color: #07111b;
    line-height: 50px;
    height: 45px;
    position: relative;
    margin-right: 46px;
    font-weight: 700;
  }
  .header-nav a:hover {
    color: #c80;
  }
  .header-nav a.cur {
    color: #c80;
  }
  .header-nav a.cur:before {
    display: block;
  }
  .header-nav a:before {
    display: none;
    content: ' ';
    position: absolute;
    bottom: 0;
    background: #c80;
    width: 16px;
    height: 3px;
    left: 50%;
    margin-left: -8px;
  }
  /* 二级分类 */
  .skill {
    width: 100%;
    padding: 24px 0 0;
    position: relative;
    margin: 0 auto;
  }
  .skill a.on {
    color: #c80;
    background: rgba(204,136,0,.1);
  }
  .skill a {
    float: left;
    margin-right: 20px;
    padding: 0 12px;
    font-size: 14px;
    color: #4d555d;
    line-height: 32px;
    border-radius: 6px;
    margin-bottom: 12px;
  }
  .skill a:hover {
    background: #d9dde1;
  }
</style>
<template>
  <main role="main">
    <div class="album py-5 bg-light">
      <div class="container">
        <div class="row course-head">
          <div class="col-sm-6" id="cover-video-div">
            <img class="img-fluid" v-bind:src="course.image">
          </div>
          <div class="col-sm-6">
            <h1>{{course.name}}</h1>
            <p class="course-head-item">
              <span><i class="fa fa-clock-o"></i> {{(course.time) | formatSecond}}</span>
              <span>{{COURSE_LEVEL | optionKV(course.level)}}</span>
              <span><i class="fa fa-user"></i> {{course.enroll}}</span>
            </p>
            <p class="course-head-desc">{{course.summary}}</p>
            <p class="course-head-price">
              <span class="price-now text-danger"><i class="fa fa-yen"></i>&nbsp;{{course.price}}&nbsp;&nbsp;</span>
            </p>
            <p class="course-head-button-links">
              <a v-show="!memberCourse.id" v-on:click="enroll()" class="btn btn-lg btn-primary btn-shadow" href="javascript:;">立即报名</a>
              <a v-show="memberCourse.id" href="#" class="btn btn-lg btn-success btn-shadow disabled">您已报名</a>
            </p>
          </div>
        </div>

        <div class="row">

          <!-- 课程内容 & 大章小节 -->
          <div class="col-md-9">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs">
              <li class="nav-item">
                <a class="nav-link active" href="#info" data-toggle="tab">课程介绍</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#chapter" data-toggle="tab">章节目录</a>
              </li>
            </ul>

            <br>

<!--             Tab panes-->
            <div class="tab-content">
              <div class="tab-pane active" id="info" v-html="course.content">
              </div>
              <div class="tab-pane" id="chapter">
                  <div v-for="(chapter, i) in chapters" class="chapter">
                  <div v-on:click="doFolded(chapter, i)" class="chapter-chapter">
                    <span>{{chapter.name}}</span>
                    <span class="pull-right">
                      <i v-show="chapter.folded" class="fa fa-plus-square" aria-hidden="true"></i>
                      <i v-show="!chapter.folded" class="fa fa-minus-square" aria-hidden="true"></i>
                    </span>
                  </div>
                  <div v-show="!chapter.folded">
                    <table class="table table-striped">
                      <tr v-for="(s, j) in chapter.sections" class="chapter-section-tr">
                        <td class="col-sm-8 col-xs-12">
                          <div v-on:click="play(s)" class="section-title">
                            <i class="fa fa-video-camera d-none d-sm-inline"></i>&nbsp;&nbsp;
                            <span class="d-none d-sm-inline">第{{j+1}}节&nbsp;&nbsp;</span>
                            {{s.title}}
                            <span v-show="s.charge !== SECTION_CHARGE.CHARGE.key" class="badge badge-primary hidden-xs">免费</span>
                          </div>
                        </td>
                        <td class="col-sm-1 text-right">
                          <span class="badge badge-primary">{{s.time | formatSecond}}</span>
                        </td>
                      </tr>
                    </table>
                  </div>
                </div>
              </div>
            </div>

          </div>

          <!-- 讲师信息 -->
          <div class="col-md-3">
            <div class="card" style="width: 18rem;">
              <span class="card-text" align="center">信息介绍</span>
              <img v-bind:src="teacher.image" class="card-img-top">
              <div class="card-body">
                <h5 class="card-title">{{teacher.name}}</h5>
                <p class="card-text">{{teacher.motto}}</p>
                <p class="card-text">{{teacher.intro}}</p>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>

    <modal-player ref="modalPlayer"></modal-player>
  </main>
</template>

<script>
  import ModalPlayer from "../components/modal-player";
  export default {
    name: "detail.vue",
    components: {ModalPlayer},
    //全局变量
    data:function () {
      return{
        id:"",        //视频ID
        course:{},    //视频数据
        teachers:{},   //讲师数据
        chapters:{},   //大章数据
        sections:{},   //小节数据
        memberCourse: {},//报名表数据
        COURSE_LEVEL:COURSE_LEVEL, //视频等级枚举（初级，中级，高级）
        SECTION_CHARGE: SECTION_CHARGE,//收费枚举
      }
    },

    mounted() {
      let _this = this;
      _this.id = _this.$route.query.id;//路由带参跳转
      _this.findCourse();
    },

    methods:{
      /**
       * 根据ID查找视频
       */
      findCourse(){
        let _this = this;
        _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/web/course/find/' + _this.id).then(function (response) {
          let resp = response.data;
          _this.course = resp.content;
          _this.teacher = _this.course.teachers || {};
          _this.chapters = _this.course.chapters || [];
          _this.sections = _this.course.sections || [];

          //获取报名信息
          _this.getEnroll();

          // 将所有的节放入对应的章中
          for (let i = 0; i < _this.chapters.length; i++) {
            let c = _this.chapters[i];
            c.sections = [];
            for (let j = 0; j < _this.sections.length; j++) {
              let s = _this.sections[j];
              if (c.id === s.chapterId) {
                c.sections.push(s);
              }
            }

            Tool.sortAsc(c.sections, "sort");
          }

        }).catch(function (response) {
          console.log("error:",response);
        })
      },

      /**
       * 展开/收缩一个章节
       * folded:表示收缩，初始时候为非，意思是不收缩，所有的章节都是展开的
       * @param chapter
       */
      doFolded (chapter, i) {
        let _this = this;
        chapter.folded = !chapter.folded;
        // 在v-for里写v-show，只修改属性不起作用，需要$set
        _this.$set(_this.chapters, i, chapter);
      },

      /**
       * 播放视频
       * @param section
       */
      play(section) {
        let _this = this;
         if (section.charge === _this.SECTION_CHARGE.CHARGE.key ) {
           //先把登录的缓存信息取出来，判断是否已经登录了
           let loginMember = Tool.getLoginMember();
           if (Tool.isEmpty(loginMember)) {
             Toast.warning("请先登录");
             return;
           } else {
             //如果不是空，就在判断是否已报名
            if (Tool.isEmpty(_this.memberCourse)) {
              Toast.warning("请先报名");
              return;
            }
          }
           _this.$refs.modalPlayer.playVod(section.vod);
         }
      },


      /**
       * 报名
       */
      enroll() {
        let _this = this;
        let loginMember = Tool.getLoginMember();
        if (Tool.isEmpty(loginMember)) {
          Toast.warning("请先登录");
          return;
        }
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/member-course/enroll', {
          courseId: _this.course.id,
          memberId: loginMember.id
        }).then((response)=>{
          let resp = response.data;
          alert(resp.content.id)
          if (resp.success) {
            _this.memberCourse = resp.content;
            Toast.success("报名成功！");
          } else {
            Toast.warning(resp.message);
          }
        });
      },

      /**
       * 获取报名信息
       */
      getEnroll(){
        let _this = this;
        let loginMember = Tool.getLoginMember();
        if (Tool.isEmpty(loginMember)) {
          console.log("未登录");
          return;
        }
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/web/member-course/get-enroll',{
          courseId: _this.course.id,
          memberId: loginMember.id
        }).then(function (response) {
          let resp = response.data;
          if(resp.success){
            _this.memberCourse = resp.content || {};
          }
        })
      },
    }

  }
</script>

<style scoped>
  /* 课程信息 */
  .course-head {
  }
  .course-head h1 {
    font-size: 2rem;
    margin-bottom: 1.5rem;
  }
  .course-head-item span {
    margin-right: 1rem;
  }
  .course-head-desc {
    font-size: 1rem;
    color: #555
  }
  .course-head a {
  }
  .course-head-price {
    font-size: 2rem;
  }
  @media (max-width: 700px) {
    .course-head h1 {
      font-size: 1.5rem;
    }
  }
  /* 章节列表 */
  .chapter {
    padding-bottom: 1.25rem;
  }
  .chapter-chapter {
    font-size: 1.25rem;
    padding: 1.25rem;
    background-color: #23527c;
    color: white;
    cursor: pointer;
  }
  .chapter-section-tr {
    font-size: 1rem;
  }
  .chapter-section-tr td{
    padding: 1rem 1.25rem;
    vertical-align: middle;
  }
  /*鼠标手势*/
  .chapter-section-tr td .section-title{
    color: #555;
  }
  .chapter-section-tr td .section-title:hover{
    color: #23527c;
    font-weight: bolder;
    cursor: pointer;
  }
  /*行头小图标*/
  .chapter-section-tr td .section-title i{
    color: #2a6496;
  }
  @media (max-width: 700px) {
    .chapter-chapter {
      font-size: 1.2rem;
    }
    .chapter-section-tr {
      font-size: 0.9rem;
    }
  }
</style>
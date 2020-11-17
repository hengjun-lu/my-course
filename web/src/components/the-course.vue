<template>
    <div class="card mb-4 shadow-sm">
        <img class="img-fluid" v-bind:src="course.image">
        <div class="card-body">
            <h4 class="">{{course.name}}</h4>
            <p class="card-text">{{course.summary}}</p>
            <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                    <router-link v-bind:to="'/detail?id=' + course.id" type="button" class="btn btn-sm btn-outline-secondary">视频详情</router-link>
                </div>
                <div class="text-muted">
                    <span class="badge badge-info"><i class="fa fa-yen" aria-hidden="true"></i>&nbsp;{{course.price}}</span>&nbsp;
                    <span class="badge badge-info"><i class="fa fa-user" aria-hidden="true"></i>&nbsp;{{course.enroll}}</span>&nbsp;
                    <span class="badge badge-info">{{COURSE_LEVEL | optionKV(course.level)}}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "the-course",
        //组件实例的作用域是孤立的。这意味着不能 (也不应该) 在子组件的模板内直接引用父组件的数据。
        // 父组件的数据需要通过 prop 才能下发到子组件中。
        //也就是props是子组件访问父组件数据的唯一接口。
        props:{
            course:{},
        },
        data:function () {
            return{
                COURSE_LEVEL: COURSE_LEVEL,
            }
        },

    }
</script>

<style scoped>
    .course h4{
        font-size: 1.25rem;
        margin: 15px 0;
    }

    .course .text-muted .badge{
        font-size: 1rem;
    }
</style>
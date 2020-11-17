<template>
    <big-file
            v-bind:input-id="inputId"
            v-bind:text="text"
            v-bind:suffixs="suffixs"
            v-bind:use="use"
            v-bind:after-upload="afterUpload"
            v-bind:shard-size="1000 * 1024 * 1024"
            v-bind:url="'vod'">
    </big-file>
</template>

<script>
    import BigFile from "./big-file";
    export default {
        name: 'vod',
        components: {BigFile},
        props:{
            text:{
               default:"上传VOD"
            },
            inputId:{
               default:"vod-input"
            },
            suffixs:{
               default:[]
            },
            use:{
               default:""
            },
            afterUpload:{
               type:Function,
               default:null
            },
        },
        methods:{

        }

    }
</script>

<style scoped>

</style>
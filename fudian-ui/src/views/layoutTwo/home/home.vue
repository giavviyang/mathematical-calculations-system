<template>
    <div class="app-home">
        <tag></tag>
        <router-view></router-view>
    </div>
</template>
<script>
import tag from '@/views/layoutTwo/components/tags.vue'
export default {
    components:{
        tag
    },
    data(){
        return{

        }
    },
    methods:{
    }
}
</script>
<style lang="scss" scoped>
.app-home {
    width: 100%;
    height:100%;
}
</style>
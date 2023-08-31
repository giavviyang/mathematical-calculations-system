<template>
    <div class="app-tag">
        <i class="el-icon-s-fold" style="cursor: pointer;margin-right:10px;" @click="isOpenOrClose"></i>
            <el-tag
                class="tag"
                v-for="tag in tags"
                :key="tag.name"
                closable
                :type="tag.type">
                {{tag.name}}
            </el-tag>
    </div>
</template>
<script>
export default {
    data(){
        return{
            isCollapse: false,
      tags: [
          { name: '日常管理', type: '' },
          { name: '材料管理', type: ''  },
          { name: '材料接收', type: ''  },
          { name: '日常维护', type: ''  }
        ],
        }
    },
    methods:{
         isOpenOrClose() {           //左侧导航显示关闭
       this.isCollapse = !this.isCollapse
      },
    }
}
</script>
<style lang="scss" scoped>
.app-tag{
        margin:5px;
        box-shadow: 0px 3px 1px rgba(246, 250, 252, 0.9);
    .tag {
        margin:2px;
    }
} 

</style>


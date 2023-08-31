<template>
  <div class="dashboard-editor-container" style="margin-left: -10px;" >
      <el-row style="background:#fff;margin-bottom:32px;margin-top: 20px;">
        <moban />
      </el-row>
      <el-row style="background:#fff;margin-bottom:32px;">
        <daiban/>
      </el-row>
  </div>
</template>

<script>
  import moban from './components/top'
  import daiban from './components/daiban'
  export default {
    name: 'DashboardEdit',
    components: {
      moban,
      daiban
    },
    data() {
      return {
        mycontainer: {height: ''},
      }
    },
    methods: {
      getHeight() {
        let containerH = window.innerHeight-84;
        this.mycontainer.height = containerH + 'px';  //获取浏览器高度减去顶部导航栏
      },
    },
    created() {
      window.addEventListener('resize', this.getHeight);
      this.getHeight()
    }
  }
</script>

<style lang="scss" scoped>
 .dashboard-editor-container {
   padding: 20px;
   width: 100%;
   // background-color: rgb(240, 242, 245);
   position: relative;

   .github-corner {
     position: absolute;
     top: 0px;
     border: 0;
     right: 0;
   }

   .chart-wrapper {
     background: #fff;
     padding: 10px 10px 0;
     margin-bottom: 32px;
   }
 }

  @media (max-width:1024px) {
    .chart-wrapper {
      padding: 8px;
    }
  }
</style>

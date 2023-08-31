<template>
  <el-container :style="container"  style="background-color: rgb(240, 242, 245);">
    <!--主页面-->
    <el-main style="padding: 0px">
      <el-row >
        <el-col
        :xs="{span: 24}"
        :sm="{span: 24}"
        :md="{span: 24}"
        :lg="{span: 12}"
        :xl="{span: 12}" style="padding-right:8px;width: 100%;display: flex">
          <admin-dashboard />
        </el-col>
        <!--<el-col :xs="{span: 24}"-->
                <!--:sm="{span: 12}"-->
                <!--:md="{span: 12}"-->
                <!--:lg="{span: 6}"-->
                <!--:xl="{span: 6}"-->
                <!--style="width: 30%; display: flex">-->
          <!--<editor-dashboard/>-->
        <!--</el-col>-->
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
import { mapGetters } from 'vuex'
import adminDashboard from './admin'
import editorDashboard from './editor'

export default {
  name: 'Dashboard',
  components: { adminDashboard },
  data() {
    return {
      container: {height: '',padding:'0px'},
      currentRole: 'adminDashboard'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  methods:{
    getHeight() {
      // alert(window.innerHeight - 68 );
      let containerH = window.innerHeight - 84
      this.container.height = containerH + 'px';  //获取浏览器高度减去顶部导航栏
    },
  },
  created() {
    // if (!this.roles.includes('admin')) {
    //   this.currentRole = 'editorDashboard'
    // }
    window.addEventListener('resize', this.getHeight);
    this.getHeight()

  }
}
</script>

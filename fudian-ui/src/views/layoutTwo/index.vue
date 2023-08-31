<template>
  <div class="app-html">
    <el-container>
      <el-header class="title">
        <div class="title-left">
          <img src="../../assets/logo/logo.png" alt="" width="35px" />
          <!-- <img src="../../assets/logo/timg2.png" alt="" width="35px" /> -->
          <div style="margin-left:3px">雍小线上教学系统</div>
        </div>
        <div class="title-right">
          <span>当前用户：管理员</span>
          <span class="icon-style">
            <i class="el-icon-upload2"></i>
            退出
          </span>
        </div>
      </el-header>
      <el-container>
        <div class="aside">
          <el-menu router default-active="1-4-1" class="el-menu-vertical-demo" :collapse="isCollapse">
            <el-menu-item index="/homeHome">
                <i class="el-icon-setting"></i>
                <span slot="title" >首页</span>
            </el-menu-item>
            <el-submenu index="0">
                <template slot="title">
                <i class="el-icon-location"></i>
                <span slot="title">日常管理</span>
                </template>
                <el-menu-item-group>
                <el-menu-item index='/daily'>档案管理</el-menu-item>
                <el-menu-item index="1-2">出具证明</el-menu-item>
                <el-menu-item index="1-3">档案接收</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
            <el-menu-item index="2">
                <i class="el-icon-menu"></i>
                <span slot="title">查借阅申请</span>
            </el-menu-item>
            <el-menu-item index="3">
                <i class="el-icon-document"></i>
                <span slot="title">档案浏览</span>
            </el-menu-item>
            <el-menu-item index="4">
                <i class="el-icon-setting"></i>
                <span slot="title">查借阅审批</span>
            </el-menu-item>
        </el-menu>
        </div>
            <router-view />

      </el-container>
    </el-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      isCollapse: false,
      tags: [
          { name: '日常管理', type: '' },
          { name: '材料管理', type: ''  },
          { name: '材料接收', type: ''  },
          { name: '日常维护', type: ''  }
        ],
      // 部门人员表格数据
        tableData1: [{
          id:1,
            name: "0010012",
            dept_id: "组织部uuuu",
            address: "在职",
            a0101: "王小五",
            a0107: "2016-05-14",
            a0144: "2015-05-04",
            a0134: "2019-05-04",
            education: "本科",
            state: "在职",
            data4: "部门",
            a0151: "干部"
          },
          {
            id:2,
            name: "0010013",
            dept_id: "研发部",
            address: "在职",
            a0101: "赵小六",
            a0107: "2016-05-14",
            a0144: "2015-05-04",
            a0134: "2019-05-04",
            education: "本科",
            state: "哈哈",
            data4: "部门",
            a0151: "嘿嘿"
          },
          {
            id:3,
            name: "0010012",
            dept_id: "组织部uuuuuuuuuu",
            address: "在职",
            a0101: "梁建华",
            a0107: "2016-05-14",
            a0144: "2015-05-04",
            a0134: "2019-05-04",
            education: "本科",
            state: "在职",
            data4: "部门",
            a0151: "干部"
          },
          {
            id:4,
            name: "0010012",
            dept_id: "组织部uuuuuuuuuu",
            address: "在职",
            a0101: "梁建华",
            a0107: "2016-05-14",
            a0144: "2015-05-04",
            a0134: "2019-05-04",
            education: "本科",
            state: "在职",
            data4: "部门",
            a0151: "干部"
          },
          {
            id:5,
            name: "0010012",
            dept_id: "组织部uuuuuuuuuu",
            address: "在职",
            a0101: "梁建华",
            a0107: "2016-05-14",
            a0144: "2015-05-04",
            a0134: "2019-05-04",
            education: "本科",
            state: "在职",
            data4: "部门",
            a0151: "干部"
          },
          {
            id:6,
            name: "0010012",
            dept_id: "组织部uuuuuuuuuu",
            address: "在职",
            a0101: "梁建华",
            a0107: "2016-05-14",
            a0144: "2015-05-04",
            a0134: "2019-05-04",
            education: "本科",
            state: "在职",
            data4: "部门",
            a0151: "干部"
          },
          {
            id:7,
            name: "0010012",
            dept_id: "组织部uuuuuuuuuu",
            address: "在职",
            a0101: "梁建华",
            a0107: "2016-05-14",
            a0144: "2015-05-04",
            a0134: "2019-05-04",
            education: "本科",
            state: "在职",
            data4: "部门",
            a0151: "干部"
          },
          {
            id:8,
            name: "0010012",
            dept_id: "组织部uuuuuuuuuu",
            address: "在职",
            a0101: "梁建华",
            a0107: "2016-05-14",
            a0144: "2015-05-04",
            a0134: "2019-05-04",
            education: "本科",
            state: "在职",
            data4: "部门",
            a0151: "干部"
          }
        ],
    };
  },
  watch: {},
  methods: {

      isOpenOrClose() {           //左侧导航显示关闭
       this.isCollapse = !this.isCollapse
      },
      home (){                     //首页
        this.$router.push({

        })
      }
  }
};
</script>
<style lang="scss" scoped>
 .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
  .app-html {
    height: calc(100vh - 0px);
    overflow-x: hidden;
    overflow-y: hidden;
  }
.title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 16px;
  background-color: #3c8dbc;
  color: #fff;
  height:50px;
  .title-left {
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 18px;
  }
  .title-right {
    .icon-style {
      margin-left: 10px;
    }
  }
}
.aside {
//   background-color:#bbd4e9;
}
.main-bread {
  display: flex;
  justify-content: left;
  align-items: center;
}
.table-style {
    margin-top:15px;
}
</style>

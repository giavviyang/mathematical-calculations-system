<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" />

    <div class="right-menu" >
<!--      <el-tooltip content="消息中心" placement="bottom" effect="light">-->
<!--          <el-badge is-dot class="solid">-->
<!--            <router-link to="/user/info">-->
<!--              <i class="el-icon-bell"></i>-->
<!--            </router-link>-->
<!--          </el-badge>-->
<!--      </el-tooltip>-->
      <el-dropdown v-if="!isApply&&approve" class="avatar-container right-menu-item hover-effect" trigger="hover">
        <span>
          <el-badge :is-dot="ismessage" class="solid">
             <i class="el-icon-bell"></i>
          </el-badge>
        </span>
        <el-dropdown-menu slot="dropdown" v-if="approve" style="top:20px">
          <router-link v-if="browseApprove" to="/checkBorrowApprove/browse">
              <el-dropdown-item ><img src="@/assets/image/temp.png" width="14px" height="14px"/> 临时申请<el-badge class="mark" :value="browse" /></el-dropdown-item>
          </router-link>
          <router-link v-if="electronicApprove" to="/checkBorrowApprove/electronic">
            <el-dropdown-item><img src="@/assets/image/electron.png" width="14px" height="14px"/> 电子档案<el-badge class="mark" :value="electro" /></el-dropdown-item>
          </router-link>
          <router-link v-if="phyApprove" to="/checkBorrowApprove/physical">
            <el-dropdown-item><img src="@/assets/image/browse.png" width="14px" height="14px"/> 查阅申请<el-badge class="mark" :value="phys" /></el-dropdown-item>
          </router-link>
          <router-link v-if="phyBorrowApprove" to="/checkBorrowApprove/physicalBorrowApproval">
            <el-dropdown-item><img src="@/assets/image/borrow.png" width="14px" height="14px"/> 借阅申请<el-badge class="mark" :value="approval" /></el-dropdown-item>
          </router-link>

        </el-dropdown-menu>
      </el-dropdown>
            <!-- <span class="div-hand">|</span>       -->
       <span class="user" v-if="!isApply">
          <i class="el-icon-s-custom"></i>
          {{userName}}
        </span>
      <span class="user" v-if="isApply">
          <i class="el-icon-s-custom"></i>
          临时用户
        </span>
      <el-dropdown v-if="!isApply" class="avatar-container right-menu-item hover-effect" trigger="hover">
         <span>
          <i class="el-icon-caret-bottom" />
          </span>
        <el-dropdown-menu slot="dropdown" style="top:20px">
<!--          <el-dropdown-item>-->
<!--            <span @click="changePassWord">修改密码</span>-->
<!--          </el-dropdown-item>-->
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <!--<el-dropdown-item>
            <span @click="downPlug">控件下载</span>
          </el-dropdown-item>
          <el-dropdown-item>
            <span>帮助文档</span>
          </el-dropdown-item>-->
        </el-dropdown-menu>
      </el-dropdown>
            <!-- <span class="div-hand">|</span>      -->
      <el-tooltip content="退出" placement="bottom" effect="light">
       <span class="loginOut" @click="logout">
          <i class="iconfont icon-tuichu" style="font-size:20px;color:#304156"></i>
          <!-- <span style="font-size:15px">退出</span> -->
          </span>
      </el-tooltip>
    </div>
    <!-- //修改密码 -->
    <el-dialog title="修改密码" :close-on-click-modal="false" :visible.sync="dialogVisible" class=" dialog-style" width="50%">
        <el-form ref="form" :model="user" :rules="rules" label-width="80px">
          <el-form-item label="旧密码" prop="oldPassword">
            <el-input v-model="user.oldPassword" placeholder="请输入旧密码" type="password" />
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input v-model="user.newPassword" placeholder="请输入新密码" type="password" />
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input v-model="user.confirmPassword" placeholder="请确认密码" type="password" />
          </el-form-item>
        </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="close">取消</el-button>
        <el-button type="primary" size="mini" @click="submit">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'
import RuoYi from '@/components/RuoYi'
import {downLoadPost} from '@/downLoad/filedownload'
import {queryTemp,queryElec, queryBrowse, queryBorrow,queryAskApplic} from "@/api/message/info"
import {getInfo} from "@/api/login"
export default {
  data(){
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return{
      ismessage:false,
      approve:false,
      browseApprove:false,
      electronicApprove:false,
      phyApprove:false,
      phyBorrowApprove:false,
      userName:'',
      isApply:false,
      browse:"",
      electro:"",
      phys:"",
      approval:"",
      collect:"",
      dialogVisible:false,
      test: "1test",
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      // 表单校验
      rules: {
        oldPassword: [
          { required: true, message: "旧密码不能为空", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          { min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ]
      }
    }
  },
  components: {
    Breadcrumb,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    RuoYi
  },
  mounted(){
    getInfo().then(response=>{
      this.userName = response.user.userName;
      this.isApply = response.user.userId == 'apply'
    })
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    }
  },
  created() {
    /*this.setButtonPermisson();
    this.getTemp();
    this.getElec();
    this.getBrowse();
    this.getBorrow();
    this.getAsk();*/
  },
  methods: {
    downPlug(){
      downLoadPost("/fileStorage/getSystemUnit")
      // let downUrl = encodeURI(serverUrl);
      // var a = document.createElement('a');
      // a.setAttribute('href', downUrl);
      // a.setAttribute("download","电子档案扫描控件.exe");
      // document.body.appendChild(a);
      // a.click();
    },
    setButtonPermisson(){
      this.$store._modules.root.state.permission.routes.map(item=>{
        if(item.name == '查借阅审批'){
          this.approve = true;
          //展现小铃铛
        }
        if(typeof item.children != 'undefined'){
          item.children.map(childrenItem=>{
            if(childrenItem.name == "临时查阅档案审批"){
              this.browseApprove = true;
              //展现临时查阅档案审批
            }
            if(childrenItem.name == "电子档案查阅审批"){
              this.electronicApprove = true;
              //展现电子档案查阅审批
            }
            if(childrenItem.name == "实体档案查阅审批"){
              this.phyApprove = true;
              //展现实体档案查阅审批
            }
            if(childrenItem.name == "实体档案借阅审批"){
              this.phyBorrowApprove = true;
              //展现实体档案借阅审批
            }
          })
        }
      })
    },
      //消息提醒页面方法
    getTemp() {
      queryTemp().then(response => {
        this.browse=response.total;
        if(this.browse>0&&this.browseApprove){
          this.ismessage = true;
        }
      });
    },
    getElec() {
      queryElec().then(response => {
        this.electro = response.total;
        if(this.electro>0&&this.electronicApprove){
          this.ismessage = true;
        }
      });
    },
    getBrowse() {
      queryBrowse().then(response => {
        this.phys = response.total;
        if(this.phys>0&&this.phyApprove){
          this.ismessage = true;
        }
      });
    },
    getBorrow() {
      queryBorrow().then(response => {
        this.approval = response.total;
        if(this.approval>0&&this.phyBorrowApprove){
          this.ismessage = true;
        }
      });
    },
    getAsk(){
      // queryAskApplic().then(response => {
      //   this.collect = response.total;
      //   if(this.collect>0){
      //     this.ismessage = true;
      //   }
      // });
    },
    changePassWord () {
      this.dialogVisible = true;
    },
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwd(this.user.oldPassword, this.user.newPassword).then(
            response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
              } else {
                this.msgError(response.msg);
              }
            }
          );
        }
      });
    },
    close() {
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/index" });
    },
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserProfile(this.user).then(response => {
            if (response.code === 200) {
              this.msgSuccess("修改成功");
            } else {
              this.msgError(response.msg);
            }
          });
        }
      });
    },

    close() {
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/index" });
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          // location.reload()
          this.$store.dispatch('tagsView/delAllViews').then(({ visitedViews }) => {
            // if (this.affixTags.some(tag => tag.path === view.path)) {
            //   return
            // }
            const latestView = visitedViews.slice(-1)[0];
          })
          this.$router.push({path:'login'})
        })
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    height: 100%;
    display: flex;
    color: #304156;
    justify-content: flex-end;
    align-items: center;
    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding-right: 10px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;
      display: flex;
      justify-content: flex-end;
      align-items: center;
      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          // background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      // margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
.div-hand {
  color:#97a8eb;
}


.hand{
  font-size:10px;
  padding-left:10px;
}
.loginOut {
  margin-left: 5px;
  margin-right:30px;
}
.loginOut:hover{
  cursor:pointer;
  color:rgb(64, 158, 255);
}
// 消息铃
.solid {
  font-size:18px;
  margin-top: 2px;
  margin-right: 10px;
}
.solid:hover{
  cursor:pointer;
  color:rgb(64, 158, 255);
}
// 管理员
.user {
  font-size: 16px;
  margin-left: 5px;
  color:#304156;
}
/*.user:hover{*/
/*  cursor:pointer;*/
/*  color:rgb(64, 158, 255);*/
/*}*/
.el-icon-caret-bottom:hover{
  color:rgb(64, 158, 255);
}
/deep/#dropdown-menu-7931 {
  top:30px !important;
}
    // 公用弹框样式
  .dialog-style {
    /deep/.el-dialog__body {
      padding: 10px;
    }
    /deep/.el-dialog__header {
      background: rgba(204, 204, 204, 0.18);
      padding: 10px;
    }
    /deep/.el-dialog__title {
      font-weight: 500;
      font-size: 15px
    }
    /deep/.el-dialog__headerbtn {
      top: 13px;
    }

    /deep/.el-dialog__footer {
      background: rgba(204, 204, 204, 0.18);
      padding-right: 15px;
      padding: 11px;
    }
    /deep/.el-button+.el-button {
      margin-left: 6px;
    }

  }
  .el-dropdown-menu{
    top: 30px !important;
  }
</style>

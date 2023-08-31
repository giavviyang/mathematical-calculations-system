<template>
  <div class="app-container">
    <el-row :gutter="20" style="height: 330px;">
      <el-col :span="6" :xs="24" style="height: 100%;">
        <el-card class="box-card test">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon-class="user" style="margin-right: 10px;"/>
                用户名称
                <div class="pull-right">{{ user.userName }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="phone" style="margin-right: 10px;"/>
                手机号码
                <div class="pull-right">{{ user.iphone }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="tree" style="margin-right: 10px;"/>
                所属学校
                <div class="pull-right" v-if="user.deptId">{{ user.deptId }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="date" style="margin-right: 10px;"/>
                创建日期
                <div class="pull-right">{{ user.createTime }}</div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18" :xs="24" style="height: 100%;">
        <el-card>
          <div slot="header" class="clearfix">
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="基本资料" name="first">
                <userInfo @update="getUser" :user="user2"/>
              </el-tab-pane>
              <el-tab-pane label="修改密码" name="uppwd">

                <el-form ref="form" :model="pwd" :rules="rules" label-width="80px">
                  <el-form-item label="旧密码" prop="oldPassword">
                    <el-input v-model="pwd.oldPassword" placeholder="请输入旧密码" type="password"/>
                  </el-form-item>
                  <el-form-item label="新密码" prop="newPassword">
                    <el-input v-model="pwd.newPassword" placeholder="请输入新密码" type="password"/>
                  </el-form-item>
                  <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input v-model="pwd.confirmPassword" placeholder="请确认密码" type="password"/>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" size="mini" @click="submit">保存</el-button>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import userInfo from "./userInfo";
import resetPwd from "./resetPwd";
import {getUserProfile} from "@/api/system/user";
import {updateUserPwd, getLoginUserInfo} from "@/api/personal/info"

export default {
  name: "Profile",
  components: {userAvatar, userInfo, resetPwd},
  data() {
    //新密码规则
    const newPassword = (rule, value, callback) => {
      if(value===this.pwd.oldPassword){
        callback(new Error("新密码不能与旧密码一致"));
      }else {
        let reg=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
        if(!reg.test(value)){
          callback(new Error("密码必须是由6-20位字母+数字组合"));
        }else {
          callback();
        }
      }
    };
    //确认密码规则
    const equalToPassword = (rule, value, callback) => {
        if (this.pwd.newPassword !== value) {
          callback(new Error("两次输入的密码不一致"));
        } else {
          callback();
        }
    };

    return {
      activeName: 'first',
      user: {},
      pwd: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      user2: {},
      roleGroup: {},
      postGroup: {},
      activeTab: "userinfo",
      // 表单校验
      rules: {
        oldPassword: [
          {required: true, message: "旧密码不能为空", trigger: "blur"}
        ],
        newPassword: [
          {required: true, message: "新密码不能为空", trigger: "blur"},
          {required: true, validator: newPassword, trigger: "blur"}
        ],
        confirmPassword: [
          {required: true, message: "确认密码不能为空", trigger: "blur"},
          {required: true, validator: equalToPassword, trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getUser();
  },
  methods: {
    getUser() {
      getLoginUserInfo().then(response => {
        this.user = response.data;
        this.user2 = JSON.parse(JSON.stringify(this.user));
        // this.roleGroup = response.roleGroup;
        // this.postGroup = response.postGroup;
      });
    },
    handleClick() {

    },
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.pwd.oldPassword === this.pwd.newPassword) {

          }
          updateUserPwd(this.pwd.oldPassword, this.pwd.newPassword).then(
            response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功,退出系统后生效");
                this.pwd.oldPassword = "";
                this.pwd.newPassword = "";
                this.pwd.confirmPassword = "";
              } else {
                this.msgError(response.msg);
              }
            }
          );
        }
      });
    },
  }
};
</script>
<style lang="scss" scoped>
/deep/ .el-card__body {
  padding-top: 0px;
}

.list-group {
  margin: 0px;
}

/deep/ .list-group-item:nth-child(1) {
  border-top: none;
}

.test {
  /*width: 236px;*/
  height: 358px;
}

/deep/ .box-card {
  width: 100%;
  height: 100%;
}
</style>


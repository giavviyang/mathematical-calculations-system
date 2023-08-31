<template>
  <div class="login">
    <!-- <vxe-button @click="value2 = !value2">记忆功能的窗口</vxe-button>
    <vxe-modal v-model="value2" title="测试窗口" width="600" height="400" resize remember>
      <template>
        <p style="color: red">会记忆最后一次操作的状态，单重新打开会还原</p>
        <p style="color: red">按住头部移动！！！！！！！！！！！！！！！</p>
        <p style="color: blue">按住左边距拖动！！！！！！！！！！！！！！！</p>
        <p style="color: red">按住右边距拖动！！！！！！！！！！！！！！！</p>
        <p style="color: blue">按住底边距拖动！！！！！！！！！！！！！！！</p>
        <p style="color: blue">按住左下角拖动 ！！！！！！！！！！！！！！！</p>
        <p style="color: blue">按住右下角拖动！！！！！！！！！！！！！！！</p>
      </template>
    </vxe-modal> -->
      <h3 class="title">雍小线上教学系统</h3>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
		<el-tabs v-model="activeName" :stretch="true">
			<el-tab-pane label="登录"  name="login">
				<el-form-item prop="username">
					<el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
						<svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
					</el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input
					  v-model="loginForm.password"
					  type="password"
					  auto-complete="off"
					  placeholder="密码"
					  @keyup.enter.native="handleLogin"
					>
					<svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
					</el-input>
				</el-form-item>
				<el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
				<el-form-item style="width:100%;">
					<el-button
					  :loading="loading"
					  size="medium"
					  type="primary"
					  style="width:100%;"
					  @click.native.prevent="handleLogin"
					>
					  <span v-if="!loading">登 录</span>
					  <span v-else>登 录 中...</span>
					</el-button>
				</el-form-item>
			</el-tab-pane>
			<!--<el-tab-pane label="临时借阅" name="temporary" class="temporary">
				<el-button type="primary" @click.native.prevent="handleApply" class="temporary-btn">发起申请</el-button>
				<div class="temporary-footer">
				  依据《中华人民共和国档案法》，查阅档案人员必须严格遵守保密制度，不得泄密或擅自向外公布档案内容
				</div>
			</el-tab-pane>-->
		</el-tabs>
    </el-form>
    <!--  底部  -->
    <!-- <div class="el-login-footer">
      <span>Copyright © 2018-2019 fudian.vip All Rights Reserved.</span>
    </div> -->
  </div>
</template>

<script>
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'
import {licenseKeyCheck} from '@/api/license'
import {MessageBox, Notification} from 'element-ui'
import {getRouters} from "@/api/menu";
export default {
  name: "Login",
  data() {
    return {
      value2:false,
      codeUrl: "",
      cookiePassword: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "用户名不能为空" }
        ],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ]
      },
      loading: false,
      redirect: undefined,
      activeName: 'login'
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.$store._modules.root.state.mask.lodding = false;
    this.getCookie();
  },
  methods: {

    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleApply(){
      let params = {
        username:'apply',
        password:'admin',
      }
      console.log(this.$router);
      this.$router.options.routes[4].children[0].hidden = true;
      this.$store
        .dispatch("Login", params)
        .then(() => {
          this.loading = false;
          this.$router.push({ path:  "provisional-inspection" });
          // this.$router.push({ path: this.redirect || "/" });
        })
        .catch(() => {
          this.loading = false;
        });
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }

          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              //跳转到第一个目录下的第一个菜单s
              getRouters().then(res => {
                let path = res.data[0].path+'/'+res.data[0].children[0].path
                this.loading = false;
                this.$router.push({ path: path });
              })
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  height: 100%;
  background-image: url("../assets/image/yongxiao-bg.jpg");
  background-size: cover;
  .title {
  color: #ffffff;
  font-size:30px;
  }
  .login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  height: 310px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
}



// .login-tip {
//   font-size: 13px;
//   text-align: center;
//   color: #bfbfbf;
// }
// .login-code {
//   width: 33%;
//   height: 38px;
//   float: right;
//   img {
//     cursor: pointer;
//     vertical-align: middle;
//   }
// }
// .el-login-footer {
//   height: 40px;
//   line-height: 40px;
//   position: fixed;
//   bottom: 0;
//   width: 100%;
//   text-align: center;
//   color: #fff;
//   font-family: Arial;
//   font-size: 12px;
//   letter-spacing: 1px;
// }
.temporary{
  height: 226px;
  display: flex;
  justify-content: center;
  align-items: center;
  .temporary-btn{
    margin-top: -55px;
  }
  .temporary-footer{
    text-align: center;
    font-family: Arial;
    font-size: 12px;
    color:#97a8be;
    position: absolute;
    left: 5px;
    top: 175px;
  }
}
</style>

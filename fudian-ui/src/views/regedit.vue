<template>
  <div class="dashboard-editor-container">

    <el-container  class="div_container" >
      <el-header >
        <div class="div_header_text">
          系统未经授权,请联系软件出版方进行授权!
        </div>

      </el-header>
      <el-main >
        <div
          style="
          height: 50px;
          text-align: center;
        "
        >
          <h2>联系电话:400-688-5528</h2>
        </div>
        <div
          style="
          width: 100%;
          height: 50px;
          text-align: center;
          margin-top: 100px;
        "
        >
          <span>注册码:</span>
          <el-input style="width: 600px;" v-model="form.code"></el-input>

        </div>
      </el-main>
      <el-footer >
        <div class="div_footer_text">
          <el-button type="primary" style="float: right;margin-left: 20px;" @click="regedit">注册</el-button>
          <el-button type="primary" style="float: right;margin-left: 20px;" @click="giveMe">信息采集</el-button>
        </div>
      </el-footer>
    </el-container>

  </div>
</template>

<script>
  import {regeditLicense} from '@/api/license';
  export default {

    name:"reedit",

    data() {
      return {
        form:{
          code:''
        }
      }
    },
    watch: {

    },
    created() {

    },
    methods: {
      regedit(){
        console.log(this.form.code)
        regeditLicense(JSON.stringify(this.form)).then(response => {
          if(response.code == 200){
            this.$message.success("注册成功");
            // alert("");
            this.$router.replace({path:'/login'})
          }else{
            this.$message.error("注册失败");
            // alert("");
          }
        })
      },
      giveMe(){
        this.$router.replace({path:'/infoInput'})
      }
    }
  };
</script>

<style lang="scss" scoped>
  /deep/ .div_container{

    /*margin-top: 20px;*/
    width:60%;
    border-radius:10px;
    /*background-color: #f0f2f5;*/
    margin-left: 20%;
    border: #eaecec 1px solid;
    height:80%;
    .div_header_text{
      height: 60px;
      line-height: 60px;
      width: 100%;
      border-bottom: #C0C4CC 1px solid;
    }
    .div_footer_text{
      float: right;
      width: 100%;
      padding-top: 10px;
      border-top: #C0C4CC 1px solid;
    }
    .div_header{
      height:90%;
      width: 100%;
    }
    .div_footer{
      height:10%;
      width: 100%;
    }
  }
  .dashboard-editor-container {
    padding-top: 20px;
    height: 100%;
    /*padding: 32px;*/
    /*background-color: rgb(240, 242, 245);*/
    /*position: relative;*/

    /*.chart-wrapper {*/
    /*  background: #fff;*/
    /*  padding: 16px 16px 0;*/
    /*  margin-bottom: 32px;*/
    /*}*/
  }
</style>

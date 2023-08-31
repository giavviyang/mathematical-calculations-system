<template>
  <div class="dashboard-editor-container">
    <el-container  class="div_container" >
      <el-header >
        <div class="div_header_text">
          系统注册-信息采集
        </div>

      </el-header>
      <el-main >
        <div
          style="
                width:100%;
                height: 50px;
                text-align: center;
                margin-top: 10px;
              "
        >
        <span>用户名称:</span>
        <el-input style=" width:80%;" v-model="form.customerName"></el-input>
        </div>

        <div
          style="

                width:100%;
                height: 50px;
                text-align: center;
                margin-top: 10px;

              "
        >
          <span>系统名称:</span>
          <!--          <el-input style=" width:80%;" v-model="form.sysname"></el-input>-->
          <el-select style="width: 80%;" v-model="form.systemName" clearable placeholder="请选择">
            <el-option
              v-for="item in sysname"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </div>
        <div
          style="

                width:100%;
                height: 50px;
                text-align: center;
                margin-top: 10px;
              "
        >
          <span>系统版本号:</span>
          <!--          <el-input style="width : 585px;" v-model="form.sysversion"></el-input>-->
          <el-select style="width : 78%;" v-model="form.version" clearable placeholder="请选择">
            <el-option
              v-for="item in sysversion"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </div>
        <div
          style="

                width:100%;
                height: 50px;
                text-align: center;
                margin-top: 10px;
              "
        >
          <span>部署人姓名:</span>
          <el-input style="width : 78%;" v-model="form.executor"></el-input>
        </div>
        <div
          style="

                width:100%;
                height: 50px;
                text-align: center;
                margin-top: 10px;
              "
        >
          <span>校验信息:</span>
          <el-input style="
           width:80%;" v-model="form.singnatrue"></el-input>
        </div>
        <div
          style="

                width:100%;
                height: 50px;
                text-align: center;
                margin-top: -13px;
              "
        >
          <span style="color: red;margin-left: -170px;">请将校验信息发送给北京博文中科信息技术有限公司负责人!</span>
        </div>
      </el-main>
      <el-footer >
        <div class="div_footer_text">
          <el-button type="primary" style="float: right;margin-left: 20px;" @click="submitForm">生成校验</el-button>
          <el-button type="primary" style="float: right;margin-left: 20px;" @click="cancel">返 回</el-button>
        </div>
      </el-footer>
    </el-container>
  </div>

</template>

<script>
  import {generatorLicenseKey} from '@/api/license';

  export default {

    data() {
      return {
        form: {
          customerName: '',
          systemName: '',
          version: '',
          executor: '',
          singnatrue: ''
        },
        sysname: [{
          value: '雍小线上教学系统',
          label: '雍小线上教学系统'
        }],
        sysversion: [
          {
            value: 'V3.0.0',
            label: 'V3.0.0'
          }
        ]

      }
    },
    watch: {},
    created() {

    },
    methods: {
      submitForm() {
        let form = this.form;
        if (form.customerName.trim() == "") {
          this.$message.warning("请输入用户名称");
          // alert("");
          return;
        }
        // if (form.orgname.trim() == "") {
        //   this.$message.warning("请输入全宗名称");
        //   // alert("");
        //   return;
        // }
        if (form.systemName.trim() == "") {
          this.$message.warning("请选择系统名称");
          // alert("")
          return;
        }
        if (form.version.trim() == "") {
          this.$message.warning("请选择系统版本");
          // alert("")
          return;
        }
        if (form.executor.trim() == "") {
          this.$message.warning("请输入部署人姓名");
          // alert("")
          return;
        }
        this.form.singnatrue = '';
        generatorLicenseKey(JSON.stringify(this.form)).then(response => {
          if (response.code == 200) {
            this.form.singnatrue = response.data;
            this.$message.success("生成licenseKey成功!");
          }
        })
      },
      cancel() {
        this.$router.replace({path: '/reedit'})
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

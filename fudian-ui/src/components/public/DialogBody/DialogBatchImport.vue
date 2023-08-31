<template>
  <!--  批量导入  -->
  <div class="container">
    <div class="form">
      <slot name="form"/>
    </div>
    <div class="radio">
      <slot name="radio"/>
    </div>
    <div class="upload">
      <slot name="upload"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "DialogBatchImport"
}
</script>

<style lang="scss" scoped>
.container {
  height: 100%;
  width: 100%;

  .form{
    width: 100%;
    /deep/ .el-form {
      width: 85%;
      margin-left: 7.5%;
      .el-form-item {
        margin: 5px 0;
        display: flex;
        justify-content: left;
        .el-form-item__label{
          width: 180px;
        }
        .el-form-item__content{
          width: 100%;
        }
        .el-input{
          width: 100%;
        }
        .el-select{
          width: 100%;
        }
        .el-textarea{
          width: 100%;
        }
      }
    }
  }

  .radio {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    /deep/ .el-radio-group {
      width: 90%;
      display: inline-flex;
      flex-wrap: wrap;
      align-items: center;
      //justify-content: space-evenly;
      justify-content: space-between;

      label {
        height: 25px;
        margin-right: 0;
        display: flex;
        justify-content: center;
        align-items: center;

        span {
          font-size: 12px;
        }
      }
    }
  }

  .upload {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    /deep/ .el-upload {
      width: 90%;
      .el-upload-dragger {
        width: 340px;
      }
    }

    /deep/ .el-upload__tip {
      width: 100%;
      text-align: center;
      margin: 5px 0;
    }
  }

}
</style>

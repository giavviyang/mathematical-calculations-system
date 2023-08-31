<template>
  <!--  打印  -->
  <div class="container">
    <div class="radio">
      <slot name="radio"/>
    </div>
    <div class="form">
      <slot name="form"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "DialogPrint",
}
</script>

<style lang="scss" scoped>
.container {
  height: 100px;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  //ie浏览器兼容
  box-sizing: content-box;
  -moz-box-sizing: inherit;
  -webkit-box-sizing: inherit;

  .radio {
    width: 100%;
    /deep/ .el-radio-group {
      width: 100%;
      display: inline-flex;
      flex-wrap: wrap;
      align-items: center;
      justify-content: space-evenly;

      label {
        width: 50%;
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
          width: 100px;
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
}


</style>

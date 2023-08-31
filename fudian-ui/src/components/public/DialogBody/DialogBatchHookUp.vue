<template>
  <!-- 批量挂接 -->
  <div class="main">
    <div class="cntLeft">
      <div class="radio">
        <slot name="radio"/>
      </div>
      <div class="form">
        <slot name="form"/>
      </div>
    </div>
    <div class="cntRight">
      <div class="title">
        <slot name="TableTitle"/>
      </div>
      <div class="dialogTable">
        <slot name="dialogTable"/>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DialogBatchHookUp"
}
</script>

<style lang="scss" scoped>
.main {
  //background-color: red;
  display: flex;
  height: calc(100vh - 250px);
  .cntLeft{
  border: 2px dashed rgb(204, 204, 204);
    width: 35%;
    margin-right: 5px;
    .radio {
      width: 100%;
      margin-top: 5px;
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
  .cntRight{
    width: 65%;
    //background: orange;
    .title{
      padding-left: 20px;
      height: 40px;
      border: 3px solid rgba(204, 204, 204, 0.18);
      background-color:rgba(204, 204, 204, 0.18);
      line-height: 40px;
    }
    .dialogTable{
      height: calc(100% - 40px);
    }
  }
}
</style>

<template>
  <div class="container">
      <slot name="form"/>
  </div>
</template>

<script>
export default {
  name: "DialogAddForm"
}
</script>

<style lang="scss" scoped>
.container{
  width: 100%;
  //height: 100%;
  min-height: 100px;
    /deep/ .el-form {
      max-height:calc(100vh - 190px);
      width: 100%;
      //height: 100%;
      min-height: 100px;
      overflow: auto;
      display: flex;
      align-items: center;
      flex-direction: column;
      justify-content: space-around;
      .el-form-item {
        width: 85%;
        margin: 5px 0;
        display: flex;
        height: 50px;
        justify-content: left;
        align-items: center;
        .el-form-item__label{
          min-width: 100px;
        }
        .el-form-item__content{
          width: 100%;
          p {
            font-size: 13px;
            line-height: 10px;
            height: 20px;
            border-bottom: 1px solid #ccc;
          }
          .el-form-item__error{
            top:36px
          }
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
        .el-cascader{
          width: 100%;
        }
      }
    }

}
</style>

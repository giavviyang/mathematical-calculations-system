<template>
  <div class="containerTop">
    <div class="ipt">
      <slot name="ipt"/>
    </div>
    <div class="btnDrop">
      <slot name="btnDrop"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "ContainerTop"
}
</script>

<style lang="scss" scoped>
.containerTop {
  width: 100%;
  //height: 80px;
  text-align: left;
  //line-height: 40px;

  .ipt {
    background-color: #f6f6f6;
    padding: 10px;

    /deep/ .el-form {
      //padding-left: 5px;
      display: flex;
      flex-wrap: nowrap;
      height: 36px;
      overflow-x: auto;
      overflow-y: hidden;

      .el-form-item {
        min-width: 220px;

        .el-form-item__label {
          line-height: 36px;
          padding: 0;
          min-width: 50px;
        }

        .el-form-item__content {
          line-height: 36px;
          width: calc(100% - 100px);
          //height: 40px;
          .el-button {
            height: 28px;
          }

          .el-input__inner {
            height: 28px;
            //padding: 0 15px;
          }

          .el-select {
            .el-input__inner {
              padding-right: 28px;
            }
          }

          .el-date-editor {
            padding: 0 15px;

            span {
              display: inline-block;
              margin-right: 10px;
            }
          }
        }
      }

    }

  }

  .btnDrop {
    display: inline-flex;
    width: 100%;

    /deep/ .el-form {
      height: 29px;
      margin: 10px 0;

      .el-form-item {
        position: relative;
        top: -3px;
      }
    }
  }

}
</style>

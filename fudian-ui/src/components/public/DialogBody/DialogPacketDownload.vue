<template>
  <!--数据包下载-->
  <div class="container">
    <div class="radio">
      <slot name="radio"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "DialogPacketDownload"
}
</script>

<style lang="scss" scoped>
.container {
  height: 100px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;

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
}
</style>

<template>
<!--  数据包上传  -->
  <div class="container">
    <slot name="upload"/>
  </div>
</template>

<script>
export default {
  name: "DialogPacketUpload"
}
</script>

<style lang="scss" scoped>
.container {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  /deep/ .el-upload {
    margin-top: 5px;
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
</style>

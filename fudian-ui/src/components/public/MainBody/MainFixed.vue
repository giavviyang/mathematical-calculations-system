<template>
  <div class="mainBody">
    <div class="topContainer">
      <slot name="topContainer"/>
    </div>
    <div class="centerContainer">
      <slot name="centerContainer"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "MainFixed",
}
</script>

<style lang="scss" scoped>
.mainBody {
  padding: 0 5px;
  width: 100%;
  height: 100%;


  .topContainer {
    height: 40px;
    width: 100%;
    /deep/.el-form{
      padding-left: 5px;
      background:rgba(204, 204, 204, 0.18);
    }
  }

  .centerContainer {
    height: calc(100% - 45px);
    margin-top: 5px;
    width: 100%;
  }
}


</style>

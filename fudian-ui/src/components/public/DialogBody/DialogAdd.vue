<template>
  <!--  新增  -->
  <div class="container">
    <div class="formMarking">
      <slot name="form"/>
    </div>
    <div class="btn">
      <slot name="btn"/>
    </div>
    <div class="DialogTable">
      <slot name="DialogTable"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "DialogAdd"
}
</script>

<style lang="scss" scoped>
.container {
  height: 100%;

  .formMarking {
    height: 160px;
    overflow: auto;
  }

  .btn {
    height: 40px;
    line-height: 40px;
  }

  .DialogTable {
    max-height: calc(100vh - 370px);
    //height:calc(100vh - 370px);
    margin-left: 0.5%;
  }
}


</style>

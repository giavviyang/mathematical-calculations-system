<template>
  <el-tabs v-model="activeName"
           tab-position="left"
           @tab-click="cutoverTabs"
           class="tabLeft">
    <el-tab-pane :label="item.label"
                 v-for="(item,index) in leftLabel"
                 :key="index" :name="item.name">
      <slot :name="item.name"/>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
export default {
  name: "TabLeft",
  props: ['activeName', 'leftLabel'],
  methods: {
    cutoverTabs() {
      this.$emit('cutoverTabs')
    }
  }
}
</script>

<style lang="scss" scoped>
.tabLeft {
  width: 100%;
  height: 100%;
  display: flex;
  //background-color: red;
  /deep/ .el-tabs__header {
    height: 100%;
    width: 10%;

    .el-tabs__nav-wrap {
      width: 100%;
      height: auto;

      .el-tabs__item {
        text-align: center;
      }
    }
  }

  /deep/ .el-tabs__content {
    height: 100%;
    width: 100%;
    .el-tab-pane{
      height: 100%;
      //background-color: yellow;
    }
  }
}
</style>

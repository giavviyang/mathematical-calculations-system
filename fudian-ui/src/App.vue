<template>
  <div id="app">
    <router-view v-loading="$store._modules.root.state.mask.lodding"
                 element-loading-text="正在生成文件"
                 element-loading-spinner="el-icon-loading"
                 element-loading-background="rgba(0, 0, 0, 0.8)"/>
  </div>
</template>

<script>
export default  {
  name:  'App'
}
</script>

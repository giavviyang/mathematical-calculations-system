<template>
      <div class="shrinkageDiv">
          <div @click="divClick()" :class="picClassname"></div>
      </div>
</template>

<script>
  export default {
    data() {
      return {
        treeShow:true,
        rightStyle: {
          width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)'
        },
        leftStyle: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc',position: 'relative'
        },
        leftStyleName: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc',position: 'relative'
        },
        rightStyleName: {
          width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)'
        },
        leftStyle1: {
          width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc',position: 'relative'
        },
        rightStyle1: {
          width: '99%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)'
        },
        picClassname:"shrinkageLeft",
        leftTree1: true,
      }
    },
     methods: {
       divClick() {
         this.leftTree1 = !this.leftTree1
         if (this.leftTree1) {
           this.rightStyleName = this.rightStyle
           this.leftStyleName = this.leftStyle
           this.picClassname = 'shrinkageLeft'
           this.treeShow =! this.treeShow
         } else {
           this.rightStyleName = this.rightStyle1
           this.leftStyleName = this.leftStyle1
           this.picClassname = 'shrinkageRight'
           this.treeShow =! this.treeShow
         }
       }
     }
  }

</script>

<style>
  .shrinkageLeft{
      border-right: 8px solid #FFFFFF;
      border-top: 8px solid transparent;
      border-bottom: 8px solid transparent;
      position: absolute;
      width: 8px;
      height: 8px;
      left: 0px;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
      cursor:pointer;
  }
  .shrinkageRight{
      border-left: 8px solid #FFFFFF;
      border-top: 8px solid transparent;
      border-bottom: 8px solid transparent;
      position: absolute;
      width: 8px;
      height: 8px;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
      cursor:pointer;
  }
  .shrinkageDiv{
      background-color: #1890ff;
      right: 0;
      bottom: 0;
      margin: auto;
      top: 0;
      height: 200px;
      width: 8px;
      border-radius: 20px;
      position: absolute;
  }
</style>

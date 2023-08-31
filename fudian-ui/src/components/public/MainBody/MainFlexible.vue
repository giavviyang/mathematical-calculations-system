<template>
  <div class="mainBody">
    <div class="mainLeft" :class="{pageLeftStyle:flag}">
      <slot name="mainLeft"/>
      <div class="shrinkageDiv" @click="divClick()">
        <div class="pic" :class="picClassname"></div>
      </div>
    </div>
    <div class="mainRight" :class="{pageRightContainer:flag}">
      <slot name="mainRight"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "MainFlexible",
  data() {
    return {
      picClassname:'shrinkageLeft',
      flag: false
    }
  },
  methods: {
    divClick() {
      this.flag = !this.flag
      if (this.flag) {
        this.picClassname = 'shrinkageRight'
      } else {
        this.picClassname = 'shrinkageLeft'
      }
    },
  }
}
</script>

<style lang="scss" scoped>
.mainBody {
  width: 100%;
  height: 100%;
  //background-color: red;
display: flex;
  .mainLeft {
    //background-color: pink;
    height: 100%;
    width: 12%;
    position: relative;
    overflow: hidden;
    border-right: 1px solid #ccc;
    .pic {
      position: absolute;
      width: 8px;
      height: 8px;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
      cursor: pointer;
    }

    .shrinkageLeft {
      border-right: 8px solid #FFFFFF;
      border-top: 8px solid transparent;
      border-bottom: 8px solid transparent;
    }

    .shrinkageRight {
      border-left: 8px solid #FFFFFF;
      border-top: 8px solid transparent;
      border-bottom: 8px solid transparent;
    }

    .shrinkageDiv {
      background-color: #1890ff;
      cursor: pointer;
      right: 0;
      bottom: 0;
      margin: auto;
      top: 0;
      height: 200px;
      width: 8px;
      border-radius: 20px;
      position: absolute;
      z-index: 99;
    }
  }
  .mainRight{
    //background-color: orange;
    height: 100%;
    width: 88%;
    margin: 0 5px;
  }
  .pageLeftStyle {
    width: 1%;
  }
  .pageRightContainer{
    width: 99%;
  }
}

</style>

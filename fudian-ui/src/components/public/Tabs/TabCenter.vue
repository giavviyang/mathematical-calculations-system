<template>
  <el-tabs class="tabCenter" type="border-card" v-model="passedActiveName" @tab-click="cutoverTabs">
    <el-tab-pane v-for="(item,index) in centerLabel" :label="item.label" :name="item.name">
      <slot :name="item.name"/>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
export default {
  name: "TabCenter",
  props: ['passedActiveName', 'centerLabel'],
  methods: {
    cutoverTabs() {
      this.$emit('cutoverTabs')
    }
  }
}
</script>

<style lang="scss" scoped>
.tabCenter {
  width: 100%;
  height: 100%;
  //background-color: pink;

  /deep/ .el-tabs__header {
    height: 40px;
    width: 100%;

    .el-tabs__item {
      margin-top: 0;
    }
  }

  /deep/ .el-tabs__content {
    padding: 5px;
    height: calc(100% - 40px);

    .el-tab-pane {
      height: 100%;
      //background-color: yellow;

      & > div {
        width: 100%;
        height: 100%;

        .el-form {
          padding-left: 5px;
          height: 40px;
          line-height: 40px;
          background: rgba(204, 204, 204, 0.18);

          .el-form-item {
            height: 40px;
            line-height: 40px;

            .el-form-item__label {
              height: 40px;
              line-height: 40px;
              padding-right: 5px;

            }

            .el-form-item__content {
              height: 40px;
              line-height: 40px;
            }
          }
        }

        .btn {
          width: 100%;
          padding-left: 5px;
          //background-color: orange;
          height: 40px;
          line-height: 40px;
        }

        .tableContainer {
          height: calc(100% - 80px);
          //background-color: palegreen;
        }
      }
    }
  }
}

/*/deep/.el-tabs--border-card {
  & > .el-tabs__header {
    .el-tabs__item {
      margin-top: 0;
    }
    .el-tabs__item {
      &:first-child {
        margin-top: 0;
      }
    }
  }
}*/


</style>

<template>
  <div class="mainBody">
    <div class="mainLeft" :class="{pageLeftStyle:flag}">
      <div class="containerTree">
        <div class="inputClear">
          <el-input
            size="mini"
            placeholder="请输入内容"
            v-model="filterText"
            clearable>
          </el-input>
        </div>

        <el-scrollbar>
          <el-tree
            class="filter-tree"
            empty-text=" "
            :data="data"
            :props="defaultProps"
            :default-expand-all="defaultExpand"
            :filter-node-method="filterNode"
            @node-click="handleNodeClick"
            @node-contextmenu="rightClick"
            :highlight-current="true"
            expand-on-click-node
            icon-class="el-icon-folder-opened"
            ref="tree">
          </el-tree>
          <div class="RightClick">
            <slot name="RightClick"/>
          </div>
        </el-scrollbar>
      </div>
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
  name: "MainFlexibleTree",

  props: {
    data: {
      type: Array,
      default: () => []
    },
    defaultProps: {
      type: Object,
      default: () => {
      }
    },
    defaultExpand: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      picClassname: 'shrinkageLeft',
      flag: false,
      filterText: '',
    }
  },
  watch: {
    filterText(val) {
      console.log('val', val)
      this.$refs.tree.filter(val);
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
    filterNode(value, data) {
      if (!value) return true;
      return data[this.defaultProps.label].indexOf(value) !== -1;
    },
    handleNodeClick(val, node, component) {
      this.$emit('handleNodeClick', val, node, component)
    },
    rightClick(event, object, value,) {
      this.$emit('rightClick', event, object, value,)
    }
  }
}
</script>

<style lang="scss" scoped>
.mainBody {
  width: 100%;
  height: 100%;
  display: flex;

  .mainLeft {
    height: 100%;
    width: 16%;
    position: relative;
    overflow: hidden;
    border-right: 1px solid #ccc;

    .containerTree {
      width: 100%;
      height: 100%;

      .inputClear {
        /deep/ .el-input {
          width: 90%;
          padding: 20px 0 0 20px;
          font-size: 12px;
          .el-input__suffix {
            top: 11px;
          }
        }
      }


      /deep/ .el-scrollbar {
        height: calc(100% - 75px);

        /deep/ .el-scrollbar__wrap {
          overflow-x: hidden;

          /deep/ .el-tree {
            padding: 20px;
            height: 100%;
          }
        }
      }
    }


    .RightClick {
      position: fixed;
      display: block;
      z-index: 99999;
      border-radius: 3px;
      box-shadow: 0 0.5em 1em 0 rgba(0, 0, 0, .1);
      /*
           /deep/ .right-menu {
              border: 1px solid #eee;
              box-shadow: 0 0.5em 1em 0 rgba(0,0,0,.1);
              border-radius: 1px;
              display: block;
              font-family: Microsoft Yahei,Avenir,Helvetica,Arial,sans-serif;
              -webkit-font-smoothing: antialiased;
              -moz-osx-font-smoothing: grayscale;
              text-align: center;
              color: #2c3e50;
              position: fixed;
              background: #fff;
              border: 1px solid rgba(0,0,0,.2);
              border-radius: 3px;
              z-index: 999;
              display: none;
              a {
                padding: 2px 15px;

                // width: 120px;
                height: 28px;
                line-height: 28px;
                text-align: center;
                display: block;
                color: #1a1a1a;

              }
              user agent stylesheet
              a:-webkit-any-link {
                color: -webkit-link;
                cursor: pointer;
                text-decoration: underline;
              }
              a:hover {
                // background: #42b983;
                //background: $color-primary;
                background-color: #1890ff;;
                color: #fff;
              }
            }*/

      /deep/ .right-menus {
        font-size: 14px;
        background-color: #fff;
        width: 100px;
        border: solid 1px rgba(0, 0, 0, .2);

        a {
          width: 98px;
          height: 28px;
          line-height: 28px;
          text-align: center;
          display: block;
          color: #1a1a1a;
          padding: 2px;

          &:hover {
            background: #ccc;
            color: #fff;
          }
        }
      }

      a {
        text-decoration: none;
      }
    }

    //显示滚动条
    /deep/ .el-scrollbar .el-scrollbar__wrap {
      overflow-x: hidden;

      .el-scrollbar__view {
        height: 100%;
      }


    }

    /deep/ .el-tree {
      padding: 20px;
      height: 100%;
      overflow: auto;
    }


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

  .mainRight {
    height: 100%;
    width: 84%;
    padding: 10px 10px 0 10px;
    box-sizing: border-box;
  }

  .pageLeftStyle {
    width: 15px;

    .containerTree {
      display: none;
    }
  }

  .pageRightContainer {
    width: calc(100% - 20px);
  }
}

</style>

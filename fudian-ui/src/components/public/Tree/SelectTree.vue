<template>
  <div class="containerTree">
    <el-input
      size="mini"
      placeholder="请输入内容"
      v-model="filterText"
      clearable>
    </el-input>
    <el-scrollbar>
      <el-tree
        class="filter-tree"
        empty-text=" "
        :data="data"
        :props="defaultProps"
        default-expand-all
        :filter-node-method="filterNode"
        icon-class="el-icon-folder-opened"
        @node-click="handleNodeClick"
        @node-contextmenu="rightClick"
        :highlight-current="true"
        :expand-on-click-node="false"
        ref="tree">
      </el-tree>
      <div class="RightClick">
        <slot name="RightClick"/>
      </div>
    </el-scrollbar>
  </div>
</template>

<script>
export default {
  name: "SelectTree",
  watch: {
    filterText(val) {
      console.log('val', val)
      this.$refs.tree.filter(val);
    }
  },
  props: ['data', 'defaultProps'],
  data() {
    return {
      filterText: '',
    }
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data[this.defaultProps.label].indexOf(value) !== -1;
    },
    handleNodeClick(val) {
      this.$emit('handleNodeClick', val)
    },
    rightClick(event, object, value,) {
      this.$emit('rightClick', event, object, value,)
    }
  },
}
</script>

<style lang="scss" scoped>
.containerTree {
  width: 100%;
  height: 100%;

  /deep/.el-input {
    width: 90%;
    margin: 5px 5%;
    font-size: 12px;
    .el-input__inner {
      padding: 0 15px;
    }
  }

  /deep/.el-scrollbar {
    height: calc(100% - 75px);

    /deep/ .el-scrollbar__wrap {
      overflow-x: hidden;

      /deep/ .el-tree {
        & > .el-tree-node {
          min-width: 100%;
          display: inline-block;
          .el-tree-node__label {
            font-size: 12px;
          }
        }
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

  /deep/ .right-menus {
    font-size: 14px;
    background-color: #fff;
    width: 100px;
    border: solid 1px rgba(0, 0, 0, .2);

    a {
      width: 97px;
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
}

/deep/ .el-tree > .el-tree-node {
  min-width: 100%;
  display: inline-block;
}
</style>

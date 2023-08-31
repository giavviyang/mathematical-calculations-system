<template>
  <div class="tableContainer">
    <div class="table">
      <el-table
        :row-style="{height:40+'px'}"
        stripe
        border
        height="100%"
        empty-text=" "
        size="mini"
        tooltip-effect="light"
        :data="tableData"
        @selection-change="handleSelectionChange"
        @row-click="handleRow">
        <el-table-column
          type="selection"
          width="40">
        </el-table-column>
        <el-table-column
          type="index"
          width="50"
          align="center"
          label="序号"/>
        <template v-for="(item, index) in columnData">
          <el-table-column
            show-overflow-tooltip
            align="center"
            :v-if="item.isCol"
            :formatter="item.formatter"
            :key="index"
            :prop="item.prop"
            :label="item.label"/>
        </template>
        <el-table-column align="center" :label="operableLable" min-width="100" max-width="200" fixed="right">
          <template slot-scope="scope">
            <el-button @click="onlinePreview(scope.row)" :refId="scope.row" type="text" size="mini">{{ operable }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="page">
      <el-pagination
        :page-size="pageSize"
        :current-page="pageNum"
        :total="total"
        :page-sizes="[20, 50, 100, 200]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handlePageNum"/>
    </div>

  </div>
</template>

<script>
export default {
  name: "OperableAndFixed",
  props: {
    //表格数据
    tableData: {
      type: Array,
      default: () => []
    },
    //表头数据
    columnData: {
      type: Array,
      default: () => []
    },
    //最后一列label
    operableLable: {
      type: String,
      default: ''
    },
    //最后一列操作名
    operable: {
      type: String,
      default: ''
    },
    //单页数据量
    pageSize: {
      type: Number,
      default: 20
    },
    //当前页码
    pageNum: {
      type: Number,
      default: 1
    },
    //分页数据总数
    total: {
      type: Number,
      default: 0
    },
  },
  data() {
    return {}
  },
  methods: {
    handleSelectionChange(val) {
      this.$emit('handleSelectionChange', val);
    },
    handleRow(row, column, event) {
      this.$emit('handleRow', row, column, event);
    },
    onlinePreview(val) {
      this.$emit('onlinePreview', val);
    },
    handleSizeChange(pageSize) {
      this.$emit('handleChange', pageSize, this.pageNum);
    },
    handlePageNum(pageNum) {
      this.$emit('handleChange', this.pageSize, pageNum);
    },
  }
}
</script>

<style lang="scss" scoped>
.tableContainer {
  width: 100%;
  height: 100%;
  $fontSize: 12px;
  //ie浏览器兼容
  box-sizing: content-box;
  -moz-box-sizing: inherit;
  -webkit-box-sizing: inherit;

  .table {
    height: calc(100% - 38px);
    overflow: hidden;

    /deep/ .el-table {
      width: 100%;
      height: 100%;
      color: #909399;
      //border: 3px rgba(204, 204, 204, 0.18) solid;
      //overflow: hidden;

      & > .el-table__header-wrapper {
        & > table {
          th {
            background: rgba(204, 204, 204, 0.18);
            color: #909399;
            //height: 31px;
            font-size: 12px;
          }

          & > colgroup {
            col {
              &:last-of-type {
                width: 17px !important;
              }
            }
          }
        }
      }

      .el-table__body-wrapper {
        .el-table__row {
          td {
            &:last-child {
              .cell {
                display: flex;
                flex-direction: row;
                justify-content: space-around;
                width: 100%;
                padding: 0;
              }
            }
          }
        }
      }

 /*     .el-table__fixed-right {
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.12);

        .el-table__fixed-body-wrapper {
          top: 40px !important;
        }
      }*/

    }
  }

  .page {
    height: 38px;
    text-align: right;

    /deep/ .el-pagination {
      height: 28px;
      position: relative;
      top: 3px;
      font-size: $fontSize;

      & > .el-pagination__total {
        font-size: $fontSize;
      }

      & > .el-pagination__sizes {
        .el-input {
          input {
            font-size: $fontSize;
            height: 26px;
          }
        }
      }

      & > .el-pager {
        li {
          font-size: $fontSize;
        }
      }

      & > .el-pagination__jump {
        font-size: $fontSize;

        .el-input {
          input {
            font-size: $fontSize;
            height: 26px;
          }
        }
      }
    }
  }
}
</style>

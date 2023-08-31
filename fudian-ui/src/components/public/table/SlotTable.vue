<template>
  <div class="tableContainer">
    <div class="table">
      <slot name="table"/>
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
  name: "SlotTable",
  props: {
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
  methods:{
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
    height: calc(100% - 40px);
    overflow: hidden;

    /deep/ .el-table {
      //height: 100%;
      color: #909399;
      //border: 3px rgba(204, 204, 204, 0.18) solid;
      overflow: auto;

      & > .el-table__header-wrapper {
        & > table {
          th {
            background: rgba(204, 204, 204, 0.18);
            color: #909399;
            font-size: 12px;
            text-align: center;
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
            text-align: center;
            &:last-child {
              .cell {
                display: flex;
                flex-direction: column;
                justify-content: space-around;
                width: 100%;
                padding: 0;
              }
            }
          }
        }
      }
    }
  }

  .page {
    height: 38px;
    text-align: right;

    /deep/ .el-pagination {
      height: 28px;
      //position: relative;
      //top: 3px;
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
      button {
        background-color: transparent;
      }

      & > .el-pager {
        li {
          font-size: $fontSize;
          background-color: transparent;
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

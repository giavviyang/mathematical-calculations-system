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
        <el-table-column align="center" :label="operableLable" min-width="100" max-width="200">
          <template slot-scope="scope">
            <div v-for="item in operable">
              <div v-if="item.operableName=='启用'">
                <!-- 存储配置 -->
                <div v-if="scope.row.active!=null">
                  <el-button @click="onlinePreview(scope.row)"
                             :refId="scope.row"
                             type="text"
                             v-if="scope.row.active == '1'"
                             :disabled="true"
                             size="mini">已{{ item.operableName }}
                  </el-button>
                  <el-button @click="onlinePreview(scope.row)"
                             :refId="scope.row"
                             type="text"
                             v-else-if="scope.row.active != '1'"
                             size="mini">{{ item.operableName }}
                  </el-button>
                </div>
                <!-- 业务管理 -->
                <div v-else-if="scope.row.busiStatus!=null">
                  <el-button @click="onlinePreview(scope.row)"
                             :refId="scope.row"
                             type="text"
                             v-if="scope.row.busiStatus == 0"
                             size="mini">{{ item.operableName }}
                  </el-button>
                  <el-button @click="onlinePreviewOne(scope.row)"
                             :refId="scope.row"
                             type="text"
                             v-if="scope.row.busiStatus == 1"
                             size="mini"> 停用
                  </el-button>
                </div>
              </div>
              <div v-else-if="item.operableName=='初始化'">
                <!-- 存储配置 -->
                <div v-if="scope.row.globalOrgStatus!=null">
                  <el-button @click="onlinePreview(scope.row)"
                             :refId="scope.row"
                             type="text"
                             v-if="scope.row.globalOrgStatus == '1'"
                             :disabled="true"
                             size="mini">已{{ item.operableName }}
                  </el-button>
                  <el-button @click="onlinePreview(scope.row)"
                             :refId="scope.row"
                             type="text"
                             v-if="scope.row.globalOrgStatus != '1'"
                             size="mini">未{{ item.operableName }}
                  </el-button>
                </div>
                <!-- 业务管理 -->
                <div v-else-if="scope.row.busiStatus!=null">
                  <el-button @click="onlinePreview(scope.row)"
                             :refId="scope.row"
                             type="text"
                             v-if="scope.row.busiStatus == 0"
                             size="mini">{{ item.operableName }}
                  </el-button>
                  <el-button @click="onlinePreviewOne(scope.row)"
                             :refId="scope.row"
                             type="text"
                             v-if="scope.row.busiStatus == 1"
                             size="mini"> 停用
                  </el-button>
                </div>
              </div>
              <div v-else>
                <el-button @click="handleTestConnect(scope.row)"
                           :refId="scope.row"
                           type="text"
                           size="mini">{{ item.operableName }}
                </el-button>
              </div>
            </div>
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
  name: "OperableAndUnfixedIsActive",
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
      type: Array,
      default: () => []
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
    onlinePreviewOne(val) {
      this.$emit('onlinePreviewOne', val);
    },
    handleTestConnect(val) {
      this.$emit('handleTestConnect', val);
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
      border: 3px rgba(204, 204, 204, 0.18) solid;
      overflow: hidden;

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

//.el-table__body-wrapper tbody  .el-table__row td:last-child .cell{
//  display: flex;
//  flex-direction: row;
//  justify-content: space-between;
//  width: 100%;
//}
</style>


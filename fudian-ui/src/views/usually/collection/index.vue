<template>
  <div class="mainHeight">
    <main-flexible-tree :data="treeData" :defaultProps="TreeDefaultProps" @handleNodeClick="handleNodeClick">
      <el-tabs type="border-card" slot="mainRight" v-model="newsFlag"
               @tab-click="rankingTabsEvent">
        <el-tab-pane label="知识点练习" name="0">
          <div class="practise">
            <el-table
              ref="multipleTable"
              :data="tableData"
              style="width: 100%"
              :cell-style="cellStyle"
              @selection-change="handleSelectionChange"
              :row-class-name="tableRowClassName"
              height="100%"
              :cell-class-name="cellClsNm"
              @row-click="rowClickEvent">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column align="center" label="操作" >
                <template slot-scope="scope">
                  <div>{{ scope.row.newsContent }}</div>
                  <div>{{ scope.row.createTime }}</div>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <div class="page">
            <div class="pageCheck">
              <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange('multipleTable')">全选
              </el-checkbox>
              <el-button type="primary" size="mini" style="margin-left: 30px;" @click.prevent="read">已读</el-button>
              <el-button type="primary" size="mini" @click.prevent="deleteSelection">删除</el-button>
            </div>
            <el-pagination
              :page-size="pageSize"
              :current-page="pageNum"
              :total="total"
              :page-sizes="[20, 50, 100, 200]"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handlePageNum"/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="学生闯关" name="1">
          <div class="practise">
            <el-table
              ref="multipleTable1"
              :data="tableData"
              style="width: 100%"
              :cell-style="cellStyle"
              @selection-change="handleSelectionChange"
              :row-class-name="tableRowClassName"
              height="100%"
              :cell-class-name="cellClsNm"
              @row-click="rowClickEvent">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                  <div>{{ scope.row.newsContent }}</div>
                  <div>{{ scope.row.createTime }}</div>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <div class="page">
            <div class="pageCheck">
              <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange('multipleTable1')">全选
              </el-checkbox>
              <el-button type="primary" size="mini" style="margin-left: 30px;" @click.prevent="read">已读</el-button>
              <el-button type="primary" size="mini" @click.prevent="deleteSelection">删除</el-button>
            </div>
            <el-pagination
              :page-size="pageSize"
              :current-page="pageNum"
              :total="total"
              :page-sizes="[20, 50, 100, 200]"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handlePageNum"/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="错题本练习" name="2">
          <div class="practise">
            <el-table
              ref="multipleTable2"
              :data="tableData"
              style="width: 100%"
              :cell-style="cellStyle"
              @selection-change="handleSelectionChange"
              :row-class-name="tableRowClassName"
              height="100%"
              :cell-class-name="cellClsNm"
              @row-click="rowClickEvent">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                  <div>{{ scope.row.newsContent }}</div>
                  <div>{{ scope.row.createTime }}</div>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <div class="page">
            <div class="pageCheck">
              <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange('multipleTable2')">全选
              </el-checkbox>
              <el-button type="primary" size="mini" style="margin-left: 30px;" @click.prevent="read">已读</el-button>
              <el-button type="primary" size="mini" @click.prevent="deleteSelection">删除</el-button>
            </div>
            <el-pagination
              :page-size="pageSize"
              :current-page="pageNum"
              :total="total"
              :page-sizes="[20, 50, 100, 200]"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handlePageNum"/>
          </div>
        </el-tab-pane>
      </el-tabs>
    </main-flexible-tree>
  </div>
</template>

<script>
import MainFlexibleTree from "@/components/public/MainBody/MainFlexibleTree";
import {getDeptTree} from "@/api/system/dept";
import {
  pagingQueryNewsNotice,
  removeNewsNotice,
  readNewsNotice,
} from "@/api/usually/collection";

const params = {
  pageNum: 1,
  pageSize: 20
}
export default {
  name: "index",
  components: {MainFlexibleTree},
  created() {
    this.leftDepTree();
  },
  data() {
    return {
      treeData: [],
      newsFlag: "0",
      TreeDefaultProps: {
        children: "children",
        label: "deptName",
      },
      tableData: [],
      params: {
        /* 总页数 */
        pageNum: 1,
        pageSize: 20,
        deptId: '',
        newsType: '0',
      },
      /* 总页数 */
      total: 0,
      pageNum: 1,
      pageSize: 20,
      multipleSelection: [],
      checkAll: '',
      isIndeterminate: false,
      flag: false,
    }
  },

  methods: {
    /*查询左侧tree树*/
    leftDepTree() {
      getDeptTree({deptType: "1"}).then(response => {
        if (response.code === 200) {
          this.treeData = response.data
          this.fore(this.treeData[0])
          this.queryNewsNotice(this.params)
        }
      })
    },
    /*循环查找tree树中的班级节点*/
    fore(obj) {
      if (obj && obj.nodeType !== "2") {
        this.fore(obj.children[0])
      } else {
        this.params.deptId = obj.id
      }
      return;
    },

    /*左侧tree树点击事件*/
    handleNodeClick(val, node, component) {
      // console.log(val, node, component)
      this.fore(val)
      this.queryNewsNotice(this.params)
    },
    /*卡片点击事件*/
    rankingTabsEvent() {
      this.queryNewsNotice(this.params)
    },
    /*查询右侧数据 消息数据*/
    queryNewsNotice(params) {
      params.newsType = this.newsFlag
      pagingQueryNewsNotice(params).then(response => {
        if (response.code === 200) {
          // console.log('response', response)
          this.tableData = response.rows;
          this.total = response.total;
          this.pageSize=response.pageSize;
          this.pageNum=response.pageNum;
        }
      })
    },
    cellClsNm({row, column, rowIndex, columnIndex}) {
      if (column.label === '操作') {
        return 'caozuo'
      }
    },
    /*全选事件*/
    handleCheckAllChange(tableRef) {
      if (!this.checkAll) {
        // console.log(this.$refs[tableRef]);
        this.$refs[tableRef].clearSelection()//全不选
      } else {
        this.$refs[tableRef].toggleAllSelection()//全选

      }
    },
    // 状态列字体颜色
    cellStyle({row, column, rowIndex, columnIndex}) {
      // console.log(row, column, rowIndex, columnIndex)
      if (row.isRead === 1 ) {
        return 'color: #ddd'
      } else {
        return 'color: red'
      }
    },
    /*单行勾选事件*/
    handleSelectionChange(val) {
      this.multipleSelection = val;
      // console.log(index)
      if (val.length === this.tableData.length) {
        this.checkAll = true;
      } else {
        this.checkAll = '';
      }
    },
    /**
     * 获取选中行的当前index
     * @param row
     * @param rowIndex
     */
    tableRowClassName({row, rowIndex}) {
      //把每一行的索引放进row
      row.index = rowIndex;
    },
    /*行点击事件*/
    rowClickEvent(row) {
      // console.log(row)
      this.multipleSelection = []
      this.multipleSelection.push(row)
      // this.read()
      readNewsNotice(JSON.stringify(this.multipleSelection)).then(response => {
        // console.log('1111',response)
        if(response.code===200){
          this.queryNewsNotice(this.params)
        }
      })
    },
    /*已读事件*/
    read() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: '请选择您要读取的内容',
          type: 'warning'
        })
        return;
      } else {
        readNewsNotice(JSON.stringify(this.multipleSelection)).then(response => {
          // console.log('1111',response)
          if(response.code===200){
            this.queryNewsNotice(this.params)
          }
        })
      }
    },
    /*删除消息*/
    deleteSelection() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: '请选择您要删除的内容',
          type: 'warning'
        })
        return;
      } else {
        this.$confirm('此操作将永久删除该消息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          removeNewsNotice(JSON.stringify(this.multipleSelection)).then(response => {
            this.$message.success("删除成功！")
            this.queryNewsNotice(this.params)
          })
        })
      }
    },
    handleSizeChange(val) {
      this.params.pageSize = val;
      this.queryNewsNotice(this.params)
    },
    handlePageNum(val) {
      this.params.pageNum = val
      this.queryNewsNotice(this.params)
    }
  }
}
</script>

<style lang="scss" scoped>
.el-tabs {
  height: calc(100% - 10px);

  /deep/ .el-tabs__content {
    height: calc(100% - 40px);
    padding: 0 5px;

    .el-tab-pane {
      height: 100%;

      .practise {
        height: calc(100% - 40px);
      }

      .el-table {

        overflow: auto;

        .el-table__header-wrapper {
          display: none;
        }

        .el-table__body-wrapper {
          height: calc(100% - 40px);

          tr {
            height: 60px;

            .caozuo {
              .cell {
                display: flex;
                justify-content: space-between;
              }
            }

            td {
              border-bottom: none;
              padding: 0;

              .list {
                width: 100%;
                height: 50px;
                border: 1px solid #ddd;
                border-radius: 20px;
                display: flex;
                justify-content: space-between;
                padding: 0 20px;
                //color: red;
              }

              //.readList{
              //  color: #ddd;
              //}
            }
          }

          .el-table__row {
            .el-table-column--selection {
              .cell {
                padding: 0;
                text-align: center;
              }

            }
          }
        }
      }

      .el-table::before {
        background-color: transparent;
      }

      .el-table--enable-row-hover {
        .el-table__body {
          tr {
            &:hover {
              & > td {
                background-color: #fff;
              }
            }
          }
        }
      }

      .el-table::before {
        background-color: #fff;
      }

      .page {
        display: flex;
        justify-content: space-between;
      }

      .el-checkbox {
        height: 30px;
        line-height: 30px;

        .el-checkbox__label {
          font-size: 18px;
          position: relative;
          top: 2px;
        }

        .el-checkbox__inner {
          width: 20px;
          height: 20px;
          border-radius: 50%;
        }

        .el-checkbox__inner:after {
          height: 10px;
          width: 5px;
          left: 7px;
          top: 2px;
        }
      }
    }
  }
}
</style>

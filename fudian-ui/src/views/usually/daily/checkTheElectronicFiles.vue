<template>
  <div class="mainHeight">
    <main-flexible-tree :data="treeData" :defaultProps="TreeDefaultProps" @handleNodeClick="handleNodeClick">
      <el-tabs type="border-card"
               slot="mainRight"
               v-model="relationType"
               @tab-click="rankingTabsEvent">
        <container-top class="mainTop">
          <el-form :inline="true" slot="ipt" :model="deblockingForm">
            <el-form-item label="状态：">
              <el-select placeholder="请选择状态" v-model="deblockingForm.status" clearable>
                <el-option label="失败" value="1"></el-option>
                <el-option label="成功" value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="姓名：">
              <el-input v-model="deblockingForm.name" placeholder="请输入学生姓名" clearable
                        @keyup.enter.native="queryByName"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="queryByName">搜索</el-button>
              <el-button size="mini" type="primary" @click="reset">重置</el-button>
            </el-form-item>
          </el-form>
        </container-top>
        <el-tab-pane label="知识点训练" name="0">
          <div class="shibai">
            <el-table
              border
              :data="tableData"
              ref="multipleTable"
              @selection-change="handleSelectionChange"
              height="100%"
              :cell-class-name="cellClsNm"
              :header-cell-class-name="headerName">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                type="index"
                label="序号"
                width="50">
              </el-table-column>
              <el-table-column
                v-for="(item,index) in tableDataColumn"
                :key="index"
                :prop="item.prop"
                :label="item.label"
                :formatter="item.formatter"
                :width="item.width">
              </el-table-column>
              <el-table-column prop="errorTopics" label="查看错题">
                <template slot-scope="scope">
                <span v-for="errorItem in scope.row.errorList" :key="errorItem.exercisesId"
                      v-show="scope.row.correcRate!==100">
                    <span>{{ errorItem.str }}</span>
                </span>
                  <span v-show="scope.row.correcRate===100">
                    <span>无错题</span>
                </span>
                </template>
              </el-table-column>
              <el-table-column type="expand" width="50">
                <template slot-scope="props">
                  <el-form :class="{errorForm:props.row.correcRate!==100}">
                    <el-form-item :label="props.row.relationName" v-show="props.row.correcRate!==100">
                      <span v-for="errorItem in props.row.errorList" :key="errorItem.exercisesId">
                        <span>{{ errorItem.exercisesNum }}={{ errorItem.errorAnswers }}</span>
                      </span>
                    </el-form-item>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column align="left" label="错题诊断">
                <template slot-scope="scope">
                    <span v-show="scope.row.correcRate!==100">{{ scope.row.diagnosisMsg }}</span>
                    <span v-show="scope.row.correcRate===100">无错题</span>
                </template>
              </el-table-column>
              <el-table-column align="center" label="操作" width="150">
                <template slot-scope="scope">
                  <el-button type="text" size="mini" @click="rowUnlock(scope.row)">
                    解锁
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="page">
            <div>
              <el-button type="primary" size="mini" @click.prevent="unlock">解锁</el-button>
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
        <el-tab-pane label="闯关" name="1">
          <div class="shibai chaungguan">
            <el-table
              border
              :data="tableData"
              ref="multipleTable"
              @selection-change="handleSelectionChange"
              height="100%"
              :cell-class-name="cellClsNm"
              :header-cell-class-name="headerName">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                type="index"
                label="序号"
                width="50">
              </el-table-column>
              <el-table-column
                v-for="(item,index) in tableDataColumnBreak"
                :key="index"
                :prop="item.prop"
                :label="item.label"
                :formatter="item.formatter"
                :width="item.width">
              </el-table-column>
              <el-table-column align="center" label="关联知识点/占比/正确率" :min-width="getMaxLength">
                <template slot="header">
                  <div class="cellDiv">
                    <span>关联知识点</span><span>/</span> 占比<span>/</span>正确率
                  </div>
                </template>
                <template slot-scope="scope">
                  <div class="cellDiv" v-for="errorItem in scope.row.list" :key="errorItem.id">
                    <span>{{ errorItem.knowledgeName }}</span><span>/</span>
                    <span>{{ errorItem.proportion }}%</span><span>/</span>
                    <span>{{ errorItem.correcRate }}%</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="errorTopics" label="错题详情" :min-width="errorLength">
                <template slot-scope="props">
                  <div v-for="errorItem in props.row.list" :key="errorItem.studentId" v-show="props.row.flag!==true">
                    <div v-for="errorListItem in errorItem.newErrorList" :key="errorListItem.id">
                      <span>{{ errorListItem}}</span>
                    </div>
                  </div>
                  <span v-show="props.row.flag===true">
                    <span>无错题</span>
                </span>
                </template>
              </el-table-column>
              <el-table-column type="expand" width="50">
                <template slot-scope="props">
                  <el-form :class="{errorForm:props.row.flag!==true}">
                    <el-form-item :label="errorItem.knowledgeName" v-show="errorItem.correcRate!==100"
                                  v-for="errorItem in props.row.list" :key="errorItem.studentId">
                      <span v-for="errorListItem in errorItem.errorList" :key="errorListItem.id">
                      <span>{{ errorListItem.exercisesNum }}={{ errorListItem.errorAnswers }}</span>
                      </span>
                    </el-form-item>
                  </el-form>
                </template>
              </el-table-column>

              <el-table-column align="left" label="错因诊断" :min-width="getDiagnosisMsgLength">
                <template slot-scope="scope">
                  <span v-for="errorItem in scope.row.list" :key="errorItem.relationId" v-show="scope.row.flag!==true">
                    <span v-show="errorItem.correcRate!==100">{{ errorItem.diagnosisMsg }}</span>
                    <span v-show="errorItem.correcRate===100"></span>
                  </span>
                  <span v-show="scope.row.flag===true">
                    <span>无错题</span>
                </span>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="page" style="justify-content: flex-end">
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
        <el-tab-pane label="错题本" name="2">
          <div class="shibai">
            <el-table
              border
              :data="tableData"
              ref="multipleTable"
              @selection-change="handleSelectionChange"
              height="100%"
              :cell-class-name="cellClsNm"
              :header-cell-class-name="headerName">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                type="index"
                label="序号"
                width="50">
              </el-table-column>
              <el-table-column
                v-for="(item,index) in tableDataColumn"
                :key="index"
                :prop="item.prop"
                :label="item.label"
                :formatter="item.formatter"
                :width="item.width">
              </el-table-column>
              <el-table-column prop="errorTopics" label="查看错题">
                <template slot-scope="scope">
                <span v-for="errorItem in scope.row.errorList" :key="errorItem.exercisesId"
                      v-show="scope.row.correcRate!==100">
                    <span>{{ errorItem.str }}</span>
                </span>
                  <span v-show="scope.row.correcRate===100">
                    <span>无错题</span>
                </span>
                </template>
              </el-table-column>
              <el-table-column type="expand" width="50">
                <template slot-scope="props">
                  <el-form :class="{errorForm:props.row.correcRate!==100}">
                    <el-form-item :label="props.row.relationName" v-show="props.row.correcRate!==100">
                      <span v-for="errorItem in props.row.errorList" :key="errorItem.exercisesId">
                        <span>{{ errorItem.exercisesNum }}={{ errorItem.errorAnswers }}</span>
                      </span>
                    </el-form-item>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column align="left" label="错题诊断">
                <template slot-scope="scope">
                  <span v-show="scope.row.correcRate!==100">{{ scope.row.diagnosisMsg }}</span>
                  <span v-show="scope.row.correcRate===100">无错题</span>
                </template>
              </el-table-column>
              <el-table-column align="center" label="操作" width="150">
                <template slot-scope="scope">
                  <el-button type="text" size="mini" @click="rowUnlock(scope.row)">
                    解锁
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="page">
            <div>
              <el-button type="primary" size="mini" @click.prevent="unlock">解锁</el-button>
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
  pagingQueryUnlock,
  modifyUnlockById,
} from "@/api/usually/daily/checkTheElectronicFiles";
import ContainerTop from "@/components/public/MainBody/ContainerTop";

const params = {
  pageNum: 1,
  pageSize: 20
}
export default {
  name: "checkTheElectronicFiles",
  components: {ContainerTop, MainFlexibleTree},
  created() {
    this.leftDepTree();
    // 全局添加事件监听
    // window.addEventListener('keyup', this.queryByName)
    this.keyupSubmit();
  },
  data() {
    return {
      relationType: "0",
      treeData: [],
      multipleSelection: [],
      TreeDefaultProps: {
        children: "children",
        label: "deptName",
      },
      deblockingForm: {
        status: "",
        name: "",
      },
      tableData: [],
      tableDataColumn: [
        {label: '姓名', isCol: true, prop: 'studentName', width: '100'},
        {label: '知识点名称', isCol: true, prop: 'relationName', width: '250'},
        {
          label: '正确率', isCol: true, prop: 'correcRate',
          formatter: function (row, column, cellValue) {
            return cellValue + '%';
          },
          width: '100'
        },

      ],
      tableDataColumnBreak: [
        {label: '姓名', isCol: true, prop: 'studentName', width: '100'},
        {label: '关卡名称', isCol: true, prop: 'relationName', width: '150'},
        {
          label: '总正确率', isCol: true, prop: 'correcRate',
          formatter: function (row, column, cellValue) {
            return cellValue + '%';
          },
          width: '100'
        },
      ],
      params: {
        pageNum: 1,
        pageSize: 20
      },
      pageSize: 20,
      pageNum: 1,
      total: 0,
    }
  },
  computed: {
    getMaxLength() {
      let arr = [];
      let maxLength = 0;
      if (this.tableData === []) {
        return 200;
      } else {
        this.tableData.forEach(item => {
          if (item.list) {
            item.list.forEach(listItem => {
              arr.push(listItem.knowledgeName.length)

            })
            for (let i = 0; i < arr.length; i++) {
              if (maxLength < arr[i]) {
                maxLength = arr[i]
              }
            }
          }
        })
        return maxLength * 15
      }
    },
    errorLength() {
      let arr = [];
      let maxLength = 0;
      if (this.tableData === []) {
        return 200;
      } else {
        this.tableData.forEach(item => {
          if (item.list) {
            item.list.forEach(listItem => {
              if (listItem.errorList) {
                let strLength = 0;
                listItem.errorList.forEach(errorItem => {
                  if (errorItem.str) {
                    strLength += errorItem.str.length
                  }
                })
                arr.push(strLength)
                for (let i = 0; i < arr.length; i++) {
                  if (maxLength < arr[i]) {
                    maxLength = arr[i]
                  }
                }
              }
            })
          }
        })
        if (maxLength !== 0) {
          return maxLength * 11
        } else {
          return 150
        }
      }
    },
    getDiagnosisMsgLength() {
      let arr = [];
      let maxLength = 0;
      if (this.tableData === []) {
        return 200;
      } else {
        this.tableData.forEach(item => {
          if (item.list) {
            item.list.forEach(listItem => {
              arr.push(listItem.diagnosisMsg.length)
            })
            for (let i = 0; i < arr.length; i++) {
              if (maxLength < arr[i]) {
                maxLength = arr[i]
              }
            }
          }
        })
        return maxLength * 15
      }
    },
  },
  methods: {
    //键盘按下enter搜索事件
    keyupSubmit() {
      document.onkeydown = e => {
        const _key = window.event.keyCode
        if (_key === 13) {
          this.queryByName();
        }
      }
    },
    leftDepTree() {
      getDeptTree({deptType: "1"}).then(response => {
        if (response.code === 200) {
          this.treeData = response.data
          this.fore(this.treeData[0])
          this.params.relationType = this.relationType
          this.lock()
          this.queryUnlock(this.params)
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
    /*判断正确锁错误锁*/
    lock() {
      if (this.deblockingForm.status == "" || !this.deblockingForm.status) {
        this.params.isLock = "0"
        /*} else if (this.relationType == 1) {
          this.params.isLock = "2"*/
      } else {
        this.params.isLock = this.deblockingForm.status
      }
    },

    /*卡片点击事件*/
    rankingTabsEvent(val) {
      // console.log(val)
      this.lock()
      this.params.relationType = this.relationType;
      this.params.studentName = this.deblockingForm.name;
      this.params.pageNum = 1;
      this.params.pageSize = 20;
      this.queryUnlock(this.params)
    },
    /*左侧tree树点击事件*/
    handleNodeClick(val, node, component) {
      this.fore(val)
      this.params.pageNum = 1;
      this.params.pageSize = 20;
      this.params.relationType = this.relationType
      this.lock()
      this.params.studentName = this.deblockingForm.name
      this.queryUnlock(this.params)
    },
    /*查询右侧数据 解锁数据*/
    queryUnlock(params) {
      pagingQueryUnlock(params).then(response => {
        if (response.code === 200) {
          this.tableData = response.rows;
          this.total = response.total;
          this.pageNum = response.pageNum;
          this.pageSize = response.pageSize;
          if (this.tableData) {
            this.tableData.forEach(item => {
              if (item.errorList) {
                if (item.errorList.length < 5) {
                  item.errorList.forEach(errorItem => {
                    errorItem.str = errorItem.exercisesNum + '=' + errorItem.errorAnswers;
                  })
                } else {
                  item.errorList.slice(0, 5).forEach(
                    errorItem => {
                      errorItem.str = errorItem.exercisesNum + '=' + errorItem.errorAnswers
                    }
                  )
                }

              }
              if (item.list) {
                let num = 0;
                item.list.forEach(listItem => {
                  if (listItem.errorList) {
                    let arr = [];
                    if (listItem.errorList.length < 5) {
                      listItem.errorList.forEach(errorItem => {
                        errorItem.str = errorItem.exercisesNum + '=' + errorItem.errorAnswers;
                        arr.push(errorItem.str)
                      })
                    } else {
                      listItem.errorList.slice(0, 5).forEach(errorItem => {
                        errorItem.str = errorItem.exercisesNum + '=' + errorItem.errorAnswers;
                        arr.push(errorItem.str)
                      })
                    }
                    listItem.newErrorList = arr;
                  }
                  if (listItem.correcRate === 100) {
                    num++;
                  }
                })
                if (item.list.length === num) {
                  item.flag = true;
                }
              }
            })
          }
        }
      })
    },
    /*查询按钮事件*/
    queryByName() {
      this.lock()
      this.params.studentName = this.deblockingForm.name
      this.queryUnlock(this.params)
    },
    /*重置*/
    reset() {
      this.deblockingForm.status = ""
      this.deblockingForm.name = ""
      this.queryByName()
    },
    /*行解锁事件*/
    rowUnlock(row) {
      this.multipleSelection = []
      this.multipleSelection.push(row)
      this.unlock()
    },
    /*解锁*/
    unlock() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: '请选择您要解锁的内容',
          type: 'warning'
        })
        return;
      } else {
        this.$confirm('此操作将解锁此数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          modifyUnlockById(JSON.stringify(this.multipleSelection)).then(response => {
            this.$message.success("解锁成功！")
            this.queryUnlock(this.params)
          })
        })
      }
    },
    //知识点训练、错题本表头
    headerName({row, column, rowIndex, columnIndex}) {
      if (column.label === '查看错题') {
        return 'errorList'
      }
      if (column.label === '错题详情') {
        return 'errorList'
      }
    },

    cellClsNm({row, column, rowIndex, columnIndex}) {
      if (column.label === '错题详情') {
        return 'errorList'
      }
      if (column.label === '查看错题') {
        return 'errorList'
      }
      if (column.label === '知识点名称') {
        return 'klgName'
      }
      if (column.label === '错因诊断') {
        return 'list'
      }
      if (column.label === '错题诊断') {
        return 'diagnosisMsg'
      }
    },
    /*复选框勾选事件*/
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deblock() {
      this.multipleSelection.forEach(item => {
        item.status = '已解锁'
      });
      this.$refs.multipleTable.clearSelection()//全不选
    },
    handleSizeChange(val) {
      this.params.pageSize = val;
      this.pageSize = val;
      this.queryUnlock(this.params);

    },
    handlePageNum(val) {
      this.params.pageNum = val;
      this.pageSize = val;
      this.queryUnlock(this.params)
    }
  }
}
</script>

<style lang="scss" scoped>
.el-tabs {
  height: calc(100% - 10px);

  /deep/ .el-tabs__content {
    height: calc(100% - 40px);
    padding: 5px 5px 0 5px;

    .mainTop {
      height: 54px;
    }

    .el-tab-pane {
      margin-top: 10px;
      height: calc(100% - 64px);

      .shibai {
        border: 1px #ddd solid;
        height: calc(100% - 40px);
      }

      .el-table {
        overflow: auto;

        .el-table__header-wrapper {
          .errorList {
            .cell {
              text-align: center;
              //width: 80px;
            }

          }
        }

        .cellDiv {
          color: red;
          width: 100%;
          display: flex;
          padding-left: 10px;

          span:first-of-type {
            display: inline-block;
            text-align: right;
          }

          span:nth-of-type(2), span:nth-of-type(4) {
            color: #5a5e66;
            font-size: 16px;
            font-weight: 600;
            display: inline-block;
            margin: 0 5px;
          }
        }

        .el-table__expanded-cell {
          padding: 0;

          .errorForm {
            width: 100%;

            .el-form-item {
              width: 100%;
              display: flex;
              justify-content: space-between;

              .el-form-item__label {
                width: 25%;
                text-align: left;
              }

              .el-form-item__content {
                width: 70%;

                span {
                  //word-break: break-word;
                  color: red;
                  display: inline-block;
                  text-align: left;
                  //white-space: pre-line;
                  margin-right: 5px;
                }
              }
            }
          }

          .errorForm:first-of-type {
            margin-top: 20px;
          }
        }

        tr {
          td, th {
            color: #909399;

            .cell {
              padding: 0;
              text-align: center;

              .diagnose {
                text-align: left;
                margin-left: 10px;
              }
            }

          }

          .errorList {
            border-right: none;
          }

          .klgName {
            padding: 10px;

            .cell {
              color: red;
            }
          }

          .diagnosisMsg {
            padding: 10px;

            .cell {
              text-align: left;
              color: red;
              word-break: break-word;
              white-space: pre-line;
            }
          }

          .errorList {
            .cell {
              padding-left: 10px;
              word-break: break-word;
              color: red;
              text-align: left;

              span {
                display: inline-block;
                padding-right: 5px;
              }
            }
          }

        }


      }

      .chaungguan {
        .el-table__header-wrapper {
          .el-table__header {
            width: 100%;

            .cellDiv {
              color: red;
              justify-content: center;

              span:nth-of-type(3) {
                color: #5a5e66;
                font-size: 16px;
                font-weight: 600;
                display: inline-block;
                margin: 0 5px;
              }
            }
          }

        }

        .el-table__body-wrapper {
          .errorList {
            .cell {
              & > div {
                display: flex;
                flex-direction: row;
              }
            }
          }
        }

        .list {
          .cell {
            color: red;
            display: flex;
            flex-direction: column;
            align-items: self-start;
            padding-left: 10px;
          }
        }

        .errorForm {
          width: 100%;

          .el-form-item {
            width: 100%;

            .el-form-item__content {
              width: 100%;
              display: flex;
              justify-content: flex-start;
              flex-wrap: wrap;
            }
          }
        }

      }

      .el-table__header-wrapper {
        table {
          & > colgroup {
            col {
              &:last-of-type {
                width: 17px !important;
              }
            }
          }
        }
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
        background-color: transparent;
      }

      .page {
        display: flex;
        justify-content: space-between;
      }
    }
  }
}
</style>

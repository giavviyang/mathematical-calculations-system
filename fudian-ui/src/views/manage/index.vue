<template>
  <div class="mainHeight">
    <main-flexible-tree :data="treeData" :defaultProps="TreeDefaultProps" @handleNodeClick="handleNodeClick">
      <div slot="mainRight" class="mainHeight">
        <container-top class="mainTop">
          <el-form v-model="queryForm" :inline="true" slot="ipt">
            <el-form-item label="组题名称：">
              <el-input v-model="input" placeholder="请输入内容" clearable></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="onSearch">搜索
              </el-button>
            </el-form-item>
          </el-form>
        </container-top>
        <slot-table
          class="mainCenter"
          :pageSize="pageSize"
          :pageNum="pageNum"
          :total="total"
          @handleChange="handleChange" style="height: calc(100% - 70px)">
          <el-table
            slot="table"
            row-key="id"
            align="left"
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            class="table-style"
            empty-text=" "
            size="mini"
            ref="tableDataline"
            style="width: 100%;height: 100%"
            :data="tableDataline">
            <el-table-column
              label="序号"
              type="index">
            </el-table-column>
            <el-table-column show-overflow-tooltip align="center"
                             v-for="(item, index) in tableDatalineCol"
                             :key="index"
                             :prop="item.prop"
                             :label="item.label">
            </el-table-column>
            <el-table-column
              label="知识点/占比"
              prop="classification"
              width="500px">
              <template slot="header">
                <div class="cellDiv">
                  <span>知识点</span><span>/</span> 占比
                </div>
              </template>
              <template slot-scope="scope">
                <div class="cellDiv" v-for="listItem in scope.row.classification"
                     :key="listItem.classificationId"
                     v-show="listItem.classificationMsg">
                  <span>{{ listItem.classificationMsg }}</span> <span>/</span> {{ listItem.percentage }}%
                </div>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              prop="desc"
              min-width="100" max-width="200">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="handleClick(scope.row)">查看详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </slot-table>
      </div>
    </main-flexible-tree>
    <el-dialog title="组题分析" width="90%" :visible.sync="dialogTableVisible"
               class="dialog-style dialog-basic dialogTable">
      <el-tabs v-model="activeName" type="border-card" @tab-click="handleTabClick">
        <el-tab-pane label="学生分析" name="first">
          <slot-table
            class="mainCenter"
            :pageSize="studentPageSize"
            :pageNum="studentPageNum"
            :total="studentTotal"
            @handleChange="handleChangeStudent">
            <el-table :data="studentData" slot="table" stripe
                      border :cell-class-name="cellClsNm" height="100%" :header-cell-class-name="headerName">
              <el-table-column type="index" width="50" align="center" label="序号"
                               :index="customIndex"/>
              <el-table-column prop="studentName" label="姓名" width="150px"></el-table-column>
              <el-table-column prop="sumCorrectRate" label="成绩" width="150px"></el-table-column>
              <el-table-column prop="avgAnswerTime" label="平均用时" width="100px">
                <template slot-scope="scope">
                  <!--              {{ scope.row.avgAnswerTime }}秒-->
                  <span v-show="String(scope.row.avgAnswerTime).indexOf('.')==-1"
                        v-if="scope.row.avgAnswerTime">{{ scope.row.avgAnswerTime }}秒</span>
                  <span v-show="String(scope.row.avgAnswerTime).indexOf('.')!=-1" v-if="scope.row.avgAnswerTime">
                {{ String(scope.row.avgAnswerTime).slice(0, String(scope.row.avgAnswerTime).indexOf('.') + 3) }}秒
              </span>
                </template>
              </el-table-column>
              <el-table-column prop="classification" label="知识点名称" :min-width="classificationLength">
                <template slot="header">
                  <div class="cellDiv">
                    <span>知识点</span><span>/</span>正确率
                  </div>
                </template>
                <template slot-scope="scope">
                  <div class="cellDiv" v-for="listItem in scope.row.classifyDetails"
                       :key="listItem.classificationId"
                       v-show="listItem.classificationMsg">
                    <span>{{ listItem.classificationMsg }}</span> <span>/</span> {{ listItem.correctRate }}%
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="errorTopics" label="查看错题" :min-width="errorTopicsLength">
                <template slot-scope="scope">
                  <div v-for="(item,index) in scope.row.classifyDetails" :key="item.classificationId">
                    {{ item.errorTopics }}
                  </div>
                </template>
              </el-table-column>
              <el-table-column type="expand" width="50">
                <template slot-scope="props">
                  <el-form v-for="(item,index) in props.row.classifyDetails" :key="item.classificationId"
                           :class="{errorForm:item.errorTopicsForm}">
                    <el-form-item :label="item.classificationMsg" v-show="item.errorTopicsForm">
                      <span>{{ item.errorTopicsForm }}</span>
                    </el-form-item>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column prop="diagnosisMsg" label="错题诊断" :min-width="getMaxLength">
                <template slot-scope="scope">
                  <div v-for="(item,index) in scope.row.classifyDetails" :key="item.classificationId">
                   <span v-show="item.diagnosisMsg!=='无错题'">
                     {{item.diagnosisMsg }}
                   </span>
                  </div>
                  <div v-show="scope.row.sumCorrectRate===100">无错题</div>
                </template>
              </el-table-column>
              <el-table-column prop="createTime" label="答题时间" width="180px"></el-table-column>
            </el-table>
          </slot-table>
        </el-tab-pane>
        <el-tab-pane label="知识点分析" name="second">
          <slot-table
            class="mainCenter classifyTable"
            :pageSize="classifyPageSize"
            :pageNum="classifyPageNum"
            :total="classifyTotal"
            @handleChange="handleChangeClassify">
            <el-table :data="classifyData" slot="table" stripe
                      border :cell-class-name="cellClsClassify" height="100%">
              <el-table-column type="index" width="50" align="center" label="序号"
                               :index="classifyIndex"/>
              <el-table-column prop="classificationMsg">
                <template slot="header">
                  <div class="cellDiv">
                    <span>知识点</span><span>/</span> 占比
                  </div>
                </template>
                <template slot-scope="scope">
                  <div class="cellDiv">
                    <span>{{ scope.row.classificationMsg }}</span> <span>/</span> {{ scope.row.percentage }}%
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="sumCorrectRate" label="正确率" width="80px">
                <template slot-scope="scope">
                  <span v-show="String(scope.row.sumCorrectRate).indexOf('.')==-1">{{
                      scope.row.sumCorrectRate
                    }}%</span>
                  <span v-show="String(scope.row.sumCorrectRate).indexOf('.')!=-1">
                {{ String(scope.row.sumCorrectRate).slice(0, String(scope.row.sumCorrectRate).indexOf('.') + 3) }}%
              </span>
                </template>
              </el-table-column>
              <el-table-column prop="sumErrorNum" label="错题数量" width="80px" v-if="errorDetailsFlag"></el-table-column>
              <el-table-column prop="avgTime" label="平均用时" width="80px" v-if="errorDetailsFlag">
                <template slot-scope="scope">
                  <!--              {{ scope.row.avgTime }}秒-->
                  <span v-show="String(scope.row.avgTime).indexOf('.')==-1">{{ scope.row.avgTime }}秒</span>
                  <span v-show="String(scope.row.avgTime).indexOf('.')!=-1">
                {{ String(scope.row.avgTime).slice(0, String(scope.row.avgTime).indexOf('.') + 3) }}秒
              </span>
                </template>
              </el-table-column>
              <el-table-column prop="errorDetails" label="错题详情" v-if="errorDetailsFlag">
                <template slot-scope="scope">
                  <div v-for="(item,index) in scope.row.errorDetails" :key="index">
                    <span>{{ item.exercisesNum }}</span> 共 <span> {{ item.studentNum }}</span>人，姓名：<span>{{
                      item.studentName
                    }}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="errorList" label="查看错题" v-if="errorListFlag" width="400px">
                <template slot-scope="scope">
                  <div v-for="(item,index) in scope.row.errorList" :key="index">
                    {{ item }}
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="diagnosisMsg" label="错题诊断" width="250px">
              </el-table-column>
            </el-table>
          </slot-table>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>

<script>
import MainFlexibleTree from '@/components/public/MainBody/MainFlexibleTree'
import ContainerTop from '@/components/public/MainBody/ContainerTop'
import {queryStudentErrorTopic, queryClassifyErrorTopic} from '@/api/classroomTest/classroomTest'
import {pagingQuery} from '@/api/applet/classroomTest/manage'
import SlotTable from "@/components/public/table/SlotTable";
import {getDeptTree} from "@/api/system/dept";
import Page401 from "@/views/error/401";

export default {
  name: 'index',
  components: {Page401, SlotTable, MainFlexibleTree, ContainerTop},
  created() {
    this.leftDepTree();
    // window.addEventListener('keyup', this.onSearch);
    // this.testTabeGrid(this.queryForm)
    this.keyupSubmit();
  },
  data() {
    return {
      treeData: [],
      TreeDefaultProps: {
        children: "children",
        label: "deptName",
      },
      /*查询表单数据*/
      queryForm: {
        pageSize: 20,
        pageNum: 1,
        isRelease: 2,
        testName: '',
        classId: '',
      },
      classId: '',
      activeName: 'first',
      tableDataline: [],
      tableDatalineCol: [
        {label: '组题名称', prop: 'testName'},
        {label: '限制用时', prop: 'countTime'},
        {label: '总题数', prop: 'testTopicNum'},
      ],
      total: 0,
      pageNum: 1,
      pageSize: 20,
      studentPageSize: 20,
      studentPageNum: 1,
      studentTotal: 0,
      classifyPageSize: 20,
      classifyPageNum: 1,
      classifyTotal: 0,
      loading: true,
      input: '',
      studentData: [],
      classifyData: [],
      errorListFlag: false,
      errorDetailsFlag: false,
      dialogTableVisible: false,
      formLabelWidth: '120px',
      testId: '',
      // errorForm:'errorForm'
    }
  },
  computed: {
    //取表格知识点名称字数最多的宽度
    getMaxLength() {
      let itemArr = [];
      let maxLength = 0;
      if (this.studentData === []) {
        return false;
      } else {
        this.studentData.forEach(item => {
          var arr = [];
          if (item.classifyDetails) {
            item.classifyDetails.forEach(childItem => {
              arr.push(childItem.diagnosisMsg.length)
            })
          }
          itemArr.push(...arr);
          for (let i = 0; i < itemArr.length; i++) {
            if (maxLength < itemArr[i]) {
              maxLength = itemArr[i]
            }
          }
        })
        return maxLength * 13
      }
    },
    errorTopicsLength() {
      let itemArr = [];
      let maxLength = 0;
      if (this.studentData === []) {
        return false;
      } else {
        this.studentData.forEach(item => {
          var arr = [];
          if (item.classifyDetails) {
            item.classifyDetails.forEach(childItem => {
              if (childItem.errorTopics) {
                arr.push(childItem.errorTopics.length)
              }
            })
          }
          itemArr.push(...arr);
          for (let i = 0; i < itemArr.length; i++) {
            if (maxLength < itemArr[i]) {
              maxLength = itemArr[i]
            }
          }
        })
        return maxLength * 10
      }
    },
    classificationLength() {
      let itemArr = [];
      let maxLength = 0;
      if (this.studentData === []) {
        return false;
      } else {
        this.studentData.forEach(item => {
          var arr = [];
          if (item.classifyDetails) {
            item.classifyDetails.forEach(childItem => {
              if (childItem.classificationMsg) {
                arr.push(childItem.classificationMsg.length)
              }
            })
          }
          itemArr.push(...arr);
          for (let i = 0; i < itemArr.length; i++) {
            if (maxLength < itemArr[i]) {
              maxLength = itemArr[i]
            }
          }
        })
        return maxLength * 15
      }
    }
  },
  methods: {
    //自定义序号
    customIndex(index) {
      return (this.studentPageNum - 1) * (this.studentPageSize) + (index + 1);
    },
    //自定义序号
    classifyIndex(index) {
      return (this.classifyPageNum - 1) * (this.classifyPageSize) + (index + 1);
    },
    //键盘按下enter搜索事件
    keyupSubmit() {
      document.onkeydown = e => {
        const _key = window.event.keyCode
        if (_key === 13) {
          this.queryForm();
        }
      }
    },
    leftDepTree() {
      getDeptTree({deptType: "1"}).then(response => {
        if (response.code === 200) {
          this.treeData = response.data
          console.log(response.data)
          this.fore(this.treeData[0])
          // params.classId=response.data[0].id;
          // this.queryForm.classId=response.data[0].id;
          this.testTabeGrid(this.queryForm)
          // this.getTable(this.params);
        }
      })
    },
    handleNodeClick(val, node, component) {
      console.log(val, node, component)
      this.fore(val)
      this.testTabeGrid(this.queryForm)
    },
    onSearch() {
      this.queryForm.testName = this.input;
      this.testTabeGrid(this.queryForm)
    },
    /*循环查找tree树中的班级节点*/
    fore(obj) {
      if (obj && obj.nodeType !== "2") {
        this.fore(obj.children[0])
      } else {
        this.classId = obj.id;
        this.queryForm.classId = obj.id;
      }
      return;
    },
    /* gird数据请求 */
    testTabeGrid(params) {
      const _this = this
      pagingQuery(params).then(function (response) {
        console.log('111', params, response)
        _this.tableDataline = response.rows;
        _this.total = response.total;
        _this.loading = false;
        _this.pageSize = response.pageSize;
        _this.pageNum = response.pageNum;

      })
    },
    handleTabClick(tab, event) {
      console.log(tab, event);
      if (tab.name === 'first') {
        this.getStudentErrorTopic(20, 1, this.testId, this.classId);
      } else if (tab.name === 'second') {
        this.getClassifyErrorTopic(20, 1, this.testId,this.classId);
      }
    },
    handleChange(pageSize, pageNum) {
      this.pageSize = pageSize
      this.pageNum = pageNum;
      this.queryForm.pageSize = pageSize;
      this.queryForm.pageNum = pageNum;
      this.testTabeGrid(this.queryForm)
    },
    handleChangeStudent(pageSize, pageNum) {
      this.studentPageSize = pageSize
      this.studentPageNum = pageNum;
      this.getStudentErrorTopic(this.studentPageSize, this.studentPageNum, this.testId, this.classId);
    },
    handleChangeClassify(pageSize, pageNum) {
      this.classifyPageSize = pageSize
      this.classifyPageNum = pageNum;
      this.getStudentErrorTopic(this.classifyPageSize, this.classifyPageNum, this.testId, this.classId);
    },
    handleClick(val) {
      this.dialogTableVisible = true;
      this.testId = val.id;
      // this.getDetails(20, 1, val.id,this.classId)
      if (this.activeName === 'first') {
        this.getStudentErrorTopic(20, 1, val.id, this.classId);
      } else if (this.activeName === 'second') {
        this.getClassifyErrorTopic(20, 1, val.id,this.classId);
      }

    },
    headerName({row, column, rowIndex, columnIndex}) {
      if (column.label === '查看错题') {
        return 'errorList'
      }
    },
    cellClsNm({row, column, rowIndex, columnIndex}) {
      if (column.label === '查看错题') {
        return 'errorList'
      }
      if (column.label === '错题诊断') {
        return 'diagnosisMsg'
      }
      if (column.label === '知识点') {
        return 'studentCellDiv'
      }
    },
    cellClsClassify({row, column, rowIndex, columnIndex}) {
      if (column.label === '错题详情') {
        return 'errorListDetails'
      }
      if (column.label === '错题诊断') {
        return 'ClassifyDiagnosisMsg'
      }
      if (column.label === '查看错题') {
        return 'classifyErrorList'
      }
    },
    getStudentErrorTopic(pageSize, pageNum, testId, classId) {
      queryStudentErrorTopic({
        pageSize,
        pageNum,
        testId,
        classId,
      }).then(res => {
        if (res.code === 200) {
          this.studentData = res.rows;
          this.studentPageSize = res.pageSize;
          this.studentPageNum = res.pageNum;
          this.studentTotal = res.total;
          if (this.studentData) {
            this.studentData.forEach(item => {
              item.classifyDetails.forEach(classifyDetailsItem => {
                if (classifyDetailsItem.errorTopics) {
                  let arr = classifyDetailsItem.errorTopics.split(',');
                  let arrForm = classifyDetailsItem.errorTopics.split(',');
                  console.log(arr, arrForm)
                  let str = '';
                  let strForm = '';
                  if (arr.length > 5) {
                    arr = arr.splice(0, 5);
                    str = arr.join('  ');
                  } else {
                    str = arr.join('  ');
                  }
                  strForm = arrForm.join('  ');
                  classifyDetailsItem.errorTopics = str;
                  classifyDetailsItem.errorTopicsForm = strForm;
                }
              })
            })
          }
        }
      });
    },
    getClassifyErrorTopic(pageSize, pageNum, testId,classId) {
      queryClassifyErrorTopic({pageSize, pageNum, testId,classId}).then(res => {
        if (res.code === 200) {
          this.classifyData = res.rows;
          this.classifyPageSize = res.pageSize;
          this.classifyPageNum = res.pageNum;
          this.classifyTotal = res.total;
          res.rows.forEach(item => {
            if (item.errorDetails) {
              this.errorDetailsFlag = true;
              this.errorListFlag = false;
            } else {
              this.errorDetailsFlag = false;
              this.errorListFlag = true;
            }
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.mainTop {
  height: 67px;
}

.cellDiv {
  color: red;
  width: 100%;
  display: flex;
  padding: 0;

  span:first-of-type {
    display: inline-block;
    width: 80%;
    text-align: right;
  }

  span:nth-of-type(2) {
    color: #5a5e66;
    font-size: 16px;
    font-weight: 600;
    display: inline-block;
    margin: 0 5px;
  }
}

/deep/ .dialogTable {
  .el-dialog {
    height: 100%;

    .el-dialog__body {
      height: calc(100% - 44px);

      .el-tabs {
        height: 100%;
      }

      .el-tabs__item {
        text-align: center;
        padding: 0 20px;
      }

      .el-tabs__content {
        height: calc(100% - 40px);
        padding-bottom: 0;

        .el-tab-pane {
          height: 100%;

          .mainCenter {
            height: 100%;

            .table {
              height: calc(100% - 44px);

              .cellDiv {
                color: red;
                width: 100%;
                display: flex;
                padding: 0;

                span:first-of-type {
                  display: inline-block;
                  width: 80%;
                  text-align: right;
                }

                span:nth-of-type(2) {
                  color: #5a5e66;
                  font-size: 16px;
                  font-weight: 600;
                  display: inline-block;
                  margin: 0 5px;
                }
              }

              .studentCellDiv {
                .cell {
                  white-space: nowrap;
                  //text-align: left;
                  color: red;
                }
              }

              .diagnosisMsg {
                padding-left: 10px;

                .cell {
                  white-space: nowrap;
                  text-align: left;
                  color: red;

                }
              }

              .ClassifyDiagnosisMsg {
                padding: 10px;

                .cell {
                  text-align: left;
                  color: red;
                  word-break: break-word;
                  white-space: pre-line;
                }
              }

              .errorListDetails {
                .cell {
                  text-align: left;

                  span {
                    color: red;
                  }
                }
              }

              .classifyErrorList {
                .cell {
                  display: flex;
                  flex-wrap: wrap;

                  div {
                    margin-right: 5px;
                    color: red;

                  }
                }
              }
            }

            .el-table__header-wrapper {
              .errorList {
                border-right: none;
              }
            }

            .el-table__body-wrapper {
              .errorList {
                border-right: none;

                .cell {
                  white-space: nowrap;
                  text-align: left;
                  color: red;
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
                        width: 100%;
                        display: flex;
                        justify-content: flex-start;
                        flex-wrap: wrap;
                        word-break: break-word;
                        color: red;
                        text-align: left;
                        white-space: pre-line;
                        margin-right: 5px;
                      }

                    }
                  }
                }
                .errorForm:first-of-type{
                  margin-top: 20px;
                }
              }
            }
          }
        }
      }

    }
  }
}
</style>

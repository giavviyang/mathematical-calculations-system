<template>
  <div class="mainHeight">
    <main-flexible-tree :data="treeData" :defaultProps="TreeDefaultProps" @handleNodeClick="handleNodeClick">
      <div slot="mainRight" class="mainHeight" style="display: flex;flex-direction: column">
        <container-top class="mainTop">
          <el-form v-model="form" :inline="true" slot="ipt">
            <el-form-item label='知识点：'>
              <el-cascader
                size="mini"
                ref="cascaderAddr"
                v-model="form.classificationId"
                @change="addHandleTypeChange"
                placeholder="请选择知识点"
                :props="defaultProps"
                :options="cascaderForm.options"
                filterable
                clearable/>
            </el-form-item>
            <el-form-item label="答题时间：" class="time">
              <el-date-picker
                v-model="cascaderForm.time"
                type="datetimerange"
                :picker-options="cascaderForm.time"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                @change="addTimeChange"
                align="right">
              </el-date-picker>
            </el-form-item>
          </el-form>
        </container-top>
        <el-tabs v-model="activeName" type="border-card" @tab-click="handleTabClick">
          <el-tab-pane label="学生分析" name="first">
            <slot-table class="mainCenter"
                        :pageSize="pageSize"
                        :pageNum="pageNum"
                        :total="total"
                        @handleChange="handleChange">
              <el-table
                border
                stripe
                slot="table"
                :data="tableData"
                style="width: 100%"
                height="100%"
                :default-sort="{prop: 'accuracy', order: 'ascending'}"
                :cell-class-name="cellClsNm" :header-cell-class-name="headerName">
                <el-table-column
                  type="index"
                  label="排名"
                  width="50">
                </el-table-column>
                <el-table-column
                  prop="studentName"
                  label="姓名" width="100">
                </el-table-column>
                <el-table-column
                  prop="classificationName"
                  label="知识点名称">
                </el-table-column>
                <el-table-column
                  prop="correcRate"
                  label="正确率"
                  width="100">
                  <template slot-scope="scope">
                <span v-if="String(scope.row.correcRate).indexOf('.')==-1">
                  {{ scope.row.correcRate }}%
                </span>
                    <span v-if="String(scope.row.correcRate).indexOf('.')!=-1">
                  {{ String(scope.row.correcRate).slice(0, String(scope.row.correcRate).indexOf('.')) }}%
                </span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="timeProportion"
                  label="平均用时"
                  width="100">
                  <template slot-scope="scope">
                <span v-if="String(scope.row.timeProportion).indexOf('.')==-1">
                  {{ scope.row.timeProportion }}秒
                </span>
                    <span v-if="String(scope.row.timeProportion).indexOf('.')!=-1">
                  {{ String(scope.row.timeProportion).slice(0, String(scope.row.timeProportion).indexOf('.') + 3) }}秒
                </span>
                  </template>
                </el-table-column>
                <el-table-column prop="wrongQuestion" label="查看错题">
                  <template slot-scope="scope">
<!--                    {{ scope.row.wrongQuestion }}-->
                    <div v-show="scope.row.correcRate===100">无错题</div>
                    <div v-show="scope.row.correcRate!==100"> {{ scope.row.wrongQuestion }}</div>
                  </template>
                </el-table-column>
                <el-table-column type="expand" width="50">
                  <template slot-scope="props">
                    <el-form :class="{errorForm:props.row.correcRate!==100}">
                      <el-form-item :label="props.row.classificationName" v-show="props.row.correcRate!==100">
                        <span>{{ props.row.wrongQuestionForm }}</span>
                      </el-form-item>
                    </el-form>
                  </template>
                </el-table-column>
                <el-table-column label="错题诊断" width="200">
                  <template slot-scope="scope">
                      <div v-show="scope.row.correcRate===100">无错题</div>
                      <div v-show="scope.row.correcRate!==100"> {{ scope.row.diagnosisMsg }}</div>
                  </template>
                </el-table-column>
              </el-table>
            </slot-table>
          </el-tab-pane>
          <el-tab-pane label="知识点分析" name="second" class="klgTab">
            <el-form>
              <el-form-item label="出题方式：">
                <el-radio-group v-model="isRandom" @change="handleRadioChange">
                  <el-radio label='0'>随机出题</el-radio>
                  <el-radio label='1'>统一出题</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-form>
            <slot-table class="mainCenter"
                        :pageSize="classifyPageSize"
                        :pageNum="classifyPageNum"
                        :total="classifyTotal"
                        @handleChange="classifyChange">
              <el-table border
                        stripe
                        slot="table"
                        :data="classifyData"
                        style="width: 100%"
                        height="100%"
                        :default-sort="{prop: 'accuracy', order: 'ascending'}"
                       :cell-class-name="cellClsNm" :header-cell-class-name="headerName"
                        v-if="errorTopicFlag">
                <el-table-column
                  type="index"
                  width="50"
                  label="序号">
                </el-table-column>
                <el-table-column prop="classificationName" label="知识点名称">
                </el-table-column>
                <el-table-column prop="sumCorrectRate" label="正确率" width="100">
                  <template slot-scope="scope">
                <span v-if="String(scope.row.correcRate).indexOf('.')==-1">
                  {{ scope.row.correcRate }}%
                </span>
                    <span v-if="String(scope.row.correcRate).indexOf('.')!=-1">
                  {{ String(scope.row.correcRate).slice(0, String(scope.row.correcRate).indexOf('.') + 3) }}%
                </span>
                  </template>
                </el-table-column>
                <el-table-column prop="timeProportion" label="平均用时" width="100">
                  <template slot-scope="scope">
                    <span v-if="String(scope.row.timeProportion).indexOf('.')==-1">
                      {{ scope.row.timeProportion }}秒
                    </span>
                    <span v-if="String(scope.row.timeProportion).indexOf('.')!=-1">
                      {{
                        String(scope.row.timeProportion).slice(0, String(scope.row.timeProportion).indexOf('.') + 3)
                      }}秒
                    </span>
                  </template>
                </el-table-column>
                <el-table-column prop="errorTopics" label="查看错题">
                  <template slot-scope="scope">
<!--                    {{ scope.row.errorTopic }}-->
                    <div v-show="scope.row.correcRate===100">无错题</div>
                    <div v-show="scope.row.correcRate!==100"> {{ scope.row.errorTopic }}</div>
                  </template>
                </el-table-column>
                <el-table-column type="expand" width="50">
                  <template slot-scope="props">
                    <el-form :class="{errorForm:props.row.correcRate!==100}">
                      <el-form-item :label="props.row.classificationName" v-show="props.row.correcRate!==100">
                        <span>{{ props.row.errorTopicForm }}</span>
                      </el-form-item>
                    </el-form>
                  </template>
                </el-table-column>
                <el-table-column prop="diagnosisMsg" label="错题诊断" width="200px">
                  <template slot-scope="scope">
                    <div v-show="scope.row.correcRate===100">无错题</div>
                    <div v-show="scope.row.correcRate!==100"> {{ scope.row.diagnosisMsg }}</div>
                  </template>
                </el-table-column>
              </el-table>
              <el-table border
                        stripe
                        slot="table"
                        :data="classifyData"
                        style="width: 100%"
                        height="100%"
                        :default-sort="{prop: 'accuracy', order: 'ascending'}"
                       :cell-class-name="cellClsNm" :header-cell-class-name="headerName"
                        v-if="wrongQuestionListFlag">
                <el-table-column
                  type="index"
                  width="50"
                  label="序号">
                </el-table-column>
                <el-table-column prop="classificationName" label="知识点名称">
                </el-table-column>
                <el-table-column prop="sumCorrectRate" label="正确率" width="100">
                  <template slot-scope="scope">
                <span v-if="String(scope.row.correcRate).indexOf('.')==-1">
                  {{ scope.row.correcRate }}%
                </span>
                    <span v-if="String(scope.row.correcRate).indexOf('.')!=-1">
                  {{ String(scope.row.correcRate).slice(0, String(scope.row.correcRate).indexOf('.') + 3) }}%
                </span>
                  </template>
                </el-table-column>
                <el-table-column prop="timeProportion" label="平均用时" width="100">
                  <template slot-scope="scope">
                    <span v-if="String(scope.row.timeProportion).indexOf('.')==-1">
                      {{ scope.row.timeProportion }}秒
                    </span>
                    <span v-if="String(scope.row.timeProportion).indexOf('.')!=-1">
                      {{
                        String(scope.row.timeProportion).slice(0, String(scope.row.timeProportion).indexOf('.') + 3)
                      }}秒
                    </span>
                  </template>
                </el-table-column>
                <el-table-column prop="wrongQuestionList" label="错题详情">
                  <template slot-scope="scope">
                    <div v-for="(item,index) in scope.row.wrongQuestionLists" :key="index">
                      <span>{{ item.wrongQuestion }}</span>
                      共 <span> {{ item.studentNum }}</span>人，
                      姓名：<span>{{ item.studentName }}</span>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column prop="diagnosisMsg" label="错题诊断" width="200px"></el-table-column>
              </el-table>
            </slot-table>
          </el-tab-pane>
        </el-tabs>
      </div>
    </main-flexible-tree>
  </div>
</template>

<script>
import MainFlexibleTree from "@/components/public/MainBody/MainFlexibleTree";
import {getDeptTree} from "@/api/system/dept";
import ContainerTop from "@/components/public/MainBody/ContainerTop";
import SlotTable from "@/components/public/table/SlotTable";
import {getDataByEparentCode} from '@/api/system/dict/newDict'
import {getKLCByParams} from '@/api/usually/knowledgemaintain/knowledge'
import {pagingQueryAnswer, pagingQueryClassify} from '@/api/usually/receiveMaterial'

export default {
  name: "index",
  components: {SlotTable, ContainerTop, MainFlexibleTree},
  created() {
    let end = new Date();
    let start = new Date();
    start.setTime(start.getTime() - 3600 * 1000 * 24);
    this.form.beginTime = start
    this.form.endTime = end
    this.leftDepTree();
    getDataByEparentCode({dictType: 'classfiy', eparentCode: '-'}).then(response => {
      this.cascaderForm.options = response.data;
    })
  },
  data() {
    return {
      treeData: [],
      TreeDefaultProps: {
        children: "children",
        label: "deptName",
      },
      cascaderForm: {
        options: [],
        time: []
      },
      defaultProps: {
        leaf: 'leaf',
        value: 'dictCode',
        label: 'fullName',
        lazy: true,
        lazyLoad(node, resolve) {
          const {level} = node;
          setTimeout(() => {
            const {data, level, path} = node;
            if (!data || (data && data.haschild == false) || (data && data.leaf == true)) {
              return resolve([])
            } else if (level == 3) {
              let params = {
                classfiyCode: node.data.dictCode,
                pageNum: 1,
                pageSize: 99,
              }
              getKLCByParams(params).then(response => {
                let returnData = [];
                for (let i = 0; i < response.rows.length; i++) {
                  let data = response.rows[i];
                  returnData.push({dictCode: data.classificationId, fullName: data.classificationMsg, leaf: true})
                }
                resolve(returnData);
              })
            } else {
              getDataByEparentCode({dictType: 'classfiy', eparentCode: node.data.dictCode}).then(response => {
                resolve(response.data);
              })
            }
          }, 100);
        }
      },
      form: {
        pageSize: 20,
        pageNum: 1,
        classId: '',
        classificationId: '',
        beginTime: '',
        endTime: '',
        isRandom: 1,
      },
      isRandom: 1,
      activeName: 'first',
      tableData: [],
      errorTopicFlag: true,
      wrongQuestionListFlag: false,
      classifyData: [],
      pageSize: 20,
      pageNum: 1,
      total: 1,
      classifyPageSize: 20,
      classifyPageNum: 1,
      classifyTotal: 0,
    }
  },
  // computed: {
  //   getMaxLength() {
  //     let arr = [];
  //     let maxLength = 0;
  //     if (this.tableData === []) {
  //       return false;
  //     } else {
  //       console.log('11111', this.tableData)
  //       this.tableData.forEach(item => {
  //         if (item.wrongQuestion) {
  //           arr.push(item.wrongQuestion.length)
  //           for (let i = 0; i < arr.length; i++) {
  //             if (maxLength < arr[i]) {
  //               maxLength = arr[i]
  //             }
  //           }
  //         }
  //         console.log(maxLength)
  //       })
  //       return maxLength * 1.5
  //     }
  //   },
  //   errorTopicsLength() {
  //     let arr = [];
  //     let maxLength = 0;
  //     if (this.classifyData === []) {
  //       return false;
  //     } else {
  //       this.classifyData.forEach(item => {
  //         if (item.errorTopic) {
  //           arr.push(item.errorTopic.length)
  //           for (let i = 0; i < arr.length; i++) {
  //             if (maxLength < arr[i]) {
  //               maxLength = arr[i]
  //             }
  //           }
  //         }
  //       })
  //       return maxLength * 1.8
  //     }
  //   },
  // },
  methods: {
    leftDepTree() {
      getDeptTree({deptType: "1"}).then(response => {
        if (response.code === 200) {
          this.treeData = response.data
          this.fore(this.treeData[0])
          this.pagingQueryAnswer(this.form)
        }
      })
    },
    /*关联知识点点击事件*/
    addHandleTypeChange(val) {
      if (val.length > 0) {
        this.form.classificationId = val[val.length - 1]
        this.getTable();
      } else {
        this.form.classificationId = ''
        this.getTable();
      }
    },
    /*时间点击事件*/
    addTimeChange(val) {
      // console.log("-----------------------" + JSON.stringify(val))
      if(val){
        this.form.beginTime = val[0]
        this.form.endTime = val[1];
        this.getTable();
      }else {
        let end = new Date();
        let start = new Date();
        start.setTime(start.getTime() - 3600 * 1000 * 24);
        this.form.beginTime = start
        this.form.endTime = end
        this.getTable();
      }
    },
    /*循环查找tree树中的班级节点*/
    fore(obj) {
      if (obj && obj.nodeType !== "2") {
        this.fore(obj.children[0])
      } else {
        this.form.classId = obj.id
      }
      return;
    },
    /*查询右侧数据 消息数据*/
    pagingQueryAnswer(params) {
      // console.log(params)
      pagingQueryAnswer(JSON.stringify(params)).then(response => {
        if (response.code === 200) {
          // console.log(params, response)
          this.tableData = response.rows;
          this.total = response.total;
          this.pageSize = response.pageSize;
          this.pageNum = response.pageNum;
          if (this.tableData) {
            this.tableData.forEach(item => {
              if (item.wrongQuestion) {
                let toArr = item.wrongQuestion.split(',');
                let arr = toArr.filter(function(value,index,self){
                  return self.indexOf(value) === index;
                });
                let toArrForm = item.wrongQuestion.split(',');
                let arrForm = toArrForm.filter(function(value,index,self){
                  return self.indexOf(value) === index;
                });
                let str = '';
                let strForm = '';
                if (arr.length > 5) {
                  arr = arr.splice(0, 5);
                  str = arr.join(' ');
                } else {
                  str = arr.join(' ');
                }
                strForm = arrForm.join(' ');
                item.wrongQuestion = str;
                item.wrongQuestionForm = strForm;
                // console.log(item)
              }
            })
          }
        }
      })
    },
    /*查询右侧数据 消息数据*/
    pagingQueryClassify(params) {
      pagingQueryClassify(JSON.stringify(params)).then(response => {
        if (response.code === 200) {
          // console.log('12222', response)
          this.classifyData = response.rows;
          this.classifyTotal = response.total;
          this.classifyPageSize = response.pageSize;
          this.classifyPageNum = response.pageNum;
          if (this.classifyData) {
            this.classifyData.forEach(item => {
              if (item.errorTopic) {
                let toArr = item.errorTopic.split(',');
                let arr = toArr.filter(function(value,index,self){
                  return self.indexOf(value) === index;
                });
                let toArrForm = item.errorTopic.split(',');
                let arrForm = toArrForm.filter(function(value,index,self){
                  return self.indexOf(value) === index;
                });
                let str = '';
                let strForm = '';
                if (arr.length > 5) {
                  arr = arr.splice(0, 5);
                  str = arr.join('   ');
                } else {
                  str = arr.join('   ');
                }
                strForm = arrForm.join(' ');
                item.errorTopic = str;
                item.errorTopicForm = strForm;
              }
              if (item.wrongQuestionList) {
                item.wrongQuestionLists = [];
                item.wrongQuestionList.forEach(listItem => {
                  let itemFirstIndex = listItem.indexOf('|');
                  let itemLastIndex = listItem.lastIndexOf('|');
                  let obj = {};
                  obj.wrongQuestion = listItem.slice(0, itemFirstIndex);
                  obj.studentName = listItem.slice(itemFirstIndex + 1, itemLastIndex);
                  obj.studentNum = listItem.slice(itemLastIndex + 1);
                  item.wrongQuestionLists.push(obj);
                })
              }
            })
          }


        }
      })
    },
    handleNodeClick(val, node, component) {
      this.fore(val)
      this.activeName ='first';
      this.getTable();
    },
    /*tab切换*/
    handleTabClick(tab, event) {
      // console.log(tab, event);
      this.getTable();
      // console.log('555',this.isRandom);
      // console.log(this.form.isRandom)
    },
    getTable(){
      if (this.activeName === 'first') {
        this.pagingQueryAnswer(this.form);
        this.handleRadioChange(0);
      } else if (this.activeName === 'second') {
        this.isRandom = '0'
        this.form.isRandom = Number(this.isRandom);
        this.pagingQueryClassify(this.form)
      }

    },
    /*radio切换*/
    handleRadioChange(val) {
      this.isRandom = val;
      if (val == 0) {
        this.form.isRandom = Number(val);
        this.errorTopicFlag = true;
        this.wrongQuestionListFlag = false;
        this.pagingQueryClassify(this.form);

      } else if (val == 1) {
        this.form.isRandom = Number(val);
        this.errorTopicFlag = false;
        this.wrongQuestionListFlag = true;
        this.pagingQueryClassify(this.form);
      }

    },
    handleChange(pageSize, pageNum) {
      this.form.pageNum = pageNum;
      this.form.pageSize = pageSize;
      this.pagingQueryAnswer(this.form)
    },
    classifyChange(pageSize, pageNum) {
      this.form.pageNum = pageNum;
      this.form.pageSize = pageSize;
      this.pagingQueryClassify(this.form)
    },
    headerName({row, column, rowIndex, columnIndex}) {
      if (column.label === '查看错题') {
        return 'errorList'
      }
    },
    cellClsNm({row, column, rowIndex, columnIndex}) {
      if (column.label === '知识点名称') {
        return 'klgName'
      }
      if (column.label === '查看错题') {
        return 'errorList'
      }
      if (column.label === '错题诊断') {
        return 'ClassifyDiagnosisMsg'
      }
      if (column.label === '错题详情') {
        return 'errorListDetails'
      }
    },

  }
}
</script>
<style lang="scss" scoped>
.mainTop {
  //height: 100px;
  height: 67px;

  /deep/ .ipt {
    margin-bottom: -10px;
  }

  /deep/ .el-form {
    width: 100%;
    display: flex;

    .el-form-item {
      width: 50%;

      .el-form-item__content {
        width: 80%;

        .el-cascader {
          width: 100%;
        }
      }

    }
  }
}

/deep/ .el-tabs {
  height: calc(100% - 75px);

  .el-tabs__content {
    height: calc(100% - 30px);
    padding: 10px 10px 5px 10px;

    .el-tab-pane {
      height: calc(100% - 5px);

      .mainCenter {
        height: 100%;

        .el-table {
          .el-table__header-wrapper {
            .errorList {
              border-right: none;
            }
          }

          .el-table__body-wrapper {

            .el-table__row {
              .errorList {
                border-right: none;
                .cell {
                  //white-space: nowrap;
                  //text-align: left;
                  //color: red;
                  word-break: break-word;
                  color: red;
                  text-align: left;
                  //white-space: pre-line;
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

              .klgName {
                .cell {
                  color: red;
                  text-align: left;
                }

              }

              td {
                text-align: center;

                &:last-child {
                  .cell {
                    align-items: flex-start;
                    padding: 0 10px !important;
                  }
                }
              }
            }
          }

          .el-table__expanded-cell {
            //padding: 20px;
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

    .klgTab {
      .el-form-item {
        margin-bottom: 5px;
      }

      .mainCenter {
        height: calc(100% - 40px);
      }
    }
  }
}


</style>

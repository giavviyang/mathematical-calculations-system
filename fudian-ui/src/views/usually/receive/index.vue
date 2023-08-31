<template>
  <div class="mainHeight">
    <main-flexible-tree :data="treeData" :defaultProps="TreeDefaultProps" @handleNodeClick="handleNodeClick">
      <div slot="mainRight" class="mainHeight">
        <container-top class="mainTop">
          <el-form v-model="form" :inline="true" slot="ipt">
            <el-form-item label="数域：">
              <el-select v-model="form.shuyu" placeholder="请选择数域" clearable ref="selectShuYu" @visible-change="isShowSelectOptions">
                <el-option v-for="item in numfields" :key="item.dictCode" :label="item.fullName"
                           :value="item.dictCode"/>
              </el-select>
            </el-form-item>
            <el-form-item label="运算法则：">
              <el-select v-model="form.faze" placeholder="请选择运算法则" clearable ref="selectFaZe" @visible-change="isShowSelectOptions">
                <el-option v-for="item in algorithms" :key="item.dictCode" :label="item.fullName"
                           :value="item.dictCode" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="mini" icon="el-icon-search" @click="depement()" >搜索</el-button>
            </el-form-item>
          </el-form>
        </container-top>
        <div class="practicedTitle">
          <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-bingzhuangtu1"></use>
          </svg>
          知识点分析
        </div>
        <el-tabs type="border-card">
          <el-tab-pane label="已练习" class="practiced">
            <el-table
              :data="tableData"
              :default-expand-all="flag"
              ref="refTable">
              <el-table-column
                label="知识点名"
                prop="classificationMsg" :min-width="getMaxLength">
              </el-table-column>
              <el-table-column
                label="平均答题用时"
                prop="averageTime"
                width="170">
                <template slot-scope="props">
                  平均答题用时：<span style="color: #00afff;font-weight: 600;font-size: 16px">{{
                    props.row.averageTime
                  }}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="本班答题人数"
                prop="studentNum"
                width="170">
                <template slot-scope="props">
                  本班答题人数：<span style="color: #00afff;font-weight: 600;font-size: 16px">{{ props.row.studentNum }}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="总练习数"
                prop="count"
                width="170">
                <template slot-scope="props">
                  总练习题数：<span style="color: #00afff;font-weight: 600;font-size: 16px">{{ props.row.count }}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="正确率"
                prop="correctRate"
                width="170">
                <template slot-scope="props">
                  正确率：<span style="color: red;font-weight: 600;font-size: 16px">
                  <span>
                    {{ props.row.correctRate }}%
                  </span>
                </span>
                </template>
              </el-table-column>
            </el-table>
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
          </el-tab-pane>
          <el-tab-pane label="未练习" class="notPracticed">
            <el-table
              :data="tableData1">
              <el-table-column>
                <template slot-scope="props">
                  <span>{{ props.row }}</span>
                </template>
              </el-table-column>
            </el-table>
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
import '../../../assets/styles/iconfont'
import {getKnowledgeAnalysis, queryStudentInfoByStudent} from "@/api/usually/daily/analysis"
import {queryDictionaryByType, queryDictionaryByTypes} from "@/api/usual/receive";

export default {
  name: "index",
  components: {ContainerTop, MainFlexibleTree},
  created() {
    this.leftDepTree();
    this.initColumnMap();
    // 全局添加事件监听
    // window.addEventListener('keyup', this.depement)
    this.keyupSubmit();
  },
  computed: {
    //取表格知识点名称字数最多的宽度
    getMaxLength() {
      let itemArr = [];
      let maxLength = 0;
      if (this.tableData === []) {
        return false;
      } else {
        this.tableData.forEach(item => {
          itemArr.push(item.classificationMsg.length)
        })
        for (let i = 0; i < itemArr.length; i++) {
          if (maxLength < itemArr[i]) {
            maxLength = itemArr[i]
          }
        }
        return maxLength * 8
      }
    },
  },
  data() {
    return {
      treeData: [],
      numfields: [],
      algorithms: [],
      TreeDefaultProps: {
        children: "children",
        label: "deptName",
      },
      form: {
        shuyu: '',
        faze: '',
      },
      flag: true,
      tableData: [],
      tableData1: [],
      pageSize: 1,
      pageNum: 20,
      total: 20,
      classId: '',
      params: {
        classId: this.classId,
        shuyu: '',
        faze: '',
        pageNum: 1,
        pageSize: 20,
      }
    }
  },

  methods: {
    //键盘按下enter搜索事件
    keyupSubmit() {
      document.onkeydown = e => {
        const _key = window.event.keyCode
        if (_key === 13) {
          this.depement();
        }
      }
    },
    // 是否显示下拉框
    isShowSelectOptions(isShowSelectOptions){
      if(!isShowSelectOptions) this.$refs.selectShuYu.blur();
      if(!isShowSelectOptions) this.$refs.selectFaZe.blur();
    },
    depement() {
      this.pageNum = 1;
      this.params.faze = this.form.faze;
      this.params.shuyu = this.form.shuyu;
      this.getTable(this.params);
    },
    // 初始化转换参数
    initColumnMap() {
      // 数域
      queryDictionaryByType({dictType: 'numfield'})
        .then(response => {
          this.numfields = response.data
        })
      // 运算法则
      queryDictionaryByType({dictType: 'algorithm'})
        .then(response => {
          this.algorithms = response.data
        })
    },
    leftDepTree() {
      getDeptTree({deptType: "1"}).then(response => {
        if (response.code === 200) {
          this.treeData = response.data
          this.fore(this.treeData[0]);
          this.getTable(this.params);
        }
      })
    },
    fore(obj) {
      if (obj && obj.nodeType !== "2") {
        this.fore(obj.children[0])
      } else {
        this.classId = obj.id;
        this.params.classId = obj.id;
      }
      return;
    },
    getTable(params) {
      getKnowledgeAnalysis(params).then(response => {
        if (response.code === 200) {
          this.tableData = response.rows[0].practiced;
          // console.log(response)
          this.tableData1 = response.rows[0].notPracticed;
          this.total = response.total;
          this.pageSize = response.pageSize;
          this.pageNum = response.pageNum;
        }
      });
    },
    handleSizeChange(val) {
      this.params.pageSize = val;
      this.getTable(this.params);
    },
    handlePageNum(val) {
      this.params.pageNum = val;
      this.getTable(this.params);

    },
    handleNodeClick(val, node, component) {
      this.fore(val)
      this.params.pageNum=1;
      this.params.pageSize=20;
      this.getTable(this.params);
    }
  }
}
</script>

<style lang="scss" scoped>
.mainTop {
  height: 57px;
}

.practicedTitle {
  display: flex;
  align-items: center;
  font-weight: 600;
  height: 40px;
  font-size: 14px;
  opacity: 0.7;
  //margin: 10px 0;

  .icon {
    width: 20px;
    height: 20px;
    margin: 0 10px 0 5px;
    opacity: 1;
  }
}

.el-tabs {
  height: calc(100% - 107px);
  overflow: auto;

  /deep/ .el-tabs__content {
    padding: 0;
    height: calc(100% - 40px);
    .el-tab-pane {
      height: 100%;
      background-color: #F6F7FB;

      .el-table {
        height: calc(100% - 40px);
        width: 98%;
        margin: 0 auto;
        background-color: #F6F7FB;
        overflow: auto;

        .el-icon-arrow-right {
          color: red;
          font-size: 16px;
          font-weight: 600;
        }

        .el-table__header {
          display: none;
        }

        .el-table__body-wrapper {

          .el-table__body {
            border-collapse: separate;
            border-spacing: 0 10px;
            margin-right: 10px;

            tr {
              background-color: #fff;

              td {
                border-bottom: none;
              }

              td:first-child {
                border-top-left-radius: 20px;
                border-bottom-left-radius: 20px;

                .cell {
                  padding-left: 30px;
                }
              }

              td:last-child {
                border-top-right-radius: 20px;
                border-bottom-right-radius: 20px;
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
    }
    .notPracticed {
      .el-table{
        height: calc(100% - 10px);
      }
      .cell {
        color: rgba(0, 0, 0, 0.4);
      }
    }
  }
}
</style>

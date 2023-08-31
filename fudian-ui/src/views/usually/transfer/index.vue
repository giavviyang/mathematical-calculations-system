<template>
  <div class="mainHeight">
    <main-flexible-tree :data="treeData" :defaultProps="TreeDefaultProps" @handleNodeClick="handleNodeClick">
      <el-tabs type="border-card" slot="mainRight" v-model="rankingFlag"
               @tab-click="rankingTabsEvent">
        <el-tab-pane label="周勤奋榜" name="0">
          <div class="title">周勤奋榜</div>
          <div class="contentTable">
            <el-table
              :data="tableData"
              height="100%" :cell-class-name="cellClsNm">
              <el-table-column
                type="index"
                label="排名"
                width="50">
                <template slot-scope="scope">
                  <img src="../../../assets/image/excellent-icon.png"/>
                  <span>{{ scope.row.number }}</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="deptName"
                label="年级"
                width="180">
              </el-table-column>
              <el-table-column
                prop="studentName"
                label="姓名"
                width="180">
              </el-table-column>
              <el-table-column
                prop="totalNum"
                label="总题数">
              </el-table-column>
              <el-table-column
                prop="averageCorrecRate"
                label="平均正确率">
                <template slot-scope="scope">
                  <span v-if="String(scope.row.averageCorrecRate).indexOf('.')==-1">
                    {{ scope.row.averageCorrecRate }}%
                  </span>
                  <span v-if="String(scope.row.averageCorrecRate).indexOf('.')!=-1">
                    {{
                      String(scope.row.averageCorrecRate).slice(0, String(scope.row.averageCorrecRate).indexOf('.'))
                    }}%
                  </span>

                </template>
              </el-table-column>
              <el-table-column
                prop="averageTime"
                label="平均每题用时">
                <template slot-scope="scope">
                  <span v-if="String(scope.row.averageTime).indexOf('.')==-1">
                    {{ scope.row.averageTime }}秒
                  </span>
                  <span v-if="String(scope.row.averageTime).indexOf('.')!=-1">
                    {{ String(scope.row.averageTime).slice(0, String(scope.row.averageTime).indexOf('.') + 3) }}秒
                  </span>
                </template>
              </el-table-column>
              <el-table-column
                prop="progressRate"
                label="勤奋之星数量">
                <template slot-scope="scope">
                  {{ scope.row.diligentStar }}
                </template>
              </el-table-column>
              <el-table-column align="center" label="操作" min-width="100" max-width="200">
                <template slot-scope="scope">
                  <el-button type="primary" round @click="onlinePreview(scope.row)" :refId="scope.row" size="mini">发送
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
        </el-tab-pane>
        <el-tab-pane label="总勤奋榜" name="1">
          <div class="title">总勤奋榜</div>
          <div class="contentTable">
            <el-table
              :data="tableData"
              height="100%" :cell-class-name="cellClsNm">
              <el-table-column
                type="index"
                label="排名"
                width="50">
                <template slot-scope="scope">
                  <img src="../../../assets/image/excellent-icon.png"/>
                  <span>{{ scope.row.number }}</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="deptName"
                label="年级"
                width="180">
              </el-table-column>
              <el-table-column
                prop="studentName"
                label="姓名"
                width="180">
              </el-table-column>
              <el-table-column
                prop="totalNum"
                label="总题数">
              </el-table-column>
              <el-table-column
                prop="averageCorrecRate"
                label="平均正确率">
                <template slot-scope="scope">
                  <span v-if="String(scope.row.averageCorrecRate).indexOf('.')==-1">
                    {{ scope.row.averageCorrecRate }}%
                  </span>
                  <span v-if="String(scope.row.averageCorrecRate).indexOf('.')!=-1">
                    {{
                      String(scope.row.averageCorrecRate).slice(0, String(scope.row.averageCorrecRate).indexOf('.'))
                    }}%
                  </span>
                </template>
              </el-table-column>
              <el-table-column
                prop="averageTime"
                label="平均每题用时">
                <template slot-scope="scope">
                  <span v-if="String(scope.row.averageTime).indexOf('.')==-1">
                    {{ scope.row.averageTime }}秒
                  </span>
                  <span v-if="String(scope.row.averageTime).indexOf('.')!=-1">
                    {{ String(scope.row.averageTime).slice(0, String(scope.row.averageTime).indexOf('.') + 3) }}秒
                  </span>
                </template>
              </el-table-column>
              <el-table-column
                prop="progressRate"
                label="勤奋之星数量">
                <template slot-scope="scope">
                  {{ scope.row.diligentStar }}
                </template>
              </el-table-column>
              <el-table-column align="center" label="操作" min-width="100" max-width="200">
                <template slot-scope="scope">
                  <el-button type="primary" round @click="onlinePreview(scope.row)" :refId="scope.row" size="mini">发送
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
        </el-tab-pane>
        <el-tab-pane label="进步榜" name="2">
          <div class="title">进步榜</div>
          <div class="contentTable">
            <el-table
              :data="tableData"
              height="100%" :cell-class-name="cellClsNm">
              <el-table-column
                type="index"
                label="排名"
                width="50">
                <template slot-scope="scope">
                  <img src="../../../assets/image/excellent-icon.png"/>
                  <span>{{ scope.row.number }}</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="deptName"
                label="年级"
                width="180">
              </el-table-column>
              <el-table-column
                prop="studentName"
                label="姓名"
                width="180">
              </el-table-column>
              <el-table-column
                prop="totalNum"
                label="本周做题总数">
              </el-table-column>
              <el-table-column
                prop="averageCorrecRate"
                label="平均正确率">
                <template slot-scope="scope">
                  <span v-if="String(scope.row.averageCorrecRate).indexOf('.')==-1">
                    {{ scope.row.averageCorrecRate }}%
                  </span>
                  <span v-if="String(scope.row.averageCorrecRate).indexOf('.')!=-1">
                    {{
                      String(scope.row.averageCorrecRate).slice(0, String(scope.row.averageCorrecRate).indexOf('.'))
                    }}%
                  </span>
                </template>
              </el-table-column>
              <el-table-column
                prop="progressRate"
                label="正确率提升">
                <template slot-scope="scope">
                  <span v-if="String(scope.row.progressRate).indexOf('.')==-1">
                    {{ scope.row.progressRate }}%
                  </span>
                  <span v-if="String(scope.row.progressRate).indexOf('.')!=-1">
                    {{ String(scope.row.progressRate).slice(0, String(scope.row.progressRate).indexOf('.')) }}%
                  </span>
                </template>
              </el-table-column>
              <el-table-column
                prop="progressRate"
                label="进步之星数量">
                <template slot-scope="scope">
                  {{ scope.row.progressStar }}
                </template>
              </el-table-column>
              <el-table-column align="center" label="操作" min-width="100" max-width="200">
                <template slot-scope="scope">
                  <el-button type="primary" round @click="onlinePreview(scope.row)" :refId="scope.row" size="mini">发送
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
        </el-tab-pane>
      </el-tabs>
    </main-flexible-tree>
  </div>
</template>

<script>
import MainFlexibleTree from "@/components/public/MainBody/MainFlexibleTree";
import {getDeptTree} from "@/api/system/dept";
import {addStars, diligenceRanking, progressRanking,} from "@/api/usually/transfer"

export default {
  name: "index",
  components: {MainFlexibleTree},
  created() {
    this.leftDepTree();
  },

  data() {
    return {
      treeData: [],
      params: {
        deptId: "",
        type: "",
        pageSize: 20,
        pageNum: 1,
      },
      rankingFlag: "0",
      TreeDefaultProps: {
        children: "children",
        label: "deptName",
      },
      tableData: [],
      tableDataIndex: '',
      pageSize: 20,
      pageNum: 1,
      total: 1,
    }
  },
  methods: {
    /*设置表格列*/
    cellClsNm({row, column, rowIndex, columnIndex}) {
      if (column.label === '排名' && row.number == 1) {
        return 'tiName'
      }
      if (column.label === '排名' && row.number == 2) {
        return 'tiName'
      }
      if (column.label === '排名' && row.number == 3) {
        return 'tiName'
      }
    },
    /*查询tree树*/
    leftDepTree() {
      getDeptTree({deptType: "1"}).then(response => {
        if (response.code === 200) {
          this.treeData = response.data;
          this.fore(this.treeData[0])
          this.queryRanking();
        }
      })
    },
    /*左侧tree树点击事件*/
    handleNodeClick(val, node, component) {
      console.log(val, node, component)
      this.params.pageNum=1;
      this.params.pageSize=20;
      this.fore(val)
      this.queryRanking()
    },
    /*点击事件*/
    rankingTabsEvent() {
      this.params.pageNum=1;
      this.params.pageSize=20;
      this.queryRanking()
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
    queryRanking() {
      this.tableData = []
      if (this.rankingFlag == "0" || this.rankingFlag == "1") {
        this.params.type = this.rankingFlag
        diligenceRanking(this.params).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
          this.pageSize=response.pageSize;
          this.pageNum=response.pageNum;
        })
      } else if (this.rankingFlag == "2") {
        this.params.type = this.rankingFlag
        progressRanking(this.params).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
          this.pageSize=response.pageSize;
          this.pageNum=response.pageNum;
        })
      } else if (this.rankingFlag == "3") {

      }
    },
    onlinePreview(row) {
      addStars({studentId: row.studentId, type: this.rankingFlag}).then(response => {
        this.$message({
          message: '您已成功发送一颗星星',
          type: 'success'
        });
        this.queryRanking()
      })
    },
    handleSizeChange(val) {
      this.params.pageSize = val;
      this.pageSize=val;
      this.queryRanking()
    },
    handlePageNum(val) {
      this.params.pageNum = val;
      this.pageNum=val;
      this.queryRanking()
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

      .title {
        height: 50px;
        text-align: center;
        line-height: 50px;
        font-size: 20px;
      }

      .contentTable {
        height: calc(100% - 88px);

        .el-table {
          .el-table__header-wrapper {
            height: 40px;

            th {
              background-color: #fff;
              color: #909399;
              border-bottom: none;

              .cell {
                text-align: center;
              }
            }
          }

          .el-table__body-wrapper {
            height: calc(100% - 40px);

            tr {
              height: 60px;

              td {
                border-bottom: none;
                color: #909399;
                padding: 0;

                img {
                  height: 0;
                }

                .cell {
                  text-align: center;
                }
              }
            }

            .tiName {
              height: 60px;
              color: orange;
              border-bottom: none;



                .cell {
                  text-align: center;
                  position: relative;

                  img {
                    height: 60px !important;
                  }

                  span {
                    color: red;
                    position: absolute;
                    top: 35%;
                    left: 60%;
                    transform: translate(-50%, -50%);
                  }
                }

            }
          }

          .el-button {
            width: 80px;
            height: 40px;
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
          background-color: #fff;
        }
      }

    }
  }
}
</style>

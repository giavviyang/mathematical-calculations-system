<template>
  <div class="app-electronic-check-paramenter">
    <el-row style="height:100%">
      <el-col :span="4" :style="leftStyleName" style="height: calc(100vh - 86px)">
        <!-- 树结构 -->
        <div v-if="leftTree1" class="tree tree-style" :style="treeHeight">
          <el-scrollbar>
            <el-tree
              ref="teacherTree"
              :data="teacherTree"
              :props="defaultProps"
              :expand-on-click-node="false"
              icon-class="el-icon-folder-opened"
              default-expand-all
              highlight-current
              @node-click="treeNodeClick"
            />
          </el-scrollbar>
        </div>
        <div class="shrinkageDiv" @click="divClick()">
          <div :class="picClassname"></div>
        </div>
      </el-col>
      <el-col :span="18" style="height: calc(100vh - 86px)" :style="rightStyleName">
        <el-row ref="searchRow" :gutter="10">
          <div class="btn">
            <el-button type="primary" icon="el-icon-delete" size="mini" v-hasPermi="['parameter:electronic:delete']"
                       @click="deleteElectronic">删除
            </el-button>
          </div>
        </el-row>
        <el-table
          :default-sort="{ prop: 'date', order: 'descending' }"
          size="mini"
          ref="ElectronicData"
          :data="ElectronicData"
          border
          stripe
          class="table"
          :height="tableHeight"
          empty-text=" "
          tooltip-effect="light"
          :header-cell-style="headClass"
          align="center"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="40"/>
          <el-table-column align="center" show-overflow-tooltip label="序号" type="index" min-width="50"/>
          <el-table-column
            align="center"
            show-overflow-tooltip
            v-for="(item, index) in ElectronicColumn"
            v-if="ElectronicColumn[index].isCol"
            :key="index"
            :width="item.width"
            :formatter="item.formatter"
            :prop="ElectronicColumn[index].prop"
            :label="item.label"/>
        </el-table>
        <!-- 分页 -->
        <div class="block" style="float: right;margin-top: 5px">
          <el-pagination
            :page-sizes="[20, 50, 100]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            :current-page="pageNum"
            @size-change="pageSizes"
            @current-change="pageCurrent"
          />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {queryTeacher ,pagingQueryStudents,} from '@/api/applet/school/studentManagement'

  const params = {
    pageNum: 1,
    pageSize: 20
  }

  export default {

    data() {
      return {
        status: 0,
        /* 总页数 */
        total: 0,
        pageNum: 1,
        pageSize: 20,
        /* 刷新遮罩 */
        loading: true,
        picClassname: 'shrinkageLeft',
        // 树结构数据
        teacherTree: [
          /*{
            teacherName: '教师1',
            teacherId: 'teacherId1',
            children: [
              {
                teacherName: '教师2',
                teacherId: 'teacherId2',
                children: [],
              },
              {
                teacherName: '教师3',
                teacherId: 'teacherId3',
                children: [],
              },
            ],
          },*/
        ],
        defaultProps: {
          children: 'children',
          label: 'teacherName',
          id: 'teacherId'
        },
        checkYear: '',
        rightStyle: {
          width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px 10px 0px 10px', height: 'calc(100vh - 86px)'
        },
        rightStyle1: {
          width: '99%', height: 'calc(100vh - 86px)', padding: ' 10px 10px 0px 10px', height: 'calc(100vh - 86px)'
        },
        leftStyle: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        leftStyle1: {
          width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        leftStyleName: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        rightStyleName: {
          width: '84%', height: 'calc(100vh - 86px)', padding: '10px 10px 0px 10px', height: 'calc(100vh - 86px)'
        },
        leftTree1: true,
        treeHeight: "height:500px",
        tableHeight: '73%',
        // 条件区
        queryParams: {},
        showElectronic: true,
        // 详情
        dialogDetailsVisible: false,
        detailsData: [],
        // 申请人列名
        ElectronicColumn: [
          {label: 'id', isCol: false, prop: 'id'},
          {label: '隶属全宗', isCol: false, prop: 'orgId'},
          {label: '单位名称', isCol: false, prop: 'e01z824a'},
          {label: '人员信息id', isCol: false, prop: 'userId'},

          {label: '申请人姓名', width: 100, isCol: true, prop: 'userName'},
          {label: '申请人证件号', width: 150, isCol: true, prop: 'userCard'},
          {label: '申请查阅理由', width: 100, isCol: true, prop: 'applicationReasons'},
          {label: '申请人所在单位', width: 150, isCol: true, prop: 'e01z824a'},
          {label: '申请人电话', width: 100, isCol: true, prop: 'userPhone'},
          {label: '申请人职务', width: 100, isCol: true, prop: 'duty'},
          {label: '申请查阅档案卷数 ', width: 150, isCol: true, prop: 'relaNum'},
          {label: '申请时间', width: 150, isCol: true, prop: 'approvTime', formatter: this.getFullName},
          {label: '申请查阅截止时间至', width: 150, isCol: true, prop: 'endDate', formatter: this.getFullName},
          {label: '申请类型', width: 100, isCol: true, prop: 'type'},
          {label: '查阅状态', width: 100, isCol: true, prop: 'lookType'},
          {label: '被申请人姓名 ', width: 150, isCol: true, prop: 'a0101'},
          {label: '被申请人所在部门', width: 150, isCol: true, prop: 'deptName'},
          {label: '申请内容', width: 100, isCol: true, prop: 'e01z111a'},
          {label: '申请通过内容', width: 100, isCol: true, prop: 'adoptE01z1Msg', formatter: this.getFormatter},
          {label: '经办人', width: 100, isCol: true, prop: 'personCharge'},
          {label: '审批人', width: 100, isCol: true, prop: 'approver'},
          {
            label: '审批状态',
            width: 100,
            isCol: true,
            prop: 'status',
            formatter: function (row, column, cellValue, index) {
              if (cellValue == '0') {
                return "待审批";
              }
              if (cellValue == '1') {
                return "已通过";
              }
              if (cellValue == '-1') {
                return "未通过";
              }
            }
          },
          {label: '审批时间', width: 150, isCol: true, prop: 'approverDate', formatter: this.getFullName},
          {label: '审批意见', width: 200, isCol: true, prop: 'approverOpinion'},
          {label: '打印内容', width: 200, isCol: true, prop: 'printRemark'},
          {label: '备注', width: 200, isCol: true, prop: 'e01z831'},
        ],
        // 表格数据
        ElectronicData: [],
        // 表格总数据

        // 复选框
        checkbox: [],
        checkboxDatas: [],
        dutys: [],
        searchRow: 'searchRow',
        queryParams2: {}
      }
    },
    mounted() {
      queryTeacher().then(response => {
        this.teacherTree = [{
          children: response.data,
          teacherName: '教师',
          teacherId: 'teacherId'

        }]
        console.log(JSON.stringify(response.data))
        // this.teacherTree = response.data
      })

    },
    created() {
    },
    methods: {
      // 公用表格表头样式
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      treeNodeClick(data) {
        this.archivesaFunction();
      },
      divClick() {
        this.leftTree1 = !this.leftTree1
        if (this.leftTree1) {
          this.rightStyleName = this.rightStyle
          this.leftStyleName = this.leftStyle
          this.picClassname = 'shrinkageLeft'
          this.treeShow = !this.treeShow
        } else {
          this.rightStyleName = this.rightStyle1
          this.leftStyleName = this.leftStyle1
          this.picClassname = 'shrinkageRight'
          this.treeShow = !this.treeShow
        }
      },
      // 加载数据
      archivesaFunction() {
        pagingQueryStudents(params).then(response => {
          console.log(response);
          this.ElectronicData = response.rows;
          this.total = response.total;
        })

      },
      //数据格式转换
      getFormatter(row, column) {
        if (row.status == 1) {
          if (row.adoptE01z1Msg == null || row.adoptE01z1Msg == '') {
            return row.e01z111a
          } else {
            return row.adoptE01z1Msg;
          }
        }
        return "";
      },
      getFullName(row, col) {
        // 精确到时分秒的时间转换
        if (col.property == "endDate" || col.property == "approverDate" || col.property == "approvTime") {
          if (row[col.property] != null) {
            return this.moment(row[col.property]).format("YYYY-MM-DD HH:mm:ss")

          }
        }
      },

      deleteElectronic(value) {
      },
      /* 复选框事件 */
      handleSelectionChange(val) {
      },
      pageCurrent(val) {
        console.log(val)
        this.pageNum = val
        this.archivesaFunction(params)
      },
      pageSizes(val) {
        console.log(this.totalData)
        this.pageSize = val
        this.archivesaFunction()
      },
    }
  }
</script>

<style lang="scss" scoped>
  .app-electronic-check-paramenter {
    height: 100%;

    .search-form {
      width: 99%;
      background: rgba(204, 204, 204, 0.18);
      margin-left: 5px;
      padding-left: 10px;
      padding-bottom: 10px;
      display: flex;

      .form-width {
        width: 22%;
        display: flex;
      }

      /deep/ .el-form-item {
        margin-bottom: 0 !important;
      }
    }

    .btn {
      width: 100%;
      position: relative;
      padding-bottom: 10px;
      padding-top: 10px;
      padding-left: 5px;
      display: flex;

      .btn-search {
        position: absolute;
        right: 0px;
        top: 10px;
        padding-bottom: 20px;
      }
    }

    .page {
      float: right;
    }
  }

  .el-dialog__body {
    height: 500px;
    display: block;
  }

  .app-archives {
    overflow: auto;
    height: calc(100vh - 86px);
  }

  .el-input__inner {
    width: 250px;
  }

  .el-form-item__content {
    width: 250px;
  }

  .el-dialog-footer {
    padding-right: 15px;
    padding: 11px;
    padding-bottom: 35px;
  }

  /deep/ .el-table__fixed-right-patch {
    background-color: #F8F8F9
  }

  // 设置表格高度
  /deep/ .el-table tbody tr:hover > td {
    background-color: #ecf5ff !important
  }

  /deep/ .el-table__body td {
    padding: 0;
    height: 40px;
  }

  // 公用弹框样式
  .dialog-style {
    /deep/ .el-dialog__body {
      padding: 10px;
    }

    /deep/ .el-dialog__header {
      background: rgba(204, 204, 204, 0.18);
      padding: 10px;
    }

    /deep/ .el-dialog__title {
      font-weight: 500;
      font-size: 15px
    }

    /deep/ .el-dialog__headerbtn {
      top: 13px;
    }

    /deep/ .el-dialog__footer {
      background: rgba(204, 204, 204, 0.18);
      padding-right: 15px;
      padding: 11px;
    }

    /deep/ .el-button + .el-button {
      margin-left: 6px;
    }
  }

  .tree {
    width: 100%;
    height: calc(100vh - 135px);
    // overflow: auto;
    padding: 20px;
    /*border-right: 1px solid #e2e3e6;*/
  }

  // 公用树结构样式
  .tree-style {
    /deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
      background: rgba(204, 204, 204, 0.18);
    }
  }

  .el-scrollbar {
    height: 100%;
  }

  /deep/ .el-scrollbar .el-scrollbar__wrap {
    overflow-x: hidden;
  }

  /deep/ .el-tree > .el-tree-node {
    min-width: 100%;
    display: inline-block;
  }

  /deep/ .el-table .el-table__header-wrapper th {
    height: 31px;
  }

  .shrinkageLeft {
    border-right: 8px solid #FFFFFF;
    border-top: 8px solid transparent;
    border-bottom: 8px solid transparent;
    position: absolute;
    width: 8px;
    height: 8px;
    left: 0px;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    cursor: pointer;
  }

  .shrinkageRight {
    border-left: 8px solid #FFFFFF;
    border-top: 8px solid transparent;
    border-bottom: 8px solid transparent;
    position: absolute;
    width: 8px;
    height: 8px;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    cursor: pointer;
  }

  .shrinkageDiv {
    cursor: pointer;
    background-color: #1890ff;
    right: 0;
    bottom: 0;
    margin: auto;
    top: 0;
    height: 200px;
    width: 8px;
    border-radius: 20px;
    position: absolute;
  }
</style>

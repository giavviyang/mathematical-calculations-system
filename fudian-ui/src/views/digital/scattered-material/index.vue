<template>
  <div class="app-scattered-material">
    <el-tabs
      v-if="tabsShow"
      id="elTabs"
      v-model="activeName"
      :tab-position="tabPosition"
      style="min-height: calc(100vh - 86px);border: 1px solid #CCCCCC;"
      @tab-click="tabClick"
    >
      <el-tab-pane label="待处理" name="pending">
        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px);">
          <el-row ref="searchRow" :gutter="10" style="margin:0 0 10px 0;padding-left: 0px">
            <!-- 检索   开始 -->
            <el-form
              v-if="searchShow"
              ref="queryRef"
              :model="queryParams"
              :inline="true"
              label-width="88px"
              class="search-form"
            >
              <el-form-item label="创建人" label-width="68px" prop="createName"
                            style="margin-top: 10px;margin-bottom: 10px">
                <el-input v-model="queryParams.createName" placeholder="请输入创建人姓名" @keyup.enter.native="onEnterSearch"
                          clearable size="mini" style="width: 150px"/>
              </el-form-item>
              <el-form-item label="创建时间起" prop="startTime" style="margin-top: 10px;margin-bottom: 10px">
                <el-date-picker v-model="queryParams.beginTime" type="date" value-format="yyyy-MM-dd"
                                placeholder="请选择创建起始时间" size="mini" style="width:150px"/>
              </el-form-item>
              <el-form-item label="创建时间至" prop="endTime" style="margin-top: 10px;margin-bottom: 10px">
                <el-date-picker v-model="queryParams.endTime" type="date" value-format="yyyy-MM-dd"
                                placeholder="请选择创建截止时间" size="mini" style="width:150px"/>
              </el-form-item>
              <el-form-item style="margin-top: 10px;margin-bottom: 10px">
                <el-button type="primary" icon="el-icon-search" size="mini" @click="queryTask">搜索</el-button>
                <!-- <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button> -->
              </el-form-item>
            </el-form>
            <div class="btn">
              <el-button type="primary" icon="el-icon-finished" size="mini" @click="finishTask">完成</el-button>
              <el-button type="primary" icon="el-icon-delete" size="mini" @click="deleteTask(1)">删除</el-button>
              <el-button type="primary" icon="el-icon-download" size="mini" v-hasPermi="['digital:scattered-material:import']" @click="handleExport">导出</el-button>
              <el-button type="primary" icon="el-icon-printer" size="mini" @click="openScanner">任务处理</el-button>
              <!--
              <div class="btn-search">
                <el-button type="primary" icon="el-icon-search" size="mini" circle @click="function(){searchShow = !searchShow;tableHeight = searchShow?'73%':'80%'}" />
                <el-dropdown style="float: right; margin-left: 5px;">
                  <el-button icon="el-icon-menu" size="mini" circle />
                  <el-dropdown-menu style="padding: 10px">
                    <el-checkbox-group v-for="(item, index) in pendingColumn" :key="index" v-model="item.isCol" size="mini">
                      <el-checkbox>{{ item.label }}</el-checkbox>
                    </el-checkbox-group>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              -->
            </div>
          </el-row>
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            size="mini"
            ref="pendlRef"
            :data="pendData"
            border
            stripe
            class="table"
            row-key="id"
            tooltip-effect="light"
            empty-text=" "
            :header-cell-style="headClass"
            :height="tableHeight"
            align="center"
            @row-click="StepsListRowClick"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="50"/>
            <el-table-column align="center" show-overflow-tooltip :resizable="false" label="序号" type="index"
                             min-width="60"/>
            <el-table-column
              align="center"
              show-overflow-tooltip
              :resizable="false"
              v-for="(item, index) in pendingColumn"
              v-if="pendingColumn[index].isCol"
              :key="index"
              :width="item.width"
              :formatter="item.formatter"
              :prop="pendingColumn[index].prop"
              :label="item.label"/>
            <el-table-column align="center" fixed="right" label="操作" width="100">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="viewPendingDetals(scope.row)">查看详情</el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页 -->
          <div class="block" style="float: right;padding: 10px">
            <el-pagination
              :page-sizes="[20, 50, 100]"
              :page-size="20"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="pageSizes"
              @current-change="pageCurrent"
            />
          </div>
        </el-col>
      </el-tab-pane>
      <el-tab-pane label="已完成" name="completed">
        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px);">
          <el-row ref="searchRow2" :gutter="10" style="margin:0 0 10px 0;padding-left: 0px">
            <!-- 检索   开始 -->
            <el-form
              v-if="searchShow"
              ref="queryRef"
              :model="queryParams2"
              :inline="true"
              label-width="88px"
              class="search-form"
            >
              <el-form-item label="创建人" label-width="68px" prop="createName"
                            style="margin-top: 10px;margin-bottom: 10px">
                <el-input v-model="queryParams2.createName" placeholder="请输入创建人姓名" @keyup.enter.native="onEnterSearch"
                          clearable size="mini" style="width: 150px"/>
              </el-form-item>
              <el-form-item label="创建时间起" prop="startTime" style="margin-top: 10px;margin-bottom: 10px">
                <el-date-picker v-model="queryParams2.beginTime" type="date" value-format="yyyy-MM-dd"
                                placeholder="请选择创建起始时间" size="mini" style="width:150px"/>
              </el-form-item>
              <el-form-item label="创建时间至" prop="endTime" style="margin-top: 10px;margin-bottom: 10px">
                <el-date-picker v-model="queryParams2.endTime" type="date" value-format="yyyy-MM-dd"
                                placeholder="请选择创建截止时间" size="mini" style="width:150px"/>
              </el-form-item>
              <el-form-item style="margin-top: 10px;margin-bottom: 10px">
                <el-button type="primary" icon="el-icon-search" size="mini" @click="queryTask">搜索</el-button>
                <!-- <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button> -->
              </el-form-item>
            </el-form>
            <div class="btn">
              <el-button type="primary" icon="el-icon-delete" size="mini" @click="deleteTask(2)">删除</el-button>
              <el-button type="primary" icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
              <!--              <el-button type="primary" icon="el-icon-printer" size="mini" @click="openScanner">打开电子档案扫描工具</el-button>-->
              <!--
              <div class="btn-search">
                <el-button type="primary" icon="el-icon-search" size="mini" circle @click="function(){searchShow = !searchShow;tableHeight = searchShow?'73%':'80%'}" />
                <el-dropdown style="float: right; margin-left: 5px;">
                  <el-button icon="el-icon-menu" size="mini" circle />
                  <el-dropdown-menu style="padding: 10px">
                    <el-checkbox-group v-for="(item, index) in completedColumn" :key="index" v-model="item.isCol" size="mini">
                      <el-checkbox>{{ item.label }}</el-checkbox>
                    </el-checkbox-group>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              -->
            </div>
          </el-row>
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            size="mini"
            ref="completedRef"
            :data="compData"
            border
            stripe
            class="table"
            row-key="id"
            tooltip-effect="light"
            empty-text=" "
            :header-cell-style="headClass"
            :height="tableHeight"
            align="center"
            @row-click="StepsListRowClick"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="50"/>
            <el-table-column align="center" show-overflow-tooltip :resizable="false" label="序号" type="index"
                             min-width="60"/>
            <el-table-column
              align="center"
              show-overflow-tooltip
              :resizable="false"
              v-for="(item, index) in completedColumn"
              v-if="completedColumn[index].isCol"
              :key="index"
              :width="item.width"
              :formatter="item.formatter"
              :prop="completedColumn[index].prop"
              :label="item.label"/>
            <el-table-column align="center" fixed="right" label="操作" width="100">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="viewPendingDetals(scope.row)">查看详情</el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页 -->
          <div class="block" style="float: right;padding: 10px">
            <el-pagination
              :page-sizes="[20, 50, 100]"
              :page-size="20"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="pageSizes2"
              @current-change="pageCurrent2"
            />
          </div>
        </el-col>
      </el-tab-pane>
    </el-tabs>
    <!-- 详情对话框 -->
    <el-dialog title="零散材料任务详情" :visible.sync="dialogPendingVisible" width="85%" class="dialog-style">
      <div class="btn">
        <el-button type="primary" icon="el-icon-upload2" size="mini" @click="handleExport">导出</el-button>
      </div>
      <el-table
        :default-sort="{ prop: 'date', order: 'descending' }"
        size="mini"
        :data="pendingData"
        ref="completedRef"
        class="table"
        tooltip-effect="light"
        align="center"
        stripe
        border
        empty-text=" "
        :header-cell-style="headClass"
        height="300px"
      >
        <el-table-column align="center" label="序号" :resizable="false" type="index" min-width="60"/>
        <el-table-column align="center" label="干部名称" prop="a0101" :resizable="false" show-overflow-tooltip width="90"/>
        <el-table-column align="center" label="编号" prop="number" :resizable="false"
                         show-overflow-tooltip></el-table-column>
        <el-table-column align="center" label="出生日期" prop="a0107" :resizable="false" show-overflow-tooltip
                         width="100"></el-table-column>
        <el-table-column align="center" label="材料分类" prop="full_name" :resizable="false" show-overflow-tooltip
                         width="170"></el-table-column>
        <el-table-column align="center" label="材料名称" prop="e01z111a" :resizable="false" show-overflow-tooltip
                         width="170"></el-table-column>
        <el-table-column align="center" label="材料制成日期" prop="e01z117" :resizable="false" show-overflow-tooltip
                         width="100"></el-table-column>
        <el-table-column align="center" label="页数" prop="e01z114" :resizable="false" width="50"></el-table-column>
        <el-table-column align="center" label="备注" prop="e01z121" :resizable="false"
                         show-overflow-tooltip></el-table-column>
        <el-table-column align="center" fixed="right" label="操作" :resizable="false" width="100">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="deleteRow(scope.$index, pendingData,scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogPendingVisible = false">关 闭</el-button>
        </span>
    </el-dialog>
  </div>
</template>
<script>
  import {
    queryTaskList,
    okTask,
    deleteTaskByIds,
    queryE01z1sByTaskId,
    deleteTaskE01z1ByTaskId
  } from '@/api/digital/scattered'
  import {downLoad, downLoadPost} from '@/downLoad/filedownload'

  const params = {
    pageNum: 1,
    pageSize: 20
  }
  export default {
    name: '零散材料任务处理',
    watch: {
      pendData: {
        handler(newName, oldName) {
          this.$nextTick(() => {
            this.$refs.pendlRef.doLayout();
          })
        },
        deep: true,
      },
      compData: {
        handler(newName, oldName) {
          this.$nextTick(() => {
            this.$refs.completedRef.doLayout();
          })
        },
        deep: true,
      },
      pendingData: {
        handler(newName, oldName) {
          this.$nextTick(() => {
            this.$refs.completedRef.doLayout();
          })
        },
        deep: true,
        immediate: true
      },
    },
    activated() {
      this.$refs.pendlRef.doLayout();
      this.$refs.completedRef.doLayout();
    },
    data() {
      return {
        /* 总页数 */
        total: 0,
        pageNum: 1,
        pageSize: 20,
        total2: 0,
        pageNum2: 1,
        pageSize2: 20,
        // 页面切换
        tabPosition: 'left',
        tabsShow: true,
        activeName: 'pending',
        // 条件区
        searchShow: true,
        queryParams: {
          status: 1,
          digitizationType: 2,
          taskType: 2
        },
        queryParams2: {
          status: 2,
          digitizationType: 2,
          taskType: 2
        },
        dialogPendingVisible: false,
        tableHeight: "0px",
        compData: [],
        pendData: [],
        // 待处理
        pendingColumn: [
          {label: 'id', prop: 'id', isCol: false},
          {label: '创建人', prop: 'createName', isCol: true},
          {label: '创建时间', prop: 'createTime', isCol: true},
          {label: '材料总份数', prop: 'shareNum', isCol: true},
          {label: '材料总页数', prop: 'pageNum', isCol: true},
        ],
        completedColumn: [
          {label: 'id', prop: 'id', isCol: false},
          {label: '创建人', prop: 'createName', isCol: true},
          {label: '创建时间', prop: 'createTime', isCol: true},
          {label: '材料总份数', prop: 'shareNum', isCol: true},
          {label: '材料总页数', prop: 'pageNum', isCol: true},
        ],
        pendingData: [],
        operPendData: [],
        // 扫描 数据存放的材料人员 和目录
        materialList: '',

        // 复选框
        checkbox: [],
        checkboxDatas: [],

        searchRow: 'searchRow',
        taskId: '',
      }
    },
    mounted() {
      this.pendingFunction()
      this.completedFunction()
      this.$nextTick(() => {
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 70 + this.searchRowHeight)) + "px"
      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (84 + 70 + this.searchRowHeight)) + "px"
        })();
      };
    },
    //字典表获取
    created() {
      this.initColumnMap()
    },
    methods: {
      dataFormatter() {

      },
      onEnterSearch() {
        this.queryTask();
      },
      // 公用表格表头样式
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      // 点击行事件
      StepsListRowClick(row) {
        console.log(row)
        this.$refs.pendlRef.clearSelection()
        if (this.checkbox.length === 0) {
          this.$refs.pendlRef.toggleRowSelection(row)
          return;
        }
        this.$refs.pendlRef.toggleRowSelection(row)
        this.materialList = row
      },
      /* 复选框事件 */
      handleSelectionChange(val) {
        this.checkboxDatas = val
        this.checkbox = val.map(item => item.id)
      },
      // 初始化字典表
      initColumnMap() {

      },
      tabClick(tab, event) {
        if (tab.label === '待处理') {
          this.pageNum = 1;
          this.searchRow = 'searchRow'
          this.pendingFunction();

        } else if (tab.label === '已完成') {
          this.pageNum2 = 1;
          this.searchRow = 'searchRow2'
          this.completedFunction();
        }
        //this.archivesaFunction()
      },
      // 加载
      pendingFunction() {
        let params = JSON.parse(JSON.stringify(this.queryParams));
        params.pageNum = this.pageNum;
        params.pageSize = this.pageSize;
        queryTaskList(params).then(response => {
          this.total = response.total;
          this.pendData = response.rows;
        })
      },
      // 加载
      completedFunction() {
        let params = JSON.parse(JSON.stringify(this.queryParams2));
        params.pageNum = this.pageNum2;
        params.pageSize = this.pageSize2;
        queryTaskList(params).then(response => {
          this.total2 = response.total;
          this.compData = response.rows;
        })
      },
      // 搜索
      queryTask() {
        if (this.searchRow == 'searchRow') {
          this.pageNum = 1
          this.pendingFunction();
        } else {
          this.pageNum2 = 1;
          this.completedFunction();
        }
      },
      // 重置
      reset() {
        console.log(this.searchRow);
        if (this.searchRow == 'searchRow') {
          this.queryParams.createName = ""
          this.queryParams.beginTime = ""
          this.queryParams.endTime = ""
          this.pageNum = 1
          this.pendingFunction();
        } else {
          this.queryParams2.createName = ""
          this.queryParams2.beginTime = ""
          this.queryParams2.endTime = ""
          this.pageNum2 = 1;
          this.completedFunction();
        }

        // this.operPendData = this.pendData
      },
      // 完成
      finishTask() {
        if (this.checkboxDatas == null || this.checkboxDatas.length == 0) {
          this.$message({
            type: 'warning',
            message: '请勾选要完成的任务'
          })
          return
        }

        let id = [];
        this.checkboxDatas.map(item => {
          id.push(item.id);
        })
        okTask({id: id.join(",")}).then(response => {
          this.pendingFunction();
          this.completedFunction();
          // if(this.searchRow == 'searchRow'){
          //
          // }else{
          //
          // }
        })

      },
      // 删除
      deleteTask(val) {
        if (this.checkboxDatas == null || this.checkboxDatas.length == 0) {
          this.$message({
            type: 'warning',
            message: '请勾选要删除的任务'
          })
          return
        }
        this.$confirm('此操作将永久删除所选信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            let id = [];
            this.checkboxDatas.map(item => {
              id.push(item.id);
            })
            deleteTaskByIds({id: id.join(",")}).then(response => {
              this.$message.success("操作成功")
              if (this.searchRow == 'searchRow') {
                this.pendingFunction();
              } else {
                this.completedFunction();
              }
            })
            //删除
          })
          .catch(() => {
          })
      },
      // 待处理查看详情
      viewPendingDetals(val) {
        // if(this.searchRow == 'searchRow2'){
        console.log(val)
        this.dialogPendingVisible = true;
        this.taskId = val.id;
        queryE01z1sByTaskId({taksId: this.taskId}).then(response => {
          if (response.code == 200) {
            this.pendingData = response.data;
          }
        })
        // params.e01z1Id = val.id
        return;
        // }

        // this.$router.push({
        //   name:'任务处理',
        //   params:{
        //     taskId:val.id
        //   }
        // })
        /**
         queryTransferBillList(params)
         .then(response => {
          this.pendingData = response.rows
          console.log(this.pendingData)
          this.total = response.total
          this.loading = false
        })
         .catch(err => {
          this.$message({
            showClose: true,
            message: '查询失败，请联系管理员',
            type: 'error'
          })
        })
         */
      },
      // 详情删除
      deleteRow(index, rows, row) {
        this.$confirm('此操作将永久删除所选信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            let params = {
              taskId: this.taskId,
              e01z1Id: row.id
            }
            deleteTaskE01z1ByTaskId(params).then(response => {
              if (response.code == 200) {
                this.$message.success("操作成功");
                if(this.pendingData.length == 1){
                  //关闭窗口，刷新主table;
                  this.dialogPendingVisible = false;
                  this.pendingFunction();
                  return;
                }
                queryE01z1sByTaskId({taksId: this.taskId}).then(response => {
                  if (response.code == 200) {
                    this.pendingData = response.data;
                  }
                })
              }
            })
          })
          .catch(() => {
          })
      },
      // 导出
      handleExport() {
        if(this.checkboxDatas.length ==0 ){
          this.$confirm('是否导出搜索后的任务数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            if (this.searchRow == 'searchRow') {
              let params = JSON.parse(JSON.stringify(this.queryParams));
              params.pageNum = 1;
              params.pageSize = 99999999;
              queryTaskList(params).then(response => {
                console.log(response.rows);
                if(response.rows.length==0){
                  this.$message.error("无数据,导出失败");
                  return;
                }
                let row = {
                  row:response.rows,
                  status:3
                }
                downLoadPost("/export/excel/exportClick",row )
              })
            }else {
              let params = JSON.parse(JSON.stringify(this.queryParams2));
              params.pageNum = 1;
              params.pageSize = 9999999;
              queryTaskList(params).then(response => {
                console.log(response.rows);
                if(response.rows.length==0){
                  this.$message.error("无数据,导出失败");
                  return;
                }
                let row = {
                  row:response.rows,
                  status:3
                }
                downLoadPost("/export/excel/exportClick",row )
              })
            }
          })

        }else{
          this.$confirm('是否导出选中的任务数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let row = {
              row:this.checkboxDatas,
              status:3
            }
            downLoadPost("/export/excel/exportClick",row )
          })

        }


      },
      // 打印
      handlePrint() {

      },
      // 打开扫描工具
      openScanner() {
        // this.$router.push({
        //   name: "任务处理",
        //   query: {
        //     data:this.materialList
        //   }
        // });
        if (this.checkboxDatas.length == 0 || this.checkboxDatas.length > 1) {
          this.$message({
            type: 'warning',
            message: '请选择一条数据进行此操作'
          })
          return;
        }
        this.$router.push({
          name: "任务处理",
          params: {
            taskId: this.checkboxDatas[0].id
          }
        });
      },
      pageCurrent(val) {
        this.pageNum = val;
        this.pendingFunction();
      },
      pageSizes(val) {
        this.pageSize = val;
        this.pendingFunction();
      },

      pageCurrent2(val) {
        this.pageNum2 = val;
        this.completedFunction();
      },
      pageSizes2(val) {
        this.pageSize2 = val;
        this.completedFunction();
      },
      /**
       pageCurrent(val) {
      this.pageNum = val
      const params = {
        pageNum: val,
        pageSize: this.pageSize
      }
      this.receiveQuery(params)
    },
       pageSizes(val) {
      this.pageSize = val
      const params = {
        pageNum: this.pageNum,
        pageSize: val
      }
      this.receiveQuery(params)
    },
       */
    }
  }
</script>
<style lang="scss" scoped>
  .app-scattered-material {
    height: 100%;
    .tree {
      width: 100%;
      height: 500px;
      padding: 20px;
      border-right: 1px solid #e2e3e6;
    }
    .search-form {
      width: 100%;
      background: rgba(204, 204, 204, 0.18);
      padding-left: 10px;
      padding-top: 10px;
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

  .el-dialog__body {
    height: 500px;
    display: block;
  }

  .el-dialog-footer {
    padding-right: 15px;
    padding: 11px;
    padding-bottom: 35px;
  }

  /deep/ .el-table__fixed-right-patch {
    background-color: #F8F8F9
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

  // 公用树结构样式
  .tree-style {
    /deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
      background: rgba(204, 204, 204, 0.18);
    }
  }

  /deep/ .el-table .el-table__header-wrapper th {
    height: 31px;
  }

</style>

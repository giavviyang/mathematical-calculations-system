<template>
  <div class="app-find">
    <el-row style="height: 100%">
      <el-col :span="24" style="padding: 10px;height: 100%">
        <el-row :gutter="10" style="margin:0 0 10px 0;padding-left: 0px" ref="searchRow">
          <el-form
            v-hasPermi="['log:loginlog:search']"
            v-if="showSearchBox"
            ref="queryForm2"
            class="search-form"
            :model="queryForm2"
            :inline="true"
            label-width="68px"
            style="background:rgba(204, 204, 204, 0.18);padding: 10px 0;margin-right: 20px;margin-bottom: 10px;"
          >
            <el-form-item label="访问时间" style="margin-left: 20px" prop="loginTime">
              <!--              <el-input v-model="queryForm.loginTime" placeholder="请输入访问时间" clearable size="mini" style="width: 150px" />-->
              <el-date-picker
                size="mini"
                v-model="queryForm2.loginTime"
                type="date"
                placeholder="请选择时间"
                clearable
                value-format="yyyy-MM-dd"
                style="width: 150px"/>
            </el-form-item>
            <!--<el-form-item label="结束时间" style="margin-left: 20px" prop="endTime">
              &lt;!&ndash;              <el-input v-model="queryForm.loginTime" placeholder="请输入访问时间" clearable size="mini" style="width: 150px" />&ndash;&gt;
              <el-date-picker size="mini" v-model="queryForm2.endTime" type="date" placeholder="请选择时间"
                              style="width: 150px"
                              value-format="yyyy-MM-dd" clearable
              />
            </el-form-item>-->
            <el-form-item label="用户账号" style="margin-left: 20px" prop="userName">
              <el-input
                @keyup.enter.native="onEnterSearch"
                v-model="queryForm2.userName"
                placeholder="请输入用户账号"
                clearable
                size="mini"
                style="width: 150px"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
              <!-- <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button> -->
            </el-form-item>
          </el-form>
          <el-col :span="1.5" style="padding: 0px">
            <el-button type="primary" icon="el-icon-download" v-hasPermi="['log:loginlog:export']" size="mini" @click="handleExportOperLog()">导出</el-button>
          </el-col>

          <!--          <el-col :span="1.5"><el-button type="primary" icon="el-icon-search" size="mini" @click="operator()">用户姓名</el-button></el-col>-->

          <!--          搜索区域的展示与隐藏-->
          <!--          <el-dropdown style="float: right; margin-right: 30px;">-->
          <!--            <el-button icon="el-icon-menu" size="mini" circle />-->
          <!--            <el-dropdown-menu style="padding: 10px">-->
          <!--              <el-checkbox-group v-for="(item, index) in col" v-model="col[index].isCol" size="mini">-->
          <!--                <el-checkbox>{{ item.label }}</el-checkbox>-->
          <!--              </el-checkbox-group>-->
          <!--            </el-dropdown-menu>-->
          <!--          </el-dropdown>-->
          <!--          <el-col :span="1.5" style="float: right;">-->
          <!--            <el-button type="primary" icon="el-icon-search" size="mini" circle @click="function(){showSearchBox = !showSearchBox;tableHeight = showSearchBox?'73%':'85%'}" />-->
          <!--          </el-col>-->
        </el-row>

        <el-table
          ref="table"
          v-loading="loading"
          :height="tableHeight"
          size="mini"
          :data="tableData"
          border row-key="id"
          align="left"
          stripe
          tooltip-effect="light"
          :header-cell-style="headClass"
          highlight-current-row
          @current-change="handleCurrentChange"
          @selection-change="handleSelectionChange">
          <!--          <el-table-column type="selection" width="40" />-->
          <el-table-column
            label="序号"
            type="index"
            :index="customIndex"
            align="center"
            show-overflow-tooltip
            width="50">
          </el-table-column>
          <el-table-column align="center" v-for="(item, index) in col" v-if="col[index].isCol" :key="index"
                           :formatter="item.format" :prop="col[index].prop" :label="item.label" show-overflow-tooltip/>
          <!--          <el-table-column  align="center" prop="znum" :formatter="numFormat" label="总容量" />-->
          <!--          <el-table-column  align="center" prop="cz"  label="操作" width="200px">-->
          <!--            <template slot-scope="scope">-->
          <!--              <el-button type="text" size="mini" @click="structure(scope.row)">库房结构管理</el-button>-->
          <!--              <el-button type="text" size="mini" @click="archives(scope.row)">实体档案管理</el-button>-->
          <!--            </template>-->
          <!--          </el-table-column>-->
        </el-table>
        <div class="block">
          <el-pagination
            :page-sizes="[20, 50, 100, 200]"
            :page-size="queryForm.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            :current-page="queryForm.pageNum"
            @size-change="pageSize"
            @current-change="pageCurrent"
          />
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
  import {queryAllLoginLog,queryAllLoginLogExcel} from "@/api/log/loginlog";
  import {downLoadPost} from "@/downLoad/filedownload";

  const params = {
    pageNum: 1,
    pageSize: 20
  }
  export default {
    // name: "登录日志",
    data() {

      return {
        tableHeight: '73%',
        loading: true,
        //grid数据
        tableData: [],
        //头部搜索框展示与隐藏
        showSearchBox: true,
        //搜索框条件
        queryForm2:{
          userName: '',//操作人员
          //loginTime:'',//访问时间
          //endTime:''
        },
        //查询条件
        queryForm: {
          pageNum: 1,
          pageSize: 20,

        },
        total: 0,

        col: [
          // { label: '访问ID', prop: 'infoId', isCol: true },
          {
            label: '用户账号', prop: 'userName', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '登录IP地址', prop: 'ipaddr', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          /*{
            label: '登录地点', prop: 'loginLocation', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },*/
          {
            label: '浏览器类型', prop: 'browser', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '操作系统', prop: 'os', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '登录状态', prop: 'status', format: function (row, column, cellValue, index) {
              if (cellValue == 0) {
                return "成功"
              } else if (cellValue == 1) {
                return "失败"
              } else {
                return cellValue
              }
            }, isCol: true
          },
          {
            label: '提示消息', prop: 'msg', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '访问时间', prop: 'loginTime', isCol: true
          },
        ],
        currentRow: [],

        searchRowHeight: '',
        searchRow: 'searchRow',
        select: []
      }
    },
    watch: {
      tableData: {
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(() => {
            _this.$refs.table.doLayout()
          })
        },
        deep: true,
        immediate: true
      },
    },
    mounted() {
      this.$nextTick(() => {
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 65 + this.searchRowHeight)) + "px"
      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (84 + 65 + this.searchRowHeight)) + "px"
        })();
      };
      this.keyupSubmit();
    },
    created() {
      this.getRoomList(params);
      // window.addEventListener('keyup', this.search)
    },
    methods: {
      //键盘按下enter搜索事件
      keyupSubmit() {
        document.onkeydown = e => {
          const _key = window.event.keyCode
          if (_key === 13) {
            this.search();
          }
        }
      },
      //自定义序号
      customIndex(index){
        return (this.queryForm.pageNum-1)*(this.queryForm.pageSize)+(index+1);
      },
      handleExportOperLog() {
        const _this = this;
        if (this.tableData.length <= 0) {
          this.$message({
            type: 'warning',
            message: '无任何数据需要导出'
          });
          return;
        }

        this.$confirm('是否导出搜索后的日志数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.$store._modules.root.state.mask.lodding = true;
          let param = {};
          for (let key in params) {
            param[key] = params[key]
          }
          param.pageSize = 99999999;
          let callback = function (response) {
            response.rows.map((item, index) => {
              let indexs = (index + 1).toString();
              if (index.toString().length < 3) {
                for (let i = 0; i < 3 - index.toString().length; i++) {
                  indexs = '0' + indexs;
                }
              }
              item.code = indexs
              item.status = _this.statusHadnler(item.status);
              item.loginTime = _this.dateHandler(item.loginTime);
            })
            if(response.rows.length==0){
              _this.$message.error("无数据,导出失败");
              return;
            }
            let data = {
              colTitle: {
                userName:"用户账号",
                ipaddr:"登录IP地址",
                browser:"浏览器类型",
                os:"操作系统",
                status:"登录状态",
                msg:"提示消息",
                loginTime:"访问时间",
              },
              dataList: response.rows,
              tableTitle: "登录日志表",
              sheetTitle: "登录日志表",
              downLoadName: "登录日志表"
            }
            // console.log(JSON.stringify(data))
            downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data),_this)
          }
          queryAllLoginLogExcel(param,callback)
        })

        // if(this.select.length==0)

      },//导出
      statusHadnler(cellValue){
        if (cellValue == 0) {
          return "成功"
        } else if (cellValue == 1) {
          return "失败"
        } else {
          return cellValue
        }
      },
      dateHandler(cellValue){
        if (cellValue == null || "" == cellValue) {
          return cellValue;
        }
        var d = new Date(cellValue);
        var month = '' + (d.getMonth() + 1)
        var day = '' + d.getDate()
        var year = d.getFullYear()
        if (month.length < 2) month = '0' + month
        if (day.length < 2) day = '0' + day
        var hour = d.getHours();
        var minus = d.getMinutes()
        var seconds = d.getSeconds()
        return [year, month, day].join('-') + " " + hour + ":" + minus + ":" + seconds
      },
      handlePrintOperLog() {
      },//打印
      operator() {
        if (this.currentRow == null || this.currentRow.length == 0) {
          this.$message({
            type: 'warning',
            message: '请选中需要查看登录人员的信息'
          })
          return
        }
        const val = this.currentRow
        let param = {}
        param.userName = val.userName;
        param.pageNum = this.queryForm.pageNum
        param.pageSize = this.queryForm.pageSize
        this.getRoomList(param)
        this.$refs.table.setCurrentRow();
      },
      onEnterSearch() {
        this.search();
      },
      //条件搜索
      search(val) {
        this.queryForm.pageNum = 1;
        this.queryForm.userName = this.queryForm2.userName,
        this.queryForm.loginTime = this.queryForm2.loginTime,
        this.queryForm.endTime = this.queryForm2.endTime,
        this.getRoomList()
      },
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      /* grid  行点击事件*/
      handleCurrentChange(val) {
        this.currentRow = val
      },
      /* 复选框事件 */
      handleSelectionChange(val) {
        this.select = val;
      },

      //初始化列表数据
      getRoomList() {
        this.loading = true;
        queryAllLoginLog(this.queryForm).then(response => {
          this.tableData = response.rows;
          response.rows.map((item, index) => {
            let indexs = (index + 1).toString();
            if (index.toString().length < 3) {
              for (let i = 0; i < 3 - index.toString().length; i++) {
                indexs = '0' + indexs;
              }
            }
            item.code = indexs
          })
          this.total = response.total;
          this.loading = false;
        })
      },

      pageCurrent(val) {
        this.queryForm.pageNum = val
        const params = {
          pageNum: val,
          pageSize: this.queryForm.pageSize
        }
        this.getRoomList(params)
      },
      pageSize(val) {
        const params = {
          pageNum: this.queryForm.pageNum,
          pageSize: val
        }
        this.getRoomList(params)
      },
      reset() {
        this.queryForm.userName = "";
        this.queryForm.endTime = "";
        this.queryForm.loginTime = "";
        this.queryForm.pageNum = 1;
        this.queryForm.pageSize = 20;
        this.getRoomList(params);
      },
    }
  }
</script>

<style lang="scss" scoped>
  .app-find {
    overflow: auto;
    height: calc(100vh - 86px);
  }

  .search-form {
    width: 100%;
    background: rgba(204, 204, 204, 0.18);
    padding: 10px;
    display: flex;
    .form-width {
      width: 22%;
      display: flex;
    }
    /deep/ .el-form-item {
      margin-bottom: 0 !important;
    }
    /deep/ .el-table__fixed-right-patch {
      background-color: #F8F8F9;
    }
  }
  .block {
    float: right;
    margin-top: 5px;
  }
</style>

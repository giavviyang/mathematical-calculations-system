<template>
  <div class="app-find">
    <el-row style="height: 100%">
      <el-col :span="24" style="padding: 10px;height: 100%">
        <el-row :gutter="10" style="margin:0 0 10px 0;padding-left: 0px" ref="searchRow">
          <el-form
            v-hasPermi="['log:browselog:search']"
            v-if="showSearchBox"
            ref="queryForm2"
            class="search-form"
            :model="queryForm2"
            :inline="true"
            label-width="68px"
            style="background:rgba(204, 204, 204, 0.18);padding: 10px;margin-right: 20px;margin-bottom: 10px;"
          >
            <el-form-item label="浏览人员" style="margin-left: 20px" prop="operName">
              <el-input @keyup.enter.native="onEnterSearch" v-model="queryForm2.userName" placeholder="请输入浏览人员名称" clearable size="mini"
                        style="width: 150px"/>
            </el-form-item>
            <el-form-item label="档案名称" style="margin-left: 20px" prop="title">
              <el-input @keyup.enter.native="onEnterSearch" v-model="queryForm2.e01z111a" placeholder="请输入档案名称" clearable size="mini" style="width: 150px"/>
            </el-form-item>
            <el-form-item style="margin-left: 20px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
              <!-- <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button> -->
            </el-form-item>
          </el-form>
          <el-col :span="1.5" style="padding: 0px">
            <el-button type="primary" icon="el-icon-download"
                       v-hasPermi="['log:browselog:export']" size="mini" @click="handleExportOperLog()">导出</el-button>
          </el-col>

          <!--          <el-col :span="1.5"><el-button type="primary" icon="el-icon-search" size="mini" @click="operator()">浏览人员</el-button></el-col>-->

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
          @current-change="handleCurrentChange"
          @selection-change="handleSelectionChange">
          <!--          <el-table-column type="selection" width="40"/>-->
          <el-table-column
            label="序号"
            type="index"
            :index="customIndex"
            show-overflow-tooltip
            align="center"
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
  import {queryAllBrowseLog,queryAllBrowseLogExcel} from "@/api/log/browselog";
  import {downLoadPost} from "@/downLoad/filedownload";

  export default {
    // name: "操作日志",
    data() {
      return {

        tableHeight: '73%',
        loading: true,
        //grid数据
        tableData: [],
        //头部搜索框展示与隐藏
        showSearchBox: true,
        //搜索框条件
        queryForm2: {
          userName: '',//浏览人员
          e01z111a: ''//材料名称
        },
        //搜索框条件
        queryForm: {
          pageNum: 1,
          pageSize: 20
        },
        total: 0,

        col: [
          // { label: 'uuid', prop: 'id',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
          {
            label: '用户ID', prop: 'userId', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: false
          },
          {
            label: '用户名称', prop: 'userName', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '干部id', prop: 'a01Id', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: false
          },
          {
            label: '干部姓名', prop: 'a0101', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '部门id', prop: 'deptId', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: false
          },
          {
            label: '部门名称', prop: 'deptName', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '材料id', prop: 'e01z1Id', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: false
          },
          {
            label: '材料名称', prop: 'e01z111a', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '操作类别', prop: 'operatorType', format: function (row, column, cellValue, index) {
              if (cellValue == 0) {
                return "其它"
              } else if (cellValue == 1) {
                return "后台用户"
              } else if (cellValue == 2) {
                return "手机端用户"
              } else {
                return cellValue
              }
            }, isCol: true
          },
          {
            label: '主机地址', prop: 'operIp', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          // { label: '操作地点', prop: 'operLocation',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
          {
            label: '操作时间', prop: 'operTime', isCol: true
          },
          // { label: '描述信息', prop: 'detail',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
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
    },
    created() {
      this.getRoomList();
    },
    methods: {

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
          // 取列头
          let col = this.col;
          // let colTitle = {
          //   userName:"用户名称",
          //   a0101:"干部姓名",
          //   deptName:"部门名称",
          //   e01z111a:"材料名称",
          //   operatorType:"操作类别",
          //   operIp:"主机地址",
          //   operTime:"操作时间",
          // }
          // if (this.select.length == 0) {
          let params = {};
          for (let key in this.queryForm) {
            params[key] = this.queryForm[key]
          }
          params.pageSize = 99999999;
          let callback = function (response) {
            response.rows.map((item, index) => {
              let indexs = (index + 1).toString();
              if (index.toString().length < 3) {
                for (let i = 0; i < 3 - index.toString().length; i++) {
                  indexs = '0' + indexs;
                }
              }
              item.code = indexs
              // 处理类型
              item.operatorType = _this.statusHandler(item.operatorType);
              item.operTime = _this.dateHandler(item.operTime);
              // console.log(item)
              // console.log(index)
            })
            if(response.rows.length==0){
              _this.$message.error("无数据,导出失败");
              return;
            }
            let data = {
              colTitle: {
                userName:"用户名称",
                a0101:"干部姓名",
                deptName:"部门名称",
                e01z111a:"材料名称",
                operatorType:"操作类别",
                operIp:"主机地址",
                operTime:"操作时间",
              },
              dataList: response.rows,
              tableTitle: "浏览日志表",
              sheetTitle: "浏览日志表",
              downLoadName: "浏览日志表"
            }
            downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data),_this)
          }
          queryAllBrowseLogExcel(params,callback)
          // } else {
          //   let data = {
          //     "colTitle": colTitle,
          //     "dataList": this.select,
          //     "tableTitle": "浏览日志表",
          //     "sheetTitle": "浏览日志表",
          //     "downLoadName": "浏览日志表"
          //   }
          //   downLoadPost("/export/excel/exportExcelDynamicCol", data)
          // }

        })


      },//导出
      statusHandler(operatorType) {
        if (operatorType == 0) {
          return "其它"
        } else if (operatorType == 1) {
          return "后台用户"
        } else if (operatorType == 2) {
          return "手机端用户"
        } else {
          return operatorType;
        }
      },
      dateHandler(cellValue) {
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
        queryAllBrowseLog(this.queryForm).then(response => {
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
      onEnterSearch() {
        this.search();
      },

      //条件搜索
      search() {
        this.queryForm.pageNum = 1;
        this.queryForm.userName = this.queryForm2.userName;
        this.queryForm.e01z111a = this.queryForm2.e01z111a;
        this.getRoomList();
      },

      operator() {
        if (this.currentRow == null || this.currentRow.length == 0) {
          this.$message({
            type: 'warning',
            message: '请选中需要查看浏览人员的信息'
          })
          return
        }
        const val = this.currentRow
        this.queryForm.userName = val.userName
        queryAllBrowseLog(this.queryForm).then(response => {
          this.tableData = response.rows;
          const _this = this;
          response.rows.map((item, index) => {
            let indexs = (index + 1).toString();
            if (index.toString().length < 3) {
              for (let i = 0; i < 3 - index.toString().length; i++) {
                indexs = '0' + indexs;
              }
            }
            item.code = indexs
          })
          this.queryForm.total = response.total;
          this.loading = false;
        })
        this.$refs.table.setCurrentRow();
      },

      pageCurrent(val) {
        this.queryForm.pageSize = this.queryForm.pageSize;
        this.queryForm.pageNum = val;
        this.getRoomList();
      },
      pageSize(val) {
        this.queryForm.pageSize = val;
        this.queryForm.pageNum = this.queryForm.pageNum;
        this.getRoomList();
      },
      reset() {
        this.queryForm.userName = "";
        this.queryForm.e01z111a = "";
        this.queryForm.pageNum = 1
        this.getRoomList();
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
    width: 99%;
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

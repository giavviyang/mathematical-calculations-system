<template>
  <div>
    <el-col :span="24" :xs="24" style="height: calc(100vh - 86px);">
      <el-row :gutter="10" ref="searchRow1" style="margin:0;">
        <el-form
          ref="searchRef"
          class="search-form"
          :model="searchParams"
          :inline="true"
          style="background:rgba(204, 204, 204, 0.18);"
          v-hasPermi="['recycle:back:search']"
        >
          <el-form-item label="干部姓名" class="form-width" style="margin-left: 20px" prop="a0101">
            <el-input
              @keyup.enter.native="onEnterSearch"
              v-model="searchParams.a0101"
              placeholder="请输入干部姓名"
              clearable
              size="mini"
              style="width: 150px"
            />
          </el-form-item>
          <el-form-item label="材料名称" class="form-width" style="margin-left: 20px" prop="e01z111a">
            <el-input
              @keyup.enter.native="onEnterSearch"
              v-model="searchParams.e01z111a"
              placeholder="请输入材料名称"
              clearable
              size="mini"
              style="width: 150px"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini"
                       @click="handleQuery">搜索
            </el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-col :span="24" style="padding: 10px 0px;">
          <el-button type="primary" icon="el-icon-delete" size="mini" v-hasPermi="['recycle:back:delete']" @click="deleteRec">删除</el-button>
          <el-button type="primary" icon="el-icon-refresh-right" size="mini" v-hasPermi="['recycle:back:rollback']" @click="returnPla">还原</el-button>
          <el-button type="primary" icon="el-icon-delete" size="mini" v-hasPermi="['recycle:back:clear']" @click="deleteAll">清空</el-button>
          <el-button type="primary" icon="el-icon-upload2" size="mini" v-hasPermi="['recycle:back:export']" @click="exportExcel">导出</el-button>
      </el-col>
      <el-table
        ref="table"
        v-loading="loading"
        :height="tableHeight"
        size="mini"
        :data="tableData"
        border
        align="left"
        stripe
        empty-text=" "
        tooltip-effect="light"
        :header-cell-style="headClass"
        @current-change="handleCurrentChange"
        @row-click="handleRowClick"
        @selection-change="handleSelectionChange">
        <el-table-column align="center" :resizable="false" :show-overflow-tooltip="true" type="selection"
                         width="50"/>
        <el-table-column
          label="序号"
          type="index"
          width="50"
          align="center"
          show-overflow-tooltip
          :index="customIndex"
        >
        </el-table-column>
        <el-table-column align="center" v-for="(item, index) in col" v-if="col[index].isCol" :key="index"
                         :width="col[index].width"
                         :formatter="item.format" :prop="col[index].prop" :label="item.label"
                         show-overflow-tooltip/>
      </el-table>
      <div class="block" style="float: right;margin-top: 5px">
        <el-pagination
          :page-sizes="[20, 50, 100, 200]"
          :page-size="pageSize1"
          :current-page="pageNum"
          layout="total, sizes, prev, pager, next, jumper"
          :total="searchParams.total"
          @size-change="pageSize"
          @current-change="pageCurrent"
        />
      </div>
    </el-col>
  </div>
</template>
<script>
  import {queryE01z1ListByName, queryE01z1ListByFlag, deleteById, returnPlace} from "@/api/recycle/file";
  import {downLoad, downLoadPost} from '@/downLoad/filedownload'

  const params = {
    pageNum: 1,
    pageSize: 20
  }
  export default {
    name: "index",
    data() {

      return {
        searchRow1:"searchRow1",
        select: [],
        excel: [],
        searchRowHeight: '',
        searchRow: 'searchRow',
        currentPage: 1,
        //上方搜索区域
        searchParams: {
          pageSize: 20,
          pageNum: 1,
        },
        tableHeight: '73%',
        loading: true,
        //grid数据
        tableData: [],
        col: [
          {
            label: 'ID', prop: 'id', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: false
          },
          {
            label: '部门名称', prop: 'deptName', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '干部姓名', prop: 'a0101', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '材料类号', prop: 'e01z101', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '材料序号', prop: 'e01z104', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          // {
          //   label: '材料编号', prop: 'e01z107', format: function (row, column, cellValue, index) {
          //     return cellValue
          //   }, isCol: true
          // },
          {
            label: '材料名称', prop: 'e01z111a', width: 150, format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '页数', prop: 'e01z114', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '材料制成日期', prop: 'e01z117', width: 150, format: this.e01z117Format, isCol: true
          },
          {
            label: '备注', prop: 'e01z121', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          /*{
            label: '份数', prop: 'e01z124', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '档案卷本号', prop: 'e01z127',width:150, format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },*/
          {
            label: '删除时间', prop: 'updateTime', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
          {
            label: '操作人员', prop: 'updateBy', format: function (row, column, cellValue, index) {
              return cellValue
            }, isCol: true
          },
        ],
        total: 0,
        pageNum: 1,
        pageSize1: 20
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
        this.searchRowHeight = this.$refs[this.searchRow1].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + "px"
      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow1].$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + "px"
        })();
      };
    },
    created() {
      this.getGridList(params);
    },
    methods: {
      //自定义序号
      customIndex(index) {
        return (this.pageNum - 1) * (this.pageSize1) + (index + 1);
      },
      e01z117Format(row) {
        if (!row['e01z117'] || row['e01z117'] === '') {
          return ""
        }
        return this.moment(row['e01z117']).format("YYYY-MM-DD HH:mm:ss");
      },
      updateTimeFormat(row) {
        if (!row['updateTime'] || row['updateTime'] === '') {
          return ""
        }
        return this.moment(row['updateTime']).format("YYYY-MM-DD HH:mm:ss");
      },
      // 职务格式化
      getFullName(row, col) {
        //职务
        if (col.property == "duty") {
          let returnValues = '';
          this.dutys.forEach(data => {
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName;
            }
          })
          return returnValues;
        }
      },
      //获取grid列表
      getGridList(params) {
        this.loading = true;
        this.searchParams.efileFlag = "1";
        queryE01z1ListByName(params)
          .then(response => {
            this.tableData = response.rows;
            console.log(this.tableData)
            this.searchParams.total = response.total;
            this.loading = false;
          })
      },
      onEnterSearch() {
        this.handleQuery()
      },
      //搜索按钮
      handleQuery() {
        params.pageNum = 1;
        params.pageSize = this.searchParams.pageSize;
        // this.searchParams.pageSize = this.pageSize1;
        params.efileFlag = "1";
        params.a0101 = this.searchParams.a0101;
        params.e01z111a = this.searchParams.e01z111a;
        // this.pageCurrent(1);
        // this.currentPage=1;
        this.pageNum = 1;
        this.getGridList(params);
      },
      exportExcel() {
        if (this.excel.length == 0) {
          this.$confirm('是否导出搜索后的材料数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            this.pageNum = 1
            this.searchParams.efileFlag = "1";
            this.searchParams.pageSize = "9999999";
            queryE01z1ListByFlag(this.searchParams)
              .then(response => {
                if (response.rows.length == 0) {
                  this.$message.error("无数据,导出失败");
                  return;
                }
                let row = {
                  row: response.rows,
                  status: 10
                }
                downLoadPost("/export/excel/exportClick", row)
              })
          })

        } else {
          this.$confirm('是否导出选中的材料数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let row = {
              row: this.excel,
              status: 10
            }
            downLoadPost("/export/excel/exportClick", row)
          })

        }


      },
      //重置按钮
      resetQuery() {
        this.searchParams.a0101 = "";
        this.searchParams.e01z111a = "";
        this.getGridList();
      },
      //删除按钮
      deleteRec() {
        if (this.select.length < 1) {
          this.$message({type: 'warning', message: '请选择一条及以上数据进行删除'})
          return
        }

        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          // center: true
        }).then(() => {
          deleteById({ids: this.select.toString()})
            .then(response => {
              if (response.code == 200) {
                this.$message({type: 'success', message: '删除成功'})
                this.getGridList(params)
              } else {
                this.$message({type: 'warning', message: '删除失败'})
                this.getGridList(params)
              }
            })
        })
      },
      //放回原处按钮
      returnPla() {
        if (this.select.length < 1) {
          this.$message({type: 'warning', message: '请选择一条及以上数据进行操作'})
          return
        }
        console.log(this.select)
        returnPlace({ids: this.select.toString()})
          .then(response => {
            if (response.code == 200) {
              this.$message({type: 'success', message: '材料已放回原处'})
              this.getGridList(params)
            } else {
              this.$message({type: 'warning', message: '放回原处失败'})
              this.getGridList(params)
            }
          })
      },
      //全部删除按钮
      deleteAll() {
        if (this.tableData.length <= 0) {
          this.$message({
            type: 'warning',
            message: '无可清空数据'
          })
          return;
        }

        if (this.select.length == 0) {
          this.$confirm('此操作将永久清空文件列表, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            deleteById({ids: "all"})
              .then(response => {
                if (response.code == 200) {
                  this.$message({type: 'success', message: '清空成功'})
                  this.getGridList(params)
                } else {
                  this.$message({type: 'warning', message: '清空失败'})
                }
              })
          })
        }
      },

      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },

      handleCurrentChange() {

      },
      //行点击事件
      handleRowClick(row, column, event) {
        this.$refs.table.toggleRowSelection(row);
      },
      /* 复选框事件 */
      handleSelectionChange(val) {
        this.excel = val;
        this.select = val.map(item => item.id);
      },
      pageSize(val) {
        // this.searchParams.pageSize = val;
        // this.searchParams.pageNum = this.searchParams.pageNum;
        // this.getGridList();
        this.pageSize1 = val
        params.pageNum = this.pageNum,
          params.pageSize = val
        this.getGridList(params);
      },
      pageCurrent(val) {
        // this.searchParams.pageSize = this.searchParams.pageSize;
        // this.searchParams.pageNum = val;
        this.pageNum = val
        params.pageNum = val,
          params.pageSize = this.pageSize1
        this.getGridList(params);
      },
    }
  }
</script>

<style lang="scss" scoped>

  .el-col.el-col-24.el-col-xs-24 {
    padding: 10px;
  }

  .app-find {
    /*overflow: auto;*/
    height: calc(100vh - 86px);
  }

  .search-form {
    width: 100%;
    /*background:rgba(204, 204, 204, 0.18);*/
    padding: 10px;

    display: flex;
    /deep/ .el-form-item {
      margin-bottom: 0 !important;
    }
  }

  /deep/ .el-table__body td {
    padding: 0;
    height: 41px;
  }


</style>

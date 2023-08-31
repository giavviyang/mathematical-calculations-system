<template>
  <div>
    <el-row>
      <el-col :span="24" style="height:100%;padding:10px;">
        <el-row :gutter="10" style="margin:0;">
          <el-form @submit.native.prevent
                   class="search-form"
                   ref="form"
                   :model="form"
                   :inline="true"
                   label-width="68px"
                   style="background:rgba(204, 204, 204, 0.18);">
            <el-form-item label="协议标题" prop="roleName">
              <el-input style="width: 150px" @keyup.enter.native="search" size="mini" v-model="queryParams.title"
                        placeholder="请输入协议标题" clearable/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
        </el-row>
        <el-col :span="24" style="padding: 10px 0px;">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="handleUpdate">修改</el-button>
          <el-button type="primary" icon="el-icon-delete" size="mini" @click="handleDelete">删除</el-button>
          <el-button type="primary" icon="el-icon-delete" size="mini" @click="handleStart">启用协议</el-button>
        </el-col>
        <!--
            -->
        <el-table
          v-loading="loading"
          :data="tableData"
          :row-style="{height:'40px'}"
          :cell-style="{padding:'0px'}"
          row-key="id"
          align="left"
          :default-sort="{ prop: 'date', order: 'descending' }"
          stripe
          border
          empty-text=" "
          size="mini"
          tooltip-effect="light"
          ref="tableGrid"
          :header-cell-style="headClass"
          style="width: 100%; height:calc(100vh - 231px)"
          @row-click="rowSelectClick"
          @selection-change="SelectionChange"
        >
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column type="index" width="50" align="center" label="序号" show-overflow-tooltip
                           :index="customIndex"/>
          <el-table-column
            label="协议标题"
            align="center"
            prop="title"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="状态"
            align="center"
            prop="isStart"
            :formatter="statusFormat"
          />
          <el-table-column label="创建者" align="center" prop="createName"/>
          <el-table-column label="创建时间" align="center" prop="createTime">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
        </el-table>
        <div class="block" style="float: right;margin-top: 5px">
          <el-pagination :page-sizes="[20, 50, 100, 200]"
                         :page-size="pageSize"
                         :current-page="pageNum"
                         layout="total, sizes, prev, pager, next, jumper"
                         :total="total"
                         @size-change="pageSizeS"
                         @current-change="pageCurrent"
          />
        </div>
      </el-col>
    </el-row>

    <!-- 添加或修改公告对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="协议标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入协议标题"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="协议内容">
              <editor v-model="form.content" :min-height="300"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open=false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    pagingQueryAgreement,
    addAgreement,
    modifyAgreementById,
    queryAgreementById,
    removeAgreementById,
    modifyAgreementStartById,
  } from '@/api/system/userAgreement'

  const params = {
    pageNum: 1,
    pageSize: 20
  }

  export default {
    name: 'Notice',
    data() {
      return {
        formLabelWidth: '100px',
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        /* 总页数 */
        total: 0,
        pageNum: 1,
        pageSize: 20,
        /* grid数据 */
        tableData: [],
        /*选中的协议*/
        selectAgreement: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 20,
        },
        // 表单参数
        form: {
          title: '',
          content: '',
        },
        // 表单校验
        rules: {
          title: [
            {required: true, message: '协议标题不能为空', trigger: 'blur'}
          ],
        },
        rightStyle: {
          width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px'
        },
        leftStyle: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        leftStyleName: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        rightStyleName: {
          width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px'
        },
        leftStyle1: {
          width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        rightStyle1: {
          width: '99%', height: 'calc(100vh - 86px)', padding: ' 10px'
        }
      }
    },
    created() {
      this.queryUserAgreement(params)
    },
    methods: {
      //自定义序号
      customIndex(index) {
        return (this.pageNum - 1) * (this.pageSize) + (index + 1)
      },
      pageCurrent(val) {
        this.pageNum = val
        const params = {
          pageNum: val,
          pageSize: this.pageSize
        }
        this.queryUserAgreement(params)
      },
      pageSizeS(val) {
        this.pageSize = val
        const params = {
          pageNum: this.pageNum,
          pageSize: val
        }
        this.queryUserAgreement(params)
      },
      headClass() {
        return 'background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px'
      },
      //分页查询用户协议
      queryUserAgreement(params) {
        pagingQueryAgreement(params).then(response => {
          this.tableData = response.rows
          this.total = response.total
          this.loading = false
        })
      },
      // 公告状态字典翻译
      statusFormat(row, column, cellValue) {
        if (cellValue === 0) {
          return '未启用'
        } else {
          return '已启用'
        }
      },

      /** 搜索按钮操作 */
      handleQuery() {
        let params = {
          pageNum: 1,
          pageSize: 20,
          title : this.queryParams.title
        }
        this.queryUserAgreement(params)
      },
      /** 重置按钮操作 */
      resetQuery() {
        let params = {
          pageNum: 1,
          pageSize: 20,
          title : this.queryParams.title = ''
        }
        this.queryUserAgreement(params)
      },
      /*行点击事件*/
      rowSelectClick(row) {
        this.selectAgreement = []
        this.$refs.tableGrid.clearSelection()
        if (this.selectAgreement.length === 0) {
          this.selectAgreement.push(row)
          this.$refs.tableGrid.toggleRowSelection(row)
          return
        }
        this.$refs.tableGrid.toggleRowSelection(row)
      },
      /* grid 复选框 */
      SelectionChange(val) {
        this.selectAgreement = val
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.open = true
        this.title = '添加协议'
      },
      /** 修改按钮操作 */
      handleUpdate() {
        if (this.selectAgreement.length != 1) {
          this.$message({
            type: 'warning',
            message: '请勾选一条数据'
          })
        } else {
          this.form.title = this.selectAgreement[0].title
          this.form.id = this.selectAgreement[0].id
          this.form.content = this.selectAgreement[0].content
          this.form.type = this.selectAgreement[0].type
          this.form.isStart = this.selectAgreement[0].isStart
          this.open = true
          this.title = '修改协议'
        }
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form && this.form.id) {
              modifyAgreementById(this.form).then(response => {
                this.open = false
                this.form = {}
                this.queryUserAgreement(params)
                this.$message({
                  type: 'success',
                  message: '修改成功!'
                })
              })
            } else {
              addAgreement(this.form).then(response => {
                this.open = false
                this.form = {}
                this.queryUserAgreement(params)
                this.$message({
                  type: 'success',
                  message: '添加成功!'
                })
              })
            }
          }
        })
      },
      /* 全选删除 */
      handleDelete() {
        if (this.selectAgreement.length === 0) {
          this.$message({
            type: 'warning',
            message: '请勾选一条协议后点击删除'
          })
          return
        }
        if (this.selectAgreement === '') {
          this.$message({
            message: '请选择您要删除的内容',
            type: 'warning'
          })
        } else {
          this.$confirm('此操作将永久删除该协议, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            removeAgreementById(JSON.stringify(this.selectAgreement))
              .then(() => {
                this.queryUserAgreement(params)
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                })
              })
          })
        }
      },
      /* 启用协议 */
      handleStart() {
        if (this.selectAgreement.length != 1) {
          this.$message({
            type: 'warning',
            message: '请勾选一条数据'
          })
        } else {
          this.$confirm('此操作将替换正在使用的协议, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            console.log(JSON.stringify(this.selectAgreement[0].id))
            modifyAgreementStartById({id: this.selectAgreement[0].id}).then(() => {
                this.queryUserAgreement(params)
                this.$message({
                  type: 'success',
                  message: '替换成功!'
                })
              })
          })
        }
      },
    }
  }
</script>

<style lang="scss" scoped>

  #inputClear {
    /deep/ .el-input__suffix {
      top: 11px;
    }
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

  .el-input__inner {
    width: 250px;
  }

  .el-form-item__content {
    width: 250px;
  }

  .el-input {
    width: 250px;
  }

  .inputW {
    width: 250px;
  }

  .el-dialog__body {
    height: 500px;
    display: block;
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

  .tree {
    width: 100%;
    height: calc(100vh - 135px);
    // overflow: auto;
    padding: 20px;
    /*border-right: 1px solid #e2e3e6;*/
  }

  .verticalBar {
    width: 2px;
    height: 354px;
    background: #cccccc;
    // margin-top: 31px;
    vertical-align: top;
    margin: 0 auto
  }

  //设置统一表头
  /deep/ .el-table tbody tr:hover > td {
    background-color: #ecf5ff !important
  }

  /deep/ .el-table__body td {
    padding: 0;
    height: 40px;
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

  ul {
    padding: 0;
  }

  .shrinkageDiv {
    background-color: #1890ff;
    cursor: pointer;
    right: 0;
    bottom: 0;
    margin: auto;
    top: 0;
    height: 200px;
    width: 8px;
    border-radius: 20px;
    position: absolute;
  }

  .el-dialog__wrapper {
    z-index: 99999;
  }

  /deep/ .el-table .el-table__header-wrapper th {
    height: 31px;
  }

  .table-style {
    /deep/ .el-table__fixed-right-patch {
      background-color: #F8F8F9;
    }
  }
</style>


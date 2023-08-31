<template>
  <div>
    <el-col :span="24" :xs="24" style="height: calc(100vh - 86px);">
      <el-row :gutter="10" ref="searchRow1" style="margin:0;">
        <el-form
          v-if="showBorrowing"
          class="search-form"
          :model="params"
          :inline="true"
          v-hasPermi="['system:storageConfig:search']"
          style="background:rgba(204, 204, 204, 0.18);"
          @submit.native.prevent
        >
          <el-form-item label="存储名称" prop="fileName">
            <el-input
              v-model="params.name"
              placeholder="请输入存储名称"
              clearable
              size="mini"
              style="width: 150px"
              @keyup.enter.native="onEnterSearch"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryBorrowing">搜索
            </el-button>
          </el-form-item>
        </el-form>

      </el-row>
      <el-col :span="24" style="padding: 10px 0px;">
        <el-button type="primary" size="mini" v-hasPermi="['system:storageConfig:add']" icon="el-icon-plus" @click="addWindows">新增</el-button>
        <el-button type="primary" size="mini" v-hasPermi="['system:storageConfig:edit']" icon="el-icon-edit" @click="electronicFeedback">编辑</el-button>
        <el-button type="primary" size="mini" v-hasPermi="['system:storageConfig:delete']" icon="el-icon-delete" @click="deleteStorage">删除</el-button>
      </el-col>
      <el-table
        :default-sort="{ prop: 'date', order: 'descending' }"
        stripe
        border
        class="table-style"
        :height="tableHeight"
        empty-text=" "
        size="mini"
        tooltip-effect="light"
        ref="BorrowingData0"
        :header-cell-style="headClass"
        style="width: 100%"
        :data="BorrowingData"
        @row-click="StepsListRowClick"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="40"/>
        <el-table-column style="width: 20px" type="index" show-overflow-tooltip align="center" label="序号" :index="customIndex"/>
        <el-table-column
          align="center"
          show-overflow-tooltip
          v-for="(item, index) in pendingApprovalCol"
          :v-if="item.isCol"
          :key="index"
          :width="item.width"
          :formatter="item.formatter"
          :prop="item.prop"
          :label="item.label"/>
        <el-table-column label="操作" align="center" width="150px" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" size="mini" :disabled="true" v-hasPermi="['system:storageConfig:active']"
                       v-if="scope.row.active == '1'">已启用
            </el-button>
            <el-button type="text" size="mini" v-hasPermi="['system:storageConfig:active']"
                       v-if="scope.row.active != '1'" @click="active(scope.row)">启用
            </el-button>
            <el-button type="text" size="mini" v-hasPermi="['system:storageConfig:test']" @click="test(scope.row)">
              测试连接
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="block" style="float: right;margin-top: 5px">
        <el-pagination
          :page-sizes="[20, 50, 100, 200]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          :current-page="pageNum"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-col>

    <!--  新增编辑页面  -->
    <el-dialog
      :title="titleName"
      style="margin-top: 3%"
      width="820px"
      class=" dialog-style dialog-basic"
      :visible.sync="approvalProcessingShow"
      :append-to-body="true"
      :close-on-click-modal="false"
      :destroy-on-close="true"
    >
      <div style="">
        <el-form ref="fileStroageForm" :inline="true" :model="fileStroageForm" :rules="rules" style="margin-left: 10%;margin-right: 10%"
                 label-width="120px">
          <el-form-item prop="name" label="存储名称">
            <el-input size="mini" v-model="fileStroageForm.name" clearable placeholder="请输入存储名称"/>
          </el-form-item>
          <el-form-item prop="type" label="存储方式">
            <el-select size="mini" style="width: 100%;" clearable v-model="fileStroageForm.type" placeholder="请选择存储方式">
              <el-option
                v-for="item in typeOptions"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="ip" label="ip地址">
            <el-input size="mini" v-model="fileStroageForm.ip" clearable placeholder="请输入ip地址"/>
          </el-form-item>
          <el-form-item prop="port" label="端口号">
            <el-input size="mini" v-model="fileStroageForm.port" clearable placeholder="请输入端口号"/>
          </el-form-item>
          <el-form-item prop="username" label="用户名称">
            <el-input size="mini" v-model="fileStroageForm.username" clearable placeholder="请输入用户名称"/>
          </el-form-item>
          <el-form-item prop="password" label="密码">
            <el-input size="mini" v-model="fileStroageForm.password" clearable placeholder="请输入密码"/>
          </el-form-item>
          <el-form-item prop="basePath" label="存储路径">
            <el-input size="mini" v-model="fileStroageForm.basePath" clearable placeholder="请输入存储路径"/>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="exit()">取 消</el-button>
        <el-button size="mini" type="primary" @click="addStorageConfig()">保 存</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
  import {downLoad, downLoadPost} from '@/downLoad/filedownload'
  import {selectAll, insert, deleteStorage, updateById, active, test} from '@/api/system/storage'
  import {getTreeByDictType} from '@/api/system/dict/data'

  const params = {
    pageNum: 1,
    pageSize: 20
  }
  export default {
    data() {
      let ipTest = /^(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d{2}|2[0-4]\d|25[0-5])$/;
      let isIpTest = (rule, value, callback) => {
        if(value==""){
          callback();
        }
        if (!ipTest.test(value)) {
          return callback(new Error('只能输入IP地址!'))
        } else {
          callback();
        }
      };
      let portTest = /^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
      let isPortTest = (rule, value, callback) => {
        if(value==""){
          callback();
        }
        if (!portTest.test(value)) {
          return callback(new Error('只能输入端口号!'))
        } else {
          callback();
        }
      };
      let userNameTest = /^[0-9a-zA-Z]+$/;
      let isUserNameTest = (rule, value, callback) => {
        if(value==""){
          callback();
        }
        if (!userNameTest.test(value)) {
          return callback(new Error('只能输入数字和字母!'))
        } else {
          callback();
        }
      };
      var winpath = /^[a-zA-Z];[\\/]((?! )(?![^\\/]*\s+[\\/])[\w -]+[\\/])*(?! )(?![^.]+\s+\.)[\w -]+$/;
      var lnxPath = /^([\/] [\w-]+)*$/;
      let isPathTest = (rule, value, callback) => {
        if(value==""){
          callback();
        }
        if (!winpath.test(value)) {
          if (!lnxPath.test(value)) {
            return callback(new Error('请输入正确的路径!'))
          }else{
            callback();
          }
        } else {
          callback();
        }
      };
      return {
        // 存储类型数据字典
        typeOptions: [],

        approvalProcessingShow: false,
        showBorrowing: true,
        borrowingParams: {},
        fileStroageForm: {
          name:"",
          type:"",
          ip:"",
          port:"",
          username:"",
          password:"",
          basePath:""
        },
        tableHeight: "73%",
        searchRowHeight: '',
        searchRow1: 'searchRow1',
        electronicCheck: [],
        total: 0,
        pageSizeParam: {},
        titleName: "新增存储配置",
        pageNum: 1,
        pageSize: 20,
        BorrowingData: [],

        //表单校验
        rules: {
          name: [
            {required: true, message: '请输入存储名称', trigger: 'blur'},
            {min: 1, max: 10, message: '输入内容不得超过10个字符', trigger: 'blur'}
          ],
          type: [
            {required: true, message: '请选择存储方式', trigger: 'blur'},
          ],
          ip: [
            {min: 1, max: 15, message: '输入内容不得超过15个字符', trigger: 'blur'},
            // {validator: isIpTest}
          ],
          port: [
            {min: 1, max: 5, message: '输入内容不得超过5个数字', trigger: 'blur'},
            // {validator: isPortTest},
          ],
          username: [
            {min: 1, max: 20, message: '输入内容不得超过20个字符', trigger: 'blur'},
            // {validator: isUserNameTest},
          ],
          password: [
            {min: 1, max: 20, message: '输入内容不得超过20个字符', trigger: 'blur'},
            // {validator: isUserNameTest},
          ],
          basePath: [
            {min: 1, max: 60, message: '输入内容不得超过60个字符', trigger: 'blur'},
            // {validator: isPathTest},
          ]
        },

        //校验规则
        rulesUtil: {
          //判空
          notNull: {
            required: true,
            message: '不可为空',
            trigger: 'blur'
          },
          //判断长度（长）
          lengthLong: {
            min: 0,
            max: 40,
            message: '长度不可大于40',
            trigger: 'blur'
          },
          //判断长度（短）
          lengthShort: {
            min: 0,
            max: 18,
            message: '长度不可大于18',
            trigger: 'blur'
          },
          //判断长度（非常长）
          lengthLongLong: {
            min: 0,
            max: 500,
            message: '长度不可大于500',
            trigger: 'blur'
          },
          //判断数字长度（短）
          lengthNumShort: {
            type: 'number',
            min: 1,
            max: 999,
            message: '请输入0-999之间的数值',
            trigger: 'blur'
          },
          lengthNumLong: {
            type: 'number',
            min: 1,
            max: 999999,
            message: '请输入0-999999之间的数值',
            trigger: 'blur'
          },
          //判断是否为数字
          isNumber: {
            type: 'number',
            message: '该值为数字',
            trigger: 'blur'
          },
          //可空数字校验
          isNullNumLongLong: {
            validator: (rule, value, callback) => {
              if (value != "") {
                if ((/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/).test(value) == false) {
                  callback(new Error("请填写大于0的数字"));
                } else {
                  if (value > 0 && value < 999) {
                    callback();
                  } else {
                    callback(new Error("请填写大于0并且小于999的数字"));
                  }

                }
              } else {
                callback();
              }

            },
            trigger: 'change'
          },
          //ip校验
          ipRule: {
            validator: (rule, value, callback) => {
              if (value === "") {
                callback(new Error("ip地址不能为空"));
              } else {
                if ((/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/).test(value)) {
                  callback();
                } else {
                  callback(new Error("请填写正确的ip地址"));
                }
              }

            },
            trigger: 'change'
          }
        },

        params: {
          pageNum: 1,
          pageSize: 20
        },

        pendingApprovalCol: [
          {label: '存储名称', prop: 'name', isCol: true, width: "110px"},
          {label: '存储方式', prop: 'type', isCol: true, width: "110px", formatter: this.typeFormat},
          {label: 'ip地址', prop: 'ip', isCol: true, width: "110px"},
          {label: '端口号', prop: 'port', isCol: true, width: "110px"},
          {label: '存放路径', prop: 'basePath', isCol: true, width: "110px"},
          {label: '创建时间', prop: 'createTime', isCol: true, width: "200px"},
          {label: '创建人姓名', prop: 'createName', isCol: true, width: "150px"},
          {label: '更新时间', prop: 'updateTime', isCol: true, width: "200px"},
          {label: '更新时间', prop: 'updateTime', isCol: true, width: "200px"},
          {label: '更新人姓名', prop: 'updateName', isCol: true, width: "150px"},
        ],
      }
    },
    methods: {
      //自定义序号
      customIndex(index){
        return (this.pageNum-1)*(this.pageSize)+(index+1);
      },
      exit(){
        this.approvalProcessingShow=false
        this.fileStroageForm = {
          name:"",
          type:"",
          ip:"",
          port:"",
          username:"",
          password:"",
          basePath:""
        };
      },
      //新增配置
      addStorageConfig() {
        const _this = this;
        if (_this.fileStroageForm.id) {
          updateById(_this.fileStroageForm)
            .then(function (response) {
              if(response.code ==200){
                _this.loadGrid(params);
                _this.approvalProcessingShow = false;
                _this.$message({
                  message: '修改成功',
                  type: 'success'
                });
                _this.formatForm();
              }else{
                _this.$message({
                  message: '修改失败',
                  type: 'error'
                });
              }

            })
        } else {
          this.$refs["fileStroageForm"].validate((valid) => {
            if (valid) {
              insert(_this.fileStroageForm)
                .then(function (response) {
                  if(response.code == 200){
                    _this.loadGrid(params);
                    _this.approvalProcessingShow = false;
                    _this.$message({
                      message: "新增成功",
                      type: 'success'
                    });
                    _this.formatForm();
                  }else{
                    _this.$message({
                      message: '新增失败',
                      type: 'error'
                    });
                  }

                })
            } else {
              return false;
            }
          })

        }

      },
      //删除配置
      deleteStorage() {
        const _this = this;
        if (this.electronicCheck.length == 0) {
          _this.$message({
            message: '请至少选择一条记录',
            type: 'warning'
          })
          return;
        }
        let ids = [];
        this.electronicCheck.map(item => {
          ids.push(item.id);
        })
        this.$confirm(
          '是否删除选中的' + this.electronicCheck.length + '条数据',
          '系统提示',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          deleteStorage({ids: JSON.stringify(ids)}).then(response => {
            if (response.code == 200) {
              _this.$message({
                message: "删除成功",
                type: 'success'
              })
              _this.loadGrid(params);
            }else{
              _this.$message({
                message: '删除失败',
                type: 'error'
              });
            }
          })
        })
      },
      //激活配置
      active(row) {
        const _this = this;
        this.$confirm('当前操作会切换系统存储配置,非此连接上传的文件将不可使用,请谨慎操作', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          active({id: row.id})
            .then(function (response) {
              _this.loadGrid(params);
              if (response.code == 200) {
                _this.$message({
                  message: "启用成功",
                  type: 'success'
                })
              } else {
                _this.$message({
                  message: "启用失败",
                  type: 'error'
                })
              }
            })
        })
      },
      StepsListRowClick(row) {
        console.log(row)
        this.$refs.BorrowingData0.clearSelection()
        if (this.electronicCheck.length === 0) {
          this.$refs.BorrowingData0.toggleRowSelection(row)
          return;
        }
        this.$refs.BorrowingData0.toggleRowSelection(row)
      },
      //测试连接
      test(row) {
        const _this = this;
        test(row)
          .then(function (response) {
            if (response.code == 200) {
              _this.$message({
                message: "连接成功",
                type: 'success'
              })
            } else {
              Message({
                message: '连接失败',
                type: 'error'
              });
            }
          })
      },
      //编辑
      electronicFeedback() {
        const _this = this;
        if (this.electronicCheck.length <= 0) {
          this.$message({
            message: '请勾选要编辑的数据',
            type: 'warning'
          });
          return;
        }
        if (this.electronicCheck.length > 1) {
          this.$message({
            message: '请选择一条数据进行编辑',
            type: 'warning'
          });
          return;
        }
        _this.fileStroageForm = this.electronicCheck[0];
        _this.approvalProcessingShow = true;
      },
      formatForm() {
        const _this = this;
        _this.fileStroageForm = {
          name:"",
          type:"",
          ip:"",
          port:"",
          username:"",
          password:"",
          basePath:""
        };
      },
      //搜索
      handleQueryBorrowing() {
        const _this = this;
        params.name = _this.params.name;
        this.pageNum = 1;
        params.pageNum = 1;
        _this.loadGrid(params);
      },
      exportExcel() {
        this.$confirm('是否确认导出Excel?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          // 暂时注释 谁写这个功能谁写
          // if(this.electronicCheck.length==0){
          //   // 在这里通过调用查询列表的方法查询数据返回传入后台 导出Excel
          // }else{
          //   // 传入选中的数据到后台 导出Excel
          // }
          // 暂时传入假数据
          // let row = {
          //   row: this.electronicCheck,
          //   status: 20
          // }
          // downLoadPost("/export/excel/exportClick", row)

        });
      },
      addWindows() {
        const _this = this;
        _this.fileStroageForm = {};
        _this.approvalProcessingShow = true;
      },
      // grid 选中
      handleSelectionChange(val) {
        this.electronicCheck = val
      },
      // 档案借阅监听
      // 分页大小
      handleSizeChange(val) {
        // this.pageSize = val
        // this.pageSizeParam.pageSize = this.pageSize
        // this.archivesaFunction(
        //     this.pageSizeParam
        // )
        this.pageSize = val
        params.pageNum = this.pageNum;
        params.pageSize = val
        this.loadGrid(params)

      },
      // 分页监听
      handleCurrentChange(val) {
        // this.pageNum = val
        // this.pageSizeParam.pageSize = this.pageSize
        // this.archivesaFunction(
        //     this.pageSizeParam
        // )
        this.pageNum = val
        params.pageNum = val;
        params.pageSize = this.pageSize
        this.loadGrid(params)
      },
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },

      /*--- 格式化字典项 ---*/
      typeFormat(row, col) {
        return this.selectDictLabel(this.typeOptions, row.type);
      },
      onEnterSearch() {
        this.handleQueryBorrowing();
      },
      loadGrid(params) {
        const _this = this;
        selectAll(params)
          .then(function (response) {
            _this.BorrowingData = response.rows
            _this.total = response.total
          })
      }
    },
    created() {
      const _this = this;
      /*--- 查询字典表 ---*/
      getTreeByDictType({transValue: "storage_type"}).then(response => {
        this.typeOptions = response.data;
      });

      /*--- 查询配置列表 ---*/
      _this.loadGrid(params);
    },
    mounted() {
      /*--- 表单校验 ---*/
      // this.formRules = {
      //   name: [this.rulesUtil.notNull],
      //   type: [this.rulesUtil.notNull]
      // };

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
    watch: {
      BorrowingData: {
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(() => {
            _this.$refs.BorrowingData0.doLayout();
          })
        },
        deep: true,
        immediate: true
      },

    },
  };

</script>
<style lang="scss" scoped>
  .el-col.el-col-24.el-col-xs-24 {
    padding: 10px;
  }

  /* 左侧竖分割线 */
  /*.el-tabs--left /deep/ .el-tabs__header.is-left {
    height: calc(100vh - 86px);
  }*/

  .block {
    float: right;
  }

  .form-width {
    width: 40%;
    display: flex;
  }

  // .demo-table-expand {
  //   font-size: 0;
  // }

  // .demo-table-expand label {
  //   width: 90px;
  //   color: #99a9bf;
  // }

  // .demo-table-expand .el-form-item {
  //   margin-right: 0;
  //   margin-bottom: 0;
  //   width: 50%;
  // }

  .inputW {
    width: 200px;
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

  // /deep/.el-table tbody tr:hover>td {
  //   background-color:#ecf5ff !important
  // }

  // 到期续借弹窗
  .dialog-state {
    position: relative;
    height: 180px;

    margin: 20px;
    margin-left: 10%;
    width: 80%;
    // width: 240px;
    border: 1px solid #CAD9E7;
    box-shadow: 0 0 3px 1px #EBF0F5;
    .state {
      position: absolute;
      top: -9px;
      left: 18px;
    }
    .state-form {
      width: 150px;
      margin-left: 20%;
      margin-top: 20px
    }
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

  /deep/ .el-table .el-table__header-wrapper th {
    height: 31px;
  }

  .table-style {
    /deep/ .el-table__fixed-right-patch {
      background-color: #F8F8F9;
    }
  }
</style>

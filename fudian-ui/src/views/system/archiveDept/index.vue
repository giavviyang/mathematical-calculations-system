<template>
  <div class="app-wait">
    <el-row>
      <el-col :span="24" style="height:100%">
        <el-col :span="4" :style="leftStyleName" style="height: calc(100vh - 86px)">
          <div id="inputClear">
            <!-- 树结构 -->
            <el-input placeholder="请输入内容" v-if="treeShow" ref="left_input" v-model="filterText"
                      style="width:90%;padding:20px 0 0 20px" size="mini" clearable/>
          </div>
          <div class="tree tree-style">
            <el-scrollbar style="height: 100%;">
              <el-tree
                node-key="id"
                empty-text=" "
                ref="tree4"
                @node-click="treeCleck"
                :data="data2"
                :props="defaultProps"
                icon-class="el-icon-folder-opened"
                :filter-node-method="filterNode"
                :highlight-current="true"
                :expand-on-click-node="false"
                default-expand-all/>
            </el-scrollbar>
          </div>
          <div class="shrinkageDiv" @click="divClick()">
            <div :class="picClassname"></div>
          </div>
        </el-col>
        <el-col :span="20" style="padding: 10px" :style="rightStyleName">
          <el-col :span="24" ref="searchForm" style="padding: 10px 0;">
            <el-button type="primary" size="mini" icon="el-icon-plus" v-hasPermi="['system:archiveDept:add']" @click="addSysDeptBygd">新增</el-button>
            <el-button type="primary" size="mini" icon="el-icon-edit" v-hasPermi="['system:archiveDept:modify']" @click="upData">编辑</el-button>
            <el-button type="primary" size="mini" icon="el-icon-delete" v-hasPermi="['system:archiveDept:delete']" @click="handleDelete">删除</el-button>
            <el-button type="primary" size="mini" icon="el-icon-sort" v-hasPermi="['system:archiveDept:sort']" @click="orderDeptNumButton">排序</el-button>
            <el-button type="primary" size="mini" icon="el-icon-download" v-hasPermi="['system:archiveDept:export']" @click="exportExcel">导出</el-button>
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
            ref="tableGrid"
            :header-cell-style="headClass"
            style="width: 100%"
            :data="tableData"
            @row-click="StepsListRowClick"
            @selection-change="handleSelectionChange"


          >
            <el-table-column type="selection" width="40"/>
            <el-table-column type="index" width="50" align="center" label="序号" show-overflow-tooltip :index="customIndex"/>
            <el-table-column show-overflow-tooltip align="center" v-for="(item, index) in deptColomn" :formatter="item.formatter" :key="index"
                             :v-if="item.isCol" :prop="item.prop" :label="item.label"/>
          </el-table>
          <div class="block" style="float: right;margin-top: 5px">
            <el-pagination
              :page-sizes="[20, 50, 100, 200]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              :current-page="pageNum"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-col>
      </el-col>


      <!--  新增编辑页面  -->
      <el-dialog
        :title="title"
        width="50%"
        class=" dialog-style dialog-basic"
        :destroy-on-close="true"
        :append-to-body="true"
        :close-on-click-modal="false"
        :visible.sync="addOrEditW">

        <div style="">
          <el-row>
            <el-col :span="24" style="width:100%">
              <el-form ref="deptForm" :model="deptForm" style="margin-left: 10%;margin-right: 10%" :rules="rules"
                       label-width="120px">
                <el-col :span="12">
                  <el-form-item prop="deptName" label="单位名称">
                    <el-input size="mini" clearable v-model="deptForm.deptName" placeholder="请输入单位名称" style="width:100%"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item prop="deptNumber" label="单位编号">
                    <el-input size="mini" clearable v-model="deptForm.deptNumber" placeholder="请输入单位编号" style="width:100%"/>
                  </el-form-item>
                </el-col>
                <!--<el-col :span="24">-->
                  <!--<el-form-item prop="status" label="档案单位状态 ">-->
                    <!--<el-radio-group style="" v-model="deptForm.status">-->
                      <!--<el-radio label="0">启用</el-radio>-->
                      <!--<el-radio label="1">禁用</el-radio>-->
                    <!--</el-radio-group>-->
                  <!--</el-form-item>-->
                <!--</el-col>-->
                <el-col :span="24">
                  <el-form-item prop="remark" label="备注">
                    <el-input size="mini" clearable v-model="deptForm.remark" placeholder="请输入备注" type="textarea"
                              style="width:100%"/>
                  </el-form-item>
                </el-col>
              </el-form>
            </el-col>
          </el-row>
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="addOrEditW=false">取 消</el-button>
          <el-button size="mini" type="primary" @click="saveDeptForm('deptForm')">保 存</el-button>

        </div>
      </el-dialog>

      <!--  排序页面  -->
      <el-dialog
        :title="title"
        width="50%"
        class=" dialog-style dialog-basic"
        :destroy-on-close="true"
        :append-to-body="true"
        :close-on-click-modal="false"
        :visible.sync="orderDeptNum">

        <div class="btn-sort">
          <el-button type="primary" icon="el-icon-top" size="mini" @click="moveUp">上移</el-button>
          <el-button type="primary" icon="el-icon-bottom" size="mini" @click="moveDown">下移</el-button>
          <!-- <span class="span-tit">当前材料类型：</span> -->
        </div>
        <div style="padding:10px 0 0 0">
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            class="table-style"
            :row-key="getRowKey"
            height="400px"
            empty-text=" "
            size="mini"
            tooltip-effect="light"
            ref="tableGrid1"
            :header-cell-style="headClass"
            style="width: 100%"
            :data="tableData1"
            @row-click="StepsListRowClick1"
            @selection-change="handleSelectionChange1"
          >
            <el-table-column type="selection" width="40" :reserve-selection="true"/>
            <el-table-column type="index" width="50" align="center" label="序号"/>
            <el-table-column
              show-overflow-tooltip
              align="center"
              v-for="(item, index) in deptColomn"
              :formatter="item.formatter"
              :key="index"
              :v-if="item.isCol"
              :prop="item.prop"
              :label="item.label"/>
          </el-table>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="orderDeptNum=false">取 消</el-button>
          <el-button size="mini" type="primary" @click="saveOrderNumber">保 存</el-button>
        </div>
      </el-dialog>


    </el-row>
  </div>
</template>
<script>
  import {
    listDept, getDept, addDept, updateDept, getListDept, delDept, orderSave,
    deleteDeptByIds, querySysDeptByLike, dealStatus, getDeptTree
  } from '@/api/system/dept'
  import {prompt} from '@/components/windoprompt/prompt'
  import {downLoad, downLoadPost} from '@/downLoad/filedownload'

  export default {
    data() {
      return {
        treeHeight: "height:500px",
        orderDeptNum: false,
        tableHeight: "73%",
        rules:{
          deptName:[
            {required: true, message: '请输入单位名称', trigger: 'blur'},
            {min: 0, max: 18, message: '内容不得超过18个字符', trigger: 'blur'}
          ],
          deptNumber:[
            {min: 0, max: 30, message: '内容不得超过30个字符', trigger: 'blur'}
          ],
          remark:[
            {min: 0, max: 200, message: '内容不得超过200个字符', trigger: 'blur'}
          ]
        },
        addSysDetp: {
          deptName: [
            {required: true, message: '请输入单位名称', trigger: 'blur'},
            {min: 2, max: 18, message: '长度在 2 到 18 个字符', trigger: 'blur'}
          ],
          leader: [
            {required: true, message: '请填写负责人', trigger: 'blur'},
            {min: 2, max: 18, message: '长度在 2 到 18 个字符', trigger: 'blur'}
          ],
          phone: [
            {required: true, message: '请填写电话号码', trigger: 'blur'},
            {min: 10, max: 11, message: '长度在 10 到 11 个数字', trigger: 'blur'}
          ],
          email: [
            {required: true, message: '请填写邮箱地址', trigger: 'blur'},
            {min: 8, max: 20, message: '请输入8-20位的字符邮箱地址', trigger: 'blur'}
          ],
        },
        // 总页数
        total: 0,
        pageNum: 1,
        pageSize: 20,
        tableData1: [],
        pageParam: {
          pageNum: 1,
          pageSize: 20,
        },
        loading: true,
        addOrEditW: false,
        checkTreeData: {},
        title: "新增档案单位信息",
        deptForm: {
          id:'',
          deptName:'',
          deptNumber:'',
          remark:''
        },
        // 全选
        isIndeterminate: true,
        addFormVisible: false,
        filterText: "",
        deptType: 1,
        updateFormVisible: false,
        addForm: {
          deptName: '',
          leader: '',
          phone: '',
          email: '',
          status: 0,
        },
        OrderCheckData: [],
        updateForm: '',
        formLabelWidth: '120px',
        showSearchBox: false,
        queryForm: {
          deptName: ''
        },
        col: [],
        deptColomn: [
          {
            label: '单位名称', prop: 'deptName', formatter: function (row, column, cellValue) {
              return cellValue
            }, isCol: true
          },
          {
            label: '单位编号', prop: 'deptNumber', formatter: function (row, column, cellValue) {
              return cellValue
            }, isCol: true
          },
          // {
          //   label: '单位状态', prop: 'status', formatter: function (row, column, cellValue) {
          //     return cellValue === "0" ? "启用" : "禁用";
          //   }, isCol: true
          // },
          {
            label: '显示顺序', prop: 'orderNum', formatter: function (row, column, cellValue) {
              return cellValue
            }, isCol: true
          },
          {
            label: '备注', prop: 'remark', formatter: function (row, column, cellValue) {
              return cellValue
            }, isCol: true
          },
        ],
        searchForm:"searchForm",
        searchRowHeight:"",
        tableData: [],
        deleteCheck: [],
        gridCheck: [],
        data2: [],
        defaultProps: {
          children: "children",
          label: "deptName",
        },
        treeShow: true,
        rightStyle: {
          width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)'
        },
        leftStyle: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        leftStyleName: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        rightStyleName: {
          width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)'
        },
        leftStyle1: {
          width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        rightStyle1: {
          width: '99%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)'
        },
        picClassname: "shrinkageLeft",
        leftTree1: true,
      }
    },
    mounted() {
      this.getListDeptByParentId({
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        parentId: "000",
        deptType: this.deptType
      })
      // 额外加49的像素 因为没有搜索区
      this.$nextTick(() => {
        this.searchRowHeight = this.$refs[this.searchForm].$el.offsetHeight
        this.tableHeight = ((document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + 49) + "px"
      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchForm].$el.offsetHeight
          this.tableHeight = ((document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + 49) + "px"
        })();
      };
      this.initLeftTreeData()
    },
    watch: {
      filterText(val) {
        this.$refs.tree4.filter(val);
      },
      tableData: {
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(() => {
            _this.$refs.tableGrid.doLayout();
          })
        },
        deep: true,
        // immediate:true
      },
    },
    methods: {
      //自定义序号
      customIndex(index){
        return (this.pageNum-1)*(this.pageSize)+(index+1);
      },
      StepsListRowClick1(row) {
        console.log(row)
        this.$refs.tableGrid1.clearSelection()
        if (this.OrderCheckData.length === 0) {
          this.$refs.tableGrid1.toggleRowSelection(row)
          return;
        }
        this.$refs.tableGrid1.toggleRowSelection(row)
      },
      // 复选框事件
      handleSelectionChange1(val) {
        this.OrderCheckData = val

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
      //排序保存
      saveOrderNumber() {
        let ids = this.tableData1.map(item => item.id).join(",");
        orderSave({pageNum: this.pageNum, pageSize: this.pageSize, jsonStr: ids}).then(response => {
          if (response.code == 200) {
            this.$message({
              type: "success",
              message: "排序成功"
            })
            this.initLeftTreeData()
            let params = {
              pageSize: this.pageSize,
              pageNum: this.pageNum,
              parentId: "000",
              deptType: this.deptType
            }
            if (this.checkTreeData.id != "" && this.checkTreeData.id != undefined) {
              params.parentId = this.checkTreeData.id;
            }
            this.getListDeptByParentId(params)
            this.orderDeptNum = false;
          } else {
            this.$message({
              type: "error",
              message: "排序失败"
            })
          }
        })

      },
      moveUp() {
        if (this.OrderCheckData.length != 1) {
          this.$message({
            type: "warning",
            message: "请勾选一条数据"
          })

        } else {
          let checkData = this.OrderCheckData[0];
          let tableDatas = JSON.parse(JSON.stringify(this.tableData1));
          for (let n in tableDatas) {
            if (tableDatas[n].id == checkData.id) {
              if (n != 0) {
                let nextData = tableDatas[(n - 1)];
                tableDatas[n - 1] = checkData;
                tableDatas[n] = nextData;
                this.tableData1 = tableDatas;
              }
            }
          }
        }
      },
      moveDown() {
        if (this.OrderCheckData.length != 1) {
          this.$message({
            type: "warning",
            message: "请勾选一条数据"
          })

        } else {
          let checkData = this.OrderCheckData[0];
          let tableDatas = JSON.parse(JSON.stringify(this.tableData1));
          for (let n in tableDatas) {
            if (tableDatas[n].id == checkData.id) {
              if (n != tableDatas.length - 1) {
                let nextData = tableDatas[Number(n) + 1];
                tableDatas[Number(n) + 1] = checkData;
                tableDatas[n] = nextData;
                this.tableData1 = tableDatas;
                return;
              }
            }
          }
        }
      },
      exportExcel() {
        if (this.gridCheck.length == 0) {
          this.$confirm('是否导出搜索后的机构数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            this.pageParam.pageNum = 1;
            this.pageParam.pageSize = 99999999;
            if (this.pageParam.parentId == "" || this.pageParam.parentId == undefined) {
              this.pageParam.parentId = "000";
              this.pageParam.deptType = "1";
            }
            getListDept(this.pageParam).then(response => {
              if (response.rows.length == 0) {
                this.$message.error("无数据,导出失败");
                return;
              }
              let row = {
                row: response.rows,
                status: 6
              }
              downLoadPost("/export/excel/exportClick", row)
            })
          })
        } else {
          this.$confirm('是否导出选中的机构数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let row = {
              row: this.gridCheck,
              status: 6
            }
            downLoadPost("/export/excel/exportClick", row)
          })

        }

      },

      orderDeptNumButton() {
        if (this.tableData.length > 1) {
          this.tableData1 = JSON.parse(JSON.stringify(this.tableData));
          this.orderDeptNum = true;
        } else if (this.tableData.length == 1) {
          this.$message({
            type: "warning",
            message: "当前表格中数据只有一条，无需排序"
          })
        } else {
          this.$message({
            type: "warning",
            message: "当前表格没有数据，无法排序"
          })
        }
      },
      getRowKey(row) {
        return row.id
      },
      // 分页大小
      handleSizeChange(val) {
        this.pageSize = val
        this.pageParam.pageSize = this.pageSize
        this.getListDeptByParentId(this.pageParam)
      },
      // 分页监听
      handleCurrentChange(val) {
        this.pageNum = val
        this.pageParam.pageNum = this.pageNum
        this.getListDeptByParentId(this.pageParam)
      },
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      initLeftTreeData() {
        getDeptTree({deptType: this.deptType,otherParam:"isSystem"}).then(response => {
          this.data2 = response.data
        })
      },
      initForm() {
        this.deptForm = {
          deptName: "",
          deptNumber: "",
          status: "0",
          remark: "",
          deptType: this.deptType,
          id: "",
        }
      },
      //新增单位
      addSysDeptBygd() {
        this.initForm()
        if (this.data2.length == 0) {
          this.deptForm.parentId = "000";
          this.title = '新增档案单位根节点信息';
          this.addOrEditW = true;
        } else {
          this.deptForm.parentId = this.checkTreeData.id;
          this.title = '新增档案单位信息';
          this.addOrEditW = true;
        }
        this.deptForm.ancestors = this.checkTreeData.ancestors == null ? "000," : this.checkTreeData.ancestors;
      },
      getListDeptByParentId(param) {
        getListDept(param).then(response => {
          this.total = response.total
          this.tableData = response.rows;
        })
      },
      //编辑单位
      upData() {
        if (this.gridCheck.length != 1) {
          this.$message({
            type: "warning",
            message: "请勾选一条数据"
          })
        } else {
          // this.deptForm = this.gridCheck[0];
          this.deptForm.deptName = this.gridCheck[0].deptName;
          this.deptForm.deptNumber = this.gridCheck[0].deptNumber;
          this.deptForm.remark = this.gridCheck[0].remark;
          this.deptForm.id = this.gridCheck[0].id;
          this.deptForm.parentId = this.gridCheck[0].parentId;

          this.title = '编辑档案单位信息';
          this.deptForm.deptType = this.deptType
          this.addOrEditW = true;
        }

      },


      //新增或编辑单位时保存干部档案
      saveDeptForm(form) {
        let _this = this;
        this.$refs[form].validate((valid) => {
          if (valid) {
            if (this.title == "编辑档案单位信息") {
              updateDept(_this.deptForm)
                .then(function (response) {
                  prompt("保存", 0)
                  _this.pageNum = 1;
                  _this.pageParam.pageNum = 1;
                  if (_this.checkTreeData.id != "" && _this.checkTreeData.id != undefined) {
                    _this.pageParam.parentId = _this.checkTreeData.id;
                  } else {
                    _this.pageParam.parentId = "000";
                  }
                  _this.pageParam.deptType = _this.deptType;
                  _this.getListDeptByParentId(_this.pageParam)
                  _this.initLeftTreeData()
                  _this.addOrEditW = false;

                })

            } else {
              addDept(_this.deptForm)
                .then(function (response) {
                  prompt("新增", 0)
                  _this.pageNum = 1;
                  _this.pageParam.pageNum = 1;
                  if (_this.checkTreeData.id != "" && _this.checkTreeData.id != undefined) {
                    _this.pageParam.parentId = _this.checkTreeData.id;
                  } else {
                    _this.pageParam.parentId = "000";
                  }
                  _this.pageParam.deptType = _this.deptType;
                  _this.getListDeptByParentId(_this.pageParam)
                  _this.initLeftTreeData()
                  _this.addOrEditW = false;
                  _this.deptForm.deptName = "";
                  _this.deptForm.deptNumber = "";
                  _this.deptForm.remark = "";
                })
            }
          }else{
            return false;
          }
        })

      },

      //点击左侧树节点
      treeCleck(val) {
        this.checkTreeData = val;
        console.info(this.checkTreeData)
        this.pageNum = 1;
        this.pageParam.pageNum = 1;
        this.pageParam.parentId = val.id;
        this.pageParam.deptType = this.deptType;
        this.getListDeptByParentId(this.pageParam)
      },

      filterNode(value, data) {
        if (!value) return true;
        return data.deptName.indexOf(value) !== -1;
      },
      //搜索单位得方法
      searchDept() {
        let name = this.queryForm.deptName
        if (name == "") {
          this.$message({
            type: 'warning',
            message: '请输入单位名称!'
          })
          return;
        }
        let param = this.queryForm;
        querySysDeptByLike(param).then(
          res => {
            this.tableData = res.data;
          }
        )
      },
      reset() {
        this.queryForm.deptName = '';
        this.querySysDeptsBy();
      },
      /* grid查询数据 */
      querySysDeptsBy() {
        const _this = this
        listDept()
          .then(function (response) {
            // let value=  dealStatus(response.data);
            // console.info(value);
            _this.tableData = response.data;
            _this.loading = false
          })
      },
      StepsListRowClick(row) {
        console.log(row)
        this.$refs.tableGrid.clearSelection()
        if (this.gridCheck.length === 0) {
          this.$refs.tableGrid.toggleRowSelection(row)
          return;
        }
        this.$refs.tableGrid.toggleRowSelection(row)
      },
      // 复选框事件
      handleSelectionChange(val) {
        this.gridCheck = val
        //  console.info(this.gridCheck[0].status);
        // for(var i=0;i<this.gridCheck.length;i++){
        //   if (this.gridCheck[i].status=="启用") {
        //     this.gridCheck[i].status=0
        //   } else{
        //     this.gridCheck[i].status=1
        //   }
        // }
        this.deleteCheck = val.map(item => item.id)
      },
      // 导出
      importClick() {
      },
      /* 新增弹窗 */
      addFormWindow() {
        this.addFormVisible = true
      },

      // 多选删除
      handleDelete() {
        if (this.gridCheck.length == 0) {
          this.$message({
            type: "warning",
            message: "请勾选数据"
          })
          return;
        }
        let flag = true;
        let ids = "";
        let message = "";
        for (let n in this.gridCheck) {
          let node = this.$refs.tree4.getNode(this.gridCheck[n].id);
          console.info(node)
          if (node != null && node.childNodes != null && node.childNodes.length > 0) {
            flag = false;
            message = message + this.gridCheck[n].deptName + "、";
          }
          ids = ids + this.gridCheck[n].id + ",";
        }

        if (flag) {
          this.$confirm('此操作将永久删除该部门节点, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
            .then(() => {
              deleteDeptByIds({otherParam: ids,jsonStr:"true"}).then(response => {
                if (response.code == 200) {
                  this.$message({
                    type: "success",
                    message: "删除成功"
                  })
                  this.pageNum = 1;
                  this.pageParam.pageNum = 1;
                  // this.pageParam.parentId = "000";
                  if (this.checkTreeData.id != "" && this.checkTreeData.id != undefined) {
                    this.pageParam.parentId = this.checkTreeData.id;
                  } else {
                    this.pageParam.parentId = "000";
                  }
                  this.pageParam.deptType = this.deptType;
                  this.getListDeptByParentId(this.pageParam)
                  this.initLeftTreeData()
                } else {
                  return false;
                }
              })
            })
        } else {
          message = message.substr(0, message.length - 1);
          this.$message({
            type: "warning",
            message: message + "的单位有下级单位,无法删除"
          })
        }
      },
    }
  }
</script>
<style lang="scss" scoped>
  .el-dialog__body {
    height: 500px;
    display: block;
  }

  .app-wait {
    overflow: auto;
    height: calc(100vh - 86px);;
  }

  .el-input__inner {
    width: 300px;
  }

  .el-form-item__content {
    width: 300px;
  }

  .el-input {
    width: 300px;
  }

  .inputW {
    width: 300px;
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

  .block {
    float: right;
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

  #inputClear {
    /deep/ .el-input__suffix {
      top: 11px;
    }
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
</style>

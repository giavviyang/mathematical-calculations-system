<template>
  <div class="app-wait">
    <el-row>
      <el-col :span="24" style="height: 100%;">
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
                :highlight-current="true"/>
            </el-scrollbar>
          </div>
          <div class="shrinkageDiv" @click="divClick()">
            <div :class="picClassname"></div>
          </div>
        </el-col>

        <el-col :span="20" style="height: calc(100vh - 86px)" :style="rightStyleName">
          <el-col :span="24" ref="searchForm" style="padding: 10px 0;">
              <el-button type="primary" size="mini" icon="el-icon-plus" @click="addSysDeptBygd">新增</el-button>
              <el-button type="primary" size="mini" icon="el-icon-edit" @click="upData">编辑</el-button>
              <el-button type="primary" size="mini" icon="el-icon-delete" @click="handleDelete">删除</el-button>
              <el-button type="primary" size="mini" icon="el-icon-sort" @click="orderDeptNumButton">排序</el-button>
              <el-button type="primary" size="mini" icon="el-icon-download" @click="exportExcel">导出</el-button>
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
                             v-if="item.isCol" :prop="item.prop" :label="item.label"/>
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
        :visible.sync="addOrEditW">

        <div style="">
          <el-row>
            <el-col :span="24" style="width:100%">
              <el-form
                ref="deptForm"
                :rules="rules"
                :model="deptForm"
                style="margin-left: 10%;margin-right: 10%"
                label-width="120px">
                <el-col  :span="12">
                    <el-form-item prop="deptName" :label=formTitle.name>
                      <el-input size="mini" v-model="deptForm.deptName" placeholder="请输入名称" style="width:100%"/>
                    </el-form-item>
                </el-col>
                <el-col  :span="12">
                  <el-form-item prop="deptNumber" :label=formTitle.number>
                    <el-input size="mini" v-model="deptForm.deptNumber" placeholder="请输入编号" style="width:100%"/>
                  </el-form-item>
                </el-col>
                <el-col  :span="12">
                  <el-form-item prop="address" label="地址" v-if="this.titleType==''">
                    <el-input size="mini" v-model="deptForm.address"  placeholder="请输入地址" style="width:100%"/>
                  </el-form-item>
                </el-col>
                <el-col  :span="12">
                  <el-form-item prop="leader" label="联系人">
                    <el-input size="mini" v-model="deptForm.leader" placeholder="请输入联系人" style="width:100%"/>
                  </el-form-item>
                </el-col>
                <el-col  :span="12">
                  <el-form-item prop="phone" label="联系方式">
                    <el-input size="mini" v-model="deptForm.phone" placeholder="请输入联系方式" style="width:100%"/>
                  </el-form-item>
                </el-col>
                <el-col  :span="24">
                  <el-form-item prop="remark" label="备注">
                    <el-input size="mini" v-model="deptForm.remark" placeholder="请输入备注" type="textarea" style="width:100%"/>
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
        title="排序"
        width="50%"
        :destroy-on-close="true"
        class=" dialog-style dialog-basic"
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
            <el-table-column show-overflow-tooltip align="center" v-for="(item, index) in deptColomn" :formatter="item.formatter" :key="index"
                             :v-if="item.isCol" :prop="item.prop" :label="item.label"/>
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
    deleteDeptByIds, querySysDeptByLike, dealStatus, getDeptTree,getDeptTreeByUserSchool
  } from '@/api/system/dept'
   import {prompt} from '@/components/windoprompt/prompt'
  import {downLoad, downLoadPost} from '@/downLoad/filedownload'

  export default {
    data() {
      return {
        formTitle:{
          nodeType:'',
          title:'',
          name:'',
          number:'',
        },
        OrderCheckData: [],
        titleType: '',
        treeHeight: "height:500px",
        orderDeptNum: false,
        pageParam: {
          pageNum:1,
          pageSize:20
        },
        tableHeight: "73%",
        rules: {
          deptName: [
            {required: true, message: '请输入学校名称', trigger: 'blur'},
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
            {required: true, message: '请输入名称', trigger: 'blur'},
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
        searchForm:"searchForm",
        searchRowHeight: '',
        // 总页数
        total: 0,
        tableData1: [],
        pageNum: 1,
        pageSize: 20,
        loading: true,
        addOrEditW: false,
        checkTreeData: {},
        title: "",
        deptForm: {},
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
        updateForm: '',
        formLabelWidth: '120px',
        showSearchBox: false,
        queryForm: {
          deptName: ''
        },
        col: [],
        deptColomn: [
          {
            label: "学校名称", prop: 'deptName', formatter: function (row, column, cellValue) {
              return cellValue
            }, isCol: true
          },
          {
            label: "学校编号", prop: 'deptNumber', formatter: function (row, column, cellValue) {
              return cellValue
            }, isCol: true
          },
          {
            label: '地址', prop: 'address', formatter: function (row, column, cellValue) {
              return cellValue
            }, isCol: true
          },
          {
            label: '联系人', prop: 'leader', formatter: function (row, column, cellValue) {
              return cellValue
            }, isCol: true
          },
          {
            label: '联系方式', prop: 'phone', formatter: function (row, column, cellValue) {
              return cellValue
            }, isCol: true
          },
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
          width: '84%', padding: ' 10px', height: 'calc(100vh - 86px)'
        },
        leftStyle: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        leftStyleName: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        rightStyleName: {
          width: '84%', padding: ' 10px', height: 'calc(100vh - 86px)'
        },
        leftStyle1: {
          width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        rightStyle1: {
          width: '99%', padding: ' 10px', height: 'calc(100vh - 86px)'
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
    },
    methods: {
      //自定义序号
      customIndex(index){
        return (this.pageNum-1)*(this.pageSize)+(index+1);
      },
      exportExcel() {
        if (this.gridCheck.length == 0) {
          this.$confirm('是否导出搜索后的机构数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            this.pageParam.pageNum = 1;
            this.pageParam.pageSize = 9999999;
            if (this.pageParam.parentId == "" || this.pageParam.parentId == undefined) {
              this.pageParam.parentId = "000";
              this.pageParam.deptType = "0";
            }
            getListDept(this.pageParam).then(response => {
              if (response.rows.length == 0) {
                this.$message.error("无数据,导出失败");
                return;
              }
              let row = {
                row: response.rows,
                status: 5
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
              status: 5
            }
            downLoadPost("/export/excel/exportClick", row)
          })

        }


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

            let param = {pageSize: this.pageSize, pageNum: this.pageNum, parentId: "000", deptType: this.deptType};
            if (this.checkTreeData.id != "" && this.checkTreeData.id != undefined) {
              param.parentId = this.checkTreeData.id;
            }
            this.getListDeptByParentId(param)
            this.orderDeptNum = false;
          } else {
            this.$message({
              type: "error",
              message: "排序失败"
            })
          }
        })

      },
      orderDeptNumButton() {
        if (this.tableData.length > 1) {
          this.tableData1 = JSON.parse(JSON.stringify(this.tableData));
          this.orderDeptNum = true;
          console.log(this.orderDeptNum)
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

      initLeftTreeData() {
        getDeptTreeByUserSchool().then(response => {
          this.data2 = response.data;
          if (this.data2) {
            this.data2.forEach(item => {
              if (item.children) {
                item.children.forEach(childrenItem => {
                  childrenItem.children = []
                })
              }
            })
          }
        })

      },
      initForm(){
        this.deptForm={
          deptName:"",
          deptNumber:"",
          status:"0",
          remark:"",
          deptType:this.deptType,
          id:"",
        }
      },
      setTitle(){
        if (this.titleType==''){
          this.deptColomn[0].label="学校名称";
          this.deptColomn[1].label="学校编号";
          this.formTitle.nodeType = "学校"
          this.formTitle.name="学校名称";
          this.formTitle.number="学校编号";
        }else if(this.titleType==0){
          this.deptColomn[0].label="年级名称";
          this.deptColomn[1].label="年级编号";
          this.deptColomn[2].isCol=false;
          this.formTitle.nodeType = "年级"
          this.formTitle.name="年级名称";
          this.formTitle.number="年级编号";
        }else if (this.titleType==1){
          this.deptColomn[0].label="班级名称";
          this.deptColomn[1].label="班级编号";
          this.deptColomn[2].isCol=false;
          this.formTitle.nodeType = "班级"
          this.formTitle.name="班级名称";
          this.formTitle.number="班级编号";
        }
        // this.$refs.tableGrid.doLayout();
        // this.$refs.tableGrid1.doLayout();
      },
      //新增部门
      addSysDeptBygd() {
        if (this.checkTreeData.nodeType=="2"){
          this.$message.warning("班级节点下无法新增节点")
        }else{
          this.initForm()
          this.setTitle();
          if (this.data2.length == 0) {
            this.deptForm.parentId = "000";
            this.title = '新增学校信息';
            this.addOrEditW = true;
          } else {
            this.deptForm.parentId = this.checkTreeData.id;
            this.title = '新增'+this.formTitle.nodeType+'信息';
            this.addOrEditW = true;
          }
          this.deptForm.ancestors = this.checkTreeData.ancestors == null ? "000," : this.checkTreeData.ancestors;
        }
        },
      getListDeptByParentId(param) {
        getListDept(param).then(response => {
          this.total = response.total;
          this.tableData = response.rows;
          this.setTitle()
        })
      },
      //编辑部门
      upData() {
        if (this.gridCheck.length != 1) {
          this.$message({
            type: "warning",
            message: "请勾选一条数据"
          })
        } else {
          this.setTitle();
          this.deptForm = this.gridCheck[0];
          this.title = '编辑'+this.formTitle.nodeType+'信息';
          this.deptForm.deptType = this.deptType
          this.addOrEditW = true;
        }

      },
      //新增或编辑部门时保存干部档案
      saveDeptForm(form) {
        let _this = this;
        this.$refs[form].validate((valid) => {
          if (_this.checkTreeData.id != "" && _this.checkTreeData.id != undefined) {
            _this.deptForm.parentId = _this.checkTreeData.id;
            if ( _this.checkTreeData.nodeType==="0"){
              _this.deptForm.nodeType = "1";
            }else{
              _this.deptForm.nodeType = "2";
            }
          } else {
            _this.deptForm.nodeType = "0";
          }
          if (valid) {
            let title = this.title.slice(0,2);
            if (title == "编辑") {
              updateDept(_this.deptForm)
                .then(function (response) {
                  prompt("保存",0)
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
                    prompt("新增",0)
                    _this.pageNum = 1;
                    _this.pageParam.pageNum = 1;
                    if (_this.checkTreeData.id != "" && _this.checkTreeData.id != undefined) {
                      _this.pageParam.parentId = _this.checkTreeData.id;
                      if ( _this.checkTreeData.nodeType==="0"){
                        _this.pageParam.nodeType = "1";
                      }else{
                        _this.pageParam.nodeType = "2";
                      }
                    } else {
                      _this.pageParam.parentId = "000";
                      _this.pageParam.nodeType = "0";
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
          } else {
            return false;
          }
        })

      },

      //点击左侧树节点
      treeCleck(val) {
        this.checkTreeData = val;
        this.titleType = val.nodeType
        console.info(this.checkTreeData)
        this.pageNum = 1;
        this.pageParam.pageNum = 1;
        this.pageParam.parentId = val.id;
        this.pageParam.deptType = this.deptType;
        this.pageParam.pageNum = this.pageNum;
        this.pageParam.pageSize = this.pageSize
        this.getListDeptByParentId(this.pageParam)
      },

      filterNode(value, data) {
        if (!value) return true;
        return data.deptName.indexOf(value) !== -1;
      },
      //搜索部门得方法
      searchDept() {
        let name = this.queryForm.deptName
        if (name == "") {
          this.$message({
            type: 'warning',
            message: '请输入学校名称!'
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
      StepsListRowClick1(row) {
        console.log(row)
        this.$refs.tableGrid1.clearSelection()
        if (this.tableData1.length === 0) {
          this.$refs.tableGrid1.toggleRowSelection(row)
          return;
        }
        this.$refs.tableGrid1.toggleRowSelection(row)
      },
      // 复选框事件
      handleSelectionChange(val) {
        this.gridCheck = val
        this.deleteCheck = val.map(item => item.id)
      },
      // 复选框事件
      handleSelectionChange1(val) {
        this.OrderCheckData = val

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
      /* 新增弹窗 */
      addFormWindow() {
        console.log(this.checkTreeData)
        this.addFormVisible = true
      },
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
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
          this.$confirm('此操作将永久删除该节点, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
            .then(() => {
              deleteDeptByIds({otherParam: ids,jsonStr:"false"}).then(response => {
                if (response.code == 200) {
                  this.$message({
                    type: "success",
                    message: "删除成功"
                  })
                  this.pageNum = 1;
                  this.pageParam.pageNum = 1;
                  if (this.checkTreeData.id != "" && this.checkTreeData.id != undefined) {
                    this.pageParam.parentId = this.checkTreeData.id;
                  } else {
                    this.pageParam.parentId = "000";
                  }
                  this.pageParam.deptType = this.deptType;
                  this.getListDeptByParentId(this.pageParam)
                  this.initLeftTreeData()
                } else {
                  this.$message({
                    type: "error",
                    message: "删除失败"
                  })
                }
              })
            })
        } else {
          message = message.substr(0, message.length - 1);
          this.$message({
            type: "warning",
            message: message + "节点有下级节点无法删除"
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

  .el-dialog__body {
    height: 500px;
    display: block;
  }

  .app-wait {
    /*overflow: auto;*/
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
</style>

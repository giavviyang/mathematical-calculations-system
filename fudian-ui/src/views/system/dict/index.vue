<template>
  <div class="app-daily">
    <el-row>
      <el-col :span="24" style="height:100%">
        <el-col :span="4" :style="leftStyleName" style="height: calc(100vh - 86px)">
          <div id="inputClear">
            <el-input
              placeholder="请输入内容"
              v-if="treeShow"
              v-model="filterText"
              clearable
              class="inp-select"
              style="width:90%;padding:20px 0 0 20px"/>
          </div>
          <!-- 树结构 -->
          <div class="tree tree-style">
            <el-scrollbar style="height:100%">
              <el-tree
                ref="tree2"
                :data="data2"
                :props="defaultProps"
                :load="loadNode"
                lazy
                icon-class="el-icon-folder-opened"
                @node-click="handleNodeClick"
                empty-text=" "
                :highlight-current="true"
                :expand-on-click-node="false"
                :filter-node-method="filterNode"
              />
              <!--<span class="span-ell" slot-scope={node,data}>-->
              <!--<span :title="data.dictLabel">{{data.dictLabel}}</span>-->
              <!--</span>-->


              <!--</div>-->
            </el-scrollbar>
          </div>
          <div class="shrinkageDiv" @click="divClick()">
            <div :class="picClassname"></div>
          </div>
        </el-col>
        <el-col :span="20" style="height: calc(100vh - 86px)" :style="rightStyleName">
          <el-row :gutter="10" ref="searchForm" style="margin: 0">
            <el-form
              :inline="true"
              ref="formSearch"
              v-hasPermi="['system:dict:search']"
              :model="formSearch"
              style="background:rgba(204, 204, 204, 0.18)"
              class="search-form"
              @submit.native.prevent>
              <el-form-item label="字典名称" @keyup.enter.native="onEnterSearch" prop="dictLabel">
                <el-input v-model="formSearch.dictLabel" clearable placeholder="请输入内容"
                          size="mini"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" size="mini" icon="el-icon-search" @click="depement()">搜索</el-button>
              </el-form-item>
            </el-form>
          </el-row>
          <el-col :span="24" style="padding: 10px 0;">
            <el-button type="primary" size="mini" icon="el-icon-plus" @click="handleAdd"
                       v-hasPermi="['system:dict:add']">新增
            </el-button>
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate"
                       v-hasPermi="['system:dict:modify']">编辑
            </el-button>
            <el-button type="primary" size="mini" icon="el-icon-delete" @click="handleDelete"
                       v-hasPermi="['system:dict:delete']">删除
            </el-button>
            <el-button type="primary" size="mini" icon="el-icon-upload2" @click="handleExport"
                       v-hasPermi="['system:dict:export']">导出
            </el-button>
          </el-col>
          <el-table
            ref="tableData"
            align="left"
            :data="tableData"
            class="table-style"
            size="mini"
            :height="tableHeight "
            tooltip-effect="light"
            border
            empty-text=" "
            :header-cell-style="headClass"
            stripe
            @row-click="StepsListRowClick"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" :resizable="false"></el-table-column>
            <el-table-column type="index" label="序号" width="50px" align="center" show-overflow-tooltip :index="customIndex"/>

            <el-table-column label="字典名称" align="center" prop="dictLabel"
                             show-overflow-tooltip/>
            <el-table-column show-overflow-tooltip label="字典状态" align="center" prop="status"
                             :formatter="formateSta"/>
            <el-table-column label="字典类型" align="center" prop="dictType"
                             show-overflow-tooltip/>
            <el-table-column show-overflow-tooltip label="字典值" align="center" prop="dictValue"
            />

          </el-table>
          <div class="block" style="float: right;margin-top: 5px">
            <el-pagination
              :page-sizes="[20, 50, 100, 200]"
              :page-size="20"
              :current-page="pageNum"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-col>
      </el-col>
    </el-row>


    <!--  新增编辑页面  -->
    <el-dialog
      :title="title"
      :destroy-on-close="true"
      :append-to-body="true"
      :close-on-click-modal="false"
      width="60%"
      class=" dialog-style dialog-basic"
      :visible.sync="addType">

      <div style="">
        <el-form
          ref="addTypeForm"
          :model="addTypeForm"
          style="margin-left: 10%;margin-right: 10%"
          :rules="rules"
          :inline="true"
          label-width="120">
          <el-form-item label="字典名称" :label-width="formLabelWidth" prop="dictName">
            <el-input size="mini" v-model="addTypeForm.dictName" clearable placeholder="请输入字典名称" style="width:150px"/>
          </el-form-item>
          <el-form-item label="字典类型" :label-width="formLabelWidth" prop="dictType">
            <el-input size="mini" :disabled="dis" v-model="addTypeForm.dictType" clearable placeholder="请输入字典类型"
                      style="width:150px"/>
          </el-form-item>

          <!--          调用子组件, v-bind:transForm 父组件传参数到子组件，@child-event子组件传回参数到父组件方法-->
          <!--          <tr_ee v-bind:transForm="transForm" @child-event="backEvevt"></tr_ee>-->
          <el-form-item label="状态" :label-width="formLabelWidth" prop="status">
            <el-radio-group v-model="addTypeForm.status" style="width: 150px;">
              <el-radio
                v-for="dict in statusOptions"
                :key="dict.dictValue1"
                :label="dict.dictValue1"
              >{{dict.dictLabel1}}
              </el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item prop="remark" :label-width="formLabelWidth" label="备注">
            <el-input size="mini" v-model="addTypeForm.remark" clearable placeholder="请输入备注" type="textarea"
                      style="width:150px;"/>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="cancel">取 消</el-button>
        <el-button size="mini" type="primary" @click="submitTypeForm('addTypeForm')">保 存</el-button>
      </div>
    </el-dialog>


    <el-dialog
      :destroy-on-close="true"
      :append-to-body="true"
      :close-on-click-modal="false"
      width="60%"
      :title="title"
      class=" dialog-style dialog-basic"
      :visible.sync="addData">

      <div style="">
        <el-form ref="addDataForm" :model="addDataForm" style="margin-left: 10%;margin-right: 10%" :rules="rules2"
                 :inline="true" label-width="120px">
          <el-form-item :label-width="formLabelWidth" label="字典名称" prop="dictLabel">
            <el-input size="mini" v-model="addDataForm.dictLabel" clearable placeholder="请输入字典名称" style="width:150px"/>
          </el-form-item>
          <el-form-item :label-width="formLabelWidth" label="字典值" prop="dictValue">
            <el-input size="mini" v-model="addDataForm.dictValue" clearable placeholder="请输入字典名称" style="width:150px"/>
          </el-form-item>
          <el-form-item :label-width="formLabelWidth" label="字典类型" prop="dictType">
            <el-input size="mini" :disabled="true" v-model="addDataForm.dictType" clearable placeholder="请输入字典类型"
                      style="width:150px"/>
          </el-form-item>
          <el-form-item :label-width="formLabelWidth" label="状态" prop="status">
            <el-radio-group v-model="addDataForm.status">
              <el-radio
                v-for="dict in statusOptions"
                :key="dict.dictLabel1"
                :label="dict.dictValue1"
              >{{dict.dictLabel1}}
              </el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item :label-width="formLabelWidth" label="默认" prop="isDefault">
            <el-radio-group v-model="addDataForm.isDefault">
              <el-radio
                v-for="dict in setDefultOptions"
                :key="dict.dictLabel2"
                :label="dict.dictValue2"
              >{{dict.dictLabel2}}
              </el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label-width="165px" prop="remark" label="备注">
            <el-input size="mini" v-model="addDataForm.remark" clearable placeholder="请输入备注" type="textarea"
                      style="width:150px"/>
          </el-form-item>
          <!--          <el-form-item prop="remark" label="备注">-->
          <!--            <el-input size="mini" v-model="addDataForm.remark"  placeholder="请输入备注"  type="textarea" style="width:200px"/>-->
          <!--          </el-form-item>-->
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="addData=false">取 消</el-button>
        <el-button size="mini" type="primary" @click="submitDateForm('addDataForm')">保 存</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改字典类型对话框 -->


    <!-- 添加或修改参数配置对话框 -->


  </div>
</template>
<script>
  import {
    getData,
    addDictData,
    delData,
    updateData,
    getLeftTreeNode,
    listType,
    getType,
    delType,
    addType,
    updateType,
    exportType,
    clickTreeNode,
    queryDictTree,
    addNode,
    addOrUpdateNodeType,
    removeNodeById,
    addOrUpdateNodeData,
    queryByLabel,
    getTreeByDictType
  } from "@/api/system/dict/type";
  import {downLoad, downLoadPost} from '@/downLoad/filedownload'

  //引用子组件
  // import tr_ee from '@/components/DictTree/dictDate'
  const params = {
    pageNum: 1,
    pageSize: 20
  }
  export default {
    //注册子组件
    // components: {
    //   "tr_ee":tr_ee
    // },
    name: "dict",
    watch: {
      filterText(val) {
        this.$refs.tree2.filter(val);
      },
      tableData: {
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(() => {
            _this.$refs.tableData.doLayout();
          })
        },
        deep: true,
        // immediate: true
      },
    },
    data() {
      return {
        //父组件往子组件传参数
        // transForm:{transValue:'12'},
        node_had: '',
        resolve_had: '',
        pageNum: 1,
        pageSize: 20,
        dis: "",//字典类型设置值
        dia: "",//字典类型设置值
        flag: "",
        deleteType: "",
        checkboxDatas: [],
        nodeTrans: {
          dictTypeid: '',
          dictType: '',
          dictValueid: '',
        },
        formLabelWidth: '120px',
        clickParam: {
          dictTypeid: '',
          dictType: '',
          dictValueid: '',
          pageSize: 20,
          pageNum: 1
        },
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        title: "新增",
        addType: false,
        addData: false,
        addTypeForm: {
          dictName: '',
          dictType: '',
          status: '0',
          remark: ''
        },
        addDataForm: {
          dictValue: "",
          dictLabel: "",
          isDefault: 'Y',
          status: '0',
          remark: ''
        },
        rules: {
          dictName: [
            {required: true, message: "请输入字典名称", trigger: "blur"},
            {min: 0, max: 50, message: '内容不得超过50个字符', trigger: 'blur'}
          ],
          dictType: [
            {required: true, message: "请输入字典类型", trigger: "blur"},
            {min: 0, max: 50, message: '内容不得超过50个字符', trigger: 'blur'}
          ],
          remark: [
            {min: 0, max: 200, message: '内容不得超过200个字符', trigger: 'blur'}
          ]
        },
        rules2: {
          dictLabel: [
            {required: true, message: "请输入字典标签", trigger: "blur"},
            {min: 0, max: 50, message: '内容不得超过50个字符', trigger: 'blur'}
          ],
          dictValue: [
            {required: true, message: "请输入字典数据", trigger: "blur"},
            {min: 0, max: 50, message: '内容不得超过50个字符', trigger: 'blur'}
          ],
          remark: [
            {min: 0, max: 200, message: '内容不得超过200个字符', trigger: 'blur'}
          ]
        },
        // 状态数据字典
        statusOptions: [{
          "dictValue1": "0",
          "dictLabel1": "正常"
        }, {
          "dictValue1": "1",
          "dictLabel1": "停用"
        }],
        setDefultOptions: [{
          "dictValue2": "Y",
          "dictLabel2": "是"
        }, {
          "dictValue2": "N",
          "dictLabel2": "否"
        }],


        // showSearchBox: true,//搜索框显示
        dialogVisible: false, //  对话框显示      高级检索
        dialogVisible1: false, //基本信息
        dialogVisible2: false, //排序
        dialogVisible3: false, //查缺
        dialogVisible4: false, //职务变动表
        dialogVisible5: false, //状态调整
        dialogVisible6: false, //部门调整
        innerVisible: false,
        checkList: [],           //勾选框选中数组
        searchForm: 'searchForm',
        tableHeight: '73%',
        searchRowHeight: '',
        //基本信息表单
        // 搜索表单数据
        formSearch: {
          dictLabel: '',
          pageSize: 1,
          pageNum: '',
          queryType: ''
        },
        //排序
        tableDataSort: [{}],
        //档案查缺表单
        formlack: {
          a0101: '',
          dept_id: '',
          //干部工号无
          a0151: '',
          state: '',
          e01z111a: '',
          redio: '不包含',
        },
        //状态调整表单
        formState: {
          state: 'true'
        },
        //部门树结构数据
        filterText: '',
        data2: [{
          dictLabel: "字典管理",
          dictType: "xxx_xxx",
          dictTypeid: '000',
          children: []
        }],

        defaultProps: {
          label: "dictLabel",
          children: "children"
        },
        tableData: [],
        selectList: [], //表格选中的行数
        showdaliog: 'true',
        //职务变动维护-姓名
        userName: '',
        //分页
        currentPage1: 5,
        currentPage2: 5,
        currentPage3: 5,
        currentPage4: 4,

        // 当前节点选中的type
        dictType: "xxx_xxx",
        // 分页总数
        total: 0,
        //弹出层标题
        //是否显示弹出层
        open: false,
        childrenData: [],
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
      };
    },
    mounted() {
      this.$nextTick(() => {
        this.searchRowHeight = this.$refs[this.searchForm].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + "px"
      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchForm].$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + "px"
        })();
      };
    },
    created() {
      this.getLeftTreeNode();
      this.getGrid();
    },
    methods: {
      //自定义序号
      customIndex(index){
        return (this.pageNum-1)*(this.pageSize)+(index+1);
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

      getGrid() {
        var param = {}
        if (this.nodeTrans.dictTypeid == "" && this.nodeTrans.dictValueid == "") {
          param.dictTypeid = "000"
          param.dictType = "xxx_xxx"
        } else if (this.nodeTrans.dictTypeid != "") {
          param.dictTypeid = this.nodeTrans.dictTypeid
          param.dictType = this.nodeTrans.dictType
          if (this.formSearch.dictLabel != "" && this.formSearch.dictLabel != undefined) {
            param.dictLabel = this.formSearch.dictLabel;
          } else {
            param.dictLabel = "";
          }
        }

        param.pageNum = this.pageNum;
        param.pageSize = this.pageSize
        clickTreeNode(param)
          .then(response => {
            this.tableData = response.rows
            this.total = response.total
          });
      },

      //树结构懒加载
      loadNode(node, resolve) {
        if (node.level === 0) {
          this.node_had = node;
          this.resolve_had = resolve;
          return resolve(node.data);
        }
        if (node.level > 0 && node.level < 2) {
          return resolve(node.data.children);
        }
        if (node.level > 1) {
          var Tid = "";
          if (node.data.dictTypeid == undefined) {
            Tid = node.data.dictValueid
          } else {
            Tid = node.data.dictTypeid;
          }
          queryDictTree({id: Tid})
            .then(response => {
              if (response.code == 200) {
                return resolve(response.data);
                // node.childNodes=response.data;
              }
            });
        }
      },
      formateSta(row, col) {
        if (row[col.property] != null && row[col.property] != '') {
          if (row[col.property] == 0) {
            return "启用"
          } else if (row[col.property]) {
            return "禁止"
          }
        }
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.dictLabel.indexOf(value) !== -1;
      },
      // 树搜索
      filterSearch() {
        let filterText = this.filterText;
        let childrenData = this.childrenData;
        let arrayData = [];
        for (let i = 0; i < childrenData.length; i++) {
          if (childrenData[i].dictLabel.indexOf(filterText) != -1) {
            arrayData.push(childrenData[i]);
          }
        }
        this.data2[0].children = arrayData;
      },
      // 获取左侧树
      getLeftTreeNode() {
        queryDictTree().then(response => {
          let _this = this;
          _this.data2[0].children = []
          _this.data2[0].children = response.data;
        })
      },

      // 节点点击 获取表格数据
      handleNodeClick(data, node, component) {
        if (data.dictTypeid == undefined) {
          this.nodeTrans.dictTypeid = data.parentId
          this.nodeTrans.dictValueid = data.dictValueid
          this.nodeTrans.dictType = data.dictType
        } else {
          this.nodeTrans.dictTypeid = data.dictTypeid
          this.nodeTrans.dictValueid = ""
          this.nodeTrans.dictType = data.dictType
        }
        if (node.childNodes.length > 0) {
          // this.clickParam.dictTypeid = data.dictTypeid
          // this.clickParam.dictType = data.dictType
          // this.clickParam.dictValueid = data.dictValueid
          params.dictTypeid = data.dictTypeid
          params.dictType = data.dictType
          params.dictValueid = data.dictValueid
          if (this.formSearch.dictLabel != "" && this.formSearch.dictLabel != undefined) {
            params.dictLabel = this.formSearch.dictLabel;
          } else {
            params.dictLabel = "";
          }
          clickTreeNode(params).then(response => {
            if (response.rows != null) {
              this.tableData = response.rows;
              this.total = response.total;
            } else {
              this.tableData3 = []
              this.total = response.total;
            }
          });
        } else {
          // this.clickParam.dictTypeid = data.dictTypeid
          // this.clickParam.dictType = data.dictType
          // this.clickParam.dictValueid = data.dictValueid
          params.dictTypeid = data.dictTypeid
          params.dictType = data.dictType
          params.dictValueid = data.dictValueid
          if (this.formSearch.dictLabel != "" && this.formSearch.dictLabel != undefined) {
            params.dictLabel = this.formSearch.dictLabel;
          } else {
            params.dictLabel = "";
          }
          clickTreeNode(params).then(response => {
            if (response.rows != null) {
              this.tableData = response.rows;
              this.total = response.total;
            } else {
              this.tableData3 = []
              this.total = response.total;
            }
          });
        }
      },
      // 取消按钮
      cancel() {
        this.addType = false;
        this.addData = false;
        if (this.nodeTrans.dictType == 'xxx_xxx') {
          this.resetType();
        } else {
          this.resetData();
        }
      },
      // 类型表单重置
      resetType() {
        this.addTypeForm = {
          dictName: "",
          dictType: undefined,
          status: "0",
          remark: undefined
        };
        this.resetForm("addTypeForm");
      },
      resetData() {
        this.addDataForm = {
          dictLable: undefined,
          dictValue: undefined,
          status: "0",
          isDefault: "N",
          dictType: this.dictType,
          remark: undefined
        };
        this.resetForm("addDataForm");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.dateRange = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      handleAdd() {
        this.flag = false;
        if (this.nodeTrans.dictTypeid == "" && this.nodeTrans.dictValueid == "" && this.nodeTrans.dictType == "") {
          this.$message({
            type: 'warning',
            message: '请选中左则字典管理后进行操作!'
          })
          return
        }
        if (this.nodeTrans.dictType == "xxx_xxx" || (this.nodeTrans.dictTypeid == "" && this.nodeTrans.dictValueid == "")) {
          this.dis = false
          this.addTypeForm.dictType = "";
          this.addTypeForm.dictName = "";
          this.addTypeForm.status = "0";
          this.addTypeForm.remark = "";
          // this.resetType();
          this.addType = true;
          this.title = "添加字典类型";
        } else {
          this.addDataForm.dictType = this.nodeTrans.dictType
          this.addDataForm.status = "0";
          this.addDataForm.isDefault = "Y";
          this.addDataForm.dictLabel = "";
          this.addDataForm.dictValue = "";
          this.addDataForm.remark = "";
          // this.resetData();
          this.dia = false;
          this.addData = true;
          this.title = "添加字典数据";
        }
      },
      StepsListRowClick(row) {
        console.log(row)
        this.$refs.tableData.clearSelection()
        if (this.checkboxDatas.length === 0) {
          this.$refs.tableData.toggleRowSelection(row)
          return;
        }
        this.$refs.tableData.toggleRowSelection(row)
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.checkboxDatas = selection
        if (selection.map(item => item.dictTypeid)[0] == undefined) {
          this.deleteType = true;
          this.ids = selection.map(item => item.dictValueid)
        } else {
          this.deleteType = false
          this.ids = selection.map(item => item.dictTypeid)
        }
        // this.single = selection.length != 1
        // this.multiple = !selection.length
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.flag = true;
        if (this.ids.length != 1) {
          this.$message({
            type: 'warning',
            message: '请选中1条数据后进行操作!'
          })
          return
        }
        console.log(this.checkboxDatas[0]);
        if (this.nodeTrans.dictType == "xxx_xxx" || this.nodeTrans.dictTypeid == "" && this.nodeTrans.dictValueid == "") {
          console.log(1)
          this.dis = true
          this.title = "修改字典类型";
          let params = {
            "dictName": this.checkboxDatas[0].dictLabel,
            "dictType": this.checkboxDatas[0].dictType,
            "remark": this.checkboxDatas[0].remark,
            "status": this.checkboxDatas[0].status,
            "dictTypeid": this.checkboxDatas[0].dictTypeid
          }
          // this.addTypeForm.dictLabel = this.checkboxDatas[0].dictLabel;
          // this.addTypeForm.dictType = this.checkboxDatas[0].dictType;
          // this.addTypeForm.remark = this.checkboxDatas[0].remark
          // this.addTypeForm.status = this.checkboxDatas[0].status
          // this.addTypeForm.dictTypeid = this.checkboxDatas[0].dictTypeid
          this.addTypeForm = params;
          this.addType = true;
        } else {
          this.dia = true;
          this.title = "修改添加字典数据";
          let params = {
            "dictLabel": this.checkboxDatas[0].dictLabel,
            "dictType": this.checkboxDatas[0].dictType,
            "remark": this.checkboxDatas[0].remark,
            "status": this.checkboxDatas[0].status,
            "isDefault": this.checkboxDatas[0].isDefault,
            "dictValue": this.checkboxDatas[0].dictValue,
            "dictValueid": this.checkboxDatas[0].dictValueid
          }
          // this.addDataForm.dictValueid = this.checkboxDatas[0].dictValueid
          // this.addDataForm.dictLabel = this.checkboxDatas[0].dictLabel;
          // this.addDataForm.dictType = this.checkboxDatas[0].dictType;
          // this.addDataForm.remark = this.checkboxDatas[0].remark
          // this.addDataForm.status = this.checkboxDatas[0].status
          // this.addDataForm.isDefault = this.checkboxDatas[0].isDefault
          this.addDataForm = params;
          this.addData = true;
        }
        // if (this.dictType == "xxx_xxx") {
        //   const dictId = row.dictTypeid || this.ids
        //   getType(dictId).then(response => {
        //     this.addTypeForm = response.data;
        //     this.addType = true;
        //     this.title = "修改字典类型";
        //   });
        // } else {
        //   const dictId = row.dictValueid || this.ids
        //   getData(dictId).then(response => {
        //     this.addDataForm = response.data;
        //     this.addData = true;
        //     this.title = "修改字典数据";
        //   });
        // }
      },

      /**新增或修改-字典数据提交按钮 */
      submitDateForm(form) {
        let _this = this;
        this.$refs[form].validate((valid) => {
          if (valid) {
            if (_this.nodeTrans.dictValueid == "") {
              _this.addDataForm.parentId = _this.nodeTrans.dictTypeid
            } else {
              _this.addDataForm.parentId = _this.nodeTrans.dictValueid;
            }
            // true 修改
            if (_this.dia) {
              _this.addDataForm.jsonStr = "true"
              addOrUpdateNodeData(_this.addDataForm)
                .then(response => {
                  if (response.code == 200) {
                    _this.addData = false;
                    _this.node_had.childNodes = [];
                    _this.loadNode(_this.node_had, _this.resolve_had);
                    _this.getLeftTreeNode();
                    _this.getGrid();
                    _this.msgSuccess(response.msg);
                  } else {
                    _this.msgError(response.msg);
                  }
                });
            } else {
              _this.addDataForm.jsonStr = "false"
              addOrUpdateNodeData(_this.addDataForm)
                .then(response => {
                  if (response.code == 200) {
                    _this.addData = false;
                    _this.node_had.childNodes = [];
                    _this.loadNode(_this.node_had, _this.resolve_had);
                    _this.getLeftTreeNode();
                    _this.getGrid();
                    _this.msgSuccess(response.msg);
                  } else {
                    _this.msgError(response.msg);
                  }
                });
            }
          } else {
            return false;
          }
        })

      },
      /** 新增/修改-字典类型提交按钮 */
      submitTypeForm(form) {
        let _this = this;
        this.$refs[form].validate((valid) => {
          if (valid) {
//flag==true说明点击的是修改按钮
            //flag==false说明点击的是新增按钮
            if (_this.flag) {
              _this.addTypeForm.jsonStr = "true";
              addOrUpdateNodeType(_this.addTypeForm)
                .then(response => {
                  if (response.code === 200) {
                    _this.addType = false;
                    _this.addTypeForm.dictType = "";
                    _this.addTypeForm.dictName = "";
                    _this.addTypeForm.status = "0";
                    _this.addTypeForm.remark = "";
                    _this.node_had.childNodes = [];
                    _this.loadNode(_this.node_had, _this.resolve_had);
                    _this.getLeftTreeNode();
                    _this.getGrid();
                    _this.msgSuccess("修改成功");
                  } else {
                    _this.addType = false;
                    _this.msgError(response.msg);
                  }
                });
            } else {
              _this.addTypeForm.jsonStr = "false";
              addOrUpdateNodeType(_this.addTypeForm)
                .then(response => {
                  if (response.code === 200) {
                    _this.addType = false;
                    _this.addDataForm.status = "0";
                    _this.addDataForm.isDefault = "Y";
                    _this.addDataForm.dictLabel = "";
                    _this.addDataForm.dictValue = "";
                    _this.addDataForm.remark = "";
                    // _this.getLeftTreeNode();
                    _this.node_had.childNodes = [];
                    _this.loadNode(_this.node_had, _this.resolve_had);
                    _this.getLeftTreeNode();
                    _this.getGrid()
                    _this.msgSuccess("操作成功");
                  } else {
                    _this.addType = false;
                    _this.msgError(response.msg);
                  }
                });
            }
          } else {
            return false;
          }
        })


      },
      /** 删除按钮操作 */
      handleDelete() {
        if (this.ids.length == 0) {
          this.$message({
            type: 'warning',
            message: '请选中数据后进行操作'
          })
          return
        }
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          // center: true
        }).then(() => {
          removeNodeById({ids: this.ids.toString(), type: this.deleteType})
            .then(response => {
              if (response.code == 200) {
                this.$message({type: 'success', message: '删除成功'})
                this.node_had.childNodes = [];
                this.loadNode(this.node_had, this.resolve_had);
                this.getLeftTreeNode();
                this.getGrid();
              } else {
                this.$message({type: 'warning', message: '删除失败'})
                this.node_had.childNodes = [];
                this.loadNode(this.node_had, this.resolve_had);
                this.getLeftTreeNode();
                this.getGrid();
              }
            })
        })
      },
      onEnterSearch() {
        this.depement();
      },
      depement() {
        this.pageNum = 1;
        if (this.nodeTrans.dictValueid == "" && this.nodeTrans.dictTypeid == "" || this.nodeTrans.dictTypeid == "000") {
          //说明查询sysdict
          // this.formSearch.pageNum = 1
          // this.formSearch.pageSize = 20;
          // this.formSearch.queryType = true
          params.pageNum = 1;
          params.queryType = true;
          params.dictLabel = this.formSearch.dictLabel;
          params.dictTypeid = this.nodeTrans.dictTypeid;
          queryByLabel(params)
            .then(response => {
              this.tableData = response.rows;
              this.total = response.total
            });
        } else {
          params.pageNum = 1;
          params.queryType = true;
          params.dictLabel = this.formSearch.dictLabel;
          // this.formSearch.pageNum = 1
          // this.formSearch.pageSize = 20
          this.formSearch.queryType = false
          // this.formSearch.dictValueid = this.nodeTrans.dictValueid
          params.dictValueid = this.nodeTrans.dictValueid
          // this.formSearch.dictTypeid = this.nodeTrans.dictTypeid
          params.dictTypeid = this.nodeTrans.dictTypeid
          // queryByLabel(params)
          //   .then(response => {
          //     this.tableData = response.rows;
          //     this.total = response.total
          //   });
          clickTreeNode(params).then(response => {
            if (response.rows != null) {
              this.tableData = response.rows;
              this.total = response.total;
            } else {
              this.tableData3 = []
              this.total = response.total;
            }
          });
        }
      },
      handleExport() {
        if (this.checkboxDatas.length == 0) {
          this.$confirm('是否导出搜索后的字典数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            if (this.nodeTrans.dictValueid == "" && this.nodeTrans.dictTypeid == "" || this.nodeTrans.dictTypeid == "000") {
              //说明查询sysdict
              this.formSearch.pageNum = 1;
              this.formSearch.pageSize = 99999999;
              this.formSearch.queryType = true;
              queryByLabel(this.formSearch)
                .then(response => {
                  if (response.rows.length == 0) {
                    this.$message.error("无数据,导出失败");
                    return;
                  }
                  let row = {
                    row: response.rows,
                    status: 9
                  }
                  downLoadPost("/export/excel/exportClick", row)
                });
            } else {
              this.formSearch.pageNum = 1;
              this.formSearch.pageSize = 99999999;
              this.formSearch.queryType = false;
              this.formSearch.dictValueid = this.nodeTrans.dictValueid;
              this.formSearch.dictTypeid = this.nodeTrans.dictTypeid;
              queryByLabel(this.formSearch)
                .then(response => {
                  if (response.rows.length == 0) {
                    this.$message.error("无数据,导出失败");
                    return;
                  }
                  let row = {
                    row: response.rows,
                    status: 9
                  }
                  downLoadPost("/export/excel/exportClick", row)
                });
            }
          })

        } else {
          this.$confirm('是否导出选中的字典数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let row = {
              row: this.checkboxDatas,
              status: 9
            }
            downLoadPost("/export/excel/exportClick", row)
          })

        }


      },
      // 重置按钮
      clear() {
        this.formSearch.dictName = "";
        this.tableData = this.childrenData;
      },
      // 公用表格表头样式
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },

      //分页
      handleSizeChange(val) {
        this.pageSize = val
        params.pageSize = val;
        params.pageNum = this.pageNum;
        // params.dictLabel = this.formSearch.dictLabel;
        params.dictType = this.nodeTrans.dictType;
        params.dictTypeid = this.nodeTrans.dictTypeid;
        if (params.dictType == undefined || params.dictType == "") {
          params.dictType = "xxx_xxx";
        }
        if (params.dictTypeid == undefined || params.dictTypeid == "") {
          params.dictTypeid = "000";
        }
        clickTreeNode(params).then(response => {
          if (response.rows != null) {
            this.tableData = response.rows;
            this.total = response.total;
          } else {
            this.tableData3 = []
            this.total = response.total;
          }
        });
        // this.getGrid();
      },
      handleCurrentChange(val) {
        // this.pageSize = this.pageSize;
        this.pageNum = val
        params.pageNum = val
        params.pageSize = this.pageSize;
        // params.dictLabel = this.formSearch.dictLabel;
        params.dictType = this.nodeTrans.dictType;
        params.dictTypeid = this.nodeTrans.dictTypeid;
        // this.pageNum = val;
        if (params.dictType == undefined || params.dictType == "") {
          params.dictType = "xxx_xxx";
        }
        if (params.dictTypeid == undefined || params.dictTypeid == "") {
          params.dictTypeid = "000";
        }

        // this.getGrid();
        clickTreeNode(params).then(response => {
          if (response.rows != null) {
            this.tableData = response.rows;
            this.total = response.total;
          } else {
            this.tableData3 = []
            this.total = response.total;
          }
        });

      }
    }
  };

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

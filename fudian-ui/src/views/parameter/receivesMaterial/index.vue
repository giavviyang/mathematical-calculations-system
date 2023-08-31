<template>
  <div class="app-receives-material">
    <el-row style="height:100%">
      <el-col :span="4" :style="leftStyleName" style="height: calc(100vh - 86px)">
        <!-- 树结构 -->
        <div v-if="leftTree1" class="tree tree-style" :style="treeHeight">
          <el-scrollbar>
            <el-tree
              ref="tree2"
              :data="data2"
              :filter-node-method="filterNode"
              :expand-on-click-node="false"
              icon-class="el-icon-folder-opened"
              class="filter-tree"
              default-expand-all
              @node-click="treeNodeClick"
            />
          </el-scrollbar>
        </div>
        <div class="shrinkageDiv" @click="divClick()">
          <div :class="picClassname"></div>
        </div>
      </el-col>
      <el-col :span="18" style="height: calc(100vh - 86px)" :style="rightStyleName">
        <!--检索   开始    -->
        <el-row ref="searchRow" :gutter="10">
          <el-form
            ref="queryForm"
            :model="queryForm"
            v-if="showSearchBox"
            :inline="true"
            label-width="70px"
            class="search-form"
            v-hasPermi="['parameter:receivesMaterial:search']"
            >
            <!-- style="background:rgba(204, 204, 204, 0.18);padding: 20px 0 0 30px;margin-bottom: 10px;" -->
            <el-form-item @keyup.enter.native="onEnterSearch" label="干部姓名" prop="personName" style="margin-top: 10px;margin-bottom: 10px;">
              <el-input v-model="queryForm.personName" placeholder="请输入干部姓名" clearable size="mini" style="width:150px"/>
            </el-form-item>
            <!--
            <el-form-item label="部门" label-width="40px" prop="deptName" style="margin-top: 10px;margin-bottom: 10px;">
              <el-input v-model="queryForm.deptName" placeholder="请输入部门名称" clearable size="mini" style="width:138px"/>
            </el-form-item>
            -->
            <el-form-item @keyup.enter.native="onEnterSearch" label="材料名称" prop="e01z221a" style="margin-top: 10px;margin-bottom: 10px;">
              <el-input v-model="queryForm.e01z221a" placeholder="请输入材料名称" clearable size="mini" style="width:150px"/>
            </el-form-item>
            <el-form-item label="收件日期" prop="e01z201" style="margin-top: 10px;margin-bottom: 10px">
              <el-date-picker v-model="queryForm.e01z201" type="date" placeholder="请选择收件日期" value-format="yyyyMMdd" size="mini" style="width:150px"/>
            </el-form-item>
            <el-form-item style="margin-top: 10px;margin-bottom: 10px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
              <!-- <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button> -->
            </el-form-item>
          </el-form>

        <div class="btn">
          <el-button type="primary" icon="el-icon-plus" size="mini" v-hasPermi="['parameter:receivesMaterial:add']" @click="materialReceive">新增</el-button>
          <el-button type="primary" icon="el-icon-edit" size="mini" v-hasPermi="['parameter:receivesMaterial:edit']" @click="handleUpData">编辑</el-button>
          <el-button type="primary" icon="el-icon-delete" size="mini" v-hasPermi="['parameter:receivesMaterial:delete']" @click="handleDelete">删除</el-button>
          <el-button type="primary" icon="el-icon-download" size="mini" v-hasPermi="['parameter:receivesMaterial:import']" @click="handleExport">导出</el-button>
          <!-- <div class="btn-search">
            <el-button type="primary" icon="el-icon-search" size="mini" circle @click="function(){showSearchBox = !showSearchBox;tableHeight = showSearchBox?'73%':'84%'}" />
          <el-dropdown style="float: right; margin-left: 5px;">
              <el-button icon="el-icon-menu" size="mini" circle />
              <el-dropdown-menu style="padding: 10px">
                <el-checkbox-group v-for="(item, index) in col" :key="index" v-model="item.isCol" size="mini">
                  <el-checkbox>{{ item.label }}</el-checkbox>
                </el-checkbox-group>
              </el-dropdown-menu>
            </el-dropdown>
          </div> -->
        </div>
        </el-row>

          <el-table
            size="mini"
            v-loading="loading"
            ref="tableRefs"
            :data="tableData"
            element-loading-text="拼命加载中"
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)"
            border
            class="table"
            :height="tableHeight"
            :stripe="true"
            empty-text=" "
            row-key="id"
            tooltip-effect="light"
            :header-cell-style="headClass"
            align="center"
            @row-click="StepsListRowClick"
            @selection-change="handleSelectionChange"
            >
            <el-table-column type="selection" width="45" align="center"/>
            <el-table-column align="center" show-overflow-tooltip label="序号" :index="customIndex" type="index" min-width="55" />
            <el-table-column
              align="center"
              show-overflow-tooltip
              v-for="(item, index) in col"
              v-if="col[index].isCol"
              :key="index"
              :width="item.width"
              :formatter="item.formatter"
              :prop="col[index].prop"
              :label="item.label"/>
            <!--
            <el-table-column align="center" fixed="right" label="操作" width="100px">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="handleUpData(scope.row)">编辑</el-button>
                <el-button type="text" size="small" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
            -->
          </el-table>
          <!-- 分页 -->
          <div class="block" style="float: right;margin-top: 5px">
            <el-pagination
              :page-sizes="[20, 50, 100]"
              :page-size="pageSize"
              :current-page="pageNum"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="pageSizes"
              @current-change="pageCurrent"
            />
          </div>
      </el-col>
          <!-- </div>
        </template>
      </vxe-modal> -->
    </el-row>
    <!-- 编辑对话框 -->
    <el-dialog
      title="材料接收记录编辑"
      :visible.sync="disableEditArchiveForm"
      width="50%"
      class="dialog-style"
      :destroy-on-close="true"
      :close-on-click-modal="false"
      :append-to-body="true"
      >
      <!-- <vxe-modal v-model="disableEditArchiveForm" title="材料记录编辑" width="50%" height="50%" resize remember>
        <template>
          <div style="margin: 25px auto"> -->
      <el-row>
        <el-col :span="24" style="width:100%;padding-top: 20px">
          <el-form :rules="rules" :model="editorForm" ref="editorForm" label-width="130px" style="width:90%;padding-left:30px;">
            <el-col :span="12">
              <el-form-item label="干部姓名">
                <el-input v-model="editorForm.a0101" placeholder="请输入干部姓名" size="mini" :disabled="true" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <!--<el-form-item label="干部编号" >
              <el-input v-model="editorForm.number" placeholder="请输入干部编号" size="mini"/>
            </el-form-item>-->
            <el-col :span="12">
              <el-form-item label="职务" prop="duty">
                <el-select v-model="editorForm.duty" filterable placeholder="请选择职务" size="mini" style="width: 100%;">
                  <el-option v-for="item in dutys" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="来件单位" prop="e01z204a">
                <el-input v-model="editorForm.e01z204a" placeholder="请输入来件单位名称" size="mini"  style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="收件日期" prop="e01z201">
                <el-date-picker v-model="editorForm.e01z201" type="date" placeholder="请输入收件日期" value-format="yyyy-MM-dd" size="mini"  style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="经办人" prop="e01z207">
                <el-input v-model="editorForm.e01z207" placeholder="请输入经办人" size="mini" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="材料分类" prop="e01z211">
                <el-select v-model="editorForm.e01z211" clearable filterable  placeholder="请选择材料类别" size="mini" style="width: 100%;">
                  <el-option v-for="item in e01z211s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="材料序号" prop="e01z214">
                <el-input  v-model="editorForm.e01z214" placeholder="请输入材料序号" size="mini" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="材料名称" prop="e01z221a">
                <el-input  v-model="editorForm.e01z221a" placeholder="请输入材料名称" size="mini" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="材料制成日期" prop="e01z227">
                <el-date-picker type="date"  v-model="editorForm.e01z227" placeholder="请选择材料制成日期" value-format="yyyy-MM-dd" size="mini" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="材料页数" prop="e01z224">
                <el-input-number v-model="editorForm.e01z224" :min="0" size="mini" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="备注" prop="e01z231">
                <el-input
                  type="textarea"
                  style="width: 100%;"
                  :autosize="{minRows:1, maxRows: 2}"
                  v-model="editorForm.e01z231"
                  placeholder="请输入备注"
                  size="mini"
                />
              </el-form-item>
            </el-col>
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
          <el-button size="mini" @click="disableEditArchiveForm = false">取 消</el-button>
          <el-button size="mini" type="primary" @click="onCommitEdit('editorForm')">保 存</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
import { queryYear, queryE01z2s, deleteE01z2ByIds, updateE01z2ById } from '@/api/usual/parameter/receives-meterial'
import { queryDictionaryByTypes, queryDictionaryByType, queryArchiveTree} from '@/api/usual/receive'
import {downLoad, downLoadPost} from '@/downLoad/filedownload'
const params = {
  pageNum: 1,
  pageSize: 20
}

export default {
  name: '材料接收年度台账',
  watch: {
    tableData :{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          _this.$refs.tableRefs.doLayout()
        })
      },
      deep:true,
      immediate:true
    },
  },
  data() {
    return {
       /* 总页数 */
      total: 0,
      pageNum: 1,
      pageSize: 20,
      e01z211s:[],
      //系统字典表数据，用于formatter
      sysDictionaryMap: {},
      loading: true,
      picClassname: 'shrinkageLeft',
      // 树结构数据
      data2: [],
      checkYear: '',
      rightStyle: {
        width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px 10px 0px 10px', height: 'calc(100vh - 86px)'
      },
      rightStyle1: {
        width: '99%', height: 'calc(100vh - 86px)', padding: ' 10px 10px 0px 10px', height: 'calc(100vh - 86px)'
      },
      leftStyle: {
        width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc',position: 'relative'
      },
      leftStyle1: {
        width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc',position: 'relative'
      },
      leftStyleName: {
        width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc',position: 'relative'
      },
      rightStyleName: {
        width: '84%', height: 'calc(100vh - 86px)', padding: '10px 10px 0px 10px', height: 'calc(100vh - 86px)'
      },
      leftTree1: true,
      treeHeight: "height:500px",
      showSearchBox: true,
      // tableHeight:'73%',
      tableHeight: window.innerHeight - 86 - 52 - 76 - 48 + 15,
      /* 全选 */
      disableEditArchiveForm:false,
      isIndeterminate: true,
      dialogFormVisible: false,
      //编辑框
      editorForm:{
        org_id:"",
        a01_id:"",
        e01z201:"",
        e01z204a:"",
        e01z207:"",
        e01z211:"",
        e01z214:"",
        e01z221a:"",
        e01z224:"",
        e01z227:"",
        e01z231:""
      },
      rules: {
        e01z204a:[
          { max: 60, message: '输入内容不得超过60个字符', trigger: 'blur' }
        ],
        e01z207:[
          { max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
        ],
        e01z211: [
          { required: true, message: '请选择材料分类', trigger: 'blur' }
        ],
        e01z214:[
          { max: 3, message: '输入内容不得超过3个字符', trigger: 'blur' }
        ],
        e01z221a: [
          { required: true, message: '请输入材料名称', trigger: 'blur' },
          {  max: 40, message: '输入内容不得超过40个字符' }
        ],
        e01z227:[
          { required: true, message: '请选择材料制成日期',trigger: 'blur' },
        ],
        e01z224: [
          { required: true, message: '请输入材料页数', trigger: 'blur' },
          { type:'number', max:999, message: '输入内容不得超过3位数字', trigger: 'blur' }
        ],
        e01z231:[
          { min: 1, max: 50, message: '输入内容不得超过50个字符', trigger: 'blur' }
        ]
      },
      copiesForm: { //条件区副本
        checkYear: '',
        personName: '',
        e01z201: '',
        // deptName: '',
        e01z221a: '',
      },
      queryForm: {
        personName: '',
        // deptName: '',
        e01z201: '',
        e01z221a: ''
      },
      checkboxDatas :[],
      checkbox :[],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      col: [
        { label: '干部姓名', prop: 'a0101', isCol: true },
        // { label: '干部编号', prop: 'number', isCol: true },
        { label: '干部id', prop: 'a01Id', isCol: false },
        { label: '职务', prop: 'duty', isCol: true ,formatter:this.getFullName,width:"100px"},
        { label: '来件单位', prop: 'e01z204a', isCol: true,width:"120px" },
        { label: '收件日期', prop: 'e01z201', isCol: true ,formatter:this.getFullName,width:"100px" },
        { label: '经办人', prop: 'e01z207', isCol: true },
        { label: '材料分类', prop: 'e01z211', isCol: true,formatter:this.getFullName,width:"150px" },
        { label: '材料序号', prop: 'e01z214', isCol: true },
        { label: '材料名称', prop: 'e01z221a', isCol: true,width:"180px" },
        { label: '材料制成日期', prop: 'e01z227', isCol: true,formatter:this.getFullName,width:"120px"},
        { label: '页数', prop: 'e01z224', isCol: true },
        { label: '备注', prop: 'e01z231', isCol: true },
        { label: '全宗', prop: 'orgId', isCol: false }
      ],
      tableData: [],
      dutys:[],
      searchRow:'searchRow',
    }
  },
  mounted() {
    queryYear().then(response=>{
      this.data2 = [{
        id:'0',
        label:'申请年度',
        children:response.data
      }]
      // this.data2[0].children =
      // console.log(this.data2);
    })
    this.queryE01z2(params)
    this.getComboxDatas();
    this.$nextTick(()=>{
      this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
      this.tableHeight = (document.body.clientHeight - (84 + 50 + this.searchRowHeight)) + "px"
      this.treeHeight = "height:" + (document.body.clientHeight - 86) + "px";
    })
    window.onresize = () => {
      return (() => {
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 50 + this.searchRowHeight)) + "px"
        this.treeHeight = "height:" + (document.body.clientHeight - 86) + "px";
      })();
    };
  },
  created(){
    this.initData()
  },
  methods: {
    //自定义序号
    customIndex(index){
      return (this.pageNum-1)*(this.pageSize)+(index+1);
    },
    // 公用表格表头样式
    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
    },
    // 点击行事件
    StepsListRowClick(row){
      this.$refs.tableRefs.clearSelection()
      if (this.checkbox.length === 0) {
      this.$refs.tableRefs.toggleRowSelection(row)
        return;
      }
      this.$refs.tableRefs.toggleRowSelection(row)
    },
    // 左侧结构
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    treeNodeClick(data) {
      this.pageNum = 1
      if (data.id != '0') {
        this.checkYear = data.id
      } else {
        this.checkYear = ''
      }
      this.queryE01z2()
    },
    divClick() {
     this.leftTree1 = !this.leftTree1
     if (this.leftTree1) {
       this.rightStyleName = this.rightStyle
       this.leftStyleName = this.leftStyle
       this.picClassname = 'shrinkageLeft'
       this.treeShow =! this.treeShow
     } else {
       this.rightStyleName = this.rightStyle1
       this.leftStyleName = this.leftStyle1
       this.picClassname = 'shrinkageRight'
       this.treeShow =! this.treeShow
     }
    },
    geteFullNameWapper(item,field){
      let col = {
        property:field
      }
      return this.getFullName(item,col);
    },
    // 新增按钮
    materialReceive() {
      this.$router.push({
        name: '材料接收',
        params: {
          name: 'materialReceiving',
        }
      })
    },
    //初始化页面所需参数
    initData(){
      //当前页面所需字典表数据
      queryDictionaryByTypes({types:"ZB97B"})
        .then(response => {
          this.sysDictionaryMap = response.data
        })
      //当前页面所需字典表数据  职务
      queryDictionaryByType({ dictType: 'ZB09', eparentCode: '0' })
        .then(response => {
          this.dutys = response.data
        })
    },
    // 根据code转换成中文
    getFullName(row, col) {
      // 时间转换
      if (col.property == 'e01z201' || col.property == 'e01z227' ) {
        if (row[col.property] != null) {
          return this.moment(row[col.property]).format("YYYY-MM-DD")
        }
      }
      //材料分类
      if (col.property == 'e01z211') {
        if (this.sysDictionaryMap.ZB97B == null ) {
          return null
        } else {
          let returnValues=''
          this.sysDictionaryMap.ZB97B.forEach(data=>{
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName
            }
          })
          return returnValues;
        }
      }
      // 职务
      if (col.property == 'duty') {
        let returnValues='';
        this.dutys.forEach(data => {
          if(data.dictCode==row[col.property]){
            returnValues=data.fullName;
          }
        })
        return returnValues;
      }
      return row[col.property]
    },
    // 获取系统字典表下拉框数据
    getComboxDatas() {
      //获取材料类别
      queryArchiveTree().then(response=>{
        this.e01z211s = response.data
      })
    },
    queryE01z2(){
      const param = {
        checkYear: this.checkYear,
        personName: this.copiesForm.personName,
        // deptName: this.copiesForm.deptName,
        e01z201: this.copiesForm.e01z201,
        e01z221a: this.copiesForm.e01z221a,
      }
      params.pageSize = this.pageSize
      params.pageNum = this.pageNum
      params.searchValue = param
      queryE01z2s(params)
        .then(response => {
        this.tableData = response.rows
        this.$nextTick(()=>{
          this.$refs.tableRefs.doLayout();
        })
          this.total = response.total
          this.loading = false
      })
    },
    /* grid  行点击事件
    handleCurrentChange(val) {
      console.log(val)
    },
    */
    /* 复选框事件 */
    handleSelectionChange(val) {
      this.checkboxDatas = val;
      this.checkbox = val.map(item => item.id)
      console.log(val)
    },
    /* 编辑 */
    handleUpData(val) {
      console.log(this.checkboxDatas)
      if(this.checkboxDatas==null || this.checkboxDatas.length==0){
        this.$message({
          type: 'warning',
          message: '请勾选要编辑的信息'
        })
        return
      }
      if(this.checkboxDatas.length!=1){
        this.$message({
          type: 'warning',
          message: '请选择一条信息进行编辑'
        })
        return
      }
      this.editorForm=JSON.parse(JSON.stringify(this.checkboxDatas[0]));
      this.disableEditArchiveForm = !this.disableEditArchiveForm
    },
    //保存编辑内容
    onCommitEdit(val){
      this.$refs[val].validate((valid) =>{
        if(valid){
          let editFormData = JSON.parse(JSON.stringify(this.editorForm));
          console.log(editFormData)
          if(editFormData.e01z201 != ''){
            editFormData.e01z201 = this.moment(editFormData.e01z201).format("YYYYMMDD")
          }
          if(editFormData.e01z227 != ''){
            editFormData.e01z227 = this.moment(editFormData.e01z227).format("YYYYMMDD")
          }
          editFormData.otherParam = editFormData.duty
          console.log(editFormData)
          updateE01z2ById(editFormData)
            .then(response => {
            if(response.code==200){
              this.$message({
                message: response.msg,
                type: 'success'
              });
              this.queryE01z2(params)
              this.disableEditArchiveForm=false;
            }else{
              this.$message({
                message: response.msg,
                type: 'error'
              });
            }
          }).catch(() => {
          })
        }else{
          this.$message({
            message: '请按要求填写表单',
            type: 'error'
          });
        }
      })
    },
    /* 删除 */
    handleDelete(val) {
      if(this.checkboxDatas==null || this.checkboxDatas.length==0){
        this.$message({
          type: 'warning',
          message: '请勾选要删除的信息'
        })
        return
      }
      this.$confirm('此操作将永久删除所选信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
          const deleteId = this.checkbox.join('-')
          const id = deleteId || val.id
          if (id != '') {
            deleteE01z2ByIds({ e01z2Ids: id })
              .then(response => {
                if(response.code==200){
                  this.$message({
                    message: '删除成功',
                    type: 'success'
                  });
                  this.queryE01z2(params)
                }else{
                  this.$message({
                    message: '删除失败',
                    type: 'error'
                  });
                }
            })
          } else {
            this.$message({
              message: '请选择您要删除的内容',
              type: 'warning'
            })
          }
        })
        .catch(() => {
        })
    },

    // 查看附件
    getReceipt(val){},

    // 上传附件

    // 删除附件

    // 下载附件

    /* 导出 */
    handleExport() {
      if (this.checkboxDatas.length == 0) {
        this.$confirm('是否导出搜索后的台账数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          const param = {
            checkYear: this.checkYear,
            personName: this.copiesForm.personName,
            // deptName: this.copiesForm.deptName,
            e01z201: this.copiesForm.e01z201,
            e01z221a: this.copiesForm.e01z221a,
          }
          params.pageSize = 99999999
          params.pageNum = 1
          params.searchValue = param
          console.log(param)
          queryE01z2s(params)
            .then(response => {
              if(response.rows.length==0){
                this.$message.error("无数据,导出失败");
                return;
              }
              response.rows.map((item, index) => {
                item.duty = this.geteFullNameWapper(item, "duty");
                item.e01z201 = this.geteFullNameWapper(item, "e01z201");
                item.e01z211 = this.geteFullNameWapper(item, "e01z211");
                item.e01z227 = this.geteFullNameWapper(item, "e01z227");
              })
              let data = {
                "colTitle":{
                  "a0101":"干部名称",
                  // "number":"干部编号",
                  "duty":"职务",
                  "e01z204a":"来件单位",
                  "e01z201":"收件日期",
                  "e01z207":"经办人",
                  "e01z211":"材料分类",
                  "e01z214":"材料序号",
                  "e01z221a":"材料名称",
                  "e01z227":"材料制成日期",
                  "e01z224":"页数",
                  "e01z231":"备注"
                },
                "colWidth":{
                  "e01z204a":"25",
                  "e01z201":"25",
                  "e01z227":"25",
                  "e01z211":"40",
                  "e01z221a":"55",
                },
                "dataList":response.rows,
                "tableTitle":"材料接收台账表",
                "sheetTitle":"材料接收台账表",
                "downLoadName":"材料接收台账表"
              }
              downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data))
            })
        })

      } else {
        this.$confirm('是否导出选中的台账数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let checkboxDatas = this.checkboxDatas;
          checkboxDatas.map((item,index)=>{
            item.duty = this.geteFullNameWapper(item, "duty");
            item.e01z201 = this.geteFullNameWapper(item, "e01z201");
            item.e01z211 = this.geteFullNameWapper(item, "e01z211");
            item.e01z227 = this.geteFullNameWapper(item, "e01z227");
          })
          let data = {
            "colTitle":{
              "a0101":"干部名称",
              // "number":"干部编号",
              "duty":"职务",
              "e01z204a":"来件单位",
              "e01z201":"收件日期",
              "e01z207":"经办人",
              "e01z211":"材料分类",
              "e01z214":"材料序号",
              "e01z221a":"材料名称",
              "e01z227":"材料制成日期",
              "e01z224":"页数",
              "e01z231":"备注"
            },
            "colWidth":{
              "e01z204a":"25",
              "e01z201":"25",
              "e01z227":"25",
              "e01z211":"40",
              "e01z221a":"55",
            },
            "dataList":checkboxDatas,
            "tableTitle":"材料接收台账表",
            "sheetTitle":"材料接收台账表",
            "downLoadName":"材料接收台账表"
          }
          downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data))
        })

      }
    },
    /* 打印 */
    handlePrint() {

    },
    /* 机构管理 */
    structure(val) {
      this.$router.replace({ path: '/views/house/structure', params: { val: val }})
    },
    pageCurrent(val) {
      this.pageNum = val
      params.pageNum = this.pageNum;
      params.pageSize = this.pageSize;
      this.queryE01z2()
    },
    pageSizes(val) {
      this.pageSize = val
      params.pageNum = this.pageNum;
      params.pageSize = this.pageSize;
      this.queryE01z2()
    },
    search() {
      this.pageNum = 1
      this.copiesForm = JSON.parse(JSON.stringify(this.queryForm))
      this.queryE01z2()
    },
    //搜索区回车事件
    onEnterSearch() {
      this.search();
    },
    // 重置
    reset() {
      this.queryForm.personName= ""
      this.queryForm.deptName= ""
      this.queryForm.e01z201= ""
      this.queryForm.e01z221a= ""
      this.queryE01z2(params)
    }
  }
}
</script>

<style lang="scss" scoped>
  .app-receives-material {
    height: 100%;
    .tree {
      width: 100%;
      height: 500px;
      padding: 20px;
      border-right: 1px solid #e2e3e6;
    }
    .search-form{
      width:99%;
      background:rgba(204, 204, 204, 0.18);
      margin-left: 5px;
      padding-left: 10px;
      padding-bottom:10px;
      display:flex;
      .form-width{
        width:22%;
        display:flex;
      }
        /deep/ .el-form-item{
        margin-bottom: 0 !important;
      }
    }
    .btn {
      width:100%;
      position: relative;
      padding-bottom: 10px;
      padding-top: 10px;
      padding-left: 5px;
      display:flex;
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
  // 设置表格高度
  /deep/.el-table tbody tr:hover>td {
    background-color:#ecf5ff !important
  }
  /deep/.el-table__body td {
    padding: 0;
    height: 40px;
  }
  // 公用弹框样式
  .dialog-style {
    /deep/.el-dialog__body {
      padding: 10px;
    }
    /deep/.el-dialog__header {
      background: rgba(204, 204, 204, 0.18);
      padding: 10px;
    }
    /deep/.el-dialog__title {
      font-weight: 500;
      font-size: 15px
    }
    /deep/.el-dialog__headerbtn {
      top: 13px;
    }
    /deep/.el-dialog__footer {
      background: rgba(204, 204, 204, 0.18);
      padding-right: 15px;
      padding: 11px;
    }
    /deep/.el-button+.el-button {
      margin-left: 6px;
    }
  }
  // 公用树结构样式
  .tree-style{
    /deep/.el-tree--highlight-current .el-tree-node.is-current>.el-tree-node__content {
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

  .shrinkageLeft{
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
      cursor:pointer;
  }
  .shrinkageRight{
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
      cursor:pointer;
  }
  .shrinkageDiv{
      background-color: #1890ff;
      cursor:pointer;
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

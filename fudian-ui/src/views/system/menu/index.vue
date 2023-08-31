<template>
  <div class="app-daily">
    <el-row>
      <el-col :span="24" style="height: 100%;">
        <el-col :span="4" :style="leftStyleName" style="height: calc(100vh - 86px)">
          <!-- 树结构 -->
          <!--        id="treescroll"-->
          <el-input placeholder="请输入内容" v-if="treeShow" size="mini" class="inp-select" style="width:90%;padding:20px 0 0 20px"
                    v-model="treeFilter"></el-input>
          <div class="tree tree-style" >

            <el-scrollbar style="height:100%">
              <el-tree
                ref="tree4"
                :data="treeData"
                :props="TreeDefaultProps"
                icon-class="el-icon-folder-opened"
                :highlight-current="true"
                @node-click="handleNodeClick"/>
            </el-scrollbar>
          </div>
          <div class="shrinkageDiv" @click="divClick()">
            <div :class="picClassname"></div>
          </div>
        </el-col>
        <el-col :span="20" style="height: calc(100vh - 86px)" :style="rightStyleName">
          <el-col :span="24" ref="searchRow" style="padding: 10px 0;">
            <el-button type="primary" size="mini" icon="el-icon-plus" v-hasPermi="['system:menu:add']" @click="addFormWindow">新增</el-button>
          </el-col>
          <el-table
            ref="tableRefs1"
            size="mini"
            :data="tableData1"
            :height="tableHeight "
            class="table-style"
            tooltip-effect="light"
            border
            style="width: 100%"
            :header-cell-style="headClass"
            stripe
            empty-text=" "
            @selection-change="selectChecked">
            <el-table-column type="selection" width="55" />
            <el-table-column align="center" show-overflow-tooltip v-for="(item, index) in col" :key="index" v-if="item.isCol" :prop="item.prop" :label="item.label" />
            <el-table-column align="center" width="150px" fixed="right" label="操作">
              <template slot-scope="scope">
                <el-button type="text" size="small" v-hasPermi="['system:menu:modify']" @click="upData(scope.row)">编辑</el-button>
                <el-button type="text" size="small" v-hasPermi="['system:menu:delete']" @click="handleDelete(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-col>
      <el-dialog title="添加菜单" :visible.sync="addFormVisible" class="dialog-style dialog-basic">
        <el-form ref="addForm" :model="addForm">
          <el-form-item label="菜单名称" :label-width="formLabelWidth">
            <el-input v-model="addForm.menuName" autocomplete="off" />
          </el-form-item>
          <el-form-item label="菜单类型" :label-width="formLabelWidth">
            <el-radio-group v-model="addForm.menuType" @change="archivesTypeClick">
              <el-radio :label="0">目录</el-radio>
              <el-radio :label="1">菜单</el-radio>
              <el-radio :label="2">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="路由地址" :label-width="formLabelWidth">
            <el-input v-model="addForm.path" autocomplete="off" />
          </el-form-item>
          <el-form-item label="组件路径" :label-width="formLabelWidth">
            <el-input v-model="addForm.component" autocomplete="off" />
          </el-form-item>
          <el-form-item label="权限标识" :label-width="formLabelWidth">
            <el-input v-model="addForm.perms" autocomplete="off" />
          </el-form-item>
          <el-form-item label="显示状态" :label-width="formLabelWidth">
            <el-radio v-model="addForm.visible" :label="0">显示</el-radio>
            <el-radio v-model="addForm.visible" :label="1">隐藏</el-radio>
          </el-form-item>
          <el-form-item label="是否启用" :label-width="formLabelWidth">
            <el-radio v-model="addForm.status" :label="0">启用</el-radio>
            <el-radio v-model="addForm.status" :label="1">禁用</el-radio>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="addFormVisible = false">取消</el-button>
          <el-button type="primary" size="mini"  @click="addFormSave('addForm')">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog title="菜单编辑" :visible.sync="updateFormVisible"  class="dialog-style dialog-basic">
        <el-form  ref="updateForm" :model="updateForm">
          <el-form-item label="菜单名称" :label-width="formLabelWidth">
            <el-input v-model="updateForm.menuName" autocomplete="off" />
          </el-form-item>
          <el-form-item label="菜单类型" :label-width="formLabelWidth">
            <el-radio-group v-model="updateForm.menuType" @change="archivesTypeClick">
              <el-radio :label="0">目录</el-radio>
              <el-radio :label="1">菜单</el-radio>
              <el-radio :label="2">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="路径地址" :label-width="formLabelWidth">
            <el-input v-model="updateForm.path" autocomplete="off" />
          </el-form-item>
          <el-form-item label="组件路径" :label-width="formLabelWidth">
            <el-input v-model="updateForm.component" autocomplete="off" />
          </el-form-item>
          <el-form-item label="权限标识" :label-width="formLabelWidth">
            <el-input v-model="updateForm.perms" autocomplete="off" />
          </el-form-item>
          <el-form-item label="显示状态" :label-width="formLabelWidth">
            <el-radio v-model="updateForm.visible" :label="0">显示</el-radio>
            <el-radio v-model="updateForm.visible" :label="1">隐藏</el-radio>
          </el-form-item>
          <el-form-item label="是否启用" :label-width="formLabelWidth">
            <el-radio v-model="addForm.status" :label="0">启用</el-radio>
            <el-radio v-model="addForm.status" :label="1">禁用</el-radio>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button  size="mini" @click="updateFormVisible = false">取消</el-button>
          <el-button type="primary" size="mini" @click="updateFormSave('updateForm')">保存</el-button>
        </div>
      </el-dialog>
    </el-row>
  </div>
</template>
<script>
import {getDeptTree} from "@/api/system/dept";
import {queryDictionaryByType,queryDictionaryByTypes} from "@/api/usual/receive";
import {
  getA01List,
  updateA01,
  deptList,
  removeRecord,
  adjustDept,
  adjustState,
  getHighSearch,
  saveArchiveLock
} from "@/api/usually/collect"
// import {getHighSearch} from "../../../api/usually/collect";
import {MessageBox, Notification} from 'element-ui'
import shrinkage from '@/components/shrinkage/index'  // 打印  // 路径
import {downLoad} from '@/downLoad/filedownload'

import {queryMenuById, querySysMenusList, insertSysMenu,deleteSysMenuById,updateSysMenuById } from '@/api/system/menu'

export default {
  components: {shrinkage},
  // name: '菜单管理',
  watch: {
    tableData1: {
      handler(newName, oldName) {
        let _this = this
        _this.$nextTick(() => {
          _this.$refs.tableRefs1.doLayout();
        })
      },
      deep: true,
      immediate: true
    },
  },
  data() {
    return {
      // 全选
      isIndeterminate: true,
      addFormVisible:false,
      updateFormVisible:false,
      addForm: {
        menuName: '',
        menuType: '',
        path: '',
        component: '',
        perms: '',
        visible: 0,
        status:0
      },
      updateForm: {
        menuName:'',
        menuType: '',
        menuId: '',
        path:'',
        component:'',
        perms:'',
        visible:null,
        status:null,

      },
      formLabelWidth: '120px',
      showSearchBox: false,
      queryForm: {
        e01zc24a: '',
        e01z804: ''
      },
      scrollTop:0,
      tableDom:'',
      treeShow: true,
      treeHeight: 'height:500px',
      loading: true,
      //复选框
      checkbox: [],
      checkboxDatas: [],
      //左侧树结构收缩
      picClassname: 'shrinkageLeft',
      rightStyle: {
        width: '84%',  padding: ' 10px', height: 'calc(100vh - 86px)'
      },
      rightStyle1: {
        width: '99%', padding: ' 10px', height: 'calc(100vh - 86px)'
      },
      leftStyle: {
        width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      leftStyle1: {
        width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      leftStyleName: {
        width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      rightStyleName: {
        width: '84%', padding: ' 10px', height: 'calc(100vh - 86px)'
      },
      leftTree1: true,
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
      tableHeight: '73%',
      searchRow: 'searchRow',
      searchRowHeight: '',
      // 搜索表单数据 档案查缺-高级检索
      formSearch: {
        pageSize: 20,
        deptName: '',
        pageNum: 1,
        a0101: '',
        state: '0',
        deptId: '',
        data4: '',
        total: 0,
        number: '',
        a0151: '',
        e01z111a: '',
        redio: '0',
      },
      //排序
      tableDataSort: [{}],
      //状态调整表单
      formState: {
        state: 'true'
      },
      //部门树结构数据
      filterText2: '',
      filterText3: '',
      treeFilter: '',
      TreeDefaultProps: {
        children: "children",
        label: "menuName",
      },
      data2: [],
      data3: [],
      defaultProps: {
        children: "children",
        label: "label"
      },

      treeData: [],
      //表格表头勾选隐藏
      col:[
        { label: '菜单名称', prop: 'menuName', isCol: true },
        { label: '排序', prop: 'orderNum', isCol: true },
        { label: '路由地址', prop: 'path', isCol: true },
        { label: '组件路径', prop: 'component', isCol: true },
        { label: '菜单类型', prop: 'menuType', isCol: true },
        { label: '菜单状态', prop: 'visible', isCol: true },
        { label: '权限标识', prop: 'perms', isCol: true },
        { label: '是否启用', prop: 'status', isCol: true },
        { label: '备注', prop: 'remark', isCol: true }
      ],
      // 部门人员表格数据
      tableData1: [],
      // 搜索框表格数据
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


      //字典表
      a0151s: [],
      a0104s1: [],
      states: [],
      education: [],
      dutys: [],

      a0111bs: [],
      a0114bs: [],
      a0115bs: [],
      a0117s: [],
      a0127s: [],
      a0131s: [],
      a0141s: [],
      a0157bs: [],
      a0161s: [],
      a0164s: [],
      a0167s: [],
      a0171s: [],
      a0174s: [],
      a0177s: [],
      a0181as: [],
      a0183s: [],
      a0191s: [],
      flags: [],
      exportDatas: [],
      deleteCheck:[],
      treeClickId:"",
      gridCheck:[]

    };
  },
  mounted() {
    this.$nextTick(() => {
      this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
      this.tableHeight = ((document.body.clientHeight - (84 + 100 + this.searchRowHeight))+95) + "px"
    })
    window.onresize = () => {
      return (() => {
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = ((document.body.clientHeight - (84 + 100 + this.searchRowHeight))+95) + "px"
      })();
    };
    this.leftDepTree()
  },
  created() {
    // this.initData();
    // this.getGridList();
    this.leftDepTree();
  },
  methods: {
    /* 新增保存 */
    addFormSave(addForm) {
      if( this.addForm.menuType=="0"){
        this.addForm.menuType="M"
      }else if(this.addForm.menuType=="1"){
        this.addForm.menuType="C"
      }else{
        this.addForm.menuType="B"
      }
      const _this = this;
      let param = this.addForm;
      param.parentId =this.treeClickId;
      this.$refs[addForm].validate(valid => {
        if (valid) {
          insertSysMenu(param)
            .then(function(response) {
              if(response.code==200){
                _this.$message({ showClose: true, message: "添加成功,请刷新页面", type: "success" });
              }
              queryMenuById({id:_this.treeClickId}).then(response => {
                _this.tableData1 = response.data;
              })
              _this.addForm = {
                menuName: '',
                menuType: '',
                path: '',
                component: '',
                perms: '',
                visible: 0,
                status:0
              }
              _this.addFormVisible = false
            })
            .catch(function(error) {
              _this.$message({
                showClose: true,
                message: error,
                type: 'error'
              })
            })
        } else {
          return false
        }
      })

    },
    archivesTypeClick() {
    },
    //自定义序号
    customIndex(row, column, cellValue, index){
      return (this.formSearch.pageNum-1)*(this.formSearch.pageSize)+(index+1);
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
    resetHigh() {
      this.highSearch.key3 = '';
      this.highSearch.key4 = '';
    },
    initData() {
      // ZB01
      queryDictionaryByTypes({types: "ZB09:0-GB22614-GB22611-FDA01State-ZB64-GB3304-GB22613-GB22612-GB4762-ZB87-ZB03-ZB04-ZB07-ZB76-AMS07-AMS08"})
        .then(response => {
          // this.sysDictionaryMap = response.data
          this.a0104s1 = response.data.GB22611;
          this.states = response.data.FDA01State;
          this.education = response.data.ZB64;
          this.a0151s = response.data.GB22614;
          this.dutys = response.data.ZB09;
          this.a0157bs = response.data.ZB02
          this.a0117s = response.data.GB3304;
          this.a0127s = response.data.GB22613;
          this.a0131s = response.data.GB22612;
          this.a0141s = response.data.GB4762;
          this.a0164s = response.data.ZB87;
          this.a0167s = response.data.ZB03;
          this.a0171s = response.data.ZB04;

          this.a0177s = response.data.ZB07;
          this.a0183s = response.data.ZB76;
          this.a0191s = response.data.AMS07;
          this.flags = response.data.AMS08;
          //this.a0171s = response.data.ZB04;
        })
    },
    leftDepTree() {
      const _this = this
      querySysMenusList()
        .then(function(response) {
          _this.treeData = response.data
          _this.total = response.data.length
          _this.tableData1 = response.data;
          _this.formSearch.total = response.data.length;
          if(_this.treeData){
            _this.treeData.forEach(item=>{
              item.children = []
            })
          }
        })
    },

    //左侧树节点 点击事件
    handleNodeClick(data,node) {
      console.log(node)
      this.treeClickId = data.menuId;
      queryMenuById({id:data.menuId}).then(response => {
        this.tableData1 = response.data;
      })
    },
    upData(val) {
      console.log(val);
      this.updateFormVisible= true
      this.updateForm.menuId = val.menuId;
      this.updateForm.menuName = val.menuName;
      this.updateForm.menuType = val.menuType;
      this.updateForm.visible = val.visible;
      this.updateForm.path = val.path;
      this.updateForm.component = val.component;
      this.updateForm.perms = val.perms;
      this.updateForm.status = val.status;
      if(this.updateForm.menuType=="M"){
        this.updateForm.menuType=0;
      }else if(this.updateForm.menuType=="C"){
        this.updateForm.menuType=1;
      }else{
        this.updateForm.menuType=2;
      }
      this.updateForm.visible = parseInt(this.updateForm.visible)
    },

    updateFormSave(){
      const _this = this;
      if( this.updateForm.menuType=="0"){
        this.updateForm.menuType="M"
      }else if(this.updateForm.menuType=="1"){
        this.updateForm.menuType="C"
      }else{
        this.updateForm.menuType="B"
      }
      let param = this.updateForm;
      param.children = null;
      updateSysMenuById(param)
        .then(function(response) {
          if(response.code==200){
            _this.$message({ showClose: true, message: "修改成功,请刷新页面", type: "success" });
          }
          queryMenuById({id:_this.treeClickId}).then(response => {
            _this.tableData1 = response.data;
          })
          _this.updateFormVisible = false;
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        });

    },
    addFormWindow(){
      this.addFormVisible = true;
      this.addForm={
        menuName: '',
          menuType: '',
          path: '',
          component: '',
          perms: '',
          visible: 0,
          status:0
      }

    },
    handleDelete(val) {
      if(val.children.length>0){
        alert("存在子节点无法删除");
        return;
      }
      let _this = this
      let deleteId = val.menuId;
      if (deleteId === '') {
        _this.$message({
          message: '请选择您要删除的内容',
          type: 'warning'
        })
      } else {
        _this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            deleteSysMenuById({ menuId: deleteId.toString() })
              .then(function(response) {
                if(response.code==200){
                  _this.$message({ showClose: true, message: "删除成功,请刷新页面", type: "success" });
                }
                queryMenuById({id:_this.treeClickId}).then(response => {
                  _this.tableData1 = response.data;
                })
                // _this.$message({
                //   type: 'success',
                //   message: '删除成功!'
                // })
              })
              .catch(function(error) {
                _this.$message.error(error)
              })
          })
          .catch(() => {
            _this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
      }
    },

    // 公用表格表头样式
    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
    },
    // 判断表格是否勾选一行方法
    selectChecked(data) {
      this.checkboxDatas = data
      this.checkbox = data.map(item => item.id)
      this.gridCheck = data
      this.deleteCheck = data.map(item => item.menuId)
    },

    // 功能按钮
    strToDate(val){
      if(val == null || val == '' || val.toString().length<8){
        return "";
      }
      return val.toString().substring(0,4) + "-" + val.toString().substring(4,6) + "-" + val.toString().substring(6,8)
    },
    //分页
    handleSizeChange(val) {
      this.formSearch.pageSize = val,
        this.formSearch.pageNum = this.formSearch.pageNum
    },
    handleCurrentChange(val) {
      this.formSearch.pageSize = this.formSearch.pageSize,
        this.formSearch.pageNum = val
    },
  }
};

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

/deep/ .el-table .el-table__header-wrapper th {
  height: 31px;
}

.table-style {
/deep/ .el-table__fixed-right-patch {
  background-color: #F8F8F9;
}
}
</style>

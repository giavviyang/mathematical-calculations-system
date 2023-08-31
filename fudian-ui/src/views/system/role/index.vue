<template>
  <div>

    <el-col :span="24" :xs="24" style="height: calc(100vh - 86px);">
      <el-row :gutter="10" ref="searchForm" style="margin:0;">
        <el-form @submit.native.prevent
                 class="search-form"
                 ref="queryForm"
                 :model="queryForm"
                 :inline="true"
                 label-width="68px"
                 style="background:rgba(204, 204, 204, 0.18);">
          <el-form-item label="所属学校" prop="deptId" >
            <el-select  style="width: 150px" v-model="queryForm.deptId" placeholder="请选择所属学校" clearable class="inputW" size="mini">
              <el-option v-for="item in schools" :key="item.id" :label="item.deptName" :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="角色名称" prop="roleName" >
            <el-input style="width: 150px" size="mini" v-model="queryForm.roleName" placeholder="请输入角色名称" clearable  />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
            <!--              <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>-->
          </el-form-item>
        </el-form>
      </el-row>
      <el-col :span="24" style="padding: 10px 0px;">
        <el-button type="primary" icon="el-icon-plus" size="mini"
                   @click="handleAdd">新增
        </el-button>

        <el-button type="primary" icon="el-icon-edit" size="mini"
                   @click="upData">编辑
        </el-button>

        <el-button type="primary" icon="el-icon-delete" size="mini"
                   @click="handleDelete">删除
        </el-button>

        <el-button type="primary" icon="el-icon-download" size="mini"
                   @click="exportExcel">导出
        </el-button>
      </el-col>

      <el-table


        :row-style="{height:'40px'}"
        :cell-style="{padding:'0px'}"
        row-key="id"
        align="left"
        :default-sort="{ prop: 'date', order: 'descending' }"
        stripe
        border
        :height="tableHeight"
        empty-text=" "
        size="mini"
        @row-click="StepsListRowClick"
        tooltip-effect="light"
        ref="tableGrid"
        :header-cell-style="headClass"
        style="width: 100%"
        :data="tableData"
        @selection-change="SelectionChange"
      >
        <el-table-column align="center" type="selection" width="55"/>
        <el-table-column type="index" width="50" align="center" label="序号" show-overflow-tooltip :index="customIndex"/>
        <el-table-column align="center" show-overflow-tooltip v-for="(item, index) in col" :v-if="col[index].isCol"
                         :formatter="item.formatter" :key="index" :prop="col[index].prop" :label="item.label"/>
        <el-table-column align="center" fixed="right" label="操作" width="220">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="openMenuWindows(scope.row)">
              功能权限
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="page" style="margin-top: 5px;">
        <el-pagination :page-sizes="[20, 50, 100, 200]" :page-size="pageSize" :current-page="pageNum"
                       layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="pageSizes"
                       @current-change="pageCurrent"/>
      </div>
    </el-col>


    <!-- 新增角色信息 -->
    <el-dialog :visible.sync="dialogFormVisible"
               :destroy-on-close="true" class="dialog-style dialog-basic" title="新增角色信息" width="60%">
      <div>
        <el-form ref="addForm" :model="addForm" style="margin-left: 10%;margin-right: 10%" :rules="addSysRoleRules"
                 :inline="true" label-width="120px" label-position="right">
          <el-form-item label="角色名称" :label-width="formLabelWidth" prop="roleName">
            <el-input v-model="addForm.roleName" size="mini" placeholder="角色名称" autocomplete="off"
                      style="width:150px" ></el-input>
          </el-form-item>

          <el-form-item label="角色描述" :label-width="formLabelWidth">
            <el-input v-model="addForm.roleSummary" autocomplete="off" size="mini" style="width: 150px"></el-input>
          </el-form-item>

          <el-form-item label="所属学校" prop="schoolId" >
            <el-select  style="width: 150px" v-model="addForm.schoolId" placeholder="请选择所属学校" clearable class="inputW" size="mini">
              <el-option v-for="item in schools" :key="item.id" :label="item.deptName" :value="item.id"/>
            </el-select>
          </el-form-item>

          <el-form-item label="备注" :label-width="formLabelWidth">
            <el-input v-model="addForm.remark" type="textarea" autocomplete="off" style="width: 150px"></el-input>
          </el-form-item>
        </el-form>

      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogFormVisible=false">取消</el-button>
        <el-button size="mini" type="primary" @click="addRoleForm('addForm')">保存</el-button>
      </div>
    </el-dialog>

    <!-- 编辑角色信息 -->
    <el-dialog :visible.sync="dataPermissionsW" :append-to-body="true"
               :destroy-on-close="true" class=" dialog-style dialog-basic" title="编辑角色信息" width="60%">
      <div>
        <el-form ref="upDateForm" :model="upDateForm" style="margin-left: 10%;margin-right: 10%"
                 :rules="addSysRoleRules" :inline="true" label-width="120px" label-position="right">
          <el-form-item label="角色名称" :label-width="formLabelWidth" prop="roleName">
            <el-input v-model="upDateForm.roleName" size="mini" placeholder="角色名称" autocomplete="off"
                      style="width:150px"></el-input>
          </el-form-item>

          <el-form-item label="角色描述" :label-width="formLabelWidth">
            <el-input v-model="upDateForm.roleSummary" autocomplete="off" size="mini" style="width: 150px"></el-input>
          </el-form-item>
          <el-form-item label="所属学校" prop="schoolId" >
            <el-select  style="width: 150px" v-model="upDateForm.schoolId" placeholder="请选择所属学校" clearable class="inputW" size="mini">
              <el-option v-for="item in schools" :key="item.id" :label="item.deptName" :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="备注" :label-width="formLabelWidth">
            <el-input v-model="upDateForm.remark" type="textarea" autocomplete="off" style="width: 150px"></el-input>
          </el-form-item>
        </el-form>

      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dataPermissionsW=false">取消</el-button>
        <el-button size="mini" type="primary" @click="upDateFormData('upDateForm')">保存</el-button>
      </div>
    </el-dialog>


    <!-- 权限分配 -->
    <el-dialog :visible.sync="dialogFormVisible1"
               :append-to-body="true"
               class=" dialog-style dialog-basic" title="数据权限分配" width="75%">
      <div>
        <div>
          <div class="tree tree-style" style="height:420px;margin:0 auto">
            <el-scrollbar style="height:100%" wrap-class="scrollbar-wrapper">
              <el-tree node-key="id" empty-text=" " border style="font-size:20px;overflow: auto" ref="tree"
                       @node-click="treeCleck" :data="treeData" :props="defaultProps"
                       icon-class="el-icon-folder-opened" :filter-node-method="filterNode"
                       :highlight-current="true"
                       :expand-on-click-node="false" show-checkbox @check="deptTreeClick"
                       :default-checked-keys="firstTreeCheckedData"
                       default-expand-all/>
            </el-scrollbar>
          </div>
          <div  slot="footer" class="dialog-footer" >
            <el-button  size="mini" style="float: right;margin-top: 20px" type="primary" @click="saveDeptAndNext">
              保存
            </el-button>
          </div>
        </div>

      </div>

    </el-dialog>


    <!-- 功能分配 -->
    <el-dialog :visible.sync="dialogFormVisibleMenu"
               :destroy-on-close="true" class=" dialog-style dialog-basic" title="功能权限分配" width="30%">
      <div>
        <div class="tree tree-style" style="height:400px;margin:0 auto">
          <el-scrollbar wrap-class="scrollbar-wrapper">
            <el-tree node-key="menuId" ref="menuTree" default-expand-all
                     :highlight-current="true" show-checkbox :data="menuTreeData"
                     icon-class="el-icon-folder-opened" :props="defaultPropsMenu"
                     :default-checked-keys="menuTreeCheckData" @check="menuTreeCheck"/>
          </el-scrollbar>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogFormVisibleMenu=false">取消</el-button>
        <el-button size="mini" type="primary" @click="saveMenuTree">保存</el-button>
      </div>
    </el-dialog>



  </div>
</template>
<script>
  import {
    addOrRemoveA01Permission,
    batchInsertRoleMenu,
    deleteRoleById,
    getAllMenuIds,
    getAllMenuTree,
    getInitDataPermission,
    getRoleBindUserByDeptIds,
    getRoleBindUserByDeptIdsRemain,
    insertSysRole,
    queryRolePageList,
    saveDataPermissions,
    updateRoleById
  } from '@/api/system/role'
  import {
    queryPermissionListByRoleId,
    queryPermissionListMap,
    queryRemainingPermissionListByRoleId
  } from '@/api/system/permissions'
  import {queryRemainingUserListByRoleId, queryUserByRoleId} from '@/api/system/user'
  import {getDeptListByLoginUser, getDeptTree} from '@/api/system/dept'
  import {queryE01z1TreeGrid} from '@/api/usual/receive'
  import {downLoadPost} from '@/downLoad/filedownload'
  import DialogAddForm from "@/components/public/DialogBody/DialogAddForm";

  const cityOptions = ['测试', '研发', '人事', '售后']
const params = {
  pageNum: 1,
  pageSize: 20
}
export default {
  name: "角色管理",
  components: {DialogAddForm},
  data() {
    return {
      menuTreeCheckData: [],
      dialogFormVisibleMenu: false,
      menuTreeData: [],
      dataPermissionForm: {type: []},
      upSearchForm: {a0101:""},
      downSearchForm: {a0101:""},
      upSearchText: "",
      downSearchText: "",
      isRoot: false,
      upGridCheckData: [],
      downGridCheckData: [],
      classifyTreeData: [],
      treeData: [],
      dataPermissionsW: false,
      tableHeight: "73%",
      searchRowHeight:"",
      titletext: '',
      classifyTreeCheckKey: [],
      windowtext: '',
      treeCheckKey: [],
      defaultPropsMenu: {
        children: "children",
        label: "menuName",
      },
      addSysRoleRules: {
        roleName: [
          {required: true, message: '请输入角色名称', trigger: 'blur'},
          {min: 2, max: 18, message: '长度在 2 到 18 个字符', trigger: 'blur'}
        ],
        roleStatus: [
          {required: true, message: '请选择状态', trigger: 'blur'},
        ],
        isSystem: [
          {required: true, message: '请选择角色类型', trigger: 'blur'},
        ],
      },

      defaultProps: {
        children: "children",
        label: "deptName",
      },
      permissionList: [],
      /* 总页数 */
      total: 0,
      pageNum: 1,
      pageSize: 20,
      /* 刷新遮罩 */
      loading: true,
      // 全选
      isIndeterminate: true,
      checkAll: false,
      checkedCities: [],
      checkTreeData: [],
      cities: cityOptions,
      dialogFormVisible1: false,
      dialogFormVisible: false,
      Menu: false,
      remainUserTable: false,
      remainPermissonTable: false,
      dialogPermissionFormVisible: false,
      userGridData: [],
      permissionGridData: [],
      /* 新增form */
      addForm: {
        roleName: '',
        roleStatus: '0',
        isSystem: '1',
        remark: '',
      },
      /* 编辑form */
      upDateForm: {},
      form: {
        roleName: '',
        roleStatus: '',
        isSystem: '',
        schoolName: '',
        remark: ''
      },
      formLabelWidth: '90px',
      showSearchBox: true,
      /* 搜索 */
      queryForm: {
        roleName: '',
        isSystem: '',
        deptId: '',
      },
      schools:[],
      school:{},
      /* grid 题名 */
      col: [
        {label: '角色名称', prop: 'roleName', isCol: true},
        {label: '角色描述', prop: 'roleSummary', isCol: true},
        {label: '创建时间', prop: 'createTime', isCol: true, formatter: this.getFullName},
        {label: '创建人', prop: 'createBy', isCol: true},
        {label: '备注', prop: 'remark', isCol: true}
      ],
      distributionDta: [],
      /*当前为分配用户*/
      distributionCol: [
        {prop: 'loginCode', label: '登录名'},
        {prop: 'userName', label: '用户名称'},
        {prop: 'createTime', label: '创建时间'},
        {prop: 'userStatus', label: '用户状态'}
      ],
      distributionForm: {},
      hasPerTableData: [],
      nHasPerTableData: [],
      permissionsCheck: '',
      permissionCol: [
        {property: 'permissionName', label: '权限名称'},
        {property: 'permissionDataSummary', label: '数据权限摘要'},
        {property: 'permissionMenuSummary', label: '功能权限摘要'},
        {property: 'permissionStatus', label: '权限状态'}
      ],

      searchForm:"searchForm",
      /* grid数据 */
      tableData: [],
      rolechek: [],
      permissionIds: [],
      queryUserForm: {
        userName: '',
        roleId: ''
      },
      firstTreeCheckedData: [],
      deptTreeClickDatas: {},
      clickRoleData: {},
      permissionsData: [],
      queryPermissionForm: {
        permissionName: '',
        roleId: ''
      },
      //数据权限两个table分页字段
      upTotal: 0,
      downTotal: 0,
      upPageParams: {},
      downPageParams: {},
      upPageSize: 20,
      upPageNum: 1,
      downPageSize: 20,
      downPageNum: 1,
    }
  },
  mounted() {
    this.queryRoleList(params)
    this.getSchools();
    this.loadAllPerMissons();
    this.keyupSubmit();
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
    //获取学校信息
    getSchools(){
      getDeptListByLoginUser().then(reopnse=>{
        this.schools = reopnse.data;
      })
    },
    //自定义序号
    customIndex(index){
      return (this.pageNum-1)*(this.pageSize)+(index+1);
    },

    openMenuWindows(val) {
      this.clickRoleData = val;
      getAllMenuIds({roleId: val.roleId}).then(res => {
        if (this.$refs.menuTree != null) {
          this.$refs.menuTree.setCheckedKeys([]);
        }
        if (res.data != null && res.data.length > 0) {
          let checkMenuDataIds = res.data.map(e => e.menuId)
          this.menuTreeCheckData = this.getShowCheckedTreeData(this.menuTreeData, checkMenuDataIds, "menuId");
        }
        this.dialogFormVisibleMenu = true;
      })
    },

    //获取展示时的选中节点集合
    getShowCheckedTreeData(treeData, checkDatas, val) {
      //第一级
      for (let n in treeData) {
        let parentIds=[];
        if (checkDatas.indexOf(treeData[n][val]) != -1) {
          parentIds.push(treeData[n][val]);
          this.getChildrenShowCheckedData(treeData[n], checkDatas, val,parentIds);
          parentIds=[];
        } else if (treeData[n].children != null && treeData[n].children.length > 0) {
          for (let y in treeData[n].children) {
            if (checkDatas.indexOf(treeData[n].children[y][val]) != -1) {
              parentIds.push(treeData[n].children[y][val])
              this.getChildrenShowCheckedData(treeData[n].children[y], checkDatas, val,parentIds);
              parentIds=[];
            }
          }
        }
      }
      return checkDatas;
    },
    getChildrenShowCheckedData(treeData, checkDatas, val,parentIds) {
      if (treeData.children != null && treeData.children.length > 0) {
        let flag = true;
        for (let n in treeData.children) {
          if (checkDatas.indexOf(treeData.children[n][val]) == -1 && flag) {
            parentIds.push(treeData[val]);
            flag = false;
            this.delListByData(checkDatas, treeData[val],parentIds);
            parentIds=[];
          } else {
            this.getChildrenShowCheckedData(treeData.children[n], checkDatas, val,parentIds)
          }
        }
      }
    },
    //根据元素删除数组
    delListByData(delList, delData,parentIds) {
      for (let n in delList) {
        if (parentIds.indexOf(delList[n]) != -1) {
          delList.splice(n, 1)
        }
      }
    },

    saveMenuTree() {
      let sMenuTreeData = JSON.parse(JSON.stringify(this.menuTreeData))
      let checkMenuTreeDatas = this.makeCheckTree(sMenuTreeData, this.menuTreeCheckData, "menuId");
      let ids = this.getIdByTreeData(checkMenuTreeDatas, "menuId")
      let returnMenus = "";
      if (ids.length > 0) {
        for (let n in ids) {
          if (ids[n] == "000000000") {
            ids.splice(n, 1);
          }
        }
        returnMenus = ids.join(",");
      }

      batchInsertRoleMenu({roleId: this.clickRoleData.roleId, menuIds: returnMenus}).then(res => {
        if (res.code == "200") {
          this.$message({
            type: "success",
            message: "保存成功"
          })
          this.dialogFormVisibleMenu = false;
        }
      })
    },
    menuTreeCheck(val, data) {
      this.menuTreeCheckData = data.checkedKeys;
    },
    insertOrRemove(params) {
      addOrRemoveA01Permission(params).then(res => {
        if (res.code == 200) {
          let returnData = "";
          if (this.isRoot && (this.treeCheckKey.indexOf(this.checkTreeData[0].id) != -1)) {
            returnData = "root"
          } else {
            let treeDatasx = JSON.parse(JSON.stringify(this.checkTreeData));
            let screenTreeData = this.makeCheckTree(treeDatasx, this.treeCheckKey, "id");
            returnData = this.getIdByTreeData(screenTreeData, "id").join(",");
          }
          this.initUpDownTable(returnData, this.clickRoleData.roleId);
        }
      })
    },

    deptTreeClick(val, data) {
      this.deptTreeClickDatas = data
      this.firstTreeCheckedData = data.checkedKeys
    },

    initUpDownTable(deptIds, roleId) {
      this.upPageParams.pageSize = this.upPageSize;
      this.upPageParams.pageNum = this.upPageNum = 1;
      this.downPageParams.pageSize = this.downPageSize;
      this.downPageParams.pageNum = this.downPageNum = 1;
      this.upPageParams.deptIds = deptIds;
      this.upPageParams.roleId = roleId;
      this.downPageParams.deptIds = deptIds;
      this.downPageParams.roleId = roleId;
      this.getUpTableDatas(this.upPageParams);
      this.getDownTableDatas(this.downPageParams);
    },
    //获取数据权限里得table表数据
    getUpTableDatas(params) {
      getRoleBindUserByDeptIds(params).then(response => {
        this.hasPerTableData = response.rows;
        this.upTotal = response.total;
      })
    },
    getDownTableDatas(params) {
      getRoleBindUserByDeptIdsRemain(params).then(response => {
        this.nHasPerTableData = response.rows;
        this.downTotal = response.total;
      })
    },

    saveDeptAndNext() {
      if (this.deptTreeClickDatas.checkedKeys == null || this.deptTreeClickDatas.checkedKeys.length == 0) {
        this.$message({
          type: "warning",
          message: "请勾选部门后进行下一步操作"
        })
        return;
      }
      this.hasPerTableData = [];
      this.nHasPerTableData = [];
      this.upTotal = 0;
      this.downTotal = 0;
      let treeDatasx = JSON.parse(JSON.stringify(this.treeData));
      let screenTreeData = this.makeCheckTree(treeDatasx, this.deptTreeClickDatas.checkedKeys, "id");
      this.checkTreeData = JSON.parse(JSON.stringify(screenTreeData))
      this.treeCheckKey = JSON.parse(JSON.stringify(this.deptTreeClickDatas.checkedKeys));
      //保存的条件
      let deptIds = '';
      let deptParamsIds = '';
      if (this.deptTreeClickDatas.checkedKeys.indexOf(this.treeData[0].id) != -1) {
        deptIds = 'root';
        deptParamsIds = 'root'
        this.isRoot = true;
      } else {
        let deptList = this.getIdByTreeData(screenTreeData, "id");
        deptParamsIds = deptList.join(",");
        let removeRootIdsList = JSON.parse(JSON.stringify(deptList));
        deptIds = removeRootIdsList.join(",");
      }
      let roleId = this.clickRoleData.roleId;
      saveDataPermissions({roleId: roleId, deptIds: deptIds}).then(response => {
        if (response.code == 200) {
          this.$message({
            type: "success",
            message: "保存成功"
          })
        } else {
          this.$message({
            type: "error",
            message: "保存失败"
          })
        }
        this.dialogFormVisible1 = false;
      })
    },

    //从tree型数据中获取所有某个值并返回list
    getIdByTreeData(treeData, val) {
      let returnData = [];
      this.recursionGetList(returnData, treeData, val);
      return returnData;
    },
    recursionGetList(listData, treeData, val) {
      for (let n in treeData) {
        listData.push(treeData[n][val]);
        if (treeData[n].children != null && treeData[n].children.length > 0) {
          this.recursionGetList(listData, treeData[n].children, val);
        }
      }
    },
    //制作选中数据
    makeCheckTree(treeData, checkData, val) {
      let returnData = [];
      for (let n in treeData) {
        if (checkData.indexOf(treeData[n][val]) != -1) {
          returnData.push(treeData[n]);
        } else if (treeData[n].children != null) {
          let ssr = JSON.parse(JSON.stringify(treeData));
          let childrensh = [];
          for (let i in treeData[n].children) {
            let ss = JSON.parse(JSON.stringify(treeData));
            let childrenDatas = this.queryChildrenData(ss[n].children[i], checkData, val);
            if (childrenDatas != null) {
              childrensh.push(childrenDatas)

            }
          }
          if (childrensh.length != 0) {
            ssr[n].children = childrensh
            returnData.push(ssr[n])
          }
        }
      }
      return returnData;
    },
    //查询子级节点数据是否存在勾选并之制作子集节点数据
    queryChildrenData(childrenData, checkData, val) {
      let makeChildrenData = {};
      let temporaryData = JSON.parse(JSON.stringify(childrenData));
      makeChildrenData = temporaryData;
      let flag = true;
      if (checkData.indexOf(temporaryData[val]) != -1) {
        flag = false;
        return makeChildrenData;
      } else {
        makeChildrenData.children = [];
      }
      if (childrenData.children != null) {
        for (let n in childrenData.children) {
          if (checkData.indexOf(childrenData.children[n][val]) != -1) {
            flag = false;
            makeChildrenData.children.push(childrenData.children[n]);
          } else {
            let childrens = this.queryChildrenData(childrenData.children[n], checkData,val);
            if (childrens != null) {
              flag = false;
              makeChildrenData.children.push(childrens);
            }

          }

        }
      }
      if (flag) {
        return null;
      } else {
        return makeChildrenData;
      }
    },
    roleForDataPermissions(val) {
      this.clickRoleData = val;

      //初始化勾选状态
      if (this.deptTreeClickDatas.checkedKeys != null) {
        this.deptTreeClickDatas.checkedKeys = []
      }
      this.firstTreeCheckedData = []
      this.treeCheckKey = []
      this.classifyTreeCheckKey = []
      this.dataPermissionForm.type = []
      if (this.$refs.classifyTree != null) {
        this.$refs.classifyTree.setCheckedKeys([]);
      }
      if (this.$refs.tree != null) {
        this.$refs.tree.setCheckedKeys([]);
      }
      getInitDataPermission({roleId: val.roleId}).then(res => {
        if (res.code == 200) {
          if (res.data["1"] != null || res.data["6"] != null || res.data["3"] != null) {
            if (res.data["1"] == "root") {
              this.deptTreeClickDatas.checkedKeys = this.getIdByTreeData(this.treeData, "id");
              this.firstTreeCheckedData = this.getIdByTreeData(this.treeData, "id");
            } else {
              let ss = this.getShowCheckedTreeData(this.treeData, res.data["1"], "id")
              this.deptTreeClickDatas.checkedKeys = ss
              this.firstTreeCheckedData = ss
            }
            this.dataPermissionForm.type = res.data["6"]
            this.classifyTreeCheckKey = res.data["3"]

          }
          this.dialogFormVisible1 = true;
        }

      })

    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    //点击左侧树节点
    treeCleck(val) {
    },
    //获取系统中的所有的权限选项内容
    loadAllPerMissons() {
      getAllMenuTree().then(res => {
        let menuDatas=[
          {
            id:'000000000',
            menuName:'功能菜单',
            children:res.data
          }
        ]
        this.menuTreeData = menuDatas
      })
      //获取当前的权限id
      queryPermissionListMap().then(
        res => {
          this.permissionList = res.data;
        }
      )

      getDeptTree({deptType: 1,otherParam:"isSystem"}).then(response => {
        this.treeData = response.data
      })

      queryE01z1TreeGrid().then(response => {
        let classifyDatas = [
          {
            id: "000",
            label: "材料分类",
            children: response.data
          }
        ]
        this.classifyTreeData = classifyDatas;
      })
    },


    /* 分配用户弹窗 */
    distribution(val) {
      if (val.roleStatus == 0) {
        val.roleStatus = '已启用';
      } else if (val.roleStatus == 1) {
        val.roleStatus = '已禁用';
      }
      if (val.isSystem == 0) {
        val.isSystem = '内置';
      } else if (val.isSystem == 1) {
        val.isSystem = '自定义';
      }
      let title = val.roleName + '[' + val.roleStatus + ']角色详情'
      this.titletext = title
      this.distributionForm = val
      this.Menu = true
      this.roleId = val.roleId
      const _this = this
      let param = {};
      param.roleId = this.distributionForm.roleId;
      queryRemainingUserListByRoleId(param).then(
        res => {
          this.userGridData = res.data;
        }
      )
      let params = {};
      params.roleId = this.roleId;
      queryUserByRoleId(params)
        .then(function (response) {
          _this.distributionDta = response.data
        })

    },
    getFullName(row, col) {
      // 时间
      if (col.property == "createTime") {
        if (row[col.property] != null) {

          return this.moment(row[col.property]).format("YYYY-MM-DD HH:mm:ss")
        }
      }
    },
    /* grid 查询数据展示页面*/
    queryRoleList(params) {
      const _this = this
      queryRolePageList(params, {emulateJSON: true})
        .then(function (response) {
          _this.tableData = response.rows
          _this.total = response.total
          _this.loading = false
        })
    },
    /* 新增保存*/
    addRoleForm(addForm) {
      const _this = this
      let param = this.addForm;
      param.isSystem = 1;
      param.roleStatus = 0;
      this.$refs.addForm.validate(valid => {
        if (valid) {
          insertSysRole(param)
            .then(function (response) {
              _this.queryRoleList(params)
              _this.dialogFormVisible = false
              _this.addForm = {
                roleName: '',
                roleStatus: '0',
                isSystem: '1',
                remark: '',
              },
                _this.$message({
                  type: 'success',
                  message: '新增' + param.roleName + '角色成功!'
                })
            })
        } else {
          return false
        }
      })
    },
    StepsListRowClick(row) {
      this.$refs.tableGrid.clearSelection()
      if (this.rolechek.length === 0) {
        this.$refs.tableGrid.toggleRowSelection(row)
        return;
      }
      this.$refs.tableGrid.toggleRowSelection(row)
    },
    /* grid 复选框 */
    SelectionChange(val) {
      this.rolechek = val
    },
    exportExcel() {
      if (this.rolechek.length == 0) {
        this.$confirm('是否导出搜索后的数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          params.pageSize = 9999999;
          queryRolePageList(params, {emulateJSON: true})
            .then(function (response) {
              if (response.rows.length == 0) {
                this.$message.error("无数据,导出失败");
                return;
              }
              let row = {
                row: response.rows,
                status: 7
              }
              downLoadPost("/export/excel/exportClick", row)
            })
        })

      } else {
        this.$confirm('是否导出选中的数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let row = {
            row: this.rolechek,
            status: 7
          }
          downLoadPost("/export/excel/exportClick", row)
        })

      }


    },

    /* 全选删除 */
    handleDelete() {
      if (this.rolechek.length == 0) {
        this.$message({
          type: "error",
          message: "请勾选一条角色信息后点击删除"
        })
        return;
      }
      const _this = this
      let userIdList = []
      for (let i = 0; i < this.rolechek.length; i++) {
        if(this.rolechek[i].isSystem==0){
          this.$message({
            type: "warning",
            message: "系统内置角色无法删除"
          })
          return;
        }
        userIdList.push(this.rolechek.userId)
      }
      if (this.rolechek === '') {
        this.$message({
          message: '请选择您要删除的内容',
          type: 'warning'
        })
      } else {
        this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            deleteRoleById({param: JSON.stringify(this.rolechek)})
              .then(function (response) {
                _this.queryRoleList(params)
                _this.$message({
                  type: 'success',
                  message: '删除角色成功!'
                })
              })
          })
      }
    },
    // 编辑弹窗
    upData() {
      if (this.rolechek.length != 1) {
        this.$message({
          type: "warning",
          message: "请勾选一条数据"
        })
      } else if(this.rolechek[0].isSystem==0){
        this.$message({
          type: "warning",
          message: "系统内置角色无法修改"
        })
      }else {
        this.upDateForm = JSON.parse(JSON.stringify(this.rolechek[0]));
        this.dataPermissionsW = !this.dataPermissionsW
      }
    },
    /* 编辑保存 */
    upDateFormData(addForm) {

      let param = this.upDateForm;
      let _this = this;
      _this.$refs[addForm].validate(valid => {
        if (valid) {
          updateRoleById(param)
            .then(function (response) {
              _this.queryRoleList(params)
              _this.dataPermissionsW = false
              _this.$message({
                message: '更新成功',
                type: 'success'
              })

            })
        }
      })
    },
    assigningPermission(val) {
      this.dialogPermissionFormVisible = true;
      if (val.roleStatus == 0) {
        val.roleStatus = '已启用';
      } else {
        val.roleStatus = '已禁用';
      }
      if (val.isSystem == 0) {
        val.isSystem = '内置';
      } else {
        val.isSystem = '自定义';
      }
      let title = val.roleName + '[' + val.roleStatus + ']权限详情'
      this.windowtext = title;
      this.distributionForm = val;
      this.roleId = val.roleId

      this.remainPermissonTable = true;
      let param = {};
      param.roleId = this.distributionForm.roleId;
      queryRemainingPermissionListByRoleId(param).then(
        res => {
          this.permissionGridData = res.data;
        }
      )
      const _this = this
      let params = {};
      params.roleId = this.roleId;
      queryPermissionListByRoleId(params)
        .then(res => {
          _this.permissionsData = res.data
        })
    },

    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
    },
    handleAdd() {
      this.dialogFormVisible = true;
      this.addForm={
        roleName: '',
          roleStatus: '0',
          isSystem: '1',
          remark: '',
      }
    },
    pageCurrent(val) {
      this.pageNum = val
      const params = {
        pageNum: val,
        pageSize: this.pageSize
      }
      this.queryRoleList(params)
    },
    pageSizes(val) {
      this.pageSize = val
      const params = {
        pageNum: this.pageNum,
        pageSize: val
      }
      this.queryRoleList(params)
    },
    /* 上部搜索 */
    search() {
      let params = {
        pageNum: 1,
        pageSize: 20,
        roleName: this.queryForm.roleName ,/* JSON.stringify(this.queryForm.roleName) */
        isSystem: this.queryForm.isSystem,
        schoolId: this.queryForm.deptId,
      }
      this.queryRoleList(params)
    },
    reset() {
      this.queryForm.roleName = '';
      this.queryForm.isSystem = '';
      this.queryRoleList(params);
    }
  },
  watch: {
    tableData: {
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(() => {
          _this.$refs.tableGrid.doLayout();
        })
      },
      deep: true,
      // immediate: true
    },
  },
}
</script>
<style lang="scss" scoped>
.el-col.el-col-24.el-col-xs-24 {
  padding: 10px;
}
.el-dialog__body {
  height: 500px;
  display: block;
}

.app-return {
  overflow: auto;
  height: calc(100vh - 86px);
}

.el-input__inner {
  width: 200px;
}

.el-form-item__content {
  width: 200px;
}

.el-input {
  width: 200px;
}

.inputW {
  width: 200px;
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

.dialog-state {
  position: relative;
  height: 350px;

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

//设置统一表头
  /deep/ .el-table tbody tr:hover > td {
    background-color: #ecf5ff !important
  }

// /deep/.el-table__header-wrapper {
   //    padding: 0;
   //    height: 40px;
   //  }

.search-form {
  width: 100%;
  /*background:rgba(204, 204, 204, 0.18);*/
  padding: 10px;

  display: flex;
/deep/ .el-form-item {
  margin-bottom: 0 !important;
}
}
.page {
  float: right;
}

/deep/ .el-table .el-table__header-wrapper th{
  height:31px;
}
/deep/ .el-table__fixed-right-patch {
  background-color:#F8F8F9;
}

</style>

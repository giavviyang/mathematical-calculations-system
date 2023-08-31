<template>
  <div class="app-dispose">
    <el-row>
      <!-- 左侧树节点信息 -->
      <el-col :span="24" style="height:100%">
        <!-- 右侧展示查询数据 -->
        <el-col :span="20" style="height: calc(100vh - 86px);width:100%;padding: 10px">
          <el-row :gutter="10" ref="searchForm" style="margin: 0">
            <el-form
              ref="queryForm"
              class="search-form"
              :model="queryForm"
              :inline="true"
              style="background:rgba(204, 204, 204, 0.18)"
              v-hasPermi="['parameter:user:search']"
              @submit.native.prevent
            >
              <el-form-item label="所属学校" @keyup.enter.native="onEnterSearch" prop="deptId">
                <el-select style="width: 150px" v-model="queryForm.deptId" placeholder="请选择所属学校" clearable
                           class="inputW" size="mini">
                  <el-option v-for="item in schools" :key="item.id" :label="item.deptName" :value="item.id"/>
                </el-select>
              </el-form-item>
              <el-form-item label="教师姓名" @keyup.enter.native="onEnterSearch" prop="userName">
                <el-input v-model="queryForm.userName" size="mini" placeholder="请输入教师姓名" clearable
                          style="width: 150px"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini"
                           @click="search">搜索
                </el-button>
              </el-form-item>
            </el-form>

          </el-row>
          <el-col :span="24" style="padding: 10px 0;">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="addData">新增
            </el-button>
            <el-button type="primary" icon="el-icon-edit" size="mini"
                       @click="upData">编辑
            </el-button>
            <el-button type="primary" icon="el-icon-delete" size="mini"
                       @click="handleDelete">删除
            </el-button>
            <el-button type="primary" icon="el-icon-user-solid" size="mini"
                       @click="SpecifyRoleWindow">指定角色
            </el-button>
            <el-button type="primary" icon="el-icon-help" size="mini" v-hasPermi="['parameter:user:rePassword']"
                       @click="resetPwd">重置密码
            </el-button>
            <el-button type="primary" icon="el-icon-download" size="mini" v-hasPermi="['parameter:user:export']"
                       @click="exportExcel">导出
            </el-button>
          </el-col>
          <el-table
            row-key="id"
            align="left"
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            class="table-style"
            :header-cell-style="headClass"
            tooltip-effect="light"
            :height="tableHeight"
            empty-text=" "
            size="mini"
            ref="tableGrid"
            style="width: 100%"
            :data="userTableData"
            @row-click="StepsListRowClick"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="40" align="center"/>
            <el-table-column type="index" label="序号" align="center" width="55" show-overflow-tooltip
                             :index="customIndex"/>
            <el-table-column show-overflow-tooltip align="center" v-for="(item, index) in col"
                             :formatter="item.formatter"
                             :v-if="col[index].isCol" :key="index" :prop="item.prop" :label="item.label"/>
            <el-table-column align="center" fixed="right" label="操作" width="220">
              <template slot-scope="scope">
                <el-button type="text" size="mini"
                           @click="roleForDataPermissions(scope.row)">数据权限
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="block" style="float: right;margin-top: 5px">
            <el-pagination
              :page-sizes="[20, 50, 100, 200]"
              :page-size="pageSize"
              :current-page="pageNum"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="pageSizeS"
              @current-change="pageCurrent"
            />
          </div>
        </el-col>
      </el-col>
      <!-- 新增用户信息 -->
      <el-dialog
        :visible.sync="dialogFormVisible"
        :destroy-on-close="true"
        class=" dialog-style dialog-basic"
        title="新增教师信息"
        width="60%">
        <div>
          <el-form
            ref="form"
            style="margin-left: 10%;margin-right: 10%"
            :model="form"
            :inline="true"
            :rules="rules"
            label-width="120px">
            <el-form-item label="教师姓名" :label-width="formLabelWidth" prop="userName">
              <el-input v-model="form.userName" clearable size="mini" placeholder="请输入教师姓名" style="width:150px"/>
            </el-form-item>
            <el-form-item label="教师编号" :label-width="formLabelWidth" prop="userNumber">
              <el-input v-model="form.userNumber" clearable size="mini" placeholder="请输入教师编号" style="width:150px"/>
            </el-form-item>
            <el-form-item label="登录账号" :label-width="formLabelWidth" prop="loginCode">
              <el-input v-model="form.loginCode" clearable size="mini" placeholder="请输入登录账号" style="width:150px"/>
            </el-form-item>
            <el-form-item label="所属学校" :label-width="formLabelWidth" prop="deptId">

              <el-select style="width: 150px" v-model="form.deptId" placeholder="请选择所属学校" clearable class="inputW"
                         size="mini">
                <el-option v-for="item in schools" :key="item.id" :label="item.deptName" :value="item.id"/>
              </el-select>
            </el-form-item>
            <el-form-item label="手机号" :label-width="formLabelWidth" prop="iphone">
              <el-input v-model="form.iphone" size="mini" type="text" placeholder="请输入手机号" clearable
                        style="width:150px"/>
            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth" prop="remark">
              <el-input v-model="form.remark" size="mini" type="textarea" placeholder="请输入备注" clearable
                        style="width:150px"/>
            </el-form-item>

            <el-form-item label="用户状态" :label-width="formLabelWidth">
              <el-radio-group style="" v-model="form.userStatus">
                <el-radio label="0">启用</el-radio>
                <el-radio label="1">禁用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>

        </div>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="exit()">取 消</el-button>
          <el-button size="mini" type="primary" @click="addForm('form')">保 存</el-button>
        </div>
      </el-dialog>

      <!-- 修改用户信息 -->
      <el-dialog :visible.sync="dialogFormVisible1" :destroy-on-close="true"
                 class=" dialog-style dialog-basic"
                 title="编辑教师信息" width="60%" resize
                 remember>
        <div>
          <el-form ref="editorForm" style="margin-left: 10%;margin-right: 10%" :model="editorForm" :inline="true"
                   :rules="rules" label-width="120px">
            <el-form-item label="教师姓名" :label-width="formLabelWidth" prop="userName">
              <el-input clearable v-model="editorForm.userName" placeholder="请输入教师姓名" size="mini" style="width:150px"/>
            </el-form-item>
            <el-form-item label="教师编号" :label-width="formLabelWidth" prop="userNumber">
              <el-input clearable v-model="editorForm.userNumber" placeholder="请输入教师编号" size="mini"
                        style="width:150px"/>
            </el-form-item>
            <el-form-item label="登录账号" :label-width="formLabelWidth" prop="loginCode">
              <el-input clearable v-model="editorForm.loginCode" size="mini" placeholder="请输入登录账号" style="width:150px"/>
            </el-form-item>
            <el-form-item label="所属学校" :label-width="formLabelWidth" prop="deptId">

              <el-select style="width: 150px" v-model="editorForm.deptId" placeholder="请选择所属学校" clearable class="inputW"
                         size="mini">
                <el-option v-for="item in schools" :key="item.id" :label="item.deptName" :value="item.id"/>
              </el-select>
            </el-form-item>
            <el-form-item label="手机号" :label-width="formLabelWidth" prop="iphone">
              <el-input clearable v-model="editorForm.iphone" placeholder="请输入手机号" size="mini" type="text"
                        style="width:150px"/>
            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth" prop="remark">
              <el-input clearable v-model="editorForm.remark" placeholder="请输入备注" size="mini" type="textarea"
                        style="width:150px"/>
            </el-form-item>
            <el-form-item label="用户状态" :label-width="formLabelWidth">
              <el-radio-group v-model="editorForm.userStatus">
                <el-radio label="0">启用</el-radio>
                <el-radio label="1">禁用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogFormVisible1=false">取 消</el-button>
          <el-button size="mini" type="primary" @click="editorFormUpload('editorForm')">保 存</el-button>
        </div>
      </el-dialog>
      <el-dialog title="指定角色" :visible.sync="dialogtableVisible1" :destroy-on-close="true" :append-to-body="true"
                 :close-on-click-modal="false"
                 width="90%"
                 class=" dialog-style dialog-basic">
        <div>
          <div style="margin: 10px 20px;font-size: 14px;font-weight: 600">已有角色列表：</div>
          <div style="height:calc(16vh);margin: 0 20px;">
            <el-table
              class="table-style"
              tooltip-effect="light"
              height="100%"
              size="mini"
              stripe
              :header-cell-style="headClass"
              empty-text=" "
              ref="leftTableRole"
              :data="SpecifyRole"
              @selection-change="SpecifyRoleChange"
              border>
              <el-table-column type="index" width="50" align="center" label="序号"/>
              <el-table-column show-overflow-tooltip v-for="(item, index) in SpecifyRoleCol" align="center"
                               :key="index"
                               :property="item.property" :label="item.label"/>
              <el-table-column label="操作" align="center" width="100" fixed="right">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="leftDel(scope.row,scope.$index)">删除</el-button>
                </template>
              </el-table-column>

            </el-table>
          </div>
        </div>
        <div>
          <div style="margin: 10px 20px;font-size: 14px;font-weight: 600">新增角色列表：</div>
          <el-form @submit.native.prevent
                   ref="queryRoleForm"
                   :model="queryRoleForm"
                   :inline="true"
                   class="addRoleForm">
            <el-form-item label="角色描述" prop="roleSummary">
              <el-input @keyup.enter.native="SpecifyRoleWindow" size="mini" v-model="queryRoleForm.roleSummary"
                        placeholder="请输入角色名称" clearable/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="SpecifyRoleWindow">搜索</el-button>
            </el-form-item>
          </el-form>
          <div style="height:calc(40vh);margin: 0 20px;">
            <el-table
              class="table-style"
              tooltip-effect="light"
              stripe
              :header-cell-style="headClass"
              size="mini" height="100%"
              empty-text=" "
              ref="rightTableRole"
              :data="rightSpecifyRole"
              @selection-change="SpecifyRoleChange" border>
              <el-table-column type="index" width="50" align="center" label="序号"/>
              <el-table-column show-overflow-tooltip v-for="(item, index) in SpecifyRoleCol" align="center"
                               :key="index"
                               :property="item.property" :label="item.label"/>
              <el-table-column label="操作" align="center" width="100" fixed="right">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="rightAdd(scope.row,scope.$index)">添加</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogtableVisible1=false">取 消</el-button>
          <el-button size="mini" type="primary" @click="SpecifyRoleSave('editorForm')">保 存</el-button>
        </div>
      </el-dialog>
      <!-- 权限分配 -->
      <el-dialog :visible.sync="dataPermissionDialog"
                 :append-to-body="true"
                 :close-on-click-modal="true"
                 class=" dialog-style dialog-basic treeDataDialog" title="数据权限分配" width="30%">
        <div class="tree tree-style" style="height:420px;margin:0 auto">
          <el-tree node-key="id" empty-text=" " border
                   :data="treeDataPermission" :props="defaultProps" ref="treeDataPermission"
                   icon-class="el-icon-folder-opened" :filter-node-method="filterNode"
                   :highlight-current="true"
                   :expand-on-click-node="false" show-checkbox @check="deptTreeClick"
                   :default-checked-keys="treeDataPermissionChecked"
                   default-expand-all>
                          <span class="custom-tree-node" slot-scope="{ node, data }">
                            <span>{{ node.label }}</span>
                              <span v-show="node.level===3 && node.checked===true" class="courseTeacher">
                             任课教师：
                               <el-radio-group v-model="data.isTeacher">
                                 <el-radio :label="1">是</el-radio>
                                 <el-radio :label="0">否</el-radio>
                               </el-radio-group>
                            </span>
                          </span>
          </el-tree>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dataPermissionDialog=false">取 消</el-button>
          <el-button size="mini" type="primary" @click="saveDataPermission">保 存</el-button>
        </div>
      </el-dialog>

    </el-row>
  </div>
</template>

<script>
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import {queryUserDeptTree, userGrid, saveUserDataPermissions} from '@/api/system/user'
import {userTree, getRoleListByUserId} from '@/api/system/user'
import {deleteUser, resetPassword, prohibitUser} from '@/api/system/user'
import {addUser, getAllRoleList} from '@/api/system/user'
import {insertSysUserRoles} from '@/api/system/user'
import {updateUserByIdNew} from '@/api/system/user'
import {queryPermissionListMap} from '@/api/system/permissions'
import {queryRoleListMap} from '@/api/system/role'
import {getDeptListByLoginUser, getDeptTree} from '@/api/system/dept'
import {downLoadPost} from '@/downLoad/filedownload'
import selectTree from '@/views/components/tree/tree.vue'
import {
  queryDeptMap
} from '@/api/usual/parameter/receives-paramenter'
import ContainerTop from "@/components/public/MainBody/ContainerTop";

const params = {
  pageNum: 1,
  pageSize: 20
}
export default {
  // name: "用户管理",
  components: {ContainerTop, Treeselect, selectTree},
  watch: {
    dialogFormVisible: {
      handler(newName, oldName) {
        console.info(this.dialogFormVisible)
      }
    },
    filterText(val) {
      this.$refs.tree4.filter(val);
    },
    userTableData: {
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(() => {
          if (_this.$refs.tableGrid != null) {
            _this.$refs.tableGrid.doLayout();
          }
        })
      },
      deep: true,
      immediate: true
    },
    rightSpecifyRole: {
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(() => {
          if (_this.$refs.rightTableRole != null) {
            _this.$refs.rightTableRole.doLayout();
          }
        })
      },
      deep: true,
      immediate: true
    },
    SpecifyRole: {
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(() => {
          if (_this.$refs.leftTableRole != null) {
            _this.$refs.leftTableRole.doLayout();
          }
        })
      },
      deep: true,
      immediate: true
    }
  },
  data() {
    let loginCodeTest = /^[0-9a-zA-Z]+$/;
    let isloginCodeTest = (rule, value, callback) => {
      if (!loginCodeTest.test(value)) {
        return callback(new Error('只能输入数字和字母!'))
      } else {
        callback();
      }
    };
    let checkPhone=(rule,value,callback)=>{
      let reg=/^1[3456789]\d{9}$/
      if(!reg.test(value)){
        callback(new Error('请输入11位手机号'))
      }else {
        callback()
      }
    }
    return {
      dataPermissionDialog: false,
      treeDataPermissionChecked: [],
      // treeDataPermissionCheckedNodes: [],
      treeDataPermission: [],
      deptMap: {},
      tableRefs: [],
      ischecked: true,
      selectTreeWidth: "100px",
      selectSTyle: {
        width: "150px",
        background: '#ffffff'
      },
      treeStyle: {
        width: "150px",
        height: "200px"
      },
      userTypeOptions: [
        {label: "普通用户", value: "1"},
        {label: "临时用户", value: "2"}
      ],
      rightSpecifyRole: [],
      treeHeight: "700px",
      tableHeight: "73%",
      searchForm: "searchForm",
      searchRowHeight: '',
      treeShow: true,
      rightStyle: {
        width: '84%',padding: ' 10px', height: 'calc(100vh - 86px)'
      },
      leftStyle: {
        width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      leftStyleName: {
        width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      rightStyleName: {
        width: '84%',  padding: ' 10px', height: 'calc(100vh - 86px)'
      },
      leftStyle1: {
        width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      rightStyle1: {
        width: '99%',  padding: ' 10px', height: 'calc(100vh - 86px)'
      },
      picClassname: "shrinkageLeft",
      leftTree1: true,
      filterText: "",
      // deptOptions: [],
      /* 左侧树 */
      treeData: [],

      value: [],
      // 部门选择下拉
      dropDownData: [],
      // 下拉选权限信息
      permissionList: [],
      // 下拉选角色信息
      roleList: [],
      /* 复选框选中*/
      gridCheck: [],
      total: 0,
      pageNum: 1,
      pageSize: 20,
      loading: true,
      /* 绑定权限数据 */
      permissionsData: [],
      /* 绑定权限题名 */
      gridDataCol: [
        {property: 'permissionName', label: '权限名称'},
        {property: 'permissionDataSummary', label: '数据权限摘要'},
        {property: 'permissionMenuSummary', label: '功能权限摘要'},
        {property: 'permissionStatus', label: '权限状态'}
      ],
      /* 指定角色数据 */
      SpecifyRole: [],
      /* 指定角色题名 */
      SpecifyRoleCol: [
        {property: 'roleName', label: '角色名称'},
        {property: 'roleSummary', label: '角色描述'},
      ],
      defaultProps: {
        children: 'children',
        label: 'deptName',
        id: 'id',
      },
      dialogFormVisible: false,
      dialogFormVisible1: false,
      dialogtableVisible: false,
      dialogtableVisible1: false,
      gridCheckDatas: [],
      schools: [],
      queryRoleForm: {
        schoolId: '',
        roleSummary: '',
      },
      /* 新增数据 */
      form: {
        userId: '',
        userName: '',
        userNumber: '',
        loginCode: '',
        deptId: null,
        createBy: '0',
        userStatus: '0',
        remark: '',
        userType: "1",
        loginPasswordDate: "",
      },
      deptIdOrder: "",
      permissionIds: [],
      roleIds: [],
      /* 编辑数据 */
      editorForm: {
        userName: "",
        userNumber: "",
        loginCode: "",
        deptId: "",
        remark: "",
        userStatus: 0,
        userId: "",
        userType: "",
        loginPasswordDate: "",
      },
      formLabelWidth: '120px',
      showSearchBox: false,
      queryForm: {
        userName: '',
      },
      col: [
        {label: '教师姓名', prop: 'userName', isCol: true},
        {label: '手机号', prop: 'iphone', isCol: true},
        {label: '登录账号', prop: 'loginCode', isCol: true},
        {label: '学校', prop: 'deptName', isCol: true},
        {label: '角色身份', prop: 'roleName', isCol: true},
        {
          label: '用户状态', prop: 'userStatus', isCol: true, formatter: function (row, column, cellValue) {
            return cellValue == "0" ? "启用" : "禁用";
          }
        },
      ],
      userTableData: [],
      rules: {
        userName: [{required: true, message: '请输入用户姓名', trigger: 'blur'},
          {min: 0, max: 5, message: '输入内容不得超过5个字符', trigger: 'blur'}],
        loginCode: [
          {required: true, message: '请输入登录账号', trigger: 'blur'},
          {min: 0, max: 16, message: '输入内容不得超过16个字符', trigger: 'blur'},
          {validator: isloginCodeTest}
        ],
        userNumber: [{min: 0, max: 16, message: '输入内容不得超过16个字符', trigger: 'blur'}],
        deptId: [{required: true, message: '请选择部门', trigger: 'blur,change'}],
        remark: [{min: 0, max: 40, message: '输入内容不得超过40个字符', trigger: 'blur'}],
        userStatus: [{required: true, message: '请选择用户状态', trigger: 'blur'}],
        iphone:[
          {required: true, message: '请输入手机号', trigger: 'blur'},
          {validator:checkPhone}
        ]
      },
      permissionsCheck: '',
      // userId: '',
      deptNormalizer(node) {
        // 去掉children=[]的children属性
        if (node.children && !node.children.length) {
          delete node.children
        }
        return {
          id: node.id,
          label: node.deptName,
          children: node.children
        }
      }
    }
  },

  mounted() {
    this.userTabeGrid(params)
    this.getSchools();
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
    this.keyupSubmit();
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
    saveDataPermission() {
      let arr = [];
      let arr1 = this.$refs.treeDataPermission.getCheckedNodes(false, true);
      if (arr1) {
        arr1.forEach(item => {
          let obj = {};
          obj.dataPermissionId = item.id;
          obj.roleId = this.userId;
          obj.isTeacher = item.isTeacher;
          arr.push(obj)
        })
        saveUserDataPermissions({sysRoleDataString: JSON.stringify(arr), userId: this.userId}).then(res => {
          console.log(res)
          if (res.code === 200) {
            this.$message({
              message: '保存成功',
              type: 'success'
            })
            this.dataPermissionDialog=false;
          }
        })
      }
    },
    deptTreeClick(val, data) {
      console.log(val, data)
      this.treeDataPermissionChecked = data.checkedKeys;
      console.log('1111', data)
    },
    roleForDataPermissions(val) {
      console.log(val)
      this.dataPermissionDialog = true;
      this.userId = val.userId;
      queryUserDeptTree({userId: val.userId}).then(res => {
        console.log(res)
        this.treeDataPermission = res.data.list;
        this.treeDataPermissionChecked = res.data.listIds;

      })
    },
    //获取学校信息
    getSchools() {
      getDeptListByLoginUser().then(reopnse => {
        this.schools = reopnse.data;
      })
    },
    //自定义序号
    customIndex(index) {
      return (this.pageNum - 1) * (this.pageSize) + (index + 1);
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
    leftDel(val, index) {
      let leftdata = JSON.parse(JSON.stringify(this.SpecifyRole));
      let rightdata = JSON.parse(JSON.stringify(this.rightSpecifyRole));
      leftdata.splice(index, 1);
      this.SpecifyRole = leftdata;
      rightdata.push(val);
      this.rightSpecifyRole = rightdata;

    },
    rightAdd(val, index) {
      let leftdata = JSON.parse(JSON.stringify(this.SpecifyRole));
      let rightdata = JSON.parse(JSON.stringify(this.rightSpecifyRole));

      leftdata.push(val);
      this.SpecifyRole = leftdata;
      rightdata.splice(index, 1);
      this.rightSpecifyRole = rightdata;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.deptName.indexOf(value) !== -1;
    },

    // 获取系统中的所有的权限选项内容
    loadAllPerMissons() {
      // 获取当前的权限id
      queryPermissionListMap().then(
        res => {
          this.permissionList = res.data
        }
      )
    },
    // 将当前所有的角色数据
    loadAllRoles() {
      queryRoleListMap().then(
        res => {
          this.roleList = res.data
        }
      )
    },
    /* gird数据请求 */
    userTabeGrid(params) {
      const _this = this
      userGrid(params, {emulateJSON: true})
        .then(function (response) {
          _this.userTableData = response.rows
          _this.total = response.total
          _this.loading = false
        })
    },
    /* 权限复选框选中*/
    permissionsChange(val) {
      this.permissionsCheck = val
    },
    /* 指定角色弹窗 */
    SpecifyRoleWindow() {
      console.log(this.gridCheckDatas)
      if (this.gridCheckDatas.length != 1) {
        this.$message({
          type: "warning",
          message: "请勾选一条数据"
        })
        return;
      }
      let val = this.gridCheckDatas[0];
      this.userId = val.userId
      console.log(this.userId)
      this.dialogtableVisible1 = true
      const _this = this
      _this.queryRoleForm.schoolId = val.deptId
      _this.SpecifyRole = []
      _this.rightSpecifyRole = [];
      getRoleListByUserId({userId: this.userId}, {emulateJSON: true})
        .then(function (response) {
          if (response.code == 200) {
            _this.SpecifyRole = response.data
            getAllRoleList(_this.queryRoleForm).then(response1 => {
              let rightData = [];
              let data = response1.data;
              for (let n in data) {
                let flag = true;
                for (let y in _this.SpecifyRole) {
                  if (_this.SpecifyRole[y].roleId == data[n].roleId) {
                    flag = false;
                  }
                }
                if (flag) {
                  rightData.push(data[n]);
                }
              }
              _this.rightSpecifyRole = rightData;
              this.userId = '';
            })
          }


        })

    },
    /* 指定角色复选框选中 */
    SpecifyRoleChange(val) {
      this.permissionsCheck = val
    },
    /* 指定角色保存 */
    SpecifyRoleSave() {
      let roleIds = "";
      let userId = this.gridCheckDatas[0].userId;
      if (this.SpecifyRole.length != 0) {
        roleIds = this.SpecifyRole.map(item => item.roleId).join(",");
      }
      insertSysUserRoles({userId: userId, otherParam: roleIds}).then(response => {
        if (response.code == 200) {
          this.$message({
            type: "success",
            message: "指定成功"
          })
          this.dialogtableVisible1 = false;
        } else {
          this.$message({
            type: "error",
            message: "指定失败"
          })
        }
      })
    },
    /* 左侧树 请求*/
    leftUserTree() {
      queryDeptMap().then(response => {
        this.deptMap = response.data
      })
      const _this = this
      _this.loading = true
      userTree({deptType: 1})
        .then(function (response) {

          _this.treeData = response.data;

        })
    },
    /* 左侧树 节点点击事件 */
    leftTreeChange(data) {
      const _this = this

      this.form.deptId = data.id
      this.deptIdOrder = data.id;
      // params1.userName = this.queryForm.userName;
      params.deptId = data.id;
      params.userName = this.queryForm.userName;
      // if (data.id == "000") {
      userGrid(params, {emulateJSON: true})
        .then(function (response) {
          _this.userTableData = response.rows
          _this.total = response.total
          _this.loading = false
        })
      // }
    },

    resetPwd() {
      if (this.gridCheck.length > 0) {
        let ids = this.gridCheck.join(",");
        let message = "";
        for (let n in this.gridCheckDatas) {
          message = message + this.gridCheckDatas[n].userName + ","
        }
        message = message.substr(0, message.length - 1);
        this.$confirm('此操作将重置' + message + '账户的密码为"das000", 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            resetPassword({otherParam: ids})
              .then(response => {
                if (response.code == 200) {
                  this.$message({
                    type: 'success',
                    message: '重置成功!'
                  })
                } else {
                  this.$message({
                    type: 'error',
                    message: '重置失败!'
                  })
                }
              })
          })
      } else {
        this.$message({
          type: "warning",
          message: "请勾选人员条目"
        })
      }
    },

    // 复选框事件
    handleSelectionChange(val) {
      this.gridCheckDatas = val;
      this.gridCheck = val.map(item => item.userId)
    },
    //新增
    addData(){
      this.dialogFormVisible = true;
      this.form={
        userId: '',
          userName: '',
          userNumber: '',
          loginCode: '',
          deptId: null,
          createBy: '0',
          userStatus: '0',
          remark: '',
          userType: "1",
          loginPasswordDate: "",
      }
    },
    // 编辑
    upData() {
      if (this.gridCheckDatas.length != 1) {
        this.$message({
          type: "warning",
          message: "请勾选一条数据"
        })
      } else {
        console.info(this.gridCheckDatas[0])
        let params = {
          userName: this.gridCheckDatas[0].userName,
          userNumber: this.gridCheckDatas[0].userNumber,
          loginCode: this.gridCheckDatas[0].loginCode,
          deptId: this.gridCheckDatas[0].deptId,
          remark: this.gridCheckDatas[0].remark,
          iphone: this.gridCheckDatas[0].iphone,
          userStatus: 0,
          userId: this.gridCheckDatas[0].userId,
          userType: this.gridCheckDatas[0].userType.toString(),
          // loginPasswordDate: this.gridCheckDatas[0].loginPasswordDate,
        };
        this.editorForm = params;
        // this.editorForm = this.gridCheckDatas[0];
        if (this.gridCheckDatas[0].userStatus == 1) {
          this.editorForm.userStatus = "1";
        } else {
          this.editorForm.userStatus = "0";
        }
        this.dialogFormVisible1 = true
      }
    },
    // 行删除
    lineDelete(val) {
      const _this = this
      console.info(val)
      if (val.userStatus == '已启用') {
        val.userStatus = '0'
      } else {
        val.userStatus = '1'
      }
      this.$confirm('此操作将永久删除该账户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deleteUser(val)
            .then(function (response) {
              _this.userTabeGrid(params)
              _this.$message({
                type: 'success',
                message: '删除成功!'
              })
            })
        })
    },
    // 多选删除
    handleDelete() {
      const _this = this
      const param = {}
      param.jsonStr = this.gridCheck.toString()
      if (this.gridCheck.length == 0) {
        this.$message({
          showClose: true,
          message: '请选择您要删除的用户信息',
          type: 'warning'
        })
      } else {
        this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            // deleteUser({data:this.gridCheck.toString()})
            deleteUser(param)
              .then(function (response) {
                if (response.code == 200) {

                  params.deptId = _this.deptIdOrder
                  params.userName = _this.queryForm.userName;
                  _this.userTabeGrid(params)
                  _this.$message({
                    type: 'success',
                    message: '删除成功!'
                  })
                } else {
                  this.$message({
                    message: '删除失败',
                    type: 'error'
                  });
                }
              })
          })
      }
    },
    // 分页
    pageCurrent(val) {
      this.pageNum = val
      params.pageNum = val
      params.pageSize = this.pageSize;
      this.userTabeGrid(params)
    },
    pageSizeS(val) {
      this.pageSize = val
      params.pageSize = val;
      params.pageNum = this.pageNum;
      this.userTabeGrid(params)
    },
    exportExcel() {
      let _this = this;
      if (this.gridCheckDatas.length == 0) {
        this.$confirm('是否导出搜索后的用户数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let searchText = this.queryForm.userName;
          if (searchText != "") {
            params.userName = searchText;
          }
          params.pageSize = 999999;
          const _this = this
          userGrid(params, {emulateJSON: true})
            .then(function (response) {
              if (response.rows.length == 0) {
                this.$message({
                  message: '无数据,导出失败',
                  type: 'error'
                });
                return;
              }
              let row = {
                row: response.rows,
                status: 8
              }
              downLoadPost("/export/excel/exportClick", row, this)
            })
        })

      } else {
        this.$confirm('是否导出选中的用户数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let row = {
            row: this.gridCheckDatas,
            status: 8
          }
          downLoadPost("/export/excel/exportClick", row, this)
        })

      }


    },
    onRoleEnterSearch() {
      this.SpecifyRoleWindow();
    },
    onEnterSearch() {
      this.search();
    },
    /* 上部搜索 */
    search() {
      let userName = this.queryForm.userName
      let deptId = this.queryForm.deptId
      params.userName = userName;
      params.deptId = deptId;
      params.pageNum = 1;
      this.pageNum = 1;
      this.userTabeGrid(params);
    },
    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
    },
    reset() {
      this.queryForm.userName = ''
      this.queryForm.loginCode = ''
      this.userTabeGrid(params);
    },
    // 新增保存
    addForm(form) {
      const _this = this
      let param = this.form;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          addUser(param)
            .then(function (response) {
              if (response.code == 200) {
                params.deptId = _this.deptIdOrder;
                params.userName = _this.queryForm.userName;
                _this.userTabeGrid(params)
                _this.dialogFormVisible = false
                _this.form = {
                  userId: '',
                  userName: '',
                  userType: '',
                  loginCode: '',
                  deptId: _this.form.deptId,
                  createBy: '0',
                  userStatus: '0',
                  remark: ''
                };
                _this.$message({
                  message: "新增成功",
                  type: 'success'
                })
              } else {
                this.$message({
                  message: '新增失败',
                  type: 'error'
                });
              }

            })
        } else {
          return false;
        }
      })
    },
    exit() {
      this.dialogFormVisible = false;
      this.form = {
        userId: '',
        userName: '',
        userType: '',
        loginCode: '',
        deptId: this.form.deptId,
        createBy: '0',
        userStatus: '0',
        remark: ''
      };
    },
    StepsListRowClick(row) {
      this.$refs.tableGrid.clearSelection()
      if (this.gridCheckDatas.length === 0) {
        this.$refs.tableGrid.toggleRowSelection(row)
        return;
      }
      this.$refs.tableGrid.toggleRowSelection(row)
    },
    getFullNameWapper(row, field) {
      let col = {
        property: field
      }
      return this.getFullName(row, col);
    },
    getFullName(row, col) {
      // 部门
      if (col.property == 'deptId') {
        if (this.deptMap[row.deptId] == null) {
          return ''
        } else {
          return this.deptMap[row.deptId]
        }
      }
      return row[col.property]
    },
    /* 编辑 */
    editorFormUpload(editorForm) {
      const _this = this
      this.$refs[editorForm].validate(valid => {
        if (valid) {
          updateUserByIdNew(this.editorForm)
            .then(function (response) {
              if (response.code == 200) {
                params.deptId = _this.deptIdOrder;
                params.userName = _this.queryForm.userName;
                _this.userTabeGrid(params)
                _this.dialogFormVisible1 = false
                _this.$message({type: 'success', message: '修改成功'})

              } else {
                this.$message({
                  message: '修改失败',
                  type: 'error'
                });
              }
            })
        } else {
          return false
        }
      })
    }
  },
}
</script>
<style lang="scss" scoped>

#inputClear {
  /deep/ .el-input__suffix {
    top: 11px;
  }
}

/deep/ .addRoleForm {
  background: rgba(204, 204, 204, 0.18);
  display: flex;
  align-items: center;
  height: 50px;
  //margin-bottom: 10px;
  margin: 0 20px 10px 20px;

  .el-form-item {
    margin-bottom: 0;
    margin-left: 20px;
  }
}

.search-form {
  width: 100%;
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

.tree {
  width: 100%;
  height: calc(100vh - 135px);
  padding: 20px;
}

.verticalBar {
  width: 2px;
  height: 354px;
  background: #cccccc;
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


ul {
  padding: 0;
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

.treeDataDialog {
  .el-dialog__body {
    .el-tree {
      color: #606266;
      /deep/ .el-tree-node__children {
        .el-tree-node {
          .custom-tree-node {
            .courseTeacher {
              color: red;
              display: inline-block;
              margin-left: 50px;

              .el-radio {
                margin-right: 15px;
              }
            }
          }
        }
      }

    }

  }
}
</style>

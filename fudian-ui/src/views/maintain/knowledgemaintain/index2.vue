<template>
  <div class="app-dispose">
    <el-row>
      <!-- 左侧树节点信息 -->
      <el-col :span="24" style="height:100%">
        <!--        <el-col :span="4" :style="leftStyleName" style="height: calc(100vh - 86px)">-->
        <!--          <div id="inputClear">-->
        <!--            <el-input placeholder="请输入内容" v-if="treeShow" class="inp-select" v-model="filterText"-->
        <!--                      style="width:90%;padding:20px 0 0 20px" size="mini" clearable/>-->
        <!--          </div>-->
        <!--          &lt;!&ndash; 树结构 &ndash;&gt;-->
        <!--          <div class="tree  tree-style">-->
        <!--            <el-scrollbar style="height:100%">-->
        <!--              <el-tree-->
        <!--                empty-text=" "-->
        <!--                ref="tree4"-->
        <!--                @node-click="leftTreeChange"-->
        <!--                :data="treeData"-->
        <!--                :props="defaultProps"-->
        <!--                icon-class="el-icon-folder-opened"-->
        <!--                :filter-node-method="filterNode"-->
        <!--                :highlight-current="true"-->
        <!--                :expand-on-click-node="false"-->
        <!--                default-expand-all/>-->
        <!--            </el-scrollbar>-->
        <!--          </div>-->
        <!--          <div class="shrinkageDiv" @click="divClick()">-->
        <!--            <div :class="picClassname"></div>-->
        <!--          </div>-->

        <!--        </el-col>-->
        <!-- 右侧展示查询数据 -->
        <el-col :span="20" style="height: calc(100vh - 86px)" :style="rightStyleName">
          <el-row :gutter="10" ref="searchForm" style="margin: 0">
            <el-form
              ref="queryForm"
              class="search-form"
              :model="queryForm"
              :inline="true"
              style="background:rgba(204, 204, 204, 0.18)"
              v-hasPermi="['system:user:search']"
              @submit.native.prevent
            >
              <el-form-item label="用户姓名" @keyup.enter.native="onEnterSearch" prop="userName">
                <el-input v-model="queryForm.userName" size="mini" placeholder="请输入用户姓名" clearable
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
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="dialogFormVisible = true"
                       v-hasPermi="['system:user:add']">新增
            </el-button>
            <el-button type="primary" icon="el-icon-edit" size="mini" v-hasPermi="['system:user:modify']"
                       @click="upData">编辑
            </el-button>


            <el-button type="primary" icon="el-icon-delete" size="mini" v-hasPermi="['system:user:delete']"
                       @click="handleDelete">删除
            </el-button>

            <el-button type="primary" icon="el-icon-user-solid" size="mini" v-hasPermi="['system:user:pointRole']"
                       @click="SpecifyRoleWindow">添加子分类
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
            <el-table-column type="index" label="序号" align="center" width="55" show-overflow-tooltip :index="customIndex"/>
            <el-table-column show-overflow-tooltip align="center" v-for="(item, index) in col"
                             :formatter="item.formatter"
                             :v-if="col[index].isCol" :key="index" :prop="item.prop" :label="item.label"/>
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
        :append-to-body="true"
        :close-on-click-modal="false"
        class=" dialog-style dialog-basic"
        title="新增人员信息"
        width="60%">
        <div>
          <el-form
            ref="form"
            style="margin-left: 10%;margin-right: 10%"
            :model="form"
            :inline="true"
            :rules="rules"
            label-width="120px">
            <el-form-item label="用户姓名" :label-width="formLabelWidth" prop="userName">
              <el-input v-model="form.userName" clearable size="mini" placeholder="请输入用户姓名" style="width:150px"/>
            </el-form-item>
            <el-form-item label="用户编号" :label-width="formLabelWidth" prop="userNumber">
              <el-input v-model="form.userNumber" clearable size="mini" placeholder="请输入用户编号" style="width:150px"/>
            </el-form-item>
            <el-form-item label="登录账号" :label-width="formLabelWidth" prop="loginCode">
              <el-input v-model="form.loginCode" clearable size="mini" placeholder="请输入登录账号" style="width:150px"/>
            </el-form-item>
            <el-form-item label="部门" :label-width="formLabelWidth" prop="deptId">
              <!-- <treeselect
                v-model="form.deptId"
                filterable
                :normalizer="deptNormalizer"
                :options="treeData"
                class="inputW"
                placeholder="请选择部门"
                style="width:150px"
              /> -->
              <selectTree
                v-model="form.deptId"
                :datas="deptOptions"
                :defaultProps="defaultProps"
                size="mini"
                :filterable="true"
                :ischecked="true"
                :defaultExpandAll="true"
                placeholder="请选择部门"
                width="150px"
              />
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

            <el-form-item label="用户类型" :label-width="formLabelWidth" prop="userType">
              <el-select size="mini" style="width:150px" v-model="form.userType" placeholder="请选择">
                <el-option
                  v-for="item in userTypeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item v-if="form.userType=='2'" label="密码到期时间" prop="loginPasswordDate"  >
              <el-date-picker  size="mini"  value-format="yyyy-MM-dd HH:mm:ss"   v-model="form.loginPasswordDate"  type="date" placeholder="请选择时间"  style="width: 150px" />
            </el-form-item>
          </el-form>

        </div>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="exit()">取 消</el-button>
          <el-button size="mini" type="primary" @click="addForm('form')">保 存</el-button>
        </div>
      </el-dialog>

      <!-- 修改用户信息 -->
      <el-dialog :visible.sync="dialogFormVisible1" :destroy-on-close="true" :append-to-body="true" :close-on-click-modal="false"
                 class=" dialog-style dialog-basic"
                 title="编辑人员信息" width="60%" resize
                 remember>
        <div>
          <el-form ref="editorForm" style="margin-left: 10%;margin-right: 10%" :model="editorForm" :inline="true"
                   :rules="rules" label-width="120px">
            <el-form-item label="用户姓名" :label-width="formLabelWidth" prop="userName">
              <el-input clearable v-model="editorForm.userName" placeholder="请输入用户姓名" size="mini" style="width:150px"/>
            </el-form-item>
            <el-form-item label="用户编号" :label-width="formLabelWidth" prop="userNumber">
              <el-input clearable v-model="editorForm.userNumber" placeholder="请输入用户编号" size="mini"
                        style="width:150px"/>
            </el-form-item>
            <el-form-item label="登录账号" :label-width="formLabelWidth" prop="loginCode">
              <el-input clearable v-model="editorForm.loginCode" size="mini" placeholder="请输入登录账号" style="width:150px"/>
            </el-form-item>
            <el-form-item label="部门" :label-width="formLabelWidth" prop="deptId">
              <selectTree
                v-model="editorForm.deptId"
                :datas="deptOptions"
                :defaultProps="defaultProps"
                size="mini"
                :filterable="true"
                :ischecked="true"
                :defaultExpandAll="true"
                placeholder="请选择部门"
                width="150px"
              />

            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth" prop="remark">
              <el-input clearable v-model="editorForm.remark" placeholder="请输入备注" size="mini" type="textarea"
                        style="width:150px"/>
            </el-form-item>
            <el-form-item label="用户状态" :label-width="formLabelWidth">
              <el-radio-group  v-model="editorForm.userStatus">
                <el-radio label="0">启用</el-radio>
                <el-radio label="1">禁用</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="用户类型" :label-width="formLabelWidth" prop="userType">
              <el-select size="mini" style="width:150px" v-model="editorForm.userType" placeholder="请选择">
                <el-option
                  v-for="item in userTypeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item v-if="editorForm.userType=='2'" label="密码到期时间" prop="loginPasswordDate"  >
              <el-date-picker  size="mini" value-format="yyyy-MM-dd HH:mm:ss"  v-model="editorForm.loginPasswordDate"  type="date" placeholder="请选择时间"  style="width: 150px" />
            </el-form-item>

          </el-form>
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogFormVisible1=false">取 消</el-button>
          <el-button size="mini" type="primary" @click="editorFormUpload('editorForm')">保 存</el-button>
        </div>
      </el-dialog>

      <el-dialog title="绑定权限" :append-to-body="true" :destroy-on-close="true" :close-on-click-modal="false" :visible.sync="dialogtableVisible">
        <div style="overflow: auto;height: 350px;">
          <el-table :data="permissionsData" @selection-change="permissionsChange">
            <el-table-column type="selection" width="55"/>
            <el-table-column v-for="(item, index) in gridDataCol" :key="index" width="200" :property="item.property"
                             :label="item.label"/>
          </el-table>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogtableVisible = false">取 消</el-button>
          <el-button type="primary" @click="permissionsSave">保 存</el-button>
        </div>
      </el-dialog>

      <el-dialog title="指定角色" :visible.sync="dialogtableVisible1" :destroy-on-close="true" :append-to-body="true" :close-on-click-modal="false"
                 width="1000px"
                 class=" dialog-style dialog-basic">
        <el-row>
          <el-col :span="24" style="height:100%">
            <el-col :span="11">
              <div style="overflow: auto;height: 350px;">
                <span style="">已拥有角色列表:</span>
                <el-table
                  class="table-style"
                  tooltip-effect="light"
                  height="300px"
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
            </el-col>
            <el-col :span="2">
              <div class="verticalBar">

              </div>
            </el-col>
            <el-col :span="11">
              <div style="overflow: auto;padding:0 0 0 0px;height: 350px;">
                <span style="">所有角色列表:</span>
                <el-table
                  class="table-style"
                  tooltip-effect="light"
                  stripe
                  :header-cell-style="headClass"
                  size="mini" height="300px"
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
            </el-col>

          </el-col>
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogtableVisible1=false">取 消</el-button>
          <el-button size="mini" type="primary" @click="SpecifyRoleSave('editorForm')">保 存</el-button>
        </div>
      </el-dialog>

    </el-row>
  </div>
</template>

<script>
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  import {userGrid} from '@/api/system/user'
  import {userTree, getRoleListByUserId} from '@/api/system/user'
  import {deleteUser, resetPassword, prohibitUser} from '@/api/system/user'
  import {addUser, getAllRoleList} from '@/api/system/user'
  import {addPermissions} from '@/api/system/user'
  import {queryRoleList} from '@/api/system/user'
  import {savePermissions} from '@/api/system/user'
  import {insertSysUserRole, insertSysUserRoles} from '@/api/system/user'
  import {updateUserById, updateUserByIdNew} from '@/api/system/user'
  import {queryPermissionListMap} from '@/api/system/permissions'
  import {queryRoleListMap} from '@/api/system/role'
  import {queryDeptTree} from '@/api/usual/receive'
  import {querySysUserPermissionListById} from '@/api/system/user'
  import {querySysUserRoleListById} from '@/api/system/user'
  import {downLoad, downLoadPost} from '@/downLoad/filedownload'
  import {treeListFieldTransformation} from '@/utils/ruoyi'
  import selectTree from '@/views/components/tree/tree.vue'
  import {
    queryDeptMap
  } from '@/api/usual/parameter/receives-paramenter'

  const params = {
    pageNum: 1,
    pageSize: 20
  }
  export default {
    name: "用户管理",
    components: {Treeselect, selectTree},
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
      return {
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
        userTypeOptions:[
          {label:"普通用户",value:"1"},
          {label:"临时用户",value:"2"}
        ],
        rightSpecifyRole: [],
        treeHeight: "700px",
        tableHeight: "73%",
        searchForm:"searchForm",
        searchRowHeight: '',
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
          width: '100%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)'
        },
        leftStyle1: {
          width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        rightStyle1: {
          width: '99%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)'
        },
        picClassname: "shrinkageLeft",
        leftTree1: true,
        filterText: "",
        deptOptions: [],
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
          id: 'id'
        },

        dialogFormVisible: false,
        dialogFormVisible1: false,
        dialogtableVisible: false,
        dialogtableVisible1: false,
        gridCheckDatas: [],
        /* 新增数据 */
        form: {
          userId: '',
          userName: '',
          userNumber: '',
          userType: '',
          loginCode: '',
          deptId: null,
          createBy: '0',
          userStatus: '0',
          remark: '',
          userType:"1",
          loginPasswordDate:"",
        },
        deptIdOrder: "",
        permissionIds: [],
        roleIds: [],
        /* 编辑数据 */
        editorForm: {
          userName:"",
          userNumber:"",
          loginCode:"",
          deptId:"",
          remark:"",
          userStatus:0,
          userId:"",
          userType:"",
          loginPasswordDate:"",
        },
        formLabelWidth: '120px',
        showSearchBox: false,
        queryForm: {
          userName: '',
          loginCode: ''
        },
        col: [
          {label: '数据范围', prop: 'userName', isCol: true},
          {label: '运算规则', prop: 'userNumber', isCol: true},
          {label: '进位规则', prop: 'loginCode', isCol: true},
          {label: '知识点描述', prop: 'deptId', isCol: true, formatter: this.getFullName},
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
        },
        permissionsCheck: '',
        userId: '',
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
      this.leftUserTree()
      // this.loadAllPerMissons()
      // this.loadAllRoles()
      this.getDeptTree()
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
      //自定义序号
      customIndex(index){
        return (this.pageNum-1)*(this.pageSize)+(index+1);
      },
      divClick() {
        console.log(111)
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
        console.log(leftdata)
        console.log(rightdata)
        console.log(index)
        console.log(val)

        leftdata.push(val);
        this.SpecifyRole = leftdata;
        rightdata.splice(index, 1);
        this.rightSpecifyRole = rightdata;
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.deptName.indexOf(value) !== -1;
      },
      // 获取部门树
      getDeptTree() {
        queryDeptTree({deptType: 0}).then(response => {
          // let map = {
          //   "deptName": "label"
          // }
          this.deptOptions = response.data
        })
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
      /* 权限弹窗 */
      bindingWindow(val) {
        this.userId = val.userId
        this.dialogtableVisible = true
        const _this = this
        addPermissions({userId: this.userId}, {emulateJSON: true})
          .then(function (response) {
            for (var i = 0; i < response.data.length; i++) {
              if (response.data[i].permissionStatus === 0) {
                response.data[i].permissionStatus = '已启用'
              } else {
                response.data[i].permissionStatus = '未启用'
              }
            }
            _this.permissionsData = response.data
          })
      },
      /* 权限保存 */
      permissionsSave() {
        for (let i = 0; i < this.permissionsCheck.length; i++) {
          if (this.permissionsCheck[i].permissionStatus === '已启用') {
            this.permissionsCheck[i].permissionStatus = 0
          } else {
            this.permissionsCheck[i].permissionStatus = 1
          }
        }
        const param = {
          userId: this.userId,
          data: JSON.stringify(this.permissionsCheck)
        }
        const _this = this
        savePermissions(param, {emulateJSON: true})
          .then(function (response) {
            if (response.code == 200) {
              _this.dialogtableVisible = false
              _this.$message({
                message: '保存成功',
                type: 'success'
              })
            } else {
              this.$message({
                message: '保存失败',
                type: 'error'
              });
            }

          })
      },
      /* 权限复选框选中*/
      permissionsChange(val) {
        this.permissionsCheck = val
        console.log(this.permissionsCheck)
      },
      /* 指定角色弹窗 */
      SpecifyRoleWindow() {
        if (this.gridCheckDatas.length != 1) {
          this.$message({
            type: "warning",
            message: "请勾选一条数据"
          })
          return;
        }
        let val = this.gridCheckDatas[0];
        this.userId = val.userId
        this.dialogtableVisible1 = true
        const _this = this
        _this.SpecifyRole = []
        _this.rightSpecifyRole = [];
        getRoleListByUserId({userId: this.userId}, {emulateJSON: true})
          .then(function (response) {
            if (response.code == 200) {
              _this.SpecifyRole = response.data
              getAllRoleList().then(response1 => {
                let rightData = [];
                let data = response1.data;
                for (let n in data) {
                  let flag = true;
                  for (let y in  _this.SpecifyRole) {
                    if (_this.SpecifyRole[y].roleId == data[n].roleId) {
                      flag = false;
                    }
                  }
                  if (flag) {
                    rightData.push(data[n]);
                  }
                }
                _this.rightSpecifyRole = rightData;
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
        userTree({deptType: 0})
          .then(function (response) {

            _this.treeData = response.data;

          })
      },
      /* 左侧树 节点点击事件 */
      leftTreeChange(data) {
        const _this = this
        // let params1 = {
        //   pageNum: 1,
        //   pageSize: 20,
        //   deptId: data.id
        // }
        this.form.deptId = data.id
        this.deptIdOrder = data.id;
        // params1.userName = this.queryForm.userName;
        params.deptId = data.id;
        params.userName = this.queryForm.userName;
        ;
        // if (data.id == "000") {
        userGrid(params, {emulateJSON: true})
          .then(function (response) {
            console.log(response.rows)
            _this.userTableData = response.rows
            _this.total = response.total
            _this.loading = false
          })
        // }
      },


      // 复选框事件
      handleSelectionChange(val) {
        this.gridCheckDatas = val;
        this.gridCheck = val.map(item => item.userId)
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
            userName:this.gridCheckDatas[0].userName,
            userNumber:this.gridCheckDatas[0].userNumber,
            loginCode:this.gridCheckDatas[0].loginCode,
            deptId:this.gridCheckDatas[0].deptId,
            remark:this.gridCheckDatas[0].remark,
            userStatus:0,
            userId:this.gridCheckDatas[0].userId,
            userType:this.gridCheckDatas[0].userType.toString(),
            loginPasswordDate:this.gridCheckDatas[0].loginPasswordDate,
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

      onEnterSearch() {
        this.search();
      },
      /* 上部搜索 */
      search() {
        let searchText = this.queryForm.userName
        params.userName = searchText;
        params.pageNum = 1;
        this.pageNum = 1;
        if (this.form.deptId != undefined && this.form.deptId != "") {
          params.deptId = this.form.deptId;
        }
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
                    message: "添加成功",
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


        // } else {
        //   return false
        // }
        // })
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
        console.log(row)
        this.$refs.tableGrid.clearSelection()
        if (this.gridCheckDatas.length === 0) {
          this.$refs.tableGrid.toggleRowSelection(row)
          return;
        }
        this.$refs.tableGrid.toggleRowSelection(row)
      },
      getFullNameWapper(row,field){
        let col = {
          property:field
        }
        return this.getFullName(row,col);
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

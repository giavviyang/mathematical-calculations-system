<template>
  <div class="app-permissions">
    <el-row>
      <el-col :span="24" style="padding: 10px">
        <el-row :gutter="10" class="mb8" style="margin:0 0 10px 0;padding-left: 5px">
          <el-form
            v-if="showSearchBox"
            ref="queryForm"
            class="transition"
            :model="queryForm"
            :inline="true"
            label-width="68px"
            style="background:rgba(204, 204, 204, 0.18);padding: 15px;margin-right: 20px;margin-bottom: 10px;"
          >
            <el-form-item label="权限名称" prop="permissionName" style="margin-top: 10px;margin-bottom: 10px">
              <el-input v-model="queryForm.permissionName" placeholder="权限名称" clearable size="small" style="width: 240px" />
            </el-form-item>
            <el-form-item label="所属全宗" prop="orgId" style="margin-top: 10px;margin-bottom: 10px"><el-input v-model="queryForm.orgId" placeholder="权限标识" clearable size="small" style="width: 240px" /></el-form-item>
            <el-form-item style="margin-top: 10px;margin-bottom: 10px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="permissionSearch">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="permissionReset">重置</el-button>
            </el-form-item>
          </el-form>
          <el-col :span="1.5"><el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd">新增权限</el-button></el-col>
          <el-col :span="1.5"><el-button type="primary" icon="el-icon-delete" size="mini" @click="handleDelete">批量删除</el-button></el-col>
          <el-dropdown style="float: right; margin-right: 30px;">
            <el-button icon="el-icon-menu" size="mini" circle />
            <el-dropdown-menu style="padding: 10px">
              <el-checkbox-group v-for="(item, index) in col" :key="index" v-model="col[index].isCol" size="small">
                <el-checkbox>{{ item.label }}</el-checkbox>
              </el-checkbox-group>
            </el-dropdown-menu>
          </el-dropdown>
          <el-col :span="1.5" style="float: right;"><el-button type="primary" icon="el-icon-search" size="mini" circle @click="showSearchBox = !showSearchBox" /></el-col>
        </el-row>
        <el-table
          ref="tableData"
          v-loading="loading"
          :data="tableData"
          border
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          row-key="id"
          align="left"
          @row-click="handleCurrentChange"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column  align="center" v-for="(item, index) in col" v-if="item.isCol" :key="index" :prop="item.prop" :label="item.label" />
          <el-table-column align="center" fixed="right" label="操作" width="300px">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="upData(scope.row)">编辑</el-button>
              <el-button type="text" size="small" @click="permissionsWindow(scope.row)">数据权限</el-button>
              <el-button type="text" size="small" @click="SpecifyRole(scope.row)">指定角色</el-button>
              <el-button type="text" size="small" @click="distribution(scope.row)">分配用户</el-button>
              <!-- <el-button type="text" size="small" @click="lineDelete(scope.row)">删除</el-button> -->
            </template>
          </el-table-column>
        </el-table>
        <div class="block" style="float: right;padding: 10px">
          <el-pagination
            :page-sizes="[20, 50, 100, 200]"
            :page-size="20"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="pageSize"
            @current-change="pageCurrent"
          />
        </div>
      </el-col>
      <el-dialog :visible.sync="dialogFormVisible" width="60%">
        <h4 style="border-bottom: 1px solid #cccccc;line-height: 50px;font-size: 25px; margin-top: 0">基本信息</h4>
        <div style="width: 60%; margin: 0 auto">
          <el-form ref="form"
                   :model="form"
                   :rules="addPermissionRules"
                   :inline="true">
            <el-form-item label="权限名称" :label-width="formLabelWidth" prop="permissionName" ><el-input v-model="form.permissionName" autocomplete="off" style="width: 350px;"/></el-form-item><br/>
            <el-form-item label="权限状态" :label-width="formLabelWidth">
              <el-select v-model="form.permissionStatus" autocomplete="off" style="width: 350px;">
                <el-option
                  v-for="item in statusStr"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>

            </el-form-item><br/>
            <!-- <el-form-item label="系统内数据权限" :label-width="formLabelWidth">
              <el-select v-model="form.name" class="inputW" placeholder="请选择部门">
                <el-option label="男" value="1" />
                <el-option label="女" value="2" />
                <el-option label="未知" value="3" />
              </el-select>
            </el-form-item> -->
            <el-form-item label="备注" :label-width="formLabelWidth"><el-input v-model="form.remark" type="textarea" autocomplete="off" style="text-align: center; width: 350px"/></el-form-item>
          </el-form>
        </div>
        <h4 style="border-bottom: 1px solid #cccccc;line-height: 50px;font-size: 25px;margin-top: 0 ">菜单权限</h4>
        <el-button type="primary" @click="loadFunData()">选择功能权限</el-button>
        <el-tree ref="tree" height="200px" :data="fnData" highlight-current :props="funProps" />
        <div id="titleDialog" slot="title" class="el-dialog__title"  />
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="addFormSave('form')">保存</el-button>
        </div>
      </el-dialog>

      <!--选择功能权限弹框-->
      <el-dialog
        title="选择功能权限"
        :visible.sync="dialogVisibleSelect"
        width="30%"
      >
        <el-tree
          ref="funData"
          :props="funProps"
          :data="funData"
          node-key="menuId"
          show-checkbox
          :check-strictly="false"
        />

        <!--:default-checked-keys="selectMenuFun"
        :default-expanded-keys="selectMenuFun"-->
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisibleSelect = false">取 消</el-button>
          <el-button type="primary" @click="addFunData">保存</el-button>
        </span>
      </el-dialog>

      <!--添加用户弹框-->
      <el-dialog
        title="添加用户"
        :visible.sync="dialogVisibleAddUser"
        width="30%"
      >
        <el-table :data="addUserData" height="300px" border @selection-change="addUserChange">
          <el-table-column type="selection" width="55" />
          <el-table-column  align="center" v-for="(item, index) in distributionCol" :key="index" :prop="item.prop" :label="item.label" />
        </el-table>
        <el-pagination
          :page-sizes="[10, 20, 50, 100]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="addUserTotal"
          @size-change="addUserPageSize"
          @current-change="addUserPageCurrent"
        />
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisibleAddUser = false">取 消</el-button>
          <el-button type="primary" @click="addUserPermission">保存</el-button>
        </span>
      </el-dialog>

      <el-dialog title="数据权限" :visible.sync="dialogFormVisible1" width="90%" height="800px">
        <el-form :model="permissionsForm" :inline="true" style="overflow: hidden">
          <el-form-item label="权限名称" :label-width="formLabelWidth"><el-input v-model="permissionsForm.permissionName" :disabled="true" autocomplete="off" /></el-form-item>
          <el-form-item label="所属全宗" :label-width="formLabelWidth"><el-input v-model="permissionsForm.orgId" :disabled="true" autocomplete="off" /></el-form-item>
          <el-form-item label="权限状态" :label-width="formLabelWidth"><el-input v-model="permissionsForm.permissionStatus" :disabled="true" autocomplete="off" /></el-form-item>
        </el-form>
        <div class="custom-tree-container" style="overflow: hidden">
          <div class="tree_block" style="width: 15%;float: left">
            <p>机构列表</p>
            <el-tree :data="institutions" :check-strictly="true" :default-checked-keys="globalOrgIds" :default-expanded-keys="globalOrgIds" :props="institutionsProps" show-checkbox node-key="id" default-expand-all @check-change="institutionsChange" />
          </div>
          <div class="tree_block" style="width: 15%;float: left">
            <p>用户列表</p>
            <el-tree :data="userList" :default-checked-keys="userIds" :default-expanded-keys="userIds" :props="userProps" show-checkbox node-key="id" default-expand-all @check-change="userListChange" />
          </div>
          <div class="tree_block" style="width: 20%;float: left">
            <p>材料类型</p>
            <el-tree :data="materialType" :default-checked-keys="materialIds" :default-expanded-keys="materialIds" :props="materialProps" show-checkbox node-key="dictCode" default-expand-all @check-change="materialTypeChange" />
          </div>
          <div class="tree_block" style="width: 20%;float: left">
            <p>材料名称</p>
            <el-tree :data="materialName" :props="materialNameProps" show-checkbox node-key="id" :default-checked-keys="materialNameIds" :default-expanded-keys="materialNameIds" default-expand-all @check-change="materialNameChange" />
          </div>
          <div class="tree_block" style="width: 25%;float: left">
            <el-table
              :data="sysFileData"
              :header-cell-style="tableHeaderColor"
              :cell-style="{height:'26px',padding:'0px 0px',border:'#FFFFFF'}"
              style="width: 100%;height:100%;border: #cccccc">
              <el-table-column
                prop="fileName"
                label="电子文件"
                >
              </el-table-column>
              <el-table-column
                prop="id"
                label="查看"
                align="center" width="50px" v-model="checkList">
                <template slot-scope="scope">
                  <el-checkbox @change="butTypeClick(1,scope.row)" :checked="scope.row.lookChange"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column
                prop="id"
                label="打印"
                align="center" width="50px">
                <template slot-scope="scope">
                  <el-checkbox @change="butTypeClick(2,scope.row)" :checked="scope.row.printChecked"></el-checkbox>
                </template>
              </el-table-column>
              <el-table-column
                prop="id"
                label="下载"
                align="center" width="50px">
                <template slot-scope="scope">
                  <el-checkbox @change="butTypeClick(3,scope.row)" :checked="scope.row.downChecked"></el-checkbox>
                </template>
              </el-table-column>
            </el-table>

          </div>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible1 = false">取消</el-button>
          <el-button type="primary" @click="permissionsSave">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog title="角色绑定" :visible.sync="dialogTableVisible">
        <el-form ref="queryForm" class="transition" :model="queryForm" :inline="true" label-width="68px" style="padding: 10px 0;margin-bottom: 10px;">
          <el-form-item label="角色名称" prop="e01zc24a"><el-input v-model="queryForm.e01zc24a" placeholder="角色名称" clearable size="small" style="width: 240px" /></el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="roleSearch">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="roleReset">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="gridData" height="400px" @selection-change="roleSelectionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column v-for="(item, index) in gridDataCol" :key="index" :prop="item.prop" :label="item.label" />
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible = false">取消</el-button>
          <el-button type="primary" @click="roleFormSave">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog title="分配用户" :visible.sync="dialogTableVisible1" width="60%">
        <el-form ref="distributionForm" class="transition" :model="distributionForm" :inline="true" label-width="68px" style="padding: 10px 0;margin-bottom: 10px;">
          <el-form-item label="权限名称" prop="permissionName">
            <el-input v-model="distributionForm.permissionName" :disabled="true" placeholder="权限名称" clearable size="small" style="width: 240px" />
          </el-form-item>
          <el-form-item label="权限标识" prop="orgId">
            <el-input v-model="distributionForm.orgId" :disabled="true" placeholder="权限标识" clearable size="small" style="width: 240px" />
          </el-form-item>
          <el-form-item label="权限状态" prop="permissionStatus">
            <el-input v-model="distributionForm.permissionStatus" :disabled="true" placeholder="权限状态" clearable size="small" style="width: 240px" />
          </el-form-item>
        </el-form>
        <el-form ref="queryForm" class="transition" :model="queryForm" :inline="true" label-width="68px" style="padding: 10px 0;margin-bottom: 10px;">
          <el-form-item label="用户名称" prop="e01zc24a"><el-input v-model="queryForm.e01zc24a" placeholder="用户名称" clearable size="small" style="width: 240px" /></el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="userSearch">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="userReset">重置</el-button>
            <el-button type="primary" size="mini" @click="addUser">添加用户</el-button>
            <el-button type="primary" size="mini" @click="Cancel">取消授权</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="distributionDta" height="300px" border @selection-change="distributionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column  align="center" v-for="(item, index) in distributionCol" :key="index" :prop="item.prop" :label="item.label" />
        </el-table>
        <!--<el-pagination
          :page-sizes="[10, 20, 50, 100]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="adduserTotal"
          @size-change="pageSize"
          @current-change="pageCurrent"
        />-->
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible1 = false">关闭</el-button>
          <!--<el-button type="primary" @click="dialogTableVisible1 = false">保存</el-button>-->
        </div>
      </el-dialog>
    </el-row>
  </div>
</template>
<script>
import { queryPermissionPageList } from '@/api/system/permissions'
import { querySysMenusList } from '@/api/system/menu'
import { queryAddUserList } from '@/api/system/permissions'
import { insertDataPermissionsByPermissionId } from '@/api/system/permissions'
import { selectDataPermissionsByPermissionId } from '@/api/system/permissions'
import { addUserAndPermission } from '@/api/system/permissions'
import { sysFileTree } from '@/api/system/permissions'
import { queryDeptListByParentId } from '@/api/system/permissions'
import { insertSysPermission } from '@/api/system/permissions'
import { updateSysPermissionById } from '@/api/system/permissions'
import { selectSysPermissionMenuByPermissionId } from '@/api/system/permissions'
import { deleteSysPermissionById } from '@/api/system/permissions'
import { insertRolePermission } from '@/api/system/permissions'
import { queryRoleListByPermissionIdEnabled } from '@/api/system/permissions'
import { queryUserListByPermissionId } from '@/api/system/permissions'
import { deleteUserPermissionsByPermissionId } from '@/api/system/permissions'
import { permissionsTree, materialNameTree, materialTypeTree, listUsersTree, institutionsTree } from '@/api/system/permissions'

const cityOptions = ['查看', '下载', '打印']
const params = {
  pageNum: 1,
  pageSize: 20
}

const addUserParams = {
  pageNum: 1,
  pageSize: 10
}
export default {
  data() {
    return {
      loading: true,
      total: 0,
      defaultProps: {
        children: 'children',
        label: 'label',
        id: 'id'
      },
      checkList: [],
      adduserTotal: 0,
      addUserTotal: 0,
      checkAll: false,
      checkedCities: [],
      cities: cityOptions,
      isIndeterminate: true,
      gridData: [],
      gridDataCol: [
        { prop: 'roleNum', label: '角色编号' },
        { prop: 'roleName', label: '角色名称' },
        { prop: 'roleSummary', label: '角色摘要' },
        { prop: 'roleStatus', label: '角色状态' }
      ],
      distributionDta: [],
      distributionCol: [
        { prop: 'loginCode', label: '登录名' },
        { prop: 'userName', label: '用户名称' },
        { prop: 'createTime', label: '创建时间' },
        { prop: 'userStatus', label: '用户状态' }
      ],
      distributionForm: {},

      addUserData: [],

      dialogFormVisible1: false,
      dialogFormVisible: false,
      dialogTableVisible: false,
      dialogVisibleSelect: false,
      dialogTableVisible1: false,
      dialogVisibleAddUser: false,
      showHeader: false,
      form: {
        permissionName: '',
        permissionStatus: '1',
        remark: ''
      },

      formLabelWidth: '120px',
      showSearchBox: false,
      queryForm: {
        permissionName: '',
        orgId: ''
      },
      // 权限弹窗数据
      permissionsForm: {},
      /* 机构树 */
      institutions: [],
      /* 用户列表 树*/
      userList: [],
      /* 材料类型 */
      materialType: [],
      materialTypeTemp: [],
      /* 材料名称 */
      materialName: [],
      /* 功能权限树 */
      fnData: [],
      statusStr: [{
        value: '1',
        label: '未启用'
      }, {
        value: '0',
        label: '已启用'
      }],
      col: [
        { label: '权限主键', prop: 'permissionId', isCol: false },
        { label: '权限名称', prop: 'permissionName', isCol: true },
        { label: '所属全宗', prop: 'orgId', isCol: true },
        { label: '创建人', prop: 'createBy', isCol: true },
        { label: '修改人', prop: 'updateBy', isCol: true },
        { label: '权限状态', prop: 'permissionStatus', isCol: true },
        { label: '创建时间', prop: 'createTime', isCol: true },
        { label: '修改时间', prop: 'updateTime', isCol: true },
        { label: '备注', prop: 'remark', isCol: true }
      ],
      addPermissionRules:{
        permissionName: [
          { required: true, message: '请输入权限名称', trigger: 'blur' }
        ]
      },
      tableData: [],
      sysFileData: [],
      gridCheck: [],
      permissionId: '',
      roleSelectionCheck: [],
      distributionCheck: [],
      lookChecked:[],
      printChecked:false,
      downChecked:false,
      lookChange: false,
      addUserCheck: [],
      funData: [],
      allFunData: {},
      selectMenuFun: [],
      funProps: {
        label: 'menuName',
        children: 'children'
      },
      institutionsProps: {
        label: 'deptName',
        children: 'children',
        id: 'id'
      },
      materialProps: {
        label: 'fullName',
        children: 'children',
        id: 'dictCode'
      },
      materialNameProps: {
        label: 'e01z111a',
        children: 'children',
        id: 'id'
      },
      userProps: {
        label: 'a0101',
        children: 'children',
        id: 'id'
      },
      updataPojo: null,
      globalOrgIds: [],
      materialIds: [],
      materialNameIds: [],
      userIds: [],
      sysFileIds: []
    }
  },
  //在这里调用ajax请求方法
  created(){
    this.selectAllFunData();
  },
  mounted() {
    this.permissionsGrid(params)
  },
  methods: {

    /* -------------菜单功能权限----------------*/
    // 每点击一次刷新一次选中的数据
    checkChange(data, status) {
      const _this = this
      // console.log(_this.allFunData);
      // console.log(status)
      /* for (var i = 0; i < status.checkedKeys.length; i++) {
        if (_this.allFunData[status.checkedKeys[i]].children.length < 1) {
          _this.selectMenuFun.push(status.checkedKeys[i]);
          _this.addSelectMenuFun(_this.allFunData[status.checkedKeys[i]].parentId)
        }
      }
      console.log(_this.selectMenuFun)*/
      _this.selectMenuFun = status.checkedKeys
    },
    addSelectMenuFun(key) {
      const _this = this
      this.selectMenuFun.push(key.menuId);
      if (key.children.length>0) {
        for (let i = 0;i < key.children.length; i++) {
          this.addSelectMenuFun(key.children[i]);
        }
      } else {

      }
    },
    // 加菜单权限树
    loadFunData() {
      const _this = this
      // 查詢菜单
      querySysMenusList({ parentId: '0' })
        .then(function(response) {
          _this.getAllData(response.data, _this.allFunData)
          _this.funData = response.data
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
      // 弹出功能框
      this.dialogVisibleSelect = true
    },
    handleClose() {},
    getAllData(data, allData) {
      for (var i = 0; i < data.length; i++) {
        allData[data[i].menuId] = data[i]
        if (data[i].children && data[i].children.length > 0) {
          this.getAllData(data[i].children, allData)
        }
      }
    },
    addErmissionsSave() {},
    // 选择功能权限 复选框
    addFunData() {
      this.selectMenuFun=[];
      const array = this.$refs.funData.getCheckedNodes()
      console.log("选中的节点")
      console.log(array)
      for (let i = 0; i < array.length; i++) {
        this.addSelectMenuFun(array[i]);
      }
      this.fnData = array
      this.dialogVisibleSelect = false
    },
    // 新增保存或修改保存
    addFormSave(form) {
      const _this = this;
      if (this.selectMenuFun.length <= 0) {
        _this.$message({
          showClose: true,
          message: "请至少选择一条权限",
          type: 'warning'
        })
        return;
      }
      this.$refs[form].validate(valid => {
        if (valid) {
          if (_this.updataPojo == null) {
            this.form['jsonStr'] = this.selectMenuFun.toString()
            insertSysPermission(this.form)
              .then(function(response) {
                _this.permissionsGrid(params)
                _this.$message({
                  type: 'success',
                  message: '新增成功!'
                })
                _this.dialogFormVisible = false
              })
              .catch(function(error) {
                _this.$message({
                  showClose: true,
                  message: error,
                  type: 'error'
                })
              })
          } else {
            _this.updataPojo['jsonStr'] = this.selectMenuFun.toString()
            _this.updataPojo['permissionName'] = this.form['permissionName'];
            _this.updataPojo['remark'] = this.form['remark'];
            console.log(this.form['permissionStatus'])
            if (this.form['permissionStatus'] === '已启用' || this.form['permissionStatus'] === '0') {
              _this.updataPojo['permissionStatus'] = '0'
            } else {
              _this.updataPojo['permissionStatus'] = '1'
            }

            _this.updataPojo.createTime = _this.format(_this.updataPojo.createTime,"yyyy-MM-dd");
            _this.updataPojo.updateTime = _this.format(_this.updataPojo.updateTime,"yyyy-MM-dd");

            updateSysPermissionById(_this.updataPojo)
              .then(function(response) {
                _this.permissionsGrid(params)
                _this.$message({
                  type: 'success',
                  message: '修改成功!'
                })
                _this.dialogFormVisible = false
              })
              .catch(function(error) {
                _this.$message({
                  showClose: true,
                  message: error,
                  type: 'error'
                })
              })
          }
        } else {
          return false
        }
      })
    },
    format(datetime,fmt) {
      if (parseInt(datetime)==datetime) {
        if (datetime.length==10) {
          datetime=parseInt(datetime)*1000;
        } else if(datetime.length==13) {
          datetime=parseInt(datetime);
        }
      }
      datetime=new Date(datetime);
      var o = {
        "M+" : datetime.getMonth()+1,                 //月份
        "d+" : datetime.getDate(),                    //日
        "h+" : datetime.getHours(),                   //小时
        "m+" : datetime.getMinutes(),                 //分
        "s+" : datetime.getSeconds(),                 //秒
        "q+" : Math.floor((datetime.getMonth()+3)/3), //季度
        "S"  : datetime.getMilliseconds()             //毫秒
      };
      if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
      for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
      return fmt;
    },
    addFnData(key, allValue) {
      /* var flag = false;
      const  _this = this;
      debugger;
      for (var j = 0; j < _this.fnData.length; j++) {
        if (_this.fnData[j].menuId === key) {
          console.log("menuid:" + _this.fnData[j].menuId);
          console.log("key:" + key);
          console.log(_this.fnData)
            flag = true;
        }
      }
      if (!flag) {
        _this.fnData.push(allValue[key]);
      }

      console.log(key)
      console.log(allValue[key])
      if (key.length > 1) {
        this.addFnData(allValue[key].parentId,allValue)
      }*/
    },

    /* ----------分配用户-----------*/
    addUser() {
      const _this = this
      var newAddUserParams = {pageSize:addUserParams.pageSize,pageNum:addUserParams.pageNum};
      newAddUserParams['jsonStr'] = _this.permissionId;
      queryAddUserList(newAddUserParams)
        .then(function(response) {
          for (var i = 0; i < response.rows.length; i++) {
            if (response.rows[i].userStatus === 0) {
              response.rows[i].userStatus = '已启用'
            } else {
              response.rows[i].userStatus = '未启用'
            }
          }
          _this.addUserData = (response.rows)
          _this.addUserTotal = response.total;
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
      queryDeptListByParentId()
        .then(function(response) {
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
      _this.dialogVisibleAddUser = true
    },
    /* 添加用户复选框 */
    addUserChange(val) {
      this.addUserCheck = val
    },

    addUserPermission() {
      const _this = this;
      var userIds = [];
      for (var i = 0; i < this.addUserCheck.length; i++) {
        userIds.push(this.addUserCheck[i].userId);
      }

      if (userIds.length <= 0) {
        _this.$message({
          showClose: true,
          message: "请至少选择一名用户",
          type: 'warning'
        })
        return;
      }

      addUserAndPermission({userIds:userIds.toString(),permissionId:this.permissionId})
        .then(function (response) {

          var newAddUserParams = {pageSize:addUserParams.pageSize,pageNum:addUserParams.pageNum};
          newAddUserParams['jsonStr'] = _this.permissionId;
          queryAddUserList(newAddUserParams)
            .then(function(response) {
              for (var i = 0; i < response.rows.length; i++) {
                if (response.rows[i].userStatus === 0) {
                  response.rows[i].userStatus = "已启用"
                } else {
                  response.rows[i].userStatus = "未启用"
                }
              }
              _this.addUserData = (response.rows)
              _this.addUserTotal = response.total;
            })
            .catch(function(error) {
              _this.$message({
                showClose: true,
                message: error,
                type: 'error'
              })
            })
          queryUserListByPermissionId({ permissionId: _this.permissionId })
            .then(function(response) {
              for (let i = 0; i < response.data.length; i++) {
                if (response.data[i].userStatus === 0) {
                  response.data[i].userStatus = '已启用'
                } else {
                  response.data[i].userStatus = '未启用'
                }
              }
              _this.distributionDta = response.data
            })
            .catch(function(error) {
              _this.$message({
                showClose: true,
                message: error,
                type: 'error'
              })
            })

          _this.$message({
            message: '保存成功',
            type: 'success'
          })
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
    },

    permissionsGrid(params) {
      const _this = this
      queryPermissionPageList(params, { emulateJSON: true })
        .then(function(response) {
          for (var i = 0; i < response.rows.length; i++) {
            if (response.rows[i].permissionStatus === 0) {
              response.rows[i].permissionStatus = '已启用'
            } else {
              response.rows[i].permissionStatus = '未启用'
            }
              response.rows[i].createTime = _this.format(response.rows[i].createTime,"yyyy-MM-dd");
              response.rows[i].updateTime = _this.format(response.rows[i].updateTime,"yyyy-MM-dd");
          }
          _this.tableData = response.rows
          _this.total = response.total
          _this.loading = false
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
    },
    handleCheckAllChange(val) {
      this.checkedCities = val ? cityOptions : []
      this.isIndeterminate = false
    },
    handleCheckedCitiesChange(value) {
      const checkedCount = value.length
      this.checkAll = checkedCount === this.cities.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length
    },
    // grid  行点击事件
    handleCurrentChange(row) {
      if (row) {
        this.$refs.tableData.toggleRowSelection(row)
      } else {
        this.$refs.tableData.clearSelection()
      }
    },
    // 复选框事件
    handleSelectionChange(val) {
      this.gridCheck = val
    },

    selectAllFunData() {
      const _this = this;
      // 查詢菜单
      querySysMenusList({ parentId: '0' })
        .then(function(response) {
          _this.getAllData(response.data, _this.allFunData)
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
    },

    // 编辑
    upData(val) {
      const _this = this;
      _this.fnData=[]
      document.getElementById('titleDialog').innerHTML = '编辑权限'
      _this.updataPojo = val
      // 查询要编辑用户的菜单权限
      selectSysPermissionMenuByPermissionId({ permissionId: val.permissionId }).then(function(response) {
        // 将选中的数据回显
        _this.selectMenuFun = response.data;
        for (let i = 0;i < response.data.length; i++) {
          if (response.data[i].length === 1) {
            let key = response.data[i];
            _this.fnData.push(_this.allFunData[key])
          }
        }
      }).catch(function(error) {
        _this.$message({
          showClose: true,
          message: error,
          type: 'error'
        })
      })

      _this.form.permissionName = val.permissionName
      _this.form.permissionStatus = val.permissionStatus
      _this.form.remark = val.remark
      this.dialogFormVisible = true
    },
    // 指定角色
    SpecifyRole(val) {
      this.dialogTableVisible = true
      this.permissionId = val.permissionId
      const _this = this
      queryRoleListByPermissionIdEnabled({ permissionId: val.permissionId }, { emulateJSON: true })
        .then(function(response) {
          for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].roleStatus === 0) {
              response.data[i].roleStatus = '已启用'
            } else {
              response.data[i].roleStatus = '未启用'
            }
          }
          _this.gridData = response.data
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
    },
    /*  指定角色 复选框 */
    roleSelectionChange(val) {
      this.roleSelectionCheck = val
    },
    /* 指定角色保存 */
    roleFormSave() {
      const _this = this
      if (this.roleSelectionCheck.length <= 0) {
        _this.$message({
          showClose: true,
          message: "请至少选择一个角色",
          type: 'warning'
        })
        return;
      }
      for (let i = 0; i < this.roleSelectionCheck.length; i++) {
        if (this.roleSelectionCheck[i].roleStatus === '已启用') {
          this.roleSelectionCheck[i].roleStatus = 0
        } else {
          this.roleSelectionCheck[i].roleStatus = 1
        }
      }
      const param = {
        permissionId: this.permissionId,
        jsonStr: JSON.stringify(this.roleSelectionCheck)
      }
      insertRolePermission(param, { emulateJSON: true })
        .then(function(response) {
          // _this.dialogTableVisible = false
          _this.$message({
            message: '保存成功',
            type: 'success'
          })
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
    },
    /* 分配用户弹窗 */
    distribution(val) {
      this.distributionForm = val
      this.dialogTableVisible1 = true
      this.permissionId = val.permissionId
      const _this = this
      queryUserListByPermissionId({ permissionId: val.permissionId }, { emulateJSON: true })
        .then(function(response) {
          for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].userStatus === 0) {
              response.data[i].userStatus = '已启用'
            } else {
              response.data[i].userStatus = '未启用'
            }
          }
          _this.distributionDta = response.data
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
    },
    /* 分配用户复选框 */
    distributionChange(val) {
      this.distributionCheck = val
    },
    /* 取消授权 */
    Cancel() {
      const _this = this
      var userIds = [];
      for (let i = 0; i < this.distributionCheck.length; i++) {
        userIds.push({userId:this.distributionCheck[i].userId})
      }
      const param = {
        jsonStr: JSON.stringify(userIds),
        permissionId: this.permissionId
      }
      if (this.distributionCheck === '') {
        this.$message({
          message: '请选择您要取消授权的内容',
          type: 'warning'
        })
      } else {
        this.$confirm('此操作将永久取消授权, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            deleteUserPermissionsByPermissionId(param)
              .then(function(response) {
                // _this.permissionsGrid(params)
                queryUserListByPermissionId({ permissionId: _this.permissionId })
                  .then(function(response) {
                    for (let i = 0; i < response.data.length; i++) {
                      if (response.data[i].userStatus === 0) {
                        response.data[i].userStatus = '已启用'
                      } else {
                        response.data[i].userStatus = '未启用'
                      }
                    }
                    _this.distributionDta = response.data
                  })
                  .catch(function(error) {
                    _this.$message({
                      showClose: true,
                      message: error,
                      type: 'error'
                    })
                  })
                _this.$message({
                  type: 'success',
                  message: '取消成功!'
                })
              })
              .catch(function(error) {
                _this.$message.error(error)
              })
          })
          .catch(() => {
            _this.$message({
              type: 'info',
              message: '已取消'
            })
          })
      }
    },
    // 删除
    lineDelete(val) {
      const _this = this
      var param = JSON.stringify(val)
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deleteSysPermissionById({ jsonStr: param }, { emulateJSON: true })
            .then(function(response) {
              _this.permissionsGrid(params)
              _this.$message({
                type: 'success',
                message: '删除成功!'
              })
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
    },

    /* 新增弹窗 */
    handleAdd() {
      const _this = this
      document.getElementById('titleDialog').innerHTML = '新增权限'
      // 初始化弹框
      _this.form.permissionName = ''
      _this.form.permissionStatus = '1'
      _this.form.remark = ''
      _this.selectMenuFun = []
      _this.fnData = []
      _this.updataPojo = null
      this.dialogFormVisible = true
    },

    /* 多选删除 */
    handleDelete() {
      const _this = this
      var data = []
      for (var i = 0; i < this.gridCheck.length; i++) {
        data.push({ permissionId: this.gridCheck[i].permissionId })
      }
      var param = JSON.stringify(data)
      if (this.gridCheck === '') {
        this.$message({
          showClose: true,
          message: '请选择您要删除的内容',
          type: 'warning'
        })
      } else {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            deleteSysPermissionById({ jsonStr: param }, { emulateJSON: true })
              .then(function(response) {
                _this.permissionsGrid(params)
                _this.$message({
                  type: 'success',
                  message: '删除成功!'
                })
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
    // 权限弹窗
    permissionsWindow(val) {
      this.dialogFormVisible1 = true
      this.permissionsForm = val
      this.permissionId = val.permissionId

      const _this = this
      _this.institutions = [];
      _this.globalOrgIds = [];
      _this.userList = [];
      _this.userIds = [];
      _this.materialIds = [];
      _this.materialType = [];
      _this.materialNameIds = [];
      _this.materialName = [];
      _this.sysFileData = [];
      /* 机构树 */
      institutionsTree()
        .then(response => {
          _this.institutions = response.data
        })
        /* 用户列表 */
      /* listUsersTree()
        .then(response => {
          _this.userList = response.data
        })*/
      /* 材料类型 */
      materialTypeTree()
        .then(response => {
          _this.materialTypeTemp = response.data
        })

      /* 材料名称 */
      /* materialNameTree()
        .then(response => {
          _this.materialName = response.data
        })*/
      // /* 功能权限树 */
      // permissionsTree()
      //   .then(response => {
      //     _this.fnData = response.data
      //   })

      //查询权限
      selectDataPermissionsByPermissionId({permissionId:_this.permissionId})
        .then(response => {
          let data = response.data;

          var globalOrgIds = [];
          var userIds = [];
          var materialIds = [];
          var materialNameIds = [];
          var sysFileData = {};

          for (let i =0; i<data.length; i++) {
            switch (data[i].dataPermissionsType) {
              case '1':
                if (globalOrgIds.indexOf(data[i].dataId) == -1) {
                  globalOrgIds.push(data[i].dataId);
                }
                break;
              case '2':
                if (userIds.indexOf(data[i].dataId) == -1) {
                  userIds.push(data[i].dataId);
                }
                break;
              case '3':
                if (materialIds.indexOf(data[i].dataId) == -1) {
                  materialIds.push(data[i].dataId);
                }
                break;
              case '4':
                console.log(materialNameIds,data[i].dataId)
                if (materialNameIds.indexOf(data[i].dataId) == -1) {
                  materialNameIds.push(data[i].dataId);
                }
                break;
              case '5':
                let id = data[i].dataId;
                let dataPermissionSummary = data[i].dataPermissionSummary;
                sysFileData[id]= dataPermissionSummary;
                /*for (let j = 0; j < _this.sysFileData.length; j++) {
                  if (_this.sysFileData[j].id === data[i].dataId) {
                    if (data[i].dataPermissionSummary.indexOf('1') != -1) {
                          _this.sysFileData[j].lookChange = true;
                    }
                    if (data[i].dataPermissionSummary.indexOf('2') != -1) {
                      _this.sysFileData[j].printChecked = true;
                    }
                    if (data[i].dataPermissionSummary.indexOf('3') != -1) {
                      _this.sysFileData[j].downChecked = true;
                    }
                  }
                }*/
                break;
            }
          }

          if (globalOrgIds.length > 0) {
            _this.globalOrgIds = globalOrgIds;
            /* 查询用户列表 */
            listUsersTree({ ids: _this.globalOrgIds.toString() })
              .then(response => {
                _this.userList = response.data
                if (userIds.length > 0) {
                  _this.userIds = userIds;
                  _this.materialType = _this.materialTypeTemp;
                  if (materialIds.length > 0) {
                    _this.materialIds = materialIds;
                    if (materialNameIds.length > 0) {
                      materialNameTree({ userIds: _this.userIds.toString(), materialIds: _this.materialIds.toString() })
                        .then(response => {
                          _this.materialNameIds = materialNameIds;
                          _this.materialName = response.data;
                          sysFileTree({ids:_this.materialNameIds.toString()})
                            .then(response => {
                              for (  let i = 0; i < response.data.length; i++){
                                let dataPermissionSummary = sysFileData[response.data[i].id];
                                if (dataPermissionSummary && dataPermissionSummary.indexOf('1') != -1) {
                                  response.data[i].lookChange = true;
                                } else {
                                  response.data[i].lookChange = false;
                                }
                                if (dataPermissionSummary && dataPermissionSummary.indexOf('2') != -1) {
                                  response.data[i].printChecked = true;
                                } else {
                                  response.data[i].printChecked = false;
                                }
                                if (dataPermissionSummary && dataPermissionSummary.indexOf('3') != -1) {
                                  response.data[i].downChecked = true;
                                } else {
                                  response.data[i].downChecked = false;
                                }
                              }
                              //TODO 查询到的电子文件
                              _this.sysFileData = response.data;
                              console.log(_this.sysFileData)
                            })
                        })
                    }
                  }
                }
              })
          }
        })
    },
    // 权限保存
    permissionsSave() {
      const  _this = this;


      if (_this.materialNameIds.length <= 0) {
        _this.$message({
          showClose: true,
          message: '请至少选择一份材料',
          type: 'warning'
        })
        return;
      }

      var param = {};
      param['globalOrgIds'] = _this.globalOrgIds;
      param['userIds'] = _this.userIds;
      param['materialIds'] = _this.materialIds;
      param['materialNameIds'] = _this.materialNameIds;
      /*for (let i = 0;i<_this.globalOrgIds.length;i++) {
        let data = {};
        data.dataPermissionsName = "部门权限";
        data.dataId = _this.globalOrgIds[i];
        data.dataPermissionsType = '1';
        data.dataPermissionsIdentity = '1';
        param.push(data);
      }
      for (let i = 0;i<_this.userIds.length;i++) {
        let data = {};
        data.dataPermissionsName = "人员权限";
        data.dataId = _this.userIds[i];
        data.dataPermissionsType = '2';
        data.dataPermissionsIdentity = '1';
        param.push(data);
        for (let j = 0;j<_this.materialIds.length;j++) {
          let data2 = {};
          data2.dataPermissionsName = "分类权限";
          data2.dataId = _this.materialIds[j];
          data2.dataPermissionsType = '3';
          data2.dataPermissionsIdentity = '1';
          data2.data_permission_summary = _this.userIds[i];
          param.push(data2);
        }
      }

      for (let i = 0;i<_this.materialNameIds.length;i++) {
        let data = {};
        data.dataPermissionsName = "条目权限";
        data.dataId = _this.materialNameIds[i];
        data.dataPermissionsType = '4';
        data.dataPermissionsIdentity = '1';
        param.push(data);
      }
      for (let i = 0;i<_this.sysFileData.length;i++) {
        if (_this.sysFileData[i].lookChange || _this.sysFileData[i].printChecked || _this.sysFileData[i].downChecked) {
          let data = {};
          data.dataPermissionsName = "电子文件权限";
          data.dataId = _this.sysFileData[i].id;
          var str = "";
          if (_this.sysFileData[i].lookChange) {
            str+="1";
          }
          if (_this.sysFileData[i].printChecked) {
            str+="2";
          }
          if (_this.sysFileData[i].downChecked) {
            str+="3";
          }
          data.data_permission_summary = str;
          data.dataPermissionsType = '5';
          data.dataPermissionsIdentity = '1';
          param.push(data);
        }
      }*/

      var sysFile = [];
      for (let i = 0;i<_this.sysFileData.length;i++) {
        if (_this.sysFileData[i].lookChange || _this.sysFileData[i].printChecked || _this.sysFileData[i].downChecked) {
          let data = {};
          data.dataId = _this.sysFileData[i].id;
          var str = "";
          if (_this.sysFileData[i].lookChange) {
            str+="1";
          }
          if (_this.sysFileData[i].printChecked) {
            str+="2";
          }
          if (_this.sysFileData[i].downChecked) {
            str+="3";
          }
          data.data_permission_summary = str;
          sysFile.push(data);
        }
      }
      param['sysFile'] = sysFile;

      insertDataPermissionsByPermissionId({permissionId:_this.permissionId,dataPermissions:JSON.stringify(param)})
        .then(response => {
          _this.$message({
            type: 'success',
            message: '保存成功!'
          })
          _this.permissionsGrid(params);
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
      _this.dialogFormVisible1 = false;
    },
    /* 材料名称 监听 */
    materialNameChange(data, checked, indeterminate) {
      const _this = this;
      if (checked) {
        _this.materialNameIds.push(data.id);
      } else {
        let index = _this.materialNameIds.indexOf(data.id);
        _this.materialNameIds.splice(index,1);
      }

      if (_this.materialNameIds.length > 0) {
        this.sysFileTree();
      } else {
        _this.sysFileIds = [];
        _this.sysFileData=[];
      }
    },
    sysFileTree() {
      const  _this = this;
      sysFileTree({ids:_this.materialNameIds.toString()})
        .then(response => {
          _this.sysFileData = response.data;
          for (  let i = 0; i < _this.sysFileData.length; i++){
            _this.sysFileData[i].lookChange = false;
            _this.sysFileData[i].printChecked = false;
            _this.sysFileData[i].downChecked = false;
          }
        })
    },
    //  电子文件按钮 分类
    butTypeClick( num ,val ) {
      const _this = this;
      // 查看 lookChange  打印 printChecked 下载downChecked
      if(num === 1){
        val.lookChange =! val.lookChange
      }else if(num === 2) {
        val.printChecked =! val.printChecked
      }else if(num === 3) {
        val.downChecked =! val.downChecked
      }
    },
    /* 材料类型 监听*/
    materialTypeChange(data, checked, indeterminate) {
      const _this = this;
      if (checked) {
        if (data.dictCode !== '4' && data.dictCode !== '9') {
          _this.materialIds.push(data.dictCode)
        }
      } else {
        const index = _this.materialIds.indexOf(data.dictCode)
        if (index !== -1) {
          _this.materialIds.splice(index, 1)
        }
      }
      if (_this.userIds.length <= 0) {
        _this.materialIds = []
      }

      if (_this.materialIds.length <= 0) {

        _this.materialName = []
        _this.materialNameIds = []
      }
      if (_this.materialNameIds.length <= 0 || _this.materialName.length <= 0) {

        _this.sysFileData = [];
        _this.sysFileIds=[];
      }
      this.materialNameTree();
    },
    materialNameTree() {
      const  _this = this;
      materialNameTree({ userIds: _this.userIds.toString(), materialIds: _this.materialIds.toString() })
        .then(response => {
          _this.materialName = response.data
          if (_this.materialName.length > 0) {
            _this.sysFileTree();
          }
        })
    },
    /* 用户列表 监听*/
    userListChange(data, checked, indeterminate) {
      const _this = this
      if (checked) {
        _this.userIds.push(data.id)
        _this.materialType = _this.materialTypeTemp
      } else {
        const index = _this.userIds.indexOf(data.id)
        _this.userIds.splice(index, 1)
        if (_this.userIds.length <= 0 || _this.userList.length <= 0) {
          _this.materialType = [];
          _this.materialIds = [];
          _this.materialName = [];
          _this.sysFileData = [];
        }
      }
      _this.materialNameIds = [];
      _this.sysFileIds=[];
      if (_this.materialIds.length > 0) {
        _this.materialNameTree()
      }
    },
    /* 机构树  监听*/
    institutionsChange(data, checked, indeterminate) {
      const _this = this
      if (checked) {
        _this.globalOrgIds.push(data.id)
      } else {
        const index = _this.globalOrgIds.indexOf(data.id)
        if (index !== -1) {
          _this.globalOrgIds.splice(index, 1)
        }
      }

      _this.userIds = [];
      _this.materialType = [];
      _this.materialIds = [];
      _this.materialName = [];
      _this.materialNameIds = [];
      _this.sysFileData = [];
      _this.sysFileIds = [];

      /* 查询用户列表 */
      listUsersTree({ ids: _this.globalOrgIds.toString() })
        .then(response => {
          _this.userList = response.data
        })
    },
    pageCurrent(num) {
      params.pageNum = num;
      this.permissionsGrid(params);
    },
    pageSize(num) {
      params.pageSize = num;
      this.permissionsGrid(params);
    },
    addUserPageSize(num){
      const _this = this
      addUserParams.pageSize = num;
      var newAddUserParams = {pageSize:addUserParams.pageSize,pageNum:addUserParams.pageNum};
      newAddUserParams['jsonStr'] = _this.permissionId;
      queryAddUserList(newAddUserParams)
        .then(function(response) {
          for (var i = 0; i < response.rows.length; i++) {
            if (response.rows[i].userStatus === 0) {
              response.rows[i].userStatus = '已启用'
            } else {
              response.rows[i].userStatus = '未启用'
            }
          }
          _this.addUserData = (response.rows)
          _this.addUserTotal = response.total;
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
    },
    addUserPageCurrent(num){
      const _this = this
      addUserParams.pageNum = num;
      var newAddUserParams = {pageSize:addUserParams.pageSize,pageNum:addUserParams.pageNum};
      newAddUserParams['jsonStr'] = _this.permissionId;
      queryAddUserList(newAddUserParams)
        .then(function(response) {
          for (var i = 0; i < response.rows.length; i++) {
            if (response.rows[i].userStatus === 0) {
              response.rows[i].userStatus = '已启用'
            } else {
              response.rows[i].userStatus = '未启用'
            }
          }
          _this.addUserData = (response.rows)
          _this.addUserTotal = response.total;
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
    },
    permissionSearch() {
      var param = {};
      param.pageNum = params.pageNum;
      param.pageSize = params.pageSize;
      param.permissionName = this.queryForm.permissionName;
      param.orgId = this.queryForm.orgId;
      this.permissionsGrid(param);
    },
    permissionReset() {
      this.queryForm.permissionName = "";
      this.queryForm.orgId = "";
      var param = {};
      param.pageNum = params.pageNum;
      param.pageSize = params.pageSize;
      this.permissionsGrid(param);
    },
    roleSearch() {
      const  _this = this;
      var param = {};
      /*param.pageNum = params.pageNum;
      param.pageSize = params.pageSize;*/
      param.roleName = this.queryForm.e01zc24a;
      param.permissionId = this.permissionId;
      queryRoleListByPermissionId(param)
        .then(function(response) {
          for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].roleStatus === 0) {
              response.data[i].roleStatus = '已启用'
            } else {
              response.data[i].roleStatus = '未启用'
            }
          }
          _this.gridData = response.data
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })

    },
    roleReset() {
      const  _this = this;
      this.queryForm.e01zc24a = '';
      var param = {};
      /*param.pageNum = params.pageNum;
      param.pageSize = params.pageSize;
      param.roleName = this.queryForm.e01zc24a;*/
      param.permissionId = this.permissionId;
      queryRoleListByPermissionId(param)
        .then(function(response) {
          for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].roleStatus === 0) {
              response.data[i].roleStatus = '已启用'
            } else {
              response.data[i].roleStatus = '未启用'
            }
          }
          _this.gridData = response.data
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
    },
    userSearch() {
      const  _this = this;
      var param = {};

      param.permissionId = _this.permissionId;
      param.userName = _this.queryForm.e01zc24a;

      queryUserListByPermissionId(param)
        .then(function(response) {
          for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].userStatus === 0) {
              response.data[i].userStatus = '已启用'
            } else {
              response.data[i].userStatus = '未启用'
            }
          }
          _this.distributionDta = response.data
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })

    },
    userReset() {
      const  _this = this;
      var param = {};

      param.permissionId = _this.permissionId;
      _this.queryForm.e01zc24a = '';
      queryUserListByPermissionId(param)
        .then(function(response) {
          for (let i = 0; i < response.data.length; i++) {
            if (response.data[i].userStatus === 0) {
              response.data[i].userStatus = '已启用'
            } else {
              response.data[i].userStatus = '未启用'
            }
          }
          _this.distributionDta = response.data
        })
        .catch(function(error) {
          _this.$message({
            showClose: true,
            message: error,
            type: 'error'
          })
        })
    },
    search() {
    },
    reset() {},
    // 修改table header的背景色
    tableHeaderColor({ row, column, rowIndex, columnIndex }) {
        return 'background:#FFFFFF;height:31px;padding:0px 0px; border-bottom: 1px solid #cccccc; font-weight:500; text-align:center'
    }
  }
}
</script>
<style lang="scss" scoped>
.tree_block {
  border: 1px solid #cccccc;
  margin: 5px;
  height: 400px;
  overflow: auto;
}
.tree_block > p {
  text-align: center;
  border-bottom: 1px solid #cccccc;
  margin: 0;
  line-height: 30px;
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
.el-dialog__body {
  height: 500px;
  display: block;
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
.el-dialog__body {
  height: 640px;
  overflow: auto;
}
</style>

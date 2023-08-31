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
            <el-form-item label="权限名称" prop="permissionName">
              <el-input v-model="queryForm.permissionName" placeholder="权限名称" clearable size="small" style="width: 240px" />
            </el-form-item>
            <el-form-item label="所属全宗" prop="orgId"><el-input v-model="queryForm.orgId" placeholder="权限标识" clearable size="small" style="width: 240px" /></el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>
            </el-form-item>
          </el-form>
          <el-col :span="1.5"><el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button></el-col>
          <el-col :span="1.5"><el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete">删除</el-button></el-col>
          <el-dropdown style="float: right; margin-right: 30px;">
            <el-button icon="el-icon-menu" size="mini" circle />
            <el-dropdown-menu style="padding: 10px">
              <el-checkbox-group v-for="(item, index) in col" v-model="col[index].isCol" size="small">
                <el-checkbox>{{ item.label }}</el-checkbox>
              </el-checkbox-group>
            </el-dropdown-menu>
          </el-dropdown>
          <el-col :span="1.5" style="float: right;"><el-button type="primary" icon="el-icon-search" size="mini" circle @click="showSearchBox = !showSearchBox" /></el-col>
        </el-row>
        <el-table
          v-loading="loading"
          :data="tableData"
          border
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          row-key="permissionId"
          align="left"
          @current-change="handleCurrentChange"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column v-for="(item, index) in col" v-if="item.isCol" :key="index" :prop="item.prop" :label="item.label" />
          <el-table-column fixed="right" label="操作" width="300px">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="upData(scope.row)">编辑</el-button>
              <el-button type="text" size="small" @click="dialogFormVisible1 = true">数据权限</el-button>
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
      <el-dialog :title="title" :visible.sync="dialogFormVisible" width="60%">
        <h4 style="border-bottom: 1px solid #cccccc;line-height: 50px;font-size: 25px; margin-top: 0">基本信息</h4>
        <el-form ref="form" :model="form" :inline="true">
          <el-form-item label="权限名称" :label-width="formLabelWidth"><el-input v-model="form.permissionName" autocomplete="off" /></el-form-item>
          <el-form-item label="权限状态" :label-width="formLabelWidth"><el-input v-model="form.permissionStatus" autocomplete="off" /></el-form-item>
          <!-- <el-form-item label="系统内数据权限" :label-width="formLabelWidth">
            <el-select v-model="form.name" class="inputW" placeholder="请选择部门">
              <el-option label="男" value="1" />
              <el-option label="女" value="2" />
              <el-option label="未知" value="3" />
            </el-select>
          </el-form-item> -->
          <el-form-item label="备注" :label-width="formLabelWidth"><el-input v-model="form.remark" type="textarea" autocomplete="off" /></el-form-item>
        </el-form>
        <h4 style="border-bottom: 1px solid #cccccc;line-height: 50px;font-size: 25px;margin-top: 0 ">菜单权限</h4>
        <el-button type="primary" @click="dialogFormVisible = false">选择功能权限</el-button>
        <el-tree ref="tree" height="200px" :data="fnData" show-checkbox default-expand-all node-key="id" highlight-current :props="defaultProps" />
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="addFormSave('form')">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog title="数据权限" :visible.sync="dialogFormVisible1" width="80%" height="800px">
        <el-form :model="form" :inline="true" style="overflow: hidden">
          <el-form-item label="权限名称" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
          <el-form-item label="权限标识" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
          <el-form-item label="权限状态" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
        </el-form>
        <div class="custom-tree-container" style="overflow: hidden">
          <div class="tree_block" style="width: 200px;float: left">
            <p>机构列表</p>
            <el-tree :data="institutionsTree" show-checkbox node-key="id" default-expand-all />
          </div>
          <div class="tree_block" style="width: 200px;float: left">
            <p>用户列表</p>
            <el-tree :data="userListTree" show-checkbox node-key="id" default-expand-all />
          </div>
          <div class="tree_block" style="width: 400px;float: left">
            <p>材料类型</p>
            <el-tree :data="materialType" show-checkbox node-key="id" default-expand-all />
          </div>
          <div class="tree_block" style="width: 330px;float: left">
            <p>材料名称</p>
            <el-tree :data="materialName" show-checkbox node-key="id" default-expand-all />
          </div>
          <div class="tree_block" style="width: 300px;float: left;">
            <p>电子文件</p>
            <el-checkbox v-model="checkAll" style="padding: 0 10px" :indeterminate="isIndeterminate" @change="handleCheckAllChange">全选</el-checkbox>
            <div style="margin: 15px 0;" />
            <el-checkbox-group v-model="checkedCities" style="padding: 0 10px" @change="handleCheckedCitiesChange">
              <el-checkbox v-for="city in cities" :key="city" :label="city">{{ city }}</el-checkbox>
            </el-checkbox-group>
          </div>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible1 = false">取消</el-button>
          <el-button type="primary" @click="">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog title="角色绑定" :visible.sync="dialogTableVisible">
        <el-form ref="queryForm" class="transition" :model="queryForm" :inline="true" label-width="68px" style="padding: 10px 0;margin-bottom: 10px;">
          <el-form-item label="角色名称" prop="e01zc24a"><el-input v-model="queryForm.e01zc24a" placeholder="角色名称" clearable size="small" style="width: 240px" /></el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="gridData" height="400px" @selection-change="roleSelectionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column v-for="(item, index) in gridDataCol" :prop="item.prop" :label="item.label" />
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible = false">取消</el-button>
          <el-button type="primary" @click="roleFormSave">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog title="分配用户" :visible.sync="dialogTableVisible1" width="60%">
        <el-form ref="distributionForm" class="transition" :model="distributionForm" :inline="true" label-width="68px" style="padding: 10px 0;margin-bottom: 10px;">
          <el-form-item label="权限名称" prop="permissionName">
            <el-input v-model="distributionForm.permissionName" placeholder="角色名称" clearable size="small" style="width: 240px" />
          </el-form-item>
          <el-form-item label="权限标识" prop="orgId">
            <el-input v-model="distributionForm.orgId" placeholder="角色名称" clearable size="small" style="width: 240px" />
          </el-form-item>
          <el-form-item label="权限状态" prop="permissionStatus">
            <el-input v-model="distributionForm.permissionStatus" placeholder="角色名称" clearable size="small" style="width: 240px" />
          </el-form-item>
        </el-form>
        <el-form ref="queryForm" class="transition" :model="queryForm" :inline="true" label-width="68px" style="padding: 10px 0;margin-bottom: 10px;">
          <el-form-item label="角色名称" prop="e01zc24a"><el-input v-model="queryForm.e01zc24a" placeholder="角色名称" clearable size="small" style="width: 240px" /></el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>
            <el-button type="primary" size="mini">添加用户</el-button>
            <el-button type="primary" size="mini" @click="Cancel">取消授权</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="distributionDta" height="300px" border @selection-change="distributionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column v-for="(item, index) in distributionCol" :prop="item.prop" :label="item.label" />
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogTableVisible1 = false">取消</el-button>
          <el-button type="primary" @click="dialogTableVisible1 = false">保存</el-button>
        </div>
      </el-dialog>
    </el-row>
  </div>
</template>
<script>
import { queryPermissionPageList } from '@/api/system/permissions'
import { insertSysPermission } from '@/api/system/permissions'
import { deleteSysPermissionById } from '@/api/system/permissions'
import { insertRolePermission } from '@/api/system/permissions'
import { queryRoleListByPermissionId } from '@/api/system/permissions'
import { queryUserListByPermissionId } from '@/api/system/permissions'
import { deleteUserPermissionsByPermissionId } from '@/api/system/permissions'
const cityOptions = ['查看', '下载', '打印']
const params = {
  pageNum: 1,
  pageSize: 20
}
export default {
  data() {
    return {
      loading: true,
      total: 0,
      title:'权限新增',
      defaultProps: {
        children: 'children',
        label: 'label',
        id: 'id'
      },
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

      dialogFormVisible1: false,
      dialogFormVisible: false,
      dialogTableVisible: false,
      dialogTableVisible1: false,
      form: {
        permissionName: '',
        permissionStatus: '',
        remark: ''
      },

      formLabelWidth: '120px',
      showSearchBox: true,
      queryForm: {
        permissionName: '',
        orgId: ''
      },
      /* 机构树 */
      institutionsTree: [],
      /* 用户列表 树*/
      userListTree: [],
      /* 材料类型 */
      materialType: [],
      /* 材料名称 */
      materialName: [],
      /* 功能权限树 */
      fnData: [],
      col: [
        { label: '权限主键', prop: 'permissionId', isCol: false },
        { label: '权限名称', prop: 'permissionName', isCol: true },
        { label: '所属全宗', prop: 'orgId', isCol: true },
        { label: '创建时间', prop: 'createTime', isCol: true },
        { label: '创建人', prop: 'createBy', isCol: true },
        { label: '修改时间', prop: 'updateTime', isCol: true },
        { label: '修改人', prop: 'updateBy', isCol: true },
        { label: '权限状态', prop: 'permissionStatus', isCol: true },
        { label: '备注', prop: 'remark', isCol: true }
      ],
      tableData: [],
      gridCheck: [],
      permissionId: '',
      roleSelectionCheck: [],
      distributionCheck: []
    }
  },
  mounted() {
    this.permissionsGrid(params)
  },
  methods: {
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
    handleCurrentChange(val) {
      console.log(val)
    },
    // 复选框事件
    handleSelectionChange(val) {
      this.gridCheck = val
    },
    // 编辑
    upData(val) {
      this.title = '编辑权限'
      this.dialogFormVisible = true
    },
    // 指定角色
    SpecifyRole(val) {
      console.log(val)
      this.dialogTableVisible = true
      this.permissionId = val.permissionId
      const _this = this
      queryRoleListByPermissionId({ permissionId: val.permissionId }, { emulateJSON: true })
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
      console.log(this.roleSelectionCheck)
      for (let i = 0; i < this.roleSelectionCheck.length; i++) {
        if (this.roleSelectionCheck[i].roleStatus === '已启用') {
          this.roleSelectionCheck[i].roleStatus = 0
        } else {
          this.roleSelectionCheck[i].roleStatus = 1
        }
      }
      const _this = this
      const param = {
        permissionId: this.permissionId,
        jsonStr: JSON.stringify(this.roleSelectionCheck)
      }
      insertRolePermission(param, { emulateJSON: true })
        .then(function(response) {
          console.log(response)
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
      console.log(val)
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
      for (let i = 0; i < this.distributionCheck.length; i++) {
        if (this.distributionCheck[i].userStatus === '已启用') {
          this.distributionCheck[i].userStatus = 0
        } else {
          this.distributionCheck[i].userStatus = 1
        }
      }
      const param = {
        jsonStr: JSON.stringify(this.distributionCheck),
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
            deleteUserPermissionsByPermissionId(param, { emulateJSON: true })
              .then(function(response) {
                // _this.permissionsGrid(params)
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
      this.dialogFormVisible = true
    },
    /* 新增保存 */
    addFormSave(form) {
      const _this = this
      this.$refs[form].validate(valid => {
        if (valid) {
          insertSysPermission(this.form, { emulateJSON: true })
            .then(function(response) {
              _this.permissionsGrid(params)
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
          return false
        }
      })
    },
    /* 多选删除 */
    handleDelete() {
      const _this = this
      var param = JSON.stringify(this.gridCheck)
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
    pageCurrent() {},
    pageSize() {},
    search() {},
    reset() {}
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

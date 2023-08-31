<template>
  <div class="app-find">
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
            style="padding: 15px;margin-right: 20px;margin-bottom: 10px;"
          >
            <el-form-item label="库房名称" prop="e01zc24a">
              <el-input v-model="queryForm.e01zc24a" placeholder="请输入库房名称" clearable size="mini" style="width: 240px" />
            </el-form-item>
            <el-form-item label="库房地址" prop="e01zc24a">
              <el-input v-model="queryForm.e01zc24a" placeholder="请输入库房地址" clearable size="mini" style="width: 240px" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>
            </el-form-item>
          </el-form>
          <el-col :span="1.5"><el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd">新增库房</el-button></el-col>
          <!-- <el-col :span="1.5"><el-button type="success" icon="el-icon-edit" size="mini" @click="handleUpData">编辑</el-button></el-col> -->
          <el-col :span="1.5"><el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete">删除</el-button></el-col>
          <el-dropdown style="float: right; margin-right: 30px;">
            <el-button icon="el-icon-menu" size="mini" circle />
            <el-dropdown-menu style="padding: 10px">
              <el-checkbox-group v-for="(item, index) in col" v-model="col[index].isCol" size="mini">
                <el-checkbox>{{ item.label }}</el-checkbox>
              </el-checkbox-group>
            </el-dropdown-menu>
          </el-dropdown>
          <el-col :span="1.5" style="float: right;">
            <el-button type="primary" icon="el-icon-search" size="mini" circle @click="showSearchBox = !showSearchBox" />
          </el-col>
        </el-row>
        <el-table :data="tableData" border row-key="id" align="left" @current-change="handleCurrentChange" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column v-for="(item, index) in col" v-if="col[index].isCol" :key="index" :prop="col[index].prop" :label="item.label" />
          <el-table-column fixed="right" label="操作" width="200px">
            <template slot-scope="scope">
              <el-button type="text" size="mini" @click="handleUpData(scope.row)">编辑</el-button>
              <el-button type="text" size="mini" @click="structure(scope.row)">结构管理</el-button>
              <el-button type="text" size="mini" @click="archivesChange()">档案管理</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="block" style="float: right;padding: 10px">
          <el-pagination
            :page-sizes="[100, 200, 300, 400]"
            :page-size="100"
            layout="total, sizes, prev, pager, next, jumper"
            :total="400"
            @size-change="pageSize"
            @current-change="pageCurrent"
          />
        </div>
      </el-col>
      <el-dialog title="新增组织机构" :visible.sync="dialogFormVisible">
        <div style="overflow: hidden;">
          <el-col :span="12" style="padding:0 10px;border-right: 1px solid #CCCCCC;">
            <h2 style="padding-left: 15px;margin-top: 0;border-bottom: 1px solid #CCCCCC;">新增库房</h2>
            <el-form :model="form">
              <el-form-item label="*库房名称" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="*库房面积" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="*库房位置" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="*库房管理员" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="备注" :label-width="formLabelWidth"><el-input v-model="form.desc" type="textarea" /></el-form-item>
            </el-form>
          </el-col>
          <el-col :span="12" style="padding:0 10px;">
            <h2 style="padding-left: 15px;margin-top: 0;border-bottom: 1px solid #CCCCCC;">库房结构快速定义(选填)</h2>
            <el-form :model="form">
              <el-form-item label="列数" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="节数" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="层数" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="存放数" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
            </el-form>
          </el-col>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog title="组织机构编辑" :visible.sync="dialogFormVisible1">
        <div style="overflow: hidden;">
          <el-col :span="12" style="padding:0 10px;border-right: 1px solid #CCCCCC;">
            <h2 style="padding-left: 15px;margin-top: 0;border-bottom: 1px solid #CCCCCC;">编辑库房</h2>
            <el-form :model="form">
              <el-form-item label="*库房名称" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="*库房面积" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="*库房位置" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="*库房管理员" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="备注" :label-width="formLabelWidth"><el-input v-model="form.desc" type="textarea" /></el-form-item>
            </el-form>
          </el-col>
          <el-col :span="12" style="padding:0 10px;">
            <h2 style="padding-left: 15px;margin-top: 0;border-bottom: 1px solid #CCCCCC;">库房结构快速定义(选填)</h2>
            <el-form :model="form">
              <el-form-item label="列数" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="节数" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="层数" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
              <el-form-item label="存放数" :label-width="formLabelWidth"><el-input v-model="form.name" autocomplete="off" /></el-form-item>
            </el-form>
          </el-col>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogFormVisible1 = false">取消</el-button>
          <el-button type="primary" @click="dialogFormVisible1 = false">保存</el-button>
        </div>
      </el-dialog>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      /* 全选 */
      isIndeterminate: true,
      dialogFormVisible: false,
      dialogFormVisible1: false,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      formLabelWidth: '130px',
      showSearchBox: true,
      queryForm: {
        e01zc24a: '',
        e01z804: ''
      },
      col: [
        { label: '库房名称', prop: 'name', isCol: true },
        { label: '库房面积', prop: 'address', isCol: true },
        { label: '库房地址', prop: 'address1', isCol: true },
        { label: '库房管理员', prop: 'address2', isCol: true },
        { label: '总列数', prop: 'address3', isCol: true },
        { label: '总节数', prop: 'address4', isCol: true },
        { label: '总层数', prop: 'address5', isCol: true },
        { label: '总容量', prop: 'address6', isCol: true },
        { label: '已占用', prop: 'address7', isCol: true },
        { label: '剩余容量', prop: 'address8', isCol: true }
      ],
      tableData: [
        {
        name:'a库房',
        address:'500',
        address1:'2楼',
        address2:'梁建华',
        address3:'3',
        address4:'3',
        address5:'3',
        address6:'9',
        address7:'3',
        address8:'6',
        },
            {
        name:'b库房',
        address:'500',
        address1:'2楼',
        address2:'梁建华',
        address3:'3',
        address4:'3',
        address5:'3',
        address6:'9',
        address7:'3',
        address8:'6',
        }
      ]
    }
  },
  methods: {
    // 档案管理跳转
    archivesChange(){
this.$router.push({name:'Archives'})
    },
    /* grid  行点击事件*/
    handleCurrentChange(val) {
      console.log(val)
    },
    /* 复选框事件 */
    handleSelectionChange(val) {
      console.log(val)
    },
    /* 编辑 */
    handleUpData(val) {
      console.log(val)
      this.dialogFormVisible1 = !this.dialogFormVisible1
    },
    /* 机构管理 */
    structure(val) {
      this.$router.replace({ name: 'Structure', params: { val: val }})
    },
    archivesTypeClick() {},
    /* 删除 */
    delete(val) {
      console.log(val)
    },
    handleAdd() {
      this.dialogFormVisible = true
    },
    handleDelete() {},
    pageCurrent() {},
    pageSize() {},
    search() {},
    reset() {}
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
  height: calc(100vh - 86px);
}
.el-form--inline .el-form-item {
margin:0px;
}
</style>

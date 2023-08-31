<template>
  <div class="app-security">
    <el-row>
      <el-col :span="4"><el-tree ref="tree" :data="data" show-checkbox default-expand-all node-key="id" highlight-current :props="defaultProps" /></el-col>
      <el-col :span="20" style="padding: 10px">
        <el-row :gutter="10" class="mb8" style="margin:0 0 10px 0;padding-left: 5px">
          <el-form
            v-if="showSearchBox"
            ref="queryForm"
            class="transition"
            :model="queryForm"
            :inline="true"
            label-width="68px"
            style="background:rgba(204, 204, 204, 0.18);padding: 10px 0;margin-bottom: 10px;"
          >
            <el-form-item label="检查人" prop="e01zc24a">
              <el-input v-model="queryForm.e01zc24a" placeholder="请输入检查人" clearable size="mini" />
            </el-form-item>
            <el-form-item label="检查时间">
              <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" size="mini"></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>
            </el-form-item>
          </el-form>
          <el-col :span="1.5"><el-button type="primary" size="mini" icon="el-icon-plus" @click="handleAdd">新增安全检查记录</el-button></el-col>
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
        <el-table :data="tableData" border row-key="id" align="left" @current-change="handleCurrentChange" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column v-for="(item, index) in col" v-if="col[index].isCol" :key="index" :prop="col[index].prop" :label="item.label" />
          <el-table-column fixed="right" label="操作" width="50px">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="edit(scope.row)">编辑</el-button>
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
      <el-dialog title="修改安全检查记录" :visible.sync="dialogFormVisible">
        <el-form :model="form" :inline="true">
            <el-form-item label="*检查人" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*检查时间" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*温度" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*湿度" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*库房基本配置" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*灭火设施检查" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*防尘检查" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*防蛀检查" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*通风情况" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*线路设置" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*易燃易爆品防护" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*备注" :label-width="formLabelWidth"><el-input v-model="form.name" type="textarea" class="inputW" /></el-form-item>
          </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog title="新增安全检查记录" :visible.sync="dialogFormVisible1">
          <el-form :model="form" :inline="true">
            <el-form-item label="*检查人" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*检查时间" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*温度" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*湿度" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*库房基本配置" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*灭火设施检查" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*防尘检查" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*防蛀检查" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*通风情况" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*线路设置" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*易燃易爆品防护" :label-width="formLabelWidth"><el-input v-model="form.name" type="text" class="inputW" /></el-form-item>
            <el-form-item label="*备注" :label-width="formLabelWidth"><el-input v-model="form.name" type="textarea" class="inputW" /></el-form-item>
          </el-form>
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
      data: [
        {
          id: 1,
          label: '一级 1',
          children: [
            {
              id: 4,
              label: '二级 1-1',
              children: [
                {
                  id: 9,
                  label: '三级 1-1-1'
                },
                {
                  id: 10,
                  label: '三级 1-1-2'
                }
              ]
            }
          ]
        },
        {
          id: 2,
          label: '一级 2',
          children: [
            {
              id: 5,
              label: '二级 2-1'
            },
            {
              id: 6,
              label: '二级 2-2'
            }
          ]
        },
        {
          id: 3,
          label: '一级 3',
          children: [
            {
              id: 7,
              label: '二级 3-1'
            },
            {
              id: 8,
              label: '二级 3-2'
            }
          ]
        }
      ],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      col: [
        { label: '检查人', prop: 'name', isCol: true },
        { label: '检查时间', prop: 'address', isCol: true },
        { label: '温度', prop: 'date', isCol: true },
        { label: '湿度', prop: 'date1', isCol: true },
        { label: '库房基本配置', prop: 'name', isCol: true },
        { label: '灭火设施检查', prop: 'address', isCol: true },
        { label: '防尘检查', prop: 'date', isCol: true },
        { label: '防蛀检查', prop: 'name', isCol: true },
        { label: '通风情况', prop: 'address', isCol: true },
        { label: '线路设置', prop: 'date', isCol: true },
        { label: '易燃易爆品防护', prop: 'address', isCol: true },
        { label: '备注', prop: 'date', isCol: true }
      ],
      tableData: [
      ]
    }
  },
  methods: {
    /* grid  行点击事件*/
    handleCurrentChange(val) {
      console.log(val)
    },
    /* 复选框事件 */
    handleSelectionChange(val) {
      console.log(val)
    },
    /* 新增 */
    handleAdd() {
      this.dialogFormVisible1 = !this.dialogFormVisible1
    },
    /* 编辑 */
    edit(val) {
      this.dialogFormVisible = !this.dialogFormVisible
    },
    /* 机构管理 */
    structure(val) {
      this.$router.replace({ path: '/views/house/structure', params: { val: val }})
    },
    /* 删除 */
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
  .app-security {
    overflow: auto;
    height: calc(100vh - 86px);
  }
  .inputW {
    width: 250px;
  }
  .el-form--inline .el-form-item {
margin:0px;
}
</style>

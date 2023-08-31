<template>
  <div class="app-archives">
    <el-row>
      <el-col :span="4"><el-tree ref="tree" :data="data" default-expand-all node-key="id" highlight-current :props="defaultProps" /></el-col>
      <el-col :span="20" style="padding: 10px">
        <el-row :gutter="10" class="mb8" style="margin:0 0 10px 0;padding-left: 5px">
          <el-form
            v-if="showSearchBox"
            ref="queryForm"
            class="transition"
            :model="queryForm"
            :inline="true"
            label-width="68px"
            style="padding: 15px 0;margin-bottom: 10px;padding-left: 10px;"
          >
            <el-form-item label="干部名称" prop="e01zc24a">
              <el-input v-model="queryForm.e01zc24a" placeholder="请输入干部名称" clearable size="small" style="width: 240px" />
            </el-form-item>
            <el-form-item label="所在位置" prop="e01zc24a">
              <el-input v-model="queryForm.e01zc24a" placeholder="请输入所在位置" clearable size="small" style="width: 240px" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>
            </el-form-item>
          </el-form>
          <el-col :span="1.5"><el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd">档案新增</el-button></el-col>
          <el-col :span="1.5"><el-button type="success" icon="el-icon-sort" size="mini" @click="handleUpData">更改位置</el-button></el-col>
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
          <el-table-column fixed="right" label="操作" width="80px">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="outbound(scope.row)">出库</el-button>
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
      <el-dialog title="库房位置变动" :visible.sync="dialogFormVisible">
        <div style="overflow: hidden;">
          <el-tree ref="tree" :data="data" show-checkbox default-expand-all node-key="id" highlight-current :props="defaultProps" />
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog title="档案新增" :visible.sync="dialogFormVisible1">
        <div style="width: 500px;margin: 0 auto;">
          <el-form :model="form">
            <el-form-item label="选择档案" :label-width="formLabelWidth">
                <el-select v-model="form.region" placeholder="请选择选择档案" class="inputW">
                  <el-option label="区域一" value="shanghai"></el-option>
                  <el-option label="区域二" value="beijing"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="选择位置" :label-width="formLabelWidth">
               <el-select v-model="form.region" placeholder="请选择选择位置" class="inputW">
                 <el-option label="区域一" value="shanghai"></el-option>
                 <el-option label="区域二" value="beijing"></el-option>
               </el-select>
            </el-form-item>
            <el-form-item label="存放数" :label-width="formLabelWidth"><el-input type="textarea" v-model="form.desc" class="inputW"></el-input></el-form-item>
          </el-form>
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
      data: [
        {
          id: 1,
          label: 'a库房',
          children: [
            {
              id: 4,
              label: '列',
              children: [
                {
                  id: 9,
                  label: '节'
                },
                {
                  id: 10,
                  label: '层'
                }
              ]
            }
          ]
        },
        {
          id: 2,
          label: 'b库房',
          children: [
            {
              id: 5,
              label: '列'
            },
            {
              id: 6,
              label: '节'
            }
          ]
        },
        {
          id: 3,
          label: 'c库房',
          children: [
            {
              id: 7,
              label: '列'
            },
            {
              id: 8,
              label: '节'
            }
          ]
        }
      ],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      col: [
        { label: '序号', prop: 'address', isCol: true },
        { label: '干部名称', prop: 'name', isCol: true },
        { label: '所在位置', prop: 'date', isCol: true },
        { label: '当前状态', prop: 'date1', isCol: true }
      ],
      tableData: [
        {
          address:'1',
          name:'a库房',
          date:'2楼',
          date1:'开放'
          },{
          address:'2',
          name:'b库房',
          date:'2楼',
          date1:'开放'
          }

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
    handleUpData(val) {
      this.dialogFormVisible = !this.dialogFormVisible
    },
    /* 机构管理 */
    structure(val) {
      this.$router.replace({ path: '/views/house/structure', params: { val: val }})
    },
    /* 出库 */
    outbound() {},
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
.el-input {
  width: 250px;
}
.inputW {
  width: 250px;
}
.el-form--inline .el-form-item {
margin:0px;
}
</style>

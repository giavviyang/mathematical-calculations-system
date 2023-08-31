<template>
  <div class="app-structure">
    <!-- 搜索框 -->
    <el-form :inline="true" class="form-inline" v-if="showSearchBox" 
    style="padding: 15px;margin-right: 20px;margin-left: 10px;    margin-top: 10px;">
    <el-form-item label="列名称">
              <el-input placeholder="请输入内容" clearable size="mini" />
            </el-form-item>
            <el-form-item label="节名称">
              <el-input placeholder="请输入内容" clearable size="mini" />
            </el-form-item>
      <el-form-item label="层名称" >
        <el-input placeholder="请输入内容" clearable size="mini"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="onSubmit">搜索</el-button>
      </el-form-item>
      <el-form-item>
         <el-button size="mini" icon="el-icon-refresh" @click="onSubmit">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 功能按钮 -->
    <div class="btn">
      <div class="btn-search">
        <el-button icon="el-icon-search" size="mini" type="primary" circle @click="showSearchBox = !showSearchBox"/>
        <el-button icon="el-icon-menu" size="mini" circle />
      </div>
    </div>
    <!-- 库房表格 -->
    <el-table
      :data="tableData"
      style="width: 100%;padding-left:10px;padding-roght:10px"
      row-key="id"
      border
      lazy
      :load="load"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column
        type="index"
        width="50px"
        label="序号"
      />
      <el-table-column
        prop="name"
        label="名称"
      />
      <el-table-column
        prop="address"
        label="总容量"
      />
      <el-table-column
        prop="address"
        label="已占用"
      />
      <el-table-column
        prop="address"
        label="剩余容量"
      />
      <el-table-column
        label="操作"
      >
        <template>
          <el-button type="text" size="mini">新增</el-button>
          <el-button type="text" size="mini">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="page">
      <el-pagination
        :current-page="currentPage4"
        :page-sizes="[100, 200, 300, 400]"
        :page-size="100"
        layout="total, sizes, prev, pager, next, jumper"
        :total="400"
        @size-change="handleSizeChange"
      />
    </div>
    <el-dialog title="新增" :visible.sync="dialogFormVisible1">
      <el-form :model="form">
        <el-form-item label="*节数" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="*层数" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="*层存放数" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="*别名" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogFormVisible1 = false">取消</el-button>
        <el-button type="primary" @click="dialogFormVisible1 = false">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showSearchBox:true,
      form: {
        name: ''
      },
      tableData: [{
        id: 1,
        name: '库房1',
        address: '1'
      }, {
        id: 2,
        name: '库房2',
        address: '2'
      }, {
        id: 3,
        name: '库房3',
        address: '2',
        children: [{
          id: 31
          //   name: '王小虎',
          //   address: '1',
        }, {
          id: 32
          //   name: '王小虎',
          //   address: '2'
        }]
      }, {
        id: 4,
        name: '库房4',
        address: '4'
      }]
    }
  },
  methods: {
    onSubmit() {

    },
    load(tree, treeNode, resolve) {
      setTimeout(() => {
        resolve([
          {
            id: 31,
            name: '列',
            address: '9'

          }, {
            id: 32,
            name: '节',
            address: '9'
          }, {
            id: 33,
            name: '层',
            address: '9'
          }
        ])
      }, 1000)
    }
  }
}

</script>

<style lang="scss" scoped>
// .form-inline {
//     width:100%;
//     height: 100%;
//     padding-left: 20px;
//     padding-top: 10px;
//     border-bottom:1px solid #e2e3e6;
// }
.btn {
    position: relative;
    width: 100%;
    height: 55px;
    // padding-top:10px;
    // padding-bottom: 20px;
    // border-bottom:1px solid #e2e3e6;
    .btn-search {
        position: absolute;
        right: 40px;
        top:10px;
    }
}
.el-form--inline .el-form-item {
margin:0px;
}
.page {
  float: right;
}
</style>

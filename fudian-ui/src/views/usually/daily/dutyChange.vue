<template>
  <div class="app-dutyChange">
    <div class="container">
      <el-col :span="20" ref="searchRow"></el-col>
      <div class="btn">
        <el-button type="primary" icon="el-icon-plus" size="mini" @click="onAdd">新增</el-button>
        <el-button v-model="showdaliog" type="primary" icon="el-icon-edit" size="mini" @click="onUpdate">编辑</el-button>
        <el-button v-model="showdaliog" type="primary" icon="el-icon-delete" size="mini" @click="onDelete">删除</el-button>
        <el-button type="primary" icon="el-icon-sort" size="mini" @click="sort">排序</el-button>
        <el-button type="primary" icon="el-icon-upload2" size="mini" @click="onReturn">导出</el-button>
        <el-button type="primary" icon="el-icon-tickets" size="mini" @click="onCopy">打印</el-button>
      </div>
      <el-table
        ref="tableRefs"
        :height="tableHeight"
        :data="tableData1"
        size="mini"
        style="width: 100%"
        tooltip-effect="light"
        border
        empty-text=" "
        :header-cell-style="headClass"
        row-key="id"
        :stripe="true"
        @row-click="StepsListRowClick"
        @selection-change="selectChecked"
      >
        <el-table-column type="selection" :resizable="false" align="center"/>
        <el-table-column type="index" label="序号" width="50px" align="center" :resizable="false" />
        <!-- <el-table-column type="index" label="序号" width="50px" align="center" :resizable="false" /> -->
        <el-table-column label="干部名称" prop="a0101" :resizable="false" align="center" />
        <el-table-column label="性别" prop="address222" :resizable="false" align="center" />
        <el-table-column label="出生日期" prop="daty" :resizable="false" align="center" />
        <el-table-column label="籍贯" prop="address1" :resizable="false" align="center" />
        <el-table-column label="编号" prop="data" :resizable="false" align="center" />
        <el-table-column label="操作" fixed="right" width="130px" :resizable="false" align="center">
          <template slot-scope="">
            <el-button type="text" size="mini">附件下载</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="block" style="float: right;padding: 10px">
        <el-pagination style="width: 100%;" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                       :page-sizes="[100, 200, 300, 400]" :page-size="100"
                       layout="total, sizes, prev, pager, next, jumper" :total="400"></el-pagination>
      </div>
    </div>
    <!-- 新增 -->
    <el-dialog :title="isEdit?'编辑':'新增职务变动情况'" :close-on-click-modal="false" :visible.sync="dialogVisible" class="dialog-style dialog-basic" width="50%">
      <el-form ref="formProve" :inline="true" :model="formProve" label-position="right">
        <div class="form-basic">第一步：请填写基本信息</div>
        <el-form-item label="干部名称" label-width="110px" prop="a0101" size="mini">
          <el-input v-model="formProve.a0101" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="性别" label-width="110px" size="mini" prop="a0104">
          <el-select v-model="formProve.a0104" placeholder="请选择">
            <el-option label="男" value="1" />
            <el-option label="女" value="2" />
            <el-option label="未知" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期" label-width="110px" size="mini" type="date">
          <el-date-picker type="date" placeholder="选择日期" size="mini" v-model="formProve.a0107" style="width: 159px"></el-date-picker>
        </el-form-item>
        <el-form-item label="籍贯" label-width="110px" size="mini">
          <el-input v-model="formProve.a0111a" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="干部编号" label-width="110px" size="mini">
          <el-input v-model="formProve.dutyIndex" placeholder="请输入内容" />
        </el-form-item>
        <div class="form-supplement" style="">第二步：请填写职务变动情况</div>
        <el-form-item label="任职日期" label-width="110px" size="mini" type="date">
          <el-date-picker type="date" placeholder="选择日期" size="mini" v-model="formProve.onDate" style="width: 159px"></el-date-picker>
        </el-form-item>
        <el-form-item label="免职日期" label-width="110px" size="mini" type="date">
          <el-date-picker type="date" placeholder="选择日期" size="mini" v-model="formProve.offDate" style="width: 159px"></el-date-picker>
        </el-form-item>
        <el-form-item label="部门" label-width="110px" size="mini">
          <el-select v-model="formProve.deptName" placeholder="请选择">
            <el-option label="组织部" value="1" />
            <el-option label="人事部" value="2" />
            <el-option label="研发部" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="职务" label-width="110px" size="mini" prop="duty">
          <el-input v-model="formProve.duty" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="批准文电号" label-width="110px" size="mini">
          <el-input v-model="formProve.approvalnum" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item size="mini" style="margin-left:60px">
          <el-upload
            action="https://jsonplaceholder.typicode.com/posts/"
            multiple
            :limit="3"
            :file-list="fileList"
          >
            <el-button size="small" type="primary">上传文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" size="mini">确定</el-button>
      </span>
    </el-dialog>
    <!-- 排序 -->
    <el-dialog title="职务变动情况排序" :close-on-click-modal="false" :visible.sync="dialogVisible2" class=" dialog-style dialog-sort" width="50%">
        <div class="btn-sort">
          <el-button type="primary" icon="el-icon-top" size="mini">上移</el-button>
          <el-button type="primary" icon="el-icon-bottom" size="mini">下移</el-button>
          <el-button type="primary" icon="el-icon-video-pause" size="mini">保存</el-button>
        </div>
      <el-table
          ref="tableRefsSort"
          :data="tableDataSort"
          size="mini"
          :header-cell-style="headClass"
          tooltip-effect="light"
          border
          row-key="id"
          class="table-sort"
          :stripe="true"
            >
            <el-table-column type="selection" :resizable="false" />
            <el-table-column type="index" label="序号" show-overflow-tooltip :resizable="false" align="center"></el-table-column>
            <el-table-column label="干部名称" prop="e01z111a" show-overflow-tooltip :resizable="false" align="center"></el-table-column>
            <el-table-column label="性别" prop="e01z114" show-overflow-tooltip :resizable="false" align="center"></el-table-column>
            <el-table-column label="出生日期" prop="e01z117" show-overflow-tooltip :resizable="false" align="center" width=110px></el-table-column>
            <el-table-column label="籍贯" prop="wu" show-overflow-tooltip :resizable="false" align="center" width=110px></el-table-column>
            <el-table-column label="编号" prop="e01z121" show-overflow-tooltip :resizable="false" align="center"></el-table-column>

          </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false" size="mini">取 消</el-button>
        <el-button type="primary" size="mini">确定</el-button>
      </span>
    </el-dialog>
    <!-- 导出 -->
    <!-- <el-dialog title="导出档案电子目录" :close-on-click-modal="false" class="dialog-style" :visible.sync="dialogVisible5" width="25%">
      <div class="dialog-return">
        <span class="return">导出文件类型</span>
        <el-form class="return-form">
          <el-form-item size="mini">
            <el-radio-group v-model="formreturn.state">
              <el-radio style="margin-top:20px" label="Excel" />
              <el-radio style="margin-bottom:10px" label="Word" />
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible3 = false">取 消</el-button>
        <el-button type="primary" size="mini">确定</el-button>
      </span>
    </el-dialog> -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      // dialogVisible5: false, // 导出
      dialogVisible2: false, // 编辑
      dialogVisible: false, // 新增
      showdaliog: 'true',
      isEdit: false,
      checkList: [], // 表格勾选数组
      formreturn: { // 导出表单数据
        state: 'Excel'
      },
      // 新增/编辑-弹出框表单
      formProve: {
        a0101: '',
        a0104: '',
        a0107: '',
        a0111a: '',
        dutyIndex:'',
        name1: '', // 编辑
        onDate: '', // 任职日期
        offDate: '', // 免职日期
        deptName: '',//部门
        duty: '',
        approvalnum: ''// 批准文电号
      },
      fileList: [{ name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100' }],

      // 表格数据
      tableData1: [{
          id: '1',
          a0101: '梁建华',
          daty: '梁建华1',
          address222: '梁建华2',
          address1: '梁建华3',
          data: '梁建华4'
        },
        {
          id: '2',
          a0101: '梁建华',
          daty: '梁建华1',
          address222: '梁建华2',
          address1: '梁建华3',
          data: '梁建华4'
        },
        {
          id: '3',
          a0101: '梁建华',
          daty: '梁建华1',
          address222: '梁建华2',
          address1: '梁建华3',
          data: '梁建华4'
        },
        {
          id: '4',
          a0101: '梁建华',
          daty: '梁建华1',
          address222: '梁建华2',
          address1: '梁建华3',
          data: '梁建华4'
        },
        {
          id: '5',
          a0101: '梁建华',
          daty: '梁建华1',
          address222: '梁建华2',
          address1: '梁建华3',
          data: '梁建华4'
        },
        {
          id: '6',
          a0101: '梁建华',
          daty: '梁建华1',
          address222: '梁建华2',
          address1: '梁建华3',
          data: '梁建华4'
        },
        {
          id: '7',
          a0101: '梁建华',
          daty: '梁建华1',
          address222: '梁建华2',
          address1: '梁建华3',
          data: '梁建华4'
        },
        {
          id: '8',
          a0101: '梁建华',
          daty: '梁建华1',
          address222: '梁建华2',
          address1: '梁建华3',
          data: '梁建华4'
        },
        {
          id: '9',
          a0101: '梁建华',
          daty: '梁建华1',
          address222: '梁建华2',
          address1: '梁建华3',
          data: '梁建华4'
        },
        {
          id: '10',
          a0101: '梁建华',
          daty: '梁建华1',
          address222: '梁建华2',
          address1: '梁建华3',
          data: '梁建华4'
        },
        {
          id: '11',
          a0101: '梁建华',
          daty: '梁建华1',
          address222: '梁建华2',
          address1: '梁建华3',
          data: '梁建华4'
        },
      ],
      //排序表格数据
      tableDataSort:[{

      }],

      tableHeight:'73%',
      searchRowHeight:'',
      searchRow:'searchRow',
    }
  },
  mounted() {
    this.$nextTick(()=>{
      this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
      this.tableHeight = (document.body.clientHeight - (140 + 70 + this.searchRowHeight)) + "px"
    })
    window.onresize = () => {
      return (() => {
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (140 + 70 + this.searchRowHeight)) + "px"
      })();
    };
  },
  methods: {
    // 表格-公用表头样式
    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
    },
    // 表格勾选框变化-方法
    selectChecked(data) {
      this.selectList = [...data]
      this.showdaliog = this.selectList.length === 0
    },
    // 表格点击一行事件-方法
    StepsListRowClick(row) {
      this.$refs.tableRefs.clearSelection()
      if (this.checkList.length === 0) {
        this.$refs.tableRefs.toggleRowSelection(row)
        this.checkList = row
        this.formProve = this.checkList
	        return
	    }
      this.$refs.tableRefs.toggleRowSelection(row)
    },
    // 新增
    onAdd() {
      this.isEdit = false
      this.dialogVisible = true
      // if (this.showdaliog) {
      // } else {
        // this.$message.warning('新增时不能勾选')
      // }
    },
    // 上传文件

    // 编辑
    onUpdate() {
      this.isEdit = true
      if (!this.showdaliog) {
        this.dialogVisible = true
      } else {
        this.$message.warning('请勾选需要编辑的数据')
      }
    },
    // 删除
    onDelete() {

    },
    sort() { // 排序
      this.dialogVisible2 = true
    },
    onReturn() { // 导出
      this.$message.warning('导出Excel成功')
      // this.dialogVisible5 = true
    },
    onCopy() { // 打印
    },
    handleSizeChange(){},
    handleCurrentChange(){},
  }
}
</script>

<style lang="scss" scoped>
.container {
  margin:20px;
  .btn{
    margin-bottom:20px;
  }
}
  // 弹窗固定样式
  .dialog-style {
    /deep/.el-dialog__header {
      background: rgba(204, 204, 204, 0.18);
      padding: 10px;
    }
    /deep/.el-dialog__title {
      font-weight: 500;
      font-size: 15px
    }
    /deep/.el-dialog__headerbtn {
      top: 13px;
    }
    /deep/.el-tree--highlight-current .el-tree-node.is-current>.el-tree-node__content {
      background: rgba(204, 204, 204, 0.18);
    }
    /deep/.el-dialog__footer {
      background: rgba(204, 204, 204, 0.18);
      padding-right: 15px;
      padding: 11px;
    }
    /deep/.el-button+.el-button {
      margin-left: 6px;
    }
    /deep/.el-dialog__body {
      padding: 10px 10px;
    }
  }
  //导出
  .dialog-return {
    position: relative;
    height: 160px;
    margin-left: 15%;
    width: 65%;
    border: 1px solid #CAD9E7;
    box-shadow: 0 0 3px 1px #EBF0F5;
    .return {
      position: absolute;
      top: -9px;
      left: 18px;
    }
    .return-form {
      width: 40px;
      margin-left: 20%;
      margin-top: 20px
    }
  }

//   编辑
.dialog-basic {
  .form-basic{
    margin-bottom:15px;
    line-height:30px;
    border-bottom: 1px solid #ccc
  }
  .form-supplement{
    margin-bottom:15px;
    line-height:30px;
    border-bottom: 1px solid #ccc
  }
  /deep/.el-select {
    width: 163px;
  }
}
.page {
  float: right;
}
//排序
  .dialog-sort {
    .btn-sort{
      margin-bottom: 10px;
      width: 100%;
    }
    .table-sort {
      height: 300px;
      overflow: auto;
    }
    /deep/ .el-table::before{
      height: 0px !important;
    }
  }
/deep/ .el-table .el-table__header-wrapper th{
  height:31px;
}
</style>

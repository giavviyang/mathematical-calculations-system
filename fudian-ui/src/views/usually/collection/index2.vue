<template>
  <div class="app-collection">
    <el-tabs id="elTabs" tabPosition="left" @tab-click="tabClick">
      <el-tab-pane label="待收集">
        <div class="wait">
          <!-- 待收集-搜索框 -->
          <div>
            <el-form :inline="true" ref="formSearchWait" label-width="85px" class="form-searchWait" :model="formSearchWait">
              <el-form-item label="收发文件号" >
                <el-input v-model="formSearchWait.fileNo" placeholder="请输入内容" size="mini"></el-input>
              </el-form-item>
              <el-form-item label="发文日期起">
                <!-- <el-date-picker
                  v-model="formSearchWait.num"
                  type="datetimerange"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker> -->
                <el-date-picker v-model="formSearchWait.postDate" placeholder="请选择" size="mini" style="width:163px" value-format="yyyy-MM-dd"></el-date-picker>
              </el-form-item>
               <el-form-item label="发文日期至">
                <el-date-picker v-model="formSearchWait.endTime" placeholder="请选择" size="mini" style="width:163px" value-format="yyyy-MM-dd"></el-date-picker>
              </el-form-item>

              <el-form-item >
                <el-button type="primary" size="mini" icon="el-icon-search" @click="depementWait()">搜索</el-button>
              <el-button size="mini" icon="el-icon-refresh" @click="clear()">重置</el-button>
              </el-form-item>
            </el-form>
          </div>
          <!-- 待收集-功能按钮 -->
          <div class="div-btn" >
            <el-button type="primary" size="mini" icon="el-icon-plus" @click="onAddWait">新增</el-button>
            <el-button type="primary" size="mini" icon="el-icon-edit" v-model="showdaliog" @click="onUpdateWait">编辑</el-button>
            <el-button type="primary" size="mini" icon="el-icon-tickets">打印</el-button>
            <el-button type="primary" size="mini" icon="el-icon-upload2" >导出</el-button>
            <!-- <div class="btn-right">
              <el-button icon="el-icon-search" size="mini" type="primary" circle @click="showSearchBox = !showSearchBox"></el-button>
              <el-dropdown>
                <el-button icon="el-icon-menu" size="mini" circle />
                <el-dropdown-menu style="padding: 10px">
                </el-dropdown-menu>
              </el-dropdown>
            </div> -->
        </div>
        <!-- 待搜集-表格 -->
        <div class="table-wait">
          <el-table
            ref="tableRefsWait"
            row-key="id"
            :data="tableDataWait"
            height="380px"
            size="mini"
            tooltip-effect="light"
            border
            :header-cell-style="headClass"
            :stripe="true"
            @row-click="StepsListRowClickWait"
            @selection-change="selectChecked"
           >
            <el-table-column type="selection" :resizable="false" align="center"/>
            <el-table-column type="index" label="序号" width="50px" align="center" :resizable="false" />
            <el-table-column label="发文日期" prop="postDate" :resizable="false" align="center" :formatter="formatTime"/>
            <el-table-column label="文件号" prop="fileNo" :resizable="false" align="center" />
            <el-table-column label="收集日期" prop="collectDate" :resizable="false" align="center" :formatter="formatTime"/>
            <el-table-column label="移交部门" prop="handDept" :resizable="false" align="center" />
            <el-table-column label="经办人" prop="agent" :resizable="false" align="center" />
            <el-table-column label="备注" prop="marks" :resizable="false" align="center" />
            <el-table-column label="原文链接" fixed="originalSc" :resizable="false" align="center">
              <template slot-scope="">
                  <el-link href="https://element.eleme.io" target="_blank" type="success">链接跳转</el-link>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
          <el-pagination
            class="page"
            :page-sizes="[20, 50, 100, 200]"
            :page-size="20"
            layout="total, sizes, prev, pager, next, jumper"
            :total="formSearchWait.total"
            @size-change="handleSizeChange1"
            @current-change="handleCurrentChange1"
          />
        </div>
      </el-tab-pane>
      <el-tab-pane label="已收集">
        <div class="already">
            <!-- 已收集-搜索框 -->
          <div>
            <el-form :inline="true" ref="formSearchWait" label-width="85px" class="form-searchAlready" :model="formSearchWait">
              <el-form-item label="收发文件号" >
                <el-input v-model="formSearchWait.fileNo" placeholder="请输入内容" size="mini"></el-input>
              </el-form-item>
              <el-form-item label="发文日期起">
                <!-- <el-date-picker
                  v-model="formSearchWait.num"
                  type="datetimerange"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker> -->
                <el-date-picker v-model="formSearchWait.postDate" placeholder="请选择" size="mini" style="width:163px" value-format="yyyy-MM-dd"></el-date-picker>
              </el-form-item>
               <el-form-item label="发文日期至">
                <el-date-picker v-model="formSearchWait.endTime" placeholder="请选择" size="mini" style="width:163px" value-format="yyyy-MM-dd"></el-date-picker>
              </el-form-item>
              <el-form-item >
                <el-button type="primary" size="mini" icon="el-icon-search" @click="depementAlready()">搜索</el-button>
              <el-button size="mini" icon="el-icon-refresh" @click="clear()">重置</el-button>
              </el-form-item>
            </el-form>
          </div>
          <!-- 已收集-功能按钮 -->
          <div class="div-btn" >
            <el-button type="primary" size="mini" icon="el-icon-edit" v-model="showdaliogAlready" @click="onUpdateAlready">编辑</el-button>
            <el-button type="primary" size="mini" icon="el-icon-tickets">打印</el-button>
            <el-button type="primary" size="mini" icon="el-icon-upload2" >导出</el-button>
            <!-- <div class="btn-right">
              <el-button icon="el-icon-search" size="mini" type="primary" circle></el-button>
              <el-dropdown>
                <el-button icon="el-icon-menu" size="mini" circle />
                <el-dropdown-menu style="padding: 10px">
                </el-dropdown-menu>
              </el-dropdown>
            </div> -->
        </div>
        <!-- 已收集-表格 -->
        <div class="table-already">
          <el-table
            ref="tableRefsAlready"
            row-key="id"
            :data="tableDataAlready"
            height="380px"
            size="mini"
            tooltip-effect="light"
            border
            :header-cell-style="headClass"
            :stripe="true"
            @row-click="StepsListRowClickAlready"
            @selection-change="selectCheckedAlready"
           >
            <el-table-column type="selection" :resizable="false" />
            <el-table-column type="index" label="序号" width="50px" align="center" :resizable="false" />
            <el-table-column label="发文日期" prop="postDate" :resizable="false" align="center" :formatter="formatTime"/>
            <el-table-column label="文件号" prop="fileNo" :resizable="false" align="center" />
            <el-table-column label="收集日期" prop="collectDate" :resizable="false" align="center" :formatter="formatTime"/>
            <el-table-column label="移交部门" prop="handDept" :resizable="false" align="center" />
            <el-table-column label="经办人" prop="agent" :resizable="false" align="center" />
            <el-table-column label="备注" prop="marks" :resizable="false" align="center" />
            <el-table-column label="原文链接" fixed="originalSc" :resizable="false" align="center">
              <template slot-scope="">
                <el-link href="https://element.eleme.io" target="_blank" type="success">链接跳转</el-link>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!-- 分页 -->
          <el-pagination
            class="page"
            :page-sizes="[20, 50, 100, 200]"
            :page-size="20"
            layout="total, sizes, prev, pager, next, jumper"
            :total="formSearchWait.total"
            @size-change="handleSizeChange1"
            @current-change="handleCurrentChange1"
          />
        </div>
      </el-tab-pane>
    </el-tabs>
    <!-- 待收集-新增 -->
    <el-dialog :title="isEdit?'编辑':'新增'" :close-on-click-modal="false" :visible.sync="dialogVisible1" :destroy-on-close="true" class=" dialog-style" width="45%">
      <div class="dialog-WaitEdit">
      <el-form ref="formhWaitEdit" :model="formhWaitEdit" label-width="100px"  :inline="true">
        <el-form-item label="发文日期">
          <el-date-picker v-model="formhWaitEdit.postDate" style="width:163px" type="date" placeholder="请选择" size="mini" value-format="yyyy-MM-dd"/>
        </el-form-item>
        <el-form-item label="文件号">
          <el-input v-model="formhWaitEdit.fileNo" style="width:163px" size="mini" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="收集日期">
          <el-date-picker v-model="formhWaitEdit.collectDate" style="width:163px" type="date" placeholder="请选择" size="mini" value-format="yyyy-MM-dd"/>
        </el-form-item>
        <el-form-item label="移交部门">
          <el-input v-model="formhWaitEdit.handDept" style="width:163px" size="mini" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="经办人">
          <el-input v-model="formhWaitEdit.agent" style="width:163px" size="mini" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="原文链接">
          <el-input v-model="formhWaitEdit.originalSc" style="width:163px" size="mini" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="formhWaitEdit.marks" style="width:163px" type="textarea" size="mini" placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible1 = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveWaitEdit">保 存</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
  import { getList, addArchiveColl } from "@/api/usually/collect"
export default {
  data() {
    return {
      tableHeight:'73%',
      searchRow:'tableRefsWait',
      //复选框
      checkbox: [],
      checkboxDatas: [],
      dialogVisible1:false,  //待收集-编辑弹出框
      // showSearchBox:true,
      checkListWait:[],
      checkListAlready:[],
      isEdit:false,
      showdaliog:'true',
      showdaliogAlready:'true',
      formSearchWait:{     //待收集-页面搜索框
        fileNo:'',
        type:'1',
        postDate:'',
        endTime:'',
        pageSize:20,
        pageNum:1,
        total:0
      },
      formhWaitEdit:{       //待收集-编辑表单
        postDate:'',
        fileNo:'',
        collectDate:'',
        handDept:'',
        agent:'',
        marks:'',
        originalSc:'',
        type:'1',
      },
      tableDataWait:[],      //待收集-页面表格数据
      // formSearchAlready:{    //已收集-搜索表单
      //   fileNo:'',
      //   type:'2',
      //   postDate:'',
      //   endTime:'',
      //   pageSize:20,
      //   pageNum:1,
      //   total:0
      // },
      tableDataAlready:[],     //已收集-页面表格数据
    }
  },
  created() {
    this.getGridList();
  },
  mounted() {
    this.$nextTick(()=>{
      this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
      this.tableHeight = (document.body.clientHeight - (84 + 70 + this.searchRowHeight)) + "px"
    })
    window.onresize = () => {
      return (() => {
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 70 + this.searchRowHeight)) + "px"
      })();
    };
  },
  watch:{
    tableDataWait:{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          _this.$refs.tableRefsWait.doLayout();
        })
      },
      deep:true,
      // immediate:true
    },
    tableDataAlready:{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          _this.$refs.tableRefsAlready.doLayout();
        })
      },
      deep:true,
      // immediate:true
    },
  },
  methods:{
    formatTime(row,col){
      if(row[col.property]!=null&&row[col.property]!=''){
        var d = new Date(row[col.property]),
          month = '' + (d.getMonth() + 1),
          day = '' + d.getDate(),
          year = d.getFullYear();
        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;
        return [year, month, day].join('-');
      }
    },
    //tab切换事件
    tabClick(tab, event) {
      if (tab.label === '待收集') {
          this.formSearchWait.type="1",
          this.formSearchWait.fileNo="",
          this.formSearchWait.postDate=""
          this.formSearchWait.endTime=""
          this.formSearchWait.pageSize=this.formSearchWait.pageSize
          // this.formSearchWait.pageNum=this.formSearchWait.pageNum
          // this.formSearchWait.total=this.formSearchWait.total,
          this.getGridList();
      } else if (tab.label === '已收集') {
          this.formSearchWait.type = "2",
          this.formSearchWait.fileNo = "",
          this.formSearchWait.postDate = ""
          this.formSearchWait.endTime = ""
          this.formSearchWait.pageSize = this.formSearchWait.pageSize
          // this.formSearchWait.pageNum = this.formSearchWait.pageNum
          // this.formSearchWait.total=this.formSearchWait.total,
          this.getGridList();

      }
    },

    getGridList(){
      this.loading = true;
      getList(this.formSearchWait).then(response =>{
        if (this.formSearchWait.type==="1"){
          if (response.rows!=null){
            this.tableDataWait = response.rows;
            this.formSearchWait.total = response.total;
            this.loading = false;
          }else {
            this.tableDataWait = []
          }
        }else {
          if (response.rows!=null){
            this.tableDataAlready = response.rows;
            this.formSearchWait.total = response.total;
            this.loading = false;
          }else {
            this.tableDataAlready = []
          }
        }
      })
    },

      //搜索区域
      clear(){
        this.formSearchWait.pageNum=1,
        this.formSearchWait.pageSize=this.formSearchWait.pageSize
        this.formSearchWait.fileNo = "",
        this.formSearchWait.postDate = ""
        this.formSearchWait.endTime = ""
        // this.formSearchWait.total = 0,
        this.getGridList();
      },

    //新增保存
    saveWaitEdit(){
      addArchiveColl(this.formhWaitEdit)
        .then(response => {
          if(response.code==200){
            this.$message({
              message: '新增成功',
              type: 'success'
            });
            this.dialogVisible1=false
            this.getGridList()
          }else{
            this.$message({
              message: '新增失败',
              type: 'error'
            });
            this.dialogVisible1=false
            this.getGridList()
          }
        })
    },
      headClass() {                   // 公用表格表头-背景颜色样式
        return "background:rgba(204, 204, 204, 0.18)";
      },

      selectChecked(data) {           //待收集-勾选框变化事件-方法
        this.checkboxDatas = data
        this.checkbox = data.map(item => item.id)
      },
       StepsListRowClickWait(row){    //待收集-点击行事件-方法
        this.$refs.tableRefsWait.clearSelection()
        if (this.checkListWait.length === 0) {
          this.$refs.tableRefsWait.toggleRowSelection(row)
          this.checkListWait = row
          //this.formhWaitEdit=this.checkListWait
	        return;
	      }
        this.$refs.tableRefsWait.toggleRowSelection(row)
      },
      depementWait(){                 //待收集-搜索
        this.formSearchWait.total=0;
        this.getGridList();
      },
      onAddWait(){
        this.isEdit=false                //待收集-新增
        console.log(this.formhWaitEdit)
        this.dialogVisible1 = true;
        /*this.formhWaitEdit.postDate=""
        this.formhWaitEdit.fileNo=""
        this.formhWaitEdit.collectDate=""
        this.formhWaitEdit.handDept=""
        this.formhWaitEdit.agent=""
        this.formhWaitEdit.marks=""
        this.formhWaitEdit.originalSc=""*/
        // if (this.showdaliog) {
        //   this.dialogVisible1 = true;
        // } else {
        //   this.$message.warning('新增时不能勾选')
        //
        // }
      },
      onUpdateWait(){
        this.isEdit = true               //待收集-编辑
        if(this.checkboxDatas==null || this.checkboxDatas.length==0){
          this.$message({
            type: 'warning',
            message: '请勾选需要编辑的证明'
          })
          return
        }
        if(this.checkboxDatas.length>1){
          this.$message({
            type: 'warning',
            message: '请勾选一条需要编辑的证明'
          })
          return
        }
        this.formhWaitEdit=JSON.parse(JSON.stringify(this.checkboxDatas[0]))
        this.dialogVisible1=true
    },

      selectCheckedAlready(data) {           //已收集-勾选框变化事件-方法
        this.checkboxDatas = data
        this.checkbox = data.map(item => item.id)
      },
       StepsListRowClickAlready(row){    //已收集-点击行事件-方法
        this.$refs.tableRefsAlready.clearSelection()
        if (this.checkListAlready.length === 0) {
        this.$refs.tableRefsAlready.toggleRowSelection(row)
        this.checkListAlready = row
        this.formhWaitEdit=this.checkListAlready
	        return;
	      }
        this.$refs.tableRefsAlready.toggleRowSelection(row)

      },
      onUpdateAlready(){
        this.isEdit = true               //已收集-编辑
        if(this.checkboxDatas==null || this.checkboxDatas.length==0){
          this.$message({
            type: 'warning',
            message: '请勾选需要编辑的证明'
          })
          return
        }
        if(this.checkboxDatas.length>1){
          this.$message({
            type: 'warning',
            message: '请勾选一条需要编辑的证明'
          })
          return
        }
        this.formhWaitEdit=this.checkboxDatas[0]
        this.dialogVisible1 = true;
      },
      depementAlready(){                  //已收集-搜索
        this.formSearchWait.total=0;
        this.getGridList();
      },
    handleCurrentChange1(val){
      this.formSearchWait.pageSize=this.formSearchWait.pageSize,
      this.formSearchWait.pageNum=val,
      this.getGridList()
    },
    handleSizeChange1(val){
      this.formSearchWait.pageSize=val,
      this.formSearchWait.pageNum=this.formSearchWait.pageNum,
      this.getGridList()
    },
    handleCurrentChange(){},
    handleSizeChange(){},

  }
}
</script>

<style lang="scss" scoped>
.app-collection{
  .wait {
    .form-searchWait {
      width:100%;
      background-color:rgba(204, 204, 204, 0.18);
      padding: 10px;
      margin-top: 15px;
      margin-left: 5px;
      display:flex;
    }
    .div-btn{
      position: relative;
      padding: 10px;
      .btn-right {
        position: absolute;
        right: 0px;
        top: 0px;
        margin-right: 80px;
        margin-top: 10px;
      }
    }
    /deep/ .el-form-item{
    margin-bottom: 0 !important;
    }
    .page{
      float: right;
    }
  }
  .already{
    .form-searchAlready {
      width:100%;
      background-color:rgba(204, 204, 204, 0.18);
      padding: 10px;
      margin-top: 15px;
      margin-left: 5px;
      display:flex;
    }
    .div-btn{
      position: relative;
      padding: 10px;
      .btn-right {
        position: absolute;
        right: 0px;
        top: 0px;
        margin-right: 80px;
        margin-top: 10px;
      }
    }
    /deep/ .el-form-item{
    margin-bottom: 0 !important;
    }

    .page{
      float: right;
    }
  }
}
    // 公用弹框样式
  .dialog-style {
    /deep/.el-dialog__body {
      padding: 10px;
      padding-left: 20px;
    }
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

    /deep/.el-dialog__footer {
      background: rgba(204, 204, 204, 0.18);
      padding-right: 15px;
      padding: 11px;
    }
    /deep/.el-button+.el-button {
      margin-left: 6px;
    }

  }

  //待收集-编辑
  .dialog-WaitEdit{
      // padding-left: 33px;
      /deep/.el-form--inline /deep/.el-form-item {
        margin-bottom: 5px;
      }
  }
</style>

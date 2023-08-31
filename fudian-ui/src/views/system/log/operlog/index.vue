 <template>
  <div class="app-find">
    <el-row style="height: 100%">
      <el-col :span="24" style="padding: 10px;height: 100%">
        <el-row  style="margin:0 0 10px 0;padding-left: 0px" ref="searchRow">
          <el-form
            v-hasPermi="['log:operlog:search']"
            v-if="showSearchBox"
            ref="queryForm2"
            class="search-form"
            :model="queryForm2"
            :inline="true"
            label-width="68px"
            style="background:rgba(204, 204, 204, 0.18);padding: 10px 0;margin-right: 20px;margin-bottom: 10px;"
          >
            <el-form-item label="操作人员" style="margin-left: 20px" prop="operName">
              <el-input @keyup.enter.native="onEnterSearch" v-model="queryForm2.operName" placeholder="请输入操作人员名称" clearable size="mini" style="width: 150px" />
            </el-form-item>
            <el-form-item label="模块标题" style="margin-left: 20px" prop="title">
              <el-input @keyup.enter.native="onEnterSearch" v-model="queryForm2.title" placeholder="请输入模块标题" clearable size="mini" style="width: 150px" />
            </el-form-item>
            <el-form-item style="margin-left: 20px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
               <!-- <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button> -->
            </el-form-item>
          </el-form>
          <el-col :span="1.5"><el-button type="primary" icon="el-icon-download" v-hasPermi="['log:operlog:export']" size="mini" @click="handleExportOperLog()">导出</el-button></el-col>

<!--          <el-col :span="1.5"><el-button type="primary" icon="el-icon-search" size="mini" @click="operator">操作人员</el-button></el-col>-->

<!--          搜索区域的展示与隐藏-->
<!--          <el-dropdown style="float: right; margin-right: 30px;">-->
<!--            <el-button icon="el-icon-menu" size="mini" circle />-->
<!--            <el-dropdown-menu style="padding: 10px">-->
<!--              <el-checkbox-group v-for="(item, index) in col" v-model="col[index].isCol" size="mini">-->
<!--                <el-checkbox>{{ item.label }}</el-checkbox>-->
<!--              </el-checkbox-group>-->
<!--            </el-dropdown-menu>-->
<!--          </el-dropdown>-->
<!--          <el-col :span="1.5" style="float: right;">-->
<!--            <el-button type="primary" icon="el-icon-search" size="mini" circle @click="function(){showSearchBox = !showSearchBox;tableHeight = showSearchBox?'73%':'85%'}" />-->
<!--          </el-col>-->
        </el-row>

        <el-table
          ref="table"
          v-loading="loading"
          :height="tableHeight"
          size="mini"
          :data="tableData"
          border row-key="id"
          align="left"
          stripe
          tooltip-effect="light"
          :header-cell-style="headClass"
          highlight-current-row
          @current-change="handleCurrentChange"
          @selection-change="handleSelectionChange">
<!--          <el-table-column type="selection" width="40" />-->
          <el-table-column
            label="序号"
            type="index"
            align="center"
            :index="customIndex"
            show-overflow-tooltip
            width="50">
          </el-table-column>
          <el-table-column align="center"   v-for="(item, index) in col"  v-if="col[index].isCol" :key="index"  :formatter="item.format" :prop="col[index].prop"  :label="item.label" show-overflow-tooltip/>
        </el-table>
        <div class="block">
          <el-pagination
            :page-sizes="[20, 50, 100, 200]"
            :page-size="queryForm.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="queryForm.total"
            @size-change="pageSize"
            @current-change="pageCurrent"
            :current-page="queryForm.pageNum"
          />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {queryAllOperLog,queryAllOperLogExcel}  from "@/api/log/operlog";
  import {downLoadPost} from "@/downLoad/filedownload";

  export default {
    // name: "操作日志",
    data(){
      return {
        queryForm2:{
          operName: '',//操作人员
          title: ''//模块标题
        },
        tableHeight:'73%',
        loading:true,
        //grid数据
        tableData: [],
        //头部搜索框展示与隐藏
        showSearchBox: true,
        //搜索框条件
        queryForm: {
          pageNum:1,
          total:0,
          pageSize:20,

        },
        col:[
          // { label: '日志主键', prop: 'operId', isCol: true },
          { label: '日志主键', prop: 'operId', format:function(row, column, cellValue, index){return cellValue},isCol: false},
          { label: '模块标题', prop: 'title',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
          { label: '业务类型', prop: 'businessType',format:function(row, column, cellValue, index){
            if (cellValue==0){
              return "查询"
            }else if (cellValue==1) {
              return "新增"
            }else if (cellValue==2){
              return "修改"
            }else if (cellValue==3){
              return "删除"
            }else {
              return cellValue
            }
            }, isCol: true },
          { label: '方法名称', prop: 'method',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
          { label: '请求方式', prop: 'requestMethod',format:function(row, column, cellValue, index){
            if(cellValue == 0){
              return "GET";
            }
              if(cellValue == 1){
                return "POST"
              }
              if(cellValue == 2){
                return "PUT"
              }
              if(cellValue == 3){
                return "DELETE"
              }
            }, isCol: true },
          // { label: '操作类别', prop: 'operatorType',format:function(row, column, cellValue, index){
          //     if (cellValue==0){
          //       return "PC端用户"
          //     }else if (cellValue==1){
          //       return "后台用户"
          //     }else if (cellValue==2){
          //       return "手机端用户"
          //     }else {
          //       return cellValue
          //     }
          //   }, isCol: true },
          { label: '操作人员', prop: 'operName',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
          // { label: '部门名称', prop: 'deptName',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
          { label: '请求URL', prop: 'operUrl',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
          { label: '主机地址', prop: 'operIp',format:function(row, column, cellValue, index){return cellValue}, isCol: true },

          // { label: '请求参数', prop: 'operParam',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
          // { label: '返回参数', prop: 'jsonResult',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
          { label: '操作状态', prop: 'status',format:function(row, column, cellValue, index){
              if (cellValue==0){
                return "正常"
              }else if (cellValue==1){
                return "异常"
              }else {
                return cellValue
              }
            }, isCol: true },

          { label: '操作时间', prop: 'operTime', isCol: true },
        ],
        currentRow:[],

        searchRowHeight:'',
        searchRow:'searchRow',
      }
    },

    watch:{
      tableData:{
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(()=>{
            _this.$refs.table.doLayout()
          })
        },
        deep:true,
        immediate:true
      },
    },

    mounted() {

      this.$nextTick(()=>{
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 65 + this.searchRowHeight)) + "px"
      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (84 + 65 + this.searchRowHeight)) + "px"
        })();
      };
      this.keyupSubmit();
    },
    created() {
      this.queryForm2 = JSON.parse(JSON.stringify(this.queryForm));
      this.getRoomList();
      // window.addEventListener('keyup', this.search)
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
      //自定义序号
      customIndex(index){
        return (this.queryForm.pageNum-1)*(this.queryForm.pageSize)+(index+1);
      },
      // 根据code转换成中文
      formatTime(row, col){
        if (col.property == 'operTime'){
          if (row[col.property] != null){
            var d = new Date(row[col.property])
            var month = '' + (d.getMonth() + 1)
            var day = '' + d.getDate()
            var year = d.getFullYear()
            if (month.length < 2) month = '0' + month
            if (day.length < 2) day = '0' + day
            return [year, month, day].join('-')
          }
        }
      },
      onEnterSearch() {
        this.search();
      },
      handleExportOperLog(){
        const _this = this;
        if (this.tableData.length <= 0) {
          this.$message({
            type: 'warning',
            message: '无任何数据需要导出'
          });
          return;
        }

        this.$confirm('是否导出搜索后的日志数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          // 取列头
          this.$store._modules.root.state.mask.lodding = true;
          let col = this.col;
          // let colTitle = {
          //   userName:"用户名称",
          //   a0101:"干部姓名",
          //   deptName:"部门名称",
          //   e01z111a:"材料名称",
          //   operatorType:"操作类别",
          //   operIp:"主机地址",
          //   operTime:"操作时间",
          // }
          // if (this.select.length == 0) {
          let params = {};
          for (let key in this.queryForm) {
            params[key] = this.queryForm[key]
          }
          params.pageSize = 99999999;
          let callback = function (response) {
            response.rows.map((item, index) => {
              let indexs = (index + 1).toString();
              if (index.toString().length < 3) {
                for (let i = 0; i < 3 - index.toString().length; i++) {
                  indexs = '0' + indexs;
                }
              }
              item.code = indexs
              // 处理类型
              item.businessType = _this.businessTypeHandler(item.businessType);
              item.requestMethod = _this.requestMethodHandler(item.requestMethod);
              item.operatorType = _this.operatorTypeHandler(item.operatorType);
              item.status = _this.statusHandler(item.status);
              item.operTime = _this.dateHandler(item.operTime);
              // console.log(item)
              // console.log(index)
            })
            if(response.rows.length==0){
              _this.$message.error("无数据,导出失败");
              return;
            }
            let data = {
              colTitle: {
                title:"模块标题",
                businessType:"业务类型",
                method:"方法名称",
                requestMethod:"请求方式",
                operatorType:"操作类别",
                operName:"操作人员",
                deptName:"部门名称",
                operUrl:"请求URL",
                operIp:"主机地址",
                operParam:"请求参数",
                jsonResult:"返回参数",
                status:"操作状态",
                operTime:"操作时间"
              },
              dataList: response.rows,
              tableTitle: "浏览日志表",
              sheetTitle: "浏览日志表",
              downLoadName: "浏览日志表"
            }
            downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data),_this)
          }
          queryAllOperLogExcel(params,callback);
          // } else {
          //   let data = {
          //     "colTitle": colTitle,
          //     "dataList": this.select,
          //     "tableTitle": "浏览日志表",
          //     "sheetTitle": "浏览日志表",
          //     "downLoadName": "浏览日志表"
          //   }
          //   downLoadPost("/export/excel/exportExcelDynamicCol", data)
          // }

        })

      },//导出
      handlePrintOperLog(){},//打印
      dateHandler(cellValue) {
        if (cellValue==null||""==cellValue){
          return cellValue;
        }
        var d = new Date(cellValue);
        var month = '' + (d.getMonth() + 1)
        var day = '' + d.getDate()
        var year = d.getFullYear()
        if (month.length < 2) month = '0' + month
        if (day.length < 2) day = '0' + day
        var hour=d.getHours();
        var minus=d.getMinutes()
        var seconds=d.getSeconds()
        return [year, month, day].join('-')+" "+hour+":"+minus+":"+seconds
      },
      statusHandler(cellValue) {
        if (cellValue==0){
          return "正常"
        }else if (cellValue==1){
          return "异常"
        }else {
          return cellValue
        }
      },
      operatorTypeHandler(cellValue) {
        if (cellValue==0){
          return "PC端用户"
        }else if (cellValue==1){
          return "后台用户"
        }else if (cellValue==2){
          return "手机端用户"
        }else {
          return cellValue
        }
      },
      businessTypeHandler(cellValue) {
        if (cellValue==0){
          return "查询"
        }else if (cellValue==1) {
          return "新增"
        }else if (cellValue==2){
          return "修改"
        }else if (cellValue==3){
          return "删除"
        }else {
          return cellValue
        }
      },
      requestMethodHandler(cellValue) {
        if(cellValue == 0){
          return "GET";
        }
        if(cellValue == 1){
          return "POST"
        }
        if(cellValue == 2){
          return "PUT"
        }
        if(cellValue == 3){
          return "DELETE"
        } else {
          return cellValue;
        }
      },
      search(val){
        // let param = {}
        // param.pageNum = '1'
        // param.pageSize = '20'
        this.queryForm.pageNum=1;
        this.queryForm.operName=this.queryForm2.operName
        this.queryForm.title=this.queryForm2.title
        this.getRoomList()
      },//条件搜索
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      /* grid  行点击事件*/
      handleCurrentChange(val) {
        this.currentRow=val;
      },
      /* 复选框事件 */
      handleSelectionChange(val) {
        this.select = val;
      },

      //初始化列表数据
      getRoomList(){
        this.loading = true;
        queryAllOperLog(this.queryForm).then(response =>{
          this.tableData = response.rows;
          const _this=this;
          response.rows.map((item,index)=>{
            let indexs = (index + 1).toString();
            if(index.toString().length<3){
              for(let i = 0;i<3-index.toString().length;i++){
                indexs = '0' + indexs;
              }
            }
            item.code = indexs
          })
          this.queryForm.total = response.total;
          this.loading = false;
        })
      },

      operator(){
        if(this.currentRow==null || this.currentRow.length==0){
          this.$message({
            type: 'warning',
            message: '请选中需要查看操作人员的信息'
          })
          return
        }
        const val=this.currentRow

        this.queryForm.operName=val.operName;
        queryAllOperLog(this.queryForm).then(response =>{
          this.tableData = response.rows;
          const _this=this;
          response.rows.map((item,index)=>{
            let indexs = (index + 1).toString();
            if(index.toString().length<3){
              for(let i = 0;i<3-index.toString().length;i++){
                indexs = '0' + indexs;
              }
            }
            item.code = indexs
          })
          this.queryForm.total = response.total;
          this.loading = false;
        })
        this.$refs.table.setCurrentRow();
      },
      pageCurrent(val){
        this.queryForm.pageNum=val
        this.getRoomList();
      },

      pageSize(val) {
        this.queryForm.pageSize = val;
        this.queryForm.pageNum = this.queryForm.pageNum;
        this.getRoomList();
      },
      reset() {
        this.queryForm.operName = "";
        this.queryForm.title = "";
        this.queryForm.pageNum=1
        this.getRoomList();
      },
    }
  }
</script>

<style lang="scss" scoped>
  .app-find {
    overflow: auto;
    height: calc(100vh - 86px);
  }
  .search-form{
    width:100%;
    background:rgba(204, 204, 204, 0.18);
    padding: 10px;
    display:flex;
    .form-width{
      width:22%;
      display:flex;
    }
    /deep/ .el-form-item{
      margin-bottom: 0 !important;
    }
    /deep/ .el-table__fixed-right-patch {
      background-color:#F8F8F9;
    }
  }

  .block {
    float: right;
    margin-top: 5px;
  }
</style>

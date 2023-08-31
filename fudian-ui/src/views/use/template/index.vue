<template>
  <div class="app-template">
    <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px);overflow: auto;">
      <div>
        <el-row :gutter="10" ref="searchRow" style="margin:0 ;">
          <el-form
            v-hasPermi="['use:template:search']"
            size="mini"
            class="search-form"
            ref="searchForm"
            :model="searchParams"
            :inline="true"
            style="background:rgba(204, 204, 204, 0.18);padding: 14px;"
          >
            <el-form-item label="模板名称" prop="templateName">
              <el-input size="mini"  @keyup.enter.native="onEnterSearch" clearable v-model="searchParams.templateName" placeholder="请输入模板名称" style="width:150px"/>
            </el-form-item>
            <el-form-item label="使用情况" prop="templateStatus">
              <el-select v-model="searchParams.templateStatus" clearable style="width: 150px" placeholder="请选择使用情况">
                <el-option v-for="item in conditionOptions" :key="item.conditions" :label="item.label" :value="item.conditions" size="mini"/>
              </el-select>
            </el-form-item>
            <el-form-item >
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button> -->
            </el-form-item>
          </el-form>
          <div class="btn" style="padding: 10px 0">
              <el-button type="primary" size="mini" icon="el-icon-plus" v-hasPermi="['use:template:add']" @click="addTemp">新增</el-button>
              <el-button type="primary" size="mini" icon="el-icon-edit"  v-hasPermi="['use:template:edit']"  @click="editTemp">编辑</el-button>
              <el-button type="primary" size="mini" icon="el-icon-delete" v-hasPermi="['use:template:delete']" @click="deleTemp">删除</el-button>
              <el-button type="primary" size="mini" icon="el-icon-download" v-hasPermi="['use:template:import']" @click="exportExcel">导出</el-button>
          </div>
          <!-- <el-col :span="1.5">
            <el-button type="primary" size="mini" icon="el-icon-download"  @click="downWordFile">下载word文档</el-button>
          </el-col> -->
        </el-row>
      </div>
      <el-table
        :default-sort="{ prop: 'date', order: 'descending' }"
        size="mini"
        :data="tempData"
        ref="tempRef"
        border
        stripe
        empty-text=" "
        style="width: 100%"
        :header-cell-style="headClass"
        :height="tableHeight"
        @selection-change="handleSelectionChange"
        @row-click="StepsListRowClick"
        >
        <el-table-column :resizable="false" type="selection" width="40" />
        <el-table-column align="center" :resizable="false" label="序号" type="index" min-width="60" :index="customIndex"/>
        <el-table-column
          align="center"
          v-for="(item, index) in tempColumn"
          :v-if="item.isCol"
          :key="index"
          :resizable="false"
          :label="item.label"
          :prop="item.prop"
          :width="item.width"
          :formatter="item.formatter"
        />
        <el-table-column :resizable="false" align="center" fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="tempViewDetals(scope.row)" v-hasPermi="['use:template:details']">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination
          :page-sizes="[20, 50, 100, 200]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          :current-page="pageNum"
          @size-change="pageSizes"
          @current-change="pageCurrent"
        />
      </div>
    </el-col>
    <!--  模板详情  -->
    <el-dialog
      :close-on-click-modal="false"
      title="常用模板详情"
      width="90%"
      class="dialog-style dialog-basic"
      :visible.sync="tempDetailsVisible"
      :destroy-on-close="true"
      >
       <div>
        <div style="margin: 10px 30px">
          <span slot="tip" >模板名称：</span>
          <span slot="tip" >{{templateName}}</span>
          <!--<el-button style="margin-left: 5px" type="text" size="mini" @click="reguBrowse" >查看</el-button>-->
        </div>
        <div style="margin: 10px 30px">
          <span slot="tip" >使用情况：</span>
          <span slot="tip" >{{templateStatus=="0"?"正在使用":"已停用"}}</span>
        </div>
        <div style="margin: 10px 30px">
          <span slot="tip" >下载文件：</span>
          <span slot="tip" style="color:#1890ff;cursor: pointer;text-decoration:underline"  @click="downWord">下载word</span>
          <span style="padding:0 0 0 10px;color:#1890ff;cursor: pointer;text-decoration:underline" @click="downPdf" slot="tip" >下载pdf</span>
        </div>
      </div>
     <div style="height: calc(100vh - 300px);">
        <iframe :src="pdfSrc" height="100%" width="100%"></iframe>
      </div>
      <span slot="footer" >
        <el-button size="mini" @click="tempDetailsVisible = false">关 闭</el-button>
      </span>
    </el-dialog>

    <!--  新增/编辑模板  -->
    <el-dialog :close-on-click-modal="false" title="模板信息" width="70%" class="dialog-style dialog-basic" :visible.sync="tempAddVisible" :destroy-on-close="true">
       <el-row >
        <el-col :span="24">
          <el-form ref="tempForm" :rules="rules" :model="tempForm" label-width="120px" style="margin: 10px 0px 0px 0px">
            <el-col :span="24">
              <el-form-item prop="templateName" label="模板名称">
                <el-input size="mini" v-model="tempForm.templateName" placeholder="请输入模板名称" style="width:100%"/>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item prop="templateStatus" label="使用情况">
                <el-radio-group v-model="tempForm.templateStatus">
                  <el-radio label="0">正在使用</el-radio>
                  <el-radio label="1">已停用</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item  label="word文件" prop='wordName'>
                <input  type="file"  single accept=".doc,.docx"  name="upload_Wfile"  style="display: none;"
                  @change="wordFileChange" ref="wordFile"/>
                <el-button size="mini"  onclick="upload_Wfile.click(); " style="width:75px">选择文件</el-button>
                <el-input size="mini"  v-model="tempForm.wordName"  onclick="upload_Wfile.click();"  :readonly="true" class="fileTextInput"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="pdf文件" >
                <input  type="file"  accept="application/pdf"  name="upload_Pfile"  multiple="multiple" style="display: none;"  ref="pdfFile"
                 @change="pdfFileChange"/>
                <el-button size="mini"  onclick="upload_Pfile.click(); " style="width:75px">选择文件</el-button>
                <el-input size="mini"  v-model="tempForm.pdfName"  onclick="upload_Pfile.click();"  :readonly="true" class="fileTextInput"/>
              </el-form-item>
            </el-col>
          </el-form>
        </el-col>
       </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="tempAddVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="onCommitEdit('tempForm')">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {getFileName, insertTemplate,getWordFileByTemplateIds,updateTemplate ,delTemplatesByIds,getTemplateList,getPdfFileByTemplateId } from '@/api/use/template'
import { getBaseURL } from 'xe-utils/methods'
  import { downLoad,downLoadPost } from '@/downLoad/filedownload'
  const datalist = []
  const baseUrl = process.env.VUE_APP_BASE_API;
  export default {
    watch: {
      tempData:{
        handler(newName, oldName) {
          this.$nextTick(()=>{
            this.$refs.tempRef.doLayout();
          })
        },
        deep:true,
        // immediate:true
      },
    },
    data() {
      return {
        templateName:"",
        templateStatus:"",
        templateId:"",
        pdfSrc:"",
        addOrEdit:'add',
        /* 总页数 */
        total: 0,
        pageNum: 1,
        pageSize: 20,
        pageParams:{
          pageSize:20,
          pageNum:1
        },
        /* 刷新遮罩 */
        loading: true,
        // 高度
        searchRow:'searchRow',
        searchRowHeight :'',
        tableHeight:'73%',
        // 模板条件区
        searchParams: {},
        tempData1: [], // 备份数据
        conditionOptions: [
          {
            conditions: '0',
            label: '正在使用'
          },
          {
            conditions: '1',
            label: '已停用'
          },
        ],
        conditions: '',
        // 模板
        tempColumn: [
          { label: '模板名称', isCol: true, prop: 'templateName' },
          { label: '使用情况', isCol: true, prop: 'templateStatus' ,formatter:function(row, column, cellValue){
            return cellValue==null?"":cellValue==""?"":cellValue=="0"?"正在使用":"已停用"
          }},
        ],
        rules:{
          templateName:[
            { required: true, message: '请输入模板名称',trigger: 'blur'  },
            { min: 1, max: 20, message: '输入内容不得超过20个字符',trigger: 'blur'  }
          ],
          templateStatus:[
            { required: true, message: '请选择使用情况',trigger: 'blur'  },
          ],

        },
        tempData: [],
        tempDetailsVisible: false,
        url: '',
        // 新增/编辑模板
        tempAddVisible: false,
        tempForm: {templateStatus:'0',wordName:"",pdfName:"",templateName:""},
        // 复选框
        checkbox: [],
        checkboxDatas: [],
      }
    },
    mounted() {
      this.archivesaFunction()
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
    },
    // 字典表获取
    created() {
      this.copyData()
    },
    methods: {
      //自定义序号
      customIndex(index){
        return (this.pageNum-1)*(this.pageSize)+(index+1);
      },
      onEnterSearch() {
        this.handleQuery()
      },
      exportExcel(){

        if (this.tempData.length <= 0) {
          this.$message({
            type: 'warning',
            message: '无任何数据需要导出'
          });
          return;
        }

        if(this.checkboxDatas.length == 0){
          this.$confirm('是否导出搜索后的模板数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            this.pageParams.pageNum=1;
            this.pageParams.pageSize=999999;
            getTemplateList(this.pageParams).then(res=>{
              if(res.rows.length==0){
                this.$message.error("无数据,导出失败");
                return;
              }
              let row = {
                row: res.rows,
                status: 19
              }
              downLoadPost("/export/excel/exportClick", row)
            })
          })

        }else{
          this.$confirm('是否导出选中的模板数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let row = {
              row: this.checkboxDatas,
              status: 19
            }
            downLoadPost("/export/excel/exportClick", row)
          })

        }



        // let s = JSON.stringify(this.pageParams);
        // console.log(s);
        // s = encodeURI(s);
        // downLoad("/export/excel/export?status=11&exportData=" + s)
      },

      wordFileChange(val){
        var fileName = val.target.value;
        if(val.target.value==''){
          this.tempForm.wordName="未选择任何文件"
        }else{
          var index1=fileName.lastIndexOf(".");
          var index2=fileName.length;
          var suffix=fileName.substring(index1, index2);//后缀名
          if (suffix == '.doc' || suffix == '.docx') {
            this.tempForm.wordName=fileName
          } else {
            this.$refs.wordFile.value = "";
            this.tempForm.wordName="未选择任何文件";
            this.$message({
              type: 'warning',
              message: '只能上传word文档'
            })
          }
        }
      },
      pdfFileChange(val){
        var fileName = val.target.value;
        if(val.target.value==''){
          this.tempForm.pdfName="未选择任何文件";
        }else{
          var index1=fileName.lastIndexOf(".");
          var index2=fileName.length;
          var suffix=fileName.substring(index1, index2);//后缀名
          if (suffix == '.pdf') {
            this.tempForm.pdfName=fileName
          } else {
            this.$refs.pdfFile.value = "";
            this.tempForm.pdfName="未选择任何文件";
            this.$message({
              type: 'warning',
              message: '只能上传pdf文档'
            })
          }
        }
      },
      downWord(){
        //downLoad('/wfTemplate/getWordOrPdf',{templateId:this.templateId,fileType:'doc'})
         window.location.href=baseUrl + "/wfTemplate/getWordOrPdf?templateId="+this.templateId+"&fileType="+"doc"
      },
      downPdf(){
        //downLoad('/wfTemplate/getWordOrPdf',{templateId:this.templateId,fileType:'pdf'})
         window.location.href=baseUrl + "/wfTemplate/getWordOrPdf?templateId="+this.templateId+"&fileType="+"pdf"
      },
      downWordFile(){
        if(this.checkboxDatas==null || this.checkboxDatas.length==0){
          this.$message({
            type: 'warning',
            message: '请勾选要下载的条目'
          })
          return
        }

        getWordFileByTemplateIds({templateIds:this.checkbox.join(",")}).then(res=>{
          if(res.code=="200"){
            for(let n in res.data){
              // console.info(getBaseURL())
               let url= "/fileStorage/downFile?fileId="+res.data[n];
               console.info(url)
              //  window.location.href = url
            }
            console.info(res.data)
          }else{

          }
        })


        // window.location.href = baseURL + "/common/download?fileName=" + encodeURI(fileName) + "&delete=" + true;
      },
      copyData(){this.tempData1 = this.tempData},
      // 公用表格表头样式
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      // 模板搜索
      handleQuery(){
        if(this.searchParams.templateName!=null&&this.searchParams.templateName.replace(" ","")!=""){
          this.pageParams.templateName=this.searchParams.templateName;
        }else{
          this.pageParams.templateName="";
        }
        if(this.searchParams.templateStatus!=null&&this.searchParams.templateStatus.replace(" ","")!=""){
          this.pageParams.templateStatus=this.searchParams.templateStatus;
        }else{
          this.pageParams.templateStatus="";
        }
        this.pageNum=1;
        this.pageParams.pageNum=1;
        this.archivesaFunction();
      },
      // 模板重置
      resetQuery(){
        this.searchParams.templateStatus = ""
        this.searchParams.templateName = ""
        this.pageParams.templateName="";
        this.pageParams.templateStatus="";
        this.pageNum=1;
        this.pageParams.pageNum=1;
        this.archivesaFunction(this.pageParams);
      },
      // 点击行事件
      StepsListRowClick(row){
        this.$refs.tempRef.clearSelection()
        if (this.checkbox.length === 0) {
        this.$refs.tempRef.toggleRowSelection(row)
          return;
        }
        this.$refs.tempRef.toggleRowSelection(row)
      },
      // 新增模板
      addTemp(){
        this.tempForm={templateStatus:"0",wordName:"未选择任何文件",pdfName:"未选择任何文件"};
        this.addOrEdit="add";
        this.tempAddVisible = true
      },
      // 编辑模板
      editTemp(){
        if(this.checkboxDatas==null || this.checkboxDatas.length==0){
          this.$message({
            type: 'warning',
            message: '请勾选要编辑的信息'
          })
          return
        }
        if(this.checkboxDatas.length!=1){
          this.$message({
            type: 'warning',
            message: '请选择一条信息进行编辑'
          })
          return
        }

         getFileName({templateId:this.checkboxDatas[0].templateId}).then(res =>{
          if(res.code=='200'){
            for(let i in this.checkboxDatas[0]){
              this.tempForm[i]=this.checkboxDatas[0][i]
            }
            if(res.data!=null&&res.data!=''){
              if(res.data.pdf!=null&&res.data.pdf!=''){
                this.tempForm.pdfName=res.data.pdf;
              }else{
                this.tempForm.pdfName="未选择任何文件";
              }
              if(res.data.word!=null&&res.data.word!=''){
                this.tempForm.wordName=res.data.word;
              }else{
                this.tempForm.wordName="未选择任何文件";
              }

            }else{
              this.tempForm.pdfName="未选择任何文件";
              this.tempForm.wordName="未选择任何文件";
            }
            this.addOrEdit="edit";
            this.tempAddVisible = true
          }
        })
      },
      // 编辑保存
      onCommitEdit(val){
        this.$refs[val].validate((valid) => {
          if (valid) {
            let testFormData = new FormData();
            for(let key in this.tempForm){
              if(this.tempForm[key]!=null){
                if(key!="createTime"&&key!="updateTime"){
                  testFormData.append(key,this.tempForm[key])
                }
              }
            }
            testFormData.append("files",this.$refs.wordFile.files[0])
            testFormData.append("files",this.$refs.pdfFile.files[0])
            if(this.addOrEdit=="add"){
              if(this.$refs.pdfFile.files[0]==null||this.$refs.pdfFile.files[0]==""||this.$refs.wordFile.files[0]==null||this.$refs.wordFile.files[0]==""){
                this.$message({
                  type:"warning",
                  message:"请选择pdf文件与word文件"
                })
                return;
              }
              insertTemplate(testFormData).then(res=>{
                if(res.code=='200'){
                  this.$message({
                    type:"success",
                    message:"保存成功"
                  })

                  this.pageNum=1;
                  this.pageParams.pageNum=1;
                  this.archivesaFunction(this.pageParams)
                  this.tempAddVisible=false;
                  this.$refs[val].resetFields();
                }
              })
            }else{
              updateTemplate(testFormData).then(res=>{
                if(res.code=='200'){
                  this.$message({
                    type:"success",
                    message:"保存成功"
                  })

                  this.archivesaFunction(this.pageParams)
                  this.tempAddVisible=false;
                  this.$refs[val].resetFields();
                }
              })
            }
          }
        })

      },
      // 删除模板
      deleTemp(){
        if(this.checkboxDatas==null || this.checkboxDatas.length==0){
          this.$message({
            type: 'warning',
            message: '请勾选要删除的信息'
          })
          return
        }
        this.$confirm('此操作将永久删除所选信息, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
          .then(() => {
            if (this.checkbox != '' && this.checkbox != null) {
              let ids = this.checkbox.join(",");
              delTemplatesByIds({ids:ids}).then(res=>{
                if(res.code=="200"){
                  this.$message({
                    type:"success",
                    message:"删除成功"
                  })
                  this.pageNum=1;
                  this.pageParams.pageNum=1
                  this.archivesaFunction(this.pageParams);
                }
              })
            }
          })
          .catch(() => {
          })
      },
      // 创建flexpapaer
      opened(){
        console.log(this.url)
        let fp = new FlexPaperViewer(
          '/FlexPaperViewer',  //这里是FlexPaperViewer.swf的路径，相对根目录
          'viewerPlaceHolder', { //这里是要显示Swf的区域的ID
            config: {
              SwfFile:this.url, //编码设置
              Scale: 0.6,
              ZoomTransition: 'easeOut', //变焦过渡
              ZoomTime: 0.5,
              ZoomInterval: 0.2, //缩放滑块-移动的缩放基础[工具栏]
              FitPageOnLoad: true, //自适应页面
              FitWidthOnLoad: true, //自适应宽度
              FullScreenAsMaxWindow: false, //全屏按钮-新页面全屏[工具栏]
              ProgressiveLoading: false, //分割加载
              MinZoomSize: 0.2, //最小缩放
              MaxZoomSize: 3, //最大缩放
              SearchMatchAll: true,
              InitViewMode: 'Portrait', //初始显示模式(SinglePage,TwoPage,Portrait)
              ViewModeToolsVisible: true, //显示模式工具栏是否显示
              ZoomToolsVisible: true, //缩放工具栏是否显示
              NavToolsVisible: true, //跳页工具栏
              CursorToolsVisible: false,
              SearchToolsVisible: true,
              PrintPaperAsBitmap: false,
              localeChain: 'en_US',
            }
          })
      },
      // 模板详情
      tempViewDetals(val){
        this.templateName=val.templateName;
        this.templateStatus=val.templateStatus;
        this.templateId=val.templateId;
       getPdfFileByTemplateId({templateId:val.templateId}).then(res=>{
          if(res.code=="200"){
            let serverUrl = '/pdfjs/web/viewer.html'
            let pdfUrl =  baseUrl + "/fileStorage/downFileTest?fileId="+res.data // 调取接口返回文件流

            /**
             * 附加功能
             */
            // let buttonData="0,1"  //0打印功能    下载功能
            // pdfUrl=pdfUrl+"&button="+buttonData;

            let url = serverUrl+"?file="+encodeURIComponent(pdfUrl);
            this.pdfSrc=url

            this.tempDetailsVisible = true

          } else {
            this.$message(this.$message({
              type:"error",
              message:res.msg
            }))
          }
        })
      },
      // 下载模板
      tempDownload(){},

      /* 复选框事件 */
      handleSelectionChange(val) {
        this.checkboxDatas = val
        this.checkbox = val.map(item => item.templateId)
      },
      // 分页
      pageCurrent(val) {
        this.pageNum = val
        this.pageParams.pageNum=val;
        this.archivesaFunction(this.pageParams)
      },
      pageSizes(val) {
        this.pageSize = val
        this.pageNum=1;
        this.pageParams.pageSize=val;
        this.pageParams.pageNum=1;
        this.archivesaFunction()
      },
      archivesaFunction(){
        getTemplateList(this.pageParams).then(res=>{
          this.total=res.total;
          this.tempData=res.rows;
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .app-template {
    height: calc(100vh - 86px);
    .tree {
      width: 100%;
      height: 500px;
      padding: 20px;
      border-right: 1px solid #e2e3e6;
    }
    .search-form{
      width:100%;
      /*background:rgba(204, 204, 204, 0.18);*/
      margin-left:0px;
      margin-top:5px;
      padding-left: 10px;
      padding-top:10px;
      padding-bottom:10px;

      /deep/ .el-form-item{
        margin-bottom: 0 !important;
      }
    }
    .el-col.el-col-24.el-col-xs-24 {
      padding: 10px;
      margin: 10px;
    }
    .block {
      float: right;
      margin-top: 10px;
    }

    .el-tab-pane {
      min-height: calc(100vh - 86px);
    }

    .el-tabs__nav-wrap {
      min-height: calc(100vh - 86px);
    }

    .el-tabs__header {
      min-height: calc(100vh - 86px);
    }
    /deep/.el-table tbody tr:hover>td {
      background-color:#ecf5ff !important
    }
    .dialog-style {
      /deep/.el-dialog__body {
        padding: 10px;
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
    .dialog-basic {
      margin-left: 10px;
      .form-basic {
        margin-bottom: 15px;
        line-height: 30px;
        border-bottom: 1px solid #ccc;
        margin-left: 15px;
        margin-right: 15px;
      }
      .form-supplement {
        margin-bottom: 15px;
        line-height: 30px;
        border-bottom: 1px solid #ccc;
        margin-left: 15px;
        margin-right: 15px;
      }
      /deep/.el-select>.el-input{
        width: 163px !important;
      }
    }
    /deep/.el-scrollbar__wrap{
      overflow-x: hidden;
    }
    /deep/ .el-table__fixed-right-patch {
      background-color:#F8F8F9;
    }
    /deep/ .el-table .el-table__header-wrapper th{
      height:31px;
    }

     .fileTextInput{
      width:50%;
      /deep/.el-input__inner{
        padding:0px;
        border: 0;  // 去除未选中状态边框
        outline: none; // 去除选中状态边框
        background-color: rgba(0, 0, 0, 0);// 透明背景
      }
    }
  }
</style>

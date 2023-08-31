<template>
  <div class="app-regulations">
    <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px);overflow: auto;">
      <div class="fdsa" >
        <el-row ref="searchRow" style="margin: 0">
          <el-form
            v-hasPermi="['use:regulations:search']"
            size="mini"
            class="search-form"
            ref="searchRef"
            :model="searchParams"
            :inline="true"
            style="background:rgba(204, 204, 204, 0.18);padding: 14px;"
            >
            <el-form-item label="制度名称" prop="reguName" >
              <el-input
                @keyup.enter.native="onEnterSearch"
                v-model="searchParams.ruleName"
                placeholder="请输入制度名称"
                clearable
                style="width: 150px"
              />
            </el-form-item>
            <el-form-item label="制度类型" prop="reguType" >
              <el-select v-model="searchParams.ruleType" filterable placeholder="请选择制度类型" size="mini" clearable style="width:100%">
                <el-option v-for="item in typesOptions" :key="item.types" :label="item.label" :value="item.types"  :disabled="item.disabled"/>
              </el-select>
            </el-form-item>
            <el-form-item >
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button> -->
            </el-form-item>
          </el-form>
          <div class="btn" style="padding: 10px 0">
              <el-button type="primary" size="mini" icon="el-icon-plus" v-hasPermi="['use:regulations:add']" @click="addRegu">新增</el-button>
              <el-button type="primary" size="mini" icon="el-icon-edit" v-hasPermi="['use:regulations:edit']"   @click="editRegu">编辑</el-button>
              <el-button type="primary" size="mini" icon="el-icon-delete" v-hasPermi="['use:regulations:delete']"   @click="deleRegu">删除</el-button>
              <el-button type="primary" size="mini" icon="el-icon-download" v-hasPermi="['use:regulations:import']"  @click="exportExcel">导出</el-button>
          </div>

          <!-- <el-col :span="1.5">
            <el-button type="primary" size="mini" icon="el-icon-download"  @click="downWordFile">下载word文档</el-button>
          </el-col> -->
        </el-row>
      </div>
      <el-table
        :default-sort="{ prop: 'date', order: 'descending' }"
        size="mini"
        :data="reguData"
        ref="reguRef"
        stripe
        border
        empty-text=" "
        tooltip-effect="light"
        :height="tableHeight"
        :header-cell-style="headClass"
        style="width: 100%"
        @selection-change="handleSelectionChange"
        @row-click="StepsListRowClick"
        >
        <el-table-column align="center" :resizable="false" :show-overflow-tooltip="true" type="selection" width="50" />
        <el-table-column align="center" :resizable="false"  :show-overflow-tooltip="true" label="序号" type="index" min-width="60" :index="customIndex"/>
        <el-table-column
          align="center"
          v-for="(item, index) in reguColumn"
          :v-if="item.isCol"
          :key="index"
          :resizable="false"
          :label="item.label"
          :prop="item.prop"
          :formatter="item.formatter"
          :width="item.width"
          :show-overflow-tooltip="true"
        />
        <el-table-column fixed="right" :resizable="false" align="center" label="操作" width="100">
          <template slot-scope="scope">
            <el-button type="text" size="mini"  v-hasPermi="['use:regulations:details']" @click="reguViewDetals(scope.row)">查看详情</el-button>
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
    <!--  制度详情  -->
    <el-dialog
      title="规章制度详情"
      width="90%"
      :close-on-click-modal="false"
      class="dialog-style dialog-basic"
      :visible.sync="reguDetailsVisible"
      :destroy-on-close="true"
      >
      <!-- <div class="form-basic">规章制度信息</div> -->
      <!-- <div style="margin: 10px 30px;border: 1px solid #888888;">
        <a id="viewerPlaceHolder" style="width: 100%; height: 700px; display: block;"></a>
      </div> -->
       <div>
        <div style="margin: 10px 30px">
          <span slot="tip" >制度名称：</span>
          <span slot="tip" >{{ruleName}}</span>
          <!--<el-button style="margin-left: 5px" type="text" size="mini" @click="reguBrowse" >查看</el-button>-->
        </div>
        <div style="margin: 10px 30px">
          <span slot="tip" >执行情况：</span>
          <span slot="tip" >{{ruleStatus=="0"?"现行有效":"已废止"}}</span>
        </div>
        <div style="margin: 10px 30px">
          <span slot="tip" >文件注释：</span>
          <span slot="tip" >{{ruleRemark}}</span>
        </div>
        <div style="margin: 10px 30px">
          <span slot="tip" >下载文件：</span>
          <span slot="tip" style="color:#1890ff;cursor: pointer;text-decoration:underline"  @click="downWord">下载word</span>
          <span style="padding:0 0 0 10px;color:#1890ff;cursor: pointer;text-decoration:underline" @click="downPdf" slot="tip" >下载pdf</span>
        </div>
      </div>
      <div style="height: calc(100vh - 330px);">
        <iframe :src="pdfSrc" height="100%" width="100%"></iframe>
      </div>
      <span slot="footer" >
        <el-button size="mini" @click="reguDetailsVisible = false">关 闭</el-button>
      </span>
    </el-dialog>


    <!--  新增/编辑制度  -->
    <el-dialog :close-on-click-modal="false" title="制度信息" width="70%"  class="dialog-style dialog-basic"  :visible.sync="reguAddVisible" :destroy-on-close="true">
      <div>
      <el-row >
        <el-col :span="24">
          <el-form ref="reguForm" :rules="rules" :model="reguForm"  label-width="120px" style="width:90%;margin: 10px 0px 0px 0px">
            <el-col :span="24">
              <el-form-item prop="ruleName" label="制度名称">
                <el-input size="mini" v-model="reguForm.ruleName" placeholder="请输入制度名称" style="width:100%"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="ruleNumber" label="文号">
                <el-input size="mini" v-model="reguForm.ruleNumber" placeholder="请输入文号" style="width:100%"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="制度类型"  >
                <el-select v-model="reguForm.ruleType" filterable placeholder="请选择制度类型" size="mini"  style="width:100%">
                  <el-option v-for="item in typesOptions" :key="item.types" :label="item.label" :value="item.types"  :disabled="item.disabled"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="ruleStatus" label="执行情况" label-width="80px" >
                <el-radio-group v-model="reguForm.ruleStatus">
                  <el-radio label="0">现行有效</el-radio>
                  <el-radio label="1">已废止</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item prop="ruleRemark" label="文件注释">
                <el-input
                  type="textarea"
                  style="width:100%"
                  :autosize="{minRows:1, maxRows: 3}"
                  v-model="reguForm.ruleRemark"
                  placeholder="请输入文件注释"
                  size="mini"
                  />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item  label="word文件" prop='wordName'>
                <input  type="file"  single accept=".doc,.docx"  name="upload_Wfile"  style="display: none;"
                  @change="wordFileChange" ref="wordFile"/>
                <el-button size="mini"  onclick="upload_Wfile.click(); " style="width:75px">选择文件</el-button>
                <el-input size="mini"  v-model="reguForm.wordName"   onclick="upload_Wfile.click(); "  :readonly="true"  class="fileTextInput"/>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="pdf文件" >
                <input  type="file" single accept="application/pdf"  name="upload_Pfile"  style="display: none;"  ref="pdfFile"
                 @change="pdfFileChange"/>
                <el-button size="mini"  onclick="upload_Pfile.click(); " style="width:75px">选择文件</el-button>
                <el-input size="mini"  v-model="reguForm.pdfName"  onclick="upload_Pfile.click();"  :readonly="true" class="fileTextInput"/>
              </el-form-item>
            </el-col>
          </el-form>

        </el-col>
      </el-row>
      </div>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="reguAddVisible = false">取 消</el-button>
          <el-button size="mini" type="primary" @click="onCommitEdit('reguForm')">保 存</el-button>
        </div>
    </el-dialog>
  </div>
</template>

<script>
  import { getFileName,insertWfRule,getRuleList ,delWfRuleByIds,updateWfRule,getPdfFileByRuleId } from '@/api/use/regulations'
import { getBaseURL } from 'xe-utils/methods'
import { downLoad,downLoadPost } from '@/downLoad/filedownload'

  const baseUrl = process.env.VUE_APP_BASE_API;

  export default {
    watch: {
      reguData:{
        handler(newName, oldName) {
          this.$nextTick(()=>{
            this.$refs.reguRef.doLayout();
          })
        },
        deep:true,
        // immediate:true
      },
    },
    data() {
      return {
        /* 总页数 */
        pdfSrc:"",
        ruleId:"",
        total: 0,
        pageNum: 1,
        pageSize: 20,
        pageParams:{
          pageNum:1,
          pageSize:20
        },
        addOrEdit:"add",
        /* 刷新遮罩 */
        loading: true,
        tabsShow: true,
        // 高度
        searchRow:'searchRow',
        searchRowHeight :'',
        tableHeight:'73%',
        // 制度条件区
        searchParams: {},
        reguData1: [], //备份数据
        // 制度
        reguColumn: [
          { label: '制度名称', isCol: true, prop: 'ruleName' },
          { label: '文号', isCol: true, prop: 'ruleNumber' },
          { label: '制度类型', isCol: true, prop: 'ruleType', width: '100px' },
          { label: '执行情况', isCol: true, prop: 'ruleStatus', width: '100px' ,formatter:function(row, column, cellValue){
            return cellValue==null?"":cellValue==""?"":cellValue=="0"?"现行有效":"已废止"
          }},
          { label: '文件注释', isCol: true, prop: 'ruleRemark' },
        ],
        reguData: [],
        // 制度详情
        reguDetailsVisible: false,
        rules:{
          ruleName:[
            { required: true, message: '请输入制度名称',trigger: 'blur' },
            { min: 1, max: 50, message: '输入内容不得超过100个字符' ,trigger: 'blur' }
          ],
          ruleNumber:[
            { min: 1, max: 40, message: '输入内容不得超过40个字符', trigger: 'blur' }
          ],
          ruleRemark:[
            { required: true, message: '请输入文件注释' ,trigger: 'blur'},
            { min: 1, max: 255, message: '输入内容不得超过500个字符',trigger: 'blur' }
          ]
        },
        editWordName:"",
        editPdfName:"",
        ruleName:"",
        ruleStatus:"",
        ruleRemark:"",
        reguName: '',
        condition: '',
        annotation: '',
        url: '',
        // 新增/编辑制度
        reguAddVisible: false,
        reguForm: {ruleName:'',ruleNumber:'',ruleStatus:'0',wordName:"未选择任何文件",pdfName:"未选择任何文件",ruleType:"",ruleRemark:""},
        typesOptions: [
          {
            types: '法律法规',
            label: '法律法规'
          },
          {
            types: '综合',
            label: '综合'
          },
          {
            types: '审核整理',
            label: '审核整理'
          },
          {
            types: '档案目录',
            label: '档案目录'
          },
          {
            types: '提供利用',
            label: '提供利用'
          },
          {
            types: '接收转递',
            label: '接收转递'
          },
          {
            types: '专项审核',
            label: '专项审核'
          },
          {
            types: '材料收集',
            label: '材料收集'
          },
        ],
        types: '',
        // 复选框
        checkbox: [],
        checkboxDatas: [],
      }
    },
    mounted() {
      this.archivesaFunction()
      this.$nextTick(()=>{
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = document.body.clientHeight - (84 + 55 + this.searchRowHeight) + "px"
      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
          this.tableHeight = document.body.clientHeight - (84 + 55 + this.searchRowHeight) + "px"
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

      wordFileChange(val){
        var fileName = val.target.value;
        if(val.target.value==''){
          this.reguForm.wordName="未选择任何文件"
        }else{
          var index1=fileName.lastIndexOf(".");
          var index2=fileName.length;
          var suffix=fileName.substring(index1, index2);//后缀名
          if (suffix == '.doc' || suffix == '.docx') {
            this.reguForm.wordName=fileName
          } else {
            this.$refs.wordFile.value = "";
            this.reguForm.wordName="未选择任何文件";
            this.$message({
              type: 'warning',
              message: '只能上传word文档'
            })
          }
        }
      },
      onEnterSearch() {
        this.handleQuery();
      },
      pdfFileChange(val){
        console.info(val)
        var fileName = val.target.value;
        if(val.target.value==''){
          this.reguForm.pdfName="未选择任何文件"
        }else{
          var index1=fileName.lastIndexOf(".");
          var index2=fileName.length;
          var suffix=fileName.substring(index1, index2);//后缀名
          if (suffix == '.pdf') {
            this.reguForm.pdfName=fileName;
          } else {
            this.$refs.pdfFile.value = "";
            this.$message({
              type: 'warning',
              message: '只能上传pdf文档'
            })
          }
        }
      },
      downWord(){
        // console.info(this.activeService);
        //downLoad('/wfRule/getWordOrPdf',{ruleId:this.ruleId,fileType:'doc'})
        window.location.href=baseUrl + "/wfRule/getWordOrPdf?ruleId="+this.ruleId+"&fileType="+"doc"
      },
      downPdf(){
        //downLoad('/wfRule/getWordOrPdf',{ruleId:this.ruleId,fileType:'pdf'})
        window.location.href=baseUrl + "/wfRule/getWordOrPdf?ruleId="+this.ruleId+"&fileType="+"pdf"
      },
      downWordFile(){
        if(this.checkboxDatas==null || this.checkboxDatas.length==0){
          this.$message({
            type: 'warning',
            message: '请勾选要下载的条目'
          })
          return
        }

        console.info(this.checkboxDatas[0].ruleId)



        // window.location.href = baseURL + "/common/download?fileName=" + encodeURI(fileName) + "&delete=" + true;
      },
      copyData(){this.reguData1 = this.reguData},
      // 公用表格表头样式
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      // 制度搜索
      handleQuery(){
        if(this.searchParams.ruleName!=null&&this.searchParams.ruleName.replace(" ","")!=""){
          this.pageParams.ruleName=this.searchParams.ruleName;
        }else{
          this.pageParams.ruleName="";
        }
        if(this.searchParams.ruleType!=null&&this.searchParams.ruleType.replace(" ","")!=""){
          this.pageParams.ruleType=this.searchParams.ruleType;
        }else{
          this.pageParams.ruleType="";
        }
        this.pageNum=1;
        this.pageParams.pageNum=1;
        this.archivesaFunction();
      },
      // 制度重置
      resetQuery(){
        this.searchParams.ruleName = ""
        this.searchParams.ruleType = ""
        this.pageParams.ruleName="";
        this.pageParams.ruleType="";
        this.pageNum=1;
        this.pageParams.pageNum=1;
        this.archivesaFunction(this.pageParams);
      },
      // 点击行事件
      StepsListRowClick(row){
        this.$refs.reguRef.clearSelection()
        if (this.checkbox.length === 0) {
        this.$refs.reguRef.toggleRowSelection(row)
          return;
        }
        this.$refs.reguRef.toggleRowSelection(row)
      },
      // 新增制度
      addRegu(){
        this.reguForm={ruleName:'',ruleNumber:'',ruleStatus:'0',wordName:"未选择任何文件",pdfName:"未选择任何文件",ruleType:"",ruleRemark:""},
        this.addOrEdit="add";
        this.reguAddVisible = true
      },
      // 编辑制度
      editRegu(){
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
        getFileName({ruleId:this.checkboxDatas[0].ruleId}).then(res =>{

          if(res.code=='200'){
            for(let i in this.checkboxDatas[0]){
              this.reguForm[i]=this.checkboxDatas[0][i]
            }
            if(res.data!=null&&res.data!=''){
              if(res.data.pdf!=null&&res.data.pdf!=''){
                this.reguForm.pdfName=res.data.pdf;
              }else{
                this.reguForm.pdfName="未选择任何文件";
              }
              if(res.data.word!=null&&res.data.word!=''){
                this.reguForm.wordName=res.data.word;
              }else{
                this.reguForm.wordName="未选择任何文件";
              }
            }else{
              this.reguForm.wordName="未选择任何文件";
              this.reguForm.pdfName="未选择任何文件";
            }

            this.addOrEdit="edit";

            this.reguAddVisible = true
          }
        })

      },
      // 编辑保存
      onCommitEdit(val){
        const _this = this;
        this.$refs[val].validate((valid) => {
          if (valid) {
            let testFormData = new FormData();
            for(let key in this.reguForm){
              if(this.reguForm[key]!=null){
                if(key!="createTime"&&key!="updateTime"){
                  testFormData.append(key,this.reguForm[key])
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
              let success = function (res,_this) {
                if(res.code=='200'){
                  _this.$message({
                    type:"success",
                    message:"保存成功"
                  })

                  _this.pageNum=1;
                  _this.pageParams.pageNum=1;
                  _this.archivesaFunction(_this.pageParams)
                  _this.reguAddVisible=false;
                  _this.$refs[val].resetFields();
                }
              }
              insertWfRule(testFormData,success,_this)
            }else{
              let success = function (res,_this) {
                if(res.code=='200'){
                  _this.$message({
                    type:"success",
                    message:"保存成功"
                  })

                  _this.archivesaFunction(_this.pageParams)
                  _this.reguAddVisible=false;
                  _this.$refs[val].resetFields();
                }
              }
              updateWfRule(testFormData,success,_this)
            }
          }
        })


      },
      // 删除制度
      deleRegu() {
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
              delWfRuleByIds({ids:ids}).then(res=>{
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
              localeChain: 'en_CN',
            }
          })
      },
      // 制度详情
      reguViewDetals(val){
        this.ruleName = val.ruleName;
        this.ruleStatus = val.ruleStatus;
        this.ruleRemark = val.ruleRemark;
        this.ruleId=val.ruleId;
        getPdfFileByRuleId({ruleId:val.ruleId}).then(res=>{
          if(res.code=="200"){
            let serverUrl = '/pdfjs/web/viewer.html'
            let pdfUrl =  baseUrl + "/fileStorage/downFile?fileId="+res.data // 调取接口返回文件流

            /**
             * 附加功能
             */
            // let buttonData="0,1"  //0打印功能    下载功能
            // pdfUrl=pdfUrl+"&button="+buttonData;

            let url = serverUrl+"?file="+encodeURIComponent(pdfUrl);
            console.info(url)
            this.pdfSrc=url

            this.reguDetailsVisible = true

          } else {
            this.$message(this.$message({
              type:"error",
              message:res.msg
            }))
          }
        })


      },
      // 查看制度
      reguBrowse(){
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
              localeChain: 'en_US'
            }
          })
      },
      // 下载制度
      reguDownload(){},

      /* 复选框事件 */
      handleSelectionChange(val) {
        this.checkboxDatas = val
        this.checkbox = val.map(item => item.ruleId)
      },
      // 分页
      pageCurrent(val) {
        this.pageNum = val
        this.pageParams.pageNum=val;
        this.archivesaFunction()
      },
      pageSizes(val) {
        this.pageSize = val
        this.pageParams.pageSize=val;
        this.archivesaFunction()
      },
      archivesaFunction(){
        getRuleList(this.pageParams).then(res=>{
          this.total=res.total;
          this.reguData=res.rows;
        })
      },

      exportExcel(){

        if (this.reguData.length <= 0) {
          this.$message({
            type: 'warning',
            message: '无任何数据需要导出'
          });
          return;
        }

        if(this.checkboxDatas.length == 0){
          this.$confirm('是否导出搜索后的制度数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            this.pageParams.pageNum=1;
            this.pageParams.pageSize=9999999;

            getRuleList(this.pageParams).then(res=>{
              if(res.rows.length==0){
                this.$message.error("无数据,导出失败");
                return;
              }
              let row = {
                row: res.rows,
                status: 18
              }
              downLoadPost("/export/excel/exportClick", row)
            })
          })

        }else{
          this.$confirm('是否导出选中的制度数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let row = {
              row: this.checkboxDatas,
              status: 18
            }
            downLoadPost("/export/excel/exportClick", row)
          })

        }



        // let s = JSON.stringify(this.pageParams);
        // console.log(s);
        // s = encodeURI(s);
        // downLoad("/export/excel/export?status=10&exportData=" + s)
      }
    }
  }
</script>

<style lang="scss" scoped>
  .app-regulations {
    height: calc(100vh - 86px);
    .tree {
      width: 100%;
      height: 500px;
      padding: 20px;
      border-right: 1px solid #e2e3e6;
    }
    .search-form{
      width:100%;
      background:rgba(204, 204, 204, 0.18);
      padding: 10px;
      display: flex;
      .form-width {
        width: 22%;
        display: flex;
      }
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
      margin-top: 5px;
      margin-bottom: -5px;
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

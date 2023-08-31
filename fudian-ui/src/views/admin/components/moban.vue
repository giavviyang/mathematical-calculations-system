<template>

  <el-card class="box-card" style="width: 100%;position: relative;" :body-style=this.bodystyle>
    <div slot="header">
      <!--<span>常用模板</span>
      <el-button style="float: right; padding:3px 0" type="text" @click="showModal" >更多</el-button>-->
    </div>
    <el-table
      :data="list"
      style="width: 100%;"
      :show-header="false"
      tooltip-effect="light"
      >
      <el-table-column label="模板名称" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          ○ {{ scope.row.templateName }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="preview(scope.row)" type="text" size="small">预览</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--  模板详情  -->
    <el-dialog
      title="常用模板详情"
      :close-on-click-modal="false"
      width="90%"
      class="dialog-style dialog-basic"
      :visible.sync="tempDetailsVisible"
      @opened="opened"
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
     <div>
        <iframe :src="pdfSrc" height="600" width="100%"></iframe>
      </div>
      <span slot="footer" >
        <el-button size="mini" @click="tempDetailsVisible = false">关 闭</el-button>
      </span>
    </el-dialog>
  </el-card>
</template>

<script>
  import {getTemplateList,getPdfFileByTemplateId} from '@/api/use/template'
  const baseUrl = process.env.VUE_APP_BASE_API;
  export default {
    data() {
      return {
        templateName:'',
        templateStatus:'',
        templateId:'',
        pdfSrc:'',
        bodystyle:{
          padding:'0'
        },
        list: null,
        tempDetailsVisible: false,

      }
    },
    created() {
      // this.fetchData()
      // this.initData()
    },
    methods: {
      downWord(){
        window.location.href="/wfTemplate/getWordOrPdf?templateId="+this.templateId+"&fileType="+"doc"
      },
      downPdf(){
        window.location.href="/wfTemplate/getWordOrPdf?templateId="+this.templateId+"&fileType="+"pdf"
      },
      initData(){
        let param={
          pageSize:6,
          pageNum:1,
          templateStatus:"0"
        }
        getTemplateList(param).then(res=>{
          this.list=res.rows;
        })
      },
      showModal(){
        this.$router.push({
          name: '常用模板'
        })
      },
      fetchData() {
        this.list = [
          {
            id: '1',
            tempName: '干部人事档案目录',
            condition: '正在使用',
            url: '../template/1.干部人事档案目录.swf',
          },
          {
            id: '2',
            tempName: '干部人事档案专项审核情况登记表',
            condition: '正在使用',
            url: '../template/2.干部人事档案专项审核情况登记表.swf',
          },
          {
            id: '3',
            tempName: '干部“三龄一历”确认表',
            condition: '正在使用',
            url: '../template/3.干部三龄一历确认表.swf',
          },
          /**
          {
            id: '4',
            tempName: '干部职务变动登记表',
            condition: '正在使用',
            url: '../template/4.干部职务变动登记表.swf',
          },
          {
            id: '5',
            tempName: '档案审核整理责任卡',
            condition: '正在使用',
            url: '../template/5.档案审核整理责任卡.swf',
          },
          */
        ]
      },
      // 创建flexpapaer
      opened(){
        console.log(this.url)
        let fp = new FlexPaperViewer(
          '/FlexPaperViewer',  //这里是FlexPaperViewer.swf的路径，相对根目录
          'viewerPlaceHolder2', { //这里是要显示Swf的区域的ID
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
      preview(val){
        this.templateName=val.templateName;
        this.templateStatus=val.templateStatus;
        this.templateId=val.templateId;
        getPdfFileByTemplateId({templateId:val.templateId}).then(res=>{
          if(res.code=="200"){
            let serverUrl = '/pdfjs/web/viewer.html'
            let pdfUrl = baseUrl + "/fileStorage/downFile?fileId="+res.data // 调取接口返回文件流
            let url = serverUrl+"?file="+encodeURIComponent(pdfUrl);
            this.pdfSrc=url
            this.tempDetailsVisible = true
          }
        })
      },
      // 下载模板
      download(){},
    }
  }
</script>


<style lang="scss" scoped>
 /* .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  } */


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

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
    padding: 0px;
  }

  //设置统一表头
    /deep/.el-table tbody tr:hover>td {
      background-color:#ecf5ff !important
    }

  /deep/.el-table__body td {
     padding: 0;
     height: 40px;
   }
</style>

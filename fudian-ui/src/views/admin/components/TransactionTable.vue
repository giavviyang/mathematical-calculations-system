<template>

  <el-card class="box-card" style="width: 100%;position: relative;"  :body-style=this.bodystyle>
    <div slot="header">
      <!--<span>规章制度</span>
      <el-button style="float: right; padding: 3px 0" type="text" @click="showModal">更多</el-button>-->
      <!--
      <vxe-modal v-model="modal.show" width="800" height="400" min-width="400" min-height="320" style="z-index: 9999" resize remember>
        <template v-slot:title>
          <span >{{modal.title}}</span>
        </template>
        <template>
          <vxe-grid
            border
            resizable
            show-overflow
            auto-resize
            height="auto"
            :sync-resize="modal.show"
            >
          </vxe-grid>
        </template>
      </vxe-modal>
      -->
    </div>
    <el-table
        :data="list"
         style="width: 100%;"
        :show-header="false"
        tooltip-effect="light">
      <el-table-column label="制度名称"
      :show-overflow-tooltip="true">
        <template slot-scope="scope">
          ○{{ scope.row.ruleName }}
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        align="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button @click="preview(scope.row)" type="text" size="mini">预览</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--  制度详情  -->
    <el-dialog
      :close-on-click-modal="false"
      title="规章制度详情"
      width="90%"
      class="dialog-style dialog-basic"
      :visible.sync="reguDetailsVisible"
      @opened="opened"
      :destroy-on-close="true"
      >
      <!-- <div class="form-basic">规章制度信息</div> -->
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
      <div>
        <iframe :src="pdfSrc" height="600" width="100%"></iframe>
      </div>
      <span slot="footer" >
        <el-button size="mini" @click="reguDetailsVisible = false">关 闭</el-button>
      </span>
    </el-dialog>
    <!--  制度下载  -->
  </el-card>
</template>

<script>
import { getRuleList,getPdfFileByRuleId} from '@/api/use/regulations'
const baseUrl = process.env.VUE_APP_BASE_API;
  export default {
    data() {
      return {
        pdfSrc:'',
        reguDetailsVisible: false,
        reguDetailsForm: {},
        ruleName: '',
        ruleStatus: '',
        ruleRemark: '',
        ruleId:'',
        url: '',

        // modal:{
        //   show:false,
        //   title:'测试标题'
        // },
        bodystyle: {
          padding: '0'
        },
        list: null
      }
    },
    created() {
    //  this.fetchData()
    //   this.initData()
    },
    methods: {
      downWord(){
        window.location.href="/wfRule/getWordOrPdf?ruleId="+this.ruleId+"&fileType="+"doc"
      },
      downPdf(){
        window.location.href="/wfRule/getWordOrPdf?ruleId="+this.ruleId+"&fileType="+"pdf"
      },
      initData(){
        let params  = {
          pageSize:6,
          pageNum:1,
          ruleStatus:"0"
        }
        getRuleList(params).then(res=>{
          this.list=res.rows;
        })
      },
      showModal(){
        //this.modal.title = title;
        //this.modal.show = !this.modal.show
        this.$router.push({
          name: '规章制度'
        })
      },
      fetchData() {
        this.list = [
          {
            id: '1',
            reguName: '干部人事档案工作条例',
            reguVersion: '',
            reguType: '法律法规',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/1.干部人事档案工作条例.swf'
          },
          {
            id: '2',
            reguName: '中国人民共和国档案法',
            reguVersion: '中华人民共和国主席令第57号',
            reguType: '法律法规',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/2.中华人民共和国档案法.swf'
          },
          {
            id: '3',
            reguName: '中华人民共和国档案法实施办法',
            reguVersion: '国务院令第676号',
            reguType: '法律法规',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/3.中华人民共和国档案法实施办法.swf'
          },
          /**
          {
            id: '4',
            reguName: '档案管理违法违纪行为处分规定',
            reguVersion: '中华人民共和国监察部、中华人民共和国人力资源和社会保障部、国家档案局令第30号',
            reguType: '法律法规',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/4.档案管理违法违纪行为处分规定.swf'
          },
          {
            id: '5',
            reguName: '中共中央组织部关于进一步从严管理干部档案的通知',
            reguVersion: '中组发[2014]9号',
            reguType: '综合',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/5.中共中央组织部关于进一步从严管理干部档案的通知.swf'
          },
          {
            id: '6',
            reguName: '中共中央组织部关于印发《干部人事档案材料收集归档规定》的通知',
            reguVersion: '中组发[2009]12号',
            reguType: '材料收集',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/6.中共中央组织部关于印发《干部人事档案材料收集归档规定》的通知.swf'
          },
          {
            id: '7',
            reguName: '中共中央关于印发《党政领导干部选拔任用工作条例》的通知（节录）',
            reguVersion: '中发[2014]3号',
            reguType: '法律法规',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/7.中共中央关于印发《党政领导干部选拔任用工作条例》的通知（节录）.swf'
          },
          {
            id: '8',
            reguName: '中华人民共和国公务员法',
            reguVersion: '中华人民共和国主席令第76号',
            reguType: '法律法规',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/8.中华人民共和国公务员法.swf'
          },
          {
            id: '9',
            reguName: '中共中央组织部关于印发《干部人事档案工作目标管理暂行办法》和《干部人事档案工作目标管理考评标准》的通知',
            reguVersion: '组通字[1996]55号',
            reguType: '综合',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/9.中共中央组织部关于印发《干部人事档案工作目标管理暂行办法》和《干部人事档案工作目标管理考评标准》的通知.swf'
          },
          {
            id: '10',
            reguName: '中共中央组织部关于印发《干部人事档案工作目标管理检查验收细则》的通知',
            reguVersion: '组通字[1998]13号',
            reguType: '综合',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/10.中共中央组织部关于印发《干部人事档案工作目标管理检查验收细则》的通知.swf'
          },
          {
            id: '11',
            reguName: '中共中央办公厅关于印发《党政领导干部选拔任用工作责任追究办法（试行）》的通知（节录）',
            reguVersion: '中办发[2010]9号',
            reguType: '综合',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/11.中共中央办公厅关于印发《党政领导干部选拔任用工作责任追究办法（试行）》的通知（节录）.swf'
          },
          {
            id: '12',
            reguName: '中共中央组织部办公厅关于严格审核干部任前公示信息的通知',
            reguVersion: '组厅字[2012]36号',
            reguType: '综合',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/12.中共中央组织部办公厅关于严格审核干部任前公示信息的通知.swf'
          },
          {
            id: '13',
            reguName: '中共中央组织部人事部中央编办关于进一步加强管理严肃干部人事工作纪律有关问题的通知',
            reguVersion: '人发[2001]88号',
            reguType: '综合',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/13.中共中央组织部人事部中央编办关于进一步加强管理严肃干部人事工作纪律有关问题的通知.swf'
          },
          {
            id: '14',
            reguName: '中共中央组织部关于报送新任中管干部数字档案的通知',
            reguVersion: '组通字[2010]61号',
            reguType: '接收转递',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/14.中共中央组织部关于报送新任中管干部数字档案的通知.swf'
          },
          {
            id: '15',
            reguName: '中共中央组织部关于进一步规范中管干部档案查借阅工作的通知',
            reguVersion: '组通字[2007]34号',
            reguType: '提供利用',
            condition: '现行有效',
            annotation: '《条例》总结吸收党的十八大以来从严管理干部人事档案工作的新经验新成果，'
              +'对干部人事档案工作的体制机制、内容建设、日常管理、利用审核、纪律监督等加以规范完善，'
              +'是今后一个时期全国各级各类干部人事档案工作的基本遵循。本条例自2018年11月20日起实施，'
              +'1991年4月2日中央组织部、国家档案局印发的《干部档案工作条例》同时废止。',
            url: '../regular/15.中共中央组织部关于进一步规范中管干部档案查借阅工作的通知.swf'
          },
          */
        ]
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
      // 预览
      preview(val){
        this.reguDetailsVisible = true
        this.ruleName = val.ruleName
        this.ruleStatus = val.ruleStatus
        this.ruleRemark = val.ruleRemark
        this.ruleId=val.ruleId
        getPdfFileByRuleId({ruleId:val.ruleId}).then(res=>{
        if(res.code=="200"){
          let serverUrl = '/pdfjs/web/viewer.html'
          let pdfUrl = baseUrl + "/fileStorage/downFile?fileId="+res.data // 调取接口返回文件流
          let url = serverUrl+"?file="+encodeURIComponent(pdfUrl);
          this.pdfSrc=url

          this.reguDetailsVisible = true

        }
      })
      },
      // 查看
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
      // 下载
      download(val){

      }
    }
  }
</script>
<style lang="scss" scoped>
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

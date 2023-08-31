<template>
   <div class="app-wait" ref="appwait" id="app-wait">
     <el-row>
       <el-col :span="24">
           <!-- @load="iframeLoad" -->
           <div class="uploadMks"
            v-if="uploadMks"
            v-loading="true"
            element-loading-text="正在上传电子档案，请耐心等待"
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)">
           </div>
           <div class="uploadMks"
            v-if="updonwMks"
            v-loading="true"
            element-loading-text="正在下载，请耐心等待"
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)">
           </div>
           <div v-if="pdfhidden" class="pdfViewer">
              <div class="pdfBth">
                <a @click="exitClick" style="width: 60px;display: block;height: 40px;float: right;">退出</a>
              </div>
              <iframe :src="PdfSrc" class="iframeViewer" frameborder="0"></iframe>
           </div>
           <iframe :src="iframeSrc" height="600" width="500" style="margin: 50px;" hidden="hidden" ref="iframe"></iframe>
       </el-col>
       <el-col :span="24" style="height: calc(100vh - 84px);">
         <!-- 功能按钮 -->
         <div class="btn" v-if="isBrowse">
           <el-button :disabled="isBut" type="primary" size="mini" v-if="upBtnHidden" icon="el-icon-upload" class="top-btn" @click="sendMessage(2)">导入</el-button>
           <el-button :disabled="isBut" style="margin: 5px;" v-if="upBtnHidden" type="primary" size="mini" icon="el-icon-upload" @click="sendMessage(1)">扫描</el-button>
           <el-button style="margin: 5px;" v-if="upBtnHidden" type="primary" size="mini" icon="el-icon-delete-solid" @click="deleteAll">删除</el-button>
           <el-button style="margin: 5px;" v-if="upBtnHidden" type="primary" size="mini" icon="el-icon-download" @click="TheSorting">排序</el-button>
           <el-dropdown style="margin: 5px;" v-if="upBtnHidden" @command="downloadPic" placement="bottom-end">
              <el-button type="primary" size="mini" icon="el-icon-arrow-down">下载</el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="1">下载当前张
                  <!-- <el-button style="margin: 5px;" type="primary" size="mini" icon="el-icon-download" @click="downloadPic(1)">下载当前张</el-button> -->
                </el-dropdown-item>
                <el-dropdown-item command="2">下载当前份
                  <!-- <el-button style="margin: 5px;" type="primary" size="mini" icon="el-icon-download" @click="downloadPic(2)">下载当前份</el-button> -->
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-dropdown style="margin: 5px;" v-if="upBtnHidden" @command="PrintRow" placement="bottom-end">
              <el-button type="primary" size="mini" icon="el-icon-arrow-down">打印</el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="1">打印当前张
                  <!-- <el-button style="margin: 5px;" type="primary" size="mini" icon="el-icon-tickets" @click="PrintRow(1)">打印当前张</el-button> -->
                </el-dropdown-item>
                <el-dropdown-item command="2">打印当前份
                  <!-- <el-button style="margin: 5px;" type="primary" size="mini" icon="el-icon-tickets" @click="PrintRow(2)">打印当前份</el-button> -->
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-button :disabled="isBut" style="margin: 5px;" v-if="upBtnHidden" type="primary" size="mini" icon="el-icon-upload" @click="upload">上传</el-button>
<!--            <el-button style="margin: 5px;" v-if="browseBtnHidden" type="primary" icon="el-icon-caret-left" @click="OnThePicture" size="mini">上页</el-button>
            <el-button style="margin: 5px;" v-if="browseBtnHidden" type="primary" icon="el-icon-caret-right" @click="nextPicture" size="mini">下页</el-button>
            <el-button style="margin: 5px;" v-if="browseBtnHidden" type="primary" icon="el-icon-d-arrow-left" @click="onAFile" size="mini">上份</el-button>
            <el-button style="margin: 5px;" v-if="browseBtnHidden" type="primary" icon="el-icon-d-arrow-right" @click="underAFile" size="mini">下份</el-button> -->
            <el-button style="margin: 5px;" type="primary" size="mini" icon="el-icon-upload" @click="browseMode">{{modelText}}</el-button>
         </div>
         <el-col :span="24" >
           <div :class="rightBoxStyle">
           <el-col :span="24" v-if="leftShow">
              <el-row id="rightBox" ref="rightBox" :class="rightBoxStyle">
                   <img ref="rotate" :style="imgStyle" :src="url" lazy @mouseout="onmouseout" @mousedown="start" @mouseup="stop" @mousemove="move"/>
               </el-row>
               <div class="tool-bt-nav1">
                 <div class="el-image-viewer__actions__inner">
                   <i class="el-icon-zoom-out" @click="picSizeClick(1)" />
                   <i class="el-icon-zoom-in" @click="picSizeClick(2)" />
                   <!-- <i :class="fullScreen" @click="fullScreenClick" /> -->
                   <span style="color: #fff;font-weight:bold;font-size: 14px;">当前{{this.leftNum+1}}页</span>
                   <i class="el-icon-refresh-left" @click="leftRotating" />
                   <i class="el-icon-refresh-right" @click="rightRotating" />
                 </div>
               </div>
               <el-col :span="2" class="leftRightBt1" >
                 <div class="el-image-viewer__btn el-image-viewer__prev prevPromptF" @click="onAFile"><span class="promptOnF">上一份</span><i class="el-icon-d-arrow-left" /></div>
                 <div style="margin-left: 150px" class="el-image-viewer__btn el-image-viewer__prev prevPromptY" @click="OnThePicture"><span class="promptOnY">上一页</span><i class="el-icon-arrow-left" /></div>
               </el-col>
               <el-col :span="2" class="RightleftBt1">
                  <div style="margin-right: 150px;" class="el-image-viewer__btn el-image-viewer__next nextPicture" @click="nextPicture"> <span class="promptFNextY">下一页</span><i class="el-icon-arrow-right" /></div>
                  <div class="el-image-viewer__btn el-image-viewer__next nextpromptF" @click="underAFile"><span class="promptFNextF">下一份</span><i class="el-icon-d-arrow-right" /></div>
               </el-col>
           </el-col>
            <el-col :span="24" v-if="rightShow">
               <el-col id="print" ref="print" :span="3" style="position: relative;padding: 5px;height: calc(100vh - 170px);overflow: auto;">
                    <div ref="printdiv" style="height: 162px;position: relative;" @click="leftPicClick(index)" v-for="(item,index) in urlAll" :class="[urlAll[index].isActive?'pagingCol':'pagingColor']" >
                      <el-image :id="index" :key="index" :src="item.src" :style="leftImg" lazy>
                         <div slot="placeholder" class="image-slot">
                           <span class="dot"></span>
                         </div>
                      </el-image>
                    </div>
               </el-col>
               <div class="tool-bt-nav">
                 <div class="el-image-viewer__actions__inner">
                   <i class="el-icon-zoom-out" @click="picSizeClick(1)" />
                   <i class="el-icon-zoom-in" @click="picSizeClick(2)" />
                   <!-- <i :class="fullScreen" @click="fullScreenClick" /> -->
                    <span style="color: #fff;font-weight:bold;font-size: 14px;">当前{{this.leftNum+1}}页</span>
                   <i class="el-icon-refresh-left" @click="leftRotating" />
                   <i class="el-icon-refresh-right" @click="rightRotating" />
                 </div>
               </div>
               <el-col :span="21" ref="rightBrowse" id="rightBrowse" style="position: relative;height: calc(100vh - 170px);overflow: auto;">
                  <img ref="leftRotate" :src="rightSrc" :style="rightSrcStyle" @mouseout="onmouseout" @mousedown="start" @mouseup="stop" @mousemove="move" lazy />
                  <el-col :span="2" class="leftRightBt">
                    <div class="el-image-viewer__btn el-image-viewer__prev prevPromptF" @click="onAFile"><span class="promptOnF">上一份</span><i class="el-icon-d-arrow-left" /></div>
                    <div style="margin-left: 150px;" class="el-image-viewer__btn el-image-viewer__prev prevPromptY" @click="OnThePicture"><span class="promptOnY">上一页</span><i class="el-icon-arrow-left" /></div>
                  </el-col>
                  <el-col :span="2" class="RightleftBt">
                     <div style="margin-right: 150px;" class="el-image-viewer__btn el-image-viewer__next nextPicture" @click="nextPicture"> <span class="promptFNextY">下一页</span><i class="el-icon-arrow-right" /></div>
                     <div class="el-image-viewer__btn el-image-viewer__next nextpromptF" @click="underAFile"><span class="promptFNextF">下一份</span><i class="el-icon-d-arrow-right" /></div>
                  </el-col>
               </el-col>
            </el-col>
            </div>
          </el-col>
         <el-col :span="24">
           <div class="table-wait">
             <el-form :rules="rules" :inline="true"  :model="formElectronicFile">
              <el-form-item label="材料序号" label-width="80px">
                 <el-input style="width: 40px;" v-model="formElectronicFile.e01z104" :disabled="true"></el-input>
               </el-form-item>
               <el-form-item label="材料名称" prop="e01z111a" label-width="80px">
                 <el-input style="width: 150px;height: ;" v-model="formElectronicFile.e01z111a" :disabled="isDisabled"></el-input>
               </el-form-item>
               </el-form-item>
               <el-form-item label="材料日期" prop="e01z117Year" label-width="80px">
                 <el-input style="width: 80px;" v-model="formElectronicFile.e01z117Year" :disabled="isDisabled"></el-input>
               </el-form-item>
               <el-form-item label="" prop="e01z117Month">
                 <el-input style="width: 50px;" v-model="formElectronicFile.e01z117Month" :disabled="isDisabled"></el-input>
               </el-form-item>
               <el-form-item label="" prop="e01z117Day">
                 <el-input style="width: 50px;" v-model="formElectronicFile.e01z117Day" :disabled="isDisabled"></el-input>
               </el-form-item>
               <el-form-item label="页数" prop="e01z114" label-width="60px">
                 <el-input style="width: 60px;" v-model="formElectronicFile.e01z114" :disabled="isDisabled"></el-input>
               </el-form-item>
               <el-form-item label="备注" label-width="40px">
                 <el-input style="width: 100px;" v-model="formElectronicFile.e01z121" :disabled="isDisabled"></el-input>
               </el-form-item>
             </el-form>
             <el-dialog
               title="电子文件排序" style="height:100%;"
               :visible.sync="dialogVisible"
               ref="dialogVisible1"
               width="80%"
               height="80%"
               :show-close="false"
               :before-close="cancelSorting"
               >
               <div style="height: 100%;">
                 <el-col :span="24" style="height: 42px;background-color: #FFFFFF;" >
                   <div style="position: fixed;width: 78%;background-color: #FFFFFF;padding: 7px 10px;">
                     <el-button type="primary" size="mini" icon="el-icon-download" @click="MoveUp">上移</el-button>
                     <el-button type="primary" size="mini" icon="el-icon-download" @click="MoveDown">下移</el-button>
                   </div>
                 </el-col>
                 <div>
                   <div class="dialogPic" :class="[sortingList[index].isActive?'dialogPicStyleWhite':'dialogPicStyleRed']" :style="sortingW" v-for="(item,index) in sortingList" :key="index" >
                     <img :src="item.src" alt="" style="width: 100%;height: 100%;" @click="leftPicClick(index)" />
                   </div>
                 </div>
               </div>
                 <span slot="footer" class="dialog-footer">
                   <el-button @click="cancelSorting">取 消</el-button>
                   <el-button type="primary" @click="picdetermineUploud">确 定</el-button>
                 </span>
             </el-dialog>
           </div>
         </el-col>
       </el-col>
     </el-row>
   </div>
 </template>
 <script>
   import {downLoad,printParams,requestDownLoad} from '@/downLoad/filedownload'
   import {upDataParams,printImgPdfBy,queryImgInfoByE01z1Id,download,downloadFile,updateE01z1} from '@/api/usually/daily/materials'
   // 获取当前网站端口
   let local = window.location.host;
   // let local = '192.168.0.25:8081';
   let ip = local.split(':',local.length)[0];
   let port =  8081;
   const baseUrl = process.env.VUE_APP_BASE_API
   // let port =  "8081";
 export default {
   data() {
      var reg = /^[0-9]*$/;
      var yaer = (rule, value, callback) => {
             if (value === '') {
               callback(new Error(this.$message.error('请输入年份')))
             } else {
               if (!reg.test(this.formElectronicFile.e01z117Year)) {
                  callback(new Error(this.$message.error('年份必须是4位数字')))
               }
               if(this.formElectronicFile.e01z117Year.length != 4){
                  callback(new Error(this.$message.error('年份必须是4位数字')))
                  return
               }

             }
          };
      var Month = (rule, value, callback) => {
             if (value === '') {
               callback(new Error(this.$message.error('请输入月份')))
             } else {
               if (!reg.test(this.formElectronicFile.e01z117Month)) {
                  callback(new Error(this.$message.error('月份必须是2位数字')))
               }
                if(this.formElectronicFile.e01z117Month.length != 2){
                  callback(new Error(this.$message.error('月份必须是2位数字')))
                  return
               }

             }
          };
      var Day = (rule, value, callback) => {
             if (value === '') {
               callback(new Error(this.$message.error('请输入日期')))
             } else {
               if (!reg.test(this.formElectronicFile.e01z117Day)) {
                  callback(new Error(this.$message.error('日期必须是2位数字')))
               }
               if(this.formElectronicFile.e01z117Day.length != 2){
                  callback(new Error(this.$message.error('日期必须是2位数字')))
                  return
               }

             }
          };
      var filePage = (rule, value, callback) => {
             if (value === '') {
               callback(new Error(this.$message.error('请输入页数')))
             } else {
               if (!reg.test(this.formElectronicFile.e01z114)) {
                  callback(new Error(this.$message.error('页数必须是数字')))
                  return
               }else if(this.formElectronicFile.e01z114.length > 3){
                  callback(new Error(this.$message.error('页数必须小于3位数字')))
                  return
               }
             }
          };
     return {
       //
       isBrowse:false,
       rightBoxStyle:'rightBoxStyle1',
       isBut:false,
       updonwMks:false,
       uploadMks:false,
       pdfhidden:false,
       PdfSrc:'',
       rightSrcStyle:{},
       iframeWin: {},
       isLoaded: false,
       iframeSrc:'http://127.0.0.1:38610/index2.html',
       pdfUrl:'',
       fileBt:'编辑',
       browseBtnHidden:true,
       upBtnHidden:false,
       // 左侧电子文件属性
       formElectronicFile:{
         e01z111a:'',
       },
       formElectronic:[
         {
           name:'我是履历材料',
           date:'2020-03-09',
           type:'履历',
           page:1,
           note:'测试'
         },
         {
           name:'我是工资材料',
           date:'2020-03-10',
           type:'工资',
           page:1,
           note:'测试'
         },
         {
           name:'我是任免材料',
           date:'2020-03-1',
           type:'任免',
           page:1,
           note:'测试'
         },
       ],
       isDisabled:true,
       dialogVisible: false,
       // 当前 所点击的图片
       currentPage: 0,
       url: '',
       maskUrl: '',
       urlAll: [],
       imgStyle: {},
       PicStyle: {},
       drawTargetEle: {},
       img: [],
       // 旋转度数
       deg: 0,
       // 放大缩小
       scale: 1,
       // 全屏
       fullScreen: 'el-icon-full-screen',
       canDrag: false,
       // 图片 位子
       currentX: 0,
       currentY: 0,
       // 图片dom
       maskElement: '',
       Number: 0,
       initialList:[],
       // 当前页
       currentPages:0,
       // 当前显示数量
       value:1,
       // 分页长度
       initialLength:0,
       //右侧 主图显示
       rightShow: false,
       // 左侧 缩略图
       leftShow: true,
       // 右侧显示图片
       rightSrc:'',
       // 左侧缩略图 索引
       leftNum:0,
       // 记录左侧点击事件
       oldNum:0,
       // 单张显示
       clientWH:{
         width:0,
         height:0
       },
       picList:[],
       picNum:0,
       uploadNum:0,
       dialogPicList:[],
       startA:0,
       oloaded:0,
       // 十大类 的索引
       typeIndex:0,
       // 十大类下材料的索引
       materialIndex:0,
       // 特殊类型的索引
       specialIndex:0,
       //当前干部点击查看的材料ID
       cadresMaterialId:'',
       // 当前干部十大类
       cadresInformation:[],
       // 当前干部所有的材料
       datas:[],
       // 上传图片的名称 存放
       imgName:[],
       // 上传文件流
       imageFlow:[],
       deleteList:[],
       params:{},
       // 上传后台 返回的参数
       paramsId:'',
       //定时
       timer:'',
       num:0,
       userId:'',
       leftImg:{
         position: 'absolute',
         left: 0,
         right: 0,
         bottom: 0,
         top: 0,
         margin: 'auto'
       },
       sortingList:[],
       numDiv:0,
       sortingNum:0,
       sortingW:{},
       temporaryList:[],
       // 材料信息校验
       rules:{
          e01z111a:[
                { required: true, message: '请填写名称',trigger: 'blur' }
              ],
          e01z117Year: [
                { validator: yaer, trigger: 'blur' }
              ],
          e01z117Month: [
                { validator: Month, trigger: 'blur' }
              ],
          e01z117Day: [
                { validator: Day, trigger: 'blur' }
              ],
          e01z114: [
                { required: true,validator: filePage, trigger: 'blur' }
              ],
       },
       temp:'',
       modelText:'管理模式',
       imgSrc:'data:image/png;base64,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'
     }
   },
   mounted() {
      let  material = JSON.parse(sessionStorage.getItem('materialInformation'));
      this.iframeWin = this.$refs.iframe.contentWindow;
      this.cadresInformation = material.AllFile;//10大类数据
      this.cadresMaterialId =  material.fileAttribute.id;  // 当前点击id
      this.userId = material.id;//当前用户id
      this.clientWH.width = this.$refs.rightBox.$el.parentNode.clientWidth;
      this.clientWH.height = this.$refs.rightBox.$el.parentNode.clientHeight;
      this.currentMaterialPic();
      this.electronicFile(this.cadresMaterialId,true);
      window.onresize = () => {
        return (() => {
          this.dynamicPicSize();
        })()
      }
      var rightHealth = document.getElementById("app-wait");
      rightHealth.oncontextmenu = function (e) {
        window.event.returnValue=false;
      }
 //在外部vue的window上添加postMessage的监听，并且绑定处理函数handleMessage
      // window.addEventListener('message', this.actionH, false);
      window.addEventListener('message', event => {
        this.num++;
            const data = event.data;
            if( data.cmd === 'returnHeight' ){
                if( data.params === 'uploadSuccessful' ){
                  if(this.num==1){
                  if( data.code === "200" ){
                    this.uploadMks = false;
                    this.$message({message: '上传成功',type: 'success'});
                    this.electronicFile(this.datas[this.materialIndex].id,true)
                  }else if( data.code === "500" ){
                    this.uploadMks = false;
                    this.$message.error('上传失败');
                    this.electronicFile(this.datas[this.materialIndex].id,true)
                  }else if( data.code === "501" ){
                    this.uploadMks = false;
                    this.$message.error('上传参数错误');
                    this.electronicFile(this.datas[this.materialIndex].id,true)
                  }
                  }
                }else{
                  console.log(data)
                  // 扫描 和 导入 成功返回结果渲染
                  for( let i = 0; i < data.params.length; i++ ){
                    this.urlAll.push({
                     a01Id:this.userId,
                     isActive:true,
                     src:data.params[i].src,
                     index:'',
                     fileName:data.params[i].fileName,
                     e01z1Id:this.cadresMaterialId,
                     type:data.params[i].type,
                     dir:data.params[i].dir ,
                     paramsType:data.params[i].paramsType
                    })
                  }
                  this.url = [];
                  this.rightSrc = [];
                  this.urlAll[this.leftNum].isActive = false;
                  this.rightSrc = this.urlAll[this.leftNum].src;
                  this.dynamicPicSize(this.rightSrc,2);
                  this.dialogStyle()
                }
            }else if( data.cmd === 0 ){
              clearTimeout(this.temp);
              this.isBut = false;
            }
      });
   },
   methods: {
     //  给 iframe 传参
    sendMessage ( num ) {
        this.iframeWin.postMessage({
          cmd: 'getFormJson',
          params: this.params,
          type:num,
          ip:ip,
          port:port,
          local:local
        }, '*')
    },
     currentMaterialPic(){
       // 取当前干部下所有材料  push到datas 数组里 备用
       this.cadresInformation.map(item=>{
          if(item.id==4){
            item.children.map(chil=>{
              if(typeof chil.children!='undefined'){
                this.datas.push(...chil.children);
              }
            })
          } else if(item.id==9){
            item.children.map(chil=>{
              if(typeof chil.children!='undefined'){
                this.datas.push(...chil.children);
              }
            })
          }else{
            if(typeof item.children!='undefined'){
              this.datas.push(...item.children);
            }
          }
       })
        for( let i = 0; i < this.datas.length; i++ ){
           if( this.cadresMaterialId === this.datas[i].id ){
             this.materialIndex = i;
             this.materialName(this.datas[i])
           }
        }

     },
     materialName(data){
       this.formElectronicFile = {
         e01z111a:data.e01z111a,
         e01z114:data.e01z114,
         e01z104:data.e01z104,
         e01z117Day:data.e01z117Day,
         e01z117Month:data.e01z117Month,
         e01z117Year:data.e01z117Year,
         e01z121:data.e01z121,
         a01Id:data.a01Id,
         id:data.id
       }
     },
     // 后台接口 数据
     electronicFile(id,isType){
       this.urlAll = [];
       this.sortingList = []
       this.url = [];
       this.rightSrc = [];
       queryImgInfoByE01z1Id(id)
       .then(response => {
          if( response.code === 200 ){
            if( response.data.length === 0 ){
                this.$message({message: '当前材料暂无电子档案',type: 'warning'});
                if( this.rightShow === true ){
                  this.rightSrc = this.imgSrc
                  this.dynamicPicSize(this.imgSrc,3);
                }else{
                  this.url = this.imgSrc
                  this.dynamicPicSize(this.imgSrc,3);
                }
            }else{
                for (let i = 0; i < response.data.length; i++) {
                  this.urlAll.push({
                     isActive:true,
                     src:"/fileStorage/downImage?fileId="+response.data[i].fileSource+"&compress="+isType+"",
                     id:response.data[i].id,
                     type: 1,
                     dir:'',
                     fileSource:response.data[i].fileSource,
                     fileName:response.data[i].fileName,
                     index:response.data[i].fileIndex,
                     paramsType:'update'
                  })
                }
                this.urlAll[0].isActive = false;
                this.rightSrc = baseUrl + "/fileStorage/downImage?fileId="+this.urlAll[0].fileSource+"&compress=false";
                this.url = baseUrl + "/fileStorage/downImage?fileId="+this.urlAll[0].fileSource+"&compress=false";
                 if( this.rightShow === true ){
                   this.dynamicPicSize(this.rightSrc,2);
                   this.dialogStyle()
                 }else{
                   this.dynamicPicSize(this.url,1);
                 }
            }
          }
       })
     },
     // 编辑事件
     fileEditor(){
       if(this.fileBt === '编辑' ){
         this.fileBt = '保存';
         this.isDisabled = false;
       }else if( this.fileBt === '保存' ){
         let reg = /^[0-9]*$/
         if(!reg.test(this.formElectronicFile.e01z117Year) && this.formElectronicFile.e01z117Year.length != 4 ){
              this.$message.error('请填写正确的年份');
              return

         }
         if(!reg.test(this.formElectronicFile.e01z117Month) && this.formElectronicFile.e01z117Month.length != 2 ){
              this.$message.error('请填写正确的月份');
              return
         }
         if(!reg.test(this.formElectronicFile.e01z117Day) && this.formElectronicFile.e01z117Dayss.length != 2){
              this.$message.error('请填写正确的日期');
              return
         }
         if(!reg.test(this.formElectronicFile.e01z114)){
           this.$message.error('请填写正确的页数');
           return
         }
         updateE01z1(this.formElectronicFile)
         .then(data=>{
            if( data.code === 200 ){
              this.fileBt = '编辑';
              this.isDisabled = true;
              this.$message({showClose: true,message: '保存成功',type: 'success'});
            }else{
              this.$message.error('保存失败，请联系管理员。');
            }
         })
       }
     },
     // 动态改变图片大小
     dynamicPicSize(urlLoad,accordingType) {
       if( accordingType === 1 ){
            var img = new Image();
            img.src = urlLoad;
            img.onload = () => { // 图片加载成功后把地址给原来的img
                let a = img.width/this.value - this.clientWH.width/this.value;
                let b = img.height/this.value - this.clientWH.height/this.value;
                if(a > b) {
                  let width = this.clientWH.width/this.value;
                  let widthB= (this.clientWH.width/this.value)/(img.width/this.value);
                  let height=img.height/this.value*widthB;
                  this.imgStyle = {
                    width: width+'px',
                    height: height+'px',
                    margin: '0 auto',
                    display:'block',
                    cursor:'move'
                  }
                }else{
                  var height = (this.clientWH.height)/this.value;
                  let heightB= (this.clientWH.height)/this.value/(img.height/this.value);
                  let width=img.width/this.value*heightB;
                  this.imgStyle = {
                    width: width+'px',
                    height: height+'px',
                    margin: '0 auto',
                    display:'block',
                    cursor:'move'
                  }
                }
            }
       }else if( accordingType === 2 ){
          var img = new Image();
          img.src = urlLoad;
          img.onload = () => { // 图片加载成功后把地址给原来的img
              let a = img.width/this.value - this.clientWH.width/this.value;
              let b = img.height/this.value - this.clientWH.height/this.value;
              if(a > b) {
                let width = this.clientWH.width/this.value;
                let widthB= (this.clientWH.width/this.value)/(img.width/this.value);
                let height=img.height/this.value*widthB;
                this.rightSrcStyle = {
                  width: width+'px',
                  height: height+'px',
                  display:'block',
                  position: 'absolute',
                  left: 0,
                  right: 0,
                  bottom: 0,
                  top: 0,
                  margin: 'auto',
                  cursor:'move'
                }
              }else{
                let height = (this.clientWH.height)/this.value;
                let heightB= (this.clientWH.height)/this.value/(img.height/this.value);
                let width=img.width/this.value*heightB;
                this.rightSrcStyle = {
                  width: width+'px',
                  height: height+'px',
                  position: 'absolute',
                  left: 0,
                  right: 0,
                  bottom: 0,
                  top: 0,
                  margin: 'auto',
                  display:'block',
                  cursor:'move'
                }
              }
          }
       }else if( accordingType === 3 ){
             this.rightSrcStyle = {
               width: '430px',
               height: '300px',
               display:'block',
               position: 'absolute',
               left: 0,
               right: 0,
               bottom: 0,
               top: 0,
               margin: 'auto',
               cursor:'move'
             }
             this.imgStyle = {
               width: '430px',
               height: '300px',
               display:'block',
               position: 'absolute',
               left: 0,
               right: 0,
               bottom: 0,
               top: 0,
               margin: 'auto',
               cursor:'move'
             }
       }
     },
     dialogStyle(){
       if( this.numDiv < this.urlAll.length ){
            this.leftDiv(this.urlAll[this.numDiv].src)
       }else{
         return
       }
     },
     //  左侧缩略图
     leftDiv(url){
        this.numDiv++;
        var img = new Image();
        img.src = url;
        img.onload = () => {
          let a = img.width/110;
          let b = img.height/160;
          if( img.width < 110 && img.height < 160 ){
            this.leftImg = {
              width: img.width+'px',
              height: img.height+'px',
              position: 'absolute',
              margin: 'auto',
              left: 0,
              right: 0,
              top:0,
              bottom:0,
              display: 'block'
            }
            return
          }
          if(a > b) {
              let width = 110;
              let widthB= 110/img.width;
              let height=img.height*widthB;
              this.leftImg = {
                width: width+'px',
                // width:'100%',
                height: height+'px',
                position: 'absolute',
                margin: 'auto',
                left: 0,
                right: 0,
                top:0,
                bottom:0,
                display: 'block'
              }
          }else{
            console.log(2)
              let height = 160;
              let heightB= 160/img.height;
              let width=img.width*heightB;
              this.leftImg = {
                width: width+'px',
                height: height+'px',
                position: 'absolute',
                margin: 'auto',
                top: 0,
                bottom: 0,
                left: 0,
                right: 0,
                top:0,
                bottom:0,
                display: 'block'
              }
          }
        }
        this.dialogStyle()
     },
     // 浏览模式
     browseMode(e){
       this.rightShow =!this.rightShow;
       this.leftShow =!this.leftShow;
      if( this.rightShow === true ){
        this.modelText = '浏览模式'
        if( this.urlAll.length >= 1 ){
          this.upBtnHidden = true
          this.browseBtnHidden = false
          this.leftNum = this.currentPages;
          this.urlAll[this.oldNum].isActive = true;
          this.urlAll[this.leftNum].isActive = false;
          this.oldNum = this.leftNum;
          if( this.urlAll[this.leftNum].type === 1 ){
            this.rightSrc = "/fileStorage/downImage?fileId="+this.urlAll[this.leftNum].fileSource+"&compress=false"
          }else{
            this.rightSrc = this.urlAll[this.leftNum].src
          }
          this.currentPages = this.leftNum;
          this.dynamicPicSize(this.rightSrc,2);
          this.dialogStyle()

          return
        }else{
          this.url = []
          this.upBtnHidden = true
          this.browseBtnHidden = false
          this.rightSrc = this.imgSrc
          this.dynamicPicSize(this.rightSrc,3);
        }
      }
      if( this.leftShow === true ){
        this.upBtnHidden = false
        this.browseBtnHidden = true
        this.modelText = '管理模式'
        if( this.urlAll.length >= 1 ){
          if( this.urlAll[this.leftNum].type === 1 ){
            this.url = "/fileStorage/downImage?fileId="+this.urlAll[this.leftNum].fileSource+"&compress=false"
          }else{
            this.url = this.urlAll[this.leftNum].src
          }
          this.dynamicPicSize(this.url,1);
        }else{
          this.url = []
          this.url = this.imgSrc
          this.dynamicPicSize(this.url,3);
        }
        this.dynamicPicSize(this.url,3);
        return
      }
     },
     // 缩略图 下页
     nextPicture(){
       for( let i = 0; i < this.urlAll.length;i++ ){
         this.urlAll[i].isActive = true;
       }
        if(!this.leftShow){
          this.leftNum++;
          if( this.leftNum > this.urlAll.length-1 ){
              this.leftNum = 0
              this.underAFile();
              return
          }
          this.rightSrc = [];
          this.urlAll[this.leftNum].isActive = false;
          this.urlAll[this.oldNum].isActive = true;
          this.oldNum = this.leftNum;
          if (this.urlAll[this.leftNum].type === 1) {
            this.rightSrc ="/fileStorage/downImage?fileId="+this.urlAll[this.leftNum].fileSource+"&compress=false";
          }else{
            this.rightSrc = this.urlAll[this.leftNum].src;
          }
          this.scale = 1;
          this.deg = 0;
          let element = document.getElementById('print');
          if( element.scrollTop > this.$refs.print.$el.clientHeight ){
            element.scrollTop = this.leftNum*160
          }else{
            element.scrollTop = this.leftNum*160-(this.$refs.print.$el.clientHeight-180)
          }

          this.$refs.leftRotate.style.transform = `scale(${this.scale})`;
          this.$refs.leftRotate.style.transform = `rotate(${this.deg}deg)`;
        }else{
          this.currentPages++;
          this.deg = 0;
          this.scale = 1;
          this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`;
          this.$refs.rotate.style.transform = `scale(${this.scale})`;
          if( this.urlAll.length === 0 ){
            this.underAFile();
          }else if(  this.currentPages === this.urlAll.length ){
            this.currentPages = 0;
            this.underAFile()
          }else if( this.currentPages < this.urlAll.length ){
            this.url = [];
            if (this.urlAll[this.currentPages].type === 1) {
              this.url = "/fileStorage/downImage?fileId="+this.urlAll[this.currentPages].fileSource+"&compress=false";
            }else{
              this.url = this.urlAll[this.currentPages].src;
            }
            this.leftNum = this.currentPages;
          }
        }
     },
     // 左侧缩略图 图片点击事件
     leftPicClick(num){
      for( let i = 0; i < this.urlAll.length;i++ ){
        this.urlAll[i].isActive = true;
      }
      this.leftNum = num;
      this.sortingNum = num
      if( num === this.oldNum ){
        this.urlAll[num].isActive = false;
        return
      }
      let element = document.getElementById('print');
      element.scrollTop = this.leftNum*120
      this.rightSrc = [];
      this.urlAll[num].isActive = !this.urlAll[num].isActive;
      // this.urlAll[this.oldNum].isActive = true;
       if (this.urlAll[num].type === 1) {
         this.rightSrc = "/fileStorage/downImage?fileId="+this.urlAll[num].fileSource+"&compress=false";
       }else{
         this.rightSrc = this.urlAll[num].src;
       }
      this.oldNum = num;
      this.dynamicPicSize(this.rightSrc,2);
      this.currentPages = this.leftNum;
     },
     // 缩略图 上页
     OnThePicture(){
       for( let i = 0; i < this.urlAll.length;i++ ){
         this.urlAll[i].isActive = true;
       }
       if(!this.leftShow){
        this.leftNum--;
        if( this.leftNum < 0 ){
          this.leftNum = 0;
          this.onAFile();
          return
        }
        this.rightSrc = [];
        this.urlAll[this.leftNum].isActive = !this.urlAll[this.leftNum].isActive;
        this.urlAll[this.oldNum].isActive = true;
        if (this.urlAll[this.leftNum].type === 1) {
          this.rightSrc = "/fileStorage/downImage?fileId="+this.urlAll[this.leftNum].fileSource+"&compress=false";
        }else{
          this.rightSrc = this.urlAll[this.leftNum].src;
        }
        this.oldNum = this.leftNum;
        this.scale = 1;
        this.deg = 0;
        let element = document.getElementById('print');
        element.scrollTop = this.leftNum*160
        this.$refs.leftRotate.style.transform = `scale(${this.scale})`;
        this.$refs.leftRotate.style.transform = `rotate(${this.deg}deg)`;
       }else{
          this.currentPages--;
          this.deg = 0;
          this.scale = 1;
          this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`;
          this.$refs.rotate.style.transform = `scale(${this.scale})`;
          if( this.urlAll.length === 0 ){
            this.currentPages = 0;
            this.onAFile();
          }else if( this.currentPages < 0 ){
            this.currentPages = 0;
            this.onAFile();
          }else{
            for( let i = this.currentPages*(this.value*this.value); i < (this.currentPages+1)*(this.value*this.value); i++ ){
                this.url = [];
                if (this.urlAll[i].type === 1) {
                  this.url = "/fileStorage/downImage?fileId="+this.urlAll[this.currentPages].fileSource+"&compress=false";
                }else{
                  this.url = this.urlAll[this.currentPages].src;
                }
                // this.url = "/fileStorage/downImage?fileId="+this.urlAll[i].fileSource+"&compress=false";
                this.leftNum = this.currentPages;
            }
          }
       }
     },
     //上一份
     onAFile(){
       this.isDisabled = true;
       this.fileBt = '编辑';
      for( let i = 0; i < this.urlAll.length;i++ ){
        this.urlAll[i].isActive = true;
      }
      if(!this.leftShow){
          // 十大类下的材料索引
          this.materialIndex--;
          this.numDiv = 0;
          if( this.materialIndex < 0 ){
             this.materialIndex = 0;
             this.$message({message: '这已经是第一份材料',type: 'warning'});
          }else{
            this.currentPages = 0;
            this.leftNum = 0;
            this.electronicFile(this.datas[this.materialIndex].id,true);
            this.materialName(this.datas[this.materialIndex]);
          }
          this.scale = 1;
          this.deg = 0;
          this.$refs.leftRotate.style.transform = `scale(${this.scale})`;
          this.$refs.leftRotate.style.transform = `rotate(${this.deg}deg)`;
       }else{
          // 十大类下的材料索引
         this.materialIndex--;
         this.deg = 0;
         this.scale = 1;
         this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`;
         this.$refs.rotate.style.transform = `scale(${this.scale})`;
         if( this.materialIndex < 0 ){
           this.materialIndex = 0;
           this.$message({message: '这已经是第一份材料',type: 'warning'});
         }else{
           this.currentPages = 0;
           this.leftNum = 0;
           this.electronicFile(this.datas[this.materialIndex].id,false);
           this.materialName(this.datas[this.materialIndex]);
         }
       }
     },
     //下一份
     underAFile(){
       this.isDisabled = true;
       this.fileBt = '编辑';
       for( let i = 0; i < this.urlAll.length;i++ ){
         this.urlAll[i].isActive = true;
       }
       if(!this.leftShow){
          this.materialIndex++;
          this.numDiv = 0;
          if( this.materialIndex === this.datas.length ){
             this.materialIndex = this.datas.length-1;
             this.$message({message: '这已经是最后一份材料',type: 'warning'});
          }else{
            this.currentPages = 0;
            this.leftNum = 0;
            this.electronicFile(this.datas[this.materialIndex].id,true);
            this.materialName(this.datas[this.materialIndex]);
          }
          this.scale = 1;
          this.deg = 0;
          this.$refs.leftRotate.style.transform = `scale(${this.scale})`;
          this.$refs.leftRotate.style.transform = `rotate(${this.deg}deg)`;
       }else{
         // 十大类下的材料索引
         this.materialIndex++;
         this.deg = 0;
         this.scale = 1;
         this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`;
         this.$refs.rotate.style.transform = `scale(${this.scale})`;
         if( this.materialIndex === this.datas.length ){
           this.materialIndex = this.datas.length-1;
           this.$message({message: '这已经是最后一份材料',type: 'warning'});
         }else{
           this.currentPages = 0;
           this.leftNum = 0;
           this.electronicFile(this.datas[this.materialIndex].id,false);
           this.materialName(this.datas[this.materialIndex]);
         }
       }
     },
     // 左旋转
     leftRotating() {
       if(!this.leftShow){
         this.deg = this.deg - 90;
         if (this.deg === 0) {
           this.deg = 360;
         }
         this.$refs.leftRotate.style.transform = `rotate(${this.deg}deg)`;
       }else{
         this.deg = this.deg - 90;
         if (this.deg === 0) {
           this.deg = 360;
         }
         this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`;
       }
     },
     // 右旋转
     rightRotating() {
       if(!this.leftShow){
         this.deg += 90;
         if (this.deg >= 360) {
           this.deg = 0;
         }
         this.$refs.leftRotate.style.transform = `rotate(${this.deg}deg)`;
       }else{
         this.deg += 90;
         if (this.deg >= 360) {
           this.deg = 0;
         }
         this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`;
       }
     },
     // 全屏
     fullScreenClick() {
       if(!this.leftShow){
          if (this.fullScreen === 'el-icon-full-screen') {
            this.fullScreen = 'el-icon-c-scale-to-original';
            this.scale = 3;
            this.$refs.leftRotate.style.transform = `scale(${this.scale})`;
          } else {
            this.fullScreen = 'el-icon-full-screen';
            this.scale = 1;
            this.$refs.leftRotate.style.transform = `scale(${this.scale})`;
          }
       }else{
         if (this.fullScreen === 'el-icon-full-screen') {
           this.fullScreen = 'el-icon-c-scale-to-original';
           this.scale = 3;
           this.$refs.rotate.style.transform = `scale(${this.scale})`;
         } else {
           this.fullScreen = 'el-icon-full-screen';
           this.scale = 1;
           this.$refs.rotate.style.transform = `scale(${this.scale})`;
         }
       }

     },
     // 放大缩小
     // transform:scale
     picSizeClick(type) {
       if(!this.leftShow){
        if (type === 1) {
           this.scale = this.scale - 0.1;
           if (this.scale <= 0.1) {
             this.scale = 0.1;
           }
           this.$refs.leftRotate.style.transform = `scale(${this.scale})`;
        } else {
           this.scale += 0.1;
           if (this.scale >= 3) {
             this.scale = 3;
           }
           this.$refs.leftRotate.style.transform = `scale(${this.scale})`;
         }
       }else{
         if (type === 1) {
           this.scale = this.scale - 0.1;
           if (this.scale <= 0.1) {
             this.scale = 0.1;
           }
           this.$refs.rotate.style.transform = `scale(${this.scale})`;
         } else {
           this.scale += 0.1;
           if (this.scale >= 3) {
             this.scale = 3;
           }
           this.$refs.rotate.style.transform = `scale(${this.scale})`;
         }
       }
     },
     // 鼠标按下
     start(e) {
       // 鼠标左键点击
       if(!this.leftShow){
         if (e.button === 0) {
           e.preventDefault();
           this.canDrag = true;
           this.maskElement = e.target; // 获取目标元素
           // 算出鼠标相对元素的位置
           this.imgOffsetLeft = this.maskElement.offsetLeft;
           this.imgOffsetTop = this.maskElement.offsetTop;
           this.maskElement.style.marginLeft = this.maskElement.offsetLeft;
           this.maskElement.style.marginTop = this.maskElement.offsetTop;
           this.currentX = e.pageX - this.maskElement.offsetLeft;
           this.currentY = e.pageY - this.maskElement.offsetTop;
         }
       }else{
         if (e.button === 0) {
           e.preventDefault();
           this.canDrag = true;
           this.maskElement = e.target; // 获取目标元素
           // 算出鼠标相对元素的位置
           this.imgOffsetLeft = this.maskElement.offsetLeft;
           this.imgOffsetTop = this.maskElement.offsetTop;
           this.maskElement.style.marginLeft = this.maskElement.offsetLeft;
           this.maskElement.style.marginTop = this.maskElement.offsetTop;
           this.currentX = e.pageX - this.maskElement.offsetLeft;
           this.currentY = e.pageY - this.maskElement.offsetTop;
         }
       }
     },
     // 划出
     onmouseout() {
       if(!this.leftShow){
         document.onmousewheel = null;
         document.onmouseover = null;
       }else{
         document.onmousewheel = null;
         document.onmouseover = null;
       }
     },
     // 鼠标弹起
     stop(e) {
       if(!this.leftShow){
         this.canDrag = false;
       }else{
         this.canDrag = false;
       }
     },
     //  删除事件
     move() {
       if(!this.leftShow){
         if (this.canDrag === true) {
           document.onmousemove = (e) => { // 鼠标按下并移动的事件
             // 用鼠标的位置减去鼠标相对元素的位置，得到元素的位置
             const left = e.pageX - this.currentX;
             const top = e.pageY - this.currentY;
             // 移动当前元素
             this.maskElement.style.margin = 0;
             this.maskElement.style.left = left + 'px';
             this.maskElement.style.top = top + 'px';
             this.maskElement.style.position = 'absolute';
           }
           document.onmouseup = (e) => {
             document.onmousemove = null;
             document.onmouseup = null;
           }
         }
       }else{
         if (this.canDrag === true) {
           document.onmousemove = (e) => { // 鼠标按下并移动的事件
             // 用鼠标的位置减去鼠标相对元素的位置，得到元素的位置
             const left = e.pageX - this.currentX;
             const top = e.pageY - this.currentY;
             // 移动当前元素
             this.maskElement.style.margin = 0;
             this.maskElement.style.left = left + 'px';
             this.maskElement.style.top = top + 'px';
             this.maskElement.style.position = 'absolute';
           }
           document.onmouseup = (e) => {
             document.onmousemove = null;
             document.onmouseup = null;
           }
         }
       }
     },
     //退出
     exitClick(){
        this.pdfhidden = false;
        this.PdfSrc = '';
     },
     //打印
     PrintRow(num){
       if( this.urlAll.length === 0 ){
         this.$message({message: '没有电子文件可打印',type: 'warning'});
         return
       }
       if( num === '1' ){
         printImgPdfBy(this.urlAll[this.leftNum].id)
        .then(response => {
            if( response.code === 200 ){
              let serverUrl = '/pdfjs/web/viewer.html'
              let pdfUrl =  "/common/printpdf?id="+response.data // 调取接口返回文件流
              let buttonData="0"  //0打印功能    下载功能
              let Url = pdfUrl+"&button="+buttonData;
              window.open(serverUrl+"?file="+encodeURIComponent(Url))
            }else{
                this.$message.error('打印失败,请联系管理员');
            }
        })
       }else{
         printImgPdfBy(this.datas[this.materialIndex].id)
         .then(response => {
              if( response.code === 200 ){
                let serverUrl = '/pdfjs/web/viewer.html'
                let pdfUrl =  "/common/printpdf?id="+response.data // 调取接口返回文件流
                let buttonData="0"  //0打印功能    下载功能
                let Url = pdfUrl+"&button="+buttonData;
                window.open(serverUrl+"?file="+encodeURIComponent(Url))
              }else{
                  this.$message.error('打印失败,请联系管理员');
              }
         })
       }
     },
     // 下载
     downloadPic(num){
       if( this.urlAll.length === 0 ){
         this.$message({message: '没有电子文件可下载',type: 'warning'});
         return
       }
       this.updonwMks = true;
       let _this = this;
       if( num === '1' ){
         let callback = function call(response){
           if( response.code === 200 ){
             return true;
           }else{
             return false;
           }
         }
         _this.updonwMks = false;
         requestDownLoad(downloadFile({imgId:this.urlAll[this.leftNum].id}),callback,this);
         // downloadFile({imgId:this.urlAll[this.leftNum].id})
         // .then(response => {
         //    if( response.code === 200 ){
         //      this.updonwMks = false;
         //      let str = '/common/download';
         //      let params = {id:response.data,fileName:response.fileName};
         //      downLoad(str,params)
         //    }else{
         //        this.$message.error('下载失败,请联系管理员');
         //    }
         // })
       }else{
          // downloadFile({e01z1Id:this.datas[this.materialIndex].id})
          // .then(response => {
          //   this.updonwMks = false;
          //    if( response.code === 200 ){
          //      let str = '/common/download';
          //      let params = {id:response.data,fileName:response.fileName};
          //      downLoad(str,params)
          //    }else{
          //        this.$message.error('下载失败,请联系管理员');
          //    }
          // })
         let callback = function call(response){
           if( response.code === 200 ){
             return true;
           }else{
             return false;
           }
         }
         _this.updonwMks = false;
         requestDownLoad(downloadFile({e01z1Id:this.datas[this.materialIndex].id}),callback,this);
          // downloadFile({e01z1Id:this.datas[this.materialIndex].id})
          // .then(response => {
          //   this.updonwMks = false;
          //    if( response.code === 200 ){
          //      let str = '/common/download';
          //      let params = {id:response.data,fileName:response.fileName};
          //      downLoad(str,params)
          //    }else{
          //        this.$message.error('下载失败,请联系管理员');
          //    }
          // })
        }
     },
     //全部删除
     deleteAll(){
        if( this.urlAll.length === 1 ){
          this.deleteList.push(this.urlAll.splice(this.leftNum,1)[0]);
          this.rightSrc = []
          this.url = []
          for( let i = 0; i < this.deleteList.length; i++ ){
            this.deleteList[i].paramsType = 'delete';
          }
          this.$message({
            message: '没有电子文件',
            type: 'success'
          });
        }else if( this.urlAll.length > 1 ){
          if( this.leftNum == 0 ){
            this.deleteList.push(this.urlAll.splice(this.leftNum,1)[0]);
            this.rightSrc = "/fileStorage/downImage?fileId="+this.urlAll[this.leftNum].fileSource+"&compress=false";
            this.urlAll[this.leftNum].isActive = false;
            for( let i = 0; i < this.deleteList.length; i++ ){
              this.deleteList[i].paramsType = 'delete';
            }
            this.$message({
              message: '当前图片已删除，请点击上传进行保存',
              type: 'success'
            });
          }else{
            this.deleteList.push(this.urlAll.splice(this.leftNum,1)[0]);
            this.rightSrc = "/fileStorage/downImage?fileId="+this.urlAll[this.leftNum-1].fileSource+"&compress=false";
            this.urlAll[this.leftNum-1].isActive = false;
            this.leftNum = this.leftNum - 1
            for( let i = 0; i < this.deleteList.length; i++ ){
              this.deleteList[i].paramsType = 'delete';
            }
            this.$message({
              message: '当前图片已删除，请点击上传进行保存',
              type: 'success'
            });
          }

        }
     },
     //上传
      upload(){
        this.num=0;
        this.uploadMks = true;
        if( this.urlAll.length != Number(this.formElectronicFile.e01z114) ){
          this.uploadMks = false;
           this.$message({
             message: '上传的页数和实际电子文件的页数不一致',
             type: 'warning'
           });
          return
        }
        for( let i = 0; i < this.deleteList.length; i++ ){
          if( this.deleteList[i].type === 1 ){
            this.urlAll.push(this.deleteList[i]);
          }
        }
        if( this.urlAll.length === 0 ){
            return
        }
        if( this.fileBt === '保存' ){
          this.uploadMks = false;
          this.$message({ message: '请先保存再进行上传',type: 'warning'});
            return
        }
       // 重组数据 向后台发送
       for( let i = 0; i < this.urlAll.length; i++ ){
         this.urlAll[i].index = i
         this.urlAll[i].a01Id = this.userId
         this.urlAll[i].e01z1Id = this.datas[this.materialIndex].id
       }
          let formData = new FormData();
          let params = {
            ip:ip,
            port:port,
            info:this.urlAll
          }
            formData.append("params",JSON.stringify(params));
            upDataParams(params)
            .then(response => {
              if( response.code === 200 ){
                this.params = response.data;
                this.sendMessage(3);
              }else{
                this.$message.error('上传失败,请联系管理员');
              }
            })
     },
     // 排序
     TheSorting(){
       this.dialogVisible = true;
       let windowW = Number(window.outerWidth)*0.8
       let sortingWindow = windowW*8-20
       let rowsNum = Math.floor(sortingWindow/250)
       let sortingImgW = (sortingWindow)/rowsNum
       this.sortingW = {
         width:sortingImgW-10+'px',
         height:sortingImgW+90+'px',
         margin:'5px',
         float: 'left'
       }
       this.sortingList = this.urlAll.concat();
       for( let i = 0; i < this.sortingList.length; i++ ){
         this.sortingList[i].isActive = true
       }
       if( this.sortingList.length === 0 ){
         return
       }
       this.sortingNum = 0
       this.sortingList[0].isActive = false
     },
     // 上移
     MoveUp(){
       if( this.sortingNum === 0 ){
         this.sortingNum = 0
         return
       }
       let urlSrcAll = this.sortingList.splice(this.sortingNum,1);
       this.sortingList.splice(this.sortingNum-1,0,urlSrcAll[0]);
       this.sortingNum = this.sortingNum - 1;
     },
     //下移
     MoveDown(){
       if( this.sortingNum === this.sortingList.length-1 ){
         this.sortingNum = this.sortingList.length-1
         return
       }
      let urlSrcAll = this.sortingList.splice(this.sortingNum,1);
      this.sortingList.splice(this.sortingNum+1,0,urlSrcAll[0]);
      this.sortingNum = this.sortingNum + 1;
     },
     // 图片排序取消
     cancelSorting(){
        this.dialogVisible = false;
        this.sortingList = this.urlAll.concat();
     },
     // 图片排序后 确定 上传
     picdetermineUploud(){
        this.$confirm('电子文件排序成功，请点击上传按钮进行上传', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.dialogVisible = false;
            this.urlAll = this.sortingList.concat();
        }).catch(() => {
            this.dialogVisible = false;
            this.sortingList = this.urlAll.concat();
            this.$message({
              type: 'info',
              message: '已取消排序'
            });
          });
     },
   }
 }
 </script>
 <style lang="scss" scoped>
   /deep/.el-input__inner {
       height: 28px;
       line-height: 28px;
   }
 .table-wait {
   width: 100%;
   padding-top: 3px;
   height: 58px;
   background-color: #f0f2f5;
 }
 .page {
   float: right;
   padding: 10px;
 }
 .btn {
   padding: 3px 5px;
   background-color: #f0f2f5;
 }
 // .el-dropdown {
 //     margin-right: 8px;
 // }
 .top-btn {
   margin: 5px;
   padding-bottom: 10px;
   position: relative;
   cursor:pointer;
 }
 .inputFile{
   width: 120px;
   height: 34px;
   opacity: 0;
   position: absolute;
   left: 0;
   top: 0;
   cursor:pointer;
 }
 .upload-demo{
   display: inline-block;
 }
  .Pic-mask{
     position: absolute;
     top: 0;
     left: 0;
     right: 0;
     bottom: 0;
     margin: auto;
     width: 100%;
     height: 100%;
     background-color: rgba(0, 0, 0, 0.65);
     z-index: 11;
   }
   .tool-bt-nav1{
     position: absolute;
     width: 260px;
     bottom: 9%;
     background-color: #6062662e;
     height: 40px;
     left: 0;
     right: 0;
     margin: auto;
     border-radius: 50px;
     color: #FFFFFF;
     z-index: 999;
   }
   .tool-bt-nav{
     position: absolute;
     width: 260px;
     bottom: 9%;
     background-color: #6062662e;
     height: 40px;
     left: 10%;
     right: 0;
     margin: auto;
     border-radius: 50px;
     color: #FFFFFF;
     z-index: 999;
   }
   .leftRightBt{
     position: fixed;
     height: 44px;
     bottom: 44%;
     // top: 0;
     // margin-left: 80px;
     width: 200px;
     // margin: auto;
     color: #FFFFFF;
   }
   .RightleftBt{
     position: fixed;
     height: 44px;
     bottom: 44%;
     // top: 0;
     right: 0;
     width: 200px;
     // margin: auto;
     color: #FFFFFF;
   }
   .leftRightBt1{
     position: absolute;
     height: 44px;
     bottom: 45%;
     // top: 0;
     // margin-left: 150px;
     width: 200px;
     // margin: auto;
     color: #FFFFFF;
   }
   .RightleftBt1{
     position: absolute;
     height: 44px;
     bottom: 45%;
     // top: 0;
     right: 0;
     width: 200px;
     // margin: auto;
     color: #FFFFFF;
   }
   .el-image-viewer__actions__inner i{
     cursor: pointer;
   }
   .el-image-viewer__close{
     color: #FFFFFF;
   }
   .el-image-viewer__close[data-v-c218e280]{
     z-index: 999;
   }
   .pic-mask-style{
     position: absolute;
     top: 0;
     left: 0;
     bottom: 0;
     right: 0;
     margin: auto;
     z-index: 22;
   }
   .pic-mask-style:hover{
     cursor:move
   }
   .pagingColor{
     border: 2px solid #FF0000;
    }
   .pagingCol{
     border: 2px solid rgb(240, 242, 245);
   }
   .dialogPicStyleWhite{
     border: 5px solid #FFFFFF;
    }
   .dialogPicStyleRed{
     border: 5px solid #FF0000;
   }
   /deep/.el-dialog__body{
     height: calc(70vh);
     overflow: auto;
     padding: 0;
   }
   .pdfViewer{
     width: 100%;
     height: 100%;
     position: absolute;
     left: 0;
     right: 0;
     top: 0;
     bottom: 0;
     background: #000;
     z-index: 9999;
   }
   .iframeViewer{
     width: 80%;
     height: 80%;
     position: absolute;
     left: 0;
     right: 0;
     top: 0;
     bottom: 0;
     margin: auto;
     background-color: #fff;
   }
   .pdfBth{
     width: 100%;
     height: 40px;
     text-align: center;
     line-height: 40px;
     background-color: #fff;
   }
   .uploadMks{
     position: absolute;
     width: 100%;
     height: 100%;
     z-index: 9999;
    }
    .promptOnY{
      display: block;
      width: 100px;
      font-size: 12px;
      position: absolute;
      margin: -35px 0 0 0;
      text-align: right;
      display: none;
    }
    .promptOnF{
      display: block;
      width: 100px;
      font-size: 12px;
      position: absolute;
      margin: -35px 0 0 0;
      display: none;
    }
    .el-image-viewer__prev{
      background-color: #9E9E9E;
    }
    .el-image-viewer__next{
      background-color: #9E9E9E;
    }
    .promptFNextY{
      display: block;
      width: 100px;
      font-size: 12px;
      position: absolute;
      margin: -35px 0 0 0;
      display: none;
    }
    .promptFNextF{
      display: block;
      width: 100px;
      font-size: 12px;
      position: absolute;
      margin: -35px 0 0 0;
      text-align: right;
      display: none;
    }
    .nextpromptF:hover .promptFNextF{
      display: block;
    }
    .nextPicture:hover .promptFNextY{
      display: block;
    }
    .prevPromptY:hover .promptOnY{
      display: block;
    }
    .prevPromptF:hover .promptOnF{
      display: block;
    }
    /deep/.el-dialog__header{
      background: rgba(204,204,204,0.18);
    }
    /deep/.el-dialog__footer{
      background: rgba(204,204,204,0.18);
    }
    .rightBoxStyle{
      width: 100%;
      height: calc(100vh - 170px);
      overflow: auto;
      background: #606266;
    }
    .rightBoxStyle1{
      width: 100%;
      height: calc(100vh - 125px);
      overflow: auto;
      background: #606266;
    }
 </style>

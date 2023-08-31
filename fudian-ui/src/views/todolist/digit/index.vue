<template>
  <div class="app-wait" ref="maskWidth">
    <el-row>
      <el-col :span="6" style="height: calc(100vh - 84px);">
        <div class="uploadMks"
         v-if="uploadMks"
         v-loading="true"
         element-loading-text="正在上传电子档案，请耐心等待"
         element-loading-spinner="el-icon-loading"
         element-loading-background="rgba(0, 0, 0, 0.8)">
        </div>
        <div class="table-wait">
          <el-table size="mini" border ref="tableRefs" :data="tableData" style="width: 100%;"  :height="tableHeight" @selection-change="handleSelectionChange" @row-dblclick="dblclickEditor" @row-click="handlerClickRow">
            <el-table-column type="selection" width="45" align="center" />
            <el-table-column type="index" label="序号" width="55" />
            <el-table-column prop="a0101" :show-overflow-tooltip="true" label="姓名" width="80" />
            <el-table-column prop="e01z111a" :show-overflow-tooltip="true" label="材料名称" width="100" />
            <el-table-column prop="e01z114" label="页数" style="width: 50%">
              <template slot-scope="scope">
                <span v-if="scope.row.initial" >{{scope.row.e01z114}}</span>
                <el-input v-focus ref="input" @blur="onInputBlur(scope.row)" class="input" v-if="scope.row.editor" v-model.number="scope.row.e01z114" placeholder="请输入页数"></el-input>
              </template>
            </el-table-column>
<!--            <el-table-column fixed="right" label="操作" width="100">-->
<!--              <template slot-scope="scope">-->
<!--                <el-button type="text" size="small" @click="moveUp(scope.row)">上移</el-button>-->
<!--                <el-button type="text" size="small" @click="moveDown(scope.row)">下移</el-button>-->
<!--              </template>-->
<!--            </el-table-column>-->
          </el-table>
        </div>
      </el-col>
      <el-col :span="18" style="height: calc(100vh - 86px);">
        <!-- 功能按钮 -->
        <div class="btn">
         <el-button :disabled="isBrowse" type="primary" size="mini" class="top-btn" @click="sendMessage(1)">
           扫描
           <i class="el-icon-printer" />
         </el-button>
          <el-button :disabled="isBrowse" type="primary" size="mini" class="top-btn" @click="sendMessage(2)">
            导入图片
            <i class="el-icon-folder-add" />
            <!-- <input ref="clearFile" type="file" class="inputFile" multiple="multiplt" @change="importClick($event)" /> -->
          </el-button>
          <el-button type="primary" size="mini" class="top-btn" @click="MoveUp" v-if="isSXmove">
            移至上一份
            <i class="el-icon-caret-top" />
          </el-button>
          <el-button type="primary" size="mini" class="top-btn" @click="MoveDown" v-if="isSXmove">
            移至下一份
            <i class="el-icon-caret-bottom" />
          </el-button>
<!--          <el-button type="primary" size="mini" class="top-btn" @click="editTable">-->
<!--            编辑-->
<!--            <i class="el-icon-edit" />-->
<!--          </el-button>-->
          <el-button type="primary" size="mini" class="top-btn" @click="deleteDiv">
            删除
            <i class="el-icon-delete" />
          </el-button>
           <el-button type="primary" size="mini" class="top-btn" @click="TheSorting">
             排序
            <i class="el-icon-s-grid" />
            </el-button>
<!--          <el-button type="primary" size="mini" class="top-btn" @click="electronicFileAll">-->
<!--            还原-->
<!--            <i class="el-icon-s-grid" />-->
<!--          </el-button>-->


<!--          <el-button type="primary" size="mini" class="top-btn" @click="classification">-->
<!--            分档-->
<!--            <i class="el-icon-s-grid" />-->
<!--          </el-button>-->
          <el-button :disabled="isBrowse" type="primary" size="mini" class="top-btn" @click="submitUpload">
            上传
            <i class="el-icon-upload" />
          </el-button>
          <div style="float: right;margin-right: 10px;line-height: 42px">应扫页数/已扫页数：{{TotalPage}}/{{currentPage}}</div>
        </div>
        <el-col :span="24">
            <!-- @load="iframeLoad" -->
            <iframe :src="iframeSrc" height="600" width="500" style="margin: 50px;" hidden="hidden" ref="iframe"></iframe>
        </el-col>
        <el-row ref="rightBox" style="height: calc(100vh - 146px);overflow: auto;padding: 10px">
          <el-col v-for="(item, index) in url" :key="index" ref="imgDiv" :draggable="allowHandle" :class="[url[index].isActive?'pagingCol':'pagingColor']" :style="picSize">
<!--            <div style="cursor:pointer;position: absolute;right: 5px;top: 0;" @click="deleteDiv(index)"><i class="el-icon-circle-close" /></div>-->
            <el-image :id="index" ref="img" :style="imgStyle" :src="item.src" lazy @dblclick="previewDblclick" @click="leftPicClick(index,item)">
              <div slot="placeholder" class="image-slot">
                加载中
                <span class="dot">...</span>
              </div>
            </el-image>
          </el-col>
          <div v-if="maskShow" ref="maskShow"  class="Pic-mask">
            <div><img ref="rotate" :style="PicStyle" :src="maskUrl" @mouseover="onmouseover" @mouseout="onmouseout" @mousedown="start" @mouseup="stop" @mousemove="move" /></div>
            <div class="tool-bt-nav">
              <div class="el-image-viewer__actions__inner">
                <i class="el-icon-zoom-out" @click="picSizeClick(1)" />
                <i class="el-icon-zoom-in" @click="picSizeClick(2)" />
                <i class="el-icon-arrow-left" @click="turnPage(2)" />
<!--                <i :class="fullScreen" @click="fullScreenClick" />-->
                <i class="el-icon-arrow-right" @click="turnPage(1)" />
                <i class="el-icon-refresh-left" @click="leftRotating" />
                <i class="el-icon-refresh-right" @click="rightRotating" />
              </div>
            </div>
            <div class="el-image-viewer__btn el-image-viewer__close"><i class="el-icon-circle-close" @click="maskShow = false" /></div>
            <div class="el-image-viewer__btn el-image-viewer__prev" @click="turnPage(2)"><i class="el-icon-arrow-left" /></div>
            <div class="el-image-viewer__btn el-image-viewer__next" @click="turnPage(1)"><i class="el-icon-arrow-right" /></div>
          </div>
        </el-row>
        <el-dialog
          title="电子文件排序" style="height:100%;z-index:9999"
          :visible.sync="dialogVisible"
          :close-on-click-modal="false"
          :append-to-body="true"
          ref="dialogVisible1"
          width="80%"
          height="80%"
          :show-close="false"
          :before-close="cancelSorting"
        >
          <div style="height: 100%;">
            <el-col :span="24" style="height: 42px;background-color: #FFFFFF;" >
              <div style="position: fixed;width: 78%;background-color: #FFFFFF;padding: 7px 10px;">
                <el-button type="primary" size="mini" icon="el-icon-download" @click="sortingMoveUp">上移</el-button>
                <el-button type="primary" size="mini" icon="el-icon-download" @click="sortingMoveDown">下移</el-button>
              </div>
            </el-col>
            <div>
              <div class="dialogPic" :class="[sortingList[index].isActive?'dialogPicStyleWhite':'dialogPicStyleRed']" :style="sortingW" v-for="(item,index) in sortingList" :key="index" >
                <img :src="item.src" alt="" style="width: 100%;height: 100%;" @click="sortingPicClick(index)" />
              </div>
            </div>
          </div>
          <span slot="footer" class="dialog-footer">
                   <el-button @click="cancelSorting">取 消</el-button>
                   <el-button type="primary" @click="picdetermineUploud">确 定</el-button>
                 </span>
        </el-dialog>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  let local = window.location.host;
  let reg = /^[0-9]*$/;
  // let local = '192.168.0.21:8083'
  let ip = local.split(':',local.length)[0];
  let port =  window.location.port;
  const baseUrl = process.env.VUE_APP_BASE_API;

  // let port =  '8083';
  import {upDataParams,updateStatus} from '@/api/usually/daily/materials'
  import {queryE01z1sByTaskId,updateE01z1114ByE01z1Id} from '@/api/digital/scattered'
export default {
  data() {
    return {
      isBrowse:true,
      iframeSrc:'http://127.0.0.1:38610/index2.html',
      // 进度条
      // 弹窗遮罩
      maskShow: false,
      allowHandle: true,
      url: [],
      maskUrl: '',
      urlAll: [],
      picSize: {},
      imgStyle: {},
      PicStyle: {},
      leftNum:0,
      tableHeight:'calc( 100vh - 142px )px',
      // 记录左侧点击事件
      oldNum:0,
      tableData: [],
      value: 4,
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
      pages: 0,
      upNumber:0,
      divW:0,
      diwH:0,
      params:'',
      num:0,
      uploadMks:false,
      leftTaleNum:0,
      imageNum:0,
      checkbox:'',
      isInput:'',
      checkboxdata:'',
      insertNum:0,
      isSXmove:false,
      TotalPage:0,
      currentPage:0,
      sortingList:[],
      sortingW:{},
      dialogVisible: false,
      sortingNum:0,
      currentClickNum:0,
      sortingLocation:0,
      startLocation:0,
      upParam:[]

    };
  },
  mounted() {
    this.divW = this.$refs.rightBox.$el.clientWidth;
    this.divH = this.$refs.rightBox.$el.clientHeight;
    window.onresize = () => {
      return (() => {
        for( let i= 0; i < this.url.length; i++ ){
          this.dynamicPicSize(this.url[i].src);
        }
      })();
    };
    this.$nextTick(() => {
      this.tableHeight = (document.body.clientHeight - 84 )+ "px"
    })
    window.onresize = () => {
      return (() => {
        this.tableHeight = (document.body.clientHeight - 84 )+ "px"
      })();
    };
    //在外部vue的window上添加postMessage的监听，并且绑定处理函数handleMessage
         window.addEventListener('message', event => {
           this.num++;
           // 根据上面制定的结构来解析iframe内部发回来的数据
           const data = event.data
           if( data.cmd === 'returnHeight' ){
             if( data.params === 'uploadSuccessful' ){
               if(this.num==1){
                   if( data.code === "200" ){
                     this.uploadMks = false;
                     this.$message({message: '上传成功',type: 'success'});
                     this.isUpLoad(data)
                   }else if( data.code === "500" ){
                     this.isUpLoad(data)
                     this.uploadMks = false;
                     this.$message.error('上传失败');
                   }else if( data.code === "501" ){
                     this.uploadMks = false;
                     this.$message.error('上传参数错误');
                   }
               }
             }else{
               if( this.insertNum === 0 ){
                 for( let i = 0; i < data.params.length; i++ ){
                   this.tableData[0].img.push({
                     isActive:true,
                     src:data.params[i].src,
                     index:'',
                     fileName:data.params[i].fileName,
                     e01z1Id:'',
                     type:data.params[i].type,
                     dir:data.params[i].dir,
                     paramsType:data.params[i].paramsType
                   })
                   this.url.push({
                     isActive:true,
                     src:data.params[i].src,
                     index:'',
                     fileName:data.params[i].fileName,
                     e01z1Id:'',
                     type:data.params[i].type,
                     dir:data.params[i].dir,
                     paramsType:data.params[i].paramsType
                   })
                   this.dynamicPicSize(this.url[i].src)
                 }
                 for( let i = 0 ; i < this.tableData[0].img.length; i++ ){
                   this.tableData[0].img[i].index = i
                 }
                 for( let i = 0; i < this.url.length; i++ ){
                   this.url[i].index = i
                 }
                 this.url[0].isActive = false;
                 this.imageNum = this.url[0].index
                 this.leftNum = 0;
                 this.currentPage = this.tableData[0].img.length
                 let  TotalPage = 0;
                 for ( let i = 1; i < this.tableData.length-1; i++ ){
                      TotalPage = TotalPage + this.tableData[i].e01z114
                 }
                 this.TotalPage = TotalPage
               }else{
                 for( let i = 0; i < data.params.length; i++ ){
                   this.tableData[0].img.splice(this.insertNum,0,{
                     isActive:true,
                     src:data.params[i].src,
                     index:'',
                     fileName:data.params[i].fileName,
                     e01z1Id:'',
                     type:data.params[i].type,
                     dir:data.params[i].dir,
                     paramsType:data.params[i].paramsType
                   })
                   this.url.push({
                     isActive:true,
                     src:data.params[i].src,
                     index:'',
                     fileName:data.params[i].fileName,
                     e01z1Id:'',
                     type:data.params[i].type,
                     dir:data.params[i].dir,
                     paramsType:data.params[i].paramsType
                   })
                   this.dynamicPicSize(this.url[i].src)
                 }
                 for( let i = 0 ; i < this.tableData[0].img.length; i++ ){
                   this.tableData[0].img[i].index = i
                 }
                  if( this.leftTaleNum === 0 ){
                    for( let i = 0 ; i < this.url.length; i++ ){
                      this.url[i].index = i
                    }
                  }
                 if( this.tableData[0].img.length > 0 ){
                   this.tableData[this.leftTaleNum].e01z114 = this.url.length
                 }
                 this.url[0].isActive = false;
                 this.imageNum = this.url[0].index
                 this.currentPage = this.tableData[0].img.length
                 let  TotalPage = 0;
                 for ( let i = 1; i < this.tableData.length-1; i++ ){
                   TotalPage = TotalPage + this.tableData[i].e01z114
                 }
                 this.TotalPage = TotalPage
               }

             }
           }else if( data.cmd === 0 ){
             clearTimeout(this.temp);
             this.isBrowse = false;
           }
         });
          let _this = this;
          setTimeout(()=>{  //设置延迟执行
            _this.temp = setTimeout(()=>{
              _this.$message.error("请在'控件下载'中下载扫描插件并默认安装，且保持插件稳定运行");
              _this.isBrowse = true;
            },1000);
            _this.sendMessage(10)
          },700);
         this.iframeWin = this.$refs.iframe.contentWindow;
         this.leftTable()
  },
  directives: {
    // 注册一个局部的自定义指令 v-focus
    focus: {
      // 指令的定义
      inserted: function (el) {
        // 聚焦元素
        el.querySelector('input').focus()
      }
    }
  },
  methods: {
    // 扫描
    sendMessage ( num ) {
      if( this.leftTaleNum === this.tableData.length-1 ){
        this.$message({
          message: "其他材料不能进行此操作",
          type: 'warning'
        });
        return
      }
      // 处理 点击当前材料目录进行扫描或者是导入 用插入的方式在当前目录插入
      // 计算出在 this.tableData[0].img 那个元素后边插入
      if( this.leftTaleNum === 0 ){
        this.insertNum = 0
      }else{
        this.insertNum = this.tableData[this.leftTaleNum].e01z114
      }
      for( let i = 1; i < this.tableData[this.leftTaleNum].index+1; i++ ){
        this.insertNum = this.insertNum + this.tableData[i].e01z114
      }
      // if( this.url.length < this.tableData[val.index+1].e01z114 ){
      this.insertNum = this.insertNum - this.tableData[this.leftTaleNum].e01z114 + this.url.length
      // }
        // 外部vue向iframe内部传数据
        this.iframeWin.postMessage({
          cmd: 'getFormJson',
          params: this.params,
          type:num,
          ip:ip,
          port:port,
          local:local,
          prefix:baseUrl
        }, '*')
    },
    handleMessage (event) {
      // 根据上面制定的结构来解析 iframe内部发回来的数据
      const data = event.data
      switch (data.cmd) {
        case 'returnFormJson':
        // 业务逻辑
        break
        case 'returnHeight':
        // 业务逻辑
        break
      }
    },
    // 左侧表格数据接口
    leftTable(){
      queryE01z1sByTaskId({taksId:this.$route.params.taskId,status:'0'})
      .then(response => {
        if( response.code === 200 ){
          if( response.data.length > 0 ){
            this.tableData = response.data
            for( let i = 0; i < this.tableData.length; i++ ){
              this.TotalPage = this.TotalPage + this.tableData[i].e01z114
            }
            for( let i = 0; i < this.tableData.length; i++ ){
              this.tableData[i].index = i;
              this.tableData[i].editor = false;
              this.tableData[i].initial = true;

            }
            this.tableData.unshift({
              a0101:"",
              id:"all",
              e01z111a:"全部任务",
              e01z114:'',
              img:[]
            })
            this.tableData.push({
              a0101:"",
              id:"other",
              e01z111a:"其他任务",
              e01z114:'无',
              img:[]
            })
          }else{
            this.tableData = []
          }
        }else{
          this.$message.error('数据获取异常,请联系管理员');
        }
      })
    },
    // 动态改变图片大小
    dynamicPicSize(urlLoad) {
      let img = new Image();
      img.src = urlLoad;
      let rowsNum = Math.floor(this.divW/250)
      let sortingImgW = this.divW/rowsNum
      this.picSize = {
        width: sortingImgW+45 + 'px',
        height: sortingImgW + 'px',
        margin: '5px',
        position: 'relative'
      };
      img.onload = () => {
          let a = img.width - sortingImgW;
          let b = img.height - sortingImgW;
          if(a > b) {
            let width = sortingImgW;
            let widthB= sortingImgW/img.width;
            let height = img.height*widthB;
            this.imgStyle = {
              width: width + 'px',
              height: height-10 + 'px',
              position: 'absolute',
              left: 0,
              bottom: 0,
              right: 0,
              top: 0,
              margin: 'auto',
              display:'block'
            }
          }else{
            var height = sortingImgW;
            let heightB= sortingImgW/img.height;
            let width=img.width*heightB;
            this.imgStyle = {
              width: width + 'px',
              height: height-10 + 'px',
              position: 'absolute',
              left: 0,
              bottom: 0,
              right: 0,
              top: 0,
              margin: 'auto',
              display:'block'
            }
          }
      }

    },
    // 上传 点击事件
    submitUpload() {
          if( this.currentPage === this.TotalPage ){
            let pageNum = 0;
            let pageNumS  = 0;
            //  给左侧grid 材料目录 进行 分档
            for( let i = 1; i < this.tableData.length-1; i++ ){
                pageNum = pageNum + this.tableData[i].e01z114;
                pageNumS = pageNum - this.tableData[i].e01z114;
                this.tableData[i].img = []
                for ( let j = pageNumS; j < pageNum; j++ ){
                  this.tableData[i].img.push({
                      isActive:true,
                      src:this.tableData[0].img[j].src,
                      index:j,
                      a01Id:this.tableData[i].a01Id,
                      fileName:this.tableData[0].img[j].fileName,
                      e01z1Id:this.tableData[i].id,
                      type:this.tableData[0].img[j].type,
                      dir:this.tableData[0].img[j].dir,
                      paramsType:this.tableData[0].img[j].paramsType
                  })
                }
            }
            //  如果上传失败  用this.upParam数组中的数据  去进行对比回显
            this.upParam = [];
            for( let i = 1; i < this.tableData.length-1; i++ ){
              this.upParam.push(this.tableData[i])
            }
            for( let i = 0; i < this.upParam.length; i++ ){
              if(this.upParam[i].e01z114 > this.upParam[i].img.length){
                this.$message({
                  message: "第"+(i+1)+"条档案目录应扫页数和已扫页数不正确",
                  type: 'warning'
                });
                return
              }else if(this.upParam[i].e01z114 < this.upParam[i].img.length){
                this.$message({
                  message: "第"+(i+1)+"条档案目录应扫页数和已扫页数不正确",
                  type: 'warning'
                });
                return
              }
            }
            // 最终向后台传的数据upParams
            let upParams = []
            for( let i = 0 ; i < this.upParam.length; i++ ){
              for ( let j = 0; j < this.upParam[i].img.length; j++ ){
                this.upParam[i].img[j].index = j
                upParams.push(this.upParam[i].img[j])
              }
            }
            this.UploadInterface(upParams);
        }else{
            this.$message({
                    message: "应扫页数和已扫页数不对，请核对后再进行上传",
                    type: 'warning'
                  });
          }
    },
    // 上传 接口请求
    UploadInterface(data){
      this.uploadMks = true;
      this.num = 0;
      this.upNumber++
      let formData = new FormData();
      let params = {
        ip:ip,
        port:port,
        info:data
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
    // 上传成功或上传失败进行的处理
    isUpLoad(data){
      let isSuccessful = []
      if( data.code === '200' ){
        for ( let i = 0; i < this.upParam.length; i++ ){
          isSuccessful.push({
            id:this.upParam[i].id,
            taskId:this.$route.params.taskId
          })
        }
        updateStatus({params:JSON.stringify(isSuccessful)})
        .then(data=>{
          if( data.code === 200 ){
            this.url = []
            this.tableData = []
          }else{
            this.$message.error('上传失败,请联系管理员');
          }
        })
      }else if( data.code === '500' ){
        let array = []
        for( let i=0;i<data.info.length;i++ ){
          array.push(data.info[i].e01z1Id)
        }
        let unique= arr =>{
          let newA=[];
          arr.forEach(key => {
            if( newA.indexOf(key)<0 ){ //遍历newA是否存在key，如果存在key会大于0就跳过push的那一步
              newA.push(key);
            }
          });
          return newA;
        }
        let arrayData = unique(array)
        let paramArray = JSON.parse(JSON.stringify(this.upParam))
        let paramsArray = []
        console.log(arrayData)
        console.log(paramArray)
        for ( let i=0;i<paramArray.length;i++ ){
          if( paramArray[i].id === arrayData[i] ){
            paramsArray.push(paramArray[i])
          }
        }
        console.log(paramsArray)
        this.tableData.splice(1,this.tableData.length-2)
        this.tableData[0].img = []
        for( let i = 0; i < paramsArray.length; i++ ){
          this.tableData.splice(i+1,0,paramsArray[i])
          for ( let j = 0; j < paramsArray[i].img.length; j++ ){
            this.tableData[0].img.push(paramsArray[i].img[j])
          }
        }
        this.url = []
        for( let i = 0; i < this.tableData[1].img.length; i++ ){
          this.url.push(this.tableData[1].img[i])
        }
        console.log(this.tableData)
      }
    },
    //排序弹窗
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
      this.sortingList = this.tableData[this.leftTaleNum].img.concat();
      for( let i = 0; i < this.sortingList.length; i++ ){
        this.sortingList[i].isActive = true
      }
      if( this.sortingList.length === 0 ){
        return
      }
      this.sortingNum = 0;
      this.sortingList[0].isActive = false;
      // 排序窗口 所操做的图片
      if( this.leftTaleNum === 0 ){
        this.sortingLocation = 0
      }else{
        this.sortingLocation = this.tableData[this.leftTaleNum].e01z114
      }
      for( let i = 1; i < this.tableData[this.leftTaleNum].index+1; i++ ){
          this.sortingLocation = this.sortingLocation + this.tableData[i].e01z114
      }
      if( this.tableData[this.leftTaleNum].e01z114 > this.url.length ){
        this.sortingLocation = this.sortingLocation - this.tableData[this.leftTaleNum].e01z114 + this.url.length
        this.startLocation = this.sortingLocation - this.url.length
      }else{
        if( this.leftTaleNum === 0 ){
          this.startLocation = this.sortingLocation
        }else{
          this.startLocation = this.sortingLocation - this.url.length
        }
      }
    },
    //排序上移
    sortingMoveUp(){
      if( this.sortingNum === 0 ){
        this.sortingNum = 0
        return
      }
      let urlSrcAll = this.sortingList.splice(this.sortingNum,1);
      this.sortingList.splice(this.sortingNum-1,0,urlSrcAll[0]);
      this.sortingNum = this.sortingNum - 1;
    },
    //排序下移
    sortingMoveDown(){
      if( this.sortingNum === this.sortingList.length-1 ){
        this.sortingNum = this.sortingList.length-1
        return
      }
      let urlSrcAll = this.sortingList.splice(this.sortingNum,1);
      this.sortingList.splice(this.sortingNum+1,0,urlSrcAll[0]);
      this.sortingNum = this.sortingNum + 1;
    },
    //排序图片点击事件
    sortingPicClick(num){
      for( let i = 0; i < this.sortingList.length;i++ ){
        this.sortingList[i].isActive = true;
      }
      this.sortingNum = num;
      this.sortingList[num].isActive = false;
    },
    //排序取消
    cancelSorting(){
      this.dialogVisible = false;
    },
    //排序确定
    picdetermineUploud(){
      this.tableData[0].img.splice(this.startLocation,this.sortingList.length)
      for( let j = 0; j < this.sortingList.length; j++ ){
        this.tableData[0].img.splice(this.startLocation+j,0,this.sortingList[j]);
        this.tableData[0].img[this.startLocation+j].index = this.startLocation+j
      }
      this.url = this.sortingList
      for ( let i = 0; i < this.tableData[0].img.length; i++ ){
        this.tableData[0].img[i].index = i
      }
      this.dialogVisible = false;
    },
    // 上移
    MoveUp(){
      if( this.url.length === 0 ){
        this.$message({
          message: "没有图片",
          type: 'warning'
        });
        return
      }
      if( this.leftTaleNum === 0 ){
        this.$message({
          message: "第一行不进行此操作",
          type: 'warning'
        });
        return
      }
      if( this.leftTaleNum === 1 ){
        this.$message({
          message: "当前行不进行此操作",
          type: 'warning'
        });
        return
      }
      if( this.leftTaleNum === this.tableData.length-1 ){
        this.$message({
          message: "最后一行不进行此操作",
          type: 'warning'
        });
        return
      }
      if( this.oldNum === 0 ){
        this.tableData[this.leftTaleNum-1].e01z114 = this.tableData[this.leftTaleNum-1].e01z114+1
        this.url.splice(0, 1);
        this.TotalPage = this.TotalPage+1
        let pageNum = 0

        for( let i = 1; i <= this.leftTaleNum; i++ ){
          pageNum = pageNum+this.tableData[i].e01z114
        }
        let pageSize = pageNum-this.tableData[this.leftTaleNum].e01z114
        this.tableData[this.leftTaleNum].img = []
        this.url = []
        for( let j = pageSize; j < pageNum; j++ ){
          this.tableData[this.leftTaleNum].img.push(this.tableData[0].img[j])
          this.url.push(this.tableData[0].img[j])
        }
        for( let i = 0; i <= this.url.length; i++ ){
          this.url[i].isActive = true
        }
        this.url[0].isActive = false
      }else{
        this.$message({
          message: "请选择当前条目的第一张进行上移",
          type: 'warning'
        });
      }

    },
    //下移
    MoveDown(){
      if( this.url.length === 0 ){
        this.$message({
          message: "没有图片",
          type: 'warning'
        });
        return
      }
      if( this.leftTaleNum === 0 ){
        this.$message({
          message: "第一行不进行此操作",
          type: 'warning'
        });
        return
      }
      if( this.leftTaleNum === this.tableData.length-2 ){
        this.$message({
          message: "当前行不进行此操作",
          type: 'warning'
        });
        return
      }
      if( this.leftTaleNum === this.tableData.length-1 ){
        this.$message({
          message: "最后一行不进行此操作",
          type: 'warning'
        });
        return
      }
      if( this.tableData[this.leftTaleNum].e01z114 > this.url.length ){
        this.$message({
          message: "应扫页数和实际已扫页数不对称不能进行次操作",
          type: 'warning'
        });
        return
      }
      if( this.oldNum === this.url.length-1 ){
        this.tableData[this.leftTaleNum].e01z114 = this.tableData[this.leftTaleNum].e01z114-1
        this.url.splice(this.url.length-1, 1);
        this.TotalPage = this.TotalPage-1
      }else{
        this.$message({
          message: "请选择当前条目的最后一张进行下移",
          type: 'warning'
        });
      }
    },
    // 删除1
    deleteDiv() {
      if( this.url.length === 0 ){
        this.$message({
          message: "没有电子文件不可以删除",
          type: 'warning'
        });
        return
      }
      if( this.leftTaleNum === 0 ){
        this.currentPage = this.currentPage - 1
      }else {
        this.currentPage = this.currentPage - 1
        this.TotalPage = this.TotalPage - 1
      }
      for ( let i = 0; i < this.tableData[this.leftTaleNum].img.length; i++ ){
        if( this.tableData[this.leftTaleNum].img[i].index === this.imageNum ){
          this.url.splice(i, 1);
          this.tableData[this.leftTaleNum].img.splice(i, 1);
          for( let j = 0; j < this.tableData[0].img.length; j++ ){
            if( this.tableData[0].img[j].index === this.imageNum ){
              this.tableData[0].img.splice(j, 1);
            }
          }
          this.imageNum = i;
          this.tableData[this.leftTaleNum].e01z114 = this.tableData[this.leftTaleNum].e01z114-1
        }
      }
      for( let i = 0; i < this.tableData[this.leftTaleNum].img.length; i++ ){
        this.tableData[this.leftTaleNum].img[i].isActive = true;
      }
      for( let i = 0; i < this.url.length; i++ ){
        this.url[i].isActive = true;
      }
      if( this.url.length === 0 ){
        return;
      }
      if( this.imageNum === this.url.length ){
        this.imageNum = this.url.length-1
      }
      this.url[this.imageNum].isActive = false
      this.imageNum = this.url[this.imageNum].index
      // if( this.imageNum === this.url.length ){
      //   this.imageNum = this.url.length-1
      // }
      // if( this.url.length === 0 ){
      //   this.imageNum = 0
      // }else{
      //   this.url[this.imageNum].isActive = false
      //   this.imageNum = this.url[this.imageNum].index
      // }
    },
    // 复选框
    handleSelectionChange(val){
      this.checkbox = val.map(item => item.id)
      this.checkboxdata = val
    },
    // 编辑框失去光标后保存
    onInputBlur(data){
      console.log(data)
      // updateE01z1114ByE01z1Id
      if( data.e01z114 === '' ){
        this.$message({
          type: 'warning',
          message: '页数不能为空'
        })
        return
      }
      if(!reg.test(data.e01z114)){
        this.$message({
          type: 'warning',
          message: '页数只能是数字'
        })
        return
      }
      updateE01z1114ByE01z1Id({
        id:data.id,
        e01z114:data.e01z114,
        taskId:this.$route.params.taskId
      })
      .then(data=>{
        if( data.code === 200 ){
          console.log(data)
        }
      })

      // this.checkboxdata[0].initial = true
      // this.checkboxdata[0].editor = false
      // console.log(this.leftTaleNum)
      // data.initial = true;
      // data.editor = false;
      this.tableData[this.leftTaleNum].initial = true
      this.tableData[this.leftTaleNum].editor = false
    },
    // 触发 回车离开输入框
    onchange(data){
      data.initial = true;
      data.editor = false;
        this.$confirm('调整应扫页数后, 当前材料目录页数页会进行调整是否保存?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '保存成功'
          });
        }).catch(() => {
          data.e01z114 = this.isInput
          this.$message({
            type: 'info',
            message: '已取消'
          });
        });

    },
    //双击编辑
    dblclickEditor(val){
      this.isInput = JSON.parse(JSON.stringify(val.e01z114))
      if( val.e01z111a === '其他任务' ){
        this.$message({
          type: 'warning',
          message: '其他任务不能进行编辑'
        })
        return
      }
      if( val.e01z111a === '全部任务' ){
        this.$message({
          type: 'warning',
          message: '全部任务不能进行编辑'
        })
        return
      }
      this.leftTaleNum = val.index+1
      val.editor = true;
      val.initial = false;
    },
    //  grid 点击第一行做的处理
    handlerGridAll(val){
      if( val.id === 'all' ){
        this.leftTaleNum = 0
        if( val.img.length > 0 ){
          // this.url = cloneObj(val.img)
          this.url = JSON.parse(JSON.stringify(val.img))
          for( let i = 0; i < this.url.length; i++ ){
            this.url[i].isActive = true;
            this.tableData[0].img[i].index  = i
          }
          this.url[0].isActive = false;
          this.imageNum = this.url[0].index;
          return;
        }else {
          return;
        }
      }
    },
    // geid 点击最后一行做的处理
    handlerGridOther(val){
      if ( val.id === 'other' ){
        this.leftTaleNum = this.tableData.length-1
        this.url = []
        let currentImageNum = 0
        for( let i = 1; i < this.tableData.length-1; i++ ){
          currentImageNum = currentImageNum+this.tableData[i].e01z114
        }
        if( currentImageNum < this.tableData[0].img.length ){
          for ( let i = currentImageNum; i < this.tableData[0].img.length; i++ ){
            this.tableData[this.leftTaleNum].img.push(this.tableData[0].img[i])
            this.url.push(this.tableData[0].img[i])
          }
          for( let i = 0; i < this.url.length; i++ ){
            this.url[i].index = i
            this.url[i].isActive = true;
          }
          this.url[0].isActive = false;
          this.imageNum = this.url[0].index;
          return;
        }else {
          return;
        }
      }
    },
    // grid 点击材料目录做的处理
    handlerMaterial(val){
      let currentNum = val.index+1;
      let currentImageNum = 0;
      let startPage = 0;
      for ( let i = 0; i < currentNum; i++ ){
        if ( i === 0 ){
          startPage = startPage+0
        }else{
          startPage = startPage+this.tableData[i].e01z114
        }
        currentImageNum = currentImageNum+this.tableData[i+1].e01z114
      }
      if( currentImageNum > this.tableData[0].img.length ){
        this.leftTaleNum = val.index+1
        currentImageNum = this.tableData[0].img.length
      }
      if( startPage >= this.tableData[0].img.length){
        this.url = []
        return;
      }
      this.url = []
      this.tableData[currentNum].img = []
      this.leftTaleNum = val.index+1
      for ( let i = startPage; i < currentImageNum; i++ ){
        this.tableData[currentNum].img.push(this.tableData[0].img[i])
        this.url.push(this.tableData[0].img[i])
      }
      for ( let i = 0; i < this.url.length; i++ ){
        this.url[i].isActive =  true;
      }
      if( this.url.length === 0 ){
        return;
      }
      this.url[0].isActive = false;
      this.imageNum = this.url[0].index;
    },
    // grid 行点击事件
    handlerClickRow(val) {
      this.$refs.tableRefs.clearSelection()
      if (this.checkbox.length === 0) {
        this.$refs.tableRefs.toggleRowSelection(val)
      }
      this.oldNum = 0
      if ( this.tableData[0].img.length === 0 ){
        return;
      }
      if( val.id === 'all' ){
        // 判断 是否点击的全部档案信息  （table第一行）
        this.insertNum = 0 ;
        this.leftTaleNum = 0
        this.isSXmove = false;
        this.handlerGridAll(val)
        return;
      }else if ( val.id === 'other' ){
        this.insertNum = 0 ;
        this.leftTaleNum = 0
        this.isSXmove = false;
        // 判断 是否点击的其他档案信息  （table最后一行）
        this.handlerGridOther(val)
        return;
      }else{
        // 判断 是否点击的是材料目录条目
        this.leftTaleNum = val.index+1
        this.isSXmove = true;
        this.handlerMaterial(val)
        return;
      }
    },
    // 缩略图 图片点击事件
    leftPicClick(num,data){
       for( let i = 0; i < this.url.length;i++ ){
         this.url[i].isActive = true;
       }
       if( num === this.oldNum ){
         this.url[num].isActive = false;
         return
       }
       this.url[num].isActive = !this.url[num].isActive;
       this.oldNum = num;
       console.log(data.index)
       this.currentClickNum = num;
       this.imageNum = data.index;
    },
    // 双击查看
    previewDblclick(val) {
      let divW = this.$refs.maskWidth.clientWidth;
      let divH = this.$refs.maskWidth.clientHeight;
      let a = val.path[0].naturalWidth - divW;
      let b = val.path[0].naturalHeight - divH;
      if(a > b) {
        let width = divW;
        let widthB= divW/val.path[0].naturalWidth;
        let height= val.path[0].naturalHeight*widthB
        this.PicStyle = {
          width: width+'px',
          height: height-67+'px',
          display:'block',
          position: 'absolute',
          top: 0,
          right: 0,
          bottom: 0,
          left: 0,
          cursor: 'move',
          margin: 'auto'
        }
      }else{
        let height = divH;
        let heightB= divH/val.path[0].naturalHeight;
        let width=val.path[0].naturalWidth*heightB;
        this.PicStyle = {
          width: width+'px',
          height: divH-67+'px',
          display:'block',
          position: 'absolute',
          top: 0,
          right: 0,
          bottom: 0,
          left: 0,
          cursor: 'move',
          margin: 'auto'
        }
      }
      // this.currentClickNum = num
      this.currentClickNum = this.oldNum
      console.log(this.currentClickNum)
      this.maskUrl = val.path[0].src;
      this.maskShow = true;
    },
    // 左旋转
    leftRotating() {
      this.deg = this.deg - 90;
      if (this.deg === 0) {
        this.deg = 360;
      }
      this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`;
    },
    // 右旋转
    rightRotating() {
      this.deg += 90;
      if (this.deg >= 360) {
        this.deg = 0;
      }
      this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`;
    },
    // 翻页
    turnPage(type) {
      if (type === 1) {
        // 下一张
        this.currentClickNum++;
        if (this.currentClickNum >= this.url.length) {
          this.currentClickNum = 0;
        }
        this.deg = 0;
        this.scale = 1;
        this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`;
        this.$refs.rotate.style.transform = `scale(${this.scale})`;
        this.maskUrl = this.url[this.currentClickNum].src;
      } else {
        // 上一张
        this.currentClickNum--;
        if (this.currentClickNum < 0) {
          this.currentClickNum = this.url.length - 1;
        }
        this.deg = 0;
        this.scale = 1;
        this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`;
        this.$refs.rotate.style.transform = `scale(${this.scale})`;
        this.maskUrl = this.url[this.currentClickNum].src;
      }
    },
    // 全屏
    fullScreenClick() {
      if (this.fullScreen === 'el-icon-full-screen') {
        this.fullScreen = 'el-icon-c-scale-to-original';
        this.scale = 2;
        this.$refs.rotate.style.transform = `scale(${this.scale})`;
      } else {
        this.fullScreen = 'el-icon-full-screen';
        this.scale = 1;
        this.$refs.rotate.style.transform = `scale(${this.scale})`;
      }
    },
    // 放大缩小
    // transform:scale
    picSizeClick(type) {
      if (type === 1) {
        this.scale = this.scale - 0.1;
        if (this.scale <= 0.1) {
          this.scale = 0.1;
        }
        this.$refs.rotate.style.transform = `scale(${this.scale})`;
      } else {
        this.scale += 0.1;
        if (this.scale >= 2) {
          this.scale = 2;
        }
        this.$refs.rotate.style.transform = `scale(${this.scale})`;
      }
    },
    // 划过
    onmouseover() {},
    // 鼠标按下
    start(e) {
      // 鼠标左键点击
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
      // // recordImg
      // document.onmousewheel = e => {
      //   if (e.wheelDelta === 120) {
      //     this.scale += 0.1;
      //     if (this.scale >= 2) {
      //       this.scale = 2;
      //     }
      //     this.$refs.rotate.style.transform = `scale(${this.scale})`;
      //   } else {
      //     this.scale = this.scale - 0.1;
      //     if (this.scale <= 0.1) {
      //       this.scale = 0.1;
      //     }
      //     this.$refs.rotate.style.transform = `scale(${this.scale})`;
      //   }
      // };
    },
    // 划出
    onmouseout() {
      document.onmousewheel = null;
      document.onmouseover = null;
    },
    // 鼠标弹起
    stop(e) {
      this.canDrag = false;
    },
    //  删除事件
    move() {
      if (this.canDrag === true) {
        document.onmousemove = e => {
          // 鼠标按下并移动的事件
          // 用鼠标的位置减去鼠标相对元素的位置，得到元素的位置
          const left = e.pageX - this.currentX;
          const top = e.pageY - this.currentY;
          // 移动当前元素
          this.maskElement.style.margin = 0;
          this.maskElement.style.left = left + 'px';
          this.maskElement.style.top = top + 'px';
          this.maskElement.style.position = 'absolute';
        };
        document.onmouseup = e => {
          document.onmousemove = null;
          document.onmouseup = null;
        };
      }
    }
  }
};
</script>

<style lang="scss" scoped>
  .dialogPicStyleWhite{
    border: 5px solid #FFFFFF;
  }
  .dialogPicStyleRed{
    border: 5px solid #FF0000;
  }
.app-wait{
  background: #f8f8f9;
}
.table-wait {
  width: 100%;
  min-height: calc(100vh - 86px);
  border: 1px solid #e2e3e6;
}
.page {
  float: right;
  padding: 10px;
}
  /deep/.el-dialog__body{
    height: calc(70vh);
    overflow: auto;
    padding: 0;
  }
.btn {
  padding: 10px;
  background: #fff;
}
.top-btn {
  margin: 5px;
  padding-bottom: 10px;
  position: relative;
  cursor: pointer;
}
.inputFile {
  width: 120px;
  height: 34px;
  opacity: 0;
  position: absolute;
  left: 0;
  top: 0;
  cursor: pointer;
}
.upload-demo {
  display: inline-block;
}
.Pic-mask {
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
.tool-bt-nav {
  position: absolute;
  width: 260px;
  bottom: 100px;
  background-color: #606266;
  height: 40px;
  left: 0;
  right: 0;
  border-radius: 50px;
  margin: auto;
  padding: 0 10px;
  color: #ffffff;
  z-index: 999;
}
.el-image-viewer__actions__inner i {
  cursor: pointer;
}
.el-image-viewer__close {
  color: #ffffff;
}
.el-image-viewer__close[data-v-c218e280] {
  z-index: 999;
}
.pic-mask-style {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  margin: auto;
  z-index: 22;

}
.pic-mask-style:hover {
  cursor: move;
}
.pagingColor{
  border: 2px solid #FF0000;
}
.pagingCol{
  border: 2px solid #000000;
}
.uploadMks{
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 9999;
}
</style>

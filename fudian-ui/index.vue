<template>
  <div class="app-wait">
    <el-row>
      <el-col :span="8" style="height: calc(100vh - 86px);">
        <div class="table-wait">
          <el-table border :data="tableData" style="width: 100%" @row-dblclick="handlerClickRow">
            <el-table-column type="index" label="序号" width="55" />
            <el-table-column prop="name" label="姓名" style="width: 24%" />
            <el-table-column prop="date" label="材料名称" style="width: 24%" />
            <el-table-column prop="address" label="应扫页数" style="width: 24%" />
            <el-table-column prop="page" label="页数" style="width: 24%" />
            <el-table-column fixed="right" label="操作" width="100">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="moveUp(scope.row)">上移</el-button>
                <el-button type="text" size="small" @click="moveDown(scope.row)">下移</el-button>
              </template>
            </el-table-column>
          </el-table>

          <!-- 分页 -->
          <div class="page">
            <el-pagination
              :page-sizes="[20, 50, 100, 200]"
              :page-size="20"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
          <el-drawer
            title="上传进度"
            :visible.sync="drawer"
            :with-header="false"
          >
            <span><span style="width: 200px;display: block;float: left;">电子文件名称</span><span style="width: 250px;display: block;float: right;"><el-progress v-for="(item, index) in percentage" :key="index" :percentage="item">文件</el-progress></span></span>
          </el-drawer>
        </div>
      </el-col>
      <el-col :span="16" style="height: calc(100vh - 86px);">
        <!-- 功能按钮 -->
        <div class="btn">
          <el-dropdown>
            <el-button type="primary">
              导入<i class="el-icon-arrow-down el-icon--right" />
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <el-button type="primary" class="top-btn">导入图片<i class="el-icon-upload el-icon--right" />
                  <input ref="clearFile" type="file" class="inputFile" multiple="multiplt" @change="importClick($event)">
                </el-button>
              </el-dropdown-item>
              <el-dropdown-item>
                <el-button type="primary" class="top-btn">导入文件<i class="el-icon-upload el-icon--right" />
                  <input ref="clearFile" webkitdirectory type="file" class="inputFile" multiple="multiplt" @change="importClick($event)">
                </el-button>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button type="primary" class="top-btn" @click="submitUpload">上传<i class="el-icon-download" /></el-button>
          <el-button type="primary" class="top-btn" @click="electronicFileAll">所有电子文件<i class="el-icon-download" /></el-button>
          <el-button type="primary" class="top-btn">选择任务<i class="el-icon-download" /></el-button>
          <el-button type="primary" class="top-btn" @click="drawer = true">上传进度<i class="el-icon-download" /></el-button>
          <el-button type="primary" class="top-btn" @click="imgProcessing">图像处理<i class="el-icon-download" /></el-button>
          <el-select v-model="value" placeholder="请选择" style="width: 100px;" @change="selectChange">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </div>
        <el-row id="picBox" ref="rightBox" style="height: calc(100vh - 142px);overflow: auto;">
          <el-col v-for="(item,index) in url" ref="imgDiv" :draggable="allowHandle" :style="picSize">
            <span style="display: block;width: 90%;height: 17px;size: 10px;text-align: center;line-height: 17px;">电子文件名</span>
            <div style="cursor:pointer;position: absolute;right: 5px;top: 0;" @click="deleteDiv(index)"><i class="el-icon-circle-close" /></div>
            <el-image class="imgS" :id="index" ref="img" :key="index" :style="imgStyle" :src="item" lazy draggable="true" @dblclick="previewDblclick" @mouseup="stop" @mousemove="mobile" @mousedown="faceImgdrag($event,item)">
              <div slot="placeholder" class="image-slot">
                加载中<span class="dot">...</span>
              </div>
            </el-image>
          </el-col>
        </el-row>
        <div v-if="maskShow" class="Pic-mask">
          <div>
            <img ref="rotate" :style="PicStyle" :src="maskUrl" @mouseover="onmouseover" @mouseout="onmouseout" @mousedown="start" @mouseup="stop" @mousemove="move">
          </div>
          <div class="tool-bt-nav">
            <div class="el-image-viewer__actions__inner">
              <i class="el-icon-zoom-out" @click="picSizeClick(1)" />
              <i class="el-icon-zoom-in" @click="picSizeClick(2)" />
              <i class="el-icon-arrow-left" @click="turnPage(2)" />
              <i :class="fullScreen" @click="fullScreenClick" />
              <i class="el-icon-arrow-right" @click="turnPage(1)" />
              <i class="el-icon-refresh-left" @click="leftRotating" />
              <i class="el-icon-refresh-right" @click="rightRotating" />
            </div>
          </div>
          <div class="el-image-viewer__btn el-image-viewer__close">
            <i class="el-icon-circle-close" @click="maskShow=false" />
          </div>
          <div class="el-image-viewer__btn el-image-viewer__prev" @click="turnPage(1)"><i class="el-icon-arrow-left" /></div>
          <div class="el-image-viewer__btn el-image-viewer__next" @click="turnPage(2)"><i class="el-icon-arrow-right" /></div>
        </div>
      </el-col>
    </el-row>
  </div>

</template>

<script>
export default {
  data() {
    return {
      // 进度条
      percentage: [],
      // 当前 所点击的图片
      currentPage: 0,
      // 弹窗遮罩
      maskShow: false,
      drawer: false,
      allowHandle: true,
      total: 0,
      url: [],
      maskUrl: '',
      urlAll: [],
      picSize: {},
      imgStyle: {},
      PicStyle: {},
      tableData: [
        {
          date: '干部履历表',
          name: '王小虎',
          address: 2,
          index: 0,
          page: 2,
          img: []
        },
        {
          date: '干部履历表',
          name: '王小虎',
          address: 2,
          index: 1,
          page: 2,
          img: []
        },
        {
          date: '干部履历表',
          name: '王小虎',
          address: 2,
          index: 2,
          page: 2,
          img: []
        },
        {
          date: '干部履历表',
          name: '王小虎',
          address: 2,
          index: 3,
          page: 2,
          img: []
        }
      ],
      options: [{
        value: 1,
        label: '1*1'
      }, {
        value: 2,
        label: '2*2'
      }, {
        value: 3,
        label: '3*3'
      }, {
        value: 4,
        label: '4*4'
      }],
      value: 3,
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
      zIndex: 0,
      mobileL: 0,
      // pic index
      picIndex: 0,
      //mobile pic index
      mobilePicIndex: 0
    }
  },
  mounted() {
    console.log(this.$refs.rightBox)
    window.onresize = () => {
      return (() => {
        this.dynamicPicSize()
      })()
    }
  },
  methods: {
    faceImgdrag(e, item) {
      console.log(e)
      e.preventDefault()
      this.canDrag = true
      this.maskElement = e.target // 获取目标元素
      this.maskElement.style.zIndex = this.zIndex+1
      this.picIndex = Number(this.maskElement.id)
      this.mobilePicIndex = Number(this.maskElement.id)
      // 算出鼠标相对元素的位置
      this.imgOffsetLeft = this.maskElement.offsetLeft
      this.imgOffsetTop = this.maskElement.offsetTop
      this.currentX = e.pageX - this.maskElement.offsetLeft
      this.currentY = e.pageY - this.maskElement.offsetTop

    },
    mobile() {
      if (this.canDrag === true) {

        document.onmousemove = (e) => { // 鼠标按下并移动的事件
        const left = e.pageX - this.currentX
        const top = e.pageY - this.currentY
        const z = (Number(this.maskElement.id) + 1)/this.value
        if( Number.isInteger(z) ){

          if( left === -15 ){
            this.mobileL = left
            this.picIndex = this.picIndex-1
          }
        }else{
          if( left === -15 ){
              this.mobileL = left
              if( this.picIndex === 0 ){
                  this.picIndex = 0
              }else {
                this.picIndex = this.picIndex-1
              }
          }else if( left > -15 ){
              this.mobileL = left
              if( this.picIndex === this.url.length ){
                  this.picIndex = this.url.length
              }else {
                this.picIndex = this.picIndex+1
              }
          }
        }
          // 用鼠标的位置减去鼠标相对元素的位置，得到元素的位置
          // 移动当前元素
          this.zIndex = this.zIndex+1
          this.maskElement.style.margin = 0
          this.maskElement.style.left = left + 'px'
          this.maskElement.style.position = 'absolute'

          console.log(left)

        }
        document.onmouseup = (e) => {
          document.onmousemove = null
          document.onmouseup = null
        }

      }
    },
    // 动态改变图片大小
    dynamicPicSize() {
      this.picSize = {
        width: this.$refs.rightBox.$el.clientWidth / this.value - 12 + 'px',
        height: this.$refs.rightBox.$el.clientHeight / this.value - 12 + 'px',
        padding: '5px',
        margin: '5px',
        border: ' 1px solid #ccc',
        position: 'relative'
      }
      this.imgStyle = {
        position: 'absolute',
        width: '95%',
        left: '8px',
        bottom: '4px',
        overflow:'visible',
        height: this.$refs.rightBox.$el.clientHeight / this.value - 40 + 'px'
      }
    },
    handleSizeChange() {},
    handleCurrentChange() {},
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    },
    // 下拉分页监听
    selectChange(val) {
      if (val === 1) {
        this.dynamicPicSize()
      } else if (val === 2) {
        this.dynamicPicSize()
      } else if (val === 3) {
        this.dynamicPicSize()
      } else if (val === 4) {
        this.dynamicPicSize()
      }
    },
    // 图像处理
    imgProcessing() {

    },
    // 上传
    submitUpload() {
      // this.$refs.upload.submit()
    },
    // 导入
    importClick(event) {
      console.log(event)
      var file = event.target.files
      for (let i = 0; i < file.length; i++) {
        this.dynamicPicSize()
        this.url.push(this.getObjectURL(file[i]))
        this.urlAll.push(this.getObjectURL(file[i]))
        this.percentage.push(0)
      }
    },
    getObjectURL(file) {
      let url = null
      if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file)
      } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file)
      } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file)
      }
      return url
    },
    // 所有电子文件
    electronicFileAll() {
      this.url = this.urlAll
    },
    // 删除
    deleteDiv(i) {
      this.url.splice(i, 1)
      this.urlAll.splice(i, 1)
      this.percentage.splice(i, 1)
    },
    AddPic(ee, a) {
      if (ee > a) {
        return
      }
      for (let i = a; i < this.urlAll.length; i++) {
        this.img.push(this.urlAll[i])
      }
      ee++
      this.AddPic()
    },
    // grid 行点击事件
    handlerClickRow(val) {
      console.log(val)
      this.url = []
      val.img = []
      this.Number = 0
      if (val.index === 0) {
        this.Number = 0
      } else {
        for (let i = 0; i < val.index; i++) {
          this.Number = this.Number + this.tableData[i].address
        }
      }
      for (let i = this.Number; i < this.Number + val.address; i++) {
        val.img.push(this.urlAll[i])
      }
      console.log(this.Number)
      if (this.Number >= this.urlAll.length) {
        return
      }
      this.url = val.img
      console.log(this.Number)
      // val.img = this.img
      // this.url = val.img
      // console.log(val.img)
    },
    // 上移
    moveUp(val) {
      console.log(val)
      if (val.index === 0) {
        return
      } else {
        this.tableData[val.index - 1].img.push(val.img[0])
        this.tableData[val.index - 1].address = this.tableData[val.index - 1].address + 1
        val.address = val.address - 1
        val.img.splice(0, 1)
      }
    },
    // 下移
    moveDown(val) {
      if (val.index === this.tableData.length - 1) {
        return
      } else {
        this.tableData[val.index + 1].img.unshift(val.img[val.img.length - 1])
        this.tableData[val.index + 1].address = this.tableData[val.index + 1].address + 1
        val.address = val.address - 1
        val.img.splice(val.img.length - 1, 1)
      }
    },
    // 双击查看
    previewDblclick(val) {
      console.log(val)
      this.PicStyle = {
        width: val.path[0].naturalWidth + 'px',
        height: val.path[0].naturalHeight + 'px',
        position: 'absolute',
        top: 0,
        right: 0,
        bottom: 0,
        left: 0,
        cursor: 'move',
        margin: 'auto',
        zIndex: 9999

      }
      this.currentPage = Number(val.path[0].id)
      this.maskUrl = val.path[0].src
      this.maskShow = true
    },
    // 左旋转
    leftRotating() {
      this.deg = this.deg - 90
      if (this.deg === 0) {
        this.deg = 360
      }
      this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`
    },

    // 右旋转
    rightRotating() {
      this.deg += 90
      if (this.deg >= 360) {
        this.deg = 0
      }
      this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`
    },
    // 翻页
    turnPage(type) {
      if (type === 1) { // 上一张
        this.currentPage++
        if (this.currentPage >= this.url.length) {
          this.currentPage = 0
        }
        this.deg = 0
        this.scale = 1
        this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`
        this.$refs.rotate.style.transform = `scale(${this.scale})`
        this.maskUrl = this.url[this.currentPage]
      } else { // 下一张
        this.currentPage--
        if (this.currentPage < 0) {
          this.currentPage = this.url.length - 1
        }
        this.deg = 0
        this.scale = 1
        this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`
        this.$refs.rotate.style.transform = `scale(${this.scale})`
        this.maskUrl = this.url[this.currentPage]
      }
    },
    // 全屏
    fullScreenClick() {
      if (this.fullScreen === 'el-icon-full-screen') {
        this.fullScreen = 'el-icon-c-scale-to-original'
        this.scale = 2
        this.$refs.rotate.style.transform = `scale(${this.scale})`
      } else {
        this.fullScreen = 'el-icon-full-screen'
        this.scale = 1
        this.$refs.rotate.style.transform = `scale(${this.scale})`
      }
    },
    // 放大缩小
    // transform:scale
    picSizeClick(type) {
      if (type === 1) {
        this.scale = this.scale - 0.1
        if (this.scale <= 0.1) {
          this.scale = 0.1
        }
        this.$refs.rotate.style.transform = `scale(${this.scale})`
      } else {
        this.scale += 0.1
        if (this.scale >= 2) {
          this.scale = 2
        }
        this.$refs.rotate.style.transform = `scale(${this.scale})`
      }
    },
    // 划过
    onmouseover() {

    },
    // 鼠标按下
    start(e) {
      console.log(e)
      // 鼠标左键点击
      if (e.button === 0) {
        e.preventDefault()
        this.canDrag = true
        this.maskElement = e.target // 获取目标元素
        // 算出鼠标相对元素的位置
        this.imgOffsetLeft = this.maskElement.offsetLeft
        this.imgOffsetTop = this.maskElement.offsetTop
        this.maskElement.style.marginLeft = this.maskElement.offsetLeft
        this.maskElement.style.marginTop = this.maskElement.offsetTop
        this.currentX = e.pageX - this.maskElement.offsetLeft
        this.currentY = e.pageY - this.maskElement.offsetTop
      }
      console.log(this.imgOffsetTop)

      // recordImg
      document.onmousewheel = (e) => {
        if (e.wheelDelta === 120) {
          this.scale += 0.1
          if (this.scale >= 2) {
            this.scale = 2
          }
          // let imgY = ((this.recordImg.height * this.scale) - this.recordImg.height) * (e.layerX / this.recordImg.height)
          // let imgW = ((this.recordImg.width * this.scale) - this.recordImg.width) * (e.layerY / this.recordImg.width)
          // console.log(imgY)
          this.$refs.rotate.style.transform = `scale(${this.scale})`
          // this.maskElement.style.top = (this.imgOffsetTop - imgY)+'px'
          // this.maskElement.style.left = (this.imgOffsetLeft - imgW)+'px'
          // this.maskElement.style.position = 'absolute'
        } else {
          this.scale = this.scale - 0.1
          if (this.scale <= 0.1) {
            this.scale = 0.1
          }
          this.$refs.rotate.style.transform = `scale(${this.scale})`
        }
      }
    },

    // 划出
    onmouseout() {
      document.onmousewheel = null
      document.onmouseover = null
    },
    // 鼠标弹起
    stop(e) {
      this.canDrag = false
      console.log(this.mobilePicIndex,this.picIndex)
      if( this.mobilePicIndex > this.picIndex ){
        console.log('上移')
      }else{
        console.log('下移')
      }

       // swapArray(this.url, index, index-1);
    },
    //  删除事件
    move() {
      if (this.canDrag === true) {
        document.onmousemove = (e) => { // 鼠标按下并移动的事件
          // 用鼠标的位置减去鼠标相对元素的位置，得到元素的位置
          const left = e.pageX - this.currentX
          const top = e.pageY - this.currentY
          // 移动当前元素
          this.maskElement.style.margin = 0
          this.maskElement.style.left = left + 'px'
          this.maskElement.style.top = top + 'px'
          this.maskElement.style.position = 'absolute'
        }
        document.onmouseup = (e) => {
          document.onmousemove = null
          document.onmouseup = null
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.table-wait {
  width: 100%;
  min-height: calc(100vh - 86px);
  border: 1px solid #e2e3e6;
}
.page {
  float: right;
  padding: 10px;
}
.btn {
  padding-left: 10px;
  padding-top: 10px;
}
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
  .tool-bt-nav{
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
    color: #FFFFFF;
    z-index: 999;
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
    z-index: 99999;
  }
  .pic-mask-style:hover{
    cursor:move
  }
  /deep/.el-image img{
    position: absolute;
    left: 0;
    top: 0;
  }
</style>

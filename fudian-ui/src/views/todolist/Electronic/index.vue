<template>
  <el-row>
    <el-col :style="leftStyleName">
      <div v-if="leftTree1" class="tree">
        <el-tree accordion :data="ElectronicData" :expand-on-click-node="true" :props="defaultProps" @node-click="LeftViewElectronicTree" />
      </div>
      <div class="left-tree"><div class="left-tree-click" @click="divClick"><i :class="picClassname" /></div></div>
    </el-col>
    <el-col ref="rightBxo" :style="rightStyleName">
      <div><el-button type="success" @click="contrastClick">对比</el-button></div>
      <el-col v-for="(url, index) in src" :key="index" :span="8" type="flex" style="padding: 5px;">
        <div :key="index" class="block" @click="preview" @dblclick="previewDblclick">
          <span class="demonstration"><el-checkbox v-model="model[index].model" @change="changePic(model[index].model,index)" />自定义</span>
          <el-col :span="24" :style="picSize">
            <el-image :id="index" ref="img" :key="index" style="width: 100%;height: 100%;" :src="url" lazy>
              <div slot="placeholder" class="image-slot">
                加载中<span class="dot">...</span>
              </div>
            </el-image>
          </el-col>
        </div>
      </el-col>
    </el-col>
    <div v-if="maskShow" class="Pic-mask">
      <div>
        <img v-if="PicMask" ref="rotate" :style="PicStyle1" class="" :src="url1" @mouseover="onmouseover" @mouseout="onmouseout" @mousedown="start" @mouseup="stop" @mousemove="move">
        <img v-if="PicMask1" ref="rotate1" :style="PicStyle" class="" :src="url" @mouseover="onmouseover" @mouseout="onmouseout" @mousedown="start" @mouseup="stop" @mousemove="move">
      </div>
      <div class="tool-bt-nav">
        <div class="el-image-viewer__actions__inner">
          <i class="el-icon-zoom-out" @click="picSizeClick(1)" />
          <i class="el-icon-zoom-in" @click="picSizeClick(2)" />
          <i v-if="turnPageShow" class="el-icon-arrow-left" @click="turnPage(2)" />
          <i v-if="turnPageShow" :class="fullScreen" @click="fullScreenClick" />
          <i v-if="turnPageShow" class="el-icon-arrow-right" @click="turnPage(1)" />
          <i class="el-icon-refresh-left" @click="leftRotating" />
          <i class="el-icon-refresh-right" @click="rightRotating" />
        </div>
      </div>
      <div class="el-image-viewer__btn el-image-viewer__close">
        <i class="el-icon-circle-close" @click="maskShow=false" />
      </div>
      <div v-if="turnPageShow" class="el-image-viewer__btn el-image-viewer__prev" @click="onAPicture"><i class="el-icon-arrow-left" /></div>
      <div v-if="turnPageShow" class="el-image-viewer__btn el-image-viewer__next" @click="UnderAPicture"><i class="el-icon-arrow-right" /></div>
    </div>
  </el-row>
</template>
<script>
import { getSysFile } from '@/api/todolist/Electronic'
export default {
  data() {
    return {
      fullScreen: 'el-icon-full-screen',
      // el-icon-c-scale-to-original
      turnPageShow: true,
      PicMask: false,
      PicMask1: false,
      model: [{ model: false }, { model: false }, { model: false }, { model: false }, { model: false }, { model: false }],
      // 档案数据
      params: '',
      // 鼠标 是否按下
      canDrag: false,
      // 图片 位子
      currentX: 0,
      currentY: 0,
      // 预览图片元素
      maskElement: '',
      // 遮罩
      maskShow: false,
      // 旋转deg
      deg: '',
      // 放大
      scale: 1,
      // 当前页
      currentPage: 0,
      url: '',
      url1: '',
      PicStyle: {},
      // 图片左位置
      imgOffsetLeft: '',
      // 图片上位置
      imgOffsetTop: '',
      // 记录 图片的宽高
      recordImg: {},
      // 左侧树父节点 数组
      leftTreeData: [],
      // 左侧树当前节点
      leftCurrent: {},
      // 用户id
      a01Id: '',
      src: [
        'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg',
        'https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg',
        'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg',
        'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg',
        'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg',
        'https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg'
      ],
      picClassname: 'el-icon-caret-left',
      rightStyle: {
        width: '80%', height: 'calc(100vh - 86px)', padding: ' 10px', overflow: 'auto'
      },
      rightStyle1: {
        width: '99.5%', height: 'calc(100vh - 86px)', padding: ' 10px', overflow: 'auto'
      },
      leftStyle: {
        width: '20%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      leftStyle1: {
        width: '0.5%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      leftStyleName: {
        width: '20%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      rightStyleName: {
        width: '80%', height: 'calc(100vh - 86px)', padding: ' 10px', overflow: 'auto'
      },
      leftTree1: true,
      ElectronicData: [
        { label: '履历材料', id: 0, children: [
          { label: '履历测试材料', id: 0 },
          { label: '履历测试材料', id: 1 },
          { label: '履历测试材料', id: 2 }
        ]
        },
        { label: '自传材料', id: 1, children: [{ label: '自传测试材料' }] },
        { label: '工资材料', id: 2, children: [{ label: '工资测试材料' }] }
      ],
      defaultProps: {
        children: 'children',
        label: 'fullName',
        id: 'id',
        index: 'index'
      },
      dataPic: [],
      picSize: '',
      // 左侧树索引
      leftTreeIndex: 0
    }
  },
  watch: {

  },
  mounted() {
    this.toObtainFn()
        this.picSize = {
          width: this.$refs.rightBxo.$el.clientWidth / 3 - 10 + 'px',
          height: this.$refs.rightBxo.$el.clientHeight / 3 - 10 + 'px',
          padding: '10px'
        }
  },
  methods: {
    changePic(val, index) {
      if (val === true) {
        this.dataPic.push(this.src[index])
      } else {
        if (this.dataPic.length === 1) {
          this.dataPic.splice(0, 1)
        } else {
          for (let i = 0; i < this.dataPic.length; i++) {
            if (this.dataPic[i] === this.src[index]) {
              this.dataPic.splice(i, 1)
            }
          }
        }
      }
    },
    contrastClick(val) {
      this.PicStyle1 = {
        width: '400px',
        height: '400px',
        position: 'absolute',
        top: 0,
        left: '50px',
        bottom: 0,
        margin: 'auto'

      }
      this.PicStyle = {
        width: '400px',
        height: '400px',
        position: 'absolute',
        top: 0,
        right: '50px',
        bottom: 0,
        margin: 'auto'
      }
      if (this.dataPic.length === 2) {
        this.url1 = this.dataPic[0]
        this.url = this.dataPic[1]
        this.turnPageShow = false
        this.maskShow = true
        this.PicMask = true
        this.PicMask1 = true
      } else {
        this.$message({
          message: '请选择两张图片在进行对比',
          type: 'warning'
        })
      }
    },
    // 获取参数
    toObtainFn() {
      document.oncontextmenu = function() {
        return false
      }
      //通过查阅页面的getType 将当前的数据
      this.ElectronicData = this.$route.params.data;
      this.a01Id = this.$route.params.a01Id
      getSysFile({ e01z1Id: this.ElectronicData[0].children[0].id })
        .then(response => {
          if (response.code === 200) {
            this.src = []
            console.log(response.data)
            for (let i = 0; i < response.data.length; i++) {
              this.model.push({ model: false })
              this.src.push('/fileStorage/downFileByFileSource?fileSource=' + response.data[i].fileSource)
            }
          }
        })
    },
    // 左侧
    loadNode(node, resolve) {
      console.log(node)
    },
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
          if (this.PicMask === false) {
            this.$refs.rotate1.style.transform = `scale(${this.scale})`
          } else {
            this.$refs.rotate1.style.transform = `scale(${this.scale})`
            this.$refs.rotate.style.transform = `scale(${this.scale})`
          }
          // this.maskElement.style.top = (this.imgOffsetTop - imgY)+'px'
          // this.maskElement.style.left = (this.imgOffsetLeft - imgW)+'px'
          // this.maskElement.style.position = 'absolute'
        } else {
          this.scale = this.scale - 0.1
          if (this.scale <= 0.1) {
            this.scale = 0.1
          }
          if (this.PicMask === false) {
            this.$refs.rotate1.style.transform = `scale(${this.scale})`
          } else {
            this.$refs.rotate1.style.transform = `scale(${this.scale})`
            this.$refs.rotate.style.transform = `scale(${this.scale})`
          }
        }
      }
    },
    move(e) {
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
    },
    stop(e) {
      this.canDrag = false
    },
    // 左侧树 收缩
    divClick() {
      this.leftTree1 = !this.leftTree1
      if (this.leftTree1) {
        this.rightStyleName = this.rightStyle
        this.leftStyleName = this.leftStyle
        this.picClassname = 'el-icon-caret-left'
      } else {
        this.rightStyleName = this.rightStyle1
        this.leftStyleName = this.leftStyle1
        this.picClassname = 'el-icon-caret-right'
      }
    },
    // 电子文件弹窗 左侧 树点击事件
    LeftViewElectronicTree(val) {
      console.log(val)
      if (!val.children) {
        // 左侧树当前节点
        this.leftCurrent = val
        this.leftTreeIndex = this.leftCurrent.index
        console.log(this.ElectronicData)
        getSysFile({ e01z1Id: val.id })
          .then(response => {
            if (response.code === 200) {
              this.src = []
              console.log(response.data)
              for (let i = 0; i < response.data.length; i++) {
                this.model.push({ model: false })
                this.src.push('/fileStorage/downFileByFileSource?fileSource=' + response.data[i].fileSource)
              }
            }
          })
      } else {
        // 左侧树父节点 数组
        this.leftTreeData = val.children
        for (let i = 0; i < this.leftTreeData.length; i++) {
          this.leftTreeData[i].index = i
        }
      }
    },
    previewDblclick(val) {
      this.PicStyle = {
        width: val.path[0].naturalWidth + 'px',
        height: val.path[0].naturalHeight + 'px',
        position: 'absolute',
        top: 0,
        right: 0,
        bottom: 0,
        left: 0,
        margin: 'auto'

      }
      this.recordImg = {
        width: val.path[0].naturalWidth,
        height: val.path[0].naturalHeight
      }
      this.currentPage = Number(val.path[0].id)
      console.log(this.currentPage)
      this.url = val.path[0].src
      this.PicMask = false
      this.PicMask1 = true
      this.maskShow = true
      this.turnPageShow = true
    },
    // 图 预览事件
    preview(val) {
      console.log(val)
    },
    // 左旋转
    leftRotating() {
      this.deg = this.deg - 90
      if (this.deg === 0) {
        this.deg = 360
      }
      if (this.PicMask === false) {
        this.$refs.rotate1.style.transform = `rotate(${this.deg}deg)`
      } else {
        this.$refs.rotate1.style.transform = `rotate(${this.deg}deg)`
        this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`
      }
    },

    // 右旋转
    rightRotating() {
      this.deg += 90
      if (this.deg >= 360) {
        this.deg = 0
      }
      if (this.PicMask === false) {
        this.$refs.rotate1.style.transform = `rotate(${this.deg}deg)`
      } else {
        this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`
        this.$refs.rotate1.style.transform = `rotate(${this.deg}deg)`
      }
    },
    // 翻页
    turnPage(type) {
      if (type === 1) { // 上一张
        this.currentPage++
        if (this.currentPage >= this.src.length) {
          this.currentPage = 0
        }
        this.deg = 0
        this.scale = 1
        if (this.PicMask === false) {
          this.$refs.rotate1.style.transform = `rotate(${this.deg}deg)`
          this.$refs.rotate1.style.transform = `scale(${this.scale})`
        } else {
          this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`
          this.$refs.rotate.style.transform = `scale(${this.scale})`
          this.$refs.rotate1.style.transform = `scale(${this.scale})`
          this.$refs.rotate1.style.transform = `rotate(${this.deg}deg)`
        }
      } else { // 下一张
        this.currentPage--
        if (this.currentPage < 0) {
          this.currentPage = this.src.length - 1
        }
        this.deg = 0
        this.scale = 1
        if (this.PicMask === false) {
          this.$refs.rotate1.style.transform = `rotate(${this.deg}deg)`
          this.$refs.rotate1.style.transform = `scale(${this.scale})`
        } else {
          this.$refs.rotate.style.transform = `rotate(${this.deg}deg)`
          this.$refs.rotate.style.transform = `scale(${this.scale})`
          this.$refs.rotate1.style.transform = `scale(${this.scale})`
          this.$refs.rotate1.style.transform = `rotate(${this.deg}deg)`
        }
      }
      this.url = this.src[this.currentPage]
    },
    // 全屏
    fullScreenClick() {
      if (this.fullScreen === 'el-icon-full-screen') {
        this.fullScreen = 'el-icon-c-scale-to-original'
        this.scale = 2
        this.$refs.rotate1.style.transform = `scale(${this.scale})`
      } else {
        this.fullScreen = 'el-icon-full-screen'
        this.scale = 1
        this.$refs.rotate1.style.transform = `scale(${this.scale})`
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
        if (this.PicMask === false) {
          this.$refs.rotate1.style.transform = `scale(${this.scale})`
        } else {
          this.$refs.rotate.style.transform = `scale(${this.scale})`
          this.$refs.rotate1.style.transform = `scale(${this.scale})`
        }
      } else {
        this.scale += 0.1
        if (this.scale >= 2) {
          this.scale = 2
        }
        if (this.PicMask === false) {
          this.$refs.rotate1.style.transform = `scale(${this.scale})`
        } else {
          this.$refs.rotate.style.transform = `scale(${this.scale})`
          this.$refs.rotate1.style.transform = `scale(${this.scale})`
        }
      }
    },
    // 上一份
    onAPicture() {
      if (this.leftTreeIndex === 0) {
        this.leftTreeIndex = 0
      } else {
        this.leftTreeIndex = this.leftTreeIndex-1
        getSysFile({ e01z1Id: this.leftTreeData[this.leftTreeIndex].id })
          .then(response => {
            if (response.code === 200) {
              this.src = []
              console.log(response.data)
              for (let i = 0; i < response.data.length; i++) {
                this.model.push({ model: false })
                this.src.push('/fileStorage/downFileByFileSource?fileSource=' + response.data[i].fileSource)
              }
              this.url = this.src[0]
              this.currentPage = 0
            }
          })
      }
    },
    // 下一份
    UnderAPicture() {
      if (this.leftTreeIndex === this.leftTreeData.length) {
        this.leftTreeIndex = this.leftTreeData.length
      } else {
        this.leftTreeIndex = this.leftTreeIndex+1
        getSysFile({ e01z1Id: this.leftTreeData[this.leftTreeIndex].id })
          .then(response => {
            if (response.code === 200) {
              this.src = []
              console.log(response.data)
              for (let i = 0; i < response.data.length; i++) {
                this.model.push({ model: false })
                this.src.push('/fileStorage/downFileByFileSource?fileSource=' + response.data[i].fileSource)
              }
              this.url = this.src[0]
              this.currentPage = 0
            }
          })
      }
    },
    // 划过
    onmouseover() {

    },

    // 划出
    onmouseout() {
      document.onmousewheel = null
      document.onmouseover = null
    }

  }
}
</script>

<style lang="scss" scoped>
  .left-tree{
    right: 0;
    top: 0;
    height: calc(100vh - 86px);
    width: 10px;
    position: absolute;
  }
  .left-tree-click{
    position: absolute;
    padding: 20px 0;
    width: 16px;
    height: 16px;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
  }
  .demonstration{
    display: block;
    width: 100%;
    text-align: left;
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
  /deep/.el-tree-node__label {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }
</style>

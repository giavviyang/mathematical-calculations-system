<template>
  <div>
    <el-col :span="24" :xs="24" style="height: calc(100vh - 86px);">
      <el-row :gutter="10" ref="searchRow1" style="margin:0;">
        <el-form
          v-if="showBorrowing"
          class="search-form"
          :model="params"
          :inline="true"
          style="background:rgba(204, 204, 204, 0.18);"
          @submit.native.prevent
        >
          <el-form-item label="商品名称" prop="fileName">
            <el-input
              v-model="params.goodsName"
              placeholder="请输入名称"
              clearable
              size="mini"
              style="width: 150px"
              @keyup.enter.native="onEnterSearch"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryBorrowing">搜索
            </el-button>
          </el-form-item>
        </el-form>

      </el-row>
      <el-row :span="24" style="padding: 10px 0px;">
        <el-button type="primary" size="mini" icon="el-icon-plus" @click="addWindows">新增</el-button>
        <el-button type="primary" size="mini"  icon="el-icon-edit" @click="updateClick">编辑</el-button>
        <el-button type="primary" size="mini"  icon="el-icon-delete" @click="deleteCmmodity">删除</el-button>
      </el-row>
      <el-table
        :default-sort="{ prop: 'date', order: 'descending' }"
        stripe
        border
        class="table-style"
        :height="tableHeight"
        empty-text=" "
        size="mini"
        tooltip-effect="light"
        ref="BorrowingData0"
        :header-cell-style="headClass"
        style="width: 100%"
        :data="BorrowingData"
        @row-click="StepsListRowClick"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="40"/>
        <el-table-column style="width: 20px" type="index" align="center" label="序号" :index="customIndex"/>
        <el-table-column
          align="center"
          show-overflow-tooltip
          v-for="(item, index) in pendingApprovalCol"
          v-if="item.isCol"
          :key="index"
          :width="item.width"
          :formatter="item.formatter"
          :prop="item.prop"
          :label="item.label"/>
        <el-table-column style="width: 20px" show-overflow-tooltip prop="fileData" type="text" align="center" label="商品内容">
          <template slot-scope="scope">
            <img @click="openImgWindows(scope.row.fileData)" :src="'data:image/png;base64,' + scope.row.fileData" style="width: 20px;height: 20px"/>
          </template>
        </el-table-column>
      </el-table>

      <div class="block" style="float: right;margin-top: 5px">
        <el-pagination
          :page-sizes="[20, 50, 100, 200]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          :current-page ="pageNum"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-col>

    <!--  新增编辑页面  -->
    <el-dialog
      :title="titleName"
      width="30%"
      class=" dialog-style dialog-basic"
      :destroy-on-close="true"
      :append-to-body="true"
      :close-on-click-modal="false"
      :visible.sync="approvalProcessingShow">

      <div style="">
        <el-form ref="commodityForm" :model="commodityForm" :rules="formRules" style="margin-left: 10%;margin-right: 10%"
                 label-width="120px">
          <el-form-item prop="goodsName" label="商品名称">
            <el-input size="mini" placeholder="请输入商品名称" clearable v-model="commodityForm.goodsName"/>
          </el-form-item>
          <el-form-item prop="goodsCoins" label="金币数量">
            <el-input-number :min="1" :max="999999" size="mini" clearable v-model="commodityForm.goodsCoins"/>
          </el-form-item>
          <el-form-item prop="goodsNum" label=" 商品数量">
            <el-input-number :min="1" :max="999999" size="mini" clearable v-model="commodityForm.goodsNum"/>
          </el-form-item>
          <el-form-item prop="goodsDescribe" label="商品描述">
            <el-input size="mini" placeholder="请输入商品描述" clearable v-model="commodityForm.goodsDescribe"/>
          </el-form-item>
          <el-form-item prop="name" label="商品内容">
            <el-upload
              class="upload-demo"
              ref="upload"
              :action="action"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :file-list="fileList"
              :on-change="handleChange"
              :auto-upload="false"
              :on-success="uploadSuccess"
              :on-error="uploadError"
              :data="commodityForm">
              <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
            <img :src="'data:image/png;base64,' + commodityForm.fileData" v-if=" commodityForm.fileData && ''!== commodityForm.fileData && (($refs.upload && !$refs.upload.uploadFiles[0]) || (!$refs.upload))" style="width: 40px;height: 40px;margin-left: 100px">
          </el-form-item>
        </el-form>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="approvalProcessingShow=false">取 消</el-button>
        <el-button size="mini" type="primary" @click="submitUpload()">保 存</el-button>

      </div>
    </el-dialog>

    <!--  图片放大  -->
    <el-dialog
      title="图片预览"
      :visible.sync="imgWindows"
      width="30%"
      :append-to-body="true"
      :close-on-click-modal="false"
    >
      <div style="width: 100%; height: 100%"><img id="openImg" :src="imageData" :style="imgCss" fit="scale-down" /></div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="imgWindows = false">取 消</el-button>
        <el-button type="primary" @click="imgWindows = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
import {getGoodsList,insertGoodsList,updateGoodsListTwo,deleteGoodsListByIds} from '@/api/system/commodity'
import {MessageBox} from 'element-ui'
const params = {
  pageNum: 1,
  pageSize: 20
}
const baseUrl = process.env.VUE_APP_BASE_API;
export default {
  created() {
    const _this = this;
    /*--- 查询商品列表 ---*/
    _this.loadGrid(params);
  },
  mounted() {
    this.$nextTick(() => {
      this.searchRowHeight = this.$refs[this.searchRow1].$el.offsetHeight
      this.tableHeight = (document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + "px"
    })
    window.onresize = () => {
      return (() => {
        this.searchRowHeight = this.$refs[this.searchRow1].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + "px"
      })();
    };
    this.keyupSubmit();
  },
  watch: {
    BorrowingData: {
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(() => {
          _this.$refs.BorrowingData0.doLayout();
        })
      },
      deep: true,
      immediate: true
    },

  },
  data() {
    return {
      approvalProcessingShow: false,
      imgWindows:false,
      imgCss:{},
      showBorrowing: true,
      commodityForm: {
        goodsId:'',
        goodsName:'',
        goodsImge:'',
        goodsCoins:'',
        goodsNum:'',
        goodsType:'',
        goodsDescribe:'',
        fileData:'',
        fileId:'',
        fileFormat:'',
      },
      imageData:'',
      tableHeight: "73%",
      searchRowHeight: '',
      searchRow1: 'searchRow1',
      electronicCheck: [],
      total: 0,
      pageSizeParam: {},
      titleName: "新增",
      pageNum: 1,
      pageSize: 20,
      BorrowingData: [
      ],

      /*文件列表*/
      fileList: [],

      /* 水印文件参数 */
      fileData : {},

      /* 请求路径 */
      action:'',

      //表单校验
      formRules : {
        goodsName: [
          {required: true, message: '请输入商品名称', trigger: 'blur'},
        ],
        goodsCoins: [
          {required: true, message: '请输入金币数量', trigger: 'blur'},
        ],
        goodsNum: [
          {required: true, message: '请输入商品数量', trigger: 'blur'},
        ],
        goodsDescribe:[
          {required: true, message: '请输入商品描述', trigger: 'blur'},
        ]
      },
      params : {
        pageNum: 1,
        pageSize: 20
      },

      pendingApprovalCol: [
        {label: '商品名称', prop: 'goodsName', isCol: true, width: "200px"},
        // {label: '商品类型', prop: 'goodsType', isCol: true, width: "200px"},
        {label: '金币数量', prop: 'goodsCoins', isCol: true, width: "200px"},
        {label: '商品数量', prop: 'goodsNum', isCol: true, width: "200px"},
        {label: '商品描述', prop: 'goodsDescribe', isCol: true, width: "200px"},
      ],
    }
  },
  methods: {
    //键盘按下enter搜索事件
    keyupSubmit() {
      document.onkeydown = e => {
        const _key = window.event.keyCode
        if (_key === 13) {
          this.handleQueryBorrowing();
        }
      }
    },
    //自定义序号
    customIndex(index){
      return (this.pageNum-1)*(this.pageSize)+(index+1);
    },
    handleChange(file, fileList) {
      if (fileList.length > 0) {
        this.fileList = [fileList[fileList.length - 1]]  // 这一步，是 展示最后一次选择的csv文件
      }
    },
    //删除商品
    deleteCmmodity() {
      const _this = this;
      if (this.electronicCheck.length == 0) {
        _this.$message({
          message: '请至少选择一条记录',
          type: 'warning'
        })
        return;
      }
      let ids='';
      for (let i = 0; i <  this.electronicCheck.length; i++) {
        ids = ids+this.electronicCheck[i].goodsId+',';
      }
      MessageBox.confirm(
        '是否删除选中的' + this.electronicCheck.length + '条数据',
        '系统提示',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(() => {
        deleteGoodsListByIds({ids:ids}).then(response => {
          if (response.code == 200) {
            _this.$message({
              message: "删除成功",
              type: 'success'
            })
            _this.loadGrid(params);
          }
        })
      })
    },

    submitUpload() {
      const _this = this;
      this.$refs["commodityForm"].validate((valid) => {
        if (valid) {
          if (!_this.commodityForm.goodsId || '' == _this.commodityForm.goodsId) { //新增
            if (!this.$refs.upload.uploadFiles[0]) {
              this.$message({
                showClose: true,
                message: "请选择一张图片",
                type: 'warning'
              });
              return;
            }
            if (this.$refs.upload.uploadFiles[0].raw.type !== "image/png" && this.$refs.upload.uploadFiles[0].raw.type !== "image/jpeg") {
              _this.$message({
                showClose: true,
                message: "图片类型只能为jpg格式或者png格式",
                type: 'warning'
              });
              return;
            }
            if (this.$refs.upload.uploadFiles[0].size > 512000) {
              _this.$message({
                showClose: true,
                message: "图片大小不能超过500k",
                type: 'warning'
              })
              return;
            }
            _this.$refs.upload.submit();
          } else { //修改
            if (!this.$refs.upload.uploadFiles[0]) { //未更改图片
              let data = {};
              for (let key in _this.commodityForm) {
                data[key] = _this.commodityForm[key];
              }
              data.image=null;
              updateGoodsListTwo(data)
                .then(function (response) {
                  _this.loadGrid(params);
                  _this.approvalProcessingShow = false;
                  _this.$message({
                    showClose: true,
                    message: response.msg,
                    type: 'success'
                  });
                  _this.formatForm();
                })
                .catch(function (error) {
                  _this.$message({
                    showClose: true,
                    message: error,
                    type: 'error'
                  })
                })
            } else { //已更改图片
              if (!this.$refs.upload.uploadFiles[0]) {
                this.$message({
                  showClose: true,
                  message: "请选择一张图片",
                  type: 'warning'
                });
                return;
              }
              if (this.$refs.upload.uploadFiles[0].raw.type !== "image/png" && this.$refs.upload.uploadFiles[0].raw.type !== "image/jpeg") {
                _this.$message({
                  showClose: true,
                  message: "图片类型只能为jpg格式或者png格式",
                  type: 'warning'
                });
                return;
              }
              if (this.$refs.upload.uploadFiles[0].size > 512000) {
                _this.$message({
                  showClose: true,
                  message: "图片大小不能超过500k",
                  type: 'warning'
                })
                return;
              }
              _this.$refs.upload.submit();
            }
          }
        }
      })


    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    uploadSuccess(res) {
      this.$refs.upload.clearFiles();
      this.loadGrid(params);
      this.approvalProcessingShow = false;
      if (res.code === 200) {
        this.$message({
          showClose: true,
          message: res.msg,
          type: 'success'
        })
      } else {
        this.$message({
          showClose: true,
          message: res.msg,
          type: 'error'
        })
      }

    },
    uploadError() {
      const _this = this;
      _this.$message({
        showClose: true,
        message: "上传失败",
        type: 'error'
      })
    },

    //编辑
    updateClick() {
      if (this.electronicCheck.length!=1){
        this.$message.warning("请勾选一条数据")
        return ;
      }
      let val = this.electronicCheck[0];
      const _this = this;
      _this.action = baseUrl+'/applet/goods/updateGoodsList'
      _this.titleName = "编辑"
      _this.commodityForm.goodsId = val.goodsId;
      _this.commodityForm.goodsName = val.goodsName;
      _this.commodityForm.goodsImge = val.goodsImge;
      _this.commodityForm.goodsCoins = val.goodsCoins;
      _this.commodityForm.goodsNum = val.goodsNum;
      _this.commodityForm.goodsType = val.goodsType;
      _this.commodityForm.goodsDescribe = val.goodsDescribe;
      _this.commodityForm.fileData = val.fileData;
      _this.commodityForm.fileId = val.fileId;
      _this.commodityForm.fileFormat = val.fileFormat;
      _this.approvalProcessingShow = true;
    },
    formatForm() {
      const  _this = this;
      _this.commodityForm={
        goodsId:'',
        goodsName:'',
        goodsImge:'',
        goodsCoins:'',
        goodsNum:'',
        goodsType:'',
        goodsDescribe:'',
        fileData:'',
        fileId:'',
        fileFormat:'',
      };
    },
    onEnterSearch() {
      this.handleQueryBorrowing();
    },
    //搜索
    handleQueryBorrowing() {
      const _this = this;
      params.goodsName = this.params.goodsName;
      params.pageNum = 1;
      this.pageNum =1;
      _this.loadGrid(params);
    },
    addWindows() {
      const _this = this;
      _this.titleName = "新增"
      _this.action = baseUrl+'/applet/goods/insertGoodsList';
      _this.formatForm();
      _this.approvalProcessingShow=true;
    },
    openImgWindows(val) {
      this.imageData = 'data:image/png;base64,' + val;
      this.imgWindows = true;
      this.getImg(this.imageData);
    },
    getImg(src){
      var img_url  =src
      var img = new Image()
      img.src=img_url
      if(img.height > 100 && img.width >100){
        this.imgCss["width"] = "100%";
        this.imgCss["height"] = "100%";
        this.imgCss["position"] = undefined;
        this.imgCss["left"] = undefined
        this.imgCss["top"] = undefined
        this.imgCss["margin-top"] = undefined
        this.imgCss["margin-left"] = undefined
      }else{
        this.imgCss["width"] = undefined
        this.imgCss["height"] = undefined
        this.imgCss.left = "50%";
        this.imgCss.position = "absolute";
        this.imgCss.top = "50%";
        var top = img.height/2;
        this.imgCss["margin-top"] = "-"+top+"px";
        var left = img.width/2;
        this.imgCss["margin-left"] = "-"+left+"px";
        console.log(this.imgCss)
      }
    },
    StepsListRowClick(row) {
      console.log(row)
      this.$refs.BorrowingData0.clearSelection()
      if (this.electronicCheck.length === 0) {
        this.$refs.BorrowingData0.toggleRowSelection(row)
        return;
      }
      this.$refs.BorrowingData0.toggleRowSelection(row)
    },
    // grid 选中
    handleSelectionChange(val) {
      this.electronicCheck = val
    },
    // 档案借阅监听
    // 分页大小
    handleSizeChange(val) {
      // this.pageSize = val
      // this.pageSizeParam.pageSize = this.pageSize
      // this.archivesaFunction(
      //     this.pageSizeParam
      // )
      this.pageSize = val
      params.pageNum=this.pageNum,
        params.pageSize=val
      this.loadGrid(params);

    },
    // 分页监听
    handleCurrentChange(val) {
      // this.pageNum = val
      // this.pageSizeParam.pageSize = this.pageSize
      // this.archivesaFunction(
      //     this.pageSizeParam
      // )
      this.pageNum = val
      params.pageNum=val,
        params.pageSize=this.pageSize
      this.loadGrid(params);
    },
    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
    },

    loadGrid(params){
      const _this = this;
      getGoodsList(params)
        .then(function (response) {
          _this.BorrowingData = response.rows
          _this.total = response.total
        })
    }
  },
};

</script>
<style lang="scss" scoped>
.el-col.el-col-24.el-col-xs-24 {
  padding: 10px;
  /*margin: 10px;*/
}

/* 左侧竖分割线 */
/*.el-tabs--left /deep/ .el-tabs__header.is-left {
  height: calc(100vh - 86px);
}*/

.block {
  float: right;
}

.form-width {
  width: 40%;
  display: flex;
}

// .demo-table-expand {
   //   font-size: 0;
   // }

// .demo-table-expand label {
   //   width: 90px;
   //   color: #99a9bf;
   // }

// .demo-table-expand .el-form-item {
   //   margin-right: 0;
   //   margin-bottom: 0;
   //   width: 50%;
   // }

.inputW {
  width: 200px;
}

.search-form {
  width: 100%;
  /*background:rgba(204, 204, 204, 0.18);*/
  padding: 10px;

  display: flex;
/deep/ .el-form-item {
  margin-bottom: 0 !important;
}
}

/deep/ .el-dialog__wrapper{
  z-index: 9999999;
}
// 到期续借弹窗
   .dialog-state {
     position: relative;
     height: 180px;

     margin: 20px;
     margin-left: 10%;
     width: 80%;
   // width: 240px;
     border: 1px solid #CAD9E7;
     box-shadow: 0 0 3px 1px #EBF0F5;
.state {
  position: absolute;
  top: -9px;
  left: 18px;
}
.state-form {
  width: 150px;
  margin-left: 20%;
  margin-top: 20px
}
}

// 公用弹框样式
   .dialog-style {
/deep/ .el-dialog__body {
  padding: 10px;
}
/deep/ .el-dialog__header {
  background: rgba(204, 204, 204, 0.18);
  padding: 10px;
}
/deep/ .el-dialog__title {
  font-weight: 500;
  font-size: 15px
}
/deep/ .el-dialog__headerbtn {
  top: 13px;
}
/deep/ .el-dialog__footer {
  background: rgba(204, 204, 204, 0.18);
  padding-right: 15px;
  padding: 11px;
}
/deep/ .el-button + .el-button {
  margin-left: 6px;
}
}

.table-style{
/deep/ .el-table__fixed-right-patch {
  background-color:#F8F8F9;
}
}
/deep/ .el-table .el-table__header-wrapper th{
  height:31px;
}

/deep/ .el-list-enter-active,
/deep/ .el-list-leave-active {
  transition: none;
}

/deep/ .el-list-enter,
/deep/ .el-list-leave-active {
  opacity: 0;
}
</style>

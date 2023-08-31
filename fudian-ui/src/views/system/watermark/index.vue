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
          v-hasPermi="['system:watermark:search']"
          @submit.native.prevent
        >
          <el-form-item label="水印名称" prop="fileName">
            <el-input
              v-model="params.name"
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
            <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQueryBorrowing('borrowingParams')">重置</el-button> -->
          </el-form-item>
        </el-form>

      </el-row>
      <el-row :span="24" style="padding: 10px 0px;">
          <el-button type="primary" size="mini" v-hasPermi="['system:watermark:add']" icon="el-icon-plus" @click="addWindows">新增</el-button>
          <el-button type="primary" size="mini" v-hasPermi="['system:watermark:delete']" icon="el-icon-download" @click="deleteStorage">删除</el-button>
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
        <el-table-column style="width: 20px" show-overflow-tooltip prop="image" type="text" align="center" label="水印内容">
          <template slot-scope="scope">
              <img @click="openImgWindows(scope.row.image)" v-if="scope.row.type == '1'" :src="'data:image/png;base64,' + scope.row.image" style="width: 20px;height: 20px"/>
              <span v-if="scope.row.type == '0'"> {{scope.row.text}} </span>
          </template>
        </el-table-column>
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
        <el-table-column label="操作" align="center" width="110" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" size="mini" v-hasPermi="['system:watermark:stop']" v-if="scope.row.active == '1'" @click="disable(scope.row)">停用</el-button>
            <el-button type="text" size="mini" v-hasPermi="['system:watermark:active']" v-if="scope.row.active != '1'" @click="active(scope.row)">启用</el-button>
            <el-button type="text" size="mini" v-hasPermi="['system:watermark:edit']" @click="electronicFeedback(scope.row)">编辑</el-button>
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
      style="margin-top: 8%"
      width="30%"
      class=" dialog-style dialog-basic"
      :destroy-on-close="true"
      :append-to-body="true"
      :close-on-click-modal="false"
      :visible.sync="approvalProcessingShow">

      <div style="">
        <el-form ref="fileStorageForm" :model="fileStorageForm" :rules="formRules" style="margin-left: 10%;margin-right: 10%"
                 label-width="120px">
          <el-form-item prop="name" label="水印名称">
            <el-input size="mini" placeholder="请输入水印名称" clearable v-model="fileStorageForm.name"/>
          </el-form-item>
          <el-form-item prop="type" label="水印类型">
            <el-select size="mini" style="width: 100%;" clearable v-model="fileStorageForm.type" placeholder="请选择水印类型">
              <el-option
                v-for="item in watermarkTypeOptions"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="text" label="文字内容" v-if="fileStorageForm.type == '0'">
            <el-input size="mini" v-model="fileStorageForm.text" placeholder="请输入文字内容"/>
          </el-form-item>

          <!--:limit = "parseInt('1')"-->
            <el-upload
              class="upload-demo"
              style="margin-left: 100px"
              ref="upload"
              :action="action"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :file-list="fileList"
              :on-change="handleChange"
              :auto-upload="false"
              :on-success="uploadSuccess"
              :on-error="uploadError"
              :data="fileStorageForm" v-if="fileStorageForm.type == '1'">
              <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          <img :src="'data:image/png;base64,' + fileStorageForm.image" v-if="fileStorageForm.image && ''!==fileStorageForm.image && fileStorageForm.type == '1' && (($refs.upload && !$refs.upload.uploadFiles[0]) || (!$refs.upload))" style="width: 40px;height: 40px;margin-left: 100px">

        </el-form>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="approvalProcessingShow=false">取 消</el-button>
        <el-button size="mini" type="primary" @click="submitUpload()">保 存</el-button>

      </div>
    </el-dialog>

    <!--  图片放大  -->
    <el-dialog
      title="水印预览"
      style="margin-top: 8%"
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
  import {downLoad, downLoadPost} from '@/downLoad/filedownload'
  import {selectAll,insert,deleteStorage,updateById,active,disable} from '@/api/system/watermark'
  import {getTreeByDictType} from '@/api/system/dict/data'
  import {MessageBox} from 'element-ui'
  const params = {
    pageNum: 1,
    pageSize: 20
  }
  const baseUrl = process.env.VUE_APP_BASE_API;
  export default {
    data() {
      return {
        // 存储类型数据字典
        watermarkTypeOptions: [],
        approvalProcessingShow: false,
        imgWindows:false,
        imgCss:{},
        showBorrowing: true,
        borrowingParams: {},
        fileStorageForm: {
          name:'',
          type:'',
          text:'',
          image:''
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
          name: [
            {required: true, message: '请输入水印名称', trigger: 'blur'},
            {min: 1, max: 10, message: '输入内容不得超过10个字符', trigger: 'blur'}
          ],
          type: [
            {required: true, message: '请选择水印类型', trigger: 'blur'},
          ],
          text: [
            {required: true, message: '请输入水印内容', trigger: 'blur'},
            {min: 1, max: 20, message: '输入内容不得超过20个字符', trigger: 'blur'}
          ]
        },

        //校验规则
        rulesUtil:{
          //判空
          notNull:{
            required: true,
            message: '不可为空',
            trigger: 'blur'
          },
          //判断长度（长）
          lengthLong:{
            min: 0,
            max: 40,
            message: '长度不可大于40',
            trigger: 'blur'
          },
          //判断长度（短）
          lengthShort:{
            min: 0,
            max: 18,
            message: '长度不可大于18',
            trigger: 'blur'
          },
          //判断长度（非常长）
          lengthLongLong:{
            min: 0,
            max: 500,
            message: '长度不可大于500',
            trigger: 'blur'
          },
          //判断数字长度（短）
          lengthNumShort:{
            type: 'number',
            min: 1,
            max: 999,
            message: '请输入0-999之间的数值',
            trigger: 'blur'
          },
          lengthNumLong:{
            type: 'number',
            min: 1,
            max: 999999,
            message: '请输入0-999999之间的数值',
            trigger: 'blur'
          },
          //判断是否为数字
          isNumber:{
            type: 'number',
            message: '该值为数字',
            trigger: 'blur'
          },
          //可空数字校验
          isNullNumLongLong:{
            validator:(rule,value,callback)=>{
              if(value != ""){
                if((/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/).test(value) == false){
                  callback(new Error("请填写大于0的数字"));
                }else{
                  if(value>0&&value<999){
                    callback();
                  }else{
                    callback(new Error("请填写大于0并且小于999的数字"));
                  }

                }
              }else{
                callback();
              }

            },
            trigger:'change'
          },
          //ip校验
          ipRule:{
            validator:(rule,value,callback)=>{
              if(value === ""){
                  callback(new Error("ip地址不能为空"));
              }else{
                if(( /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/).test(value)){
                  callback();
                }else{
                  callback(new Error("请填写正确的ip地址"));
                }
              }

            },
            trigger:'change'
          }
        },

        params : {
          pageNum: 1,
          pageSize: 20
        },

        pendingApprovalCol: [
          {label: '水印名称', prop: 'name', isCol: true, width: "110px"},
          {label: '水印类型', prop: 'type', isCol: true, width: "110px", formatter:this.typeFormat},
          {label: '文字颜色', prop: 'color', isCol: false, width: "110px"},
          {label: '透明度', prop: 'diaphaneity', isCol: false, width: "110px"},
          {label: '旋转角度', prop: 'rotate', isCol: false, width: "110px"},
          {label: '文字大小', prop: 'fontSize', isCol: false, width: "200px"},
          {label: '行间距', prop: 'rowLength', isCol: false, width: "200px"},
          {label: '列间距', prop: 'colLength', isCol: false, width: "150px"},
          {label: '创建时间', prop: 'createTime', isCol: true, width: "200px"},
          {label: '创建人姓名', prop: 'createName', isCol: true, width: "150px"},
          {label: '更新时间', prop: 'updateTime', isCol: true, width: "200px"},
          {label: '更新人姓名', prop: 'updateName', isCol: true, width: "150px"},
        ],
      }
    },
    methods: {
      //自定义序号
      customIndex(index){
        return (this.pageNum-1)*(this.pageSize)+(index+1);
      },
      handleChange(file, fileList) {
        if (fileList.length > 0) {
          this.fileList = [fileList[fileList.length - 1]]  // 这一步，是 展示最后一次选择的csv文件
        }
      },
      //新增配置
      addStorageConfig() {
        const _this = this;
        if (_this.fileStorageForm.id) {
          updateById(_this.fileStorageForm)
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
        } else {
          insert(_this.fileStorageForm)
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
        }

      },
      //删除配置
      deleteStorage() {
        const _this = this;
        if (this.electronicCheck.length == 0) {
          _this.$message({
            message: '请至少选择一条记录',
            type: 'warning'
          })
          return;
        }
        let ids = [];
        this.electronicCheck.map(item => {
          ids.push(item.id);
        })
        MessageBox.confirm(
          '是否删除选中的' + this.electronicCheck.length + '条数据',
          '系统提示',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          deleteStorage({ids:JSON.stringify(ids)}).then(response => {
            if (response.code == 200) {
              _this.$message({
                message: response.msg,
                type: 'success'
              })
              _this.loadGrid(params);
            }
          })
        })
      },
      //激活配置
      active(row) {
        const _this = this;
        active({id:row.id})
          .then(function (response) {
            _this.loadGrid(params);
            if (response.code == 200) {
              _this.$message({
                showClose: true,
                message: response.msg,
                type: 'success'
              })
            } else {
              _this.$message({
                showClose: true,
                message: response.msg,
                type: 'error'
              })
            }
          })
      },
      //停用配置
      disable(row) {
        const _this = this;
        disable({id:row.id})
          .then(function (response) {
            _this.loadGrid(params);
            if (response.code == 200) {
              _this.$message({
                showClose: true,
                message: response.msg,
                type: 'success'
              })
            } else {
              _this.$message({
                showClose: true,
                message: response.msg,
                type: 'error'
              })
            }
          })
      },

      submitUpload() {
        const _this = this;
        this.$refs["fileStorageForm"].validate((valid) => {
            if (valid) {
              if (_this.fileStorageForm.type === '1') { //图片上传

                if (!_this.fileStorageForm.id || '' == _this.fileStorageForm.id) { //新增
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
                    for (let key in _this.fileStorageForm) {
                      data[key] = _this.fileStorageForm[key];
                    }
                    data.image=null;
                    updateById(data)
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
                    if (this.$refs.upload.uploadFiles[0]) {
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

              } else { //文字上传
                if (!this.fileStorageForm.id || "" === this.fileStorageForm.id) { //新增
                  insert(_this.fileStorageForm)
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
                } else {//修改
                  updateById(_this.fileStorageForm)
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
      electronicFeedback(val) {
        const _this = this;
        _this.action = baseUrl+'/watermark/updateById'
        _this.titleName = "编辑"
        _this.fileStorageForm.id = val.id;
        _this.fileStorageForm.name = val.name;
        _this.fileStorageForm.text = val.text;
        _this.fileStorageForm.image = val.image;
        _this.fileStorageForm.type = val.type;
        _this.approvalProcessingShow = true;
      },
      formatForm() {
        const  _this = this;
        _this.fileStorageForm={
          name:'',
          type:'',
          text:'',
          image:''
        };
      },
      onEnterSearch() {
        this.handleQueryBorrowing();
      },
      //搜索
      handleQueryBorrowing() {
        const _this = this;
        params.name = this.params.name;
        params.pageNum = 1;
        this.pageNum =1;
        _this.loadGrid(params);
      },
      exportExcel() {
        this.$confirm('是否确认导出Excel?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          // 暂时注释 谁写这个功能谁写
          // if(this.electronicCheck.length==0){
          //   // 在这里通过调用查询列表的方法查询数据返回传入后台 导出Excel
          // }else{
          //   // 传入选中的数据到后台 导出Excel
          // }
          // 暂时传入假数据
          let row = {
            row: this.electronicCheck,
            status: 20
          }
          downLoadPost("/export/excel/exportClick", row)
        });
      },
      //重置表单
      resetQueryBorrowing() {
        this.borrowingParams.userName = "";
        this.borrowingParams.startTime = "";
        this.borrowingParams.endTime = "";
        // this.archivesaFunction({
        //     pageNum: 0,
        //     pageSize: this.pageSize,
        //     status:this.applyStates,
        // })
      },
      addWindows() {
        const _this = this;
        _this.titleName = "新增"
        _this.action = baseUrl+'/watermark/insert';
        _this.fileStorageForm = {};
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

      /*--- 格式化字典项 ---*/
      typeFormat(row, col) {
        return this.selectDictLabel(this.watermarkTypeOptions, row.type);
      },

      loadGrid(params){
        const _this = this;
        selectAll(params)
          .then(function (response) {
            _this.BorrowingData = response.rows
            _this.total = response.total
          })
      }
    },
    created() {
      const _this = this;
      /*--- 查询字典表 ---*/
      getTreeByDictType({transValue:"watermark_type"}).then(response => {
        this.watermarkTypeOptions = response.data;
      });

      /*--- 查询配置列表 ---*/
      _this.loadGrid(params);
    },
    mounted() {
      /*--- 表单校验 ---*/
      // this.formRules = {
      //   name:[this.rulesUtil.notNull],
      //   type:[this.rulesUtil.notNull]
      // };

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

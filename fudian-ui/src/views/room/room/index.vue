<template>
  <div class="app-find">
    <el-row style="height: 100%">
      <el-col :span="24" style="padding: 10px;height: 100%">
        <el-row :gutter="10" ref="searchRow"  >
          <el-form
            v-hasPermi="['room:room:search']"
            v-if="showSearchBox"
            ref="queryForm"
            class="search-form"
            :model="queryForm"
            :inline="true"
            label-width="68px"
            style="background:rgba(204, 204, 204, 0.18);"
          >
            <el-form-item label="库房名称"  prop="e01zc24a">
              <el-input v-model="queryRoomForm.roomName" @keyup.enter.native="search" placeholder="请输入库房名称" clearable size="mini" style="width: 150px" />
            </el-form-item>
            <el-form-item label="库房地址" style="margin-left: 20px" prop="e01zc24a">
              <el-input v-model="queryRoomForm.roomLocation" @keyup.enter.native="search" placeholder="请输入库房地址" clearable size="mini" style="width: 150px" />
            </el-form-item>
            <el-form-item style="margin-left: 20px">
              <el-button type="primary" v-hasPermi="['room:room:search']" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
<!--              <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>-->
            </el-form-item>
          </el-form>
          <el-col :span="24" style="padding:10px 0;margin-left: 5px">
            <el-button type="primary" v-hasPermi="['room:room:add']" icon="el-icon-plus" size="mini" @click="handleAdd()">新增</el-button>
            <el-button type="primary" v-hasPermi="['room:room:edit']" icon="el-icon-edit" size="mini" @click="handleUpData()">编辑</el-button>
            <el-button type="primary" v-hasPermi="['room:room:delete']" icon="el-icon-delete" size="mini" @click="deleteData()">删除</el-button>
            <el-button type="primary" icon="el-icon-download" size="mini" v-hasPermi="['room:room:export']" @click="exportExcel()">导出</el-button>
          </el-col>

          <!--          <el-col :span="1.5"><el-button type="success" icon="el-icon-edit" size="mini" @click="handleUpData">编辑</el-button></el-col>-->
<!--          <el-col :span="1.5"><el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete">删除</el-button></el-col>-->
<!--          <el-dropdown style="float: right; margin-right: 30px;">-->
<!--            <el-button icon="el-icon-menu" size="mini" circle />-->
<!--            <el-dropdown-menu style="padding: 10px">-->
<!--              <el-checkbox-group v-for="(item, index) in col" v-model="col[index].isCol" size="mini">-->
<!--                <el-checkbox>{{ item.label }}</el-checkbox>-->
<!--              </el-checkbox-group>-->
<!--            </el-dropdown-menu>-->
<!--          </el-dropdown>-->
<!--          <el-col :span="1.5" style="float: right;">-->
<!--            <el-button type="primary" icon="el-icon-search" size="mini" circle @click="function(){showSearchBox = !showSearchBox;tableHeight = showSearchBox?'73%':'85%'}" />-->
<!--          </el-col>-->
        </el-row>
          <el-table
            ref="table"
            v-loading="loading"
            :height="tableHeight"
            size="mini"
            :data="tableData"
            border row-key="id"
            align="left"
            stripe
            @row-click="StepsListRowClick"
            tooltip-effect="light"
            :header-cell-style="headClass"
            @current-change="handleCurrentChange"
            @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="40" />
                      <el-table-column
                        label="序号"
                        type="index"
                        align="center"
                        :index="customIndex"
                        width="50">
                      </el-table-column>
          <el-table-column align="center" show-overflow-tooltip  v-for="(item, index) in col"  :width="item.width" v-if="col[index].isCol" :key="index"  :formatter="item.format" :prop="col[index].prop"  :label="item.label" />
          <el-table-column  align="center" prop="znum" :formatter="numFormat" label="总容量" />
          <el-table-column  align="center" prop="cz"  label="操作" width="200px">
            <template slot-scope="scope">

              <el-button type="text" v-hasPermi="['room:room:struct']" size="mini" @click="structure(scope.row)">库房结构管理</el-button>
              <el-button type="text" v-hasPermi="['room:room:archives']" size="mini" @click="archives(scope.row)">实体档案管理</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="page" style="margin-top: 5px;">
          <el-pagination
            :page-sizes="[20, 50, 100, 200]"
            :page-size="queryForm.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="queryForm.total"
            :current-page="queryForm.pageNum"
            @size-change="pageSize"
            @current-change="pageCurrent"
          />
        </div>
      </el-col>
      <el-dialog title="新增库房" style="margin-top: 8%" :destroy-on-close="true"
                 :append-to-body="true" :close-on-click-modal="false" :visible.sync="dialogFormVisible"  class=" dialog-style dialog-basic">
        <el-form status-icon :rules="formRules" :inline="true" ref="addForm" :model="form">
        <div style="overflow: hidden;">
<!--          <el-col :span="" style="padding:0 10px;border-right: 1px solid #CCCCCC;">-->
            <p style="padding-left: 15px;margin-top: 0;border-bottom: 1px solid #CCCCCC;">库房信息</p>

              <el-form-item prop="roomName" style="margin-top: 20px" size="mini" label="库房名称" :label-width="formLabelWidth"><el-input v-model="form.roomName" autocomplete="off" /></el-form-item>
              <el-form-item prop="roomArea" style="margin-top: 20px" size="mini" label="库房面积" :label-width="formLabelWidth"><el-input v-model.number="form.roomArea" autocomplete="off" /></el-form-item>
              <el-form-item prop="roomLocation" style="margin-top: 20px" size="mini" label="库房地址" :label-width="formLabelWidth"><el-input v-model="form.roomLocation" autocomplete="off" /></el-form-item>
              <el-form-item prop="roomManager" style="margin-top: 20px" size="mini" label="库房管理员" :label-width="formLabelWidth"><el-input v-model="form.roomManager" autocomplete="off" /></el-form-item>
              <el-form-item prop="remark" style="margin-top: 20px" size="mini" label="备注" :label-width="formLabelWidth"><el-input v-model="form.remark" type="textarea" /></el-form-item>

<!--          </el-col>-->
<!--          <el-col :span="12" style="padding:0 10px;">-->
            <p style="padding-left: 15px;margin-top: 0;border-bottom: 1px solid #CCCCCC;">
              库房结构快速定义(选填)
              <el-tooltip class="item" effect="dark"  placement="top">
                <div slot="content">
                  库房结构快速定义表单只可全部不填写或者全部填写，
                  <br/>
                  如果库房下存在库房结构，则不可填写。
                  <br/>
                  列数：该库房下总共多少列
                  <br/>
                  节数：每列下总共几节
                  <br/>
                  层数：每节下总共几层
                  <br/>
                  层存放数：每层存放多少本档案

                </div>
                <i class="el-icon-warning"></i>
              </el-tooltip>

<!--              <el-popover-->
<!--              placement="top-start"-->
<!--              title="标题"-->
<!--              width="200"-->
<!--              trigger="hover"-->
<!--              content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。">-->
<!--                -->
<!--            </el-popover>-->
            </p>
<!--            <el-form  :model="form">-->
              <el-form-item label="列数" size="mini" style="margin-top: 20px" prop="columns" :label-width="formLabelWidth"><el-input v-model="form.columns" autocomplete="off" /></el-form-item>
              <el-form-item label="节数" size="mini" style="margin-top: 20px" prop="sectionnum" :label-width="formLabelWidth"><el-input v-model="form.sectionnum" autocomplete="off" /></el-form-item>
              <el-form-item label="层数" size="mini" style="margin-top: 20px" prop="layernum" :label-width="formLabelWidth"><el-input v-model="form.layernum" autocomplete="off" /></el-form-item>
              <el-form-item label="层存放数" size="mini" style="margin-top: 20px" prop="num" :label-width="formLabelWidth"><el-input v-model="form.num" autocomplete="off" /></el-form-item>
<!--            </el-form>-->
<!--          </el-col>-->
        </div>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogFormVisible = false">取消</el-button>
          <el-button size="mini" type="primary" @click="addRoom('addForm')">保存</el-button>
        </div>
      </el-dialog>

      <el-dialog title="编辑库房" :destroy-on-close="true"
                 :append-to-body="true" :close-on-click-modal="false" style="margin-top: 8%" class=" dialog-style dialog-basic" :visible.sync="dialogFormVisible1">
        <el-form :model="form" :inline="true" :rules="formRules" ref="updateForm">
        <div style="overflow: hidden;">
<!--            <h2 style="padding-left: 15px;margin-top: 0;border-bottom: 1px solid #CCCCCC;">库房信息</h2>-->

              <el-form-item style="margin-top: 20px" prop="roomName"  label="库房名称" :label-width="formLabelWidth"><el-input size="mini" v-model="form.roomName" autocomplete="off" /></el-form-item>
              <el-form-item style="margin-top: 20px" prop="roomArea" label="库房面积" :label-width="formLabelWidth"><el-input size="mini" v-model.number="form.roomArea" autocomplete="off" /></el-form-item>
              <el-form-item style="margin-top: 20px" prop="roomLocation" label="库房地址" :label-width="formLabelWidth"><el-input size="mini" v-model="form.roomLocation" autocomplete="off" /></el-form-item>
              <el-form-item style="margin-top: 20px" prop="roomManager" label="库房管理员" :label-width="formLabelWidth"><el-input size="mini" v-model="form.roomManager" autocomplete="off" /></el-form-item>
              <el-form-item style="margin-top: 20px" prop="remark" label="备注" :label-width="formLabelWidth"><el-input size="mini" v-model="form.remark" type="textarea" /></el-form-item>

<!--            <h2 style="padding-left: 15px;margin-top: 0;border-bottom: 1px solid #CCCCCC;">库房结构快速定义(选填)-->
<!--              <el-tooltip class="item" effect="dark"  placement="top">-->
<!--                <div slot="content">-->
<!--                  库房结构快速定义表单只可全部不填写或者全部填写，-->
<!--                  <br/>-->
<!--                  如果库房下存在库房结构，则不可填写。-->
<!--                  <br/>-->
<!--                  列数：该库房下总共多少列-->
<!--                  <br/>-->
<!--                  节数：列下总共几节-->
<!--                  <br/>-->
<!--                  层数：节下总共几层-->
<!--                  <br/>-->
<!--                  层存放数：每层存放多少本档案-->

<!--                </div>-->
<!--                <i class="el-icon-warning"></i>-->
<!--              </el-tooltip>-->
<!--            </h2>-->

<!--&lt;!&ndash;            <el-form :model="form">&ndash;&gt;-->
<!--              <el-form-item style="margin-top: 20px" prop="columns" label="列数"  :label-width="formLabelWidth"><el-input :disabled="this.structDisable"  v-model="form.columns" autocomplete="off" /></el-form-item>-->
<!--              <el-form-item style="margin-top: 20px" prop="sectionnum" label="节数"  :label-width="formLabelWidth"><el-input :disabled="this.structDisable" v-model="form.sectionnum" autocomplete="off" /></el-form-item>-->
<!--              <el-form-item style="margin-top: 20px" prop="layernum" label="层数"  :label-width="formLabelWidth"><el-input :disabled="this.structDisable" v-model="form.layernum" autocomplete="off" /></el-form-item>-->
<!--              <el-form-item style="margin-top: 20px" prop="num" label="层存放数" :label-width="formLabelWidth"><el-input :disabled="this.structDisable" v-model="form.num" autocomplete="off" /></el-form-item>-->
        </div>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogFormVisible1 = false">取消</el-button>
          <el-button size="mini" type="primary" @click="saveUpdate('updateForm')">保存</el-button>
        </div>
      </el-dialog>
    </el-row>
  </div>
</template>

<script>

  import { queryRoomList,insertRoom,updateRoom,checkRoom,deleteRoom}  from "@/api/room/room";
  import { MessageBox,Notification } from 'element-ui'
  import {downLoad, downLoadPost} from '@/downLoad/filedownload'
  var reg = /^[0-9]*$/;
  var yaer = (rule, value, callback) => {
    if (value === '') {
       callback(new Error('请输入库房面积'))
    } else {
      if (!reg.test(value)) {
        callback(new Error('只能输入数字'))
      }
      if(value<1){
        callback(new Error('最小面积是1'))
        return
      }

    }
  };
export default {
  watch: {
    tableData: {
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(() => {
          _this.$refs.table.doLayout();

        })
      },
      deep: true,

    }
  },
  data() {

    return {
      tableHeight:'73%',
      loading:true,

      /* 全选 */
      isIndeterminate: true,
      dialogFormVisible: false,
      dialogFormVisible1: false,
      form: {
        roomName: '',
        roomManager: '',
        roomArea: '',
        remark: '',
        roomLocation: '',
        columns: '',
        layernum: '',
        sectionnum: '',
        num:''
      },
      structDisable:false,
      rulesUtil:{
        //判空
        notNull:{
          required: true,
          message: '输入内容不可为空',
          trigger: 'blur'
        },
        //判断长度（长）
        lengthLong:{
          min: 0,
          max: 40,
          message: '输入内容不得超过40个字符',
          trigger: 'blur'
        },
        //判断长度（短）
        lengthShort:{
          min: 0,
          max: 18,
          message: '输入内容不得超过18个字符',
          trigger: 'blur'
        },
        //判断长度（非常长）
        lengthLongLong:{
          min: 0,
          max: 200,
          message: '输入内容不得超过200个字符',
          trigger: 'blur'
        },
        //判断数字长度（短）
        lengthNumShort:{
          min: 1,
          max: 999,
          message: '请输入0-999的数字',
          trigger: 'blur'
        },
        lengthNumLong:{
          min: 1,
          max: 999999,
          message: '请输入0-999999得数字',
          trigger: 'blur'
        },
        //判断是否为数字
        isNumber:{
          type:'number',
          message: '只能输入数字',
          trigger: 'blur'
        },
        //可空数字校验
        isNullNumLongLong:{
          validator:(rule,value,callback)=>{
            if(value != ""){
              if((/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/).test(value) == false){
                callback(new Error("请输入大于0的数字"));
              }else{
                if(value>0&&value<999){
                  callback();
                }else{
                  callback(new Error("请输入0-999的数字"));
                }

              }
            }else{
              callback();
            }

          },
          trigger:'change'
        }
      },
      formRules:{
        roomName:[
          { required: true, message: '输入内容不可为空', trigger: 'blur' },
          { min: 0, max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
        ],
        roomLocation:[
          { required: true, message: '输入内容不可为空', trigger: 'blur' },
          { min: 0, max: 40, message: '输入内容不得超过40个字符', trigger: 'blur' }
        ],
        roomArea:[
          { type: 'number' ,required: true, message: '输入内容不可为空', trigger: 'blur' },
          { type: 'number', message: '只能输入数字', trigger: 'blur'},
          { type: 'number', min: 1, max: 999999, message: '请输入1-999999的数字', trigger: 'blur' }
        ],
        roomManager:[
          { required: true, message: '输入内容不可为空', trigger: 'blur' },
          { min: 0, max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
        ],
        remark:[
          { min: 0, max: 200, message: '输入内容不得超过200个字符', trigger: 'blur' }
        ],
        columns:[
          { min: 0, max: 200, message: '输入内容不得超过200个字符', trigger: 'blur' }
        ],
        sectionnum:[
          { min: 0, max: 200, message: '输入内容不得超过200个字符', trigger: 'blur' }
        ],
        layernum:[
          { min: 0, max: 200, message: '输入内容不得超过200个字符', trigger: 'blur' }
        ],
        num:[
          { min: 0, max: 200, message: '输入内容不得超过200个字符', trigger: 'blur' }
        ]
      },
      formLabelWidth: '130px',
      showSearchBox: true,
      queryRoomForm:{
        roomName: '',
        roomLocation: ''
      },
      queryForm: {
        pageNum:1,
        total:0,
        pageSize:20

      },
      col: [
        { label: '库房名称', wdith:'100px', prop: 'roomName',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
        { label: '库房编号', wdith:'100px', prop: 'code',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
        { label: '库房面积', wdith:'100px', prop: 'roomArea',format:function(row, column, cellValue, index){return cellValue + "平方米"}, isCol: true },
        { label: '库房地址', wdith:'100px', prop: 'roomLocation', format:function(row, column, cellValue, index){return cellValue},isCol: true },
        { label: '库房管理员', wdith:'100px', prop: 'roomManager',format:function(row, column, cellValue, index){return cellValue}, isCol: true },
        { label: '总列数', wdith:'100px', prop: 'columnNum',format:function(row, column, cellValue, index){return cellValue + "列"}, isCol: true },
        { label: '总节数', wdith:'100px', prop: 'sectionNum',format:function(row, column, cellValue, index){return cellValue + "节"}, isCol: true },
        { label: '总层数', wdith:'100px', prop: 'layerNum',format:function(row, column, cellValue, index){return cellValue + "层"}, isCol: true },
        { label: '已占用',wdith:'100px', prop: 'curNum', format:function(row, column, cellValue, index){return cellValue + "卷"},isCol: true },
        { label: '剩余容量',wdith:'100px', prop: 'allowance',format:function(row, column, cellValue, index){return cellValue + "卷"}, isCol: true }
      ],
      tableData: [
      ],
      select:[],
      searchRowHeight:'',
      searchRow:'searchRow'
    }
  },
  created() {
    this.getRoomList();
  },
  mounted() {

    this.$nextTick(()=>{

      let _this = this;
      setTimeout(()=>{

        _this.searchRowHeight = _this.$refs[_this.searchRow].$el.offsetHeight
        _this.tableHeight = _this.tableHeight = (document.body.clientHeight - (84 + 52 + _this.searchRowHeight)) + "px"
      },200)

    })

    window.onresize = () => {
      return (() => {
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 52 + this.searchRowHeight)) + "px"
      })();
    };

  },
  methods: {
    //自定义序号
    customIndex(index){
      return (this.queryForm.pageNum-1)*(this.queryForm.pageSize)+(index+1);
    },
    exportExcel(){
      if(this.select.length == 0){
        this.$confirm('是否导出搜索后的库房数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.queryForm.pageSize = 99999999;
          queryRoomList(this.queryForm).then(response =>{
            response.rows.map((item,index)=>{
              let indexs = (index + 1).toString();
              if(index.toString().length<3){
                for(let i = 0;i<3-index.toString().length;i++){
                  indexs = '0' + indexs;
                }
              }
              item.code = indexs
            })
            if(response.rows.length==0){
              this.$message.error("无数据,导出失败");
              return;
            }
            console.log(response.rows)
            let row = {
              row:response.rows,
              status:1
            }
            downLoadPost("/export/excel/exportClick",row,this );
          })
        })
      }else{
        this.$confirm('是否导出选中的库房数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let row = {
            row:this.select,
            status:1
          }
          downLoadPost("/export/excel/exportClick",row,this )
        })

      }

    },

    StepsListRowClick(row){    //档案管理-点击行事件-方法
      this.$refs.table.clearSelection()
      if (this.select.length === 0) {
        this.$refs.table.toggleRowSelection(row)
        return;
      }
      this.$refs.table.toggleRowSelection(row)
    },
    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
    },
    //进入库房内档案管理
    archives(val){
      this.$router.push({
        name:'实体档案管理',
        params:{roomId:val.id,roomName:val.roomName,info:'已占用' + val.curNum + ',剩余' + val.allowance}

      })
    },
    //保存修改
    saveUpdate(formNames){
      this.$refs[formNames].model.roomArea = Number(this.$refs[formNames].model.roomArea);
      this.$refs[formNames].validate((valid)=>{
        if(valid){
          if(!this.checkStruct()){
            this.$message({
              message: '快速定义表单只可全填或不填',
              type: 'warning'
            });
          }else{
            updateRoom(this.form).then(response=>{
              if(response.code==200){
                this.getRoomList();
                this.resetForm(formNames);
                this.dialogFormVisible1 = false;
                this.$message({
                  message: response.msg,
                  type: 'success'
                });
              }
            })
          }

        }
      })
    },
    //保存添加
    addRoom(formNames){
      console.log(this.form);
      this.$refs[formNames].validate((valid)=>{
        if(valid){
          if(!this.checkStruct()){
            this.$message({
              message: '快速定义表单只可全填或不填',
              type: 'warning'
            });
          }else{
            insertRoom(this.form).then(response=>{
              if(response.code==200){
                this.getRoomList();
                this.resetForm(formNames);
                this.dialogFormVisible = false;
                this.$message({
                  message: response.msg,
                  type: 'success'
                });
              }
            })
          }

        }
      })
    },
    //检查库房内是否存在结构
    checkStruct(){
      if(this.form.sectionnum==''&&this.form.columns==''&&this.form.layernum==''&&this.form.num==''){
        return true;
      }
      if(this.form.sectionnum!=''&&this.form.columns!=''&&this.form.layernum!=''&&this.form.num!=''){
        return true;
      }
      return false;
    },
    numFormat(row,column){
      return row.curNum + row.allowance + "卷";
    },
    //初始化列表数据
    getRoomList(){
      this.loading = true;
      queryRoomList(this.queryForm).then(response =>{
        this.tableData = response.rows;
        response.rows.map((item,index)=>{
          let indexs = (index + 1).toString();
          if(index.toString().length<3){
            for(let i = 0;i<3-index.toString().length;i++){
              indexs = '0' + indexs;
            }
          }
          item.code = indexs
        })
        this.queryForm.total = response.total;
        this.loading = false;
      })
    },
    /* grid  行点击事件*/
    handleCurrentChange(val) {
      console.log(val)
    },
    /* 复选框事件 */
    handleSelectionChange(val) {
      this.select = val;
    },
    //删除库房
    deleteData(){
      if(this.select.length==0){
        this.$message({
          message:'请选择一个库房',
          type:'warning'
        })

      }
      if(this.select.length>1){
        this.$message({
          message:'请选择一个库房',
          type:'warning'

        })
        return ;
      }
      if(this.select.length==1){
        let val = this.select[0];
        checkRoom(val.id).then(response=>{
          if(response.code==200){
            //删除
            MessageBox.confirm(
              '是否删除' + val.roomName + '库房',
              '系统提示',
              {
                confirmButtonText: '是',
                cancelButtonText: '取消',
                type: 'warning',
                closeOnClickModal:false
              }
            ).then(() => {
              deleteRoom(val.id).then(response=>{
                if(response.code==200){
                  this.getRoomList();
                  this.$message({
                    message:response.msg,
                    type:'success'

                  })
                }
              })
            })

          }
        })
      }

    },
    /* 编辑 */
    handleUpData() {
      if(this.select.length==0){
        this.$message({
          message:'请选择一个库房',
          type:'warning'
        })

      }
      if(this.select.length>1){
        this.$message({
          message:'请选择一个库房',
          type:'warning'

        })
        return ;
      }
      if(this.select.length==1) {
        let val = this.select[0];
        this.structDisable = val.layerNum > 0;
        this.dialogFormVisible1 = !this.dialogFormVisible1
        this.formReset();
        // this.resetForm("updateForm");
        // this.form = Object.assign({},val);
        this.form.roomName = val.roomName;
        this.form.roomLocation = val.roomLocation;
        this.form.roomArea = val.roomArea;
        this.form.roomManager = val.roomManager;
        this.form.id = val.id;
        this.form.remark = val.remark;
      }
    },
    /* 机构管理 */
    structure(val) {
      this.$router.push({
        name:'结构管理',
        params:{roomId:val.id,roomName:val.roomName,roomCode:val.code}

      })
      // this.$router.addRoutes()
      // this.$router.replace({ name: 'Structure', params: { val: val }})
    },
    archivesTypeClick() {},
    /* 删除 */
    delete(val) {
      console.log(val)
    },
    //点击添加
    handleAdd() {

      this.dialogFormVisible = true;
      this.formReset();
      this.resetForm("addForm");

    },
    handleDelete() {},
    pageCurrent(val) {
      console.log(val)
      this.queryForm.pageNum = val;
      this.getRoomList();
    },
    pageSize(val) {
      console.log(val)
      this.queryForm.pageSize = val;
      this.queryForm.pageNum = 1;
      this.getRoomList();
    },
    //点击搜索
    search() {
      this.queryForm.pageNum = 1;
      this.queryForm.roomName = this.queryRoomForm.roomName;
      this.queryForm.roomLocation = this.queryRoomForm.roomLocation;
      this.getRoomList();
    },
    //重置搜索数据
    reset() {
      this.queryForm.roomLocation = "";
      this.queryForm.roomName = "";
      this.getRoomList();
    },
    //重置form数据
    formReset(){
      this.form.num = '';
      this.form.remark = '';
      this.form.roomManager = '';
      this.form.roomLocation = '';
      this.form.roomArea = '';
      this.form.roomName = '';
      this.form.columns = '';
      this.form.sectionnum = '';
      this.form.layernum = '';
    },
    //重置表单以及form数据
    resetForm(formName){
      this.$nextTick(()=>{
        this.$refs[formName].resetFields();
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.el-dialog__body {
  height: 500px;
  display: block;
}
.app-find {
  overflow: auto;
  height: calc(100vh - 86px);
}
.app-wait {
  overflow: auto;
  height: calc(100vh - 86px);
}
.el-form--inline .el-form-item {
margin:0px;
}
.cell .el-input__inner{
  background-color: #f5f7fa0d !important;
  border-color: #e4e7ed00 !important;
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
/deep/.el-table tbody tr:hover>td {
  background-color:#ecf5ff !important
}
.search-form{
  width:99%;
  background:rgba(204, 204, 204, 0.18);
  padding: 10px 0;
  padding-left: 20px;
  margin-right: 10px;
  margin-left: 5px;
  display:flex;
  .form-width{
    width:22%;
    display:flex;
  }
  /deep/ .el-form-item{
    margin-bottom: 0 !important;
  }
}
.page {
   float: right;
 }
</style>

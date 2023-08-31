<template>
  <div class="app-structure">
    <!-- 搜索框 -->
<!--    <el-form :inline="true" class="form-inline">-->
<!--      <el-form-item label="列名称">-->
<!--        <el-input placeholder="请输入内容" />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="节名称">-->
<!--        <el-input placeholder="请输入内容" />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="层名称">-->
<!--        <el-input placeholder="请输入内容" />-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" @click="onSubmit">查询</el-button>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="warning" @click="onSubmit">重置</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->


    <div style="width: 100%;height: 10px">
    </div>

    <el-col :span="10">
      <el-button  type="primary" icon="el-icon-plus" v-hasPermi="['room:sreuct:addcol']" size="mini" @click="addColumn">新增列级</el-button>
      <el-button style="margin-left: 15px" type="primary" icon="el-icon-edit" size="mini" v-hasPermi="['room:struct:edit']" @click="editColumn">编辑属性</el-button>
      <el-button style="margin-left: 15px" type="primary" icon="el-icon-edit" size="mini" v-hasPermi="['room:struct:delete']" @click="deleteStruct">删除</el-button>
    </el-col>
    <el-col :span="12">
      <span style="float:right;margin-right: 15px">库房结构 - {{$route.params.roomName}}</span>

    </el-col>

    <div style="width: 100%;height: 40px">
    </div>

    <el-table

      ref="treeGrid"
      :data="tableData"
      size="mini"
      :height="tableHeight"
      row-key="id"
      border
      lazy
      stripe
      @row-click="StepsListRowClick"
      tooltip-effect="light"
      :header-cell-style="headClass"
      @selection-change="handleSelectionChange"
      :load="load"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column type="selection" width="40" />
      <el-table-column
        prop="index"
        label="序号"
        :formatter="indexFormatter"
      />
      <el-table-column
        prop="node_name"
        label="别名"
        align="center"

      >
      </el-table-column>

      <el-table-column
        prop="code"
        label="编号"
        align="center"
      >

      </el-table-column>

      <el-table-column
        prop="nodeName"
        label="等级"
        align="center"
      />
      <el-table-column
        prop="num"
        label="总容量"
        align="center"
        :formatter="this.numFormat"
      />
      <el-table-column
        prop="curNum"
        align="center"
        label="已占用"
      />
      <el-table-column
        prop="allowance"
        align="center"
        label="剩余容量"
      />
      <el-table-column
        label="操作"
        align="center"
        width="100px"
      >

        <template slot-scope="scope">

          <el-button type="text" v-if="!scope.row.disable" @click="saveNodeName(scope.row)" size="small">
            保存
          </el-button>
          <el-button type="text" v-if="!scope.row.disable" @click="cancelEdit(scope.row)" size="small">
            取消
          </el-button>
          <el-button v-hasPermi="['room:struct:addsele']" type="text" v-if="scope.row.type<3&&scope.row.disable&&scope.row.type==1" @click="addStruct(scope.row)" size="small">
            <span v-if="">新增节级</span>
          </el-button>
          <el-button v-hasPermi="['room:struct:addlayer']" type="text" v-if="scope.row.type<3&&scope.row.disable&&scope.row.type==2" @click="addStruct(scope.row)" size="small">
            <span >新增层级</span>
          </el-button>
<!--          <el-button type="text"  v-if="scope.row.type==3&&scope.row.disable" @click="updateStructLayer(scope.row)" size="small">-->
<!--            修改层级-->
<!--          </el-button>-->
<!--          <el-button type="text" v-if="scope.row.disable" @click="deleteStruct(scope.row)" size="small">无</el-button>-->

        </template>
      </el-table-column>
    </el-table>


    <el-dialog :title="addTitle" :close-on-click-modal="false" :append-to-body="true" style="margin-top: 5%" :visible.sync="dialogFormVisible1" class=" dialog-style dialog-basic"
               width="50%">
      <div>
        <el-row >
        <el-form :model="form" ref="addform" label-width="120px" :inline="true" :rules="formRules">
          <el-col v-if="!formDisabled.sectionNum" :span="12">
          <el-form-item :span="12" prop="sectionNum"
                        >
            <span slot="label"><span style="color: red">*</span>节数</span>
            <el-input size="mini" width="100%" v-model.number="form.sectionNum" autocomplete="off"/>
          </el-form-item>
          </el-col>
          <el-col v-if="!formDisabled.layerNum" :span="12">
          <el-form-item :span="12"  prop="layerNum"  >
            <span slot="label"><span style="color: red">*</span>层数</span>
            <el-input size="mini" v-model.number="form.layerNum" autocomplete="off"/>
          </el-form-item>
          </el-col>
          <el-col  :span="12">
          <el-form-item :span="12" prop="num" >
            <span slot="label"><span style="color: red">*</span>层存放数</span>
            <el-input size="mini" v-model.number="form.num" autocomplete="off"/>
          </el-form-item>
          </el-col>
          <el-col :span="12">
          <el-form-item :span="12" label="别名"  prop="nickName" >
<!--            <slot style="width: " name="label"><span style="color: red">*</span>别名</slot>-->
            <el-input size="mini" v-model="form.nickName" autocomplete="off"/>
          </el-form-item>
          </el-col>
        </el-form>
        </el-row>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogFormVisible1 = false" >取消</el-button>
        <el-button size="mini" type="primary" @click="saveAddStruct('addform')" >保存</el-button>
      </span>
    </el-dialog>
    <el-dialog :title="title" :append-to-body="true" :close-on-click-modal="false"  style="margin-top: 5%" :visible.sync="dialogFormVisible" class=" dialog-style dialog-basic"
               width="30%">
          <div>

            <el-form :model="form2" ref="updateform" label-width="110px" :rules="formRules">
              <el-form-item   :hidden="form2.type!=3" label="*层已存放数" >
                <span slot="label">层已存放数</span>
                <el-input size="mini" :disabled="true" v-model.number="form2.curNum" autocomplete="off" />
              </el-form-item>
              <el-form-item prop="num" :hidden="form2.type!=3" label="*层剩余存放数" >
                <span slot="label"><span style="color: red">*</span>层剩余存放数</span>
                <el-input size="mini" v-model.number="form2.num" autocomplete="off" />
              </el-form-item>

              <el-form-item prop="nickname" label="别名" >
                <el-input size="mini" v-model="form2.nickName" autocomplete="off"/>
              </el-form-item>
              <el-form-item prop="nickname" label="编号" >
                <el-col :span="11">
                  <el-input  size="mini" :disabled="true" v-model="form2.discode" style="width: 100%;" autocomplete="off"/>
                </el-col>
                <el-col class="line" :span="2"><div style="text-align: center;">-</div></el-col>
                <el-col :span="11">
                  <el-input size="mini"   v-model="form2.code" style="width: 100%;" autocomplete="off"/>
                </el-col>
                <!--                <el-input v-model="form.code" autocomplete="off"/>-->
              </el-form-item>
            </el-form>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button size="mini" @click="dialogFormVisible = false" >取消</el-button>
            <el-button size="mini" type="primary" @click="saveUpateStruct('updateform')" >保存</el-button>
          </span>
    </el-dialog>
  </div>
</template>

<script>
import {queryRoomStruct,insertRoomStruct,updateStructName,updateRoomStruct,deleteRoomStruct,checkRoomStruct} from "@/api/room/struct"
import { Notification, MessageBox } from 'element-ui'
export default {
  watch:{
    tableData2:{
      handler(a,b){
        let _this = this;
        _this.$nextTick(()=>{
          _this.$refs.treeGrid.doLayout();

        })
      }
    }
  },
  data() {
    return {
      dialogFormVisibleEdit:false,
      title:'',
      editform:{
        nickname:'',
        nickname1:'',
        code:'',
        code1:'',
      },
      tableHeight:'0',
      addTitle:'',
      formDisabled:{
        sectionNum:false,
        layerNum:false
      },
      rulesUtil:{
        isNullNumLongLong:{
          validator:(rule,value,callback)=>{
            if(value != ""){
              if((/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/).test(value) == false){
                callback(new Error("请输入0-999的数字"));
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
        },
        lengthNumShort:{
          type: 'number',
          min: 0,
          max: 999,
          message: '请输入0-999的数字',
          trigger: 'blur'
        },
        lengthNumShort2:{
          type: 'number',
          min: 1,
          max: 999,
          message: '请输入1-999的数字',
          trigger: 'blur'
        }
      },
      formRules:{
        sectionNum:[],
        layerNum:[],
        num:[],
        nickName:[]
      },
      formRow:'',
      form: {
        sectionNum: '',
        layerNum:'',
        num:'',
        nickName:'',
        parentId:'',
        discode:'',
        code:'',
        style:{},
        roomId:this.$route.params.roomId
      },
      //编辑
      form2: {
        sectionNum: '',
        layerNum:'',
        num:'',
        nickName:'',
        parentId:'',
        discode:'',
        code:'',
        style:{},
        roomId:this.$route.params.roomId
      },
      formHeight:'60%',
      dialogFormVisible:false,
      dialogFormVisible1:false,
      formLabelWidth: '130px',
      tableData: [],
      tableData2:[],
      treeParam:{},
      roomId:this.$route.params.roomId,
      select:[],
      roomCode:this.$route.params.roomCode,
      searchRowHeight:'',
      searchRow:'searchRow'
    }
  },

  mounted() {

    this.$nextTick(()=>{

      let _this = this;
      setTimeout(()=>{

        // _this.searchRowHeight = _this.$refs[_this.searchRow].$el.offsetHeight
        _this.tableHeight = _this.tableHeight = (document.body.clientHeight - (84 + 55)) + "px"
      },200)

    })

    window.onresize = () => {
      return (() => {
        // this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 55)) + "px"
      })();
    };



    this.formRules.num.push(this.rulesUtil.lengthNumShort)
  },
  created() {
    this.getStruct();
  },

  methods: {
    //重置表单以及form数据
    resetForm(formName){
      let _this = this;
      this.$nextTick(()=>{
        _this.$refs[formName].resetFields();
        // if(typeof _this.$refs[formName] == 'undefined'){
        //   setTimeout(()=>{
        //
        //   },1000)
        // }

      })
    },
    indexFormatter(row){
      let prefix = "第";
      let suffix = "";
      if(row.type == 1){
        suffix = "列";
      }else if(row.type == 2){
        suffix = "节"
      }else{
        suffix = "层"
      }
      return prefix + row.index + suffix;
    },

    ishidden(){
      console.log(this.form.type!=3);
      return this.form.type!=3;
    },
    StepsListRowClick(row){    //档案管理-点击行事件-方法
      this.$refs.treeGrid.clearSelection()
      if (this.select.length === 0) {
        this.$refs.treeGrid.toggleRowSelection(row)
        return;
      }
      this.$refs.treeGrid.toggleRowSelection(row)
    },
    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
    },
    cancelEdit(row){
      row.node_name2 = row.node_name;
      row.editCode2 = row.editCode;
      row.disable = true;
    },
    editColumn(){
      if(this.select.length==0){
        this.$message({
          message:'请选择一条数据',
          type:'warning'
        })
        // this.$message({
        //   message: '快速定义表单只可全填或不填',
        //   type: 'warning'
        // });
      }else if(this.select.length>1){
        this.$message({
          message:'请选择一条数据',
          type:'warning'
        })
      }else{
        this.dialogFormVisible = true;
        // this.select.map(item=>{
          // item.disable = false;
          // this.dialogFormVisibleEdit = true;
          //初始化表单

        // this.resetForm("updateform");
          let row = this.select[0];
          let code = row.code.split("-");
          //
          this.title = '修改' + row.node_name;
          this.formRow = row;
          this.form2.id = row.uuid;
          this.form2.nodeId = row.id;
          this.form2.num = parseInt(row.allowance.toString());
          this.form2.type = row.type;
          this.form2.discode = row.code.toString().substring(0,row.code.toString().length - 3);
          this.form2.code = row.editCode;
          this.form2.code2 = row.editCode2;
          this.form2.nickName = row.node_name;
          this.form2.nickName2 = row.node_name2;
          this.formHeight = row.type==3?'60%':'40%';
          this.form2.curNum = row.curNum;

          this.formRules.layerNum = [];
          this.formRules.sectionNum = [];
          this.formDisabled.layerNum = true;
          this.formDisabled.sectionNum = true;
          //添加层存放数校验规则



        //修改的不为层则不进行校验
        console.log(row);
        console.log(this.form2);
        console.log(this.formRules);
        if(this.form2.type != 3 ){
          this.formRules.num = [

          ]
        }else{
          this.formRules.num = [
            {
              type: 'number',
              min: row.curNum,
              max: 999,
              message: '请输入'+ row.curNum +'-999的数字',
              trigger: 'blur'

            },
            this.rulesUtil.lengthNumShort
          ]
        }
      }
    },
    handleSelectionChange(val){
      this.select = val;
    },
    //修改别名
    saveNodeName(row){

      var myreg = /^\d{3}$/;
      if(!myreg.test(row.editCode2)){
        this.$message({
          message:'编号为最长三位的数字',
          type:'warning'
        })
        return;
      }



      if(row.node_name2==row.node_name&&row.editCode2==row.editCode){
        row.disable = true;
        return ;
      }else{

        console.log("fdsa");
        let param = {};
        param.roomId = this.form.roomId;
        param.type = row.type;
        param.parentId = row.id;
        if(row.node_name2!=row.node_name){
          param.nickName = row.node_name2;
        }
        if(row.editCode!=row.editCode2){
          param.code = row.editCode2;
        }

        //向后台发起请求修改别名
        updateStructName(param).then(response=>{
          if(response.code==200){

            this.$message({
              message:response.msg,
              type:'success'
            })
            this.flushTreeNodeByNodeId(row);
            this.flushSubTreeNodeByNodeId(row);
            row.disable = true;
          }
        })
      }
    },
    uneditNodeName(row){
      console.log(row);
      row.disable = true;
    },
    //行内编辑
    editNodeName(row){
      console.log(row);
      row.disable = false;
    },
    //删除节点方法
    deleteStruct(){
      if(this.select.length==0){
        this.$message({
          message:'请选择至少一个节点',
          type:'warning'
        })
        return;
      }
      let nodes = [];
      let msg = [];
      this.select.map(item=>{
        let node = {
          type : item.type,
          nodeId:item.id
        }
        msg.push(item.nodeName);
        nodes.push(node);
      })
      let params = {
        roomId:this.$route.params.roomId,

        nodes:JSON.stringify(nodes)
      }


      checkRoomStruct(params).then(response=>{
        if(response.code==200){
          MessageBox.confirm(
            '是否删除' + msg.join("、") +" 库房属性",
            '系统提示',
            {
              confirmButtonText: '是',
              cancelButtonText: '取消',
              type: 'warning'
            }
          ).then(()=>{
            deleteRoomStruct(params).then(response=>{
              this.$message({
                message:response.msg,
                type:'success'
              })
              // Notification.success({
              //   title: response.msg
              // })
              // this.getStruct();
              this.select.map(item=>{
                this.flushTreeNodeByNodeId(item);
              })
              // if(this.select.length==1){
              //   this.flushTreeNodeByNodeId(this.select[0]);
              // }else{
              //   this.getStruct();
              // }

            })
          })
        }
      })
    },
    //修改别名方法
    updateStructNickName(backcall){
      let param = {};
      param.roomId = this.form2.roomId;
      param.type = this.form2.type;
      param.parentId = this.form2.nodeId;
      console.log(this.form2);
      if(this.form2.nickName!=this.form2.nickName.nickName2){
        param.nickName = this.form2.nickName;
      }
      if(this.form2.code2!=this.form2.code){
        param.code = this.form2.code;

      }



      //向后台发起请求修改别名
      updateStructName(param).then(response=>{
        // if(response.code==200){
        //   this.$message({
        //     message:response.msg,
        //     type:'success'
        //   })
        // }
        backcall(response,this);
      })
    },
    //修改保存方法
    saveUpateStruct(formName){
      console.log("11111111");
      console.log(this.form);
      if(this.form2.code == ""){
        this.$message({
          type:'warning',
          message:'请填写编号'
        })
        return;
      }
      let reg = /^[0-9]+[0-9]*]*$/;
      let b = reg.test(this.form2.code);
      if(!b){
        this.$message({
          type:'warning',
          message:'编号为数字'
        })
        return;
      }
      let num = parseInt(this.form2.code);
      if(num<1||num>999){
        this.$message({
          type:'warning',
          message:'编号取值范围在1-999之间'
        })
        return;
      }
      console.log("22222")
      console.log(this.form2)
      this.$refs[formName].validate((valid)=>{
        if(valid){
          //修改层  可能会修改存放数
          if(this.form2.type==3){
            updateRoomStruct(this.form2).then(response=>{
              if(response.code==200){

                //判断是否修改别名及编号
                console.log(this.form);
                if(this.form2.code==this.form2.code2&&this.form2.nickName==this.form2.nickName2){
                  this.flushTreeNodeByNodeId(this.formRow);
                  this.formReset();
                  this.$refs[formName].resetFields();
                  this.dialogFormVisible = false;
                  this.$message({
                    message:response.msg,
                    type:'success'
                  })
                  //退出
                }else{
                  //进行相应的修改
                  this.updateStructNickName(function(response,_this){
                    if(response.code==200){
                      _this.$message({
                        message:response.msg,
                        type:'success'
                      })
                      // console.log(this.formRow);
                      _this.flushTreeNodeByNodeId(_this.formRow);
                      _this.formReset();
                      _this.$refs[formName].resetFields();
                      _this.dialogFormVisible = false;

                    }
                  });
                }

              }
            })
          }else{
            this.updateStructNickName(function(response,_this){
              if(response.code==200){
                _this.$message({
                  message:response.msg,
                  type:'success'
                })
                // console.log(this.formRow);
                _this.flushTreeNodeByNodeId(_this.formRow);
                _this.formReset();
                _this.$refs[formName].resetFields();
                _this.dialogFormVisible = false;

              }
            });
          }

        }
      })
    },
    //点击修改时方法
    updateStructLayer(row){
      //初始化表单
      this.title = '修改' + row.node_name;
      this.formRow = row;
      this.form.id = row.uuid;
      this.form.nodeId = row.id;
      this.form.num = row.allowance;
      this.form.curNum = row.curNum;
      this.dialogFormVisible = true;
      this.formRules.layerNum = [];
      this.formRules.sectionNum = [];
      this.formDisabled.layerNum = true;
      this.formDisabled.sectionNum = true;
      //添加层存放数校验规则
      this.formRules.num = [
        {
          lengthNumShort:{
            type: 'number',
            min: row.allowance,
            max: 999,
            message: '请输入'+ row.allowance +'-999之间的数值',
            trigger: 'blur'
          }
        }
      ]

      //重置表单校验规则
      this.formRules.num = [this.rulesUtil.lengthNumShort];

    },

    //添加保存方法
    saveAddStruct(formNames){
      this.$refs[formNames].validate((valid)=>{
        if(valid){
          insertRoomStruct(this.form).then(response=>{
            if(response.code==200){
              // Notification.success({
              //   title: response.msg
              // })
              this.$message({
                message:response.msg,
                type:'success'
              })
              this.$refs[formNames].resetFields();
              this.formReset();
              this.dialogFormVisible1 = false;
              this.flushTreeNodeByNodeId(this.formRow);
            }
          })
        }
      })
    },
    //点击添加方法
    addStruct(row){
      //初始化表单数据
      let row2 = null;
      //获取到row 以供刷新页面使用
      this.tableData2.map(item=>{
        if(item.parentId == row.id){
          row2 = item;
        }
      })
      if(row2!=null){
        this.formRow = row2;
      }else{
        this.formRow = row;
      }

      this.formRules.layerNum = [];
      this.formRules.sectionNum = [];
      this.dialogFormVisible1 = true;
      this.resetForm("addform");
      this.formDisabled.sectionNum = true;
      this.form.parentId = row.id;
      this.formRules.num.push(this.rulesUtil.lengthNumShort2)
      if(row.type==1){
        this.addTitle = '新增节';
        this.form.parentId = row.index;
        //新增节 显示层存放数
        this.formDisabled.layerNum = false;
        //添加层存放数校验规则
        this.formRules.layerNum.push(this.rulesUtil.lengthNumShort2)
      }

      if(row.type==2){

        //新增层 只显示基本
        //去掉校验规则
        this.addTitle = '新增层';
        this.formRules.sectionNum = []
        this.formRules.layerNum = [];
        this.formDisabled.layerNum = true;
      }
      this.form.type = row.type + 1;


    },
    //添加列级节点方法
    addColumn(){
      //初始化表单
      this.formRow = {type:1};
      this.addTitle = '新增列';
      this.dialogFormVisible1 = true;
      this.formRules.layerNum = [];
      this.formRules.sectionNum = [];
      this.form.type = 1;
      this.formDisabled.layerNum = false;
      this.formDisabled.sectionNum = false;
      //添加层存放数校验规则
      this.formRules.layerNum.push(this.rulesUtil.lengthNumShort2)
      this.formRules.sectionNum.push(this.rulesUtil.lengthNumShort2)
      this.formRules.num.push(this.rulesUtil.lengthNumShort2)
      this.resetForm("addform");
    },
    numFormat(row){
      return row.curNum + row.allowance;
    },

    quertTreeTableLoad(params){
      queryRoomStruct(params).then(response=>{
        response.data.map(item=>{
          item.disable = true;
          if(item.hasChildren=='true'){
            item.hasChildren = true;
          }

          if(item.hasChildren=='false'){
            item.hasChildren = false;
          }
          item.node_name2 = item.node_name;
          item.editCode2 = item.editCode;
          item.code = "k" + this.roomCode +"" + item.code;
          this.tableData2.push(item);
        })
        this.tableData = response.data;

      })
    },

    //初始化列级方法
    getStruct(){
      var params = {
        roomId:this.roomId
      }
      this.quertTreeTableLoad(params);

    },
    //树节点加载方法
    load(tree, treeNode, resolve) {
      var params = {};
      params.roomId = this.roomId;
      if(tree.type==1){
        params.columnNumber = tree.index;
      }
      if(tree.type==2){
        params.columnNumber = tree.columnNumber;
        params.sectionNumber = tree.index;
      }
      queryRoomStruct(params).then(response=>{
        let data = [];
        response.data.map(item=>{
          item.disable = true;
          if(item.hasChildren=='true'){
            item.hasChildren = true;
          }

          if(item.hasChildren=='false'){
            item.hasChildren = false;
          }
          item.parentId = tree.id;
          item.node_name2 = item.node_name;
          item.code = "k" + this.roomCode + "" + item.code;
          item.editCode2 = item.editCode;
          //备份一份数据提供给刷新使用
          this.tableData2.push(item);
          data.push(item);
        })
        resolve(data);

      })
    },
    //刷新form表单数据方法
    formReset(){
      this.form = {
        sectionNum: '',
        layerNum:'',
        num:'',
        code:'',
        nickName:'',
        parentId:'',
        roomId:this.$route.params.roomId
      }
    },

    //刷新当前节点的所有下级列表 用于编辑时使用
    flushSubTreeNodeByNodeId(row){
      let params = {};
      params.roomId = this.roomId;
      this.$set(this.$refs.treeGrid.store.states.lazyTreeNodeMap,row.id,[]);
      //重新加载下级
      if(row.type==1){
        //加载节级节点
        params.columnNumber = row.columnNumber;
      }else if(row.type==2){
        //加载层级节点
        params.columnNumber = row.columnNumber;
        params.sectionNumber = row.sectionNumber;

      }else if(row.type==3){
        return;
      }
      queryRoomStruct(params).then(response=>{
        let data = [];
        response.data.map(item=>{
          if(item.hasChildren=='true'){
            item.hasChildren = true;
          }

          if(item.hasChildren=='false'){
            item.hasChildren = false;
          }
          //做数据初始化动作
          item.disable = true;
          item.parentId = row.parentId;
          item.node_name2 = item.node_name;
          item.editCode2 = item.editCode;
          item.code = "k" + this.roomCode +"" + item.code;
          data.push(item);
          //将数据备份在tableData2中
          this.tableData2.push(item);
        })
        //重新设置数据为请求的数据
        this.$set(this.$refs.treeGrid.store.states.lazyTreeNodeMap,row.id,data);
      })
    },

    //刷新当前节点以及所有上级列表
    flushTreeNodeByNodeId(row){
      let params = {};
      params.roomId = this.roomId;
      if(row.type==1){
        this.getStruct();
      }
      //设置请求参数
      if(row.type>1){
        //将当前级的数据设为空
        this.$set(this.$refs.treeGrid.store.states.lazyTreeNodeMap,row.parentId,[]);
        if(row.type==2){//删除的为节   取到列节点
          params.columnNumber = row.columnNumber;
        }
        if(row.type==3){//删除的为层   取到列，节节点
          params.columnNumber = row.columnNumber;
          params.sectionNumber = row.sectionNumber;
        }
        //发起请求
        queryRoomStruct(params).then(response=>{
          let data = [];
          response.data.map(item=>{
            if(item.hasChildren=='true'){
              item.hasChildren = true;
            }

            if(item.hasChildren=='false'){
              item.hasChildren = false;
            }
            //做数据初始化动作
            item.disable = true;
            item.parentId = row.parentId;
            item.node_name2 = item.node_name;
            item.editCode2 = item.editCode;
            item.code = "k" + this.roomCode + "" + item.code;
            data.push(item);
            //将数据备份在tableData2中
            this.tableData2.push(item);
          })
          //重新设置数据为请求的数据
          this.$set(this.$refs.treeGrid.store.states.lazyTreeNodeMap,row.parentId,data);
          //递归刷新所有父级
          if(row.parentId!=''&&typeof row.parentId!="undefined"){
            let row2 = {};
            this.tableData2.map(item=>{
              if(item.id == row.parentId){
                row2 = item;
              }
            })
            this.flushTreeNodeByNodeId(row2);
          }
        })

      }
    }
  }
}

</script>

<style lang="scss" scoped>
  /*/deep/.cell .el-input__inner{*/
  /*  !*background-color: 'none';*!*/
  /*  !*color: rgb(0, 0, 0);*!*/
  /*  background-color: #f5f7fa0d !important;*/
  /*  border-color: #e4e7ed00 !important;*/
  /*}*/

  /deep/.node_name_edit_class {
    margin:0px;
    font-size: 12px;
  }
.form-inline {
    width:100%;
    height: 100%;
    padding-left: 20px;
    padding-top: 10px;
    border-bottom:1px solid #e2e3e6;
}
.btn {
    position: relative;
    width: 100%;
    height: 55px;
    padding-top:10px;
    padding-bottom: 20px;
    border-bottom:1px solid #e2e3e6;
    .btn-search {
        position: absolute;
        right: 40px;
        top:10px;
    }
}
.page {
    text-align: center;

}
  /deep/.el-table tbody tr:hover>td {
    background-color:#ecf5ff !important
  }
  /deep/.el-table__body td {
    padding: 0;
    height: 40px;
  }
  /*/deep/.dialog-footer {*/
  /*  background: rgba(204, 204, 204, 0.18);*/
  /*  padding-right: 15px;*/
  /*  !*height:43px;*!*/
  /*  padding: 11px;*/
  /*}*/
  // 基本信息弹框


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
  .app-structure {
    padding-left: 10px;
    padding-right: 10px;
  }
</style>

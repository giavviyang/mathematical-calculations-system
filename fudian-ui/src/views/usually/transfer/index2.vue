<template>
  <div class="app-transfer">
    <!-- 档案转出 -->
    <el-col :span="24" :xs="24" style="margin: 0;padding: 0;height: calc(100vh - 90px);overflow: auto;">
      <el-row>
        <el-col :span="24" >
          <div style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">第一步：填写转出信息</div>
          <div ref="customRef" id="app" style="width:100%">
            <el-form ref="topData" :inline="true" :rules="rules" label-width="100px" :model="topData" show-message style="padding: 20px 0 0 0;width: 75%;margin: 0 auto">
                <el-form-item label="转出文号" prop="id">
                  <el-input  placeholder="请输入转出文号" v-model="topData.id" @change="handleSelect" size="mini"/>
                </el-form-item>
                <el-form-item label="转往单位" prop="e01z704a" size="mini">
                  <el-input placeholder="请输入转往单位" v-model="topData.e01z704a" />
                </el-form-item>
                <el-form-item label="转出日期" prop="e01z701" >
                  <el-date-picker v-model="topData.e01z701" type="date" placeholder="请选择转出日期" size="mini"/>
                </el-form-item>
                <el-form-item label="转递原因" prop="e01z721">
                  <el-input placeholder="请输入转递原因" v-model="topData.e01z721" size="mini"/>
                </el-form-item>
                <el-form-item label="经办人" prop="e01z717">
                  <el-input placeholder="请输入经办人" v-model="topData.e01z717" size="mini"/>
                </el-form-item>
                <el-form-item label="审批人" prop="approver">
                  <el-input placeholder="请输入审批人" v-model="topData.approver" size="mini"/>
                </el-form-item>
              </el-form>
            <div style="padding-top: 10px;width: 75%;margin: 0 auto">
              <el-button style="float: right;margin-right: 10px;width:80px" size="mini"  @click="resetForm">重置表单</el-button>
            </div>
          </div>
        </el-col>
        <!-- 选择干部 -->
        <el-col :span="24">
          <div style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">第二步：请选择待转出干部档案</div>
          <div style="width: 75%;margin: 0 auto;">
            <div style="width: 100%;padding:20px 0 0 0">
              <el-form :inline="true" v-model="a01Search" style="width: 100%;">
                <el-form-item label="干部状态">
                  <el-select v-model="a01Search.state" filterable placeholder="请选择干部状态" class="input-w" size="mini">
                    <el-option v-for="item in states" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-input v-model="cadresText" @keyup.enter.native="search(1)" clearable placeholder="请输入部门" size="mini"/>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="search(1)" size="mini">部门搜索</el-button>
                </el-form-item>
                <el-form-item>
                  <el-input v-model="personnelText" @keyup.enter.native="search(2)" clearable placeholder="请输入干部名称" size="mini"/>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary"  @click="search(2)" size="mini">干部搜索</el-button>
                </el-form-item>
              </el-form>
            </div>
            <!-- 干部表格数据 -->
            <el-table
              size="mini"
              :data="leftData"
              ref="searchTableData"
              stripe
              border
              empty-text=" "
              tooltip-effect="light"
              style="width: 100%;"
              height="222px"
              :header-cell-style="headClass"
              @cell-dblclick="deptClick"
              @selection-change="leftChange"
              @row-click="handleGridChange"
              >
              <el-table-column type="selection" width="40"  key="1" v-if="deptNshow"/>
              <el-table-column style="width: 1%" type="index"  align="center" label="序号" />
              <el-table-column prop="a0101" show-overflow-tooltip v-if="isa01 == 2" key="2" label="干部名称"  align="center" />
<!--              <el-table-column prop="number" v-if="isa01 == 2" key="3" label="干部编号"  align="center" />-->
              <el-table-column prop="deptName" show-overflow-tooltip v-if="isa01 == 2" key="4" label="所属部门" align="center" />
              <el-table-column prop="deptName" show-overflow-tooltip v-if="isa01 == 1" key="5" label="部门名称" align="center" style="width: 50%"/>
              <el-table-column prop="parentId" show-overflow-tooltip v-if="isa01 == 1" key="6" label="上级部门" align="center" style="width: 50%"/>
              <el-table-column prop="a0104" show-overflow-tooltip v-if="isa01 == 2" key="7" label="性别"  align="center" :formatter="getFullName" />
              <el-table-column prop="a0107" show-overflow-tooltip v-if="isa01 == 2" key="8" label="出生日期"  align="center" :formatter="getFullName" />
              <el-table-column prop="state" show-overflow-tooltip v-if="isa01 == 2" key="9" label="干部状态"  align="center" :formatter="getFullName" />
            </el-table>
          </div>
          <div style="padding-top: 10px;width: 75%;margin: 0 auto">
              <el-button type="primary" style="float: right;margin-right: 10px;width:80px" size="mini"  @click="createGridData('form')">添加</el-button>
          </div>
        </el-col>
        <el-col :span="24" >
          <div style="margin: 0 auto;width:80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc">第三步：确认干部信息并生成转出列表</div>
          <div style="width: 75%;margin: 0 auto;">
          <!-- 转出信息表格数据 -->
            <div style="padding:10px 0 0 0">
              <el-table
                size="mini"
                ref='rightData'
                :data="rightData"
                stripe
                border
                empty-text=" "
                height="222px"
                tooltip-effect="light"
                :header-cell-style="headClass"
                :default-sort="{ prop: 'date', order: 'descending' }"
                @selection-change="handleSelectionChange"
                >
                <el-table-column  align="center" style="width: 1%" type="index" label="序号" />
                <el-table-column show-overflow-tooltip  align="center" prop="a0101" label="干部名称" min-width="80px"/>
<!--                <el-table-column  align="center" prop="number" label="干部编号" show-overflow-tooltip />-->
                <el-table-column  align="center" prop="deptId" label="所属部门" show-overflow-tooltip width='100' :formatter="getFullName"/>
                <el-table-column  align="center" prop="duty" label="职务" show-overflow-tooltip :formatter="getFullName" />
                <el-table-column  align="center" prop="e01z721" label="转递原因" show-overflow-tooltip />
                <el-table-column show-overflow-tooltip  align="center" prop="e01z711" label="正本" width='80' />
                <el-table-column show-overflow-tooltip  align="center" prop="e01z714" label="副本" width='80' />
                <el-table-column show-overflow-tooltip  align="center" prop="matNum" label="档案材料" width='120' />
                <el-table-column align="center" label="操作" width="100">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="update(scope.row)">编辑</el-button>
                    <el-button type="text" size="small" @click="deleteRow(scope.$index, rightData)">移除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
          <div style="padding: 20px 0 0 0;width: 75%;margin: 0 auto">
            <el-button type="primary" style="float: right;margin-right: 10px;width:80px" size="mini" @click="createTransfer">确认转出</el-button>
          </div>
        </el-col>
      </el-row>
    </el-col>
    <!-- 转出信息编辑对话框 -->
    <el-dialog title="编辑信息"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    :append-to-body="true"
    width="75%"
    class="dialog-style" >
      <el-form ref="editForm" :model="editForm" label-width="140px" :inline="true">
        <el-form-item label="干部名称" >
          <el-input v-model="editForm.a0101" placeholder="请输入干部名称" :disabled="true" size="mini" />
        </el-form-item>
        <!--<el-form-item label="干部编号" >
          <el-input v-model="editForm.number" placeholder="请输入干部编号" size="mini" />
        </el-form-item>-->
        <el-form-item label="所属部门" >
          <selectTree
            v-model="editForm.deptId"
            :datas="deptOptions"
            :defaultProps="defaultProps"
            size="mini"
            :filterable="true"
            :ischecked="true"
            :defaultExpandAll="true"
            placeholder="请选择所属部门"
            width="150px"
          />
        </el-form-item>
        <el-form-item label="职务">
          <el-select v-model="editForm.duty" filterable placeholder="请选择职务" size="mini" style="width:150px">
            <el-option v-for="item in dutys" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
          </el-select>
        </el-form-item>
        <el-form-item label="转递原因" prop="e01z721">
          <el-input v-model="editForm.e01z721" placeholder="请输入内容" size="mini"/>
        </el-form-item>
        <el-form-item label="正本(卷)">
          <el-input-number v-model="editForm.e01z711" :min="0" size="mini" style="width:150px"/>
        </el-form-item>
        <el-form-item label="副本(卷)">
          <el-input-number v-model="editForm.e01z714" :min="0" size="mini" style="width:150px"/>
        </el-form-item>
        <el-form-item label="档案材料(份)">
          <el-input-number v-model="editForm.matNum" :min="0" size="mini" style="width:150px"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" >
        <el-button @click="dialogVisible = false" size="mini">取 消</el-button>
        <el-button type="primary" @click="onCommitEdit" size="mini">确 定</el-button>
      </div>
    </el-dialog>



         <!--  转出成功  -->
    <el-dialog title="转出成功"
    width="28%"
    class=" dialog-style dialog-basic"
    :visible.sync="tranferSuccessWin"
    :close-on-click-modal="false"
    :append-to-body="true"
    >
      <div class="dialog-state">
      <span class="state">库房位置已移除，请选择下一步操作</span>
        <el-form  :model="successForm" style="width:50%;margin:auto;padding-top:14%"  label-width="120px">
          <el-radio-group v-model="successForm.sradio">
            <el-radio label="3" >继续转出</el-radio>
            <el-radio label="1" style="padding:10px 0 0 0">导出excel数据包</el-radio>
            <el-radio label="2" style="padding:10px 0 0 0">导出xml数据包</el-radio>

          </el-radio-group>
        </el-form>

      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="tranferSuccessWin = false ">关 闭</el-button>
        <el-button size="mini" type="primary" @click="tansferSuccessNext">下一步</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
import { queryA01List, queryIdAndDate, queryIdIsUsable, insertTransfer, exportPacket } from '@/api/usual/transfer'
import { queryDictionaryByType, queryLoginUser, queryDeptTree } from '@/api/usual/receive'
import {queryDeptMap} from '@/api/usual/parameter/receives-paramenter'
import { downLoad,resolveBlob } from '@/downLoad/filedownload'
import XEUtils from 'xe-utils'
// 下拉树
import selectTree from '@/views/components/tree/tree.vue'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  components: { Treeselect, selectTree },
  name: '档案转出',
  watch: {
    leftData:{
      handler(newName, oldName) {
        this.$nextTick(()=>{
          this.$refs.searchTableData.doLayout();
        })
      },
      deep:true,
    },
    rightData:{
      handler(newName, oldName) {
        this.$nextTick(()=>{
          this.$refs.rightData.doLayout();
        })
      },
      deep:true,
    },
  },
  data() {
    return {
      userName: '', //当前登录用户
      idIsUsable: true,
      // 人员选择数据
      beforeQueryData:[],
      leftPersonCheck:[],
      states:[],
      a01State:'',
      a01Search:{
        state:"0"
      },
      tranferSuccessWin:false,
      successForm:{sradio:"3"},
      select: '',
      deptNshow:false,
      isa01: 2,
      personnelText: '',
      cadresText: '',
      searchText: '',
      leftChek: [],
      leftData: [],
      // 编辑对话框
      dialogVisible: false,
      topData: {
        e01z701: new Date(),
        id: '',
        e01z704a: '',
        e01z717: '',
      },
      editForm: {
        // number: '',
        deptId: '',
        duty: '',
        e01z711: '',
        e01z714: '',
        matNum: ''
      },
      deptMap: {},
      deptOptions: [],
      defaultProps: {
        children: 'children',
        label: 'deptName',
        id: 'id'
      },
      selectTreeWidth: "100px",
      ischecked: true,
      rightDataIndex: '',
      exportTransferId:'',
      rightData: [],
      rightDataChek: [],
      value: '',
      //字典表转换
      dutys: [],
      a0104s: [],
      a0104s1: [],
      a0141s: [],
      orgMap: [],
      // 验证必填项
      rules: {
        id: [
          { required: true, message: '请输入转出文号', trigger: 'blur' },
          { max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
        ],
        e01z704a: [
          { required: true, message: '请输入转往单位', trigger: 'blur' },
          { max: 60, message: '输入内容不得超过60个汉字', trigger: 'blur' }
        ],
        e01z701: [
          { required: true, message: '请选择转出日期', trigger: 'blur' }
        ],
        e01z721: [
          { required: true, message: '请输入转递原因', trigger: 'blur' },
          { max: 40, message: '输入内容不得超过40个汉字', trigger: 'blur' }
        ],
        e01z717: [
          { required: true, message: '请输入经办人', trigger: 'blur' },
          { max: 18, message: '输入内容不得超过18个汉字', trigger: 'blur' }
        ],
        approver: [
          { message: '请输入审批人', trigger: 'blur' },
          { max: 18, message: '输入内容不得超过18个汉字', trigger: 'blur' }
        ]
      },
    }
  },
  mounted() {
    this.queryFunction()
    this.activeA01List()
    this.getDeptTree()
  },
  created(){
    this.initColumnMap()
    this.getLoginUser()
  },
  methods: {
    // 公用表格表头样式
    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
    },
    // 加载数据
    queryFunction() {
      queryIdAndDate()
        .then(response => {
          this.topData.id = response.data.id
          this.topData.e01z701 = this.moment(response.data.e01z701).format("YYYY-MM-DD")
        })
    },
    // 日常管理跳转档案转出接收数据
    activeA01List() {
      if (this.$route.params.a01s) {
        let tableData = JSON.parse(this.$route.params.a01s)
        tableData.forEach(a01 => {
          let gridList = {}
          gridList.a01Id = a01.id
          gridList.a0101 = a01.a0101
          // gridList.orgName = a01.orgId
          gridList.duty = a01.duty
          gridList.deptId = a01.deptId
          gridList.e01z721 = ''
          gridList.e01z711 = 1
          gridList.e01z714 = 0
          gridList.matNum = 1
          delete gridList.id
          this.rightData.push(gridList)
        })
      }
    },
    // 初始化转换参数
    initColumnMap() {
      // 当前页面所需字典表数据 职务
      queryDictionaryByType({ dictType: 'ZB09', eparentCode: '0' })
        .then(response => {
          this.dutys = response.data
        })
      // 当前页面所需字典表数据 性别
      queryDictionaryByType({ dictType: 'GB22611' })
        .then(response => {
          this.a0104s1 = response.data
        })
      // 获取政治面貌
      queryDictionaryByType({ dictType: 'GB4762' })
        .then(response => {
          this.a0141s = response.data
        })
      // 获取干部状态
      queryDictionaryByType({ dictType: 'FDA01State' })
        .then(response => {
          this.states = response.data
        })
      // 部门
      queryDeptMap().then(response => {
        this.deptMap = response.data
        console.log(this.deptMap)
      })
    },
    // 获取当前登录用户
    getLoginUser(){
      queryLoginUser().then(response=>{
        if(response.code==200){
          let user = response.data;
          this.userName = user.userName;
          this.topData.e01z717 = this.userName;
          // this.topData.approver =user.userName;
        }
      })
    },
    // 获取部门树
    getDeptTree() {
      queryDeptTree({deptType: 0}).then(response => {
        this.deptOptions = response.data
      })
    },
    // 转出文号监听
    handleSelect(item) {
      const _this = this
      queryIdIsUsable({ id: item })
        .then(response => {
          if (response.data === true) {
            _this.topData.id = this.topData.id
            this.idIsUsable = true
          } else {
            this.$message.error(response.msg)
            this.idIsUsable = false
          }
        })
    },
    // 重置表单
    resetForm(){
      this.topData = {
        id:this.topData.id,
        e01z704a: '',
        e01z701: new Date(),
        e01z721: '',
        e01z717: this.userName,
        // approver: 'admin',
      }
    },
    // 干部、部门过滤
    search(type) {
      const _this = this
      _this.leftChek=[];
      if (type === 1) {
        this.deptNshow = false
        this.searchText = this.cadresText
      } else {
        if(this.personnelText == null || this.personnelText == ''){
          this.$message({
            message:'请输入干部搜索条件',
            type:'warning'
          })
          return ;
        }
        this.deptNshow = true
        this.searchText = this.personnelText
      }
      this.isa01 = type;
      queryA01List({ searchText: this.searchText, type: type ,state:this.a01Search.state })
        .then(response => {
          _this.leftData = response.data
        })
    },
    // 勾选干部
    leftChange(val) {
      this.leftChek = val
      this.leftPersonCheck = val.map(item => item.id)
    },
    // grid 双击部门 查询干部
    deptClick(val) {
      if(this.isa01 == 2){
        return ;
      }
      this.isa01 = 2;
      if (val.children) {
        this.deptNshow=true;
        let tableData = [];
        for(let i in val.children){
          if(val.children[i].state==this.a01Search.state){
            tableData.push(val.children[i]);
          }
        }
        this.leftData = tableData
        this.beforeQueryData = val.children
      }
    },
    // 部门搜索栏单击事件
    handleGridChange(row) {
      if(this.isa01 == 1){
        return;
      }
      if (row) {
        this.$refs.searchTableData.toggleRowSelection(row)
      } else {
        this.$refs.searchTableData.clearSelection()
      }
    },
    // 添加
    createGridData() {
      this.$refs['topData'].validate((valid) =>{
        if (valid) {

          let repet = ""
          let flag = false
          let checkGrid = JSON.parse(JSON.stringify(this.leftChek))
          console.log(this.leftChek)
          if(this.leftChek.length==0){
            this.$message({
              message: '请选择干部条目',
              type: 'warning'
            });
            return;
          }
          OL:for (let i = 0; i < checkGrid.length; i++) {
            let gridList = {}
            gridList=checkGrid[i]
            for (let j = 0; j < this.rightData.length; j++) {
              if(checkGrid[i].id == this.rightData[j].a01Id){
                repet += checkGrid[i].a0101 + '、'
                flag = true
                continue OL;
              }
            }
            gridList.a01Id = checkGrid[i].id
            gridList.e01z721 = this.topData.e01z721
            gridList.e01z711 = 1
            gridList.e01z714 = 0
            gridList.matNum = checkGrid[i].matNum
            delete gridList.id
            this.rightData.push(gridList)
            for(let j in this.leftData){
              if(this.leftData[j].id==this.leftChek[i].id){
                this.leftData.splice(j,1);
              }
            }
          }
          if(flag){
            repet = repet.substring(0,repet.length-1)
            this.$message({
              type: 'warning',
              message: repet + '信息已添加'
            })
          }
        } else {
          this.$message({
            type: 'warning',
            message: '请检查表单信息是否填写完整'
          })
        }
      })
    },
    // 移除
    deleteRow(index, rows) {
      rows.splice(index, 1)
    },
    // 编辑
    update(val) {
      this.dialogVisible = true
      this.editForm = JSON.parse(JSON.stringify(val))
    },
    // 保存编辑
    onCommitEdit() {
      let list = this.rightData
      let par = this.editForm
      for(let i in list){
        let dataMap = list[i];
        if(dataMap.a01Id==par.a01Id){
          // dataMap.number = par.number
          dataMap.deptId = par.deptId
          dataMap.duty = par.duty
          dataMap.e01z721 = par.e01z721
          dataMap.e01z711 = par.e01z711
          dataMap.e01z714 = par.e01z714
          dataMap.matNum = par.matNum
          this.dialogVisible = false
          return list[i] = dataMap
        }
      }
    },
    change(e){
      this.$forceUpdate()
    },

    //转出成功后下一步操作
    tansferSuccessNext(){
      if(this.successForm.sradio=="1"){//excel数据包
        exportPacket({
          transId: this.exportTransferId,
          fileType: 0
        }).then(responss => {
          resolveBlob({data:responss.data,fileName:this.exportTransferId + '.zip'})
        })

      }else if(this.successForm.sradio=="2"){//xml数据包
        exportPacket({
          transId: this.exportTransferId,
          fileType: 1
        }).then(responss => {
          //downLoad('/fileStorage/getFileBytes',{redisId:responss.data,fileName:this.topData.id + '.zip'})
          resolveBlob({data:responss.data,fileName:this.exportTransferId + '.zip'})
        })

      }else if(this.successForm.sradio=="3"){
        this.tranferSuccessWin =false
      }
    },

    // 确认转出
    createTransfer() {
      console.log(this.topData)
      this.$refs['topData'].validate((valid) =>{
        if(valid && this.idIsUsable){
          let formData = JSON.parse(JSON.stringify(this.topData))
          formData.e01z701 = this.parseTime(this.topData.e01z701).substring(0,10);
          let reg = new RegExp('-','g');
          formData.e01z701 =  formData.e01z701.replace(reg, '')
          for(let i = 0; i < this.rightData.length; i++) {
            if(this.rightData[i].e01z721 == "" || this.rightData[i].e01z721 == null){
              this.$message({
                message: '请检查转递原因是否填写完整',
                type: 'warning'
              })
              return
            }
          }
          let returnData = {
            formValues: formData,
            gridValues: this.rightData
          }
          insertTransfer({ jsonStr:returnData})
            .then(response => {
              if (response.code === 200) {
                this.successForm.sradio="3";
                this.tranferSuccessWin=true;
                this.exportTransferId=JSON.parse(JSON.stringify(this.topData.id))
                this.a01Search.state = '0'
                this.a01Search.cadresText = ''
                this.a01Search.personnelText = ''
                this.topData =  {
                  id:(parseInt(this.topData.id) + 1).toString(),
                  e01z701: new Date(),
                  e01z704a: '',
                  e01z717: '',
                },
                this.leftData = []
                this.rightData= []
              }
            })
            .catch(() => {
              this.$message({
                message: '转出失败，请联系管理员。',
                type: 'error'
              })
            })
        }else{
          if(!this.idIsUsable){
            this.$message({
              message: '转出失败，转出文号不可重复。',
              type: 'warning'
            })
            return;
          }
          this.$message({
            message: '转出失败，请按要求填写表头信息。',
            type: 'warning'
          })
        }
      })
    },
    // 导出请求
    exportRequest() {
      exportPacket({transId: this.topData.id})
        .then(response => {
          this.$message({
            message: '导出成功',
            type: 'success'
          })
        })
    },

    // 根据code转换成中文
    getFullName(row,col){
      // 时间转换
      if(col.property=="e01z701" || col.property=="a0107"){
        if(row[col.property]!=null&&row[col.property]!=''){
          return this.moment(row[col.property]).format("YYYY-MM-DD")
        }
      }
      // 职务
      if(col.property=="duty"){
        let returnValues='';
        this.dutys.forEach(data => {
          if(data.dictCode==row[col.property]){
            returnValues=data.fullName;
          }
        })
        return returnValues;
      }
      // 性别
      if(col.property=="a0104"){
        let returnValues='';
        this.a0104s1.forEach(data => {
          if(data.dictCode==row[col.property]){
            returnValues=data.fullName;
          }
        })
        return returnValues;
      }
      // 政治面貌
      if(col.property=="a0141"){
        let returnValues='';
        this.a0141s.forEach(data => {
          if(data.dictCode==row[col.property]){
            returnValues=data.fullName;
          }
        })
        return returnValues;
      }
      // 干部状态
      if(col.property=="state"){
        let returnValues='';
        this.states.forEach(data => {
          if(data.dictCode==row[col.property]){
            returnValues=data.fullName;
          }
        })
        return returnValues;
      }
      //部门
      if (col.property == 'deptId') {

        console.log(col.property)
        if (this.deptMap[row.deptId] == null) {
          return ''
        } else {
          return this.deptMap[row.deptId]
        }
      }
      // 全宗
      if(col.property=="orgName"){
        if(this.orgMap[row.orgName]==null){
          return null;
        }else{
          return this.orgMap[row.orgName];
        }
      }
      return row[col.property]
    },
    // grid 监听
    handleSelectionChange() {
    },
  }
}
</script>

<style lang="scss" scoped>
  .el-col.el-col-24.el-col-xs-24 {
    padding: 10px;
    margin: 10px;
  }

  .block {
    float: right;
  }
  .el-tabs__nav-wrap {
    min-height: calc(100vh - 86px);
  }

  .el-tabs__header {
    min-height: calc(100vh - 86px);
  }

  .demo-table-expand {
    font-size: 0;
  }

  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }

  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }

  .input-w {
    width: 150px;
  }

  .el-input__inner {
    width: 150px;
  }

  .el-input {
    width: 150px;
  }

  // 公用弹框样式
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
      padding-right: 15px;
      padding: 0px;
    }
    /deep/.el-button+.el-button {
      margin-left: 6px;
    }
  }

  // 转出成功页面
  .dialog-state {
    position: relative;
    height: 160px;

    margin:20px;
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
      margin-left:20%;
      margin-top: 20px
    }
  }
  .form-basic {
      margin-bottom: 15px;
      line-height: 30px;
      border-bottom: 1px solid #ccc;
      margin-left: 15px;
      margin-right: 15px;
    }
  // 公用树结构样式
  .tree-style{
    /deep/.el-tree--highlight-current .el-tree-node.is-current>.el-tree-node__content {
      background: rgba(204, 204, 204, 0.18);
    }
  }
  .dialog-footer {
      background: rgba(204,204,204,0.18);
      padding-right: 15px;
      padding: 11px;
  }

  /deep/.el-input.is-disabled .el-input__inner{
      background-color: #f5f7fa0d;
      border-color: #e4e7ed00;
      color: #606266;
  }
  .vue-treeselect--single .vue-treeselect__input {
      width: 100%;
      height: 28px;
  }
  .vue-treeselect__control {
      padding-left: 5px;
      padding-right: 5px;
      display: table;
      table-layout: fixed;
      width: 100%;
      height: 28px;
      border: 1px solid #ddd;
      border-radius: 5px;
      background: #fff;
      transition-duration: 200ms;
      transition-property: border-color, box-shadow, width, height, background-color, opacity;
      transition-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
  }
</style>

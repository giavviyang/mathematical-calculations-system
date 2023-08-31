<template>
  <div class="app-materials">
    <!-- 档案材料 -->
    <div class="center-table" :class="{'isShow':!showBox}">
      <div class="btn">
        <div class="btn-btn">
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="onAdd">新增</el-button>
          <el-button type="primary" icon="el-icon-edit" size="mini" v-model="showdaliog" @click="onUpdate">编辑</el-button>
          <el-button type="primary" icon="el-icon-delete" size="mini" @click="onDelete">删除</el-button>
          <el-button type="primary" icon="el-icon-sort" size="mini" @click="sort">排序</el-button>
          <el-button type="primary" icon="el-icon-s-operation" size="mini" @click="materialChange">材料调整</el-button>
          <el-button type="primary" icon="el-icon-remove" size="mini" @click="materialMove">材料转移</el-button>
          <el-button type="primary" icon="el-icon-upload2" size="mini" @click="onReturn">目录导出</el-button>
          <span class="span-title" style="font-family: fantasy">共*份，共*页</span>
        </div>
        <!-- <div class="btn-circle">
          <el-button icon="el-icon-s-unfold" size="mini" type="primary" circle ></el-button>
          <el-button slot="reference" icon="el-icon-menu" size="mini" circle></el-button>
        </div>      -->
      </div>
      <div class="table">
        <el-table
          ref="tableRefs"
          :cell-style="cellStyle"
          :data="tableData"
          row-key="id"
          border
          size="mini"
          default-expand-all
          @row-click="StepsListRowClick"
          tooltip-effect="light"
          :height="tableHeight "
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
          element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          :header-cell-style="headClass"
          @selection-change="selectChecked"
          >
          <el-table-column type="selection" width="45px" align="center" :resizable="false"></el-table-column>
          <el-table-column label="类号" align="center" :resizable="false" prop="e01z104" width="90" />
          <el-table-column label="材料名称" header-align="center" prop="e01z111a" show-overflow-tooltip width="450px" :resizable="false"></el-table-column>
          <el-table-column label="材料制成时间" align="center" :resizable="false">
            <el-table-column label="年" prop="year" align="center" :resizable="false" width="60px">
            </el-table-column>
            <el-table-column label="月" prop="month" align="center" :resizable="false" width="50px">
            </el-table-column>
            <el-table-column label="日" prop="day" align="center" :resizable="false" width="50px">
            </el-table-column>
          </el-table-column>
          <el-table-column label="页数" prop="e01z114" align="center" :resizable="false" width="45px">
          </el-table-column>
          <el-table-column label="备注" prop="e01z121" align="center" :resizable="false" show-overflow-tooltip>
          </el-table-column>
          <el-table-column label="电子文件" prop="filed" align="center" :resizable="false" width="100px" >
          </el-table-column>
          <el-table-column fixed="right" label="操作" align="center" :resizable="false" width="130px">
            <template  slot-scope="scope">
              <!-- v-if="scope.row.type === 3" -->
              <el-button @click="checkFile(scope.row)" v-if="scope.row.type != 1" type="text" size="small">查看电子文件</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <!-- 新增对话框 -->
    <el-dialog :title="isEdit?'编辑材料信息':'新增材料'" :close-on-click-modal="false" :visible.sync="dialogVisible4" style="margin-top:5%" class=" dialog-style" width="40%">
      <div class="dialog-input">
      <el-form ref="form" :inline="true" label-width="80px">
<!--        <el-form-item label="材料分类" label-width="80px">-->
<!--          <el-select placeholder="请选择" size="mini" style="width:163px">-->
<!--            <el-option label="一：履历材料" value="shanghai"></el-option>-->
<!--            <el-option label="二：自传和思想类材料" value="beijing"></el-option>-->
<!--            <el-option label="三：考核鉴定类材料" value="beijing"></el-option>-->
<!--            <el-option label="四：学历学位、专业技术职务（职称）、学术评鉴和教育培训类材料" value="beijing"></el-option>-->
<!--            <el-option label="五：政审、审计和审核类材料" value="beijing"></el-option>-->
<!--            <el-option label="七：表彰奖励类材料" value="beijing"></el-option>-->
<!--            <el-option label="八：违规违纪违法处理处分类材料" value="beijing"></el-option>-->
<!--            <el-option label="九：工资、任免、出国和会议代表类材料" value="beijing"></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
        <el-form-item label="材料名称" label-width="80px">
          <el-select placeholder="请选择" size="mini" style="width:163px" v-model="highSearch.e01z111a">
            <el-option label="材料" value="shanghai"></el-option>
            <el-option label="离职" value="beijing"></el-option>
            <el-option label="退休" value="beijing"></el-option>
            <el-option label="死亡" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间"label-width="80px">
          <el-date-picker type="date" placeholder="选择日期" size="mini" style="width: 163px" v-model="highSearch.date"></el-date-picker>
        </el-form-item>
        <el-form-item label="页数" label-width="80px">
          <el-input placeholder="请输入内容" size="mini" v-model="highSearch.e01z114"></el-input>
        </el-form-item>
        <el-form-item label="备注" label-width="80px">
          <el-input placeholder="请输入内容" size="mini" v-model="highSearch.e01z121"></el-input>
        </el-form-item>
      </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible4 = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="dialogVisible4 = false">保 存</el-button>
        <el-button size="mini" type="primary" v-if="!isEdit"> 保存并新增下一条</el-button>
      </span>
    </el-dialog>
    <!-- 材料转移对话框 -->
    <el-dialog title="材料转移" :close-on-click-modal="false" :visible.sync="dialogVisible"  class="dialog-style tree-style" width="60%">
      <div class="dialog-center">
      <div class="div-left">
        <span >第一步：请输入想要搜索的干部名称</span>
        <el-form :data="formremove" ref="ruleForm" :inline="true" style="" label-width="70px" class="form-search">
          <el-form-item label="干部查询" prop="a0101" >
            <el-input v-model="formremove.a0101" placeholder="请输入内容" style="width:115px" size="mini"></el-input>
          </el-form-item>
          <el-form-item label="档案状态">
          <el-select v-model="formremove.state" placeholder="请选择" style="width:115px" size="mini">
            <el-option label="在职" value="shanghai"></el-option>
            <el-option label="离职" value="beijing"></el-option>
            <el-option label="退休" value="beijing"></el-option>
            <el-option label="死亡" value="beijing"></el-option>
          </el-select>
        </el-form-item>
          <el-form-item>
            <el-button type="primary" size="mini">查询</el-button>
          </el-form-item>
        </el-form>
        <!-- <el-scrollbar> -->
          <!-- <div class="table-remove"> -->
        <el-table
            ref="table1Refs"
            :data="tableData1" size="mini"
            :header-cell-style="headClass"
            tooltip-effect="light"
            border
            height="190px"
            @row-click="StepsListRowClick1"
            row-key="id"
              :stripe="true">
              <el-table-column type="selection" width="45px"/>
              <el-table-column type="index" label="序号" show-overflow-tooltip :resizable="false" align="center"></el-table-column>
              <el-table-column label="姓名" prop="a0101" width="60px" show-overflow-tooltip :resizable="false" align="center">
              </el-table-column>
              <el-table-column label="性别" prop="a0104" width="60px" show-overflow-tooltip :resizable="false"
                align="center">
              </el-table-column>
              <el-table-column label="档案状态" prop="state" show-overflow-tooltip :resizable="false"
                align="center">
              </el-table-column>
              <el-table-column label="出生日期" prop="a0107" show-overflow-tooltip :resizable="false" width="90px"
                align="center"></el-table-column>
              <el-table-column label="籍贯名称" prop="a0111a" show-overflow-tooltip width="80px" :resizable="false"
                align="center"></el-table-column>
            </el-table>
            <el-pagination style="float: right;" small="small"
            :current-page="currentPage4" :page-sizes="[100, 200, 300, 400]" :page-size="100"
            layout="total, sizes, prev, pager, next, jumper" :total="400"></el-pagination>
          <!-- </div> -->
        <!-- </el-scrollbar> -->
      </div>
      <div class="right-tree">

        <span>第二步：选择目录</span>
        <div class="tree-div">

       <el-scrollbar wrap-class="scrollbar-wrapper" class="scrollbar-border">
        <el-tree class="material-tree" ref="tree2" default-expand-all :highlight-current="true" :data="data2"
          :filter-node-method="filterNode" icon-class="el-icon-folder-opened" :expand-on-click-node="false" />
        </el-scrollbar>
        </div>
        </div>
        </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="mini">取 消</el-button>
        <el-button type="primary" size="mini">确定</el-button>
      </span>
    </el-dialog>
    <!-- 材料排序 -->
    <el-dialog title="材料排序" :close-on-click-modal="false" :visible.sync="dialogVisible1" class=" dialog-style dialog-sort"  width="50%">
        <div class="btn-sort">
          <el-button type="primary" icon="el-icon-top" @click="prov(-1)" size="mini">上移</el-button>
          <el-button type="primary" icon="el-icon-bottom" @click="prov(1)" size="mini">下移</el-button>
          <!--<el-button type="primary" icon="el-icon-video-pause" @click="sort" size="mini">保存</el-button>-->
          <span class="span-tit">当前材料类型：{{e01z101}}</span>
        </div>
      <el-table
        ref="tableRefsSort"
        :data="tableData2"
        size="mini"
        :header-cell-style="headClass"
        tooltip-effect="light"
        border
        @selection-change="selectChecked2"
        empty-text=" "
        row-key="id"
        class="table-sort"
        :stripe="true"
        @row-click="StepsListRowClickSort"
        >
        <el-table-column type="selection" :reserve-selection="true" :resizable="false" />
        <el-table-column type="index" label="序号" show-overflow-tooltip :resizable="false" align="center"></el-table-column>
        <el-table-column label="材料名称" prop="e01z111a" show-overflow-tooltip :resizable="false" align="center"></el-table-column>
        <el-table-column label="页数" prop="e01z114" show-overflow-tooltip :resizable="false" align="center" width=50px></el-table-column>
        <el-table-column label="材料制成时间" prop="e01z117" show-overflow-tooltip :resizable="false" align="center" width=100px></el-table-column>
        <!--<el-table-column label="是否有档案图片" prop="wu" show-overflow-tooltip :resizable="false" align="center" width=110px></el-table-column>-->
        <el-table-column label="备注" prop="e01z121" show-overflow-tooltip :resizable="false" align="center" width=100px></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false" size="mini">取 消</el-button>
        <el-button type="primary" size="mini">保 存</el-button>
      </span>
    </el-dialog>
    <!-- 材料调整 -->
    <el-dialog title="材料调整到其他分类" :close-on-click-modal="false" :visible.sync="dialogVisible2" class="dialog-style" width="40%">
      <el-tree class="chenge-tree" ref="tree2" default-expand-all :highlight-current="true" :data="data2"
          :filter-node-method="filterNode" icon-class="el-icon-folder-opened" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false" size="mini">取 消</el-button>
        <el-button type="primary" size="mini">确定</el-button>
      </span>
    </el-dialog>
    <!-- 导出 -->
    <el-dialog title="导出档案电子目录" :close-on-click-modal="false" :visible.sync="dialogVisible3" class="dialog-style" width="25%">
      <div class="dialog-return">
        <span class="return">导出文件类型</span>
        <el-form class="return-form">
          <el-form-item size="mini">
            <el-radio-group v-model="formreturn.state">
              <el-radio style="margin-bottom:10px" label="Excel"></el-radio>
              <el-radio style="margin-bottom:10px" label="Word"></el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible3 = false" size="mini">取 消</el-button>
        <el-button type="primary" size="mini">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getTreeGrid} from '@/api/usual/daily'

import { queryE01z1ListByA01AndE01z101} from '@/api/usually/daily/materials'
  export default {
    name:'材料管理',
    data() {
      return {
        a0101:this.$route.params.a0101,
        highSearch:{
          e01z111a:'',
          date:'',
          e01z114:'1',
          e01z121:''
        },
        checkList:[],//目录树表-单选选中
        checkList1:[],//材料转移-单选选中
        checkListSort:[],//排序-单选选中
        isEdit:false,
        showBox: false,
        showdaliog: 'true',
        rowclick: '',
        e01z101: '', //材料分类
        dialogVisible: false, //材料转移
        dialogVisible1: false, //材料排序
        dialogVisible2: false, //材料调整
        dialogVisible3: false, //导出
        dialogVisible4: false,  //新增
        //导出表单
        formreturn: {
          state: 'Excel'
        },
        //材料转移表单
        formremove: {
          a0101: ''
        },
         //材料转移-分页
        currentPage1: 5,
        currentPage2: 5,
        currentPage3: 5,
        currentPage4: 4,
        // 目录树表格数据
        tableData: [],
        //材料转移/材料调整树结构
        data2: [
          {
            id: 1,
            label: "(一)、履历类材料"
          },
          {
            id: 2,
            label: "(二)、自传和思想类材料"
          },
          {
            id: 3,
            label: "(三)、考核鉴定类材料"
          },
          {
            id: 4,
            label: "(四)、学历学位、专业技术职务（职称）、学术评鉴和教育培训类材料",
            children: [{
                id: 7,
                label: "4-1:学历学位材料"
              },
              {
                id: 8,
                label: "4-2:专业技术职务（职称）材料"
              },
              {
                id: 8,
                label: "4-3:学术评鉴材料"
              },
              {
                id: 8,
                label: "4-4:教育培训材料"
              }
            ]
          },
          {
            id: 5,
            label: "(五)、政审、审计和审核类材料"
          },
          {
            id: 6,
            label: "(六)、党、团类材料"
          },
          {
            id: 7,
            label: "(七)、表彰奖励类材料"
          },
          {
            id: 8,
            label: "(八)、违规违纪违法处理处分类材料"
          },
          {
            id: 9,
            label: "(九)、工资、任免、出国和会议代表类材料",
            children: [{
                id: 7,
                label: "9-1:工资材料"
              },
              {
                id: 8,
                label: "9-2:任免材料"
              },
              {
                id: 8,
                label: "9-3:出国(境)审批材料"
              },
              {
                id: 8,
                label: "9-4:会议代表材料"
              }
            ]
          },
          {
            id: 5,
            label: "(十)、其他可供组织参考的材料"
          }
        ],
        //材料转移表格数据
        tableData1: [
          {
            id:'1',
            a0111a: "有",
            a0101: "梁建华",
            state:'在职',
            a0104: "男",
            a0107: '2019-05-09'
          },
          {
            id:'2',
            a0111a: "有",
            a0101: "梁建华",
            state:'在职',
            a0104: "男",
            a0107: '2019-05-09'
          },
          {
            id:'3',
            a0111a: "有",
            a0101: "梁建华",
            state:'在职',
            a0104: "男",
            a0107: '2019-05-09'
          },
          {
            id:'4',
            a0111a: "有",
            a0101: "梁建华",
            state:'在职',
            a0104: "男",
            a0107: '2019-05-09'
          },{
            id:'5',
            a0111a: "有",
            a0101: "梁建华",
            state:'在职',
            a0104: "男",
            a0107: '2019-05-09'
          }
        ],
        //排序表格数据
        tableData2:[

        ],
        isTrue:true,
        tableHeight:'73%',
        tableData2Select:[],
        // searchRowHeight:'',
      }
    },
    mounted() {
      // 电子文件接口方法
      this.getCatalog();
      this.$nextTick(()=>{
//         this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 70)) + "px"
      })
    window.onresize = () => {
      return (() => {
//         this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 70)) + "px"
      })();
    };
    },
    methods: {
      tableRowClassName({row,index}){
        row.index = index;
      },
      //上移
      prov(index){
        let tmpArray = JSON.parse(JSON.stringify(this.tableData2));
        let srcindex = 0;
        this.tableData2.map((item,index)=>{
          tmpArray.map((item,index)=>{
            if(item.id == this.tableData2Select[0].id){
              srcindex = index;
            }
          })
          if(srcindex == (index==1?tmpArray.length-1:0)){
            return;
          }
        })
        let tmp = tmpArray[srcindex];
        tmpArray[srcindex] = tmpArray[srcindex + index];
        tmpArray[srcindex + index] = tmp;

        this.tableData2 = tmpArray
        this.$refs.tableRefsSort.clearSelection()
        this.$refs.tableRefsSort.toggleRowSelection(row)
      },
      next(){
        console.log(this.tableData2Select);
      },
      selectChecked2(rows){
        this.tableData2Select = rows;
      },
      // 公用表格表头样式
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:40px;font-size:12px";
      },
      //表格点击行事件
      StepsListRowClick(row){
          this.$refs.tableRefs.clearSelection()
        if (this.checkList.length === 0) {
            this.$refs.tableRefs.toggleRowSelection(row)
          return;
        }
        this.$refs.tableRefs.toggleRowSelection(row)
      },
      // 表格勾选方法
      selectChecked(data) {
        this.selectList = [...data]
        this.showdaliog = this.selectList.length === 0
      },
      // 材料转移弹框-表格点击行事件
      StepsListRowClick1(row){
          this.$refs.table1Refs.clearSelection()
        if (this.checkList1.length === 0) {
          this.$refs.table1Refs.toggleRowSelection(row)
          return;
        }
        this.$refs.table1Refs.toggleRowSelection(row)
      },
      //排序弹框-表格单击一行事件
      StepsListRowClickSort(row){
          this.$refs.tableRefsSort.clearSelection()
        if (this.checkListSort.length === 0) {
          this.$refs.tableRefsSort.toggleRowSelection(row)
          return;
        }
          this.$refs.tableRefsSort.toggleRowSelection(row)
      },
      // 新增
      onAdd() {
        if(!this.showdaliog){
          this.isEdit=false
          this.dialogVisible4 = true;
        }else {
          this.$message.warning('请勾选需要新增的材料类型')
        }
      },
      // 编辑
      onUpdate() {
        this.isEdit = true               //待收集-编辑
        if (this.showdaliog) {
          this.$message.warning('请勾选需要编辑的数据')
        } else {
          this.dialogVisible4 = true;
        }
      },
      // 删除
      onDelete() {

      },
      // 排序
      sort() {
        if(!this.showdaliog){
          this.tableData2Select = [];
          if(this.selectList[0].remark == 'catalog'){
            //十大类
            this.e01z101 = this.selectList[0].id;

          }else{
            //材料
            this.e01z101 = this.selectList[0].e01z101;
          }
          if(this.e01z101 == '4' || this.e01z101 == '9'){
            this.$message({
              type:'warning',
              message:'请勾选正确的条目'
            })
          }else{
            //加载所属类的材料
            queryE01z1ListByA01AndE01z101({a01Id:this.$route.params.id,e01z101:this.e01z101}).then(response=>{
              if(response.code == 200){
                this.tableData2 = response.data;
              }
            })
            this.dialogVisible1 = true;
          }

        }else {
          this.$message.warning('请勾选需要排序的材料类型')
        }
      },
      // 导出
      onReturn() {
        this.dialogVisible3 = true;
      },
      // 目录打印
      onCopy() {

      },
      // 材料转移
      materialMove() {
        if(!this.showdaliog){
          this.dialogVisible = true;
        }else {
          this.$message.warning('请勾选需要排序的材料类型')
        }
      },
      //树结构
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      // 材料调整
      materialChange() {
        if(!this.showdaliog){
          this.dialogVisible2 = true;
        }else {
          this.$message.warning('请勾选需要排序的材料类型')
        }
      },
      //电子文件目录
      getCatalog(){
        getTreeGrid({a01Id:this.$route.params.id}).then(response => {
          this.tableData = response.data
          console.log(this.tableData)
        })
      },
      cellStyle({ row, column, rowIndex, columnIndex }) {
      // && row.type == 1
        if(row.remark == 'catalog'){
          return 'fontSize:14px;background:#F5F7FA;color:red';
        }else{
          return "";
        }
        // if (columnIndex === 2&&row.type===1||columnIndex === 2&&row.type===2) {
        // // 指定列号
        //   return 'fontSize:14px;background:#F5F7FA;color:red'  //color:red
        // } else if(columnIndex === 1&&row.type===1||columnIndex === 1&&row.type===2){
        //   return 'fontSize:14px;background:#F5F7FA;color:red'  //color:red
        // } else if(columnIndex === 0&&row.type===1||columnIndex === 0&&row.type===2){
        //   return 'fontSize:14px;background:#F5F7FA;color:red'  //color:red
        // } else if(columnIndex === 3&&row.type===1||columnIndex === 3&&row.type===2){
        //   return 'fontSize:14px;background:#F5F7FA;color:red'  //color:red
        // } else if(columnIndex === 4&&row.type===1||columnIndex === 4&&row.type===2){
        //   return 'fontSize:14px;background:#F5F7FA;color:red'  //color:red
        // } else if(columnIndex === 5&&row.type===1||columnIndex === 5&&row.type===2){
        //   return 'fontSize:14px;background:#F5F7FA;color:red'  //color:red
        // } else if(columnIndex === 6&&row.type===1||columnIndex === 6&&row.type===2){
        //   return 'fontSize:14px;background:#F5F7FA;color:red'  //color:red
        // } else if(columnIndex === 7&&row.type===1||columnIndex === 7&&row.type===2){
        //   return 'fontSize:14px;background:#F5F7FA;color:red'  //color:red
        // } else if(columnIndex === 8&&row.type===1||columnIndex === 8&&row.type===2){
        //   return 'fontSize:14px;background:#F5F7FA;color:red'  //color:red
        // } else if(columnIndex === 9&&row.type===1||columnIndex === 9&&row.type===2){
        //   return 'fontSize:14px;background:#F5F7FA;color:red'  //color:red
        // }else {
        //   return ''
        // }
      },
      // 监听当前条目如否存在电子文件
      checkFile(row) {
        console.log(this.tableData)
        this.$router.push({
          name: "查看电子文件",
          query: {
            fileAttribute: row,
            AllFile:this.tableData,
            id:this.$route.params.id
          }
        })
      }
    }
}

</script>
<style lang="scss" scoped>
  .app-materials {
    width: 100%;
    clear: both;
    height: calc(100vh - 86px);
    .center-table {
      width: 75%;
      float: left;
      .btn {
        margin: 10px;
        .btn-btn{
          float: left;
          margin-bottom: 10px;
          .span-title {
            position: absolute;
            right: 20px;
            top: 12px;
          }
        }
        // .btn-circle {
        //   float: right;
        // }
      }
      .table{
      width: 100%;
      overflow:auto;
      }
    }
    .isShow{
      width:100%;
    }
    .center-form {
      width: 25%;
      float: right;
      margin-top: 45px;
      .btn-add {
        position: relative;
        left: 100px;
      }
    }
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
      background: rgba(204, 204, 204, 0.18);
      padding-right: 15px;
      padding: 11px;
    }
    /deep/.el-button+.el-button {
      margin-left: 6px;
    }
    //分页
    /deep/.el-pagination .el-select .el-input .el-input__inner {
      height: 20px;
    }
    /deep/.el-pagination--small .el-pagination__editor.el-input .el-input__inner{
      height: 20px;
    }
    /deep/.el-pagination {
      padding: 3px 6px;
      margin-top: 5px;
    }
  }
  // 公用树结构样式
  .tree-style{
    /deep/.el-tree--highlight-current .el-tree-node.is-current>.el-tree-node__content {
      background: rgba(204, 204, 204, 0.18);
    }
  }
  //导出弹框
  .dialog-return {
    position: relative;
    height: 160px;
    margin:10px;
    margin-left: 12%;
    width: 65%;
    border: 1px solid #CAD9E7;
    box-shadow: 0 0 3px 1px #EBF0F5;
    .return {
      position: absolute;
      top: -9px;
      left: 18px;
    }
    .return-form {
      width: 40px;
      margin-left: 20%;
      margin-top: 20px
    }
  }
  //材料转移弹框
  .dialog-center {
    width: 100%;
    height: 280px;
    clear: both;
    .div-left {
      width: 58%;
      float: left;
      margin-right:10px;
      .form-search {
        margin-top: 5px;
        margin-bottom:5px;
        font-weight: 100;
        width: 100%;
        display: flex;
      }
      .table-remove{
        // height: 217px;
      }
    }
    .right-tree {
      width: 40%;
      float: right;
      .tree-div{
        border: 2px solid rgba(204, 204, 204, 0.18);
        .material-tree {
          margin-top: 10px;
          margin-left: 20px;
          height: 250px;
        }
      }
    }
    /deep/.el-form-item {
    margin-bottom: 0px;
    }
    /deep/.el-table--border th.gutter:last-of-type{
       background-color:  rgba(204, 204, 204, 0.18);
     }
  }
  //材料调整弹框
  .chenge-tree{
    width: 100%;
    height: 300px;
    overflow: auto;
  }
  //材料排序弹框
  .dialog-sort {
    .btn-sort{
      margin-bottom: 10px;
      width: 100%;
      .span-tit{
        position: absolute;
        right: 3%;
        top: 70px;
      }
    }
    .table-sort {
      height: 300px;
      overflow: auto;
    }
    /deep/ .el-table::before{
      height: 0px !important;
    }
  }
  // 新增弹出框
  .dialog-input{
    // padding-left: 15%;
  }
  // 操作列横杠
  /deep/ .el-table .el-table__header-wrapper th{
    height: 40px;
  }
  // 操作列空白
  /deep/ .el-table__fixed-right-patch {
    background-color:#F8F8F9
  }
</style>

<template>
  <div class="app-tableDemo">
    <el-row style="height:100%">
      <el-col :span="4" :style="leftStyleName" style="height: calc(100vh - 86px)">
        <!-- 树结构 -->
        <div v-if="leftTree1" class="tree tree-style" :style="treeHeight">
          <el-scrollbar wrap-class="scrollbar-wrapper" style="height:100%;">
            <el-tree
              ref="tree2"
              :data="data2"
              :expand-on-click-node="false"
              icon-class="el-icon-folder-opened"
              class="filter-tree"
              :highlight-current="true"
              default-expand-all
              @node-click="yearClick"
              @node-contextmenu = "rightClick"
              :render-content="renderContent"
              />
            <el-row class="add_question" v-show="addNodeFlag">
              <el-col :span="24">
                <el-input class="common_input_32" v-model="newNode" @blur="onInputBlur" size="mini" placeholder="请输入年度节点"></el-input>
              </el-col>
            </el-row>
          </el-scrollbar>
        </div>
        <div v-show="menuVisible">
          <ul id="menu" class="menu">
            <li class="menu__item" @click="addCard(1)">插入当前节点</li>
            <li class="menu__item" @click="addCard(2)">插入子节点</li>
            <li class="menu__item" @click="moveToParent">移至父级节点</li>
            <li class="menu__item" @click="updateCard">重命名</li>
            <li class="menu__item" @click="moveUp">上移</li>
            <li class="menu__item" @click="moveDown">下移</li>
            <li class="menu__item" @click="deleteCard">删除</li>
          </ul>
        </div>
        <div class="shrinkageDiv">
            <div @click="divClick()" :class="picClassname"></div>
        </div>
      </el-col>
      <el-col :span="18" style="height: calc(100vh - 86px)" :style="rightStyleName">
        <!--检索   开始    -->
        <el-row ref="searchRow"  :gutter="10"  style="margin:0 0 0px 0;padding-left: 0px">
          <el-form
            ref="queryForm"
            :model="queryForm"
            :inline="true"
            label-width="85px"
            class="search-form"
            >
            <!--<el-form-item label="干部名称" prop="personName" style="margin-top: 10px;margin-bottom: 10px;">
              <el-input v-model="queryForm.personName" placeholder="请输入干部名称" clearable size="mini" style="width:150px"/>
            </el-form-item>
            <el-form-item label="转入部门" prop="e01z507a" style="margin-top: 10px;margin-bottom: 10px;">
              <el-input v-model="queryForm.e01z507a" placeholder="请输入部门名称" clearable size="mini" style="width:150px"/>
            </el-form-item>-->
            <el-form-item label="收件日期起" prop="e01z501" style="margin-top: 10px;margin-bottom: 10px">
              <el-date-picker v-model="queryForm.startTime" type="date" placeholder="请选择收件日期" size="mini" style="width:150px"/>
            </el-form-item>
            <el-form-item label="收件日期至" prop="e01z501" style="margin-top: 10px;margin-bottom: 10px">
              <el-date-picker v-model="queryForm.endTime" type="date" placeholder="请选择收件日期" size="mini" style="width:150px"/>
            </el-form-item>
            <el-form-item style="margin-top: 10px;margin-bottom: 10px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
              <!--<el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>-->
            </el-form-item>
          </el-form>
          <div class="btn">
            <!--表单添加-->
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="addRow">新增接收记录</el-button>
            <el-button type="primary" icon="el-icon-upload2" size="mini" @click="handleExport">导出Excel</el-button>
            <span class="span-title">共接收档案{{amount}}本</span>
            <!--<el-button type="primary" icon="el-icon-edit" size="mini" @click="handleUpData">编辑</el-button>
            <el-button type="primary" icon="el-icon-delete" size="mini" @click="handleDelete">删除</el-button>
            <el-button type="primary" icon="el-icon-download" size="mini" @click="handlePrint">打印</el-button>
            <div class="btn-search">
              <el-button type="primary" icon="el-icon-search" size="mini" circle @click="function(){showSearchBox = !showSearchBox;tableHeight = showSearchBox?'73%':'84%'}" />
              <el-dropdown style="float: right; margin-left: 5px;">
                <el-button icon="el-icon-menu" size="mini" circle />
                <el-dropdown-menu style="padding: 10px">
                  <el-checkbox-group v-for="(item, index) in col" :key="index" v-model="item.isCol" size="mini">
                    <el-checkbox>{{ item.label }}</el-checkbox>
                  </el-checkbox-group>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            -->
          </div>
        </el-row>
        <el-table
          size="mini"
          :data="tableData"
          ref="tableDataRefs"
          stripe
          border
          empty-text=" "
          tooltip-effect="light"
          style="width: 100%;"
          :header-cell-style="headClass"
          :height="tableHeight"
          @row-dblclick="RowDblclick"
          @row-click="StepsListRowClick"
          @selection-change="handleSelectionChange"
          >
          <el-table-column align="center" type="selection" width="45" />
          <el-table-column align="center" show-overflow-tooltip label="序号" type="index" min-width="60" />
          <el-table-column label="id" v-if="false">
            <template slot-scope="scope">
              <el-input v-model="scope.row.id"/>
            </template>
          </el-table-column>
          <el-table-column label="干部姓名" align="center" prop="a0101" show-overflow-tooltip width="100px">
            <template slot-scope="scope">
              <el-input v-show="doubleclick==scope.row.id" placeholder="请输入干部姓名" v-model="scope.row.a0101" size="mini"></el-input>
              <span v-show="doubleclick!=scope.row.id">{{scope.row.a0101}}</span>
              <!--<el-input v-model="scope.row.a0101" size="mini" placeholder="请输入干部姓名"/>-->
            </template>
          </el-table-column>
          <el-table-column label="转档单位" align="center" prop="e01z507a" show-overflow-tooltip width="140px">
            <template slot-scope="scope">
              <!--<treeselect
                v-model="scope.row.deptId"
                filterable
                :normalizer="deptNormalizer"
                :options="deptOptions"
                placeholder="请选择所属部门"
              />-->
              <el-input v-show="doubleclick==scope.row.id" v-model="scope.row.e01z507a" size="mini" placeholder="请选择转档单位">
                <i slot="suffix" @click="openCardCode(scope.$index)" class="el-input__icon el-icon-search"></i>
              </el-input>
              <span v-show="doubleclick!=scope.row.id">{{scope.row.e01z507a}}</span>
            </template>
          </el-table-column>
          <!--<el-table-column label="职务" align="center" prop="duty" show-overflow-tooltip width="120px">
            <template slot-scope="scope">
              <el-select v-model="scope.row.duty" filterable placeholder="请选择职务" size="mini">
                <el-option v-for="item in dutys" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
              </el-select>
            </template>
          </el-table-column>-->
          <el-table-column label="接收时间" align="center" prop="e01z501" show-overflow-tooltip width="140px">
            <template slot-scope="scope">
              <el-input v-show="doubleclick==scope.row.id" v-model="scope.row.e01z501" size="mini" @blur="checkDate(scope.row.e01z501, scope.$index, 1)"/>
              <span v-show="doubleclick!=scope.row.id">{{scope.row.e01z501}}</span>
              <!--<el-date-picker v-model="scope.row.e01z501" type="date" placeholder="请选择接收时间" size="mini"/>-->
            </template>
          </el-table-column>
          <el-table-column label="接收年度" align="center" prop="yearGd" show-overflow-tooltip width="140px">
            <template slot-scope="scope">
              <el-select  v-show="doubleclick==scope.row.id" v-model="scope.row.yearGd" filterable placeholder="请选择接收年度" size="mini">
                <el-option v-for="item in data2[0].children" :key="item.id" :label="item.label" :value="item.label" />
              </el-select>
              <span v-show="doubleclick!=scope.row.id">{{scope.row.yearGd}}</span>
            </template>
          </el-table-column>
          <el-table-column label="接收人" align="center" prop="e01z517" show-overflow-tooltip width="100px">
            <template slot-scope="scope">
              <el-input v-show="doubleclick==scope.row.id" v-model="scope.row.e01z517" size="mini" placeholder="请输入接收人姓名" />
              <span v-show="doubleclick!=scope.row.id">{{scope.row.e01z517}}</span>
            </template>
          </el-table-column>
          <!--<el-table-column label="正本数">
            <template slot-scope="scope">
              <el-input v-model="scope.row.e01z711" type="number" size="mini"></el-input>
            </template>
          </el-table-column>-->
          <el-table-column label="入库时间" align="center" prop="e01z534" show-overflow-tooltip width="140px">
            <template slot-scope="scope">
              <el-input v-show="doubleclick==scope.row.id" v-model="scope.row.e01z534" size="mini" @blur="checkDate(scope.row.e01z534, scope.$index, 2)"/>
              <span v-show="doubleclick!=scope.row.id">{{scope.row.e01z534}}</span>
              <!--<el-date-picker v-model="scope.row.e01z534" type="date" placeholder="请选择入库时间" size="mini"/>-->
            </template>
          </el-table-column>
          <el-table-column label="备注" align="center" prop="e01z544" show-overflow-tooltip width="200px">
            <template slot-scope="scope">
              <el-input v-show="doubleclick==scope.row.id" v-model="scope.row.e01z544" size="mini" placeholder="请输入备注信息" />
              <span v-show="doubleclick!=scope.row.id">{{scope.row.e01z544}}</span>
            </template>
          </el-table-column>
          <el-table-column label="备注1" align="center" prop="e01z544" show-overflow-tooltip width="200px">
            <template slot-scope="scope">
              <el-input v-show="doubleclick==scope.row.id" v-model="scope.row.e01z544" size="mini" placeholder="请输入备注信息" />
              <span v-show="doubleclick!=scope.row.id">{{scope.row.e01z544}}</span>
            </template>
          </el-table-column>
          <el-table-column label="备注2" align="center" prop="e01z544" show-overflow-tooltip width="200px">
            <template slot-scope="scope">
              <el-input v-show="doubleclick==scope.row.id" v-model="scope.row.e01z544" size="mini" placeholder="请输入备注信息" />
              <span v-show="doubleclick!=scope.row.id">{{scope.row.e01z544}}</span>
            </template>
          </el-table-column>
          <el-table-column label="备注3" align="center" prop="e01z544" show-overflow-tooltip width="200px">
            <template slot-scope="scope">
              <el-input v-show="doubleclick==scope.row.id" v-model="scope.row.e01z544" size="mini" placeholder="请输入备注信息" />
              <span v-show="doubleclick!=scope.row.id">{{scope.row.e01z544}}</span>
            </template>
          </el-table-column>
          <el-table-column label="备注4" align="center" prop="e01z544" show-overflow-tooltip width="200px">
            <template slot-scope="scope">
              <el-input v-show="doubleclick==scope.row.id" v-model="scope.row.e01z544" size="mini" placeholder="请输入备注信息" />
              <span v-show="doubleclick!=scope.row.id">{{scope.row.e01z544}}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" align="center" width="100px">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="editRow(scope.$index, scope.row)" v-show="doubleclick!=scope.row.id">编辑</el-button>
              <el-button size="mini" type="text" @click="saveRow(scope.$index, scope.row)" v-show="doubleclick==scope.row.id" style="margin: 0px">保存</el-button>
              <span style="color: #1890ff">|</span>
              <el-button size="mini" type="text" @click="deleteRow(scope.$index)" v-show="doubleclick!=scope.row.id">删除</el-button>
              <el-button size="mini" type="text" style="margin: 0px" @click="cancelRow(scope.row, scope.$index)" v-show="doubleclick==scope.row.id">取消</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
        <div class="block" style="float: right;padding: 10px 10px 0px 10px">
          <el-pagination
            :page-sizes="[20, 50, 100]"
            :page-size="20"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="pageSizes"
            @current-change="pageCurrent"
          />
        </div>
      </el-col>
    </el-row>
    <!-- 选择部门 -->
    <el-dialog title="选择部门" :close-on-click-modal="false" :visible.sync="dialogVisible" class=" dialog-style tree-style" width="30%">
      <div class="dialog-tree">
        <el-input placeholder="请输入搜索内容" clearable v-model="filterText3" style="margin-left: 10px;width: 68%"/>
        <el-tree
          style="overflow: auto"
          ref="treeDataRefs"
          :data="treeData"
          :props="TreeDefaultProps"
          :expand-on-click-node="false"
          icon-class="el-icon-folder-opened"
          :filter-node-method="filterNode3"
          @node-click="treeNodeClick"
          default-expand-all/>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="selectedDept">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import { queryDictionaryByType, queryDeptTree } from '@/api/usual/receive'
  import { getDeptTree } from "@/api/system/dept";
  import { queryE01z5s } from '@/api/usual/parameter/receives-paramenter'

  const params = {
    pageNum: 1,
    pageSize: 20
  }
  export default {
    name: 'TableDemo',
    components: {},
    watch: {
      filterText3(val) {
        this.$refs.tree3.filter(val);
      },
      treeFilter(val) {
        this.$refs.tree4.filter(val);
      },
      tableData:{
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(()=>{
            _this.$refs.tableDataRefs.doLayout();
          })
        },
        deep:true,
        immediate:true
      },
    },
    data() {
      return {
        menuVisible: false,
        addNodeFlag: false,
        addNodeForm: {},
        newNode: '',
        isChild: false,
        isRoot: false,
        obj: {},
        node: {},
        /* 总页数 */
        total: 0,
        pageNum: 1,
        pageSize: 20,
        /* 刷新遮罩 */
        loading: true,
        picClassname: 'shrinkageLeft',
        // 树结构数据
        data2: [{
          id: '0',
          label:'接收年度',
          isEdit: false,
          children:[
            {
              id: '10',
              label:'2010',
              isEdit: false,
              children:[]
            },
            {
              id: '11',
              label:'2011',
              isEdit: false,
              children:[]
            },
            {
              id: '12',
              label:'2012',
              isEdit: false,
              children:[]
            },
            {
              id: '13',
              label:'2013',
              isEdit: false,
              children:[]
            },{
              id: '14',
              label:'2014',
              isEdit: false,
              children:[]
            },{
              id: '15',
              label:'2015',
              isEdit: false,
              children:[]
            },{
              id: '16',
              label:'2016',
              isEdit: false,
              children:[]
            },{
              id: '17',
              label:'2017',
              isEdit: false,
              children:[]
            },{
              id: '18',
              label:'2018',
              isEdit: false,
              children:[]
            },{
              id: '19',
              label:'2019',
              isEdit: false,
              children:[]
            },{
              id: '20',
              label:'2020',
              isEdit: false,
              children:[]
            }]
        }],
        checkYear: '',
        rightStyle: {
          width: '84%', height: 'calc(100vh - 86px)', padding: '10px', height: 'calc(100vh - 86px)'
        },
        rightStyle1: {
          width: '99%', height: 'calc(100vh - 86px)', padding: '10px', height: 'calc(100vh - 86px)'
        },
        leftStyle: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc',position: 'relative'
        },
        leftStyle1: {
          width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc',position: 'relative'
        },
        leftStyleName: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc',position: 'relative'
        },
        rightStyleName: {
          width: '84%', height: 'calc(100vh - 86px)', padding: '10px', height: 'calc(100vh - 86px)'
        },
        leftTree1: true,
        tableHeight:'73%',
        treeHeight:"height:500px",
        // 搜索区
        queryForm: {},
        searchRow: 'searchRow',
        tableData: [],
        amount: 0, // 共接收*本
        doubleclick: null,
        isEdit: 0,
        dutys: [],
        //部门
        dialogVisible: false,
        filterText3: '',
        treeData:[],
        TreeDefaultProps: {
          children: "children",
          label: "deptName",
        },
        oldRow: {},
        index: '',
        selectDept:{
          e01z507a: '',
          deptId:'',
          // id:[]
        },
        // 部门树数据
        /*deptOptions: [],
        deptNormalizer(node) {
          // 去掉children=[]的children属性
          if (node.children && !node.children.length) {
            delete node.children
          }
          return {
            id: node.id,
            label: node.e01z507a,
            children: node.children
          }
        },*/
        //复选框
        checkbox: [],
        checkboxDatas: [],
      }
    },
    mounted(){
      console.log(this.data2)
      this.getComboxDatas()
      this.receiveQuery(params)
      this.$nextTick(()=>{
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = this.tableHeight = (document.body.clientHeight - (86 + 65 + this.searchRowHeight)) + "px"

        let height = (document.body.clientHeight - 86) + "px";
        this.treeHeight = "height:"+height;

      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (86 + 65 + this.searchRowHeight)) + "px"
          let height = (document.body.clientHeight - 86) + "px";
          this.treeHeight = "height:"+height;
        })();
      };
    },
    //字典表获取
    created() {

    },
    methods: {
      // 公用表格表头样式
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      /*:header-cell-class-name="headerStyle"
      headerStyle ({row, column, rowIndex, columnIndex}) {
        return 'tableStyle'
      },
      // 修改table tr行的背景色:row-style="tableRowStyle"
      tableRowStyle ({ row, rowIndex }) {
        return 'background-color: #C7EDCC!important;'
      },*/
      // 获取页面数据
      receiveQuery(params) {
        queryE01z5s(params)
          .then(response => {
            this.tableData = response.rows
            this.$nextTick(()=>{
              this.$refs.tableDataRefs.doLayout();
            })
            this.total = response.total
            this.amount = response.total
            this.loading = false
            this.tableData.forEach(td =>{
              if (td.e01z501 != null && td.e01z501 != '') {
                var date = new Date(td.e01z501)
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                m = m < 10 ? '0' + m : m;
                var d = date.getDate();
                d = d < 10 ? ('0' + d) : d;
                td.e01z501 = y + '-' + m + '-' + d;
              }
              if (td.e01z534 != null && td.e01z534 != '') {
                var date = new Date(td.e01z534)
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                m = m < 10 ? '0' + m : m;
                var d = date.getDate();
                d = d < 10 ? ('0' + d) : d;
                td.e01z534 = y + '-' + m + '-' + d;
              }
              return this.tableData
            })
          })
      },
      // 年度树收起展开
      divClick() {
        this.leftTree1 = !this.leftTree1
        if (this.leftTree1) {
          this.rightStyleName = this.rightStyle
          this.leftStyleName = this.leftStyle
          this.picClassname = 'shrinkageLeft'
          this.treeShow =! this.treeShow
        } else {
          this.rightStyleName = this.rightStyle1
          this.leftStyleName = this.leftStyle1
          this.picClassname = 'shrinkageRight'
          this.treeShow =! this.treeShow
        }
      },
      // 部门调整的部门树
      filterNode3(value, data){
        if (!value) return true;
        return data.e01z507a.indexOf(value) !== -1;
      },
      treeNodeClick(data){
        console.log(data)
        this.selectDept.e01z507a=data.deptName;
        this.selectDept.deptId=data.id;
      },
      // 格式转换
      getFullName(row, col) {
        // 时间转换
        if (col.property == "e01z501" || col.property == "e01z534") {
          if (row[col.property] != null && row[col.property] != '') {
            var date = new Date(row[col.property])
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            m = m < 10 ? '0' + m : m;
            var d = date.getDate();
            d = d < 10 ? ('0' + d) : d;
            return y + '-' + m + '-' + d;
          }
        }
      },
      // 获取部门树
      getComboxDatas() {
        queryDeptTree({deptType: 1}).then(response => {
          this.treeData = response.data
          console.log(this.treeData)
        })
        // 获取职务
        queryDictionaryByType({ dictType: 'ZB09', eparentCode: '0' })
          .then(response => {
            this.dutys = response.data
          })
      },
      // 选择部门dialog
      openCardCode(val){
        this.dialogVisible = true
        this.index = val
      },
      // 确定部门
      selectedDept(){
        if (this.selectDept.deptId==""){
          this.$message.warning('请选择所属部门')
          return;
        }
        this.dialogVisible = false
        this.$nextTick(()=>{
          this.tableData[this.index].e01z507a = this.selectDept.e01z507a
          this.tableData[this.index].deptId = this.selectDept.deptId
        });
        console.log(this.tableData[this.index])
      },
      // 获取uuid
      generateUUID() {
        let d = new Date().getTime()
        if (window.performance && typeof window.performance.now === 'function') {
          d += performance.now() //use high-precision timer if available
        }
        let uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
          var r = (d + Math.random() * 16) % 16 | 0
          d = Math.floor(d / 16)
          return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16)
        })
        var reg = new RegExp('-','g');
        return uuid.replace(reg, '')
      },
      pageCurrent(val) {
        this.pageNum = val
        const params = {
          pageNum: val,
          pageSize: this.pageSize
        }
        //this.receiveQuery(params)
      },
      pageSizes(val) {
        this.pageSize = val
        this.pageNum = 1
        const params = {
          pageNum: 1,
          pageSize: val
        }
        //this.receiveQuery(params)
      },
      // 年度筛选
      yearClick(data){
        let params = {
          pageNum: 1,
          pageSize: 20
        }
        if(data.id!='0'){
          params.otherParam = data.id
        }else{
          params.otherParam = ''
        }
        this.checkYear = data.id
        console.log(data)
        this.receiveQuery(params)
      },
      search() {
        let param = {}
        param.pageNum = '1'
        param.pageSize = '20'
        // param.personName = this.queryForm.personName
        // param.e01z507a = this.queryForm.e01z507a
        if(this.queryForm.startTime!=null && this.queryForm.startTime!=""){
          param.startTime = this.parseTime(this.queryForm.startTime)
        }
        if(this.queryForm.endTime!=null && this.queryForm.endTime!=""){
          param.endTime = this.parseTime(this.queryForm.endTime)
        }
        param.checkYear = this.checkYear
        console.log(param)
        this.receiveQuery({searchValue:JSON.stringify(param)})
      },
      // 保留数据
      initOldData(){
        if(this.oldRow.a0101==null){
          this.oldRow.a0101=''
        }
        if(this.oldRow.e01z507a==null){
          this.oldRow.e01z507a=''
        }
        if(this.oldRow.e01z501==null){
          this.oldRow.e01z501=''
        }
        if(this.oldRow.yearGd==null){
          this.oldRow.yearGd=''
        }
        if(this.oldRow.e01z517==null){
          this.oldRow.e01z517=''
        }
        if(this.oldRow.e01z534==null){
          this.oldRow.e01z534=''
        }
        if(this.oldRow.e01z544==null){
          this.oldRow.e01z544=''
        }
      },
      // 行的双击事件 / 注意:双击事件会触发两次行的单击事件
      RowDblclick(row, column, event) {
        //this.$message("成功双击触发事件");
        this.doubleclick = row.id;
        this.oldRow = {}
        this.oldRow = JSON.parse(JSON.stringify(row))
        this.initOldData()
      },
      // 点击加号添加一行数据
      addRow() {
        let date = new Date()
        let y = date.getFullYear();
        let m = date.getMonth() + 1;
        m = m < 10 ? '0' + m : m;
        let d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        date =  y + '-' + m + '-' + d;
        let newRow = {
          "id": this.generateUUID(),
          "a0101": "",
          "e01z507a": "",
          "deptId": "",
          //"e01z711": 0,
          //"duty": "",
          "e01z501": date,
          "yearGd": "2020",
          "e01z517": "",
          "e01z534": "",
          "e01z544": "",
          "isNew": true,
        };
        this.tableData.splice(this.tableData, 0, newRow);
        this.doubleclick = newRow.id;
      },
      deleteRow(index) {
        this.$confirm('是否确定删除当前接收的档案记录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.tableData.splice(index, 1);
          })
          .catch(() => {
          })
      },
      editRow(index,row){
        this.doubleclick = row.id;
        this.index = index;
        this.oldRow = {}
        this.oldRow = JSON.parse(JSON.stringify(row))
        this.initOldData()
      },
      saveRow(index,row){
        console.log(index)
        console.log(row)
        if(row.a0101 == '' || row.a0101 == null){
          this.$message({
            message: '请填写干部姓名后保存',
            type: 'error'
          })
          return false
        }
        if(row.yearGd == '' || row.yearGd == null){
          this.$message({
            message: '请选择接收年度后保存',
            type: 'error'
          })
          return false
        }
        if(!this.checkDate(row.e01z501, index, 1) ||!this.checkDate(row.e01z534, index, 2) ){
          this.$message({
            message: '请填写正确的日期格式后保存',
            type: 'error'
          })
          return false
        }
        this.tableData[index] = JSON.parse(JSON.stringify(row))
        this.tableData[index].isNew = false
        this.oldRow={};
        this.doubleclick = null
      },
      cancelRow(row, index){
        if (row.isNew){
          this.tableData.splice(index, 1);
        }else {
          Object.assign(row, this.oldRow)
        }
        this.doubleclick = null
      },
      checkDate(str, index, type){
        console.log(str)
        if (str=='') {
          return false;
        }
        if (str.length != 8 && str.length != 10) {
          this.$message({
            message: '请输入正确的日期格式，如20200101或2020-01-01',
            type: 'warning'
          })
          return false;
        }
        let reg=/^(\d{4})(\d{2})(\d{2})$/;
        let arr=reg.exec(str);
        let reg1=/^(\d{4})-(\d{2})-(\d{2})$/;
        let arr1=reg1.exec(str);
        console.log(arr)
        if((!reg.test(str) && !reg1.test(str)) || parseInt(RegExp.$2)>12 || parseInt(RegExp.$3)>31){
          this.$message({
            message: '请输入正确的日期格式，如20200306或2020-03-06',
            type: 'warning'
          })
          return false;
        }
        if(arr != '' && arr != null){
          str = RegExp.$1 + '-' + RegExp.$2 + '-' + RegExp.$3
        }
        if(type === 1){
          this.$nextTick(()=>{
            this.tableData[index].e01z501 = str
          });
        }else {
          this.$nextTick(()=>{
            this.tableData[index].e01z534 = str
          });
        }
        return true;
      },
      handleExport(){},
      rightClick(MouseEvent, object, Node, element) { // 鼠标右击触发事件
        this.menuVisible = false // 先把模态框关死，目的是 第二次或者第n次右键鼠标的时候 它默认的是true
        this.menuVisible = true  // 显示模态窗口，跳出自定义菜单栏
        var menu = document.querySelector('#menu')
        if(MouseEvent.clientX < 200){
          menu.style.left = MouseEvent.clientX - 50 + 'px'
          console.log(menu.style.left)
        }else {
          menu.style.left = MouseEvent.clientX - 200 + 'px'
        }
        document.addEventListener('click', this.foo) // 给整个document添加监听鼠标事件，点击任何位置执行foo方法
        let y = document.body.clientHeight - MouseEvent.clientY
        console.log(y)
        if(y < 220){
          menu.style.top = MouseEvent.clientY - 320 + 'px'
          console.log(menu.style.top)
        }else {
          menu.style.top = MouseEvent.clientY - 100 + 'px'
        }
        this.obj = object
        this.node = Node
        console.log('右键被点击的event:', MouseEvent)
        console.log('右键被点击的object:', object)
        console.log('右键被点击的value:', Node)
        console.log('右键被点击的element:', element)
        console.log('鼠标点击了树形结构图')
      },
      foo() { // 取消鼠标监听事件 菜单栏
        this.menuVisible = false
        document.removeEventListener('click', this.foo) // 关监听
      },
      // 移至父节点
      moveToParent(){
        console.log(this.node.parent.data.children)
        if(this.node.parent=='' && this.node.parent==null){
          this.$message({
            showClose: true,
            message: '该子节点没有父级节点',
            type: 'warning'
          })
          return
        }
        this.deleteCard()
        this.data2 = this.node.parent.data.children.push(this.node)
      },
      // 上移
      moveUp(){

      },
      // 下移
      moveDown(){},
      // 新增树节点
      addCard(MouseEvent) {
        console.log(MouseEvent)
        console.log(this.isChild)
        this.addNodeFlag = true
        if(MouseEvent == 2){
          this.isChild = true
        }else if(MouseEvent == 1){
          this.isChild = false
        }
        if (this.obj.id === '0'){
          this.isRoot = true
        }else {
          this.isRoot = false
        }
      },
      // input失去焦点事件
      onInputBlur(){
        let id = this.generateUUID()
        console.log(id)
        const nodeObj = {
          id: id,
          label: this.newNode.trim(),
          isEdit: false,
          children: []
        }
        console.log(nodeObj.id)
        console.log(this.isChild)
        console.log(this.isRoot)
        if(this.isChild){ // 添加子节点
          this.node.data.children.push(nodeObj)
        }else if (this.isChild==false && this.isRoot==false && this.node.parent.data.children!='' && this.node.parent.data.children!=null){ // 非1级平级
          this.node.parent.data.children.push(nodeObj)
        }else { // 根节点下一级节点平级
          this.data2.push(nodeObj)
        }
        this.addNodeFlag = false;
        this.newNode = '';
      },
      // 编辑树
      updateCard(ev, store, data){
        console.log(ev)
        this.obj.isEdit = true;
      },
      // 树操作
      renderContent(h, {node, data, store}) {
        return (
          <div class='custom_tree_node'>
            <p class='tree_node_name'>
              { this.showOrEdit(data) }
            </p>
          </div>
        );
      },
      // 编辑树的input
      showOrEdit(data) {
        if (data.isEdit) {
          return <input style="width:90%" value={data.label} on-blur={ev => this.editSure(ev, data)}/>;
        } else {
          return data.label;
        }
      },
      // 编辑树结果确定
      editSure(ev, data) {
        const $input = ev.target.parentNode.parentNode.querySelector('input') || ev.target.parentElement.parentElement.querySelector('input');
        if (!$input) {
          return false;
        } else {
          data.label = $input.value;
          data.isEdit = false;
        }
      },
      // 删除树节点
      deleteCard() {
        const parent = this.node.parent;
        const children = parent.data.children || parent.data;
        const index = children.findIndex(d => d.id === this.node.data.id);
        console.log(parent)
        console.log(children)
        console.log("index"+index)
        children.splice(index, 1);
      },
      // 复选框事件
      handleSelectionChange(val) {
        this.checkboxDatas = val
        this.checkbox = val.map(item => item.id)
      },
      // 行点击事件
      StepsListRowClick(row){
        this.$refs.tableDataRefs.clearSelection()
        if (this.checkbox.length === 0) {
          this.$refs.tableDataRefs.toggleRowSelection(row)
          return;
        }
        this.$refs.tableDataRefs.toggleRowSelection(row)
      },
    }
  }
</script>

<style lang="scss" scoped>
  .app-tableDemo {
    height: 100%;
    /*background-color: #C7EDCC;*/
    .tree {
      width: 100%;
      padding: 20px;
      border-right: 1px solid #e2e3e6;
    }
    .search-form{
      width:100%;
      background:rgba(204, 204, 204, 0.18);
      padding-left: 10px;
      padding-bottom:10px;
      display:flex;
      .form-width{
        width:22%;
        display:flex;
      }
      /deep/ .el-form-item{
        margin-bottom: 0 !important;
      }
    }
    .btn {
      width:100%;
      position: relative;
      padding-bottom: 10px;
      padding-top: 10px;
      padding-left: 5px;
      display:flex;
      .btn-search {
        position: absolute;
        right: 0px;
        top: 10px;
        padding-bottom: 20px;
      }
    }
    .page {
      float: right;
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
  }
  // 设置tree滚动条样式
  /deep/ .el-scrollbar .el-scrollbar__wrap {
    overflow-x: hidden;
  }
  // tree横向滚动条
  /deep/ .el-tree-node{
    min-width: 100px;
    //display:inline-block;
  }
  // tree节点icon大小
  /deep/ .el-tree-node__expand-icon{
    font-size: 14px;
  }
  // 设置列高
  /deep/ .el-table .el-table__header-wrapper th{
    height:31px;
    /*color: #909399;
    border-bottom: 2px solid white;*/
  }
  /*// 背景颜色
  .tableStyle {
    font-size:12px;
    font-weight:400;
  }
  /deep/ .el-tree {
    background-color: #C7EDCC;
  }
  /deep/ .el-table__empty-block{
    background-color: #C7EDCC;
  }
  /deep/ .el-select-dropdown__list{
    background-color: #C7EDCC;
  }
  // 表格边框颜色
  /deep/ .el-table th.is-leaf{
    border-left: 1px solid white;
    border-bottom: 2px solid white;
    border-top: 2px solid white;
    background-color: #C7EDCC!important;
  }
  /deep/ .el-table td{
    border-left: 1px solid white;
    border-bottom: 1px solid white;
  }*/
  // 操作列颜色问题
  /deep/ .el-table__fixed-right-patch {
    background-color:#F8F8F9;
    /*background-color: #C7EDCC;
    border-bottom: 2px solid white;
    border-top: 2px solid white;
    border-left: 1px solid white;
    border-right: 2px solid white;*/
  }
  // 表格右上角title样式
  .span-title{
    font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, Arial, sans-serif;
    color: #909399;
    font-size: 15px;
    padding-top: 5px;
    margin-left: auto;
    margin-right: 50px;
  }
  // 右键菜单样式
  .menu__item {
    display: block;
    line-height: 20px;
    text-align: center;
    margin-top: 10px;
  }
  .menu {
    font-size:12px;
    height: 220px;
    width: 120px;
    position: absolute;
    z-index: 10;
    //border-radius: 10px;
    padding-left: 0px;
    border: 1px solid #999999;
    background-color: #f4f4f4;
  }
  li:hover {
    background-color: #1790ff;
    color: white;
  }
  .shrinkageLeft{
      border-right: 8px solid #FFFFFF;
      border-top: 8px solid transparent;
      border-bottom: 8px solid transparent;
      position: absolute;
      width: 8px;
      height: 8px;
      left: 0px;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
      cursor:pointer;
  }
  .shrinkageRight{
      border-left: 8px solid #FFFFFF;
      border-top: 8px solid transparent;
      border-bottom: 8px solid transparent;
      position: absolute;
      width: 8px;
      height: 8px;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
      cursor:pointer;
  }
  .shrinkageDiv{
      background-color: #1890ff;
      right: 0;
      bottom: 0;
      margin: auto;
      top: 0;
      height: 200px;
      width: 8px;
      border-radius: 20px;
      position: absolute;
  }
</style>

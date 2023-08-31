<template>
  <div class="app-receives-paramenter">
    <el-row style="height:100%">
      <el-col :span="4" :style="leftStyleName" style="height: calc(100vh - 86px)">
        <!-- 树结构 -->
        <div v-if="leftTree1" class="tree tree-style" :style="treeHeight">
          <el-scrollbar>
            <el-tree
              ref="tree2"
              :data="data2"
              :filter-node-method="filterNode"
              :expand-on-click-node="false"
              icon-class="el-icon-folder-opened"
              class="filter-tree"
              :highlight-current="true"
              default-expand-all
              @node-click="treeNodeClick"
            />
          </el-scrollbar>
        </div>
        <div class="shrinkageDiv" @click="divClick">
          <div :class="picClassname"></div>
        </div>
      </el-col>
      <el-col :span="18" style="height: calc(100vh - 86px)" :style="rightStyleName">
        <!--检索   开始    -->
        <el-row ref="searchRow" :gutter="10">
          <el-form
            ref="queryForm"
            :model="queryForm"
            v-if="showSearchBox"
            :inline="true"
            label-width="70px"
            class="search-form"
            v-hasPermi="['parameter:receivesRecord:search']"
            >
            <el-form-item @keyup.enter.native="onEnterSearch" label="干部姓名" prop="personName" style="margin-top: 10px;margin-bottom: 10px;">
              <el-input v-model="queryForm.personName" placeholder="请输入干部姓名" clearable size="mini"
                        style="width:150px;margin-top: 5px;"/>
            </el-form-item>
            <el-form-item @keyup.enter.native="onEnterSearch" label="转入部门" prop="deptName" style="margin-top: 10px;margin-bottom: 10px;">
              <el-input v-model="queryForm.deptName" placeholder="请输入部门名称" clearable size="mini"
                        style="width:150px;margin-top: 5px;"/>
            </el-form-item>
            <el-form-item @keyup.enter.native="onEnterSearch" label="收件日期" prop="e01z501" style="margin-top: 10px;margin-bottom: 10px">
              <el-date-picker v-model="queryForm.e01z501" type="date" placeholder="请选择收件日期" value-format="yyyyMMdd" size="mini" style="width:150px;margin-top: 5px;"/>
            </el-form-item>
            <el-form-item style="margin-top: 15px;">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
<!--              <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>-->
            </el-form-item>
          </el-form>
          <div class="btn">
            <el-button type="primary" icon="el-icon-plus" size="mini" v-hasPermi="['parameter:receivesRecord:add']" @click="addRecord">新增</el-button>
            <el-button type="primary" icon="el-icon-edit" size="mini" v-hasPermi="['parameter:receivesRecord:edit']" @click="handleUpData">编辑</el-button>
            <el-button type="primary" icon="el-icon-delete" size="mini" v-hasPermi="['parameter:receivesRecord:delete']" @click="handleDelete">删除</el-button>
            <el-button type="primary" icon="el-icon-download" size="mini" v-hasPermi="['parameter:receivesRecord:import']" @click="handleExport">导出</el-button>
            <!-- <div class="btn-search">
              <el-button type="primary" icon="el-icon-search" size="mini" circle @click="function(){showSearchBox = !showSearchBox;tableHeight = showSearchBox?'73%':'84%'}" />
            <el-dropdown style="float: right; margin-left: 5px;">
                <el-button icon="el-icon-menu" size="mini" circle />
                <el-dropdown-menu style="padding: 10px">
                  <el-checkbox-group v-for="(item, index) in col" :key="index" v-model="item.isCol" size="mini">
                    <el-checkbox>{{ item.label }}</el-checkbox>
                  </el-checkbox-group>
                </el-dropdown-menu>
              </el-dropdown>
            </div> -->
          </div>
        </el-row>
        <el-table
          size="mini"
          v-loading="loading"
          ref="tableRefs"
          :data="tableData"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          border
          empty-text=" "
          :height="tableHeight"
          :stripe="true"
          row-key="id"
          tooltip-effect="light"
          :header-cell-style="headClass"
          align="center"
          @row-click="StepsListRowClick"
          @selection-change="handleSelectionChange"
          >
          <el-table-column type="selection" width="50"/>
          <el-table-column align="center" show-overflow-tooltip label="序号" :index="customIndex" type="index" min-width="60"/>
          <el-table-column
            align="center"
            show-overflow-tooltip
            v-for="(item, index) in col"
            v-if="col[index].isCol"
            :key="index"
            :width="item.width"
            :formatter="item.formatter"
            :prop="col[index].prop"
            :label="item.label"/>
          <!--
          <el-table-column align="center" fixed="right" label="操作" width="100px">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="handleUpData(scope.row)">编辑</el-button>
              <el-button type="text" size="small" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
          -->
        </el-table>
        <!-- 分页 -->
        <div class="block" style="float: right;margin-top: 5px">
          <el-pagination
            :page-sizes="[20, 50, 100]"
            :page-size="pageSize"
            :current-page="pageNum"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="pageSizes"
            @current-change="pageCurrent"
          />
        </div>
      </el-col>
    </el-row>
    <!-- 编辑对话框 -->
    <el-dialog
      title="档案接收记录编辑"
      :visible.sync="dialogFormVisible"
      width="65%"
      class="dialog-style"
      :destroy-on-close="true"
      :close-on-click-modal="false"
      :append-to-body="true"
      >
      <el-row>
        <el-col :span="24">
          <el-form :model="editorForm" ref="editorForm" :rules="rules" label-width="110px"
                   style="overflow:auto;height: 100%;padding:20px 10px 0px 10px;margin-right: 30px">
            <el-col :span="8">
              <el-form-item label="干部姓名">
                <el-input v-model="editorForm.a0101" placeholder="请输入干部姓名" style="width:100%" size="mini"
                          :disabled="true"/>
              </el-form-item>
            </el-col>
            <!--<el-col :span="8">
              <el-form-item label="干部编号">
                <el-input v-model="editorForm.number" style="width:100%" placeholder="请输入干部编号" size="mini"/>
              </el-form-item>
            </el-col>-->
            <el-col :span="8">
              <el-form-item label="来件单位" prop="e01z507a">
                <el-input v-model="editorForm.e01z507a" style="width:100%" placeholder="请输入来件单位名称" size="mini"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="转入部门" prop="deptId">
                <selectTree
                  v-model="editorForm.deptId"
                  :datas="deptOptions"
                  :defaultProps="defaultProps"
                  size="mini"
                  :filterable="true"
                  :ischecked="true"
                  :defaultExpandAll="true"
                  placeholder="请选择所属部门"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="转入原因" prop="reason">
                <el-input v-model="editorForm.reason" placeholder="请输入转入原因" style="width:100%" size="mini"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="职务" prop="duty">
                <el-select v-model="editorForm.duty" style="width:100%" filterable placeholder="请选择职务" size="mini">
                  <el-option v-for="item in dutys" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="收件日期" prop="e01z501">
                <el-date-picker v-model="editorForm.e01z501"  value-format="yyyy-MM-dd"  type="date" placeholder="请输入收件日期" style="width:100%"
                                size="mini"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="经办人" prop="e01z517">
                <el-input v-model="editorForm.e01z517" placeholder="请输入经办人" style="width:100%" size="mini"/>
              </el-form-item>
            </el-col>
            <!--<el-col :span="8">
              <el-form-item label="审核评分" prop="score">
                <el-input v-model="editorForm.score" placeholder="请输入审核评分" style="width:100%" size="mini"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="档案遗留问题" prop="problem">
                <el-input v-model="editorForm.problem" placeholder="请输入档案整理遗留问题" style="width:100%" size="mini"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="收件文号" prop="e01z504">
                <el-input v-model="editorForm.e01z504" placeholder="请输入收件文号" style="width:100%" size="mini"/>
              </el-form-item>
            </el-col>-->
            <el-col :span="8">
              <el-form-item label="正本卷数" prop="e01z511">
                <el-input-number v-model="editorForm.e01z511" :min="0" size="mini" style="width:100%"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="副本卷数" prop="e01z514">
                <el-input-number v-model="editorForm.e01z514" :min="0" size="mini" style="width:100%"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="审核人" prop="e01z521">
                <el-input v-model="editorForm.e01z521" placeholder="请输入审核人" size="mini" style="width:100%"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="审核日期" prop="e01z524">
                <el-date-picker v-model="editorForm.e01z524"  value-format="yyyy-MM-dd"  type="date" placeholder="请输入审核日期" style="width:100%"
                                size="mini"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <!-- <el-form-item label="案卷质量" prop="e01z527"><el-input class="inputW" v-model="editorForm.e01z527" placeholder="请输入案卷质量" /></el-form-item> -->
              <el-form-item label="案卷质量" prop="e01z527">
                <el-select v-model="editorForm.e01z527" clearable placeholder="请选择案卷质量" size="mini" style="width:100%">
                  <el-option v-for="item in e01z527Options" :key="item.value" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="回执日期" prop="e01z531">
                <el-date-picker v-model="editorForm.e01z531"  value-format="yyyy-MM-dd"  type="date" placeholder="请输入回执日期" style="width:100%"
                                size="mini"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="入库日期" prop="e01z534">
                <el-date-picker v-model="editorForm.e01z534"  value-format="yyyy-MM-dd"  type="date" placeholder="请输入入库日期" style="width:100%"
                                size="mini"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="入库审批人" prop="e01z537">
                <el-input v-model="editorForm.e01z537" placeholder="请输入入库审批人" style="width:100%" size="mini"/>
              </el-form-item>
            </el-col>
            <!--<el-col :span="8">
              <el-form-item label="选择库房" prop="room">
                <el-select v-model="editorForm.room" clearable class="inputW" placeholder="请选择库房" @change="selectChange"
                           size="mini">
                  <el-option v-for="item in rooms" :key="item.id" filterable :label="item.roomName" :value="item.id"/>
                </el-select>
              </el-form-item>
              </el-col>
              <el-col :span="8">
              <el-form-item label="库房位置" prop="e01z541" size="mini">
                <selectTree :testData="roomData" @handleNode="handleNodeClick" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="内部转递单" prop="transId">
                <el-input v-model="editorForm.transId" placeholder="请输入内部转递单" style="width:100%" size="mini"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="提任副本制作与否" prop="isCopy">
                <el-select v-model="editorForm.isCopy" clearable placeholder="请选择提任副本制作与否" style="width:100%"
                           size="mini">
                  <el-option v-for="item in isOptions" :key="item.value" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="副本存放处" prop="copySave">
                <el-input v-model="editorForm.copySave" placeholder="请输入副本存放处" style="width:100%" size="mini"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="材料缺失情况" prop="lack">
                <el-input v-model="editorForm.lack" placeholder="请输入材料缺失情况" style="width:100%" size="mini"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="档案目录电子版" prop="catalog">
                <el-select v-model="editorForm.catalog" clearable placeholder="请选择档案目录电子版" style="width:100%"
                           size="mini">
                  <el-option v-for="item in catalogOptions" :key="item.value" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>-->
            <el-col :span="24">
              <el-form-item label="备注" prop="e01z544">
                <el-input type="textarea"
                          style="width:100%"
                          :autosize="{minRows:1, maxRows: 1}"
                          v-model="editorForm.e01z544"
                          placeholder="请输入备注"
                          size="mini"
                />
              </el-form-item>
            </el-col>
          </el-form>
        </el-col>
      </el-row>

      <span slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogFormVisible = false">取 消</el-button>
          <el-button size="mini" type="primary" @click="onCommitEdit('editorForm')">保 存</el-button>
        </span>
    </el-dialog>
  </div>
</template>

<script>
  import {
    queryYear,
    queryE01z5s,
    updateE01z5,
    deleteE01z5s,
    queryRoomArch,
    queryDeptMap,
    queryOrgMap
  } from '@/api/usual/parameter/receives-paramenter'
  import {queryDeptTree} from '@/api/usual/receive'
  import {queryDictionaryByType} from '@/api/usual/receive'
  import {downLoad, downLoadPost} from '@/downLoad/filedownload'
  // 下拉树
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  import selectTree from '@/views/components/tree/tree.vue'

  const params = {
    pageNum: 1,
    pageSize: 20
  }

  export default {
    components: {selectTree},
    name: '档案接收年度台账',
    watch: {
      tableData: {
        handler(newName, oldName) {
          this.$nextTick(() => {
            this.$refs.tableRefs.doLayout();
          })
        },
        deep: true,
        immediate: true
      },
    },
    data() {
      return {
        /* 总页数 */
        total: 0,
        pageNum: 1,
        pageSize: 20,
        /* 刷新遮罩 */
        loading: true,
        dialogFormVisible: false,
        picClassname: 'shrinkageLeft',
        // 树结构数据
        data2: [],
        checkYear: '',
        rightStyle: {
          width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px 10px 0px 10px', height: 'calc(100vh - 86px)'
        },
        rightStyle1: {
          width: '99%', height: 'calc(100vh - 86px)', padding: ' 10px 10px 0px 10px', height: 'calc(100vh - 86px)'
        },
        leftStyle: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        leftStyle1: {
          width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        leftStyleName: {
          width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        rightStyleName: {
          width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px 10px 0px 10px', height: 'calc(100vh - 86px)'
        },
        leftTree1: true,
        treeHeight: "height:500px",
        showSearchBox: true,
        tableHeight: '73%',
        deptData: [],
        roomData: [],
        selectSTyle:{
          width:"560px"
        },
        treeStyle:{
          width:"560px",
          height:"200px"
        },
        deptNormalizer(node) {
          // 去掉children=[]的children属性
          if (node.children && !node.children.length) {
            delete node.children
          }
          return {
            id: node.id,
            label: node.deptName,
            children: node.children
          }
        },
        // 部门树数据
        deptOptions: [],
        defaultProps: {
          children: 'children',
          label: 'deptName',
          id: 'id'
        },
        selectTreeWidth: "100px",
        ischecked: true,
        rooms: [],
        e01z541Options: [],
        // 案卷质量
        e01z527Options: [
          {
          value: '0',
          label: '不合格'
        }, {
          value: '1',
          label: '合格'
        }, {
          value: '2',
          label: '优秀'
        }],
        //有无档案目录电子版
        catalogOptions: [
          {
            value: '0',
            label: '无'
          }, {
            value: '1',
            label: '有'
          }
        ],
        //是否下拉
        isOptions: [
          {
            value: '0',
            label: '否'
          }, {
            value: '1',
            label: '是'
          }
        ],
        //列表数据转换时使用
        orgMap: {},
        e01z541Map: {},
        deptMap: {},
        copiesForm: { //条件区副本
          checkYear: '',
          personName: '',
          e01z501: '',
          deptName: '',
        },
        queryForm: {
          personName: '',
          e01z501: '',
          deptName: '',
        },
        editorForm: {
          a0101: '',
          a01Id: '',
          deptId: '',
          id: '',
          orgId: '',
          e01z501: '',
          e01z504: '',
          e01z507a: '',
          e01z511: '',
          e01z514: '',
          e01z517: '',
          e01z521: '',
          e01z524: '',
          e01z527: '',
          e01z531: '',
          e01z534: '',
          e01z537: '',
          e01z541: '',
          e01z544: '',
          duty: '',
          reason: '',
          transId: '',
          // score: '',
          problem: '',
          isCopy: '',
          copySave: '',
          lack: '',
          catalog: ''
        },
        col: [
          /*{ label: 'uuid', prop: 'id', isCol: false },
          {label: '干部编号', prop: 'number', isCol: false},
          { label: '人员信息ID', prop: 'a01Id', isCol: false },
          {label: '全宗', prop: 'orgId', isCol: false},*/
          {label: '干部姓名', prop: 'a0101', isCol: true},
          {label: '来件单位', prop: 'e01z507a', isCol: true, width: "100px"},
          {label: '转入部门', prop: 'deptId', isCol: true, formatter: this.getFullName, width: "120px"},
          {label: '转入原因', prop: 'reason', isCol: true},
          {label: '职务', prop: 'duty', isCol: true, formatter: this.getFullName, width: "100px"},
          {label: '收件日期', prop: 'e01z501', isCol: true, formatter: this.getFullName, width: "100px"},
          {label: '经办人', prop: 'e01z517', isCol: true},
          // {label: '审核评分', prop: 'score', isCol: true},
          {label: '正本卷数', prop: 'e01z511', isCol: true},
          {label: '副本卷数', prop: 'e01z514', isCol: true},
          {label: '审核人', prop: 'e01z521', isCol: true},
          {label: '审核日期', prop: 'e01z524', isCol: true, formatter: this.getFullName, width: "100px"},
          {label: '案卷质量', prop: 'e01z527', isCol: true, formatter: this.getFullName},
          {label: '回执日期', prop: 'e01z531', isCol: true, formatter: this.getFullName, width: "100px"},
          {label: '入库日期', prop: 'e01z534', isCol: true, formatter: this.getFullName, width: "100px"},
          {label: '入库审批人', prop: 'e01z537', isCol: true, width: "100px"},
          {label: '档案位置', prop: 'e01z541', isCol: true, formatter: this.getFullName, width: "140px"},
          {label: '备注', prop: 'e01z544', isCol: true},
          /*{label: '收件文号', prop: 'e01z504', isCol: true},
          {label: '档案遗留问题', prop: 'problem', isCol: true, width: "100px"},
          {label: '内部转递单', prop: 'transId', isCol: true, width: "100px"},
          {label: '提任副本制作与否', prop: 'isCopy', isCol: true, width: "120px"},
          {label: '副本存放处', prop: 'copySave', isCol: true, width: "90px"},
          {label: '材料缺失情况', prop: 'lack', isCol: true, width: "100px"},
          {label: '档案目录电子版', prop: 'catalog', isCol: true, formatter: this.getFullName, width: "110px"},*/
        ],
        rules: {
          e01z507a:[
            {  max: 60, message: '输入内容不得超过60个字符', trigger: 'blur' }
          ],
          deptId: [
            { required: true, message: '请选择转入部门', trigger: 'blur' }
          ],
          reason:[
            {  max: 100, message: '输入内容不得超过100个字符', trigger: 'blur' }
          ],
          e01z501: [
            { required: true, message: '请选择收件日期', trigger: 'blur' }
          ],
          e01z517:[
            {  max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ],
          e01z511:[
            {  type:'number', max: 999, message: '输入内容不得超过3位数字', trigger: 'blur' }
          ],
          e01z514:[
            {  type:'number', max: 999, message: '输入内容不得超过3位数字', trigger: 'blur' }
          ],
          e01z521:[
            { max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ],
          e01z537:[
            {  max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ],
          e01z544:[
            {  max: 50, message: '输入内容不得超过50个字符', trigger: 'blur' }
          ],
          /*room: [
            { required: true, message: '请选择库房', trigger: 'blur' }
          ],
          e01z541: [
            { required: true, message: '请选择库房位置', trigger: 'blur' }
          ],*/
        },
        tableData: [],
        checkbox: [],
        checkboxDatas: [],
        dutys: [],
        searchRow: 'searchRow'
      }
    },
    mounted() {
      this.getDeptTree()
      queryYear().then(response => {
        this.data2 = [{
          id: '0',
          label: '接收年度',
          children: response.data
        }]
        // this.data2[0].children =
      })
      this.receiveQuery(params)
      this.$nextTick(() => {
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 50 + this.searchRowHeight)) + "px"
        this.treeHeight = "height:" + (document.body.clientHeight - 86) + "px";
      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (84 + 50 + this.searchRowHeight)) + "px"
          this.treeHeight = "height:" + (document.body.clientHeight - 86) + "px";

        })();
      }
    },
    created() {
      this.initColumnMap()
    },
    methods: {
      //自定义序号
      customIndex(index){
        return (this.pageNum-1)*(this.pageSize)+(index+1);
      },
      // 公用表格表头样式
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      // 点击行事件
      StepsListRowClick(row) {
        this.$refs.tableRefs.clearSelection()
        if (this.checkbox.length === 0) {
          this.$refs.tableRefs.toggleRowSelection(row)
          return;
        }
        this.$refs.tableRefs.toggleRowSelection(row)
      },
      // 左侧结构
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      treeNodeClick(data) {
        this.pageNum = 1
        if (data.id != '0') {
          this.checkYear = data.id
        } else {
          this.checkYear = ''
        }
        this.receiveQuery()
      },
      divClick() {
        this.leftTree1 = !this.leftTree1
        if (this.leftTree1) {
          this.rightStyleName = this.rightStyle
          this.leftStyleName = this.leftStyle
          this.picClassname = 'shrinkageLeft'
        } else {
          this.rightStyleName = this.rightStyle1
          this.leftStyleName = this.leftStyle1
          this.picClassname = 'shrinkageRight'
        }
      },
      // 新增按钮
      addRecord() {
        this.$router.push({
          name: '档案接收'
        })
      },
      // 获取部门树
      getDeptTree() {
        queryDeptTree({deptType: 0}).then(response => {
          this.deptOptions = response.data
        })
      },
      /*// 点击库房获取库房位置
      selectChange(val) {
        this.receiveForm.e01z541 = null;
        if (val != null && val != '') {
          queryRoomArch({ roomId: val }).then(response => {
            this.e01z541Options = response.data
            this.e01z541Options.map(item=>{
              // item.disableBranchNodes = true;
            })
            this.roomId = val
          })
        } else {
          this.e01z541Options = []
          this.roomId = ''
        }
      },*/
      // 初始化转换参数
      initColumnMap() {
        queryRoomArch().then(response => {
          this.e01z541Map = response.data
        })
        queryDeptMap().then(response => {
          this.deptMap = response.data
        })
        queryOrgMap().then(response => {
          this.orgMap = response.data
        })
        //当前页面所需字典表数据  职务
        queryDictionaryByType({dictType: 'ZB09', eparentCode: '0'})
          .then(response => {
            this.dutys = response.data
          })
      },
      // 根据code转换成中文
      getFullName(row, col) {
        // 时间转换
        if (col.property == 'e01z501' || col.property == 'e01z524' || col.property == 'e01z531' || col.property == 'e01z534') {
          if (row[col.property] != null && row[col.property] != '') {
            return this.moment(row[col.property]).format("YYYY-MM-DD")
          }
        }
        // 全宗
        if (col.property == 'orgId') {
          if (this.orgMap[row.orgId] == null) {
            return null
          } else {
            return this.orgMap[row.orgId]
          }
        }
        //卷数
        if (col.property == 'e01z511' || col.property == 'e01z514') {
          if (row[col.property] != null) {
            return row[col.property] + "卷";
          }
        }
        // 职务
        if (col.property == 'duty') {
          let returnValues = '';
          this.dutys.forEach(data => {
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName;
            }
          })
          return returnValues;
        }
        // 档案位置
        if (col.property == 'e01z541') {
          if (this.e01z541Map[row.e01z541] == null) {
            return null
          } else {
            return this.e01z541Map[row.e01z541]
          }
        }
        // 部门
        if (col.property == 'deptId') {
          if (this.deptMap[row.deptId] == null) {
            return ''
          } else {
            return this.deptMap[row.deptId]
          }
        }
        //案卷质量
        if (col.property == "e01z527") {
          if (row.e01z527 == null) {
            return "";
          } else {
            if (row.e01z527 == "1") {
              return "合格";
            } else if (row.e01z527 == "2") {
              return "优秀";
            } else if (row.e01z527 == "0") {
              return "不合格";
            } else {
              return row.e01z527;
            }
          }
        }
        //有无档案目录电子版
        if (col.property == "catalog") {
          if (row.catalog == null) {
            return "";
          } else {
            if (row.catalog == 1) {
              return "有";
            } else if (row.catalog == 0) {
              return "无";
            } else {
              return row.catalog;
            }
          }
        }
        //有无档案目录电子版
        if (col.property == "isCopy") {
          if (row.isCopy == null) {
            return "";
          } else {
            if (row.isCopy == 1) {
              return "是";
            } else if (row.isCopy == 0) {
              return "否";
            } else {
              return row.isCopy;
            }
          }
        }
        return row[col.property]
      },
      receiveQuery() {
        const param = {
          checkYear: this.checkYear,
          personName: this.copiesForm.personName,
          deptName: this.copiesForm.deptName,
          e01z501: this.copiesForm.e01z501,
        }
        params.pageSize = this.pageSize
        params.pageNum = this.pageNum
        params.searchValue = param
        queryE01z5s(params)
          .then(response => {
            this.tableData = response.rows
            this.$nextTick(() => {
              this.$refs.tableRefs.doLayout();
            })
            this.total = response.total
            this.loading = false
          })
      },
      /* 复选框事件 */
      handleSelectionChange(val) {
        this.checkboxDatas = val
        this.checkbox = val.map(item => item.id)
      },
      // 部门树点击
      handleNodeClick(){},
      // 编辑弹窗
      handleUpData() {
        if (this.checkboxDatas == null || this.checkboxDatas.length == 0) {
          this.$message({
            type: 'warning',
            message: '请勾选要编辑的信息'
          })
          return
        }
        if (this.checkboxDatas.length != 1) {
          this.$message({
            type: 'warning',
            message: '请选择一条信息进行编辑'
          })
          return
        }
        this.editorForm = JSON.parse(JSON.stringify(this.checkboxDatas[0]));
        this.dialogFormVisible = !this.dialogFormVisible
      },
      // 编辑保存
      onCommitEdit(val) {
        this.$refs[val].validate((valid) => {
          if (valid) {
            let returnValues = JSON.parse(JSON.stringify(this.editorForm));
            if (returnValues.e01z501 != '') {
              returnValues.e01z501 = this.moment(returnValues.e01z501).format("YYYYMMDD")
            }
            if (returnValues.e01z524 != '') {
              returnValues.e01z524 = this.moment(returnValues.e01z524).format("YYYYMMDD")
            }
            if (returnValues.e01z531 != '') {
              returnValues.e01z531 = this.moment(returnValues.e01z531).format("YYYYMMDD")
            }
            if (returnValues.e01z534 != '') {
              returnValues.e01z534 = this.moment(returnValues.e01z534).format("YYYYMMDD")
            }
            updateE01z5(returnValues)
              .then(response => {
                if (response.code == 200) {
                  this.$message({
                    message: response.msg,
                    type: 'success'
                  })
                  this.receiveQuery(params)
                  this.dialogFormVisible = !this.dialogFormVisible
                } else {
                  this.$message({
                    message: response.msg,
                    type: 'error'
                  })
                }
              })
          } else {
            this.$message({
              message: '请按要求填写表单',
              type: 'error'
            });
          }
        })
      },
      // 删除
      handleDelete(val) {
        if (this.checkboxDatas == null || this.checkboxDatas.length == 0) {
          this.$message({
            type: 'warning',
            message: '请勾选要删除的信息'
          })
          return
        }
        this.$confirm('此操作将永久删除所选信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            const deleteId = this.checkbox.join('-')
            const id = deleteId || val.id
            if (id != '') {
              deleteE01z5s({e01z5Ids: id})
                .then(response => {
                  if (response.code == 200) {
                    this.$message({
                      message: '删除成功',
                      type: 'success'
                    });
                    this.receiveQuery(params)
                  } else {
                    this.$message({
                      message: '删除失败',
                      type: 'error'
                    });
                  }
                })
            } else {
              this.$message({
                message: '请选择您要删除的内容',
                type: 'warning'
              })
            }
          })
          .catch(() => {
          })
      },
      // 查看附件
      getReceipt(val) {
      },
      // 上传附件
      // 删除附件
      // 下载附件
      /* 导出 */
      handleExport() {
        if (this.checkboxDatas.length == 0) {
          this.$confirm('是否导出搜索后的台账数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            const param = {
              checkYear: this.checkYear,
              personName: this.copiesForm.personName,
              deptName: this.copiesForm.deptName,
              e01z501: this.copiesForm.e01z501,
            }
            params.pageSize = 99999999
            params.pageNum = 1
            params.searchValue = param
            queryE01z5s(params)
              .then(response => {
                if(response.rows.length==0){
                  this.$message.error("无数据,导出失败");
                  return;
                }
                response.rows.map((item, index) => {
                  item.duty = this.geteFullNameWapper(item, "duty");
                  item.deptId = this.geteFullNameWapper(item, "deptId");
                  item.e01z501 = this.geteFullNameWapper(item, "e01z501");
                  item.e01z524 = this.geteFullNameWapper(item, "e01z524");
                  item.e01z527 = this.geteFullNameWapper(item, "e01z527");
                  item.e01z531 = this.geteFullNameWapper(item, "e01z531");
                  item.e01z534 = this.geteFullNameWapper(item, "e01z534");
                  item.e01z541 = this.geteFullNameWapper(item, "e01z541");
                  item.catalog = this.geteFullNameWapper(item, "catalog");
                })
                let data = {
                  "colTitle": {
                    "a0101": "干部姓名",
                    // "number": "干部编号",
                    "e01z507a": "来件单位",
                    "deptId": "转入部门",
                    "reason": "转入原因",
                    "duty": "职务",
                    "e01z501": "收件日期",
                    "e01z517": "经办人",
                    // "score": "审核评分",
                    // "problem": "档案遗留问题",
                    "e01z544": "备注",
                    // "orgId": "全宗",
                    // "e01z504": "收件文号",
                    "e01z511": "正本卷数",
                    "e01z514": "副本卷数",
                    "e01z521": "审核人",
                    "e01z524": "审核日期",
                    "e01z527": "案卷质量",
                    "e01z531": "回执日期",
                    "e01z534": "入库日期",
                    "e01z537": "入库审批人",
                    "e01z541": "档案位置",
                    // "transId": "内部转递单",
                    // "isCopy": "提任副本制作与否",
                    // "copySave": "副本存放处",
                    // "lack": "材料缺失情况",
                    // "catalog": "档案目录电子版"
                  },
                  "colWidth": {
                    "e01z507a": "25",
                    "deptId": "20",
                    "e01z501": "25",
                    "e01z524": "25",
                    "e01z531": "25",
                    "e01z534": "25",
                    "e01z541": "25",
                    "isCopy": "16",
                  },
                  "dataList": response.rows,
                  "tableTitle": "档案接收台账表",
                  "sheetTitle": "档案接收台账表",
                  "downLoadName": "档案接收台账表"
                }
                downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data))
              })
          })

        } else {
          this.$confirm('是否导出选中的台账数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let checkboxDatas = this.checkboxDatas;
            checkboxDatas.map((item,index)=>{
              item.duty = this.geteFullNameWapper(item, "duty");
              item.deptId = this.geteFullNameWapper(item, "deptId");
              item.e01z501 = this.geteFullNameWapper(item, "e01z501");
              item.e01z524 = this.geteFullNameWapper(item, "e01z524");
              item.e01z527 = this.geteFullNameWapper(item, "e01z527");
              item.e01z531 = this.geteFullNameWapper(item, "e01z531");
              item.e01z534 = this.geteFullNameWapper(item, "e01z534");
              item.e01z541 = this.geteFullNameWapper(item, "e01z541");
              item.catalog = this.geteFullNameWapper(item, "catalog");
            })
            let data = {
              "colTitle": {
                "a0101": "干部姓名",
                // "number": "干部编号",
                "e01z507a": "来件单位",
                "deptId": "转入部门",
                "reason": "转入原因",
                "duty": "职务",
                "e01z501": "收件日期",
                "e01z517": "经办人",
                // "score": "审核评分",
                "problem": "档案遗留问题",
                "e01z544": "备注",
                // "orgId": "全宗",
                // "e01z504": "收件文号",
                "e01z511": "正本卷数",
                "e01z514": "副本卷数",
                "e01z521": "审核人",
                "e01z524": "审核日期",
                "e01z527": "案卷质量",
                "e01z531": "回执日期",
                "e01z534": "入库日期",
                "e01z537": "入库审批人",
                "e01z541": "档案位置",
                // "transId": "内部转递单",
                // "isCopy": "提任副本制作与否",
                // "copySave": "副本存放处",
                // "lack": "材料缺失情况",
                // "catalog": "档案目录电子版"
              },
              "colWidth": {
                "e01z507a": "25",
                "deptId": "20",
                "e01z501": "25",
                "e01z524": "25",
                "e01z531": "25",
                "e01z534": "25",
                "e01z541": "25",
                "isCopy": "16",
              },
              "dataList": checkboxDatas,
              "tableTitle": "档案接收台账表",
              "sheetTitle": "档案接收台账表",
              "downLoadName": "档案接收台账表"
            }
            downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data))
          })

        }
      },
      geteFullNameWapper(item,field){
        let col = {
          property:field
        }
        return this.getFullName(item,col);
      },
      /* 打印 */
      handlePrint() {

      },
      pageCurrent(val) {
        this.pageNum = val;
        params.pageNum=val;
        params.pageSize=this.pageSize;
        this.receiveQuery(params)
      },
      pageSizes(val) {
        this.pageSize = val;
        params.pageNum=this.pageNum;
        params.pageSize=val;
        this.receiveQuery(params)
      },
      search() {
        /*params.pageNum = 1
        params.pageSize = this.pageSize
        let searchValue= {
          personName:this.queryForm.personName,
          deptName:this.queryForm.deptName,
          checkYear:this.checkYear
        };
        if (this.queryForm.e01z501 != null && this.queryForm.e01z501 != "") {
          let reg = new RegExp('-','g');
          searchValue.e01z501 = this.moment(this.queryForm.e01z501).substring(0,10).replace(reg, '');
        }
        params.searchValue=searchValue*/
        this.pageNum = 1
        this.copiesForm = JSON.parse(JSON.stringify(this.queryForm))
        this.receiveQuery()
      },
      //搜索区回车事件
      onEnterSearch() {
        this.search();
      },
      // 重置
      reset() {
        this.queryForm.searchValue = ""
        this.queryForm.deptName = ""
        this.queryForm.e01z501 = ""
      }
    }
  }
</script>

<style lang="scss" scoped>
  .app-receives-paramenter {
    height: 100%;
    .tree {
      width: 100%;
      height: 500px;
      padding: 20px;
      border-right: 1px solid #e2e3e6;
    }
    .search-form {
      width: 99%;
      background: rgba(204, 204, 204, 0.18);
      margin-left: 5px;
      padding-left: 10px;
      padding-bottom: 10px;
      display: flex;
      .form-width {
        width: 22%;
        display: flex;
      }
      /deep/ .el-form-item {
        margin-bottom: 0 !important;
      }
    }
    .btn {
      width: 100%;
      position: relative;
      padding-bottom: 10px;
      padding-top: 10px;
      padding-left: 5px;
      display: flex;
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

    /deep/ .el-select > .el-input {
      /*width: 163px !important;*/
    }
    /deep/ .el-table tbody tr:hover > td {
      background-color: #ecf5ff !important
    }
  }

  .el-dialog__body {
    height: 500px;
    display: block;
  }

  .app-archives {

    height: calc(100vh - 86px);
  }

  .el-input__inner {
    width: 250px;
  }

  .el-form-item__content {
    width: 250px;
  }

  .el-dialog-footer {
    padding-right: 15px;
    padding: 11px;
    padding-bottom: 35px;
  }

  // 设置表格高度
  /deep/ .el-table tbody tr:hover > td {
    background-color: #ecf5ff !important
  }

  /deep/ .el-table__body td {
    padding: 0;
    height: 40px;
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

  // 公用树结构样式
  .tree-style {
    /deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
      background: rgba(204, 204, 204, 0.18);
    }
  }

  /deep/ .vue-treeselect__value-container {
    height: 28px;
  }

  /deep/ .vue-treeselect__single-value {
    height: 28px;
  }
  .el-scrollbar {
    height: 100%;
  }
  /deep/ .el-scrollbar .el-scrollbar__wrap {
    overflow-x: hidden;
  }
  /deep/ .el-tree > .el-tree-node {
    min-width: 100%;
    display: inline-block;
  }

  /*vue-treeselect__control*/
  /deep/ .vue-treeselect__control {
    height: 28px;
  }

  /deep/ .el-form-item--medium .el-form-item__content {
    line-height: 28px;
  }

  /deep/ .vue-treeselect--open-below {
    height: 28px;
  }
  .shrinkageLeft {
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
    cursor: pointer;
  }

  .shrinkageRight {
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
    cursor: pointer;
  }

  .shrinkageDiv {
    cursor: pointer;
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

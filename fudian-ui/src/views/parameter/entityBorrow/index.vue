<template>
  <div class="app-ArchiveBorrow-borrow-paramenter">
    <el-row style="height:100%" v-if="tabsShow">
      <el-col :span="4" :style="leftStyleName" style="height:100%">
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
              default-expand-all
              @node-click="treeNodeClick"
            />
          </el-scrollbar>
        </div>
         <div class="shrinkageDiv" @click="divClick()">
             <div :class="picClassname"></div>
         </div>
        </el-col>
        <el-col :span="18" style="height:100%" :style="rightStyleName">
        <!--检索   开始    -->
        <el-row ref="searchRow" :gutter="10">
          <el-form
            v-if="showArchiveBorrow"
            ref="queryForm"
            :model="queryParams"
            :inline="true"
            class="search-form"
            @submit.native.prevent
            v-hasPermi="['parameter:entityCheck:search']"
            >
            <el-form-item @keyup.enter.native="onEnterSearch" label="申请人姓名" prop="userName" style="margin-top: 10px;margin-bottom: 10px">
              <el-input v-model="queryParams.userName"  placeholder="请输入借阅人姓名" clearable size="mini" style="width: 150px" />
            </el-form-item>
            <el-form-item label="申请借阅时间起" prop="startTime" style="margin-top: 10px;margin-bottom: 10px">
              <el-date-picker v-model="queryParams.beginTime" value-format="yyyy-MM-dd" type="date" placeholder="请选择借阅起始时间" size="mini" style="width:150px"/>
            </el-form-item>
            <el-form-item label="申请借阅时间至" prop="endTime" style="margin-top: 10px;margin-bottom: 10px">
              <el-date-picker v-model="queryParams.endTime" value-format="yyyy-MM-dd" type="date" placeholder="请选择借阅截止时间" size="mini" style="width:150px"/>
            </el-form-item>
            <el-form-item style="margin-top: 10px;margin-bottom: 10px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="queryArchiveBorrow">搜索</el-button>
              <!-- <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button> -->
            </el-form-item>
          </el-form>
          <div class="btn">
            <el-button type="primary" icon="el-icon-plus" size="mini" v-hasPermi="['parameter:entityBorrow:add']" @click="archiveBorrow(1)">新增</el-button>
            <el-button type="primary" icon="el-icon-edit" size="mini" v-hasPermi="['parameter:entityBorrow:edit']" @click="archiveBorrow(2)">编辑</el-button>
            <el-button type="primary" icon="el-icon-delete" size="mini" v-hasPermi="['parameter:entityBorrow:delete']" @click="deleteArchiveBorrow">删除</el-button>
            <el-button type="primary" icon="el-icon-download" size="mini" v-hasPermi="['parameter:entityBorrow:import']" @click="handleExportArchiveBorrow">导出</el-button>
            <!--
            <div class="btn-search">
              <el-button type="primary" icon="el-icon-search" size="mini" circle @click="function(){showArchiveBorrow = !showArchiveBorrow;tableHeight = showArchiveBorrow?'73%':'80%'}" />
              <el-dropdown style="float: right; margin-left: 5px;">
                <el-button icon="el-icon-menu" size="mini" circle />
                <el-dropdown-menu style="padding: 10px">
                  <el-checkbox-group v-for="(item, index) in EntityColumn" :key="index" v-model="item.isCol" size="mini">
                    <el-checkbox>{{ item.label }}</el-checkbox>
                  </el-checkbox-group>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            -->
          </div>
        </el-row>
        <el-table
          :default-sort="{ prop: 'date', order: 'descending' }"
          size="mini"
          ref="EntityData"
          :data="EntityData"
          border
          class="table"
          :height="tableHeight"
          empty-text=" "
          :stripe="true"
          row-key="id"
          tooltip-effect="light"
          :header-cell-style="headClass"
          align="center"
          @row-click="StepsListRowClick"
          @selection-change="handleSelectionChange"
          >
          <el-table-column type="selection" align="center" width="45" />
          <el-table-column align="center" show-overflow-tooltip label="序号" type="index" min-width="55" :index="customIndex"/>
          <el-table-column
            align="center"
            show-overflow-tooltip
            v-for="(item, index) in EntityColumn"
            v-if="EntityColumn[index].isCol"
            :key="index"
            :width="item.width"
            :formatter="item.formatter"
            :prop="EntityColumn[index].prop"
            :label="item.label"/>
          <!--<el-table-column align="center" fixed="right" label="操作" width="100">
            <template slot-scope="scope">
              <el-button type="text" size="mini" @click="viewDetals(scope.row)">查看详情</el-button>
            </template>
          </el-table-column>-->
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
      <!-- 详情对话框 -->
      <el-dialog
        title="实体档案借阅申请详情"
        :visible.sync="dialogDetailsVisible"
        width="85%"
        class="dialog-style"
        :destroy-on-close="true"
        :close-on-click-modal="false"
        :append-to-body="true"
        >
        <div class="btn">
          <el-button type="primary" icon="el-icon-download" size="mini"  @click="handleExportArchiveBorrow">导出</el-button>
        </div>
        <el-table
          size="mini"
          :data="detailsData"
          ref="detailsData"
          :default-sort="{ prop: 'date', order: 'descending' }"
          class="table"
          tooltip-effect="light"
          align="center"
          stripe
          border
          :header-cell-style="headClass"
          height="400px"
          >
          <el-table-column align="center" show-overflow-tooltip  label="序号" type="index" min-width="60" />
          <el-table-column prop="a0101" label="被申请人姓名" align="center"  />
          <el-table-column prop="deptName" label="被申请人所在部门" align="center"  />
          <el-table-column prop="e01z111a" :formatter="()=>{return '整本'}" :show-overflow-tooltip="true" label="申请内容" align="center"  />
          <el-table-column prop="startTime" label="申请借阅起始日期" align="center"   />
          <el-table-column prop="endTime" label="申请借阅截止日期至" align="center"   />
          <el-table-column align="center"  label="操作" width="100">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="deleteRow(scope.$index, detailsData,scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer" >
          <el-button size="mini" @click="dialogDetailsVisible = false">关 闭</el-button>
        </span>
      </el-dialog>
    </el-row>
    <!-- 新增/编辑对话框 -->
    <el-col v-if="formShow" :span="24" :xs="24" style="margin: 0;padding: 0;border: 1px solid #cccccc;height: calc(100vh - 86px);overflow: auto;">
      <el-row style="padding-left: 10%;padding-right: 10%">
        <el-col :span="24">
          <div style="width: 100%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0">第一步：填写申请人基本信息</div>
          <el-form :rules="rules" ref="addForm" :model="addForm" label-width="135px" style="margin-top:25px;padding-left: 2%;padding-right: 2%">
            <el-col :span="8">
              <el-form-item label="审批人" prop="e01z801">
                <el-input v-model="addForm.e01z801" size="mini"  placeholder="请输入审批人" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="经办人" prop="e01z817">
                <el-input v-model="addForm.e01z817" size="mini"  placeholder="请输入经办人" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="审批意见" prop="flag">
                <el-input v-model="addForm.flag" size="mini"  placeholder="请输入审批意见" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请人姓名" prop="userName">
                <el-input v-model="addForm.userName" size="mini"  placeholder="请输入申请人姓名" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请人证件号" prop="userCard">
                <el-input v-model="addForm.userCard" size="mini"  placeholder="请输入申请人证件号" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请人所在单位" prop="e01z824a">
                <el-input v-model="addForm.e01z824a" size="mini"  placeholder="请输入申请人所在单位" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请人电话" prop="userPhone">
                <el-input v-model="addForm.userPhone" size="mini"  placeholder="请输入申请人电话" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请理由" prop="applicationReasons">
                <el-input v-model="addForm.applicationReasons" size="mini"  placeholder="请输入申请理由" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请借阅起始日期" prop="startTime">
                <el-date-picker size="mini" v-model="addForm.startTime"   value-format="yyyy-MM-dd"    type="date" placeholder="选择申请开始时间" style="width: 100%;" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请借阅截止日期" style="text-align:right;" prop="endTime">
                <el-date-picker size="mini" v-model="addForm.endTime"   value-format="yyyy-MM-dd"    type="date" placeholder="选择申请截止时间至" style="width: 100%;" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="申请人职务" prop="duty">
                <el-input v-model="addForm.duty" size="mini" placeholder="清输入申请人职务" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="打印内容" prop="printContents">
                <el-input v-model="addForm.printContents" size="mini" type="textarea" placeholder="若打印请输入打印内容" style="width: 100%;"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="备注" prop="e01z831">
                <el-input v-model="addForm.e01z831" size="mini" placeholder="请输入备注" style="width: 100%;" type="textarea" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item  label="添加附件" prop='sysFile'>
                <input  type="file" single accept="image/png, image/jpeg"  name="upload_Wfile"  style="display: none;"
                        @change="fileChange" ref="imageFile"/>
                <el-button size="mini" onclick="upload_Wfile.click();" style="width:75px">选择文件</el-button>
                <el-input size="mini" v-model="addForm.fileName" onclick="upload_Wfile.click();" :readonly="true" class="fileTextInput"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-radio-group style="padding:10px 0 0 100px" v-model="addForm.archiveStatus">
                <el-radio label="已借出">已借出</el-radio>
                <el-radio label="已归还">已归还</el-radio>
              </el-radio-group>
            </el-col>
          </el-form>
        </el-col>
        <el-col :span="24" style="padding: 10px">
          <div style="width:100%;margin: 0 auto">
            <el-button  style="float: right;margin-right: 10px;width:80px" size="mini" @click="resetAddForm">重置表单</el-button>
          </div>
        </el-col>
        <el-col :span="24" >
          <div style="width: 100%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0">第二步：请选择需要借阅的干部档案</div>
          <el-row :gutter="10" style="padding-top:20px;padding-left: 2%;padding-right: 2%">
          <el-form v-model="formInline" :inline="true" :model="formInline" label-width="80px">
            <el-form-item label="干部状态">
                <el-select v-model="a01state" style="width: 150px"  filterable class="inputW" placeholder="请选择干部状态" size="mini">
                  <el-option v-for="item in states"  :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                </el-select>
              </el-form-item>
            <el-form-item prop="deptName">
              <el-input v-model="formInline.deptName" clearable @keyup.enter.native="search(1)" size="mini" style="width: 150px" placeholder="请输入部门名称" />
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="search(1)">部门搜索</el-button>
            </el-form-item>
              <el-form-item prop="a0101">
              <el-input v-model="formInline.a0101" clearable @keyup.enter.native="search(2)" size="mini" style="width: 150px" placeholder="请输入干部名称" />
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="search(2)">干部搜索</el-button>
            </el-form-item>
            </el-form>
          </el-row>
          <el-col :span="24" style="padding-left: 2%;padding-right: 2%">
            <el-table
              height="222"
              empty-text=" "
              size="mini"
              ref="searchTableData"
              class="table-style"
              :data="searchTableData"
              border
              @cell-dblclick="deptClick"
              @selection-change="gridSelectionChange"
              :header-cell-style="headClass"
              @row-click="handleGridChange"
              :stripe="true"
              >
              <el-table-column type="selection" width="45" align="center" key="1" v-if="dpbutton"/>
              <el-table-column style="width: 1%" type="index" align="center" label="序号" />
              <el-table-column prop="a0101" v-if="isa01 == 2" key="2" label="干部名称"  align="center" />
<!--              <el-table-column prop="number" v-if="isa01 == 2" key="3" label="干部编号"  align="center" />-->
              <el-table-column prop="deptName" v-if="isa01 == 2" key="4" label="部门"  align="center" />
              <el-table-column prop="deptName" v-if="isa01 == 1" key="5" label="部门名称"  align="center" style="width: 50%"/>
              <el-table-column prop="parentId" v-if="isa01 == 1" key="6" label="上级部门"  align="center" style="width: 50%"/>
              <el-table-column prop="a0104" v-if="isa01 == 2" key="7" label="性别"  align="center" :formatter="getFullName" />
              <el-table-column prop="a0107" v-if="isa01 == 2" key="8" label="出生日期"  align="center" :formatter="getFullName" />
              <el-table-column prop="state" v-if="isa01 == 2" key="9" label="干部状态"  align="center" :formatter="getFullName" />
            </el-table>
          </el-col>
        </el-col>
        <el-col :span="24" style="padding: 10px">
          <div style="width:100%;margin: 0 auto">
            <el-button type="primary" style="float: right;margin-right: 10px;width:80px" size="mini" @click="typeClick">添加</el-button>
          </div>
        </el-col>
        <div style="width: 100%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0">第三步：确认申请信息</div>
        <el-table
          :default-sort="{prop: 'date', order: 'descending'}"
          stripe
          size="mini"
          empty-text=" "
          border
          class="table-style"
          :header-cell-style="headClass"
          :data="queryData"
          ref="queryA01Data"
          style="width: 96%;margin-top:25px;margin-left: 2%;margin-right: 2%"
          height="222px"
          @selection-change="handleSelectionChange"
          >
          <el-table-column style="width: 1%" type="index" align="center" label="序号" />
          <el-table-column align="center" v-if="true" label="姓名" prop="a0101" />
<!--          <el-table-column align="center" v-if="true" label="编号" prop="number" />-->
          <el-table-column align="center" v-if="true" label="单位名称" prop="deptName" />
          <el-table-column align="center" v-if="true" label="职务" prop="duty" :formatter="getFullName"/>
          <el-table-column align="center" v-if="true" label="借档内容" prop="e01z111a" />
          <el-table-column align="center" v-if="true" label="档案类型" prop="archivesType" />
          <el-table-column align="center" fixed="right" label="操作" width="100">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="deleteRow2(scope.$index, queryData)">移除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button style="float: right;margin: 10px;" size="mini" type="primary" @click="onGrid('addForm')">保存记录</el-button>
        <el-button style="float: right;margin: 10px;" size="mini"  @click="onBack">返回</el-button>
      </el-row>
    </el-col>
  </div>
</template>

<script>
import {queryDictionaryByType,queryDictionaryByTypes,queryLoginUser} from '@/api/usual/receive'
import { queryA01List } from '@/api/usual/transfer'
import {queryE0z9s, queryE01z9LedgerList,queryE01z9A01s,updateByEntityAndList,updateByEntityBorrowAndList,queryYear,queryE01z9RelaLedgerList,deleteE01z9Ledger,deleteE01z9RelaLedger,getFileName9} from "@/api/parameter/borrow"
import {downLoad, downLoadPost} from '@/downLoad/filedownload'
import {checkBorrowEntityFile,} from '@/api/usual/usage/borrow'
import {prompt} from '@/components/windoprompt/prompt'

const params = {
  pageNum: 1,
  pageSize: 20
}

export default {
  name: '实体档案借阅年度台账',
  watch: {
    EntityData :{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          _this.$refs.EntityData.doLayout()
        })
      },
      deep:true,
    },
    searchTableData :{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          _this.$refs.searchTableData.doLayout()
        })
      },
      deep:true,
    },
    detailsData :{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          _this.$refs.detailsData.doLayout()
        })
      },
      deep:true,
    },
  },
  data() {
    return {
      rules:{
        e01z801:[
          { required: true, message: '请输入审批人' },
          { min: 2, max: 40, message: '输入内容不得超过40个字符' }
        ],
        e01z817:[
          { min: 2, max: 40, message: '输入内容不得超过40个字符' }
        ],
        flag:[
          { required: true, message: '请输入审批意见' },
          { min: 1, max: 255, message: '输入内容不得超过255个字符' }
        ],
        userName: [
          { required: true, message: '请输入申请人姓名' },
          { min: 2, max: 40, message: '输入内容不得超过40个字符' }
        ],
        userCard:[
          { required: true, message: '请输入申请人证件号' },
          { min: 1, max: 40, message: '输入内容不得超过40个字符' }
        ],
        e01z824a:[
          { required: true, message: '请输入申请人所在单位' },
          { min: 1, max: 40, message: '输入内容不得超过40个字符' }
        ],
        userPhone:[
          { required: true, message: '请输入申请人电话' },
          { min: 1, max: 20, message: '输入内容不得超过20个字符' }
        ],
        applicationReasons:[
          { required: true, message: '请输入申请理由' },
          { min: 1, max: 255, message: '输入内容不得超过255个字符' }
        ],
        endTime:[
          { required: true, message: '请选择申请查阅截止日期' },
        ],
        duty:[
          { min: 1, max: 40, message: '输入内容不得超过40个字符' }
        ],
        printContents:[
          { min: 1, max: 255, message: '输入内容不得超过255个字符' }
        ],
        e01z831:[
          { min: 1, max: 255, message: '输入内容不得超过255个字符' }
        ],
      },
      /* 总页数 */
      applyStates:"1",
      total: 0,
      pageNum: 1,
      pageSize: 20,
      /* 刷新遮罩 */
      loading: true,
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
        width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc',position: 'relative'
      },
      leftStyle1: {
        width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc',position: 'relative'
      },
      leftStyleName: {
        width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc',position: 'relative'
      },
      rightStyleName: {
        width: '84%', height: 'calc(100vh - 86px)', padding: '10px 10px 0px 10px', height: 'calc(100vh - 86px)'
      },
      leftTree1: true,
      treeHeight: "height:500px",
      tableHeight: '73%',
      // 条件区
      queryParams: {},
      queryParams2:{},
      showArchiveBorrow: true,
      // 详情
      dialogDetailsVisible: false,
      detailsData: [],
      // 申请人列名
      EntityColumn: [
        { label: '申请人姓名', prop: 'userName', isCol: true ,width:"110px"},
        { label: '申请人证件号', prop: 'userCard', isCol: true ,width:"150px"},
        { label: '申请借阅理由', prop: 'applicationReasons', isCol: true ,width:"150px"},
        { label: '申请人所在单位', prop: 'e01z824a', isCol: true ,width:"150px"},
        { label: '档案归还状态', prop: 'archiveStatus', isCol: true ,width:"150px"},
        { label: '申请人电话号码', prop: 'userPhone', isCol: true ,width:"150px"},
        { label: '申请人职务', prop: 'duty', isCol: true ,width:"110px"},
        { label: '申请借阅档案卷数', prop: 'applyNumber', isCol: true ,width:"150px"},
        { label: '申请时间', prop: 'creatersTime', isCol: true ,width:"110px", formatter:this.getFullName},
        { label: '申请借阅起始日期', prop: 'startTime', isCol: true ,width:"150px", formatter:this.getFullName},
        { label: '申请借阅截止日期', prop: 'endTime', isCol: true ,width:"150px", formatter:this.getFullName},
        { label: '申请类型', prop: 'applyStatus', isCol: true ,width:"110px"},
        { label: '被申请人姓名', prop: 'a0101', isCol: true ,width:"150px"},
        { label: '被申请人所在部门', prop: 'deptName', isCol: true ,width:"150px"},
        { label: '申请内容', prop: 'e01z111a', isCol: true ,width:"110px", formatter:this.getFormatter},
        { label: '经办人', prop: 'e01z817', isCol: true ,width:"110px"},
        { label: '审批人', prop: 'e01z801', isCol: true ,width:"110px"},
        { label: '审批状态',width:100, isCol: true, prop: 'status',formatter:function(row, column, cellValue, index){
            if(cellValue == '0'){
              return "待审批";
            }
            if(cellValue == '1'){
              return "已通过";
            }
            if(cellValue == '-1'){
              return "未通过";
            }
          } },
        { label: '审批时间', prop: 'approvTime', isCol: true ,width:"110px", formatter:this.getFullName},
        { label: '审批意见', prop: 'flag', isCol: true ,width:"110px"},
        { label: '申请打印内容', prop: 'printContents', isCol: true,width:"150px"},
        { label: '备注', prop: 'e01z831', isCol: true,width:"110px"},
      ],
      // 表格数据
      EntityData: [],
      // 表格总数据
      totalData:[],
      // 新增编辑
      addForm:{
        id: "",
        e01z801:"",
        e01z817:"",
        flag:"",
        userName:"",
        userId:"",
        userCard:"",
        e01z824a:"",
        userPhone:"",
        applicationReasons:"",
        startTime:"",
        endTime:"",
        duty:"",
        printContents:"",
        e01z831:"",
        fileName:"",
        archiveStatus: ""
      },
      addForm2:{
        id: "",
        e01z801:"",
        e01z817:"",
        flag:"",
        userName:"",
        userId:"",
        userCard:"",
        e01z824a:"",
        userPhone:"",
        applicationReasons:"",
        startTime:"",
        endTime:"",
        duty:"",
        printContents:"",
        e01z831:"",
        fileName:"",
        archiveStatus: ""
      },
      tabsShow: true,
      formShow: false,
      formInline:{
        deptName: '',
        a0101: '',
      },
      searchTableData:[],
      dpbutton:false,
      isa01: 2,
      queryData:[],
      beforeQueryData:[],
      a01state:"0",
      states:[],
      // 复选框
      checkbox: [],
      checkboxDatas: [],
      dutys:[],
      searchRow:'searchRow'
    }
  },
  mounted() {
    queryYear().then(response=>{
      this.data2 = [{
        id:'0',
        label:'申请年度',
        children:response.data
      }]
      // this.data2[0].children =
      // console.log(this.data2);
    })
    this.ArchiveBorrowsaFunction()
    this.$nextTick(()=>{
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
    };
  },
  created() {
    this.getLoginUser()
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
    StepsListRowClick(row){
      this.$refs.EntityData.clearSelection()
      if (this.checkbox.length === 0) {
      this.$refs.EntityData.toggleRowSelection(row)
        return;
      }
      this.$refs.EntityData.toggleRowSelection(row)
    },
    // 左侧结构
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    treeNodeClick(data) {
      this.pageNum = 1;
      if(data.id == '0'){
        this.queryParams2.approvTime = "";
      }else{
        this.queryParams2.approvTime = data.id + "-01-01 00:00:01";
      }
      this.ArchiveBorrowsaFunction();
    },
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
    geteFullNameWapper(item, field) {
      let col = {
        property: field
      }
      return this.getFullName(item, col);
    },
    // 加载
    ArchiveBorrowsaFunction() {
      this.EntityData = this.totalData
      let params = {
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        userName:this.queryParams2.userName,
        endTime:this.queryParams2.endTime,
        beginTime:this.queryParams2.beginTime,
        creatersTime:this.queryParams2.approvTime
      }
      queryE0z9s(params).then(response=>{
        console.log(response);
        this.EntityData = response.rows;
        this.total = response.total;
      })
      // this.$nextTick(()=>{
      //     this.$refs.EntityData.doLayout();
      //   })
    },
    // 搜索
    queryArchiveBorrow(){
      this.pageNum = 1;
      let temp = this.queryParams2.approvTime;
      this.queryParams2 = JSON.parse(JSON.stringify(this.queryParams));
      this.queryParams2.approvTime = temp;
      this.ArchiveBorrowsaFunction();
    },
    //搜索区回车事件
    onEnterSearch() {
      this.queryArchiveBorrow();
    },
    // 重置
    reset() {
      this.queryParams.userName=""
      this.queryParams.beginTime = ""
      this.queryParams.endTime= ""
      this.ArchiveBorrowsaFunction()
    },
    //获取当前登录用户
    getLoginUser(){
      queryLoginUser().then(response=>{
        if(response.code==200){
          this.loginUser = response.data;
        }
      })
    },
    // 初始化转换参数
    initColumnMap() {
      //当前页面所需字典表数据  职务
      queryDictionaryByType({ dictType: 'ZB09', eparentCode: '0' })
        .then(response => {
          this.dutys = response.data
        })
      // 当前页面所需字典表数据 性别
      queryDictionaryByType({ dictType: 'GB22611' })
        .then(response => {
          this.a0104s = response.data
        })

      // 获取民族
      queryDictionaryByType({ dictType: 'GB3304' })
        .then(response => {
          this.a0117s = response.data
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
      //当前页面所需字典表数据  目录树
      queryDictionaryByTypes({types:"ZB97A"})
        .then(response => {
          this.sysDictionaryMap = response.data

        })
    },
    // 格式转换
    getFormatter(){
      return '整本'
    },
    getFullName(row,col){
      // 时间
      if( col.property=="a0107"||col.property=="approvTime"||col.property=="creatersTime"||col.property=="endTime"||col.property=="startTime"){
        if(row[col.property]!=null){
          return this.moment(row[col.property]).format("YYYY-MM-DD")
        }
      }
      //精确到时分秒的时间转换
      if(false){
        if(row[col.property]!=null){
          var d = new Date(row[col.property]);
          let month = '' + (d.getMonth() + 1);
          let day = '' + d.getDate();
          let year = d.getFullYear();
          let hours = d.getHours()
          let minute = d.getMinutes();
          let seconds = d.getSeconds();
          if (month.length < 2) month = '0' + month;
          if (day.length < 2) day = '0' + day;
          return [year, month, day].join('-')+" "+[hours,minute,seconds].join(':');
        }
      }
      // 职务
      if (col.property == "duty") {
        let returnValues = '';
        this.dutys.forEach(data => {
          if (data.dictCode == row[col.property]) {
            returnValues = data.fullName;
            console.log(returnValues)
          }
        })
        return returnValues;
      }
      //性别
      if(col.property=="a0104"){
        let returnValues='';
        this.a0104s.forEach(data => {
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
      //民族
      if(col.property=="a0117"){
        let returnValues='';
        this.a0117s.forEach(data => {
          if(data.dictCode==row[col.property]){
            returnValues=data.fullName;
          }
        })
        return returnValues;
      }
      //政治面貌
      if(col.property=="a0141"){
        let returnValues='';
        this.a0141s.forEach(data => {
          if(data.dictCode==row[col.property]){
            returnValues=data.fullName;
          }
        })
        return returnValues;
      }
      //材料分类
      if(col.property=="e01z101"){
        if(this.sysDictionaryMap.ZB97A==null||this.sysDictionaryMap.ZB97A[row.e01z101]==null){
          return null;
        }else{
          return this.sysDictionaryMap.ZB97A[row.e01z101].fullName;
        }
      }
      return row[col.property]
    },
    /**
    // 搜索
    queryArchiveBorrow() {
      const _this=this;
      searchArchiveBorrowsCheckRecords(_this.queryParams)
        .then(response => {
          if (response.code === 200) {
            _this.EntityData=response.rows
            _this.total = response.total
            _this.loading = false
          }
        })
    },
    // 档案申请记录
    ArchiveBorrowsaFunction() {
      const _this = this
      const param = {
        ArchiveBorrowsType: this.ArchiveBorrowsType,
        pageSize: this.pageSize,
        pageNum: this.pageNum
      }
      getArchiveBorrowsCheckRecords(param)
        .then(function(response) {
          if (response.code === 200) {
            _this.EntityData = response.rows
            _this.total = response.total
            _this.loading = false
          }
        })
    },*/
    // 查看详情弹窗
    viewDetals(val){
      this.dialogDetailsVisible = true
      queryE01z9RelaLedgerList({e01z9Id:val.id}).then(response=>{
        this.detailsData = response.data;
        this.detailsData.map(item=>{
          item.endDate = val.endDate;
          item.approvTime = val.approvTime;
        })
      })
      // this.$nextTick(()=>{
      //     this.$refs.detailsData.doLayout();
      //   })
    },
    // 新增
    archiveBorrow(val){
       if(val == 1){
         this.addForm.id = ""
         this.resetAddForm()
         this.queryData = [];
         this.searchTableData = [];
         this.formInline.deptName = "";
         this.formInline.a0101 = "";
      }else{
         if(this.checkboxDatas==null || this.checkboxDatas.length==0){
           this.$message({
             type: 'warning',
             message: '请勾选要编辑的信息'
           })
           return
         }
         if(this.checkboxDatas.length!=1){
           this.$message({
             type: 'warning',
             message: '请选择一条信息进行编辑'
           })
           return
         }
         this.searchTableData = [];
         this.queryData = [];
         this.formInline.deptName = "";
         this.formInline.a0101 = "";
         let editData=JSON.parse(JSON.stringify(this.checkboxDatas[0]));
         for(let key in editData){
           this.addForm[key]=editData[key]
         }
         getFileName9({e01z9Id:this.checkboxDatas[0].e01z9Id}).then(res =>{
           if(res.code=='200'){
             if(res.data!=null&&res.data!=''){
               this.addForm.fileName=res.data.fileName;
             }else{
               this.addForm.fileName="未选择文件";
             }
             this.addForm2 = JSON.parse(JSON.stringify(this.addForm));
           }
         })
         queryE01z9A01s(this.checkboxDatas[0].e01z9Id).then(response=>{
           this.queryData = response.data;
           this.queryData.map(item=>{
             item.archivesType = "实体档案";
             item.e01z111a = "整本";
           })
         })
      }
      this.formShow = true
      this.tabsShow = false
    },
    // 干部部门搜索
    search(type) {
      console.log(this.a01state)
      this.searchTableData=[];
      this.isa01 = type;
      const _this = this
      if (type === 1) {
        this.dpbutton=false
        this.searchTableData.bodyHeight = '222px'
        this.searchText = this.formInline.deptName
      } else {
        if (this.formInline.a0101 == null || this.formInline.a0101 == '') {
          this.$message({
            message: '请输入干部搜索条件',
            type: 'warning'
          })
          return;
        }
        this.searchText = this.formInline.a0101
        this.dpbutton=true
      }
      queryA01List({ searchText: this.searchText, type: type ,state:this.a01state})
        .then(response => {
          if(type === 1){
            _this.searchTableData = response.data
          }else{
            /*_this.beforeQueryData = response.data;
            let tableData = [];
            for(let i in response.data){
              if(response.data[i].state==_this.a01state){
                tableData.push(response.data[i]);
              }
            }
            _this.searchTableData = tableData*/
            _this.searchTableData = response.data
          }
        })
    },
    // 干部部门搜索结果 复选框
    gridSelectionChange(val) {
      this.searchChek = val
    },
    // 干部部门搜索结果显示
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
    // grid  双击部门 查询人员
    deptClick(val) {
      console.log(this.$refs.searchTableData)
      if(this.isa01 == 2){
        return;
      }
      this.isa01 = 2;
      if (val.children) {
        this.searchTableData.bodyHeight = '222px'
        this.dpbutton=true
        let tableData = [];
        for(let i in val.children){
          if(val.children[i].state==this.a01state){
            tableData.push(val.children[i]);
          }
        }
        this.searchTableData = tableData
        this.beforeQueryData = val.children
      }
    },
    // 干部选择
    checkA01State(val){
      console.info(val)
      if(this.dpbutton){
        let tableData = [];
        for(let i in this.beforeQueryData){
          if(this.beforeQueryData[i].state==val){
            tableData.push(this.beforeQueryData[i]);
          }
        }
        this.searchTableData = tableData
      }
      console.info(this.searchTableData)
    },
    fileChange(val){
      console.info(val.target.value)
      if(val.target.value==''){
        this.addForm.fileName = "未选择文件"
      }else{
        this.addForm.fileName = val.target.value
      }
    },
    // 重置表单
    resetAddForm(){
      if(this.addForm.id == ""){
        this.addForm={
          id: "",
          e01z801:"",
          e01z817:"",
          flag:"",
          userName:this.loginUser.userName,
          userId:this.loginUser.userId,
          userCard:"",
          e01z824a:"",
          userPhone:"",
          applicationReasons:"",
          startTime:"",
          endTime:"",
          duty:"",
          printContents:"",
          e01z831:"",
          fileName:"未选择文件",
          archiveStatus: "1"
        }
      }else {
        this.addForm=JSON.parse(JSON.stringify(this.addForm2))
      }
    },
    // 添加
    typeClick() {
      console.info(this.searchChek)
      let gridDataList = JSON.parse(JSON.stringify(this.searchChek))
      let a01s = [];
      for (let i = 0; i < gridDataList.length; i++) {
        let flag = true;
        for(let n in this.queryData){
          if(this.queryData[n].a01Id==this.searchChek[i].id){
            a01s.push(this.searchChek[i]);
            flag=false;
          }
        }
        if(flag){
          gridDataList[i].e01z1Id = 'zb'
          gridDataList[i].a01Id=this.searchChek[i].id
          gridDataList[i].duty=this.searchChek[i].duty
          gridDataList[i].e01z111a="整本"
          gridDataList[i].archivesType="实体档案"
          this.queryData.push(gridDataList[i]);
          console.info(gridDataList[i])
        }
      }
      if(a01s.length>0){
        let message = "";
        for(let y in a01s){
          message=message+a01s[y].a0101+"、";
        }
        message = message.substr(0,message.length-1);
        message = message+"信息已添加";
        this.$message({
          message:message,
          type:"warning"
        })
      }

    },
    // 申请提交
    onGrid(addForm) {
      const _this = this
      this.$refs[addForm].validate((valid) => {
        if (valid) {
          if(this.queryData.length == 0){
            this.$message({
              type:'warning',
              message:'请选择需要借阅的干部档案'
            })
            return;
          }
          this.addForm.status=this.applyStates
          let returnMap = {
            formValues:this.addForm,
            gridValues:this.queryData,
            applyType:1
          }
          let testFormData = new FormData();
          if(this.$refs.imageFile.files[0]!=null){
            testFormData.append("files", this.$refs.imageFile.files[0])
          }
          testFormData.append("jsonStr", JSON.stringify(returnMap))
          if(this.addForm.id!=""){
            //修改方法
            updateByEntityBorrowAndList(testFormData).then(response=>{
              if(response.code==200){
                prompt('保存',0)
                this.formShow=false;
                this.tabsShow=true;
                this.resetAddForm();
                this.queryData=[];
                this.searchTableData=[]
                this.ArchiveBorrowsaFunction();
              }
            })
          }else {
            checkBorrowEntityFile(testFormData).then(response => {
              if (response.code == 200) {
                prompt('保存',0)
                this.formShow = false;
                this.tabsShow = true;
                this.resetAddForm();
                this.queryData = [];
                this.searchTableData = []
                this.ArchiveBorrowsaFunction();
              } else {
                prompt('保存',1)
              }
            })
          }
        } else {
          prompt('保存',2)
        }
      })
    },
    //返回待审批页面
    onBack(){
      this.tabsShow = true
      this.formShow = false
    },

    // 删除假数据  待删
    deleteRow(index, rows,row) {
      this.$confirm('此操作将永久删除所选信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
          deleteE01z9RelaLedger(row.id).then(response=>{
            if(response.code == 200){
              rows.splice(index, 1)
            }
          })
        })
        .catch(() => {
        })
    },
    deleteRow2(index, rows) {
      rows.splice(index, 1)
    },
    deleteArchiveBorrow(value) {
      if(this.checkboxDatas==null || this.checkboxDatas.length==0){
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
          console.log(1111111111111)
          let ids = [];
          this.checkboxDatas.map(item=>{
            ids.push(item.id);
          })
          deleteE01z9RelaLedger(ids.join(",")).then(response=>{
            this.$message({
              message: '删除成功',
              type: 'success'
            });
            this.ArchiveBorrowsaFunction();
          })
        })
        .catch(() => {
        })
    },
    /**
    //删除实体档案台账
    handleDeleteArchiveBorrow(value) {
      const _this = this;
      let param = {};
      param.ids = this.gridCheck.toString();
      param.type = _this.ArchiveBorrowsType;
      if (this.gridCheck == 0) {
        this.$message({
          showClose: true,
          message: '请选择您要删除的内容',
          type: 'warning'
        })
      } else {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
          .then(() => {
            deleteArchiveBorrowsRecords(param)
              .then(function (response) {
                _this.$message({
                  type: 'success',
                  message: '删除实体档案台账!'
                })
                //_this.ArchiveBorrowsaFunction(params);
              })
              .catch(function (error) {
                _this.$message.error(error)
              })
          })
          .catch(() => {
            _this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
      }
    },*/
    /* 复选框事件 */
    handleSelectionChange(val) {
      this.checkboxDatas = val
      this.checkbox = val.map(item => item.id)
    },
    pageCurrent(val) {
      this.pageNum = val
      const params = {
        pageNum: val,
        pageSize: this.pageSize
      }
      this.ArchiveBorrowsaFunction(params)
    },
    pageSizes(val) {
      this.pageSize = val
      const params = {
        pageNum: this.pageNum,
        pageSize: val
      }
      this.ArchiveBorrowsaFunction(params)
    },
    // 导出
    handleExportArchiveBorrow(){
      if (this.checkboxDatas.length == 0) {
        this.$confirm('是否导出搜索后的台账数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let params = {
            pageSize:9999999,
            pageNum:1,
            userName:this.queryParams.userName,
            endTime:this.queryParams.endTime,
            beginTime:this.queryParams.beginTime,
            creatersTime:this.queryParams.approvTime
          }
          queryE0z9s(params).then(response=>{
            if (response.rows.length == 0) {
              this.$message.error("无数据,导出失败");
              return;
            }
            response.rows.map((item, index) => {
              item.creatersTime = this.geteFullNameWapper(item, "creatersTime");
              item.startTime = this.geteFullNameWapper(item, "startTime");
              item.endTime = this.geteFullNameWapper(item, "endTime");
              item.approvTime = this.geteFullNameWapper(item, "approvTime");
              item.e01z111a = this.getFormatter(item.e01z111a);
            })
            let data = {
              "colTitle":{
                "status":"审批状态",
                "userName":"申请人姓名",
                "userCard":"申请人证件号",
                "applicationReasons":"申请查阅理由",
                "e01z824a":"申请人所在单位",
                "userPhone":"申请人电话",
                "duty":"申请人职务",
                "applyNumber":"申请借阅档案卷数",
                "creatersTime":"申请时间",
                "startTime":"申请借阅起始日期",
                "endTime":"申请借阅截止日期",
                "applyStatus":"申请类型",
                "a0101":"被申请人姓名",
                "deptName":"被申请人所在部门",
                "e01z111a":"申请内容",
                "e01z801":"审批人",
                "e01z817":"经办人",
                "approvTime":"审批时间",
                "flag":"审批意见",
                "printContents":"申请打印内容",
                "e01z831":"备注"
              },
              "colWidth":{
                "userCard":"25",
                "userPhone":"20",
                "creatersTime":"20",
                "startTime":"20",
                "approvTime":"20",
                "endTime":"25",
                "applicationReasons":"25",
                "e01z824a":"25",
                "applyNumber":"16",
                "deptName":"16",
              },
              "dataList":response.rows,
              "tableTitle":"实体档案借阅台账表",
              "sheetTitle":"实体档案借阅台账表",
              "downLoadName":"实体档案借阅台账表"
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
          checkboxDatas.map((item, index) => {
            item.creatersTime = this.geteFullNameWapper(item, "creatersTime");
            item.startTime = this.geteFullNameWapper(item, "startTime");
            item.endTime = this.geteFullNameWapper(item, "endTime");
            item.approvTime = this.geteFullNameWapper(item, "approvTime");
            item.e01z111a = this.getFormatter(item.e01z111a);
          })
          let data = {
            "colTitle":{
              "status":"审批状态",
              "userName":"申请人姓名",
              "userCard":"申请人证件号",
              "applicationReasons":"申请查阅理由",
              "e01z824a":"申请人所在单位",
              "userPhone":"申请人电话",
              "duty":"申请人职务",
              "applyNumber":"申请借阅档案卷数",
              "creatersTime":"申请时间",
              "startTime":"申请借阅起始日期",
              "endTime":"申请借阅截止日期",
              "applyStatus":"申请类型",
              "a0101":"被申请人姓名",
              "deptName":"被申请人所在部门",
              "e01z111a":"申请内容",
              "e01z801":"审批人",
              "e01z817":"经办人",
              "approvTime":"审批时间",
              "flag":"审批意见",
              "printContents":"申请打印内容",
              "e01z831":"备注"
            },
            "colWidth":{
              "userCard":"25",
              "userPhone":"20",
              "creatersTime":"20",
              "startTime":"20",
              "approvTime":"20",
              "endTime":"25",
              "applicationReasons":"25",
              "e01z824a":"25",
              "applyNumber":"16",
              "deptName":"16",
            },
            "dataList":checkboxDatas,
            "tableTitle":"实体档案借阅台账表",
            "sheetTitle":"实体档案借阅台账表",
            "downLoadName":"实体档案借阅台账表"
          }
          downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data))
        })

      }

    },
    // 打印
    handlePrintArchiveBorrow(){},
  }
}
</script>

<style lang="scss" scoped>
  .app-ArchiveBorrow-borrow-paramenter {
    height: 100%;
    .tree {
      width: 100%;
      height: 500px;
      padding: 20px;
    }
    .search-form{
      width:99%;
      background:rgba(204, 204, 204, 0.18);
      margin-left: 5px;
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
  .el-dialog__body {
    height: 500px;
    display: block;
  }
  .app-ArchiveBorrows {
    overflow: auto;
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

  /deep/ .el-table__fixed-right-patch {
		background-color:#F8F8F9
  }
  // 设置表格高度
  /deep/.el-table tbody tr:hover>td {
    background-color:#ecf5ff !important
  }
  /deep/.el-table__body td {
    padding: 0;
    height: 40px;
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
  // 公用树结构样式
  .tree-style{
    /deep/.el-tree--highlight-current .el-tree-node.is-current>.el-tree-node__content {
      background: rgba(204, 204, 204, 0.18);
    }
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

  /deep/ .el-table .el-table__header-wrapper th{
    height:31px;
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
      cursor:pointer;
      right: 0;
      bottom: 0;
      margin: auto;
      top: 0;
      height: 200px;
      width: 8px;
      border-radius: 20px;
      position: absolute;
  }
  .fileTextInput{
    width:50%;
    /deep/.el-input__inner{
      padding:0px;
      border: 0;  // 去除未选中状态边框
      outline: none; // 去除选中状态边框
      background-color: rgba(0, 0, 0, 0);// 透明背景
    }
  }
</style>

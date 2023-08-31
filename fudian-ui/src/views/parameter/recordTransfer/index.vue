<template>
  <div class="app-transfer-paramenter">
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
      <el-col :span="18" style="height: calc(100vh - 86px)" :style="rightStyleName">
        <!--检索   开始    -->
        <el-row ref="searchRow" :gutter="10">
          <el-form
            :inline="true"
            v-if="showSearchBox"
            label-width="70px"
            class="search-form"
            v-hasPermi="['parameter:recordTransfer:search']"
            >
            <el-form-item @keyup.enter.native="onEnterSearch" label="干部姓名" style="margin-top: 10px;margin-bottom: 10px">
              <el-input v-model="queryForm.personName" placeholder="请输入干部姓名" size="mini" clearable style="width:150px"></el-input>
            </el-form-item>
            <el-form-item @keyup.enter.native="onEnterSearch" label="所属部门" label-width="70px" style="margin-top: 10px;margin-bottom: 10px">
              <el-input v-model="queryForm.deptName" placeholder="请输入所属部门" size="mini" clearable style="width:150px"></el-input>
            </el-form-item>
            <!--
            <el-form-item label="转出日期" width="150px" style="margin-top: 10px;margin-bottom: 10px" >
            <el-date-picker
              v-model="queryForm.dateRange"
              size="mini"
              style="width: 220px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
            </el-form-item>
            -->
            <el-form-item @keyup.enter.native="onEnterSearch" label="转出文号" style="margin-top: 10px;margin-bottom: 10px">
              <el-input v-model="queryForm.id" placeholder="请输入转出文号" size="mini" clearable style="width:150px"></el-input>
            </el-form-item>
            <el-form-item style="margin-top: 10px;margin-bottom: 10px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            </el-form-item>
            <!-- <el-form-item style="margin-top: 10px;margin-bottom: 10px">
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item> -->
            <!--
            <el-form-item style="margin-top: 10px;margin-bottom: 10px">
              <el-button type="primary" size="mini" @click="searchDialog()">高级检索</el-button>
            </el-form-item>
            -->
          </el-form>
          <!-- 高级检索对话框
          <el-dialog title="高级检索" :visible.sync="highdialogVisible" width="80%">
            //查询条件
            <el-form :inline="true" class="" :model="searchTableForm">
              <el-form-item label="字段名" prop="key">
                <el-select v-model="searchTableForm.key" placeholder="请选择">
                  <el-option v-for="item in keyData" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                </el-select>
              </el-form-item>
              <el-form-item label="比较符" prop="oper">
                <el-select v-model="searchTableForm.oper" placeholder="请选择">
                  <el-option label="等于" value="EQ" />
                  <el-option label="不等于" value="NE" />
                  <el-option label="小于" value="LT" />
                  <el-option label="大于" value="GT" />
                  <el-option label="小于等于" value="LE" />
                  <el-option label="大于等于" value="GE" />
                  <el-option label="包含" value="LIKE" />
                  <el-option label="不包含" value="NOTLIKE" />
                </el-select>
              </el-form-item>
              <el-form-item label="字段值" prop="value">
                <el-input v-model="searchTableForm.value" placeholder="请输入内容" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="addSearchTableForm">添加</el-button>
              </el-form-item>
            </el-form>
            //表格展示条件
            <el-table :data="hightableData" border style="width: 100%">
              <el-table-column prop="key" label="字段名" style="width: 33%" />
              <el-table-column prop="oper" label="比较符" style="width: 33%" />
              <el-table-column prop="value" label="字段值" style="width: 33%" />
            </el-table>
            <span slot="footer" class="dialog-footer">
              <el-button @click="highdialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="determineSave">确 定</el-button>
            </span>
          </el-dialog> -->
          <!--检索   结束    -->
          <!-- 功能按钮 -->
          <div class="btn">
            <el-button type="primary" icon="el-icon-plus" size="mini" v-hasPermi="['parameter:recordTransfer:add']" @click="addTransfer">新增</el-button>
            <el-button type="primary" icon="el-icon-edit" size="mini" v-hasPermi="['parameter:recordTransfer:edit']" @click="update">编辑</el-button>
            <el-button type="primary" icon="el-icon-delete" size="mini" v-hasPermi="['parameter:recordTransfer:delete']" @click="remove(1)">删除</el-button>
            <el-button type="primary" icon="el-icon-paperclip" size="mini" v-hasPermi="['parameter:recordTransfer:receipt']" @click="getReceipt">回执管理</el-button>
            <el-button type="primary" icon="el-icon-receiving" size="mini" v-hasPermi="['parameter:recordTransfer:receiving']" @click="retrieve">档案找回</el-button>
<!--            <el-button type="primary" icon="el-icon-document" size="mini" v-hasPermi="['parameter:recordTransfer:document']">打印转递通知单</el-button>-->
            <el-button type="primary" icon="el-icon-download" size="mini" v-hasPermi="['parameter:recordTransfer:import']" @click="exportExcel">导出
            </el-button>
            <!--
            <div class="btn-search">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="function(){showSearchBox = !showSearchBox;tableHeight = showSearchBox?'380px':'400px'}" circle />
              <el-dropdown style="float: right; margin-left: 5px;">
                <el-button icon="el-icon-menu" size="mini" circle />
                <el-dropdown-menu style="padding: 10px">
                  <el-checkbox-group v-for="(item, index) in col" v-model="col[index].isCol" size="mini">
                    <el-checkbox>{{ item.label }}</el-checkbox>
                  </el-checkbox-group>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            -->
          </div>
        </el-row>
        <!-- 转出批次列表 -->
        <el-table
          size="mini"
          ref="tableRefs"
          :data="tableData"
          border
          stripe
          style="width: 100%"
          class="table"
          empty-text=" "
          :height="tableHeight"
          tooltip-effect="light"
          :header-cell-style="headClass"
          @row-click="StepsListRowClick"
          @selection-change="handleSelectionChange"
          >
          <el-table-column type="selection" width="50"/>
          <el-table-column align="center" show-overflow-tooltip label="序号" :index="customIndex" type="index" min-width="60"/>
          <el-table-column
            align="center"
            show-overflow-tooltip
            v-for="(item, index) in col"
            :key="index"
            v-if="item.isCol"
            :formatter="item.formatter"
            :prop="item.prop"
            :label="item.label"
            :width="item.width"/>
          <!--<el-table-column align="center" fixed="right" width="90px" label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="update(scope.row)">编辑</el-button>
              <el-button type="text" size="small" @click="remove(scope.row)">删除</el-button>
              <el-button type="text" size="small" @click="viewDetals(scope.row)">查看详情</el-button>
            </template>
          </el-table-column>-->
        </el-table>
        <!-- 分页 -->
        <div class="block" style="float: right;margin-top: 5px">
          <el-pagination
            style="width: 100%"
            :page-sizes="[20, 50, 100]"
            :page-size="pageSize"
            :current-page="pageNum"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="pageSizeS"
            @current-change="pageCurrent"
          />
        </div>
      </el-col>
      <!-- 详情对话框 -->
      <el-dialog
        title="转出详情"
        :visible.sync="dialogDetailsVisible"
        width="85%"
        class="dialog-style"
        :destroy-on-close="true"
        :close-on-click-modal="false"
        :append-to-body="true"
        >
        <div class="btn">
          <el-button type="primary" icon="el-icon-receiving" size="mini" @click="retrieve">档案找回</el-button>
          <el-button type="primary" icon="el-icon-download" size="mini">导出</el-button>
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
          height="300px"
          @selection-change="handleSelectionChange"
          >
          <el-table-column type="selection" width="50"/>
          <el-table-column align="center" label="序号" type="index" min-width="60"/>
          <el-table-column align="center" label="干部姓名" prop="a0101"/>
<!--          <el-table-column align="center" label="干部编号" prop="number" show-overflow-tooltip></el-table-column>-->
          <el-table-column align="center" label="所属部门" prop="deptId" show-overflow-tooltip></el-table-column>
          <el-table-column align="center" label="职务" prop="duty" show-overflow-tooltip :formatter="getFullName"/>
          <el-table-column align="center" label="转递原因" prop="e01z721" show-overflow-tooltip></el-table-column>
          <el-table-column align="center" label="正本(卷)" prop="e01z711"></el-table-column>
          <el-table-column align="center" label="副本(卷)" prop="e01z714" show-overflow-tooltip></el-table-column>
          <el-table-column align="center" label="档案材料(份)" prop="matNum"/>
          <el-table-column align="center" fixed="right" label="操作" width="100">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="remove(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogDetailsVisible = false">关 闭</el-button>
        </span>
      </el-dialog>
      <!-- 编辑对话框
      <el-dialog title="编辑" :visible.sync="dialogVisible" width="70%" class="dialog-style" >
        <el-form ref="form" :model="transForm" :inline="true" label-width="130px" >
          <el-form-item label="干部姓名">
            <el-input v-model="transForm.a0101" placeholder="请输入内容" size="mini"/>
          </el-form-item>
          <el-form-item label="干部编号" >
            <el-input v-model="transForm.number" placeholder="请输入内容" size="mini"/>
          </el-form-item>
          <el-form-item label="职务" >
            <el-select v-model="transForm.duty" filterable placeholder="请选择职务" style="width:163px" size="mini">
              <el-option v-for="item in dutys" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
            </el-select>
          </el-form-item>
          <el-form-item label="经办人" >
            <el-input v-model="transForm.e01z717" placeholder="请输入内容" size="mini"/>
          </el-form-item>
          <el-form-item label="所属部门" >
            <el-input v-model="transForm.deptName" placeholder="请输入内容" size="mini"/>
          </el-form-item>
          <el-form-item label="转往单位" >
            <el-input v-model="transForm.e01z704a" placeholder="请输入内容" size="mini"/>
          </el-form-item>
          <el-form-item label="转出日期" >
            <el-date-picker v-model="transForm.e01z701" type="date" placeholder="请选择转递日期" style="width:163px" size="mini"/>
          </el-form-item>
          <el-form-item label="转出文号" >
            <el-input v-model="transForm.id" placeholder="请输入内容" size="mini"/>
          </el-form-item>
          <el-form-item label="转递原因" >
            <el-input v-model="transForm.e01z721" placeholder="请输入内容" size="mini"/>
          </el-form-item>
          <el-form-item label="转出正本数(卷)" >
            <el-input-number v-model="transForm.e01z711" :min="0" size="mini" style="width:163px"/>
          </el-form-item>
          <el-form-item label="转出副本数 (卷)" >
            <el-input-number v-model="transForm.e01z714" :min="0" size="mini" style="width:163px"/>
          </el-form-item>
          <el-form-item label="档案材料(份)"  >
            <el-input-number v-model="transForm.matNum" :min="0" size="mini" style="width:163px"/>
          </el-form-item>
          <el-form-item label="回执附件名称"  >
            <el-input v-model="transForm.fileName" placeholder="请输入内容" size="mini"/>
          </el-form-item>
          <el-form-item label="备注" >
            <el-input type="textarea"
              style="width:163px"
              :autosize="{minRows:1, maxRows: 3}"
              v-model="transForm.e01z731"
              placeholder="请输入内容"
              size="mini"
            />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateBill('form')">确 定</el-button>
        </span>
        <span slot="footer" class="dialog-footer" >
          <el-button size="mini" @click="dialogVisible = false">取 消</el-button>
          <el-button size="mini" type="primary" @click="updateBill('form')">保 存</el-button>
        </span>
      </el-dialog>
        -->
    </el-row>
    <!-- 编辑页面 -->
    <el-col v-if="formShow" :span="24" :xs="24" style="margin: 0;padding: 0;height: calc(100vh - 90px);overflow: auto;">
      <el-row>
        <el-col :span="24">
          <div
            style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">
            第一步：填写转出信息
          </div>
          <div ref="customRef" id="app" style="width:100%">
            <el-form ref="topData" :inline="true" :rules="rules" label-width="100px" :model="topData" show-message style="padding: 20px 0 0 0;width: 75%;margin: 0 auto">
              <el-form-item label="转出文号" prop="id">
                <el-input v-model="topData.id" :disabled="true" size="mini"/>
              </el-form-item>
              <el-form-item label="转往单位" prop="e01z704a" size="mini">
                <el-input v-model="topData.e01z704a"/>
              </el-form-item>
              <el-form-item label="转出日期" :formatter="getFullName" prop="e01z701">
                <el-date-picker v-model="topData.e01z701" type="date" size="mini"/>
              </el-form-item>
              <el-form-item label="转递原因" prop="e01z721">
                <el-input v-model="topData.e01z721" size="mini"/>
              </el-form-item>
              <el-form-item label="经办人" prop="e01z717">
                <el-input v-model="topData.e01z717" size="mini"/>
              </el-form-item>
              <el-form-item label="审批人" prop="approver">
                <el-input v-model="topData.approver" size="mini"/>
              </el-form-item>
            </el-form>
            <div style="padding-top: 10px;width: 75%;margin: 0 auto">
              <el-button style="float: right;margin-right: 10px;width:80px" size="mini" @click="resetForm">重置表单</el-button>
            </div>
          </div>
        </el-col>
        <!-- 选择干部 -->
        <el-col :span="24">
          <div
            style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">
            第二步：请选择待转出干部档案
          </div>
          <div style="width: 75%;margin: 0 auto;">
            <div style="width: 100%;padding:20px 0 0 0">
              <el-form :inline="true" v-model="a01Search" style="width: 100%;">
                <el-form-item label="干部状态">
                  <el-select v-model="a01Search.state" @change="checkA01State" filterable placeholder="请选择干部状态"
                             class="input-w" size="mini">
                    <el-option v-for="item in states" :key="item.dictCode" :label="item.fullName"
                               :value="item.dictCode"/>
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-input v-model="cadresText" @keyup.enter.native="search(1)" placeholder="请输入部门" size="mini"/>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="search(1)" size="mini">部门搜索</el-button>
                </el-form-item>
                <el-form-item>
                  <el-input v-model="personnelText" @keyup.enter.native="search(2)" placeholder="请输入干部姓名" size="mini"/>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="search(2)" size="mini">干部搜索</el-button>
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
              style="width: 100%;"
              height="222px"
              :header-cell-style="headClass"
              @cell-dblclick="deptClick"
              @selection-change="leftChange"
              @row-click="handleGridChange"
            >
              <el-table-column type="selection" width="40" key="1" v-if="deptNshow"/>
              <el-table-column style="width: 1%" type="index" align="center" label="序号"/>
              <el-table-column prop="a0101" v-if="isa01 == 2" key="2" label="干部姓名" align="center"/>
<!--              <el-table-column prop="number" v-if="isa01 == 2" key="3" label="干部编号" align="center"/>-->
              <el-table-column prop="deptName" v-if="isa01 == 2" key="4" label="所属部门" align="center"/>
              <el-table-column prop="deptName" v-if="isa01 == 1" key="5" label="部门名称" align="center"
                               style="width: 50%"/>
              <el-table-column prop="parentId" v-if="isa01 == 1" key="6" label="上级部门" align="center"
                               style="width: 50%"/>
              <el-table-column prop="a0104" v-if="isa01 == 2" key="7" label="性别" align="center"
                               :formatter="getFullName"/>
              <el-table-column prop="a0107" v-if="isa01 == 2" key="8" label="出生日期" align="center"
                               :formatter="getFullName"/>
              <el-table-column prop="state" v-if="isa01 == 2" key="9" label="干部状态" align="center"
                               :formatter="getFullName"/>
            </el-table>
          </div>
          <div style="padding-top: 10px;width: 75%;margin: 0 auto">
            <el-button type="primary" style="float: right;margin-right: 10px;width:80px" size="mini" @click="createGridData('form')">添加
            </el-button>
          </div>
        </el-col>
        <el-col :span="24">
          <div style="margin: 0 auto;width:80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc">
            第三步：确认干部信息并生成转出列表
          </div>
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
                <el-table-column align="center" style="width: 1%" type="index" label="序号"/>
                <el-table-column align="center" prop="a0101" label="干部姓名" min-width="80px"/>
<!--                <el-table-column align="center" prop="number" label="干部编号" show-overflow-tooltip/>-->
                <el-table-column align="center" prop="deptId" label="所属部门" show-overflow-tooltip :formatter="getFullName"/>
                <el-table-column align="center" prop="duty" label="职务" show-overflow-tooltip :formatter="getFullName"
                                 width='80'/>
                <el-table-column align="center" prop="e01z721" label="转递原因" show-overflow-tooltip/>
                <el-table-column align="center" prop="e01z711" label="正本" width='80'/>
                <el-table-column align="center" prop="e01z714" label="副本" width='80'/>
                <el-table-column align="center" prop="matNum" label="档案材料" width='80'/>
                <el-table-column align="center" label="操作" width="100">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="updateDialog(scope.row)">编辑</el-button>
                    <el-button type="text" size="small" @click="deleteRow(scope.$index, rightData)">移除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
          <div style="padding: 20px 0 0 0;width: 75%;margin: 0 auto">
            <el-button type="primary" style="float: right;margin-right: 10px;width:80px" size="mini" @click="createTransfer">保存记录
            </el-button>
            <el-button style="float: right;margin-right: 10px;width:80px" size="mini" @click="onBack">返回</el-button>
          </div>
        </el-col>
        <!-- 转出信息编辑对话框 -->
        <el-dialog
          title="编辑"
          :visible.sync="updateDialogVisible"
          width="75%"
          class="dialog-style"
          :destroy-on-close="true"
          :close-on-click-modal="false"
          :append-to-body="true"
          >
          <el-form :rules="dialogRules" ref="editForm" :model="editForm" label-width="140px" :inline="true">
            <el-form-item label="干部姓名">
              <el-input v-model="editForm.a0101" placeholder="请输入干部姓名" :disabled="true" size="mini"/>
            </el-form-item>
            <!--<el-form-item label="干部编号">
              <el-input v-model="editForm.number" placeholder="请输入干部编号" size="mini"/>
            </el-form-item>-->
            <el-form-item label="所属部门">
<!--              <el-input v-model="editForm.deptName" placeholder="请输入所属部门" size="mini"/>-->
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
                <el-option v-for="item in dutys" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
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
          <div slot="footer" class="dialog-footer">
            <el-button @click="updateDialogVisible = false" size="mini">取 消</el-button>
            <el-button type="primary" @click="onCommitEdit" size="mini">确 定</el-button>
          </div>
        </el-dialog>
      </el-row>
    </el-col>
    <!-- 回执附件 -->
    <el-dialog
      title="回执单预览及上传"
      width="50%"
      :height="imgHeight"
      :visible.sync="dialogVisible2"
      class="dialog-style"
      :destroy-on-close="true"
      :close-on-click-modal="false"
      :append-to-body="true"
      >
      <el-upload
        ref="upload"
        name="part"
        multiple
        class="upload-demo"
        style="display: inline"
        :show-file-list="false"
        :auto-upload="false"
        :on-change="imgPreview"
        :data="uploadData"
        :file-list="fileList"
        accept="image/png, image/jpeg"
        :action="imgUrl"
        >
        <el-button slot="trigger" icon="el-icon-thumb" size="mini" type="primary">选择文件</el-button>
        <el-button style="margin-left: 10px;" size="mini" type="primary" @click="submitUpload">
          <i class="el-icon-upload el-icon--right"/>上传文件
        </el-button>
        <el-button size="mini" type="primary" @click="deleReceipt()">
          <i class="el-icon-error" style="margin-right:5px"/>删除文件
        </el-button>
      </el-upload>
      <div>
        <div style="float: left; margin-left: 5px;" slot="tip" class="el-upload-list__item-name">{{fileName}}</div>
        <div style="float: right; margin-left: 5px;" slot="tip" class="el-upload__tip">请上传jpg、jpeg或png文件，且不超过5MB</div>
      </div>
      <div style="width: 100%; height: 400px;overflow: auto">
        <el-image
          :style="imgCss"
          :src="imageData"
          >
          <div slot="error">请先上传回执单附件</div>
        </el-image>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="closeReceipt" size="mini">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import Search from '@/components/search/index.vue'
  import { queryTransferBillList, updateTransferBatch, updateTransferBillById, deleteTransferBillByIndexs, queryListByConditionList, advancedSearchDetail } from '@/api/usual/parameter/transfer-bill-paramenter'
  import { queryYear, querySysFile, uploadReceiptData, deleteSysFileByBatchId, queryTransferBillAndBatchList, getImageFile } from '@/api/usual/parameter/transfer-batch-paramenter'
  import { queryDictionaryByType, queryLoginUser, queryDeptTree } from '@/api/usual/receive'
  import { queryDeptMap } from '@/api/usual/parameter/receives-paramenter'
  import { queryA01List, queryIdAndDate, queryIdIsUsable, insertTransfer, exportPacket } from '@/api/usual/transfer'
  import { downLoad, downLoadPost } from '@/downLoad/filedownload'
  import { prompt } from '@/components/windoprompt/prompt'
  import selectTree from '@/views/components/tree/tree.vue'

  const params = {
    pageNum: 1,
    pageSize: 20
  }
  const baseUrl = process.env.VUE_APP_BASE_API;

  export default {
    components: {
      Search,
      selectTree
    },
    name: '档案转出年度台账',
    watch: {
      tableData: {
        handler(newName, oldName) {
          this.$nextTick(() => {
            this.$refs.tableRefs.doLayout();
          })
        },
        deep: true,
        // immediate:true
      },
      // 编辑
      leftData: {
        handler(newName, oldName) {
          this.$nextTick(() => {
            this.$refs.searchTableData.doLayout();
          })
        },
        deep: true,
        // immediate:true
      },
      rightData: {
        handler(newName, oldName) {
          this.$nextTick(() => {
            this.$refs.rightData.doLayout();
          })
        },
        deep: true,
        // immediate:true
      },
      detailsData: {
        handler(newName, oldName) {
          this.$nextTick(() => {
            this.$refs.detailsData.doLayout();
          })
        },
        deep: true,
        // immediate:true
      },
    },
    data() {
      return {
        userName: '', //当前登录用户
        // 编辑页面相关数据
        tabsShow: true,
        formShow: false,
        tableRefs: [],
        searchTableData: [],
        // 人员选择数据
        beforeQueryData: [],
        leftPersonCheck: [],
        states: [],
        a01State: '',
        a01Search: {
          state: "0"
        },
        imgUrl: baseUrl + '/transfer/uploadReceiptData',
        select: '',
        deptNshow: false,
        isa01: 2,
        personnelText: '',
        cadresText: '',
        searchText: '',
        leftChek: [],
        leftData: [],
        // 编辑对话框
        updateDialogVisible: false,
        // 验证必填项
        rules: {
          e01z704a: [
            { required: true, message: '请输入转往单位', trigger: 'blur' },
            { max: 60, message: '输入内容不得超过60个字符', trigger: 'blur' }
          ],
          e01z701: [
            { required: true, message: '请选择转出日期', trigger: 'blur' }
          ],
          e01z721: [
            { required: true, message: '请输入转递原因', trigger: 'blur' },
            { max: 40, message: '输入内容不得超过40个字符', trigger: 'blur' }
          ],
          e01z717: [
            { required: true, message: '请输入经办人', trigger: 'blur' },
            { max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ],
          approver: [
            { max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ]
        },
        topData: {
          id: '',
          e01z704a: '',
          e01z701: '',
          e01z721: '',
          e01z717: '',
          approver: '',
        },
        topData2:{
          id: '',
          e01z704a: '',
          e01z701: '',
          e01z721: '',
          e01z717: '',
          approver: '',
        }, //重置副本
        editForm: {
          number: '',
          deptId: '',
          duty: '',
          e01z711: '',
          e01z714: '',
          matNum: ''
        },
        deptOptions: [],
        defaultProps: {
          children: 'children',
          label: 'deptName',
          id: 'id'
        },
        selectTreeWidth: "100px",
        ischecked: true,
        rightDataIndex: '',
        rightData: [],
        rightDataChek: [],
        value: '',
        //字典表转换
        dutys: [],
        a0104s: [],
        a0104s1: [],
        a0141s: [],
        orgMap: [],
        // 编辑页面结束
        picClassname: 'shrinkageLeft',
        showSearchBox: true,
        tableHeight: '70%',
        rightStyle: {
          width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px 10px 0px 10px', height: 'calc(100vh - 86px)', overflow: 'auto'
        },
        rightStyle1: {
          width: '99%', height: 'calc(100vh - 86px)', padding: ' 10px 10px 0px 10px', height: 'calc(100vh - 86px)', overflow: 'auto'
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
          width: '84%', height: 'calc(100vh - 86px)', padding: '10px 10px 0px 10px', height: 'calc(100vh - 86px)'
        },
        leftTree1: true,
        treeHeight: "height:500px",
        /**
         // 高级搜索 form
         searchTableForm: {},
         // 搜索条件值
         keyData: [
         { dictCode: 'a0101', fullName: '姓名' },
         { dictCode: 'e01z701', fullName: '转出日期' },
         { dictCode: 'deptName', fullName: '所属部门' },
         { dictCode: 'duty', fullName: '职务' },
         { dictCode: 'e01z704a', fullName: '转往单位' },
         { dictCode: 'e01z721', fullName: '转出原因' },
         { dictCode: 'e01z717', fullName: '经办人' },
         { dictCode: 'e01z711', fullName: '转出正本数(卷)' },
         { dictCode: 'e01z714', fullName: '转出副本数(卷)' },
         { dictCode: 'matNum', fullName: '档案材料(份数)' },
         { dictCode: 'id', fullName: '转出文号' },
         { dictCode: 'e01z731', fullName: '备注' }
         ],
         */
        col: [
          {label: '干部姓名', prop: 'a0101', isCol: true},
          // {label: '干部编号', prop: 'number', isCol: true},
          {label: '转递原因', prop: 'e01z721', isCol: true, width: "100px"},
          {label: '转出日期', prop: 'e01z701', isCol: true, width: "100px", formatter: this.getFullName},
          {label: '所属部门', prop: 'deptId', isCol: true, width: "120px", formatter: this.getFullName},
          {label: '职务', prop: 'duty', isCol: true, width: "100px", formatter: this.getFullName},
          {label: '转往单位', prop: 'e01z704a', isCol: true, width: "160px"},
          // { label: '正本总数', prop: 'sum', isCol: true, width:"80px" },
          {label: '回执附件名称', prop: 'fileName', isCol: true, width: "120px"},
          {label: '经办人', prop: 'e01z717', isCol: true, width: "100px"},
          {label: '转出文号', prop: 'id', isCol: true, width: "120px"},
          {label: '审批人', prop: 'approver', isCol: true, width: "100px"},
          {label: '正本(卷)', prop: 'e01z711', isCol: true},
          {label: '副本(卷)', prop: 'e01z714', isCol: true},
          {label: '档案材料(份)', prop: 'matNum', isCol: true, width: "110px"},
          {label: '备注', prop: 'e01z731', isCol: true},
          {label: '干部状态', prop: 'state', isCol: false},
        ],
        hightableData: [],
        highdialogVisible: false,
        copiesForm: { //条件区副本
          checkYear: '',
          personName: '',
          id: '',
          deptName: '',
        },
        queryForm: {
          personName: '',
          deptName: '',
          //dateRange: [],
          id: '',
        },
        /* 总页数 */
        total: 0,
        pageNum: 1,
        pageSize: 20,
        checkYear: '',
        //dialogVisible: false,
        filterText: '',
        transForm: {},
        // 树结构数据
        data2: [],
        /**
         defaultProps: {
        children: 'children',
        label: 'deptName',
        id: 'id'
      },
         */
        checkBatchId: '',
        dialogVisible2: false,  //回执附件
        fileName: '',
        fileList: [],
        fileSize: 0,
        imageData: "",
        imgCss: {},
        imgHeight: '',
        hasFile: false, //是否有回执
        //上传文件参数
        uploadData: {
          batchId: ""
        },
        //回执单数据
        img: {
          url: '',
        },
        // 导出
        // input3: ""
        // 表格数据
        tableData: [],
        detailsData: [],
        dialogDetailsVisible: false,
        // 复选框
        checkbox: [],
        checkboxDatas: [],
        dutys: [],
        deptMap: {},
        searchRow: 'searchRow',
        // 验证必填项app-parameter
        dialogRules: {
          e01z721: [
            {required: true, message: '请输入转递原因', trigger: 'blur'},
            {min: 0, max: 40, message: '输入内容不得超过40个字符', trigger: 'blur'}
          ]
        }
      }
    },
    mounted() {
      this.getDeptTree()
      queryYear().then(response => {
        this.data2 = [{
          id: '0',
          label: '转出年度',
          children: response.data
        }]
        // this.data2[0].children =
        // console.log(this.data2);
      })
      this.queryTransferBillList();
      //this.leftDeptLoad();
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
      };
    },
    //字典表获取
    created() {
      this.initColumnMap()
      this.getLoginUser()
    },
    methods: {
      //自定义序号
      customIndex(index){
        return (this.pageNum-1)*(this.pageSize)+(index+1);
      },
      //新增跳转至档案转出页面
      addTransfer() {
        this.$router.push({
          name: '档案转出',
        })
      },
      divClick() {
        this.leftTree1 = !this.leftTree1
        if (this.leftTree1) {
          this.rightStyleName = this.rightStyle
          this.leftStyleName = this.leftStyle
          this.picClassname = 'shrinkageLeft'
          this.treeShow = !this.treeShow
        } else {
          this.rightStyleName = this.rightStyle1
          this.leftStyleName = this.leftStyle1
          this.picClassname = 'shrinkageRight'
          this.treeShow = !this.treeShow
        }
      },
      // 公用表格表头样式
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      // 点击行事件
      StepsListRowClick(row) {
        console.log(row)
        this.$refs.tableRefs.clearSelection()
        if (this.checkbox.length === 0) {
          this.$refs.tableRefs.toggleRowSelection(row)
          return;
        }
        this.$refs.tableRefs.toggleRowSelection(row)
      },
      /** 高级检索
       searchDialog(){
      this.highdialogVisible = true
    },
       */
      // 重置按钮操作
      resetQuery() {
        //this.queryForm.dateRange = [];
        this.queryForm.deptName = '';
        this.queryForm.personName = '';
        this.getTransferBillAndBatch();
      },

      exportExcel() {
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
              id: this.copiesForm.id,
            }
            params.pageSize = 999999
            params.pageNum = 1
            params.searchValue = param
            queryTransferBillList(params)
              .then(response => {
              if (response.rows.length == 0) {
                this.$message.error("无数据,导出失败");
                return;
              }
              response.rows.map((item, index) => {
                item.e01z701 = this.geteFullNameWapper(item, "e01z701");
                item.deptId = this.geteFullNameWapper(item, "deptId");
                item.duty = this.geteFullNameWapper(item, "duty");
              })
              let data = {
                "colTitle": {
                  "a0101": "干部姓名",
                  // "number": "干部编号",
                  "e01z721": "转递原因",
                  "e01z701": "转出日期",
                  "deptId": "所属部门",
                  "duty": "职务",
                  "e01z704a": "转往单位",
                  "fileName": "回执附件名称",
                  "e01z717": "经办人",
                  "id": "转出文号",
                  "approver": "审批人",
                  "e01z711": "正本(卷)",
                  "e01z714": "副本(卷)",
                  "matNum": "档案材料(份)",
                  "e01z731": "备注"
                },
                "colWidth": {
                  "e01z704a": "25",
                  "e01z701": "25",
                  "deptId": "25",
                },
                "dataList": response.rows,
                "tableTitle": "档案转出台账表",
                "sheetTitle": "档案转出台账表",
                "downLoadName": "档案转出台账表"
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
              item.e01z701 = this.geteFullNameWapper(item, "e01z701");
              item.deptId = this.geteFullNameWapper(item, "deptId");
              item.duty = this.geteFullNameWapper(item, "duty");
            })
            let data = {
              "colTitle": {
                "a0101": "干部姓名",
                // "number": "干部编号",
                "e01z721": "转递原因",
                "e01z701": "转出日期",
                "deptId": "所属部门",
                "duty": "职务",
                "e01z704a": "转往单位",
                "fileName": "回执附件名称",
                "e01z717": "经办人",
                "id": "转出文号",
                "approver": "审批人",
                "e01z711": "正本(卷)",
                "e01z714": "副本(卷)",
                "matNum": "档案材料(份)",
                "e01z731": "备注"
              },
              "colWidth": {
                "e01z704a": "25",
                "e01z701": "25",
                "deptId": "25",
              },
              "dataList": checkboxDatas,
              "tableTitle": "档案转出台账表",
              "sheetTitle": "档案转出台账表",
              "downLoadName": "档案转出台账表"
            }
            downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data))
          })

        }
      },
      geteFullNameWapper(item, field) {
        let col = {
          property: field
        }
        return this.getFullName(item, col);
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.pageNum = 1
        this.copiesForm = JSON.parse(JSON.stringify(this.queryForm))
        this.queryTransferBillList();
      },
      //搜索区回车事件
      onEnterSearch() {
        this.handleQuery();
      },
      /**
       //高级检索关闭前
       handleClose(){},
       //检索条件添加
       addSearchTableForm(){
        this.hightableData.push(this.searchTableForm)
        console.log(this.hightableData)
    },
       //高级检索确定检索功能
       determineSave(){
      const _this = this
      const data = {
          conditionsJsonArray: JSON.stringify(this.hightableData),
          pageNum: 1,
          pageSize: 20
      }
      advancedSearchDetail(data)
        .then(response => {
            _this.tableData = response.rows
            this.highdialogVisible = false
        })
    },
       */
      /**
       // 加载左侧部门树
       leftDeptLoad() {
      const _this = this
      _this.loading = true
      queryDeptListById('000').then(function(response) {
        let leftTree = [{
          id:'000',
          deptName:'转出年度',
          children:response.data
        }]
        _this.data2 = leftTree
      })
    },
       */
      treeNodeClick(data) {
        this.pageNum = 1
        if (data.id != '0') {
          this.checkYear = data.id
        } else {
          this.checkYear = ''
        }
        this.queryTransferBillList();
      },
      remove(row) {
        console.log(row)
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
            if (row == 1) {
              if (this.checkboxDatas != null && this.checkboxDatas.length > 0) {
                deleteTransferBillByIndexs({
                  jsonStr: JSON.stringify(this.checkboxDatas)
                })
                  .then(response => {
                    if (response.code == 200) {
                      this.$message({
                        message: '删除成功',
                        type: 'success'
                      })
                      this.queryTransferBillList({
                        pageNum: 1,
                        pageSize: 20
                      })
                    } else {
                      this.$message({
                        message: '删除失败',
                        type: 'error'
                      })
                    }
                  })
              }
            } else {
              let params = [row]
              let _this = this
              deleteTransferBillByIndexs({
                jsonStr: JSON.stringify(params)
              }).then(function (response) {
                _this.$message({
                  type: 'success',
                  message: response.msg
                })
                _this.viewDetals({
                  pageNum: 1,
                  pageSize: 20
                })
              })
            }
          })
          .catch(() => {
          })
      },
      //返回待审批页面
      onBack() {
        this.tabsShow = true
        this.formShow = false
      },
      // 编辑弹窗
      update() {
        console.log(this.checkboxDatas)
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
        let bills = {
          id: this.checkboxDatas[0].transId,
          pageSize: 999999,
          pageNum: 1
        }
        queryTransferBillAndBatchList(bills)
          .then(response => {
            this.rightData = []
            for (let y in response.rows[0].children){
              this.rightData.push(response.rows[0].children[y])
            }
            this.$nextTick(() => {
              this.$refs.rightData.doLayout();
            })
            this.total = response.total
            this.loading = false
          })
          .catch(err => {
            this.$message({
              showClose: true,
              message: "查询同批次转出明细失败，请联系管理员",
              type: 'error'
            })
          })
        this.topData = JSON.parse(JSON.stringify(this.checkboxDatas[0]))
        this.topData2 = JSON.parse(JSON.stringify(this.topData))
        this.tabsShow = false
        this.formShow = true
      },
      // 转出文号监听
      handleSelect(item) {
        console.log(item)
        const _this = this
        queryIdIsUsable({id: item})
          .then(response => {
            if (response.data === true) {
              _this.topData.id = this.topData.id
            } else {
              this.$message.error(response.msg)
            }
          })
      },
      // 重置表单
      resetForm() {
        this.topData = JSON.parse(JSON.stringify(this.topData2))
      },
      // 干部状态过滤
      checkA01State(val) {
        if (this.deptNshow) {
          let tableData = [];
          for (let i in this.beforeQueryData) {
            if (this.beforeQueryData[i].state == val) {
              tableData.push(this.beforeQueryData[i]);
            }
          }
          this.leftData = tableData
        }
      },
      // 干部、部门过滤
      search(type) {
        this.isa01 = type;
        const _this = this
        this.leftChek = [];
        console.log(this.leftChek)
        if (type === 1) {
          this.deptNshow = false
          this.searchText = this.cadresText
        } else {
          if (this.personnelText == null || this.personnelText == '') {
            this.$message({
              message: '请输入干部搜索条件',
              type: 'warning'
            })
            return;
          }
          this.deptNshow = true
          this.searchText = this.personnelText
        }
        queryA01List({searchText: this.searchText, type: type, state: this.a01Search.state})
          .then(response => {
            if (type === 1) {
              _this.leftData = response.data
            } else {
              _this.beforeQueryData = response.data;
              let tableData = [];
              for (let i in response.data) {
                if (response.data[i].state == _this.a01Search.state) {
                  tableData.push(response.data[i]);
                }
              }
              _this.leftData = tableData
            }
          })
      },
      // 勾选干部
      leftChange(val) {
        this.leftChek = val
        this.leftPersonCheck = val.map(item => item.id)
      },
      // grid 双击部门 查询干部
      deptClick(val) {
        if (this.isa01 == 2) {
          return;
        }
        this.isa01 = 2;
        if (val.children) {
          this.deptNshow = true;
          let tableData = [];
          for (let i in val.children) {
            if (val.children[i].state == this.a01Search.state) {
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
        let repet = ""
        let flag = false
        if (this.leftChek.length == 0) {
          this.$message({
            message: '请选择干部条目',
            type: 'warning'
          });
          return;
        }
        let checkGrid = JSON.parse(JSON.stringify(this.leftChek))
        OL:for (let i = 0; i < checkGrid.length; i++) {
          let gridList = {}
          gridList = checkGrid[i]
          for (let j = 0; j < this.rightData.length; j++) {
            if (checkGrid[i].id == this.rightData[j].a01Id) {
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
          for (let j in this.leftData) {
            if (this.leftData[j].id == this.leftChek[i].id) {
              this.leftData.splice(j, 1);
            }
          }
        }
        //}
        if (flag) {
          repet = repet.substring(0, repet.length - 1)
          this.$message({
            type: 'warning',
            message: repet + '信息已添加'
          })
        }
      },
      // 移除
      deleteRow(index, rows) {
        console.log(rows)
        if(this.rightData.length === 1){
          this.$message({
            type: 'warning',
            message: '转出列表不能为空'
          })
          return false;
        }
        rows.splice(index, 1)
      },
      // 编辑
      updateDialog(val) {
        this.updateDialogVisible = true
        this.editForm = JSON.parse(JSON.stringify(val))
      },
      // 保存编辑
      onCommitEdit() {
        let list = this.rightData
        let par = this.editForm
        console.log( this.editForm.deptId)
        for (let i in list) {
          let dataMap = list[i];
          console.log(dataMap)
          if (dataMap.a01Id == par.a01Id) {
            dataMap.number = par.number
            dataMap.deptId = par.deptId
            dataMap.duty = par.duty
            dataMap.e01z721 = par.e01z721
            dataMap.e01z711 = par.e01z711
            dataMap.e01z714 = par.e01z714
            dataMap.matNum = par.matNum
            this.updateDialogVisible = false
            return list[i] = dataMap
          }
        }
      },
      // 保存记录
      createTransfer() {
        this.$refs['topData'].validate((valid) => {
          if (valid) {
            let formData = JSON.parse(JSON.stringify(this.topData))
            formData.e01z701 = this.moment(this.topData.e01z701).format("YYYYMMDD")
            for (let i = 0; i < this.rightData.length; i++) {
              console.log(this.rightData[i])
              if (this.rightData[i].e01z721 == "" || this.rightData[i].e01z721 == null) {
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
            insertTransfer({jsonStr: returnData})
              .then(response => {
                if (response.code === 200) {
                  this.$message({
                    message: '保存成功',
                    type: 'success'
                  })
                  this.queryTransferBillList();
                  this.formShow = false
                  this.tabsShow = true
                }
              })
          } else {
            this.$message({
              message: '请检查转出信息是否填写完整',
              type: 'warning'
            })
          }
        })
      },
      /**
       // 编辑保存批次信息（未保存批次信息的修改）
       updateBill(transForm) {
      console.log(transForm)
      const _this = this
      this.$refs[transForm].validate(valid => {
        if (valid) {
          updateTransferBillById(this.transForm)
            .then(function(response) {
              _this.$message({
                showClose: true,
                message: '保存成功',
                type: 'success'
              })
              _this.getTransferBillAndBatch(params)
              _this.dialogVisible = false
            })
            .catch(function(error) {
              _this.$message({
                showClose: true,
                message: 'error',
                type: 'error'
              })
            })
        } else {
          return false
        }
      })
    },
       */
      // 查询批次及明细数据
      queryTransferBillList() {
        const param = {
          checkYear: this.checkYear,
          personName: this.copiesForm.personName,
          deptName: this.copiesForm.deptName,
          id: this.copiesForm.id,
        }
        params.pageSize = this.pageSize
        params.pageNum = this.pageNum
        params.searchValue = param
        queryTransferBillList(params)
          .then(response => {
            this.tableData = response.rows
            this.$nextTick(() => {
              this.$refs.tableRefs.doLayout();
            })
            this.total = response.total
            this.loading = false
          })
          .catch(err => {
            this.$message({
              showClose: true,
              message: "查询失败，请联系管理员",
              type: 'error'
            })
          })
      },
      getImg(src){
        var img_url = src
        var img = new Image()
        img.src = img_url
        img.onload = function(){
          console.info(img.height)
          console.info(img.width)
          if(img.height > 100 && img.width >100){
            this.imgCss={
              width:"100%",
              height:"100%",
              position:undefined,
              left:undefined,
              top:undefined,
              "margin-top":undefined,
              "margin-left":undefined,
              overflow:"auto"
            }
            // this.imgCss["width"] = "100%";
            // this.imgCss["height"] = "100%";
            // this.imgCss["position"] = undefined;
            // this.imgCss["left"] = undefined
            // this.imgCss["top"] = undefined
            // this.imgCss["margin-top"] = undefined
            // this.imgCss["margin-left"] = undefined
            // this.imgCss["overflow"] = "auto";
          }else{
            let top = img.height/2;
            let left = img.width/2;
            this.imgCss={
              width:undefined,
              height:undefined,
              position:"absolute",
              left:"50%",
              top:"50%",
              "margin-top":"-" + top + "px",
              "margin-left": "-" + left + "px",
            }
          }
        }
      },
      // 查看回执
      getReceipt(val) {
        val = this.checkboxDatas
        if (this.checkboxDatas == null || this.checkboxDatas.length == 0) {
          this.$message({
            type: 'warning',
            message: '请勾选要管理回执附件的信息'
          })
          return
        }
        if (this.checkboxDatas.length != 1) {
          this.$message({
            type: 'warning',
            message: '请选择一条信息管理回执附件'
          })
          return
        }
        let _this = this
        this.checkBatchId = val[0].id
        let batchId = val[0].id;
        querySysFile({batchId: batchId})
          .then(response => {
            console.log(response)
            if (response.data != null) {
              getImageFile({redisId:response.data}).then(res =>{
                this.imageData = 'data:image/png;base64,' + res.data;
                this.fileName = this.checkboxDatas[0].fileName;
                this.hasFile = true
                this.getImg(this.imageData);
              })
            } else {
              this.fileName = "";
              _this.imageData = "";
              this.hasFile = false
            }
          })
        _this.dialogVisible2 = true;
      },
      // 上传
      submitUpload(event) {
        if( this.fileSize === 0 ){
          this.$message({
            message: '请选择jpg、jpeg或png文件进行上传',
            type: 'warning'
          });
          return false
        }
        let _this = this;
        if(this.hasFile){
          this.$confirm('此操作将替换原回执单电子文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
            .then(response => {
              event.preventDefault();
              _this.$refs.upload.submit();
              prompt('上传',0);
              _this.handleQuery();
              /*_this.$message({
                type: 'success',
                message: "上传成功"
              })*/
            })
        }else{
          console.log(_this.uploadData)
          event.preventDefault();
          _this.$refs.upload.submit();
          prompt('上传',0)
          _this.handleQuery();
        }
      },
      // 上传前校验类型及大小
      imgPreview(file, fileList) {
        console.log(file)
        if(file == "" || file == null){
          this.$message({
            message: '请选择jpg、jpeg或png文件进行上传',
            type: 'warning'
          });
          this.fileSize = 0
          return false
        }
        let reader = new FileReader()
        reader.readAsDataURL(file.raw)
        reader.onload = () => {
          this.imageData = reader.result
          let temporaryList = [];
          temporaryList.push(fileList[fileList.length- 1]);
          this.fileList = temporaryList
        }
        //上传文件参数
        this.uploadData.batchId = this.checkBatchId;
        let testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
        this.fileName = file.name;
        const extension = testmsg === 'jpg'
        const extension2 = testmsg === 'jpeg'
        const extension3 = testmsg === 'png'
        const isLt5M = file.size / 1024 / 1024 < 5
        if (!extension && !extension2 && !extension3) {
          this.$message({
            message: '请选择jpg、jpeg或png文件进行上传',
            type: 'warning'
          });
          this.fileName = "";
          this.fileSize = 0
          return false
        }
        if (!isLt5M) {
          this.$message({
            message: '上传文件大小不能超过 5MB!',
            type: 'warning'
          });
          this.fileSize = 0
          return false
        }
        this.fileSize = file.size;
        return true;
      },
      // 删除回执
      deleReceipt() {
        if(!this.hasFile){
          return false;
        }
        let _this = this;
        this.$confirm('此操作将永久删除回执单电子文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            let batchId = this.checkBatchId
            deleteSysFileByBatchId({batchId: batchId})
              .then(response => {
                prompt('删除',0)
                _this.imageData = '';
                _this.handleQuery();
              })
              .catch(function (error) {
                prompt('删除',1)
              })
          })
          .catch(() => {
          })
      },
      // 关闭回执窗口
      closeReceipt(){
        this.dialogVisible2 = false
        this.fileName = ''
      },
      // 档案找回
      retrieve() {
        console.log(this.checkboxDatas)
        console.log(this.checkbox)
        if(this.checkboxDatas[0].state === "0"){
          this.$message({
            type: 'warning',
            message: '当前档案信息已找回，无需再次找回'
          })
          return
        }
        if (this.checkboxDatas == null || this.checkboxDatas.length == 0) {
          this.$message({
            type: 'warning',
            message: '请勾选要找回的档案信息'
          })
          return
        }
        if (this.checkboxDatas.length != 1) {
          this.$message({
            type: 'warning',
            message: '请选择一条档案信息进行找回'
          })
          return
        }
        this.$confirm('是否找回已勾选的档案?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.$router.push({
              name: '档案接收',
              params: {
                trans_a01Id: this.checkboxDatas[0].a01Id
              }
            })
          })
      },
      // 初始化转换参数
      initColumnMap() {
        // 当前页面所需字典表数据 职务
        queryDictionaryByType({dictType: 'ZB09', eparentCode: '0'})
          .then(response => {
            this.dutys = response.data
          })
        // 当前页面所需字典表数据 性别
        queryDictionaryByType({dictType: 'GB22611'})
          .then(response => {
            this.a0104s1 = response.data
          })
        // 获取政治面貌
        queryDictionaryByType({dictType: 'GB4762'})
          .then(response => {
            this.a0141s = response.data
          })
        // 获取干部状态
        queryDictionaryByType({dictType: 'FDA01State'})
          .then(response => {
            this.states = response.data
          })
        // 部门
        queryDeptMap().then(response => {
          this.deptMap = response.data
        })
      },
      // 获取部门树
      getDeptTree() {
        queryDeptTree({deptType: 0}).then(response => {
          // let map = {
          //   "deptName": "label"
          // }
          this.deptOptions = response.data
        })
      },
      // 时间格式转换
      getFullName(row, col) {
        // 时间转换
        if (col.property == "e01z701" || col.property == "a0107") {
          if (row[col.property] != null && row[col.property] != '') {
            return this.moment(row[col.property]).format("YYYY-MM-DD")
          }
        }
        // 职务
        if (col.property == "duty") {
          let returnValues = '';
          this.dutys.forEach(data => {
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName;
            }
          })
          return returnValues;
        }
        //部门
        if (col.property == 'deptId') {
          if (this.deptMap[row.deptId] == null) {
            return ''
          } else {
            return this.deptMap[row.deptId]
          }
        }
        // 性别
        if (col.property == "a0104") {
          let returnValues = '';
          this.a0104s1.forEach(data => {
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName;
            }
          })
          return returnValues;
        }
        // 政治面貌
        if (col.property == "a0141") {
          let returnValues = '';
          this.a0141s.forEach(data => {
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName;
            }
          })
          return returnValues;
        }
        // 干部状态
        if (col.property == "state") {
          let returnValues = '';
          this.states.forEach(data => {
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName;
            }
          })
          return returnValues;
        }
        // 全宗
        if (col.property == "orgName") {
          if (this.orgMap[row.orgName] == null) {
            return null;
          } else {
            return this.orgMap[row.orgName];
          }
        }
        return row[col.property]
      },
      // 获取当前登录用户
      getLoginUser() {
        queryLoginUser().then(response => {
          if (response.code == 200) {
            let user = response.data;
            this.userName = user.userName;
            this.topData.e01z717 = this.userName;
            // this.topData.approver =user.userName;
          }
        })
      },
      // 批次复选框事件
      handleSelectionChange(val) {
        console.log(val)
        this.checkboxDatas = val
        this.checkbox = val.map(item => item.id)
      },

      // 查看详情弹窗
      viewDetals(val) {
        console.log(val)
        this.dialogDetailsVisible = true
        params.transId = val.id
        queryTransferBillList(params)
          .then(response => {
            this.detailsData = response.rows
            console.log(this.detailsData)
            this.total = response.total
            this.loading = false
          })
          .catch(err => {
            this.$message({
              showClose: true,
              message: '查询失败，请联系管理员',
              type: 'error'
            })
          })
      },
      // 详情页单选
      handleSelection(val) {
        if (val.length >= 2) {
          // 删除索引为0的
          // console.log(val.splice(0,val.length-1),'被删除的')
          let arrays = val.splice(0, val.length - 1)
          arrays.forEach(row => {
            this.$refs.tableRefs.toggleRowSelection(row); //除了当前点击的，其他的全部取消选中
          })
        }
      },
      // 分页
      pageCurrent(val) {
        this.pageNum = val;
        params.pageNum=val;
        params.pageSize=this.pageSize;
        this.queryTransferBillList()
      },
      pageSizeS(val) {
        this.pageSize = val;
        params.pageNum=this.pageNum;
        params.pageSize=val;
        this.queryTransferBillList()
      },
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      /**
       updateTransferDetail() {
      this.dialogVisible = true
    }
       */
    }
  }
</script>
<style lang="scss" scoped>
  .app-transfer-paramenter {
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
  }

  .el-tree {
    background: none;
  }

  .select-opt {
    width: 100px;
    /deep/ .el-input__inner {
      background-color: #409eff;
      color: #fff;
    }
  }

  .input-w {
    width: 150px;
  }

  .el-input {
    width: 150px;
  }

  /deep/ .el-table__fixed-right-patch {
    background-color: #F8F8F9
  }

  // 表格高度
  /deep/ .el-table tbody tr:hover > td {
    background-color: #ecf5ff !important
  }

  /deep/ .el-table__body td {
    padding: 0;
    height: 41px;
  }

  // 设置列高
  /deep/ .el-table .el-table__header-wrapper th {
    height: 31px;
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

  .app-search {
    width: 80%;
    height: 100%;
    padding-left: 20px;
    padding-top: 15px;
    border-bottom: 1px solid #CCCCCC;
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
    background-color: #1890ff;
    cursor: pointer;
    right: 0;
    bottom: 0;
    margin: auto;
    top: 0;
    height: 200px;
    width: 8px;
    border-radius: 20px;
    position: absolute;
  }

  /deep/ .el-image__inner--center {
    transform:translate(-50%, 0%)
  }
</style>

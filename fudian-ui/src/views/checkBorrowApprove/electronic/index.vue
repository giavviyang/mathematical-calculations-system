<template>
  <!-- 电子档案查阅审批 -->
  <div class="app-electronic-access-approval">
    <el-tabs
      v-if="tabsShow"
      id="elTabs"
      v-model="activeName"
      :tab-position="tabPosition"
      style="min-height: calc(100vh - 86px);border: 1px solid #CCCCCC;"
      @tab-click="tabClick"
    >
      <el-tab-pane label="待审批" name="pendapproval">
        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px)">
          <el-row ref="searchRow"  :gutter="10"  style="margin:0 0 0 0;padding-left: 0px">
            <el-form
              v-if="showBorrowing"
              class="search-form"
              v-hasPermi="['checkBorrowApprove:electronic:search']"
              :model="borrowingParams0"
              :inline="true"
              ref="searchForm"
              label-width="110px"
            >
              <el-form-item label="申请人姓名" prop="deptName">
                <el-input
                  @keyup.enter.native="handleQueryBorrowing"
                  v-model="borrowingParams0.userName"
                  placeholder="请输入申请人姓名"
                  clearable
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item label="申请查阅时间起" prop="a0101">
                <el-date-picker
                  v-model="borrowingParams0.beginTime"
                  placeholder="请选择时间"
                  clearable
                  value-format="yyyy-MM-dd"
                  type="date"
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item label="申请查阅时间至" prop="a0101">
                <el-date-picker
                  v-model="borrowingParams0.endTime"
                  placeholder="请选择时间"
                  clearable
                  value-format="yyyy-MM-dd"
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item >
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryBorrowing">搜索
                </el-button>
                <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQueryBorrowing('borrowingParams')">重置</el-button> -->
              </el-form-item>
            </el-form>
          </el-row>
          <div class="btn">
            <el-button type="primary" size="mini" icon="el-icon-edit-outline" v-hasPermi="['checkBorrowApprove:electronic:approve']" @click="approvalProcess">审批处理</el-button>
            <el-button type="primary" size="mini" icon="el-icon-download" v-hasPermi="['checkBorrowApprove:electronic:export']" @click="handleExportBorrowing">导出</el-button>
          </div>

          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            empty-text=" "
            :height="tableHeight"
            size="mini"
            tooltip-effect="light"
            :header-cell-style="headClass"
            style="width: 100%"
            :data="ElectronicData0"
            @selection-change="handleSelectionChange"
            @row-click="handlerBorrowClickRow"
            ref="BorrowingData"
          >
            <el-table-column align="center" :resizable="false" type="selection" width="40" />
            <el-table-column
              label="序号"
              type="index"
              :index="customIndex0"
              align="center"
              show-overflow-tooltip
              :resizable="false"
              width="50">
            </el-table-column>
            <el-table-column
              align="center"
              v-for="(item, index) in BorrowingColumn"
              v-if="item.isCol"
              :key="index"
              :resizable="false"
              :label="item.label"
              :prop="item.prop"
              :width="item.width"
            />
            <el-table-column fixed="right" :resizable="false" wws="www" align="center" label="操作" width="100">
              <template slot-scope="scope">
                <el-button type="text" size="mini" v-hasPermi="['checkBorrowApprove:electronic:look']" @click="viewDetals(scope.row)">查看详情</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div  class="block">
            <el-pagination
              :page-sizes="[20, 50, 100, 200]"
              :page-size="pageSize0"
              :current-page="pageNum0"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total0"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-col>
      </el-tab-pane>
      <el-tab-pane label="已通过" name="passed">
        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px)">
          <el-row :gutter="10" ref="searchRow2" style="margin:0 0 0 0;padding-left: 0px">
            <el-form
              v-if="showBorrowing"
              v-hasPermi="['checkBorrowApprove:electronic:search']"
              ref="searchForm2"
              class="search-form"
              :model="borrowingParams1"
              label-width="110px"
              :inline="true"
            >
              <el-form-item label="申请人姓名" prop="deptName">
                <el-input
                  v-model="borrowingParams1.userName"
                  placeholder="请输入申请人姓名"
                  @keyup.enter.native="handleQueryBorrowing"
                  clearable
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item label="申请查阅时间起" prop="a0101">
                <el-date-picker
                  v-model="borrowingParams1.beginTime"
                  placeholder="请选择时间"
                  clearable
                  value-format="yyyy-MM-dd"
                  type="date"
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item label="申请查阅时间至" prop="a0101">
                <el-date-picker
                  v-model="borrowingParams1.endTime"
                  placeholder="请选择时间"
                  clearable
                  value-format="yyyy-MM-dd"
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item >
                <el-button type="primary" icon="el-icon-search" size="mini"  @click="handleQueryBorrowing">搜索
                </el-button>
                <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQueryBorrowing('borrowingParams')">重置</el-button> -->
              </el-form-item>
            </el-form>
          </el-row>
          <div class="btn">
            <el-button type="primary" icon="el-icon-receiving" v-hasPermi="['checkBorrowApprove:electronic:renew']" size="mini" @click="renew">到期续查
            </el-button>

            <el-button type="primary" icon="el-icon-download" size="mini" v-hasPermi="['checkBorrowApprove:electronic:export']" @click="handleExportBorrowing">导出
            </el-button>
          </div>
<!--          :default-sort="{ prop: 'date', order: 'descending' }"-->

          <el-table
            tooltip-effect="light"
            ref="BorrowingData2"
            stripe
            border
            empty-text=" "
            :height="tableHeight"
            size="mini"
            :header-cell-style="headClass"
            style="width: 100%"
            :data="ElectronicData1"
            @selection-change="handleSelectionChange"
            @row-click="handlerBorrowClickRow"


          >
            <!--            <el-table-column type="expand">-->

            <!--            </el-table-column>-->
            <!--                动态获取题名-->
            <!--            <el-table-column type="selection" width="55"  v-for=" i index in queryData"></el-table-column>-->
            <el-table-column align="center" :resizable="false" type="selection" width="40" />
            <el-table-column
              label="序号"
              type="index"
              :index="customIndex1"
              align="center"
              :resizable="false"
              width="50">
            </el-table-column>
            <el-table-column
              align="center"
              show-overflow-tooltip
              v-for="(item, index) in BorrowingColumn2"
              v-if="item.isCol"
              :key="index"
              :resizable="false"
              :label="item.label"
              :prop="item.prop"
              :width="item.width"
            />
            <el-table-column :resizable="false" align="center" fixed="right" label="操作" width="100">
              <template slot-scope="scope">
                <!--                @click="viewDetals(scope.row)"-->
                <el-button type="text" size="mini" v-hasPermi="['checkBorrowApprove:electronic:look']" @click="adoptViewDetals(scope.row)" >查看详情</el-button>
                <!--                            <el-button type="text" size="mini" @click="borrowingReturn(scope.row)">归还</el-button>-->
                <!--                            <el-button type="text" size="mini" @click="borrowingFeedback(scope.row)">反馈</el-button>-->
              </template>
            </el-table-column>
            <!--            <el-table-column fixed="right" label="操作" width="100">-->
            <!--              <template slot-scope="scope">-->
            <!--                <el-button type="text" size="mini" @click="borrowingReturn(scope.row)">归还</el-button>-->
            <!--                <el-button type="text" size="mini" @click="borrowingFeedback(scope.row)">反馈</el-button>-->
            <!--              </template>-->
            <!--            </el-table-column>-->
          </el-table>
          <div  class="block">
            <el-pagination
              :page-sizes="[20, 50, 100, 200]"
              :page-size="pageSize1"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total1"
              :current-page="pageNum1"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-col>
      </el-tab-pane>
      <el-tab-pane label="未通过" name="notpass">
        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px)">
          <el-row :gutter="10" ref="searchRow3" style="margin:0 0 0 0;padding-left: 0px">
            <el-form
              v-if="showBorrowing"
              class="search-form"
              v-hasPermi="['checkBorrowApprove:electronic:search']"
              :model="borrowingParams2"
              :inline="true"
              ref="searchForm3"
              label-width="110px"
            >
              <el-form-item label="申请人姓名" prop="deptName">
                <el-input
                  v-model="borrowingParams2.userName"
                  placeholder="请输入申请人姓名"
                  clearable
                  @keyup.enter.native="handleQueryBorrowing"
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item label="申请查阅时间起" prop="a0101">
                <el-date-picker
                  v-model="borrowingParams2.beginTime"
                  placeholder="请选择时间"
                  clearable
                  value-format="yyyy-MM-dd"
                  type="date"
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item label="申请查阅时间至" prop="a0101">
                <el-date-picker
                  v-model="borrowingParams2.endTime"
                  placeholder="请选择时间"
                  clearable
                  size="mini"
                  value-format="yyyy-MM-dd"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item >
                <el-button type="primary" icon="el-icon-search" size="mini"  @click="handleQueryBorrowing">搜索
                </el-button>
                <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQueryBorrowing('borrowingParams')">重置</el-button> -->
              </el-form-item>
            </el-form>
            <!--            <el-col :span="1.5">-->
            <!--              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddBorrowing">新增</el-button>-->
            <!--            </el-col>-->
            <!--            @click="handleUpdateBorrowing"-->
            <!--            <el-col :span="1.5">-->
            <!--              <el-button type="primary" icon="el-icon-edit" size="mini" >重新提交</el-button>-->
            <!--            </el-col>-->

<!--            <el-dropdown style="float: right; margin-right: 30px;">-->
<!--              <el-button icon="el-icon-menu" circle size="mini" />-->
<!--              <el-dropdown-menu style="padding: 10px">-->
<!--                <el-checkbox-group-->
<!--                  v-for="(item, index) in BorrowingColumn2"-->
<!--                  :key="index"-->
<!--                  v-model="BorrowingColumn2[index].isCol"-->
<!--                >-->
<!--                  <el-checkbox>{{ item.label }}</el-checkbox>-->
<!--                </el-checkbox-group>-->
<!--              </el-dropdown-menu>-->
<!--            </el-dropdown>-->
<!--            <el-col :span="1.5" style="float: right;">-->
<!--              <el-button-->
<!--                type="primary"-->
<!--                icon="el-icon-search"-->
<!--                circle-->
<!--                size="mini"-->
<!--                @click="function(){showBorrowing = !showBorrowing;tableHeight = showBorrowing?'73%':'85%'}"-->
<!--              />-->
<!--            </el-col>-->
          </el-row>
          <div class="btn">
            <el-button type="primary" icon="el-icon-download" size="mini" v-hasPermi="['checkBorrowApprove:electronic:export']" @click="handleExportBorrowing">导出
            </el-button>
          </div>
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            tooltip-effect="light"
            :height="tableHeight"
            style="width: 100%"
            size="mini"
            empty-text=" "
            :header-cell-style="headClass"
            :data="ElectronicData2"
            @selection-change="handleSelectionChange"
            @row-click="handlerBorrowClickRow"
            ref="BorrowingData3"
          >
            <!--            <el-table-column type="expand">-->

            <!--            </el-table-column>-->
            <!--                动态获取题名-->
            <!--            <el-table-column type="selection" width="55"  v-for=" i index in queryData"></el-table-column>-->
            <el-table-column align="center" :resizable="false" type="selection" width="40" />
            <el-table-column
              label="序号"
              type="index"
              :index="customIndex2"
              align="center"
              :resizable="false"
              show-overflow-tooltip
              width="50">
            </el-table-column>
            <el-table-column
              align="center"
              v-for="(item, index) in BorrowingColumn2"
              v-if="item.isCol"
              :key="index"
              :resizable="false"
              :label="item.label"
              show-overflow-tooltip
              :prop="item.prop"
              :width="item.width"
            />
            <el-table-column :resizable="false" align="center" fixed="right" label="操作" width="100">
              <template slot-scope="scope">
                <!--                @click="viewDetals(scope.row)"-->
                <el-button type="text" size="mini" v-hasPermi="['checkBorrowApprove:electronic:look']" @click="adoptViewDetals(scope.row)" >查看详情</el-button>
                <!--                            <el-button type="text" size="mini" @click="borrowingReturn(scope.row)">归还</el-button>-->
                <!--                            <el-button type="text" size="mini" @click="borrowingFeedback(scope.row)">反馈</el-button>-->
              </template>
            </el-table-column>
            <!--            <el-table-column fixed="right" label="操作" width="100">-->
            <!--              <template slot-scope="scope">-->
            <!--                <el-button type="text" size="mini" @click="borrowingReturn(scope.row)">归还</el-button>-->
            <!--                <el-button type="text" size="mini" @click="borrowingFeedback(scope.row)">反馈</el-button>-->
            <!--              </template>-->
            <!--            </el-table-column>-->
          </el-table>
          <div  class="block">
            <el-pagination
              :page-sizes="[20, 50, 100, 200]"
              :page-size="pageSize2"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total2"
              :current-page="pageNum2"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
          <el-dialog :append-to-body="true" title="借阅档案到期续借" :visible.sync="borrowingFormVisible">
            <div style="width: 80%;margin: 0 auto;overflow: hidden;">
              <el-form ref="ElectronicForm" :rules="rules" :model="ElectronicForm" :inline="true">
                <el-form-item label="借档对象姓名" prop="a0101" :label-width="formLabelWidth">
                  <el-input v-model="ElectronicForm.a0101" :disabled="true" class="inputW" autocomplete="off" />
                </el-form-item>
                <el-form-item label="职务" prop="duty" :label-width="formLabelWidth">
                  <el-input v-model="ElectronicForm.duty" :disabled="true" class="inputW" autocomplete="off" />
                </el-form-item>
                <el-form-item label="政治面貌" prop="politcy" :label-width="formLabelWidth">
                  <el-input v-model="ElectronicForm.politcy" :disabled="true" class="inputW" autocomplete="off" />
                </el-form-item>
                <el-form-item label="单位名称" prop="deptName" :label-width="formLabelWidth">
                  <el-input v-model="ElectronicForm.deptName" :disabled="true" class="inputW" autocomplete="off" />
                </el-form-item>
                <el-form-item label="借阅时间" prop="e01z804" :label-width="formLabelWidth">
                  <el-date-picker
                    v-model="ElectronicForm.e01z804"
                    :disabled="true"
                    class="inputW"
                    autocomplete="off"
                    type="date"
                    placeholder="选择日期"
                  />
                </el-form-item>
                <el-form-item label="结束时间" prop="e01z805" :label-width="formLabelWidth">
                  <el-date-picker
                    v-model="ElectronicForm.e01z805"
                    class="inputW"
                    autocomplete="off"
                    type="date"
                    placeholder="选择日期"
                  />
                </el-form-item>
                <el-form-item label="借档内容" prop="e01z111a" :label-width="formLabelWidth">
                  <el-input v-model="ElectronicForm.e01z111a" :disabled="true" class="inputW" autocomplete="off" />
                </el-form-item>
                <el-form-item label="借阅理由" prop="e01zc21" :label-width="formLabelWidth">
                  <el-input v-model="ElectronicForm.e01zc21" :disabled="true" class="inputW" autocomplete="off" />
                </el-form-item>
                <el-form-item label="反馈" prop="feedback" :label-width="formLabelWidth">
                  <el-input
                    v-model="ElectronicForm.feedback"
                    :disabled="true"
                    type="textarea"
                    class="inputW"
                    autocomplete="off"
                    resize="none"
                  />
                </el-form-item>
                <el-form-item label="备注" prop="e01z831" :label-width="formLabelWidth">
                  <el-input v-model="ElectronicForm.e01z831" type="textarea" class="inputW" autocomplete="off" resize="none"/>
                </el-form-item>
              </el-form>
            </div>
            <div slot="footer" class="dialog-footer">
              <el-button @click="borrowingFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="BorrowFormSave('ElectronicForm')">确 定</el-button>
            </div>
          </el-dialog>
        </el-col>
      </el-tab-pane>
    </el-tabs >
    <!--  临时查阅详情页  -->
    <el-dialog title="查阅详情" :append-to-body="true"  :close-on-click-modal="false" width="90%" class=" dialog-style dialog-basic" :visible.sync="viewDetalsVasable">
      <div class="form-basic">审批信息</div>
      <el-form ref="form2" :rules="approvalFormRules" :model="approvalForm" style="padding-top: 7px"  :inline="true"  label-width="120px">
        <el-form-item prop="approver" label="审批人">
          <el-input  size="mini" placeholder="请输入内容" v-model="approvalForm.approver"/>
        </el-form-item>
        <el-form-item prop="personCharge" label="经办人">
          <el-input size="mini" placeholder="请输入内容" v-model="approvalForm.personCharge" />
        </el-form-item>
        <el-form-item prop="approverOpinion" label="审批意见">
          <el-input size="mini" placeholder="请输入内容" v-model="approvalForm.approverOpinion" />
        </el-form-item>
        <!--              </el-col>-->
        <!--        <el-col :span="12">-->
        <!--          <el-form-item prop="temperature" label="审批结果">-->
        <!--            <el-input />-->
        <!--          </el-form-item>-->
      </el-form>
      <el-form ref="form"  :inline="true"  label-width="120px">
        <div class="form-basic">申请内容</div>
      </el-form>
      <!--      class="tree"-->
      <div class="t-ctn"  style="width:100%; overflow-x: auto;">
        <div class="s-ctn" style="white-space: nowrap;height: 300px">
          <div v-for="(tree,index) in e01zcTree" :class="divw">
            <el-scrollbar wrap-class="scrollbar-wrapper" style="height:100%;width: 100%" class="scrollbar-border">
<!--              <div style="" class="tree tree-style">-->
                <!--            @node-click="nodeClick"-->
                <!--            :default-expand-all="true"-->
                <!--            v-for="tree in e01zcTree"-->
<!--                :default-checked-keys="getDefaultCheck(tree)"-->
                <template>
                  <el-tree
                    :ref="tree[0].id"
                    :show-checkbox="true"
                    :data="tree"

                    icon-class="el-icon-folder-opened"
                    node-key="id"
                    highlight-current
                    :expand-on-click-node="false"
                    :props="defaultProps"
                    :default-expand-all="true"
                  />
                </template>
<!--              </div>-->
            </el-scrollbar>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <!--      <el-button size="mini" @click="viewDetalsVasable = false ">关闭</el-button>-->
        <el-button size="mini" type="primary" @click="agree">同意</el-button>
        <el-button size="mini" type="primary" @click="noAgree">不同意</el-button>
      </div>
    </el-dialog>
    <!--  批量审批处理  -->
    <el-dialog title="审批处理" :append-to-body="true"  :close-on-click-modal="false"  width="50%" class=" dialog-style dialog-basic" :visible.sync="viewDetalsVasable2">
      <div class="form-basic">审批信息</div>
      <el-row >
        <el-form ref="form" :model="approvalForm" :rules="approvalFormRules" label-width="120px">
          <el-col :span="10">
            <el-form-item :span="12" prop="approver" label="审批人">
              <el-input size="mini" placeholder="请输入内容" v-model="approvalForm.approver"/>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item :span="12" prop="personCharge" label="经办人">
              <el-input size="mini" placeholder="请输入内容" v-model="approvalForm.personCharge" />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item :span="12" prop="approverOpinion" label="审批意见">
              <el-input size="mini" placeholder="请输入内容" v-model="approvalForm.approverOpinion" />
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <!--        <el-button size="mini" @click="viewDetalsVasable2 = false ">关闭</el-button>-->
        <el-button size="mini" type="primary" @click="agree2">同意</el-button>
        <el-button size="mini" type="primary" @click="noAgree2">不同意</el-button>
      </div>
    </el-dialog>
    <!--  已通过查看详情-->
    <el-dialog title="查阅详情" :append-to-body="true"  :close-on-click-modal="false" width="90%" class=" dialog-style dialog-basic" :visible.sync="viewDetalsVasable3">
      <div class="form-basic">申请信息</div>
      <el-col style="margin-left: 2%" :span="1.5"><el-button type="primary" icon="el-icon-download" @click="exportx" size="mini" >导出</el-button></el-col>
      <!--      <el-col  :span="24" :xs="24" style="margin: 0;padding: 0;border: 1px solid #cccccc;min-height: calc(100vh - 86px)">-->
      <el-table :data="approvalData"
                border
                style="width: 95%; margin-left: 2%;margin-top: 50px;margin-bottom: 20px"
                height="400px"
                stripe
                size="mini"
                @selection-change="approvalDataSelect"
                tooltip-effect="light"
                :header-cell-style="headClass"
                ref="approvalData" >
        <el-table-column type="selection" width="40" />
        <el-table-column  type="index" width="50" align="center" label="序号" />
        <el-table-column prop="a0101" label="被申请人姓名" align="center"  />
        <el-table-column prop="deptName" label="被申请人所在部门" align="center"  />
        <el-table-column prop="e01z111a" :show-overflow-tooltip="true" label="申请内容" align="center"  />
        <el-table-column prop="adoptE01z1Msg" v-if="archivesType == 1" :show-overflow-tooltip="true"
                         :formatter="function(row, column, cellValue, index){
                           if(row.adoptE01z1Msg==null||row.adoptE01z1Msg==''){
                             return row.e01z111a
                           }else{
                             return cellValue;
                           }
                         }"
                         label="申请通过内容" align="center"  />
        <el-table-column prop="approvTime" min-width="120px" label="申请时间" align="center"   />
        <el-table-column prop="endDate" min-width="120px" label="申请查阅截止时间至" align="center"   />
        <!--          <el-table-column  label="操作" align="center" width="200" >-->
        <!--            <template slot-scope="scope">-->
        <!--              &lt;!&ndash;            @click="wholeConsult(scope.row)"&ndash;&gt;-->
        <!--              <el-button type="text" size="mini" @click="look(scope.row)" >查看档案材料</el-button>-->
        <!--            </template>-->
        <!--          </el-table-column>-->
      </el-table>

      <!--      </el-col>-->

      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="viewDetalsVasable3 = false ">关闭</el-button>
        <!--        <el-button size="mini" type="primary" @click="agree2">同意</el-button>-->
        <!--        <el-button size="mini" type="primary" @click="noAgree2">不同意</el-button>-->

      </div>
    </el-dialog>

    <!--    到期续查-->
    <el-dialog title="到期续查" :append-to-body="true"  :close-on-click-modal="false" width="30%" class="dialog-style dialog-basic" :visible.sync="renewVisible">
      <div class="dialog-state">
        <span class="state">填写查阅截止时间</span>
        <el-form ref="renewform" :rules="renewformRules" :model="renewForm"  style="width:90%;margin-top:11%;margin-left: 5%"  label-width="120px">
        <el-form-item  prop="temperature" label="查阅截止时间">
          <el-date-picker
            :disabled="true"
            style="width:100%"
            v-model="renewForm.ldate"
            type="datetime"
            size="mini"
            placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="12:00:00">
          </el-date-picker>
        </el-form-item>

        <el-form-item prop="date" label="续查时间至">
          <el-date-picker
            v-model="renewForm.date"
            type="datetime"
            size="mini"
            style="width:100%"
            placeholder="选择日期时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="12:00:00">
          </el-date-picker>
        </el-form-item>

        </el-form>
      </div>
<!--      <div class="dialog-state">-->
<!--            <span class="state">填写续借截止日期</span>-->
<!--            <el-form ref="form" :model="approvalForm" style="padding:35px 0 0 0"   :inline="true"  >-->
<!--              <el-form-item prop="oldEndTime" label="借阅截止日期至">-->
<!--                <el-date-picker   style="width: 200px" v-model="approvalForm.oldEndTime"  type="date" placeholder="请选择日期"   /> -->
<!--              </el-form-item>-->
<!--              <el-form-item prop="endTime" label="续借截止日期至">-->
<!--                <el-date-picker  size="mini"  style="width: 200px" v-model="approvalForm.endTime"  type="date" placeholder="请选择日期"   />-->
<!--              </el-form-item>-->
<!--            </el-form>-->
<!--            </div>-->


      <div slot="footer" class="dialog-footer">
        <!--        <el-button size="mini" @click="viewDetalsVasable2 = false ">关闭</el-button>-->
        <el-button size="mini" type="primary" @click="renewSave" >保存</el-button>


      </div>
    </el-dialog>

  </div>

  <!-- 添加申请记录 -->

</template>
<script>
  import {conditionSearchArchives,queryE01zcRelaTreeByE01zcId} from '@/api/usual/usage/borrow'
  import {queryDictionaryByType,queryDictionaryByTypes,queryE01z1TreeGrid} from '@/api/usual/receive'
  import {e01zcApproval,getApprovals} from '@/api/usual/usage/electronic-access-approval'
  import { queryE01zcRelaData,renew} from '@/api/usual/provisonalInspect'
  import { queryA01List } from '@/api/usual/transfer'
  // import { queryDictionaryByType,queryE01z1TreeGrid} from '@/api/usual/receive'
  // import request from '@/utils/request'
  import {downLoad,downLoadPost} from '@/downLoad/filedownload'
  const datalist = []
  export default {
    watch: {
      ElectronicData:{
        handler(newName, oldName) {
          this.$nextTick(()=>{
            this.$refs.BorrowingData.doLayout();
            this.$refs.BorrowingData2.doLayout();
            this.$refs.BorrowingData3.doLayout();
          })
        },
        deep:true,
        // immediate:true
      },
      approvalData:{
        handler(newName, oldName) {
          // console.log(_this.$refs.BorrowingData);
          // let _this = this;
          this.$nextTick(()=>{
            this.$refs.approvalData.doLayout();
            // this.$refs.BorrowingData2.doLayout();
            // this.$refs.BorrowingData3.doLayout();
          })
        },
        deep:true,
        // immediate:true
      },
    },
    data() {
      return {
        approvalFormRules:{
          approver:[
            { required: true, message: '请输入审批人', trigger: 'blur' },
            { min: 0, max: 18, message: '审批人不得超过18个汉字', trigger: 'blur' }
          ],
          personCharge:[
            { required: true, message: '请输入经办人', trigger: 'blur' },
            { min: 0, max: 18, message: '经办人不得超过18个汉字', trigger: 'blur' }
          ],
          approverOpinion:[
            { min: 0, max: 300, message: '审批意见不得超过300个汉字', trigger: 'blur' }
          ]

        },
        renewformRules:{
          date:[
            { required: true, message: '请选择续查时间', trigger: 'blur' },
          ]
        },
        approvalData:[],
        renewForm:{
          date:'',
        },

        ElectronicData0:[],
        ElectronicData1:[],
        ElectronicData2:[],
        total0: 0,
        total1: 0,
        total2: 0,
        pageNum0: 1,
        pageSize0: 20,
        pageNum1: 1,
        pageSize1: 20,
        pageNum2: 1,
        pageSize2: 20,

        //form
        //搜索框
        borrowingParams0:{
          startTime:"",
          endTime:"",
        },
        borrowingParams1:{
          startTime:"",
          endTime:"",
        },
        borrowingParams2:{
          startTime:"",
          endTime:"",
        },

        renewVisible:false,
        // dialogVisible1:false,
        // dialogVisible2:false,
        divw:'divw1',
        leftData:[],
        applicationData:[],
        // cadresText:"",
        // personnelText:"",
        // form:{},
        // apply:false,
        viewDetalsVasable2:false,
        viewDetalsVasable3:false,
        e01zcId:'',
        approvalForm:{
          approver:'',
          approverDate:'',
          approverTime:'',
          approverOpinion:'',
          personCharge:''
        },
        e01zcTree:[],
        viewDetalsVasable :false,
        tableHeight:document.body.clientHeight - (84 + 70 + this.searchRowHeight) + "px",
        // 材料树 数据
        materialData: [],
        // 树名称字段
        materialProps: {
          children: 'children',
          label: 'e01z111a',
          id: 'id',
          disabled: false
        },
        // 材料窗口
        materialWindow: false,
        // 部门干部搜索 传参
        searchText: '',
        searchChek: [],
        a0104s: [],
        data: {
          id: '00e276aff7524e0e8926fbf624dbc8e1',
          e01z8Id: '2aba308a48ca43f8b3264430eeaf30bb',
          feedback: '展示测试'
        },
        data1: {
          id: '1',
          remind: '2'
        },
        e01z1data1:[],
        e01z1data2:[],
        formLabelWidth: '130px',
        // 分页参数
        total: 0,
        total1: 0,
        total2: 0,
        pageNum: 1,
        pageSize: 20,
        options: [{
          value: '1',
          label: '查阅电子档案'
        }, {
          value: '2',
          label: '查阅实体档案'
        }, {
          value: '3',
          label: '借阅档案'
        }],
        // 右侧搜索
        formInline: {
          deptName: '',
          a0101: ''
        },
        // 搜索结果
        searchTableData: [],
        // 电子权限
        isElectronic: true,
        // 实体权限
        isEntity: false,
        disabled: false,
        isBorrowing: false,
        addForm: {
          a0101: '',
          a01Id: '',
          duty: '',
          deptName: '',
          politcy: '',
          e01z801: '',
          e01z804: '',
          e01z805: '',
          e01z821: '',
          archivesType: 1,
          delivery: false,
          priType: ['1'],
          e01z831: ''
          // workflow: ''
        },
        queryData: [],
        formShow: false,
        tabsShow: false,
        activeName: 'pendapproval',
        // tab 显示布局状态
        tabPosition: 'left',
        /* 遮罩 */
        loading: true,
        /* 电子档案参数*/
        // 显示隐藏
        // 电子档案显示初始属性
        showEntity: false,
        // 查询电子档案 条件
        queryParams: {
          a0101: '',
          deptName: '',
          pageSize: 20,
          pageNum: 1,
          type: '1'
        },
        //电子档案查阅反馈
        ElectronicFeedVisible: false,
        // 电子档案到期续查
        ElectronicFormVisible: false,
        //实体档案查阅反馈
        EntityFeedVisible:false,
        //档案借阅反馈
        BorrowFeedVisible:false,
        ElectronicForm: {
          a0101: '',
          duty: '',
          politcy: '',
          deptName: '',
          e01z111a: '',
          e01z804: '',
          e01z805: '',
          e01zc21: '',
          feedback: '',
          e01z831: ''
        },
        rules: {
          feedback: [
            { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
          ],
          a0101: [
            { required: true, message: '请输入名字', trigger: 'blur' },
            { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
          ],
          // duty: [
          //   { required: true, message: '请输入职务', trigger: 'blur' }
          // ],
          // politcy: [
          //   { required: true, message: '请输入政治面貌', trigger: 'blur' }
          // ],
          deptName: [
            { required: true, message: '请输入单位名称', trigger: 'blur' }
          ],
          e01z111a: [
            { required: true, message: '请输入查档内容', trigger: 'blur' }
          ],
          e01z804: [
            {  required: true, message: '请输入查档时间', trigger: 'blur' }
          ],
          e01z805: [
            { required: true, message: '请输入结束时间', trigger: 'blur' }
          ]
        },
        // 电子档案 批次复选框
        electronicCheck: [],
        // 电子档案控制题名显示隐藏
        // 申请人列名
        ElectronicColumn: [
          { label: 'id', isCol: false, prop: 'id' },
          { label: '隶属全宗', isCol: false, prop: 'orgId' },
          { label: '单位名称', isCol: false, prop: 'e01z824a' },
          { label: '人员信息id', isCol: false, prop: 'userId' },
          { label: '姓名', isCol: true, prop: 'userName' },
          // { label: '身份证', isCol: false, prop: 'cardId' },
          { label: '职务', isCol: true, prop: 'duty' },
          { label: '政治面貌', isCol: true, prop: 'politcy' },
          { label: '申请时间', isCol: true, prop: 'approvTime' }
        ],
        // 申请人数据
        ElectronicData: [],
        // 查档对象人数据
        ElectronicChildrenData: [],
        // 显示隐藏
        // 电子档案显示初始属性
        // 查询电子档案 条件
        // queryParams: {
        //   a0101: '',
        //   deptName: ''
        // },
        // // 查询电子档案 上部重置按钮
        // resetQueryElectronic() {},
        // // 新增

        // 实体 column
        // 申请人列名
        EntityColumn: [
          { label: 'id', isCol: false, prop: 'id' },
          { label: '隶属全宗', isCol: false, prop: 'orgId' },
          { label: '单位名称', isCol: false, prop: 'e01z824a' },
          { label: '人员信息id', isCol: false, prop: 'userId' },
          { label: '姓名', isCol: true, prop: 'userName' },
          // { label: '身份证', isCol: false, prop: 'cardId' },
          { label: '职务', isCol: true, prop: 'duty' },
          { label: '政治面貌', isCol: true, prop: 'politcy' },
          { label: '备注', isCol: true, prop: 'e01z831' },
          { label: '申请时间', isCol: true, prop: 'approvTime' }
        ],
        // 申请人数据
        EntityData: [],
        // 查档对象人列名
        EntityChildrenColumn: [
          { label: 'id', prop: 'id' },
          { label: 'e01z8Id', prop: 'e01z8Id' },
          { label: '查档对象姓名', prop: 'a0101' },
          { label: '查档对象id', prop: 'a01Id' },
          { label: '单位id', isCol: false, prop: 'deptId' },
          { label: '单位', isCol: false, prop: 'deptName' },
          { label: '查档内容', prop: 'e01z111a' },
          { label: '经办人', isCol: true, prop: 'e01z817' },
          { label: '查阅理由', isCol: true, prop: 'e01z821' },
          { label: '申请权限', isCol: true, prop: 'priType' },
          // { label: '工作流名称', isCol: true, prop: 'workflow' },
          { label: '批准人', isCol: true, prop: 'e01z801' },
          { label: '审批意见', isCol: true, prop: 'flag' },
          { label: '反馈', prop: 'feedback' },
          { label: '备注', prop: 'e01z831' }
        ],
        // 查档对象人数据
        EntityChildrenData: [],
        // 实体档案显示初始属性
        showElectronic: false,
        // 查询实体档案 条件
        entityParams: {
          a0101: '',
          deptName: '',
          pageSize: 20,
          pageNum: 1,
          type: '2'
        },
        entityFormVisible: false,
        /*   借阅档案参数*/
        // 借阅 column
        // 申请人列名
        BorrowingColumn: [
          { label: 'id',  isCol: false, prop: 'id' },
          { label: '隶属全宗', isCol: false, prop: 'orgId' },
          { label: '单位名称', isCol: false, prop: 'e01z824a' },
          { label: '人员信息id', isCol: false, prop: 'userId' },
          { label: '申请人姓名', width:100, isCol: true, prop: 'userName' },
          { label: '申请人证件号',width:150, isCol: true, prop: 'userCard' },
          { label: '申请查阅理由', width:100,isCol: true, prop: 'applicationReasons' },
          { label: '申请人所在单位',width:150, isCol: true, prop: 'e01z824a' },
          { label: '申请人职务',width:100, isCol: true, prop: 'duty' },
          { label: '申请查阅档案卷数', width:150,isCol: true, prop: 'relaNum' },
          { label: '申请时间',width:150, isCol: true, prop: 'approvTime' },
          { label: '申请查阅截止时间至',width:150, isCol: true, prop: 'endDate' },
          { label: '申请人电话',width:100, isCol: true, prop: 'userPhone' },
          { label: '申请类型',width:100, isCol: true, prop: 'type' },
          { label: '打印内容',width:200, isCol: true, prop: 'printRemark' },
          { label: '备注', width:200,isCol: true, prop: 'e01z831' },
        ],

        BorrowingColumn2: [
          { label: 'id', isCol: false, prop: 'id' },
          { label: '隶属全宗', isCol: false, prop: 'orgId' },
          { label: '单位名称', isCol: false, prop: 'e01z824a' },
          { label: '人员信息id', isCol: false, prop: 'userId' },
          { label: '申请人姓名',width:100, isCol: true, prop: 'userName' },
          { label: '申请人证件号', width:150,isCol: true, prop: 'userCard' },
          { label: '申请查阅理由',width:100, isCol: true, prop: 'applicationReasons' },
          { label: '申请人所在单位',width:150, isCol: true, prop: 'e01z824a' },
          { label: '申请人电话',width:100, isCol: true, prop: 'userPhone' },
          { label: '申请人职务',width:100, isCol: true, prop: 'duty' },
          { label: '申请查阅档案卷数 ',width:150, isCol: true, prop: 'relaNum' },
          { label: '申请时间',width:150, isCol: true, prop: 'approvTime' },
          { label: '申请查阅截止时间至', width:150,isCol: true, prop: 'endDate' },
          { label: '申请类型',width:100, isCol: true, prop: 'type' },
          { label: '查阅状态',width:100, isCol: true, prop: 'lookType' },
          { label: '审批人',width:100, isCol: true, prop: 'approver' },
          { label: '经办人', width:100,isCol: true, prop: 'personCharge' },
          { label: '审批时间',width:150, isCol: true, prop: 'approverDate' },
          { label: '审批意见',width:200, isCol: true, prop: 'approverOpinion' },
          { label: '打印内容', width:200,isCol: true, prop: 'printRemark' },
          { label: '备注', width:200,isCol: true, prop: 'e01z831' },
        ],
        // 申请人数据
        BorrowingData: [],
        borrowingFormVisible: false,
        // 查档对象人列名
        BorrowingChildrenColumn: [
          // {label: 'id', prop: 'id'},
          // {label: 'e01z9Id', isCol: false, prop: 'e01z9Id'},
          { label: '借档对象姓名', prop: 'a0101' },
          // {label: '借档对象id', prop: 'a01Id'},
          { label: '单位', isCol: false, prop: 'deptName' },
          { label: '职务', isCol: false, prop: 'duty' },
          { label: '政治面貌', isCol: false, prop: 'politcy' },
          { label: '借阅时间', isCol: true, prop: 'e01z804' },
          { label: '结束时间', isCol: true, prop: 'e01z805' },
          { label: '经办人', isCol: true, prop: 'e01z817' },
          { label: '归还日期', isCol: true, prop: 'e01z806' },
          // { label: '归还人', isCol: true, prop: 'e01z806' },
          { label: '借阅理由', isCol: true, prop: 'e01z821' },
          { label: '申请权限', isCol: true, prop: 'priType' },
          // { label: '工作流名称', isCol: true, prop: 'workflow' },
          { label: '批准人', isCol: true, prop: 'e01z801' },
          { label: '审批意见', isCol: true, prop: 'flag' },
          { label: '备注', isCol: true, prop: 'e01z831' },
          { label: '借档内容', prop: 'e01z111a' },
          { label: '材料份数', prop: 'materials ' },
          { label: '总页数', prop: 'totalPages' },
          { label: '涂改内容', prop: 'modified' },
          { label: '是否破损', prop: 'isBreak' },
          { label: '破损内容', prop: 'broken' },
          { label: '是否遗失', prop: 'isLose' },
          { label: '具体情况说明', prop: 'instructions' },
          { label: '是否已归还', prop: 'remind' },
          { label: '反馈', prop: 'feedback' }
        ],
        // 查档对象人数据
        BorrowingChildrenData: [],
        showBorrowing: true,
        // 借阅搜索参数
        borrowingParams: {
          a0101: '',
          deptName: '',
          pageSize: 20,
          pageNum: 1,
          type: '3'
        },
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        archivesType: 0,

        dpbutton:false,
        a0117s: [],
        a0141s: [],
        sysDictionaryMap:{},
        searchRowHeight :'',
        searchFormHeight:'',
        searchForm:'searchForm',
        searchRow:'searchRow',
        queryBorrowingParams:{},
        xselect:[]
      }

    },
    mounted() {
      this.archivesaFunction(true)
      this.initShowNum()
      this.$nextTick(()=>{
        console.log(this.$refs[this.searchRow].$el.offsetHeight)
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = this.tableHeight = document.body.clientHeight - (84 + 100 + this.searchRowHeight) + "px"
      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
          this.tableHeight = document.body.clientHeight - (84 + 100 + this.searchRowHeight) + "px"
        })();
      };
    },
    //字典表获取
    created() {
      this.initColumnMap();
    },
    methods: {
      //自定义序号
      customIndex0(index){
        return (this.pageNum0-1)*(this.pageSize0)+(index+1);
      },
      customIndex1(index){
        return (this.pageNum1-1)*(this.pageSize1)+(index+1);
      },
      customIndex2(index){
        return (this.pageNum2-1)*(this.pageSize2)+(index+1);
      },
      approvalDataSelect(select){
        this.xselect = select;
      },
      exportx(){
        let data = {
          "colTitle": {
            "a0101": "被申请干部姓名",
            // "number": "干部编号",
            "deptName": "被申请人所在部门",
            "deptName": "所属部门",
            "e01z111a": "申请内容",
            "adoptE01z1Msg": "申请通过内容",
            "approvTime":"申请时间",
            "endDate":"申请查阅截止时间至"

          },
          "colWidth": {
            "adoptE01z1Msg" : 25,
            "approvTime":25,
            "endDate":25
          },
          "dataList": [],
          "tableTitle": "临时查阅详情表",
          "sheetTitle": "临时查阅详情表",
          "downLoadName": "临时查阅详情表"
        }
        if (this.xselect.length == 0) {
          let checkboxDatas = this.approvalData;
          checkboxDatas.map(item=>{
            if(item.adoptE01z1Msg==null||item.adoptE01z1Msg==''){
              item.adoptE01z1Msg = item.e01z111a;
            }else{
              item.adoptE01z1Msg = item.adoptE01z1Msg;
            }
          })
          data.dataList = checkboxDatas
          downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data),this)
        }else{
          this.$confirm('是否导出选中的查阅数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let checkboxDatas = this.xselect;
            checkboxDatas.map(item=>{
              if(item.adoptE01z1Msg==null||item.adoptE01z1Msg==''){
                item.adoptE01z1Msg = item.e01z111a;
              }else{
                item.adoptE01z1Msg = item.adoptE01z1Msg;
              }
            })
            data.dataList = checkboxDatas
            downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data),this)
          })
        }
      },
      renewSave(){
        this.$refs.renewform.validate(valid=> {
          if (!valid) {
            return;
          }
          let params = {
            status:1,
            endDate:this.renewForm.date,
            type:'到期续查',
            id:this.electronicCheck[0].id
          }
          renew(params).then(response=>{
            if(response.code==200){
              this.$message({
                message:'操作成功',
                type:'success'
              })
              this.renewVisible = false;
              this.renewForm.date = '';
              this.archivesaFunction();
            }
          })
        })

      },
      //到期续借
      renew(){
        let _this = this;
        if(this.electronicCheck.length == 0){
          this.$message({
            message:"请勾选数据后审批",
            type:'warning'
          })
          return;
        }
        if(this.electronicCheck.length > 1){
          this.$message({
            message:"请勾选数据后审批",
            type:'warning'
          })
          return;
        }
        this.renewForm.ldate = this.electronicCheck[0].endDate;
        this.renewVisible = true;
      },
      //添加查阅记录
      addApprovalRecord(){
        // console.log(123)
        // this.apply = true;
        // this.tabsShow = false;
      },
      //已通过点击查看详情
      adoptViewDetals(row){
        this.viewDetalsVasable3 = true;
        let _this = this;
        queryE01zcRelaData(row.id).then(response2 => {
          console.log(response2);
          _this.approvalData = response2.data.data;
          _this.approvalData.map(item=>{
            item.endDate = response2.data.e01zc.endDate;
            item.approvTime = response2.data.e01zc.approvTime
          })
        })

      },
      //点击同意
      agree(){
        this.$refs.form2.validate((valid)=>{
            if(!valid){
              return;
            }

          let jsonData = {
            approver:this.approvalForm.approver,
            approverDate:'',
            approverOpinion:this.approvalForm.approverOpinion,
            personCharge:this.approvalForm.personCharge,
            e01zcId:this.e01zcId
          };
          const dateDate = this.approvalForm.approverDate
          const dateTime = this.approvalForm.approverTime
          jsonData.approverDate = dateDate.getFullYear() + '-' + (dateDate.getMonth() + 1) + '-' + dateDate.getDate() + ' ' + dateTime.getHours() + ':' + dateTime.getMinutes() + ':' + dateTime.getSeconds()
          let _this = this;

          let agreeNodes = [];
          _this.e01zcTree.map(item=>{
            let temp = item[0];
            let agreeNode = {};
            agreeNode.id = temp.data.id;
            let nodes = _this.$refs[temp.id][0].getCheckedNodes(false,false);
            if(nodes.length>0&&nodes[0].id.split("-").length==1){
              //选择的为全部 则不添加

            }else{
              let e01z1Ids = [];
              let e01z1Name = [];
              nodes.map(item1=>{
                if(item1.check == 'check'){
                  e01z1Ids.push(item1.data.id);
                  e01z1Name.push(item1.data.e01z111a);
                }
              })
              agreeNode.e01z1Id = e01z1Ids.join(",");
              agreeNode.e01z1Name = e01z1Name.join(",");
              agreeNodes.push(agreeNode);
            }
          })
          jsonData.agreeNodes = agreeNodes;
          jsonData.type = 1
          console.log(jsonData);
          e01zcApproval(JSON.stringify(jsonData)).then(response=>{
            if(response.code == 200){
              this.$message({
                message:'操作成功',
                type:'success'
              })
              this.$refs.form2.resetFields();

              this.viewDetalsVasable = false;
              this.archivesaFunction();
              this.approvalForm.approverOpinion = '';
            }
          })
        })

        // viewDetalsVasable = false;
      },
      //点击不同意
      noAgree(){
        this.$refs.form2.validate((valid)=>{
            if(!valid){
              return;
            }
          let jsonData = {
            approver:this.approvalForm.approver,
            approverDate:'',
            approverOpinion:this.approvalForm.approverOpinion,
            personCharge:this.approvalForm.personCharge,
            e01zcId:this.e01zcId
          };
          const dateDate = this.approvalForm.approverDate
          const dateTime = this.approvalForm.approverTime
          jsonData.approverDate = dateDate.getFullYear() + '-' + (dateDate.getMonth() + 1) + '-' + dateDate.getDate() + ' ' + dateTime.getHours() + ':' + dateTime.getMinutes() + ':' + dateTime.getSeconds()
          jsonData.type = -1
          e01zcApproval(JSON.stringify(jsonData)).then(response=>{
            if(response.code == 200){
              this.$message({
                message:'操作成功',
                type:'success'
              })
              this.$refs.form2.resetFields();
              this.viewDetalsVasable = false;
              this.archivesaFunction();
              this.approvalForm.approverOpinion = '';
            }
          })
        })

      },
      //点击同意
      agree2(){
        this.$refs.form.validate((valid)=>{
            if(!valid){
              return;
            }
          let jsonData = {
            approver:this.approvalForm.approver,
            approverDate:'',
            approverOpinion:this.approvalForm.approverOpinion,
            personCharge:this.approvalForm.personCharge,
            e01zcId:'',
            type:1,
            agreeNodes:'[]'
          };
          let e01zcId = [];
          const dateDate = this.approvalForm.approverDate
          const dateTime = this.approvalForm.approverTime
          jsonData.approverDate = dateDate.getFullYear() + '-' + (dateDate.getMonth() + 1) + '-' + dateDate.getDate() + ' ' + dateTime.getHours() + ':' + dateTime.getMinutes() + ':' + dateTime.getSeconds()
          this.electronicCheck.map(item=>{
            e01zcId.push(item.id);
          })
          jsonData.e01zcId = e01zcId.join(",");
          e01zcApproval(JSON.stringify(jsonData)).then(response=>{
            if(response.code == 200){
              this.$message({
                message:'操作成功',
                type:'success'
              })
              this.$refs.form.resetFields();
              this.viewDetalsVasable2 = false;
              this.archivesaFunction();
              this.approvalForm.approverOpinion = '';
            }
          })
        })

        // viewDetalsVasable = false;
      },
      //点击不同意
      noAgree2(){
        this.$refs.form.validate((valid)=>{
            if(!valid){
              return;
            }
          let jsonData = {
            approver:this.approvalForm.approver,
            approverDate:'',
            approverOpinion:this.approvalForm.approverOpinion,
            personCharge:this.approvalForm.personCharge,
            e01zcId:'',
            type:-1,
            agreeNodes:'[]'
          };
          let e01zcId = [];
          const dateDate = this.approvalForm.approverDate
          const dateTime = this.approvalForm.approverTime
          jsonData.approverDate = dateDate.getFullYear() + '-' + (dateDate.getMonth() + 1) + '-' + dateDate.getDate() + ' ' + dateTime.getHours() + ':' + dateTime.getMinutes() + ':' + dateTime.getSeconds()
          this.electronicCheck.map(item=>{
            e01zcId.push(item.id);
          })
          jsonData.e01zcId = e01zcId.join(",");
          e01zcApproval(JSON.stringify(jsonData)).then(response=>{
            if(response.code == 200){
              this.$message({
                message:'操作成功',
                type:'success'
              })
              this.$refs.form.resetFields();
              this.viewDetalsVasable2 = false;
              this.archivesaFunction();
              this.approvalForm.approverOpinion = '';
            }
          })
        })

      },
      getDefaultCheck(tree){
        let checks = [];
        // console.log(tree);
        if(tree.data.e01z1Id == 'zb'){
          return [tree.id];
        }
        let a01Id = tree.data.a01Id;
        let e01ziIds = tree.data.e01z1Id.split(",");

        if(e01ziIds[0].length>30){

          e01ziIds.map(item=>{
            checks.push(a01Id + "-" + item);
          })
        }else {
          e01ziIds.map(item=>{
            checks.push(a01Id + "-" + item);
          })
        }
        return checks;

      },
      //点击查看详情
      viewDetals(row){
        this.e01zcId = row.id;
        queryE01zcRelaTreeByE01zcId(row.id).then(response=>{
          if(response.code==200){
            this.e01zcTree = new Array();
            // this.e01zcTree = response.data;
            let _this = this;
            response.data.map((item,index)=>{
              console.log(item);
              _this.e01zcTree[index] = new Array();
              _this.e01zcTree[index].push(item);
            })
            this.divw = 'divw4'
            if(this.e01zcTree.length == 1){
              this.divw = 'divw1'
            }
            if(this.e01zcTree.length == 2){
              this.divw = 'divw2'
            }
            if(this.e01zcTree.length == 3){
              this.divw = 'divw3'
            }
            if(this.e01zcTree.length == 4){
              this.divw = 'divw4'
            }
            this.$nextTick().then(() =>{
              _this.e01zcTree.map(item=>{
                let temp = item[0];
                let tree = _this.$refs[temp.id][0];
                console.log(tree);
                if(temp.data.e01z1Id == 'zb'){
                  tree.setCheckedKeys([temp.id],false);
                }else{
                  let checks = [];
                  let a01Id = temp.data.a01Id;
                  let e01ziIds = temp.data.e01z1Id.split(",");

                  if(e01ziIds[0].length>30){

                    e01ziIds.map(item2=>{
                      checks.push(a01Id + "-" + item2);
                    })
                  }else {
                    e01ziIds.map(item3=>{
                      checks.push(a01Id + "-" + item3);
                    })
                  }
                  tree.setCheckedKeys(checks,false);
                }
              })
            })
          }
        })
        this.approvalForm.approverDate = new Date();
        this.approvalForm.approverTime = new Date();
        this.viewDetalsVasable = true;
      },
      //点击审批处理
      approvalProcess(){
        if(this.electronicCheck.length==0||this.electronicCheck.length>1){
          this.$message({
            message:'请勾选一条数据后进行审批',
            type:'warning'
          })
          return;
        }
        //审批处理
        this.viewDetalsVasable2 = true;
        this.approvalForm.approverDate = new Date();
        this.approvalForm.approverTime = new Date();
        console.log(this.electronicCheck);
      },
      //台账跳转
      initShowNum(){
        if(this.$route.params.sign!=null){
          this.tabsShow=false;
          this.formShow=true;
          this.addForm.archivesType=this.$route.params.sign
        }
      },

      // 档案借阅上部搜索按钮
      handleQueryBorrowing() {
        if(this.archivesType==0){
          this.pageNum0=1;
          this.queryBorrowingParams = JSON.parse(JSON.stringify(this.borrowingParams0));
        }else if(this.archivesType==1){
          this.pageNum1=1;
          this.queryBorrowingParams = JSON.parse(JSON.stringify(this.borrowingParams1));
        }else{
          this.pageNum2=1;
          this.queryBorrowingParams = JSON.parse(JSON.stringify(this.borrowingParams2));
        }
        this.archivesaFunction(false);

      },

      tabClick(tab, event) {
        if(tab.label === '待审批'){
          this.archivesType = 0
          this.pageNum = 1;
          this.searchRow = 'searchRow'
        } else if (tab.label === '已通过') {
          this.archivesType = 1
          this.pageNum = 1;
          this.searchRow = 'searchRow2'
          this.BorrowingColumn2[14].isCol = true;
        } else if (tab.label === '未通过') {
          this.archivesType = -1
          this.pageNum = 1;
          this.searchRow = 'searchRow3'
          this.BorrowingColumn2[14].isCol = false;
        }
      },
      /* 档案查询 */


      /* 电子档案申请记录 */
      archivesaFunction(all) {
        // if (this.$route.params.sign == null) {
        if(this.archivesType==0||all){//待审批
          let _this = this
          //电子档案查阅
          let type = 1;
          //系统内
          let target = 1;
          let params = {
            type: type,
            target: target,
            approval: "0",
            pageNum: this.pageNum0,
            pageSize: this.pageSize0,
            userName: this.queryBorrowingParams.userName,
            endTime: this.queryBorrowingParams.endTime,
            beginTime: this.queryBorrowingParams.beginTime,

          }
          getApprovals(params)
            .then(function (response) {
              // for (var i=0;i<)
              _this.ElectronicData0 = response.rows;
              for (let i = 0; i < _this.ElectronicData0.length; i++) {
                //ie无法使用 _this.ElectronicData[i].approvTime = _this.format(_this.ElectronicData[i].approvTime, "yyyy-MM-dd hh:mm:ss");
                //查阅截至时间
                let tdate = new Date(_this.ElectronicData0[i].endDate).getTime();
                //当前时间
                let ndate = new Date().getTime();
                if (tdate > ndate) {
                  _this.ElectronicData0[i].lookType = "未到期";
                } else {
                  _this.ElectronicData0[i].lookType = "已到期";
                }
              }
              _this.total0 = response.total
              _this.loading = false
            })
          _this.tabsShow = true
        }
        if(this.archivesType==1||all){//已通过
          let _this = this
          //电子档案查阅
          let type = 1;
          //系统内
          let target = 1;
          let params = {
            type: type,
            target: target,
            approval:"1",
            pageNum: this.pageNum1,
            pageSize: this.pageSize1,
            userName: this.queryBorrowingParams.userName,
            endTime: this.queryBorrowingParams.endTime,
            beginTime: this.queryBorrowingParams.beginTime,

          }
          getApprovals(params)
            .then(function (response) {
              // for (var i=0;i<)
              _this.ElectronicData1 = response.rows;
              for (let i = 0; i < _this.ElectronicData1.length; i++) {
                //ie无法使用 _this.ElectronicData[i].approvTime = _this.format(_this.ElectronicData[i].approvTime, "yyyy-MM-dd hh:mm:ss");
                //查阅截至时间
                let tdate = new Date(_this.ElectronicData1[i].endDate).getTime();
                //当前时间
                let ndate = new Date().getTime();
                if (tdate > ndate) {
                  _this.ElectronicData1[i].lookType = "未到期";
                } else {
                  _this.ElectronicData1[i].lookType = "已到期";
                }
              }
              _this.total1 = response.total
              _this.loading = false
            })
          _this.tabsShow = true
        }
        if(this.archivesType==-1||all){//未通过
          let _this = this
          //电子档案查阅
          let type = 1;
          //系统内
          let target = 1;
          let params = {
            type: type,
            target: target,
            approval: "-1",
            pageNum: this.pageNum2,
            pageSize: this.pageSize2,
            userName: this.queryBorrowingParams.userName,
            endTime: this.queryBorrowingParams.endTime,
            beginTime: this.queryBorrowingParams.beginTime,

          }
          getApprovals(params)
            .then(function (response) {
              // for (var i=0;i<)
              _this.ElectronicData2 = response.rows;
              for (let i = 0; i < _this.ElectronicData2.length; i++) {
                //ie无法使用 _this.ElectronicData[i].approvTime = _this.format(_this.ElectronicData[i].approvTime, "yyyy-MM-dd hh:mm:ss");
                //查阅截至时间
                let tdate = new Date(_this.ElectronicData2[i].endDate).getTime();
                //当前时间
                let ndate = new Date().getTime();
                if (tdate > ndate) {
                  _this.ElectronicData2[i].lookType = "未到期";
                } else {
                  _this.ElectronicData2[i].lookType = "已到期";
                }
              }
              _this.total2 = response.total
              _this.loading = false
            })
          _this.tabsShow = true
        }





        // }
      },

      format(datetime,fmt) {
        if (parseInt(datetime)==datetime) {
          if (datetime.length==10) {
            datetime=parseInt(datetime)*1000;
          } else if(datetime.length==13) {
            datetime=parseInt(datetime);
          }
        }
        datetime=new Date(datetime);
        var o = {
          "M+" : datetime.getMonth()+1,                 //月份
          "d+" : datetime.getDate(),                    //日
          "h+" : datetime.getHours(),                   //小时
          "m+" : datetime.getMinutes(),                 //分
          "s+" : datetime.getSeconds(),                 //秒
          "q+" : Math.floor((datetime.getMonth()+3)/3), //季度
          "S"  : datetime.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
          fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
          if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
      },
      // 查询电子档案 上部搜索按钮
      handleQueryElectronic() {
        const _this = this
        const queryParams = {
          deptName: this.queryParams.deptName,
          // a01Id: this.queryParams.a01Id,
          a0101: this.queryParams.a0101,
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          type: this.archivesType
        }
        conditionSearchArchives(queryParams)
          .then(response => {
            if (response.code === 200) {
              _this.ElectronicData = response.rows
              _this.total = response.total
              _this.loading = false
            }
          })
      },
      // 查询电子档案 上部重置按钮
      resetQueryElectronic(queryParams) {
        this.formInline.deptName = '';
        this.formInline.a0101 = '';
        this.$refs[queryParams].resetFields()
        this.archivesaFunction()
      },
      // 查询实体档案 上部重置按钮
      resetQueryEntity(entityParams) {
        this.formInline.deptName = '';
        this.formInline.a0101 = '';
        this.$refs[entityParams].resetFields()
        this.archivesaFunction()
      },
      resetQueryBorrowing(borrowingParams) {
        this.borrowingParams.userName = '';
        this.borrowingParams.beginTime = '';
        this.borrowingParams.endTime = '';
        this.pageNum = 1;
        this.archivesaFunction()
      },
      // 查询实体档案 上部搜索按钮
      handleQueryEntity() {
        const _this = this
        const entityParams = {
          deptName: this.entityParams.deptName,
          a0101: this.entityParams.a0101,
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          type: this.archivesType
        }
        conditionSearchArchives(entityParams)
          .then(response => {
            if (response.code === 200) {
              _this.EntityData = response.rows
              _this.total1 = response.total
              _this.loading = false
            }
          })
      },
      // 电子档案 选中批次
      electronicChange(val) {
        this.electronicCheck = val
      },
      // 重新提交
      // grid 选中
      handleSelectionChange(val) {
        this.electronicCheck = val
      },
      // 导出
      handleExportElectronic() {
      },
      // 打印
      handlePrintElectronic() {
      },
      // 电子查看
      electronicView() {

      },
      // 实体档案  复选框
      entityChange(val) {
        this.electronicCheck = val
      },
      // 实体档案重新提交
      // 导出
      handleExportEntity() {
      },
      // 打印
      handlePrintEntity() {
      },
      // 导出
      handleExportBorrowing() {
        if(this.electronicCheck.length == 0){
          this.$confirm('是否导出搜索后的审批数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            if(this.$route.params.sign==null){
              const _this = this
              const param = this.archivesType;
              //电子档案查阅
              const type = 1;
              //系统外
              const target = 1;
              const params = {
                type: type,
                target:target,
                approval :this.archivesType,
                pageNum: 1,
                pageSize: 99999999,
                userName: this.borrowingParams.userName,
                endTime: this.borrowingParams.endTime,
                beginTime: this.borrowingParams.beginTime,

              }
              getApprovals(params).then(function (response) {
                for (let i = 0; i < response.rows.length; i++) {
                  response.rows[i].approvTime = _this.format(response.rows[i].approvTime, "yyyy-MM-dd hh:mm:ss");
                  //查阅截至时间
                  let tdate = new Date(response.rows[i].endDate).getTime();
                  //当前时间
                  let ndate = new Date().getTime();
                  if (tdate > ndate) {
                    response.rows[i].lookType = "未到期";
                  } else {
                    response.rows[i].lookType = "已到期";
                  }
                }
                if(response.rows.length==0){
                  this.$message.error("无数据,导出失败");
                  return;
                }
                let row = {
                  row: response.rows,
                  status: 15
                }
                downLoadPost("/export/excel/exportClick", row,this)
                // console.log(response.rows)
              })
              // let s = JSON.stringify(params);
              // console.log(s);
              // s = encodeURI(s);
              // downLoad("/export/excel/export?status=7&exportData=" + s)
            }
          })

        }else{
          this.$confirm('是否导出选中的审批数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let row = {
              row: this.electronicCheck,
              status: 15
            }
            downLoadPost("/export/excel/exportClick", row,this)
          })

        }


      },
      // 打印
      handlePrintBorrowing() {
      },

      handleNodeClick(data) {
        console.log(data)
      },
      // 档案借阅监听
      // 分页大小
      handleSizeChange(val) {
        if(this.archivesType=="0"){
          this.pageSize0 = val
        }else if(this.archivesType=="1"){
          this.pageSize1 = val
        }else{
          this.pageSize2 = val
        }
        this.archivesaFunction(false)
      },
      // 分页监听
      handleCurrentChange(val) {
        if(this.archivesType=="0"){
          this.pageNum0 = val
        }else if(this.archivesType=="1"){
          this.pageNum1 = val
        }else{
          this.pageNum2 = val
        }
        this.archivesaFunction(false)
      },

      //   //实体行grid点击事件
      handlerBorrowClickRow(row){
        if (row) {
          this.$refs.BorrowingData.toggleRowSelection(row)
        } else {
          this.$refs.BorrowingData.clearSelection()
        }
      },
      //   // 初始化转换参数
      initColumnMap() {
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

        //当前页面所需字典表数据  目录树
        queryDictionaryByTypes({types:"ZB97A"})
          .then(response => {
            this.sysDictionaryMap = response.data

          })
      },
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
    }
  }
</script>
<style lang="scss" scoped>
  /deep/ .el-table--border th, /deep/ .el-table--border td {
    border-right: 0.1px solid #dfe6ec;
    border-left: 0.1px solid #dfe6ec;
  }
  /*/deep/ .el-table--border th, .el-table--border td {*/
  /*  border-left: 1px solid #dfe6ec;*/
  /*}*/
  .app-electronic-access-approval{
    /*overflow: auto;*/
    height: calc(100vh - 86px);
  }
  .t-ctn {
    width: 100%;
    overflow-x: auto;
  }

  .t-ctn .s-ctn {
    height: 150px;
    white-space: nowrap;
    font-size: 0;
  }

  .divw1{
    width: 100%;
    height: 50%;
  }

  .divw2{
    width: 50%;
    height: 50%;
  }
  .divw3{
    width: 33%;
    height: 50%;
  }
  .divw4{
    width: 25%;
    height: 50%;
  }

  .t-ctn .s-ctn div {
    font-size: 14px;
    box-sizing: border-box;
    white-space: normal;
    word-wrap: break-word;
    word-break: break-all;
    overflow: hidden;
    display: inline-block;

    height: 100%;
    /*border-right: #CCCCCC 1px solid;*/
  }
  .el-col.el-col-24.el-col-xs-24 {
    padding: 10px;
    margin: 10px;
  }

  /* 左侧竖分割线 */
  /*.el-tabs--left /deep/ .el-tabs__header.is-left {
    height: calc(100vh - 86px);
  }*/

  .block {
    float: right;
  }

  .el-tab-pane {
    min-height: calc(100vh - 86px);
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

  .inputW {
    width: 200px;
  }
  .search-form {
    width: 100%;
    background: rgba(204, 204, 204, 0.18);
    padding: 10px;
    margin-right: 10px;
    display: flex;
    .form-width {
      width: 22%;
      display: flex;
    }
    /deep/ .el-form-item {
      margin-bottom: 0 !important;
    }
  }
  /deep/.el-table tbody tr:hover>td {
    background-color:#ecf5ff !important
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
  .tree-style{
    /deep/.el-tree--highlight-current .el-tree-node.is-current>.el-tree-node__content {
      background: rgba(204, 204, 204, 0.18);
    }

  }
  .tree {
    width: 100%;
    padding: 10px 10px 26px 10px;

    /*border-right: 1px solid #e2e3e6;*/
  }
  /deep/ .el-scrollbar .el-scrollbar__wrap {
    overflow-x: hidden;
  }

  /deep/ .el-tree > .el-tree-node {
    min-width: 100%;
    display: inline-block;
  }
  /deep/ .el-table__fixed-right-patch {
    background-color:#F8F8F9;
  }
  /deep/ .el-table .el-table__header-wrapper th{
    height:31px;
  }
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
      width: 40px;
      margin-left:20%;
      margin-top: 20px
    }
  }
  .page {
    float: right;
  }
  .btn {
    width: 100%;
    position: relative;
    padding-bottom: 10px;
    padding-top: 10px;
    display: flex;
    // .btn-search {
    //   position: absolute;
    //   right: 0px;
    //   top: 10px;
    //   padding-bottom: 20px;
    // }
  }
  /deep/ .el-table > .cell{
    height: 28px;
  }
</style>

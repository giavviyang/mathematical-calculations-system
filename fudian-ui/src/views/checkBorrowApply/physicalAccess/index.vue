<template>
  <!-- 实体档案查阅申请 -->
  <div class="app-borrowing">
    <el-tabs
      v-if="tabsShow"
      id="elTabs"
      tab-position="left"
      v-model="activeName"
      style="min-height: calc(100vh - 86px);border: 1px solid #CCCCCC"
      @tab-click="tabClick"
    >
      <el-tab-pane label="待审批" name="pendapproval">
        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px)">
          <el-row  :gutter="10" ref="searchRow1" style="margin:0 0 0 0;padding-left: 0px">
            <el-form
              v-hasPermi="['checkBorrowApply:physicalAccess:search']"
              v-if="showBorrowing"
              class="search-form"
              :model="borrowingParams0"
              :inline="true"
              >
              <el-form-item label="申请查阅时间起" prop="startTime"  >
                <el-date-picker  size="mini"  v-model="borrowingParams0.startTime"  type="date" placeholder="请选择时间"  style="width: 150px" />
              </el-form-item>
              <el-form-item label="申请查阅时间至"  prop="endTime" >
                <el-date-picker  size="mini"  v-model="borrowingParams0.endTime"  type="date" placeholder="请选择时间"  style="width: 150px" />
              </el-form-item>
              <el-form-item >
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryBorrowing">搜索
                </el-button>
                <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQueryBorrowing('borrowingParams')">重置</el-button> -->
              </el-form-item>
            </el-form>
            <!-- <el-dropdown style="float: right; margin-left: 5px;">
              <el-button icon="el-icon-menu" size="mini" circle />
              <el-dropdown-menu style="padding: 10px">
                <el-checkbox-group v-for="(item, index) in pendingApprovalCol" :key="index" v-model="item.isCol" size="mini">
                  <el-checkbox>{{ item.label }}</el-checkbox>
                </el-checkbox-group>
              </el-dropdown-menu>
            </el-dropdown>
            <el-col :span="1.5" style="float: right;">
              <el-button
                type="primary"
                icon="el-icon-search"
                circle
                size="mini"
                @click="function(){showBorrowing = !showBorrowing}"
              />
            </el-col> -->
          </el-row>
          <div  class="btn">
            <el-button type="primary" size="mini" icon="el-icon-plus"  v-hasPermi="['checkBorrowApply:physicalAccess:startApply']"  @click="handleAddBorrowing">发起申请</el-button>
            <el-button type="primary" size="mini" icon="el-icon-download" v-hasPermi="['checkBorrowApply:physicalAccess:export']"  @click="handleExportBorrowing">导出</el-button>
          </div>

          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            tooltip-effect="light"
            v-loading="loading"
            :height="tableHeight"
            size="mini"
            :header-cell-style="headClass"
             empty-text=" "
            style="width: 100%"
            :data="BorrowingData0"
            ref="BorrowingData0"
            class="table-style"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="40"/>
            <el-table-column style="width: 20px" type="index" align="center" label="序号" :index="customIndex0"/>
            <el-table-column
              align="center"
              show-overflow-tooltip
              :resizable="false"
              v-for="(item, index) in pendingApprovalCol"
              :v-if="item.isCol"
              :key="index"
              :width="item.width"
              :formatter="item.formatter"
              :prop="item.prop"
              :label="item.label"/>
            <el-table-column  label="操作" align="center" width="100" fixed="right">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" v-hasPermi="['checkBorrowApply:physicalAccess:details']"  @click="electronicFeedback(scope.row)">查看详情</el-button>
                  </template>
                </el-table-column>
          </el-table>

          <div class="block">
            <el-pagination
              :page-sizes="[20, 50, 100, 200]"
              :page-size="pageSize0"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total0"
              :current-page="pageNum0"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-col>
      </el-tab-pane>
      <el-tab-pane label="已通过" name="passed">
        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px)">
          <el-row   :gutter="10" ref="searchRow2"  style="margin:0 0 0 0">
            <el-form
              v-hasPermi="['checkBorrowApply:physicalAccess:search']"
              v-if="showBorrowing"
              class="search-form"
              :model="borrowingParams1"
              :inline="true"
            >
              <el-form-item label="申请查阅时间起"   prop="startTime">
                <el-date-picker  size="mini"  v-model="borrowingParams1.startTime"  @keyup.enter.native="handleQueryBorrowing"     type="date" placeholder="请选择时间"  style="width: 150px" />
              </el-form-item>
              <el-form-item label="申请查阅时间至"   prop="endTime" >
                <el-date-picker  size="mini"  v-model="borrowingParams1.endTime"   @keyup.enter.native="handleQueryBorrowing"    type="date" placeholder="请选择时间"  style="width: 150px" />
              </el-form-item>
              <el-form-item >
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryBorrowing">搜索
                </el-button>
                <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQueryBorrowing('borrowingParams')">重置</el-button> -->
              </el-form-item>
            </el-form>
            <!-- <el-dropdown style="float: right; margin-left: 5px;">
              <el-button icon="el-icon-menu" size="mini" circle />
              <el-dropdown-menu style="padding: 10px">
                <el-checkbox-group v-for="(item, index) in pendingApprovalCol1" :key="index" v-model="item.isCol" size="mini">
                  <el-checkbox>{{ item.label }}</el-checkbox>
                </el-checkbox-group>
              </el-dropdown-menu>
            </el-dropdown>
            <el-col :span="1.5" style="float: right;">
              <el-button
                type="primary"
                icon="el-icon-search"
                circle
                size="mini"
                @click="function(){showBorrowing = !showBorrowing}"
              />
            </el-col> -->
          </el-row>
          <div  class="btn">
            <el-button type="primary" icon="el-icon-receiving"  size="mini" v-hasPermi="['checkBorrowApply:physicalAccess:renewal']"  @click="renewalOfMaturity">到期续查</el-button>
            <el-button type="primary" size="mini" icon="el-icon-download" v-hasPermi="['checkBorrowApply:physicalAccess:export']"  @click="handleExportBorrowing">导出
            </el-button>
          </div>

          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            :height="tableHeight"
            size="mini"
            v-loading="loading"
            ref="BorrowingData1"
            tooltip-effect="light"
            empty-text=" "
            :header-cell-style="headClass"
            style="width: 100%"
            :data="BorrowingData1"
            class="table-style"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="40"/>
            <el-table-column style="width: 20px" type="index" align="center" label="序号"  :index="customIndex1"/>
            <el-table-column
              align="center"
              show-overflow-tooltip
              :resizable="false"
              v-for="(item, index) in pendingApprovalCol1"
              :v-if="item.isCol"
              :key="index"
              :width="item.width"
              :formatter="item.formatter"
              :prop="item.prop"
              :label="item.label"/>
            <el-table-column  label="操作" align="center" width="100" fixed="right">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" v-hasPermi="['checkBorrowApply:physicalAccess:details']"  @click="electronicFeedback(scope.row)">查看详情</el-button>
                  </template>
                </el-table-column>
          </el-table>

          <div class="block">
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

        <!--  到期续查  -->
    <el-dialog title="到期续查" :append-to-body="true"  width="28%" class=" dialog-style dialog-basic" :visible.sync="viewDetalsVasable1">
      <div class="dialog-state">
      <span class="state">填写续查截止日期</span>
      <el-form ref="form" :model="approvalForm"  style="width:90%;margin-top:11%;margin-left: 5%"  label-width="120px">
        <el-form-item prop="oldEndTime" label="查阅截止日期至">
          <el-date-picker  size="mini" style="width:100%" v-model="approvalForm.oldEndTime"  type="date" placeholder="请选择日期"   />
        </el-form-item>
        <el-form-item prop="endTime" label="续查截止日期至">
          <el-date-picker  size="mini"  style="width:100%" v-model="approvalForm.endTime"  type="date" placeholder="请选择日期"   />
        </el-form-item>
      </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="viewDetalsVasable1 = false ">关闭</el-button>
        <el-button size="mini" type="primary" @click="saveRenewal">保存</el-button>
      </div>
    </el-dialog>

      </el-tab-pane>
      <el-tab-pane label="未通过" name="notpass">

        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px)">
          <el-row    :gutter="10" ref="searchRow3" style="margin:0 0 0 0;padding-left: 0px">
            <el-form
              v-hasPermi="['checkBorrowApply:physicalAccess:search']"
              v-if="showBorrowing"
              class="search-form"
              :model="borrowingParams2"
              :inline="true"
              label-width="110px"
            >
              <el-form-item label="申请查阅时间起" prop="startTime"  >
                <el-date-picker  size="mini"  v-model="borrowingParams2.startTime"  type="date" placeholder="请选择时间"  style="width: 150px" />
              </el-form-item>
              <el-form-item label="申请查阅时间至" prop="endTime"  >
                <el-date-picker  size="mini"  v-model="borrowingParams2.endTime"  type="date" placeholder="请选择时间"  style="width: 150px" />
              </el-form-item>
              <el-form-item >
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryBorrowing">搜索
                </el-button>
                <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQueryBorrowing('borrowingParams')">重置</el-button> -->
              </el-form-item>
            </el-form>
            <!-- <el-dropdown style="float: right; margin-left: 5px;">
              <el-button icon="el-icon-menu" size="mini" circle />
              <el-dropdown-menu style="padding: 10px">
                <el-checkbox-group v-for="(item, index) in pendingApprovalCol2" :key="index" v-model="item.isCol" size="mini">
                  <el-checkbox>{{ item.label }}</el-checkbox>
                </el-checkbox-group>
              </el-dropdown-menu>
            </el-dropdown>
            <el-col :span="1.5" style="float: right;">
              <el-button
                type="primary"
                icon="el-icon-search"
                circle
                size="mini"
                @click="function(){showBorrowing = !showBorrowing}"
              />
            </el-col> -->
          </el-row>
          <div class="btn">
            <el-button type="primary" size="mini" icon="el-icon-download" v-hasPermi="['checkBorrowApply:physicalAccess:export']"  @click="handleExportBorrowing">导出
            </el-button>
          </div>
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            class="table-style"
            :height="tableHeight"
            tooltip-effect="light"
            v-loading="loading"
            empty-text=" "
            size="mini"
            ref="BorrowingData2"
            :header-cell-style="headClass"
            style="width: 100%"
            :data="BorrowingData2"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="40"/>
            <el-table-column style="width: 20px" type="index" align="center" label="序号"  :index="customIndex2"/>
            <el-table-column
              align="center"
              show-overflow-tooltip
              :resizable="false"
              v-for="(item, index) in pendingApprovalCol2"
              :v-if="item.isCol"
              :key="index"
              :width="item.width"
              :formatter="item.formatter"
              :prop="item.prop"
              :label="item.label"/>
            <el-table-column  label="操作" align="center" width="100" fixed="right">
                  <template slot-scope="scope">
                    <el-button type="text" size="small"  v-hasPermi="['checkBorrowApply:physicalAccess:details']"  @click="electronicFeedback(scope.row)">查看详情</el-button>
                  </template>
                </el-table-column>
          </el-table>

          <div class="block">
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
        </el-col>

      </el-tab-pane>
    </el-tabs>

    <el-row v-if="formShow">
      <el-col  :span="24" :xs="24" style="margin: 0;padding: 0;border: 1px solid #cccccc;height: calc(100vh - 86px);overflow: auto;">
        <el-row style="padding-left: 10%;padding-right: 10%">
          <el-col :span="24" style="width:100%">
            <div style="width: 100%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0">第一步：填写申请人基本信息</div>
              <el-form  ref="addForm" :model="addForm" label-width="150px" style="width:90%;padding: 20px 0 0 0;margin: 0 auto;padding-left: 3%;padding-right: 3%" :rules="rules">

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
                    <el-input v-model="addForm.userPhone" maxlength="11"  size="mini"  placeholder="请输入申请人电话" style="width: 100%;"/>
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item label="申请理由" prop="applicationReasons">
                    <el-input v-model="addForm.applicationReasons" size="mini"  placeholder="请输入申请理由" style="width: 100%;"/>
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item label="申请查阅起始日期" prop="startTime">
                    <el-date-picker size="mini" v-model="addForm.startTime"  type="date" placeholder="选择申请开始时间" style="width: 100%;" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item  label="申请查阅截止日期至" style="text-align:right;" prop="endTime">
                    <el-date-picker size="mini" v-model="addForm.endTime"  :picker-options="pickerOptions"   type="date" placeholder="选择申请截止时间至" style="width: 100%;" />
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item label="申请人职务" prop="duty">
                    <el-input v-model="addForm.duty" size="mini"   placeholder="清输入申请人职务" style="width: 100%;"/>
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item label="打印内容" prop="printContents">
                    <el-input v-model="addForm.printContents" size="mini"  type="textarea"   placeholder="若打印请输入打印内容" style="width: 100%;"/>
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item label="备注" prop="e01z831">
                    <el-input v-model="addForm.e01z831" size="mini"   placeholder="请输入备注" style="width: 100%;" type="textarea" />
                  </el-form-item>
                </el-col>
                <el-col :span="16">
                  <el-form-item label="添加附件" prop="sysFile">
                    <input  type="file"  ref="imageFile" @change="fileChange"   accept="image/png, image/jpeg" name="upload_Wfile"  style="display: none;" />
                    <el-button size="mini" onclick="upload_Wfile.click();" style="width:75px">选择文件</el-button>
                    <el-input size="mini" v-model="addForm.fileName" onclick="upload_Wfile.click();" :readonly="true" class="fileTextInput"/>
                  </el-form-item>
                </el-col>
              </el-form>
          </el-col>
          <el-col :span="24" style="padding: 10px">
              <div style="width:100%;margin: 0 auto">
                  <el-button  style="float: right;margin-right: 10px;width:80px" size="mini" @click="resetAddForm">重置表单</el-button>
              </div>
          </el-col>
          <el-col :span="24" >
            <div style="width: 100%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;margin-top:-15px">第二步：请选择需要查阅的干部档案</div>
            <el-row :gutter="10"   style="margin: 0 auto;padding:25px 0 0 0">
              <el-form v-model="formInline" :inline="true" :model="formInline" label-width="80px" >
  <!--              <el-col :span="12">-->
                  <el-form-item label="干部状态" style="margin-left: 20px">
                    <el-select v-model="a01state" style="width: 150px"  filterable class="inputW" placeholder="请选择干部状态" size="mini">
                      <el-option v-for="item in states"  :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                    </el-select>
                  </el-form-item>
                  <el-form-item style="margin-left: 20px"  prop="deptName">
                    <el-input v-model="formInline.deptName" size="mini" @keyup.enter.native="search(1)"  style="width: 150px" placeholder="请输入部门名称" />
                  </el-form-item>
                  <el-form-item>
                    <el-button size="mini" type="primary" @click="search(1)">部门搜索</el-button>
                  </el-form-item>
                  <el-form-item style="margin-left: 20px"  prop="a0101">
                    <el-input v-model="formInline.a0101" size="mini"  @keyup.enter.native="search(2)"  style="width: 150px" placeholder="请输入干部名称" />
                  </el-form-item>
                  <el-form-item>
                    <el-button size="mini" type="primary" @click="search(2)">干部搜索</el-button>
                  </el-form-item>
              </el-form>
            </el-row>
            <el-col :span="24" style="padding-left: 2%;padding-right: 2%">
              <el-table height="222" empty-text=" " size="mini" ref="searchTableData1" tooltip-effect="light"  class="table-style" :data="searchTableData" border @cell-dblclick="deptClick" @selection-change="gridSelectionChange" :header-cell-style="headClass" @row-click="handleGridChange" :stripe="true">
                <el-table-column type="selection" key="1" style="width:40px"  v-if="dpbutton" />
                <el-table-column style="width: 1%" key="2"  type="index" align="center" label="序号" />
                <el-table-column show-overflow-tooltip prop="deptName" key="3"   v-if="departOrPerson==1"  label="部门名称" align="center"  />
                <el-table-column show-overflow-tooltip prop="parentId" key="4"   v-if="departOrPerson==1" label="上级部门" align="center"  />
                <el-table-column show-overflow-tooltip prop="a0101"  key="5" v-if="departOrPerson==2" label="干部姓名" align="center"  />
                <!-- <el-table-column prop="number"  key="6" v-if="departOrPerson==2" label="干部编号" align="center" /> -->
                <el-table-column show-overflow-tooltip prop="deptName" key="7"  label="所属部门"  v-if="departOrPerson==2" align="center"  />
                <el-table-column show-overflow-tooltip prop="a0104"  key="8" label="性别" v-if="departOrPerson==2"  align="center" :formatter="getFullName" />
                <el-table-column show-overflow-tooltip prop="a0107"  key="9" label="出生日期" v-if="departOrPerson==2"  align="center" :formatter="getFullName" />
                <el-table-column show-overflow-tooltip prop="a0117"  key="10" label="民族" v-if="departOrPerson==2"  align="center"  :formatter="getFullName" />
                <el-table-column show-overflow-tooltip prop="a0141" key="11"  label="政治面貌" v-if="departOrPerson==2"  align="center"  :formatter="getFullName" />
              </el-table>
            </el-col>
          </el-col>

          <el-col :span="24" style="padding: 10px">
              <div style="width:100%;margin: 0 auto">
                  <el-button type="primary" style="float: right;margin-right: 10px;width:80px" size="mini" @click="typeClick">添加</el-button>
              </div>
          </el-col>
          <div style="width: 100%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:10px 0 0 0">第三步：确认申请信息</div>
          <el-table
              :default-sort="{prop: 'date', order: 'descending'}"
              stripe
              size="mini"
              border
              :header-cell-style="headClass"
              tooltip-effect="light"
              :data="queryData"
              style="width: 96%;margin-top:25px;margin-left: 2%;margin-right: 2%"
              height="222px"
              ref="queryA01Data"
              empty-text=" "
              class="table-style"
              @selection-change="handleSelectionChange"
            >
              <el-table-column style="width: 1%" type="index" align="center" label="序号" />
              <el-table-column show-overflow-tooltip align="center" v-if="true" label="干部姓名" prop="a0101" />
              <!-- <el-table-column align="center" v-if="true" label="干部编号" prop="number" /> -->
              <el-table-column show-overflow-tooltip align="center" v-if="true" label="所属部门" prop="deptName" />
              <el-table-column show-overflow-tooltip align="center" v-if="true" label="干部职务" prop="duty" :formatter="getFullName"/>
              <el-table-column show-overflow-tooltip align="center" v-if="true" label="查档内容" prop="e01z111a" />
              <el-table-column show-overflow-tooltip align="center" v-if="true" label="档案类型" prop="archivesType" />

              <el-table-column align="center" fixed="right" label="操作" width="100">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="deleteRow(scope.$index, queryData)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-button style="float: right;margin: 10px;" size="mini" type="primary" @click="onGrid('addForm')">发起申请</el-button>
            <el-button style="float: right;margin: 10px;" size="mini"  @click="onBack">返回</el-button>
  <!--        </el-col>-->
        </el-row>
      </el-col>
    </el-row>


      <!-- 查看详情-->
    <el-dialog  title="实体档案查阅详情"
    :append-to-body="true"
    :destroy-on-close="true"
    :close-on-click-modal="false"
    width="90%"
    class=" dialog-style dialog-basic"
    :visible.sync="dialogDetailsVisible1">
      <div style="padding:5px" >
      </div>
      <el-col style="margin-left: 2%" :span="1.5"><el-button type="primary" icon="el-icon-download" size="mini" >导出</el-button></el-col>
        <el-table :data="viewDetailDatas"
                  border
                  class="table-style"
                  style="width: 95%; margin-left: 2%;margin-top: 50px;margin-bottom: 20px"
                  height="380px"
                  stripe
                  size="mini"
                  tooltip-effect="light"
                  :header-cell-style="headClass"
                  ref="searchTableData" >
          <el-table-column  type="index" width="50" align="center" label="序号" />
          <el-table-column prop="a0101" label="被申请人姓名" align="center"  />
          <el-table-column prop="deptName" label="被申请人所在部门" align="center"  />
          <el-table-column prop="e01z111a" :show-overflow-tooltip="true" label="申请内容" align="center"  />
          <el-table-column prop="creatersTime" label="申请日期" align="center"  :formatter="getFullName" />
          <el-table-column prop="startTime" label="申请查阅起始日期" align="center"  :formatter="getFullName" />
          <el-table-column prop="endTime" label="申请查阅截止日期至" align="center"  :formatter="getFullName" />
        </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogDetailsVisible1 = false ">关闭</el-button>

      </div>
    </el-dialog>


  </div>
</template>
<script>
import {
  checkBorrowEntityFile,
  getEntityArchiveCApplyRecord,
  archiveRenewalOfMaturity,
  viewDetails
} from '@/api/usual/usage/borrow'
import {downLoad,downLoadPost} from '@/downLoad/filedownload'
import { queryA01List } from '@/api/usual/transfer'
import { getTreeGrid } from '@/api/usual/daily'
import {queryDictionaryByType,queryDictionaryByTypes,queryLoginUser} from '@/api/usual/receive'
// import request from '@/utils/request'
const datalist = []
export default {
  data() {
    return {
      loading:true,
      tabsShow:true,
      formShow:false,
      showBorrowing:true,
      applyStates:"0",
      tableHeight:'76%',
      electronicCheck:[],
      BorrowingData0:[],
      BorrowingData1:[],
      BorrowingData2:[],
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
      a01state:"0",
      departOrPerson:2,
      searchText:"",
      dialogDetailsVisible:false,
      dialogDetailsVisible1:false,
      viewDetalsVasable1:false,
      activeName: 'pendapproval',
      userName:"",
      //时间限制
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() <= Date.now();
        },
      },
      viewDetailDatas:[],
      states:[],
      searchTableData:[],
      searchChek:"",
      dpbutton:true,
      searchValue:"",
      queryData:[],
      checkCol:this.checkA01Col,
      searchRowHeight:'',
      searchRow:'searchRow1',

      //所有table列
      //待审批table列
      pendingApprovalCol:[
        { label: '申请人姓名', prop: 'userName', isCol: true ,width:"110px"},
        { label: '申请人证件号', prop: 'userCard', isCol: true ,width:"150px"},
        { label: '申请查阅理由', prop: 'applicationReasons', isCol: true ,width:"150px"},
        { label: '申请人所在单位', prop: 'e01z824a', isCol: true ,width:"150px"},
        { label: '申请人电话号码', prop: 'userPhone', isCol: true ,width:"150px"},
        { label: '申请人职务', prop: 'duty', isCol: true ,width:"110px"},
        { label: '申请查阅档案卷数', prop: 'applyNumber', isCol: true ,width:"150px"},
        { label: '申请时间', prop: 'creatersTime', isCol: true ,width:"110px",formatter:this.getFullName},
        { label: '申请查阅起始日期', prop: 'startTime', isCol: true ,width:"150px",formatter:this.getFullName},
        { label: '申请查阅截止日期至', prop: 'endTime', isCol: true ,width:"150px",formatter:this.getFullName},
        { label: '申请类型', prop: 'applyStatus', isCol: true ,width:"110px"},
        { label: '申请打印内容', prop: 'printContents', isCol: true,width:"150px"},
        { label: '备注', prop: 'flag', isCol: true,width:"110px"},
      ],

      //已通过table列
      pendingApprovalCol1:[
        { label: '申请人姓名', prop: 'userName', isCol: true ,width:"110px"},
        { label: '申请人证件号', prop: 'userCard', isCol: true ,width:"150px"},
        { label: '申请查阅理由', prop: 'applicationReasons', isCol: true ,width:"150px"},
        { label: '申请人所在单位', prop: 'e01z824a', isCol: true ,width:"150px"},
        { label: '申请人电话号码', prop: 'userPhone', isCol: true ,width:"150px"},
        { label: '申请人职务', prop: 'duty', isCol: true ,width:"110px"},
        { label: '申请查阅档案卷数', prop: 'applyNumber', isCol: true ,width:"150px"},
        { label: '申请时间', prop: 'creatersTime', isCol: true ,width:"110px",formatter:this.getFullName},
        { label: '申请查阅起始日期', prop: 'startTime', isCol: true ,width:"150px",formatter:this.getFullName},
        { label: '申请查阅截止日期至', prop: 'endTime', isCol: true ,width:"150px",formatter:this.getFullName},
        { label: '申请类型', prop: 'applyStatus', isCol: true ,width:"110px"},
        { label: '审批人', prop: 'e01z801', isCol: true ,width:"110px"},
        { label: '经办人', prop: 'e01z817', isCol: true ,width:"110px"},
        { label: '审批时间', prop: 'approvTime', isCol: true ,width:"110px",formatter:this.getFullName},
        { label: '审批意见', prop: 'flag', isCol: true ,width:"110px"},
        { label: '申请打印内容', prop: 'printContents', isCol: true,width:"150px"},
        { label: '备注', prop: 'flag', isCol: true,width:"110px"},
      ],

      //未通过table列
      pendingApprovalCol2:[
        { label: '申请人姓名', prop: 'userName', isCol: true ,width:"110px"},
        { label: '申请人证件号', prop: 'userCard', isCol: true ,width:"150px"},
        { label: '申请查阅理由', prop: 'applicationReasons', isCol: true ,width:"150px"},
        { label: '申请人所在单位', prop: 'e01z824a', isCol: true ,width:"150px"},
        { label: '申请人电话号码', prop: 'userPhone', isCol: true ,width:"150px"},
        { label: '申请人职务', prop: 'duty', isCol: true ,width:"110px"},
        { label: '申请查阅档案卷数', prop: 'applyNumber', isCol: true ,width:"150px"},
        { label: '申请时间', prop: 'creatersTime', isCol: true ,width:"110px",formatter:this.getFullName},
        { label: '申请查阅起始日期', prop: 'startTime', isCol: true ,width:"150px",formatter:this.getFullName},
        { label: '申请查阅截止日期至', prop: 'endTime', isCol: true ,width:"150px",formatter:this.getFullName},
        { label: '申请类型', prop: 'applyStatus', isCol: true ,width:"110px"},
        { label: '审批人', prop: 'e01z801', isCol: true ,width:"110px"},
        { label: '经办人', prop: 'e01z817', isCol: true ,width:"110px"},
        { label: '审批时间', prop: 'approvTime', isCol: true ,width:"110px",formatter:this.getFullName},
        { label: '审批意见', prop: 'flag', isCol: true ,width:"110px"},
        { label: '申请打印内容', prop: 'printContents', isCol: true,width:"150px"},
        { label: '备注', prop: 'flag', isCol: true,width:"110px"},
      ],

      rules:{
        userName: [
          { required: true, message: '申请人姓名必须填写' , trigger: "blur"},
          { min: 2, max: 40, message: '长度在 2 到 40 个字符' , trigger: "blur"}
        ],
        userCard:[
          { required: true, message: '申请人证件号必须填写', trigger: "blur" },
          { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: "blur" }
        ],
        e01z824a:[
          { required: true, message: '申请人所在单位必须填写', trigger: "blur" },
          { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: "blur" }
        ],
        userPhone:[
          { required: true, message: '申请人电话必须填写' , trigger: "blur"},
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的电话号码",
            trigger: "blur"
          },
        ],
        applicationReasons:[
          { required: true, message: '申请理由必须填写' , trigger: "blur"},
          { min: 1, max: 255, message: '长度在 1 到 255 个字符', trigger: "blur" }
        ],
        endTime:[
          { required: true, message: '申请截止时间至必须填写' , trigger: "blur"},
        ],
        duty:[
          { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: "blur" }
        ],
        printContents:[
          { min: 1, max: 255, message: '长度在 1 到 255 个字符' , trigger: "blur"}
        ],
        e01z831:[
          { min: 1, max: 255, message: '长度在 1 到 255 个字符' , trigger: "blur"}
        ],
      },

      //申请页面
      addForm:{},
      //干部搜索
      formInline:{},
      //干部状态
      a01Search:{},
      //到期续查
      approvalForm:{},

      dutys:[],
      //测试数据
      detailsData: [],

    }

  },
  watch: {

    queryData :{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          if(_this.$refs.queryA01Data!=null){
            _this.$refs.queryA01Data.doLayout()
          }
        })
      },
      deep:true,
      // immediate:true

    },
    viewDetailDatas:{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          if(_this.$refs.searchTableData!=null){
            _this.$refs.searchTableData.doLayout();
          }
        })
      },
      deep:true,
      // immediate:true
    },
    searchTableData:{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          if(_this.$refs.searchTableData1!=null){
            _this.$refs.searchTableData1.doLayout();
          }
        })
      },
      deep:true,
      // immediate:true
    },
    BorrowingData:{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
           if(_this.$refs.BorrowingData0!=null){
              _this.$refs.BorrowingData0.doLayout();
           }
           if( _this.$refs.BorrowingData1!=null){
              _this.$refs.BorrowingData1.doLayout();
           }
           if(_this.$refs.BorrowingData2!=null){
              _this.$refs.BorrowingData2.doLayout();
           }
        })
      },
      deep:true,
      // immediate:true
    },

  },
  mounted() {
    this.archivesaFunction(true)
    this.$nextTick(()=>{
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + "px"
      })
    window.onresize = () => {
      return (() => {
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + "px"
      })();
    };
  },
  created() {
    this.getLoginUser()
    this.initColumnMap()
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
    deleteRow(index,data){
      data.splice(index, 1)
    },

    //查看详情
    electronicFeedback(val){
      this.viewDetailDatas=[];
      let params = {
        id:val.id,
        applyType:"1"
      }
      viewDetails(params).then(response=>{
        if(response.code==200){
          this.viewDetailDatas=response.data;
        }
      })
      this.dialogDetailsVisible1=true
    },
    //到期续查
    renewalOfMaturity(){
      console.info(this.electronicCheck)
      if(this.electronicCheck.length!=1){
        this.$message({
          message:"请选择一条数据",
          type:"info"
        })
      }else{
        console.info(this.electronicCheck[0]);
        if(this.electronicCheck[0].applyStatus=="到期续查"){
          this.$message({
            message:"该档案已续查过，请重新申请",
            type:"info"
          })
        }else{
          this.approvalForm.oldEndTime=this.electronicCheck[0].endTime
          this.viewDetalsVasable1=true
        }
      }
    },
    //到期续查保存
    saveRenewal(){
      let condition={
        endTime:this.approvalForm.endTime,
        id:this.electronicCheck[0].id,
      }
      let param ={
        condition:JSON.stringify(condition),
        applyType:1
      }
      archiveRenewalOfMaturity(param).then(response=>{
        if(response.code==200){
          this.$message({
            message:"到期续查申请成功",
            type:"success"
          })
          this.archivesaFunction()
          this.viewDetalsVasable1=false
        }else{
          this.$message({
            message:"到期续查申请失败",
            type:"error"
          })
        }
      })
    },
    // 申请提交
    onGrid(addForm) {
      const _this = this
      this.$refs[addForm].validate((valid) => {
        if (valid) {
          if(this.queryData.length==0){
            this.$message({
              type:"error",
              message:"请添加需要申请的档案"
            })
            return;
          }
          let returnMap = {
            formValues:this.addForm,
            gridValues:this.queryData,
            applyType:2
          }
          let testFormData = new FormData();
          testFormData.append("jsonStr",JSON.stringify(returnMap))
          if(this.$refs.imageFile.files[0]!=null){
            testFormData.append("files",this.$refs.imageFile.files[0])
          }
          checkBorrowEntityFile(testFormData).then(response =>{
              if(response.code==200){
                this.$message({
                  type:"success",
                  message:"申请成功"
                })

                //暂时注释
                this.formShow=false;
                this.tabsShow=true;
                this.addForm={};
                this.queryData=[];
                this.searchChek=[];
                this.searchTableData=[]
                this.pageNum=1;
                this.archivesaFunction();
              }else{
                this.$message({
                  type:"error",
                  message:"申请失败"
                })
              }
            })




                      // checkBorrowEntityNFile({"jsonStr":JSON.stringify(returnMap)}).then(response =>{
            //   if(response.code==200){
            //     this.$message({
            //       type:"success",
            //       message:"申请成功"
            //     })
            //     this.formShow=false;
            //     this.tabsShow=true;
            //     this.addForm={};
            //     this.queryData=[];
            //     this.searchTableData=[]

            //     this.archivesaFunction({
            //       pageNum: this.pageNum,
            //       pageSize: this.pageSize,
            //       status:this.applyStates,
            //     });
            //   }else{
            //     this.$message({
            //       type:"error",
            //       message:"申请失败"
            //     })
            //   }
            // })

        } else {
          this.$message({
            type:"warning",
            message:"请按规范填写表单"
          })
        }
      })
    },

    //table表格数据
    archivesaFunction(all) {
      if(this.applyStates=="0"||all){
        let pageSizeParam={
          pageSize:this.pageSize0,
          pageNum:this.pageNum0,
          searchValue:this.searchValue,
          status:0,
        }
        getEntityArchiveCApplyRecord(pageSizeParam).then(response =>{
          this.BorrowingData0=response.rows
          this.total0 = response.total
          this.loading = false
        })
      }
      if(this.applyStates=="1"||all){
        let pageSizeParam={
          pageSize:this.pageSize1,
          pageNum:this.pageNum1,
          searchValue:this.searchValue,
          status:1
        }
        getEntityArchiveCApplyRecord(pageSizeParam).then(response =>{
          this.BorrowingData1=response.rows
          this.total1 = response.total
          this.loading = false
        })
      }
      if(this.applyStates=="-1"||all){
        let pageSizeParam={
          pageSize:this.pageSize2,
          pageNum:this.pageNum2,
          searchValue:this.searchValue,
          status:-1
        }
        getEntityArchiveCApplyRecord(pageSizeParam).then(response =>{
          this.BorrowingData2=response.rows
          this.total2 = response.total
          this.loading = false
        })
      }


    },


    //返回待审批页面
    onBack(){
        this.tabsShow = true
        this.formShow = false
    },

    typeClick() {

      let gridDataList = JSON.parse(JSON.stringify(this.searchChek))
      let datalist=[];
      let a01s = [];
      for (let i = 0; i < gridDataList.length; i++) {
        let flag = true;
        for(let n in this.queryData){
          if(this.queryData[n].a01Id==this.searchChek[i].id){
            a01s.push(this.searchChek[i]);
            flag=false;
          }
        }
        for(let y in this.searchTableData){
              if(this.searchTableData[y].id==this.searchChek[i].id){
                this.searchTableData.splice(y,1);
              }
            }
        if(flag){
          gridDataList[i].e01z1Id = 'zb'
          gridDataList[i].a01Id=this.searchChek[i].id
          gridDataList[i].e01z111a="整本"
          gridDataList[i].archivesType="实体档案"
          datalist.push(gridDataList[i])
        }
      }
      this.queryData = datalist
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


    // 初始化转换参数
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
      // 获取职务
      queryDictionaryByType({ dictType: 'ZB09', eparentCode: '0' })
        .then(response => {
          this.dutys = response.data
        })
    },

     // 格式转换
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

        //职务
        if(col.property=="duty"){
          let returnValues='';
          this.dutys.forEach(data => {
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

        // return row[col.property]
      },
    // 部门查询
    // 左侧干部 部门 搜索
    search(type) {

      if (type === 1) {
        this.dpbutton=false
        this.searchText = this.formInline.deptName
      } else {
        if(this.formInline.a0101 == null || this.formInline.a0101==''){
            this.$message({
              message:'请输入干部搜索条件',
              type:'warning'
            })
            return ;
        }
        this.searchText = this.formInline.a0101
        this.dpbutton=true

      }
      this.departOrPerson = type;
      queryA01List({ searchText: this.searchText, type: type ,state:this.a01state})
        .then(response => {
          this.searchTableData = response.data
          this.beforeQueryData = response.data
        })
    },
    // 搜索结果 复选框
    gridSelectionChange(val) {
      this.searchChek = val
    },

    // 搜索结果显示
    handleGridChange(row) {
      if(this.isa01 == 1){
        return;
      }
      if (row) {
        this.$refs.searchTableData1.toggleRowSelection(row)
      } else {
        this.$refs.searchTableData1.clearSelection()
      }
    },
    // grid  双击部门 查询人员
    deptClick(val) {
      if (val.children) {

        this.searchTableData = val.children
        this.beforeQueryData = val.children
        this.departOrPerson=2;
        this.dpbutton=true
      }
    },
    fileChange(val){
      if(val.target.value==''){
        this.addForm.fileName = "未选择文件"
      }else{
        this.addForm.fileName = val.target.value
      }
    },
     //干部选择
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
    },


    // 档案借阅监听
    // 待审批
    // 分页大小
    handleSizeChange(val) {
      if(this.applyStates=="0"){
        this.pageSize0 = val
      }else if(this.applyStates=="1"){
        this.pageSize1 = val
      }else{
        this.pageSize2 = val
      }
      this.archivesaFunction(false)

    },
    // 分页监听
    handleCurrentChange(val) {
      if(this.applyStates=="0"){
        this.pageNum0 = val
      }else if(this.applyStates=="1"){
        this.pageNum1 = val
      }else{
        this.pageNum2 = val
      }
      this.archivesaFunction(false)
    },

     //获取当前登录用户
    getLoginUser(){
      queryLoginUser().then(response=>{
        if(response.code==200){
          this.loginUser = response.data;
        }
      })
    },
    //切换tab页
    tabClick(tab, event) {
      this.BorrowingData=[];
      if (tab.label === '待审批') {
        this.applyStates="0"
        this.searchRow = 'searchRow1';
      } else if (tab.label === '已通过') {
        this.applyStates="1"
        this.searchRow = 'searchRow2';
      }else{
        this.applyStates="-1"
        this.searchRow = 'searchRow3';
      }
    },

    //搜索
    handleQueryBorrowing(){
      if(this.applyStates=="0"){
        this.pageNum0=1;
        this.searchValue = JSON.stringify(this.borrowingParams0);
      }else if(this.applyStates=="1"){
        this.pageNum1=1;
        this.searchValue = JSON.stringify(this.borrowingParams1);
      }else{
        this.pageNum2=1;
        this.searchValue = JSON.stringify(this.borrowingParams2);
      }
      this.archivesaFunction(false);
    },
    //重置表单
    resetQueryBorrowing(){
      this.searchValue="";
      this.pageNum=1;
      this.archivesaFunction()
    },
    //发起申请
    handleAddBorrowing() {
      this.initAproveData();
      this.tabsShow = false
      this.formShow = true
    },
    initAproveData(){
      this.resetAddForm();
      this.formInline={
        deptName:"",
        a0101:"",
      }
      this.a01state="0";
      this.searchTableData=[];
      this.queryData=[];
    },

    resetAddForm(){
      this.addForm={
        startTime:"",
        endTime:"",
        e01z824a:"",
        userCard:"",
        userPhone:"",
        applicationReasons:"",
        printContents:"",
        e01z831:"",
        userName:this.loginUser.userName,
        userId:this.loginUser.userId,
        fileName:"未选择文件"
      }
    },

    //导出
    handleExportBorrowing(){
      if(this.electronicCheck.length==0){
        this.$confirm('是否导出搜索后的申请数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let searchText = JSON.stringify(this.borrowingParams);
          let params = {
            pageNum: 1,
            pageSize: 999999999,
            status:this.applyStates,
            searchValue:searchText
          }
          getEntityArchiveCApplyRecord(params).then(response =>{
            console.log(response.rows)
            if(response.rows.length==0){
              this.$message.error("无数据,导出失败");
              return;
            }
            let row = {
              row: response.rows,
              status: 12
            }
            downLoadPost("/export/excel/exportClick", row,this)
          })
        })

      }else{
        this.$confirm('是否导出选中的申请数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          console.log(this.electronicCheck)
          let row = {
            row: this.electronicCheck,
            status: 12
          }
          downLoadPost("/export/excel/exportClick", row,this)
        })

      }


      // let s = JSON.stringify(params);
      // console.log(s);
      // s = encodeURI(s);
      // downLoad("/export/excel/export?status=4&exportData=" + s)
    },
    //打印
    handlePrintBorrowing(){

    },
    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
    },
    // grid 选中
    handleSelectionChange(val) {
      this.electronicCheck = val
    },
  },
}
</script>
<style lang="scss" scoped>
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

  .form-width{
      width:40%;
      display:flex;
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

  // 到期续查弹窗
  .dialog-state {
    position: relative;
    height: 180px;

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
.table-style{
    /deep/ .el-table__fixed-right-patch {
    background-color:#F8F8F9;
    }
  }
   /deep/ .el-table .el-table__header-wrapper th{
  height:31px;
}
 //设置统一表头
    /deep/.el-table tbody tr:hover>td {
      background-color:#ecf5ff !important
    }

  /deep/.el-table__body td {
     padding: 0;
     height: 40px;
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

<template>
  <div :class="electronInspection">
    <!--    等待审批窗口-->
    <el-tabs
      v-if="tabsShow"
      id="elTabs"
      v-model="activeName"
      :tab-position="tabPosition"
      style="min-height: calc(100vh - 86px);border: 1px solid #CCCCCC"
      @tab-click="tabClick"
    >


      <el-tab-pane label="待审批" name="pendapproval">
        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px);">
          <el-row   :gutter="10" ref="searchRow" style="margin: 0px">
            <el-form
              v-hasPermi="['checkBorrowApply:electronic:search']"
              v-if="showBorrowing"
              ref="borrowingParams"
              class="search-form"
              :model="borrowingParams0"
              label-width="110px"
              :inline="true"
            >
              <!--              <el-form-item label="申请人姓名" prop="deptName" style="margin-left: 20px;margin-top: 10px;margin-bottom: 10px">-->
              <!--                <el-input-->
              <!--                  v-model="borrowingParams.userName"-->
              <!--                  placeholder="请输入申请人姓名"-->
              <!--                  clearable-->
              <!--                  size="mini"-->
              <!--                  style="width: 150px"-->
              <!--                />-->
              <!--              </el-form-item>-->
              <el-form-item label="申请查阅时间起" prop="beginTime">
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
              <el-form-item label="申请查阅时间至" prop="endTime">
                <el-date-picker
                  v-model="borrowingParams0.endTime"
                  placeholder="请选择时间"
                  clearable
                  value-format="yyyy-MM-dd"
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryBorrowing">搜索
                </el-button>
              </el-form-item>
            </el-form>
          </el-row>
          <div  class="btn">
            <el-button type="primary" size="mini" icon="el-icon-plus"  v-hasPermi="['checkBorrowApply:electronic:startApply']"  @click="aproval">发起申请</el-button>
            <el-button type="primary" size="mini" icon="el-icon-download" v-hasPermi="['checkBorrowApply:electronic:exprot']" @click="handleExportBorrowing">导出</el-button>
          </div>
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            :height="tableHeight"
            border
            stripe
            size="mini"
            v-loading="loading"
            tooltip-effect="light"
            :header-cell-style="headClass"
            style="width: 100%"
            empty-text=" "
            :data="ElectronicData0"
            @selection-change="handleSelectionChange"
            @row-click="handlerBorrowClickRow"
            ref="BorrowingData"
          >
            <!--            <el-table-column type="expand">-->
            <!--            </el-table-column>-->
            <!--                动态获取题名-->
            <!--            <el-table-column type="selection" width="55"  v-for=" i index in queryData"></el-table-column>-->
            <el-table-column align="center" type="selection" width="40"/>

            <el-table-column
              :index="customIndex0"
              label="序号"
              type="index"
              :resizable="false"
              align="center"
              width="50">
            </el-table-column>

            <el-table-column
              align="center"
              v-for="(item, index) in BorrowingColumn"
              v-if="item.isCol"
              show-overflow-tooltip
              :key="index"
              :label="item.label"
              :prop="item.prop"
              :width="item.width"

              :resizable="false"
            />
            <el-table-column fixed="right" align="center" label="操作" min-width="100">
              <template slot-scope="scope" >
                <el-button type="text" size="mini" @click="adoptViewDetals(scope.row)">查看详情</el-button>
                <!--                            <el-button type="text" size="mini" @click="borrowingReturn(scope.row)">归还</el-button>-->
                <!--                            <el-button type="text" size="mini" @click="borrowingFeedback(scope.row)">反馈</el-button>-->
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
          <el-row  :gutter="10" ref="searchRow2"  style="margin: 0px">
            <el-form
              v-if="showBorrowing"
              v-hasPermi="['checkBorrowApply:electronic:search']"
              ref="borrowingParams"
              class="search-form"
              label-width="110px"
              :model="borrowingParams1"
              :inline="true"
            >
              <!--              <el-form-item label="申请人姓名" prop="deptName" style="margin-left: 20px;margin-top: 10px;margin-bottom: 10px">-->
              <!--                <el-input-->
              <!--                  v-model="borrowingParams.userName"-->
              <!--                  placeholder="请输入申请人姓名"-->
              <!--                  clearable-->
              <!--                  size="mini"-->
              <!--                  style="width: 150px"-->
              <!--                />-->
              <!--              </el-form-item>-->
              <el-form-item label="申请查阅时间起" prop="beginTime">
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
              <el-form-item label="申请查阅时间至" prop="endTime">
                <el-date-picker
                  v-model="borrowingParams1.endTime"
                  placeholder="请选择时间"
                  clearable
                  value-format="yyyy-MM-dd"
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryBorrowing">搜索
                  <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQueryBorrowing('borrowingParams')">重置 -->
                </el-button>
              </el-form-item>
            </el-form>
            <!--            <el-col :span="1.5">-->
            <!--              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddBorrowing">新增</el-button>-->
            <!--            </el-col>-->
            <!--            @click="handleUpdateBorrowing-->
            <!--            <el-col :span="1.5">-->
            <!--              <el-button type="primary" icon="el-icon-edit" size="mini" >到期续查</el-button>-->
            <!--            </el-col>-->
            <!--            <el-col :span="1.5">-->
            <!--              <el-button type="primary" icon="el-icon-edit" size="mini" @click="addApprovalRecord" >添加查阅记录</el-button>-->
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
          <div  class="btn">
            <el-button type="primary" icon="el-icon-receiving" size="mini" v-hasPermi="['checkBorrowApply:electronic:renewal']"  @click="renew">到期续查
            </el-button>
            <el-button type="primary" icon="el-icon-download" size="mini" v-hasPermi="['checkBorrowApply:electronic:exprot']"  @click="handleExportBorrowing">导出
            </el-button>
          </div>
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            :height="tableHeight"
            size="mini"
            tooltip-effect="light"
            v-loading="loading"
            :header-cell-style="headClass"
            style="width: 100%"
            :data="ElectronicData1"
            empty-text=" "
            @selection-change="handleSelectionChange"
            @row-click="handlerBorrowClickRow"
            ref="BorrowingData2"
          >
            <!--            <el-table-column type="expand">-->

            <!--            </el-table-column>-->
            <!--                动态获取题名-->
            <!--            <el-table-column type="selection" width="55"  v-for=" i index in queryData"></el-table-column>-->
            <el-table-column :resizable="false" type="selection" width="40"/>
            <el-table-column
              label="序号"
              :index="customIndex1"
              :resizable="false"
              align="center"
              type="index"
              width="50">
            </el-table-column>
            <el-table-column
              align="center"
              show-overflow-tooltip
              v-for="(item, index) in BorrowingColumn2"
              :v-if="item.isCol"
              :key="index"
              :resizable="false"
              :label="item.label"
              :prop="item.prop"
              :width="item.width"
            />
            <el-table-column fixed="right" :resizable="false" align="center" label="操作" width="100">
              <template slot-scope="scope">
                <!--                @click="viewDetals(scope.row)"-->
                <el-button  type="text" size="mini"  v-hasPermi="['checkBorrowApply:electronic:details']"
                           @click="adoptViewDetals(scope.row)">查看详情
                </el-button>
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
          <div class="block">
            <el-pagination
              :page-sizes="[20, 50, 100, 200]"
              :page-size="pageSize1"
              layout="total, sizes, prev, pager, next, jumper"
              :current-page="pageNum1"
              :total="total1"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-col>
      </el-tab-pane>
      <el-tab-pane label="未通过" name="notpass">
        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px)">
          <el-row  :gutter="10" ref="searchRow3"  style="margin: 0px">
            <el-form
              v-hasPermi="['checkBorrowApply:electronic:search']"
              v-if="showBorrowing"
              ref="borrowingParams"
              label-width="110px"
              class="search-form"
              :model="borrowingParams2"
              :inline="true"
            >
              <!--              <el-form-item label="申请人姓名" prop="deptName" style="margin-left: 20px;margin-top: 10px;margin-bottom: 10px">-->
              <!--                <el-input-->
              <!--                  v-model="borrowingParams.userName"-->
              <!--                  placeholder="请输入申请人姓名"-->
              <!--                  clearable-->
              <!--                  size="mini"-->
              <!--                  style="width: 150px"-->
              <!--                />-->
              <!--              </el-form-item>-->
              <el-form-item label="申请查阅时间起" prop="beginTime">
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
              <el-form-item label="申请查阅时间至" prop="endTime">
                <el-date-picker
                  v-model="borrowingParams2.endTime"
                  placeholder="请选择时间"
                  clearable
                  size="mini"
                  value-format="yyyy-MM-dd"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryBorrowing">搜索
                </el-button>
                <!-- <el-button icon="el-icon-refresh" size="mini" @click="resetQueryBorrowing('borrowingParams')">重置 -->
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
          <div  class="btn">
            <el-button type="primary" icon="el-icon-download" size="mini" v-hasPermi="['checkBorrowApply:electronic:exprot']"  @click="handleExportBorrowing">导出
            </el-button>
          </div>
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            :height="tableHeight"
            style="width: 100%"
            size="mini"
            v-loading="loading"
            :header-cell-style="headClass"
            empty-text=" "
            tooltip-effect="light"
            :data="ElectronicData2"
            @selection-change="handleSelectionChange"
            @row-click="handlerBorrowClickRow"
            ref="BorrowingData3"
          >
            <!--            <el-table-column type="expand">-->

            <!--            </el-table-column>-->
            <!--                动态获取题名-->
            <!--            <el-table-column type="selection" width="55"  v-for=" i index in queryData"></el-table-column>-->
            <el-table-column type="selection" :resizable="false" width="40"/>
            <el-table-column
              label="序号"
              type="index"
              :index="customIndex2"
              align="center"
              :resizable="false"
              width="50">
            </el-table-column>
            <el-table-column
              align="center"
              v-for="(item, index) in BorrowingColumn2"
              show-overflow-tooltip
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
                <el-button  v-hasPermi="['checkBorrowApply:electronic:details']"  type="text" size="mini" @click="adoptViewDetals(scope.row)">查看详情</el-button>
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


    <el-row v-if="apply">
      <el-col style="width:100%">
        <div class="pi-form" style="width:100%;margin: 0 auto">
          <div
            style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">
            第一步：填写申请人基本信息
          </div>
          <el-col :span="24">
            <el-form ref="form" :model="form" :rules="rules" label-width="150px"
                     style="padding: 20px 0 0 0;width: 75%;margin: 0 auto;padding-left: 3%;padding-right: 3%">
              <el-col :span="8">
                <el-form-item label="申请人姓名" :span="8" prop="userName">
                  <el-input size="mini" v-model="form.userName" style="width: 100%;" placeholder="请输入申请人姓名"/>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="申请人证件号" :span="8" prop="userCard">
                  <el-input size="mini" v-model="form.userCard" style="width: 100%;" placeholder="请输入申请人身份证号"/>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="申请人所在单位" :span="8" prop="e01z824a">
                  <el-input size="mini" v-model="form.e01z824a" style="width: 100%;" placeholder="请输入申请人所在单位"/>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="申请人电话" :span="8" prop="userPhone">
                  <el-input size="mini" maxlength="11" v-model="form.userPhone" style="width: 100%;" placeholder="请输入申请人电话"/>
                </el-form-item>
              </el-col>


              <el-col :span="8">
                <el-form-item label="申请理由" :span="8" prop="applicationReasons">
                  <el-input size="mini" v-model="form.applicationReasons" style="width: 100%;" placeholder="请输入申请人理由"/>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="申请查阅截止时间至" :span="8" prop="endTime">
                  <el-date-picker
                    size="mini"
                    style="width: 100%;"
                    v-model="form.endTime"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    default-time="12:00:00">
                  </el-date-picker>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="申请人职务" :span="8" prop="duty">
                  <el-input size="mini" v-model="form.duty" style="width: 100%;" placeholder="请输入申请人职务"/>
                </el-form-item>
              </el-col>


              <el-col :span="8">
                <el-form-item label="打印内容" :span="8" prop="printRemark">
                  <el-input v-model="form.printRemark" type="textarea" style="width: 100%;" placeholder="若打印请输入打印内容"/>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="备注" :span="8" prop="e01z831">
                  <el-input v-model="form.e01z831" type="textarea" style="width: 100%;" placeholder="请输入备注"/>
                </el-form-item>
              </el-col>
            </el-form>
          </el-col>
        </div>
        <div
          style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">
          第二步：请选择需要查阅的干部档案
        </div>
        <div style="width:75%;margin: 0 auto;padding:25px 0 0 0">
          <div style="width: 100%">
            <el-form :inline="true" style="width: 100%;">

              <el-form-item label="干部状态" style="margin-left: 20px" prop="state">
                <el-select v-model="a01state" placeholder="请选择" clearable size="mini" style="width: 150px">
                  <el-option v-for="item in states" :key="item.dictCode" :label="item.fullName" :value="item.dictCode">
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="" class="input-w">
                <el-input v-model="cadresText" @keyup.enter.native="search(1)" placeholder="请输入部门" size="mini"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="search(1)" size="mini">部门搜索</el-button>
              </el-form-item>

              <el-form-item label="" class="input-w">
                <el-input v-model="personnelText" @keyup.enter.native="search(2)" placeholder="请输入人员" size="mini"/>
              </el-form-item>

              <el-form-item>
                <el-button type="primary" @click="search(2)" size="mini">干部搜索</el-button>
              </el-form-item>


            </el-form>
          </div>
          <!-- 左边表格数据 -->
          <el-table :data="leftData" border style="width: 100%;"
                    :header-cell-style="headClass"
                    stripe
                    tooltip-effect="light"
                    empty-text=" "
                    height="222px" @cell-dblclick="deptClick" ref="searchTableData">
            <el-table-column style="width: 1%" type="index" align="center" label="序号"/>
            <el-table-column show-overflow-tooltip prop="a0101" v-if="isa01 == 2" label="干部姓名" align="center"/>
            <!-- <el-table-column prop="number" v-if="isa01 == 2" label="干部编号" align="center"/> -->
            <el-table-column show-overflow-tooltip prop="deptName" v-if="isa01 == 2" label="所属部门" align="center"/>
            <el-table-column show-overflow-tooltip prop="deptName" v-if="isa01 == 1" label="部门名称" align="center"/>
            <el-table-column show-overflow-tooltip prop="parentId" v-if="isa01 == 1" label="上级部门" align="center"/>


            <el-table-column show-overflow-tooltip prop="a0104" v-if="isa01 == 2" label="性别" align="center" :formatter="getShowData"/>
            <el-table-column show-overflow-tooltip prop="a0107" v-if="isa01 == 2" label="出生日期" align="center" :formatter="getShowData"/>
            <el-table-column show-overflow-tooltip prop="state" v-if="isa01 == 2" label="干部状态" align="center"
                             :formatter="stateFormat"
            />
            <el-table-column v-if="isa01 == 2" label="操作" align="center" width="200">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="wholeConsult(scope.row)">整本查阅</el-button>
                <el-button type="text" size="mini" @click="groupConsult(scope.row)">分类查阅</el-button>
                <el-button type="text" size="mini" @click="materConsult(scope.row)">材料查阅</el-button>
              </template>
            </el-table-column>
          </el-table>

        </div>
        <div
          style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">
          第三步：确认申请信息
        </div>
        <div style="width:75%;margin: 0 auto;padding:25px 0 0 0">
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            empty-text=" "
            border
            tooltip-effect="light"
            ref="applicationData"
            :header-cell-style="headClass"
            style="margin: 0 auto;width: 100%"
            :data="applicationData"
            height="222px"
          >

            <el-table-column align="center" width="50" type="index" label="序号"/>
            <el-table-column show-overflow-tooltip v-if="true" label="被申请干部姓名" align="center" prop="a0101" width="130"/>
            <!-- <el-table-column v-if="true" label="被申请干部编号" prop="number" width="130">
            </el-table-column> -->
            <el-table-column show-overflow-tooltip prop="deptName" width="150" label="被申请干部所属部门" align="center"/>
            <el-table-column show-overflow-tooltip v-if="true" label="被申请干部性别" align="center" prop="a0104" width="130"
                             :formatter="getShowData">
            </el-table-column>
            <el-table-column show-overflow-tooltip v-if="true" label="被申请干部出生日期" align="center" prop="a0107" :formatter="getShowData"
                             width="150">
            </el-table-column>
            <el-table-column show-overflow-tooltip v-if="true" label="被申请干部档案状态" align="center" prop="state" :formatter="getShowData"
                             width="150">
            </el-table-column>
            <el-table-column show-overflow-tooltip v-if="true" label="被申请查阅类型" align="center" prop="contype" :formatter="getShowData"
                             width="130">
            </el-table-column>
            <el-table-column v-if="true" label="查阅内容" :show-overflow-tooltip="true" prop="e01z1_name"
                             :formatter="getShowData" >
            </el-table-column>
            <el-table-column label="操作" align="center" width="100" fixed="right">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="electronicFeedback(scope.row)">移除</el-button>
              </template>
            </el-table-column>
          </el-table>

        </div>
        <div style="padding: 20px 0 0 0;width: 75%;margin: 0 auto">
          <el-button type="primary" style="float: right;margin-right: 10px;width:80px" size="mini"
                     @click="comitGridValues">发起申请
          </el-button>

          <el-button style="float: right;margin-right: 10px;width:80px" size="mini" @click="backCall">返回</el-button>
        </div>


      </el-col>
    </el-row>



    <!-- 档案分类查阅窗口 -->
    <el-dialog title="选择需要查阅的档案分类" height="500px" :close-on-click-modal="false" :visible.sync="dialogVisible2"
               class="dialog-style">
      <el-tree class="chenge-tree" node-key="id" ref="tree2" default-expand-all :highlight-current="true" show-checkbox
               :data="data2"
               icon-class="el-icon-folder-opened"/>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false" size="mini">取 消</el-button>
        <el-button type="primary" size="mini" @click="changeOtherTreeNode">确定</el-button>
      </span>
    </el-dialog>

    <!--单份材料查阅窗口 -->
    <el-dialog title="选择需要查阅的材料" height="500px" :close-on-click-modal="false" :visible.sync="dialogVisible1"
               class="dialog-style">
      <el-scrollbar style="height: 500px;">
        <el-tree height="600px" class="chenge-tree" @check="e01z1Check" node-key="id" ref="tree1" default-expand-all
                 :highlight-current="true" show-checkbox :data="data1"
                 icon-class="el-icon-folder-opened"/>
      </el-scrollbar>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false" size="mini">取 消</el-button>
        <el-button type="primary" size="mini" @click="changeA01E01z1s">确定</el-button>
      </span>
    </el-dialog>

    <!--    到期续查-->
    <el-dialog title="到期续查" width="30%" class=" dialog-style dialog-basic" :visible.sync="renewVisible">
      <div class="dialog-state">
        <span class="state">填写查阅截止时间</span>
        <el-form ref="renewform" :rules="renewformRules" :model="renewForm" style="width:90%;margin-top:11%;margin-left: 5%" label-width="120px">
          <el-form-item  prop="temperature" label="查阅截止时间">
            <el-date-picker
              style="width:100%"
              :disabled="true"
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
              style="width:100%"
              v-model="renewForm.date"
              type="datetime"
              size="mini"
              placeholder="选择日期时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              default-time="12:00:00">
            </el-date-picker>
          </el-form-item>
        </el-form>
      </div>
      <!--      <div class="form-basic">续查信息</div>-->
      <!--      <el-form ref="form" :model="renewForm" style="margin-left: 5%;margin-right: 5%"   :inline="true"  label-width="120px">-->

      <!--        <el-form-item prop="temperature" label="查阅截止日期">-->
      <!--          <el-date-picker-->
      <!--            style="width: 200px"-->
      <!--            :disabled="true"-->
      <!--            v-model="renewForm.ldate"-->
      <!--            type="datetime"-->
      <!--            placeholder="选择日期时间"-->
      <!--            value-format="yyyy-MM-dd HH:mm:ss"-->
      <!--            default-time="12:00:00">-->
      <!--          </el-date-picker>-->
      <!--        </el-form-item>-->

      <!--        <el-form-item prop="temperature" label="续查时间至">-->
      <!--          <el-date-picker-->
      <!--            style="width: 200px"-->
      <!--            v-model="renewForm.date"-->
      <!--            type="datetime"-->
      <!--            placeholder="选择日期时间"-->
      <!--            value-format="yyyy-MM-dd HH:mm:ss"-->
      <!--            default-time="12:00:00">-->
      <!--          </el-date-picker>-->
      <!--        </el-form-item>-->

      <!--      </el-form>-->
      <div slot="footer" class="dialog-footer">
        <!--        <el-button size="mini" @click="viewDetalsVasable2 = false ">关闭</el-button>-->
        <el-button size="mini" type="primary" @click="renewSave">提交申请</el-button>


      </div>
    </el-dialog>

    <!--    查看材料详情-->
    <el-dialog title="查阅详情" width="90%" class=" dialog-style dialog-basic" :visible.sync="viewDetalsVasable3">
      <!--      <div class="form-basic">申请信息</div>-->
      <el-col style="margin-left: 2%" :span="1.5">
        <el-button type="primary" icon="el-icon-download" size="mini">导出</el-button>
      </el-col>
      <el-col style="margin-left: 1%" :span="1.5">
        <el-button type="primary" icon="el-icon-download" size="mini">打印</el-button>
      </el-col>

      <!--      <el-col  :span="24" :xs="24" style="margin: 0;padding: 0;border: 1px solid #cccccc;min-height: calc(100vh - 86px)">-->
      <el-table :data="adoptData"
                border
                style="width: 95%; margin-left: 2%;margin-top: 50px;margin-bottom: 20px"
                height="400px"
                stripe
                size="mini"
                tooltip-effect="light"
                :header-cell-style="headClass"
                ref="adoptData">
        <el-table-column type="selection" width="40"/>
        <el-table-column type="index" width="50" align="center" label="序号"/>
        <el-table-column prop="a0101" label="被申请人姓名" align="center"/>
        <el-table-column prop="deptName" label="被申请人所在部门" align="center"/>
        <el-table-column prop="e01z111a" :show-overflow-tooltip="true" label="申请内容" align="center"/>
        <el-table-column v-if="archivesType == 1" prop="adoptE01z1Msg" :show-overflow-tooltip="true"
                         :formatter="function(row, column, cellValue, index){
                           if(row.adoptE01z1Msg==null||row.adoptE01z1Msg==''){
                             return row.e01z111a
                           }else{
                             return cellValue;
                           }
                         }"
                         label="申请通过内容" align="center"/>
        <el-table-column prop="approvTime" min-width="120px" label="申请时间" align="center"/>
        <el-table-column prop="endDate" min-width="120px" label=" 申请查阅截止时间至" align="center"/>

        <el-table-column v-if="archivesType == 1" label="操作" align="center" width="200">
          <template slot-scope="scope">
            <router-link :to="'/browse/approvalLook/' + scope.row.a01Id + '/' + scope.row.id + '/' + scope.row.a0101"
                         class="link-type">
              <span>查看档案材料</span>
            </router-link>
          </template>
        </el-table-column>

      </el-table>

      <!--      </el-col>-->

      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="viewDetalsVasable3 = false ">关闭</el-button>
        <!--        <el-button size="mini" type="primary" @click="agree2">同意</el-button>-->
        <!--        <el-button size="mini" type="primary" @click="noAgree2">不同意</el-button>-->

      </div>
    </el-dialog>


  </div>

</template>
<script>
  import {downLoad, downLoadPost} from '@/downLoad/filedownload'
  import {queryE01zcRelaTreeByE01zcId} from '@/api/usual/usage/borrow'
  import {queryDictionaryByType, queryDictionaryByTypes, queryE01z1TreeGrid} from '@/api/usual/receive'
  import {e01zcApproval, getApprovals} from '@/api/usual/usage/electronic-access-approval'
  import {queryA01List} from '@/api/usual/transfer'
  import {queryLoginUser} from '@/api/usual/receive'

  import {queryE01zcRelaData, addE01zcRela, check, renew, getE01zcRela} from '@/api/usual/provisonalInspect'
  const baseUrl = process.env.VUE_APP_BASE_API
  const datalist = []
  export default {
    name: '电子档案查阅申请',
    watch: {
      leftData: {
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(() => {
            if(_this.$refs.searchTableData!=null){
              _this.$refs.searchTableData.doLayout();
              _this.$refs.searchTableData.bodyWrapper.scrollTop = 0;
            }
          })
        },
        deep: true,
        // immediate:true

      },
      applicationData: {
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(() => {
            if(_this.$refs.applicationData!=null){
              _this.$refs.applicationData.doLayout();
            }
          })
        },
        deep: true,
        // immediate:true
      },
      ElectronicData: {
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(() => {
            if(_this.$refs.BorrowingData!=null){
              _this.$refs.BorrowingData.doLayout();
            }
            if(_this.$refs.BorrowingData2!=null){
              _this.$refs.BorrowingData2.doLayout();
            }
            if(_this.$refs.BorrowingData3!=null){
              _this.$refs.BorrowingData3.doLayout();
            }

          })
        },
        deep: true,
        // immediate:true
      },
      adoptData: {
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(() => {
            if(_this.$refs.adoptData!=null){
              _this.$refs.adoptData.doLayout();
            }
          })
        },
        deep: true,
        // immediate:true
      },


    },
    data() {
      return {
        updateData:{},
        uploadUrl:baseUrl+"/e01z1/upload",

        renewformRules:{
          date:[
            { required: true, message: '请选择续查时间', trigger: 'blur' },
          ]
        },
        rules:{
          endTime:[
            { required: true, message: '请选择申请查阅截止时间至', trigger: 'blur' },
          ],
          userName:[
            { required: true, message: '请输入申请人姓名', trigger: 'blur' },
            { min: 0, max: 18, message: '申请人姓名不得超过18个汉字', trigger: 'blur' }
          ],
          userCard:[
            { required: true, message: '请输入申请人证件号', trigger: 'blur' },
            { min: 0, max: 18, message: '申请人证件号不得超过18个字符', trigger: 'blur' }
          ],
          e01z824a:[
            { required: true, message: '请输入申请人所在单位', trigger: 'blur' },
            { min: 0, max: 40, message: '申请人所在单位不得超过40个汉字', trigger: 'blur' }
          ],
          userPhone:[
            { required: true, message: '请输入申请人电话', trigger: 'blur' },
            {
              pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
              message: "请输入正确的电话号码",
              trigger: "blur"
            }
          ],
          applicationReasons:[
            { required: true, message: '请输入申请理由', trigger: 'blur' },
            { min: 0, max: 200, message: '申请理由不得超过200个汉字', trigger: 'blur' }
          ],
          duty:[
            { min: 0, max: 40, message: '申请人职务不得超过40个汉字', trigger: 'blur' }
          ],
          printRemark:[
            { min: 0, max: 200, message: '打印内容不得超过200个汉字', trigger: 'blur' }
          ],
          e01z831:[
            { min: 0, max: 200, message: '备注不得超过200个汉字', trigger: 'blur' }
          ]

        },
        electronInspection: 'electron-inspection',
        adoptData: [],
        //申请页面需要的data
        renewForm: {
          date: '',
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

        renewVisible: false,
        userId: '',
        userName: '',
        approvalData: [],
        approval: false,
        data1: [],
        data2: [],
        dialogVisible1: false,
        dialogVisible2: false,
        applicationData: [],
        personnelText: "",
        cadresText: "",
        a01state: '0',
        showSearchBox: true,
        states: [],
        a0104s1: [],
        apploveTitle: '正在等待审批，请稍等...',
        form: {},
        leftData: [],
        checkA01Data: {},
        isa01: 2,//搜索的类型  部门还是干部

        //等待页面需要data
        apply: false,
        showBorrowing: true,
        activeName: 'pendapproval',
        ElectronicData: [],
        divw: 'divw1',
        leftData: [],
        viewDetalsVasable2: false,
        viewDetalsVasable3: false,
        e01zcId: '',
        approvalForm: {
          approver: '',
          approverDate: '',
          approverTime: '',
          approverOpinion: '',
          personCharge: ''
        },
        e01zcTree: [],
        viewDetalsVasable: false,
        tableHeight: '76%',
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
        // 部门干部搜索 传参
        searchText: '',
        searchChek: [],
        a0104s: [],


        formLabelWidth: '130px',
        // 分页参数
        total: 0,
        pageNum: 1,
        pageSize: 20,

        // 右侧搜索
        formInline: {
          deptName: '',
          a0101: ''
        },
        // 搜索结果
        formShow: false,
        tabsShow: false,
        // tab 显示布局状态
        tabPosition: 'left',
        /* 遮罩 */
        loading: true,
        // 申请人列名
        BorrowingColumn: [
          {label: '申请人姓名', width: 100, isCol: true, prop: 'userName'},
          {label: '申请人证件号', width: 150, isCol: true, prop: 'userCard'},
          {label: '申请查阅理由', width: 100, isCol: true, prop: 'applicationReasons'},
          {label: '申请人所在单位', width: 150, isCol: true, prop: 'e01z824a'},
          {label: '申请人职务', width: 100, isCol: true, prop: 'duty'},
          {label: '申请查阅档案卷数', width: 150, isCol: true, prop: 'relaNum'},
          {label: '申请时间', width: 150, isCol: true, prop: 'approvTime'},
          {label: '申请查阅截止时间至', width: 150, isCol: true, prop: 'endDate'},
          {label: '申请人电话', width: 100, isCol: true, prop: 'userPhone'},
          {label: '申请类型', width: 100, isCol: true, prop: 'type'},
          {label: '打印内容', width: 200, isCol: true, prop: 'printRemark'},
          {label: '备注', isCol: true, prop: 'e01z831'},
        ],

        BorrowingColumn2: [
          {label: '申请人姓名', width: 100, isCol: true, prop: 'userName'},
          {label: '申请人证件号', width: 150, isCol: true, prop: 'userCard'},
          {label: '申请查阅理由', width: 100, isCol: true, prop: 'applicationReasons'},
          {label: '申请人所在单位', width: 150, isCol: true, prop: 'e01z824a'},
          {label: '申请人电话', width: 100, isCol: true, prop: 'userPhone'},
          {label: '申请人职务', width: 100, isCol: true, prop: 'duty'},
          {label: '申请查阅档案卷数 ', width: 150, isCol: true, prop: 'relaNum'},
          {label: '申请时间', width: 150, isCol: true, prop: 'approvTime'},
          {label: '申请查阅截止时间至', width: 150, isCol: true, prop: 'endDate'},
          {label: '申请类型', width: 100, isCol: true, prop: 'type'},
          {label: '查阅状态', width: 100, isCol: true, prop: 'lookType'},
          {label: '审批人', width: 100, isCol: true, prop: 'approver'},
          {label: '经办人', width: 100, isCol: true, prop: 'personCharge'},
          {label: '审批时间', width: 150, isCol: true, prop: 'approverDate'},
          {label: '审批意见', width: 200, isCol: true, prop: 'approverOpinion'},
          {label: '打印内容', width: 200, isCol: true, prop: 'printRemark'},
          {label: '备注', width: 200, isCol: true, prop: 'e01z831'},
        ],
        // 申请人数据
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        archivesType: 0,
        electronicCheck: [],
        a0117s: [],
        a0141s: [],
        borrowingParams: {
          a0101: '',
          deptName: '',
          pageSize: 20,
          pageNum: 1,
          type: '3'
        },
        searchRowHeight: '',
        searchRow: 'searchRow',
        queryBorrowingParams:{}
      }

    },
    mounted() {
      let _this = this;
      this.$nextTick(() => {
        setTimeout(() => {
          _this.searchRowHeight = _this.$refs[_this.searchRow].$el.offsetHeight
          _this.tableHeight = _this.tableHeight = (document.body.clientHeight - (84 + 100 + _this.searchRowHeight)) + "px"
        }, 1000)
      })


      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + "px"
        })();
      };

      // setTimeout(()=>{
      //   window.resizeTo(500,800);
      // },1000)

    },
    //字典表获取
    created() {
      this.initShowNum()
      this.initData()
      this.getQueryData()
      this.initColumnMap();
      // this.initData();
      queryLoginUser().then(response => {
        this.userId = response.data.userId;
        this.userName = response.data.userName;

        this.archivesaFunction(true)
      })

    },
    methods: {


      /**
       * 上传成功后方法
       * */
      success(response, file, fileList){
        if (200 !==response.code){
          alert(response.msg);
          return;
        }
        this.dialogFormVisible = false;
        this.search();
      },


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
      //添加时校验重复数据
      checlData(val) {
        let b = true;
        this.applicationData.map(item => {

          if (item.id == val.id) {

            b = false;
          }
        })
        if (!b) {
          this.$message({
            message: val.a0101 + '信息已添加',
            type: 'warning'
          })
        }
        return b;
      },
      renewSave() {
        this.$refs.renewform.validate(valid=>{
          if(!valid){
            return;
          }
          let params = {
            status: 0,
            endDate: this.renewForm.date,
            type: '到期续查',
            id: this.electronicCheck[0].id
          }
          renew(params).then(response => {
            if (response.code == 200) {
              this.$message({
                message: '操作成功',
                type: 'success'
              })
              this.renewVisible = false;
              this.renewForm.date = '';
              this.archivesaFunction();
            }
          })
        })

      },
      //到期续借
      renew() {
        if (this.electronicCheck.length == 0) {
          this.$message({
            message: '请选择一条数据',
            type: 'warning'
          })
          return;
        }
        if (this.electronicCheck.length > 1) {
          this.$message({
            message: '请选择一条数据',
            type: 'warning'
          })
          return;
        }
        this.renewForm.ldate = this.electronicCheck[0].endDate;
        this.renewVisible = true;
      },
      //申请页面所需方法
      stateFormat(row, column, value) {
        let str = '';
        this.states.map(item => {
          if (item.dictCode == value) {
            str = item.fullName;
          }
        })
        return str;
      },
      //点击查看详情事件
      //点击返回
      backCall() {
        this.apply = false;
        this.tabsShow = true;
        this.electronInspection = 'electron-inspection'
      },
      //重置发起申请
      initAprovalForm(){
        this.form={
          userName:this.userName,
          userId:this.userId,
          userCard:"",
          e01z824a:"",
          userPhone:"",
          applicationReasons:"",
          endTime:"",
          duty:"",
          printRemark:"",
          e01z831:""
        }
        this.a01state= '0',
          this.personnelText= "";
        this.cadresText= "";
        this.leftData=[];
        this.applicationData=[];
      },
      //新增申请
      aproval() {
        this.initAprovalForm();
        this.apply = true;
        this.tabsShow = false;
        this.electronInspection = 'electron-inspection2'
      },
      //材料选择选择事件
      e01z1Check(node, nodes) {
        //选中的为分类
        if (node.remark == 'catalog') {
          if (node.id == '9' || node.id == '4') {
            let b = false;
            node.children.map(item => {
              if (item.children.length > 0) {
                b = true;
              }
            })
            if (!b) {
              this.$refs.tree1.setChecked(node.id, false, true);
            }
          }
          if (node.children.length == 0) {
            //取消选中
            this.$refs.tree1.setChecked(node.id, false, false);
          }
        }
      },
      //提交按钮
      comitGridValues() {
        this.$refs.form.validate(valid=>{
          if(!valid){
            return;
          }

          //校验审批列表是否存在相同数据
          if (this.applicationData.length == 0) {
            this.$message({
              message: '请选择查阅内容',
              type: 'warning'
            })
            return;
          }
          let b = true;
          this.applicationData.map(item => {
            let i = 0;
            this.applicationData.map(item1 => {
              if (item1.id == item.id) {
                i++;
              }
            })
            if (i > 1) {
              b = false;

            }
          })
          if (!b) {
            this.$message({
              message: '表格中存在重复数据',
              type: 'warning'
            })
            return;
          }
          //像后台发起审批请求
          let params = JSON.parse(JSON.stringify(this.form));
          let time = this.form.endDate;

          params.endDate = params.endTime;

          params.target = 1;

          delete params.endTime;
          params.relas = JSON.stringify(this.applicationData);
          addE01zcRela(params).then(response => {
            if (response.code == 200) {
              //切换到等待页面
              this.apply = false;
              this.tabsShow = true;
              this.electronInspection = 'electron-inspection'
              this.applicationData = [];
              this.activeName = "pendapproval";
              this.archivesType = 0
              this.pageNum = 1;
              this.archivesaFunction()

            }
          })

        })




      },
      //第三步移除按钮
      electronicFeedback(row) {
        // this.applicationData.splice(row,1)
        let app = [];
        this.applicationData.map(item => {
          if (item.id == row.id) {

          } else {
            app.push(item);
          }
        })
        this.applicationData = app;
      },
      //获取材料树
      getQueryData() {
        queryE01z1TreeGrid().then(response => {
          this.data2 = response.data;
        })
        //queryE01z1TreeGrid(params).then(response=>{
        //  this.queryData=response.data
        // })
      },
      //根据干部信息获取材料树
      getQueryDataByA01(val) {
        queryE01z1TreeGrid({a01Id: val.id}).then(response => {
          this.data1 = response.data;
          this.dialogVisible1 = true;
        })
      },
      //整本查阅
      wholeConsult(val) {
        let b = this.checlData(val);
        if (!b) {
          return;
        }
        let oneData = JSON.parse(JSON.stringify(val));
        oneData.contype = "1";
        oneData.e01z1_name = "整本";
        oneData.e01z1_id = "zb"
        this.applicationData.push(oneData)
        this.$message({
          message: '添加成功',
          type: 'success'
        })
      },
      //分类查阅
      groupConsult(val) {
        this.checkA01Data = val;
        this.dialogVisible2 = true
        if (typeof this.$refs.tree2 == 'undefined') {
          return;
        }
        let checkTreeData = this.$refs.tree2.getCheckedNodes();
        checkTreeData.map(item => {
          this.$refs.tree2.setChecked(item, false, true);
        })
        // this.getQueryData();
      },
      //分类查阅页面确定
      changeOtherTreeNode() {
        let b = this.checlData(this.checkA01Data);
        if (!b) {
          return;
        }
        let checkTreeData = this.$refs.tree2.getCheckedNodes();
        if (checkTreeData.length > 0) {
          let oneData = JSON.parse(JSON.stringify(this.checkA01Data));
          oneData.contype = "2";
          let e01z1_ids = "";
          let e01z1_names = "";
          for (let i in checkTreeData) {
            this.$refs.tree2.setChecked(checkTreeData[i].id, false, true);
            if (checkTreeData[i].id != 4 && checkTreeData[i].id != 9) {
              if (e01z1_ids != "" && e01z1_names != "") {
                e01z1_ids = e01z1_ids + "," + checkTreeData[i].id;
                e01z1_names = e01z1_names + "," + checkTreeData[i].e01z111a;
              } else {
                e01z1_ids = checkTreeData[i].id;
                e01z1_names = checkTreeData[i].e01z111a;
              }
            }
          }
          oneData.e01z1_id = e01z1_ids;
          oneData.e01z1_name = e01z1_names;
          this.applicationData.push(oneData)
          this.dialogVisible2 = false;
        } else {
          this.$message({
            message: '请选中目录后点击确定',
            type: 'warning'
          });
        }

      },
      //单份材料查阅
      materConsult(val) {
        this.checkA01Data = val;
        this.getQueryDataByA01(val);
      },
      //单份材料查阅页面确定按钮
      changeA01E01z1s() {
        let b = this.checlData(this.checkA01Data);
        if (!b) {
          return;
        }
        let checkTreeData = this.$refs.tree1.getCheckedNodes();
        if (checkTreeData.length == 0) {
          this.$message({
            message: '请选中材料后点击确定',
            type: 'warning'
          });
          return;
        }
        let e01z1Datas = [];
        for (let i in checkTreeData) {
          this.$refs.tree1.setChecked(checkTreeData[i].id, false, true);
          if (checkTreeData[i].remark != "catalog") {
            e01z1Datas.push(checkTreeData[i])
          }
        }
        if (e01z1Datas.length == 0) {
          this.$message({
            message: '请选中材料后点击确定',
            type: 'warning'
          });
          return;
        }
        let oneData = JSON.parse(JSON.stringify(this.checkA01Data));
        oneData.contype = "3";
        let e01z1_ids = "";
        let e01z1_names = "";
        for (let n in e01z1Datas) {
          if (e01z1_ids != "" && e01z1_names != "") {
            e01z1_ids = e01z1_ids + "," + e01z1Datas[n].id;
            e01z1_names = e01z1_names + "," + e01z1Datas[n].e01z111a;
          } else {
            e01z1_ids = e01z1Datas[n].id;
            e01z1_names = e01z1Datas[n].e01z111a;
          }
        }
        oneData.e01z1_id = e01z1_ids;
        oneData.e01z1_name = e01z1_names;
        this.applicationData.push(oneData)
        this.dialogVisible1 = false;
      },
      //初始化页面所需参数
      initData() {
        // 当前页面所需字典表数据 性别
        queryDictionaryByType({dictType: 'GB22611'})
          .then(response => {
            this.a0104s1 = response.data
          })
        // 获取干部状态
        queryDictionaryByType({dictType: 'FDA01State'})
          .then(response => {
            this.states = response.data
          })
      },
      //材料接收-列表通用formatter
      getShowData(row, col) {
        //查阅类型
        if (col.property == "contype" || col.property == "contype") {
          if (row[col.property] != null && row[col.property] != '') {
            if (row[col.property] == 1) {
              return "整本档案查阅"
            }
            if (row[col.property] == 2) {
              return "档案分类查阅"
            }
            if (row[col.property] == 3) {
              return "单份材料查阅"
            }
          }
        }
        //时间转换
        if (col.property == "e01z117" || col.property == "a0107") {
          if (row[col.property] != null && row[col.property] != '') {
            return this.moment(row[col.property]).format("YYYY-MM-DD")
          }
        }
        //干部状态
        if (col.property == "state") {
          let returnValues = '';
          this.states.forEach(data => {
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName;
            }
          })
          return returnValues;
        }
        //性别
        if (col.property == "a0104") {
          let returnValues = '';
          this.a0104s1.forEach(data => {
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName;
            }
          })
          return returnValues;
        }
        return row[col.property]
      },
      // grid  双击部门 查询人员
      deptClick(val) {
        if (this.isa01 == 2) {
          return;
        }
        this.isa01 = 2;
        if (val.children) {
          this.deptNshow = true;
          let tableData = [];
          for (let i in val.children) {
            if (val.children[i].state == this.a01state) {
              tableData.push(val.children[i]);
            }
          }
          this.leftData = tableData
          this.beforeQueryData = val.children

        }
      },
      //干部 部门 搜索
      search(type) {
        const _this = this;
        if (type === 1) {
          this.searchText = this.cadresText
        } else {
          if (this.personnelText == null || this.personnelText == '') {
            this.$message({
              message: '请输入干部搜索条件',
              type: 'warning'
            })
            return;
          }
          this.searchText = this.personnelText
        }
        this.isa01 = type;
        queryA01List({searchText: this.searchText, state: this.a01state, type: type})
          .then(response => {
            if (type === 1) {
              _this.leftData = response.data
            } else {
              _this.leftData = response.data
            }
          })
      },
      //已通过点击查看详情
      adoptViewDetals(row) {
        // this.viewDetalsVasable3 = true;
        // getE01zcRela(row.id).then(response=>{
        //   this.adoptData = response.data;
        //   this.adoptData.map(item=>{
        //     item.approvTime = row.approvTime;
        //     item.endDate = row.endDate;
        //   })
        // })
        var storage = window.localStorage;
        storage.setItem("e01zcTmp", JSON.stringify(row));
        this.$router.push({
          name: '电子档案申请详情',
          // params:{
          //   id:JSON.stringify(row)
          // }
        })

      },
      //点击查看详情
      viewDetals(row) {
        this.e01zcId = row.id;
        queryE01zcRelaTreeByE01zcId(row.id).then(response => {
          if (response.code == 200) {
            this.e01zcTree = response.data;
            this.divw = 'divw4'
            if (this.e01zcTree.length == 1) {
              this.divw = 'divw1'
            }
            if (this.e01zcTree.length == 2) {
              this.divw = 'divw2'
            }
            if (this.e01zcTree.length == 3) {
              this.divw = 'divw3'
            }
            if (this.e01zcTree.length == 4) {
              this.divw = 'divw4'
            }
          }
        })
        this.approvalForm.approverDate = new Date();
        this.approvalForm.approverTime = new Date();
        this.viewDetalsVasable = true;
      },
      //台账跳转
      initShowNum() {
        if (this.$route.params.sign != null) {
          this.tabsShow = false;
          this.formShow = true;
          this.addForm.archivesType = this.$route.params.sign
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
        if (tab.label === '待审批') {
          this.archivesType = 0
          this.searchRow = 'searchRow';
        } else if (tab.label === '已通过') {
          this.archivesType = 1
          this.BorrowingColumn2[14].isCol = true;
          this.searchRow = 'searchRow2';
        } else if (tab.label === '未通过') {
          this.archivesType = -1
          this.BorrowingColumn2[14].isCol = false;
          this.searchRow = 'searchRow3';

        }
      },

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
            userId: this.userId

          }
          getApprovals(params)
            .then(function (response) {
              // for (var i=0;i<)
              _this.ElectronicData0 = response.rows;
              for (let i = 0; i < _this.ElectronicData0.length; i++) {
                //查阅截至时间
                let tdate = _this.moment(_this.ElectronicData0[i].endDate).toDate().getTime()
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
            userId: this.userId

          }
          getApprovals(params)
            .then(function (response) {
              // for (var i=0;i<)
              _this.ElectronicData1 = response.rows;
              for (let i = 0; i < _this.ElectronicData1.length; i++) {
                //查阅截至时间
                let tdate = _this.moment(_this.ElectronicData1[i].endDate).toDate().getTime();
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
            userId: this.userId

          }
          getApprovals(params)
            .then(function (response) {
              // for (var i=0;i<)
              _this.ElectronicData2 = response.rows;
              for (let i = 0; i < _this.ElectronicData2.length; i++) {
                //查阅截至时间
                let tdate = _this.moment(_this.ElectronicData2[i].endDate).toDate().getTime()
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





      format(datetime, fmt) {
        if (parseInt(datetime) == datetime) {
          if (datetime.length == 10) {
            datetime = parseInt(datetime) * 1000;
          } else if (datetime.length == 13) {
            datetime = parseInt(datetime);
          }
        }
        datetime = new Date(datetime);
        var o = {
          "M+": datetime.getMonth() + 1,                 //月份
          "d+": datetime.getDate(),                    //日
          "h+": datetime.getHours(),                   //小时
          "m+": datetime.getMinutes(),                 //分
          "s+": datetime.getSeconds(),                 //秒
          "q+": Math.floor((datetime.getMonth() + 3) / 3), //季度
          "S": datetime.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt))
          fmt = fmt.replace(RegExp.$1, (datetime.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
          if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
      },
      // grid 选中
      handleSelectionChange(val) {
        this.electronicCheck = val
      },
      // 导出
      handleExportBorrowing() {
        if (this.electronicCheck.length == 0) {
          this.$confirm('是否导出搜索后的申请数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            if (this.$route.params.sign == null) {
              const _this = this
              const param = this.archivesType;
              //电子档案查阅
              const type = 1;
              //系统内
              const target = 1;
              const params = {
                type: type,
                target: target,
                approval: this.archivesType,
                pageNum: 1,
                pageSize: 999999999,
                userName: this.borrowingParams.userName,
                endTime: this.borrowingParams.endTime,
                beginTime: this.borrowingParams.beginTime,
                userId: this.userId

              }
              getApprovals(params).then(function (response) {
                for (let i = 0; i < response.rows.length; i++) {
                  // ie无法使用 response.rows[i].approvTime = _this.format(response.rows[i].approvTime, "yyyy-MM-dd hh:mm:ss");
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
                  _this.$message.error("无数据,导出失败");
                  return;
                }
                let row = {
                  row: response.rows,
                  status: 11
                }
                downLoadPost("/export/excel/exportClick", row,this)
              })
            }
          })

        } else {
          this.$confirm('是否导出选中的申请数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let row = {
              row: this.electronicCheck,
              status: 11
            }
            downLoadPost("/export/excel/exportClick", row,this)
          })

        }

      },
      // 打印
      handlePrintBorrowing() {
      },
      // 电子档案查阅监听
      // 分页大小
      handleSizeChange(val) {
        if(this.archivesType==0){
          this.pageSize0 = val
        }else if(this.archivesType==1){
          this.pageSize1 = val
        }else{
          this.pageSize2 = val
        }
        this.archivesaFunction(false)

      },
      // 分页监听
      handleCurrentChange(val) {
        if(this.archivesType==0){
          this.pageNum0 = val
        }else if(this.archivesType==1){
          this.pageNum1 = val
        }else{
          this.pageNum2 = val
        }
        this.archivesaFunction(false)
      },
      //   //实体行grid点击事件
      handlerBorrowClickRow(row) {
        if (row) {
          this.$refs.BorrowingData.toggleRowSelection(row)
        } else {
          this.$refs.BorrowingData.clearSelection()
        }
      },
      //   // 初始化转换参数
      initColumnMap() {
        // 当前页面所需字典表数据 性别
        queryDictionaryByType({dictType: 'GB22611'})
          .then(response => {
            this.a0104s = response.data
          })

        // 获取民族
        queryDictionaryByType({dictType: 'GB3304'})
          .then(response => {
            this.a0117s = response.data
          })

        // 获取政治面貌
        queryDictionaryByType({dictType: 'GB4762'})
          .then(response => {
            this.a0141s = response.data
          })

        //当前页面所需字典表数据  目录树
        queryDictionaryByTypes({types: "ZB97A"})
          .then(response => {
            this.sysDictionaryMap = response.data

          })
      },
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
    },
  }
</script>
<style lang="scss" scoped>
  .t-ctn {
    width: 100%;
    overflow-x: auto;
  }

  .t-ctn .s-ctn {
    height: 150px;
    white-space: nowrap;
    font-size: 0;
  }

  .divw1 {
    width: 100%;
  }

  .divw2 {
    width: 50%;
  }

  .divw3 {
    width: 33%;
  }

  .divw4 {
    width: 25%;
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
  /deep/ .el-table tbody tr:hover > td {
    background-color: #ecf5ff !important
  }

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
    /deep/ .el-select > .el-input {
      width: 163px !important;
    }
  }

  .tree-style {
    /deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
      background: rgba(204, 204, 204, 0.18);
    }

  }

  .tree {
    width: 100%;
    padding: 10px 10px 26px 10px;

    /*border-right: 1px solid #e2e3e6;*/
  }

  /deep/ .el-scrollbar__wrap {
    overflow-x: hidden;
  }

  /deep/ .el-table__body td {
    padding: 0;
    height: 40px;
  }

  .electron-inspection {
    /*overflow: auto;*/
    /*overflow-y: hidden;*/
    height: calc(100vh - 86px);
  }

  .electron-inspection2 {
    overflow: auto;
    /*overflow-y: hidden;*/
    height: calc(100vh - 86px);
  }

  /deep/ .el-table__fixed-right-patch {
    background-color: #F8F8F9;
  }

  /deep/ .el-table .el-table__header-wrapper th {
    height: 31px;
  }

  .dialog-state {
    position: relative;
    height: 160px;
    margin: 20px;
    margin-left: 15%;
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
      margin-left: 20%;
      margin-top: 20px
    }
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
</style>

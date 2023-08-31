<template>
  <!-- 实体档案查阅审批 -->
  <div class="app-borrowing">
    <el-tabs
      v-if="tabsShow"
      id="elTabs"
      tab-position="left"
      v-model="activeName"
      style="min-height: calc(100vh - 86px);border: 1px solid #CCCCCC;"
      @tab-click="tabClick"
    >

      <el-tab-pane label="待审批" name="pendapproval">
        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px)">
          <el-row :gutter="10"  ref="searchRow1"  style="margin:0 0 0 0;padding-left: 0px">
            <el-form
              v-if="showBorrowing"
              class="search-form"
              :model="borrowingParams0"
              :inline="true"
              v-hasPermi="['checkBorrowApprove:physical:search']"
            >
              <el-form-item label="申请人姓名"   prop="userName" >
                <el-input
                  @keyup.enter.native="handleQueryBorrowing"
                  v-model="borrowingParams0.userName"
                  placeholder="请输入申请人姓名"
                  clearable
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item label="申请查阅时间起"  prop="startTime" >
                <el-date-picker  size="mini"  v-model="borrowingParams0.startTime"  type="date" placeholder="请选择时间"  style="width: 150px" />
              </el-form-item>
              <el-form-item label="申请查阅时间至"  prop="endTime" >
                <el-date-picker  size="mini"  v-model="borrowingParams0.endTime"  type="date" placeholder="请选择时间"  style="width: 150px" />
              </el-form-item>
              <el-form-item>
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
                @click="function(){showBorrowing = !showBorrowing;tableHeight = showBorrowing?'70%':'85%'}"
              />
            </el-col> -->
          </el-row>
          <div class="btn">
            <el-button type="primary" size="mini" icon="el-icon-edit-outline" v-hasPermi="['checkBorrowApprove:physical:approve']" @click="approvalProcessing">审批处理</el-button>

            <el-button type="primary" size="mini" icon="el-icon-download" v-hasPermi="['checkBorrowApprove:physical:export']" @click="handleExportBorrowing">导出
            </el-button>
          </div>
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            :height="tableHeight"
            tooltip-effect="light"
            size="mini"
            empty-text=" "
            :header-cell-style="headClass"
            style="width: 100%"
            :data="BorrowingData0"
            ref="BorrowingData0"
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
                    <el-button type="text" size="small" v-hasPermi="['checkBorrowApprove:physical:look']" @click="electronicFeedback(scope.row)">查看详情</el-button>
                  </template>
                </el-table-column>
          </el-table>

          <div  class="block">
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
        <!-- 详情对话框 -->
      <el-dialog title="实体档案查阅申请详情" :close-on-click-modal="false" :visible.sync="dialogDetailsVisible" width="85%" class="dialog-style">
        <el-table
          size="mini"
          class="table-style"
          :data="detailsData"
          :default-sort="{ prop: 'date', order: 'descending' }"
          tooltip-effect="light"
          align="center"
          stripe
          empty-text=" "
          border
          :header-cell-style="headClass"
          height="300px"
        >
          <el-table-column align="center" show-overflow-tooltip :resizable="false" label="序号" type="index" min-width="60" />
          <el-table-column align="center" label="被申请干部姓名" prop="a0101"/>
          <el-table-column align="center" label="编号" show-overflow-tooltip prop="number"></el-table-column>
          <el-table-column align="center" label="单位名称" show-overflow-tooltip prop="deptName"></el-table-column>
          <el-table-column align="center" label="职务" show-overflow-tooltip prop="duty" />
          <el-table-column align="center" label="查档内容" show-overflow-tooltip prop="e01z111a" ></el-table-column>
          <el-table-column align="center" label="档案类型" prop="ArchiveBorrowsType" ></el-table-column>
          <el-table-column align="center" label="审批状态" show-overflow-tooltip prop="flag" min-width="60"></el-table-column>
          <el-table-column align="center" label="备注" prop="e01z831" />
          <el-table-column align="center" fixed="right" label="操作" width="100">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="deleteRow(scope.$index, detailsData)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>
      </el-tab-pane>
      <el-tab-pane label="已通过" name="passed">
        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px)">
          <el-row :gutter="10"  ref="searchRow2" style="margin:0 0 0 0;padding-left: 0px">
            <el-form
              v-if="showBorrowing"
              class="search-form"
              v-hasPermi="['checkBorrowApprove:physical:search']"
              :model="borrowingParams1"
              :inline="true"
            >
              <el-form-item label="申请人姓名"  prop="userName" >
                <el-input
                  @keyup.enter.native="handleQueryBorrowing"
                  v-model="borrowingParams1.userName"
                  placeholder="请输入申请人姓名"
                  clearable
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item label="申请查阅时间起" prop="startTime" >
                <el-date-picker  size="mini"  v-model="borrowingParams1.startTime"  type="date" placeholder="选择时间"  style="width: 150px" />
              </el-form-item>
              <el-form-item label="申请查阅时间至" prop="endTime" >
                <el-date-picker  size="mini"  v-model="borrowingParams1.endTime"  type="date" placeholder="请选择时间"  style="width: 150px" />
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
                @click="function(){showBorrowing = !showBorrowing;tableHeight = showBorrowing?'70%':'85%'}"
              />
            </el-col> -->
          </el-row>
          <div class="btn">
            <el-button type="primary" size="mini" icon="el-icon-plus" v-hasPermi="['checkBorrowApprove:physical:add']" @click="handleAddBorrowing">添加查阅记录</el-button>

            <el-button type="primary"  icon="el-icon-notebook-2" v-hasPermi="['checkBorrowApprove:physical:file']" size="mini" @click="getReceipt">附件管理</el-button>

            <el-button type="primary"  icon="el-icon-receiving" v-hasPermi="['checkBorrowApprove:physical:renew']" size="mini" @click="renewalOfMaturity">到期续查</el-button>

            <el-button type="primary" size="mini" icon="el-icon-download" v-hasPermi="['checkBorrowApprove:physical:export']"  @click="handleExportBorrowing">导出
            </el-button>
          </div>
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            class="table-style"
            :height="tableHeight"
            size="mini"
            tooltip-effect="light"
            empty-text=" "
            ref="BorrowingData1"
            :header-cell-style="headClass"
            style="width: 100%"
            :data="BorrowingData1"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="40"/>
            <el-table-column style="width: 20px" type="index" align="center" label="序号" :index="customIndex1"/>
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
                    <el-button type="text" size="small" v-hasPermi="['checkBorrowApprove:physical:look']" @click="electronicFeedback(scope.row)">查看详情</el-button>
                  </template>
                </el-table-column>
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
          <!-- 附件管理 -->
    <el-dialog title="附件管理" :close-on-click-modal="false" width="700px" height="700px" :visible.sync="dialogVisible2" class="dialog-style">
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
        action="/api/transfer/uploadReceiptData">
        <el-button slot="trigger" size="mini" type="primary">选择文件</el-button>
        <el-button style="margin-left: 10px;" size="mini" type="success" @click="submitUpload"><i class="el-icon-upload el-icon--right"/>上传文件</el-button>
        <el-button size="mini" type="primary" @click="deleReceipt()"><i class="el-icon-error" style="margin-right:5px"/>删除文件</el-button>
      </el-upload>
        <div>
          <div style="float: left; margin-left: 5px;" slot="tip" class="el-upload-list__item-name">{{fileName}}</div>
          <div style="float: right; margin-left: 5px;" slot="tip" class="el-upload__tip">请上传jpg或png文件，且不超过5MB</div>
        </div>

      <img
        style="width: 670px; height: 400px"
        :src="img.url"
        fit="fit">
      </img>
      <div slot="footer" class="dialog-footer" >
        <el-button type="primary" @click="dialogVisible2 = false" size="mini">关 闭</el-button>
      </div>
    </el-dialog>
        </el-col>

        <!--  到期续查  -->
    <el-dialog title="到期续查" :close-on-click-modal="false" width="28%" class=" dialog-style dialog-basic" :visible.sync="viewDetalsVasable1">
      <div class="dialog-state">
      <span class="state">填写续查截止日期</span>
      <el-form ref="approvalForm" :model="approvalForm" style="width:90%;margin-top:11%;margin-left: 5%"  label-width="120px">
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
          <el-row :gutter="10"  ref="searchRow3" style="margin:0 0 0 0;padding-left: 0px">
            <el-form
              v-if="showBorrowing"
              class="search-form"
              v-hasPermi="['checkBorrowApprove:physical:search']"
              :model="borrowingParams2"
              :inline="true"
            >
              <el-form-item label="申请人姓名" prop="userName" >
                <el-input
                  v-model="borrowingParams2.userName"
                  placeholder="请输入申请人姓名"
                  @keyup.enter.native="handleQueryBorrowing"
                  clearable
                  size="mini"
                  style="width: 150px"
                />
              </el-form-item>
              <el-form-item label="申请查阅时间起" prop="startTime" >
                <el-date-picker  size="mini"  v-model="borrowingParams2.startTime"  type="date" placeholder="选择时间"  style="width: 150px" />
              </el-form-item>
              <el-form-item label="申请查阅时间至" prop="endTime" >
                <el-date-picker  size="mini"  v-model="borrowingParams2.endTime"  type="date" placeholder="请选择时间"  style="width: 150px" />
              </el-form-item>
              <el-form-item >
                <el-button type="primary" icon="el-icon-search"  size="mini" @click="handleQueryBorrowing">搜索
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
                @click="function(){showBorrowing = !showBorrowing;tableHeight = showBorrowing?'70%':'85%'}"
              />
            </el-col> -->
          </el-row>
          <div class="btn">
            <el-button type="primary" size="mini" icon="el-icon-download"  v-hasPermi="['checkBorrowApprove:physical:export']" @click="handleExportBorrowing">导出
            </el-button>
          </div>
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            class="table-style"
            empty-text=" "
            :height="tableHeight"
            size="mini"
            tooltip-effect="light"
            :header-cell-style="headClass"
            style="width: 100%"
            :data="BorrowingData2"
            ref="BorrowingData2"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="40"/>
            <el-table-column style="width: 20px" type="index" align="center" label="序号" :index="customIndex2"/>
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
                    <el-button type="text" size="small" v-hasPermi="['checkBorrowApprove:physical:look']" @click="electronicFeedback(scope.row)">查看详情</el-button>
                  </template>
                </el-table-column>
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
        </el-col>

      </el-tab-pane>
    </el-tabs>



    <el-col v-if="formShow"
            style="margin: 0;padding: 0;border: 1px solid #cccccc;height: calc(100vh - 86px);overflow: auto;">
      <el-row style="height:100%;padding-left: 10%;padding-right: 10%">


        <el-col :span="24">
          <div style="width: 100%;margin-bottom:15px;line-height:40px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0">第一步：填写申请基本信息</div>

          <el-form  ref="addForm" :rules="rules" :model="addForm" label-width="150px" style="width:90%;margin-top:25px;padding-left: 2%;padding-right: 2%">
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
              <el-form-item label="申请查阅起始日期" prop="startTime">
                <el-date-picker size="mini" v-model="addForm.startTime"  type="date" placeholder="选择申请开始时间" style="width: 100%;" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item  label="申请查阅截止日期至" style="text-align:right;" prop="endTime">
                <el-date-picker size="mini" v-model="addForm.endTime"  type="date" placeholder="选择申请截止时间至" style="width: 100%;" />
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
                <input  type="file"    @change="fileChange"  name="upload_Wfile"   style="display: none;"  ref="imageFile"   accept="image/png, image/jpeg" />
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
          <div style="width: 100%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0">第二步：请选择需要查阅的干部档案</div>

          <el-row :gutter="10"     style="margin: 0 auto;padding:25px 0 0 0">
            <el-form v-model="formInline"  :inline="true" :model="formInline" label-width="80px" >
<!--              <el-col :span="12">-->

                <el-form-item label="干部状态"  style="margin-left: 20px">
                    <el-select v-model="a01state" style="width: 150px"  filterable class="inputW" placeholder="请选择干部状态" size="mini">
                      <el-option v-for="item in states"  :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                    </el-select>
                  </el-form-item>

                <el-form-item style="margin-left: 20px"  prop="deptName">
                  <el-input v-model="formInline.deptName"  @keyup.enter.native="search(1)"  size="mini" style="width: 150px" placeholder="请输入部门名称" />
                </el-form-item>
                <el-form-item>
                  <el-button size="mini" type="primary" @click="search(1)">部门搜索</el-button>
                </el-form-item>


                 <el-form-item style="margin-left: 20px"  prop="a0101">
                  <el-input v-model="formInline.a0101"  @keyup.enter.native="search(2)"  size="mini" style="width: 150px" placeholder="请输入干部名称" />
                </el-form-item>
                <el-form-item>
                  <el-button size="mini" type="primary" @click="search(2)">干部搜索</el-button>
                </el-form-item>
<!--              </el-col>-->
<!--              <el-col :span="12">-->

<!--              </el-col>-->
            </el-form>
          </el-row>
          <el-col :span="24" style="padding-left: 2%;padding-right: 2%">
            <el-table height="222"
                      tooltip-effect="light"
                      empty-text=" "
                      size="mini"
                      ref="searchTableData1"
                      class="table-style"
                      :data="searchTableData"
                      border
                      @cell-dblclick="deptClick"
                      @selection-change="gridSelectionChange"
                      :header-cell-style="headClass" @row-click="handleGridChange" :stripe="true">
              <el-table-column type="selection" key="1" style="width:40px"  :v-if="dpbutton" />
              <el-table-column style="width: 1%" key="2"  type="index" align="center" label="序号" />
              <el-table-column show-overflow-tooltip prop="deptName" key="3"   v-if="departOrPerson==1"  label="部门名称" align="center"  />
              <el-table-column show-overflow-tooltip prop="parentId" key="4"   v-if="departOrPerson==1" label="上级部门" align="center"  />
              <el-table-column show-overflow-tooltip prop="a0101"  key="5" v-if="departOrPerson==2" label="干部姓名" align="center"  />
<!--              <el-table-column prop="number"  key="6" v-if="departOrPerson==2" label="干部编号" align="center" />-->
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
        <div style="width: 100%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0">第三步：确认申请信息</div>
        <el-table
            :default-sort="{prop: 'date', order: 'descending'}"
            stripe
            tooltip-effect="light"
            class="table-style"
            size="mini"
            border
            :header-cell-style="headClass"
            :data="queryData"
            empty-text=" "
            ref="queryData"
            style="width: 96%;margin-top:25px;margin-left: 2%;margin-right: 2%"
            height="222px"
            @selection-change="handleSelectionChange"
          >
            <el-table-column style="width: 1%" type="index" align="center" label="序号" />
            <el-table-column show-overflow-tooltip align="center" v-if="true" label="干部姓名" prop="a0101" />
<!--            <el-table-column align="center" v-if="true" label="干部编号" prop="number" />-->
            <el-table-column show-overflow-tooltip align="center" v-if="true" label="所属部门" prop="deptName" />
            <el-table-column show-overflow-tooltip align="center" v-if="true" label="干部职务"  prop="duty" :formatter="getFullName" />
            <el-table-column show-overflow-tooltip align="center" v-if="true" label="查档内容" prop="e01z111a" />
            <el-table-column show-overflow-tooltip align="center" v-if="true" label="档案类型" prop="archivesType" />

            <el-table-column align="center" fixed="right" label="操作" width="100">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="deleteRow(scope.$index, queryData)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-button style="float: right;margin: 10px;" size="mini" type="primary" @click="onGrid('addForm')">添加记录</el-button>
          <el-button style="float: right;margin: 10px;" size="mini"  @click="onBack">返回</el-button>
<!--        </el-col>-->
      </el-row>
    </el-col>

     <!--  批量审批处理  -->
    <el-dialog title="审批处理" :append-to-body="true"   :close-on-click-modal="false"  width="60%" class=" dialog-style dialog-basic" :visible.sync="approvalProcessingShow">
        <div class="form-basic">审批信息</div>
        <div style="">
      <el-form ref="approvalUpdateForm"  :model="approvalUpdateForm" :rules="rules" style="margin-left: 10%;margin-right: 10%"   :inline="true"  label-width="120px">
        <el-form-item prop="e01z801" label="审批人">
          <el-input size="mini" v-model="approvalUpdateForm.e01z801"/>
        </el-form-item>
        <el-form-item prop="e01z817" label="经办人">
          <el-input size="mini" v-model="approvalUpdateForm.e01z817" />
        </el-form-item>
        <el-form-item prop="flag" label="审批意见">
          <el-input size="mini" v-model="approvalUpdateForm.flag" />
        </el-form-item>

      </el-form>
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button size="mini" type="primary" @click="agree(1)">同意</el-button>
          <el-button size="mini" type="primary" @click="agree(2)">不同意</el-button>

        </div>
      </el-dialog>

     <!-- 查看详情-->
    <el-dialog title="实体档案查阅详情" :append-to-body="true"  :close-on-click-modal="false"  width="90%" class=" dialog-style dialog-basic" :visible.sync="dialogDetailsVisible1">

      <div class="form-basic" v-if="applyStates=='0'">审批信息</div>
        <el-form ref="approvalUpdateForm" v-if="applyStates=='0'" :model="approvalForm1" :rules="rules" style="padding:7px 0 0 0"  :inline="true"  label-width="120px">
          <el-form-item prop="e01z801" label="审批人">
            <el-input size="mini"  v-model="approvalForm1.e01z801"/>
          </el-form-item>

          <el-form-item prop="e01z817" label="经办人">
            <el-input size="mini" v-model="approvalForm1.e01z817" />
          </el-form-item>

          <el-form-item prop="flag" label="审批意见">
            <el-input size="mini" v-model="approvalForm1.flag" />
          </el-form-item>
        </el-form>
      <div class="form-basic" style="margin-top:-30px" v-if="applyStates=='0'">申请内容</div>
      <div style="padding:3px" v-if="applyStates!='0'">
      </div>
      <div style="padding:3px 0 1px 0">
        <el-col style="margin-left: 2%" :span="1.5"><el-button type="primary" icon="el-icon-download" @click="exportx" size="mini" >导出</el-button></el-col>
      </div>

        <el-table :data="viewDetailDatas"
                  border
                  empty-text=" "
                  class="table-style"
                  style="width: 95%; margin-left: 2%;margin-top:47px;margin-bottom: 20px"
                  :height="applyHeight"
                  stripe
                  size="mini"
                  tooltip-effect="light"
                  :header-cell-style="headClass"
                  @selection-change="approvalDataSelect"
                  ref="searchTableData" >
          <el-table-column type="selection" width="40" />
          <el-table-column  type="index" width="50" align="center" label="序号" />
          <el-table-column prop="a0101" label="被申请人姓名" align="center"  />
          <el-table-column prop="deptName" show-overflow-tooltip label="被申请人所在部门" align="center"  />
          <el-table-column prop="e01z111a" :show-overflow-tooltip="true" label="申请内容" align="center"  />
          <el-table-column prop="creatersTime" label="申请日期" align="center"  :formatter="getFullName" />
          <el-table-column prop="startTime" label="申请查阅起始日期" align="center"  :formatter="getFullName" />
          <el-table-column prop="endTime" label="申请查阅截止日期至" align="center"  :formatter="getFullName" />
        </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" v-if="applyStates!='0'" @click="dialogDetailsVisible1 = false ">关闭</el-button>
        <el-button size="mini" v-if="applyStates=='0'"  type="primary" @click="agree(1)">同意</el-button>
        <el-button size="mini" v-if="applyStates=='0'"  type="primary" @click="agree(2)">不同意</el-button>
      </div>
    </el-dialog>



  </div>
</template>
<script>
import {
  checkBorrowEntityFile,
  getEntityArchiveCApplyRecord,
  archiveRenewalOfMaturity,
  archiveApply,
  viewDetails
} from '@/api/usual/usage/borrow'
import { queryA01List } from '@/api/usual/transfer'
import { getTreeGrid } from '@/api/usual/daily'
import { querySysFile ,uploadReceiptData} from '@/api/usual/parameter/transfer-batch-paramenter'
import {queryDictionaryByType,queryDictionaryByTypes,queryLoginUser} from '@/api/usual/receive'
// import request from '@/utils/request'
import {downLoad,downLoadPost} from '@/downLoad/filedownload'
const datalist = []
const baseUrl = process.env.VUE_APP_BASE_API
export default {
  data() {
    return {
      xselect:[],
      //普通参数
      applyHeight:'400px',
      tabsShow:true,
      formShow:false,
      dialogVisible2:false,
      approvalForm:{},
      showBorrowing:true,
      viewDetailDatas:[],
      applyStates:"0",
      tableHeight:'70%',
      BorrowingData:[],
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

      //搜索框
      borrowingParams0:{
        userName:"",
        startTime:"",
        endTime:"",
      },
      borrowingParams1:{
        userName:"",
        startTime:"",
        endTime:"",
      },
      borrowingParams2:{
        userName:"",
        startTime:"",
        endTime:"",
      },

      a01state:"0",
      departOrPerson:2,
      dialogDetailsVisible:false,
      dialogDetailsVisible1:false,
      viewDetalsVasable1:false,
      approvalProcessingShow:false,
      activeName: 'pendapproval',
      userName:"",
      states:[],
      searchTableData:[],
      searchChek:"",
      dpbutton:false,
      queryData:[],
      pageSizeParam:{},
      searchText:"",
      newApplyData:'',
      checkArchiveId:'',
      fileName:'',
      //附件管理数据
      img: {
        url: '',
      },
      //上传文件参数
      uploadData:{
        batchId:""
      },
      fileList:[],
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
        e01z801:[
          { required: true, message: '审批人必须填写' },
          { min: 2, max: 40, message: '长度在 2 到 40 个字符' }
        ],
        e01z817:[
          { min: 2, max: 40, message: '长度在 2 到 40 个字符' }
        ],
        flag:[
          { required: true, message: '审批意见必须填写' },
          { min: 1, max: 255, message: '长度在 2 到 255 个字符' }
        ],
        userName: [
          { required: true, message: '申请人姓名必须填写' },
          { min: 2, max: 40, message: '长度在 2 到 40 个字符' }
        ],
        userCard:[
          { required: true, message: '申请人证件号必须填写' },
          { min: 1, max: 40, message: '长度在 1 到 40 个字符' }
        ],
        e01z824a:[
          { required: true, message: '申请人所在单位必须填写' },
          { min: 1, max: 40, message: '长度在 1 到 40 个字符' }
        ],
        userPhone:[
          { required: true, message: '申请人电话必须填写' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符' }
        ],
        applicationReasons:[
          { required: true, message: '申请理由必须填写' },
          { min: 1, max: 255, message: '长度在 1 到 255 个字符' }
        ],
        endTime:[
          { required: true, message: '申请截止时间至必须填写' },
        ],
        duty:[
          { min: 1, max: 40, message: '长度在 1 到 40 个字符' }
        ],
        printContents:[
          { min: 1, max: 255, message: '长度在 1 到 255 个字符' }
        ],
        e01z831:[
          { min: 1, max: 255, message: '长度在 1 到 255 个字符' }
        ],
      },

      //form
      //搜索框
      borrowingParams:{
        userName:"",
        startTime:"",
        endTime:"",
      },
      //申请页面
      addForm:{},
      dutys:[],
      //干部搜索
      formInline:{},
      //干部状态
      a01Search:{},
      //到期续查
      approvalForm:{},
      //审批form
      approvalUpdateForm:{e01z801:"",flag:"",e01z817:""},
      //查看详情审批
      approvalForm1:{
        e01z801:'',

      },


      //详情数据
      detailsData: [
      ],
      queryBorrowingParams:{}

    }

  },
  watch: {

    queryData :{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          _this.$refs.queryData.doLayout()
        })
      },
      deep:true,
      // immediate:true
    },
    viewDetailDatas:{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          _this.$refs.searchTableData.doLayout();
        })
      },
      deep:true,
      // immediate:true
    },
    searchTableData:{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          _this.$refs.searchTableData1.doLayout();
        })
      },
      deep:true,
      // immediate:true
    },

    BorrowingData:{
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(()=>{
          _this.$refs.BorrowingData0.doLayout();
          _this.$refs.BorrowingData1.doLayout();
          _this.$refs.BorrowingData2.doLayout();
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
    approvalDataSelect(select){
      this.xselect = select;
    },
    exportx(){
      let data = {
        "colTitle": {
          "a0101": "被申请干部姓名",
          // "number": "干部编号",
          "deptName": "被申请人所在部门",
          "e01z111a": "申请内容",
          "creatersTime": "申请日期",
          "startTime":"申请查阅起始日期",
          "endTime":"申请查阅截止日期至"

        },
        "colWidth": {
          deptName:25,
          startTime:25,
          endTime:25
        },
        "dataList": [],
        "tableTitle": "实体查阅详情表",
        "sheetTitle": "实体查阅详情表",
        "downLoadName": "实体查阅详情表"
      }
      if (this.xselect.length == 0) {
        let checkboxDatas = this.viewDetailDatas;
        checkboxDatas.map(item=>{
            item.creatersTime = this.getFullName2(item.creatersTime);
            item.startTime = this.getFullName2(item.startTime);
            item.endTime = this.getFullName2(item.endTime);
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
            item.creatersTime = this.getFullName2(item.creatersTime);
            item.startTime = this.getFullName2(item.startTime);
            item.endTime = this.getFullName2(item.endTime);
          })
          data.dataList = checkboxDatas
          downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data),this)
        })
      }
    },
    //审批处理
    approvalProcessing(){
      if(this.electronicCheck.length==0){
        this.$message({
          message:"请勾选数据后审批",
          type:'warning'
        })
      }else{
        this.approvalUpdateForm.e01z801=this.loginUser.userName
        this.approvalProcessingShow=true;
      }
    },

    // 附件管理
    getReceipt(){
      if(this.electronicCheck.length!=1){
        this.$message({
          message:"请勾选一条数据进行附件管理",
          type:'warning'
        })
      }else{
        this.checkArchiveId = this.electronicCheck[0].id
        let batchId = this.electronicCheck[0].id;
        querySysFile({batchId: batchId})
          .then(response => {
            if(response.data != null){
              this.img.url = encodeURI(baseUrl + "/common/downImage?id=" + response.data + "&fileName=123");
              for(let i in this.tableData){
                if(batchId == this.tableData[i].id){
                  this.fileName = this.tableData[i].otherParam;
                  return
                }
              }
            } else {
              this.$message({
                type: 'info',
                message: "未上传附件"
              })
              this.img.url = "";
            }
          })
        this.dialogVisible2 = true;
      }

    },
    // 上传前校验类型及大小
    imgPreview(file,fileList){
      let reader = new FileReader()
      reader.readAsDataURL(file.raw)
      reader.onload=()=>{
        this.img.url = reader.result
        if(this.fileList.length!=0){
        this.fileList = fileList[fileList.length-(fileList.length-1)]
        }
      }
        //上传文件参数
        this.uploadData.batchId = this.checkArchiveId;
        let testmsg = file.name.substring(file.name.lastIndexOf('.')+1)
        this.fileName = file.name;
        const extension = testmsg === 'jpg'
        const extension2 = testmsg === 'png'
        const isLt2M = file.size / 1024 / 1024 < 5
        if(!extension && !extension2) {
          this.$message({
            message: '请选择jpg或png文件进行上传',
            type: 'warning'
          });
          }
          if(!isLt2M) {
            this.$message({
              message: '上传文件大小不能超过 5MB!',
              type: 'warning'
            });
          }
      return extension || extension2 && isLt2M
    },
    fileChange(val){
      if(val.target.value==''){
        this.addForm.fileName = "未选择文件"
      }else{
        this.addForm.fileName = val.target.value
      }
    },
    // 上传
    submitUpload(event) {
      let _this = this;
      this.$confirm('此操作将替换原附件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(response => {
          event.preventDefault();
          this.$refs.upload.submit();
          _this.$message({
            type: 'success',
            message: "上传成功"
          })
        })
        /*.catch(() => {
        })*/
    },

    //审批
    agree(val){
      this.$refs.approvalUpdateForm.validate((valid) => {
        if (valid) {
          let ids="";
          let param = {};
          if(this.newApplyData!=''){
            ids=this.newApplyData.id;
            param=this.approvalForm1;
          }else{
            for(let i in this.electronicCheck){
              ids=ids+this.electronicCheck[i].id+",";
            }
            param = this.approvalUpdateForm
          }

          param.ids=ids;
          param.isAgree=val;
          param.applyType=1;
          archiveApply(param).then(respnose=>{
            if(respnose.code==200){
              this.$message({
                message:respnose.msg,
                type:"success"
              })
              this.archivesaFunction()
              this.newApplyData='';
              this.approvalProcessingShow=false;
              this.dialogDetailsVisible1 = false;
              this.$refs.approvalUpdateForm.resetFields();
            }else{
              this.$message({
                message:respnose.msg,
                type:"error"
              })
            }
          })
        }else{
          this.$message({
            type:"warning",
            message:"请按规范填写表单"
          })
        }
      })


    },

    //查看详情
    electronicFeedback(val){
      this.viewDetailDatas=[];
      let params = {
        id:val.id,
        applyType:"1"
      }
      if(this.applyStates=='0'){
        this.applyHeight='250px';
        this.newApplyData=val;
        this.approvalForm1.e01z801=this.loginUser.userName
      }else{
        this.applyHeight='400px';
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
          type:'warning'
        })
      }else{
        console.info(this.electronicCheck[0]);
        if(this.electronicCheck[0].applyStatus=="到期续查"){
          this.$message({
            message:"该档案已续查过，请重新申请",
            type:'warning'
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
                  let returnMap = {
                    formValues:this.addForm,
                    gridValues:this.queryData,
                    applyType:2
                  }
                  let testFormData = new FormData();
                  if(this.$refs.imageFile.files[0]!=null){
                    testFormData.append("files",this.$refs.imageFile.files[0])
                  }
                  testFormData.append("jsonStr",JSON.stringify(returnMap))
                  checkBorrowEntityFile(testFormData).then(response =>{
                    if(response.code==200){
                      this.$message({
                        type:"success",
                        message:"申请成功"
                      })
                      this.formShow=false;
                      this.tabsShow=true;
                      this.addForm={};
                      this.queryData=[];
                      this.searchTableData=[];
                      this.searchChek=[];
                      this.archivesaFunction();
                    }else{
                      this.$message({
                        type:"error",
                        message:"申请失败"
                      })
                    }
                  })
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
        let param = {
          pageNum: this.pageNum0,
          pageSize: this.pageSize0,
          status:0,
          searchValue:this.queryBorrowingParams,
          isAll:"123"
        }
        getEntityArchiveCApplyRecord(param).then(response =>{
          this.BorrowingData0=response.rows
          this.total0 = response.total
          this.loading = false
        })
      }
      if(this.applyStates=="1"||all){
        let param = {
          pageNum: this.pageNum1,
          pageSize: this.pageSize1,
          status:1,
          searchValue:this.queryBorrowingParams,
          isAll:"123"
        }
        getEntityArchiveCApplyRecord(param).then(response =>{
          this.BorrowingData1=response.rows
          this.total1 = response.total
          this.loading = false
        })
      }
      if(this.applyStates=="-1"||all){
        let param = {
          pageNum: this.pageNum2,
          pageSize: this.pageSize2,
          status:-1,
          searchValue:this.queryBorrowingParams,
          isAll:"123"
        }
        getEntityArchiveCApplyRecord(param).then(response =>{
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

      const gridDataList = JSON.parse(JSON.stringify(this.searchChek))
      let a01s = [];
      let datalist=[];
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
    getFullName2(date){
      return this.moment(date).format("YYYY-MM-DD")
    },
     // 格式转换
    getFullName(row,col){
        // 时间
        if( col.property=="a0107"||col.property=="approvTime"||col.property=="creatersTime"||col.property=="endTime"||col.property=="startTime"){
          if(row[col.property]!=null){
            // var d = new Date(row[col.property]);
            // let month = '' + (d.getMonth() + 1);
            // let day = '' + d.getDate();
            // let year = d.getFullYear();
            // if (month.length < 2) month = '0' + month;
            // if (day.length < 2) day = '0' + day;
            // return [year, month, day].join('-');
            return this.moment(row[col.property]).format("YYYY-MM-DD")
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
      console.info(this.a01Search.a01state)
      const _this = this
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
          _this.searchTableData = response.data
          this.beforeQueryData = response.data
        })
    },
    // 搜索结果 复选框
    gridSelectionChange(val) {
      this.searchChek = val
    },

    // 搜索结果显示
    handleGridChange(row) {
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
        console.info(this.$refs.searchTableData)
        console.info(this.$refs.searchTableData.fixedBodyHeight)
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
      console.info(this.searchTableData)
    },


    // 档案借阅监听
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
        this.queryBorrowingParams = JSON.stringify(this.borrowingParams0);
      }else if(this.applyStates=="1"){
        this.pageNum1=1;
        this.queryBorrowingParams = JSON.stringify(this.borrowingParams1);
      }else{
        this.pageNum2=1;
        this.queryBorrowingParams = JSON.stringify(this.borrowingParams2);
      }
      this.archivesaFunction(false);
    },

    //重置表单
    resetQueryBorrowing(){
      this.borrowingParams.userName="";
      this.borrowingParams.startTime="";
      this.borrowingParams.endTime="";
      this.archivesaFunction({
        pageNum: 0,
        pageSize: this.pageSize,
        status:this.applyStates,
      })
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
        fileName:"未选择文件",
        applicationReasons:"",
        printContents:"",
        e01z831:"",
        e01z817:"",
        flag:"",
        userName:this.loginUser.userName,
        userId:this.loginUser.userId,
      }
    },

    //导出
    handleExportBorrowing(){
      if(this.electronicCheck.length==0){
        this.$confirm('是否导出搜索后的审批数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let searchText = JSON.stringify(this.borrowingParams);
          let params = {
            pageNum: 1,
            pageSize: 999999999,
            status:this.applyStates,
            searchValue:searchText,
            isAll:"123"
          };
          getEntityArchiveCApplyRecord(params).then(response =>{
            if(response.rows.length==0){
              this.$message.error("无数据,导出失败");
              return;
            }
            console.log(response.rows)
            let row = {
              row: response.rows,
              status: 16
            }
            downLoadPost("/export/excel/exportClick", row,this)
          })
        })

      }else{
        this.$confirm('是否导出选中的审批数据?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let row = {
            row: this.electronicCheck,
            status: 16
          }
          downLoadPost("/export/excel/exportClick", row,this)
        })

      }

      // let s = JSON.stringify(params);
      // console.log(s);
      // s = encodeURI(s);
      // downLoad("/export/excel/export?status=8&exportData=" + s)
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
  }
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

  .el-tab-pane {
    min-height: calc(100vh - 86px);
  }

  .el-tabs__nav-wrap {
    min-height: calc(100vh - 86px);
  }

  .el-tabs__header {
    min-height: calc(100vh - 86px);
  }


  .inputW {
    width: 200px;
  }

  .form-width{
      width:50%;
      display:flex;
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
  .form-basic {
      margin-bottom: 15px;
      line-height: 30px;
      border-bottom: 1px solid #ccc;
      margin-left: 15px;
      margin-right: 15px;
    }
  //设置统一表头
    /deep/.el-table tbody tr:hover>td {
      background-color:#ecf5ff !important
    }

  /deep/.el-table__body td {
     padding: 0;
     height: 40px;
   }

  /deep/ .el-table .el-table__header-wrapper th{
    height:31px;
  }

  /deep/ .el-table__fixed-right-patch {
    background-color:#F8F8F9;
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

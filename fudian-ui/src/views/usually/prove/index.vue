<template>
  <div class="app-prove">
    <el-tabs id="elTabs" tabPosition="left" @tab-click="tabClick">
      <el-tab-pane label="亲属关系证明">
        <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px);overflow: auto;">
          <el-row :gutter="10"  ref="searchRow"  style="margin:0 0 10px 0;padding-left: 0px">
            <!-- 搜索框 -->
            <div >
              <el-form :inline="true" ref="formSearch" label-width="75px" class="form-search" :model="formSearch">
                <el-form-item label="干部姓名" class="form-width">
                  <el-input v-model="formSearch.a0101" placeholder="请输入干部姓名" size="mini" clearable style="width: 150px;"></el-input>
                </el-form-item>
                <el-form-item label="身份证号码" class="form-width">
                  <el-input v-model="formSearch.a0184" placeholder="请输入身份证号" size="mini" clearable style="width: 150px;"></el-input>
                </el-form-item>
                <el-form-item label="干部状态" class="form-width">
                  <el-select v-model="formSearch.state" style="width: 150px" clearable filterable placeholder="请选择干部状态" size="mini">
                    <el-option v-for="item in states"  :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                  </el-select>
                </el-form-item>
                <el-form-item >
                  <el-button type="primary" size="mini" icon="el-icon-search" @click="depement()">搜索</el-button>
                  <el-button size="mini" icon="el-icon-refresh" @click="clear()">重置</el-button>
                </el-form-item>
              </el-form>
            </div>
            <!-- 功能按钮 -->
            <div class="btn-div" >
              <el-button type="primary" size="mini" icon="el-icon-plus" @click="onAddRelatives">新增</el-button>
              <el-button type="primary" icon="el-icon-edit" v-model="showdaliog" @click="onUpdateRelatives" size="mini">编辑
              </el-button>
              <el-button type="primary" size="mini" icon="el-icon-delete" @click="onDeleteRelatives">删除</el-button>
              <el-button type="primary" size="mini" icon="el-icon-upload2" @click="onReturnRelatives">导出</el-button>
              <!-- <div class="btn-right">
                <el-button icon="el-icon-search" size="mini" type="primary" circle @click="showSearchBox = !showSearchBox">
                </el-button>
                <el-dropdown>
                  <el-button icon="el-icon-menu" size="mini" circle />
                  <el-dropdown-menu style="padding: 10px">
                    <el-checkbox-group v-for="(item, index) in col" :key="index" v-model="col[index].isCol" size="small">
                      <el-checkbox>{{ item.label }}</el-checkbox>
                    </el-checkbox-group>
                  </el-dropdown-menu>
                </el-dropdown>
              </div> -->
            </div>
          </el-row>

        <!-- 表格 -->
        <div clsss="table-Style">
          <el-table
            :data="tableData1"
            :height="tableHeight "
            size="mini"
            tooltip-effect="light"
            ref="tableRefs"
            border :header-cell-style="headClass"
            row-key="id"
            :stripe="true"
            empty-text=" "
            @row-click="StepsListRowClick"
            @selection-change="selectChecked"
            >
            <el-table-column type="selection" align="center"/>
            <el-table-column type="index" label="序号" width="50px" align="center" ></el-table-column>
            <el-table-column label="干部姓名" prop="a0101"  show-overflow-tooltip align="center"></el-table-column>
            <el-table-column label="干部状态" prop="state"  show-overflow-tooltip align="center" :formatter="getFullName" width="80px"></el-table-column>
            <el-table-column label="身份证号码" prop="a0184" show-overflow-tooltip align="center" width="160px"></el-table-column>
            <el-table-column label="性别" prop="a0104" show-overflow-tooltip align="center" :formatter="getFullName" width="80px"></el-table-column>
            <el-table-column label="父亲" prop="father" show-overflow-tooltip align="center"></el-table-column>
            <el-table-column label="母亲" prop="mother" show-overflow-tooltip align="center"></el-table-column>
            <el-table-column label="配偶" prop="spouse" show-overflow-tooltip align="center"></el-table-column>
            <el-table-column label="女儿" prop="daughter" show-overflow-tooltip align="center"></el-table-column>
            <el-table-column label="儿子" prop="son" show-overflow-tooltip align="center"></el-table-column>
            <el-table-column label="证明日期" prop="proveDate" show-overflow-tooltip align="center" :formatter="getFullName" width="120px"></el-table-column>
<!--            <el-table-column label="操作" fixed="right" width="130px" :resizable="false" align="center">-->
<!--              <template slot-scope="">-->
<!--                <el-button type="text" size="mini" @click="onCopyRelatives">打印证明</el-button>-->
<!--              </template>-->
<!--            </el-table-column>-->
          </el-table>
        </div>
        <!-- 分页 -->
<!--        <el-pagination class="page"  @size-change="handleSizeChange1" @current-change="handleCurrentChange1"-->
<!--          :current-page="currentPage4" :page-sizes="[20, 50, 100, 200]" :page-size="20"-->
<!--          layout="total, sizes, prev, pager, next, jumper"></el-pagination>-->
          <el-pagination
            class="page"
            :page-sizes="[20, 50, 100, 200]"
            :page-size="20"
            layout="total, sizes, prev, pager, next, jumper"
            :total="formSearch.total"
            @size-change="handleSizeChange1"
            @current-change="handleCurrentChange1"
          />
        </el-col>
      </el-tab-pane>

      <el-tab-pane label="海关工作证明">

         <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px);overflow: auto;">
          <el-row :gutter="10"  ref="searchRow2"  style="margin:0 0 10px 0;padding-left: 0px">
        <!-- 搜索框 -->
        <div >
          <el-form :inline="true" ref="formSearch" label-width="75px" :model="formSearch" class="form-search" >
            <el-form-item label="干部名称" class="form-width">
              <el-input v-model="formSearch.a0101" placeholder="请输入干部姓名" size="mini" clearable style="width: 150px;"></el-input>
            </el-form-item>
            <el-form-item label="身份证号码" class="form-width">
              <el-input v-model="formSearch.a0184" placeholder="请输入身份证号" size="mini" clearable style="width: 150px;"></el-input>
            </el-form-item>
            <el-form-item label="干部状态" class="form-width">
              <el-select v-model="formSearch.state" clearable style="width: 150px;" filterable class="inputW" placeholder="请选择干部状态" size="mini">
                <el-option v-for="item in states"  :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="mini" icon="el-icon-search" @click="depement()">搜索</el-button>
              <el-button size="mini" icon="el-icon-refresh" @click="clear()">重置</el-button>
            </el-form-item>
          </el-form>
        </div>

        <!-- 功能按钮 -->
        <div  class="btn-div">
          <el-button type="primary" size="mini" icon="el-icon-plus" @click="onAddCustoms">新增</el-button>
          <el-button type="primary" icon="el-icon-edit" v-model="showdaliog1" @click="onUpdateCustoms" size="mini">编辑</el-button>
          <el-button type="primary" size="mini" icon="el-icon-delete" @click="onDeleteCustoms">删除</el-button>
          <el-button type="primary" size="mini" icon="el-icon-upload2" @click="onReturnCustoms">导出</el-button>
          <!-- <div class="btn-right">
            <el-button icon="el-icon-search" size="mini" type="primary" circle>
            </el-button>
            <el-dropdown>
              <el-button icon="el-icon-menu" size="mini" circle />
              <el-dropdown-menu style="padding: 10px">
                <el-checkbox-group v-for="(item, index) in col" :key="index" v-model="col[index].isCol" size="small">
                  <el-checkbox>{{ item.label }}</el-checkbox>
                </el-checkbox-group>
              </el-dropdown-menu>
            </el-dropdown>
          </div> -->
        </div>
         </el-row>
        <!-- 表格 -->
        <div class="table-Style">
          <el-table
            :data="tableData2"
            ref="tableRefsCustoms"
            @row-click="StepsListRowClickCustoms"
            :height="tableHeight "
            size="mini"
            tooltip-effect="light"
            border
            :header-cell-style="headClass"
            empty-text=" "
            row-key="id"
            :stripe="true"
            @select='onTableSelect'
            @selection-change="selectCheckedCustoms"
            >
            <el-table-column type="selection" align="center"/>
            <el-table-column type="index" label="序号" width="50px" align="center" />
            <el-table-column label="干部姓名" prop="a0101" show-overflow-tooltip align="center" width="100px"></el-table-column>
            <el-table-column label="干部状态" prop="state"  show-overflow-tooltip align="center" :formatter="getFullName" width="80px"></el-table-column>
            <el-table-column label="部门" prop="deptName" show-overflow-tooltip align="center" :formatter="getFullName" width="100px"></el-table-column>
            <el-table-column label="身份证号码" prop="a0184" show-overflow-tooltip align="center" width="160px"></el-table-column>
            <el-table-column label="参加工作时间" prop="a0134" show-overflow-tooltip align="center" :formatter="getFullName"></el-table-column>
            <el-table-column label="证明时间" prop="proveDate" show-overflow-tooltip align="center" :formatter="getFullName"></el-table-column>
<!--            <el-table-column label="操作" fixed="right" width="130px" show-overflow-tooltip :resizable="false" align="center">-->
<!--              <template>-->
<!--                <el-button type="text" size="mini" @click="onCopyCustoms">打印证明</el-button>-->
<!--              </template>-->
<!--            </el-table-column>-->
          </el-table>
        </div>
        <!-- 分页 -->
           <el-pagination
             class="page"
             :page-sizes="[20, 50, 100, 200]"
             :page-size="20"
             layout="total, sizes, prev, pager, next, jumper"
             :total="formSearch.total"
             @size-change="handleSizeChange2"
             @current-change="handleCurrentChange2"
           />
         </el-col>
      </el-tab-pane>
    </el-tabs>
    <!-- </div> -->
    <!--亲属关系- 新增 -->
    <el-dialog title="新增亲属关系证明" :close-on-click-modal="false" :visible.sync="dialogVisible1" class=" dialog-style" width="65%">
      <div class="dialog-center">
      <div class="div-left">
        <span >第一步：请输入想要搜索的干部姓名</span>
        <el-form :data="formsearchRelatives" ref="ruleForm" :inline="true"  label-width="70px" class="search-people">
          <el-form-item label="干部状态">
            <el-select v-model="formsearchRelatives.state" style="width: 130px" clearable filterable class="inputW" placeholder="请选择干部状态" size="mini">
              <el-option v-for="item in states"  :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
            </el-select>
          </el-form-item>
          <el-form-item label="干部姓名" prop="a0101" >
            <el-input v-model="formsearchRelatives.a0101" placeholder="请输入干部姓名" clearable style="width:130px" size="mini"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="mini" @click="queryRela">搜索</el-button>
          </el-form-item>
        </el-form>
            <el-table
            ref="tableRefsAdd"
            :data="tableData3"
            size="mini"
            height="230px"
            :header-cell-style="headClass"
            tooltip-effect="light"
            empty-text=" "
            border
            row-key="id"
            @row-click="StepsListRowClickadd"
              :stripe="true">
<!--              <el-table-column type="selection" width="45px"/>-->
              <el-table-column type="index" label="序号" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column label="干部姓名" prop="a0101" width="80px" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column label="性别" prop="a0104" show-overflow-tooltip align="center" :formatter="getFullName" width="80px"></el-table-column>
              <el-table-column label="干部状态" prop="state" show-overflow-tooltip align="center" :formatter="getFullName" width="80px"></el-table-column>
              <el-table-column label="身份证号码" prop="a0184" show-overflow-tooltip align="center"></el-table-column>
            </el-table>
<!--            <el-pagination style="float: right;" small="small" @size-change="handleSizeChange" @current-change="handleCurrentChange"-->
<!--            :current-page="currentPage4" :page-sizes="[100, 200, 300, 400]" :page-size="100"-->
<!--            layout="total, sizes, prev, pager, next, jumper" :total="400"></el-pagination>-->
            <el-pagination
              class="page"
              :page-sizes="[20, 50, 100, 200]"
              :page-size="20"
              layout="total, sizes, prev, pager, next, jumper"
              :total="formsearchRelatives.total"
              @size-change="handleSizeChange3"
              @current-change="handleCurrentChange3"
            />
      </div>
      <div class="right-tree">
        <span>第二步：请填写信息</span>
        <div class="form-div">
      <el-form ref="formRelatives" label-width="100px" :inline="true">
        <el-form-item label="干部姓名" class="input-style">
          <el-input v-model="formRelatives.a0101" clearable size="mini" placeholder="请输入干部姓名"></el-input>
        </el-form-item>
        <el-form-item label="性别" class="input-style">
          <el-select v-model="formRelatives.a0104" clearable style="width:163px" placeholder="请选择性别" size="mini">
            <el-option v-for="item in a0104s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="身份证号码" class="input-style">
          <el-input v-model="formRelatives.a0184" clearable size="mini" placeholder="请输入身份证号码"></el-input>
        </el-form-item>
        <el-form-item label="父亲" class="input-style">
          <el-input v-model="formRelatives.father" clearable size="mini" placeholder="请输入父亲姓名"></el-input>
        </el-form-item>
        <el-form-item label="母亲" class="input-style">
          <el-input v-model="formRelatives.mother" clearable size="mini" placeholder="请输入母亲姓名"></el-input>
        </el-form-item>
        <el-form-item label="配偶" class="input-style">
          <el-input v-model="formRelatives.spouse" clearable size="mini" placeholder="请输入配偶姓名"></el-input>
        </el-form-item>
        <el-form-item label="女儿" class="input-style">
          <el-input v-model="formRelatives.daughter" clearable size="mini" placeholder="请输入女儿姓名"></el-input>
        </el-form-item>
        <el-form-item label="儿子" class="input-style">
          <el-input v-model="formRelatives.son" clearable size="mini" placeholder="请输入儿子姓名"></el-input>
        </el-form-item>
        <el-form-item label="证明日期" class="input-style">
          <el-date-picker v-model="formRelatives.proveDate" clearable style="width:163px" type="date" placeholder="请选择证明日期" value-format="yyyy-MM-dd" size="mini"/>
        </el-form-item>
      </el-form>
        </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible1 = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveRelative">保 存</el-button>
      </span>
    </el-dialog>
    <el-dialog title="编辑证明信息" :close-on-click-modal="false" :visible.sync="dialogVisible3" class=" dialog-style" width="45%">
      <div class="dialog-edit">
      <el-form ref="formRelativesEdit" :model="formRelativesEdit" label-width="100px" :inline="true">
        <el-form-item label="干部姓名">
          <el-input v-model="formRelativesEdit.a0101" clearable size="mini" placeholder="请输入干部姓名"></el-input>
        </el-form-item>
        <el-form-item label="身份证号码">
          <el-input v-model="formRelativesEdit.a0184" clearable size="mini" placeholder="请输入身份证号码"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="formRelativesEdit.a0104" clearable style="width:163px" placeholder="请选择性别" size="mini">
            <el-option v-for="item in a0104s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="父亲">
          <el-input v-model="formRelativesEdit.father" clearable size="mini" placeholder="请输入父亲姓名"></el-input>
        </el-form-item>
        <el-form-item label="母亲">
          <el-input v-model="formRelativesEdit.mother" clearable size="mini" placeholder="请输入母亲姓名"></el-input>
        </el-form-item>
        <el-form-item label="配偶">
          <el-input v-model="formRelativesEdit.spouse" clearable size="mini" placeholder="请输入配偶姓名"></el-input>
        </el-form-item>
        <el-form-item label="女儿">
          <el-input v-model="formRelativesEdit.daughter" clearable size="mini" placeholder="请输入女儿姓名"></el-input>
        </el-form-item>
        <el-form-item label="儿子">
          <el-input v-model="formRelativesEdit.son" clearable size="mini" placeholder="请输入儿子姓名"></el-input>
        </el-form-item>
        <el-form-item label="证明日期">
          <el-date-picker v-model="formRelativesEdit.proveDate" clearable style="width:163px" type="date" placeholder="请选择证明日期" value-format="yyyy-MM-dd" size="mini"/>
        </el-form-item>
      </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible3 = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveRelativesEdit">保 存</el-button>
      </span>
    </el-dialog>
    <!--海关工作证- 新增 -->
    <el-dialog title="新增海关工作证明" :close-on-click-modal="false" :visible.sync="dialogVisible2" class=" dialog-style" width="65%">
      <div class="dialog-center">
        <div class="div-left">
          <span >第一步：请输入想要搜索的干部姓名</span>
          <el-form :data="formsearchCustoms" ref="ruleForm" :inline="true"  label-width="70px" class="search-people">
            <el-form-item label="干部状态">
              <el-select v-model="formsearchCustoms.state" style="width: 150px" clearable filterable class="inputW" placeholder="请选择干部状态" size="mini">
                <el-option v-for="item in states" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
              </el-select>
            </el-form-item>
            <el-form-item label="干部姓名" prop="a0101" >
              <el-input v-model="formsearchCustoms.a0101" clearable placeholder="请输入干部姓名" style="width:130px" size="mini"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="mini" @click="queryRelaH">搜索</el-button>
            </el-form-item>
          </el-form>
          <el-table
            ref="tableRefsCustomsAdd"
            :data="tableData4"
            height="220px"
            size="mini"
            :header-cell-style="headClass"
            tooltip-effect="light"
            empty-text=" "
            border
            row-key="id"
            @row-click="StepsListRowClickCustomsAdd"
            :stripe="true">
              <el-table-column type="index" label="序号" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column label="干部姓名" prop="a0101" show-overflow-tooltip align="center" width="80px"></el-table-column>
              <el-table-column label="性别" prop="a0104" show-overflow-tooltip align="center" :formatter="getFullName" width="60px"></el-table-column>
              <el-table-column label="干部状态" prop="state" show-overflow-tooltip align="center" :formatter="getFullName" width="80px"></el-table-column>
              <el-table-column label="身份证号码" prop="a0184" show-overflow-tooltip align="center" width="160px"></el-table-column>
              <el-table-column label="证明日期" prop="proveDate" show-overflow-tooltip align="center" :formatter="getFullName" width="100px"></el-table-column>
            </el-table>
<!--            <el-pagination style="float: right;" small="small" @size-change="handleSizeChange" @current-change="handleCurrentChange"-->
<!--            :current-page="currentPage4" :page-sizes="[100, 200, 300, 400]" :page-size="100"-->
<!--            layout="total, sizes, prev, pager, next, jumper" :total="400"></el-pagination>-->
          <el-pagination
            class="page"
            :page-sizes="[20, 50, 100, 200]"
            :page-size="20"
            layout="total, sizes, prev, pager, next, jumper"
            :total="formsearchCustoms.total"
            @size-change="handleSizeChange3"
            @current-change="handleCurrentChange3"
          />
        </div>
        <div class="right-tree">
        <span>第二步：请填写信息</span>
        <div class="form-div form-divC">
        <el-form ref="formCustoms" label-width="114px" :inline="true">
        <el-form-item label="干部姓名" class="input-style">
          <el-input v-model="formCustoms.a0101" clearable size="mini" placeholder="请输入干部姓名"></el-input>
        </el-form-item>
        <el-form-item label="部门">
          <treeselect
            v-model="formCustoms.deptId"
            filterable
            :normalizer="deptNormalizer"
            :options="deptOptions"
            style="width:163px"
            placeholder="请选择部门"
          />
        </el-form-item>
        <el-form-item label="身份证号码" class="input-style">
          <el-input v-model="formCustoms.a0184" clearable size="mini" placeholder="请输入身份证号码"></el-input>
        </el-form-item>
        <el-form-item label="参加工作时间" class="input-style">
          <el-date-picker v-model="formCustoms.a0134" clearable style="width:163px" type="date" value-format="yyyy-MM-dd" placeholder="请选择参加工作时间" size="mini"/>
        </el-form-item>
        <el-form-item label="证明日期" class="input-style input-style-bottom">
          <el-date-picker v-model="formCustoms.proveDate" clearable style="width:163px" type="date" value-format="yyyy-MM-dd" placeholder="请选择证明日期" size="mini"/>
        </el-form-item>
        </el-form>
      </div>
      </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible2 = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveCustoms">保 存</el-button>
      </span>
    </el-dialog>
    <el-dialog title="编辑证明信息" :close-on-click-modal="false" :visible.sync="dialogVisible4" class=" dialog-style" width="45%">
      <div class="dialog-edit">
      <el-form ref="formCustomsEdit" :model="formCustomsEdit" label-width="100px" :inline="true">
        <el-form-item label="干部姓名">
          <el-input v-model="formCustomsEdit.a0101" clearable size="mini" placeholder="请输入干部姓名"></el-input>
        </el-form-item>
        <el-form-item label="部门">
          <treeselect
            v-model="formCustomsEdit.deptId"
            filterable
            :normalizer="deptNormalizer"
            :options="deptOptions"
            style="width:163px"
            placeholder="请选择部门"
          />
        </el-form-item>
        <el-form-item label="身份证号码">
          <el-input v-model="formCustomsEdit.a0184" clearable size="mini" placeholder="请输入身份证号码"></el-input>
        </el-form-item>
        <el-form-item label="参加工作日期">
          <el-date-picker v-model="formCustomsEdit.a0134" clearable style="width:163px" type="date"  value-format="yyyy-MM-dd" placeholder="请选择参加工作日期" size="mini"/>
        </el-form-item>
        <el-form-item label="证明日期">
          <el-date-picker v-model="formCustomsEdit.proveDate" clearable style="width:163px" type="date" value-format="yyyy-MM-dd" placeholder="请选择证明日期" size="mini"/>
        </el-form-item>
      </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible4 = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveCustomsEdit">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import { getList,deleteById, quertRelativeList, savePorve, saveEdit, exportWord, downloadHai} from "@/api/usually/prove"
  import {queryDictionaryByType, queryLoginUser, queryDeptTree } from '@/api/usual/receive'
  // 下拉树
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  export default {
    components: { Treeselect },
    name: '出具证明',
    watch: {
//       filterText(val) {
//         this.$refs.tree.filter(val);
//       },
      tableData1:{
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(()=>{
            _this.$refs.tableRefs.doLayout();
          })
        },
        deep:true,
        immediate:true
      },
      tableData2:{
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(()=>{
            _this.$refs.tableRefsCustoms.doLayout();
          })
        },
        deep:true,
        immediate:true
      },
      tableData3:{
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(()=>{
            _this.$refs.tableRefsAdd.doLayout();
          })
        },
        deep:true,
        immediate:true
      },
      tableData4:{
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(()=>{
            _this.$refs.tableRefsCustomsAdd.doLayout();
          })
        },
        deep:true,
        immediate:true
      },
    },
    data() {
      return {
        //复选框
        checkbox: [],
        checkboxDatas: [],
        //字典表转换
        a0104s: [],
        states: [],
        // 部门树数据
        deptOptions: [],
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
        checkboxCustoms: {
          a0101:'',
          a01Id:'',
          deptId:'',
          deptName:'',
          a0104:'',
          state:'',
          a0184:'',
          proveType:'',
          proveDate:''
        },
        checkboxDatasCustoms: [],

        formCustomsEdit:{
          a0101:'',
          a01Id:'',
          deptId:'',
          deptName:'',
          a0104:'',
          state:'',
          a0184:'',
          proveType:'',
          proveDate:''
        },
        formCustoms:{
          a0101:'',
          a01Id:'',
          deptId:null,
          deptName:'',
          a0104:'',
          state:'',
          a0184:'',
          father:'',
          mother:'',
          spouse:'',
          daughter:'',
          proveType:'',
          proveDate:''
        },
        rules:[],
        tableHeight:'73%',
        searchRow:'searchRow',
        // showSearchBox: true,
        dialogVisible1:false,//亲属关系-新增
        dialogVisible2:false, //海关证明-新增
        dialogVisible3:false, //亲属关系-编辑
        dialogVisible4:false, //海关证明-编辑
        showdaliog: 'true',  //亲属关系-控制选中一行
        showdaliog1: 'true',  //海关证明-控制选中一行
        checkList:[],//亲戚关系-选中一行数据
        checkListCustoms:[],//海关证明-选中一行数据
        checkListadd:[],//新增-亲戚关系-选中一行数据
        checkListaddCustoms:[],//新增-海关证明-选中一行数据
        formSearch: { //搜索框
          a0101: '',
          a0184: '',
          proveType:'1',
          state: '',
          pageNum:1,
          pageSize:20,
          total:0
        },
        formsearchCustoms:[],

        //亲属关系新增-搜索表单
        formsearchRelatives:{
          a0101:'',
          state:'',
          pageNum:1,
          pageSize:20,
          total:0
        },
        formRelatives:{  //新增右侧-填写表单
          a0101:'',
          a01Id:'',
          deptId:'',
          deptName:'',
          a0104:'',
          state:'',
          a0184:'',
          father:'',
          mother:'',
          son:'',
          spouse:'',
          daughter:'',
          proveType:'',
          proveDate:''
        },
        tableData3:[],
        formRelativesEdit:{  //亲属关系-编辑表单
          a0101:'',
          a01Id:'',
          deptId:'',
          deptName:'',
          a0104:'',
          state:'',
          a0184:'',
          father:'',
          mother:'',
          spouse:'',
          daughter:'',
          son:'',
          proveType:'',
          proveDate:''
        },
        // 亲属关系证明-表格数据
        tableData1:[],
        tableData2:[],
        tableData4:[],
        //分页
        currentPage1: 5,
        currentPage2: 5,
        currentPage3: 5,
        currentPage4: 4
      }

    },
    created() {
      this.getGridList();
      this.initColumnMap()
    },
    mounted() {
      this.getDeptTree()
      this.$nextTick(()=>{
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 70 + this.searchRowHeight)) + "px"
      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (84 + 70 + this.searchRowHeight)) + "px"
        })();
      };
    },
    methods: {

      formatTime(row,col){
        if(row[col.property]!=null&&row[col.property]!=''){
          var d = new Date(row[col.property]),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();
          if (month.length < 2) month = '0' + month;
          if (day.length < 2) day = '0' + day;
          return [year, month, day].join('-');
        }
      },

      //新增亲属关系证明查询按钮
      queryRela(){
        this.loading = true;
        quertRelativeList(this.formsearchRelatives).then(response =>{
          if (response.rows!=null){
            this.tableData3 = response.rows;
            this.formsearchRelatives.total = response.total;
          }else {
            this.tableData3=[]
            this.formsearchRelatives.total = response.total;
          }
          this.loading = false;
        })
      },
    //新增亲属关系证明保存按钮
      saveRelative(){
        savePorve(this.formRelatives)
          .then(response => {
            if(response.code==200){
              this.$message({
                message: '新增成功',
                type: 'success'
              });
              this.dialogVisible1=false
              this.getGridList()
            }else{
              this.$message({
                message: '新增失败',
                type: 'error'
              });
              this.dialogVisible1=false
              this.getGridList()
            }
          })
      },
      //新增亲属关系证编辑保存按钮
      saveRelativesEdit(){
        saveEdit(this.formRelativesEdit)
          .then(response => {
            if(response.code==200){
              this.$message({
                message: '修改成功',
                type: 'success'
              });
              this.dialogVisible3=false
              this.getGridList()
            }else{
              this.$message({
                message: '修改失败',
                type: 'error'
              });
              this.dialogVisible3=false
              this.getGridList()
            }
          })
      },
      //海关工作证明查询按钮
      queryRelaH(){
        this.loading = true;
        quertRelativeList(this.formsearchRelatives).then(response =>{
          if (response.rows!=null){
            this.tableData4 = response.rows;
          }else {
            this.tableData4=[]
          }
          this.formsearchRelatives.total = response.total;
          this.loading = false;
        })
      },

      //海关工作证明保存按钮
      saveCustoms(){
        savePorve(this.formCustoms)
          .then(response => {
            if(response.code==200){
              this.$message({
                message: '新增成功',
                type: 'success'
              });
              this.dialogVisible2=false
              this.getGridList()
            }else{
              this.$message({
                message: '新增失败',
                type: 'error'
              });
              this.dialogVisible2=false
              this.getGridList()
            }
          })
      },

      //搜索按钮功能
      depement(){
        this.formSearch.total=0,
        this.getGridList();
      },
      // 重置按钮
      clear(){
        this.formSearch.a0101="",
        this.formSearch.a0184="",
        this.formSearch.state="",
        this.formSearch.total=0;
        this.getGridList();
      },
      handleSizeChange(){},
      handleCurrentChange(){},

      //亲属关系证明分页
      handleSizeChange1(val){
        this.formSearch.pageSize=val,
        this.formSearch.pageNum=this.formSearch.pageNum,
        this.getGridList()
      },
      handleCurrentChange1(val){
        this.formSearch.pageSize=this.formSearch.pageSize,
        this.formSearch.pageNum=val,
        this.getGridList()
      },
      //新增亲属关系证明分页
      handleSizeChange3(val){
        console.log("新增亲属关系证明分页")
        console.log(val)
        this.formsearchRelatives.pageSize=val;
        this.formsearchRelatives.pageNum=this.formsearchRelatives.pageNum
        this.queryRela();
      },
      handleCurrentChange3(val){
        this.formsearchRelatives.pageSize=this.formsearchRelatives.pageSize;
        this.formsearchRelatives.pageNum=val
        this.queryRela();
      },

      //海关工作证明分页
      handleSizeChange2(){
        this.formSearch.pageSize=val,
          this.formSearch.pageNum=this.formSearch.pageNum,
          this.getGridList()
      },
      handleCurrentChange2(){
        this.formSearch.pageSize=this.formSearch.pageSize,
          this.formSearch.pageNum=val,
          this.getGridList()
      },

      getGridList(){
        this.loading = true;
        getList(this.formSearch).then(response =>{
          if (this.formSearch.proveType==="1"){
            if (response.rows!=null){
              this.tableData1 = response.rows;
            }else {
              this.tableData1 = []
            }
          }else {
            if (response.rows!=null){
              this.tableData2 = response.rows;
            }else {
              this.tableData2 = []
            }
          }
          // response.rows.map((item,index)=>{
          //   let indexs = (index + 1).toString();
          //   if(index.toString().length<3){
          //     for(let i = 0;i<3-index.toString().length;i++){
          //       indexs = '0' + indexs;
          //     }
          //   }
          //   item.code = indexs
          // })
          this.formSearch.total = response.total;
          this.loading = false;
        })
      },

      //tab切换事件
      tabClick(tab, event) {
        if (tab.label === '亲属关系证明') {
          this.formSearch.proveType="1",
          this.formSearch.a0101="",
          this.formSearch.a0184=""
          this.formSearch.state="",
          this.formSearch.pageNum=1,
          // this.formSearch.pageSize=20,
          this.searchRow = 'searchRow'
          this.getGridList();
        } else if (tab.label === '海关工作证明') {
          this.formSearch.proveType="2",
            this.formSearch.a0101="",
            this.formSearch.a0184="",
            this.formSearch.state="",
            this.formSearch.pageNum=1,
           // this.formSearch.pageSize=20,
            this.searchRow = 'searchRow2',
            this.getGridList();
        }
      },
      //新增-亲属关系-点击一行
      StepsListRowClickadd(row){
        this.formRelatives.a0101=row.a0101
        this.formRelatives.a01Id=row.id
        this.formRelatives.deptId=row.deptId;
        this.formRelatives.deptName=row.deptName
        this.formRelatives.a0104=row.a0104
        this.formRelatives.state=row.state
        this.formRelatives.a0184=row.a0184
        this.formRelatives.proveType="1"
        // this.$refs.tableRefsAdd.clearSelection()
        // if (this.checkListadd.length === 0) {
        //   this.$refs.tableRefsAdd.toggleRowSelection(row)
	      //   return;
	      // }
        // this.$refs.tableRefsAdd.toggleRowSelection(row)
      },
      // 新增-海关证明-点击一行
      StepsListRowClickCustomsAdd(row){
        this.formCustoms.a0101=row.a0101
        this.formCustoms.a01Id=row.id
        this.formCustoms.deptId=row.deptId;
        this.formCustoms.deptName=row.deptName
        this.formCustoms.a0104=row.a0104
        this.formCustoms.state=row.state
        this.formCustoms.a0184=row.a0184
        if(row.a0134!=null&&row.a0134!=''){
          var d1 = new Date()
          d1.setFullYear(row.a0134.substring(0,4))
          d1.setMonth(row.a0134.substring(4,6))
          d1.setDate(row.a0134.substring(6,8))
        }
        this.formCustoms.a0134=d1
        if(row.proveDate!=null&&row.proveDate!=''){
          var d2 = new Date()
          d2.setFullYear(row.proveDate.substring(0,4))
          d2.setMonth(row.proveDate.substring(4,6))
          d2.setDate(row.proveDate.substring(6,8))
        }
        this.formCustoms.proveDate=d2
        this.formCustoms.proveType="2"
        console.log(this.formCustoms)
      },
      // 公用表格表头样式
      headClass() {
        return 'background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px'
      },
      // 表格勾选方法
      selectChecked(data) {
        // this.selectList = [...data]
        // this.showdaliog = this.selectList.length === 0
        this.checkboxDatas = data
        this.checkbox = data.map(item => item.id)
      },
      // 功能按钮
      // 亲属关系-新增
      onAddRelatives() {
      this.dialogVisible1=true
      },
      //亲属关系-编辑
      onUpdateRelatives() {
        if(this.checkboxDatas==null || this.checkboxDatas.length==0){
          this.$message({
            type: 'warning',
            message: '请勾选需要编辑的证明'
          })
          return
        }
        if(this.checkboxDatas.length>1){
          this.$message({
            type: 'warning',
            message: '请勾选一条需要编辑的证明'
          })
          return
        }
        this.formRelativesEdit=this.checkboxDatas[0]
        this.dialogVisible3=true
      },
      //亲属关系-删除
      onDeleteRelatives() {
        if(this.checkboxDatas==null || this.checkboxDatas.length==0){
          this.$message({
            type: 'warning',
            message: '请勾选要删除的信息'
          })
          return
        }
        if(this.checkboxDatas.length>1){
          this.$message({
            type: 'warning',
            message: '请勾选一条要删除的信息'
          })
          return
        }
        this.$confirm('此操作将永久删除所选信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            // const deleteId = this.checkbox.join('-')
            // const id = deleteId || val.id
            if (this.checkbox[0] != '') {
              deleteById({ id: this.checkbox[0] })
                .then(response => {
                  if(response.code==200){
                    this.$message({
                      message: '删除成功',
                      type: 'success'
                    });
                    this.getGridList()
                  }else{
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
      //亲属关系-导出
      onReturnRelatives() {
        if(this.checkboxDatas==null || this.checkboxDatas.length==0){
          this.$message({
            type: 'warning',
            message: '请勾选要导出的信息'
          })
          return
        }
        if(this.checkboxDatas.length>1){
          this.$message({
            type: 'warning',
            message: '请勾选一条要导出的信息'
          })
          return
        }
        // exportWord({id:this.checkbox[0]})
        window.open("/issue/exportWord?id="+this.checkbox[0])
        console.log("id="+this.checkbox[0])
          // .then(response => {
          //   if(response.code==200){
          //     this.$message({
          //       message: '下载成功',
          //       type: 'success'
          //     });
          //   }else{
          //     this.$message({
          //       message: '下载失败',
          //       type: 'error'
          //     });
          //   }
          // })
      },
      //打印
      onCopyRelatives() {

      },
      //亲属关系表格只能-点击一行事件
      StepsListRowClick(row){
         this.$refs.tableRefs.clearSelection()
      if (this.checkList.length === 0) {
           this.$refs.tableRefs.toggleRowSelection(row)
           this.checkList = row
           this.formRelativesEdit=this.checkList
	        return;
	      }
        this.$refs.tableRefs.toggleRowSelection(row)
      },
      // 海关证明-------表格勾选方法
      selectCheckedCustoms(data) {
        // this.selectList = [...data]
        // this.showdaliog1 = this.selectList.length === 0
        this.checkboxDatasCustoms = data
        this.checkboxCustoms = data.map(item => item.id)
      },
      // 海关工作证-新增
      onAddCustoms() {
       this.dialogVisible2=true
      },
      //海关工作证-编辑
      onUpdateCustoms() {
        if(this.checkboxDatasCustoms==null || this.checkboxDatasCustoms.length==0){
          this.$message({
            type: 'warning',
            message: '请勾选需要编辑的证明'
          })
          return
        }
        if(this.checkboxDatasCustoms.length>1){
          this.$message({
            type: 'warning',
            message: '请勾选一条需要编辑的证明'
          })
          return
        }
        this.formCustomsEdit=this.checkboxDatasCustoms[0]
        this.dialogVisible4=true

      },
      //海关工作证-编辑-保存
      saveCustomsEdit(){
        saveEdit(this.formCustomsEdit)
          .then(response => {
            if(response.code==200){
              this.$message({
                message: '修改成功',
                type: 'success'
              });
              this.dialogVisible4=false
              this.getGridList()
            }else{
              this.$message({
                message: '修改失败',
                type: 'error'
              });
              this.dialogVisible4=false
              this.getGridList()
            }
          })
      },
      //海关工作证-删除
      onDeleteCustoms() {
        if(this.checkboxDatasCustoms==null || this.checkboxDatasCustoms.length==0){
          this.$message({
            type: 'warning',
            message: '请勾选要删除的信息'
          })
          return
        }
        if(this.checkboxDatasCustoms.length>1){
          this.$message({
            type: 'warning',
            message: '请勾选一条要删除的信息'
          })
          return
        }
        this.$confirm('此操作将永久删除所选信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            // const deleteId = this.checkbox.join('-')
            // const id = deleteId || val.id
            if (this.checkboxCustoms[0] != '') {
              deleteById({ id: this.checkboxCustoms[0] })
                .then(response => {
                  if(response.code==200){
                    this.$message({
                      message: '删除成功',
                      type: 'success'
                    });
                    this.getGridList()
                  }else{
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
      //海关工作证-导出
      onReturnCustoms() {
        if(this.checkboxDatasCustoms==null || this.checkboxDatasCustoms.length==0){
          this.$message({
            type: 'warning',
            message: '请勾选要导出的信息'
          })
          return
        }
        if(this.checkboxDatasCustoms.length>1){
          this.$message({
            type: 'warning',
            message: '请勾选一条要导出的信息'
          })
          return
        }
        console.log(this.checkboxDatasCustoms)
        console.log(this.checkboxCustoms[0])
        window.open("/issue/downloadHai?id="+this.checkboxCustoms[0])
      },
      //海关工作证-打印
      onCopyCustoms() {
      },
      //海关证明表格只能-点击一行事件
      StepsListRowClickCustoms(row){
         this.$refs.tableRefsCustoms.clearSelection()
      if (this.checkListCustoms.length === 0) {
           this.$refs.tableRefsCustoms.toggleRowSelection(row)
           this.checkListCustoms = row
           this.formCustomsEdit=this.checkListCustoms
	        return;
	      }
        this.$refs.tableRefsCustoms.toggleRowSelection(row)
      },
      // 表格
      onTableSelect(rows, row) {
        // console.log(rows, row)
        let selected = rows.length && rows.indexOf(row) !== -1
        return selected
      },
      // 初始化转换参数
      initColumnMap() {
        // 当前页面所需字典表数据 性别
        queryDictionaryByType({ dictType: 'GB22611' })
          .then(response => {
            this.a0104s = response.data
          })
        // 获取干部状态
        queryDictionaryByType({ dictType: 'FDA01State' })
          .then(response => {
            this.states = response.data
          })
      },
      // 时间格式转换
      getFullName(row, col) {
        // 时间转换
        if(col.property=="a0151" || col.property=="a0134" ||col.property=="proveDate"){
          if(row[col.property]!=null&&row[col.property]!='') {
            var date = new Date(row[col.property])
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            m = m < 10 ? '0' + m : m;
            var d = date.getDate();
            d = d < 10 ? ('0' + d) : d;
            return y + '-' + m + '-' + d;
          }
          /*if(row[col.property]!=null&&row[col.property]!=''){
            var d = new Date(row[col.property]),
              month = '' + (d.getMonth() + 1),
              day = '' + d.getDate(),
              year = d.getFullYear();
            if (month.length < 2) month = '0' + month;
            if (day.length < 2) day = '0' + day;
            return [year, month, day].join('-');
          }*/
        }
        // 性别
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
        return row[col.property]
      },
      // 获取部门树
      getDeptTree() {
        queryDeptTree({deptType: 1}).then(response => {
          this.deptOptions = response.data
        })
      },
    }
  }
</script>
<style lang="scss" scoped>
  .app-prove{
    height: 100%;
    // height: calc(100vh - 86px);
    // .table-Style {
    //   width: 100%;
    // }
    .form-search{
      width:100%;
      background:rgba(204, 204, 204, 0.18);
      padding: 10px;
      display:flex;
    .form-width{
      display:flex;
    }
  }
    .btn-div{
      position: relative;
      padding-top: 10px;
      padding-left: 5px;
      .btn-right {
        position: absolute;
        right: 0px;
        top: 0px;
        margin-right: 80px;
        margin-top: 10px;
      }
    }
    /deep/ .el-form-item{
      margin-bottom: 0 !important;
    }
    .page{
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
  //证明弹框-新增
  .dialog-center {
    width: 100%;
    height: 320px;
    clear: both;
    .div-left {
      width: 62%;
      float: left;
      margin-right:10px;
      .search-people {
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
      width: 36%;
      float: right;
      .form-div{
        border: 2px solid rgba(204, 204, 204, 0.18);
        height: 285px;
        overflow: auto;
        margin-top:15px;
        .input-style{
            padding-top: 5px;
        }
        .input-style-bottom{
          padding-bottom: 10px;
        }
      }
      .form-divC{
        height: 260px;
        padding-top: 10px;
      }
    }
    /deep/.el-form-item {
    margin-bottom: 0px;
    }
      /deep/.el-table--border th.gutter:last-of-type{
       background-color:  rgba(204, 204, 204, 0.18);
     }
  }
  // 设置列高
  /deep/ .el-table .el-table__header-wrapper th{
    height:31px;
  }
  // 操作列颜色问题
  /deep/ .el-table__fixed-right-patch {
    background-color:#F8F8F9
  }
  // treeSelect高度调整
  /deep/ .vue-treeselect__control{
    margin-top: 5px;
    height: 28px !important;
    display: flow-root;
  }
  /deep/ .vue-treeselect__input-container{
    height: 28px !important;
    display: flow-root;
  }
  /deep/ .vue-treeselect__input, .vue-treeselect__sizer{
    height: 28px !important;
    display: flow-root;
  }
  // 编辑
  .dialog-edit{
    // padding-left: 45px;
  }
  </style>

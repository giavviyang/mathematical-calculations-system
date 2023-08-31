<template>
  <div class="app-daily">
    <el-row>
      <el-col :span="4" :style="leftStyleName">
        <!-- 树结构 -->
<!--        id="treescroll"-->
        <el-input placeholder="请输入内容" v-if="treeShow" size="mini" class="inp-select" style="width:90%;padding:20px 0 0 20px"
                  v-model="treeFilter"></el-input>
        <div class="tree tree-style" >
          <el-scrollbar style="height:100%">
            <el-tree
              ref="tree4"
              :expand-on-click-node="false"
              :data="treeData"
              :props="TreeDefaultProps"
              icon-class="el-icon-folder-opened"
              :highlight-current="true"
              :filter-node-method="treeNode"
              @node-click="handleNodeClick"
              default-expand-all/>
          </el-scrollbar>
        </div>
        <div class="shrinkageDiv" @click="divClick()">
          <div :class="picClassname"></div>
        </div>
      </el-col>
      <el-col :span="20" style="height: calc(100vh - 86px)" :style="rightStyleName">
          <el-row ref="searchRow" v-hasPermi="['use:browse:search']">
            <el-form :inline="true" ref="formSearch" label-width="100px" :model="formSearch" class="search-form">
              <el-form-item label="干部状态" class="form-width">
                <el-select v-model="formSearch.state" placeholder="请选择" size="mini" clearable>
                  <el-option label="在职" value="0"></el-option>
                  <el-option label="离职" value="1"></el-option>
                  <el-option label="退休" value="2"></el-option>
                  <el-option label="死亡" value="3"></el-option>
                  <el-option label="已转出" value="7"></el-option>
                  <el-option label="其他" value="4"></el-option>
                  <el-option label="全部" value="5"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="编制类别" class="form-width">
                <el-select v-model="formSearch.data4" placeholder="请选择" size="mini" clearable>
                  <el-option v-for="item in data4" :label="item.fullName" :value="item.dictCode"/>
                  <!--                <el-option label="缉私" value="1"></el-option>-->
                  <!--                <el-option label="事业" value="2"></el-option>-->
                  <!--                <el-option label="企业" value="3"></el-option>-->
                  <!--                <el-option label="其他" value="4"></el-option>-->
                  <el-option label="全部" value="5"></el-option>
                </el-select>
                <!--              <el-input v-model="formSearch.a0184" placeholder="请输入内容" size="mini"></el-input>-->
              </el-form-item>
              <el-form-item @keyup.enter.native="onEnterSearch" label="干部姓名" class="form-width">
                <el-input v-model="formSearch.a0101" placeholder="请输入干部姓名" size="mini" clearable></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" size="mini" icon="el-icon-search" @click="depement()">搜索</el-button>
                <!--              <el-button size="mini" icon="el-icon-refresh" @click="clear()">重置</el-button>-->
                <el-button type="primary" size="mini" icon="el-icon-zoom-in" @click="searchDialog()">高级检索</el-button>
              </el-form-item>
            </el-form>
          </el-row>
        <div class="btn">
          <el-button type="primary" v-hasPermi="['use:browse:export']" size="mini" icon="el-icon-download" @click="onReturn">导出</el-button>
        </div>
        <el-table
          v-loading="loading"
          ref="tableRefs1"
          size="mini"
          :data="tableData1"
          :height="tableHeight "
          tooltip-effect="light"
          border
          :header-cell-style="headClass"
          stripe
          empty-text=" "
          @row-click="StepsListRowClick" @selection-change="selectChecked">
          <el-table-column type="selection" align="center"></el-table-column>
          <el-table-column label="序号" width="50px" align="center" :formatter="customIndex"/>
          <el-table-column label="干部姓名" prop="a0101" show-overflow-tooltip align="center"></el-table-column>
          <el-table-column label="干部编号" prop="number" show-overflow-tooltip align="center"></el-table-column>
          <el-table-column label="部门" prop="deptName" show-overflow-tooltip width="100px"
                           align="center"></el-table-column>
          <el-table-column label="出生日期" prop="a0107" :formatter="conversionTime" show-overflow-tooltip width="100px"
                           align="center"></el-table-column>
          <el-table-column label="入党日期" prop="a0144" :formatter="conversionTime" show-overflow-tooltip width="100px"
                           align="center"></el-table-column>
          <el-table-column label="参加工作时间" prop="a0134" :formatter="conversionTime" show-overflow-tooltip width="130px"
                           align="center"></el-table-column>
          <el-table-column label="学历" :formatter="tableFormatter" prop="education" show-overflow-tooltip width="100px"
                           align="center"></el-table-column>
          <el-table-column label="干部状态" :formatter="tableFormatter" prop="state" show-overflow-tooltip width="80px"
                           align="center"></el-table-column>
          <el-table-column label="编制类别" :formatter="tableFormatter" prop="data4" show-overflow-tooltip
                           align="center"></el-table-column>
          <el-table-column label="职务" :formatter="tableFormatter" width="100px" prop="duty" show-overflow-tooltip
                           align="center"></el-table-column>
          <el-table-column label="个人身份" :formatter="tableFormatter" width="100px" prop="a0151" show-overflow-tooltip
                           align="center"></el-table-column>
          <el-table-column fixed="right" label="操作" align="center" width="150px">
            <template slot-scope="scope">
              <el-button type="text" v-hasPermi="['use:browse:basic']" @click="message(scope.row)" size="mini">基本信息</el-button>
              <router-link  v-hasPermi="['use:browse:materials']" :to="{path:'/usually/browse/catalog/'+ scope.row.a0101 + '/' + scope.row.id}" class="link-type">
                材料浏览
              </router-link>
              <!--<el-button type="text" @click="recordMaterial(scope.row)" size="mini">材料管理</el-button>-->
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
        <div class="page" style="margin-top: 5px;">
          <el-pagination style="width: 100%;" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                         :current-page="this.formSearch.pageNum" :page-sizes="[20, 50, 100, 200]" :page-size="this.formSearch.pageSize"
                         layout="total, sizes, prev, pager, next, jumper"
                         :total="this.formSearch.total"></el-pagination>
        </div>
      </el-col>
    </el-row>
    <!-- 高级检索对话框 -->
    <el-dialog title="高级检索"
    :close-on-click-modal="false"
    :append-to-body="true"
    :visible.sync="dialogVisible"
    class="dialog-style dialog-high"
               width="65%">
      <el-form :inline="true" class="form-search">
        <el-form-item label="查询名称" label-width="80px" size="mini" style="margin-top: 3px;margin-bottom: 0px">
          <el-select @change="resetHigh" v-model="highSearch.key1" placeholder="请选择查询名称" clearable>
            <el-option label="干部姓名" value="a0101"></el-option>
            <el-option label="档案编号" value="number"></el-option>
            <el-option label="部门" value="dept_id"></el-option>
            <el-option label="出生日期" value="a0147"></el-option>
            <el-option label="入党日期" value="a0144"></el-option>
            <el-option label="参加工作时间" value="a0134"></el-option>
            <el-option label="学历" value="education"></el-option>
            <el-option label="干部状态" value="state"></el-option>
            <el-option label="编制类别" value="data4"></el-option>
            <el-option label="职务" value="duty"></el-option>
            <el-option label="个人身份" value="a0151"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="查询条件" label-width="80px" size="mini" style="margin-top: 3px;margin-bottom: 0px">
          <el-select v-model="highSearch.key2" placeholder="请选择查询条件" clearable>
            <el-option label="包含" value="con"></el-option>
            <el-option label="不等于" value="nq"></el-option>
            <el-option label="大于" value="gt"></el-option>
            <el-option label="小于" value="lt"></el-option>
            <el-option label="小于等于" value="le"></el-option>
            <el-option label="大于等于" value="ge"></el-option>
            <el-option label="等于" value="eq"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="查询内容" label-width="80px" size="mini" style="margin-top: 3px;margin-bottom: 0px">
          <!--          &&highSearch.key1!='data4'-->
          <el-input
            v-if="highSearch.key1!='state'&&highSearch.key1!='dept_id'&&highSearch.key1!='duty'&&highSearch.key1!='data4'&&highSearch.key1!='education'&&highSearch.key1!='a0151'"
            class="inputW" size="mini" v-model="highSearch.key3" placeholder="请输入查询内容" clearable></el-input>
          <el-select v-if="highSearch.key1=='state'" @change="setKey4" v-model="highSearch.key3" placeholder="请选择"
                     size="mini" clearable>
            <el-option label="在职" value="0"></el-option>
            <el-option label="离职" value="1"></el-option>
            <el-option label="退休" value="2"></el-option>
            <el-option label="死亡" value="3"></el-option>
            <el-option label="已转出" value="7"></el-option>
            <el-option label="其他" value="4"></el-option>
            <el-option label="全部" value="5"></el-option>
          </el-select>

          <el-select v-if="highSearch.key1=='dept_id'" v-model="highSearch.key3" placeholder="请选择部门" clearable
                     class="inputW" size="mini" ref="selectReport3">
            <el-option :value="highSearch.key3" :label="highSearch.key4"
                       style="height:200px;width:200px;overflow:auto;background-color:#fff">

              <el-tree
                :data="treeData"
                :props="TreeDefaultProps"
                @node-click="handleDeptSelectClick3"
              ></el-tree>

            </el-option>
          </el-select>

          <el-select v-if="highSearch.key1=='education'" @change="setKey4" v-model="highSearch.key3" placeholder="请选择学历"
                     size="mini" clearable class="inputW">
            <el-option v-for="item in education" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>

          </el-select>

          <el-select v-if="highSearch.key1=='a0151'" @change="setKey4" v-model="highSearch.key3" placeholder="请选择个人身份"
                     size="mini" clearable class="inputW">
            <el-option v-for="item in a0151s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>

          <el-select v-if="highSearch.key1=='data4'" @change="setKey4" v-model="highSearch.key3" placeholder="请选择编制类别"
                     size="mini" clearable class="inputW">
            <el-option label="海关" value="0"></el-option>
            <el-option label="缉私" value="1"></el-option>
            <el-option label="事业" value="2"></el-option>
            <el-option label="企业" value="3"></el-option>
            <el-option label="其他" value="4"></el-option>
            <el-option label="全部" value="5"></el-option>
          </el-select>

          <el-select v-if="highSearch.key1=='duty'" @change="setKey4" v-model="highSearch.key3" placeholder="请选择职务"
                     size="mini" clearable class="inputW">
            <el-option v-for="item in dutys" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button :inline="true" size="mini" type="primary" @click="addCondition"> 添加</el-button>
        </el-form-item>
      </el-form>
      <!-- 搜索框表格 :row-key="id" -->
      <el-table
        :data="tableData"
        ref="tableRefs"
        size="mini"
        height="240px"
        class="high-table"
        empty-text=" "
        tooltip-effect="light"
        border
        stripe
        :destroy-on-close="true"
        :header-cell-style="headClass"
      >
        <el-table-column type="index" label="序号" width="50px" align="center"/>
        <el-table-column label="查询名称" prop="fieldName" width="200px" show-overflow-tooltip align="center"
                         :formatter="formaterType1"></el-table-column>
        <el-table-column label="查询条件" prop="where" width="250px" show-overflow-tooltip align="center"
                         :formatter="formaterType2"></el-table-column>
        <el-table-column label="查询内容" prop="value" width="250px" show-overflow-tooltip align="center"
                         :formatter="formaterType3"></el-table-column>
        <el-table-column fixed="right" label="操作" align="center" style="height: 240px;">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="deleteHigh(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="SavehighSearch">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 排序 -->
    <el-dialog title="干部档案排序" :close-on-click-modal="false" :visible.sync="dialogVisible2"
               class=" dialog-style dialog-sort" width="50%">
      <div class="btn-sort">
        <el-button type="primary" icon="el-icon-top" size="mini">上移</el-button>
        <el-button type="primary" icon="el-icon-bottom" size="mini">下移</el-button>
        <!--          <el-button type="primary" icon="el-icon-video-pause" size="mini">保存</el-button>-->
        <!-- <span class="span-tit">当前材料类型：</span> -->
      </div>
      <!--      row-key="id"-->
      <el-table
        ref="tableRefsSort"
        :data="tableDataSort"
        size="mini"
        :header-cell-style="headClass"
        tooltip-effect="light"
        border
        class="table-sort"
        :stripe="true"
      >
        <el-table-column type="selection" align="center"/>
        <el-table-column type="index" label="序号" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column label="部门" prop="e01z111a" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column label="干部姓名" prop="e01z114" show-overflow-tooltip align="center"></el-table-column>
        <el-table-column label="出生日期" prop="e01z117" show-overflow-tooltip align="center" width=110px></el-table-column>
        <el-table-column label="人员状态" prop="wu" show-overflow-tooltip align="center" width=110px></el-table-column>
        <el-table-column label="学历" prop="e01z121" show-overflow-tooltip align="center"></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="sortCanel" size="mini">取 消</el-button>
        <el-button type="primary" size="mini" @click="sortSave">保 存</el-button>
      </span>
    </el-dialog>
    <!-- 基本信息对话框 -->
    <el-dialog title="档案信息"
    :close-on-click-modal="false"
    :append-to-body="true"
    :visible.sync="dialogVisible1"
               class=" dialog-style dialog-basic" width="90%">
      <el-form ref="form" :rules="formRules"  :inline="true" :model="form" label-position="right" >
        <div class="form-basic" style="font-weight: bold">基本信息</div>
        <el-form-item label="干部姓名" label-width="110px" prop="a0101" size="mini">
          <el-input v-model="form.a0101" @blur="loseBlur" :disabled="true" placeholder="请输入干部姓名" clearable class="inputW"/>
        </el-form-item>
<!--        <el-form-item label="干部编号" label-width="110px" prop="number" size="mini">
          <el-input v-model="form.number" placeholder="请输入干部编号" clearable class="inputW"/>
        </el-form-item> -->
        <el-form-item label="姓名简拼" label-width="110px" size="mini" prop="a0102">
          <el-input v-model="form.a0102" placeholder="请输入姓名简拼" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="性别" label-width="110px" size="mini" prop="a0104">
          <el-select v-model="form.a0104" placeholder="请选择性别" :disabled="true" clearable class="inputW">
            <el-option v-for="item in a0104s1" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="民族" label-width="110px" prop="a0117" size="mini">
          <el-select v-model="form.a0117" placeholder="请选择民族" :disabled="true" clearable class="inputW">
            <el-option v-for="item in a0117s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="部门" label-width="110px" size="mini">
          <el-select v-model="form.deptId" placeholder="请选择部门" :disabled="true" clearable class="inputW" ref="selectReport">
            <el-option :value="form.deptId" :label="form.deptName"
                       style="height:200px;width:200px;overflow:auto;background-color:#fff">
              <el-tree
                :data="treeData"
                :props="TreeDefaultProps"
                @node-click="handleDeptSelectClick"
              ></el-tree>

            </el-option>
          </el-select>
          <!--          <el-select v-model="form.dept_id" placeholder="请选择部门" clearable class="inputW">-->
          <!--            <el-option label="组织部" value="1" />-->
          <!--            <el-option label="人事部" value="2" />-->
          <!--            <el-option label="研发部" value="3" />-->
          <!--          </el-select>-->
        </el-form-item>
        <el-form-item label="出生日期" label-width="110px" prop="a0107" size="mini">
          <el-date-picker value-format="yyyy-MM-dd" :disabled="true" v-model="form.a0107" type="date" placeholder="请选择出生日期" clearable class="inputW"/>
        </el-form-item>
        <el-form-item label="入党日期" label-width="110px" prop="a0144" size="mini">
          <el-date-picker value-format="yyyy-MM-dd" :disabled="true" v-model="form.a0144" type="date" placeholder="请选择入党日期" clearable class="inputW"/>
        </el-form-item>
        <el-form-item label="参加工作日期" label-width="110px" prop="a0134" size="mini">
          <el-date-picker value-format="yyyy-MM-dd" :disabled="true" v-model="form.a0134" type="date" placeholder="请选择参加工作日期" clearable class="inputW"/>
        </el-form-item>
        <el-form-item label="学历" label-width="110px" prop="education" size="mini">
          <el-select v-model="form.education" placeholder="请选择学历" :disabled="true" clearable class="inputW">
            <el-option v-for="item in education" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>

          </el-select>
        </el-form-item>
        <el-form-item label="人员现状" label-width="110px" prop="state" size="mini">
          <el-select v-model="form.state" placeholder="请选择人员现状" :disabled="true" clearable class="inputW">
            <el-option v-for="item in states" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="编制类别" label-width="110px" prop="data4" size="mini">
          <el-select v-model="form.data4" placeholder="请选择编制类别" :disabled="true" clearable class="inputW">
            <el-option v-for="item in data4" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="个人身份" label-width="110px" prop="a0151" size="mini">
          <el-select v-model="form.a0151" placeholder="请选择个人身份" :disabled="true" clearable class="inputW">
            <el-option v-for="item in a0151s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <div class="form-supplement" style="font-weight: bold">补充信息</div>
        <el-form-item label="籍贯名称" label-width="200px" prop="a0111a" size="mini">
          <el-input v-model="form.a0111a" placeholder="请输入籍贯名称" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="出生地代码" label-width="200px" prop="a0114b" size="mini">
          <el-input v-model="form.a0114b" placeholder="请输入出生地代码" :disabled="true" clearable class="inputW"></el-input>
          <!--          <el-select v-model="form.a0111b" placeholder="请选择出生地代码" clearable class="inputW">-->
          <!--          <el-option v-for="item in a0111bs" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />-->
          <!--          </el-select>-->
        </el-form-item>

        <el-form-item label="健康状况描述" label-width="200px" prop="a0128" size="mini">
          <el-input v-model="form.a0128" placeholder="请输入健康状况描述" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="婚姻状况" label-width="200px" prop="a0131" size="mini">
          <el-select v-model="form.a0131" placeholder="请选择婚姻状况" :disabled="true" clearable class="inputW">
            <el-option v-for="item in a0131s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="健康状况" label-width="200px" prop="a0127" size="mini">
          <el-select v-model="form.a0127" placeholder="请选择健康状况" :disabled="true" clearable class="inputW">
            <el-option v-for="item in a0127s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="工龄计算正值" label-width="200px" prop="a0137" size="mini">
          <el-input  v-model.number="form.a0137" placeholder="请输入工龄计算正值" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="政治面貌" label-width="200px" prop="a0141" size="mini">
          <el-select v-model="form.a0141" placeholder="请选择政治面貌" :disabled="true" clearable class="inputW">
            <el-option v-for="item in a0141s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="现身份起始日期" label-width="200px" prop="a0154" size="mini" type="date">
          <el-date-picker type="date"  value-format="yyyy-MM-dd" :disabled="true" v-model="form.a0154" placeholder="请选择现身份起始日期" clearable class="inputW"></el-date-picker>
        </el-form-item>
        <el-form-item label="户口性质" label-width="200px" prop="a0177" size="mini">
          <el-select v-model="form.a0177" placeholder="请选择户口性质" :disabled="true" clearable class="inputW">
            <el-option v-for="item in a0177s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="户籍所在地" label-width="200px" prop="a0181b" size="mini">
          <el-input v-model="form.a0181b" placeholder="请输入户籍所在地" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="有效证件类型" label-width="200px" prop="a0183" size="mini">
          <el-select v-model="form.a0183" placeholder="请选择有效证件类型" :disabled="true" clearable class="inputW">
            <el-option v-for="item in a0183s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="证件号" label-width="200px" prop="a0184" size="mini">
          <el-input v-model="form.a0184" placeholder="请输入证件号" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="籍贯代码" label-width="200px" prop="a0111b" size="mini">
          <el-input v-model="form.a0111b" placeholder="请输入出生地代码" :disabled="true" clearable class="inputW"></el-input>
          <!--          <el-select v-model="form.a0111b" placeholder="请选择籍贯代码" clearable class="inputW">-->
          <!--          <el-option v-for="item in a0111bs" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />-->
          <!--          </el-select>-->
        </el-form-item>
        <el-form-item label="成长地" label-width="200px" prop="a0115a" size="mini">
          <el-input v-model="form.a0115a" placeholder="请输入成长地" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="成长地代码" label-width="200px" prop="a0115b" size="mini">
          <el-input v-model="form.a0115b" placeholder="请输入成长地代码" :disabled="true" clearable class="inputW"></el-input>
          <!--          <el-select v-model="form.a0115b" placeholder="请选择成长地代码" clearable class="inputW">-->
          <!--            <el-option v-for="item in a0115bs" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />-->
          <!--          </el-select>-->
        </el-form-item>
        <el-form-item label="户籍所在地代码" label-width="200px" prop="a0181b" size="mini">
          <el-input v-model="form.a0181a" placeholder="请输入户籍所在地代码" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="出生地名称" label-width="200px" prop="a0114a" size="mini">
          <el-input v-model="form.a0114a" placeholder="请输入出生地名称" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="爱好" label-width="200px" prop="a0187b" size="mini">
          <el-input v-model="form.a0187b" placeholder="请输入爱好" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="专长" label-width="200px" prop="a0187a" size="mini">
          <el-input v-model="form.a0187a" placeholder="请输入专长" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="人事关系所在单位名称" label-width="200px" prop="a0157a" size="mini">
          <el-input v-model="form.a0157a" placeholder="请输入人事关系所在单位名称" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="人事关系所在单位代码" label-width="200px" prop="a0157b" size="mini">
          <el-input v-model="form.a0157b" placeholder="请输入人事关系所在单位代码" :disabled="true" clearable class="inputW"></el-input>
          <!--          <el-select v-model="form.a0157b" placeholder="请选择人事关系所在单位" clearable class="inputW">-->
          <!--            <el-option v-for="item in a0157bs" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />-->
          <!--          </el-select>-->
        </el-form-item>
        <el-form-item label="人事关系所在单位所在政区" label-width="200px" prop="a0161" size="mini">
          <!-- <el-select v-model="form.a0161" filterable  placeholder="请选择人事关系所在单位所在政区" class="inputW">
            <el-option v-for="item in a0161s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select> -->
          <el-input v-model="form.a0161" placeholder="请选择人事关系所在单位所在政区" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="人事关系所在单位隶属关系" label-width="200px" prop="a0164" size="mini">
          <el-select v-model="form.a0164" placeholder="请选择人事关系所在单位隶属关系" :disabled="true" clearable class="inputW">
            <el-option v-for="item in a0164s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="人事关系所在单位级别" label-width="200px" prop="a0197" size="mini">
          <el-select v-model="form.a0197" placeholder="请选择人事关系所在单位级别" :disabled="true" clearable class="inputW">
            <el-option v-for="item in a0167s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="人事关系所在单位性质类别" label-width="200px" prop="a0171" size="mini">
          <el-select v-model="form.a0171" placeholder="请选择人事关系所在单位性质类别" :disabled="true" clearable class="inputW">
            <el-option v-for="item in a0171s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="人事关系所在单位所属行业" label-width="200px" prop="a0174" size="mini">
          <el-input v-model="form.a0174" placeholder="请输入人事关系所在单位所属行业" :disabled="true" clearable class="inputW"></el-input>
        </el-form-item>
        <el-form-item label="声像信息标识" label-width="200px" prop="a0191" size="mini">
          <el-select v-model="form.a0191" placeholder="请选择声像信息标识" :disabled="true" clearable class="inputW">
            <el-option v-for="item in a0191s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible1 = false">关闭</el-button>
      </span>
    </el-dialog>
    <!-- 档案查缺 -->
    <el-dialog title="档案查缺" :append-to-body="true" :close-on-click-modal="false" :visible.sync="dialogVisible3"
               class=" dialog-style dialog-lack" width="48%">
      <div class="div-lack">
        <el-form ref="formlack" :rules="rules" :inline="true" :model="missingForm" label-width="100px">
          <el-form-item label="干部姓名" label-width="100px" size="mini">
            <el-input v-model="missingForm.a0101" class="inputW" placeholder="请输入干部姓名"></el-input>
          </el-form-item>
          <el-form-item label="部门" size="mini" label-width="100px">
            <el-select v-model="missingForm.deptId" placeholder="请选择部门" clearable class="inputW" ref="selectReport">
              <el-option :value="missingForm.deptId" :label="missingForm.deptName"
                         style="height:200px;width:200px;overflow:auto;background-color:#fff">

                <el-tree
                  :data="treeData"
                  :props="TreeDefaultProps"
                  @node-click="handleDeptSelectClick2"
                ></el-tree>

              </el-option>
            </el-select>
            <!--            <el-cascader :options="treeData" :show-all-levels="false"></el-cascader>-->
          </el-form-item>
          <el-form-item label="个人身份" size="mini" label-width="100px">
            <el-select v-model="missingForm.a0151" placeholder="请选择" class="inputW" size="mini">
              <el-option v-for="item in a0151s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
              <!--              <el-option label="公务员" value="shanghai"></el-option>-->
              <!--              <el-option label="事业人员" value="beijing"></el-option>-->
              <!--              <el-option label="固定职工" value="beijing"></el-option>-->
              <!--              <el-option label="合同制职工" value="beijing"></el-option>-->
              <!--              <el-option label="企业人员" value="beijing"></el-option>-->
              <!--              <el-option label="金融企业人员" value="beijing"></el-option>-->
              <!--              <el-option label="参照管理机关工作人员" value="beijing"></el-option>-->
              <!--              <el-option label="参照管理事业单位工作人员" value="beijing"></el-option>-->
            </el-select>
          </el-form-item>
          <el-form-item label="干部状态" size="mini" label-width="100px">
            <el-select v-model="missingForm.state" class="inputW" placeholder="请选择" size="mini">
              <el-option label="在职" value="0"></el-option>
              <el-option label="离职" value="1"></el-option>
              <el-option label="退休" value="2"></el-option>
              <el-option label="死亡" value="3"></el-option>
              <el-option label="其他" value="4"></el-option>
              <el-option label="全部" value="5"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="材料名称" prop="receive" size="mini" :hide-required-asterisk="true" label-width="100px">
            <el-input v-model="missingForm.e01z111a" placeholder="请选择材料名称" class="inputW" size="mini">
              <!--              <el-option label="干部履历表" value="1"></el-option>-->
              <!--              <el-option label="自传" value="2"></el-option>-->
              <!--              <el-option label="2018年度考核表" value="3"></el-option>-->
              <!--              <el-option label="2019年度考核表" value="4"></el-option>-->
              <!--              <el-option label="研究生登记表" value="5"></el-option>-->
              <!--              <el-option label="入党志愿书" value="6"></el-option>-->
            </el-input>
          </el-form-item>
          <el-form-item label="查询条件" size="mini" label-width="100px">
            <el-radio-group class="inputW" v-model="missingForm.redio">
              <el-radio label="0">包含</el-radio>
              <el-radio label="1">不包含</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible3 = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveLock">保 存</el-button>
      </span>
    </el-dialog>
    <!-- 部门调整 -->
    <el-dialog title="档案部门调整" :append-to-body="true" :close-on-click-modal="false" :visible.sync="dialogVisible6"
               class=" dialog-style tree-style" width="25%">
      <div style="height:300px" class="dialog-tree">
<!--        <span style="margin-left: 20px;">部门名称:</span>-->
        <el-input placeholder="请输入搜索内容" size="mini" v-model="filterText3" style="margin-left: 5%;margin-right:5%;width: 90%"/>
        <!--        <div class="tree">-->
        <el-scrollbar style="height: 250px;">
          <el-tree
            style="overflow: auto"
            ref="tree3"
            :data="treeData"
            :props="TreeDefaultProps"
            :expand-on-click-node="false"
            icon-class="el-icon-folder-opened"
            :highlight-current="true"
            :filter-node-method="filterNode3"
            @node-click="handleNodeClick3"
            default-expand-all/>
        </el-scrollbar>
        <!--        </div>-->
        <!--        <el-tree class="dep-tree" ref="tree3" :highlight-current="true" :data="data3" :props="defaultProps"-->
        <!--           icon-class="el-icon-folder-opened" default-expand-all  :filter-node-method="filterNode"/>-->
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible6 = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveAdjust">保 存</el-button>

      </span>
    </el-dialog>
    <!-- 状态调整 -->
    <el-dialog title="档案状态调整"
      :close-on-click-modal="false"
      :append-to-body="true"
      :visible.sync="dialogVisible5"
      class=" dialog-style"
               width="30%">
      <div class="dialog-state">
        <span class="state">干部状态</span>
        <el-form class="state-form">
          <el-form-item size="mini">
            <el-radio-group v-model="formState.state">
              <el-radio style="margin-bottom:10px" label="0">在职状态</el-radio>
              <el-radio style="margin-bottom:10px" label="1">离职状态</el-radio>
              <el-radio style="margin-bottom:10px" label="2">退休状态</el-radio>
              <el-radio style="margin-bottom:10px" label="3">死亡状态</el-radio>
              <el-radio style="margin-bottom:10px" label="4">其他状态</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible5 = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveState">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import {getDeptTree} from "@/api/system/dept";
  import {queryDictionaryByType,queryDictionaryByTypes,queryPingYin} from "@/api/usual/receive";
  import {
    getA01List,
    updateA01,
    deptList,
    removeRecord,
    adjustDept,
    adjustState,
    getHighSearch,
    saveArchiveLock
  } from "@/api/usually/collect"
  import {MessageBox, Notification} from 'element-ui'
  import shrinkage from '@/components/shrinkage/index'  // 打印  // 路径
  import {downLoad,requestExcel,requestDownLoad} from '@/downLoad/filedownload'

  export default {
    components: {shrinkage},
    name: '档案管理',
    watch: {
      filterText2(val) {
        this.$refs.tree2.filter(val);
      },
      filterText3(val) {
        this.$refs.tree3.filter(val);
      },
      treeFilter(val) {
        this.$refs.tree4.filter(val);
      },
      tableData1: {
          handler(newName, oldName) {
            this.$nextTick(() => {
              this.$refs.tableRefs1.doLayout();
            })
          },
          deep: true,
          // immediate: true
        },
    },

    data() {
      return {
        scrollTop:0,
        tableDom:'',
        formRules:{
          a0101:[
            { required: true, message: '请输入干部姓名', trigger: 'blur' },
            { min: 0, max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ],
          a0102:[
            { required: true, message: '请输入干部简拼', trigger: 'blur' },
            { min: 0, max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ],
          a0107:[
            { required: true, message: '请选择出生日期', trigger: 'blur' },
          ],
          a0144:[
            { required: true, message: '请选择入党日期', trigger: 'blur' },
          ],
          a0134:[
            { required: true, message: '请选择参加工作日期', trigger: 'blur' },
          ],
          education:[
            { required: true, message: '请选择学历', trigger: 'blur' },
          ],
          a0102:[
            { min: 0, max: 20, message: '输入内容不得超过20个字符', trigger: 'blur' }
          ],
          a0114b:[
            { min: 0, max: 6, message: '输入内容不得超过6个字符', trigger: 'blur' }
          ],
          a0128:[
            { min: 0, max: 60, message: '输入内容不得超过60个字符', trigger: 'blur' }
          ],
          a0137:[
            { type:'number', min: 0, max: 99, message: '输入内容在0-99范围之间,', trigger: 'blur' }
          ],
          a0181b:[
            { min: 0, max: 15, message: '输入内容不得超过15个字符', trigger: 'blur' }
          ],
          a0161:[
            { min: 0, max: 6, message: '输入内容不得超过6个字符', trigger: 'blur' }
          ],
          a0184:[
            { min: 0, max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ],
          a0111b:[
            { min: 0, max: 6, message: '输入内容不得超过6个字符', trigger: 'blur' }
          ],

          a0115a:[
            { min: 0, max: 20, message: '输入内容不得超过20个字符', trigger: 'blur' }
          ],
          a0115b:[
            { min: 0, max: 6, message: '输入内容不得超过6个字符', trigger: 'blur' }
          ],

          a0181a:[
            { min: 0, max: 6, message: '输入内容不得超过6个字符', trigger: 'blur' }
          ],

          a0114a:[
            { min: 0, max: 20, message: '输入内容不得超过20个字符', trigger: 'blur' }
          ],
          a0114a:[
            { min: 0, max: 20, message: '输入内容不得超过20个字符', trigger: 'blur' }
          ],
          a0187b:[
            { min: 0, max: 200, message: '输入内容不得超过200个字符', trigger: 'blur' }
          ],
          a0187a:[
            { min: 0, max: 200, message: '输入内容不得超过200个字符', trigger: 'blur' }
          ],

          a0157a:[
            { min: 0, max: 60, message: '输入内容不得超过60个字符', trigger: 'blur' }
          ],

          a0157b:[
            { min: 0, max: 15, message: '输入内容不得超过15个字符', trigger: 'blur' }
          ],

          a0174:[
            { min: 0, max: 5, message: '输入内容不得超过5个字符', trigger: 'blur' }
          ]


        },
        treeShow: true,
        treeHeight: 'height:500px',
        loading: true,
        data4: [
          {
            fullName: '海关',
            dictCode: '0'
          }, {
            fullName: '缉私',
            dictCode: '1'
          }, {
            fullName: '事业',
            dictCode: '2'
          }, {
            fullName: '企业',
            dictCode: '3'
          }, {
            fullName: '其他',
            dictCode: '4'
          }
        ],
        //部门调整
        adjustDept: {
          deptId: '',
          id: []
        },
        //复选框
        checkbox: [],
        checkboxDatas: [],
        //高级检索数据
        highSearch: {
          key1: '',
          key2: '',
          key3: ''
        },
        //左侧树结构收缩
        picClassname: 'shrinkageLeft',
        rightStyle: {
          width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)'
        },
        rightStyle1: {
          width: '99%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)'
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
          width: '84%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)'
        },
        leftTree1: true,
        // showSearchBox: true,//搜索框显示
        dialogVisible: false, //  对话框显示      高级检索
        dialogVisible1: false, //基本信息
        dialogVisible2: false, //排序
        dialogVisible3: false, //查缺
        dialogVisible4: false, //职务变动表
        dialogVisible5: false, //状态调整
        dialogVisible6: false, //部门调整
        innerVisible: false,
        checkList: [],           //勾选框选中数组
        tableHeight: '73%',
        searchRow: 'searchRow',
        searchRowHeight: '',
        //基本信息表单
        form: {
          a3811: '',
          a0101: '',
          data4: '',
          dept_id: '',
          a0107: '',
          a0144: '',
          a0134: '',
          aeducation: '',
          state: '',
          // a0101:'',//编制类别无
          a0151: '',
          a0102: '',
          a0104: '',
          a0111a: '',
          a0114b: '',
          a0117: '',
          a0128: '',
          a0111a: '',
          a0131: '',
          a0137: '',
          a0141: '',
          a0154: '',
          a0177: '',
          a0181b: '',
          a0183: '',
          a0111b: '',
          a0115a: '',
          a0115b: '',
          a0181b: '',
          a0187b: '',
          a0184: '',
          a0114a: '',
          a0187a: '',
          a0157a: '',
          a0157b: '',
          a016: '',
          a0164: '',
          a0167: '',
          a0171: '',
          a0174: '',
          a0191: ''
        },
        // 搜索表单数据 档案查缺-高级检索
        formSearch: {
          pageSize: 20,
          deptName: '',
          pageNum: 1,
          a0101: '',
          state: '0',
          deptId: '',
          data4: '',
          total: 0,
          number: '',
          a0151: '',
          e01z111a: '',
          redio: '0',
        },
        treeSearch: {
          pageSize: 20,
          deptName: '',
          pageNum: 1,
          a0101: '',
          state: '0',
          deptId: '',
          data4: '',
          total: 0,
          number: '',
          a0151: '',
          e01z111a: '',
          redio: '0',
        },
        // 档案查缺
        missingForm:{
          pageSize: 20,
          deptName: '',
          pageNum: 1,
          a0101: '',
          state: '0',
          deptId: '',
          data4: '',
          total: 0,
          number: '',
          a0151: '',
          e01z111a: '',
          redio: '0',
        },
        //排序
        tableDataSort: [{}],
        //档案查缺表单
        // formlack: {
        //   a0101: '',
        //   deptId: '',
        //   number:'',
        //   a0151: '',
        //   state: '',
        //   e01z111a: '',
        //   redio: '0',
        // },
        //状态调整表单
        formState: {
          state: 'true'
        },
        // 表单验证
        rules: {
          // receive: {
          //   required: true,
          //   message: '请输入内容',
          //   trigger: 'blur'
          // },
        },
        //部门树结构数据
        filterText2: '',
        filterText3: '',
        treeFilter: '',
        TreeDefaultProps: {
          children: "children",
          label: "deptName",
        },
        data2: [],
        data3: [],
        defaultProps: {
          children: "children",
          label: "label"
        },

        treeData: [],
        //表格表头勾选隐藏
        col: [
          {
            label: "档案编号",
            prop: "name",
            isCol: true
          },
          {
            label: "干部姓名",
            prop: "address222",
            isCol: true
          },
          {
            label: "部门",
            prop: "daty",
            isCol: true
          },
          {
            label: "出生日期",
            prop: "address1",
            isCol: true
          },
          {
            label: "入党日期",
            prop: "data",
            isCol: true
          },
          {
            label: "参加工作时间",
            prop: "date1",
            isCol: true
          },
          {
            label: "学历",
            prop: "data2",
            isCol: true
          },
          {
            label: "人员状态",
            prop: "address3",
            isCol: true
          },
          {
            label: "编制类别",
            prop: "num",
            isCol: true
          },
          {
            label: "个人身份",
            prop: "data4",
            isCol: true
          }
        ],
        // 部门人员表格数据
        tableData1: [],
        // 搜索框表格数据
        tableData: [],
        selectList: [], //表格选中的行数
        showdaliog: 'true',
        //职务变动维护-姓名
        userName: '',
        //分页
        currentPage1: 5,
        currentPage2: 5,
        currentPage3: 5,
        currentPage4: 4,


        //字典表
        a0151s: [],
        a0104s1: [],
        states: [],
        education: [],
        dutys: [],

        a0111bs: [],
        a0114bs: [],
        a0115bs: [],
        a0117s: [],
        a0127s: [],
        a0131s: [],
        a0141s: [],
        a0157bs: [],
        a0161s: [],
        a0164s: [],
        a0167s: [],
        a0171s: [],
        a0174s: [],
        a0177s: [],
        a0181as: [],
        a0183s: [],
        a0191s: [],
        flags: [],
        exportDatas: []

      };
    },
    activated() {
      let _this = this;
      setTimeout(function(){
        _this.tableDom.scrollTop = _this.scrollTop
        _this.tableDom.scrollTo(0,_this.scrollTop);
      },10);

    },
    beforeRouteLeave(to, form, next){
      this.tableDom = this.$refs.tableRefs1.bodyWrapper;
      let tableDom = this.tableDom;
      let scrollTop = tableDom.scrollTop;
      this.scrollTop = scrollTop;
      next();
    },
    mounted() {
      this.getComboxDatas()
      this.$nextTick(() => {
        this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + "px"
      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (84 + 100 + this.searchRowHeight)) + "px"
        })();
      };
      this.leftDepTree()
    },
    created() {
      this.initData();
      // this.searchToTableData(this.formSearch);
      this.getGridList();
      this.leftDepTree();
    },
    methods: {
      //自定义序号
      customIndex(index){
        return (this.formSearch.pageNum-1)*(this.formSearch.pageSize)+(index+1);
      },
      // 基本信息 失去光标 转拼音
      loseBlur(val){
        if (this.form.a0101 != null && this.form.a0101 != '') {
          queryPingYin({ a0101: this.form.a0101 }).then(response => {
            this.form.a0102 = response.data
          })
        }
      },
      // 时间转换格式
      conversionTime(row, column, cellValue, index){
        if( cellValue === '' || cellValue === null ){
          return cellValue
        }
        let years = cellValue.substring(0,4);
        let month = cellValue.substring(4,6);
        let day = cellValue.substring(6,8);
        return  years+'-'+month+'-'+day
      },

      //自定义序号
      customIndex(row, column, cellValue, index){
        return (this.formSearch.pageNum-1)*(this.formSearch.pageSize)+(index+1);
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
      //搜索区回车事件
      onEnterSearch() {
        this.depement();
      },
      setKey4(value) {
        let fieldName = this.highSearch.key1;
        let datas = [];
        if (fieldName == 'state') {
          datas = this.states;
        }
        if (fieldName == 'education') {
          datas = this.education;
        }
        if (fieldName == 'a0151') {
          datas = this.a0151s;
        }
        if (fieldName == 'data4') {
          datas = this.data4;
        }
        let _this = this;
        datas.map(item => {
          if (value == item.dictCode) {
            _this.highSearch.key4 = item.fullName;
          }
        })

      },
      resetHigh() {
        this.highSearch.key3 = '';
        this.highSearch.key4 = '';
      },
      initData() {
        // ZB01
        queryDictionaryByTypes({types: "ZB09:0-GB22614-GB22611-FDA01State-ZB64-GB3304-GB22613-GB22612-GB4762-ZB87-ZB03-ZB04-ZB07-ZB76-AMS07-AMS08-ZB01"})
          .then(response => {
            // this.sysDictionaryMap = response.data
            this.a0104s1 = response.data.GB22611;
            this.states = response.data.FDA01State;
            //向states 数组中 手动加入 已转出状态
            this.states.push({
              arry: null,
              beginTime: null,
              codeAttr: "011",
              createBy: null,
              dictCode: "7",
              dictType: "FDA01State",
              endTime: null,
              eparentCode: "-",
              fullName: "已转出",
              isAsc: null,
              jsonStr: null,
              nickerName: "已转出",
              orderByColumn: null,
              orderCondition: "",
              otherParam: null,
              pageNum: null,
              pageSize: null,
              params: null,
              pinyin: "zc",
              remark: null,
              searchValue: null,
              updateBy: null
            })

            this.education = response.data.ZB64;
            this.a0151s = response.data.GB22614;
            this.dutys = response.data.ZB09;
            // this.a0111bs = response.data.ZB01;
            // this.a0114bs = response.data.ZB01;
            // this.a0115bs = response.data.ZB01;
            this.a0161s = response.data.ZB01;
            // this.a0181as = response.data.ZB01;
            this.a0157bs = response.data.ZB02
            this.a0117s = response.data.GB3304;
            this.a0127s = response.data.GB22613;
            this.a0131s = response.data.GB22612;
            this.a0141s = response.data.GB4762;
            this.a0164s = response.data.ZB87;
            this.a0167s = response.data.ZB03;
            this.a0171s = response.data.ZB04;

            this.a0177s = response.data.ZB07;
            this.a0183s = response.data.ZB76;
            this.a0191s = response.data.AMS07;
            this.flags = response.data.AMS08;
            //this.a0171s = response.data.ZB04;
          })
        // 当前页面所需字典表数据 性别
        // queryDictionaryByType({ dictType: 'GB22611' })
        //   .then(response => {
        //     this.a0104s1 = response.data
        //   })
        // // 获取干部状态
        // queryDictionaryByType({ dictType: 'FDA01State' })
        //   .then(response => {
        //     this.states = response.data;
        //   })
        //
        // queryDictionaryByType({ dictType: 'ZB64' })
        //   .then(response => {
        //     this.education = response.data;
        //   })
        // queryDictionaryByType({ dictType: 'ZB64' })
        //   .then(response => {
        //     this.education = response.data;
        //   })
      },

      tableFormatter(row, column, cellValue, index) {
        let datas = [];
        if (column.property == 'state') {
          datas = this.states;
        }
        if (column.property == 'education') {
          datas = this.education;
        }
        if (column.property == 'a0151') {
          datas = this.a0151s;
        }
        if (column.property == 'a0104') {
          datas = this.a0104s1;
        }
        if (column.property == 'duty') {
          datas = this.dutys;
        }
        if (column.property == 'data4') {
          datas = this.data4;
        }
        let data = cellValue;
        datas.map(item => {
          if (cellValue == item.dictCode) {
            data = item.fullName;
          }
        })
        return data;
      },
      //高级检索条件删除
      deleteHigh(index, row) {
        let _this = this;
        if (row.fieldName == 'a0101') {
          _this.formSearch.a0101 = '';
        }
        if (row.fieldName == 'dept_id') {
          _this.formSearch.deptId = '';
          _this.formSearch.deptName = '';
        }
        if (row.fieldName == 'data4') {
          _this.formSearch.data4 = '';
        }
        if (row.fieldName == 'state') {
          _this.formSearch.state = '';
        }
        if (row.fieldName == 'a0151') {
          _this.formSearch.a0151 = '';
        }
        this.tableData.splice(index, 1);
      },
      formaterType1(row, col) {
        if (row[col.property] != null && row[col.property] != '') {
          if (row[col.property] === "a0101") {
            return "干部姓名"
          } else if (row[col.property] === "number") {
            return "档案编号"
          } else if (row[col.property] === "dept_id") {
            return "部门"
          } else if (row[col.property] === "a0147") {
            return "出生日期"
          } else if (row[col.property] === "a0144") {
            return "入党日期"
          } else if (row[col.property] === "a0134") {
            return "参加工作时间"
          } else if (row[col.property] === "education") {
            return "学历"
          } else if (row[col.property] === "state") {
            return "干部状态"
          } else if (row[col.property] === "data4") {
            return "编制类别"
          } else if (row[col.property] === "duty") {
            return "职务"
          } else if (row[col.property] === "a0151") {
            return "个人身份"
          }
        }
      },
      formaterType2(row, col) {
        if (row[col.property] != null && row[col.property] != '') {
          if (row[col.property] === "con") {
            return "包含"
          }
          if (row[col.property] === "nq") {
            return "不等于"
          }
          if (row[col.property] === "gt") {
            return "大于"
          }
          if (row[col.property] === "lt") {
            return "小于"
          }
          if (row[col.property] === "le") {
            return "小于等于"
          }
          if (row[col.property] === "ge") {
            return "大于等于"
          }
          if (row[col.property] === "eq") {
            return "等于"
          }
        }
      },
      formaterType3(row, column, cellValue, index) {
        // return "";
        return row.type == 1 ? row.value : row.key;
      },

      //档案查缺保存按钮
      saveLock() {
        this.missingForm.pageNum = 1;
        this.searchToTableData(this.missingForm)
        this.getGridList();
        this.dialogVisible3 = false;
      },

      getComboxDatas() {
        // 获取个人身份
        queryDictionaryByType({dictType: 'GB22614'})
          .then(response => {
            this.a0151s = response.data
          })

      },
      //高级检索
      SavehighSearch() {
        let search = JSON.parse(JSON.stringify(this.formSearch))
        this.formSearch = {
          pageSize: 20,
          deptName: '',
          pageNum: 1,
          a0101: '',
          state: '0',
          deptId: '',
          data4: '',
          total: 0,
          number: '',
          a0151: '',
          e01z111a: '',
          redio: '0',
        },
        this.searchToTableData(search)
        this.getGridList();
        this.dialogVisible = false;
      },

      //将搜索区内容转换为高级检索内容
      searchToTableData(data) {
        let _this = this;
        let add = function (src, fieldName, key, value, where, type,defaultWhere) {
          if (value) {
            if(where == ''){
              where = defaultWhere;
            }
            _this.tableData.map(item=>{
              if(item.fieldName == fieldName){
                //取出搜索条件
                where = item.where;
              }
            })
            //将干部拼音 干部姓名 搜索方式为包含放入高级检索区中
            src.push({
              fieldName: fieldName,
              where: where,
              value: value,
              key: key,
              type: type,
            })
          }

        }
        //将搜索条件添加到高级检索区域 除却包含，材料名称之外所有条件全部走高级检索
        let tmpdata = JSON.parse(JSON.stringify(this.tableData));
        //移除高级检索表格中的检索表单
        tmpdata = tmpdata.filter(function (item) {
          return !(item.fieldName == 'dept_id' || item.fieldName == 'data4' || item.fieldName == 'a0101' || item.fieldName == 'a0151' || item.fieldName == 'state' || item.fieldName == 'a0101');
        });

        add(tmpdata, 'a0101', "", data.a0101, '', '1','con');

        add(tmpdata, 'dept_id', data.deptName, data.deptId, '', '2','eq');
        if (data.state != '') {
          let key = "全部";
          this.states.map(item => {
            if (item.dictCode == data.state) {
              key = item.fullName;
            }
          })
          add(tmpdata, 'state', key, data.state, '', '2','eq');
        }
        if (data.a0151 != '') {
          let key = "";
          this.a0151s.map(item => {
            if (item.dictCode == data.a0151) {
              key = item.fullName;
            }
          })
          add(tmpdata, 'a0151', key, data.a0151, '', '2','eq');
        }


        if (data.data4 != '') {
          let key = "";
          this.data4.map(item => {
            if (item.dictCode == data.data4) {
              key = item.fullName;
            }
          })
          add(tmpdata, 'data4', key, data.data4, '', '2','eq');
        }
        this.tableData = tmpdata;
      },

      //根据高级检索区内容重置搜索区内容
      resetSearchByTableData() {
        let _this = this;
        //先清空表单区域内容
        // _this.formSearch.a0101 = '';
        // _this.formSearch.a0151 = '';
        // _this.formSearch.state = '';
        // _this.formSearch.data4 = '';
        // _this.formSearch.deptName = '';
        // _this.formSearch.deptId = '';
        //先替换表单搜索区的内容
        this.tableData.map(item => {
          if (item.fieldName == 'a0101') {
            _this.formSearch.a0101 = item.value;
          }
          if (item.fieldName == 'dept_id') {
            _this.formSearch.deptId = item.value;
            _this.formSearch.deptName = item.key;
          }
          if (item.fieldName == 'data4') {
            _this.formSearch.data4 = item.value;
          }
          if (item.fieldName == 'state') {
            _this.formSearch.state = item.value;
          }
          if (item.fieldName == 'a0151') {
            _this.formSearch.a0151 = item.value;
          }
        })
      },

      getGridList() {
        this.loading = true;
        // this.searchToTableData();
        this.resetSearchByTableData();
        //过滤干部状态跟编制类别为全部的
        let info = JSON.parse(JSON.stringify(this.tableData)).filter(x => {
          if ((x.fieldName == 'state' && x.value == '5') || (x.fieldName == 'data4' && x.value == '5')) {
            return false;
          }
          return true;
        })
        let data = {
          info: info,
          e01z111a: this.formSearch.e01z111a,
          redio: this.formSearch.redio,
          pageNum: this.formSearch.pageNum,
          pageSize: this.formSearch.pageSize
        }
        this.loading = true;
        getA01List({data: JSON.stringify(data)}).then(response => {
          this.loading = false;
          if (response.rows != null) {
            this.tableData1 = response.rows;
            this.$nextTick(() => {
              this.$refs.tableRefs1.doLayout();
            })
          } else {
            this.tableData1 = []
          }
          this.formSearch.total = response.total;
          this.loading = false;
        })
      },
      //高级检索添加方法
      addCondition() {
        let highSearch = this.highSearch;
        if (highSearch.key1 == '' || highSearch.key2 == '' || highSearch.key3 == '') {
          this.$message.warning("请将查询内容填写完整");
          return;
        }
        //替换搜索区内容
        if (highSearch.key1 == 'a0101') {
          this.formSearch.a0101 = highSearch.key3;
        }
        if (highSearch.key1 == 'dept_id') {
          this.formSearch.deptId = highSearch.key3;
          this.formSearch.deptName = highSearch.key4;
        }
        if (highSearch.key1 == 'data4') {
          this.formSearch.data4 = highSearch.key3;
        }
        if (highSearch.key1 == 'state') {
          this.formSearch.state = highSearch.key3;
        }
        if (highSearch.key1 == 'a0151') {
          this.formSearch.a0151 = highSearch.key3;
        }
        let b = true;
        //替换高级搜索表单中存在的值
        this.tableData.map(item => {
          if (item.fieldName == highSearch.key1) {
            //替换
            item.where = highSearch.key2;
            item.value = highSearch.key3;
            item.key = highSearch.key4;
            b = false
          }
        })
        if (!b) {
          return;
        }
        const param = {}
        param.fieldName = highSearch.key1
        if (highSearch.key1 != 'state' && highSearch.key1 != 'dept_id' && highSearch.key1 != 'data4' && highSearch.key1 != 'education' && highSearch.key1 != 'a0151') {
          param.type = '1';
        } else {
          param.type = '2';
          param.key = highSearch.key4
        }
        param.where = highSearch.key2
        param.value = highSearch.key3

        this.tableData.push(param)
      },

      sortCanel() {//干部档案排序页面取消
        this.dialogVisible2 = false;
      },
      sortSave() {
        this.dialogVisible2 = false;
      },
      click() {
        // this.leftTree1 = !this.leftTree1
        // if (this.leftTree1) {
        //   this.rightStyleName = this.rightStyle
        //   this.leftStyleName = this.leftStyle
        //   this.picClassname = 'shrinkageLeft'
        //   this.treeShow =! this.treeShow
        // } else {
        //   this.rightStyleName = this.rightStyle1
        //   this.leftStyleName = this.leftStyle1
        //   this.picClassname = 'shrinkageRight'
        //   this.treeShow =! this.treeShow
        // }
      },
      // 左侧结构
      treeNode(value, data) {
        if (!value) return true;
        return data.deptName.indexOf(value) !== -1;
      },
      treeNodeClick() {
      },
      //部门tree
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },

      //部门调整的部门树
      filterNode3(value, data) {
        if (!value) return true;
        return data.deptName.indexOf(value) !== -1;
      },
      handleNodeClick3(data) {
        // this.filterText3 = data.deptName
        this.adjustDept.deptId = data.id;
      },
      //部门调整的部门树的点击方法
      leftDepTree() {
        getDeptTree({deptType: "1"}).then(response => {
          if (response.code === 200) {
            this.treeData = response.data
          }
        })
      },

      handleDeptSelectClick: function (node) {

        this.form.deptId = node.id
        this.form.deptName = node.deptName
        this.$refs.selectReport.blur()

      },

      handleDeptSelectClick3: function (node) {

        this.highSearch.key3 = node.id
        this.highSearch.key4 = node.deptName
        this.$refs.selectReport3.blur()

      },

      handleDeptSelectClick2: function (node) {
        this.missingForm.deptId = node.id
        this.missingForm.deptName = node.deptName
        this.$refs.selectReport.blur()

      },
      //左侧树节点 点击事件
      handleNodeClick(data) {
        this.treeSearch.deptId = data.id
        this.treeSearch.pageNum = 1
        this.treeSearch.total = 0
        this.formSearch.pageNum = 1
        this.treeSearch.deptName = data.deptName;
        this.searchToTableData(this.treeSearch);
        this.getGridList();
        // this.formSearch.state="0"
        // this.formSearch.a0101=""
        // getA01List(this.formSearch).then(response => {
        //   this.tableData1 = response.rows
        //   this.formSearch.total=response.total
        // })
      },
      // 搜索框
      // 搜索按钮
      depement() {
        this.tableData = []
        this.formSearch.pageNum = 1;
        this.treeSearch.state = this.formSearch.state
        this.treeSearch.data4 = this.formSearch.data4
        this.treeSearch.a0101 = this.formSearch.a0101
        this.searchToTableData(this.formSearch);
        this.getGridList();
      },
      // 重置按钮
      clear() {
        this.formSearch.state = "5"
        // this.formSearch.data4 = "5"
        this.formSearch.a0101 = ""
        this.formSearch.deptId = ""
        this.formSearch.pageNum = 1
        this.getGridList()
      },
      //高级检索
      searchDialog() {
        //this.tableData=[]
        this.dialogVisible = true;
      },

      // 公用表格表头样式
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      // 判断表格是否勾选一行方法
      selectChecked(data) {
        this.checkboxDatas = data
        this.checkbox = data.map(item => item.id)
        this.showdaliog = this.selectList.length === 0
      },
      StepsListRowClick(row) {    //档案管理-点击行事件-方法
        this.$refs.tableRefs1.clearSelection()
        if (this.checkList.length === 0) {
          this.$refs.tableRefs1.toggleRowSelection(row)
          return;
        }
        this.$refs.tableRefs1.toggleRowSelection(row)
      },

      // 功能按钮
      //新增
      addRecord() {
        this.$router.push({path: "/usually/receive"});
      },
      //删除
      onDelete() {
        if (this.checkboxDatas == null || this.checkboxDatas.length == 0) {
          this.$message({
            type: 'warning',
            message: '请勾选需要删除的数据'
          })
          return
        }
        if (this.checkboxDatas.length > 1) {
          this.$message({
            type: 'warning',
            message: '请勾选一条需要删除的数据'
          })
          return
        }
        MessageBox.confirm(
          '是否确定删除干部档案及相关信息。',
          '系统提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          removeRecord(this.checkboxDatas[0])
            .then(response => {
              if (response.code == 200) {
                this.$message({
                  message: '删除成功',
                  type: 'success'
                });
                this.getGridList();
              } else {
                this.$message({
                  message: '删除失败',
                  type: 'error'
                });
              }
            })
        })


      },
      //打印
      onCopy() {
      },
      //排序
      sort() {
        this.dialogVisible2 = true;
      },
      //导出
      onReturn() {
        let callback = function(response){
          if(response.code == 200){
            return true;
          }else{
            return false;
          }
        }
        let exportDatas = this.checkboxDatas;
        if (exportDatas.length != 0) {
          let names = "[";
          for (var i = 0; i < exportDatas.length; i++) {
            names += exportDatas[i].a0101;
            if (i != exportDatas.length) {
              names += ",";
            }
          }
          names += "]";
          this.$confirm('是否导出选中的档案数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let params = {
              exportData: this.checkbox
            }

            // exportPersonInfo(params).then()

            requestExcel("/export/excel/export?status=1&exportData=" + JSON.stringify(this.checkbox),callback,this);
            // requestDownLoad()
            // downLoad("/export/excel/export?status=1&exportData=" + JSON.stringify(this.checkbox));
          });
        } else {
          this.$confirm('是否导出搜索后的档案数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            //过滤干部状态跟编制类别为全部的
            let info = JSON.parse(JSON.stringify(this.tableData)).filter(x => {
              if ((x.fieldName == 'state' && x.value == '5') || (x.fieldName == 'data4' && x.value == '5')) {
                return false;
              }
              return true;
            })

            let data = {
              info: info,
              e01z111a: this.formSearch.e01z111a,
              redio: this.formSearch.redio,
              pageNum: 1,
              pageSize: 999999999
            }
            let s = JSON.stringify(data);
            s = encodeURI(s);
            requestExcel("/export/excel/export?status=2&exportData=" + s,callback,this);
          });

        }

      },
      //部门调整
      depementChange() {
        if (this.checkboxDatas == null || this.checkboxDatas.length == 0) {
          this.$message({
            type: 'warning',
            message: '请勾选需要部门调整的数据'
          })
          return
        }
        this.dialogVisible6 = true;
        this.data3 = JSON.parse(JSON.stringify(this.data2))
      },
      // 保存部门调整
      saveAdjust() {
        let param = {};
        param.ids = this.checkbox.toString();
        param.deptId = this.adjustDept.deptId
        if (this.adjustDept.deptId == "") {
          this.$message.warning('请选中需调整部门')
          return;
        }
        adjustDept(param)
          .then(response => {
            if (response.code == 200) {
              this.$message({
                message: '部门调整保存成功',
                type: 'success'
              });
              this.dialogVisible6 = false
              this.getGridList()
            } else {
              this.$message({
                message: '部门调整保存失败',
                type: 'error'
              });
              this.dialogVisible6 = false
              this.getGridList()
            }
          })
      },
      //状态调整
      stateChange() {
        if (this.checkboxDatas == null || this.checkboxDatas.length == 0) {
          this.$message({
            type: 'warning',
            message: '请勾选需要状态调整的数据'
          })
          return
        }
        this.formState.state = this.checkboxDatas[0].state;
        this.dialogVisible5 = true;
        // if (this.showdaliog) {
        //   this.$message.warning('请勾选需要调整状态的干部档案')
        // } else {
        //   this.dialogVisible5 = true;
        // }
      },
      // 保存状态调整
      saveState() {
        let param = {};
        param.ids = this.checkbox.toString();
        param.state = this.formState.state
        adjustState(param)
          .then(response => {
            if (response.code == 200) {
              this.$message({
                message: '状态调整保存成功',
                type: 'success'
              });
              this.dialogVisible5 = false
              this.getGridList()
            } else {
              this.$message({
                message: '状态调整保存失败',
                type: 'error'
              });
              this.dialogVisible5 = false
              this.getGridList()
            }
          })
      },
      //档案查缺
      lack() {
        this.dialogVisible3 = true;
      },
      // 保存档案差缺
      saveLack() {
        this.dialogVisible3 = false;
        this.$message.success('档案差缺保存成功')
      },
      //职务变动维护
      dutyChange() {
        if (this.showdaliog) {
          this.$message.warning('请勾选需要职务变动维护的干部档案')
        } else {
          this.$router.push({
            name: "职务变动维护",
            query: {
              userName: this.userName
            }
          });
        }
      },
      strToDate(val){
        if(val == null || val == '' || val.toString().length<8){
          return "";
        }
        return val.toString().substring(0,4) + "-" + val.toString().substring(4,6) + "-" + val.toString().substring(6,8)
      },
      //操作-基本信息对话框
      message(val) {
        let form = JSON.parse(JSON.stringify(val));
        val.a0137 = Number(val.a0137 )
        form.a0107 = this.strToDate(form.a0107);
        form.a0134 = this.strToDate(form.a0134);
        form.a0144 = this.strToDate(form.a0144);
        form.a0154 = this.strToDate(form.a0154);
        this.form = form;
        this.dialogVisible1 = true;
      },
      // 保存基本信息
      saveInfo() {
        this.form.a0137 = Number(this.form.a0137)
        this.$refs.form.validate((valid)=>{
          if(!valid){
            return;
          }
          let form = JSON.parse(JSON.stringify(this.form));
          form.a0107 = form.a0107.toString().replace("-","").replace("-","");
          form.a0134 = form.a0134.toString().replace("-","").replace("-","");
          form.a0144 = form.a0144.toString().replace("-","").replace("-","");
          form.a0154 = form.a0154.toString().replace("-","").replace("-","");
          updateA01(form).then(response => {
            if (response.code == 200) {
              this.dialogVisible1 = false
              this.getGridList()
              this.$message.success('基本信息保存成功')
            } else {
              this.$message.error('保存失败');
            }
          })
        })


      },
      //操作-档案材料
      recordMaterial(rowData) {
        this.$router.push({
          name: "材料管理",
          params: {
            userName: rowData.a0101,
            id: rowData.id
          }
        })
      },
      //分页
      handleSizeChange(val) {
        this.formSearch.pageSize = val,
          this.formSearch.pageNum = this.formSearch.pageNum,
          this.getGridList()
      },
      handleCurrentChange(val) {
        this.formSearch.pageSize = this.formSearch.pageSize,
          this.formSearch.pageNum = val,
          this.getGridList()
      }
    }
  };

</script>
<style lang="scss" scoped>

  .app-daily {
    overflow-y: auto;
    overflow-x: hidden;
    height: calc(100vh - 86px);

    .tree {
      width: 100%;
      height: calc(100vh - 135px);
      // overflow: auto;
      padding: 20px;
      /*border-right: 1px solid #e2e3e6;*/
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
    .page {
      float: right;
    }
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

  //高级搜索
  .dialog-high {
    .form-search {
      display: flex;
      justify-content: center;
    }
    .high-table {
      /deep/ .el-table__fixed-right-patch {
        background-color: rgba(204, 204, 204, 0.18);
      }
    }

    /deep/ .el-select > .el-input {
      width: 163px !important;
    }
  }

  //排序
  .dialog-sort {
    .btn-sort {
      margin-bottom: 10px;
      width: 100%;
      // .span-tit{
      //   position: absolute;
      //   right: 140px;
      //   top: 70px;
      // }
    }
    .table-sort {
      height: 300px;
      overflow: auto;
    }
    /deep/ .el-table::before {
      height: 0px !important;
    }
  }

  // 基本信息弹框
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

  // 状态调整弹窗
  .dialog-state {
    position: relative;
    height: 160px;
    margin: 20px 10px 20px 80px;
    width: 55%;
    // width: 240px;
    border: 1px solid #CAD9E7;
    box-shadow: 0 0 3px 1px #EBF0F5;
    .state {
      position: absolute;
      top: -9px;
      left: 18px;
    }
    .state-form {
      margin-left: 30%;
      margin-top: 20px
    }
  }

  //部门调整弹框
  .dialog-tree {
    .dep-tree {
      height: 300px;
      overflow: auto;
    }
  }

  //档案查缺
  .dialog-lack {
    .div-lack {
      text-align: center;
    }
    /deep/ .el-select > .el-input {
      width: 163px !important;
    }
    .input {
      width: 250px;
    }
  }

  /deep/ .el-table__fixed-right-patch {
    background-color: #F8F8F9;
  }

  /deep/ .el-table .el-table__header-wrapper th {
    height: 31px;
  }

  /deep/ .link-type {
    color: #1890ff
  }

  .inputW {
    width: 163px;
  }

  /deep/ .el-select-dropdown__item {
    padding: 0 0;
  }

  /deep/ .el-tree>.el-tree-node{
    min-width: 100%;
    display: inline-block;
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
    right: 0;
    cursor: pointer;
    bottom: 0;
    margin: auto;
    top: 0;
    height: 200px;
    width: 8px;
    border-radius: 20px;
    position: absolute;
  }
  /deep/ .el-scrollbar .el-scrollbar__wrap {
    overflow-x: hidden;
  }
</style>

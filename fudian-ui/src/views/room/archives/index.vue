<template>
  <div class="app-archives">
    <el-row style="height:100%">
      <!--      <el-scrollbar wrap-class="scrollbar-wrapper">-->
      <!--        <div class="tree tree-style">-->
      <!--          <el-input placeholder="请输入内容" class="inp-select" />-->
      <!--          <el-tree style="overflow: auto" ref="tree2" :data="data2" :props="defaultProps"-->
      <!--                   icon-class="el-icon-folder-opened"-->
      <!--                   default-expand-all />-->
      <!--        </div>-->
      <!--      </el-scrollbar>-->
      <el-col :style="leftStyleName">
        <el-scrollbar style="height:100%" wrap-class="scrollbar-wrapper">
          <div v-if="leftTree1" style="height:100%" class="tree">

            <div style="height:100%" class="tree tree-style">
              <!--                <el-input placeholder="请输入内容" style="width: 100%" class="inp-select" />-->
              <el-tree @node-click="nodeClick"
                       style="overflow: auto"
                       ref="tree"
                       lazy
                       :expand-on-click-node="false"
                       :load="loadleftNode"
                       icon-class="el-icon-folder-opened"
                       :data="data"
                       :default-expanded-keys="['000']"
                       node-key="id"
                       highlight-current
                       :props="defaultProps"/>

            </div>

          </div>
        </el-scrollbar>
        <div class="shrinkageDiv" @click="divClick()">
          <div :class="picClassname"></div>
        </div>
      </el-col>
      <el-col :style="rightStyleName">
        <el-row :gutter="10" ref="searchRow" >
          <el-form
            v-if="showSearchBox"
            ref="queryForm"
            class="search-form"
            v-hasPermi="['room:archives:search']"
            :model="queryForm"
            :inline="true"
            label-width="68px"
            style="background:rgba(204, 204, 204, 0.18);margin-bottom: 10px;"
          >
            <el-form-item label="干部名称" prop="e01zc24a">
              <el-input v-model="a01QuertForm.a0101" @keyup.enter.native="search" size="mini" placeholder="请输入干部名称" clearable style="width: 150px"/>
            </el-form-item>

            <el-form-item label="干部状态" style="margin-left: 20px;" prop="state">
              <el-select v-model="a01QuertForm.state" placeholder="请选择" clearable size="mini" style="width: 150px">
                <el-option v-for="item in a01Status" :key="item.dictCode" :label="item.fullName" :value="item.dictCode">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="档案状态" style="margin-left: 20px;" prop="status">
              <el-select v-model="a01QuertForm.status" placeholder="请选择" clearable size="mini" style="width: 150px">
                <el-option key="1" label="正常" value="1"/>
                <el-option key="2" label="已借出" value="2"/>
                <!--                <el-option key="3" label="正查阅" value="3"/>-->
              </el-select>
            </el-form-item>
            <!--            <el-form-item label="所在位置" prop="e01zc24a">-->
            <!--              <el-input v-model="queryForm.location" placeholder="请输入所在位置" clearable size="small" style="width: 240px" />-->
            <!--            </el-form-item>-->
            <el-form-item style="margin-left: 20px">
              <el-button type="primary" icon="el-icon-search" size="mini"  @click="search">搜索</el-button>
<!--              <el-button icon="el-icon-refresh" size="mini" @click="reset">重置</el-button>-->
            </el-form-item>
          </el-form>
          <el-col :span="24" style="margin-bottom: 10px">
            <el-button type="primary" icon="el-icon-plus" v-hasPermi="['room:archives:add']" size="mini" @click="handleAdd">档案上架</el-button>
            <el-button type="primary" icon="el-icon-sort" v-hasPermi="['room:archives:edit']" size="mini" @click="handleUpData">更改档案位置</el-button>
            <el-button type="primary" icon="el-icon-delete" v-hasPermi="['room:archives:delete']" size="mini" @click="outbound">移除档案位置</el-button>
            <el-button type="primary" icon="el-icon-download" v-hasPermi="['room:archives:export']" @click="exportExcel()" size="mini">导出</el-button>
          </el-col>

          <!--          outbound(scope.row)-->
          <!--          <el-dropdown style="float: right; margin-right: 30px;">-->
          <!--            <el-button icon="el-icon-menu" size="mini" circle />-->
          <!--            <el-dropdown-menu style="padding: 10px">-->
          <!--              <el-checkbox-group v-for="(item, index) in col" v-model="col[index].isCol" size="small">-->
          <!--                <el-checkbox>{{ item.label }}</el-checkbox>-->
          <!--              </el-checkbox-group>-->
          <!--            </el-dropdown-menu>-->
          <!--          </el-dropdown>-->
          <!--          <el-col :span="1.5" style="float: right;"><el-button type="primary" icon="el-icon-search" size="mini" circle @click="function(){showSearchBox = !showSearchBox;tableHeight = showSearchBox?'73%':'85%'}" /></el-col>-->
        </el-row>
        <el-table :data="tableData"
                  size="mini"
                  :height="tableHeight"
                  border
                  row-key="id"
                  stripe
                  empty-text="无"
                  tooltip-effect="light"
                  ref="tableRefs"
                  :header-cell-style="headClass"
                  @current-change="handleCurrentChange"
                  @row-click="StepsListRowClick"
                  @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="40"/>
          <el-table-column
            label="序号"
            type="index"
            width="50">
          </el-table-column>
          <el-table-column show-overflow-tooltip v-for="(item, index) in col" :width="item.width"
                           v-if="col[index].isCol" align="center" :key="index" :prop="col[index].prop"
                           :formatter="col[index].formatter" :label="item.label"/>
          <el-table-column
            prop="state"
            label="干部状态"
            width="100"
            align="center"
            :formatter="stateFormat"
            filter-placement="bottom-end">

          </el-table-column>
          <el-table-column
            prop="status"
            label="档案状态"
            width="100"
            align="center"
            :formatter="statusFormat"
            filter-placement="bottom-end">


          </el-table-column>
          <el-table-column
            prop="location"
            label="所在库房位置"
            min-width="300"
            align="center"
            filter-placement="bottom-end">
          </el-table-column>
          <!--          <el-table-column-->
          <!--            prop="remark"-->
          <!--            label="备注"-->
          <!--            width="200"-->
          <!--            align="center"-->
          <!--            filter-placement="bottom-end">-->
          <!--          </el-table-column>-->


          <!--          <el-table-column  label="操作" width="150px">-->
          <!--            <template slot-scope="scope">-->
          <!--              <el-button type="text" size="small" @click="outbound(scope.row)">移除</el-button>-->
          <!--              <el-button type="text" size="small" @click="onHistory(scope.row)">历史轨迹</el-button>-->
          <!--            </template>-->
          <!--          </el-table-column>-->
        </el-table>
        <div class="block" style="float: left;margin-top: 8px;padding-left: 10px">
          {{leftButtomInfo}}
        </div>

        <div style="margin-top: 8px;float: right">
          <el-pagination
            :page-sizes="[20, 50, 100, 200]"
            :page-size="20"
            layout="total, sizes, prev, pager, next, jumper"
            :current-page="queryForm.pageNum"
            :total="total"
            @size-change="pageSize"
            @current-change="pageCurrent"
          />
        </div>
      </el-col>
      <el-dialog :close-on-click-modal="false" :title="roomPostionUpdate" width="30%" style="margin-top: 5%" class=" dialog-style dialog-basic"
                 :visible.sync="dialogFormVisible">
        <div style="overflow: hidden;height: 280px">
          <el-scrollbar wrap-class="scrollbar-wrapper" style="height:100%" class="scrollbar-border">
            <el-tree @node-click="tree2Click" ref="tree2"
                     lazy

                     :load="loadAllNode"
                     style="margin-top: 10px;margin-left: 20px"
                     :data="data2"
                     icon-class="el-icon-folder-opened"
                     node-key="id"
                     :highlight-current="true"
                     :props="defaultProps"/>
          </el-scrollbar>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogFormVisible = false">取消</el-button>
          <el-button size="mini" type="primary" @click="saveLocaltionUpdate">保存</el-button>
        </div>
      </el-dialog>


      <el-dialog :close-on-click-modal="false" title="放置实体档案" width="60%" style="margin-top: 5%" class="dialog-style tree-style"
                 :visible.sync="dialogFormVisible1">
        <div style="width: 100%;height: 280px;;margin: 0 auto;">
          <div
            style="float: left;width: 63%;height: 92%;padding-right:10px;border-right: rgba(31,45,61,0.14) 1px solid">
            <div style="height:10%; width:100%;margin-right:15px;border-bottom: rgba(31,45,61,0.14) 1px solid">
              第一步：选择未放置的实体档案
            </div>
            <el-form :data="formremove2" ref="ruleForm" :inline="true" style="" label-width="70px" class="form-search">
              <el-form-item style="margin-right: 10px" label="干部查询" prop="a0101">
                <el-input v-model="formremove2.a0101" placeholder="请输入内容" style="width:115px" size="mini"></el-input>
              </el-form-item>
              <el-form-item style="margin-right: 10px" label="档案状态">
                <el-select v-model="formremove2.state" placeholder="请选择" style="width:115px" size="mini">
                  <el-option v-for="item in a01Status" :value="item.dictCode" :label="item.fullName"></el-option>
                  <!--                        <el-option label="在职" value="shanghai"></el-option>-->
                  <!--                        <el-option label="离职" value="beijing"></el-option>-->
                  <!--                        <el-option label="退休" value="beijing"></el-option>-->
                  <!--                        <el-option label="死亡" value="beijing"></el-option>-->
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="getDeptA01DataByParams()" size="mini">查询</el-button>
              </el-form-item>
            </el-form>
            <!-- <el-scrollbar> -->
            <!-- <div class="table-remove"> -->
            <el-table

              ref="table1Refs"
              :data="a01Data.rows" size="mini"
              :header-cell-style="headClass"
              tooltip-effect="light"
              border
              height="70%"
              @row-click="StepsListRowClick2"
              @selection-change="a01NodeClick"
              row-key="id"
              :stripe="true">
              <el-table-column type="selection" width="40" :resizable="false"/>
              <el-table-column type="index" label="序号" show-overflow-tooltip :resizable="false"
                               align="center"></el-table-column>
              <el-table-column label="姓名" prop="a0101" width="70px" show-overflow-tooltip :resizable="false"
                               align="center">
              </el-table-column>
              <el-table-column label="性别" :formatter="sexFormatter" prop="a0104" width="60px" show-overflow-tooltip
                               :resizable="false"
                               align="center">
              </el-table-column>
              <el-table-column label="干部状态" :formatter="stateFormatter" prop="state" show-overflow-tooltip
                               :resizable="false"
                               align="center">
              </el-table-column>
              <el-table-column label="出生日期" prop="a0107" show-overflow-tooltip :resizable="false" width="90px"
                               align="center"></el-table-column>
              <el-table-column label="籍贯名称" prop="a0111a" show-overflow-tooltip width="80px" :resizable="false"
                               align="center"></el-table-column>
            </el-table>
            <el-pagination
              small
              :page-sizes="[20, 50, 100, 200]"
              :page-size="20"
              style="float: right;"
              :current-page="a01Data.pageNum"
              layout="total, sizes, prev, pager, next, jumper"
              :total="a01Data.total"
              @size-change="pageSize2"
              @current-change="pageCurrent2"
            ></el-pagination>

          </div>

          <div style="float: left;width: 35%;height: 90%;">
            <div style="height:10%;margin-left:15px;border-bottom: rgba(31,45,61,0.14) 1px solid">第二步：选择位置</div>
            <el-scrollbar style="height: 100%;">
              <el-tree
                style="height:90%;width:90%;margin-top: 20px;margin-left: 15px;"
                ref="tree3"
                :expand-on-click-node="false"
                @node-click="addA01TreeNodeClick"
                lazy
                :highlight-current="true"
                icon-class="el-icon-folder-opened"
                :default-expanded-keys="defaultExpandKeys"
                :load="loadNode"
                :data="data"
                node-key="id"
                :props="defaultProps"/>
            </el-scrollbar>
          </div>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogFormVisible1 = false">取消</el-button>
          <el-button type="primary" size="mini" @click="saveAddA01">保存</el-button>
        </div>

      </el-dialog>
      <vxe-modal :title="historyTitle" v-model="historyVisable" style="z-index: 2002" resize remember width="70%"
                 height="70%">
        <div class="block" style="width: 80%;text-align: center;margin-left: 10%">
          <el-timeline>
            <template v-for="item in historyDatas">
              <el-timeline-item :timestamp="item.hisDate" placement="top">
                <el-card>
                  <h4>{{item.type}}</h4>
                  <p>{{item.hisDesc}}</p>
                </el-card>
              </el-timeline-item>
            </template>

            <!--            <el-timeline-item timestamp="2018/4/3" placement="top">-->
            <!--              <el-card>-->
            <!--                <h4>更新 Github 模板</h4>-->
            <!--                <p>王小虎 提交于 2018/4/3 20:46</p>-->
            <!--              </el-card>-->
            <!--            </el-timeline-item>-->
            <!--            <el-timeline-item timestamp="2018/4/2" placement="top">-->
            <!--              <el-card>-->
            <!--                <h4>更新 Github 模板</h4>-->
            <!--                <p>王小虎 提交于 2018/4/2 20:46</p>-->
            <!--              </el-card>-->
            <!--            </el-timeline-item>-->
          </el-timeline>
        </div>
      </vxe-modal>


    </el-row>
  </div>
</template>

<script>
  import {Notification, MessageBox} from 'element-ui'
  import {
    queryA01,
    insertA01,
    downRoom,
    updateA01Position,
    selectA01ByDeptIdAndPosiIsNull,
    queryA01History,
    queryNodeNum
  } from '@/api/room/arch'
  import {queryRoomStruct} from '@/api/room/struct'
  import {getDeptTree} from '@/api/system/dept'
  import {queryRoomList} from "@/api/room/room";
  import {queryDictData} from "@/api/system/dict/data";
  import {downLoadPost} from "@/downLoad/filedownload"

  // import AddA01 from "./adda01"
  export default {
    // components: {AddA01},
    // components: {
    //   AddA01
    // },
    watch: {
      tableData: {
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(() => {
            _this.$refs.tableRefs.doLayout();

          })
        },
        deep: true,

      },
      a01Data: {
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(() => {
            _this.$refs.table1Refs.doLayout();

          })
        },
        deep: true,

      },
    },
    data() {
      return {
        tableHeight: '0',
        a01DataData: [],
        historyDatas: [],
        historyVisable: false,
        historyTitle: '历史轨迹',
        a01Sex: [],
        formremove: {
          state: '0',
          a0101: '',
        },
        nodesSummary: [],
        cadresText: '',
        personnelText: '',
        total: 0,
        a01Status: [],
        roomPostionUpdate: '',
        data2: [],
        picClassname: 'shrinkageLeft',
        rightStyle: {
          width: '85%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)', overflow: 'auto'
        },
        rightStyle1: {
          width: '99.5%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)', overflow: 'auto'
        },
        leftStyle: {
          width: '15%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        leftStyle1: {
          width: '0.5%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        leftStyleName: {
          width: '15%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
        },
        rightStyleName: {
          width: '85%', height: 'calc(100vh - 86px)', padding: ' 10px', height: 'calc(100vh - 86px)', overflow: 'auto'
        },
        leftTree1: true,
        deptData: [],
        a01Data: {
          rows: [],
          total: 0,
          pageNum :1,
          pageSize:20
        },
        checkA01s: [],
        addA01PosNode: '',
        /* 全选 */
        multipleSelection: [],
        isIndeterminate: true,
        dialogFormVisible: false,
        dialogFormVisible1: false,
        leftButtomInfo: this.$route.params.info,
        roomId: this.$route.params.roomId,
        selectTree2Node: '',
        formLabelWidth: '130px',
        showSearchBox: true,
        a01QuertForm: {
          state: '0',
          a0101: '',
          status: '',
        },
        queryForm: {
          state: '0',
          location: '',
          roomId: this.$route.params.roomId,
          parentId: '',
          pageNum: 1,
          pageSize: 20
        },
        data: [],
        defaultProps: {
          children: 'children',
          // hasChildren: 'hasChildren',
          label: 'node_name',
          isLeaf: 'hasChildren'
        },

        deptProps: {
          children: 'children',
          // hasChildren: 'hasChildren',
          label: 'deptName',
          isLeaf: 'hasChildren'
        },
        a01Props: {
          children: 'children',
          // hasChildren: 'hasChildren',
          label: 'a0101',
          isLeaf: 'hasChildren'
        },
        col: [
          {
            label: '干部名称', width: 100, prop: 'a0101', isCol: true, formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {
            label: '干部编号', width: 100, prop: 'code', isCol: false, formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          {label: '性别', width: 100, prop: 'a0104', isCol: true},
          {
            label: '出生日期', width: 100, prop: 'a0107', isCol: true, formatter: function (row, column, cellValue, index) {
              return cellValue.toString().substring(0, 4) + "-" + cellValue.toString().substring(4, 6) + "-" + cellValue.toString().substring(6, 8)
            }
          },
          {
            label: '所属部门',
            width: 100,
            prop: 'deptName',
            isCol: true,
            formatter: function (row, column, cellValue, index) {
              return cellValue
            }
          },
          // { label: '所在库房位置',width:300, prop: 'location', isCol: true ,formatter:function(row, column, cellValue, index){return cellValue}}

        ],
        tableData: [],
        searchText: '',
        deptNameShow: false,
        nodeId: '',
        defaultExpandKeys: [],
        a01DataPage: {
          pageSize: 20,
          pageNum: 1
        },
        selectTree2NodeParent: '',
        selectTree3NodeParent: '',
        leftNode: '',
        searchRowHeight: '',
        searchRow: 'searchRow',
        formremove2:{
          state:'0',
          a0101:''
        }
        // headClass: "background:rgba(204, 204, 204, 0.18)",
      }
    },

    created() {
      this.getNodeNum();
      queryDictData({dictType: 'FDA01State', eparentCode: ''}).then(response => {
        this.a01Status = response.data;
        this.a01Status.push({dictCode: '', fullName: '全部'})
      })
      queryDictData({dictType: 'GB22611', eparentCode: ''}).then(response => {
        this.a01Sex = response.data;
      })

      this.getList();
      // this.getDeptList();

    },
    mounted() {

      this.$nextTick(() => {

        let _this = this;
        setTimeout(() => {

          _this.searchRowHeight = _this.$refs[_this.searchRow].$el.offsetHeight
          _this.tableHeight = _this.tableHeight = (document.body.clientHeight - (84 + 61 + _this.searchRowHeight)) + "px"
        }, 200)

      })

      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs[this.searchRow].$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (84 + 61 + this.searchRowHeight)) + "px"
        })();
      };

      this.col[2].formatter = this.sexFormatter;
      // this.data = [{
      //   node_name: this.$route.params.roomName,
      //   id: '000',
      //   hasChildren: true
      // }]
    },
    methods: {
      exportExcel(){
        let data = {
          "colTitle": {
            "a0101": "干部名称",
            // "number": "干部编号",
            "a0104": "性别",
            "a0107": "出生日期",
            "deptName": "所属部门",
            "state": "干部状态",
            "status": "档案状态",
            "location":"所在库房位置"

          },
          "colWidth": {

          },
          "dataList": [],
          "tableTitle": "库房存储档案表",
          "sheetTitle": "库房存储档案表",
          "downLoadName": "库房存储档案表"
        }
        if (this.multipleSelection.length == 0) {
          this.$confirm('是否导出搜索后的档案数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let form = JSON.parse(JSON.stringify(this.queryForm));
            form.pageSize = 9999999;

            queryA01(form).then(response => {
              if (response.rows.length == 0) {
                this.$message.error("无数据,导出失败");
                return;
              }
              data.dataList = response.rows;
              data.dataList.map(item=>{
                item.state = this.stateFormat({},{},item.state);
                item.status = this.statusFormat({},{},item.status);
                item.a0104 = this.sexFormatter({},{},item.a0104);
              })
              downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data),this)
              // tableData
            })


          })
        } else {
          this.$confirm('是否导出选中的台账数据?', "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let checkboxDatas = this.multipleSelection;
            checkboxDatas.map(item=>{
              item.state = this.stateFormat({},{},item.state);
              item.status = this.statusFormat({},{},item.status);
              item.a0104 = this.sexFormatter({},{},item.a0104);
            })
            // checkboxDatas.map((item, index) => {
            //   item.e01z701 = this.geteFullNameWapper(item, "e01z701");
            //   item.deptId = this.geteFullNameWapper(item, "deptId");
            //   item.duty = this.geteFullNameWapper(item, "duty");
            // })
            data.dataList = checkboxDatas
            downLoadPost("/export/excel/exportExcelDynamicCol", JSON.stringify(data),this)
          })

        }


      },
      //加载库房节点的统计信息
      getNodeNum() {

        let leftNode = this.leftNode;
        //假设缓存中不存在值
        // let b = false;
        // this.nodesSummary.map(item=>{
        //   if(item.id == leftNode.id){
        //     //缓存中存在值  替换  跳出该方法
        //     this.leftButtomInfo = item.msg;
        //     b = true;
        //   }
        // })
        // if(b){
        //   return;
        // }
        let params = {
          roomId: this.roomId,
          type: (typeof leftNode.type == 'undefined' || leftNode.type == null || leftNode.type == '') ? 0 : leftNode.type,
          column: leftNode.columnNumber,
          section: leftNode.sectionNumber,
          layer: leftNode.index
        }
        queryNodeNum(params).then(response => {
          // this.leftButtomInfo = "已占用" + node.data.curNum + ",剩余" + node.data.allowance
          let msg = "已占用" + response.data.curNum + ",剩余" + response.data.allowance;
          this.leftButtomInfo = msg;
          let nodeNum = {
            msg: msg,
            id: leftNode.id
          }
          // this.nodesSummary.map(item=>{
          //   //缓存中存在值  替换之前的值
          //   if(item.id == leftNode.id){
          //     item.msg = msg;
          //     b = true;
          //   }
          // })

        })
      },

      pageSize2(value) {

        // this.queryForm.pageSize = value
        this.a01DataPage.pageSize = value;
        if (this.a01Data.total > 0) {
          // this.getCheckList()
          this.getDeptA01Data();
        }


      },
      pageCurrent2(value) {
        this.a01DataPage.pageNum = value;
        // this.queryForm.pageNum = value
        if (this.a01Data.total > 0) {
          // this.getCheckList()
          this.getDeptA01Data();
        }
      },
      // headClass() {
      //   return "background:rgba(204, 204, 204, 0.18)";
      // },
      StepsListRowClick(row) {    //档案管理-点击行事件-方法
        this.$refs.tableRefs.clearSelection()
        if (this.multipleSelection.length === 0) {
          this.$refs.tableRefs.toggleRowSelection(row)
          return;
        }
        this.$refs.tableRefs.toggleRowSelection(row)
      },
      StepsListRowClick2(row) {    //档案管理-点击行事件-方法
        this.$refs.table1Refs.clearSelection()
        if (this.multipleSelection.length === 0) {
          this.$refs.table1Refs.toggleRowSelection(row)
          return;
        }
        this.$refs.table1Refs.toggleRowSelection(row)
      },

      onHistory(row) {
        this.historyVisable = true;
        this.historyTitle = row.a0101 + "的历史轨迹"
        queryA01History(row.id).then(response => {
          this.historyDatas = response.data;
          // console.log(response);
        })
      },
      sexFormatter(row, col, value) {
        let str;
        this.a01Sex.map(item => {
          if (item.dictCode == value) {
            str = item.fullName;
          }
        })
        return str;
      },
      stateFormatter(row, col, value) {
        let str;
        this.a01Status.map(item => {
          if (item.dictCode == value) {
            str = item.fullName;
          }
        })
        return str;
      },
      a01Search(type) {
        const _this = this
        if (type === 1) {
          this.searchText = this.cadresText
        } else {
          this.deptNameShow = true
          this.searchText = this.personnelText
        }
        queryA01List({searchText: this.searchText, type: type})
          .then(response => {
            _this.leftData = response.data
          })
      },
      stateFormat(row, column, value) {
        let str = '';
        this.a01Status.map(item => {
          if (item.dictCode == value) {
            str = item.fullName;
          }
        })
        return str;
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
      //选中添加档案页面中的位置树时
      addA01TreeNodeClick(no, node, tree) {
        this.addA01PosNode = node.data;
        this.selectTree3NodeParent = node.parent.data.id;
      },
      //新增档案修改
      saveAddA01() {
        console.log(this.addA01PosNode);
        console.log(this.checkA01s);

        if (this.addA01PosNode.type != 3) {
          // Notification.error({
          //   title: '请选择正确的位置'
          // })
          this.$message({
            message: '请选择正确的位置',
            type: 'warning'
          })
        } else {
          if (this.checkA01s.length == 0) {
            this.$message({
              message: '请选择干部',
              type: 'warning'
            })
            // Notification.error({
            //   title: '请选择干部'
            // })
          } else {
            let params = {
              a01Id: [],
              posId: '',
            };
            let a0101s = [];
            this.checkA01s.map(item => {
              params.a01Id.push(item.id);
              a0101s.push(item.a0101);
            })
            params.posId = this.addA01PosNode.uuid;
            MessageBox.confirm(
              '是否在' + this.addA01PosNode.node_name + '中添加' + a0101s.join(",") + " 干部",
              '系统提示',
              {
                confirmButtonText: '是',
                cancelButtonText: '取消',
                type: 'warning'
              }
            ).then(() => {
              params.a01Id = params.a01Id.join("-");
              insertA01(params).then(response => {
                if (response.code == 200) {
                  this.$message({
                    message: response.msg,
                    type: 'success'
                  })
                  //刷新右侧树节点
                  let params2 = {};
                  let nodeIds = this.addA01PosNode.id.split("-");
                  params2.roomId = this.roomId;
                  params2.columnNumber = nodeIds[0];
                  params2.sectionNumber = nodeIds[1];
                  queryRoomStruct(params2).then(response => {
                    let data = [];
                    response.data.map(item => {
                      if (item.hasChildren == 'true') {
                        item.hasChildren = false;
                      }
                      if (item.hasChildren == 'false') {
                        item.hasChildren = true;
                      }
                      item.roomId = this.addA01PosNode.roomId;
                      ;
                      if (item.type == 3) {
                        item.node_name = item.node_name + "(剩余容量 " + item.allowance + ")"
                      }
                      data.push(item)
                    })
                    // resolve(data);
                    // console.log(this.selectTree2NodeParent);
                    // console.log(data);
                    this.$refs.tree3.updateKeyChildren(this.selectTree3NodeParent, data)
                  })
                  MessageBox.confirm(
                    '是否继续新增',
                    '系统提示',
                    {
                      confirmButtonText: '是',
                      cancelButtonText: '取消',
                      type: 'warning'
                    }
                  ).then(() => {
                    this.getDeptA01Data();
                  }).catch(() => {
                    this.dialogFormVisible1 = false;
                  })
                  this.getList();
                  if (this.addA01PosNode.id == this.leftNode.id) {
                    this.getNodeNum();
                  }

                }
              })
            })

          }
        }

        // this.dialogFormVisible1 = false;
      },
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
      //用户节点被选中时方法
      a01NodeClick(val) {
        console.log(val);
        this.checkA01s = val;
      },
      //点击部门树节点事件
      // flushA01(no,node,tree){
      //   console.log(node);
      //   this.getDeptA01Data(node.data.id);
      // },
      //加载干部数据

      getDeptA01DataByParams() {
        // this.a01DataPage.pageSize = 20;
        this.a01DataPage.pageNum = 1;
        this.formremove.a0101 = this.formremove2.a0101;
        this.formremove.state = this.formremove2.state;
        // this.formremove2 = JSON.parse(JSON.stringify(this.formremove));
        this.getDeptA01Data();
      },

      getDeptA01Data() {

        let params = {
          state: this.formremove.state == '' ? '' : this.formremove.state,
          a0101: this.formremove.a0101,
          pageSize: this.a01DataPage.pageSize,
          pageNum: this.a01DataPage.pageNum

        }
        if (params.state == '-') {
          params.state = '';
        }
        let _this = this;
        selectA01ByDeptIdAndPosiIsNull(params).then(response => {
          _this.a01Data.rows = response.rows;
          _this.a01DataData = response.rows;
          _this.a01Data.total = response.total;
        })
      },
      //加载部门树数据
      getDeptList() {
        getDeptTree().then(response => {
          this.deptData = response.data;
        })
      },
      //更改位置保存按钮
      saveLocaltionUpdate() {
        if (this.selectTree2Node.type != 3) {
          // Notification.error({
          //   title:'请选中层'
          // })
          this.$message({
            message: '请选中层',
            type: 'warning'
          })
        } else {
          let params = {
            a01Ids: [],
            posId: ''
          };
          this.multipleSelection.map(item => {
            params.a01Ids.push(item.id);
          })
          params.a01Ids = params.a01Ids.join("-");
          params.posId = this.selectTree2Node.uuid;
          updateA01Position(params).then(response => {
            if (response.code == 200) {
              // Notification.success({
              //   title:response.msg
              // })
              this.$message({
                message: response.msg,
                type: 'success'
              })
              //刷新tree2的节点数据
              // console.log(this.selectTree2Node);
              var params = {};
              let nodeIds = this.selectTree2Node.id.split("-");
              params.roomId = this.selectTree2Node.roomId;
              params.columnNumber = nodeIds[0];
              params.sectionNumber = nodeIds[1];
              queryRoomStruct(params).then(response => {
                let data = [];
                response.data.map(item => {
                  if (item.hasChildren == 'true') {
                    item.hasChildren = false;
                  }
                  if (item.hasChildren == 'false') {
                    item.hasChildren = true;
                  }
                  item.roomId = this.selectTree2Node.roomId;
                  ;
                  if (item.type == 3) {
                    item.node_name = item.node_name + "(剩余容量 " + item.allowance + ")"
                  }
                  data.push(item)
                })
                // resolve(data);
                // console.log(this.selectTree2NodeParent);
                // console.log(data);
                this.$refs.tree2.updateKeyChildren(this.selectTree2NodeParent, data)
              })
              // this.$refs.tree.updateKeyChildren(this.selectTree2Node,)
              this.getList();
              this.dialogFormVisible = false;
            }
          })
        }
      },
      //更改位置tree被点击触发
      tree2Click(no, node, tree) {
        // console.log(node);
        // console.log(tree);
        // console.log(no);
        this.selectTree2Node = node.data;
        this.selectTree2NodeParent = node.parent.data.id;
      },
      //左侧树被点击出发
      nodeClick(no, node, tree) {
        // console.log(node.data.id);
        this.leftNode = node.data;
        this.nodeId = node.data.id;
        // console.log(this.$refs.tree3);
        this.getNodeNum();

        // console.log(this.$refs.tree3);
        if (node.data.id == '000') {
          // this.leftButtomInfo = this.$route.params.info;
          this.queryForm.parentId = '';
        } else {
          // this.leftButtomInfo = "已占用" + node.data.curNum + ",剩余" + node.data.allowance
          this.queryForm.parentId = node.data.id;
        }

        this.queryForm.pageNum = 1
        this.getList();
      },
      filterTag(value, row) {
        console.log(value, row);
      },
      statusFormat(row, columns) {
        // row.status =
        return row.status == 1 ? "在库" : "已借出";
      },
      getList() {

        queryA01(this.queryForm).then(response => {
          this.tableData = response.rows;
          this.total = response.total;
          // tableData
        })
      },
      //加载所有库房结构
      loadAllNode(node, resolve) {
        console.log(node);
        if (node.data.length == 0) {
          //加载库房列表
          queryRoomList(null).then(response => {
            response.data.map(item => {
              item.node_name = item.roomName;
              item.roomId = item.id;
              item.type = 0;
            })
            resolve(response.data);
          })
          return;
        }
        console.log(node);
        node = node.data;
        var params = {};
        params.roomId = node.roomId;
        if (node.type == 1) {
          params.columnNumber = node.index;
        }
        if (node.type == 2) {
          params.columnNumber = node.columnNumber;
          params.sectionNumber = node.index;
        }
        queryRoomStruct(params).then(response => {
          let data = [];
          response.data.map(item => {
            if (item.hasChildren == 'true') {
              item.hasChildren = false;
            }
            if (item.hasChildren == 'false') {
              item.hasChildren = true;
            }
            item.id = item.id + "-" + item.uuid;
            item.roomId = node.roomId;
            item.node_name = "第" + item.node_name
            if (item.type == 3) {
              item.node_name = item.node_name + "(剩余容量 " + item.allowance + ")"
            }
            data.push(item)
          })
          resolve(data);

        })
      },
      //加载库房内结构
      loadleftNode(node, resolve) {
        console.log(node);
        if (node.level === 0) {
          setTimeout(() => {
            const data = [{
              node_name: this.$route.params.roomName,
              id: '000',
              hasChildren: false
            }];

            resolve(data);
          }, 100);
        } else {
          node = node.data;
          var params = {};
          params.roomId = this.roomId;
          if (node.type == 1) {
            params.columnNumber = node.index;
          }
          if (node.type == 2) {
            params.columnNumber = node.columnNumber;
            params.sectionNumber = node.index;
          }
          queryRoomStruct(params).then(response => {
            let data = [];
            response.data.map(item => {
              if (item.hasChildren == 'true') {
                item.hasChildren = false;
              }

              if (item.hasChildren == 'false') {
                item.hasChildren = true;
              }
              // if(item.type==3){
              //   // item.node_name = item.node_name + "(剩余容量 " + item.allowance + ")"
              // }
              data.push(item)
            })
            // if (node.level === 0){
            //   resolve([{
            //     node_name:this.$route.params.roomName,
            //     id:'000',
            //     hasChildren:false,
            //     children:data
            //   }])
            // }else{
            //   resolve(data);
            // }
            resolve(data);


          })
        }
      },

      //加载新增实体档案页面库房结构
      loadNode(node, resolve) {
        console.log(node);
        if (node.level === 0) {
          setTimeout(() => {
            const data = [{
              node_name: this.$route.params.roomName,
              id: '000',
              hasChildren: false
            }];

            resolve(data);
          }, 100);
        } else {
          node = node.data;
          var params = {};
          params.roomId = this.roomId;
          if (node.type == 1) {
            params.columnNumber = node.index;
          }
          if (node.type == 2) {
            params.columnNumber = node.columnNumber;
            params.sectionNumber = node.index;
          }
          queryRoomStruct(params).then(response => {
            let data = [];
            response.data.map(item => {
              if (item.hasChildren == 'true') {
                item.hasChildren = false;
              }

              if (item.hasChildren == 'false') {
                item.hasChildren = true;
              }
              item.node_name = "第" + item.node_name;
              if (item.type == 3) {
                item.node_name = item.node_name + "(剩余容量 " + item.allowance + ")"
              }
              data.push(item)
            })
            // if (node.level === 0){
            //   resolve([{
            //     node_name:this.$route.params.roomName,
            //     id:'000',
            //     hasChildren:false,
            //     children:data
            //   }])
            // }else{
            //   resolve(data);
            // }
            resolve(data);


          })
        }
      },

      /* grid  行点击事件*/
      handleCurrentChange(val) {
        // console.log(val)
      },
      /* 复选框事件 */
      handleSelectionChange(val) {

        this.multipleSelection = val;
        console.log(this.multipleSelection);
      },
      /* 新增 */
      handleAdd() {
        // this.addA01PosNode = '';
        //初始化干部选择信息
        this.checkA01s = [],
          this.getDeptList();
        this.getDeptA01Data("-");
        this.dialogFormVisible1 = true;
        let _this = this;
        if (this.nodeId == '' || this.nodeId == '000') {

        } else {
          let array = _this.nodeId.split("-");
          _this.defaultExpandKeys = ['000'];
          let array2 = [];
          for (let i = 0; i < array.length - 1; i++) {
            array2.push(array[i]);
            _this.defaultExpandKeys.push(array2.join("-"));
          }

          console.log(_this.$refs.tree3);

          let timout = function () {
            setTimeout(() => {
              let node = _this.$refs.tree3.store.nodesMap[_this.nodeId];
              console.log(node);
              if (typeof node == 'undefined') {
                timout();
              } else {
                _this.$refs.tree3.setCurrentKey(_this.nodeId);
                _this.addA01PosNode = node.data;
                _this.selectTree3NodeParent = node.parent.data.id;
              }

            }, 100)
          };
          timout();


        }
        // setTimeout(() => {
        //   console.log(_this.$refs.tree3.store);
        //   _this.$refs.tree3.store.nodesMap['000'].expanded = true;
        // }, 200)
        //
      },
      /* 档案位置变动 */
      handleUpData(val) {
        if (this.multipleSelection.length == 0) {
          // Notification.error({
          //   title: '请选中档案'
          // })
          this.$message({
            message: '请选中档案',
            type: 'warning'
          })
        } else {
          this.roomPostionUpdate = "库房位置变动 - 已选中" + this.multipleSelection.length + "本档案";
          this.dialogFormVisible = !this.dialogFormVisible
        }

      },
      /* 机构管理 */
      structure(val) {
        this.$router.replace({path: '/views/house/structure', params: {val: val}})
      },
      /* 出库 */
      outbound() {
        if (this.multipleSelection.length == 0) {
          // Notification.error({
          //   title: '请选中档案'
          // })
          this.$message({
            message: '请选中档案',
            type: 'warning'
          })
          return;
        }
        let msg = [];
        let ids = [];
        this.multipleSelection.map(ite => {
          // msg = msg + item.a0101;
          msg.push(ite.a0101);
          ids.push(ite.id)
        })
        MessageBox.confirm(
          '是否将' + msg.join(",") + '移出当前位置',
          '系统提示',
          {
            confirmButtonText: '是',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          downRoom(ids.join(",")).then(response => {
            if (response.code == 200) {
              // Notification.success({
              //   title: response.msg
              // })
              this.$message({
                message: response.msg,
                type: 'success'
              })
              this.getNodeNum();
              this.getList();
            }
          })
        })
      },
      pageCurrent(value) {
        this.queryForm.pageNum = value;
        this.getList();
      },
      pageSize(value) {
        this.queryForm.pageSize = value;
        this.getList();
      },
      search() {
        this.queryForm.pageNum = 1;
        this.queryForm.a0101 = this.a01QuertForm.a0101;
        this.queryForm.state = this.a01QuertForm.state;
        this.queryForm.status = this.a01QuertForm.status;
        this.getList();
      },
      reset() {
        this.queryForm.a0101 = '';
        this.queryForm.state = '';
        this.queryForm.status = '';
        this.queryForm.pageNum = 1;
        this.getList();
      },

    }
  }
</script>

<style lang="scss" scoped>
  .app-archives {
    height: 100%;

    .tree {
      width: 100%;
      padding: 10px 10px 26px 10px;
      /*border-right: 1px solid #e2e3e6;*/
    }

    .search-form {
      width:99%;
      background:rgba(204, 204, 204, 0.18);
      margin-left:5px;
      margin-bottom: 10px;
      padding-left: 10px;
      padding-top:10px;
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
  }

  .tree-style {
    /deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
      background: rgba(204, 204, 204, 0.18);
    }
  }

  .el-dialog__body {
    height: 500px;
    display: block;
  }

  .app-archives {
    overflow: auto;
    height: calc(100vh - 86px);
  }

  .el-input__inner {
    width: 250px;
  }

  .el-form-item__content {
    width: 250px;
  }

  .el-input {
    width: 250px;
  }

  .inputW {
    width: 250px;
  }

  .el-form--inline .el-form-item {
    margin: 0px;
  }

  /deep/ .el-scrollbar__wrap {
    overflow-x: hidden;
  }


  div {
    table {

    }

    form {

    }
  }

  .table-left {
    width: 100%;
    height: 90%;
    border: 0.5px solid #e2e3e6;
    padding: 10px;
    min-height: calc(100vh - 84px);

    .input-w {
      width: 28%;
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

    //分页
    /deep/ .el-pagination .el-select .el-input .el-input__inner {
      height: 20px;
    }

    /deep/ .el-pagination--small .el-pagination__editor.el-input .el-input__inner {
      height: 20px;
    }

    /deep/ .el-pagination {
      padding: 3px 6px;
      margin-top: 5px;
    }
  }

  /deep/ .el-table tbody tr:hover > td {
    background-color: #ecf5ff !important
  }

  /deep/ .el-table__body td {
    padding: 0;
    height: 40px;
  }

  .form-search {
    margin-top: 5px;
    margin-bottom: 5px;
    font-weight: 100;
    width: 100%;
    display: flex;
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
</style>

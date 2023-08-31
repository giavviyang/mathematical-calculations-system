<template>
  <div class="mainHeight">
    <main-flexible-tree :data="treeData" :defaultProps="TreeDefaultProps" @handleNodeClick="handleNodeClick">
      <div slot="mainRight" class="mainHeight">
        <container-top class="mainTop">
          <el-form
            ref="queryForm"
            slot="ipt"
            :model="queryForm"
            :inline="true"
            @submit.native.prevent
          >
            <el-form-item label="组题名称：" prop="testName">
              <el-input v-model="queryForm.testName" size="mini" placeholder="请输入组题名称" clearable/>
            </el-form-item>
            <el-form-item label="是否发布：" prop="isRelease">
              <el-select v-model="queryForm.isRelease"
                         placeholder="请选择"
                         clearable
                         class="inputW"
                         size="mini">
                <el-option v-for="item in isRelease" :key="item.key" :label="item.label" :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="出题方式：" prop="topicMode">
              <el-select
                v-model="queryForm.topicMode"
                placeholder="请选择"
                clearable
                class="inputW"
                size="mini">
                <el-option v-for="item in topicMode" :key="item.key" :label="item.label" :value="item.value"/>
              </el-select>
            </el-form-item>
<!--            <el-form-item>-->
              <el-button type="primary" icon="el-icon-search" size="mini"
                         @click="search">搜索
              </el-button>
              <el-button type="primary" icon="el-icon-search" size="mini"
                         @click="reset">重置
              </el-button>
<!--            </el-form-item>-->
<!--            <el-form-item>-->
<!--             -->
<!--            </el-form-item>-->
          </el-form>
          <el-form :inline="true" slot="btnDrop">
            <el-form-item>
              <el-button size="mini" type="primary" @click="addClickEvent">新增</el-button>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="modifyClickEvent">编辑</el-button>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="removeTestDate">删除</el-button>
            </el-form-item>
          </el-form>
        </container-top>
        <slot-table
          class="mainCenter"
          :pageSize="pageSize"
          :pageNum="pageNum"
          :total="total"
          @handleChange="handleChange">
          <el-table
            slot="table"
            row-key="id"
            align="left"
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            class="table-style"
            :header-cell-style="headClass"
            tooltip-effect="light"
            empty-text=" "
            size="mini"
            ref="tableGrid"
            style="width: 100%"
            :data="tableGridData"
            @row-click="rowClickEvent"
            @selection-change="handleSelectionChange"
            height="100%"
            :cell-class-name="cellClsNm">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column type="index" label="序号" align="center" width="50px"/>
            <el-table-column show-overflow-tooltip align="center"
                             v-for="(item, index) in col"
                             :formatter="item.formatter"
                             :v-if="col[index].isCol"
                             :key="index"
                             :width="item.width"
                             :prop="item.prop"
                             :label="item.label">
            </el-table-column>
            <el-table-column
              prop="classification"
              isCol="true" width="550px">
              <template slot="header">
                <div class="cellDiv">
                  <span>知识点</span><span>/</span> 占比
                </div>
              </template>
              <template slot-scope="scope">
                <div class="cellDiv" v-for="listItem in scope.row.classification"
                     :key="listItem.classificationId"
                     v-show="listItem.classificationMsg">
                  <span>{{ listItem.classificationMsg }}</span> <span>/</span> {{ listItem.percentage }}%
                </div>
              </template>
            </el-table-column>
            <el-table-column label="创建人" align="center" prop="creator" isCol="true"/>
            <el-table-column label="创建时间" align="center" prop="createTime" isCol="true" width="140px"/>
            <el-table-column
              label="操作"
              prop="desc"
              min-width="100" max-width="200"
            >
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="modifyReleaseState(scope.row)"
                           v-if="scope.row.isRelease===1">发布
                </el-button>
                <el-button type="text" size="mini" @click="modifyReleaseState(scope.row)" disabled
                           v-if="scope.row.isRelease===2">已发布
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </slot-table>
      </div>
    </main-flexible-tree>
    <!-- 新增信息 -->
    <el-dialog
      :visible.sync="dialogFormVisible"
      :destroy-on-close="true"
      :append-to-body="true"
      :close-on-click-modal="false"
      class=" dialog-style dialog-basic addDialogForm"
      title="新增组题信息"
      width="60%">
      <el-form
        ref="addForm"
        :model="addForm"
        :rules="rules"
        class="addTest">
        <el-form-item label="组题名称：" prop="testName">
          <el-input v-model="addForm.testName" clearable size="mini" placeholder="请输入组题名称"/>
        </el-form-item>
        <el-form-item label="出题数量：" prop="testTopicNum">
          <el-input v-model.number="addForm.testTopicNum" clearable size="mini" placeholder="请输入出题数量"/>
        </el-form-item>
        <el-form-item label="限制用时：" prop="countTime">
          <el-input v-model.number="addForm.countTime" clearable size="mini" placeholder="请输入答题时间"/>
        </el-form-item>
        <el-form-item label="出题方式：">
          <el-radio-group v-model="addForm.topicMode">
            <el-radio :label='1'>统一</el-radio>
            <el-radio :label='2'>随机</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="本题受众范围" style="width: 100%;">
          <div>
            <el-scrollbar style="height: 150px;">
              <el-tree
                node-key="id"
                empty-text=" "
                ref="tree4"
                :data="classRoom"
                :props="defaultProps"
                :default-checked-keys="defaultTreeKeys"
                :filter-node-method="filterNode"
                icon-class="el-icon-folder-opened"
                show-checkbox
                @check="deptTreeClick"
                :highlight-current="true"
                :expand-on-click-node="false"
                default-expand-all/>
            </el-scrollbar>
          </div>
        </el-form-item>
        <!--        <el-form-item label="本题受众范围：" class="testRange">
                  <el-cascader-panel :options="classRoom"
                                     ref="rangeChange"
                                     :props="defaultProps"
                                     @change="rangeHandleChange">
                  </el-cascader-panel>
                </el-form-item>-->
      </el-form>
      <el-form :model="cascaderForm" class="relatedKnowledge">
        <el-form-item label='关联知识点：' class="testRange">
          <el-cascader
            size="mini"
            ref="addFormCascader"
            @change="addHandleTypeChange"
            placeholder="请选择知识点"
            :props="knowledgeListProps"
            :options="cascaderForm.options"
            filterable
            clearable></el-cascader>
        </el-form-item>
      </el-form>
      <el-form
        :model="addForm"
        v-for="(item,index) in addForm.classification" :key="index"
        v-if="item.classificationMsg"
        class="knowledgeName">
        <el-form-item label="知识点名称：">
          <el-input size="mini" :disabled="true" clearable v-model="item.classificationMsg"></el-input>
        </el-form-item>
        <el-form-item label="占比：">
          <el-input-number :min="0" :max="100" size="mini" clearable
                           v-model="item.percentage"></el-input-number>
        </el-form-item>
        <el-form-item>
          <el-button size="mini" @click.prevent="remove(index)">移除</el-button>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogFormVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="addFormEvent">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import selectTree from '@/views/components/tree/tree.vue'


import {addTestTable, modifyReleaseState, pagingQuery, removeTestTable} from '@/api/applet/classroomTest/manage'
import {getDataByEparentCode} from '@/api/system/dict/newDict'
import {getKLCByParams, getListByPid} from '@/api/usually/knowledgemaintain/knowledge'

import {getDeptTree} from '@/api/system/dept'
import ContainerTop from "@/components/public/MainBody/ContainerTop";
import SlotTable from "@/components/public/table/SlotTable";
import Page401 from "@/views/error/401";
import MainFlexibleTree from "@/components/public/MainBody/MainFlexibleTree";
import {getInfo} from "@/api/login";

const params = {
  pageNum: 1,
  pageSize: 20,
  classId: '',
}
export default {
  name: "index",
  components: {MainFlexibleTree, Page401, SlotTable, ContainerTop, Treeselect, selectTree},
  data() {
    let loginCodeTest = /^[0-9a-zA-Z]+$/;
    let isloginCodeTest = (rule, value, callback) => {
      if (!loginCodeTest.test(value)) {
        return callback(new Error('只能输入数字和字母!'))
      } else {
        callback();
      }
    };
    return {
      userId: '',
      treeData: [],
      TreeDefaultProps: {
        children: "children",
        label: "deptName",
      },
      tableGridData: [],
      col: [
        {label: '组题名称', prop: 'testName', isCol: true, width: '150px',},
        {
          label: '是否发布', prop: 'isRelease', isCol: true, formatter: function (row, column, cellValue) {
            if (cellValue == '1') {
              return "未发布"
            } else if (cellValue == '2') {
              return "已发布"
            } else if (cellValue == '3') {
              return "已回收"
            }
          }
        },
        /*1-统一（都做相同的题），2-随机（每个人随机出题）*/
        {
          label: '出题方式', prop: 'topicMode', isCol: true, formatter: function (row, column, cellValue) {
            return cellValue == "1" ? "统一" : "随机";
          }
        },
        {label: '总题数', prop: 'testTopicNum', isCol: true},
        {label: '限制用时', prop: 'countTime', isCol: true},
        {
          label: '本题受众范围', prop: 'classRoomName', isCol: true, width: '150px',
          formatter: function (row, column, cellValue) {
            let result = ''
            cellValue.forEach(row => {
              result += row + "\n"
            })
            return result;
          }
        },
      ],
      /*搜索表单*/
      queryForm: {
        pageSize: 20,
        pageNum: 1,
        classId: '',
      },

      isRelease: [
        {label: '未发布', value: '1', key: '1'},
        {label: '已发布', value: '2', key: '2'},
      ],
      topicMode: [
        {label: '统一', value: '1', key: '1'},
        {label: '随机', value: '2', key: '2'},
      ],
      /* 复选框选中*/
      gridCheck: [],
      dialogFormVisible: false,
      classRoom: [],
      defaultProps: {
        children: "children",
        label: "deptName",
        value: 'id',
        multiple: true,
      },
      /* 新增数据 */
      addForm: {
        testName: '',
        testTopicNum: '',
        countTime: '',
        topicMode: 1,
        isRelease: '1',
        classification: [],
      },
      modifyForm: {
        testName: '',
        testTopicNum: '',
        countTime: '',
        topicMode: 1,
        isRelease: '1',
        classification: [],
      },
      cascaderForm: {
        options: []
      },
      cascaderRange: {
        options: []
      },
      /**/
      deptTreeClickDatas: [],
      defaultTreeKeys: [],
      firstTreeCheckedData: [],
      knowledgeListProps: {
        leaf: 'leaf',
        value: 'dictCode',
        label: 'fullName',
        lazy: true,
        lazyLoad(node, resolve) {
          const {level} = node;
          setTimeout(() => {
            const {data, level, path} = node;
            if (!data || (data && data.haschild == false) || (data && data.leaf == true)) {
              return resolve([])
            } else if (level == 3) {
              let params = {
                classfiyCode: node.data.dictCode,
                pageNum: 1,
                pageSize: 99999,
              }
              getKLCByParams(params).then(response => {
                let returnData = [];
                for (let i = 0; i < response.rows.length; i++) {
                  let data = response.rows[i];
                  returnData.push({dictCode: data.classificationId, fullName: data.classificationMsg, leaf: true})
                }
                for (let i = 0; i < returnData.length; i++) {
                  getListByPid({pid: returnData[i].dictCode}).then(reponse => {
                    if (reponse.data != null && reponse.data != '') {
                      returnData[i].leaf = false;
                    }
                    if (i == returnData.length - 1) {
                      resolve(returnData);
                    }
                  })
                }
              })
            } else if (level == 4) {
              getListByPid({pid: node.data.dictCode}).then(reponse => {
                let returnData = [];
                for (let i = 0; i < reponse.data.length; i++) {
                  let data = reponse.data[i];
                  returnData.push({dictCode: data.classificationId, fullName: data.classificationMsg, leaf: true})
                }
                resolve(returnData);
              })
            } else {
              getDataByEparentCode({dictType: 'classfiy', eparentCode: node.data.dictCode}).then(response => {
                resolve(response.data);
              })
            }
          }, 100);
        }
      },
      formLabelWidth: '120px',
      total: 0,
      pageNum: 1,
      pageSize: 20,
      loading: true,
      classId: '',
      rules: {
        testName: [{required: true, message: '请输入组题名称', trigger: 'blur'},
          {min: 0, max: 18, message: '输入内容不得超过18个字符', trigger: 'blur'}],
        testTopicNum: [
          {required: true, message: '请输入出题数量', trigger: 'blur'},
          {type: 'number', message: '出题数量必须为数值'}
        ],
        topicMode: [{message: '请选择出题方式', trigger: 'blur'}],
        countTime: [{required: true, message: '请输入限制用时', trigger: 'blur'},
          {type: 'number', message: '限制用时必须为数值'}],
      },
    }
  },
  created() {
    this.leftDepTree();
    this.getUser();
    getDataByEparentCode({dictType: 'classfiy', eparentCode: '-'}).then(response => {
      this.cascaderForm.options = response.data;
    })
    this.keyupSubmit();
    // window.addEventListener('keydown', this.handleKeyDown, true);
    // console.log(Cookies.get("username"))
  },
  methods: {
    //键盘按下enter搜索事件
    keyupSubmit() {
      document.onkeydown = e => {
        const _key = window.event.keyCode
        if (_key === 13) {
          this.search();
        }
      }
    },
    getUser() {
      getInfo().then(res => {
        if (res.code === 200) {
          this.userId = res.user.userId
          // console.log("111111111111111111111"+JSON.stringify(res.user.userId))
        }
      })
    },
    leftDepTree() {
      getDeptTree({deptType: "1"}).then(response => {
        if (response.code === 200) {
          this.treeData = response.data
          this.fore(this.treeData[0])
          this.testTabeGrid(params)
        }
      })
    },
    handleNodeClick(val, node, component) {
      console.log(val, node, component)
      this.fore(val)
      // params.classId = val.id
      this.testTabeGrid(params);
    },
    /*循环查找tree树中的班级节点*/
    fore(obj) {
      if (obj && obj.nodeType !== "2") {
        this.fore(obj.children[0])
      } else {
        this.classId = obj.id;
        params.classId = obj.id;
        this.queryForm.classId = obj.id;
      }
      return;
    },
    deptTreeClick(val, data) {
      this.deptTreeClickDatas = data
      this.firstTreeCheckedData = data.checkedKeys
    },
    /* gird数据请求 */
    testTabeGrid(params) {
      const _this = this
      pagingQuery(params).then(function (response) {
        // console.log(response)
        _this.tableGridData = response.rows
        _this.total = response.total
        _this.loading = false;
        _this.pageNum = response.pageNum;
        _this.pageSize = response.pageSize;

      })
    },
    /*行点击事件*/
    rowClickEvent(row) {
      this.$refs.tableGrid.clearSelection()
      if (this.gridCheck.length === 0) {
        this.$refs.tableGrid.toggleRowSelection(row)
        return
      }
    },
    /*复选框事件*/
    handleSelectionChange(val) {
      this.gridCheck = val;
    },
    /*设置表格列*/
    cellClsNm({row, column, rowIndex, columnIndex}) {
      if (column.label === '组题名称') {
        return 'tiName'
      }
      if (column.label === '知识点/知识点占比') {
        return 'klgPer'
      }
    },
    /*添加按钮点击事件*/
    addClickEvent() {
      this.initLeftTreeData()
      this.addForm = JSON.parse(JSON.stringify(this.modifyForm))
      this.dialogFormVisible = true
    },
    /*选择知识点范围级联面板*/
    rangeHandleChange() {
      // console.log(this.$refs.rangeChange)
      let checkNode = this.$refs.rangeChange.getCheckedNodes()[0]
      console.log('节点', checkNode);
      let arr = [];
      if (checkNode) {
        arr.push(checkNode.value);
        if (checkNode.hasChildren) {
          checkNode.children.forEach(item => {
            arr.push(item.value);
          })
        }
      }
      this.firstTreeCheckedData = arr;
      console.log(this.firstTreeCheckedData)
    },
    /*知识点联级选择器点击事件*/
    addHandleTypeChange() {
      let checkNode = this.$refs.addFormCascader.getCheckedNodes()[0];
      if (checkNode != null) {
        let index = checkNode.level - 1;
        let label = checkNode.pathLabels[index];
        for (let i = 0; i < this.addForm.classification.length; i++) {
          if (this.addForm.classification[i].classificationId == checkNode.value) {
            this.$message.warning("知识点已添加")
            return;
          }
        }
        let knowledge = {classificationId: checkNode.value, classificationMsg: label, percentage: '1'};
        this.addForm.classification.push(knowledge);
      }
    },
    /*知识点相关的联级选择器中的方法*/
    filterNode(value, data) {
      if (!value) return true;
      return data.deptName.indexOf(value) !== -1;
    },
    /*关联知识点删除事件*/
    remove(index) {
      if (index !== -1) {
        this.addForm.classification.splice(index, 1)
      }
    },

    /*查询部门（学校班级）tree树*/
    initLeftTreeData() {
      getDeptTree({deptType: 1}).then(response => {
        this.classRoom = response.data;
        // console.log(response)
      })
    },
    /*添加组题条目*/
    addFormEvent() {
      this.$refs.addForm.validate(valid => {
        if (valid) {
          if (this.addForm.classification.length < 1) {
            this.$message.warning("请选择知识点")
            return;
          }
          let percentage = 0;
          for (let i = 0; i < this.addForm.classification.length; i++) {
            let knowledge = this.addForm.classification[i];
            percentage += parseInt(knowledge.percentage)
          }
          if (percentage != 100) {
            this.$message.warning("知识点总占比未达到百分百")
            return;
          }
          if (this.firstTreeCheckedData.length <= 0) {
            this.$message.warning("请选择受众范围！")
            return;
          }

          this.addForm.classRoomId = this.firstTreeCheckedData
          addTestTable(this.addForm).then(response => {
            this.addForm = {}
            this.queryForm.pageNum = params.pageNum
            this.queryForm.pageSize = params.pageSize
            this.queryForm.classId = params.classId
            this.testTabeGrid(this.queryForm);
            this.dialogFormVisible = false
            this.defaultTreeKeys = []
            if (response.code == 200) {
              this.$message.success(response.msg)
            }
          })
        }
      })
    },

    modifyClickEvent() {
      //判断数据条数
      if (this.gridCheck.length != 1) {
        this.$message.warning("请选择一条数据！")
        return
      }
      if (this.gridCheck[0].isRelease == 2) {
        this.$message.warning("您选择的数据已发布，不可修改！")
        return
      }
      let creatorId = this.gridCheck[0].creatorId;
      if (this.userId === creatorId) {
        this.initLeftTreeData()
        this.defaultTreeKeys = this.gridCheck[0].classRoomId
        this.firstTreeCheckedData = this.gridCheck[0].classRoomId
        this.addForm = JSON.parse(JSON.stringify(this.gridCheck[0]))
        this.dialogFormVisible = true
      } else {
        this.$message.warning("您不是本组题的创建者，无法继续操作！")
        return
      }
    },

    removeTestDate() {
      //判断数据条数
      if (this.gridCheck.length !== 1) {
        this.$message.warning("请选择一条数据！")
        return
      }
      let ids = []
      this.gridCheck.forEach(row => {
        ids.push(row.id)
      })
      this.queryForm.pageNum = 1;
      this.queryForm.pageSize = 20;
      this.queryForm.classId = params.classId;
      let creatorId = this.gridCheck[0].creatorId;
      if (this.userId === creatorId) {
        if (this.gridCheck.length == 1) {
          this.$confirm('此操作将永久删除该数据并清除学生答题相关数据, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            removeTestTable({testId: ids[0]}).then(response => {
              this.testTabeGrid(this.queryForm);
              if (response.code == 200) {
                this.$message.success("删除成功！")
              }
            })
          })
        }
      } else {
        this.$message.warning("您不是本组题的创建者，无法继续操作！")
        return
      }

      // } else {
      //   this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
      //     confirmButtonText: '确定',
      //     cancelButtonText: '取消',
      //     type: 'warning'
      //   }).then(() => {
      //     batchRemoveTestTable({jsonIds: JSON.stringify(ids)}).then(response => {
      //       this.queryForm.pageNum = params.pageNum;
      //       this.queryForm.pageSize = params.pageSize;
      //       this.queryForm.classId = params.classId;
      //       this.testTabeGrid(this.queryForm);
      //       if (response.code == 200) {
      //         this.$message.success("删除成功！")
      //       }
      //     })
      //   })
      // }
    },
    modifyReleaseState(row) {
      //判断数据条数
      if (row.isRelease == 2) {
        this.$message.warning("您选择的数据已发布！")
        return
      }
      let creatorId = row.creatorId;
      console.log(this.userId, creatorId)
      if (this.userId === creatorId) {
        console.log(this.userId, creatorId)
        let ids = []
        ids.push(row.id)
        this.$confirm('此操作将发布该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          modifyReleaseState({idList: JSON.stringify(ids), flag: 2}).then(response => {
            this.queryForm.pageNum = params.pageNum;
            this.queryForm.pageSize = params.pageSize;
            this.queryForm.classId = params.classId;
            this.testTabeGrid(this.queryForm);
            if (response.code == 200) {
              this.$message.success("发布成功！")
            }
          })
        }).catch(err => {
          console.log(err)
        })
      } else {
        this.$message.warning("您不是本组题的创建者，无法继续操作！")
        return
      }
    },
    // 分页
    pageCurrent(val) {
      this.pageNum = val
      params.pageNum = val
      params.pageSize = this.pageSize;
      this.testTabeGrid(params)
    },
    pageSizeS(val) {
      this.pageSize = val
      params.pageSize = val;
      params.pageNum = this.pageNum;
      this.testTabeGrid(params)
    },
    handleChange(pageSize, pageNum) {
      this.pageSize = pageSize
      params.pageSize = pageSize;
      this.pageNum = pageNum;
      params.pageNum = pageNum;
      // params.pageNum = this.pageNum;
      this.testTabeGrid(params)
    },
    /* 上部搜索 */
    search() {
      this.queryForm.pageNum = 1;
      this.queryForm.pageSize = 20;
      this.testTabeGrid(this.queryForm);
    },
    /*重置上部搜索*/
    reset() {
      this.queryForm = {
        pageSize: 20,
        pageNum: 1,
        classId: params.classId
      }
      this.testTabeGrid(params);
    },
    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
    },
  },
}
</script>
<style lang="scss" scoped>
.mainCenter {
  height: calc(100% - 107px);

  .cellDiv {
    color: red;
    width: 100%;
    display: flex;
    padding: 0;

    span:first-of-type {
      display: inline-block;
      width: 75%;
      text-align: right;
    }

    span:nth-of-type(2) {
      color: #5a5e66;
      font-size: 16px;
      font-weight: 600;
      display: inline-block;
      margin: 0 5px;
    }
  }
}

/deep/ .container {
  .el-form {
    flex-direction: row;
    min-height: 70px;
    width: 80%;
    margin: 0 auto;
  }

}

.remove {
  margin-left: 10px;
}

/deep/ .el-table .cell {
  white-space: pre-line;
}

.addDialogForm {

  min-width: 522px;
  overflow: scroll;

  /deep/ .el-dialog__body {
    width: 90%;
    margin: 10px auto;

    .addTest {
      display: flex;
      width: 100%;
      flex-wrap: wrap;

      .el-form-item {
        width: 50%;
        display: flex;

        .el-form-item__label {
          width: 133px;
        }

        .el-form-item__content {
          width: calc(100% - 150px);

          .el-input {
            width: 100%;
          }
        }

      }

      .testRange {
        width: 100%;
        display: flex;

        .el-form-item__label {
          width: 133px;
        }

        .el-form-item__content {
          width: calc(100% - 150px);

          .el-input {
            width: 80%;
          }

          .el-cascader {
            width: 100% !important;
          }
        }
      }
    }

    .relatedKnowledge {
      width: 100%;

      .testRange {
        width: 100%;
        display: flex;

        .el-form-item__label {
          width: 133px;
        }

        .el-form-item__content {
          width: calc(100% - 150px);

          .el-cascader {
            width: 100% !important;
          }
        }
      }
    }

    .knowledgeName {
      width: 100%;
      display: flex;
      justify-content: space-between;

      .el-form-item:first-of-type {
        width: 70%;
        display: flex;

        .el-form-item__label {
          width: 133px;
        }

        .el-form-item__content {
          width: 60%;
        }

      }

      .el-form-item:nth-of-type(2) {
        width: 300px;
        display: flex;

        .el-form-item__label {
          width: 60px;
        }

        .el-form-item__content {
          width: 50%;
        }
      }

      .el-form-item:last-of-type {
        width: 10%;
        display: flex;
        justify-content: end;
      }
    }
  }
}

</style>

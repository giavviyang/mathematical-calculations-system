<template>
  <div class="mainHeight">
    <main-flexible-tree :data="treeData" :defaultProps="TreeDefaultProps"
                        @handleNodeClick="handleNodeClick">
      <div slot="mainRight" class="mainHeight">
    <container-top class="mainTop">
      <el-form v-model="queryForm" :inline="true" slot="ipt">
        <el-form-item label="数域：">
          <el-select v-model="queryForm.knowledgeNumField" placeholder="请选择数域" clearable>
            <el-option v-for="(item,index) in knowledgeNumField" :key="index" :label="item.label"
                       :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="运算法则：">
          <el-select v-model="queryForm.knowledgeOperation" placeholder="请选择运算法则" clearable>
            <el-option v-for="(item,index) in knowledgeOperation" :key="index" :label="item.label"
                       :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="知识点名称：">
          <el-input placeholder="请输入知识点名称" v-model="queryForm.classificationName" size="mini" clearable/>
        </el-form-item>
<!--        <el-form-item>-->
          <el-button type="primary" size="mini" icon="el-icon-search" @click="onSubmit">搜索</el-button>
<!--        </el-form-item>-->
      </el-form>
      <el-form v-model="queryForm" :inline="true" slot="btnDrop">
        <el-form-item label="出题方式：">
          <el-select v-model="queryForm.isRandom" placeholder="请选择出题方式" clearable size="mini">
            <el-option v-for="(item,index) in isRandom" :key="index" :label="item.label"
                       :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" icon="el-icon-search" @click="onSubmit">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" icon="el-icon-tickets" @click="unifyTest">统一出题</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" icon="el-icon-document-copy" @click="diffTest">随机出题</el-button>
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
        stripe
        border
        class="table-style"
        empty-text=" "
        size="mini"
        style="width: 100%;height: 100%"
        :data="tableDate"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          label="序号"
          type="index"
          width="55">
        </el-table-column>
        <el-table-column
          label="知识点名称"
          prop="classification">
          <template slot-scope="scope">
            {{ scope.row.knowledgeNumField }}-{{ scope.row.knowledgeOperation }}-{{
              scope.row.knowledgeType
            }}-{{ scope.row.classificationName }}
          </template>
        </el-table-column>
        <el-table-column
          label="出题方式"
          prop="desc"
          min-width="100" width="250px">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="handleClick(scope.row)" v-if="scope.row.isRandom==0">随机
            </el-button>
            <el-button type="text" size="mini" @click="handleClick(scope.row)"
                       v-if="scope.row.isRandom==1">统一
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </slot-table>
        </div>
    </main-flexible-tree>
  </div>
</template>
<script>
import ContainerTop from "@/components/public/MainBody/ContainerTop";
import SlotTable from "@/components/public/table/SlotTable";
import {pageingQueryQuestionSetting, modifyIsRandom} from '@/api/training/trainingDetails'
import MainFlexibleTree from "@/components/public/MainBody/MainFlexibleTree";
import {getDeptTree} from "@/api/system/dept";

export default {
  name: "index",
  components: {MainFlexibleTree, SlotTable, ContainerTop},
  data() {
    return {
      treeData: [],
      TreeDefaultProps: {
        children: "children",
        label: "deptName",
      },
      tableDate: [],
      multipleSelection: [],
      pageSize: 20,
      pageNum: 1,
      total: 0,
      queryForm: {
        knowledgeNumField: '',
        knowledgeOperation: '',
        isRandom: '',
        classificationName: '',
        pageSize: 20,
        pageNum: 1,
        classId:'',
      },
      classId:'',
      knowledgeNumField: [
        {
          value: 1,
          label: '整数'
        },
        {
          value: 2,
          label: '小数'
        },
        {
          value: 3,
          label: '分数'
        },
      ],
      knowledgeOperation: [
        {
          value: 1,
          label: '加法'
        },
        {
          value: 2,
          label: '减法'
        },
        {
          value: 3,
          label: '乘法'
        },
        {
          value: 4,
          label: '除法'
        }
      ],
      isRandom: [
        {
          value: 0,
          label: '随机'
        },
        {
          value: 1,
          label: '统一'
        },
      ]
    }
  },
  created() {
    this.leftDepTree();
    this.getTrainingDetails(this.queryForm);
    // window.addEventListener('keyup', this.onSubmit);
    this.keyupSubmit();
  },
  methods: {
    //键盘按下enter搜索事件
    keyupSubmit() {
      document.onkeydown = e => {
        const _key = window.event.keyCode
        if (_key === 13) {
          this.onSubmit();
        }
      }
    },
    leftDepTree() {
      getDeptTree({deptType: "1"}).then(response => {
        if (response.code === 200) {
          this.treeData = response.data
          console.log(response.data)
          this.fore(this.treeData[0])
        }
      })
    },
    handleNodeClick(val, node, component) {
      console.log(val, node, component)
      this.queryForm={
        knowledgeNumField: '',
          knowledgeOperation: '',
          isRandom: '',
          classificationName: '',
          pageSize: 20,
          pageNum: 1,
      };
      this.fore(val)
      this. getTrainingDetails(this.queryForm)
    },
    /*循环查找tree树中的班级节点*/
    fore(obj) {
      if (obj && obj.nodeType !== "2") {
        this.fore(obj.children[0])
      } else {
        this.classId = obj.id;
        this.queryForm.classId = obj.id;
      }
      return;
    },
    handleChange(pageSize, pageNum) {
      this.pageNum = pageNum;
      this.queryForm.pageNum=pageNum;
      this.pageSize = pageSize;
      this.queryForm.pageSize=pageSize;
      this.getTrainingDetails(this.queryForm)
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    diffTest() {
      console.log(this.multipleSelection)
      if (this.multipleSelection===[]||this.multipleSelection.length<1) {
        this.$message({
          message: '请至少先选择一条数据',
          type: 'warning'
        });
      } else {
        let ids = [];
        this.multipleSelection.forEach(item => {
          ids.push(item.classificationId)
        })
        this.$confirm('此操作将改变知识点训练出题方式为随机出题, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            modifyIsRandom({idList: JSON.stringify(ids), flag: 0}).then(response => {
              console.log(response)
              if (response.code == 200) {
                this.$message.success("操作成功！");
                this.multipleSelection=[];
                this.getTrainingDetails(this.queryForm)
              }
            })
        })
      }
    },
    unifyTest() {
      console.log(this.multipleSelection)
      if (this.multipleSelection===[]||this.multipleSelection.length<1) {
        this.$message({
          message: '请至少先选择一条数据',
          type: 'warning'
        });
      } else {
        let ids = [];
        this.multipleSelection.forEach(item => {
          ids.push(item.classificationId)
        })
        this.$confirm('此操作将改变知识点训练出题方式为统一出题, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          modifyIsRandom({idList: JSON.stringify(ids), flag: 1,classId:this.classId}).then(response => {
            console.log(response)
            if (response.code == 200) {
              this.$message.success("操作成功！");
              this.multipleSelection=[];
              this.getTrainingDetails(this.queryForm)
            }
          })
        })
      }
    },
    handleClick(val) {
      console.log(val)
      let ids = []
      ids.push(val.classificationId)
      this.$confirm('此操作将改变知识点训练出题方式, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (val.isRandom == 0) {
          modifyIsRandom({idList: JSON.stringify(ids), flag: 1,classId:this.classId}).then(response => {
            console.log(response)
            if (response.code == 200) {
              this.$message.success("操作成功！");
              this.getTrainingDetails(this.queryForm)
            }

          })
        } else if (val.isRandom == 1) {
          modifyIsRandom({idList: JSON.stringify(ids), flag: 0,classId:this.classId}).then(response => {
            console.log(response)
            if (response.code == 200) {
              this.$message.success("操作成功！");
              this.getTrainingDetails(this.queryForm)
            }
          })
        }
      })
    },
    onSubmit() {
      this.getTrainingDetails(this.queryForm)
    },
    getTrainingDetails(params) {
      pageingQueryQuestionSetting(params).then(res => {
        console.log(res);
        if (res.code === 200) {
          this.tableDate = res.rows;
          this.pageNum = res.pageNum;
          this.pageSize = res.pageSize;
          this.total = res.total;
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.mainTop {
  height: 110px;
}

.tableContainer {
  height: calc(100% - 105px);
}
</style>

<template>
  <div class="mainHeight">
    <main-flexible-tree :data="treeData" :defaultProps="TreeDefaultProps" @handleNodeClick="handleNodeClick">
      <div slot="mainRight" class="mainHeight">
        <container-top class="mainTop">
          <el-form :inline="true" slot="ipt">
            <el-form-item label="关卡等级：">
              <el-input v-model="queryForm.levelName" placeholder="请输入关卡等级" clearable ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="onSubmit">搜索
              </el-button>
            </el-form-item>
          </el-form>
          <el-form :inline="true" slot="btnDrop">
            <el-form-item>
              <el-button size="mini" type="primary" @click="addTable">新增</el-button>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="updateTable">编辑</el-button>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="deleteLevels">删除</el-button>
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
            :data="tableData"
            border
            stripe
            height="100%"
            @selection-change="handleSelectionChange">
            <el-table-column
              type="selection"
              width="55">
            </el-table-column>
            <el-table-column label="序号" width="50px" type="index" align="center" :index="customIndex"/>
            <el-table-column
              prop="levelName"
              label="关卡等级"
              width="180">
              <template slot-scope="scope">
                <span>第{{ scope.row.levelName }}关</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="levelTime"
              label="限制用时"
              width="180">
            </el-table-column>
            <el-table-column
              prop="levelNumTopic"
              label="总题数"
              width="180">
            </el-table-column>
            <el-table-column
              prop="knowledgeList">
              <template slot="header">
                <div class="cellDiv">
                  <span>关联知识点</span><span>/</span> 占比
                </div>
              </template>
              <template slot-scope="scope">
                <div class="cellDiv" v-for="(listItem,listIndex) in scope.row.knowledgeList" :key="listIndex"
                     v-show="listItem.classificationMsg">
                  <span>{{ listItem.classificationMsg }}</span> <span>/</span> {{ listItem.percentage }}%
                </div>
              </template>
            </el-table-column>
          </el-table>
        </slot-table>
      </div>
    </main-flexible-tree>
    <!--表格新增弹框-->
    <el-dialog :visible.sync="addTableDialog"
               width="50%"
               class=" dialog-style dialog-basic"
               title="新增关卡">
      <dialog-add-form>
        <el-form ref="addForm" slot="form" :model="customsForm" :rules="formRules">
          <el-row>
            <el-col :span="12">
              <el-form-item label="关卡等级：" prop="levelName">
                <el-input-number :min="1" :max="9999" size="mini" v-model="customsForm.levelName"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="限制用时：" prop="levelTime">
                <el-input-number :min="1" :max="9999" size="mini" v-model="customsForm.levelTime"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="总题数：" prop="levelNumTopic">
                <el-input-number :min="1" :max="999" size="mini" v-model="customsForm.levelNumTopic"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <el-form class="cascaderForm" slot="form" :model="cascaderForm">
          <el-form-item label='关联知识点：'>
            <el-cascader
              size="mini"
              ref="cascaderAddr"
              @change="addHandleTypeChange"
              placeholder="请选择知识点"
              :props="defaultProps"
              :options="cascaderForm.options"
              filterable
              clearable></el-cascader>
          </el-form-item>
        </el-form>

        <el-form slot="form" class="knowledgeList" :model="customsForm"
                 v-for="(item,index) in customsForm.knowledgeList" :key="index" v-if="item.classificationMsg">
          <el-form-item label="知识点名称：">
            <el-input size="mini" :disabled="true" v-model="item.classificationMsg" style="width: 235px"></el-input>
          </el-form-item>
          <el-form-item label="占比：">
            <el-input-number style="width: 100px" :min="1" :max="100" size="mini"
                             v-model="item.percentage"></el-input-number>
          </el-form-item>
          <el-form-item style="margin-left: 10px">
            <el-button size="mini" @click.prevent="remove(index)">移除</el-button>
          </el-form-item>
        </el-form>
      </dialog-add-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="addTableDialog=false">取 消</el-button>
        <el-button size="mini" type="primary" @click="addTableSave">确 定</el-button>
      </span>
    </el-dialog>
    <!--表格编辑弹框-->
    <el-dialog :visible.sync="updateTableDialog"
               width="50%"
               class=" dialog-style dialog-basic"
               title="编辑关卡">
      <dialog-add-form>
        <el-form ref="updateForm" slot="form" :model="customsForm" prop="levelName" :rules="formRules">
          <el-row>
            <el-col :span="12">
              <el-form-item label="关卡等级：" prop="levelName">
                <el-input-number :min="1" :max="9999" size="mini" v-model="customsForm.levelName"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="限制用时：" prop="levelTime">
                <el-input-number :min="1" :max="9999" size="mini" v-model="customsForm.levelTime"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="总题数：" prop="levelNumTopic">
                <el-input-number :min="1" :max="999" size="mini" v-model="customsForm.levelNumTopic"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <el-form class="cascaderForm" slot="form" :model="cascaderForm">
          <el-form-item label='关联知识点：'>
            <el-cascader
              size="mini"
              ref="cascaderAddr"
              @change="addHandleTypeChange"
              placeholder="请选择知识点"
              :props="defaultProps"
              :options="cascaderForm.options"
              filterable
              clearable></el-cascader>
          </el-form-item>
        </el-form>
        <el-form slot="form" class="knowledgeList" :model="customsForm"
                 v-for="(item,index) in customsForm.knowledgeList" :key="index" v-if="item.classificationMsg">
          <el-form-item label="知识点名称：">
            <el-input size="mini" :disabled="true" style="width: 235px" v-model="item.classificationMsg"></el-input>
          </el-form-item>
          <el-form-item label="占比：">
            <el-input-number style="width: 100px" :min="1" :max="100" size="mini"
                             v-model="item.percentage"></el-input-number>
          </el-form-item>
          <el-form-item style="margin-left: 10px">
            <el-button size="mini" @click.prevent="remove(index)">移除</el-button>
          </el-form-item>
        </el-form>
      </dialog-add-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="updateTableDialog=false">取 消</el-button>
        <el-button size="mini" type="primary" @click="updateTableSave">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import ContainerTop from "@/components/public/MainBody/ContainerTop";
import SlotTable from "@/components/public/table/SlotTable";
import DialogAddForm from "@/components/public/DialogBody/DialogAddForm";
import {
  getLevelTableList,
  saveLevelTable,
  removeLevelTableById,
  getMaxLevelNum
} from "@/api/usually/checkBorrowApprove/browse";
import MainFlexibleTree from "@/components/public/MainBody/MainFlexibleTree";
import {getDeptTree, getDeptTreeByUserSchool} from "@/api/system/dept";
import {getDataByEparentCode} from '@/api/system/dict/newDict'
import {getKLCByParams, getTreeById, getListByPid} from '@/api/usually/knowledgemaintain/knowledge'
import {removeStudentsById} from "@/api/system/students";
import {prompt} from "@/components/windoprompt/prompt";


export default {
  name: "index",
  components: {MainFlexibleTree, DialogAddForm, SlotTable, ContainerTop},
  created() {
    this.leftDepTree()
    this.getLevel();
    getDataByEparentCode({dictType: 'classfiy', eparentCode: '-'}).then(response => {
      this.cascaderForm.options = response.data;
    });
    this.keyupSubmit();
  },
  data() {
    return {
      formRules: {
        levelName: [
          {required: true, message: '请输入关卡名', trigger: 'blur'},
        ],
        levelTime: [
          {required: true, message: '请输入限制用时', trigger: 'blur'},
        ],
        levelNumTopic: [
          {required: true, message: '请输入总题数', trigger: 'blur'},
        ],
      },
      defaultProps: {
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
                      // console.log(returnData);
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
      queryForm: {
        levelName: "",
        gradeId: "",
        nodeType: "",
      },
      treeData: [],
      TreeDefaultProps: {
        children: "children",
        label: "deptName",
      },
      // tabPosition: 'left',
      tableData: [],
      addTableDialog: false, //新增表格弹窗
      updateTableDialog: false,  //编辑表格弹窗
      customsForm: { //关卡
        levelName: '',
        levelTime: '',
        levelNumTopic: "",
        knowledgeList: [],
      },
      select: [],
      cascaderForm: {
        options: []
      },
      pageSize: 20,
      pageNum: 1,
      total: 1,
    }
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
    //自定义序号
    customIndex(index) {
      return (this.pageNum - 1) * (this.pageSize) + (index + 1);
    },
    deleteLevels() {
      if (this.select.length < 1) {
        this.$message.warning("请选择关卡！")
        return;
      }
      this.$confirm('是否确定删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let ids = "";
          for (let n in this.select) {
            ids = ids + this.select[n].levelId + ",";
          }
          removeLevelTableById({ids: ids}).then(response => {
            if (response.code === 200) {
              this.$message.success("删除成功")
            } else {
              this.$message.erroe("删除失败")
            }
            this.pageNum = 1;
            this.getLevel();
          })
        })
    },
    getLevel() {
      let pageNum = JSON.parse(this.pageNum);
      let pageSize = JSON.parse(this.pageSize);
      let param = {
        pageNum: pageNum,
        pageSize: pageSize,
        levelName: this.queryForm.levelName,
        gradeId: this.queryForm.gradeId,
      }
      //默认获取一年级所有关卡
      getLevelTableList(param).then(response => {
        if (response.code === 200) {
          this.tableData = response.rows   //tree树第一级节点
          this.pageNum = response.pageNum
          this.pageSize = response.pageSize
          this.total = response.total
        }
      })
    },
    leftDepTree() {
      getDeptTreeByUserSchool({showClass: false}).then(response => {
        if (response.code === 200) {
          this.treeData = response.data
        }
      })

    },
    handleNodeClick(val, node, component) {
      // console.log(val, node, component)
      // console.log(val.nodeType);
      if (node.level === 1) {
        this.queryForm.gradeId = val.children[0].id

      } else if (node.level === 2) {
        this.queryForm.gradeId = val.id
      }
      this.queryForm.nodeType = val.nodeType
      this.pageNum = 1;
      this.getLevel();
    },
    onSubmit() {
      this.pageNum = 1;
      this.getLevel();
    },
    addTable() {
      // console.log(this.queryForm.nodeType)
      if (this.queryForm.gradeId == null || this.queryForm.gradeId == "" || this.queryForm.nodeType != 1) {
        this.$message.warning("请选择左侧年级节点")
      } else {
        this.addTableDialog = true;
        getMaxLevelNum({gradeId: this.queryForm.gradeId}).then(reponse => {
          this.customsForm = { //关卡
            levelName: reponse.data + 1,
            levelTime: '',
            levelNumTopic: '',
            knowledgeList: [],
          }
        })
      }
    },
    addHandleTypeChange() {
      let checkNode = this.$refs.cascaderAddr.getCheckedNodes()[0];
      if (checkNode != null) {
        let index = checkNode.level - 1;
        let label = checkNode.pathLabels[index];
        for (let i = 0; i < this.customsForm.knowledgeList.length; i++) {
          if (this.customsForm.knowledgeList[i].classificationId == checkNode.value) {
            this.$message.warning("知识点已添加")
            return;
          }
        }
        let knowledge = {classificationId: checkNode.value, classificationMsg: label, percentage: '1'};
        this.customsForm.knowledgeList.push(knowledge);
      }
    },
    remove(index) {
      // var index = this.customsForm.knowledgeList.indexOf(item);
      if (index !== -1) {
        this.customsForm.knowledgeList.splice(index, 1)
      }
    },
    addTableSave() {
      this.$refs['addForm'].validate((valid) => {
        if (!valid) {
          return false;
        }
        if (this.customsForm.knowledgeList.length < 1) {
          this.$message.warning("请选择知识点")
          return;
        }
        let percentage = 0;
        for (let i = 0; i < this.customsForm.knowledgeList.length; i++) {
          let knowledge = this.customsForm.knowledgeList[i];
          percentage += parseInt(knowledge.percentage)
        }
        if (percentage != 100) {
          this.$message.warning("知识点总占比未达到百分百")
          return;
        }
        this.customsForm.gradeId = this.queryForm.gradeId;
        let data = JSON.parse(JSON.stringify(this.customsForm));
        saveLevelTable(data).then(response => {
          if (response.code == 200) {
            this.$message.success(response.msg)
            this.addTableDialog = false;
            this.pageNum = 1;
            this.getLevel();
          } else {
            this.$message.error(response.msg)
          }
        })
      })
    },
    updateTableSave() {
      this.$refs['updateForm'].validate((valid) => {
        if (!valid) {
          return false;
        }
        if (this.customsForm.knowledgeList.length < 1) {
          this.$message.warning("请选择知识点")
          return;
        }
        let percentage = 0;
        for (let i = 0; i < this.customsForm.knowledgeList.length; i++) {
          percentage += this.customsForm.knowledgeList[i].percentage
        }
        if (percentage != 100) {
          this.$message.warning("知识点总占比未达到百分百")
          return;
        }
        this.customsForm.gradeId = this.select[0].gradeNum;
        let data = JSON.parse(JSON.stringify(this.customsForm));
        saveLevelTable(data).then(response => {
          if (response.code == 200) {
            this.$message.success(response.msg)
            this.updateTableDialog = false;
            this.pageNum = 1;
            this.getLevel();
          } else {
            this.$message.error(response.msg)
          }
        })
      })
    },

    updateTable() {
      if (this.queryForm.gradeId == null || this.queryForm.gradeId == "" || this.queryForm.nodeType != 1) {
        this.$message.warning("请选择左侧年级节点")
      } else {
        if (this.select.length !== 1) {
          this.$message.warning("请选择一条数据")
        } else {
          this.updateTableDialog = true;
          this.customsForm = this.select[0];
          // console.log(this.select[0]);
          // console.log("this.select[0]");
        }
      }
    },
    handleSelectionChange(val) {
      this.select = val;
    },
    handleChange(pageSize, pageNum) {
      this.pageNum = pageNum;
      this.pageSize = pageSize;
      this.getLevel()
    }
  }
}
</script>

<style lang="scss" scoped>
.mainCenter {
  height: calc(100% - 107px);
  //margin-top: 10px;
  .cellDiv {
    color: red;
    width: 100%;
    display: flex;

    span:first-of-type {
      display: inline-block;
      width: 50%;
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
</style>

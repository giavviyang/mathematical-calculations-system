<template>
  <div class="app-wait">
    <el-row>
      <el-col :span="24" style="height: 100%;">
        <el-col :span="4" :style="leftStyleName" style="height: calc(100vh - 86px)">
          <div id="inputClear">
            <!-- 树结构 -->
            <el-input placeholder="请输入内容" v-if="treeShow" ref="left_input" v-model="filterText"
                      style="width:90%;padding:20px 0 0 20px" size="mini" clearable/>
          </div>
          <div class="tree tree-style">
            <el-scrollbar style="height: 100%;">
              <el-tree
                style="display: inline-block"
                node-key="id"
                empty-text=" "
                ref="tree4"
                @node-click="treeCleck"
                :data="data2"
                :props="defaultProps"
                icon-class="el-icon-folder-opened"
                :filter-node-method="filterNode"
                :highlight-current="true"/>
            </el-scrollbar>
          </div>
          <div class="shrinkageDiv" @click="divClick()">
            <div :class="picClassname"></div>
          </div>
        </el-col>

        <el-col :span="20" style="height: calc(100vh - 86px)" :style="rightStyleName">
          <el-row :gutter="10" ref="searchRow" style="margin: 0">
            <el-form
              ref="queryForm"
              class="search-form"
              :model="queryForm"
              :inline="true"
              style="background:rgba(204, 204, 204, 0.18)"
              @submit.native.prevent
            >
              <el-form-item label="学生姓名" @keyup.enter.native="search" prop="userName">
                <el-input v-model="queryForm.userName" size="mini" placeholder="请输入学生姓名" clearable
                          style="width: 150px"/>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini"
                           @click="search">搜索
                </el-button>
              </el-form-item>
            </el-form>

          </el-row>

          <el-col :span="24" ref="searchForm" style="padding: 10px 0;">
            <el-button type="primary" size="mini" icon="el-icon-plus"
                       @click="addStudent">新增
            </el-button>
            <el-button type="primary" size="mini" icon="el-icon-edit"
                       @click="upData">编辑
            </el-button>
            <el-button type="primary" size="mini" icon="el-icon-delete"
                       @click="handleDelete">删除
            </el-button>
            <!--              <el-button type="primary" size="mini" icon="el-icon-download" v-hasPermi="['parameter:students:export']" @click="exportExcel">导出</el-button>-->
            <el-button type="primary" size="mini" icon="el-icon-upload2"
                       @click="importExcel">导入
            </el-button>
            <el-button type="primary" size="mini" icon="el-icon-set-up"
                       @click="changeClass">班级调整
            </el-button>
            <el-button type="primary" size="mini" icon="el-icon-document-add"
                       @click="changeGraduate">毕业
            </el-button>
            <el-button type="primary" size="mini" icon="el-icon-document-checked"
                       @click="changeAlreadyGraduated">已毕业
            </el-button>
          </el-col>
          <el-table
            :default-sort="{ prop: 'date', order: 'descending' }"
            stripe
            border
            class="table-style"
            :height="tableHeight"
            empty-text=" "
            size="mini"
            tooltip-effect="light"
            ref="tableGrid"
            :header-cell-style="headClass"
            style="width: 100%"
            :data="tableData"
            @row-click="StepsListRowClick"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40"/>
            <el-table-column type="index" width="50" align="center" label="序号" show-overflow-tooltip
                             :index="customIndex"/>
            <el-table-column show-overflow-tooltip align="center" v-for="(item, index) in tableColomn"
                             :formatter="item.formatter" :key="index"
                             :v-if="item.isCol" :prop="item.prop" :label="item.label"/>
          </el-table>
          <div class="block" style="float: right;margin-top: 5px">
            <el-pagination
              :page-sizes="[20, 50, 100, 200]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
              :current-page="pageNum"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-col>
      </el-col>

      <!--  新增编辑页面  -->
      <el-dialog
        :title="title"
        width="50%"
        class=" dialog-style dialog-basic"
        :destroy-on-close="true"
        :visible.sync="addOrEditW">

        <div style="">
          <el-row>
            <el-col :span="24" style="width:100%">
              <el-form ref="studentForm" :rules="rules" :model="studentForm" style="margin-left: 10%;margin-right: 10%"
                       label-width="120px">
                <el-col :span="12">
                  <el-form-item prop="studentName" label="学生姓名">
                    <el-input size="mini" v-model="studentForm.studentName" placeholder="请输入节点名称" style="width:100%"/>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item prop="studentNumber" label="学号">
                    <el-input size="mini" v-model="studentForm.studentNumber" placeholder="请输入学号"
                              style="width:100%" @blur="onInputBlur" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="班级" prop="classRoomId"   style="width:100%" >
                    <el-select size="mini" v-model="studentForm.className" placeholder="请选择班级" ref="selectReport"  style="width:100%">
                    <el-option :value="studentForm.classRoomId" :label="studentForm.className"
                                 style="height:200px;width:200px;overflow:auto;background-color:#fff">
                        <el-tree
                          :data="data2"
                          :props="defaultProps"
                          @node-click="handleDeptSelectClick"
                        ></el-tree>

                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="24">
                  <el-form-item prop="remark" label="备注">
                    <el-input size="mini" v-model="studentForm.remark" placeholder="请输入备注" type="textarea"
                              style="width:100%"/>
                  </el-form-item>
                </el-col>
              </el-form>
            </el-col>
          </el-row>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="addOrEditW=false">取 消</el-button>
          <el-button size="mini" type="primary" @click="saveStudentForm('studentForm')">保 存</el-button>
        </div>
      </el-dialog>
      <!-- 班级调整 -->
      <el-dialog title="班级调整"  :visible.sync="dialogVisible"
                 class=" dialog-style tree-style" width="30%">
        <div style="height:300px" class="dialog-tree">
          <el-scrollbar style="height: 300px;">
            <el-tree
              ref="tree3"
              node-key="id"
              @node-click="treeCleck2"
              :data="data2"
              :props="defaultProps"
              icon-class="el-icon-folder-opened"
              :highlight-current="true"
              :filter-node-method="filterNode"
            />
          </el-scrollbar>
        </div>
        <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="saveClassChange">保 存</el-button>

      </span>
      </el-dialog>
      <!-- 导入excel -->
      <el-dialog
        v-loading="uploadLoading"
        element-loading-text="上传中，请稍等"
        title="导入学生信息"
        :visible.sync="excelWindows"
        :append-to-body="true"
        width="30%"
        class="dialog-style"
        :before-close="function() {
        fileList=[];
        fileName='';
        excelWindows=false
      }"
      >
        <div>
          <div style="margin-left: 5px;" slot="tip" class="el-upload-list__item-name">{{ fileName }}</div>
          <div style="float: right; margin-left: 5px;" slot="tip" class="el-upload__tip">请上传xls或xlsx文件</div>
        </div>
        <el-upload
          ref="upload"
          name="part"
          multiple
          accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel "
          class="upload-demo"
          style="display: inline"
          :show-file-list="false"
          :auto-upload="false"
          :on-change="changeFile"
          :on-success="uploadSuccess"
          :on-error="uploadError"
          :file-list="fileList"
          :action="importExcelURL">
          <el-button size="mini" type="primary">选择文件</el-button>
        </el-upload>

        <el-button style="margin-left: 10px;" size="mini" type="success" @click="submitUpload">
          <i class="el-icon-upload el-icon--right"/>上传文件
        </el-button>
      </el-dialog>
      <el-dialog title="已毕业学生信息列表" :visible.sync="outerVisible" class="dialog-style graduate">
        <el-form
          class="search-form"
          :inline="true"
          @submit.native.prevent>
          <el-form-item label="学生姓名" @keyup.enter.native="searchGraduate" prop="userName">
            <el-input v-model="graduateUserName" size="mini" placeholder="请输入学生姓名" clearable
                      style="width: 150px"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini"
                       @click="searchGraduate">搜索
            </el-button>
          </el-form-item>
        </el-form>
        <slot-table
          :pageSize="graduatePageSize"
          :pageNum="graduatePageNum"
          :total="graduateTotal"
          @handleChange="graduateHandleChange">
          <el-table
            border
            stripe
            slot="table"
            :data="graduateTableData"
            style="width: 100%"
            height="100%">
            <el-table-column
              type="index"
              label="序号"
              width="50"/>
            <el-table-column
              label="姓名"
              prop="studentName"/>
            <el-table-column
              label="学号"
              prop="studentNumber"/>
            <el-table-column
              label="备注"
              prop="remark"/>
            <el-table-column label="操作" align="center" width="100">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="changeGraduateClass(scope.row)">班级调整</el-button>
              </template>
            </el-table-column>
          </el-table>
        </slot-table>
        <el-dialog
          width="30%"
          title="已毕业学生班级调整"
          :visible.sync="innerVisible"
          append-to-body
          class="dialog-style">
          <el-tree
            ref="tree3"
            node-key="id"
            @node-click="treeCleck2"
            :data="data2"
            :props="defaultProps"
            icon-class="el-icon-folder-opened"
            :highlight-current="true"
            :filter-node-method="filterNode"/>
          <div slot="footer" class="dialog-footer">
            <el-button @click="innerVisible = false" size="mini">取 消</el-button>
            <el-button type="primary" @click="saveChangeGraduateClass" size="mini">保存</el-button>
          </div>
        </el-dialog>
        <div slot="footer" class="dialog-footer">
          <el-button @click="outerVisible = false" size="mini">取 消</el-button>
          <!--          <el-button type="primary" @click="innerVisible = true" size="mini">打开内层 Dialog</el-button>-->
        </div>
      </el-dialog>
    </el-row>
  </div>
</template>
<script>
import {
  listDept, getDept, addDept, updateDept, getListDept, delDept, orderSave,
  deleteDeptByIds, querySysDeptByLike, dealStatus, getDeptTree, getDeptTreeByUserSchool
} from '@/api/system/dept'
import {
  queryStudentAndClass,
  addStudents,
  modifyStudentsById,
  removeStudentsById,
  changeClass,
  queryStudentsNumber,
  studentGraduation,
  pagingQueryStudentGraduation
} from '@/api/system/students'
import {prompt} from '@/components/windoprompt/prompt'
import axios from "axios";
import SlotTable from "@/components/public/table/SlotTable";

const baseURL = process.env.VUE_APP_BASE_API;
export default {
  components: {SlotTable},
  data() {
    var checkStudentNumber = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('学号不能为空'));
      }
      var reg = /^[0-9]*$/;
      if (!reg.test(value)) {
        callback(new Error('学号为纯数字'));
      }
      if (value.toString().length < 6) {
        callback(new Error('学号不能小于6位数'));
      }
    };
    return {
      outerVisible: false,
      innerVisible: false,
      graduateUserName: '',
      graduatePageSize: 20,
      graduatePageNum: 1,
      graduateTotal: 1,
      graduateTableData: [],
      graduateClassRoomId: '',
      graduateStuRoomId: '',
      checkDept: '',
      currentNode: '',
      dialogVisible: false,
      importExcelURL: baseURL + '/students/importExcelData',
      fileList: [],
      fileName: "",
      excelWindows: false,
      OrderCheckData: [],
      treeHeight: "height:500px",
      pageParam: {
        pageNum: 1,
        pageSize: 20
      },
      tableHeight: "73%",
      rules: {
        studentName: [
          {required: true, message: '请输入学生姓名', trigger: 'blur'},
          {min: 0, max: 18, message: '内容不得超过18个字符', trigger: 'blur'}
        ],
        studentNumber: [

          {required: true, message: '请输入学号', trigger: 'blur'},
          {min: 6, message: '内容不得少于6个字符', trigger: 'blur'},
        ],
        classRoomId: [
          {required: true, message: '请选择班级', trigger: 'blur'},
        ],
        remark: [
          {min: 0, max: 200, message: '内容不得超过200个字符', trigger: 'blur'}
        ]

      },
      searchForm: "searchForm",
      searchRowHeight: '',
      // 总页数
      total: 0,
      tableData1: [],
      pageNum: 1,
      pageSize: 20,
      loading: true,
      uploadLoading: false,
      addOrEditW: false,
      checkTreeData: {},
      title: "新增学生信息",
      studentForm: {},
      ipt: '',
      flag: false,
      // 全选
      addFormVisible: false,
      filterText: "",
      classRoomId: "",
      addForm: {
        className: '',
        leader: '',
        phone: '',
        email: '',
        status: 0,
      },
      updateForm: '',
      queryForm: {
        userName: '',
      },
      col: [],
      tableColomn: [
        {label: '学生姓名', prop: 'studentName', isCol: true},
        {label: '学号', prop: 'studentNumber', isCol: true},
        {label: '年级', prop: 'gradeName', isCol: true},
        {label: '班级', prop: 'className', isCol: true},
        {label: '备注', prop: 'remark', isCol: true},
      ],
      tableData: [],
      deleteCheck: [],
      gridCheck: [],
      data2: [],
      defaultProps: {
        children: "children",
        label: "deptName",
      },
      treeShow: true,
      rightStyle: {
        width: '84%',  padding: ' 10px', height: 'calc(100vh - 86px)'
      },
      leftStyle: {
        width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      leftStyleName: {
        width: '16%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      rightStyleName: {
        width: '84%', padding: ' 10px', height: 'calc(100vh - 86px)'
      },
      leftStyle1: {
        width: '1%', height: 'calc(100vh - 86px)', 'border-right': '1px solid #cccccc', position: 'relative'
      },
      rightStyle1: {
        width: '99%',  padding: ' 10px', height: 'calc(100vh - 86px)'
      },
      picClassname: "shrinkageLeft",
      leftTree1: true,
    }
  },
  mounted() {
    this.initForm();
    this.initLeftTreeData()
    this.keyupSubmit();
    // 额外加49的像素 因为没有搜索区
    this.$nextTick(() => {
      this.searchRowHeight = this.$refs[this.searchForm].$el.offsetHeight
      this.tableHeight = ((document.body.clientHeight - (90 + 100 + this.searchRowHeight))) + "px"
    })
    window.onresize = () => {
      return (() => {
        this.searchRowHeight = this.$refs[this.searchForm].$el.offsetHeight
        this.tableHeight = ((document.body.clientHeight - (90 + 100 + this.searchRowHeight))) + "px"
      })();
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree4.filter(val);
    },
    tableData: {
      handler(newName, oldName) {
        let _this = this;
        _this.$nextTick(() => {
          _this.$refs.tableGrid == null ? "" : _this.$refs.tableGrid.doLayout();
          _this.$refs.tableGrid1 == null ? "" : _this.$refs.tableGrid1.doLayout();

        })
      },
      deep: true,
      immediate: true
    },
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
    saveClassChange() {
      // console.log(this.gridCheck, this.checkDept)
      let ids = "";
      for (let i = 0; i < this.gridCheck.length; i++) {
        ids += this.gridCheck[i].studentId + ",";
      }
      let params = {
        ids: ids,
        classId: this.checkDept.id
      }
      if (this.checkDept.nodeType !== '2') {
        this.$message({
          message: '请选择班级',
          type: 'warning'
        });
      } else {
        if (this.gridCheck[0].classRoomId === this.checkDept.id) {
          this.$message({
            message: '请选择不同班级',
            type: 'warning'
          });
        } else {
          changeClass(params).then(reponse => {
            if (reponse.code == 200) {
              this.$message.success("调整成功")
              this.pageNum = 1
              this.getTableList();

            } else {
              this.$message.error("调整失败")
            }
            this.checkDept = "";
            this.dialogVisible = false;
          })
        }
      }
    },
    changeClass() {
      if (this.gridCheck.length < 1) {
        this.$message({
          type: "warning",
          message: "请勾选数据"
        })
        return;
      }
      this.dialogVisible = true;
    },
    changeGraduate() {
      if (!this.currentNode) {
        this.$message({
          type: "warning",
          message: "请先选择需要毕业的年级"
        })
      } else if (this.currentNode.level === 2 && this.currentNode.data.deptPym !== 'lnj') {
        this.$message({
          type: "warning",
          message: "请先选择正确的毕业年级"
        })
      } else if (this.currentNode.level === 2 && this.currentNode.data.deptPym === 'lnj') {
        this.$message({
          type: "warning",
          message: "请先选择需要毕业的班级"
        })
      } else if (this.currentNode.level === 3 && this.currentNode.parent.data.deptPym !== 'lnj') {
        this.$message({
          type: "warning",
          message: "请先选择正确的毕业年级"
        })
      } else if (this.currentNode.level === 3 && this.currentNode.parent.data.deptPym === 'lnj') {
        if (this.gridCheck.length === 0) {
          this.$message({
            type: "warning",
            message: "请先选择需要毕业的学生"
          })
        } else {
          this.$confirm('是否确定毕业?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            let ids = [];
            for (let i = 0; i < this.gridCheck.length; i++) {
              ids.push(this.gridCheck[i].studentId)
            }
            let params = {
              studentIds: JSON.stringify(ids),
              classId: this.currentNode.parent.parent.data.id
            }
            studentGraduation(params).then(res => {
              if (res.code === 200) {
                this.getTableList();
              }
            })
          })
        }
      }
    },
    changeAlreadyGraduated() {
      if (!this.currentNode) {
        this.$message({
          message: '请先选择需要查询已毕业学生的学校',
          type: 'warning'
        });
      } else {
        this.outerVisible = true;
        this.getGraduate(this.graduateUserName, this.graduateClassRoomId, 0, 20);
      }
    },
    searchGraduate() {
      this.getGraduate(this.graduateUserName, this.graduateClassRoomId, 0, 20);
    },
    getGraduate(studentName, classRoomId, pageNum, pageSize) {
      pagingQueryStudentGraduation({studentName, classRoomId, pageNum, pageSize}).then(res => {
        if (res.code === 200) {
          this.graduateTableData = res.rows;
          this.graduatePageNum = res.pageNum;
          this.graduatePageSize = res.pageSize;
          this.graduateTotal = res.total;
        }
      })
    },
    graduateHandleChange(pageSize, pageNum) {
      this.graduatePageNum = pageNum;
      this.graduatePageSize = pageSize;
      this.getGraduate(this.graduateUserName, this.graduateClassRoomId, pageNum, pageSize);
    },
    changeGraduateClass(row) {
      this.graduateStuRoomId = row.studentId;
      this.innerVisible = true;
      this.checkDept = "";
    },
    saveChangeGraduateClass() {
      // console.log(this.graduateStuRoomId)
      let params = {
        ids: this.graduateStuRoomId,
        classId: this.checkDept.id
      }
      if (this.checkDept.nodeType !== '2') {
        this.$message({
          message: '请选择班级',
          type: 'warning'
        });
      } else {
        changeClass(params).then(reponse => {
          if (reponse.code == 200) {
            this.$message.success("调整成功")
            this.pageNum = 1;
            this.checkDept = "";
            this.innerVisible = false;
            this.getTableList();
            this.getGraduate(this.graduateUserName, this.graduateClassRoomId, this.graduatePageNum, this.graduatePageSize);
            this.$refs.tree3.setCheckedKeys([]);
          } else {
            this.$message.error("调整失败")
          }
          this.checkDept = "";
          this.innerVisible = false;
        })
      }
    },
    onInputBlur() {
      var reg = /^[0-9]*$/;
      if (!reg.test(this.studentForm.studentNumber)) {
        this.$message({
          message: '学号必须为纯数值',
          type: 'warning'
        });
      }else {
        if (this.title == "编辑学生信息") {
          if (this.studentForm.studentNumber !== this.ipt) {
            axios({
              url: baseURL + '/students/queryStudentsNumber',
              method: 'get',
              params: {
                studentNumber: this.studentForm.studentNumber
              }
            }).then(res => {
              if (res.data.code === 200) {
                this.flag = true;
              }
            }).catch(err => {
              this.$message({
                message: '此学号已存在',
                type: 'warning'
              });
              this.flag = false
            })
          } else {
            this.flag = true
          }
        } else {
          axios({
            url: baseURL + '/students/queryStudentsNumber',
            method: 'get',
            params: {
              studentNumber: this.studentForm.studentNumber
            }
          }).then(res => {
            if (res.data.code === 200) {
              this.flag = true;
            }
          }).catch(err => {
            this.$message({
              message: '此学号已存在',
              type: 'warning'
            });
            this.flag = false
          })
        }
      }
    },
    //上传
    submitUpload(event) {
      if (this.fileList.length == 0) {
        console.log('1111')
        this.$message({
          type: "warning",
          message: "请选择选择文件后点击上传文件"
        })
        return
      }else {
        console.log('222')
        this.uploadLoading = true;
        event.preventDefault();
        this.$refs.upload.submit()
      }
    },
    // 上传成功后
    uploadSuccess(response) {
      console.log('111')
      if (response.code === 200) {
        this.$message.success(response.msg)
      } else {
        this.$message.error("上传失败")
      }
      this.excelWindows = false;
      this.uploadLoading = false;
    },
    uploadError(err){
      console.log('222')
      if(!err.code||err.code!=200){
        this.$message.error("请上传正确文件格式");
        this.uploadLoading = false;
        this.excelWindows = false;
        this.uploadLoading = false;
      }
    },
    // 上传前校验类型及大小
    changeFile(file, fileList) {
      if (fileList.length > 0) {
        this.fileList = [fileList[fileList.length - 1]]  // 这一步，展示最后一次选择的文件
      }
      let testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
      this.fileName = file.name
      const extension = testmsg === 'xls'
      const extension2 = testmsg === 'xlsx'
      if (!extension && !extension2) {
        this.$message({
          message: '请选择xls或xlsx文件进行上传',
          type: 'warning'
        })
      }
      this.dialogFormVisible = false;
      return extension || extension2
    },
    /**
     * 文件选择
     * @param val
     */
    fileChange(val) {
      if (val.target.value === '') {
        this.fileName = "未选择文件"
      } else {
        this.fileName = val.target.value
      }
    },
    importExcel() {
      this.excelWindows = true;
      this.fileName='';
    },
    handleDeptSelectClick: function (node) {
      if (node.nodeType == 2) {
        this.studentForm.classRoomId = node.id
        this.studentForm.className = node.deptName
        this.$refs.selectReport.blur();
      }
    },

    search() {
      this.pageNum = 1;
      this.getTableList();
    },
    //自定义序号
    customIndex(index) {
      return (this.pageNum - 1) * (this.pageSize) + (index + 1);
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
    // 分页大小
    handleSizeChange(val) {
      this.pageSize = val
      this.getTableList()
    },
    // 分页监听
    handleCurrentChange(val) {
      this.pageNum = val
      this.getTableList()
    },

    initLeftTreeData() {
      // getDeptTree({deptType: 1}).then(response => {
      getDeptTreeByUserSchool().then(response => {
        // console.log('123', response)
        this.data2 = response.data
        this.classRoomId = this.data2[0].id;
        this.getTableList();
      })

    },
    initForm() {
      this.studentForm = {
        studentName: "",
        studentNumber:'',
        remark: "",
        classRoomId: "",
      }
    },
    //新增部门
    addStudent() {
      this.initForm();
      this.title = '新增学生信息';
      this.addOrEditW = true;
    },
    getTableList() {
      let param = {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        studentName: this.queryForm.userName,
        classId: this.classRoomId,
      }
      queryStudentAndClass(param).then(response => {
        this.total = response.total;
        this.tableData = response.rows;
        // console.log("1111", this.tableData)
      })
    },
    //编辑学生信息
    upData() {
      if (this.gridCheck.length != 1) {
        this.$message({
          type: "warning",
          message: "请勾选一条数据"
        })
      } else {
        this.studentForm = this.gridCheck[0];
        this.title = '编辑学生信息';
        this.addOrEditW = true;
        // console.log(this.studentForm)
        this.ipt = this.studentForm.studentNumber;

      }

    },


    //新增或编辑学生信息
    saveStudentForm(form) {

      let _this = this;
      console.log(_this.studentForm)
      this.onInputBlur();
      this.$refs[form].validate((valid) => {
        console.log('111')
        if (valid) {
          console.log('222')
          _this.studentForm.studentNumber=_this.studentForm.studentNumber+'';
          if (this.title == "编辑学生信息") {
            if (_this.flag) {
              modifyStudentsById(_this.studentForm)
                .then(function (response) {
                  prompt("保存", 0)
                  _this.pageNum = 1;
                  _this.initForm()
                  _this.getTableList()
                  _this.initLeftTreeData()
                  _this.addOrEditW = false;
                  _this.ipt = '';
                  _this.flag = false;
                })
            }
          } else {
            if (_this.flag) {
              addStudents(_this.studentForm)
                .then(function (response) {
                  prompt("新增", 0)
                  _this.addOrEditW = false;
                  // _this.ipt = '';
                  _this.pageNum = 1;
                  _this.initForm()
                  _this.getTableList()
                  _this.initLeftTreeData();
                  _this.flag = false;
                })
            } else {
              this.$message({
                message: '此学号已存在',
                type: 'warning'
              });
            }

          }
        } else {
          return false;
        }
      })

    },

    //点击左侧树节点
    treeCleck(val, node) {
      this.checkTreeData = val;
      this.pageNum = 1;
      this.classRoomId = val.id;
      this.currentNode = node;
      if (this.currentNode.level === 1) {
        this.graduateClassRoomId = this.currentNode.data.id
      } else if (this.currentNode.level === 2) {
        this.graduateClassRoomId = this.currentNode.parent.data.id
      } else if (this.currentNode.level === 3) {
        this.graduateClassRoomId = this.currentNode.parent.parent.data.id
      }
      this.getTableList();
    },
    //点击左侧树节点
    treeCleck2(val) {
      // console.log(this.checkDept)
      this.checkDept = val;
    },

    filterNode(value, data) {
      if (!value) return true;
      return data.deptName.indexOf(value) !== -1;
    },
    StepsListRowClick(row) {
      this.$refs.tableGrid.clearSelection()
      if (this.gridCheck.length === 0) {
        this.$refs.tableGrid.toggleRowSelection(row)
        return;
      }
      this.$refs.tableGrid.toggleRowSelection(row)
    },
    StepsListRowClick1(row) {
      this.$refs.tableGrid1.clearSelection()
      if (this.tableData1.length === 0) {
        this.$refs.tableGrid1.toggleRowSelection(row)
        return;
      }
      this.$refs.tableGrid1.toggleRowSelection(row)
    },
    // 复选框事件
    handleSelectionChange(val) {
      this.gridCheck = val
      this.deleteCheck = val.map(item => item.id)
    },
    // 复选框事件
    handleSelectionChange1(val) {
      this.OrderCheckData = val

    },
    headClass() {
      return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
    },
    // 多选删除
    handleDelete() {
      let _this = this;
      if (this.gridCheck.length == 0) {
        this.$message({
          type: "warning",
          message: "请勾选数据"
        })
        return;
      }
      this.$confirm('是否确定删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let ids = "";
          for (let n in this.gridCheck) {
            ids = ids + this.gridCheck[n].studentId + ",";
          }
          removeStudentsById({ids: ids})
            .then(function (response) {
              prompt("删除", 0)
              _this.pageNum = 1;
              _this.getTableList()
            })
        })
    },
  }
}
</script>
<style lang="scss" scoped>

#inputClear {
  /deep/ .el-input__suffix {
    top: 11px;
  }
}

.el-dialog__body {
  height: 500px;
  display: block;
}

.app-wait {
  /*overflow: auto;*/
  height: calc(100vh - 86px);;
}

.el-input__inner {
  width: 300px;
}

.el-form-item__content {
  width: 300px;
}

.el-input {
  width: 300px;
}

.inputW {
  width: 300px;
}
.graduate {
  /deep/ .el-dialog {
    .el-dialog__body {
      padding-bottom: 0;
      height: 80vh;
      .tableContainer {
        margin: 0 10px;
        height: calc(100% - 60px);
        width: calc(100% - 20px);
        .table {
          height: calc(100% - 40px);
        }
      }
    }
  }

}

.block {
  float: right;
}

//设置统一表头
/deep/ .el-table tbody tr:hover > td {
  background-color: #ecf5ff !important
}

/deep/ .el-table__body td {
  padding: 0;
  height: 40px;
}

.el-scrollbar {
  height: 100%;
}

/deep/ .el-scrollbar .el-scrollbar__wrap {
  overflow-x: hidden;
}

/deep/ .el-tree > .el-tree-node {
  min-width: 100%;
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
  cursor: pointer;
  right: 0;
  bottom: 0;
  margin: auto;
  top: 0;
  height: 200px;
  width: 8px;
  border-radius: 20px;
  position: absolute;
}

// 公用树结构样式
.tree-style {
  /deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
    background: rgba(204, 204, 204, 0.18);
  }
}

.tree {
  width: 100%;
  height: calc(100vh - 135px);
  // overflow: auto;
  padding: 20px;
  /*border-right: 1px solid #e2e3e6;*/
}

.search-form {
  width: 100%;
  /*background:rgba(204, 204, 204, 0.18);*/
  padding: 10px;

  display: flex;

  /deep/ .el-form-item {
    margin-bottom: 0 !important;
  }
}

.fileTextInput {
  width: 100%;

  /deep/ .el-input__inner {
    padding: 0px;
    border: 0; // 去除未选中状态边框
    outline: none; // 去除选中状态边框
    background-color: rgba(0, 0, 0, 0); // 透明背景
  }
}
</style>

<template>
  <el-col :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px);overflow: auto;">
    <el-row :gutter="10" class="mb8" style="padding: 10px;margin: 0">
      <el-form
        v-if="showElectronic"
        ref="queryParams"
        class="transition"
        :model="queryParams"
        :inline="true"
        label-width="68px"
        style="background:rgba(204, 204, 204, 0.18);padding: 15px;margin-right: 20px;margin-bottom: 10px;""
      >
        <el-form-item style="margin-top: 10px;margin-bottom: 10px" label="单位名称" prop="deptName">
          <el-input
            v-model="queryParams.deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            style="width: 240px"
          />
        </el-form-item>
        <el-form-item style="margin-top: 10px;margin-bottom: 10px" label="干部名称" prop="a0101">
          <el-input
            v-model="queryParams.a0101"
            placeholder="请输入干部名称"
            clearable
            size="small"
            style="width: 240px"
          />
        </el-form-item>
        <el-form-item style="margin-top: 10px;margin-bottom: 10px">
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleQueryElectronic">搜索
          </el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQueryElectronic('queryParams')">重置
          </el-button>
        </el-form-item>
      </el-form>
      <el-col :span="1.5" style="margin-bottom: 10px;">
        <el-button type="primary" icon="el-icon-download" size="small" @click="handleExportElectronic">导出
        </el-button>
      </el-col>
      <el-col :span="1.5" style="margin-bottom: 10px;">
        <el-button type="primary" icon="el-icon-download" size="small" @click="handlePrintElectronic">打印
        </el-button>
      </el-col>
      <el-dropdown style="float: right; margin-right: 30px;">
        <el-button icon="el-icon-menu" circle size="small" />
        <el-dropdown-menu style="padding: 10px">
          <el-checkbox-group
            v-for="(item, index) in ElectronicColumn"
            :key="index"
            v-model="ElectronicColumn[index].isCol"
            :kye="index"
          >
            <el-checkbox>{{ item.label }}</el-checkbox>
          </el-checkbox-group>
        </el-dropdown-menu>
      </el-dropdown>
      <el-col :span="1.5" style="float: right;margin-bottom: 10px;">
        <el-button
          type="primary"
          icon="el-icon-search"
          circle
          size="small"
          @click="showElectronic = !showElectronic"
        />
      </el-col>
      <el-table
        v-loading="loading"
        :data="tableData"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        border
        style="width: 100%;"
      >
        <el-table-column align="center" v-for="(item , index) in ElectronicColumn" v-if="item.isCol" :key="index" :prop="item.prop" :label="item.label" />
        <el-table-column align="center" fixed="right" label="操作" width="80">
          <template slot-scope="scope">
            <el-button type="text" @click="handleClick(scope.row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination
          :page-sizes="[20, 50, 100, 200]"
          :page-size="20"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
      <vxe-modal v-model="detailsWindow" title="详情" width="1200" height="750" resize remember>

        <div style="padding: 20px;width: 10%;height: 100%;float: left;">
          <el-tree :data="personnelData" :props="defaultProps" @node-click="LeftTree" />
        </div>
        <div style="float: left;width: 90%;min-height: 100%;border-left: 1px solid #CCCCCC;">
          <el-form ref="personalForm" :inline="true" :model="personalForm" label-width="80px" style="padding: 10px">
            <!--  <el-form-item label="姓名" prop="a0101">
              <el-input v-model="personalForm.a0101" class="inputW" :disabled="true" />
            </el-form-item>
            <el-form-item label="单位" prop="deptName">
              <el-input v-model="personalForm.deptName" class="inputW" :disabled="true" />
            </el-form-item>
            <el-form-item label="职务" prop="duty">
              <el-input v-model="personalForm.duty" class="inputW" :disabled="true" />
            </el-form-item>
            <el-form-item label="政治面貌" prop="politcy">
              <el-input v-model="personalForm.politcy" class="inputW" :disabled="true" />
            </el-form-item>
            <el-form-item label="批准人" prop="e01z801">
              <el-input v-model="personalForm.e01z801" class="inputW" :disabled="true" placeholder="请输入批准人名称" />
            </el-form-item>
            <el-form-item label="申请理由" prop="e01z821">
              <el-input v-model="personalForm.e01z821" class="inputW" :disabled="true" placeholder="请输入申请理由" />
            </el-form-item> -->
            <el-form-item label="申请时间" prop="e01z804">
              <el-date-picker v-model="personalForm.e01z804" class="inputW" type="date" placeholder="选择开始时间" />
            </el-form-item>
            <el-form-item label="结束时间" prop="e01z805">
              <el-col :span="11" style="padding-left:0 ;">
                <el-date-picker v-model="personalForm.e01z805" class="inputW" type="date" placeholder="选择结束时间" />
              </el-col>
              <el-col class="line" :span="2" style="text-align: center">-</el-col>
              <el-col :span="11">
                <el-time-picker v-model="personalForm.e01z805" class="inputW" placeholder="选择结束时间" />
              </el-col>
            </el-form-item>
            <el-form-item label="档案类型" prop="archivesType">
              <el-radio-group v-model="personalForm.archivesType" @change="archivesTypeClick">
                <el-radio v-model="personalForm.archivesType" :label="1">电子档案</el-radio>
                <el-radio v-model="personalForm.archivesType" :label="2">实体档案</el-radio>
                <el-radio v-model="personalForm.archivesType" :label="3">借阅档案</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="申请权限" prop="priType">
              <el-checkbox-group v-model="personalForm.priType">
                <el-col v-if="isElectronic" :span="24">
                  <el-checkbox key="1" label="1" name="priType" :disabled="disabled">查阅</el-checkbox>
                  <el-checkbox key="10" label="10" name="priType" :disabled="disabled">摘抄</el-checkbox>
                  <el-checkbox key="100" label="100" name="priType" :disabled="disabled">打印</el-checkbox>
                </el-col>
                <el-col v-if="isEntity" :span="24">
                  <el-checkbox key="2" label="2" name="priType" :disabled="disabled">查阅</el-checkbox>
                  <el-checkbox key="20" label="20" name="priType" :disabled="disabled">摘抄</el-checkbox>
                  <el-checkbox key="200" label="200" name="priType" :disabled="disabled">复印</el-checkbox>
                </el-col>
                <el-col v-if="isBorrowing" :span="24">
                  <el-checkbox key="3" label="3" name="priType" :disabled="disabled">借阅</el-checkbox>
                </el-col>
              </el-checkbox-group>
            </el-form-item>
            <!-- <el-form-item label="备注" prop="e01z831">
              <el-input v-model="personalForm.e01z831" :disabled="true" type="textarea" />
            </el-form-item> -->
          </el-form>
          <div style="height: 500px;overflow: auto;border-top: 1px solid #CCCCCC;">
            <el-tree ref="permissionsTree" :data="permissionsTree" :check-strictly="false" show-checkbox default-expand-all node-key="id" highlight-current :props="defaultProps1" />
          </div>
          <div class="TreeSave">
            <div style="float: right;">
              <el-button @click="noAgreed">不 同 意</el-button>
              <el-button type="primary" @click="permissionsSave">同 意</el-button>
            </div>
          </div>
        </div>
      </vxe-modal>
    </el-row>
  </el-col>
</template>
<script>
import {
  conditionSearchArchives
} from '@/api/usual/usage/borrow'
import { getNoApproval, noPassApprove, personalToAudit, accessPermissions, approveDate } from '@/api/todolist/utilize'
export default {
  data() {
    return {
      loading: true,
      detailsWindow: false,
      pageSize: 20,
      pageNum: 1,
      total: 0,
      showElectronic: false,
      queryParams: {
        a0101: '',
        deptName: '',
        pageSize: 20,
        pageNum: 1,
        type: '1'
      },
      ElectronicColumn: [
        { label: '姓名', isCol: true, prop: 'userName' },
        { label: '单位名称', isCol: true, prop: 'e01z824a' },
        { label: '职务', isCol: true, prop: 'duty' },
        { label: '政治面貌', isCol: true, prop: 'politcy' },
        { label: '用户类型', isCol: true, prop: 'target' },
        { label: '开始时间', isCol: true, prop: 'approvTime' },
        { label: '备注', isCol: true, prop: 'e01z831' }
      ],
      tableData: [
        { userName: '尹龙鑫', e01z824a: '测试部门', duty: '测试', politcy: '教授', e01z831: '测试' }
      ],
      defaultProps: {
        label: 'a0101',
        id: 'a01Id'
      },
      personnelData: [
        { label: '尹龙鑫', id: 1 },
        { label: '尹龙鑫', id: 2 },
        { label: '尹龙鑫', id: 3 }
      ],
      personalForm: {
        a0101: '',
        duty: '',
        deptName: '',
        politcy: '',
        e01z801: '',
        e01z804: '',
        e01z805: '',
        e01z821: '',
        archivesType: 1,
        priType: [],
        e01z831: ''
      },
      isElectronic: true,
      disabled: false,
      isEntity: false,
      isBorrowing: false,
      rules: {
        e01z804: [
          { type: 'date', required: true, message: '请输入查档时间', trigger: 'blur' }
        ],
        e01z805: [
          { type: 'date', required: true, message: '请输入结束时间', trigger: 'blur' }
        ]
      },
      batchId: '',
      permissionsTree: [],
      defaultProps1: {
        label: 'e01z111a',
        id: 'e01z104',
        disabled: false
      }
    }
  },
  mounted() {
    this.queryToAudit()
  },
  methods: {
    // 查询电子档案 上部搜索按钮
    handleQueryElectronic() {
      const _this = this
      const queryParams = {
        deptName: this.queryParams.deptName,
        // a01Id: this.queryParams.a01Id,
        a0101: this.queryParams.a0101,
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        type: this.archivesType
      }
      conditionSearchArchives(queryParams)
        .then(response => {
          if (response.code === 200) {
            _this.ElectronicData = response.rows
            _this.total = response.total
            _this.loading = false
          }
        })
    },
    // 查询电子档案 上部重置按钮
    resetQueryElectronic(queryParams) {
      this.$refs[queryParams].resetFields()
      this.archivesaFunction()
    },
    // 查待审核数据
    queryToAudit() {
      const _this = this
      const Param = {
        pageSize: this.pageSize,
        pageNum: this.pageNum
      }
      getNoApproval(Param)
        .then(response => {
          if (response.code === 200) {
            _this.tableData = response.rows
            for (let i = 0; i < _this.tableData.length; i++) {
              _this.tableData[i].approvTime = _this.format(_this.tableData[i].approvTime,"yyyy-MM-dd hh:mm:ss");
              if (_this.tableData[i].target === "1") {
                _this.tableData[i].target = "系统内用户";
              } else if (_this.tableData[i].target === "2") {
                _this.tableData[i].target = "系统外用户";
              }
            }
            console.log("tableData")
            console.log(_this.tableData)
            _this.total = response.total
            _this.loading = false
          }
        })
    },

    format(datetime,fmt) {
      if (parseInt(datetime)==datetime) {
        if (datetime.length==10) {
          datetime=parseInt(datetime)*1000;
        } else if(datetime.length==13) {
          datetime=parseInt(datetime);
        }
      }
      datetime=new Date(datetime);
      var o = {
        "M+" : datetime.getMonth()+1,                 //月份
        "d+" : datetime.getDate(),                    //日
        "h+" : datetime.getHours(),                   //小时
        "m+" : datetime.getMinutes(),                 //分
        "s+" : datetime.getSeconds(),                 //秒
        "q+" : Math.floor((datetime.getMonth()+3)/3), //季度
        "S"  : datetime.getMilliseconds()             //毫秒
      };
      if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
      for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
      return fmt;
    },
    // 导出
    handleExportElectronic() {},
    // 打印
    handlePrintElectronic() {},
    // 详情
    handleClick(val) {
      console.log(val)
      this.batchId = val.id
      const _this = this
      this.detailsWindow = true
      personalToAudit({ batchId: val.id })
        .then(response => {
          if (response.code === 200) {
            _this.personnelData = response.data
          }
        })
    },
    // 详情弹窗 左侧树 节点事件
    LeftTree(val) {
      console.log(val)
      if (val.priType === '1' || val.priType === '111' || val.priType === '101' || val.priType === '11') {
        if (val.priType === '1') {
          val.priType = ['1']
        } else if (val.priType === '111') {
          val.priType = ['1', '10', '100']
        } else if (val.priType === '101') {
          val.priType = ['1', '100']
        } else if (val.priType === '11') {
          val.priType = ['1', '10']
        }
        val.archivesType = 1
      } else if (val.priType === '2' || val.priType === '222' || val.priType === '202' || val.priType === '22') {
        val.archivesType = 2
        if (val.priType === '2') {
          val.priType = ['2']
        } else if (val.priType === '222') {
          val.priType = ['2', '20', '200']
        } else if (val.priType === '202') {
          val.priType = ['2', '200']
        } else if (val.priType === '22') {
          val.priType = ['2', '20']
        }
      } else if (val.priType === '3') {
        val.archivesType = 3
        val.priType = ['3']
      }
      this.personalForm = val
      accessPermissions({ a01Id: val.a01Id })
        .then(response => {
          if (response.code === 200) {
            this.permissionsTree = response.data
            if (val.e01z111a === '整本') {
              val.e01z1Id = 'zb'
              this.$refs.permissionsTree.setCheckedKeys([1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'ZZZZ'])
            } else {
              this.$refs.permissionsTree.setCheckedKeys([val.e01z1Id])
            }
          }
        })
    },
    handleSizeChange() {},
    handleCurrentChange() {},
    archivesTypeClick(val) {
      console.log(val)
      if (val === 1) {
        this.isElectronic = true
        this.isEntity = false
        this.isBorrowing = false
        this.personalForm.priType = ['1']
      } else if (val === 2) {
        this.isElectronic = false
        this.isEntity = true
        this.isBorrowing = false
        this.personalForm.priType = ['2']
      } else if (val === 3) {
        this.isElectronic = false
        this.isEntity = false
        this.isBorrowing = true
        this.personalForm.priType = ['3']
      }
    },
    // 同意
    permissionsSave() {
      let addFormType = this.personalForm.priType
      console.log(this.personalForm)
      const SaveData = this.$refs.permissionsTree.getCheckedNodes()
      for (let i = 0; i < addFormType.length; i++) {
        if (addFormType.length === 1) {
          addFormType = Number(addFormType[0])
        } else if (addFormType.length === 2) {
          addFormType = Number(addFormType[0]) + Number(addFormType[1])
        } else if (addFormType.length === 3) {
          addFormType = Number(addFormType[0]) + Number(addFormType[1]) + Number(addFormType[2])
        }
      }
      for (let i = 0; i < SaveData.length; i++) {
        SaveData[i].a01Id = this.personalForm.a01Id
        SaveData[i].a0101 = this.personalForm.a0101
        SaveData[i].archivesType = this.personalForm.archivesType
        SaveData[i].deptId = this.personalForm.deptId
        SaveData[i].deptName = this.personalForm.deptName
        SaveData[i].duty = this.personalForm.duty
        SaveData[i].e01z804 = this.personalForm.e01z804
        SaveData[i].e01z805 = this.personalForm.e01z805
        SaveData[i].e01zcId = this.personalForm.e01zcId
        SaveData[i].priType = addFormType.toString()
        SaveData[i].e01z1Id = SaveData[i].id
        SaveData[i].flag = 3
        SaveData[i].id = this.personalForm.id
      }
      console.log(SaveData)
      if (this.personalForm.a0101 === '') {
      } else {
        approveDate({ batchId: this.batchId, data: JSON.stringify(SaveData) })
          .then(response => {
            if (response.code === 200) {
              // this.personalToAudit(this.batchId)
                this.loading = true
            }
          })
      }
    },
    noAgreed() {
      console.log(this.personalForm)
      if (this.personalForm.a0101 === '') {
      } else {
        noPassApprove({ flag: 2, id: this.personalForm.id })
          .then(response => {
            if (response.code === 200) {
                this.loading = true
            }
          })
      }
    }
  }
}
</script>
<style scoped>
  .el-tabs--left /deep/ .el-tabs__header.is-left {
    height: calc(100vh - 84px);
  }
  .block {
    float: right;
    padding: 10px;
  }
  .vxe-modal--wrapper{
    z-index: 9999 !important;
  }
  .vxe-modal--wrapper /deep/.vxe-modal--body{
    padding: 0 !important;
  }
  .inputW {
    width: 200px;
  }
  .TreeSave{
    padding: 10px;
  }
</style>

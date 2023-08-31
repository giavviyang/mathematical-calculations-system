<template>
  <div class="app-receive">
    <el-col :span="24" :xs="24" style="margin: 0;padding: 0;height: calc(100vh - 90px);overflow: auto;">
      <el-row>
        <!-- 左侧头像与人员名称 -->
        <el-col :span="3" v-if="A01Record.a01Id!=null">
          <div style="padding: 40px 0 0 40px">
            <el-image style="width: 140px; height: 180px; margin-right: 15%" :src="a01ImgSrc">
            </el-image>
            <span style="padding: 0 0 0 40px;font-size: 20px;" class="title">{{ this.A01Record.a0101 }}</span>
          </div>
        </el-col>
        <el-col :span="A01Record.a01Id==null?24:21">
          <div
            style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">
            第一步：请填写接收信息
          </div>
          <div ref="customRef" id="app" style="width:100%">
            <el-form ref="form" :rules="archiveRules" :model="form" label-width="110px" style="width:80%;padding: 20px 0 0 0;margin: 0 auto;padding-left: 3%;padding-right: 3%">
              <el-col :span="8">
                <el-form-item label="接收时间" prop="e01z201">
                  <el-date-picker v-model="form.e01z201" type="date" placeholder="选择接收时间" style="width: 100%;" size="mini"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="来件单位" prop="e01z204a">
                  <el-input v-model="form.e01z204a" style="width:100%" placeholder="请输入来件单位" size="mini"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="材料类别" prop="e01z101">
                  <el-select v-model="form.e01z101" style="width:100%" clearable filterable placeholder="请选择材料类别" size="mini">
                    <el-option v-for="item in e01z101s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="材料名称" prop="e01z111a">
                  <el-input v-model="form.e01z111a" style="width:100%" placeholder="请输入材料名称" size="mini"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="接收人" prop="e01z207">
                  <el-input v-model="form.e01z207" style="width:100%" placeholder="请输入接收人" size="mini"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="材料制成日期" prop="e01z117">
                  <el-date-picker v-model="form.e01z117" style="width:100%" type="date" placeholder="选择材料制成日期" size="mini"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="页数" prop="e01z114">
                  <el-input-number v-model="form.e01z114" style="width:100%" controls-position="right" size="mini" :min="1" :max="999"></el-input-number>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="备注">
                  <el-input v-model="form.e01z121" style="width:100%" size="mini" type="textarea"/>
                </el-form-item>
              </el-col>
            </el-form>
          </div>
          <div style="padding-top: 10px;width: 75%;margin: 0 auto">
            <el-col :span="A01Record.a01Id==null?24:21">
              <el-button style="float: right;margin-right: 15px;width:80px" size="mini" @click="resetForm">重置表单
              </el-button>
            </el-col>
          </div>
        </el-col>
        <!-- 左侧筛选部门与人员表格 -->
        <el-col :span="24" v-if="A01Record.a01Id==null">
          <div style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">第二步：请选择接收材料的干部档案</div>
          <div class="table-left" style="width: 75%;margin: 0 auto;">
            <div style="width: 100%;padding:20px 0 0 0">
              <el-form :inline="true" v-model="formInline" style="width: 100%;">
                <el-form-item label="干部状态" class="input-w">
                  <el-select v-model="a01state" @change="checkA01State" filterable class="inputW" placeholder="请选择干部状态" size="mini">
                    <el-option v-for="item in states" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
                  </el-select>
                </el-form-item>
                <el-form-item class="input-w">
                  <el-input v-model="formInline.deptName" @keyup.enter.native="search(1)" placeholder="请输入部门" size="mini"/>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="search(1)" size="mini">部门搜索</el-button>
                </el-form-item>
                <el-form-item class="input-w">
                  <el-input v-model="formInline.a0101"  @keyup.enter.native="search(2)"  placeholder="请输入人员" size="mini"/>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="search(2)" size="mini">干部搜索</el-button>
                </el-form-item>
              </el-form>
            </div>
            <!-- 左边表格数据 -->
            <el-table
              :data="leftData"
              empty-text=" "
              border
              class="table-style"
              :header-cell-style="headClass"
              style="width: 100%;"
              height="222px"
              @cell-dblclick="deptClick"
              @selection-change="leftChange"
              ref="searchTableData"
              @row-click="handleGridChange"
              tooltip-effect="light"
              >
              <el-table-column type="selection" width="40" key="1" v-if="dpbutton"/>
              <el-table-column style="width: 1%"  type="index" align="center" label="序号" />
              <el-table-column prop="a0101" show-overflow-tooltip v-if="isa01 == 2" key="2" label="干部名称" align="center" />
              <el-table-column prop="number" show-overflow-tooltip v-if="isa01 == 2&&false" key="3" label="干部编号" align="center" />
              <el-table-column prop="deptName" :show-overflow-tooltip="true" v-if="isa01 == 2" key="4" label="所属部门" align="center" />
              <el-table-column prop="deptName" :show-overflow-tooltip="true" v-if="isa01 == 1" key="5" label="部门名称" align="center" style="width: 50%"/>
              <el-table-column prop="parentId" show-overflow-tooltip v-if="isa01 == 1" key="6" label="上级部门" align="center" style="width: 50%"/>
              <el-table-column prop="a0104" show-overflow-tooltip v-if="isa01 == 2" key="7" label="性别" align="center" :formatter="getFullName" />
              <el-table-column prop="a0107" show-overflow-tooltip v-if="isa01 == 2" key="8"  label="出生日期" align="center" :formatter="getFullName" />
              <el-table-column prop="state" show-overflow-tooltip v-if="isa01 == 2" key="9" label="干部状态" align="center" :formatter="getFullName" />
            </el-table>
          </div>
          <div style="padding-top: 10px;width: 75%;margin: 0 auto">
            <el-button type="primary" style="float: right;margin-right: 10px;width:80px" size="mini" @click="createGridData('form')">添加</el-button>
          </div>
        </el-col>
        <el-col :span="24">
          <div style="margin: 0 auto;width:80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc">第三步：确认材料信息并生成任务列表</div>
          <div style="padding:10px 0 0 0">
            <el-table
              :default-sort="{ prop: 'date', order: 'descending' }"
              stripe
              class="table-style"
              border
              empty-text=" "
              style="margin: 0 auto;width: 75%"
              ref='archiveData'
              :data="archiveData"
              tooltip-effect="light"
              :header-cell-style="headClass"
              height="222px"
              @selection-change="handleSelectionChange"
              >
              <el-table-column align="center" style="width: 1%" type="index" label="序号"/>
              <el-table-column align="center" v-if="true" label="姓名" prop="a0101" style="10%"/>
              <el-table-column v-if="true" label="材料分类" :show-overflow-tooltip="true" prop="e01z101" :formatter="getFullName" style="30%">
                <!-- <template slot-scope="scope">
                   <el-form  :model="scope.row">
                      <el-form-item >
                        <el-select style="width:100%;font-size: 13px" v-model="scope.row.e01z101" clearable filterable class="inputW" placeholder="请选择材料类别" :disabled="scope.row.disabled">
                          <el-option v-for="item in e01z101s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                        </el-select>
                    </el-form-item>
                   </el-form>
                  </template> -->
              </el-table-column>
              <el-table-column v-if="true" :show-overflow-tooltip="true" label="材料名称" prop="e01z111a" style="30%">
                <!-- <template slot-scope="scope">
                  <el-form :model="scope.row" >
                    <el-form-item  prop="e01z111a">
                      <el-input style="width:100%;font-size: 12px" v-model="scope.row.e01z111a" :disabled="scope.row.disabled "/>
                  </el-form-item>
                 </el-form>
                </template> -->
              </el-table-column>
              <el-table-column align="center" v-if="true" label="制成时间" prop="e01z117" :formatter="getFullName" style="30%">
                <!-- <template slot-scope="scope">
                    <el-form :model="scope.row" >
                      <el-form-item  prop="e01z117">
                        <el-date-picker style="width:100%;font-size: 12px"  v-model="scope.row.e01z117" :disabled="scope.row.disabled " type="date" placeholder="选择材料制成日期" />
                    </el-form-item>
                   </el-form>bianji
                  </template> -->
              </el-table-column>
              <el-table-column align="center" v-if="true" label="页数" prop="e01z114" style="30%">
                <!-- <template slot-scope="scope">
                    <el-form :model="scope.row" >
                      <el-form-item  prop="e01z114">
                        <el-input-number v-model="scope.row.e01z114" style="width:100%;font-size: 12px" :controls="false"  :disabled="scope.row.disabled " :min="0" :max="100" />

                    </el-form-item>
                   </el-form>
                  </template> -->
              </el-table-column>
              <el-table-column align="center" v-if="true" show-overflow-tooltip label="备注" prop="e01z121" style="30%">
                <!-- <template slot-scope="scope">
                    <el-form :model="scope.row" >
                      <el-form-item  prop="e01z121">
                        <el-input style="width:100%;font-size: 12px" width="50px" v-model="scope.row.e01z121" :disabled="scope.row.disabled "/>
                    </el-form-item>
                   </el-form>
                  </template> -->
              </el-table-column>
              <el-table-column align="center" label="操作" width="100">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="electronicView( scope.row)">编辑</el-button>
                  <el-button type="text" size="small" @click="electronicFeedback(scope.$index)">移除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div style="padding: 20px 0 0 0;width: 75%;margin: 0 auto">
            <el-button type="primary" style="float: right;margin-right: 10px;width:80px" size="mini" @click="createE01z1AndCreateTask">创建材料</el-button>
          </div>
        </el-col>
      </el-row>
    </el-col>
    <!-- 材料信息编辑页面 -->
    <el-dialog title="编辑信息"
    :visible.sync="dialogFormVisible"
    :close-on-click-modal="false"
    :append-to-body="true"
    width="55%"
    class="dialog-style" >
      <el-form ref="editorForm" :model="editorForm" label-width="150px" :rules="archiveRules" :inline="true">
              <el-form-item label="姓名" prop="a0101">
                <el-input v-model="editorForm.a0101" placeholder="请输入姓名" size="mini" :disabled="true"/>
              </el-form-item>
              <el-form-item label="材料分类" prop="e01z101">
                <el-select v-model="editorForm.e01z101" clearable filterable class="inputW" placeholder="请选择材料类别" size="mini">
                  <el-option v-for="item in e01z101s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
                </el-select>
              </el-form-item>
              <el-form-item label="材料名称" prop="e01z111a">
                <el-input v-model="editorForm.e01z111a" placeholder="请输入材料名称" size="mini"/>
              </el-form-item>
              <el-form-item label="制成时间" prop="e01z117">
                <el-date-picker v-model="editorForm.e01z117" class="inputW" type="date" placeholder="请选择材料制成日期" size="mini"/>
              </el-form-item>
              <el-form-item label="页数" prop="e01z114">
                <el-input-number v-model="editorForm.e01z114" class="inputW" controls-position="right" size="mini" :min="1" :max="100"></el-input-number>
              </el-form-item>
              <el-form-item label="备注">
                <el-input v-model="editorForm.e01z121" type="textarea" class="inputW" :autosize="{minRows:1, maxRows: 2}"/>
              </el-form-item>
            </el-form>
      <div slot="footer" class="dialog-footer" >
        <el-button @click="dialogFormVisible = false" size="mini">取 消</el-button>
        <el-button type="primary" @click="onCommitEdit('editorForm')" size="mini">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { insertA01, insertE01z1sAndTask, queryA01ById, queryArchiveTree, queryDeptTree, queryDictionaryByType, queryDictionaryByTypes, queryE01z1TreeGrid, queryLoginUser, queryPingYin, queryRoomArch, queryRoomList,addE01z1Task } from '@/api/usual/receive'
  import { queryA01List } from '@/api/usual/transfer'
  import XEUtils from 'xe-utils'
  // 下拉树
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  const cityOptions = ['梁健华', '测试部门']
  export default {
    components: { Treeselect },
    name: '材料接收',
    data() {
      return {
        fit: '',
        a01ImgSrc: '',
        // 案卷质量
        e01z527Options: [
          {
            value: '0',
            label: '不合格'
          }, {
            value: '1',
            label: '合格'
          }, {
            value: '2',
            label: '优秀'
          }
        ],
        data2: [],
        dialogVisible2: false,
        //材料录入新增编辑校验
        validRules: {
          e01z111a: [
            { required: true, message: '材料名成必须填写' },
            { min: 2, max: 50, message: '输入内容在 2 到 50 个字符' }
          ],
          e01z114: [
            { required: true, message: '页数必须填写' }
          ],
          n: [
            { pattern: /^[0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3}$/, message: '格式不正确' }
          ],
          y: [
            { pattern: /^\d+$|^\d+[.]?\d+$/, message: '只能输入数字' }
          ],
          r: [
            { pattern: /^\d+$|^\d+[.]?\d+$/, message: '只能输入数字' }
          ]
        },
        tableColumn: [
          { type: 'checkbox', width: 50 },
          { width: 50, title: '', align: 'center', treeNode: true },
          // { field: 'e01z104', title: '序号',align:"center" ,width: 50},
          {
            field: 'e01z111a',
            style: 'font-weight:bold;',
            title: '材料名称',
            align: 'left',
            editRender: { name: 'input' },
            width: '550px',
            showOverflow: true,
            slots: {
              // 使用 JSX 渲染函数
              default: ({ row }, h) => {
                if (row.remark == 'catalog') {
                  return [
                    h('span', {
                        style: {
                          'font-weight': 'bold'
                        }
                      }
                      , row.e01z111a)
                  ]
                } else {
                  return [
                    h('span', {}
                      , row.e01z111a)
                  ]
                }

              }
            }
          },
          {
            title: '材料制成时间', align: 'center',
            children: [
              { field: 'n', title: '年', align: 'center', editRender: { name: 'input' } },
              { field: 'y', title: '月', align: 'center', editRender: { name: 'input' } },
              { field: 'r', title: '日', align: 'center', editRender: { name: 'input' } }
            ]
          },
          { field: 'e01z114', title: '页数', align: 'center', editRender: { name: 'input' } }
          ,

          { field: 'e01z121', title: '备注', width: '400px', align: 'center', editRender: { name: 'input' } },
          {
            field: 'operation', title: '操作', align: 'center',
            slots: {
              // 对应自定义插槽的名称
              default: 'name_default'
            }
          }
        ],
        tableToolbar: {
          buttons: [
            { code: 'insert_e01z1', name: '新增' },
            { code: 'delete_selections', name: '移除数据' },
            { code: 'saves', name: '保存材料' },
            { code: 'inportExcel', name: '导入excel' },
            { code: 'adjustment', name: '调整分类' }

          ]
        },
        dialogFormVisible: false,
        //干部名称
        A01Record: {
          a01Id: this.$route.params.id,
          a0101: this.$route.params.a0101
        },
        //人员id
        a01Id: '',
        beforeQueryData: [],
        dpbutton: false,
        isa01: 2,
        deleteHtml: '移除',
        updateHtml: '编辑',
        e01z1Show: false,
        fileList: [],
        excelWindows: false,
        //左侧人员选择数据
        leftPersonCheck: [],
        options: [],
        // 材料 档案接收显示
        receiveShow: true,
        // 录入材料 显示
        entryShow: false,
        personnelText: '',
        a01state: '0',
        cadresText: '',
        searchText: '',
        leftData: [],
        leftChek: [],
        activeName: 'fileReceive',
        // 部门树数据
        deptOptions: [],
        archiveData: [],
        // 库房位置树数据
        e01z541Options: [],
        a0104s: [],
        a0104s1: [],
        a0117s: [],
        a0183s: [],
        a0141s: [],
        educations: [],
        a0151s: [],
        dutys: [],
        states: [],
        grades: [],
        rooms: [],
        tree2Checked: {},
        // 系统字典表下拉框数据
        e01z101s: [],
        receiveForm: {
          a0101: '',
          a0102: '',
          a0104: '',
          a0107: '',
          a0117: '',
          a0183: '',
          a0184: '',
          a0141: '',
          a0134: '',
          a0144: '',
          education: '',
          a0151: '',
          duty: '',
          grade: '',
          e01z501: new Date(),
          e01z541: null,
          e01z517: '',
          e01z507a: '',
          deptId: null,
          reason: '',
          e01z511: '',
          e01z514: '',
          e01z521: '',
          e01z524: '',
          e01z527: '',
          e01z531: '',
          e01z534: '',
          e01z537: '',
          e01z544: ''

        },
        //系统字典表数据，用于formatter
        sysDictionaryMap: {},

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
        value: '',
        checkAll: false,
        checkedCities: [],
        cities: cityOptions,
        isIndeterminate: true,
        queryParams: {
          e01zc24a: '',
          e01z804: ''
        },
        ElectronicColumn: {
          e01z111a: true,
          n: true,
          y: true,
          r: true,
          e01z114: true,
          e01z121: true
        },
        disabled: true,
        roomId: '',
        fileName: '',
        tree2Datas: [],
        //材料接收编辑缓存数据
        receiveRData: '',

        showElectronic: false,
        queryData: [],
        a01Search: {
          state: '0'
        },

        form: {
          e01z201: new Date(),
          e01z117: '',
          e01z204a: '',
          e01z207: '',
          e01z101: '',
          e01z111a: '',
          e01z114: '',
          e01z121: ''
        },
        editorForm: {
          e01z201: '',
          e01z117: '',
          e01z204a: '',
          e01z207: '',
          e01z101: '',
          e01z111a: '',
          e01z114: '',
          e01z121: ''
        },
        tabPosition: 'left',
        // 验证 必填项
        rules: {
          a0101: [
            { required: true, message: '请输入姓名', trigger: 'blur' },
            { min: 2, max: 18, message: '输入内容在 2 到 18 个字符', trigger: 'blur' }
          ],
          a0102: [
            { required: true, message: '请输入姓名简拼', trigger: 'blur' },
            { min: 1, max: 6, message: '输入内容在 1 到 6 个字符', trigger: 'blur' }

          ],
          a0104: [
            { required: true, message: '请选择性别', trigger: 'blur' }
          ],
          a0107: [
            { required: true, message: '请选择日期', trigger: 'blur' }
          ],
          a0117: [
            { required: true, message: '请选择民族', trigger: 'blur' }
          ],
          a0184: [
            { min: 1, max: 18, message: '输入内容在 1 到 18 个字符', trigger: 'blur' }
          ],
          e01z501: [
            { required: true, message: '请选择接收日期', trigger: 'blur' }
          ],
          e01z517: [
            { required: true, message: '请选择经办人', trigger: 'blur' }
          ],
          e01z507a: [
            { required: true, message: '请选择来件单位', trigger: 'blur' }
          ],
          deptId: [
            { required: true, message: '请选择转入部门', trigger: 'blur' }
          ]
        },
        formInline: {},
        archiveRules: {
          e01z204a:[
            { min: 1, max: 60, message: '输入内容在 1 到 60 个字符', trigger: 'blur' }
          ],
          e01z101: [
            { required: true, message: '请选择材料类别', trigger: 'blur' }
          ],
          e01z111a: [
            { required: true, message: '请输入材料名称', trigger: 'blur' },
            {  max: 40, message: '输入内容不得超过40个字符' }

          ],
          e01z117:[
            { required: true, message: '材料制成时间必须填写',trigger: 'blur' },
          ],
          e01z114: [
            { required: true, message: '请输入页数', trigger: 'blur' }
          ],
          e01z207:[
            { min: 2, max: 18, message: '输入内容在 2 到 18 个字符', trigger: 'blur' }
          ],
          e01z121:[
            { min: 1, max: 50, message: '输入内容在 1 到 50 个字符', trigger: 'blur' }
          ]
        },
        deptNshow: false

      }
    },
    watch: {

      leftData: {
        handler(newName, oldName) {
          let _this = this
          _this.$nextTick(() => {
            _this.$refs.searchTableData.doLayout()
          })
        },
        deep: true,
        immediate: true
      },
      archiveData: {
        handler(newName, oldName) {
          let _this = this
          _this.$nextTick(() => {
            _this.$refs.archiveData.doLayout()
          })
        },
        deep: true,
        immediate: true
      }

    },
    mounted() {
      this.getComboxDatas()
      this.getDeptTree()
      this.activeNameFun()
      this.getRoomList()
      this.getQueryData()
    },
    created() {
      this.initData()
      this.getA01Data()
      this.getLoginUser()
    },
    methods: {
      // 公用表格表头样式
      headClass() {
        return 'background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px'
      },
      //干部选择
      checkA01State(val) {
        if (this.deptNshow) {
          let tableData = []
          for (let i in this.beforeQueryData) {
            if (this.beforeQueryData[i].state == val) {
              tableData.push(this.beforeQueryData[i])
            }
          }
          this.leftData = tableData
        }

      },
      //获取当前登录用户
      getLoginUser() {
        queryLoginUser().then(response => {
          if (response.code == 200) {
            let user = response.data
            this.receiveForm.e01z517 = user.userName
            this.form.e01z207 = user.userName
          }
        })
      },
      //上移
      showDetailEventUp(row, column) {
        let xTree = this.$refs.xTree
        let childrenData = []

        let matchObj = XEUtils.findTree(this.queryData, item => item === row, this.treeConfig)

        if (matchObj.index != 0) {
          this.queryData.forEach(datas => {
            if (datas.children != null && datas.children.length > 0) {
              let childrenDatas = XEUtils.find(datas.children, item => item === row)
              if (childrenDatas != null) {
                childrenData = datas
              }
              if (datas.id == 4 || datas.id == 9) {
                datas.children.forEach(childrensa => {
                  let childrenDatasp = XEUtils.find(childrensa.children, item => item === row)
                  if (childrenDatasp != null) {
                    childrenData = childrensa
                  }
                })
              }
            }
          })
          let destorydatas = JSON.parse(JSON.stringify(childrenData.children[matchObj.index - 1]))
          childrenData.children[matchObj.index - 1] = childrenData.children[matchObj.index]
          childrenData.children[matchObj.index] = destorydatas
          xTree.refreshData()
        }

      },
      //下移
      showDetailEventDown(row, column) {
        let xTree = this.$refs.xTree
        let childrenData = []
        let matchObj = XEUtils.findTree(this.queryData, item => item === row, this.treeConfig)
        this.queryData.forEach(datas => {
          if (datas.children != null && datas.children.length > 0) {
            let childrenDatas = XEUtils.find(datas.children, item => item === row)
            if (childrenDatas != null) {
              childrenData = datas
            }
            if (datas.id == 4 || datas.id == 9) {
              datas.children.forEach(childrensa => {
                let childrenDatasp = XEUtils.find(childrensa.children, item => item === row)
                if (childrenDatasp != null) {
                  childrenData = childrensa
                }
              })
            }
          }
        })
        if (childrenData.children.length != matchObj.index + 1) {
          let destorydatas = JSON.parse(JSON.stringify(childrenData.children[matchObj.index + 1]))
          childrenData.children[matchObj.index + 1] = childrenData.children[matchObj.index]
          childrenData.children[matchObj.index] = destorydatas
          xTree.refreshData()
        }
      },
      handleNodeClick(val) {
        this.tree2Checked = val

      },
      //调整分类
      changeOtherTreeNode() {
        if (this.tree2Checked.id != null) {
          let xTree = this.$refs.xTree
          let e01z1s = this.tree2Datas

          e01z1s.forEach(row => {

            let matchObj = XEUtils.findTree(this.queryData, item => item === row, this.treeConfig)
            if (matchObj) {
              let { items, index } = matchObj
              // 从树节点中移除
              let restRow = items.splice(index, 1)[0]
            }
          })
          xTree.refreshData()

          xTree.createRow().then(newRow => {
            // 插入到第一行
            this.queryData.forEach(datas => {
              if (datas.id == this.tree2Checked.id) {
                if (datas.children == null) {
                  datas.children = []
                }
                e01z1s.forEach(newDatas => {
                  datas.children.push(newDatas)
                })
              }
            })
            xTree.refreshData().then(() => xTree.setActiveRow(newRow))
          })

          this.dialogVisible2 = false

        } else {
          this.$message({
            message: '请选中目录后点击确定',
            type: 'warning'
          })
        }

      },
      //树结构
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      //部门搜索栏单击事件
      handleGridChange(row) {
        if(this.isa01 == 1){
          return;
        }
        if (row) {
          this.$refs.searchTableData.toggleRowSelection(row)
        } else {
          this.$refs.searchTableData.clearSelection()
        }
      },
      //初始化获取A01内容
      getA01Data() {
        let a01Id = this.$route.params.trans_a01Id
        if (a01Id != null && a01Id != ':trans_a01Id') {
          queryA01ById({ a01Id: a01Id }).then(response => {
            if (response.code == 200) {
              let a01 = response.data
              for (let item in a01) {
                this.receiveForm[item] = a01[item]
              }
            }
          })
        }
      },
      //双击编辑
      activeRowMethod({ row, rowIndex }) {
        if (row.remark == 'catalog') {
          return false
        }
        return true
      },
      //材料录入点击按钮
      toolbarButtonClickEvent({ code }, event) {
        switch (code) {
          case 'adjustment':
            let xTreess = this.$refs.xTree
            let removeRecordss = xTreess.getCheckboxRecords()
            let catalogDatas = []
            for (let index in removeRecordss) {
              if (removeRecordss[index].remark != 'catalog') {
                catalogDatas.push(removeRecordss[index])
              }
            }
            if (catalogDatas.length == 0) {
              this.$message({
                message: '请勾选材料数据后进行调整分类',
                type: 'warning'
              })
              return
            }
            this.tree2Datas = catalogDatas
            this.dialogVisible2 = true
            break
          case 'inportExcel':
            this.excelWindows = true
            //this.$XModal.alert(code)
            break
          case 'saves':

            let e01z1GridDatas = this.queryData
            let returnData = []
            for (let index in e01z1GridDatas) {
              this.getNCatalogData(e01z1GridDatas[index], returnData)
            }

            if (returnData.length != 0) {
              this.$refs.xTree.validate(returnData, valid => {
                if (valid) {
                  var returnValues = {
                    gridValues: JSON.stringify(returnData)
                  }
                  insertE01z1sAndTask({ jsonStr: returnValues }).then(response => {
                    if (response.code == 200) {
                      this.$confirm('保存成功是否生成数字化任务?', '提示', {
                        confirmButtonText: '生成数字化任务',
                        cancelButtonText: '继续档案接收',
                        type: 'warning'
                      })
                        .then(() => {
                          insertE01z1sAndTask({ jsonStr: response.data }).then(responses => {
                            if (responses.code == 200) {
                              this.$confirm('生成成功是否跳转到数字化任务页面', '提示', {
                                confirmButtonText: '跳转页面',
                                cancelButtonText: '继续档案接收',
                                type: 'warning'
                              }).then(() => {
                                //跳转到数字化页面

                              }).catch(() => {
                                this.receiveShow = true
                                // 录入材料 显示
                                this.entryShow = false
                                this.a01Id = ''
                              })
                            }
                          })
                        })
                        .catch(() => {
                          this.receiveShow = true
                          // 录入材料 显示
                          this.entryShow = false
                          this.a01Id = ''
                        })
                    } else {
                      this.$message({
                        message: response.msg,
                        type: 'error'
                      })
                    }
                  })
                } else {
                  this.$message({
                    message: '校验失败',
                    type: 'error'
                  })
                }
              })

            } else {
              this.$message({
                message: '请添加档案材料后进行创建',
                type: 'warning'
              })
            }
            break
          case 'delete_selections':
            let xTree = this.$refs.xTree
            let removeRecords = xTree.getCheckboxRecords()
            let catalogData = []
            for (let index in removeRecords) {
              if (removeRecords[index].remark != 'catalog') {
                catalogData.push(removeRecords[index])
              }
            }
            if (catalogData.length == 0) {
              this.$message({
                message: '请勾选材料数据后进行删除',
                type: 'warning'
              })
              return
            }
            this.$confirm('此操作将删除所选档案材料, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              catalogData.forEach(row => {
                let matchObj = XEUtils.findTree(this.queryData, item => item === row, this.treeConfig)
                if (matchObj) {
                  let { items, index } = matchObj
                  // 从树节点中移除
                  let restRow = items.splice(index, 1)[0]
                }
              })
              xTree.refreshData()
            })
            break
          case 'insert_e01z1':
            let xTrees = this.$refs.xTree
            let checkData = xTrees.getCheckboxRecords()
            if (checkData.length > 1 || checkData.length == 0 || checkData[0].remark != 'catalog') {
              var i = 0
              if (checkData.length > 1) {
                checkData.forEach(edata => {
                  if (edata.remark == 'catalog') {
                    i++
                  }
                })
                if (i > 1) {
                  this.$message({
                    message: '请勾选一条档案目录后新增',
                    type: 'warning'
                  })
                  return
                }
              } else {
                this.$message({
                  message: '请勾选一条档案目录后新增',
                  type: 'warning'
                })
                return
              }

            }
            xTrees.createRow({
              isNew: true
            }).then(newRow => {
              // 插入到第一行
              this.queryData.forEach(datas => {
                if (datas.id == checkData[0].id) {
                  if (datas.children == null) {
                    datas.children = []
                    datas.push(newRow)
                  } else {
                    datas.children.push(newRow)
                  }
                }
              })
              xTrees.refreshData().then(() => xTrees.setActiveRow(newRow))
            })
            break

        }
      },

      //获取uuid
      generateUUID() {
        let d = new Date().getTime()
        if (window.performance && typeof window.performance.now === 'function') {
          d += performance.now() //use high-precision timer if available
        }
        let uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
          var r = (d + Math.random() * 16) % 16 | 0
          d = Math.floor(d / 16)
          return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16)
        })
        let reg = new RegExp('-','g');
        return uuid.replace(reg, '')
      },

      //上传excel电子文件操作（成功）
      onSuccess(response, file, fileList) {
        if (response.code != '200') {
          this.$message({
            message: '导入失败，请检查文件内容',
            type: 'warning'
          })
        } else {
          this.queryData = response.data
          this.excelWindows = false
        }

      },
      //上传excel电子文件操作（上传前）
      beforeUpload(file) {
        //上传文件参数
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

        return extension || extension2
      },
      //上传
      submitUpload(event) {
        this.$confirm('此操作将替换清除已增加档案材料, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            event.preventDefault()
            this.$refs.upload.submit()
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '操作已取消'
            })
          })
      },

      //初始化页面所需参数
      initData() {

        //获取干部图片路径
        if (this.A01Record.a01Id != null) {
          let datatimes = new Date().getTime()
          this.a01ImgSrc = '/a01/queryA01Img?a01Id=' + this.A01Record.a01Id + '&tem=' + datatimes
        }
        //当前页面所需字典表数据  目录树
        queryDictionaryByTypes({ types: 'ZB97B' })
          .then(response => {
            this.sysDictionaryMap = response.data

          })
        // 当前页面所需字典表数据 性别
        queryDictionaryByType({ dictType: 'GB22611' })
          .then(response => {
            this.a0104s1 = response.data
          })
      },

      //获取材料树
      getQueryData(params) {
        queryE01z1TreeGrid().then(response => {
          this.data2 = response.data
        })
        queryE01z1TreeGrid(params).then(response => {
          this.queryData = response.data
        })
      },
      //根据code转换成中文
      getFullName(row, col) {
        //时间转换
        if (col.property == 'e01z501' || col.property == 'e01z117' || col.property == 'a0107' || col.property == 'e01z524' || col.property == 'e01z531' || col.property == 'e01z534') {
          if (row[col.property] != null && row[col.property] != '') {
            // var d = new Date(row[col.property]),
            //   month = '' + (d.getMonth() + 1),
            //   day = '' + d.getDate(),
            //   year = d.getFullYear()
            // if (month.length < 2) month = '0' + month
            // if (day.length < 2) day = '0' + day
            // return [year, month, day].join('-')
            return this.moment(row[col.property]).format("YYYY-MM-DD")
          }
        }
        // 性别
        if(col.property=="a0104"){
          let returnValues='';
          this.a0104s1.forEach(data => {
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
              console.log(returnValues)
            }
          })
          return returnValues;
        }
        //材料分类
        if (col.property == 'e01z101') {

          if (this.sysDictionaryMap.ZB97B == null ) {
            return null
          } else {
            let returnValues=''
            this.sysDictionaryMap.ZB97B.forEach(data=>{
              if (data.dictCode == row[col.property]) {
                returnValues = data.fullName
              }
            })
            return returnValues;
          }
        }
        //民族
        if (col.property == 'a0117') {
          let returnValues = ''
          this.a0117s.forEach(data => {
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName
            }
          })
          return returnValues
        }
        //政治面貌
        if (col.property == 'a0141') {
          let returnValues = ''
          this.a0141s.forEach(data => {
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName
            }
          })
          return returnValues
        }
        //全宗
        if (col.property == 'orgId') {
          if (this.orgMap[row.orgId] == null) {
            return null
          } else {
            return this.orgMap[row.orgId]
          }
        }
        //档案位置
        if (col.property == 'e01z541') {
          if (this.e01z541Map[row.e01z541] == null) {
            return null
          } else {
            return this.e01z541Map[row.e01z541]
          }
        }
        //部门
        if (col.property == 'deptId') {
          if (this.deptMap[row.deptId] == null) {
            return ''
          } else {
            return this.deptMap[row.deptId]
          }
        }
        //案卷质量
        if (col.property == 'e01z527') {
          if (row.e01z527 == null) {
            return ''
          } else {
            if (row.e01z527 == 1) {
              return '合格'
            } else if (row.e01z527 == 2) {
              return '优秀'
            } else if (row.e01z527 == 0) {
              return '不合格'
            } else {
              return row.e01z527
            }
          }
        }
        //有无档案目录电子版
        if (col.property == 'catalog') {
          if (row.catalog == null) {
            return ''
          } else {
            if (row.catalog == 1) {
              return '有'
            } else if (row.catalog == 0) {
              return '无'
            } else {
              return row.catalog
            }
          }
        }
        //有无档案目录电子版
        if (col.property == 'isCopy') {
          if (row.isCopy == null) {
            return ''
          } else {
            if (row.isCopy == 1) {
              return '是'
            } else if (row.isCopy == 0) {
              return '否'
            } else {
              return row.isCopy
            }
          }
        }
        return row[col.property]
      },

      //重置表单
      resetForm() {
        this.form = {
          e01z201: new Date(),
          e01z117: '',
          e01z204a: '',
          e01z207: this.form.e01z207,
          e01z101: '',
          e01z111a: '',
          e01z114: '',
          e01z121: ''
        }
      },
      //姓名光标离开事件
      animateWidth() {
        if (this.receiveForm.a0101 != null && this.receiveForm.a0101 != '') {
          queryPingYin({ a0101: this.receiveForm.a0101 }).then(response => {
            this.receiveForm.a0102 = response.data
          })
        }

      },

      // 左侧 复选框选中
      leftChange(val) {
        this.leftChek = val
        this.leftPersonCheck = val.map(item => item.id)
      },
      // grid  双击部门 查询人员
      deptClick(val) {
        if(this.isa01 == 2){
          return ;
        }
        this.isa01 = 2;
        if (val.children) {
          let tableData = [];
          for(let i in val.children){
            if(val.children[i].state==this.a01Search.state){
              tableData.push(val.children[i]);
            }
          }
          this.leftData = tableData
          this.beforeQueryData = val.children
          this.dpbutton = true
        }
      },
      // 左侧干部 部门 搜索
      search(type) {

        const _this = this
        if (type === 1) {
          this.dpbutton = false
          this.searchText = this.formInline.deptName
        } else {
          if (this.formInline.a0101 == null || this.formInline.a0101 == '') {
            this.$message({
              message: '请输入干部搜索条件',
              type: 'warning'
            })
            return
          }
          this.searchText = this.formInline.a0101
          this.dpbutton = true
        }
        this.isa01 = type
        queryA01List({ searchText: this.searchText, type: type ,state:this.a01state })
          .then(response => {
            _this.leftData = response.data
          })
      },
      activeNameFun() {
        if (this.$route.params.name) {
          this.activeName = this.$route.params.name
        } else {
          this.activeName = 'fileReceive'
        }
      },

      //创建档案材料并生成数字化任务
      createE01z1AndCreateTask() {
        //  this.receiveForm.a0107 = this.parseTime(this.receiveForm.a0107)
        if (this.archiveData.length == 0) {
          this.$message({
            message: '列表中没有数据',
            type: 'error'
          })
          return
        }
        let gridValues = JSON.parse(JSON.stringify(this.archiveData))
        for (let index in gridValues) {
          gridValues[index].e01z117 = this.parseTime(gridValues[index].e01z117)

        }
        let returnFormValues = JSON.parse(JSON.stringify(this.form))
        returnFormValues.e01z117 = this.parseTime(returnFormValues.e01z117)
        returnFormValues.e01z201 = this.parseTime(returnFormValues.e01z201)
        let returnValues = {
          formValues: JSON.stringify(returnFormValues),
          gridValues: JSON.stringify(gridValues)
        }

        insertE01z1sAndTask({ jsonStr: returnValues })
          .then(response => {
            if (response.code == 200) {
              this.leftData=[];
              this.a01state="0";
              this.formInline.a0101="";
              this.formInline.deptName="";
              this.$confirm('创建成功是否生成数字化任务?', '提示', {
                confirmButtonText: '生成数字化任务',
                cancelButtonText: '继续创建材料',
                type: 'warning'
              })
                .then(() => {
                  addE01z1Task({ data: returnValues }).then(response2=>{
                    if(response2.code == 200){
                      //添加数字化任务
                      this.$confirm('生成成功是否跳转到数字化任务页面', '提示', {
                        confirmButtonText: '跳转页面',
                        cancelButtonText: '继续创建材料',
                        type: 'warning'
                      })
                        .then(() => {
                          this.resetForm()
                          this.archiveData = []
                          //跳转到数字化页面
                          this.$router.push({
                            name:'任务处理',
                            params:{
                              taskId:response2.data
                            }
                          })
                        })
                        .catch(() => {
                          this.resetForm()
                          this.archiveData = []
                          return
                        })
                    }
                  })

                })
                .catch(() => {
                  this.resetForm()
                  this.archiveData = []
                  return
                })
            } else {
              this.$message({
                message: response.msg,
                type: 'error'
              })
              return
            }
          })
      },

      // 点击库房获取库房位置
      selectChange(val) {
        if (val != null && val != '') {
          queryRoomArch({ roomId: val }).then(response => {
            this.e01z541Options = response.data
            this.roomId = val
          })
        } else {
          this.e01z541Options = []
          this.roomId = ''
        }
      },
      e01z541LoadOptions({ action, parentNode, callback }) {
        let params = {
          roomId: this.roomId
        }
        if (parentNode.type == 1) {
          params.columnNumber = parentNode.columnNumber
        } else if (parentNode.type == 2) {

          params.columnNumber = parentNode.columnNumber
          params.sectionNumber = parentNode.sectionNumber
        } else if (parentNode.type == 3) {
          parentNode.children = []
          callback()
          return
        }
        queryRoomArch(params).then(response => {
          if (response.code == 200) {
            parentNode.children = response.data
            callback()
          } else {
            parentNode.children = []
            callback()
          }
        })

      },
      // 获取库房列表
      getRoomList() {
        queryRoomList().then(response => {
          this.rooms = response.data
        })
      },
      // 获取系统字典表下拉框数据
      getComboxDatas() {
        // 获取性别
        queryDictionaryByType({ dictType: 'GB22611' })
          .then(response => {
            this.a0104s = response.data
          })
        // 获取民族
        queryDictionaryByType({ dictType: 'GB3304' })
          .then(response => {
            this.a0117s = response.data
          })
        // 获取证件类型
        queryDictionaryByType({ dictType: 'ZB76' })
          .then(response => {
            this.a0183s = response.data
          })
        // 获取政治面貌
        queryDictionaryByType({ dictType: 'GB4762' })
          .then(response => {
            this.a0141s = response.data
          })
        // 获取学历
        queryDictionaryByType({ dictType: 'ZB64' })
          .then(response => {
            this.educations = response.data
          })
        // 获取个人身份
        queryDictionaryByType({ dictType: 'GB22614' })
          .then(response => {
            this.a0151s = response.data
          })
        // 获取干部状态
        queryDictionaryByType({ dictType: 'FDA01State' })
          .then(response => {
            this.states = response.data
            console.log(response.data)
          })
        // 获取职务
        queryDictionaryByType({ dictType: 'ZB09', eparentCode: '0' })
          .then(response => {
            this.dutys = response.data
          })
        // 获取级别
        queryDictionaryByType({ dictType: 'ZB09', eparentCode: '2' })
          .then(response => {
            this.grades = response.data
          })
        //获取材料类别
        queryArchiveTree().then(response => {
          this.e01z101s = response.data
        })
      },
      // 获取部门树
      getDeptTree() {
        queryDeptTree().then(response => {
          this.deptOptions = response.data
        })
      },
      // 档案接收保存
      receiveSave(val) {
        // 档案接收 隐藏
        // this.receiveShow = false
        // // 录入材料 显示
        // this.entryShow = true
        this.$refs[val].validate((valid) => {

          if (valid) {
            let returnForm = JSON.parse(JSON.stringify(this.receiveForm))
            returnForm.a0107 = this.parseTime(returnForm.a0107)
            returnForm.a0134 = this.parseTime(returnForm.a0134)
            if (returnForm.a0144 != '') {
              returnForm.a0144 = this.parseTime(returnForm.a0144)
            }
            returnForm.e01z501 = this.parseTime(returnForm.e01z501)

            if (returnForm.e01z524 != '') {
              returnForm.e01z524 = this.parseTime(returnForm.e01z524)
            }

            if (returnForm.e01z531 != '') {
              returnForm.e01z531 = this.parseTime(returnForm.e01z531)
            }
            if (returnForm.e01z534 != '') {
              returnForm.e01z534 = this.parseTime(returnForm.e01z534)
            }
            insertA01({ formValues: JSON.stringify(returnForm) })
              .then(response => {
                if (response.code === 200) {
                  this.a01Id = response.data
                  this.getQueryData({ a01Id: this.a01Id })
                  // 档案接收 隐藏
                  this.receiveShow = false
                  // 录入材料 显示
                  this.entryShow = true
                  //初始化form表单
                  this.receiveForm = {
                    a0101: '',
                    a0102: '',
                    a0104: '',
                    a0107: '',
                    a0117: '',
                    a0184: '',
                    a0183: '',
                    a0141: '',
                    a0134: '',
                    a0144: '',
                    education: '',
                    a0151: '',
                    duty: '',
                    grade: '',
                    e01z501: new Date(),
                    e01z541: null,
                    e01z517: 'admin',
                    e01z507a: '',
                    deptId: null
                  }
                }
              })
          } else {
            this.$message({
              message: '请按要求填写表单',
              type: 'error'
            })
          }
        })

      },

      // 部门查询
      deptNameQuery() {

      },

      // 干部查询
      cadresNameQuery() {

      },

      handleCheckAllChange(val) {
        this.checkedCities = val ? cityOptions : []
        this.isIndeterminate = false
      },
      handleCheckedCitiesChange(value) {
        const checkedCount = value.length
        this.checkAll = checkedCount === this.cities.length
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length
      },
      // grid 监听
      handleSelectionChange() {

      },

      createGridData(val) {
        if (this.leftChek.length == 0 && this.A01Record.a01Id == null) {
          this.$message({
            message: '请选择干部条目',
            type: 'warning'
          })
          return
        }
        if (this.A01Record.a01Id == null) {
          let flag = false
          let maxLength = this.archiveData.length + this.leftChek.length
          if (maxLength <= 50) {
            for (let index in this.leftChek) {
              if (flag) {
                return
              }
              if (this.leftChek[index].id == null) {
                this.$message({
                  message: '请选择干部条目',
                  type: 'warning'
                })
                return
              }
              this.$refs[val].validate((valid) => {
                if (valid) {
                  let gridData = JSON.parse(JSON.stringify(this.form))
                  gridData.disabled = true
                  gridData.uuid = this.generateUUID()
                  gridData.a0101 = this.leftChek[index].a0101
                  gridData.a01Id = this.leftChek[index].id
                  this.archiveData.push(gridData)
                  for (let j in this.leftData) {
                    if (this.leftData[j].id == this.leftChek[index].id) {
                      this.leftData.splice(j, 1)
                    }
                  }
                } else {
                  this.$message({
                    message: '请按要求填写表单',
                    type: 'error'
                  })
                  flag = true
                }
              })
            }
          } else {
            let num = 50 - this.archiveData.length
            this.$message({
              message: '最多添加50份材料信息，还可添加' + num + '份',
              type: 'warning'
            })
            return
          }
        } else {
          this.$refs[val].validate((valid) => {
            if (valid) {
              let gridData = JSON.parse(JSON.stringify(this.form))
              gridData.disabled = true
              if (this.archiveData.length <= 49) {
                gridData.uuid = this.generateUUID()
                gridData.a0101 = this.A01Record.a0101
                gridData.a01Id = this.A01Record.a01Id
                this.archiveData.push(gridData)
                for (let j in this.leftData) {
                  this.leftData.splice(j, 1)
                }
              } else {
                let num = 50 - this.archiveData.length
                this.$message({
                  message: '最多添加50份材料信息',
                  type: 'warning'
                })
                return
              }
            } else {
              this.$message({
                message: '请按要求填写表单',
                type: 'error'
              })
              return
            }
          })
        }
      },

      // 编辑
      electronicView(val) {
        this.editorForm = JSON.parse(JSON.stringify(val))
        this.dialogFormVisible = true

        // if (this.updateHtml === '编辑') {
        //   this.receiveRData=JSON.parse(JSON.stringify(val));
        //   val.disabled = false
        //   this.updateHtml = '保存'
        // } else {
        //   val.disabled = true
        //   this.updateHtml = '编辑'
        //   this.deleteHtml = '移除'
        // }
      },
      onCommitEdit(val) {
        this.$refs[val].validate((valid) => {
          if (valid) {
            let list = this.archiveData
            let par = this.editorForm
            for (let i in list) {
              let dataMap = list[i]
              if (dataMap.uuid == par.uuid) {
                dataMap.e01z101 = par.e01z101
                dataMap.e01z111a = par.e01z111a
                dataMap.e01z117 = par.e01z117
                dataMap.e01z114 = par.e01z114
                dataMap.e01z121 = par.e01z121
                this.dialogFormVisible = false
                return list[i] = dataMap
              }
            }
          }else{
            this.$message({
              type:"error",
              message:"请按要求填写表单"
            })
          }
        })

      },
      electronicFeedback(index) {
        this.archiveData.splice(index, 1)
      },
      // 分页
      handleSizeChange() {

      },
      // 生成
      handlePrintElectronic() {
        let e01z1GridDatas = this.queryData
        let returnData = []
        for (let index in e01z1GridDatas) {
          this.getNCatalogData(e01z1GridDatas[index], returnData)
        }
        if (returnData.length != 0) {

          insertE01z1sAndTask({ gridValues: JSON.stringify(returnData) }).then(response => {
            if (response == 200) {
              this.$message({
                message: '创建成功',
                type: 'success'
              })
              this.receiveShow = true
              // 录入材料 显示
              this.entryShow = false
            } else {
              this.$message({
                message: response.msg,
                type: 'error'
              })
            }
          })
        } else {
          this.$message({
            message: '请添加档案材料后进行创建',
            type: 'warning'
          })
        }
      },
      //获取非档案目录的数据
      getNCatalogData(data, returnData) {
        if (data.id != '4' && data.id != '9') {
          if (data.children != null && data.length != 0) {
            for (let i in data.children) {
              let dataMap = data.children[i]
              dataMap.e01z101 = data.id
              dataMap.e01z117 = data.n + data.y + data.r
              dataMap.a01Id = this.a01Id
            }
            returnData.push.apply(returnData, data.children)
          }
        } else {
          for (let index in data) {
            if (data[index].children != null && data[index].children.length != 0) {
              for (let i in data[index].children) {
                let dataMap = data[index][i].children
                dataMap.e01z101 = data[index].id
                dataMap.a01Id = this.a01Id
                dataMap.e01z117 = data[index].n + data[index].y + data[index].r
                returnData.push(dataMap)
              }
            }
          }
        }
      },
      // 导出
      handleExportElectronic() {
        this.excelWindows = true
      },
      handleCurrentChange() {

      },
      handleQueryElectronic() {

      },
      resetQueryElectronic() {

      }

    }
  }
</script>

<style lang="scss" scoped>
  .el-col.el-col-24.el-col-xs-24 {
    padding: 10px;
    margin: 10px;
  }

  .block {
    float: right;
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

  .el-input__inner {
    width: 150px;
  }


  .el-input {
    width: 150px;
  }

  .inputW {
    width: 150px;
  }

  .dialog-footer {
    background: rgba(204, 204, 204, 0.18);
    padding-right: 15px;
    padding: 11px;
  }


  /deep/ .el-input.is-disabled .el-input__inner {
    background-color: #f5f7fa0d;
    border-color: #e4e7ed00;
    color: #606266;
  }

  .vue-treeselect--single .vue-treeselect__input {
    width: 100%;
    height: 28px;
  }

  .vue-treeselect__control {
    padding-left: 5px;
    padding-right: 5px;
    display: table;
    table-layout: fixed;
    width: 100%;
    height: 28px;
    border: 1px solid #ddd;
    border-radius: 5px;
    background: #fff;
    transition-duration: 200ms;
    transition-property: border-color, box-shadow, width, height, background-color, opacity;
    transition-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
  }

  .table-style {
    /deep/ .el-table__fixed-right-patch {
      background-color: #F8F8F9;
    }
  }

  //设置统一表头
  /deep/ .el-table tbody tr:hover > td {
    background-color: #ecf5ff !important
  }

  /deep/ .el-table__body td {
    padding: 0;
    height: 40px;
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
      padding-right: 15px;
      padding: 0px;
    }
    /deep/.el-button+.el-button {
      margin-left: 6px;
    }
  }
</style>

<template>
  <div class="app-receive">
    <el-row style="height:calc(100vh - 86px);overflow: auto;" v-if="receiveShow">
          <el-col :span="24" style="padding: 10px;">
            <div style="width: 100%;margin: 0 auto" ref="initWidthOn"></div>
            <div style="width: 81%;margin: 0 auto">
              <el-form ref="receiveForm" :inline="true" :rules="rules" :model="receiveForm" label-width="110px" label-position="right" style="width:100%; padding: 10px 0 0 0">
                <div style="margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc"  >第一步：请填写基本信息</div>
                <el-form-item label="姓名" prop="a0101"><el-input v-model="receiveForm.a0101" @blur="animateWidth" placeholder="请输入姓名" size="mini"/></el-form-item>
                <el-form-item label="姓名简拼" prop="a0102"><el-input v-model="receiveForm.a0102" placeholder="请输入姓名简拼" size="mini"/></el-form-item>
                <el-form-item label="性别" prop="a0104">
                  <el-select v-model="receiveForm.a0104" clearable class="inputW" placeholder="请选择性别" size="mini">
                    <el-option v-for="item in a0104s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode"/>
                  </el-select>
                </el-form-item>
                <el-form-item label="出生日期" prop="a0107"><el-date-picker  value-format="yyyy-MM-dd"   :picker-options="pickerOptions" v-model="receiveForm.a0107" type="date" placeholder="选择日期" size="mini"/></el-form-item>
                <el-form-item label="民族" prop="a0117">
                  <el-select v-model="receiveForm.a0117" clearable filterable class="inputW" placeholder="请选择民族" size="mini">
                    <el-option v-for="item in a0117s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                  </el-select>
                </el-form-item>
                <el-form-item label="证件类型" prop="a0183">
                  <el-select v-model="receiveForm.a0183" clearable filterable class="inputW" placeholder="请选择证件类型" size="mini">
                    <el-option v-for="item in a0183s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                  </el-select>
                </el-form-item>
                <el-form-item label="证件号码" prop="a0184"><el-input v-model="receiveForm.a0184" placeholder="请输入证件号码" size="mini"/></el-form-item>
                <el-form-item label="政治面貌" prop="a0141">
                  <el-select v-model="receiveForm.a0141" clearable filterable class="inputW" placeholder="请选择政治面貌" size="mini">
                    <el-option v-for="item in a0141s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                  </el-select>
                </el-form-item>
                <el-form-item label="入党时间" prop="a0144"><el-input v-model="receiveForm.a0144" maxlength="10" placeholder="请输入入党时间" size="mini"/></el-form-item>
                <el-form-item label="参加工作时间" prop="a0134"><el-input v-model="receiveForm.a0134" maxlength="10" placeholder="请输入参加工作时间" size="mini"/></el-form-item>

                <el-form-item label="学历" prop="education">
                  <el-select v-model="receiveForm.education" clearable filterable class="inputW" placeholder="请选择学历" size="mini">
                    <el-option v-for="item in educations" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                  </el-select>
                </el-form-item>
                <el-form-item label="个人身份" prop="a0151">
                  <el-select v-model="receiveForm.a0151" clearable filterable class="inputW" placeholder="请选择个人身份" size="mini">
                    <el-option v-for="item in a0151s" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                  </el-select>
                </el-form-item>
                <el-form-item label="职务" prop="duty">
                  <el-select v-model="receiveForm.duty" filterable class="inputW" placeholder="请选择职务" size="mini">
                    <el-option v-for="item in dutys" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                  </el-select>
                </el-form-item>
                <el-form-item label="级别" prop="grade">
                  <el-select v-model="receiveForm.grade" clearable filterable class="inputW" placeholder="请选择级别" size="mini">
                    <el-option v-for="item in grades" :key="item.dictCode" :label="item.fullName" :value="item.dictCode" />
                  </el-select>
                </el-form-item>
                <div style="margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc">第二步：请填写接收信息</div>
                <el-form-item label="收件日期" prop="e01z501"><el-date-picker  value-format="yyyy-MM-dd"   :picker-options="pickerOptions" v-model="receiveForm.e01z501"  type="date" placeholder="选择日期" size="mini"/></el-form-item>
                <el-form-item label="经办人" prop="e01z517"><el-input v-model="receiveForm.e01z517" placeholder="请输入经办人" size="mini"/></el-form-item>

            <el-form-item label="来件单位" prop="e01z507a">
              <el-input v-model="receiveForm.e01z507a" placeholder="请输入来件单位" size="mini"/>
            </el-form-item>
            <el-form-item label="转入部门" prop="deptId">
              <!-- <treeselect
                v-model="receiveForm.deptId"
                filterable
                :normalizer="deptNormalizer"
                :options="deptOptions"
                class="inputW"
                placeholder="请选择转入部门"
              /> -->
              <selectTree
                v-model="receiveForm.deptId"
                :datas="deptOptions"
                :defaultProps="defaultProps"
                size="mini"
                :ischecked="true"
                :defaultExpandAll="true"
                placeholder="请选择部门"
                width="150px"
                 />
            </el-form-item>
            <el-form-item label="转入原因" prop="reason">
              <el-input class="inputW" v-model="receiveForm.reason" placeholder="请输入转入原因" size="mini"/>
            </el-form-item>
            <el-form-item label="正本卷数" prop="e01z511">
              <el-input-number v-model="receiveForm.e01z511" :min="0" :max="999" size="mini" class="inputW"/>
            </el-form-item>
            <el-form-item label="副本卷数" prop="e01z514">
              <el-input-number v-model="receiveForm.e01z514" :min="0" :max="999" size="mini" class="inputW"/>
            </el-form-item>
            <el-form-item label="收件文号" prop="e01z504">
              <el-input class="inputW" v-model="receiveForm.e01z504" placeholder="请输入收件文号" size="mini"/>
            </el-form-item>
            <el-form-item label="审核人" prop="e01z521">
              <el-input class="inputW" v-model="receiveForm.e01z521" placeholder="请输入审核人" size="mini"/>
            </el-form-item>
            <el-form-item label="审核日期" prop="e01z524">
              <el-date-picker class="inputW" v-model="receiveForm.e01z524"  value-format="yyyy-MM-dd"   :picker-options="pickerOptions" type="date" placeholder="请输入审核日期"
                              size="mini"/>
            </el-form-item>
            <el-form-item label="案卷质量" prop="e01z527">
              <el-select v-model="receiveForm.e01z527" clearable placeholder="请选择案卷质量" class="inputW" size="mini">
                <el-option v-for="item in e01z527Options" :key="item.value" :label="item.label"
                           :value="item.value"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="回执日期" prop="e01z531">
              <el-date-picker class="inputW" v-model="receiveForm.e01z531"  value-format="yyyy-MM-dd"   :picker-options="pickerOptions" type="date" placeholder="请输入回执日期"
                              size="mini"/>
            </el-form-item>
            <el-form-item label="入库日期" prop="e01z534">
              <el-date-picker class="inputW" v-model="receiveForm.e01z534"  value-format="yyyy-MM-dd"   :picker-options="pickerOptions" type="date" placeholder="请输入入库日期"
                              size="mini"/>
            </el-form-item>
            <el-form-item label="入库审批人" prop="e01z537">
              <el-input class="inputW" v-model="receiveForm.e01z537" placeholder="请输入入库审批人" size="mini"/>
            </el-form-item>

            <el-form-item label="选择库房" prop="room">
            <el-select v-model="receiveForm.room" clearable class="inputW" placeholder="请选择库房" @change="selectChange"
                         size="mini">
                <el-option v-for="item in rooms" :key="item.id" filterable :label="item.roomName" :value="item.id"/>
              </el-select>
            <!-- <selectTree
                v-model="receiveForm.room"
                :datas="rooms"
                :defaultProps="{children: 'children',label: 'roomName',id:'id'}"
                size="mini"
                :ischecked="true"
                :defaultExpandAll="true"
                :filterable="true"
                placeholder="请选择库房"
                @handleNode="selectChange"
                width="150px"
                 /> -->
            </el-form-item>
            <el-form-item label="库房位置" prop="e01z541" size="mini">
             <!-- <treeselect
                v-model="receiveForm.e01z541"
                :options="e01z541Options"
                class="inputW"
                :disable-branch-nodes="true"
                placeholder="请选择库房位置"
                @select="treeSelect"
                noOptionsText="请先选择库房"
                :load-options="e01z541LoadOptions"
              >
                <div slot="value-label" slot-scope="{ node }">{{ nodeFormatter(node) }}</div>
              </treeselect> -->
                  <el-select
                  v-model="selectValue.label"
                  size="mini"
                  placeholder="请选择库房位置"
                  style="width: 150px;background-color: #FFFFFF;"
                  ref="selectReport"
                  >
                    <el-option class="el-option" :value="selectValue.id" :label="selectValue.label" >
                      <div>
                        <el-scrollbar :style="scrollbarStyle">
                      <el-tree style="width: 150px;height: 150px;"
                      lazy
                      :load="loadNode"
                      :data="e01z541Options"
                      @node-click="treeNodeClick"
                      :props="TreedefaultProps"
                      empty-text="先选择库房再选择位置"
                       />
                       </el-scrollbar>
                       </div>
                    </el-option>
                  </el-select>
              </el-form-item>
            </el-form-item>
            <el-form-item label="备注" prop="e01z544">
              <el-input type="textarea" class="inputW" v-model="receiveForm.e01z544" placeholder="请输入备注"
                        :autosize="{minRows:1, maxRows: 1}"/>
            </el-form-item>
          </el-form>
        </div>
      </el-col>
      <div style="width: 75%;margin: 0 auto">
        <el-button type="primary" style="float: right;margin-right: 30px;width:80px" size="mini" @click="receiveSave('receiveForm')">接收档案</el-button>
      </div>
    </el-row>
    <!--  <el-col :span="24" style="padding: 10px;margin: 0"  v-if="entryShow"> -->
    <!--   材料录入 -->
    <vxe-grid
      ref="xTree"
      v-if="entryShow"
      border
      :toolbar="tableToolbar"
      :tree-config="{children: 'children'}"
      :columns="tableColumn"
      :data="queryData"
      :edit-config="{trigger: 'dblclick', mode: 'row', showStatus: true,activeMethod: activeRowMethod}"
      @toolbar-button-click="toolbarButtonClickEvent"
      :edit-rules="validRules"
    >
      <!--自定义插槽 name_default-->
      <template v-slot:name_default="{ row, column }">
            <span v-if='row.remark!="catalog"'>
              <a class="link" style="color: blue;" @click="showDetailEventUp(row, column)">上移</a>
              <a class="link" style="color: blue;" @click="showDetailEventDown(row, column)">下移</a>
            </span>
      </template>

    </vxe-grid>
    <!-- 导入excel -->
    <el-dialog
      title="导入档案目录"
      :visible.sync="excelWindows"
      width="30%"
      class="dialog-style"
      :before-close="function() {
        fileList=[];
        fileName='';
        excelWindows=false
      }"
      >
      <div>
        <div style="margin-left: 5px;" slot="tip" class="el-upload-list__item-name">{{fileName}}</div>
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
        :file-list="fileList"
        :data="uploadData"
        action="/e01z1/insertE01z1s">
        <el-button slot="trigger" size="mini" type="primary">选择文件</el-button>
        <el-button style="margin-left: 10px;" size="mini" type="success" @click="submitUpload">
          <i class="el-icon-upload el-icon--right"/>上传文件
        </el-button>
      </el-upload>
    </el-dialog>

    <!-- 材料分类调整 -->
    <el-dialog title="材料调整到其他分类" :close-on-click-modal="false" :visible.sync="dialogVisible2" class="style"
               width="40%">
      <el-tree class="chenge-tree" ref="tree2" default-expand-all :highlight-current="true" :data="data2"
               :filter-node-method="filterNode" icon-class="el-icon-folder-opened" @node-click="handleNodeClick"/>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false" size="mini">取 消</el-button>
        <el-button type="primary" size="mini" @click="changeOtherTreeNode">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
  import selectTree from '@/views/components/tree/tree.vue'
  import {
    insertA01,
    queryE01z1TreeGrid,
    queryLoginUser,
    queryA01ById,
    queryDictionaryByType,
    queryRoomArch,
    queryDeptTree,
    queryPingYin,
    queryRoomList,
    queryArchiveTree,
    queryDictionaryByTypes,
    insertE01z1sAndTask,
    insertE01z1s, // 导入档案目录
    insertWfTask, // 生成整本数字化任务
  } from '@/api/usual/receive'
  import { queryA01List } from '@/api/usual/transfer'

  import XEUtils from 'xe-utils'


  const cityOptions = ['梁健华', '测试部门']
  import elementResizeDetectorMaker from 'element-resize-detector'

  export default {
    components:{selectTree},
    name: '档案接收',
    data() {
      return {
        scrollbarStyle:{
          margin:'2px',
          height:'145px',
          width:"100%",
          padding:0,
          margin:0
        },
        selectValue:{
          label:'',
          id:''
        },
        ischecked: true,
        TreedefaultProps:{
          children: 'children',
          label: 'label',
          id:'id'
        },
        defaultProps:{
          children: 'children',
          label: 'deptName',
          id:'id'
        },
        //时间限制
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
        },
        departOrPerson: '2',
        fit: '',
        a01ImgSrc: '',
        // 案卷质量
        e01z527Options: [{
          value: '0',
          label: '不合格'
        }, {
          value: '1',
          label: '合格'
        }, {
          value: '2',
          label: '优秀'
        }],
        data2: [],
        dialogVisible2: false,
        //材料录入新增编辑校验
        validRules: {
          e01z111a: [
            { required: true, message: '材料名成必须填写' },
            { min: 2, max: 50, message: '输入内容在 2 到 50 个字' }
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
        //上传文件参数
        uploadData:{a01Id: '',},
        beforeQueryData: [],
        dpbutton: true,
        deleteHtml: '移除',
        updateHtml: '编辑',
        e01z1Show: false,
        fileList: [],
        excelWindows: false,
        //左侧人员选择数据
        leftPersonCheck: [],
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
          a0117: '01',
          a0183: '1',
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
          e01z511: '1',
          e01z514: '0',
          e01z521: '',
          e01z524: '',
          e01z527: '1',
          e01z531: '',
          e01z534: '',
          e01z537: '',
          e01z544: '',
        },
        //系统字典表数据，用于formatter
        sysDictionaryMap: {},
        props: {
          label: "deptName",
          value: "id"
        },
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

        options: [{
          value: '1',
          label: '查阅电子档案'
          }, {
            value: '2',
            label: '查阅实体档案'
          }, {
            value: '3',
            label: '借阅档案'
          }
        ],

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
            { max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ],
          a0102: [
            { required: true, message: '请输入姓名简拼', trigger: 'blur' },
            { max: 6, message: '输入内容不得超过6个字符', trigger: 'blur' }

          ],
          a0184:[
            { min: 1, max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ],
          a0107: [
            { required: true, message: '请选择出生日期', trigger: 'blur' }
          ],
          a0134: [
            { required: true, message: '请输入参加工作时间', trigger: 'blur' }
          ],
          e01z501: [
            { required: true, message: '请选择接收日期', trigger: 'blur' }
          ],
          education: [
            { required: true, message: '请选择学历', trigger: 'blur' }
          ],
          deptId: [
            { required: true, message: '请选择转入部门', trigger: 'blur' }
          ],
          room: [
            { required: true, message: '请选择库房', trigger: 'blur' }
          ],
          e01z541: [
            { required: true, message: '请选择库房位置', trigger: 'blur' }
          ],
          e01z517:[
            {  max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ],
          e01z507a:[
            {  max: 60, message: '输入内容不得超过60个字符', trigger: 'blur' }
          ],
          e01z527:[
            { required: true, message: '请选择案卷质量', trigger: 'blur' }
          ],
          reason:[
            {  max: 100, message: '输入内容不得超过100个字符', trigger: 'blur' }
          ],
          e01z504:[
            {  max: 20, message: '输入内容不得超过20个字符', trigger: 'blur' }
          ],
          e01z521:[
            { max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ],
          e01z537:[
            {  max: 18, message: '输入内容不得超过18个字符', trigger: 'blur' }
          ],
          e01z544:[
            {  max: 50, message: '输入内容不得超过50个字符', trigger: 'blur' }
          ],
        },
        formInline: {},
        rules1: {
          e01z201: [
            { required: true, message: '请选择接收时间', trigger: 'blur' }
          ],
          e01z117: [
            { required: true, message: '请选择材料制成日期', trigger: 'blur' }
          ],
          e01z204a: [
            { required: true, message: '请输入来件单位', trigger: 'blur' }
          ],
          e01z207: [
            { required: true, message: '请输入接收人', trigger: 'blur' }
          ],
          e01z101: [
            { required: true, message: '请选择材料类别', trigger: 'blur' }
          ],
          e01z111a: [
            { required: true, message: '请输入材料名称', trigger: 'blur' }
          ],
          e01z114: [
            { required: true, message: '请输入页数', trigger: 'blur' }
          ]
        },
        deptNshow: false,
        loginUserName:"",

      }
    },
    watch: {
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
      nodeFormatter(node){
        console.log(node);
        return node.raw.nodeName;
      },
      treeSelect(node,instanceId){
        console.log(node);
        console.log(instanceId);
        // if(node.type != 3){
        //   this.$message({
        //     type:'warning',
        //     message:'请选择正确的库房位置'
        //   })
        //   this.receiveForm.e01z541 = null;
        //
        // }

      },
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
        console.info(this.leftData)
      },
      //获取当前登录用户
      getLoginUser() {
        queryLoginUser().then(response => {
          if (response.code == 200) {
            let user = response.data
            this.loginUserName=user.userName
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

      /*//上传excel电子文件操作（成功）
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

      },*/
      // 上传前校验类型及大小
      changeFile(file,fileList){
        if (fileList.length > 0) {
          //this.fileList = fileList[fileList.length-(fileList.length-1)]
          this.fileList = [fileList[fileList.length - 1]]  // 这一步，展示最后一次选择的文件
        }
        console.log(this.fileList)
        //上传文件参数
        this.uploadData.a01Id = this.a01Id;
        let testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
        this.fileName = file.name
        console.log(this.fileName)
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
      // 上传成功后
      uploadSuccess(response){
        if (response.code != '200') {
          this.$message({
            message: '导入失败，请检查文件内容',
            type: 'warning'
          })
        } else {
          console.log(this.a01Id)
          this.excelWindows = false
          this.taskId = response.data
          this.$confirm('导入成功是否生成数字化任务?', '提示', {
            confirmButtonText: '生成数字化任务',
            cancelButtonText: '继续档案接收',
            type: 'warning'
          })
            .then(() => {
              insertWfTask({a01Id:this.a01Id}).then(responses => {
                if (responses.code == 200) {
                  console.log(responses)
                  this.$confirm('生成成功是否跳转到数字化任务页面', '提示', {
                    confirmButtonText: '跳转页面',
                    cancelButtonText: '继续档案接收',
                    type: 'warning'
                  })
                    .then(() => {
                      //跳转到数字化页面
                      this.$router.push({
                        name: '整本档案任务处理',
                        params: {
                          taskId: this.taskId
                        }
                      })
                    })
                    .catch(() => {
                      //继续档案接收
                      //初始化form表单
                      this.receiveForm= {
                        a0101: '',
                        a0102: '',
                        a0104: '',
                        a0107: '',
                        a0117: '01',
                        a0183: '1',
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
                        e01z517: 'admin',
                        e01z507a: '',
                        deptId: null,
                        reason: '',
                        e01z511: '1',
                        e01z514: '0',
                        e01z521: '',
                        e01z524: '',
                        e01z527: '1',
                        e01z531: '',
                        e01z534: '',
                        e01z537: '',
                        e01z544: '',
                      }
                    })
                }
              })
            })
            .catch(() => {
              //继续档案接收
              //初始化form表单
              this.receiveForm= {
                a0101: '',
                a0102: '',
                a0104: '',
                a0107: '',
                a0117: '01',
                a0183: '1',
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
                e01z517: 'admin',
                e01z507a: '',
                deptId: null,
                reason: '',
                e01z511: '1',
                e01z514: '0',
                e01z521: '',
                e01z524: '',
                e01z527: '1',
                e01z531: '',
                e01z534: '',
                e01z537: '',
                e01z544: '',
              }
            })
        }
      },
      /*//上传excel电子文件操作（上传前）
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
      },*/
      //上传
      submitUpload(event) {
        let _this = this;
        console.log(event)
        this.$confirm('此操作将替换原回执单电子文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(response => {
            event.preventDefault();
            _this.$refs.upload.submit();
            _this.$message({
              type: 'success',
              message: "上传成功"
            })
            //添加数字化任务
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
        queryDictionaryByTypes({ types: 'ZB97A' })
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
        if (col.property == 'e01z501' || col.property == 'a0107' || col.property == 'e01z524' || col.property == 'e01z531' || col.property == 'e01z534') {
          if (row[col.property] != null) {
            var d = new Date(row[col.property]),
              month = '' + (d.getMonth() + 1),
              day = '' + d.getDate(),
              year = d.getFullYear()
            if (month.length < 2) month = '0' + month
            if (day.length < 2) day = '0' + day
            return [year, month, day].join('-')

          }
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

      //材料接收-列表通用formatter
      getShowData(row, col) {
        //时间转换
        if (col.property == 'e01z117' || col.property == 'a0107') {
          if (row[col.property] != null && row[col.property] != '') {
            var d = new Date(row[col.property]),
              month = '' + (d.getMonth() + 1),
              day = '' + d.getDate(),
              year = d.getFullYear()
            if (month.length < 2) month = '0' + month
            if (day.length < 2) day = '0' + day
            return [year, month, day].join('-')
          }
        }
        //性别
        if (col.property == 'a0104') {
          let returnValues = ''
          this.a0104s1.forEach(data => {
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName
            }
          })
          return returnValues
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
        //干部状态
        if (col.property == 'state') {
          let returnValues = ''
          this.states.forEach(data => {
            if (data.dictCode == row[col.property]) {
              returnValues = data.fullName
            }
          })
          return returnValues
        }
        //材料分类
        if (col.property == 'e01z101') {
          if (this.sysDictionaryMap.ZB97A == null || this.sysDictionaryMap.ZB97A[row.e01z101] == null) {
            return null
          } else {
            return this.sysDictionaryMap.ZB97A[row.e01z101].fullName
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
          e01z207: 'admin',
          e01z101: '',
          e01z111a: '',
          e01z114: '',
          e01z121: ''
        }
      },
      //姓名光标离开事件
      animateWidth() {
        if (this.receiveForm.a0101 != null && this.receiveForm.a0101 != '') {
          console.log(this.receiveForm.a0101)
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
        if (val.children) {

          this.leftData = val.children
          this.beforeQueryData = val.children
          this.departOrPerson = 2
          this.dpbutton = true
          console.info(this.$refs.searchTableData)
          console.info(this.$refs.searchTableData.fixedBodyHeight)
        }
      },
      // 左侧干部 部门 搜索
      search(type) {
        console.info(this.a01Search.a01state)
        this.departOrPerson = type
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
        queryA01List({ searchText: this.searchText, type: type, state: this.a01state })
          .then(response => {
            this.leftData = response.data
            console.info(this.leftData)
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
              this.$confirm('创建成功是否生成数字化任务?', '提示', {
                confirmButtonText: '生成数字化任务',
                cancelButtonText: '继续创建材料',
                type: 'warning'
              })
                .then(() => {
                  this.$confirm('生成成功是否跳转到数字化任务页面', '提示', {
                    confirmButtonText: '跳转页面',
                    cancelButtonText: '继续创建材料',
                    type: 'warning'
                  })
                    .then(() => {
                      //跳转到数字化页面

                    })
                    .catch(() => {
                      this.resetForm()
                      this.archiveData = []
                      return
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
        console.log(val)
        this.receiveForm.e01z541 = null;
        if (val != null && val!= '') {
          queryRoomArch({ roomId: val }).then(response => {
            if( response.code === 200 ){
                console.log(response.data)
                this.e01z541Options = response.data
                this.roomId = val
            }
          })
        } else {
          this.e01z541Options = []
          this.roomId = ''
        }
      },
      loadNode(node, resolve) {
        if( this.roomId === '' ){
            return
        }
        let params = {
          roomId: this.roomId
        }
        if (node.data.type == 1) {
          params.columnNumber = node.data.columnNumber
        } else if (node.data.type == 2) {
          params.columnNumber = node.data.columnNumber
          params.sectionNumber = node.data.sectionNumber
        } else if (node.data.type == 3) {
          node.loading = false
          node.isLeaf = true
          return
        }
        queryRoomArch(params).then(response => {
          resolve(response.data)
        })
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
          return
        }
        queryRoomArch(params).then(response => {
          console.log(response)
          if (response.code == 200) {
            if(parentNode.type == 2){
              //设置没有子节点
              response.data.map(item=>{
                delete item.children;
              })
            }
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
        queryDeptTree({deptType: 1}).then(response => {
          this.deptOptions = response.data
          console.log(this.deptOptions)
        })
      },
      // 档案接收保存
      receiveSave(val) {
        this.$refs[val].validate((valid) => {
          if (valid) {
            let returnForm = JSON.parse(JSON.stringify(this.receiveForm))
            returnForm.a0107 = this.parseTime(returnForm.a0107)
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
                  this.$confirm('保存成功, 是否进入材料维护页面?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                  })
                    .then(response => {
                      this.$router.push({
                        name: "目录维护",
                        params: {
                          a0101:returnForm.a0101,
                          id:this.a01Id,
                          state:"1",
                        }
                      })
                      // this.excelWindows = true
                    })
                  //this.getQueryData({ a01Id: this.a01Id })
                  // 档案接收 隐藏
                  //this.receiveShow = false
                  // 录入材料 显示
                  //this.entryShow = true
                  //初始化form表单
                    this.selectValue.label = ''
                    this.selectValue.id = ''
                    this.e01z541Options=[]
                    this.receiveForm = {
                        a0101: '',
                        a0102: '',
                        a0104: '',
                        a0107: '',
                        a0117: '01',
                        a0183: '1',
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
                        e01z517: this.loginUserName,
                        e01z507a: '',
                        deptId: null,
                        reason: '',
                        e01z511: '1',
                        e01z514: '0',
                        e01z521: '',
                        e01z524: '',
                        e01z527: '1',
                        e01z531: '',
                        e01z534: '',
                        e01z537: '',
                        e01z544: '',
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
      onCommitEdit() {
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
      },
      electronicFeedback(row) {
        this.archiveData.splice(row, 1)
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

      },
      treeNodeClick(item){
        if( item.type === 3 ){
          this.receiveForm.e01z541 = item.id
          this.selectValue.label = item.nodeName
          this.selectValue.id = item.id
          this.$refs.selectReport.blur()
        }
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
    margin-bottom: 10px;
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
  // treeSelect高度调整
  /deep/ .vue-treeselect__control{
    /*margin-top: 5px;*/
    height: 28px !important;
    display: flow-root;
  }
  /deep/ .vue-treeselect__input-container{

    height: 28px !important;
    display: flow-root;
  }
  /deep/ .vue-treeselect__value-container{
    font-size: 12px;
  }
  /deep/ .vue-treeselect__input, .vue-treeselect__sizer{
    font-size: 12px;
    height: 28px !important;
    display: flow-root;
  }
  /deep/ .vue-treeselect__placeholder vue-treeselect-helper-zoom-effect-off {
    font-size:12px;
  }
  /deep/ .vue-treeselect__single-value{
    font-size: 12px;
  }
  .el-option{
    width: 150px;
    min-height: 150px;
    max-height: 200px;
    margin: 0;
    padding: 0;
    background-color: #FFFFFF;
  }
  .el-scrollbar {
    height: 100%;
    overflow:hidden;
  }
  /deep/ .el-scrollbar .el-scrollbar__wrap {
    overflow-x:hidden;
  }

  /deep/ .el-tree > .el-tree-node {
    min-width: 100%;
    display: inline-block;
    background: #ffffff;
  }
  // .selected{
  //   margin: 0;
  //   padding: 0;
  // }
  /*滚动条样式*/
  // .el-option::-webkit-scrollbar {
  //   width: 6px;
  //   height: 6px;
  // }
  // .el-option::-webkit-scrollbar-thumb {
  //   border-radius: 10px;
  //   /* -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2); */
  //   background-color: rgba(144,147,153,0.3)
  // }
  // .el-option::-webkit-scrollbar-track {
  //   /* -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2); */
  //   border-radius: 0;
  //   background-color: #FFFFFF
  // }
</style>

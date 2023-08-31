<template>
  <div class="app-receive">

    <el-col v-loading="loadding" :span="24" :xs="24" style="padding: 10px;margin: 0;height: calc(100vh - 86px);overflow: auto;">
        <el-row :gutter="10"  ref="searchRow" style="margin:0 0 10px 0;padding-left: 0px">
          <el-col :span="1.5"  v-if="moveStates">
              <el-button type="primary" size="mini" v-hasPermi="['usually:daily:catalog:add']" icon="el-icon-plus" @click="addE01z1">新增材料</el-button>
            </el-col>
            <el-col :span="1.5"  v-if="moveStates">
              <el-button type="primary" size="mini" v-hasPermi="['usually:daily:catalog:insert']" icon="el-icon-files"  @click="insertE01z1">插入材料</el-button>
            </el-col>

            <el-col :span="1.5"  v-if="moveStates">
              <el-button type="primary" size="mini" v-hasPermi="['usually:daily:catalog:importExcel']" icon="el-icon-folder-opened"  @click="improtExcel">导入excel</el-button>
            </el-col>

            <el-col :span="1.5"  v-if="moveStates">
              <el-button type="primary" size="mini" v-hasPermi="['usually:daily:catalog:exprotExcel']" icon="el-icon-download"  @click="makeExcel">导出excel</el-button>
            </el-col>

            <el-col :span="1.5"  v-if="moveStates">
              <el-button type="primary" size="mini" v-hasPermi="['usually:daily:catalog:adjustClassify']" icon="el-icon-s-ticket"  @click="adjustmentClassify">调整分类</el-button>
            </el-col>

            <el-col :span="1.5" v-if="moveStates">
              <el-button type="primary" size="mini" v-hasPermi="['usually:daily:catalog:adjustOrder']" icon="el-icon-sort"  @click="moveArchiveModel">调整位置</el-button>
            </el-col>

            <el-col :span="1.5" v-if="!moveStates">
              <el-button type="primary" size="mini" icon="el-icon-top"  @click="showDetailEventUp">上移</el-button>
            </el-col>

            <el-col :span="1.5" v-if="!moveStates">
              <el-button type="primary" size="mini" icon="el-icon-bottom"  @click="showDetailEventDown">下移</el-button>
            </el-col>

            <el-col :span="1.5" v-if="!moveStates">
              <el-button type="primary" size="mini" icon="el-icon-refresh-right"  @click="revertData">返回</el-button>
            </el-col>

            <el-col :span="1.5" v-if="!moveStates">
              <el-button type="primary" size="mini" icon="el-icon-finished"  @click="saveE01z1s">保存顺序</el-button>
            </el-col>

            <el-col :span="1.5"  v-if="moveStates">
              <el-button type="primary" size="mini" v-hasPermi="['usually:daily:catalog:eleArchiveDaily']" icon="el-icon-s-cooperation"  @click="manageEfile">电子档案管理</el-button>
            </el-col>

            <el-col :span="1.5" style="float: right;" v-if="moveStates">
              <el-button type="primary" size="mini" v-hasPermi="['usually:daily:catalog:saveArchives']" icon="el-icon-finished" @click="saveE01z1s">保存材料</el-button>
            </el-col>

        </el-row>

        <vxe-table
            ref="xTree"
            :expand-config="{expandAll: true}"
            :tree-config="{children: 'children',expandAll:true}"
            highlight-current-row
            border
            keep-source
            @keydown="tableKeyDown"
            class="vxe-tree--node-btn"
            :header-cell-style="headerCellStyle"
            :cell-style="cellStyle"
            size="mini"
            :edit-config="{autoClear:false,trigger: 'dblclick', mode: 'row',activeMethod: activeRowMethod,icon: 'fa fa-pencil'}"
            @edit-closed="editClosed"
            :height="tableHeight"
            @cell-click="cellClick"
            :edit-rules="tableRules"
            :checkbox-config="{checkStrictly: true}"
            @edit-actived="cellEdit"
            :data="queryData">
            <div slot="empty"></div>
            <vxe-table-column type="checkbox" width="40px"  align='center' ></vxe-table-column>
            <vxe-table-column  tree-node title="类号" field="e01z104Chidren" :formatter="putIndexValue" align='left' width="100px" show-overflow></vxe-table-column>
            <vxe-table-column title="材料名称" :edit-render="{name: 'input'}" field="e01z111a" align='left' width="400px" show-overflow></vxe-table-column>
            <vxe-table-column title="材料制成时间"  align='center'  show-overflow>
              <vxe-table-column title="年" field="e01z117Year" :edit-render="{name: 'input'}" align='center' width="70px" show-overflow></vxe-table-column>
              <vxe-table-column title="月" field="e01z117Month" :edit-render="{name: 'input'}" align='center' width="70px"  show-overflow></vxe-table-column>
              <vxe-table-column title="日" field="e01z117Day" :edit-render="{name: 'input'}" align='center' width="70px" show-overflow></vxe-table-column>
            </vxe-table-column>
            <vxe-table-column field="e01z114" title="页数" :edit-render="{name: 'input'}" width="70px" show-overflow align='center'></vxe-table-column>
            <vxe-table-column field="e01z121" title="备注"  :edit-render="{name: 'input'}" show-overflow align='center'></vxe-table-column>

            <!--自定义插槽 name_default-->
            <vxe-table-column field="operation"  v-if="moveStates"  fixed="right" label="操作" width="100" align="center">
              <template v-slot="{ row }">
                <template  v-if='((row.remark!="catalog"&&operationShow)||row._XID==editXid)&&operationType'>
                  <el-button v-hasPermi="['usually:daily:catalog:edit']" type="text" @click="operationLeftButtonEdit(row)" size="mini">编辑</el-button>
                  <el-button v-hasPermi="['usually:daily:catalog:delete']" type="text" @click="operationRightButtonRemove(row)" size="mini">移除</el-button>
                </template>
                <template v-if='((row.remark!="catalog"&&operationShow)||row._XID==editXid)&&!operationType'>
                  <el-button type="text" @click.native.prevent="operationLeftButtonSave(row)" size="mini">保存</el-button>
                  <el-button type="text" @click="operationRightButtonCancel(row)" size="mini">取消</el-button>
                </template>
              </template>
            </vxe-table-column>

        </vxe-table>
    </el-col>



    <!-- 导入excel -->
    <el-dialog
      title="导入档案目录"
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
        :action="importExcelURL">
        <el-button slot="trigger" size="mini" type="primary">选择文件</el-button>
        <el-button style="margin-left: 10px;" size="mini" type="success" @click="submitUpload">
          <i class="el-icon-upload el-icon--right"/>上传文件
        </el-button>
      </el-upload>
    </el-dialog>
    <!-- 材料分类调整 -->
    <el-dialog title="材料调整到其他分类"
    class="dialog-style"
    :close-on-click-modal="false"
    :append-to-body="true"
    :visible.sync="dialogVisible2"
               width="40%">
      <div class="tree tree-style" :style="treeHeight">
        <el-scrollbar >
          <el-tree class="chenge-tree" ref="tree2" default-expand-all :highlight-current="true" :data="data2"
               :filter-node-method="filterNode" icon-class="el-icon-folder-opened" @node-click="handleNodeClick"/>
        </el-scrollbar>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false" size="mini">取 消</el-button>
        <el-button type="primary" size="mini" @click="changeOtherTreeNode">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {
    moveClassify,
    getE01z1Excel,
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
    createTaskByA01Id,
    saveE01z1s,
    insertE01z1s, // 导入档案目录
    insertWfTask, // 生成整本数字化任务
    insertOneArchiveData,
  } from '@/api/usual/receive'
  import { queryA01List } from '@/api/usual/transfer'
  import { downLoad,requestExcel } from '@/downLoad/filedownload'
  import {addLookLog} from '@/api/log/browselog'
  import XEUtils from 'xe-utils'
  import { getBaseURL } from 'xe-utils/methods'

  const baseURL = process.env.VUE_APP_BASE_API;
  export default {
    name: '目录维护',
    data() {
      return {
        importExcelURL:baseURL+'/e01z1/queryE01z1Grid',
        treeHeight:"height:500px",
        loadding:false,
        oldRow:{},
        editXid:"",
        operationType:true,
        revertDatas:[],//排序时还原数据
        editDatas:[],
        editData:{},
        moveStates:true,
        moveArchive:true,
        operationShow:true,
        tableHeight:"500px",
        tableRules:{
          e01z111a: [
            { required: true, message: '材料名称必须填写'},
            {  max: 40, message: '材料名称长度不得超过40' }
          ],
          e01z117Year:[
            {pattern: /^\d+$|^\d+[.]?\d+$/, message: '只能输入数字'},
            {pattern:/^(19\d\d|20\d\d|2100)$/
            ,message:"请输入正确的年份"}

          ],
          e01z117Month:[
            {pattern: /^\d+$|^\d+[.]?\d+$/, message: '只能输入数字'},
            {pattern:/^(0?[1-9]|1[0-2])$/,message:'请输入正确的月份'}
          ],
          e01z117Day:[
            {pattern: /^\d+$|^\d+[.]?\d+$/, message: '只能输入数字'},
            {pattern:/^((0?[1-9])|((1|2)[0-9])|30|31)$/,message:'请输入正确的日期'}
          ],
          e01z114:[
            { required: true, message: '页数必须填写'},
            {pattern: /^\d+$|^\d+[.]?\d+$/, message: '只能输入数字'}
          ],
        },

        dialogFormVisible: false,
        //干部名称
        A01Record: {
          a01Id: this.$route.params.id,
          a0101: this.$route.params.a0101
        },
        //人员id
        a01Id: '',
        isAllRemove:false,
        //上传文件参数
        uploadData:{a01Id: '',},
        queryData:[],
        beforeQueryData: [],
        dpbutton: true,
        deleteHtml: '移除',
        updateHtml: '编辑',
        e01z1Show: false,
        fileList: [],
        excelWindows: false,
        //左侧人员选择数据
        leftPersonCheck: [],
        options: [],
        // 材料 档案接收显示
        receiveShow: false,
        // 录入材料 显示
        entryShow: true,
        jumpA01Id:this.$route.params.id,
        state:this.$route.params.state,
        personnelText: '',
        a01state: '0',
        cadresText: '',
        searchText: '',
        leftData: [],
        leftChek: [],
        activeName: 'fileReceive',
        dialogVisible2:false,
        data2:[],
        fileName:'',
        removeTreeData:[],
      }
    },
    watch: {


    },
    mounted() {
      this.getQueryData()
      this.$nextTick(()=>{
        this.searchRowHeight = this.$refs.searchRow.$el.offsetHeight
        this.tableHeight = (document.body.clientHeight - (84 +40 + this.searchRowHeight)) + "px"
        this.treeHeight = "height:"+(document.body.clientHeight - (84 +40 + this.searchRowHeight))*0.8+'px';
      })
      window.onresize = () => {
        return (() => {
          this.searchRowHeight = this.$refs.searchRow.$el.offsetHeight
          this.tableHeight = (document.body.clientHeight - (84+40 + this.searchRowHeight)) + "px"
          this.treeHeight = "height:"+(document.body.clientHeight - (84 +40 + this.searchRowHeight))*0.8+'px';
        })();
      };
    },
    created() {

    },
    methods: {
      //还原排序数据
      revertData(){
        this.queryData=JSON.parse(JSON.stringify(this.revertDatas));
        let xTree = this.$refs.xTree;
        xTree.syncData();
        this.moveStates=true;
      },

      //调整位置
      moveArchiveModel(){
        let xTree = this.$refs.xTree;
        xTree.clearActived();
        let tableListData = this.treeToList(this.queryData);
        /**
         * 有效的方式 - 改变下标，控制遍历
         */
        for (let i = 0; i < tableListData.length; i++) {
        　　if (tableListData[i].remark=="catalog") {
               tableListData.splice(i, 1); // 将使后面的元素依次前移，数组长度减1
        　　　　i--; // 如果不减，将漏掉一个元素
        　　}
        }
        xTree.validate(tableListData, (errMap) => {
          if (errMap) {
            this.revertDatas=JSON.parse(JSON.stringify(this.queryData));
            this.moveStates=false;
          }else{
            this.$message(
              {
                type:"warning",
                message:"请通过校验后进行调整位置操作"
              }
            )
          }
        })
      },

      validateButton(){
        let xTree=this.$refs.xTree;
        let selectRecords = xTree.getCheckboxRecords()
        xTree.validate(selectRecords, (errMap) => {
          if (errMap) {
            this.$XModal.message({ status: 'error', message: '校验不通过！' })
          } else {
            this.$XModal.message({ status: 'success', message: '校验成功！' })
          }
        })
      },


      tableKeyDown(e){
        if(e.$event.key=="Enter"){
            this.$refs.xTree.clearActived()
        }
      },
      cellClick({row,rowIndex,$rowIndex,column,columnIndex,$columnIndex,cell}){
        let xTree = this.$refs.xTree;
        xTree.setCheckboxRow(row,true)
        let checkBoxDatas=xTree.getCheckboxRecords();
        for(let n in checkBoxDatas){
            if(checkBoxDatas[n]._XID!=row._XID){
              xTree.setCheckboxRow(checkBoxDatas[n],false)
            }else{
              xTree.setCheckboxRow(checkBoxDatas[n],true)
            }
        }
      },
      manageEfile(){
        let xTree = this.$refs.xTree
        let checkData = xTree.getCheckboxRecords()
        if(checkData.length==0||checkData[0].remark=="catalog"){
          this.$message({
            type:"warning",
            message:"请勾选材料数据后点击电子档案管理"
          })
          return
        }
        let insertRecords = XEUtils.filterTree(this.queryData, item => item.isNew, this.treeConfig)
        let removeRecords = this.removeTreeData
        if(insertRecords.length>0||removeRecords.length>0||this.editDatas.length>0||!this.moveArchive||this.isAllRemove){
          this.$message({
            type:"warning",
            message:"档案材料已做修改，请保存后点击电子档案管理"
          })
          return;
        }
        let a0101 = this.$route.params.a0101;
        let logParams ={
          a0101:a0101,
          e01z111a:checkData[0].e01z111a
        }
        addLookLog(logParams)//添加日志
        let params = {
          fileAttribute: checkData[0],
          AllFile:this.queryData,
          id:this.jumpA01Id,
          isShow:true
        }
        this.$router.push({name: "查看电子文件"})
        sessionStorage.setItem('materialInformation', JSON.stringify(params));
      },
      makeExcel(){
        let insertRecords = XEUtils.filterTree(this.queryData, item => item.isNew, this.treeConfig)
        let removeRecords = this.removeTreeData
        if(insertRecords.length>0||removeRecords.length>0||this.editDatas.length>0||!this.moveArchive||this.isAllRemove){
          this.$message({
            type:"warning",
            message:"档案材料已做修改，请保存后导出"
          })
          return;
        }
        //download("/e01z1/getE01z1Excel?a01Id="+this.jumpA01Id);
        // window.location.href="/e01z1/getE01z1Excel?a01Id="+this.jumpA01Id
        // downLoad('/e01z1/getE01z1Excel',{a01Id:this.jumpA01Id})
        // window.location.href = baseURL+'/e01z1/getE01z1Excel?a01Id='+this.jumpA01Id
        // downLoad('/e01z1/getE01z1Excel',{a01Id:this.jumpA01Id})
        requestExcel('/e01z1/getE01z1Excel?a01Id=' + this.jumpA01Id,(response)=>{
          if(response.code == 200){
            return true;
          }
        })
        // downLoad(,{a01Id:})
      },
      editClosed({row}){
        //如果修改过则放入修改条目集合中
        let currentRow = JSON.parse(JSON.stringify(row));
        let currentEditData=JSON.parse(JSON.stringify(this.editData))
        let flag=false;
        for(let k in currentRow){
          if(currentRow[k]!=currentEditData[k]){
            flag=true;
          }
        }
        if(flag&&!row.isNew){
          this.editDatas.push(row._XID)
        }
        row.isNewAdd=false;
        this.operationShow=true;
        this.editXid="";
        this.operationType=true;
      },
      initOldData(){
        if(this.oldRow.e01z111a==null){
          this.oldRow.e01z111a=''
        }
        if(this.oldRow.e01z117Year==null){
          this.oldRow.e01z117Year=''
        }
        if(this.oldRow.e01z117Month==null){
          this.oldRow.e01z117Month=''
        }
        if(this.oldRow.e01z117Day==null){
          this.oldRow.e01z117Day=''
        }
        if(this.oldRow.e01z114==null){
           this.oldRow.e01z114=''
        }
        if(this.oldRow.e01z121==null){
           this.oldRow.e01z121=''
        }
      },
      //操作列左侧按钮  编辑
      operationLeftButtonEdit(row){
        this.editData=JSON.parse(JSON.stringify(row));
        this.oldRow={}
        this.$refs.xTree.setActiveRow(row)
        this.oldRow=JSON.parse(JSON.stringify(row));
        this.initOldData()
        this.operationShow=false;
        this.editXid=row._XID;
        this.operationType=false;
      },
      //操作列左侧按钮  保存
      operationLeftButtonSave(row){
        let xTree=this.$refs.xTree;
        xTree.clearActived()
        xTree.validate(row, (errMap) => {
          if (errMap) {
            let returnParam=JSON.parse(JSON.stringify(row));
            if(returnParam.a01Id==null||returnParam.a01Id==''){
              returnParam.a01Id=this.jumpA01Id;
            }
            this.queryData.forEach(datas => {
              if(datas.children!=null&&datas.children.length>0){
                datas.children.forEach(childrenData =>{
                  if(childrenData._XID==row._XID){
                    returnParam.e01z101=datas.id;
                  }
                  if(childrenData.children!=null&&childrenData.children.length>0){
                    childrenData.children.forEach(threeChildrenData=>{
                      if(threeChildrenData._XID==row._XID){
                        returnParam.e01z101=childrenData.id;
                      }
                    })
                  }
                })

              }
            })
            returnParam.e01z104=this.putIndexValue({row});
            insertOneArchiveData(returnParam).then(res=>{
              if(res.code=="200"){
                this.$message({
                  type:"success",
                  message:"保存成功"
                })

                this.oldRow={};
                this.operationShow=true;
                this.editXid="";
                this.operationType=true;
                row.isNew=false;
                row.id=res.data;
                row.a01Id = this.jumpA01Id;
                for (var i = 0; i < this.editDatas.length; i++) {
                　　if (this.editDatas[i]==row._XID) {
                       this.editDatas.splice(i, 1); // 将使后面的元素依次前移，数组长度减1
                　　　　i--; // 如果不减，将漏掉一个元素
                　　}
                }
              }
            })


          }
        })


      },

      //操作列右侧按钮  移除
      operationRightButtonRemove(row){
          if(this.editDatas.length>0){
            for (var i = 0; i < this.editDatas.length; i++) {
          　　if (this.editDatas[i]==row._XID) {
                  this.editDatas.splice(i, 1); // 将使后面的元素依次前移，数组长度减1
          　　　　i--; // 如果不减，将漏掉一个元素
          　　}
            }
          }

          let catalogData=[];
          catalogData.push(row)
            catalogData.forEach(rows => {
              let matchObj = XEUtils.findTree(this.queryData, item => item === rows, this.treeConfig)
              if (matchObj) {
                let { items, index } = matchObj
                // 从树节点中移除

                let restRow = items.splice(index, 1)[0];
                this.removeTreeData.push(restRow)
              }
            })
            let xTree = this.$refs.xTree;
            xTree.syncData()
            this.$message({
              type:"success",
              message:"材料移除成功，请点击’保存材料’进行保存"
            })
      },

      //操作列右侧按钮  取消
      operationRightButtonCancel(row){
        let xTree = this.$refs.xTree
        xTree.clearActived()
        Object.assign(row, this.oldRow)
        for (var i = 0; i < this.editDatas.length; i++) {
        　　if (this.editDatas[i]==row._XID) {
              this.editDatas.splice(i, 1); // 将使后面的元素依次前移，数组长度减1
        　　　　i--; // 如果不减，将漏掉一个元素
        　　}
        }
        //XEUtils.filterTree(this.queryData, item => item.isNew, this.treeConfig)
        this.operationShow=true;
        this.editXid="";
        this.operationType=true;
        if(row.isNewAdd){
          let matchObj = XEUtils.findTree(this.queryData, item => item === row, this.treeConfig)
          if (matchObj) {
            let { items, index } = matchObj
            // 从树节点中移除
            let restRow = items.splice(index, 1)[0];
          }
          xTree.syncData();
        }
      },

      cellEdit({row}){
        if(this.moveStates){
          this.editData=JSON.parse(JSON.stringify(row));
          this.oldRow={}
          this.oldRow=JSON.parse(JSON.stringify(row));
          this.initOldData()
          this.operationShow=false;
          this.editXid=row._XID;
          this.operationType=false;
        }
      },


      //页面序号设置
      putIndexValue( {row}){
        if(row.remark!=null&&row.remark=="catalog"){
          return row.e01z104;
        }else{
          for(let n in this.queryData){
            if(this.queryData[n].children!=null){
              if(this.queryData[n].id!='4'&&this.queryData[n].id!='9'){
                if(this.queryData[n].children.indexOf(row)!=-1){
                  return this.queryData[n].children.indexOf(row)+1
                }
              }else{
                for(let r in this.queryData[n].children){
                  if(this.queryData[n].children[r].children!=null){
                    if(this.queryData[n].children[r].children.indexOf(row)!=-1){
                      return this.queryData[n].children[r].children.indexOf(row)+1
                    }
                  }
                }
              }
            }
          }
        }
      },

      // xTreeChecked({ checked, records,row }) {
      //   let xTree=this.$refs.xTree//.setCheckboxRow;
      //   if(records!=null&&records.length>0){
      //     for(let n in records){
      //       if(records[n]._XID!=row._XID){
      //         xTree.setCheckboxRow(records[n],false)
      //       }else{
      //         xTree.setCheckboxRow(records[n],true)
      //       }
      //     }
      //   }
      // },
      //保存材料
      saveE01z1s(){
        let xTree = this.$refs.xTree;
        xTree.clearActived();

        let archiveDatas =JSON.parse(JSON.stringify(this.queryData));
        this.getTreeRemoveEmptyDatas(archiveDatas)
        let tableListData = this.treeToList(this.queryData);

        /**
         * 有效的方式 - 改变下标，控制遍历
         */
        for (let i = 0; i < tableListData.length; i++) {
        　　if (tableListData[i].remark=="catalog"||(tableListData[i].isNew&&(tableListData[i].e01z111a==null||this.customReplaceAll(" ","",tableListData[i].e01z111a)=='')&&
                    (tableListData[i].e01z117Day==null||this.customReplaceAll(" ","",tableListData[i].e01z117Day)=='')&&
                    (tableListData[i].e01z117Month==null||this.customReplaceAll(" ","",tableListData[i].e01z117Month)=='')&&
                    (tableListData[i].e01z117Year==null||this.customReplaceAll(" ","",tableListData[i].e01z117Year)=='')&&
                    (tableListData[i].e01z114==null||this.customReplaceAll(" ","",tableListData[i].e01z114)=='')&&
                    (tableListData[i].e01z121==null||this.customReplaceAll(" ","",tableListData[i].e01z121)==''))) {
               tableListData.splice(i, 1); // 将使后面的元素依次前移，数组长度减1
        　　　　i--; // 如果不减，将漏掉一个元素
        　　}
        }
        xTree.validate(tableListData, (errMap) => {
          if (errMap) {
            let params={
              e01z1s:archiveDatas,
              a01Id:this.jumpA01Id
            }
            let removeRecords = this.removeTreeData;
            if(removeRecords!=''&&removeRecords.length>0){
              params.removeIds=removeRecords.map(item=>item.id).join(",");
            }

            if(this.isAllRemove){
              params.removeType="all";
            }else{
              params.removeType="one";
            }
    //if(this.state!=null&&this.state=="1"){


            saveE01z1s(params).then(res=>{
              if(res.code=='200'){
                this.removeTreeData=[];
                this.isAllRemove=false;
                this.getQueryData()
                this.moveArchive=true;
                this.loadding=false;
                this.moveStates=true;
                this.editDatas=[];
                if(this.state!=null&&this.state=="1"){
                  this.$confirm('保存成功, 是否生成数字化任务?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                  }).then(response => {

                    createTaskByA01Id({a01Id:this.jumpA01Id}).then(ress => {
                      if(res.code=='200'){
                        this.state="0";
                        let taskId=ress.data
                        this.$confirm('生成数字化任务成功，是否跳转到处理页面?', '提示', {
                          confirmButtonText: '确定',
                          cancelButtonText: '取消',
                          type: 'warning'
                        }).then(response => {
                          this.$router.push({
                            name: "整本档案任务处理",
                            query: {
                              taskId:taskId
                            }
                          })
                        })
                      }
                    })
                  })
                }else{
                  this.$message({
                    type:"success",
                    message:"保存成功"
                  })
                }

              }
            }).catch(function(error){
              this.loadding=false;
            })
          }
        })
      },

      //去除空行数据
      getTreeRemoveEmptyDatas(treeData){
        for(let n in treeData){
          if(treeData[n].id!=4&&treeData[n].id!=9){
            if(treeData[n].children!=null&&treeData[n].children.length>0){
              for(let m=0;m<treeData[n].children.length; m++){
                if(treeData[n].children[m].isNew){
                  if((treeData[n].children[m].e01z111a==null||this.customReplaceAll(" ","",treeData[n].children[m].e01z111a)=='')&&
                    (treeData[n].children[m].e01z117Day==null||this.customReplaceAll(" ","",treeData[n].children[m].e01z117Day)=='')&&
                    (treeData[n].children[m].e01z117Month==null||this.customReplaceAll(" ","",treeData[n].children[m].e01z117Month)=='')&&
                    (treeData[n].children[m].e01z117Year==null||this.customReplaceAll(" ","",treeData[n].children[m].e01z117Year)=='')&&
                    (treeData[n].children[m].e01z114==null||this.customReplaceAll(" ","",treeData[n].children[m].e01z114)=='')&&
                    (treeData[n].children[m].e01z121==null||this.customReplaceAll(" ","",treeData[n].children[m].e01z121)=='')
                  ){
                    treeData[n].children.splice(m, 1); // 将使后面的元素依次前移，数组长度减1
        　　　　     m--; // 如果不减，将漏掉一个元素
                  }
                }
              }
            }
          }else{
            for(let x in treeData[n].children){
              let childrenList=treeData[n].children[x].children;
              if(childrenList!=null&&childrenList.length>0){
                for(let j=0;j<childrenList.length;j++ ){
                  if(childrenList[j].isNew){
                    if((childrenList[j].e01z111a==null||this.customReplaceAll(" ","",childrenList[j].e01z111a)=='')&&
                    (childrenList[j].e01z117Day==null||this.customReplaceAll(" ","",childrenList[j].e01z117Day)=='')&&
                    (childrenList[j].e01z117Month==null||this.customReplaceAll(" ","",childrenList[j].e01z117Month)=='')&&
                    (childrenList[j].e01z117Year==null||this.customReplaceAll(" ","",childrenList[j].e01z117Year)=='')&&
                    (childrenList[j].e01z114==null||this.customReplaceAll(" ","",childrenList[j].e01z114)=='')&&
                    (childrenList[j].e01z121==null||this.customReplaceAll(" ","",childrenList[j].e01z121)=='')
                  ){
                    childrenList.splice(j, 1); // 将使后面的元素依次前移，数组长度减1
        　　　　     j--; // 如果不减，将漏掉一个元素
                    }
                  }
                }
              }
            }
          }
        }
      },

      //自定义全部替换
      customReplaceAll(oldStr,newStr,data){
        let reg = new RegExp(oldStr,'g');
        return data.replace(reg,newStr)
      },

      //tree转list
      /**
       * 树转list
       */
      treeToList(tree){
         let list = [];  //结果lsit
          for(var i in tree){
              var node = tree[i];

              if (node.children!=null&&node.children.length !== 0) {  //遍历树的第一层,只有一个根结点
                  //第一层加入到list中,因为根结点模块设置为虚拟结点,所以不用加入
                  /*list.push({
                      id: node.id,
                      name: node.title,
                      parentId:node.parentId
                  });*/
                  this.toListDF(node.children, list, node.id);  //遍历子树,并加入到list中.
              }
          }
          return list;
      },

      /**
       * 深度优先遍历树
       * 一个递归方法
       * @params tree:要转换的树结构数据
       * @params list:保存结果的列表结构数据，初始传list = []
       * @params parentId:当前遍历节点的父级节点id，初始为null(因为根节点无parentId)
       **/
      toListDF (tree, list, parentId) {
          for (let i in tree) { //遍历最上层
              //将当前树放入list中
              let node = tree[i];
              list.push(node);
              //如果有子结点,再遍历子结点
              if (node.children!=null&&node.children.length !== 0) {
                  this.toListDF(node.children, list, node.id)  //递归
              }
          }
      },



      //TEST
      getInsertEvent () {
        let insertRecords = XEUtils.filterTree(this.queryData, item => item.isNew, this.treeConfig)
        this.$XModal.alert(insertRecords.length)
      },
      getRemoveEvent () {
        let removeRecords = this.removeTreeData
        this.$XModal.alert(removeRecords.length)
      },
      getUpdateEvent () {
        let updateRecords = this.$refs.xTree.getUpdateRecords()
        this.$XModal.alert(updateRecords.length)
      },



      //20200320改
      //新增
      addE01z1(){
        let xTrees = this.$refs.xTree
        let checkData = xTrees.getCheckboxRecords()
        if(checkData.length==0){
          this.$message({
            type:"warning",
            message:"请勾选一条数据后点击新增"
          })
          return;
        }
        if(checkData[0].id=='4'||checkData[0].id=='9'){
          this.$message({
            type:"warning",
            message:"请勾选下级分类后点击新增"
          })
          return;
        }
        xTrees.createRow({
          isNew: true,
          isNewAdd: true,
        }).then(newRow => {

          this.queryData.forEach(datas => {
            if (datas._XID == checkData[0]._XID) {
              if (datas.children == null) {
                datas.children = []
                datas.children.push(newRow)
              } else {
                datas.children.push(newRow)
              }
            }
            if(datas.children!=null&&datas.children.length>0){
              let flag = false;
              datas.children.forEach(childrenData =>{
                if(childrenData._XID==checkData[0]._XID){
                  if(childrenData.remark=="catalog"){
                    if(childrenData.children!=null){
                      childrenData.children.push(newRow)
                    }else{
                      childrenData.children=[];
                      childrenData.children.push(newRow)
                    }
                  }else{
                    flag=true;
                  }
                }else{
                  if(childrenData.children!=null&&childrenData.children.length>0){
                    let f=false;
                    childrenData.children.forEach(threeChildren=>{
                      if(threeChildren._XID==checkData[0]._XID){
                        f=true;
                      }
                    })
                    if(f){
                      childrenData.children.push(newRow);
                    }
                  }
                }
              })
              if(flag){
                datas.children.push(newRow)
              }
            }
          })
          xTrees.syncData().then(() => xTrees.setActiveRow(newRow))
        })


        // let xTrees = this.$refs.xTree
        // let checkData = xTrees.getRadioRecord()
        // if(checkData==null){
        //   this.$message({
        //     type:"warning",
        //     message:"请勾选一条数据后点击新增材料"
        //   })
        //   return;
        // }
        // if(checkData.id=='4'||checkData.id=='9'){
        //   this.$message({
        //     type:"warning",
        //     message:"请勾选下级分类后点击新增材料"
        //   })
        //   return;
        // }
        // xTrees.createRow({
        //   isNew: true
        // }).then(newRow => {
        //   // 新增到最后一行 偷懒  懒得搞递归  后期维护的大佬担待
        //   this.queryData.forEach(datas => {
        //     if (datas.id == checkData.id) {
        //       if (datas.children == null) {
        //         datas.children = []
        //         datas.push(newRow)
        //       } else {
        //         datas.children.push(newRow)
        //       }
        //     }
        //     if(datas.children!=null&&datas.children.length>0){
        //       let flag = false;
        //       datas.children.forEach(childrenData =>{
        //         if(childrenData.id==checkData.id){
        //           if(childrenData.remark=="catalog"){
        //             if(childrenData.children!=null){
        //               childrenData.children.push(newRow)
        //             }else{
        //               childrenData.children=[];
        //               childrenData.children.push(newRow)
        //             }
        //           }else{
        //             flag=true;
        //           }
        //         }else{
        //           if(childrenData.children!=null&&childrenData.children.length>0){
        //             let f=false;
        //             childrenData.children.forEach(threeChildren=>{
        //               if(threeChildren.id==checkData.id){
        //                 f=true;
        //               }
        //             })
        //             if(f){
        //               childrenData.children.push(newRow);
        //             }
        //           }
        //         }
        //       })
        //       if(flag){
        //         datas.children.push(newRow)
        //       }
        //     }
        //   })
        //   xTrees.syncData().then(() => xTrees.setActiveRow(newRow))
        // })
      },
      //插入
      insertE01z1(){
        let xTree = this.$refs.xTree
        let checkData = xTree.getCheckboxRecords()
        if(checkData.length==0||checkData[0].remark=="catalog"){
          this.$message({
            type:"warning",
            message:"请勾选材料数据后点击插入材料"
          })
          return
        }
        xTree.createRow({
          isNew: true,
          isNewAdd: true,
        }).then(newRow => {
          // 插入到第一行
          let rowNode = XEUtils.findTree(this.queryData, item => item._XID === checkData[0]._XID, this.treeConfig)
          if (rowNode) {
            rowNode.items.splice(rowNode.index, 0, newRow)
            xTree.syncData().then(() => xTree.setActiveRow(newRow))
          }
        })

        // let xTree = this.$refs.xTree
        // let checkData = xTree.getRadioRecord()
        // if(checkData==null||checkData.remark=="catalog"){
        //   this.$message({
        //     type:"warning",
        //     message:"请勾选材料数据后点击插入材料"
        //   })
        //   return
        // }
        // xTree.createRow({
        //   isNew: true
        // }).then(newRow => {
        //   // 插入到第一行
        //   let rowNode = XEUtils.findTree(this.queryData, item => item.id === checkData.id, this.treeConfig)
        //   if (rowNode) {
        //     rowNode.items.splice(rowNode.index, 0, newRow)
        //     xTree.syncData().then(() => xTree.setActiveRow(newRow))
        //   }
        // })
      },
      //移除数据
      removeE01z1(){
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
              let restRow = items.splice(index, 1)[0];
              this.removeTreeData.push(restRow)
            }
          })
          xTree.syncData()
        })


        // let xTree = this.$refs.xTree
        // let removeRecords = xTree.getRadioRecord()
        // let catalogData = []
        // if(removeRecords.remark != 'catalog'){
        //   catalogData.push(removeRecords)
        // }
        // if (catalogData.length == 0) {
        //   this.$message({
        //     message: '请勾选材料数据后进行删除',
        //     type: 'warning'
        //   })
        //   return
        // }
        // this.$confirm('此操作将删除所选档案材料, 是否继续?', '提示', {
        //   confirmButtonText: '确定',
        //   cancelButtonText: '取消',
        //   type: 'warning'
        // }).then(() => {
        //   catalogData.forEach(row => {
        //     let matchObj = XEUtils.findTree(this.queryData, item => item === row, this.treeConfig)
        //     if (matchObj) {
        //       let { items, index } = matchObj
        //       // 从树节点中移除
        //       let restRow = items.splice(index, 1)[0];
        //       this.removeTreeData.push(restRow)
        //     }
        //   })
        //   xTree.syncData()
        // })
      },
      //导入excel文件
      improtExcel(){
        this.excelWindows = true
      },
      //上传
      submitUpload(event) {
        if(this.fileList.length==0){
          this.$message({
            type:"warning",
            message:"请选择选择文件后点击上传文件"
          })
          return
        }
        this.$confirm('此操作将替换清除已增加档案材料, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
        })
          .then(() => {
          event.preventDefault();
          this.$refs.upload.submit()
          })
      },
      //调整分类
      adjustmentClassify(){
        let xTree = this.$refs.xTree
        let removeRecord = xTree.getCheckboxRecords()
        let catalogDatas = []
        for (let index in removeRecord) {
          if (removeRecord[index].remark != 'catalog') {
            catalogDatas.push(removeRecord[index])
          }
        }
        if (catalogDatas.length == 0) {
          this.$message({
            message: '请勾选材料数据后进行调整分类',
            type: 'warning'
          })
          return
        }
        let insertRecords = XEUtils.filterTree(this.queryData, item => item.isNew, this.treeConfig)
        let removeRecords = this.removeTreeData
        if(insertRecords.length>0||removeRecords.length>0||this.editDatas.length>0||!this.moveArchive||this.isAllRemove){
          this.$message({
            type:"warning",
            message:"档案材料已做修改，请保存后点击调整分类"
          })
          return;
        }
        this.tree2Datas = catalogDatas
        this.dialogVisible2 = true

        // let xTree = this.$refs.xTree
        // let removeRecord = xTree.getRadioRecord()
        // let catalogDatas = []
        // if (removeRecord.remark != 'catalog') {
        //     catalogDatas.push(removeRecord)
        //   }
        // if (catalogDatas.length == 0) {
        //   this.$message({
        //     message: '请勾选材料数据后进行调整分类',
        //     type: 'warning'
        //   })
        //   return
        // }
        // this.tree2Datas = catalogDatas
        // this.dialogVisible2 = true
      },

      //获取材料树
      getQueryData(params) {
        queryE01z1TreeGrid({isClassIfy:"1"}).then(response => {
          this.data2 = response.data
        })
        queryE01z1TreeGrid({a01Id:this.jumpA01Id,isClassIfy:"1"}).then(response => {
          this.queryData = response.data
          let xTree=this.$refs.xTree;
          xTree.reloadData(response.data).then(()=>{
            xTree.setAllTreeExpansion(true)
          })
        })
      },

      cellStyle({ row, rowIndex, column, columnIndex }){
        // ||column.property === 'year'||
        //   column.property === 'month'||column.property === 'day'||
        //   column.property === 'e01z114'||column.property === 'e01z121'
        if (column.property === 'e01z104Chidren'||column.property === 'e01z111a'||column.property === 'e01z117Year'||
          column.property === 'e01z117Month'||column.property === 'e01z117Day'||
          column.property === 'e01z114'||column.property === 'e01z121'||column.property === "operation") {

          if(row.remark=="catalog"){
            return {
              backgroundColor: 'rgba(210, 210, 210, 0.18)',
              color: 'red'
            }
          }
        }
        if (column.property === 'e01z104') {

          if(row.remark=="catalog"){
            return {
              color: 'red'
            }
          }
        }
      },

      headerCellStyle({column, columnIndex}) {
        return {
          backgroundColor: 'rgba(204, 204, 204, 0.18)',
          color: '#909399',

        }
      },
      // 公用表格表头样式
      headClass() {
        return 'background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px;border:0px solid #dfe6ec'
      },

      //上移
      showDetailEventUp() {
        let xTree = this.$refs.xTree
        let checkTreeData = xTree.getCheckboxRecords();
        if(checkTreeData==null||checkTreeData.length==0||(checkTreeData[0].remark!=null&&checkTreeData[0].remark=="catalog")){
          this.$message({
            type:"warning",
            message:"请勾选一条材料数据"
          })
        }
        let row = checkTreeData[0];
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
          // destorydatas.e01z104=destorydatas.e01z104+1
          // childrenData.children[matchObj.index].e01z104=childrenData.children[matchObj.index].e01z104-1
          childrenData.children[matchObj.index - 1] = childrenData.children[matchObj.index]
          childrenData.children[matchObj.index] = destorydatas
          this.moveArchive=false;
          xTree.syncData()
        }

      },
      //下移
      showDetailEventDown() {
        let xTree = this.$refs.xTree
         let checkTreeData = xTree.getCheckboxRecords();
        if(checkTreeData==null||checkTreeData.length==0||(checkTreeData[0].remark!=null&&checkTreeData[0].remark=="catalog")){
          this.$message({
            type:"warning",
            message:"请勾选一条材料数据"
          })
        }
        let row = checkTreeData[0];
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
          // destorydatas.e01z104=destorydatas.e01z104-1
          // childrenData.children[matchObj.index].e01z104=childrenData.children[matchObj.index].e01z104+1
          childrenData.children[matchObj.index + 1] = childrenData.children[matchObj.index]
          childrenData.children[matchObj.index] = destorydatas
          this.moveArchive=false;
          xTree.syncData()
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
          let checkTreeData = xTree.getCheckboxRecords()[0];
          if(this.tree2Checked.id==checkTreeData.e01z101){
            this.$message({
              type:"warning",
              message:"材料已在当前分类下"
            })
            return;
          }
          let params = JSON.parse(JSON.stringify(checkTreeData));
          params.e01z101 = this.tree2Checked.id
          moveClassify(params).then(res=>{
            if(res.code=="200"){
              this.$message({
                type:"success",
                message:"调整成功"
              })
              this.getQueryData()
               this.dialogVisible2 = false
            }
          })
          // if(true){
          //   return;
          // }
          // e01z1s.forEach(row => {

          //   let matchObj = XEUtils.findTree(this.queryData, item => item === row, this.treeConfig)
          //   if (matchObj) {
          //     let { items, index } = matchObj
          //     // 从树节点中移除
          //     let restRow = items.splice(index, 1)[0]
          //   }
          // })
          // xTree.syncData()

          // xTree.createRow().then(newRow => {
          //   // 插入到第一行
          //   this.queryData.forEach(datas => {
          //     if (datas.id == this.tree2Checked.id) {
          //       if (datas.children == null) {
          //         datas.children = []
          //       }
          //       e01z1s.forEach(newDatas => {
          //         datas.children.push(newDatas)
          //       })
          //     }
          //     if(datas.children!=null&&datas.children.length>0){
          //       datas.children.forEach(childrenData =>{
          //         if(childrenData.id == this.tree2Checked.id){
          //           if(childrenData.children==null){
          //             childrenData.children=[]
          //           }
          //           e01z1s.forEach(newDatas => {
          //             childrenData.children.push(newDatas)
          //           })
          //         }
          //       })
          //     }
          //   })
          //   this.moveArchive=false;
          //   xTree.syncData().then(() => xTree.setActiveRow(newRow))
          // })



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
        if (row.remark == 'catalog'||!this.moveStates) {
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
              xTree.syncData()
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
              xTrees.syncData().then(() => xTrees.setActiveRow(newRow))
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
        var reg = new RegExp('-','g');
        return uuid.replace(reg, '')
      },

      // 上传前校验类型及大小
      changeFile(file,fileList){
        if (fileList.length > 0) {
          //this.fileList = fileList[fileList.length-(fileList.length-1)]
          this.fileList = [fileList[fileList.length - 1]]  // 这一步，展示最后一次选择的文件
        }
        //上传文件参数
        this.uploadData.a01Id = this.a01Id;
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
      // 上传成功后
      uploadSuccess(response){
        if(response.code!="200"){
         this.$message({
            message: response.msg,
            type: 'warning'
          });
        }else{
          let xTree=this.$refs.xTree;
          xTree.reloadData(response.data).then(()=>{
            this.queryData=response.data;
            let uploadX = this.$refs.upload.clearFiles();
            this.fileName='';
            xTree.setAllTreeExpansion(true)
          })
          this.excelWindows=false;
          this.isAllRemove=true;
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
  /deep/.vxe-table .vxe-tree--node-btn {

    background-color: rgba(210, 210, 210, 0.18);
  }
  //行内加按钮时高度被撑开导致编辑时问题   size="mini"时  其他另行调整
  /deep/.vxe-table .vxe-cell {
    height: 28px;
  }
    .el-scrollbar{
	   height: 100%;
	 }
  /deep/ .el-scrollbar .el-scrollbar__wrap {
    overflow-x: hidden;
  }
  /deep/ .el-tree>.el-tree-node{
    min-width: 100%;
    display: inline-block;
  }

   /deep/ .vxe-header--column .vxe-required-icon:before{
    content:""
  }
  /deep/ .vxe-header--column .vxe-required-icon{
    display: contents;
  }

</style>

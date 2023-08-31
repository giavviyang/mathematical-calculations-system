<template>
  <div class="provisional-inspection">
      <el-row v-if="apply">
        <el-col style="width:100%" >
          <div class="pi-form" style="width:100%;margin: 0 auto">
            <div style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">第一步：填写申请人基本信息</div>
            <el-col :span="24" >
              <el-form ref="form" :rules="rules" :model="form" label-width="150px"  style="padding: 20px 0 0 0;width: 75%;margin: 0 auto;padding-left: 3%;padding-right: 3%">
                <el-col :span="8">
                  <el-form-item label="申请人姓名" :span="8"   prop="userName">
                    <el-input size="mini" v-model="form.userName"  placeholder="请输入申请人姓名" />
                  </el-form-item>

            </el-col>
                <el-col :span="8">
                  <el-form-item label="申请人证件号" :span="8"  prop="userCard">
                    <el-input size="mini" v-model="form.userCard"  placeholder="请输入申请人身份证号" />
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item label="申请人所在单位" :span="8"   prop="e01z824a">
                    <el-input size="mini" v-model="form.e01z824a"  placeholder="请输入申请人所在单位"/>
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item label="申请人电话"  :span="8"  prop="userPhone">
                    <el-input size="mini" maxlength="11" v-model="form.userPhone"  placeholder="请输入申请人电话" />
                  </el-form-item>
                </el-col>


                <el-col :span="8">
                  <el-form-item label="申请理由" :span="8"   prop="applicationReasons">
                    <el-input size="mini" v-model="form.applicationReasons"  placeholder="请输入申请人理由" />
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item label="申请查阅截止时间至"    :span="8"  prop="endTime">
                    <el-date-picker
                      :picker-options="pickerOptions"
                      size="mini"
                      style="width: 100%"
                      v-model="form.endTime"
                      type="datetime"
                      placeholder="选择日期时间"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      default-time="12:00:00">
                    </el-date-picker>
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item label="申请人职务" :span="8"  prop="duty">
                    <el-input size="mini" v-model="form.duty"  placeholder="请输入申请人职务" />
                  </el-form-item>
                </el-col>





                <el-col :span="8">
                  <el-form-item label="打印内容" :span="8"   prop="printRemark">
                    <el-input v-model="form.printRemark" type="textarea"  placeholder="若打印请输入打印内容" />
                  </el-form-item>
                </el-col>

                <el-col :span="8">
                  <el-form-item label="备注" :span="8"   prop="e01z831">
                    <el-input v-model="form.e01z831" type="textarea"  placeholder="请输入备注" />
                  </el-form-item>
                </el-col>
              </el-form>
            </el-col>
          </div>
          <div style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">第二步：请选择需要查阅的干部档案</div>
          <div  style="width:75%;margin: 0 auto;padding:25px 0 0 0">
            <div style="width: 100%">
              <el-form :inline="true" style="width: 100%;">

                <el-form-item label="干部状态" style="margin-left: 20px" prop="state">
                  <el-select  v-model="a01state" placeholder="请选择" clearable size="mini" style="width: 150px">
                    <el-option v-for="item in states" :key="item.dictCode" :label="item.fullName" :value="item.dictCode">
                    </el-option>
                  </el-select>
                </el-form-item>

                <el-form-item label="" class="input-w">
                  <el-input v-model="cadresText"  @keyup.enter.native="search(1)"  placeholder="请输入部门" size="mini"/>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" icon="el-icon-search" @click="search(1)" size="mini">部门搜索</el-button>
                </el-form-item>

                <el-form-item label="" class="input-w">
                  <el-input v-model="personnelText"  @keyup.enter.native="search(2)"  placeholder="请输入人员" size="mini"/>
                </el-form-item>

                <el-form-item>
                  <el-button type="primary" icon="el-icon-search" @click="search(2)" size="mini">干部搜索</el-button>
                </el-form-item>




              </el-form>
            </div>
            <!-- 左边表格数据 -->
            <el-table :data="leftData" border
                      :header-cell-style="headClass"
                      stripe
                      tooltip-effect="light"
                      style="width: 100%;" height="222px"  @cell-dblclick="deptClick"   ref="searchTableData" >
              <el-table-column style="width: 1%" type="index" align="center" label="序号" />
              <el-table-column show-overflow-tooltip prop="a0101"  v-if="isa01 == 2"  label="干部姓名" align="center"  />
              <el-table-column show-overflow-tooltip prop="number" v-if="isa01 == 2&&false" label="干部编号" align="center"  />
              <el-table-column show-overflow-tooltip prop="deptName"  v-if="isa01 == 2" label="所属部门" align="center"  />
              <el-table-column show-overflow-tooltip prop="deptName"  v-if="isa01 == 1" label="部门名称" align="center"  />
              <el-table-column show-overflow-tooltip prop="parentId"  v-if="isa01 == 1" label="上级部门" align="center"  />


              <el-table-column show-overflow-tooltip prop="a0104"  v-if="isa01 == 2" label="性别" align="center"  :formatter="getShowData" />
              <el-table-column show-overflow-tooltip prop="a0107"  v-if="isa01 == 2" label="出生日期" align="center"  :formatter="getShowData" />
              <el-table-column show-overflow-tooltip prop="state"  v-if="isa01 == 2" label="干部状态" align="center"
                               :formatter="stateFormat"
              />
              <el-table-column  v-if="isa01 == 2" label="操作" align="center" width="200" >
                <template slot-scope="scope">
                  <el-button type="text" size="mini" @click="wholeConsult(scope.row)">整本查阅</el-button>
                  <el-button type="text" size="mini" @click="groupConsult(scope.row)">分类查阅</el-button>
                  <el-button type="text" size="mini" @click="materConsult(scope.row)">材料查阅</el-button>
                </template>
              </el-table-column>
            </el-table>

          </div>
          <div style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">第三步：确认申请信息</div>
          <div  style="width:75%;margin: 0 auto;padding:25px 0 0 0">
            <el-table
              :default-sort="{ prop: 'date', order: 'descending' }"
              stripe
              border
              width="100%"
              tooltip-effect="light"
              ref="applicationData"
              style="margin: 0 auto;width: 100%"
              :header-cell-style="headClass"
              :data="applicationData"
              height="222px"
            >

              <el-table-column :resizable="false"  align="center" width="50" type="index" label="序号" />
              <el-table-column show-overflow-tooltip :resizable="false" v-if="true" label="被申请干部姓名" align="center" prop="a0101"  width="130" />
              <el-table-column show-overflow-tooltip :resizable="false" prop="deptName" width="150"  label="被申请干部所属部门" align="center"  />
              <el-table-column show-overflow-tooltip :resizable="false" v-if="true" label="被申请干部性别" align="center" prop="a0104" width="130"   :formatter="getShowData"   >
              </el-table-column>
              <el-table-column show-overflow-tooltip :resizable="false" v-if="true" label="被申请干部出生日期" align="center" prop="a0107" :formatter="getShowData" width="150">
              </el-table-column>
              <el-table-column show-overflow-tooltip :resizable="false" v-if="true" label="被申请干部状态" align="center" prop="state" :formatter="getShowData" width="150"  >
              </el-table-column>
              <el-table-column show-overflow-tooltip :resizable="false" v-if="true" label="被申请查阅类型" align="center" prop="contype" :formatter="getShowData" width="130">
              </el-table-column>
              <el-table-column :resizable="false" v-if="true" align="center" label="查阅内容" :show-overflow-tooltip="true" prop="e01z1_name" :formatter="getShowData" >
              </el-table-column>
              <el-table-column :resizable="false" label="操作" align="center" width="100" fixed="right">
                <template slot-scope="scope">
                  <el-button type="text" size="mini" @click="electronicFeedback(scope.row)">移除</el-button>
                </template>
              </el-table-column>
            </el-table>

          </div>
          <div style="padding: 20px 0 0 0;width: 75%;margin: 0 auto">
            <el-button type="primary" style="float: right;margin-right: 10px;width:80px" size="mini" @click="comitGridValues">发起申请</el-button>
          </div>


        </el-col>
      </el-row>


      <!-- 档案分类查阅窗口 -->
      <el-dialog title="选择需要查阅的档案分类" height="500px" :close-on-click-modal="false" :visible.sync="dialogVisible2"  class="dialog-style" >
        <el-tree  class="chenge-tree" node-key="id"   ref="tree2" default-expand-all :highlight-current="true" show-checkbox :data="data2"
                  icon-class="el-icon-folder-opened" />
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible2 = false" size="mini">取 消</el-button>
          <el-button type="primary" size="mini" @click="changeOtherTreeNode">确定</el-button>
        </span>
      </el-dialog>

      <!--单份材料查阅窗口 -->
      <el-dialog title="选择需要查阅的材料" height="500px" :close-on-click-modal="false" :visible.sync="dialogVisible1"  class="dialog-style" >
        <el-scrollbar style="height: 500px;">
          <el-tree height="600px" class="chenge-tree" @check="e01z1Check" node-key="id" ref="tree1" default-expand-all :highlight-current="true" show-checkbox :data="data1"
                   icon-class="el-icon-folder-opened" />
        </el-scrollbar>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible1 = false" size="mini">取 消</el-button>
          <el-button type="primary" size="mini" @click="changeA01E01z1s">确定</el-button>
        </span>
      </el-dialog>

      <el-row v-if="approval">
        <el-col v-if="approval" :span="24" :xs="24" style="margin: 0;padding: 0;border: 1px solid #cccccc;min-height: calc(100vh - 86px)">
          <div :class="loading" style="width: 80%;margin-bottom:15px;line-height:30px;border-bottom: 1px solid #ccc;margin: 0 auto;padding:20px 0 0 0 ">
            <div style="text-align: center;">{{apploveTitle}}
              <el-button size="mini" type="primary" @click="reInspection" style="float: right">重新发起申请</el-button>
            </div>

          </div>
          <el-table :data="approvalData"
                    border
                    style="width: 80%; margin-left: 10%;margin-top: 50px;margin-bottom: 20px"
                    height="600px"
                    stripe
                    size="mini"
                    tooltip-effect="light"
                    :header-cell-style="headClass"
                    ref="approvalData" >
            <el-table-column  type="index" width="50" align="center" label="序号" />
            <el-table-column prop="a0101" label="被申请人姓名" align="center"  />
            <el-table-column prop="deptName" label="被申请人部门" align="center"  />
            <el-table-column prop="e01z111a" :show-overflow-tooltip="true" label="申请内容" align="center"  />
            <el-table-column prop="adoptE01z1Msg" :show-overflow-tooltip="true"
                            :formatter="function(row, column, cellValue, index){
                              if(row.adoptE01z1Msg==null||row.adoptE01z1Msg==''){
                                return row.e01z111a
                              }else{
                                return cellValue;
                              }
                            }"
                            label="申请通过内容" align="center"  />
            <el-table-column prop="approvTime" label="申请时间" min-width="120px" align="center"   />
            <el-table-column prop="endDate" min-width="120px" label="申请查阅截止时间至" align="center"   />
            <el-table-column  label="操作" align="center" width="200" >
              <template slot-scope="scope">
                <router-link :to="'/browse/approvalLook/' + scope.row.a01Id + '/' + scope.row.id + '/' + scope.row.a0101" class="link-type">
                  <span>查看档案</span>
                </router-link>
              </template>
              <!--          <template slot-scope="scope">-->
              <!--&lt;!&ndash;            @click="wholeConsult(scope.row)"&ndash;&gt;-->
              <!--            <el-button type="text" size="mini" @click="look(scope.row)" >查看档案材料</el-button>-->
              <!--          </template>-->
            </el-table-column>
          </el-table>

        </el-col>
      </el-row>
<!--    </el-row>-->

  </div>

</template>
<script>
  import { queryA01List } from '@/api/usual/transfer'
  import { queryDictionaryByType,queryE01z1TreeGrid} from '@/api/usual/receive'
  import { queryE01zcRelaData,addE01zcRela,check} from '@/api/usual/provisonalInspect'
  import home from '../approvalLook'
  import Layout from '@/layout/index'


  export default {
    name:'临时查阅申请',
    watch: {
      leftData :{
        handler(newName, oldName) {
          let _this = this;
          _this.$nextTick(()=>{
            _this.$refs.searchTableData.doLayout()
            // _this.$refs.searchTableData.doLayout()
            _this.$refs.searchTableData.bodyWrapper.scrollTop =0;


          })
        },
        deep:true,
        // immediate:true

      },
      applicationData:{
        handler(newName, oldName) {
          console.log(1);
          let _this = this;
          _this.$nextTick(()=>{
            _this.$refs.applicationData.doLayout();
          })
        },
        deep:true,
        // immediate:true
      },
      approvalData:{
        handler(newName, oldName) {
          console.log(1);
          let _this = this;
          _this.$nextTick(()=>{
            _this.$refs.approvalData.doLayout();
          })
        },
        deep:true,
        // immediate:true
      },

    },
    data() {

      return {
        //时间限制
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() <= Date.now();
          },
        },
        rules:{
          endTime:[
            { required: true, message: '请选择申请查阅截止时间至', trigger: 'blur' },
          ],
          userName:[
            { required: true, message: '请输入申请人姓名', trigger: 'blur' },
            { min: 0, max: 18, message: '申请人姓名不得超过18个汉字', trigger: 'blur' }
          ],
          userCard:[
            { required: true, message: '请输入申请人证件号', trigger: 'blur' },
            { min: 0, max: 18, message: '申请人证件号不得超过18个字符', trigger: 'blur' }
          ],
          e01z824a:[
            { required: true, message: '请输入申请人所在单位', trigger: 'blur' },
            { min: 0, max: 40, message: '申请人所在单位不得超过40个汉字', trigger: 'blur' }
          ],
          userPhone:[
            { required: true, message: '请输入申请人电话', trigger: 'blur' },
            {
              pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
              message: "请输入正确的电话号码",
              trigger: "blur"
            }
          ],
          applicationReasons:[
            { required: true, message: '请输入申请理由', trigger: 'blur' },
            { min: 0, max: 200, message: '申请理由不得超过200个汉字', trigger: 'blur' }
          ],
          duty:[
            { min: 0, max: 40, message: '申请人职务不得超过40个汉字', trigger: 'blur' }
          ],
          printRemark:[
            { min: 0, max: 200, message: '打印内容不得超过200个汉字', trigger: 'blur' }
          ],
          e01z831:[
            { min: 0, max: 200, message: '备注不得超过200个汉字', trigger: 'blur' }
          ]

        },
        time:new Date().getTime(),
        approvalData:[],
        approval:false,
        apply:true,
        data1:[],
        data2:[],
        dialogVisible1:false,
        dialogVisible2:false,
        applicationData:[],
        loading:'red',
        personnelText:"",
        cadresText:"",
        a01state:'0',
        showSearchBox:true,
        states:[],
        a0104s1: [],
        apploveTitle:'正在等待审批，请稍等...',
        form:{

        },
        leftData:[],
        checkA01Data:{},
        isa01:2 //搜索的类型  部门还是干部
      }
    },
    computed: {
      visitedViews() {
        return this.$store.state.tagsView.visitedViews
      },
      routes() {
        return this.$store.state.permission.routes
      }
    },
    mounted() {
      this.initData()
      this.getQueryData()
      console.log(this.time);
      console.log(this.$route)
    },
    created(){

      // console.log(visitedViews());

    },
    methods: {
      reInspection(){
        this.apply = true;
        this.approval = false;
        this.applicationData = [];
      },
      look(val){
        //新增一个sheet title为  xxx干部的档案
        console.log(val);
        this.$router.addRoutes([
          {
            alwaysShow:true,
            path:'usual',
            name:'电子档案查阅管理',
            hidden:true,
            component:Layout,
            children:[
              {
                path: '/usual/approvalLook/',
                name: 'approvalLook',
                component:home
              }
            ]
          },

        ])
        // "8567db2cc8b74594b44c362feea6da8a"
        // "胡雯玉"
        this.$router.push({
          name:'approvalLook',
          params:{
            id:val.id,
            a0101:val.a0101,

          }
        })

        // console.log(this.$store.state.tagsView.visitedViews);
        // this.$store.state.tagsView.visitedViews.push({
        //   fullPath:'/usual/approvalLook/' + val.a01Id + "/" + val.id + "/" + val.a0101,
        //   name:'查看电子档案',
        //   path:'/usual/approvalLook',
        //   title:val.a0101 + '的档案',
        //   hash:new Date().getTime(),
        //   query:{},
        //   meta:{
        //     icon:'#',
        //     title:val.a0101 + '的档案'
        //   }
        // })


        // console.log(this.$router);
        // console.log()
        // this.$router.push({
        //   name:'查看电子档案',
        //   params:{
        //     a01Id:val.a01Id,
        //     e01zcRelaId:val.id,
        //     a0101:val.a0101
        //   }
        // })
      },

      stateFormat(row,column,value){
        let str = '';
        this.states.map(item=>{
          if(item.dictCode==value){
            str = item.fullName;
          }
        })
        return str;
      },
      //点击查看详情事件


      //材料选择选择事件
      e01z1Check(node,nodes){
        console.log(node);
        //选中的为分类
        if(node.remark == 'catalog'){
          if(node.id == '9'||node.id=='4'){
            let b = false;
            node.children.map(item=>{
              if(item.children.length>0){
                b = true;
              }

              if(typeof item.remark == undefined){

              }

            })
            if(!b){
              this.$refs.tree1.setChecked(node.id,false,true);
            }

            node.children.map(item=>{
              if(typeof item.remark == undefined){
                this.$refs.tree1.setChecked(item.id,true,false);
                this.$refs.tree1.setChecked(node.id,true,false);
              }
            })
          }
          if(node.children.length==0){
            console.log(1);
            //取消选中
            this.$refs.tree1.setChecked(node.id,false,false);
          }
        }
      },
      //提交按钮
      comitGridValues(){
        //校验审批列表是否存在相同数据
        this.$refs.form.validate(valid=>{
          if(!valid){
            return;
          }
          // console.log(this.applicationData);
          if(this.applicationData.length==0){
            this.$message({
              message:'请选择查阅内容',
              type:'warning'
            })
            return;
          }
          let b = true;
          this.applicationData.map(item=>{
            let i = 0;
            this.applicationData.map(item1=>{
              if(item1.id==item.id){
                i++;
              }
            })
            if(i>1){
              b = false;

            }
          })
          if(!b){
            this.$message({
              message:'表格中存在重复数据',
              type:'warning'
            })
            return;
          }
          console.log(this.applicationData);
          // console.log(this.form);
          //像后台发起审批请求
          let params = JSON.parse(JSON.stringify(this.form));
          let time = this.form.endDate;

          params.endDate = params.endTime;
          params.target = 2;
          delete params.endTime;
          // console.log(params);
          params.relas = JSON.stringify(this.applicationData);
          addE01zcRela(params).then(response=>{
            if(response.code==200){
              //切换到等待页面
              this.apply = false;
              this.approval = true;
              //重置文字提示
              this.apploveTitle = "正在等待审批，请稍等...";
              this.loading = "red";
              this.approvalData = [];
              //向后台发起请求  查找该审批批次 状态是否发生变化 如果发生变化则停止刷新并切换成相应的数据
              let ok = false;
              let _this = this;
              let loading = function(){
                setTimeout(()=> {
                  check(response.data).then(response1 => {
                    if (response1.data == 1) {
                      _this.apploveTitle = "审批通过";
                      _this.loading = 'blue';
                      queryE01zcRelaData(response.data).then(response2 => {
                        console.log(response2);
                        _this.approvalData = response2.data.data;
                        _this.approvalData.map(item=>{
                          item.endDate = response2.data.e01zc.endDate;
                          item.approvTime = response2.data.e01zc.approvTime
                        })
                      })
                    } else if(response1.data == 0){
                      loading();
                    } else{
                      _this.loading = 'red';
                      _this.apploveTitle = "审批未通过";
                    }
                  })
                },1000);
              }
              loading();
            }
          })

        })


        // console.log(params.relas);



      },
      //第三步移除按钮
      electronicFeedback(row){
        console.log(row);
        console.log(this.applicationData)
        // this.applicationData.splice(row,1)
        let app = [];
        this.applicationData.map(item=>{
          if(item.id == row.id){

          }else{
            app.push(item);
          }
        })
        this.applicationData = app;
      },
      //获取材料树
      getQueryData(){
        queryE01z1TreeGrid().then(response =>{
          this.data2=response.data;
        })
        //queryE01z1TreeGrid(params).then(response=>{
        //  this.queryData=response.data
        // })
      },
      //根据干部信息获取材料树
      getQueryDataByA01(val){
        console.info(val)
        queryE01z1TreeGrid({a01Id:val.id}).then(response =>{
          this.data1=response.data;
          this.dialogVisible1=true;
        })
      },
      //添加时校验重复数据
      checlData(val){
        let b = true;
        this.applicationData.map(item=>{

          if(item.id == val.id){

            b = false;
          }
        })
        if(!b){
          this.$message({
            message:val.a0101 + '信息已添加',
            type:'warning'
          })
        }
        return b;
      },
      //整本查阅
      wholeConsult(val){
        let b = this.checlData(val);
        if(!b){
          return;
        }
        let oneData = JSON.parse(JSON.stringify(val));
        oneData.contype="1";
        oneData.e01z1_name="整本";
        oneData.e01z1_id="zb"
        this.applicationData.push(oneData)
        this.$message({
          message:'添加成功',
          type:'success'
        })
      },
      //分类查阅
      groupConsult(val){
        this.checkA01Data=val;
        this.dialogVisible2=true
        if(typeof this.$refs.tree2 == 'undefined'){
          return;
        }
        let checkTreeData = this.$refs.tree2.getCheckedNodes();
        checkTreeData.map(item=>{
          this.$refs.tree2.setChecked(item,false,true);
        })
        // this.getQueryData();
      },
      //分类查阅页面确定
      changeOtherTreeNode(){
        let b = this.checlData(this.checkA01Data);
        if(!b){
          return;
        }
        //
        let checkTreeData = this.$refs.tree2.getCheckedNodes();
        console.log(checkTreeData);
        if(checkTreeData.length>0){
          let oneData = JSON.parse(JSON.stringify(this.checkA01Data));
          oneData.contype="2";
          let e01z1_ids="";
          let e01z1_names="";
          // let checkKey = [];
          // this.$refs.tree2.setChecked('5',,true);
          //
          for(let i in checkTreeData){
            // checkKey.push(i.id)
            // console.log(i);
            this.$refs.tree2.setChecked(checkTreeData[i].id,false,true);
            if(checkTreeData[i].id!=4&&checkTreeData[i].id!=9){
              if(e01z1_ids!=""&&e01z1_names!=""){
                e01z1_ids=e01z1_ids+","+checkTreeData[i].id;
                e01z1_names=e01z1_names+","+checkTreeData[i].e01z111a;
              }else{
                e01z1_ids=checkTreeData[i].id;
                e01z1_names=checkTreeData[i].e01z111a;
              }
            }
          }
          // .setCheckedKeys(checkKey,)
          oneData.e01z1_id=e01z1_ids;
          oneData.e01z1_name=e01z1_names;
          this.applicationData.push(oneData)
          this.dialogVisible2=false;
        }else{
          this.$message({
            message: '请选中目录后点击确定',
            type: 'warning'
          });
        }

      },
      //单份材料查阅
      materConsult(val){
        this.checkA01Data=val;
        this.getQueryDataByA01(val);
      },
      //单份材料查阅页面确定按钮
      changeA01E01z1s(){
        let b = this.checlData(this.checkA01Data);
        if(!b){
          return;
        }
        let checkTreeData = this.$refs.tree1.getCheckedNodes();
        if(checkTreeData.length==0){
          this.$message({
            message: '请选中材料后点击确定',
            type: 'warning'
          });
        }
        let e01z1Datas=[];
        for(let i in checkTreeData){
          this.$refs.tree1.setChecked(checkTreeData[i].id,false,true);
          if(checkTreeData[i].remark!="catalog"){
            e01z1Datas.push(checkTreeData[i])
          }
        }
        if(e01z1Datas.length==0){
          this.$message({
            message: '请选中材料后点击确定',
            type: 'warning'
          });
        }
        let oneData = JSON.parse(JSON.stringify(this.checkA01Data));
        oneData.contype="3";
        let e01z1_ids="";
        let e01z1_names="";
        for(let n in e01z1Datas){
          if(e01z1_ids!=""&&e01z1_names!=""){
            e01z1_ids=e01z1_ids+","+e01z1Datas[n].id;
            e01z1_names=e01z1_names+","+e01z1Datas[n].e01z111a;
          }else{
            e01z1_ids=e01z1Datas[n].id;
            e01z1_names=e01z1Datas[n].e01z111a;
          }
        }
        oneData.e01z1_id=e01z1_ids;
        oneData.e01z1_name=e01z1_names;
        this.applicationData.push(oneData)
        this.dialogVisible1=false;
      },
      //初始化页面所需参数
      initData(){
        // 当前页面所需字典表数据 性别
        queryDictionaryByType({ dictType: 'GB22611' })
          .then(response => {
            this.a0104s1 = response.data
          })
        // 获取干部状态
        queryDictionaryByType({ dictType: 'FDA01State' })
          .then(response => {
            this.states = response.data
          })
      },
      //材料接收-列表通用formatter
      getShowData(row,col){
        //时间转换
        if(col.property=="contype" || col.property=="contype"){
          if(row[col.property]!=null&&row[col.property]!=''){
            if(row[col.property]==1){
              return "整本档案查阅"
            }
            if(row[col.property]==2){
              return "档案分类查阅"
            }
            if(row[col.property]==3){
              return "单份材料查阅"
            }
          }
        }
        //查阅类型
        if(col.property=="e01z117" || col.property=="a0107"){
          if(row[col.property]!=null&&row[col.property]!=''){
             return this.moment(row[col.property]).format("YYYY-MM-DD")
          }
        }
        //干部状态
        if(col.property=="state"){
          let returnValues='';
          this.states.forEach(data => {
            if(data.dictCode==row[col.property]){
              returnValues=data.fullName;
            }
          })
          return returnValues;
        }
        //性别
        if(col.property=="a0104"){
          let returnValues='';
          this.a0104s1.forEach(data => {
            if(data.dictCode==row[col.property]){
              returnValues=data.fullName;
            }
          })
          return returnValues;
        }
        return row[col.property]
      },
      // grid  双击部门 查询人员
      deptClick(val) {
        if(this.isa01 == 2){
          return ;
        }
        this.isa01 = 2;
        console.log(val);
        if (val.children) {
          this.deptNshow=true;
          let tableData = [];
          this.leftData = [];
          for(let i in val.children){
            if(val.children[i].state==this.a01state){
              tableData.push(val.children[i]);
            }
          }

          this.leftData = tableData

          // this.beforeQueryData = val.children

        }
      },
      //干部 部门 搜索
      search(type) {


        const _this = this;
        if (type === 1) {
          this.searchText = this.cadresText
        } else {

          console.log(this.personnelText);
          if(this.personnelText == null || this.personnelText==''){
            this.$message({
              message:'请输入干部搜索条件',
              type:'warning'
            })
            return ;
          }
          this.searchText = this.personnelText
        }
        this.isa01 = type;
        queryA01List({ searchText: this.searchText, state:this.a01state, type: type })
          .then(response => {
            if(type === 1){
              _this.leftData = response.data;
            }else{
              _this.leftData = response.data;
            }

          })
      },
      headClass() {
        return "background:rgba(204, 204, 204, 0.18);color:#909399;height:31px;font-size:12px";
      },
    }
  }
</script>

<style lang="scss" scoped>
  .provisional-inspection {
    overflow: auto;
    height: calc(100vh - 86px);
  }
  /deep/.el-scrollbar__wrap {
    overflow-x: hidden;
  }
  .red{
    color:red;
  }
  .blue{
    color: dodgerblue;
  }
  /deep/.el-table__body td {
    padding: 0;
    height: 40px;
  }
  /deep/ body .el-table th.gutter{
    display: table-cell!important;
  }
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
      background: rgba(204, 204, 204, 0.18);
      padding-right: 15px;
      padding: 11px;
    }
    /deep/.el-button+.el-button {
      margin-left: 6px;
    }

  }
  /deep/ .el-table__fixed-right-patch {
    background-color:#F8F8F9;
  }

  /deep/ .el-table .el-table__header-wrapper th{
    height:31px;
  }
  //设置统一表头
    /deep/.el-table tbody tr:hover>td {
      background-color:#ecf5ff !important
    }

  /deep/.el-table__body td {
     padding: 0;
     height: 40px;
   }
</style>

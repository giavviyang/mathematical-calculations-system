<template>
  <el-row>
    <el-col :span="4">
      <el-tree class="tree-style" :data="data" :props="defaultProps" @node-click="handleNodeClick" />
    </el-col>
    <el-col :span="20">
        <div class="app-search" v-if="showElectronic">
          <el-form :inline="true" class="search-form"
          style="background:rgba(204, 204, 204, 0.18);padding: 15px;    margin-right: 20px;">
            <el-form-item label="部门名称">
              <el-input v-model="queryParams.e01zc24a" placeholder="请输入内容" size="mini"></el-input>
            </el-form-item>
            <el-form-item label="干部名称">
              <el-input v-model="queryParams.e01z804" placeholder="请输入内容" size="mini"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="mini" icon="el-icon-search" @click="depement()">查询</el-button>
            </el-form-item>
           <!-- <el-form-item>
              <el-button size="mini" icon="el-icon-refresh" @click="clear()">重置</el-button>
            </el-form-item> -->
          </el-form>
        </div>

      <!-- <div class="seach-right">
   <el-dropdown class="btn-seach right-btn">
          <el-button icon="el-icon-menu" circle size="mini " />
          <el-dropdown-menu>
            <el-checkbox-group v-for="(item, index) in browseCol" :key="index" v-model="browseCol[index].isCol">
              <el-checkbox>{{ item.label }}</el-checkbox>
            </el-checkbox-group>
          </el-dropdown-menu>
        </el-dropdown>
        <el-col :span="1.5" class="btn-seach ">
          <el-button  type="primary" icon="el-icon-search" circle size="mini "
            @click="showElectronic = !showElectronic" />
        </el-col>
      </div> -->



        <!-- 表格 -->
      <el-table :default-sort="{ prop: 'date', order: 'descending' }" stripe border style="width: 100%;"
        :data="tableData" @selection-change="handleSelectionChange">
        <el-table-column type="expand" align="center">
          <template @row-click="openDetails">
            <el-form label-position="left" inline class="demo-table-expand">
              <template @selection-change="handleSelectionChange">
                <el-table :data="tableData" style="width: 100%">
                  <el-table-column type="selection" width="55" />
                  <el-table-column prop="type" label="材料类型" />
                  <el-table-column prop="catalog" label="材料名称" />
                  <el-table-column fixed="right" label="操作" width="100">
                    <template slot-scope="scope">
                      <el-button type="text" size="mini" @click="electronicViewS(scope.row)">查看</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </el-form>
          </template>
        </el-table-column>
        <!-- 动态获取题名 <el-table-column type="selection" width="55"  v-for=" i index in queryData"></el-table-column> -->
        <el-table-column type="selection" width="55" />
        <el-table-column v-for="(item, index) in browseCol" :key="index" v-if="browseCol[index].isCol"
          :label="browseCol[index].label" :prop="browseCol[index].prop" />
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="electronicView(scope.$index, scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination :page-sizes="[100, 200, 300, 400]" :page-size="100"
          layout="total, sizes, prev, pager, next, jumper" :total="400" @size-change="handleSizeChange"
          @current-change="handleCurrentChange" />
      </div>
    </el-col>
  </el-row>
</template>
<script>
  export default {
    data() {
      return {
        browseCol: [{
            label: 'id',
            isCol: false,
            prop: 'id'
          },
          {
            label: '隶属全宗',
            isCol: false,
            prop: 'org_id'
          },
          {
            label: '人员信息id',
            isCol: false,
            prop: 'a01_id'
          },
          {
            label: '姓名',
            isCol: true,
            prop: 'a0101'
          },
          {
            label: '材料类号',
            isCol: false,
            prop: 'a01z101'
          },
          {
            label: '材料序号',
            isCol: false,
            prop: 'a01z104'
          },
          {
            label: '材料编号',
            isCol: false,
            prop: 'a01z107'
          },
          {
            label: '材料名称',
            isCol: false,
            prop: 'a01z111a'
          },
          {
            label: '页数',
            isCol: true,
            prop: 'a01z114'
          },
          {
            label: '材料制成日期',
            isCol: true,
            prop: 'a01z117'
          },
          {
            label: '份数',
            isCol: true,
            prop: 'a01z124'
          },
          {
            label: '档案卷本号',
            isCol: true,
            prop: 'a01z127'
          },
          {
            label: '档案条形码',
            isCol: true,
            prop: 'a01z131'
          },
          {
            label: '备注',
            isCol: true,
            prop: 'a01z121'
          }
        ],
        tableData: [{
          a0101:'陈建斌',
          a01z114:'3',
          a01z117:'2019-05-06',
          a01z124:'5',
          a01z127:'001',
          a01z131:'20120120',
          a01z121:'无',
          type:'履历材料',
            pri_type: '查阅',
            user_name: '王小虎',
            catalog: '履历表',
            mark: '无'
          },
          {
              a0101:'张纯',
          a01z114:'3',
          a01z117:'2019-05-06',
          a01z124:'5',
          a01z127:'001',
          a01z131:'20120120',
          a01z121:'无',
           type:'履历材料',
            pri_type: '查阅',
            user_name: '王小虎',
            catalog: '履历表',
            mark: '无'
          },
          {
              a0101:'孙浩然',
          a01z114:'3',
          a01z117:'2019-05-06',
          a01z124:'5',
          a01z127:'001',
          a01z131:'20120120',
          a01z121:'无',
           type:'履历材料',
            pri_type: '查阅',
            user_name: '王小虎',
            catalog: '履历表',
            mark: '无'
          },
          {
              a0101:'赵洪涛',
          a01z114:'3',
          a01z117:'2019-05-06',
          a01z124:'5',
          a01z127:'001',
          a01z131:'20120120',
          a01z121:'无',
           type:'履历材料',
            catalog: '履历表',
          },
          {
              a0101:'孙倩',
          a01z114:'3',
          a01z117:'2019-05-06',
          a01z124:'5',
          a01z127:'001',
          a01z131:'20120120',
          a01z121:'无',
           type:'履历材料',
            catalog: '履历表',
          },
          {
              a0101:'刘璐',
          a01z114:'3',
          a01z117:'2019-05-06',
          a01z124:'5',
          a01z127:'001',
          a01z131:'20120120',
          a01z121:'无',
           type:'履历材料',
            catalog: '履历表',
          }
        ],
       data: [{
            id: 1,
            label: "国务院",
            children: [{
              id: 2,
              label: "研究室",
            },{
              id: 3,
              label: "业务处",
            },{
              id: 4,
              label: "加工贸易处",
            },{
              id: 5,
              label: "稽查处",
            },{
              id: 6,
              label: "技术处",
            },{
              id: 7,
              label: "海关A分中心",
	      children: [{
                  id: 11,
                  label: "下属AA单位"
                },
                {
                  id: 12,
                  label: "下属AB单位"
                }
              ]
            },{
              id: 8,
              label: "海关B分中心",
	      children: [{
                  id: 13,
                  label: "下属BA单位"
                },
                {
                  id: 14,
                  label: "下属BB单位"
                }
              ]
            },{
              id: 9,
              label: "海关C分中心",
	      children: [{
                  id: 15,
                  label: "下属CA单位"
                },
                {
                  id: 16,
                  label: "下属CB单位"
                }
              ]
            },{
              id: 10,
              label: "海关D分中心",
	      children: [{
                  id: 17,
                  label: "下属DA单位"
                },
                {
                  id: 18,
                  label: "下属DB单位"
                }
              ]
            }
	    ]


          }
        ],

        showElectronic: true,
        queryParams: {
          e01zc24a: '',
          e01z804: ''
        },
        defaultProps: {
          children: 'children',
          label: 'label'
        }
      }
    },
    methods: {
      handleNodeClick(data) {
        console.log(data)
      },
      openDetails() {
        console.log(111)
      },
      handleQueryElectronic() {},
      resetQueryElectronic() {},
      handleAddElectronic() {},
      handleUpdateElectronic() {},
      handleContinueElectronic() {},
      handleExportElectronic() {},
      handlePrintElectronic() {},
      electronicViewS() {},
      electronicFeedbackS() {},
      electronicView() {},
      electronicFeedback() {},
      /* 监听 */
      handleSelectionChange() {},
      /* handleSizeChange */
      handleSizeChange() {},
      handleCurrentChange() {}
    }
  }

</script>
<style lang="scss" scoped>
.tree-style{
  padding-top: 20px;
  padding-left: 20px;
  min-height: calc(100vh - 100px);
  border-right: 0.5px solid #e2e3e6;
}

.btn-seach{
float: right;
margin-top: 10px;
    margin-bottom: 10px;
    margin-right: 5px;
}

  .block {
    float: right;
  }
  .app-search {
    margin:15px;
    // margin-top: 20px;
    // margin-left: 20px;
  }

.seach-right {
  overflow:hidden;
  // border-top: 0.5px solid #e2e3e6;
  padding-right: 40px;
}
.el-form--inline .el-form-item {
margin:0px;
}
</style>
